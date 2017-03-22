package saxion.EHI1VSpq_1;

import java.io.Serializable;

/**
 * A class to make sending positions easier for everyone; this should also take care of compatibility issues.
 */
class Position implements Serializable {

    double x, y;
    Priority priority = Priority.LOWEST;

    Position(double x, double y, Priority priority) {
        this.x = x;
        this.y = y;
        this.priority = priority;
    }

    Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    Position() {

    }

    /**
     * Set the x <i>and</i> y coordinates of the position.
     *
     * @param x The X coordinate to be set
     * @param y The Y coordinate to be set
     */
    void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculate the absolute distance to another point using the Pythagoras theorem.
     * Added for convenience purposes.
     *
     * @param p The Position to calculate the distance to
     * @return The absolute distance (in pixels) to <i>p</i>.
     */
    double distanceTo(Position p) {
        return Math.hypot(p.x - x, p.y - y);
    }

}
