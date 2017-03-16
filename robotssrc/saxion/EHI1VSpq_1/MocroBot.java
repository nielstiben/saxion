package saxion.EHI1VSpq_1;

import robocode.MessageEvent;
import robocode.TeamRobot;
import robocode.util.Utils;

@SuppressWarnings("unused")
public class MocroBot extends TeamRobot implements robocode.Droid {
    private Battlefield battlefield = new Battlefield();

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            ahead(1);
            back(1);
            if (battlefield.hasEnemyRobot()) {
                Position pos = battlefield.getHighestPriority();

                double theta = Utils.normalAbsoluteAngle(Math.atan2(
                        pos.getX() - getX(), pos.getY() - getY()));
                turnGunRightRadians(Utils.normalRelativeAngle(theta - getGunHeadingRadians()));
                fire(bulletPower(pos.getX(), pos.getY()));
            }
        }

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

    private double bulletPower(double xPos, double yPos) {
        double distance = Math.sqrt(Math.pow(getX() - xPos, 2) + Math.pow(getY() - yPos, 2));

        if (distance < 0) {
            distance = distance * -1;
        }

        if (distance < 100 && getEnergy() > 70) {
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
