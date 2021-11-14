package sample;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private List<Node> neighbours;


    public Node(){
        neighbours = new ArrayList<>();
    }

    public void addNeighbor(Node neighbour){
        neighbours.add(neighbour);
    }

    public List<Node> getNeighbours() {
        return neighbours;
    }

    public int getDegree(){
        return neighbours.size();
    }


}
