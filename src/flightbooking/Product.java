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
public class Product {
    protected final String name;
    protected final int price;
    protected final boolean businessClass;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public boolean isBusinessClass() {
        return businessClass;
    }

    public Product(String name, int price, boolean businessClass) {
        this.name = name;
        this.price = price;
        this.businessClass = businessClass;
    }
    
    public String getInfo() {
        return String.format("%-25s Price: %5d", name, price);
    }
}
