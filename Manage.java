import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * <h1> Manage Class </h1>
 * this class is a simple interface between logical part and graphical part of my project
 *
 * @author Amir Mojarad
 * @version 1.0
 * @since 2020-4-2
 */
public class Manage {
    /**
     * **************
     * * Attributes *
     * **************
     */
    private ArrayList<Automata> automatas;
    private ArrayList<String> automataStrings;
    private JSONArray array;
    private JSONObject jsonObject;
    private String filePath;

    /*
     * ***************
     * * Constructor *
     * ***************
     */
    public Manage() {
        this.automatas = new ArrayList<>();
        ///set up source in filaPath Attributes
        this.filePath = "E:\\University Courses\\Term 4\\Formal Languages and Automata\\Projects\\project\\src\\project01\\file.json";
        this.array = new JSONArray();
        this.jsonObject = new JSONObject();
        this.automataStrings = new ArrayList<>();
    }

    /*
     * ***********
     * * Methods *
     * ***********
     */
    /**
     * in this function add an automata object to automatas list
     * @param automata this object add to {@link Manage#automatas}
     */
    boolean addToAutomatas(Automata automata) {
        if (!automatas.contains(automata)) {
            this.automatas.add(automata);
            return true;
        }
        return false;
    }

    /**
     * saves all automatas that stored in automatas arrayList
     * @throws IOException
     */
    public void saveAll() throws IOException {
        Iterator itr = automatas.iterator();
        while (itr.hasNext()) {
            Automata automata = (Automata) itr.next();
            jsonObject.put(automata.getName(), saveObj(automata));
        }
        Files.write(Paths.get(filePath), jsonObject.toJSONString().getBytes());
    }

    /**
     *
     * @param automata take an automata and convert it to a {@link JSONObject}
     * @return  jsonObject that has all data in automata object
     */
    JSONObject saveObj(Automata automata) {
        JSONObject object = new JSONObject();
        object.put("transitions", transitionsToJSONArray(automata));
        object.put("finalState", finalStateToJSONArray(automata));
        object.put("states", statesToJSONArray(automata));
        object.put("initialState", automata.getInitialState());
        object.put("alphabets", alphabetToJSONArray(automata));
        return object;
    }

    /**
     *
     * @param automata takes automata and convert {@link Automata#getAlphabets()} to JSONArray obejct
     * @return a jsonArray object
     */
    JSONArray alphabetToJSONArray(Automata automata) {
        JSONArray array = new JSONArray();
        Iterator itr = automata.getAlphabets().iterator();
        while (itr.hasNext()) array.add(itr.next());
        return array;
    }


    /**
     *
     * @param automata takes automata and convert {@link Automata#getFinalStates()} to JSONArray obejct
     * @return a jsonArray object
     */
    JSONArray finalStateToJSONArray(Automata automata) {
        JSONArray array = new JSONArray();
        Iterator itr = automata.getFinalStates().iterator();
        while (itr.hasNext()) array.add(itr.next());
        return array;
    }


    /**
     *
     * @param automata takes automata and convert {@link Automata#getTransitions()} to JSONArray obejct
     * @return a jsonArray object
     */
    JSONArray transitionsToJSONArray(Automata automata) {
        JSONArray array = new JSONArray();
        Iterator itr = automata.getTransitions().iterator();
        while (itr.hasNext()) array.add(transitionToObject((Transition) itr.next()));
        return array;
    }

    /**
     *
     * @param transition takes a transition and convert it to JSONObject
     * @return jsonObject
     */
    JSONObject transitionToObject(Transition transition) {
        JSONObject object = new JSONObject();
        object.put("name", transition.getName());
        object.put("destination", transition.getDestination());
        object.put("source", transition.getSource());
        object.put("label", transition.getLabel());
        return object;
    }


    /**
     *
     * @param automata takes automata and convert {@link Automata#getStates()} to JSONArray obejct
     * @return a jsonArray object
     */

    JSONArray statesToJSONArray(Automata automata) {
        JSONArray array = new JSONArray();
        Iterator itr = automata.getStates().iterator();
        while (itr.hasNext()) array.add(stateToObject((State) itr.next()));
        return array;
    }

