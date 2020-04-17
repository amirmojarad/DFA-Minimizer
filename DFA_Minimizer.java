import java.lang.reflect.Array;
import java.util.*;

public class DFA_Minimizer {
    private Automata dfa;
    private ArrayList<Group> final_group;
    private ArrayList<Group> other_group;
    private ArrayList<State> final_states;
    private ArrayList<State> other_states;
    private ArrayList<Transition> transitions;

    public DFA_Minimizer() {
        this.final_states = new ArrayList<>();
        this.other_states = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.other_group = new ArrayList<>();
        this.final_group = new ArrayList<>();
    }

    public DFA_Minimizer(Automata dfa) {
        this();
        this.dfa = dfa;
    }

    public void minimize() {
        delete_void_nodes();
        partition();
        convertToGroup_final();
        minimize_final();
        change_final_transitions();
        convertToGroup_other();
        minimize_other();
        change_other_transitions();
        sortTransition();
    }

    private void sortTransition() {
        for (State state : dfa.getStates()) {
            for (Transition transition : dfa.getTransitions()) {
                if (state.getName().contains(transition.getSource()))
                    transition.setSource(state.getName());
                if (state.getName().contains(transition.getDestination()))
                    transition.setDestination(state.getName());
            }
        }
        ArrayList<Transition> new_transition = new ArrayList<>();
        for (Transition t: dfa.getTransitions()){
            if (!new_transition.contains(t))
                new_transition.add(t);
        }
        dfa.getTransitions().clear();
        dfa.setTransitions(new_transition);
    }

    //using bfs algorithm
    //has zero path to access to it from initial node(state)
    private void delete_void_nodes() {
        ArrayList<State> founded_states = new ArrayList<>();
        Queue<State> main_nodes = new ArrayDeque<>();
        main_nodes.add(find_state(dfa.getInitialState()));
        while (!main_nodes.isEmpty()) {
            State head = main_nodes.poll();
            for (Transition transition : dfa.getTransitions()) {
                if (transition.getSource().equals(head.getName())) {
                    State state = find_state(transition.getDestination());
                    if (!founded_states.contains(state)) {
                        founded_states.add(state);
                        main_nodes.add(state);
                    }
                }
            }
        }
        dfa.getStates().clear();
        dfa.setStates(founded_states);
    }

    private void convertToGroup_final() {
        for (State state : final_states) {
            Group group = new Group();
            group.setState(state);
            for (Transition transition : dfa.getTransitions()) {
                if (transition.getSource().equals(state.getName()))
                    group.addEntity(transition.getLabel(), find_state(transition.getDestination()));
            }
            this.final_group.add(group);
        }
    }

    private void convertToGroup_other() {
        for (State state : other_states) {
            Group group = new Group();
            group.setState(state);
            for (Transition transition : dfa.getTransitions()) {
                if (transition.getSource().equals(state.getName()))
                    group.addEntity(transition.getLabel(), find_state(transition.getDestination()));
            }
            this.other_group.add(group);
        }
    }


    private void minimize_final() {
        for (int i = 0; i < final_group.size(); i++) {
            Stack<Group> stack = new Stack<>();
            stack.push(final_group.get(i));
            for (int j = i + 1; j < final_group.size(); j++) {
                if (final_group.get(i).are_distinguishable(final_group.get(j)))
                    stack.push(final_group.get(j));
            }
            final_stack(stack);
        }
    }


    private void final_stack(Stack<Group> stack) {
        Group group = new Group();
        State state = new State();
        while (!stack.isEmpty()) {
            Group temp = stack.pop();
            dfa.getStates().remove(new State(temp.getState().getName()));
            state.setName(state.getName() + temp.getState().getName());
            final_group.remove(temp);
            group.setMap(temp.getMap());
        }
        group.setState(state);
        dfa.addToStates(state);
        final_group.add(group);
    }

    private void change_final_transitions() {
        for (Group group : final_group) {
            for (Transition transition : dfa.getTransitions()) {
                if (group.getState().getName().contains(transition.getSource()))
                    transition.setSource(group.getState().getName());
                else if (group.getState().getName().contains(transition.getDestination()))
                    transition.setDestination(group.getState().getName());
            }
        }
    }

    private void change_other_transitions() {
        for (Group group : other_group) {
            for (Transition transition : dfa.getTransitions()) {
                if (group.getState().getName().contains(transition.getSource()))
                    transition.setSource(group.getState().getName());
                else if (group.getState().getName().contains(transition.getDestination()))
                    transition.setDestination(group.getState().getName());
            }
        }
    }


    private void minimize_other() {
        for (int i = 0; i < other_group.size(); i++) {
            Stack<Group> stack = new Stack<>();
            stack.push(other_group.get(i));
            for (int j = i + 1; j < other_group.size(); j++) {
                if (other_group.get(i).are_distinguishable(other_group.get(j)))
                    stack.push(other_group.get(j));
            }
            other_stack(stack);
        }
    }


    private void other_stack(Stack<Group> stack) {
        Group group = new Group();
        State state = new State();
        while (!stack.isEmpty()) {
            Group temp = stack.pop();
            dfa.getStates().remove(new State(temp.getState().getName()));
            state.setName(state.getName() + temp.getState().getName());
            other_group.remove(temp);
            group.setMap(temp.getMap());
        }
        group.setState(state);
        dfa.addToStates(state);
        other_group.add(group);
    }

    //
//    private void convertToAutomata(){
//
//    }
//
    private State find_state(String state_name) {
        for (State state : dfa.getStates())
            if (state.getName().equals(state_name)) return state;
        return null;
    }

    private void fill_groups(ArrayList<String> names, ArrayList<State> states) {
        for (String string : names) {
            State temp = find_state(string);
            if (temp != null) states.add(temp);
        }
    }

    private ArrayList<String> other_states_finder(ArrayList<String> final_states) {
        ArrayList<String> result = new ArrayList<>();
        for (State state : dfa.getStates())
            if (!final_states.contains(state.getName())) result.add(state.getName());
        return result;
    }

    private void partition() {
        //final state group
        fill_groups(dfa.getFinalStates(), final_states);
        //other state group
        fill_groups(other_states_finder(dfa.getFinalStates()), other_states);
    }


    public Automata getDfa() {
        return dfa;
    }

    public void setDfa(Automata dfa) {
        this.dfa = dfa;
    }

    public ArrayList<Group> getFinal_group() {
        return final_group;
    }

    public void setFinal_group(ArrayList<Group> final_group) {
        this.final_group = final_group;
    }

    public ArrayList<Group> getOther_group() {
        return other_group;
    }

    public void setOther_group(ArrayList<Group> other_group) {
        this.other_group = other_group;
    }

    public ArrayList<State> getFinal_states() {
        return final_states;
    }

    public void setFinal_states(ArrayList<State> final_states) {
        this.final_states = final_states;
    }

    public ArrayList<State> getOther_states() {
        return other_states;
    }

    public void setOther_states(ArrayList<State> other_states) {
        this.other_states = other_states;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }
}

