/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightbooking;

/**
 *
 * @author User
 */
public class Timer {
    public static void sleep(int seconds, boolean random) {
        try {
            Thread.sleep((long) ((random ? Math.random() : 1.0D) * seconds * 500));
        } catch (InterruptedException e) {
        }
    }
}

