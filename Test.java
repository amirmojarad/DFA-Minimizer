import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Manage manage = new Manage();
        manage.setFilePath("E:\\University Courses\\Term 4\\Formal Languages and Automata\\Projects\\Project01-DFA analyze\\src\\inputFiles\\file.json");
        manage.loadAll();
        Graph graph = new Graph(manage.getAutomata("automata1"));
        Pane pane = new Pane(graph.setConnection());
        Scene scene = new Scene(pane, 800,700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}