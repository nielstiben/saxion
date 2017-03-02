package saxion.EHI1VSpq_1;

import robocode.*;
import sampleteam.RobotColors;

import static robocode.util.Utils.normalRelativeAngleDegrees;

public class QuickDroid extends TeamRobot implements robocode.Droid {

    public void run() {
        out.println("MyFirstDroid ready.");
    }

    public void onMessageReceived(MessageEvent event) {

        if(event.getMessage() instanceof Position) {
            Position position = (Position) event.getMessage();

            double dx = position.getX() - this.getX();
            double dy = position.getY() - this.getY();
            double angle = Math.toDegrees(Math.atan2(dx, dy));

            turnGunRight(normalRelativeAngleDegrees(angle - getGunHeading()));
            fire(3);
        } else if(event.getMessage() instanceof RobotColors) {
            RobotColors colors = (RobotColors) event.getMessage();
            this.setColors(colors.bodyColor, colors.gunColor, colors.radarColor, colors.bulletColor, colors.scanColor);
        }
    }

}
