package saxion.EHI1VSpq_1;

import java.io.Serializable;

class Position implements Serializable{

    private double internalX, internalY;
    private boolean isEnemy;

    Position(double x, double y) {
        internalX = x; internalY = y;
    }

    public Position(double internalX, double internalY, boolean isEnemy) {
        this.internalX = internalX;
        this.internalY = internalY;
        this.isEnemy = isEnemy;
    }

    Position() {

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

    /**
     * Get the angle (in degrees) to another position. Made to use with Robot.fire().
     * @param otherPosition The position to calculate the angle to.
     * @return  The angle (in degrees) to otherPosition.
     */
    double getAngleTo(Position otherPosition) {
        double result, tX = otherPosition.getX(), tY = otherPosition.getY(), aP, dX, dY;
        if(internalX > tX) {
            dX = internalX - tX;
            if(internalY > tY) {
                aP = 180;
                dY = internalY - tY;
            } else {
                aP = 270;
                dY = internalY + tY;
            }
        } else {
            dX = internalX + tX;
            if(internalY > tY) {
                aP = 90;
                dY = internalY - tY;
            } else {
                aP = 0;
                dY = internalY + tY;
            }
        }

        result = Math.atan(dY / dX);
        return result + aP;
    }

}
