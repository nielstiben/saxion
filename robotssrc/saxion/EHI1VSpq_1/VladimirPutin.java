package saxion.EHI1VSpq_1;

import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

@SuppressWarnings("unused")
public class VladimirPutin extends TeamRobot {

    private Battlefield battlefield = new Battlefield();
    private int colorsCounter = 0;
    private Random rand = new Random();

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            RobotColors c = robotColors();
            this.setColors(c.bodyColor, c.gunColor, c.radarColor, c.bulletColor, c.scanColor);
            try {
                broadcastMessage(c);
            } catch (IOException ignored) {
            }

            setTurnRadarRight(10000);
            //setAhead(rand.nextInt(200));
            //setBack(rand.nextInt(200));
            ahead(10);
            back(10);
            scan();
        }
    }

    public void onScannedRobot(ScannedRobotEvent event) {
        String name = event.getName();

        double absoluteBearing = getHeadingRadians() + event.getBearingRadians();
        double enemyX = getX() + event.getDistance() * Math.sin(absoluteBearing);
        double enemyY = getY() + event.getDistance() * Math.cos(absoluteBearing);

        Position position = new Position(enemyX, enemyY);

        if(isTeammate(name)) position.setPriority(Priority.TEAMMATE);

        if (battlefield.contains(name)) {
            if(battlefield.getPosition(name).getPriority() == null) position.setPriority(Priority.STANDARD);
            battlefield.update(name, position);
        }
        else battlefield.add(name, position);

        try {
            broadcastMessage(battlefield);
        } catch (IOException ignored) {
        }
    }

    public void onHitByBullet(HitByBulletEvent event) {
        String name = event.getName();
        if (battlefield.contains(name)) {
            Position p = battlefield.getPosition(name);
            p.setPriority(Priority.HIGH);
            battlefield.update(name, p);
        }

        try {
            broadcastMessage(battlefield);
        } catch (IOException ignored) {
        }
    }

    private RobotColors robotColors() {
        RobotColors c = new RobotColors();
        if (colorsCounter > 6) {
            colorsCounter = 0;
        }
        colorsCounter++;
        switch (colorsCounter) {
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
        return c;
    }

}
