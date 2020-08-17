package web;

import node.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(path = "/node")
public class NodeController {

    @RequestMapping(method = POST, path = "/create")
    public Node createNode(@RequestParam("name") String creator) {
        return Node.getInstance(creator);
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
}
