/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightbooking;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author User
 */
public class MainBook {

    // Testing enums
    enum CmdSet {
        EN("Enter name"),
        BAF("Book a flight"),
        BU("Business"),
        EC("Economy"),
        BF("Book food"),
        C("Continue"),
        Q("Quit");

        private final String name;

        private CmdSet(String s) {
            name = s;
        }
    }

    static <T extends Enum<T>> T oneOf(T[] options) {
        options[0].name();
        return options[0];
    }

    static CmdSet getCommand(String message, Set<CmdSet> list) {
        Scanner user_input = new Scanner(System.in, "ISO-8859-1");
        String answer;
        // Parse command input
        do {
            System.out.print(message);
            answer = user_input.nextLine();
            for (CmdSet a_cmd : list) {
                if (answer.equalsIgnoreCase(a_cmd.name())) {
//                    System.out.println("DEBUG: " + a_cmd.name());
                    return a_cmd;
                }
            }
            System.out.println(answer + " is a wrong choice, try again!");
        } while (answer.toUpperCase().compareTo("Q") != 0);
        return null;
    }

    static int getNumber(String message, int max) {
        System.out.print(message);
        int number = 0;
        do {
            // Check if number within range
            try {
                Scanner scan = new Scanner(System.in, "ISO-8859-1");
                number = scan.nextInt();
                if (number >= 1 && number <= max) {
                    // Number found, leave loop
                    break;
                }
                System.out.println("Enter a number between 1 and " + max);
            } catch (InputMismatchException e) {
                System.out.print("Only numbers allowed, try again: ");
            }

        } while (true);
        return number;
    }

    public static void main(String[] args) {

        Scanner user_input = new Scanner(System.in, "ISO-8859-1");
        String answer = "";
        int item = 0;
        boolean finished = false;

        Set<CmdSet> cmd_start = EnumSet.of(CmdSet.EN, CmdSet.Q);
        Set<CmdSet> cmd_book = EnumSet.of(CmdSet.BAF, CmdSet.C);
        Set<CmdSet> cmd_busec = EnumSet.of(CmdSet.BU, CmdSet.EC);
        Set<CmdSet> cmd_food = EnumSet.of(CmdSet.BF, CmdSet.C);

        CmdSet cmd = CmdSet.EN;

        // Init system
        Airport airp_home = new Airport("ARN");
        Airport airp_other = new Airport("BRA");
        Caterer caterer = new Caterer("Lisas krubby");
        Airline sas = new Airline("Scandinavian Airline Service", "SAS", caterer, airp_home, airp_other);
        Airplane plane1 = new Airplane("Boing 767", 10, 20);
        Airplane plane2 = new Airplane("JAS 39", 1, 1);
        Airplane plane3 = new Airplane("Airbus", 10, 7);
        airp_home.arrived(plane1);
        airp_home.arrived(plane2);
        airp_home.servicePlane();
        airp_other.arrived(plane3);
        airp_other.servicePlane();
        sas.createFlight(true);
        sas.createFlight(true);
        sas.createFlight(false);
        ArrayList<Flight> flights = sas.getBookableFlights();

        while (!finished) {
            cmd = getCommand("\nWelcome to Lexicon airlines!\n(EN)Enter your Name, (Q)uit: ", cmd_start);
            switch (cmd) {
                case EN:
                    do {
                        System.out.print("Name: ");
                        answer = user_input.nextLine();
                    } while (answer.isEmpty());
                    Customer cust = Customer.getCustomer(answer);
                    System.out.println("Welcome " + answer);

                    // Book a flight
                    cmd = getCommand("(BAF)Book a flight, (C)Continue: ", cmd_book);
                    if (cmd == CmdSet.C) {
                        break;
                    }
                    for (int i = 1; i <= flights.size(); i++) {
                        System.out.println(i + ": " + flights.get(i - 1).getFlightNo());
                    }
                    item = getNumber("Select a flight: ", flights.size());
                    Flight flight = sas.getFlight(flights.get(item - 1).getFlightNo());
                    cmd = getCommand("(BU)Business or (EC)Economy: ", cmd_busec);
                    boolean book_bc = false;
                    if (cmd == CmdSet.BU) {
                        book_bc = true;
                    }
                    Booking booking = flight.createBooking(cust, book_bc);
                    if (booking == null) {
                        System.out.println("No more seats in " + flight.getFlightNo());
                        break;
                    }

                    // Book some food
                    while (true) {
                        cmd = getCommand("(BF)Book food or (C)Continue: ", cmd_food);
                        if (cmd == CmdSet.C) {
                            break;
                        }
                        ArrayList<Food> menu = flight.getMenu(book_bc);
                        for (int i = 1; i <= menu.size(); i++) {
                            System.out.println(i + ": " + menu.get(i - 1).getInfo());
                        }
                        item = getNumber("Select a course: ", menu.size());
                        booking.addProduct(menu.get(item - 1));
                    }
                    flight.confirmBooking(booking);

                    // Print out booking and customer info
                    String bc_or_not = book_bc ? "business" : "economy";
                    System.out.format("\nBooking: %s for flight %s in %s class, seat %s\n", cust.getName(), flight.getFlightNo(), bc_or_not, booking.getSeat().getName());
                    for (Food food : booking.getFoodProducts()) {
                        System.out.println("Food: " + food.getName());
                    }
                    System.out.println("Price: " + booking.getAmountPaid());
                    int points = cust.getBonusPoints();
                    String level = cust.getBonusLevelName();
                    int pointsToNext = cust.pointsToNextLevel();
                    System.out.format("Total bonus points %d, Bonus level %s, only %d points to next level\n", cust.getBonusPoints(), cust.getBonusLevelName(), cust.pointsToNextLevel());
                    break;
                case Q:
                    System.out.println("Thanks for using our airline :)");
                    System.out.println("*******************************\n");
                    finished = true;
                    break;
            }
        }
    }
}
