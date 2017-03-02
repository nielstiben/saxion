package saxion.EHI1VSpq_1;

class Prediction {

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
        // TODO (Wybren)
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
