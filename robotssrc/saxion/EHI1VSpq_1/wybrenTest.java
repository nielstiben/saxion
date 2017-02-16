package saxion.EHI1VSpq_1;

import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import robocode.Robot;
import robocode.AdvancedRobot;
import static robocode.util.Utils.normalRelativeAngleDegrees;
import robocode.HitByBulletEvent;
import java.awt.*;

import static robocode.util.Utils.normalRelativeAngleDegrees;

/**
 * Created by wybrenoppedijk on 16/02/2017.
 */
public class wybrenTest extends AdvancedRobot{
    int turnDirection = 1;

    public void run(){

        while(true) {
            this.turnRight((double)(5 * this.turnDirection));
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        if (e.getBearing() >= 0.0){
            this.turnDirection = 1;
        } else {
            this.turnDirection = -1;
        }
    }

    public void onHitRobot(HitRobotEvent e) {
        double turnGunAmt = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());

        turnGunRight(turnGunAmt);
        fire(3);
        scan();
    }

    public void onHitByBullet(HitByBulletEvent e) {

            turnRight(normalRelativeAngleDegrees(90 - (getHeading() - e.getHeading())));
            ahead(70);
            dist *= -1;
        scan();
    }

}
