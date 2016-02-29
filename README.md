# Flightbooking
##Project Assignment
A small airline company has just purchased a computer for its new automated
reservations system. You have been asked to develop the new system. You are to write
an application to assign seats on the airline’s airplane. The Customer can choose
between first class and economy class.
The airplane has 10 seats. There is also food service available on the airplane, and the
customer can choose no/yes if he wants to eat food on the airplane.
###Airplane reservation
If the customer chooses first class, your application should assign a seat in the firstclass
section (seats 1–5). If the user chooses economy class, your application should
assign a seat in the economy section (seats 6–10).
A first class ticket costs 20 000kr and an Economy ticket 5000kr.
Your application should never assign a seat that has already been assigned. If there are
no available tickets in the section, ask the customer if he wants to be place in another
section.
###The food service
The customers can reserve food on the airplane. Create a list of food items and assign a
prize for each item. The first class travelers have a separate menu from the economy
travelers. Calculate the food price for each traveler.
###The complete booking system:
Integrate the food reservation and the ticket reservation in the same system and print
the total price for each passenger (food + ticket). Calculate the income for the airline
company by summing up the prices of the ticket and food items for all passengers. Print
the total income for the company.
Assume that 70% of the income is used to cover all the costs for the company. The total
profit for the company’s is then 0.3*income. Print the profit.
###Optional Bonus assignment 1: The airline company wants to expand its
business by adding 2 more airplanes. The new airplanes have 10 seats (5 First class
and 5 Economy class). Change your code so that the airplanes can be used. Make your
code flexible so that planes can be added/removed and modified without breaking the
code.
###Optional Bonus assignment 2: With the 3 airplanes they decided to compete
on the worlds shortest two-way routes that takes 2 minutes to fly. Add threads for
simulating each airplane, when the booking desk has finished the bookings for each
plane they can be sent off. When the plane is sent off simulate and print the takeoff, 
flight, landing and refuel for each airplane. The booking desk should only be able to
book passengers for a plane that is currently waiting at an airport.
