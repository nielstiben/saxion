package saxion.EHI1VSpq_1;

import robocode.Robot;
import robocode.ScannedRobotEvent;

public class ErikTest extends Robot {

    public void run() {

        while(true) {
            ahead(100);
            turnRight(90);
            ahead(100);
            turnRight(90);
            ahead(100);
            turnRight(90);
            ahead(100);
            turnRight(90);
        }

    }

    public void onScannedRobot(ScannedRobotEvent event) {
        if(event.getBearing() < 180) {
            turnRight(event.getBearing());
        } else {
            turnLeft(event.getBearing());
        }
        if(event.getDistance() < 80) {
            fire(3);
        } else {
            fire(4);
        }
    }

}
