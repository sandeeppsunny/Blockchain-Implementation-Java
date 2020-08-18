package web;

import node.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(path = "/node")
public class NodeController {

    @RequestMapping(method = POST, path = "/create")
    public Node createNode(@RequestParam("name") String creator) {
        return Node.getInstance(creator);
    }

    @RequestMapping(method = POST, path = "/bootstrap")
    public void bootstrap() {
        Node.getInstance("").bootstrap();
    }

    @RequestMapping(method = GET, path = "/blockchain")
    public List<Block> getBlockchain() {
        return Node.getInstance("").getBlockChain();
    }

    @RequestMapping(method = POST, path = "/blockchain/add")
    public List<Block> addBlock(@RequestParam("data") String data) {
        Node.getInstance("").addBlock(data);
        return Node.getInstance("").getBlockChain();
    }

    @RequestMapping(method = POST, path = "/peer/register")
    public NodeDetail[] registerPeerNode(@RequestBody() NodeDetail nodeDetail) {
        Node.getInstance("").registerPeerNode(nodeDetail);
        Set<NodeDetail> peerNodes = Node.getInstance("").getPeerNodes();
        NodeDetail[] result = new NodeDetail[peerNodes.size()];
        peerNodes.toArray(result);
        return result;
    }

    @RequestMapping(method = GET, path = "/peer")
    public NodeDetail[] getPeerNodes() {
        Set<NodeDetail> peerNodes = Node.getInstance("").getPeerNodes();
        NodeDetail[] result = new NodeDetail[peerNodes.size()];
        peerNodes.toArray(result);
        return result;
    }
}
