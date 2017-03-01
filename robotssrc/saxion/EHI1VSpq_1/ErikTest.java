package saxion.EHI1VSpq_1;

import robocode.Robot;
import robocode.ScannedRobotEvent;

public class ErikTest extends Robot {

    private Prediction predict = new Prediction();

    public void run() {

        while(true) {
            ahead(100);
            turnRight(90);
        }

    }

    public void onScannedRobot(ScannedRobotEvent event) {
        Position ownPos = new Position(getX(), getY());
        Position enemyPos = predict.getEnemyPosition(getHeading(), event.getBearing(), event.getDistance(), ownPos);
        Position fEnemyPos = predict.getFutureEnemyPosition(enemyPos, event.getVelocity(), event.getHeading(), 1);
        double angleToEnemy = ownPos.getAngleTo(fEnemyPos);

        if(getHeading() > angleToEnemy) {
            if(getHeading() - angleToEnemy > 180) {

            }
        }

        fire(2);

    }

}
