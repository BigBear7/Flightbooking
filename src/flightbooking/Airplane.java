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
public class Airplane implements Runnable {

    private final String planeID;
    private final int businessSeats;
    private final int economySeats;
    private AirplaneState state;
    private Flight assignedFlight;

    Airplane(String planeID, int businessSeats, int economySeats) {
        assert (businessSeats >= 0);
        assert (economySeats >= 0);
        this.planeID = planeID;
        this.state = AirplaneState.LANDED;
        this.businessSeats = businessSeats;
        this.economySeats = economySeats;
        this.assignedFlight = null;
        System.out.println("Creating plane " +  this.planeID);
    }

    public String getPlaneID() {
        return planeID;
    }

    public void takeoff() {
        assert (state == AirplaneState.READY);
        state = AirplaneState.FLYING;
        System.out.println(assignedFlight.getFlightNo() + " Takeoff from " + assignedFlight.source().getName() + " with " + planeID +  " bound for " + assignedFlight.destination().getName());        
    }

    public void land(Airport airport) {
        assert (state == AirplaneState.FLYING);
        state = AirplaneState.LANDED;
        System.out.println(assignedFlight.getFlightNo() + " Landed on " + assignedFlight.destination().getName());
        assignedFlight.setLanded();
        assignedFlight = null;
        airport.arrived(this);
    }

    public void groundService() {
        assert (state == AirplaneState.LANDED);
        state = AirplaneState.SERVICED;
        // includes cleaning, refuel.
    }

    public void setFullyBooked() {
        assert(state == AirplaneState.SERVICED);
        state = AirplaneState.READY;
    }

    public void assignToFlight(Flight flight) {
        assert (state == AirplaneState.SERVICED);
        assert (assignedFlight == null);
        assignedFlight = flight;
    }

    public boolean isLanded() {
        return state == AirplaneState.LANDED;
    }

    public boolean isServiced() {
        return state == AirplaneState.SERVICED;
    }
    
    public boolean isReady() {
        return state == AirplaneState.READY;
    }

    public int getBusinessSeats() {
        return businessSeats;
    }

    public int getEconomySeats() {
        return economySeats;
    }

    @Override
    public void run() {
        while (true) {
            Timer.sleep(10, true);
            if (isReady()) {
                assert(assignedFlight != null);
                takeoff();
                Timer.sleep(20, false); // fly for N seconds
                land(assignedFlight.destination());
            }
        }
    }
}
