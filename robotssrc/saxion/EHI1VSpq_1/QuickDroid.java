package saxion.EHI1VSpq_1;

import robocode.*;
import robocode.util.Utils;
import sampleteam.RobotColors;

public class QuickDroid extends TeamRobot implements robocode.Droid {

    public void run() {
        out.println("MyFirstDroid ready.");
    }

    public void onMessageReceived(MessageEvent event) {

        if(event.getMessage() instanceof Position) {
            Position position = (Position) event.getMessage();
            double theta = Utils.normalAbsoluteAngle(Math.atan2(
                    position.getX() - getX(), position.getY() - getY()));

            setTurnGunRightRadians(Utils.normalRelativeAngle(theta - getGunHeadingRadians()));
        } else if(event.getMessage() instanceof RobotColors) {
            RobotColors colors = (RobotColors) event.getMessage();
            this.setColors(colors.bodyColor, colors.gunColor, colors.radarColor, colors.bulletColor, colors.scanColor);
            fire(3);
        }
    }

}
