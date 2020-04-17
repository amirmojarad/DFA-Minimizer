import java.io.IOException;
import java.util.ArrayList;

public class T {

    public static void main(String[] args) {
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
        automata.setName("DFA");
        Manage manage = new Manage();
        manage.addToAutomatas(automata);
        manage.setFilePath("E:\\University Courses\\Term 4\\Formal Languages and Automata\\Projects\\Project01-DFA analyze\\src\\inputFiles\\temp.json");
        try {
            manage.saveAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
