package saxion.EHI1VSpq1;

import robocode.*;
import sampleteam.RobotColors;

import static robocode.util.Utils.normalRelativeAngleDegrees;

/**
 * Unused.
 */
public class Droid extends TeamRobot implements robocode.Droid {
    private int wallMargin = 60; //how close the robot is allowed to get to the walls:
    private int newDirection = 1; // Clockwise or counterclockwise
    private int distance = 50; // Distance to move when being hit

    public void run() {
        out.println("MyFirstDroid ready.");
    }

    @Override
    public void setMaxVelocity(double newMaxVelocity) {
        newMaxVelocity=200;
        super.setMaxVelocity(newMaxVelocity);
    }

    public void onMessageReceived(MessageEvent e) {
        //Get position
        if (e.getMessage() instanceof Position) {
            Position position = (Position) e.getMessage();

//            Position myPosition = new Position();
//            myPosition.setX(this.getX());
//            myPosition.setY(this.getY());
//
//            // square off against the enemy/toward him
//            setTurnRight(myPosition.getAngleTo(position) + 90 - (10 * newDirection));

            //Calculate X and Y
            double dx = position.x - this.getX();
            double dy = position.y - this.getY();

            //Calculate angle
            double theta = Math.toDegrees(Math.atan2(dx, dy));

            //Adjust gun and fire (depends on energy level to save energy)
            turnGunRight(normalRelativeAngleDegrees(theta - getGunHeading()));
            if (getEnergy() > 20) {
                fire(4);
            } else if (getEnergy() > 10) {
                fire(3);
            } else if (getEnergy() > 5) {
                fire(1.5);
            } else if (getEnergy() > 2) {
                fire(.5);
            } else {
                fire(.1);
            }
        } else if (e.getMessage() instanceof RobotColors) {
            RobotColors colors = (RobotColors) e.getMessage();
            this.setColors(colors.bodyColor, colors.gunColor, colors.radarColor, colors.bulletColor, colors.scanColor);
            fire(3);
        }
    }


    //Avoid hitting the wall
    @Override
    public void addCustomEvent(Condition condition) {
        addCustomEvent(new Condition("too_close_to_walls") {
            public boolean test() {
                return (
                        // Too close to the left wall
                        (getX() <= wallMargin ||
                                // Too close to the right wall
                                getX() >= getBattleFieldWidth() - wallMargin ||
                                // Too close to the bottom wall
                                getY() <= wallMargin ||
                                // Too close to the top wall
                                getY() >= getBattleFieldHeight() - wallMargin)
                );
            }
        });
    }
    public void onCustomEvent(CustomEvent e) {
        if (e.getCondition().getName().equals("too_close_to_walls")) {
            // switch directions and move away
            newDirection *= -1;
            setAhead(10000 * newDirection);
        }
    }
    //Being hit by a bullet: Move a bit, perpendicular to the bullet
    public void onHitByBullet(HitByBulletEvent e) {
        setTurnRight(normalRelativeAngleDegrees(90 - (getHeading() - e.getHeading())));
        setAhead(distance);
        distance *= -1;
    }

    //Being hit by the enemy: Face the enemy, ram against him
    public void onHitRobot(HitRobotEvent e) {
        //Turn to face him
        if (e.getBearing() >= 0) {
            newDirection = 1;
        } else {
            newDirection = -1;
        }
        turnRight(e.getBearing());
        // Ram him again to gain energy!
        ahead(40);
        fire(3);
    }
}




