import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.scene.Scene;

public class GraphMaker {
    Graph<String, String> graph;
    SmartPlacementStrategy strategy;
    SmartGraphPanel<String, String> graphView;
    SmartGraphDemoContainer container;

    Object[] scene(Automata automata) {
        Object objects[] = new Object[2];
        graph = build_sample_digraph(automata);
        strategy = new SmartCircularSortedPlacementStrategy();
        graphView = new SmartGraphPanel<>(graph, strategy);
        container = new SmartGraphDemoContainer(graphView);
        for(String s: automata.getFinalStates()){
            graphView.getStylableVertex(s).setStyle("-fx-fill: gold; -fx-stroke: brown;");
        }
        graphView.getStylableVertex(automata.getInitialState()).setStyle("-fx-fill: green; -fx-stroke: blue;");
        Scene scene = new Scene(container, 1200, 700);
        objects[0] = graphView;
        objects[1] = scene;
        return objects;
    }


    private Graph<String, String> build_sample_digraph(Automata automata) {
        Digraph<String, String> g = new DigraphEdgeList<>();
        for (State vertex : automata.getStates()) {
            g.insertVertex(vertex.getName());
        }
        for (Transition transition : automata.getTransitions()) {
            g.insertEdge(transition.getSource(), transition.getDestination(), makeEdge(transition));
        }
        return g;
    }


    static int counter = 0;

    String makeEdge(Transition transition) {
        StringBuilder s = new StringBuilder(transition.getLabel());
        for (int i = 0; i < counter; i++)
            s.append(" ");
        counter++;
        return s.toString();
//        return transition.getSource() + "-" + transition.getDestination() + " " + transition.getLabel();
    }

}
