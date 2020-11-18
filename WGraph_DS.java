package ex1;

import ex0.node_data;

import java.io.Serializable;
import java.util.*;

/**
 * This class implements the interface weighted_graph which represents an undirectional weighted graph.
 * contains the nodes in the graph, the neighbors of each node, and the weight of each edge between two nodes.
 *
 */
public class WGraph_DS implements weighted_graph,Comparator<node_info>,Serializable {
    private HashMap<Integer, node_info> graph;
    private HashMap<Integer, HashMap<Integer, node_info>> inner;
    private HashMap<Integer, HashMap<Integer, Double>> edges;
    private int countedge = 0;
    private int countmoves = 0;

    //private static int keycount = 0;

    @Override
    public int compare(node_info o1, node_info o2) {
        if (o1.getTag() > o2.getTag())
            return 1;
        else if (o1.getTag() < o2.getTag())
            return -1;
        return 0;
    }

    /**
     * a constructor
     */
    public WGraph_DS() {
        graph = new HashMap<Integer, node_info>();
        inner = new HashMap<Integer, HashMap<Integer, node_info>>();
        edges = new HashMap<Integer, HashMap<Integer, Double>>();
        countedge = 0;
        countmoves = 0;
    }


    /**
     * returns the node_info associates with this node_id
     *
     * @param key - the node_id
     * @return node_info
     */
    @Override
    public node_info getNode(int key) {
        if (graph.containsKey(key))
            return (graph.get(key));
        return null;
    }

    /**
     * this method returns true iff (if and only if) there is an edge between node1 and node2
     *
     * @param node1
     * @param node2
     * @return true or false
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (!(graph.containsKey(node1) && graph.containsKey(node2)))
            return false;
        if (inner.get(node1).containsKey(node2))
            return true;
        return false;
    }

    /**
     * this method returns the weight of the edge between these two nodes.
     *
     * @param node1
     * @param node2
     * @return the value of the edge
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (node1 == node2)
            return 0;
        if (!(hasEdge(node1, node2)))
            return -1;
        return (edges.get(node1).get(node2));
    }

    /**
     * this method add a new node to the graph with the given key.
     *
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (graph.containsKey(key))
            return;
        node_info1 t = new node_info1(key);
        HashMap<Integer, node_info> in = new HashMap<Integer, node_info>();
        HashMap<Integer, Double> edge = new HashMap<Integer, Double>();
        inner.put(key, in);
        edges.put(key, edge);
        graph.put(key, t);
        countmoves++;
    }

    /**
     * this method connect an edge between node1 and node2, with an edge with weight >=0..
     * if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     */

    @Override
    public void connect(int node1, int node2, double w) {
        if (!(graph.containsKey(node1) || graph.containsKey(node2)))
            return;
        if (node1 == node2)
            return;
        if (hasEdge(node1, node2)) {
            edges.get(node1).put(node2, w);
            edges.get(node2).put(node1, w);
            countmoves++;
            return;
        }
        inner.get(node1).put(node2, graph.get(node2));
        inner.get(node2).put(node1, graph.get(node1));
        edges.get(node1).put(node2, w);
        edges.get(node2).put(node1, w);
        countedge++;
        countmoves++;
    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return graph.values();

    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        if (graph.containsKey(node_id))
            return inner.get(node_id).values();
        return null;

    }

    /**
     * this method delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        if (!(graph.containsKey(key)))
            return null;
        node_info[] arr = inner.get(key).values().toArray(new node_info[0]);
        for (int i = 0; i < arr.length; i++) {
            inner.get(arr[i].getKey()).remove(key, graph.get(key));
            edges.get(arr[i].getKey()).remove(key, getEdge(key, arr[i].getKey()));
            countedge--;

        }
        inner.remove(key, getNode(key));
        edges.remove(key, getNode(key));
        countmoves++;
        return (graph.remove(key));
    }

    /**
     * this method deletes the edge from the graph,
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (!hasEdge(node1, node2))
            return;
        edges.get(node1).remove(node2, graph.get(node2));
        inner.get(node1).remove(node2);
        edges.get(node2).remove(node1, graph.get(node1));
        inner.get(node2).remove(node1);

        countedge--;
        countmoves++;
    }

    /**
     * return the number of nodes in the graph.
     *
     * @return the number of nodes in the graph
     */
    @Override
    public int nodeSize() {
        return graph.size();
    }

    /**
     * return the number of edges 'countedge' .
     *
     * @return the number of edges in the graph
     */
    @Override
    public int edgeSize() {
        return countedge;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     *
     * @return countmoves
     */
    @Override
    public int getMC() {
        return countmoves;
    }

    /**
     * implements the interface of node_info
     * this private class represents the node method in wighted graph with all its properties
     * key, tag, info
     */
    private class node_info1 implements node_info,Serializable {
        private int key;
        private String info;
        private double tag;


        /**
         * constructor creats a new node_info1 with  the given key
         *
         * @param key
         */
        public node_info1(int key) {
            tag = 0;
            info = " ";
            this.key = key;
        }

        /**
         * returns the key associates with this node
         *
         * @return int key
         */
        @Override
        public int getKey() {
            return key;
        }

        /**
         * returns the info associates with this node
         *
         * @return String info
         */
        @Override
        public String getInfo() {
            return info;
        }

        /**
         * changes the info associates with this node to the giving new info
         *
         * @param s = the new info
         */
        @Override
        public void setInfo(String s) {
            info = s;
        }

        /**
         * returns the tag associates with this node. represents distance.
         *
         * @return double tag
         */
        @Override
        public double getTag() {
            return tag;
        }

        /**
         * changes the tag associates with this node to the giving new tag
         *
         * @param t = the new tag
         */
        @Override
        public void setTag(double t) {
            tag = t;

        }

    }

    /**
     * this function override the function 'equals' of object.
     * this method compares two weighted_graph and checks if they both contain the same nodes, edges and weights.
     * if this two graph are completely identical the function return true, else, return false.
     * first, I checked if the given object is WGraph_DS type,so I can compare them.
     * after that, I did upcasting to the giving graph, and used iterator to see if each node from
     * the current graph has place in the given graph.
     * then, I ran over the nodes of the current graph, to see if each edge has an identical edge in the other graph.
     * also, I checked the number of nodes  and the number of the edges. I did not include the MC counter,
     * because two graph can be exactly the same but with a different MC.
     * @param obj, the given graph
     * @return true iff these two graph completely identical. else return false.
     */
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof WGraph_DS))
            return false;
        WGraph_DS te = (WGraph_DS) obj;
        if (te.nodeSize() != this.nodeSize())
            return false;
        if (te.edgeSize() != this.edgeSize())
            return false;
        Iterator<node_info> itr3 = te.getV().iterator();
        while (itr3.hasNext()) {
            node_info temp = itr3.next();
            if (this.getNode(temp.getKey()) == null)
                return false;
            Iterator<node_info> itr4 = te.getV(temp.getKey()).iterator();
            while (itr4.hasNext()) {
                node_info temp2 = itr4.next();
                if (this.hasEdge(temp.getKey(), temp2.getKey()) && !(te.hasEdge(temp.getKey(), temp2.getKey())))
                    return false;
                if (!(this.hasEdge(temp.getKey(), temp2.getKey())) && (te.hasEdge(temp.getKey(), temp2.getKey())))
                    return false;
                if (!(this.getEdge(temp2.getKey(), temp.getKey()) == te.getEdge(temp2.getKey(), temp.getKey())))
                    return false;
            }
        }

        return true;


    }
}




