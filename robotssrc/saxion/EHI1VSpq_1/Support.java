package saxion.EHI1VSpq_1;

import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

import java.io.IOException;

/**
 * Created by wybrenoppedijk on 02/03/2017.
 */
public class Support extends TeamRobot {
    Prediction prediction = new Prediction();

    public void run() {
        while (true) {
            setTurnRadarRight(10000);
        }
    }
    public void onScannedRobot(ScannedRobotEvent event) {
        Position position = new Position(this.getX(), this.getY());
        Position enemyPosition = prediction.getEnemyPosition(this.getHeading(), event.getBearing(), event.getDistance(), position);

        try {
            broadcastMessage(enemyPosition);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
