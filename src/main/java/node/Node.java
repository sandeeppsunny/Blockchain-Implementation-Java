package node;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Node implements Serializable {
    String creator;
    Block tail;
    List<Block> blockChain = new LinkedList<Block>();
    static Node instance;

    private Node(String creator) {
        Block genesis = Block.createGenesisBlock(creator);
        this.creator = creator;
        this.blockChain.add(genesis);
        this.tail = genesis;;
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
}
