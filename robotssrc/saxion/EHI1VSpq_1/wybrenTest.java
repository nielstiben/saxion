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
    int dist = 50;

    public void run(){

        while (true) {
            setBodyColor(Color.blue);
            turnGunRight(5);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        if (e.getDistance() <40 && this.getEnergy() > 40) {
            fire(3);
        }else if (e.getDistance() < 50 && this.getEnergy() > 35) {
            fire(2);
        } else {
            fire(1);
        }
    }

    public void onHitRobot(HitRobotEvent e) {
        double turnGunAmt = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());

        turnGunRight(turnGunAmt);
        fire(3);
    }

    public void onHitByBullet(HitByBulletEvent e) {

        while (getX() != 1 && getX() != 799 && getY() != 0 && getY() != 599){
            ahead(1);
        }

        scan();
    }

}
