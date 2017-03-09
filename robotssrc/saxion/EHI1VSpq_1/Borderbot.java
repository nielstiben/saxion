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

        if (x <= width / 2) {
            if (y <= height / 2) {
                if (x < y) {
                    goTo(0, (int) y);
                } else {
                    goTo((int) x, 0);
                }
            } else {
                if (x < height - y) {
                    goTo(0, (int) y);
                } else {
                    goTo((int) x, (int) height);
                }
            }
        } else {
            if (y <= height / 2) {
                if (y < width - x) {
                    goTo((int) x, 0);
                } else {
                    goTo((int) width, (int) y);
                }
            } else {
                if (height - y < width - x) {
                    goTo((int) x, (int) height);
                } else {
                    goTo((int) width, (int) y);
                }
            }
        }

    }

    // Run: Move around walls
    public void run() {
        // Set colors
        setBodyColor(Color.red);
        setGunColor(Color.red);
        setRadarColor(Color.red);
        setBulletColor(Color.red);
        setScanColor(Color.red);

        // Robots must move to a wall before doing anything else
        goToWall();
        // Radar aiming to corner
        setMaxVelocity(1);
        // Gun aiming at the middle

        // Turn radar 180 degree to scan the field
        // Turn gun to the left or right when enemy detected.


        while (true) {
            turnRadarRight(270);
        }


    }

    // onHitRobot:  Move away a bit.
    public void onHitRobot(HitRobotEvent e) {

    }

    // onScannedRobot:  Fire!
    public void onScannedRobot(ScannedRobotEvent e) {
        if(e.getBearing() < 0){
            turnGunLeft(e.getBearing());
            fire(1);
        }else{
            turnGunRight(e.getBearing());
            fire(1);
        }

    }
}
