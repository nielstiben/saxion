package saxion.EHI1VSpq_1;

class Prediction {

    /**
     * Get the position of the enemy you have spotted.
     *
     * @param ownHeading      Your own heading, retrieved through method Robot.getHeading().
     * @param enemyBearing    The enemies bearing, retrieved through method ScannedRobotEvent.getBearing().
     * @param distanceToEnemy The distance to the enemy, retrieved through method ScannedRobotEvent.getDistance().
     * @param ownPosition     Your own position. Use class Position, X and Y coordinates can be retrieved through Robot.getX() and Robot.getY().
     * @return The position of the enemy.
     */
    Position getEnemyPosition(double ownHeading, double enemyBearing, double distanceToEnemy, Position ownPosition) {
        Position result = new Position();
        double bearing = bearingFrom(enemyBearing, ownHeading);
        double x = ownPosition.getX(), y = ownPosition.getY();

        double distanceX = Math.cos(bearing) * distanceToEnemy;
        double distanceY = Math.sin(bearing) * distanceToEnemy;

        if (onRight(enemyBearing, ownHeading)) result.setX(x + distanceX);
        else result.setX(x - distanceX);

        if (isBelow(enemyBearing, ownHeading)) result.setY(y - distanceY);
        else result.setY(y + distanceY);

        return result;
    }

    /**
     * Get the expected position of the enemy you have spotted in 1 tick given they remain at the same speed and are not accelerating in any way.
     *
     * @param enemyPosition The position of the enemy, retrieved through Prediction.getEnemyPosition().
     * @param enemyVelocity The velocity of the enemy, retrieved through ScannedRobotEvent.getVelocity().
     * @param enemyHeading  The enemy's heading, retrieved through ScannedRobotEvent.getHeading() (Use ScannedRobotEvent!)
     * @param ticks         The amount of ticks into the future. The higher this number, the greater this inaccuracy. Use at own risk!
     * @return  The expected position of the enemy in 1 tick.
     */
    Position getFutureEnemyPosition(Position enemyPosition, double enemyVelocity, double enemyHeading, int ticks) {
        Position result = new Position();
        double x = enemyPosition.getX(), y = enemyPosition.getY();

        double distanceX = Math.cos(enemyHeading) * (enemyVelocity * ticks);
        double distanceY = Math.sin(enemyHeading) * (enemyVelocity * ticks);

        if (enemyHeading > 180) result.setX(x - distanceX);
        else result.setX(x + distanceX);

        if (enemyHeading > 90 && enemyHeading < 270) result.setY(y - distanceY);
        else result.setY(y + distanceY);

        return result;
    }

    private double bearingFrom(double b, double h) {
        if (b < 0) b += 360;
        double a = h + b, r;
        if (a > 90 && a <= 180) {
            r = a - 90;
        } else if (a > 180 && a <= 270) {
            r = a - 180;
        } else if (a > 270 && a < 360) {
            r = a - 270;
        } else {
            return a;
        }
        return r;
    }

    private boolean onRight(double b, double h) {
        double a = h + b;
        return !(a < 180);
    }

    private boolean isBelow(double b, double h) {
        double a = h + b;
        return (a > 90 && a < 270);
    }

}
