import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

/**
 * in this class manage and showing graph components
 *
 * @author amirMojarad
 * @version 1.1
 */

public class Graph {
    //saves automatas in this list
    private Automata automata;
    //saves nodes in this list
    private ArrayList<Node> nodes;
    //save transitions
    private ArrayList<Transition> transitions;
    //save states
    private ArrayList<MyGroup> graph;

    public Graph() {
        this.automata = new Automata();
        this.nodes = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.graph = new ArrayList<>();
    }

    public Graph(Automata automata) {
        this();
        this.automata = automata;
        //copy all transition that is in automata to transition's list
        this.transitions.addAll(automata.getTransitions());
        //copy all states that is in automata to state's list
        setStates();
    }

    public Automata getAutomata() {
        return automata;
    }

    public void setAutomata(Automata automata) {
        this.automata = automata;
    }

    /**
     * find node among other nodes in nodes list
     *
     * @param node must find this node into nodes'list
     * @return if find it return node that stored in list else return null
     */
    private Node findNode(Node node) {
        for (int i = 0; i < nodes.size(); i++)
            if (nodes.get(i).equals(node)) return nodes.get(i);
        return null;
    }

    /**
     * draw a group
     *
     * @param source      that node consider it by source of a group
     * @param destination that node consider it by destination of a group
     * @param edge        the edge between source and destination
     * @return
     */
    private Pane draw(Node source, Node destination, Edge edge) {
        Pane pane = new Pane();
        if (source.getLabelText().equals(destination.getLabelText())) {
            edge.setCurve(source.getPositionX(),
                    source.getPositionY(),
                    destination.getPositionX(),
                    destination.getPositionY()
                    , true);
            pane.getChildren().addAll(edge.draw(true), source.draw(), destination.draw());
        } else {
            edge.setCurve(source.getPositionX(),
                    source.getPositionY(),
                    destination.getPositionX(),
                    destination.getPositionY(),
                    false);
            pane.getChildren().addAll(edge.draw(false), source.draw(), destination.draw());
        }
        return pane;
    }

    /**
     * find group among graph list
     *
     * @param temp find that node is in graph list
     * @return edge of founded group
     */
    private Edge getEdge(MyGroup temp) {
        for (MyGroup group : graph)
            if (group.equals(temp)) return group.getEdge();
        return null;
    }

    /**
     * draw graph into scene
     *
     * @return pane of graph
     */
    public Pane setConnection() {
        Pane pane = new Pane();
        if (transitions.isEmpty()) transitions.addAll(automata.getTransitions());
        if (nodes.isEmpty()) setStates();
        for (Transition transition : transitions) {
            Node destination = findNode(new Node(transition.getDestination()));
            Node source = findNode(new Node(transition.getSource()));
            Edge edge = new Edge(transition.getLabel());
            assert source != null;
            assert destination != null;
            destination.setFinalState(findFinalState(destination.getLabelText()));
            source.setFinalState(findFinalState(source.getLabelText()));
            destination.setInitializeState(isInitialState(destination.getLabelText()));
            source.setInitializeState(isInitialState(source.getLabelText()));
            MyGroup group = new MyGroup(source, destination, edge);
            if (graph.contains(group)) {
                Objects.requireNonNull(getEdge(group)).setSecond_label(transition.getLabel());
            } else if (destination.isExists()) {
                pane.getChildren().add(draw(source, destination, edge));
            } else if (source.isExists()) {
                pane.getChildren().add(draw(source, destination, edge));
            } else {
                pane.getChildren().addAll(draw(source, destination, edge));
            }
            graph.add(group);
        }
        return pane;
    }

    boolean findFinalState(String nodeText) {
        for (String string : automata.getFinalStates())
            if (string.equals(nodeText)) return true;
        return false;
    }

    boolean isInitialState(String nodeText){
        if (nodeText.equals(automata.getInitialState())) return true;
        return false;
    }

    private void setStates() {
        for (State state : automata.getStates()) {
            Node node = new Node(state.getName(),
                    Integer.parseInt(state.getPositionX()),
                    Integer.parseInt(state.getPositionY()));
            nodes.add(node);
        }
    }
}
