package saxion.EHI1VSpq_1;

import robocode.*;
import robocode.util.Utils;

public class MocroBot extends TeamRobot implements robocode.Droid {
    Position highestPrior = new Position();
    Position firePosition;

    public void run() {
        out.println("MyFirstDroid ready.");
        highestPrior.setPriority(Priority.LOWEST);
    }

    public void onMessageReceived(MessageEvent event) {

        if (event.getMessage() instanceof Battlefield) {
            Position position = ((Battlefield) event.getMessage()).getHighestPriority();
            if (((Battlefield) event.getMessage()).getHighestPriority().getPriority().greaterThan(highestPrior.getPriority())) {
                highestPrior = position;
                firePosition = position;
            } else {
                firePosition = highestPrior;
            }
            double theta = Utils.normalAbsoluteAngle(Math.atan2(
                    firePosition.getX() - getX(), firePosition.getY() - getY()));
            turnGunRightRadians(Utils.normalRelativeAngle(theta - getGunHeadingRadians()));
            fire(bulletPower(firePosition.getX(), firePosition.getY()));
        } else if (event.getMessage() instanceof RobotColors)

        {
            RobotColors colors = (RobotColors) event.getMessage();
            this.setColors(colors.bodyColor, colors.gunColor, colors.radarColor, colors.bulletColor, colors.scanColor);
            scan();
        }
    }


    public void onRobotDeath(RobotDeathEvent event) {
        if (event.getName().equals(highestPrior.getName())) highestPrior.setPriority(Priority.LOWEST);
    }

    //    @Override
//    public void onHitByBullet(HitByBulletEvent event) {
//        double x = getX();
//        double y = getY();
//
//        System.out.println("test");
//
//        if (x > 100 ) {
//            if (y > 100 ) {
//                goTo(x-100, y-100);
//            }
//        }
//
//
//
//    }
    public double bulletPower(double xPos, double yPos) {
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
