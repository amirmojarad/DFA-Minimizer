import org.json.simple.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Automata {
    ////////////ATTRIBUTES//////////////
    private static String type = "FA";
    private ArrayList<String> alphabets;
    private ArrayList<State> states;
    private ArrayList<Transition> transitions;
    private ArrayList<String> finalStates;
    private String initialState;
    private String name;
    private JSONObject jsonObject;

    //////////Constructors//////////////
    public Automata() {
        this.alphabets = new ArrayList<>();
        this.states = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.finalStates = new ArrayList<>();
        this.jsonObject = new JSONObject();
    }

    //////////Setter and Getter//////////////

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        Automata.type = type;
    }

    public ArrayList<String> getAlphabets() {
        return alphabets;
    }

    public void setAlphabets(ArrayList<String> alphabets) {
        this.alphabets = alphabets;
    }

    public ArrayList<State> getStates() {
        return states;
    }

    public void setStates(ArrayList<State> states) {
        this.states = states;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }

    public ArrayList<String> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(ArrayList<String> finalStates) {
        this.finalStates = finalStates;
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    /////////////////////////////Methods/////////////////////////////////
    JSONObject saveAlphabets() {
        JSONArray result = new JSONArray();
        for (String letter : alphabets) {
            result.add(letter);
        }
        JSONObject object = new JSONObject();
        object.put("alphabet", result);
        return object;
    }

    JSONObject saveFinalStates() {
        JSONArray result = new JSONArray();
        for (String state : finalStates) {
            result.add(state);
        }
        JSONObject object = new JSONObject();
        object.put("finalStates", result);
        return object;
    }


    JSONObject saveStates() {
        JSONArray result = new JSONArray();
        for (State state : states) {
            JSONArray stateArray = new JSONArray();
            JSONObject name = new JSONObject();
            name.put("name", state.getName());
            JSONObject px = new JSONObject();
            px.put("positionX", state.getPositionX());
            JSONObject py = new JSONObject();
            py.put("name", state.getPositionY());
            stateArray.add(name);
            stateArray.add(px);
            stateArray.add(py);
            result.add(stateArray);
        }
        JSONObject object = new JSONObject();
        object.put("state", result);
        return object;
    }

    JSONObject saveTransitions() {
        JSONArray result = new JSONArray();
        for (Transition transition : transitions) {
            JSONArray transArray = new JSONArray();
            JSONObject name = new JSONObject();
            name.put("name", transition.getName());
            JSONObject source = new JSONObject();
            source.put("source", transition.getSource());
            JSONObject destination = new JSONObject();
            destination.put("destination", transition.getDestination());
            JSONObject label = new JSONObject();
            label.put("label", transition.getLabel());
            transArray.add(name);
            transArray.add(destination);
            transArray.add(source);
            transArray.add(label);
            result.add(transArray);
        }
        JSONObject object = new JSONObject();
        object.put("transition", result);
        return object;
    }


    JSONObject save() {
        JSONArray array = new JSONArray();
        JSONObject typeObject = new JSONObject();
        typeObject.put("type", type);
        array.add(typeObject);
        array.add(saveAlphabets());
        array.add(saveStates());
        array.add(saveTransitions());
        array.add(saveFinalStates());
        JSONObject initialStateObject = new JSONObject();
        initialStateObject.put("initialState", this.initialState);
        array.add(initialStateObject);
        jsonObject.put(this.name, array);
        return jsonObject;
    }

    String analyze() {
        Iterator itr = transitions.iterator();
        while (itr.hasNext()) {
            String[] reg = ((Transition) itr.next()).getDestination().split(" ");
            if (reg.length > 2) return "NFA";
            if (reg.length == 2 && reg[0].equals(" ")) return "NFA";
        }
        return "DFA";
    }

    void addToAlphabets(String alphabet) {
        this.alphabets.add(alphabet);
    }

    void addToStates(State state) {
        this.states.add(state);
    }

    void addToTransition(Transition transition) {
        this.transitions.add(transition);
    }
}

class State {
    ///////////////Attributes/////////////////
    private String name, positionX, positionY;
    ////////////////Constructors//////////////

    public State() {

    }

    public State(String name) {
        this.name = name;
    }

    public State(String name, String positionX, String positionY) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /////////Setter and Getter /////////////
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPositionX() {
        return positionX;
    }

    public void setPositionX(String positionX) {
        this.positionX = positionX;
    }

    public String getPositionY() {
        return positionY;
    }

    public void setPositionY(String positionY) {
        this.positionY = positionY;
    }
}

class Transition {
    /////////////////////Attributes////////////////
    private String name, source, destination, label;
    /////////////////Constructors//////////////////

    public Transition() {
    }

    public Transition(String name, String source, String destination, String label) {
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.label = label;
    }
    ///////////////Setter and Getter/////////////////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    @Override
    public boolean equals(Object obj) {
        return !this.getName().equals(((Automata) obj).getName());
    }
}


