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
class WybrenTest extends AdvancedRobot{
    int turnDirection = 1;
    int dist = 10;

    public void run(){

        while(true) {
            this.turnRight(10);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {

        this.turnRight(e.getBearing());
        this.ahead(e.getDistance() -150);
        if (e.getDistance() < 80) {
            fire(3);
        }else {
            fire(1);
        }
        scan();
    }




//    public void onHitByBullet(HitByBulletEvent e) {
//
//            turnRight(normalRelativeAngleDegrees(90 - (getHeading() - e.getHeading())));
//            ahead(dist);
//            dist *= -1;
//        scan();
//    }

}
