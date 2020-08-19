package node;

import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.*;

public class Node implements Serializable {
    String creator;
    Block tail;
    List<Block> blockChain = new LinkedList<Block>();
    static Node instance;
    Set<NodeDetail> peerNodes = new HashSet<NodeDetail>();
    NodeDetail selfDetail;

    private Node(String creator) {
        Block genesis = Block.createGenesisBlock(creator);
        this.creator = creator;
        this.blockChain.add(genesis);
        this.tail = genesis;
        try {
            this.selfDetail = new NodeDetail(InetAddress.getLocalHost().getHostAddress(), "8080");
        } catch (Exception e) {
            System.out.println("Did not receive IP address");
        }
    }

    public Block getLastBlock() {
        return tail;
    }

    public List<Block> getBlockChain() {
        return blockChain;
    }

    public int getBlockChainSize() {
        return blockChain.size();
    }

    public Block addBlock(String data) {
        Block block = new Block(tail.selfHash, creator, data, System.currentTimeMillis());
        tail = block;
        blockChain.add(block);
        broadcastBlockChain();
        return tail;
    }

    public static boolean validateBlockChain(List<Block> blockChain) {
        Block prevBlock = null;
        for(Block block: blockChain) {
            if(prevBlock == null) {
                prevBlock = block;
                continue;
            }
            if(!prevBlock.selfHash.equals(block.prevHash)) {
                return false;
            }
            prevBlock = block;
        }
        return true;
    }

    public void replaceBlockChain(List<Block> blockChain) {
        if(blockChain.size() <= getBlockChainSize()) {
            System.out.println("Chain is ignored because it isn't longer than the current chain!");
            return;
        }
        if(!validateBlockChain(blockChain)) {
            System.out.println("Chain is invalid. Hash pointers do not match!");
            return;
        }
        this.blockChain = blockChain;
    }

    public synchronized static Node getInstance(String creator) {
        if(instance == null) {
            instance = new Node(creator);
        }
        return instance;
    }

    public void bootstrap() {
        for(NodeDetail node: getPeerNodes()) {
            String uri = "http://" + node.getIpAddress() + ":" + node.getPortNo() + "/node/peer/register";
            RestTemplate restTemplate = new RestTemplate();
            NodeDetail nodeDetail = new NodeDetail(selfDetail.getIpAddress(), selfDetail.getPortNo());
            NodeDetail[] peers = restTemplate.postForObject(uri, nodeDetail, NodeDetail[].class);
            for(NodeDetail peer: peers) {
                if(peer.equals(selfDetail)) {
                    continue;
                }
                registerPeerNode(peer);
                restTemplate.postForObject(uri, nodeDetail, NodeDetail[].class);
            }
        }
    }

    public void broadcastBlockChain() {
        for(NodeDetail node: getPeerNodes()) {
            if(node.equals(selfDetail)) {
                continue;
            }
            String uri = "http://" + node.getIpAddress() + ":" + node.getPortNo() + "/node/blockchain/sync";
            RestTemplate restTemplate = new RestTemplate();
            Block[] blockChainArray = new Block[getBlockChainSize()];
            blockChain.toArray(blockChainArray);
            restTemplate.postForObject(uri, blockChainArray, Object.class);
        }
    }

    public void syncBlockChain(Block[] blockChainArray) {
        List<Block> receivedBlockChain = new LinkedList<Block>(Arrays.asList(blockChainArray));
        replaceBlockChain(receivedBlockChain);
    }

    public synchronized void registerPeerNode(NodeDetail node) {
        this.peerNodes.add(node);
    }

    public Set<NodeDetail> getPeerNodes() {
        return this.peerNodes;
    }


}
