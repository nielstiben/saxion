package saxion.EHI1VSpq_1;

import robocode.*;
import robocode.util.Utils;
import sampleteam.RobotColors;

public class MocroBot extends TeamRobot implements robocode.Droid {

    public void run() {
        out.println("MyFirstDroid ready.");

    }

    public void onMessageReceived(MessageEvent event) {


        if (event.getMessage() instanceof Position) {
            double bulletPower = Math.min(3.0, getEnergy());
            Position position = (Position) event.getMessage();
            double theta = Utils.normalAbsoluteAngle(Math.atan2(
                    position.getX() - getX(), position.getY() - getY()));
            //goTo(position.getX(), position.getY());
            setTurnGunRightRadians(Utils.normalRelativeAngle(theta - getGunHeadingRadians()));
            fire(bulletPower);
        } else if (event.getMessage() instanceof RobotColors) {
            RobotColors colors = (RobotColors) event.getMessage();
            this.setColors(colors.bodyColor, colors.gunColor, colors.radarColor, colors.bulletColor, colors.scanColor);
            scan();
        }
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
