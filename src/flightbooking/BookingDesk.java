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
public class BookingDesk implements Runnable {

    private final Airline airline;

    BookingDesk(Airline airline) {
        this.airline = airline;
    }

    public void bookAFlight() {
        ArrayList<Flight> flights = airline.getBookableFlights();
        if (flights.isEmpty()) {
            return;
        }
        Flight flight = flights.get(0);
        Customer customer = Customer.getCustomer(aCustomer());
        boolean business = maybe();
        Booking booking = flight.createBooking(customer, business);
        if (booking == null) {
            // System.out.format("#No more %s seats in %s\n", (business? "business" : "economy"), flight.getFlightNo());
            return;        
        } 
        ArrayList<Food> menu = flight.getMenu(business);
        booking.addProduct(menu.get(0));
        booking.addProduct(menu.get(1));
        booking.addProduct(menu.get(2));
        flight.confirmBooking(booking);
        // System.out.format("#Booked seat %s on flight %s\n", booking.getSeat().getName(), flight.getFlightNo());
    }

    @Override
    public void run() {
        System.out.println("Booking desk for " + airline.getName());
        while (true) {
            Timer.sleep(1, true);
            bookAFlight();
        }
    }
    
    private boolean maybe() {
        return Math.random() > 0.5D;
    }
    
    private String aCustomer() {
        switch((int) (Math.random()*10)) {
            case 0: return "Ulf"; 
            case 1: return "Lars";
            case 2: return "Björn";
            case 3: return "Nina";
            case 4: return "Anna";
            case 5: return "Bita";
            case 6: return "Stina";
            case 7: return "Mattias";
            case 8: return "Jonas";
            case 9: return "Rutger";
            default: return "Someone";
        }
    }
}
