package saxion.EHI1VSpq_1;

import java.util.HashMap;

public class Battlefield {

    private HashMap<String, Position> field = new HashMap<>();

    Battlefield() {

    }

    int robotsAlive() {
        return field.size();
    }

    void add(String name, Position position) {
        field.put(name, position);
    }

    void remove(String name) {
        field.remove(name);
    }

    void update(String name, Position position) {
        field.remove(name);
        field.put(name, position);
    }

    boolean contains(String name) {
        return field.containsKey(name);
    }

    boolean hasRobot(Position position) {
        return field.containsValue(position);
    }

    Position getPosition(String name) {
        return field.get(name);
    }

}
