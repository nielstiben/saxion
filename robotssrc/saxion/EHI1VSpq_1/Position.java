package saxion.EHI1VSpq_1;

import java.io.Serializable;

class Position implements Serializable{

    private double internalX, internalY;
    private boolean isEnemy;
    private Priority prior = Priority.LOWEST;
    private String name;

    Position(double x, double y, Priority priority) {
        internalX = x; internalY = y;
        prior = priority;
    }

    Position(double x, double y, boolean isEnemy) {
        internalX = x;
        internalY = y;
        this.isEnemy = isEnemy;
    }

    Position(double x, double y) {
        internalX = x;
        internalY = y;
    }

    Position() {

    }

    Position(double x, double y, boolean isEnemy, Priority priority, String name) {
        internalX = x;
        internalY = y;
        this.isEnemy = isEnemy;
        prior = priority;
    }

    double getX() {
        return internalX;
    }

    void setX(double x) {
        internalX = x;
    }

    double getY() {
        return internalY;
    }

    void setY(double y) {
        internalY = y;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    void setPosition(double x, double y) {
        internalX = x;
        internalY = y;
    }

    public Priority getPriority() {
        return prior;
    }

    public void setPriority(Priority priority) {
        this.prior = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
