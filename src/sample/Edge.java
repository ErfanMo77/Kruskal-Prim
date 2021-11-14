package sample;

public class Edge {

    private Node node1,node2;
    private double weight;

    public Edge (Node node1,Node node2,double weight){
        this.node1 = node1;
        this.node2 = node2;
        setWeight(weight);
    }


    public void setWeight(double weight) {
        if (weight >= 0) {
            this.weight = weight;
        }else {
            throw new IllegalArgumentException("weight can not be negative");
        }
    }


    public double getWeight() {
        return weight;
    }

    public Node getNode1() {
        return node1;
    }

    public Node getNode2() {
        return node2;
    }
}
