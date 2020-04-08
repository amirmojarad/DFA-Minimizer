import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class Graph extends Application {
    private Automata automata;
    private ArrayList<Node> nodes;
    private ArrayList<Transition> transitions;

    public Graph() {
        this.automata = new Automata();
        this.nodes = new ArrayList<>();
        this.transitions = new ArrayList<>();
    }

    public Graph(Automata automata) {
        this();
        this.automata = automata;
    }

    private Node findNode(Node node) {
        for (int i = 0; i < nodes.size(); i++)
            if (nodes.get(i).equals(node)) return nodes.get(i);
        return null;
    }

    private Pane draw(Node source, Node destination, Edge edge) {
        Pane pane = new Pane();
        if (source.getLabelText().equals(destination.getLabelText())) edge.setCurve();
        else
            edge.setLine(source.getPositionX(), source.getPositionY(),
                    destination.getPositionX(), destination.getPositionY());
        pane.getChildren().addAll(edge.draw(), source.draw(), destination.draw());
        return pane;
    }

    private Pane setConnection() {
        setNodes();
        Pane pane = new Pane();
        for (Transition transition : transitions) {
            Node destination = findNode(new Node(transition.getDestination()));
            Node source = findNode(new Node(transition.getSource()));
            Edge edge = new Edge(transition.getLabel());
            if (!Objects.requireNonNull(destination).isExists()
                    && !Objects.requireNonNull(destination).isExists()) {
                pane.getChildren().addAll(draw(source, destination, edge));
            }
        }
        return pane;
    }

    private void setTransitions() {
        this.transitions.addAll(automata.getTransitions());
    }

    private void setStates() {
        for (State state : automata.getStates()) {
            Node node = new Node(state.getName(),
                    Integer.parseInt(state.getPositionX()),
                    Integer.parseInt(state.getPositionY()));
            nodes.add(node);
        }
    }


    private void setNodes() {
        setStates();
        setTransitions();
    }

//    public Pane showGraphs() {
//        Pane pane = new Pane();
//        for (Transition transition : transitions) pane.getChildren().add()
//    }
//
//    public Pane show() {
//        Pane pane = new Pane();
//
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Automata automata = new Automata();
        automata.addToStates(new State("q1", "20", "20"));
        automata.addToStates(new State("q1", "20", "20"));
        automata.addToTransition(new Transition("t1", "q1", "q1", "a"));
        Graph graph = new Graph(automata);
        Pane pane = new Pane(graph.setConnection());
        Scene scene = new Scene(pane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
