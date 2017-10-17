cs5530u03
Tanner Martin & Robert Weischedel

To Build and Run:

1. Navigate to folder tannermartin_phase2

2. javac cs5530/*.java

3. In windows system: java -cp "./mysql.jar;." cs5530.MainMenu
   
   In Linux/Unix system: java -cp ./mysql.jar:. cs5530.MainMenu


General Notes:
1. It should first be stated that while entering input into the input fields, the user must use the exact format specified for that particular input. Most of
   the input that is given from a user is not verified before entered into the database from the application. We made this decision because Fei Fei said
   nobody is going to deliberately try to break our application.

2. Listed below is a how to guide, for how to do a few of the intricate commands in our program. It should
   be noted that we rely heavily on the user first searching for the desired id value, whether that be hid,
   fid, etc. before performing an action on that particular id (representing a th, feedback, reservation, etc).
   This id value for a particular item will typically be the first item in the print out result aka column 1.

Assumptions Made:
1. The biggest assumption we made is that we are going to require the user to first browse, search or find the desired
   id value for the action they are going to perform before they attempt to perform an action on that id value, like adding,
   deleting or updating. So please before trying to add, delete, or update an item please look up its particular id value
   first!

2. We made the assumption that once a user has recorded a visit attached with a reservation, they will no longer be allowed
   to remove or alter the reservation or visit.

3. We made the assumption that once a user has determined if someone is trusted or not, that they will not be able to delete
   or change that rating.

4. We made the assumption that once a user has listed a TH as a favorite that we will not allow them to delete or update that
   entry on their favorites list. And as well, that a user doesn't have to have reserved or visited a TH before in order to 
   make that TH a favorite (similar to starring something in the HotPads app).

5. We made the assumption that once a user has left a feedback score for a TH feedback that their score cannot be updated or
   deleted.

6. We made the assumption to make the shopping cart feature for reservation be done one at a time. Or in other words after the
   user had filled out all the information for a reservation, the application will show all the information about that TH and ask the user if 
   they want to reserve this TH. Then a yes or no menu option screen will appear, and if they select yes the reservation
   will be added.

7. We made the assumption that the reservation suggestions will be displayed immediately after a reservation is made, or as 
   stated in assumption 6 above, after the user selects yes if they are sure they want to make this reservation.

How to Guide:
1. How to add a reservation:
   1. Browse TH Menu - Browse for a TH you wish to stay at, and remember its hid value, located in column 1.
   2. Owned TH Availability Menu - Choose option 5 to view all the availabilities for the TH you wish to reserve and
      remember the from and to dates that you wish to stay.
   2. Travel to the Reservation Menu Screen and select menu option number 1.
   3. Enter all the desired information in the appropriate fields. Including the TH hid and the reservation dates.
   4. NOTE: How the queries are set up is that when the user enters the desired dates of the reservation, it is first checked
      against the availabilities that are connected with that particular TH, then if the date falls into a range of the available
      availabilities, then we check those user entered dates with the reservations that have already been made for that TH,
      if there is a issue the reservation is not made, if there is not the reservation is made. Unfortunately due to time
      constraints we didn't put in a query to allow the user to see dates that are already reserved during a THs availability.
      This can make making a reservation a little unintuitive but we just plan on using this as an API to our web interface. 
      It should be restated and assured that a reservation can definitely not be made if there is a conflict with another reservation 
      within that same availability.
   5. If the reservation can be made for that time, it will be added to the database. If not the application will tell you that you
      can't and the reservation will not be added.
