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
public enum AirplaneState {
    FLYING,
    LANDED,                     // just landed, and passengers disembarked
    SERVICED,                   // plane is refuelled and restocked
    READY,                      // ready for takeoff
}