    /**
     *
     * @params state takes a state and convert it to JSONObject
     * @return jsonObject
     */
    JSONObject stateToObject(State state) {
        JSONObject object = new JSONObject();
        object.put("positionX", state.getPositionX());
        object.put("positionY", state.getPositionY());
        object.put("name", state.getName());
        return object;
    }

    /////////////////////////////
    //Load information methods //
    /////////////////////////////

    /**
     * load all information that exist in file.json and stored in {@link Manage#jsonObject}
     * @throws ParseException
     * @throws IOException
     */
    public void loadAll() throws ParseException, IOException {
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader(filePath);
        this.jsonObject = (JSONObject) parser.parse(reader);
        this.automataStrings.addAll(jsonObject.keySet());
        convertToAutomata(jsonObject);
    }

    //TODO work on graphic of project
    //TODO done save and load and actions of programs

    /**
     *
     * @param object takes an jsonObject and convert it to automata and stored in automatas list
     */
    void convertToAutomata(JSONObject object) {
        Iterator itr = this.automataStrings.iterator();
        for (int i = 0; i < object.size(); i++) {
            String name = (String) itr.next();
            JSONObject value = (JSONObject) object.get(name);
            this.addToAutomatas(createAutomata(name, value));
        }
    }

    /**
     *
     * @param name gives a name (name of automata)
     * @param object convert this object to automata
     * @return
     */
    Automata createAutomata(String name, JSONObject object) {
        Automata automata = new Automata();
        automata.setName(name);
        automata.setAlphabets(convertToList("alphabets", (JSONArray) object.get("alphabets")));
        automata.setTransitions(convertToList("transition", (JSONArray) object.get("transitions")));
        automata.setStates(convertToList("states", (JSONArray) object.get("states")));
        automata.setFinalStates(convertToList("finalState", (JSONArray) object.get("finalState")));
        automata.setInitialState((String) object.get("initialState"));
        return automata;
    }

    /**
     *
     * @param type could be transition, finalState, alphabets
     * @param jsonArray convert this to a list that should be stored in autota object
     * @return an arrayList that setting up in automata's fields
     */
    ArrayList convertToList(String type, JSONArray jsonArray) {
        Iterator itr = jsonArray.iterator();
        if (type.equals("transition")) {
            ArrayList<Transition> list = new ArrayList<>();
            while (itr.hasNext()) list.add(castToTransition((JSONObject) itr.next()));
            return list;
        } else if (type.equals("finalStates")) {
            ArrayList<String> list = new ArrayList<>();
            while (itr.hasNext()) list.add((String) itr.next());
            return list;
        } else if (type.equals("states")) {
            ArrayList<State> list = new ArrayList<>();
            while (itr.hasNext()) list.add(castToState((JSONObject) itr.next()));
            return list;
        } else if (type.equals("alphabets")) {
            ArrayList<String> list = new ArrayList<>();
            while (itr.hasNext()) list.add((String) itr.next());
            return list;
        }
        return null;
    }

    /**
     *
     * @param object takes this and convert JSONObject to Transition Object
     * @return a transition object
     */
    Transition castToTransition(JSONObject object) {
        Transition transition = new Transition();
        transition.setName((String) object.get("name"));
        transition.setLabel((String) object.get("label"));
        transition.setSource((String) object.get("source"));
        transition.setDestination((String) object.get("destination"));
        return transition;
    }
    /**
     *
     * @param object takes this and convert JSONObject to State Object
     * @return a state object
     */
    State castToState(JSONObject object) {
        State state = new State();
        state.setPositionX((String) object.get("positionX"));
        state.setPositionY((String) object.get("positionY"));
        state.setName((String) object.get("name"));
        return state;
    }
    void clearAllData(){
        this.jsonObject.clear();
        this.automatas.clear();
        try {
            Files.write(Paths.get(filePath), jsonObject.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
     * ***************************
     *      Setter and Getter
     * ***************************
     */

    public ArrayList<Automata> getAutomatas() {
        return automatas;
    }

    public void setAutomatas(ArrayList<Automata> automatas) {
        this.automatas = automatas;
    }

    public ArrayList<String> getAutomataStrings() {
        return automataStrings;
    }

    public void setAutomataStrings(ArrayList<String> automataStrings) {
        this.automataStrings = automataStrings;
    }

    public JSONArray getArray() {
        return array;
    }

    public void setArray(JSONArray array) {
        this.array = array;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
