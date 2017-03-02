package saxion.EHI1VSpq_1;

import robocode.ScannedRobotEvent;
import robocode.AdvancedRobot;

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
