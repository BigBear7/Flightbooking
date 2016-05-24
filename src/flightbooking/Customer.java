/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightbooking;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author User
 */
public class Customer {

    private static ConcurrentHashMap<String, Customer> customers;
    private final String id;
    private int bonusPoints;
    private BonusLevel level;
    
    // Let customer class keep customer list
    public static Customer getCustomer(String id) {
        assert (!id.isEmpty());
        if (customers == null) {
            customers = new ConcurrentHashMap<>();
        }
        Customer customer = customers.get(id);
        if (customer == null) {
            customer = new Customer(id);
            customers.put(id, customer);
        }
        return customer;
    }
    
    Customer(String id) {
        this.id = id;
        this.bonusPoints = 0;
        this.level = BonusLevel.BASIC;
    }

    public String getName() {
        return id;
    }
    
    // after completed purchase, store amount as bonus points.
    public void addPurchase(int amount) {
        bonusPoints += amount;
        adjustBonusLevel();
    }

    public double getDiscountFactor() {
        return level.discountFactor();
    }

    public int getBonusPoints() {
        return bonusPoints;
    }
    
    public int pointsToNextLevel() {
        return level.pointsToNextLevel(bonusPoints);
    }
    
    public String getBonusLevelName() {
        return level.name();
    }
    
    private void adjustBonusLevel() {
        BonusLevel newLevel = BonusLevel.getLevelFor(bonusPoints);
        if (level != newLevel) {
            level = newLevel;
            System.out.println("                                                                                 " + id + " is now a " + level.toString() + " customer!");
        }
    }
}
