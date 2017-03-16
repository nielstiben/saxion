/**
 * Copyright (c) 2001-2014 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package saxion.EHI1VSpq_1;


import robocode.*;
import robocode.Robot;
import robocode.util.Utils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Walls - a sample robot by Mathew Nelson, and maintained by Flemming N. Larsen
 * <p/>
 * Moves around the outer edge with the gun facing in.
 *
 * @author Niels Tiben (original)
 * @author Niels Tiben (contributor)
 */
public class Borderbot extends TeamRobot {


    // Calculate closest wall
    private void goTo(int x, int y) {
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

    private void goToWall() {
        double x = getX();
        double y = getY();
        double width = getBattleFieldWidth();
        double height = getBattleFieldHeight();

        // Distance to stop for the wall to prevent collisions;
        int margin = 30;

        if (x <= width / 2) {
            if (y <= height / 2) {
                if (x < y) {
                    goTo(margin, (int) y);
                } else {
                    goTo((int) x, margin);
                }
            } else {
                if (x < height - y) {
                    goTo(margin, (int) y);
                } else {
                    goTo((int) x, (int) height - margin);
                }
            }
        } else {
            if (y <= height / 2) {
                if (y < width - x) {
                    goTo((int) x, margin);
                } else {
                    goTo((int) width - margin, (int) y);
                }
            } else {
                if (height - y < width - x) {
                    goTo((int) x, (int) height - margin);
                } else {
                    goTo((int) width - margin, (int) y);
                }
            }
        }

    }

    // Run: Move around walls
    public void run() {


        // Set colors
        setColors(Color.red, Color.red, Color.red, Color.red, Color.red);
        goToWall();

        do {
            if (getRadarTurnRemaining() == 0.0) {
                setTurnRadarRightRadians(Double.POSITIVE_INFINITY);

            }
            execute();
        } while (true);



    // Robots must move to a wall before doing anything else
    // Radar aiming to corner
    // Gun aiming at the middle
    // Turn radar 180 degree to scan the field
    // Turn gun to the left or right when enemy detected.


}

    // onHitRobot:  Move away a bit.
    public void onHitRobot(HitRobotEvent e) {

    }



    public void onScannedRobot(ScannedRobotEvent e) {
        double angleToEnemy = getHeadingRadians() + e.getBearingRadians();
        double radarTurn = Utils.normalRelativeAngle(angleToEnemy - getRadarHeadingRadians());
        double extraTurn = Math.min(Math.atan(36.0 / e.getDistance()), Rules.RADAR_TURN_RATE_RADIANS);
        radarTurn += (radarTurn < 0 ? -extraTurn : extraTurn);
        setTurnRadarRightRadians(radarTurn);

    }
    // onScannedRobot:  Fire!
//    public void onScannedRobot(ScannedRobotEvent e) {
//        if(e.getBearing() < 0){
//            turnGunLeft(e.getBearing());
//            fire(1);
//        }else{
//            turnGunRight(e.getBearing());
//            fire(1);
//        }

}
