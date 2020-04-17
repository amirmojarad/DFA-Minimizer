import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Scanner;

public class Test extends Application {


    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Graph<String, String> graph = build_sample_digraph();

        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        //SmartPlacementStrategy strategy = new SmartRandomPlacementStrategy();
        SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(graph, strategy);
//        Scene scene = new Scene(new SmartGraphDemoContainer(graphView), 1024, 768);

        Button button = new Button("Butt");
        button.setPrefSize(200,50);
//        button.setLayoutX(100);
//        button.setLayoutY(10);
        graphView.getChildren().add(button);

        Scene scene = new Scene(new SmartGraphDemoContainer(graphView), 1024, 768);

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("JavaFX SmartGraph Visualization");
        stage.setMinHeight(500);
        stage.setMinWidth(800);
        stage.setScene(scene);
        stage.show();

        graphView.init();

    }

    private Graph<String, String> build_sample_digraph() {
        Digraph<String, String> g = new DigraphEdgeList<>();
        Automata automata = makeAutoamta();
        for (State vertex : automata.getStates()) {
            g.insertVertex(vertex.getName());
        }
        for (Transition transition : automata.getTransitions()) {
            g.insertEdge(transition.getSource(), transition.getDestination(), makeEdge(transition));
        }
        return g;
    }


    String makeEdge(Transition transition){
        return transition.getSource()+"-"+transition.getDestination()+" "+transition.getLabel();
    }

    Automata convertToUpperCase(Automata automata) {
        for (State state : automata.getStates())
            state.setName(state.getName().toUpperCase());
        for (Transition transition : automata.getTransitions()) {
            transition.setDestination(transition.getDestination().toUpperCase());
            transition.setSource(transition.getSource().toUpperCase());
            transition.setLabel(transition.getLabel().toUpperCase());
        }
        return automata;
    }


    Automata makeAutoamta() {
        Automata automata = new Automata();
        ArrayList<State> states = new ArrayList<>();
        states.add(new State("q0", "0", "0"));
        states.add(new State("q1", "0", "0"));
        states.add(new State("q2", "0", "0"));
        states.add(new State("q3", "0", "0"));
        states.add(new State("q4", "0", "0"));
        states.add(new State("q5", "0", "0"));
        automata.setStates(states);
        ArrayList<Transition> transitions = new ArrayList<>();

        transitions.add(new Transition("t", "q0", "q3", "0"));
        transitions.add(new Transition("t", "q0", "q1", "1"));
        transitions.add(new Transition("t", "q3", "q0", "0"));
        transitions.add(new Transition("t", "q3", "q4", "1"));
        transitions.add(new Transition("t", "q1", "q2", "0"));
        transitions.add(new Transition("t", "q1", "q5", "1"));
        transitions.add(new Transition("t", "q4", "q2", "0"));
        transitions.add(new Transition("t", "q4", "q5", "1"));
        transitions.add(new Transition("t", "q2", "q2", "0"));
        transitions.add(new Transition("t", "q2", "q5", "1"));
        transitions.add(new Transition("t", "q5", "q5", "0"));
        transitions.add(new Transition("t", "q5", "q5", "1"));
        automata.setTransitions(transitions);
        automata.setInitialState("q0");
        ArrayList<String> finalStates = new ArrayList<>();
        finalStates.add("q4");
        finalStates.add("q2");
        finalStates.add("q1");
        automata.setFinalStates(finalStates);
        ArrayList<String> alphabets = new ArrayList<>();
        alphabets.add("0");
        alphabets.add("1");
        automata.setAlphabets(alphabets);
        DFA_Minimizer minimizer = new DFA_Minimizer(automata);
        minimizer.minimize();
        return convertToUpperCase(minimizer.getDfa());
    }

}