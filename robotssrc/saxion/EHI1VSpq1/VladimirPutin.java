package saxion.EHI1VSpq1;

import robocode.HitByBulletEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

/**
 * Core of the team
 *
 * @author Erik & Wybren
 */
@SuppressWarnings("unused")
public class VladimirPutin extends TeamRobot {

    private Battlefield battlefield = new Battlefield();
    private int colorsCounter = 0;
    private Random rand = new Random();

    private int movementDirection = 1;
    private int gunDirection = 1;

    /**
     * Constantly try to change colors, turn the radar with a huge amount so that it never stops turning and go back and forth with 200 pixels
     */
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
            ahead(200);
            back(200);
            scan();
        }
    }

    /**
     * When a robot is scanned: put their scanned position in the battlefield map.
     * If the name contains "leader", set this enemy as {<code>Priority.HIGHEST</code>}.
     * If there is a small energy drop in the enemy's energy, assume it fired and dodge the bullet.
     *
     * @param event robocode.ScannedRobotEvent
     */
    public void onScannedRobot(ScannedRobotEvent event) {
        String name = event.getName();

        double absoluteBearing = getHeadingRadians() + event.getBearingRadians();
        double enemyX = getX() + event.getDistance() * Math.sin(absoluteBearing);
        double enemyY = getY() + event.getDistance() * Math.cos(absoluteBearing);

        Position position = new Position(enemyX, enemyY);

        if (isTeammate(name)) position.priority = Priority.TEAMMATE;

        String compared = name.toLowerCase();
        if (compared.contains("leader") || compared.contains("master") || compared.contains("communication") || compared.contains("support"))
            position.priority = Priority.HIGHEST;

        if (battlefield.contains(name)) {
            if (battlefield.getPosition(name).priority == null) position.priority = Priority.STANDARD;
            battlefield.update(name, position);
        } else battlefield.add(name, position);

        battlefield.add(getName(), new Position(getX(), getY(), Priority.TEAMMATE));

        try {
            broadcastMessage(battlefield);
        } catch (IOException ignored) {
        } finally {
            setTurnRight(event.getBearing() + 90 - 30 * movementDirection);
            double changeInEnergy = 100 - event.getEnergy();
            if (changeInEnergy > 0 && changeInEnergy <= 3) {
                movementDirection = -movementDirection;
                setAhead((event.getDistance() / 4 + 25) * movementDirection);
            }
        }

    }

    /**
     * When a robot dies, remove it from the battlefield.
     *
     * @param event robocode.RobotDeathEvent
     */
    public void onRobotDeath(RobotDeathEvent event) {
        battlefield.remove(event.getName());
        try {
            broadcastMessage(battlefield);
        } catch (IOException ignored) {
        }
    }

    /**
     * When hit by a bullet, get the robot that is targeting this and set its priority to <code>Priority.HIGH</code>
     * @param event robocode.HitByBulletEvent
     */
    public void onHitByBullet(HitByBulletEvent event) {
        String name = event.getName();
        if (battlefield.contains(name)) {
            Position p = battlefield.getPosition(name);
            p.priority = Priority.HIGH;
            battlefield.update(name, p);
        }

        try {
            broadcastMessage(battlefield);
        } catch (IOException ignored) {
        }
    }

    /**
     * Assign random colors to every part of the robot.
     * @return RobotColors instance with random coloring
     */
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
