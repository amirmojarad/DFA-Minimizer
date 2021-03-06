import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Graphic extends Application {
    //Attributes
    Manage manage = new Manage();
    Stage stage;
    Scene scene;
    Insets insets = new Insets(10, 10, 10, 10);
    SmartGraphPanel<String, String> graphView;


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("DFA Minimizer");
        stage.setScene(mainScene());
        stage.show();
    }

    /**
     * create a usual Button component in this function with specific name
     *
     * @param name Button's name
     * @return
     */
    Button makeButton(String name) {
        Button button = new Button(name);
        button.setPrefSize(100, 25);
        button.setAlignment(Pos.CENTER);
        return button;
    }

    /**
     * create a usual HBox component in this function
     *
     * @return
     */
    HBox makeHBox() {
        HBox hBox = new HBox();
        hBox.setPadding(insets);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(10);
        return hBox;
    }


    /**
     * create a usual FlowPane component in this function
     *
     * @return
     */

    FlowPane makeFlowPane(Orientation orientation) {
        FlowPane pane = new FlowPane();
        pane.setOrientation(orientation);
        pane.setPadding(insets);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.CENTER);
        return pane;
    }

    /**
     * create a usual Label in this function
     *
     * @param text
     * @return
     */
    Label makeLabel(String text) {
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER_LEFT);
        label.setTextFill(Color.DODGERBLUE);
        label.setFont(Font.font("Verdana"));
        return label;
    }

    /**
     * create a usual TextField in this function
     *
     * @return
     */
    TextField makeTextField() {
        TextField textField = new TextField();
        textField.setPrefSize(150, 25);
        textField.setAlignment(Pos.CENTER);
        return textField;
    }

    /**
     * create usual VBox in this function
     *
     * @return
     */
    VBox makeVBox() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setFillWidth(false);
        vBox.setSpacing(10);
        return vBox;
    }

    /**
     * create usual ComboBox component with specific Type <T>
     *
     * @param <T>
     * @return
     */
    <T> ComboBox<T> makeComboBox() {
        ComboBox<T> comboBox = new ComboBox<>();
        comboBox.setPrefSize(100, 25);
        comboBox.setPadding(insets);
        return comboBox;
    }

    /**
     * open a json file from Directory and pass it to manage object to open and use it
     */
    private void openFunction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("file.json");
        fileChooser.setInitialDirectory(new File("E:\\University Courses\\Term 4\\Formal Languages and Automata\\Projects\\Project01-DFA analyze\\src\\inputFiles"));
        File file = fileChooser.showOpenDialog(stage);
        manage.setFilePath(file.getPath());
        manage.loadAll();
    }

    /**
     * in this function craete a new FA machine (DFA)
     *
     * @return
     */
    Scene addScene() {
        ArrayList<String> statesName = new ArrayList<>();
        ArrayList<String> final_list = new ArrayList<>();

        Automata automata = new Automata();

        /*
         *               ***Upper Section***
         *  contains name, alphabets, initialState, type
         */
        //Name
        Label n_label = makeLabel("Name");
        TextField n_text = makeTextField();
        VBox name_box = makeVBox();
        name_box.getChildren().addAll(n_label, n_text);
        //Alphabets
        Label a_label = makeLabel("Enter your alphabets");
        TextField a_text = makeTextField();
        VBox alphabet_box = makeVBox();
        alphabet_box.getChildren().addAll(a_label, a_text);
        //initialState
        Label initial_label = makeLabel("Select Initial State");
        ComboBox<String> initialCombo = makeComboBox();
        initialCombo.setPrefSize(150, 15);
        initialCombo.getItems().addAll(statesName);
        VBox initial_box = makeVBox();
        initial_box.getChildren().addAll(initial_label, initialCombo);
        //type
//        Label t_label = makeLabel("Type");
//        TextField t_text = makeTextField();
//        t_text.setPromptText("FA");
//        t_text.setPrefSize(50,25);
//        HBox type_box = makeHBox();
//        type_box.getChildren().addAll(t_label, t_text);
        ////Final State
        Label final_state = makeLabel("Final States");
        TextField final_text = makeTextField();
        VBox final_box = makeVBox();
        final_box.getChildren().addAll(final_state, final_text);

        //////////////////////////////////////////////////////////////////////
        ////upper section
        HBox partOne = makeHBox();
        partOne.getChildren().addAll(name_box, alphabet_box);
        HBox partTwo = makeHBox();
        partTwo.getChildren().addAll(initial_box, final_box);
//        partTwo.setSpacing(46);
        VBox upperBox = makeVBox();
        upperBox.setAlignment(Pos.CENTER_LEFT);
        upperBox.getChildren().addAll(partOne, partTwo);
        ///////////////////////////////////////////////////////////////////////
        /*
         *        ***LowerSection***
         * contains Transitions and States
         */
        //////States
        //names
        Label s_label = makeLabel("Enter your States(and at the end press ENTER key)");
        TextField s_text = makeTextField();
        s_text.setPrefSize(450, 25);
        VBox states_box = makeVBox();
        states_box.getChildren().addAll(s_label, s_text);
        ////positions
        //Combo
        Label combo_label = makeLabel("States");
        ComboBox<String> s_combo = makeComboBox();
        VBox combo_box = makeVBox();
        combo_box.getChildren().addAll(combo_label, s_combo);
        //positions
        //X
        Label x_label = makeLabel("positionX");
        TextField x_text = makeTextField();
        VBox x_box = makeVBox();
        x_box.getChildren().addAll(x_label, x_text);
        //Y
        Label y_label = makeLabel("positionX");
        TextField y_text = makeTextField();
        VBox y_box = makeVBox();
        y_box.getChildren().addAll(y_label, y_text);
        //submitButton
        Button submit = makeButton("Submit");
        /////////////////////////////////////////////
        ////Lower state Box
        HBox lower_state_box = makeHBox();
        lower_state_box.getChildren().addAll(combo_box, x_box, y_box, submit);
        /////////////////////////////////////////////////////////////////////
        ///States Label
        Label states = makeLabel("States");
        //TODO set font fot this label
        VBox main_states_box = makeVBox();
        main_states_box.getChildren().addAll(states, states_box, lower_state_box);
        ///////////////////////////////////////////////////////////

        //////////////////////////////////////////////
        ///Transition
        //name
        Label name_label = makeLabel("name");
        TextField transition_text = makeTextField();
        HBox tName_box = makeHBox();
        tName_box.getChildren().addAll(name_label, transition_text);
        //label
        Label l_label = makeLabel("Label");
        TextField l_text = makeTextField();
        HBox label_box = makeHBox();
        label_box.getChildren().addAll(l_label, l_text);
        //Source
        Label source_label = makeLabel("Source");
        TextField source_text = makeTextField();
        HBox source_box = makeHBox();
        source_box.getChildren().addAll(source_label, source_text);
        //destination
        Label destination_label = makeLabel("name");
        TextField destination_text = makeTextField();
        HBox destination_box = makeHBox();
        destination_box.getChildren().addAll(destination_label, destination_text);
        ////////////////////////////////////////////
        ///Transition Label
        Label trans_label = makeLabel("Transition");
        //TODO set font fot this label
        HBox left = makeHBox();
        left.getChildren().addAll(tName_box, label_box);
        HBox right = makeHBox();
        right.getChildren().addAll(source_box, destination_box);
        VBox transition_box = makeVBox();
        transition_box.getChildren().addAll(left, right);
        VBox trans_box = makeVBox();
        trans_box.getChildren().addAll(trans_label, transition_box);
        Button submitTransition = makeButton("Submit");
        HBox transitionBox = makeHBox();
        transitionBox.getChildren().addAll(trans_box, submitTransition);
        ////////////////////////////////////////////////////////////////////////
        //LowerBox
        VBox lowerBox = makeVBox();
        lowerBox.getChildren().addAll(main_states_box, transitionBox);
        //////mainVBox
        Button add = makeButton("Add");
        Button back = makeButton("Back");
        HBox buttons = makeHBox();
        buttons.setSpacing(400);
        buttons.getChildren().addAll(back, add);
        VBox pane = makeVBox();
        pane.getChildren().addAll(upperBox, lowerBox, buttons);
        //////////Actions
        s_text.setOnKeyPressed(event -> {
            statesName.addAll(getList(s_text.getText()));
            s_combo.getItems().addAll(statesName);
        });

        submitTransition.setOnAction(event -> {
            Transition transition = new Transition(
                    transition_text.getText(),
                    source_text.getText(),
                    destination_text.getText(),
                    l_text.getText()
            );
            transition_text.clear();
            source_text.clear();
            destination_text.clear();
            l_text.clear();
            automata.addToTransition(transition);
        });

        submit.setOnAction(event -> {
            State state = new State();
            state.setName(s_combo.getSelectionModel().getSelectedItem());
            state.setPositionX(x_text.getText());
            state.setPositionY(y_text.getText());
            automata.addToStates(state);
            x_text.clear();
            y_text.clear();
            s_combo.getItems().remove(s_combo.getSelectionModel().getSelectedItem());
            if (s_combo.getItems().size() != 0)
                s_combo.setPromptText(s_combo.getItems().get(0));
        });
        s_text.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                statesName.addAll(Arrays.asList(s_text.getText().split(" ")));
                String prompt = s_text.getText();
                s_text.setPromptText(prompt);
                s_text.clear();
                //////////////////////////////
                initialCombo.getItems().addAll(statesName);
                initialCombo.setPromptText(statesName.get(0));
                s_combo.getItems().addAll(statesName);
                s_combo.setPromptText(statesName.get(0));
            }
        });

        add.setOnAction(event -> {
            automata.setName(n_text.getText());
            n_text.clear();
            n_text.setPromptText(automata.getName());
            /////////////////////////////////////////
            automata.setAlphabets(getList(a_text.getText()));
            a_text.setPromptText(a_text.getText());
            a_text.clear();
            //////////////////////////////////////////
            automata.setInitialState(initialCombo.getSelectionModel().getSelectedItem());
            /////////////////////////////////////////
            automata.setFinalStates(getList(final_text.getText()));
            final_text.setPromptText(final_text.getText());
            final_text.clear();
            //////////////////////////////////////////
            if (!manage.addToAutomatas(automata)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("\"" + automata.getName() + "\"" + "already exists!");
                alert.show();
            }

        });

        back.setOnAction(event -> stage.setScene(mainScene()));

        Scene addScene = new Scene(pane, 600, 700);
        return addScene;
    }

    /**
     * showing a dfa (minimized or not)
     *
     * @return
     */
    Scene showScene() {
        ComboBox<String> automatas = makeComboBox();
        automatas.setPrefSize(120, 50);
        automatas.getItems().addAll(this.manage.getAutomataStrings());
        Button submit = makeButton("submit");

        RadioButton radioButton = new RadioButton("Minimize");
        ToggleGroup tg = new ToggleGroup();
        tg.getToggles().add(radioButton);

        HBox upperSection = makeHBox();
        upperSection.getChildren().addAll(automatas, radioButton, submit);

        FlowPane pane = makeFlowPane(Orientation.HORIZONTAL);
        pane.setAlignment(Pos.TOP_LEFT);
        pane.getChildren().addAll(automatas, radioButton, submit);

        submit.setOnAction(event -> {
            Automata automata = findAutomata(automatas.getSelectionModel().getSelectedItem());
            if (tg.getSelectedToggle() != null) {
                if (tg.getSelectedToggle().isSelected()) {
                    DFA_Minimizer minimizer = new DFA_Minimizer(automata);
                    minimizer.minimize();
                    Object[] objects = new GraphMaker().scene(automata);
                    stage.setScene((Scene) objects[1]);
                    SmartGraphPanel<String, String> gv = (SmartGraphPanel<String, String>) objects[0];
                    gv.init();
                }
            } else {
                Object[] objects = new GraphMaker().scene(automata);
                stage.setScene((Scene) objects[1]);
                SmartGraphPanel<String, String> gv = (SmartGraphPanel<String, String>) objects[0];
                gv.init();
            }
        });

        return new Scene(pane, 400, 250);
    }

    /**
     * convert all autmata's attributes to upperCase for better showing in SmartGraph
     *
     * @param automata
     * @return
     */
    private Automata convertToUpperCase(Automata automata) {
        for (State state : automata.getStates())
            state.setName(state.getName().toUpperCase());
        for (Transition transition : automata.getTransitions()) {
            transition.setDestination(transition.getDestination().toUpperCase());
            transition.setSource(transition.getSource().toUpperCase());
            transition.setLabel(transition.getLabel().toUpperCase());
        }
        automata.setInitialState(automata.getInitialState().toUpperCase());
        ArrayList<String> list = new ArrayList<>();
        for (String s : automata.getFinalStates()) {
            list.add(s.toUpperCase());
        }
        automata.setFinalStates(list);
        return automata;
    }

    /**
     * step through among manage.automata and find an automata with specific name
     *
     * @param name
     * @return
     */
    private Automata findAutomata(String name) {
        for (Automata automata : manage.getAutomatas()) {
            if (automata.getName().equals(name)) return convertToUpperCase(automata);
        }
        return null;
    }

    /**
     * take a string and split it by space and return a string array
     *
     * @param text
     * @return
     */

    ArrayList<String> getList(String text) {
        String[] strings = text.split(" ");
        return new ArrayList<>(Arrays.asList(strings));
    }

    /**
     * main scene in this function created
     *
     * @return
     */
    Scene mainScene() {
        Button open = makeButton("Open");
        Button clearData = makeButton("Clear Data");
        Button add = makeButton("Add");
        Button close = makeButton("Close");
        Button show = makeButton("Show");
        show.setDisable(true);
        //Actions
        add.setOnAction(event -> stage.setScene(addScene()));
        close.setOnAction(event -> stage.close());
        clearData.setOnAction(event -> {
            while (manage.isEmpty()) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setContentText("Your selected directory is empty, do yo wanna choose another one?");
                Optional<ButtonType> option = confirm.showAndWait();
                if (option.get().equals(ButtonType.OK)) openFunction();
                else break;
            }
            if (!manage.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                Optional<ButtonType> option = alert.showAndWait();
                alert.setContentText("Are You Sure To Delete All Information?");
                if (option.get().equals(ButtonType.OK)) {
                    manage.clearAllData();
                    Alert successful = new Alert(Alert.AlertType.INFORMATION);
                    successful.setContentText("deleting successfully done!");
                    successful.show();
                }
            }
        });
        open.setOnAction(event -> {
            openFunction();
            if (!manage.isEmpty()) show.setDisable(false);
        });
        show.setOnAction(event -> stage.setScene(showScene()));
        FlowPane pane = makeFlowPane(Orientation.VERTICAL);
        pane.getStyleClass().add("pane");
        pane.getChildren().addAll(show, open, add, clearData, close);
        Scene scene = new Scene(pane, 500, 600);
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
