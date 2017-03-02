/**
 * Copyright (c) 2001-2014 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package saxion.EHI1VSpq_1;


import robocode.*;
import robocode.robotinterfaces.peer.IBasicRobotPeer;
import robocode.robotinterfaces.peer.ITeamRobotPeer;
import sampleteam.Point;

import java.io.IOException;
import java.io.Serializable;


/**
 * MyFirstRobot - a sample robot by Mathew Nelson.
 * <p/>
 * Moves in a seesaw motion, and spins the gun around at each end.
 *
 * @author Mathew A. Nelson (original)
 */
/**
 * This robot can avoid wall to a certain distance
 */
public class MyFirstRobot extends TeamRobot {
    private int wallMargin = 60;
    private byte moveDirection = 1;
    IBasicRobotPeer peer;

    public void run() {
        while (true) {
            setTurnRadarRight(10000);
            ahead(100);
            back(100);
        }
    }

    public void broadcastMessage(Serializable message) throws IOException {
        if(this.peer != null) {
            ((ITeamRobotPeer) this.peer).broadcastMessage(message);
        }
    }

    /**
     * onScannedRobot:  What to do when you see another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        // Don't fire on teammates
        if (isTeammate(e.getName())) {
            return;
        }
        // Calculate enemy bearing
        double enemyBearing = this.getHeading() + e.getBearing();
        // Calculate enemy's position
        double enemyX = getX() + e.getDistance() * Math.sin(Math.toRadians(enemyBearing));
        double enemyY = getY() + e.getDistance() * Math.cos(Math.toRadians(enemyBearing));

        try {
            // Send enemy position to teammates
            broadcastMessage(new Position(enemyX,enemyY));
        } catch (IOException ex) {
            out.println("Unable to send order: ");
            ex.printStackTrace(out);
        }
    }
}


