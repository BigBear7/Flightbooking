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
public class Flight {
    private final String flightNo;
    private final Airport src;
    private final Airport dest;
    private final Airplane plane;
    private FlightState state;
    private final ArrayList<Booking> bookings;
    private final ArrayList<Seat> seats;
    private final ArrayList<Food> menu;
    private int seatLabel;
    private int flightRevenue;
    
    // It is a requirement to assign a plane to the flight before using Flight object.
    // (Unsafe for constructor to do this - would leak this reference before ready.)
    //      Airplane plane; 
    //      Flight flight = new Flight(flightNo, plane, src,  dest);
    //      plane.assignToFlight(flight);
    Flight(String flightNo, Airplane plane, Caterer caterer, Airport src, Airport dest) {
        this.flightNo = flightNo;
        this.src = src;
        this.dest = dest;
        this.plane = plane;
        this.state = FlightState.BOOKABLE;
        this.bookings = new ArrayList<>();
        this.seats = new ArrayList<>();
        this.menu = caterer.getMenu();
        this.seatLabel = 1;         // first seat label number
        this.flightRevenue = 0;     // calculated before takeoff.
        createSeats(false);
        createSeats(true);
    }
            
    public synchronized Booking createBooking(Customer customer, boolean businessClass) {
        for (Seat seat : seats) {
            if (seat.isBusinessClass() == businessClass) {
                Booking b = new Booking(customer);
                seats.remove(seat);   // remove from bookable seats
                b.addSeat(seat);      // add seat to booking
                bookings.add(b);      // add booking to our list
                return b;
            }
        }
        return null;
    }
    
    public synchronized boolean confirmBooking(Booking booking) {
        if (bookings.contains(booking)) {
            booking.confirm();
            checkIfFull();
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean cancelBooking(Booking booking) {
        if (bookings.contains(booking)) {
            seats.add(booking.getSeat());
            bookings.remove(booking);
            booking.cancel();
            return true;
        } else {
            return false;
        }
    }
    
    public ArrayList<Food> getMenu(boolean business) {
        ArrayList<Food> menuForCategory = new ArrayList<>();
        for (Food foodProduct : menu) {
            if (foodProduct.isBusinessClass() == business) {
                menuForCategory.add(foodProduct);
            }
        }
        return menuForCategory;
    }
    
    public String getFlightInfo() {
        switch (state) {
            case FULL:
                return String.format("%s is closed for booking.", flightNo);
            case FLYING:
                return String.format("%s has taken off.", flightNo);
            case LANDED:
                return String.format("%s landed at %s airport", flightNo, dest.getName());
            case BOOKABLE:
                return String.format("%s with %s from %s to %s. Business costs %d (%d free), Economy %d (%d free)",
                        flightNo,
                        plane.getPlaneID(),
                        src.getName(),
                        dest.getName(),
                        Seat.BUSINESSPRICE,
                        getFreeSeats(true),
                        Seat.ECONOMYPRICE,
                        getFreeSeats(false));
            default:
                return String.format("Bad FlightState: %s)", state.toString());
        }
    }
    
    public String getFlightNo() {
        return flightNo;
    }
    
    public Airport destination() {
        return dest;
    }
    
    public Airport source() {
        return src;
    }

    public boolean isBookable() {
        return state == FlightState.BOOKABLE;
    }
    
    public boolean hasLanded() {
        return state == FlightState.LANDED;
    }
    
    public boolean isFull() {
        return state == FlightState.FULL;
    }

    public void takeOff() {
        assert(state == FlightState.FULL);
        state = FlightState.FLYING;
        plane.takeoff();
    }
    
    public void setLanded() {
        assert(state == FlightState.FLYING);
        state = FlightState.LANDED;
    }
        
    public int getRevenue() {
        assert(state != FlightState.BOOKABLE);
        return flightRevenue;
    }
    
    private void createSeats(boolean businessClass) {
        int numberOfSeats = (businessClass? plane.getBusinessSeats() : plane.getEconomySeats());
        while (numberOfSeats > 0) {
            String seatName = Integer.toString(seatLabel++);
            seats.add(new Seat(seatName,businessClass));
            numberOfSeats--;
        }
    }
    
    private int getFreeSeats(boolean business) {
        int seatCount = 0;
        for (Seat seat : seats) {
            if (seat.isBusinessClass() == business) {
                seatCount++;
            }
        }
        return seatCount;
    }
    
    private boolean checkIfFull() {
        if (getFreeSeats(true) != 0 || getFreeSeats(false) != 0) {
            return false;
        } 
        for (Booking b : bookings) {
            if (b.isConfirmed() == false) {
                return false;
            }
        }
        state = FlightState.FULL;
        plane.setFullyBooked();
        flightRevenue = calculateTotalRevenue();
        return true;
    }
    
    private int calculateTotalRevenue() {
        int amount = 0;
        for (Booking b : bookings) {
            amount += b.getAmountPaid();
        }
        return amount;
    }
}
