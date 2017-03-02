package saxion.EHI1VSpq_1;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

import java.awt.*;

import static robocode.util.Utils.normalRelativeAngleDegrees;

public class RobotNiels extends Robot {
    boolean clockwise;
    int dist = 50; // distance to move when we're hit

    public void run() {

        while (true) {
            setBodyColor(new Color(255, 128, 0));
            ahead(100);

            turnRadarRight(360);


        }

    }

    public void onScannedRobot(ScannedRobotEvent event) {
        if (event.getBearing() < 180) {
            turnRight(event.getBearing());
            ahead(event.getDistance() - 200);
        } else {
            turnLeft(event.getBearing());
            ahead(event.getDistance() - 200);
        }
        fire(1);
        }
    public void onHitByBullet(HitByBulletEvent e) {
        fire(10);
    }



    }




