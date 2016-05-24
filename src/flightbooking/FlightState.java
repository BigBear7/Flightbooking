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
public enum FlightState {
    BOOKABLE ("Open"),
    FULL ("Full"),
    FLYING ("In air"),
    LANDED ("Landed");
    
    private final String name;
    FlightState(String name) {
        this.name = name;
    }
}
