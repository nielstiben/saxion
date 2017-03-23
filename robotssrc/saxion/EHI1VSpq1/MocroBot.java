package saxion.EHI1VSpq1;

import robocode.MessageEvent;
import robocode.RobotDeathEvent;
import robocode.TeamRobot;
import robocode.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Primary attack robot.
 *
 * @author Erik & Wybren
 */
@SuppressWarnings("unused")
public class MocroBot extends TeamRobot implements robocode.Droid {
    private Battlefield battlefield = new Battlefield();

    /**
     * Constantly get the highest priority target from the battlefield and fire at that position, given there is no ally in the way.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            ahead(1);
            back(1);
            if (battlefield.hasEnemyRobot()) {
                Position pos = battlefield.getHighestPriority();

                double theta = Utils.normalAbsoluteAngle(Math.atan2(
                        pos.x - getX(), pos.y - getY()));
                turnGunRightRadians(Utils.normalRelativeAngle(theta - getGunHeadingRadians()));
                //fire(3);
                for (Map.Entry pair : anglesToFriendlyRobots().entrySet()) {
                    double margin = ((double) pair.getValue() - 600) / -100;
                    if (new Position(getX(), getY()).distanceTo(pos) > (double) pair.getValue()) {
                        if (margin < 0) margin = margin * -1;
                        if (margin > (double) pair.getKey()) {
                            fire(3);
                        } else out.println("Friend in the way!");
                    }
                }
            }

        }
    }

    /**
     * When a message is received, check what kind it is.
     * If it is an instance of Battlefield, update the internal battlefield.
     * If it is an instance of RobotColors, change the colors of the robot.
     *
     * @param event robocode.MessageEvent
     */
    public void onMessageReceived(MessageEvent event) {

        if (event.getMessage() instanceof Battlefield) {
            battlefield = (Battlefield) event.getMessage();
        } else if (event.getMessage() instanceof RobotColors) {
            RobotColors colors = (RobotColors) event.getMessage();
            this.setColors(colors.bodyColor, colors.gunColor, colors.radarColor, colors.bulletColor, colors.scanColor);
            scan();
        }
    }

    /**
     * When a robot dies, remove it from the battlefield.
     *
     * @param event robocode.RobotDeathEvent
     */
    public void onRobotDeath(RobotDeathEvent event) {
        battlefield.remove(event.getName());
    }

    /**
     * Helper method to get the angles to all friendly robots.
     *
     * @return {@code Map<Double, Double>} with the angle as key and the distance as value.
     */
    private Map<Double, Double> anglesToFriendlyRobots() {
        Map<Double, Double> result = new HashMap<>(4);

        for (Map.Entry pair : battlefield.getBattlefield().entrySet()) {

            Position p = (Position) pair.getValue();
            if (p.priority == Priority.TEAMMATE) {
                Position ownPos = new Position(getX(), getY());
                result.put(Utils.normalAbsoluteAngle(Math.atan2(p.x - ownPos.x, p.y - ownPos.y)), ownPos.distanceTo(p));
            }
        }

        return result;
    }

    /**
     * Helper method to calculate the bullet power to shoot with.
     *
     * @param xPos Target X
     * @param yPos Target Y
     * @return The power to fire with
     */
    private double bulletPower(double xPos, double yPos) {
        Position ownPos = new Position(getX(), getY());
        double distance = ownPos.distanceTo(new Position(xPos, yPos));

        if (distance < 0) {
            distance = distance * -1;
        }

        if (distance < 100 && getEnergy() > 30) {
            return 3;
        }
        if (distance < 300 && getEnergy() > 50) {
            return 2;
        }
        if (distance > 300 && getEnergy() > 100) {
            return 2;
        }
        if (distance > 300 && getEnergy() < 100) {
            return 1;
        }
        return 1;
    }

}
