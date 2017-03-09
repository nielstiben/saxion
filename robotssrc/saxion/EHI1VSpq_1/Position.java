package saxion.EHI1VSpq_1;

import java.io.Serializable;

class Position implements Serializable{

    private double internalX, internalY;
    private boolean isEnemy;
    private Priority prior;

    Position(double x, double y, Priority priority) {
        internalX = x; internalY = y;
        prior = priority;
    }

    Position(double x, double y, boolean isEnemy) {
        internalX = x;
        internalY = y;
        this.isEnemy = isEnemy;
    }

    Position() {

    }

    Position(double x, double y, boolean isEnemy, Priority priority) {
        internalX = x;
        internalY = y;
        this.isEnemy = isEnemy;
        prior = priority;
    }

    double getX() {
        return internalX;
    }

    double getY() {
        return internalY;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    void setX(double x) {
        internalX = x;
    }

    void setY(double y) {
        internalY = y;
    }

    void setPosition(double x, double y) {
        internalX = x;
        internalY = y;
    }

    public Priority getPriority() {
        return prior;
    }

    public void setPriority(Priority priority) {
        this.prior = prior;
    }
}
