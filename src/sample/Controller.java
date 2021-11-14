package sample;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class Controller{

    private Pane root;
    private ArrayList<Circle> circles;
    private int cnt = 0;
    private Circle tempCircle;
    private Graph graph;
    private ArrayList<Line> lines;

    public Controller(){
        makePane();
        handleScene();
        circles = new ArrayList<>();
        graph = new Graph();
        lines = new ArrayList<>();
    }

    public void makePane(){
        root = new Pane();

        Text text = new Text("please click on screen to make nodes then right-click on your nodes to connect to another nodes");
        text.setStroke(Color.rgb(20,145,40));
        text.setFont(new Font(18));
        text.setStrokeWidth(1);
        text.setTranslateX(260);

        Button button = new Button("Prim");
        button.setId("button");

        Button button1 = new Button("Kruskal");
        button1.setId("button");

        Button button2 = new Button("Fully connected");
        button2.setId("button");

        Button button3 = new Button("Retry");
        button3.setId("button");

        button.setOnAction(e -> {
            MST mst = new MST(graph);
            for (Line line:lines) {
                line.setStrokeWidth(2);
                line.setStroke(Color.rgb(50,50,120));
            }
            mst.runPrim();
            ArrayList<Integer> index = mst.mstPrimEdges();
            for (int i:index) {
                lines.get(i).setStroke(Color.rgb(29,191,32));
                lines.get(i).setStrokeWidth(4);
            }
            System.out.println("weight with prim algorithm = "+mst.getMSTWeight());

        });

        button1.setOnAction(e -> {
            for (Line line:lines) {
                line.setStrokeWidth(2);
                line.setStroke(Color.rgb(50,50,120));
            }
            MST mst = new MST(graph);
            ArrayList<Integer> index = mst.runKruskal();
            for (int i:index) {
                lines.get(i).setStrokeWidth(4);
                lines.get(i).setStroke(Color.rgb(29,191,32));
            }
            System.out.println("weight with kruskal algorithm = "+mst.getMSTWeight());
        });

        button2.setOnAction(e -> {
            for (Node node1:graph.getNodes()) {
                for (Node node2:graph.getNodes()) {
                    if (!graph.isConnected(node1,node2)){
                        graph.connectNodes(node1,node2,dist(circles.get(graph.getNodes().indexOf(node1)),circles.get(graph.getNodes().indexOf(node2))));
                        connect(circles.get(graph.getNodes().indexOf(node1)),circles.get(graph.getNodes().indexOf(node2)));
                    }
                }
            }
        });

        button3.setOnAction(e -> {
            for (Circle c:circles) {
                root.getChildren().remove(c);
            }
            for (Line l:lines) {
                root.getChildren().remove(l);
            }

            circles = new ArrayList<>();
            graph = new Graph();
            lines = new ArrayList<>();
        });

        VBox vb = new VBox(text,button,button1,button2,button3);
        vb.setTranslateX(10);
        vb.setTranslateY(10);
        vb.setSpacing(8);
        root.getChildren().add(vb);

        root.setId("pane");
    }

    public Pane getRoot() {
        return root;
    }

    private void handleScene(){

        root.setOnMouseClicked(mouseEvent -> {

            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (cnt == 0) {
                    graph.addNode(new Node());
                    makeCircle(mouseEvent.getSceneX(), mouseEvent.getSceneY());
                }
                else {
                    for (Circle c:circles) {
                        if (mouseEvent.getTarget().equals(c)){
                            if (c != tempCircle) {
                                Node node1 = graph.getNodes().get(circles.indexOf(c));
                                Node node2 = graph.getNodes().get(circles.indexOf(tempCircle));
                                if (!graph.isConnected(node1,node2)) {
                                    graph.connectNodes(node1, node2, dist(c, tempCircle));
                                    connect(c, tempCircle);
                                }
                                cnt = 0;
                                for (Circle circle : circles) {
                                    circle.setFill(Color.rgb(255,87,53));
                                }
                            }
                        }
                    }
                }
            } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)){
                for (Circle c:circles) {
                    if (mouseEvent.getTarget().equals(c)){
                        cnt = 1;
                        tempCircle = c;
                        for (Circle cr:circles) {
                            cr.setFill(Color.RED);
                            if (cr != c){
                                Node node1 = graph.getNodes().get(circles.indexOf(cr));
                                Node node2 = graph.getNodes().get(circles.indexOf(c));
                                if (!graph.isConnected(node1,node2)) {
                                    cr.setFill(Color.GREEN);
                                }
                            }
                        }
                    }
                }
            }
        });

    }

    private void makeCircle(double x,double y){
        Circle c = new Circle(x,y,12, Color.rgb(255,87,53));
        c.setStrokeWidth(2);
        c.setStroke(Color.rgb(0,91,80));
        root.getChildren().add(c);
        circles.add(c);
    }

    private void connect(Circle c1,Circle c2){
        Line line = new Line(c1.getCenterX(),c1.getCenterY(),c2.getCenterX(),c2.getCenterY());
        lines.add(line);
        line.setStrokeWidth(1);
        line.setStroke(Color.rgb(50,50,120));
        root.getChildren().add(line);
    }

    private double dist(Circle c1,Circle c2){
        double startX = c1.getCenterX();
        double startY = c1.getCenterY();

        double endX = c2.getCenterX();
        double endY = c2.getCenterY();

        return Math.sqrt((endX - startX)*(endX - startX) + (endY - startY)*(endY - startY));
    }

}
