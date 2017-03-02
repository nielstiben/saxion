package saxion.EHI1VSpq_1;

import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import sampleteam.*;

import java.awt.*;
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
