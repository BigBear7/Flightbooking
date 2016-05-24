/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightbooking;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author User
 */
public class Caterer {
    private final String name;
    private ArrayList<Food> menu;
    
    Caterer(String name) {
        this.name = name;
        this.menu = new ArrayList<>();
        createMenu();
    }
    
    public ArrayList<Food> getMenu() {
        return menu;
    }
    
    private void createMenu() {
        // economy
        menu.add(new Food("Coca cola", 20, false));
        menu.add(new Food("Fanta", 20, false));
        menu.add(new Food("Plastic sandwich", 50, false));
        menu.add(new Food("Chips", 25, false));
        menu.add(new Food("Tasteless burger", 75, false));
        //business
        menu.add(new Food("Champagne 37cl", 140, true));
        menu.add(new Food("Chateau d'Yquem 15cl", 200, true));
        menu.add(new Food("Smoked salmon", 90, true));
        menu.add(new Food("Escargots (1 dozen)", 150, true));
        menu.add(new Food("Goose liver / Foie gras", 170, true));
    }
}
