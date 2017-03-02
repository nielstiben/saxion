package saxion.EHI1VSpq_1;

import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import robocode.util.Utils;
import sampleteam.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.HashMap;

public class Support extends TeamRobot {
    private RobotColors c = new RobotColors();
    private HashMap<String, Position> battlefield = new HashMap<>();

    public void run() {
        int counter = 0;
        while (true) {
            if (counter > 6) {
                counter = 0;
            }
            counter++;
            switch (counter) {
                case 0:
                    c.bodyColor = Color.red;
                    c.gunColor = Color.orange;
                    c.radarColor = Color.yellow;
                    c.bulletColor = Color.green;
                    break;
                case 1:
                    c.bodyColor = Color.orange;
                    c.gunColor = Color.yellow;
                    c.radarColor = Color.green;
                    c.bulletColor = Color.blue;
                    break;
                case 2:
                    c.bodyColor = Color.yellow;
                    c.gunColor = Color.green;
                    c.radarColor = Color.blue;
                    c.bulletColor = Color.magenta;
                    break;
                case 3:
                    c.bodyColor = Color.green;
                    c.gunColor = Color.blue;
                    c.radarColor = Color.magenta;
                    c.bulletColor = Color.white;
                    break;
                case 4:
                    c.bodyColor = Color.blue;
                    c.gunColor = Color.magenta;
                    c.radarColor = Color.white;
                    c.bulletColor = Color.red;
                    break;
                case 5:
                    c.bodyColor = Color.magenta;
                    c.gunColor = Color.white;
                    c.radarColor = Color.red;
                    c.bulletColor = Color.orange;
                    break;
                case 6:
                    c.bodyColor = Color.WHITE;
                    c.gunColor = Color.red;
                    c.radarColor = Color.orange;
                    c.bulletColor = Color.yellow;
                    break;
                default: {
                    c.bodyColor = Color.WHITE;
                    c.gunColor = Color.WHITE;
                    c.gunColor = Color.white;
                    c.bulletColor = Color.white;
                }
            }

            setTurnRadarRight(10000);
            this.setColors(c.bodyColor, c.gunColor, c.radarColor, c.bulletColor, c.scanColor);
            try {
                broadcastMessage(c);
            } catch (IOException ignored) {
            }
            ahead(1);
            back(1);
        }
    }

    public void onScannedRobot(ScannedRobotEvent event) {

        if(isTeammate(event.getName())) return;

        double bulletPower = Math.min(3.0,getEnergy());
        double myX = getX();
        double myY = getY();
        double absoluteBearing = getHeadingRadians() + event.getBearingRadians();
        double enemyX = getX() + event.getDistance() * Math.sin(absoluteBearing);
        double enemyY = getY() + event.getDistance() * Math.cos(absoluteBearing);
        double enemyHeading = event.getHeadingRadians();
        double enemyVelocity = event.getVelocity();


        double deltaTime = 0;
        double battleFieldHeight = getBattleFieldHeight(),
                battleFieldWidth = getBattleFieldWidth();
        double predictedX = enemyX, predictedY = enemyY;
        while((++deltaTime) * (20.0 - 3.0 * bulletPower) <
                Point2D.Double.distance(myX, myY, predictedX, predictedY)){
            predictedX += Math.sin(enemyHeading) * enemyVelocity;
            predictedY += Math.cos(enemyHeading) * enemyVelocity;
            if(	predictedX < 18.0
                    || predictedY < 18.0
                    || predictedX > battleFieldWidth - 18.0
                    || predictedY > battleFieldHeight - 18.0){
                predictedX = Math.min(Math.max(18.0, predictedX),
                        battleFieldWidth - 18.0);
                predictedY = Math.min(Math.max(18.0, predictedY),
                        battleFieldHeight - 18.0);
                break;
            }
        }
        double theta = Utils.normalAbsoluteAngle(Math.atan2(
                predictedX - getX(), predictedY - getY()));

        setTurnRadarRightRadians(
                Utils.normalRelativeAngle(absoluteBearing - getRadarHeadingRadians()));
        setTurnGunRightRadians(Utils.normalRelativeAngle(theta - getGunHeadingRadians()));
        fire(bulletPower);

        try {
            broadcastMessage(new Position(predictedX, predictedY));
        } catch (IOException e) {
            e.printStackTrace();
        }


        double enemyBearing = this.getHeading() + event.getBearing(), positionX = getX() + event.getDistance() * Math.sin(Math.toRadians(enemyBearing)), positionY = getY() + event.getDistance() * Math.cos(Math.toRadians(enemyBearing));

        String name = event.getName();
        Position position = new Position(positionX, positionY, !isTeammate(name));

        if (battlefield.containsKey(event.getName()))
            battlefield.replace(name, position);
        else
            battlefield.put(event.getName(), new Position(positionX, positionY, !isTeammate(event.getName())));

        try {
            broadcastMessage(battlefield);
        } catch (IOException ex) {
            out.println("Unable to send order: ");
            ex.printStackTrace(out);
        }
    }
}
