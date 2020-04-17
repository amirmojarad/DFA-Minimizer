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
    Scene scene(Automata automata) {
        graph = build_sample_digraph(automata);
        strategy = new SmartCircularSortedPlacementStrategy();
        graphView = new SmartGraphPanel<>(graph, strategy);
        container = new SmartGraphDemoContainer(graphView);
        Scene scene = new Scene(container, 1200,700);
        return scene;
    }


    private Graph<String, String> build_sample_digraph(Automata automata) {
        Digraph<String, String> g = new DigraphEdgeList<>();
//        Automata automata = makeAutoamta();
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

}
