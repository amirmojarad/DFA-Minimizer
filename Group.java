import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Group {
    private State state;
    private HashMap<String, State> map;
    private ArrayList<State> involve_states;
    public Group() {
        this.map = new HashMap<>();
        this.involve_states = new ArrayList<>();
    }

    public Group(State state, HashMap<String, State> map) {
        this.state = state;
        this.map = map;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public HashMap<String, State> getMap() {
        return map;
    }

    public void setMap(HashMap<String, State> map) {
        this.map = map;
    }

    public void addEntity(String label, State destination) {
        this.map.put(label, destination);
    }

    private boolean is_pair(Group group, String label) {
        return group.map.get(label).equals(this.state) && this.state.equals(group.map.get(label));
    }

    public boolean are_distinguishable(Group group) {
        boolean flag = false;
        for (String label : this.map.keySet()) {
            if (this.map.get(label).equals(group.getMap().get(label))) {
                flag = true;
            } else {
                flag = is_pair(group, label);
                if (!flag)
                    break;
            }
        }
        return flag;
    }

    @Override
    public String toString() {
        return this.state.getName();
    }

    @Override
    public boolean equals(Object obj) {
        Group temp = (Group) obj;
        return temp.getState().equals(this.state);
    }
}
