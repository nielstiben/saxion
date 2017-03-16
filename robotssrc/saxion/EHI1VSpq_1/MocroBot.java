package saxion.EHI1VSpq_1;

import robocode.BulletHitEvent;
import robocode.MessageEvent;
import robocode.RobotDeathEvent;
import robocode.TeamRobot;
import robocode.util.Utils;

import java.util.*;

@SuppressWarnings("unused")
public class MocroBot extends TeamRobot implements robocode.Droid {
    private Battlefield battlefield = new Battlefield();
    Position pos;
    private int fire = 0;

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            ahead(1);
            back(1);
            System.out.println("I have missed" + fire + " shots.");
            if (battlefield.hasEnemyRobot()) {
                pos = battlefield.getHighestPriority();

                double theta = Utils.normalAbsoluteAngle(Math.atan2(
                        pos.getX() - getX(), pos.getY() - getY()));
                turnGunRightRadians(Utils.normalRelativeAngle(theta - getGunHeadingRadians()));

                for (Position p : friendlyRobots()) {
                    double friendlyTheta = Utils.normalAbsoluteAngle(Math.atan2(
                            p.getX() - getX(), p.getY() - getY()));
                    out.println(theta);
                    out.println((theta + ((2 * Math.PI) / 360) * 2.5)+" Na de berekening");
                    if (friendlyTheta <= (theta + ((2 * Math.PI) / 360) * 3) && friendlyTheta >= (theta - ((2 * Math.PI) / 360) * 3)) {
                        double distanceEnemy = Math.sqrt(Math.pow(getX() - pos.getX(), 2) + Math.pow(getY() - pos.getY(), 2));
                        if (distanceEnemy < 0) {
                            distanceEnemy = distanceEnemy * -1;
                        }
                        double distanceFriendly = Math.sqrt(Math.pow(getX() - p.getX(), 2) + Math.pow(getY() - p.getY(), 2));
                        if (distanceFriendly < 0) {
                            distanceFriendly = distanceFriendly * -1;
                        }
                        if (distanceEnemy > distanceFriendly) {
                            out.println("Teammate in the way!");
                        } else {
                            fire++;
                            fire(3);
                        }
                    }else {
                        fire++;
                        fire(3);
                    }
                }
            }
        }

    }

    public void onBulletHit(BulletHitEvent event) {
        fire--;
    }

    private Set<Position> friendlyRobots() {
        Set<Position> result = new HashSet<>();
        HashMap<String, Position> field = battlefield.getBattlefield();

        Iterator iterator = field.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();

            if (((Position) pair.getValue()).getPriority() == Priority.TEAMMATE)
                result.add((Position) pair.getValue());
        }

        return result;
    }


    public void onMessageReceived(MessageEvent event) {

        if (event.getMessage() instanceof Battlefield) {
            battlefield = (Battlefield) event.getMessage();
        } else if (event.getMessage() instanceof RobotColors)

        {
            RobotColors colors = (RobotColors) event.getMessage();
            this.setColors(colors.bodyColor, colors.gunColor, colors.radarColor, colors.bulletColor, colors.scanColor);
            scan();
        }
    }

    public void onRobotDeath(RobotDeathEvent event) {
        battlefield.remove(event.getName());
    }

    private double bulletPower(double xPos, double yPos) {
        double distance = Math.sqrt(Math.pow(getX() - xPos, 2) + Math.pow(getY() - yPos, 2));

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

    private void goTo(double x, double y) {
    /* Transform our coordinates into a vector */
        x -= getX();
        y -= getY();

	/* Calculate the angle to the target position */
        //noinspection SuspiciousNameCombination
        double angleToTarget = Math.atan2(x, y);

	/* Calculate the turn required get there */
        double targetAngle = Utils.normalRelativeAngle(angleToTarget - getHeadingRadians());

	/*
     * The Java Hypot method is a quick way of getting the length
	 * of a vector. Which in this case is also the distance between
	 * our robot and the target location.
	 */
        double distance = Math.hypot(x, y);

	/* This is a simple method of performing set front as back */
        double turnAngle = Math.atan(Math.tan(targetAngle));
        setTurnRightRadians(turnAngle);
        if (targetAngle == turnAngle) {
            setAhead(distance - 100);
        } else {
            setBack(distance - 100);
        }
    }

}
