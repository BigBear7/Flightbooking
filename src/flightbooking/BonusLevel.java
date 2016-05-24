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
public enum BonusLevel {
    BASIC ("Basic", 0, 1.0),
    SILVER ("Silver", 40_000, 0.9),
    GOLD ("Gold", 80_000, 0.8);
    
    private final String levelName;
    private final int pointsRequired;
    private final double discountFactor;
    
    BonusLevel(String name, int points, double factor) {
        levelName = name;
        pointsRequired = points;
        discountFactor = factor;
    }
    
    public String getName() {
        return levelName;
    }
    public int getLimit() {
        return pointsRequired;
    }
    public double discountFactor() {
        return discountFactor;
    }
    
    public static BonusLevel getLevelFor(int bonusPoints) {
        if (bonusPoints < SILVER.pointsRequired) {
            return BASIC;
        } else if (bonusPoints < GOLD.pointsRequired) {
            return SILVER;
        } else {
            return GOLD;
        }
    }
    public int pointsToNextLevel(int bonusPoints) {
        if (this == BASIC) {
            return SILVER.pointsRequired - bonusPoints;
        } else if (this == SILVER) {
            return GOLD.pointsRequired - bonusPoints;            
        } else {
            return 0;
        }
    }
}
