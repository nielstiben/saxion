package saxion.EHI1VSpq_1;

import robocode.ScannedRobotEvent;
import robocode.Robot;
import robocode.AdvancedRobot;

import java.awt.*;

/**
 * Created by wybrenoppedijk on 16/02/2017.
 */
public class wybrenTest extends AdvancedRobot{

    public void run(){

        while (true) {
            setBodyColor(Color.orange);

        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        if (e.getDistance() <30 && this.getEnergy() > 40) {
            fire(3);
        }else if (e.getDistance() < 40 && this.getEnergy() > 35) {
            fire(2);
        } else {
            fire(1);
        }
    }
}
