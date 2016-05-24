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
public class Booking {
    private final Customer customer;
    private Seat seat;
    private ArrayList<Food> food;    
    boolean confirmed;
    boolean cancelled;
    int amountPaid;
    
    Booking(Customer customer) {
        this.customer = customer;
        this.seat = null;
        this.food = new ArrayList<>();
        this.confirmed = false;
        this.cancelled = false;
        this.amountPaid = 0;
    }

    public void addSeat(Seat seat) {
        this.seat = seat;
    }
    
    public void addProduct(Food product) {
        assert(!confirmed);
        assert(!cancelled);        
        food.add(product);
    }
    
    public void confirm() {
        assert(!cancelled);
        amountPaid = calculateTotalAmount();
        customer.addPurchase(amountPaid);
        confirmed = true;
    }
    
    public void cancel() {
        assert(!confirmed);
        cancelled = true;
    }
    
    private int calculateTotalAmount() {
        int amount = 0;
        amount += seat.getPrice();
        for (Food product : food) {
            amount += product.getPrice();
        }
        return (int) (amount * customer.getDiscountFactor());
    }
    
    public int getAmountPaid() {
        // To be called for statistics only.
        assert(confirmed);
        return amountPaid;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
    
    public Seat getSeat() {
        return seat;
    }

    public ArrayList<Food> getFoodProducts() {
        return food;
    }
}
