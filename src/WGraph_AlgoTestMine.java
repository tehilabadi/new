package ex1;

import ex0.Graph_Algo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTestMine {
    /**
     * this method initializes the graph.
     */
    @Test
    void init() {
        weighted_graph s = WGraph_DSTestMine.graphCreator();
        weighted_graph_algorithms g = new WGraph_Algo();
        g.init(s);

    }

    @Test
    void getGraph() {
        weighted_graph s = WGraph_DSTestMine.graphCreator();
        weighted_graph_algorithms g = new WGraph_Algo();
        g.init(s);
        assertNotNull(s);

    }

    @Test
    void copy() {
        weighted_graph s = WGraph_DSTestMine.graphCreator();
        weighted_graph_algorithms g1 = new WGraph_Algo();
        weighted_graph_algorithms g2 = new WGraph_Algo();
        g1.init(s);
        weighted_graph h = new WGraph_DS();
        h = g1.copy();
        assertEquals(h,s);
        h.removeEdge(1,2);
        assertNotEquals(h,s);
        h.connect(1,2,1);
        assertNotEquals(h,s);
        h.connect(1,2,0.5);
        assertEquals(h,s);
        h.removeNode(1);
        assertNotEquals(h,s);
        h.addNode(1);
        assertNotEquals(h,s);

    }

    @Test
    void isConnected() {
        weighted_graph s = WGraph_DSTestMine.graphCreator();
        weighted_graph_algorithms g = new WGraph_Algo();
        g.init(s);
        assertFalse(g.isConnected());
        s.connect(8,6,0.1);
        assertTrue(g.isConnected());
    }

    @Test
    void shortestPathDist() {
        weighted_graph s = WGraph_DSTestMine.graphCreator();
        weighted_graph_algorithms g = new WGraph_Algo();
        g.init(s);
        assertEquals(g.shortestPathDist(1,2),0.5);
        assertEquals(g.shortestPathDist(1,8),3.6);
        assertEquals(g.shortestPathDist(5,6),-1);
        assertEquals(g.shortestPathDist(1,3),2.2);
        assertEquals(g.shortestPathDist(1,9),0.6);
        s.removeEdge(1,2);
        s.connect(9,3,1);
        assertEquals(g.shortestPathDist(1,3),3);    }

    @Test
    void shortestPath() {
        weighted_graph s = WGraph_DSTestMine.graphCreator();
        weighted_graph_algorithms g = new WGraph_Algo();
        g.init(s);
        LinkedList<node_info> shortPath = new LinkedList<node_info>();
        shortPath.addFirst(s.getNode(3));
        shortPath.addFirst(s.getNode(2));
        shortPath.addFirst(s.getNode(1));
        assertEquals(shortPath,g.shortestPath(1,3));
        assertEquals(g.shortestPath(4,3).size(),3);
        assertEquals(g.shortestPath(4,1).size(),5);
        s.removeEdge(1,2);
        assertEquals(g.shortestPath(4,1).size(),6);

    }

    @Test
    void save() {
        weighted_graph s = WGraph_DSTestMine.graphCreator();
        weighted_graph_algorithms g = new WGraph_Algo();
        g.init(s);
        String file = "g.obj";
        assertTrue(g.save(file));
        WGraph_Algo T = new WGraph_Algo();
       assertTrue(T.load(file));
       assertNotNull(T);
       assertEquals(T,g);
       s.removeEdge(1,2);
       assertNotEquals(T,g);

}
}

