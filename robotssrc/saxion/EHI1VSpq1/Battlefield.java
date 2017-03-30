package saxion.EHI1VSpq1;

import java.io.Serializable;
import java.util.*;

/**
 * A class to make communication using the <i>entire</i> map of the battlefield possible, with locations of all robots; friendly and enemy.
 *
 * @author Erik
 */
class Battlefield implements Serializable {

    private static transient Battlefield battlefield;
    private static transient Set<BattlefieldUpdateListener> listeners = new HashSet<>();
    private HashMap<String, Position> field = new HashMap<>();

    /**
     * Create a new Battlefield instance
     *
     * @deprecated Deprecated since 27/03/2017; use static method <code>Battlefield.getInstance()</code> instead.
     */
    @Deprecated
    Battlefield() {

    }

    /**
     * Get the singleton instance of a battlefield
     *
     * @return The singleton instance of a battlefield
     */
    static Battlefield getInstance() {
        if (battlefield == null) battlefield = new Battlefield();
        return battlefield;
    }

    /**
     * Add a listener to the battlefield so a class gets notified on update
     *
     * @param listener The listener class to add to the battlefield
     */
    static void addListener(BattlefieldUpdateListener listener) {
        listeners.add(listener);
    }

    /**
     * Add a robot to the battlefield (only to be used by robots with scanners!)
     *
     * @param name     Name of the robot
     * @param position Position of the robot
     */
    void add(String name, Position position) {
        field.put(name, position);
        notifyListeners();
    }

    /**
     * Remove a robot from the battlefield
     *
     * @param name Name of the robot
     */
    void remove(String name) {
        field.remove(name);
        notifyListeners();
    }

    /**
     * Update a robot's position (only to be used by robots with scanners!)
     *
     * @param name     Name of the robot
     * @param position New position of the robot
     */
    void update(String name, Position position) {
        field.remove(name);
        field.put(name, position);
        notifyListeners();
    }

    /**
     * Battlefield contains robot
     *
     * @param name Name of the robot
     * @return Battlefield contains robot true/false
     */
    boolean contains(String name) {
        return field.containsKey(name);
    }

    /**
     * Loops through the entire battlefield and checks for robots with priority that is not Priority.TEAMMATE.
     *
     * @return Whether there is an enemy on the battlefield.
     */
    boolean hasEnemyRobot() {
        for (Map.Entry pair : field.entrySet()) {
            if (((Position) pair.getValue()).priority != Priority.TEAMMATE) return true;
        }
        return false;
    }

    /**
     * Get the position of a robot
     *
     * @param name Name of the robot
     * @return The position of given robot
     */
    Position getPosition(String name) {
        return field.get(name);
    }

    /**
     * Get the highest priority position in the battlefield
     *
     * @return The position with the highest priority
     */
    Position getHighestPriority() {
        Iterator iterator = field.entrySet().iterator();
        Position result = new Position();
        result.priority = Priority.LOWEST;

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            if (((Position) pair.getValue()).priority.greaterThan(result.priority))
                result = (Position) pair.getValue();
        }

        return result;
    }

    /**
     * Get the entire battlefield
     *
     * @return The entire field
     */
    HashMap<String, Position> getBattlefield() {
        return field;
    }

    private void notifyListeners() {
        for (BattlefieldUpdateListener l : listeners) {
            l.onBattlefieldUpdate();
        }
    }

}
