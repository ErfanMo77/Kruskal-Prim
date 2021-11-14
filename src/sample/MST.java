package sample;

import java.util.ArrayList;
import java.util.List;

public class MST {

    private Graph graph;
    private int size;
    private Node[] parents;
    private List<Graph> subSets;
    private double MSTWeight;

    public MST(Graph graph){
        this.graph = graph;
        size = graph.getNodes().size();
        MSTWeight = 0;
    }


    private Node extractMin(double []key,boolean []mstSet){
        double min = 100000;
        Node minNode = new Node();
        for (int i = 0; i < size; i++) {
            if (!mstSet[i] && key[i] < min){
                min = key[i];
                minNode = graph.getNodes().get(i);
            }
        }
        return minNode;

    }

    public void runPrim(){

        parents = new Node[size];
        double []key = new double[size];
        boolean []mstSet = new boolean[size];


        for (int i = 0; i <size ; i++) {
            key[i] = Double.MAX_VALUE;
            mstSet[i] = false;
        }

        key[0] = 0;

        for (int i = 0; i < size; i++) {
            Node u = extractMin(key,mstSet);
            mstSet[graph.getNodes().indexOf(u)] = true;
            for (int j = 0; j < size; j++) {
                if (graph.isConnected(u,graph.getNodes().get(j)) && !mstSet[j] && graph.weight(u,graph.getNodes().get(j)) < key[j]){
                    parents[j] = u;
                    key[j] = graph.weight(u,graph.getNodes().get(j));
                }
            }

        }

    }

    public ArrayList<Integer> mstPrimEdges(){
        ArrayList<Integer> edgesIndex = new ArrayList<>();
        for (int i = 1; i < size; i++){
            edgesIndex.add(graph.getEdgeIndex(parents[i],graph.getNodes().get(i)));
            MSTWeight += graph.weight(parents[i],graph.getNodes().get(i));
        }
        return edgesIndex;

    }


    public ArrayList<Integer> runKruskal(){

        ArrayList<Node> vertices = graph.getNodes();
        ArrayList<Edge> edges = new ArrayList<>();
        subSets = new ArrayList<>();
        ArrayList<Integer> edgesIndex = new ArrayList<>();

        for (Node v: vertices){
            ArrayList<Node> NodeList = new ArrayList<>();
            ArrayList<Edge> edgeList = new ArrayList<>();
            NodeList.add(v);
            subSets.add(new Graph(NodeList,edgeList));
        }

        int j = 0;

        ArrayList<Edge> edgeArrayList = new ArrayList<>();
        for (Edge e:graph.getEdges()) {
            edgeArrayList.add(e);
        }
        graph.getEdges().sort((Edge e1,Edge e2) -> (int)(e1.getWeight() - e2.getWeight()));

        while (edges.size() < vertices.size()-1){
            Edge e = graph.getEdges().get(j);
            int i = graph.getNodes().indexOf(e.getNode1());
            int k = graph.getNodes().indexOf(e.getNode2());

            Graph g1 = find(i);
            Graph g2 = find(k);

            if (!isEqual(g1,g2)){
                edges.add(e);
                merge(g1,g2);
            }
            j++;
        }
        for (Edge e:edges) {
            edgesIndex.add(edgeArrayList.indexOf(e));
            MSTWeight += e.getWeight();
        }
        return edgesIndex;
    }



    private Graph find(int i){

        for (Graph g:subSets) {
            for (Node v: g.getNodes()) {
                if (graph.getNodes().get(i) == v){
                    return g;
                }
            }
        }

        return null;
    }

    private boolean isEqual(Graph g1,Graph g2){
        return (g1 == g2);
    }

    private void merge(Graph g1,Graph g2){
        g1.getNodes().addAll(g2.getNodes());
        subSets.remove(g2);
    }


    public double getMSTWeight() {
        return MSTWeight;
    }
}
