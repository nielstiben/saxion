package saxion.EHI1VSpq_1;

import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import robocode.util.Utils;
import sampleteam.RobotColors;

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
            ahead(100);
            back(100);
        }
    }

    public void onHitByBullet(HitByBulletEvent event) {
        double x = getX();
        double y = getY();

        if (isTeammate(event.getName())) return;
        Position pos = battlefield.get(event.getName());
        pos.setPriority(Priority.HIGH);
        try {
            broadcastMessage(pos);
        } catch (IOException ignored) {
        }

        if (x > 100) {
            if (y > 100) {
                goTo(x - 100, y - 100);
            }
        }
    }

    public void onScannedRobot(ScannedRobotEvent event) {

        if (isTeammate(event.getName())) return;
        Position position = new Position();

        double bulletPower = Math.min(3.0, getEnergy());
        double myX = getX();
        double myY = getY();
        double absoluteBearing = getHeadingRadians() + event.getBearingRadians();
        double enemyX = getX() + event.getDistance() * Math.sin(absoluteBearing);
        double enemyY = getY() + event.getDistance() * Math.cos(absoluteBearing);
        double enemyHeading = event.getHeadingRadians();
        double enemyVelocity = event.getVelocity();
        double deltaTime = 0;
        double battleFieldHeight = getBattleFieldHeight(), battleFieldWidth = getBattleFieldWidth();
        double predictedX = enemyX, predictedY = enemyY;

        while ((++deltaTime) * (20.0 - 3.0 * bulletPower) < Point2D.Double.distance(myX, myY, predictedX, predictedY)) {
            predictedX += Math.sin(enemyHeading) * enemyVelocity;
            predictedY += Math.cos(enemyHeading) * enemyVelocity;
            if (predictedX < 18.0 || predictedY < 18.0 || predictedX > battleFieldWidth - 18.0 || predictedY > battleFieldHeight - 18.0) {
                predictedX = Math.min(Math.max(18.0, predictedX),
                        battleFieldWidth - 18.0);
                predictedY = Math.min(Math.max(18.0, predictedY),
                        battleFieldHeight - 18.0);
                break;
            }
        }
        position.setPosition(predictedX, predictedY);

        String enemyName = event.getName().toLowerCase();
        if (enemyName.contains("leader") || enemyName.contains("master") || enemyName.contains("support") || enemyName.contains("communication")) {
            position.setPriority(Priority.HIGHEST);
        }

        try {
            broadcastMessage(position);
        } catch (IOException ignored) {
        } finally {
            battlefield.put(enemyName, position);
        }

    }

    private void goTo(double x, double y) {
/* Transform our coordinates into a vector */
        x -= getX();
        y -= getY();

	/* Calculate the angle to the target position */
        double angleToTarget = Math.atan2(x, y);

	/* Calculate the turn required get there */
        double targetAngle = Utils.normalRelativeAngle(angleToTarget - getHeadingRadians());

	/*
     * The Java Hypot method is a quick way of getting the length
	 * of a vector. Which in this case is also the distance between
	 * our robot and the target location.
	 */
        double distance = Math.hypot(x, y);

	/* This is a simple method of performing set front as back */
        double turnAngle = Math.atan(Math.tan(targetAngle));
        setTurnRightRadians(turnAngle);
        if (targetAngle == turnAngle) {
            setAhead(distance);
        } else {
            setBack(distance);
        }
    }
}
