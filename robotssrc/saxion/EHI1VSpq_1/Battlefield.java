package saxion.EHI1VSpq_1;

import java.util.HashMap;

public class Battlefield {

    private HashMap<String, Position> field = new HashMap<>();

    Battlefield() {

    }

    /**
     * Get the amount of robots alive
     * @return          Amount of robots (friendly and enemy) alive
     */
    int robotsAlive() {
        return field.size();
    }

    /**
     * Add a robot to the battlefield (only to be used by robots with scanners!)
     * @param name      Name of the robot
     * @param position  Position of the robot
     */
    void add(String name, Position position) {
        field.put(name, position);
    }

    /**
     * Remove a robot from the battlefield
     * @param name      Name of the robot
     */
    void remove(String name) {
        field.remove(name);
    }

    /**
     * Update a robot's position (only to be used by robots with scanners!)
     * @param name      Name of the robot
     * @param position  New position of the robot
     */
    void update(String name, Position position) {
        field.remove(name);
        field.put(name, position);
    }

    /**
     * Battlefield contains robot
     * @param name      Name of the robot
     * @return          Battlefield contains robot true/false
     */
    boolean contains(String name) {
        return field.containsKey(name);
    }

    /**
     * Position is occupied by robot
     * @param position  The position to check
     * @return          Whether the posiition has a robot on it
     */
    boolean hasRobot(Position position) {
        return field.containsValue(position);
    }

    /**
     * Get the position of a robot
     * @param name      Name of the robot
     * @return          The position of given robot
     */
    Position getPosition(String name) {
        return field.get(name);
    }

    /**
     * Get the entire battlefield
     * @return          The entire field
     */
    HashMap getBattlefield() {
        return field;
    }

}