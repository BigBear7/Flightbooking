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
public class Simulator {

    public static void main(String[] args) {
        // Three well known airports
        Airport arn = new Airport("ARN");  threadFor(arn);
        Airport cph = new Airport("CPH");  threadFor(cph);
        Airport lhr = new Airport("LHR");  threadFor(lhr);

        // We need a menu of food
        Caterer nisse = new Caterer("Nisses Flygmat");

        // One airline for each three sides in a triangle
        Airline sas = new Airline("SAS", "SK", nisse, arn, cph);
        Airline ba = new Airline("British airways", "BA", nisse, cph, lhr);
        Airline ryan = new Airline("Ryanair", "RY", nisse, lhr, arn);
       
        // start airlines, and a Booking desk for each one.
        threadFor(sas);  threadFor(new BookingDesk(sas));
        threadFor(ba);   threadFor(new BookingDesk(ba));
        threadFor(ryan); threadFor(new BookingDesk(ryan));
       
        Airplane p1 = new Airplane("Business Jet1", 10, 0);
        Airplane p2 = new Airplane("Business Jet2", 10, 0);
        Airplane p3 = new Airplane("DC3", 30, 50);
        Airplane p4 = new Airplane("Airbus A380", 50, 150);
        Airplane p5 = new Airplane("Boeing 737", 50, 150);
        threadFor(p1); arn.arrived(p1);
        threadFor(p2); arn.arrived(p2);			// start plane threads		
        threadFor(p3); cph.arrived(p3);			// and tell airports about them
        threadFor(p4); lhr.arrived(p4);
        threadFor(p5); lhr.arrived(p5);

               
        while(true) {
            Timer.sleep(120, false);
            System.out.format("### Total revenue: %d, profit %d\n",sas.getRevenue(), sas.getProfit());
        }
       
}
    
    private static void threadFor(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
