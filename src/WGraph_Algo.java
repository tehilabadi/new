package ex1;

import ex0.node_data;

import java.io.*;
import java.util.*;
/**
 * This class implements the interface of weighted_graph_algorithms represents an
 * Undirected (positive) Weighted Graph Theory algorithms including:
 * 0. deep copy
 * 1. init(graph);
 * 2. isConnected();
 * 3. double shortestPathDist(int src, int dest);
 * 4. List<node_data> shortestPath(int src, int dest);
 * 5. Save(file);
 * 6. Load(file);
 */

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph graph;

    /**
     * a constructor for WGraph_Algo
     */
    public WGraph_Algo() {

        graph = new WGraph_DS();
    }

    /**
     * this method initializes the graph.
     */
    @Override
    public void init(weighted_graph g) {
        this.graph = g;
    }

    /**
     * this method returns the graph
     * @return graph
     */
    @Override
    public weighted_graph getGraph() {
        return this.graph;
    }

    /**
     * this method is a deep copy method to the graph.
     * This function creates a new graph that is identical to the current graph,
     * with its nodes, edges, and weights.
     * for this function I created a new WGraph_DS.
     * I put all the nodes in the current graph into an array and for each one I created a new
     * node in the new graph, that's contains the exact same key, tag and info like the node
     * in the array.
     * Then, I went back to the first node in the array, and checked for its neighbors and edges with
     * their weights, and then I connected the parallel node in the new graph with the same neighbors and
     * created new identical edges. This way I ran over all the nodes in the original graph and
     * created the exact same graph that's stands for its own.

     * @return
     */
    @Override
    public weighted_graph copy() {
        weighted_graph h = new WGraph_DS();
        if (graph.nodeSize() == 0)
            return h;
        node_info[] arr = graph.getV().toArray(new node_info[graph.nodeSize()]);
        for (int i = 0; i < arr.length; i++) {
            h.addNode(arr[i].getKey());
            h.getNode(arr[i].getKey()).setInfo(arr[i].getInfo());
            h.getNode(arr[i].getKey()).setTag(arr[i].getTag());
        }
        for (int i = 0; i < arr.length; i++) {
            node_info[] arr1 = graph.getV(arr[i].getKey()).toArray(new node_info[0]);
            for (int j = 0; j < arr1.length; j++) {
                h.connect(arr[i].getKey(), arr1[j].getKey(), graph.getEdge(arr[i].getKey(), arr1[j].getKey()));

            }
        }
        return h;
    }

    /**
     * This method checks if a WGraph_DS is connected - from one node you can get
     * to any other node in the graph.
     * I used 'shortestPathDist' function to rather or not this graph is connected
     * if this graph is not connected the method 'shortestPathDist' will change the tag of the 'untouched'
     * nodes to Integer.MAX_VALUE and by running over the graph's nodes, we can see
     * if there is any note with the tag Integer.MAX_VALUE, meaning the graph is not connected
     * @return
     */
    @Override
    public boolean isConnected() {
        if (graph == null)
            return true;
        if (graph.nodeSize() == 0 || graph.nodeSize() == 1)
            return true;
        node_info[] arr = graph.getV().toArray(new node_info[0]);
        double t = shortestPathDist(arr[0].getKey(), arr[1].getKey());
        if(t == -1)
            return false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].getTag() == Integer.MAX_VALUE)
                return false;
        }
        return true;
    }

    /**
     * This method checks what is the shortest path from two given nodes in the graph.
     * in this method I used Dijkstra's Algorithm.
     * first, I initialized the tag of every node in the graph to 'Integer.MAX_VALUE',
     * meaning that their distance from the src node is infinity until the algorithm changes it, if so.
     * I created a PriorityQueue based on the smallest tag in the queue.
     * first, I added the src node to the queue, and changed its tag to be 0, the distance from the node to itself.
     * then, while the queue is not empty, I created an Iterator for the neighbors of the first
     * node in the queue.
     * I 'asked' each neighbor if his tag is smaller then the the tag of the current node+the weight
     * of the edge between them. if so, I changed its weight to the smaller one.
     * then, I added it to the queue.
     * if not, I moved on to the next neighbors. when all the neighbors got checked, i took the node out of the
     * queue and moved on to the next first node-the node with the smallest tag in the queue.
     * the algorithm runs until the queue is empty - meaning that I set the shortest path from each node in
     * the graph to the src node.
     *
     * @param src - start node
     * @param dest - end (target) node
     * @return the tag of thee dest node-the shortest path from the src node to the dest node
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (graph.getNode(src) == null || graph.getNode(dest) == null)
            return -1;
        if (graph.nodeSize() == 0)
            return -1;
        if (src == dest)
            return 0;
        node_info[] arr = graph.getV().toArray(new node_info[0]);
        for (int i = 0; i < arr.length; i++) {
            arr[i].setTag(Integer.MAX_VALUE);
        }
        PriorityQueue<node_info> v = new PriorityQueue<node_info>(new WGraph_DS());
        graph.getNode(src).setTag(0);
        v.add(graph.getNode(src));
        while (!v.isEmpty()) {
            Iterator<node_info> itr3 = graph.getV(v.peek().getKey()).iterator();
            while (itr3.hasNext()) {
                node_info temp = itr3.next();
                if (temp.getTag() > (v.peek().getTag() + graph.getEdge(v.peek().getKey(), temp.getKey()))) {
                    v.add(temp);
                    temp.setTag(v.peek().getTag() + graph.getEdge(v.peek().getKey(), temp.getKey()));
                }
            }
            v.poll();
        }
        if (graph.getNode(dest).getTag() == Integer.MAX_VALUE)
            return -1;
        return graph.getNode(dest).getTag();
    }

    /**
     * Thit method returns a list of all the nodes in the shortest path between the src node and thee dest node.
     *first, the function uses 'shortestPathDist' function to define the shortest
     *  path from every node in the graph to the src node.
     *  I created a LinkedList to contains the nodes in the road.
     *  first, I added the dest node to the list, and then checked his neighbors with an iterator
     *  to see which one of them gave the current node his tag and add it to the list.
     *  after finding this node, I'll change the iterator to be the iterator of the new current node
     *  neighbors.
     *  when we find the node with the tag '0' the lop stops, and returns the list.
     * @param src - start node
     * @param dest - end (target) node
     * @ thelist of nodes in the path between the given two nodes
     */

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        LinkedList<node_info> end = new LinkedList<node_info>();
        double counter = shortestPathDist(src, dest);
        if (counter == -1)
            return end;
        if (graph.nodeSize() == 0)
            return end;
        if (src == dest) {
            end.addFirst(graph.getNode(src));
            return end;
        }
        node_info temp = graph.getNode(dest);
        end.add(temp);
        while (temp.getTag() != 0) {
            Iterator<node_info> itr3 = graph.getV(temp.getKey()).iterator();
            while (itr3.hasNext()) {
                node_info newtemp = itr3.next();
                if (temp.getTag() == (newtemp.getTag() + graph.getEdge(newtemp.getKey(), temp.getKey()))) {
                    end.addFirst(newtemp);
                    temp = newtemp;
                }

            }
        }
        return end;
    }
    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream re = new FileOutputStream(file);
            ObjectOutputStream qw = new ObjectOutputStream(re);
            qw.writeObject(graph);

            re.close();
            qw.close();
        }
        catch(Exception e){
            return false;
        } return true;
    }
    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream pw = new FileInputStream(file);
            ObjectInputStream re = new ObjectInputStream(pw);
            graph = (WGraph_DS) re.readObject();
            re.close();
            pw.close();

        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
        return true;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_Algo that = (WGraph_Algo) o;
        return graph.equals(that.graph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph);
    }
}
