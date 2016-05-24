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
public class Seat extends Product {
    
    public static final int BUSINESSPRICE = 20_000;
    public static final int ECONOMYPRICE = 5_000;
    
    public Seat(String name, boolean business) {
        super(name, (business? BUSINESSPRICE: ECONOMYPRICE), business);
    }
}
