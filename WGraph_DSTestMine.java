package ex1;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTestMine {


    @Test
    void getNode() {
        WGraph_DS s = graphCreator();
        assertNotNull(s.getNode(1));
        s.removeNode(1);
        assertNull(s.getNode(1));
    }

    @Test
    void hasEdge() {
        WGraph_DS s = graphCreator();
        assertFalse(s.hasEdge(1,6));
        assertTrue(s.hasEdge(7,8));
        s.removeEdge(7,8);
        assertFalse(s.hasEdge(7,8));
        assertFalse(s.hasEdge(1,1));
    }

    @Test
    void getEdge() {
        WGraph_DS s = graphCreator();
        assertEquals(s.getEdge(1,2),0.5);
        s.connect(1,2,1.2);
        assertNotEquals(s.getEdge(1,2),0.5);
        assertEquals(s.getEdge(1,2),1.2);

    }

    @Test
    void addNode() {
        WGraph_DS s = graphCreator();
        assertNull(s.getNode(15));
        s.addNode(15);
        assertNotNull(s.getNode(15));
    }

    @Test
    void connect() {
        WGraph_DS s = graphCreator();
        assertFalse(s.hasEdge(1,8));
        s.connect(1,8,0.2);
        assertTrue(s.hasEdge(1,8));
        s.connect(1,8,0.5);
        assertEquals(s.getEdge(1,2),0.5);
        assertEquals(s.edgeSize(),10);
    }

    @Test
    void getV() {
        WGraph_DS s = graphCreator();
       assertTrue(s.getV().contains(s.getNode(1)));
        assertTrue(s.getV().contains(s.getNode(2)));
        assertTrue(s.getV().contains(s.getNode(3)));
        assertTrue(s.getV().contains(s.getNode(4)));
        assertTrue(s.getV().contains(s.getNode(5)));
        assertTrue(s.getV().contains(s.getNode(6)));
        assertTrue(s.getV().contains(s.getNode(7)));
        assertTrue(s.getV().contains(s.getNode(8)));
        assertTrue(s.getV().contains(s.getNode(9)));
       s.removeNode(1);
       assertFalse(s.getV().contains(s.getNode(1)));
        assertFalse(s.getV().contains(s.getNode(10)));
        for (int i=0; i<10;i++){
            s.removeNode(i);
        }
        assertEquals(s.getV().size(),0);

    }

    @Test
    void testGetV() {
        WGraph_DS s = graphCreator();
        assertFalse(s.getV(1).contains(6));
        assertFalse(s.getV(1).contains(3));
        assertTrue(s.getV(1).contains(s.getNode(2)));
        assertTrue(s.getV(1).contains(s.getNode(9)));
        assertTrue(s.getV(1).contains(s.getNode(7)));
        s.connect(1,6,3);
        assertTrue(s.getV(1).contains(s.getNode(6)));
        s.removeEdge(1,2);
        assertFalse(s.getV(1).contains(2));
        s.removeNode(1);
        assertNull(s.getV(1));
    }

    @Test
    void removeNode() {
        WGraph_DS s = graphCreator();
        assertNotNull(s.getNode(1));
        s.removeNode(1);
        assertNull(s.getNode(1));
    }

    @Test
    void removeEdge() {
        WGraph_DS s = graphCreator();
        assertTrue(s.hasEdge(1,2));
        s.removeEdge(1,2);
        assertFalse(s.hasEdge(1,2));

    }

    @Test
    void nodeSize() {
        WGraph_DS s = graphCreator();
        assertEquals(s.nodeSize(),9);
        s.addNode(10);
        assertNotEquals(s.nodeSize(),9);
        s.removeNode(1);
        s.removeNode(2);
        assertEquals(s.nodeSize(),8);
    }

    @Test
    void edgeSize() {
        WGraph_DS s = graphCreator();
        assertEquals(s.edgeSize(),9);
        s.connect(1,4,3);
        assertEquals(s.edgeSize(),10);
    }

    @Test
    void getMC() {
        WGraph_DS s = graphCreator();
        int i = s.getMC();
        s.removeNode(1);
        assertEquals(i+1,s.getMC());
        s.removeEdge(2,3);
        assertEquals(i+2,s.getMC());
    }

    @Test
    void testEquals() {
        WGraph_DS t = graphCreator();
        WGraph_DS s = graphCreator();
        assertEquals(t,s);
        t.addNode(11);
        assertNotEquals(t,s);
        s.addNode(11);
        assertEquals(t,s);
        t.connect(1,4,0.5);
        assertNotEquals(t,s);
        t.connect(1,4,0.2);
        assertNotEquals(t,s);
        s.connect(1,4,0.5);
        assertNotEquals(t,s);
        s.connect(1,4,0.2);
        assertEquals(t,s);

    }
public static WGraph_DS graphCreator(){
    WGraph_DS s = new WGraph_DS();
    for (int i = 1; i < 10; i++) {
        s.addNode(i);
    }
    s.connect(1,2,0.5);
    s.connect(2,3,1.7);
    s.connect(4,5,2);
    s.connect(7,8,1);
    s.connect(8,9,3);
    s.connect(2,9,0.1);
    s.connect(1,7,4);
    s.connect(1,9,2);
    s.connect(3,5,1);
    return s;
}


    }

