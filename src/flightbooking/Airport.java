/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightbooking;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Airport implements Runnable {

    private final String name;
    private final ArrayList<Airplane> planes;         // planes on ground, not yet chartered.
    private final String threadName;
   

    Airport(String name) {
        this.name = name;
        planes = new ArrayList<>();
        threadName = name;
        System.out.println("Creating airport " +  threadName );
    }

    public synchronized void arrived(Airplane plane) {
        planes.add(plane);
    }

    public synchronized Airplane charterAvailablePlane() {
        for (Airplane plane : planes) {
            if (plane.isServiced()) {
                // check plane is not assigned to flight already?
            return planes.remove(0);
            }
        }
        return null;
    }

    public synchronized void servicePlane() {
        for (Airplane plane : planes) {
            if (plane.isLanded()) {
                plane.groundService();
                System.out.println("                                                        " + name + " serviced " + plane.getPlaneID());
                return;
            }
        }
    }
   
    @Override
    public void run() {
        while (true) {
            Timer.sleep(15, true);
            servicePlane();
        }
    }

    public String getName() {
        return name;
    }
}
