package node;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class NodeTest {
    @Test
    public void createNodeTest() {
        Node node1 = Node.getInstance("node-1");
        node1.addBlock("data-1");
        node1.addBlock("data-2");
        assertTrue(Node.validateBlockChain(node1.getBlockChain()));
        assertEquals(node1.getBlockChainSize(), 3);
    }
}
