package sample;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    public Graph(){
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public Graph(ArrayList<Node> nodes,ArrayList<Edge> edges){
        this.nodes = nodes;
        this.edges = edges;
    }


    public boolean isConnected(Node node1,Node node2){
        for (Edge edge:edges) {
            if ( (edge.getNode1().equals(node1) && edge.getNode2().equals(node2) ) || ( (edge.getNode1().equals(node2) && edge.getNode2().equals(node1) ))){
                return true;
            }
        }
        return false;
    }

    public void connectNodes(Node node1,Node node2,double weight){
        Edge e = new Edge(node1,node2,weight);
        edges.add(e);
        node1.addNeighbor(node2);
        node2.addNeighbor(node1);
    }

    public void addNode(Node node){
        nodes.add(node);
    }


    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public double weight(Node node1,Node node2){
        for (Edge edge:edges) {
            if ( (edge.getNode1().equals(node1) && edge.getNode2().equals(node2) ) || ( (edge.getNode1().equals(node2) && edge.getNode2().equals(node1) ))){
                return edge.getWeight();
            }
        }
        return 0;
    }


    public int getEdgeIndex(Node node1,Node node2){
        for (Edge edge:edges) {
            if ( (edge.getNode1().equals(node1) && edge.getNode2().equals(node2) ) || ( (edge.getNode1().equals(node2) && edge.getNode2().equals(node1) ))){
                return edges.indexOf(edge);
            }
        }
        return -1;
    }
}
