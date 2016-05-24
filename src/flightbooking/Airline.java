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
public class Airline implements Runnable {

    private final String name;
    private final String prefix;
    private final Caterer caterer;
    private final Airport home;
    private final Airport other;
    private final ArrayList<Flight> flights;
    private static final double PROFITFACTOR = 0.3;
    private static int lastFlightId = 100;

    Airline(String name, String prefix, Caterer caterer, Airport home, Airport other) {
        this.name = name;
        this.prefix = prefix;
        this.caterer = caterer;
        this.home = home;
        this.other = other;
        this.flights = new ArrayList<>();
    }

    @Override
    public void run() {
        boolean outbound = false;
        while (true) {
            createFlight(outbound);     // try to create flight.
            outbound = !outbound;       // toggle direction
            Timer.sleep(10, true);
        }
    }    

    // Airline main function
    public boolean createFlight(boolean outBound) {
        Airport src, dest;
        src = (outBound ? home : other);
        dest = (outBound ? other : home);
        Airplane plane = src.charterAvailablePlane();
        return createFlight(plane, src, dest);
    }

    // For booking desk
    public synchronized ArrayList<Flight> getBookableFlights() {
        ArrayList<Flight> bookableFlights = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.isBookable()) {
                bookableFlights.add(flight);
            }
        }
        return bookableFlights;
    }

    public String getName() {
        return name;
    }
    
    // For manual booking desk
    public synchronized Flight getFlight(String flightNo) {
        for (Flight flight : flights) {
            if (flight.getFlightNo().equalsIgnoreCase(flightNo)) {
                return flight;
            }
        }
        return null;
    }

    
    private synchronized String createFlightNo() {
        return prefix + Integer.toString(lastFlightId++);   // create unique
    }

    private synchronized boolean createFlight(Airplane plane, Airport src, Airport dest) {
        if (plane == null)
            return false;
        Flight flight = new Flight(createFlightNo(), plane, caterer, src, dest);
        plane.assignToFlight(flight);
        flights.add(flight);
        System.out.format("%s New flight with %s from %s to %s\n", flight.getFlightNo(), plane.getPlaneID(), src.getName(), dest.getName());
        return true;
    }

    private synchronized ArrayList<Flight> getDoneFlights() {
        ArrayList<Flight> doneFlights = new ArrayList<>();
        for (Flight flight : flights) {
            if (!flight.isBookable()) {
                doneFlights.add(flight);
            }
        }
        return doneFlights;
    }

    // For admins
    public int getProfit() {
        return (int) (getRevenue() * PROFITFACTOR);
    }

    // For admins
    public int getProfit(String flightNo) {
        return (int) (getRevenue(flightNo) * PROFITFACTOR);
    }

    // For admins
    public synchronized int getRevenue() {
        int revenue = 0;
        ArrayList<Flight> doneFlights = getDoneFlights();
        for (Flight flight : doneFlights) {
            revenue += flight.getRevenue();
        }
        return revenue;
    }

    // For admins
    public int getRevenue(String flightNo) {
        Flight flight = getFlight(flightNo);
        if (flight == null) {
            return -1;
        }
        return flight.getRevenue();
    }
}
