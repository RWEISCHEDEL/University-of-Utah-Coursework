package cs5530;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainMenu {

	public static String sessionUserName = "";

	public static void main(String[] args) {

		Connector connection = null;
		String uName = null;
		String uPass = null;
		String uType = null;
		String uInput;
		int choiceValue = 0;

		try {

			connection = new Connector();
			System.out.println("Database connection established");

			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

			while (true) {

				mainScreen();

				while ((uInput = input.readLine()) == null && uInput.length() == 0);

				try {
					choiceValue = Integer.parseInt(uInput);
				} catch (Exception e) {
					continue;
				}

				if (choiceValue < 1 || choiceValue > 3) {
					continue;
				}

				if (choiceValue == 1) {

					String[] uData = loginScreen(uName, uPass, connection);
					uName = uData[0];
					uType = uData[1];

					sessionUserName = uName;

					if (uType.equals("1")) {
						adminScreen(connection);
					} else if (uType.equals("uNameInvalid")) {
						System.out.println("User Login Name does not exist.\n");
						continue;
					} else if (uType.equals("uPassInvalid")) {
						System.out.println("Password did not match user name.");
					} else {
						System.out.println("ACCESS USER MENU");
						userMenu(connection);
					}

				} else if (choiceValue == 2) {

					boolean transactionSuccess = registerScreen(connection);

					if (transactionSuccess) {
						System.out.println("You have successfully registered a new account.");
					} else {
						System.out.println("Unable to successfully register new account.\n");
					}
				} else {
					System.out.println("\nExiting Uotel");
					connection.stmt.close();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Connection error or query execution error.");
		} finally {
			if (connection != null) {
				try {
					connection.closeConnection();
					System.out.println("Database connection terminated.");
				} catch (Exception e) {
				}
			}
		}
	}

	private static void mainScreen() {
		System.out.println("\nWelcome to Uotel");
		System.out.println("1. Login User");
		System.out.println("2. New User");
		System.out.println("3. Exit\n");
		System.out.println("Please enter your choice:");
	}

	private static String[] loginScreen(String uName, String uPass, Connector connection) throws Exception {

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("\nLogin:");
		System.out.println("Enter your username:");

		while ((uName = input.readLine()) == null && uName.length() == 0);

		System.out.println("Enter your password:");

		while ((uPass = input.readLine()) == null && uPass.length() == 0);

		LoginUser login = new LoginUser();

		String type = login.validateUserLogin(uName, uPass, connection.stmt);

		String[] uData = { uName, type };

		return uData;
	}

	private static boolean registerScreen(Connector connection) throws Exception {

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		String uLogin, fullName, uPass, address, phoneNum, uType;

		while (true) {

			System.out.println("\nRegistration:");

			System.out.println("Enter your desired login:");
			while ((uLogin = input.readLine()) == null && uLogin.length() == 0);

			System.out.println("Enter your First and Last Name:");
			while ((fullName = input.readLine()) == null && fullName.length() == 0);

			System.out.println("Enter your password:");
			while ((uPass = input.readLine()) == null && uPass.length() == 0);

			System.out.println("Enter your full address. Example: 123 Uotel Street Midvale UT 84047");
			System.out.println("Ensure that you enter the state as it's two letter abreviation. Example: Utah = UT");
			while ((address = input.readLine()) == null && address.length() == 0);

			System.out.println("Enter your telephone with no spaces, or other characters. Example: 8011234567:");
			while ((phoneNum = input.readLine()) == null && phoneNum.length() == 0);

			System.out.println("Enter your user type as 0 or 1. Example: admin (1) or user (0) (Be HONEST!)");
			while ((uType = input.readLine()) == null && uType.length() == 0);

			RegisterUser register = new RegisterUser();

			boolean transactionSuccess = register.registerUser(uLogin, fullName, uPass, address, phoneNum, uType, connection.stmt);

			if (transactionSuccess) {
				return true;
			} else {
				return false;
			}
		}
	}

	private static void adminScreen(Connector connection) throws Exception {

		int uInput;
		String choiceValue = "";
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		// TODO: JUMP HERE
		while (true) {
			System.out.println("\nAdmin Menu");
			System.out.println("1. View Owned THs");
			System.out.println("2. Add New Owned THs");
			System.out.println("3. Update Owned THs");
			System.out.println("4. Delete Owned THs");
			System.out.println("5. Browse THs");
			System.out.println("6. Reservations");
			System.out.println("7. TH Availability");
			System.out.println("8. Visits");
			System.out.println("9. List Favorites");
			System.out.println("10. Add Favorites");
			System.out.println("11. Trust");
			System.out.println("12. Statistics");
			System.out.println("13. Show degrees of separation");
			System.out.println("14. Feedback");
			System.out.println("15. Awards");
			System.out.println("16: Logout\n");
			System.out.println("Please enter your choice:");

			while ((choiceValue = input.readLine()) == null && choiceValue.length() == 0);
			try {
				uInput = Integer.parseInt(choiceValue);
			} catch (Exception e) {
				continue;
			}

			if (uInput == 1) {
				ownedTHScreen(connection);
			} 
			else if (uInput == 2) {
				newTHScreen(connection);
			} 
			else if (uInput == 3) {
				updateTHScreen(connection);
			} 
			else if (uInput == 4) {
				deleteTHScreen(connection);
			} 
			else if (uInput == 5) {
				browseTHScreen(connection);
			} 
			else if (uInput == 6) {
				reserveTHScreen(connection);
			} 
			else if (uInput == 7) {
				updateOwnedTHAvailScreen(connection);
			}
			else if (uInput == 8) {
				recordVisitScreen(connection);
			}
			else if (uInput == 9) {
				viewFavoritesScreen(connection);
			} 
			else if (uInput == 10) {
				addFavoritesScreen(connection);
			}
			else if (uInput == 11) {
				trustScreen(connection);
			}
			else if (uInput == 12) {
				statisticsScreen(connection);
			}
			else if (uInput == 13) {
				seperationScreen(connection);
			}
			else if (uInput == 14) {
				feedbackScreen(connection);
			}
			else if (uInput == 15) {
				awardsScreen(connection);
			}
			else {
				break;
			}

		}
	}
	
	private static void userMenu(Connector connection) throws Exception{
		int uInput;
		String choiceValue = "";
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		// TODO: JUMP HERE
		while (true) {
			System.out.println("\nUser Menu");
			System.out.println("1. View Owned THs");
			System.out.println("2. Add New Owned THs");
			System.out.println("3. Update Owned THs");
			System.out.println("4. Delete Owned THs");
			System.out.println("5. Browse THs");
			System.out.println("6. Reserve THs");
			System.out.println("7. TH Availability");
			System.out.println("8. Visits");
			System.out.println("9. List Favorites");
			System.out.println("10. Add Favorites");
			System.out.println("11. Trust");
			System.out.println("12. Statistics");
			System.out.println("13. Show degrees of separation");
			System.out.println("14. Feedback");
			System.out.println("15: Logout\n");
			System.out.println("Please enter your choice:");

			while ((choiceValue = input.readLine()) == null && choiceValue.length() == 0);
			try {
				uInput = Integer.parseInt(choiceValue);
			} catch (Exception e) {
				continue;
			}

			if (uInput == 1) {
				ownedTHScreen(connection);
			} 
			else if (uInput == 2) {
				newTHScreen(connection);
			} 
			else if (uInput == 3) {
				updateTHScreen(connection);
			} 
			else if (uInput == 4) {
				deleteTHScreen(connection);
			} 
			else if (uInput == 5) {
				browseTHScreen(connection);
			} 
			else if (uInput == 6) {
				reserveTHScreen(connection);
			} 
			else if (uInput == 7) {
				updateOwnedTHAvailScreen(connection);
			}
			else if (uInput == 8) {
				recordVisitScreen(connection);
			}
			else if (uInput == 9) {
				viewFavoritesScreen(connection);
			} 
			else if (uInput == 10) {
				addFavoritesScreen(connection);
			}
			else if (uInput == 11) {
				trustScreen(connection);
			}
			else if (uInput == 12) {
				statisticsScreen(connection);
			}
			else if (uInput == 13) {
				seperationScreen(connection);
			}
			else if (uInput == 14) {
				feedbackScreen(connection);
			}
			else {
				break;
			}

		}
	}

	private static void ownedTHScreen(Connector connection) throws Exception {

		System.out.println("List of All Owned TH");

		TH th = new TH();

		System.out.println(th.ownedTH(sessionUserName, connection.stmt));
	}

	private static void newTHScreen(Connector connection) throws Exception {

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		String hName, hAddress, url, hPhone, hYear, category;

		while (true) {

			System.out.println("Add New TH");

			System.out.println("Enter TH Name:");
			while ((hName = input.readLine()) == null && hName.length() == 0);

			System.out.println("Enter TH full address. Example: 123 Uotel Street Midvale UT 84047");
			System.out.println("Ensure that you enter the state as it's two letter abreviation. Example: Utah = UT");
			while ((hAddress = input.readLine()) == null && hAddress.length() == 0);

			System.out.println("Enter TH URL Example: www.thdomain.com");
			while ((url = input.readLine()) == null && url.length() == 0);

			System.out.println("Enter TH telephone with no spaces, or other characters. Example: 8011234567:");
			while ((hPhone = input.readLine()) == null && hPhone.length() == 0);

			System.out.println("Enter year that TH was built. Example: 2017");
			while ((hYear = input.readLine()) == null && hYear.length() == 0);

			System.out.println("Enter single word for the TH category. Example: House, Apartment, Condo, ...");
			while ((category = input.readLine()) == null && category.length() == 0);

			TH th = new TH();

			if (th.newTH(sessionUserName, category, url, hAddress, hName, hPhone, hYear, connection.stmt)) {
				System.out.println("New TH added.\n");
				break;
			} else {
				System.out.println("New TH not added.\n");
				break;
			}
		}
	}

	private static void updateTHScreen(Connector connection) throws IOException {

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		String updateChoice = null;
		String hid = null;
		String uInput = null;
		String updateInfo = null;

		int choiceValue = 0;

		while (true) {

			System.out.println("Update Existing TH");
			TH th1 = new TH();
			System.out.println("Your THs:");
			System.out.println(th1.ownedTH(sessionUserName, connection.stmt));
			System.out.println("You can get the hid from viewing your owned TH, column 1.");
			System.out.println("Enter TH hid to update:");

			while ((hid = input.readLine()) == null && hid.length() == 0);

			System.out.println("\nChoose field to update:");
			System.out.println("1. TH Category");
			System.out.println("2. TH Url");
			System.out.println("3. TH Address");
			System.out.println("4. TH Name");
			System.out.println("5. TH Phone");
			System.out.println("6. Return to Previous Menu\n");
			System.out.println("Please enter your choice:");

			while ((uInput = input.readLine()) == null && uInput.length() == 0);

			try {
				choiceValue = Integer.parseInt(uInput);
			} catch (Exception e) {
				continue;
			}

			if (choiceValue == 1) {
				updateChoice = "category";
			} else if (choiceValue == 2) {
				updateChoice = "url";
			} else if (choiceValue == 3) {
				updateChoice = "haddress";
			} else if (choiceValue == 4) {
				updateChoice = "hname";
			} else if (choiceValue == 5) {
				updateChoice = "hphone";
			} else {
				break;
			}

			System.out.println("Enter new information:");
			while ((updateInfo = input.readLine()) == null && updateInfo.length() == 0);

			TH th = new TH();

			if (th.updateTH(sessionUserName, hid, updateChoice, updateInfo, connection.stmt)) {
				System.out.println("TH updated.\n");
				break;
			} else {
				System.out.println("TH not updated.\n");
				break;
			}
		}
	}

	private static void deleteTHScreen(Connector connection) throws IOException {

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		String hid = null;
		String uInput = null;

		int choiceValue = 0;

		while (true) {

			System.out.println("Delete Existing TH");
			TH th1 = new TH();
			System.out.println("Your THs:");
			System.out.println(th1.ownedTH(sessionUserName, connection.stmt));
			System.out.println("You can get the hid from viewing your owned TH, column 1.");
			System.out.println("Enter TH hid to delete:");

			while ((hid = input.readLine()) == null && hid.length() == 0);

			System.out.println("Are you sure you want to delete this TH?");
			System.out.println("1. Yes, delete");
			System.out.println("2. No, don't delete. Return to Previous Menu\n");
			System.out.println("Please enter your choice:");

			while ((uInput = input.readLine()) == null && uInput.length() == 0);

			try {
				choiceValue = Integer.parseInt(uInput);
			} catch (Exception e) {
				continue;
			}

			if (choiceValue == 1) {
				TH th = new TH();

				if (th.deleteTH(sessionUserName, hid, connection.stmt)) {
					System.out.println("TH deleted.\n");
					break;
				} else {
					System.out.println("TH not deleted.\n");
					break;
				}
			} else {
				break;
			}

		}
	}

	private static void browseTHScreen(Connector connection) throws IOException {

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		int searchValue = 0;
		String searchChoice;
		int sortValue = 0;
		String sortChoice;

		TH th = new TH();

		while (true) {
			System.out.println("\nChoose how to search the TH:");
			System.out.println("1. TH by Price");
			System.out.println("2. TH by City or State");
			System.out.println("3. TH by Keywords");
			System.out.println("4. TH by Category");
			System.out.println("5. Return to previous menu\n");
			System.out.println("Please enter your choice:");

			while ((searchChoice = input.readLine()) == null && searchChoice.length() == 0);

			try {
				searchValue = Integer.parseInt(searchChoice);
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			if(!(searchValue == 1 || searchValue == 2 || searchValue == 3 || searchValue == 4)){
				break;
			}
			
			System.out.println("\nChoose how to sort the TH:");
			System.out.println("1. TH by Price");
			System.out.println("2. Average Feedback Score");
			System.out.println("3. Average Trusted User Score");
			System.out.println("4. Return to previous menu\n");
			System.out.println("Please enter your choice:");

			while ((sortChoice = input.readLine()) == null && sortChoice.length() == 0);

			try {
				sortValue = Integer.parseInt(sortChoice);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			if(!(sortValue == 1 || sortValue == 2 || sortValue == 3)){
				continue;
			}

			if (searchValue == 1) {
				
				String priceLow, priceHigh;
				int priceLowValue = 0, priceHighValue = 0;
				
				System.out.println("Enter the TH lower price range:");
				while ((priceLow = input.readLine()) == null && priceLow.length() == 0);
				
				try {
					priceLowValue = Integer.parseInt(priceLow);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
				System.out.println("Enter the TH higher price range:");
				while ((priceHigh = input.readLine()) == null && priceHigh.length() == 0);
				
				try {
					priceHighValue = Integer.parseInt(priceHigh);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				System.out.println("\nResults for this search and sort:");
				System.out.println(th.browseTHPrice(sortValue, priceLowValue, priceHighValue, connection.stmt));
				
			} 
			else if (searchValue == 2) {

				String cityorstate = null;

				System.out.println("Please enter city or state (2 char state) to search by:");
				while ((cityorstate = input.readLine()) == null && cityorstate.length() == 0);
				
				System.out.println("\nResults for this search and sort:");
				System.out.println(th.browseTHCS(sortValue, cityorstate, connection.stmt));

			} 
			else if (searchValue == 3) {
				
				String uKeyword = null;

				System.out.println("Choose to search by Keyword:");
				while ((uKeyword = input.readLine()) == null && uKeyword.length() == 0);
				
				System.out.println("\nResults for this search and sort:");
				System.out.println(th.browseTHKeyword(sortValue, uKeyword, connection.stmt));
					
			}
			else if(searchValue == 4){
				
				String uCategory = null;

				System.out.println("Choose to search by Category:");
				while ((uCategory = input.readLine()) == null && uCategory.length() == 0);
				
				System.out.println("\nResults for this search and sort:");
				System.out.println(th.browseTHCategory(sortValue, uCategory, connection.stmt));
				
			}
			else {
				break;
			}
		}
	}
	
	private static void reserveTHScreen(Connector connection) throws IOException{
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		ReservationUser reserve = new ReservationUser();
		
		String resChoice = null;
		int resValue = 0;
		
		while (true) {
			System.out.println("\nReservation Menu:");
			System.out.println("1. Add a new Reservation");
			System.out.println("2. View all your Reservations");
			System.out.println("3. Delete a Reservation");
			System.out.println("4. Return to previous menu\n");
			System.out.println("Please enter your choice:");
			
			while ((resChoice = input.readLine()) == null && resChoice.length() == 0);

			try {
				resValue = Integer.parseInt(resChoice);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			if(!(resValue == 1 || resValue == 2 || resValue == 3)){
				break;
			}
			
			if(resValue == 1){
				
				String hidChoice = null;
				String fromChoice = null;
				String toChoice = null;
				String priceChoice = null;
				
				System.out.println("\nAdd a new Reservation");
				
				System.out.println("You can get the hid from browsing THs, column 1.");
				System.out.println("Enter the TH hid for the TH to reserve:");
				while ((hidChoice = input.readLine()) == null && hidChoice.length() == 0);
				
				System.out.println("Enter all dates in the following foramt: YYYY-MM-DD: Example: 2017-03-25");
				System.out.println("Enter the from date for the new reservation:");
				while ((fromChoice = input.readLine()) == null && fromChoice.length() == 0);
				
				System.out.println("Enter the to date for the new reservation:");
				while ((toChoice = input.readLine()) == null && toChoice.length() == 0);
				
				System.out.println("Enter the price for this availbility with not extra characters:");
				System.out.println("Example: For $100.00, just enter 100");
				while ((priceChoice = input.readLine()) == null && priceChoice.length() == 0);
				
				String confirmChoice = null;
				int confirmValue = 0;
				
				System.out.println("\nThe TH you are making a reservation for: ");
				System.out.println(reserve.showReservationInfo(hidChoice, connection.stmt));
				System.out.println("Are you sure you want to reserve this TH from: " + fromChoice + " - " + toChoice);
				System.out.println("1. Yes make Reservation");
				System.out.println("2. No cancel Reservation");
				
				while ((confirmChoice = input.readLine()) == null && confirmChoice.length() == 0);

				try {
					confirmValue = Integer.parseInt(confirmChoice);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
				if(confirmValue != 1){
					continue;
				}
				
				if(reserve.newReservation(sessionUserName, hidChoice, fromChoice, toChoice, priceChoice, connection.stmt)){
					System.out.println("Reservation created.\n");
					System.out.println("Thank You for your Reservation! Here are some other THs you might like: ");
					System.out.println(reserve.viewReservationSuggestion(sessionUserName, hidChoice, connection.stmt));
				}
				else{
					System.out.println("Reservation not created.");
				}
				
			}
			else if(resValue == 2){
				System.out.println("Here are all the Reservations you have made:");
				System.out.println(reserve.viewReservations(sessionUserName, connection.stmt));
			}
			else if(resValue == 3){
				
				String ridChoice = null;
				
				System.out.println("\nDelete a Reservation:");
				System.out.println(reserve.viewReservations(sessionUserName, connection.stmt));
				System.out.println("You can get the rid from viewing your Reservations, column 2.");
				System.out.println("Enter the Reservation rid:");
				while ((ridChoice = input.readLine()) == null && ridChoice.length() == 0);
				
				if(reserve.deleteReservation(ridChoice, connection.stmt)){
					System.out.println("Deleted Reservation.");
				}
				else{
					System.out.println("Did not delete Reservation.");
				}
			}
			else{
				break;
			}
		}
	}
	
	private static void recordVisitScreen(Connector connection) throws IOException{
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		ReservationUser reserve = new ReservationUser();
		VisitsUser visit = new VisitsUser();
		
		String visitChoice = null;
		int visitValue = 0;
		
		while (true) {
			System.out.println("\nReservation Menu:");
			System.out.println("1. Record a Visit");
			System.out.println("2. View all your Visits");
			System.out.println("3. Return to previous menu\n");
			System.out.println("Please enter your choice:");
			
			while ((visitChoice = input.readLine()) == null && visitChoice.length() == 0);

			try {
				visitValue = Integer.parseInt(visitChoice);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			if(!(visitValue == 1 || visitValue == 2)){
				break;
			}
			
			if(visitValue == 1){
				
				String ridChoice = null;
				String fromChoice = null;
				String toChoice = null;
				
				System.out.println("\nAdd a new Visit");
				System.out.println(reserve.viewReservations(sessionUserName, connection.stmt));
				System.out.println("You can get the rid from viewing your reservations, column 2.");
				System.out.println("Enter the Reservation rid for the reservation to record a visit of:");
				while ((ridChoice = input.readLine()) == null && ridChoice.length() == 0);
				
				System.out.println("Enter t dates in the following foramt: YYYY-MM-DD: Example: 2017-03-25");
				System.out.println("Enter the from date for the visit:");
				while ((fromChoice = input.readLine()) == null && fromChoice.length() == 0);
				
				System.out.println("Enter the to date for the visit:");
				while ((toChoice = input.readLine()) == null && toChoice.length() == 0);
				
				if(visit.newVisit(ridChoice, fromChoice, toChoice, connection.stmt)){
					System.out.println("Added visit.");
				}
				else{
					System.out.println("Did not add visit.");
				}
				
			}
			else if(visitValue == 2){
				System.out.println("\nAll your recorded Visit:");
				System.out.println(visit.viewVisits(sessionUserName, connection.stmt));
			}
			else{
				break;
			}
		}
	}
	
	private static void updateOwnedTHAvailScreen(Connector connection) throws IOException{
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		AvailableTH availTH = new AvailableTH();
		
		String availChoice = null;
		int availValue = 0;
		
		while (true) {
			System.out.println("\nOwned TH Availiability Menu:");
			System.out.println("1. Add a new Period");
			System.out.println("2. View all Periods");
			System.out.println("3. Delete a Period");
			System.out.println("4. Add a new Availabilty");
			System.out.println("5. View all Availabilties For a TH");
			System.out.println("6. Delete a Availability");
			System.out.println("7. Return to previous menu\n");
			System.out.println("Please enter your choice:");
			
			while ((availChoice = input.readLine()) == null && availChoice.length() == 0);

			try {
				availValue = Integer.parseInt(availChoice);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			if(!(availValue == 1 || availValue == 2 || availValue == 3 || availValue == 4 || availValue == 5 || availValue == 6 || availValue == 7 || availValue == 8 || availValue == 9)){
				break;
			}
			
			if(availValue == 1){
				
				String fromChoice = null;
				String toChoice = null;
				
				System.out.println("\nAdd a new Period");
				System.out.println("Enter all dates in the following foramt: YYYY-MM-DD: Example: 2017-03-25");
				System.out.println("Enter the from date for the new period:");
				while ((fromChoice = input.readLine()) == null && fromChoice.length() == 0);
				
				System.out.println("Enter the to date for the new period:");
				while ((toChoice = input.readLine()) == null && toChoice.length() == 0);
				
				if(availTH.newPeriod(fromChoice, toChoice, connection.stmt)){
					System.out.println("Added new period.");
				}
				else{
					System.out.println("Didn't add new period.");
				}
			}
			else if(availValue == 2){
				System.out.println("\nAll currently exising Periods:");
				System.out.println(availTH.viewPeriods(connection.stmt));
			}
			else if(availValue == 3){
				
				String pidChoice = null;
				
				System.out.println("\nDelete a Period");
				
				System.out.println("You can get the pid from browing THs, column 1.");
				System.out.println("Enter period pid to delete:");
				while ((pidChoice = input.readLine()) == null && pidChoice.length() == 0);
				
				if(availTH.deletePeriod(pidChoice, connection.stmt)){
					System.out.println("Deleted a period.");
				}
				else{
					System.out.println("Didn't delete a period.");
				}
			}
			else if(availValue == 4){
				
				String hidChoice = null;
				String pidChoice = null;
				String priceChoice = null;
				
				System.out.println("\nAdd a new Availaiblity for a TH");
				TH th1 = new TH();
				System.out.println("Your THs:");
				System.out.println(th1.ownedTH(sessionUserName, connection.stmt));
				System.out.println("You can get the hid from viewing your owned THs, column 1.");
				System.out.println("Enter TH hid for this TH availabilty:");
				while ((hidChoice = input.readLine()) == null && hidChoice.length() == 0);
				
				System.out.println("You can get the pid from browing Periods, column 1.");
				System.out.println("Enter period pid for this TH availabilty:");
				while ((pidChoice = input.readLine()) == null && pidChoice.length() == 0);
				
				System.out.println("Enter the price for this availbility with not extra characters:");
				System.out.println("Example: For $100.00, just enter 100");
				while ((priceChoice = input.readLine()) == null && priceChoice.length() == 0);
				
				if(availTH.addAvailability(hidChoice, pidChoice, priceChoice, connection.stmt)){
					System.out.println("Added new availability.");
				}
				else{
					System.out.println("Didn't add new availability.");
				}
				
			}
			else if(availValue == 5){
				
				String hidChoice = null;
				
				System.out.println("\nView all Availaiblities for a TH");
				
				System.out.println("You can get the hid from viewing your owned THs, column 1.");
				System.out.println("Enter TH hid for this TH availabilty:");
				while ((hidChoice = input.readLine()) == null && hidChoice.length() == 0);
				
				System.out.println(availTH.viewAvailablityTH(hidChoice, connection.stmt));
			}
			else if(availValue == 6){
				
				String hidChoice = null;
				String pidChoice = null;
				
				System.out.println("\nDelete the Availaiblity of a TH");
				
				System.out.println("You can get the hid from viewing your owned THs, column 1.");
				System.out.println("Enter TH hid for this TH availabilty:");
				while ((hidChoice = input.readLine()) == null && hidChoice.length() == 0);
				
				System.out.println("You can get the pid from browing Periods, column 1.");
				System.out.println("Enter period pid for this TH availabilty:");
				while ((pidChoice = input.readLine()) == null && pidChoice.length() == 0);
				
				if(availTH.deleteAvailabilty(hidChoice, pidChoice, connection.stmt)){
					System.out.println("Deleted availability.");
				}
				else{
					System.out.println("Didn't delete availability.");
				}
				
			}
			else{
				break;
			}
		}
		
	}
	
	private static void viewFavoritesScreen(Connector connection){
		
		FavoriteUser favorite = new FavoriteUser();
		
		System.out.println("\nList of all Favorited THs:");
		
		System.out.println(favorite.viewFavorites(sessionUserName, connection.stmt));
	}
	
	private static void addFavoritesScreen(Connector connection) throws IOException{
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		FavoriteUser favorite = new FavoriteUser();
		
		String hidChoice = null;
		
		System.out.println("You can get the hid from browing THs, column 1.");
		System.out.println("Enter TH hid to make a personal favorite:");
		while ((hidChoice = input.readLine()) == null && hidChoice.length() == 0);
		
		if(favorite.newFavorite(sessionUserName, hidChoice, connection.stmt)){
			System.out.println("New favorite added.");
		}
		else{
			System.out.println("New favorite not added.");
		}
		
	}
	
	private static void trustScreen(Connector connection) throws IOException{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		String trustChoice = null;
		String userChoice = null;
		int trustValue = 0;
		
		TrustUser trust = new TrustUser();
		
		while (true) {
			System.out.println("\nTrust Menu:");
			System.out.println("1. Rate User as Trusted/Non-Trusted");
			System.out.println("2. View Trust Record of a User");
			System.out.println("3. Return to previous menu\n");
			System.out.println("Please enter your choice:");
			
			while ((trustChoice = input.readLine()) == null && trustChoice.length() == 0);

			try {
				trustValue = Integer.parseInt(trustChoice);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			if(!(trustValue == 1 || trustValue == 2)){
				break;
			}
			
			System.out.println("Enter the username of the user to perform this action on:");
			
			while ((userChoice = input.readLine()) == null && userChoice.length() == 0);
			
			if(trustValue == 1){
				
				String rating = null;
				
				System.out.println("Enter the trust rating for the user: Example: trusted or non-trusted (only enter one of these two options)");
				
				while ((rating = input.readLine()) == null && rating.length() == 0);
				
				if(trust.rateTrustUser(sessionUserName, userChoice, rating, connection.stmt)){
					System.out.println("Trust rating added.");
				}
				else{
					System.out.println("Trust rating not added.");
				}
			}
			else if(trustValue == 2){
				System.out.println(trust.viewUserTrust(userChoice, connection.stmt));
			}
			else{
				break;
			}
		}
	}
	
	private static void statisticsScreen(Connector connection) throws IOException{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		TH th = new TH();
		
		String statisticsChoice = null;
		int statisticsValue = 0;
		
		String limitChoice = null;
		int limitValue = 0;
		
		while (true) {
			
			System.out.println("\nStatistics Menu:");
			System.out.println("1. View Most Popular THs By Category");
			System.out.println("2. View Most Expensive THs By Category");
			System.out.println("3. View Most Highly Rated THs By Category");
			System.out.println("4. Return to previous menu\n");
			System.out.println("Please enter your choice:");
			

			while ((statisticsChoice = input.readLine()) == null && statisticsChoice.length() == 0);

			try {
				statisticsValue = Integer.parseInt(statisticsChoice);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			if(!(statisticsValue == 1 || statisticsValue == 2 || statisticsValue == 3)){
				break;
			}
			
			System.out.println("Please enter the amount of responses per category to display:");
			
			while ((limitChoice = input.readLine()) == null && limitChoice.length() == 0);

			try {
				limitValue = Integer.parseInt(limitChoice);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			if(statisticsValue == 1){
				System.out.println(th.viewPopularTH(limitValue, connection.stmt));
			}
			else if(statisticsValue == 2){
				System.out.println(th.viewExpensiveTH(limitValue, connection.stmt));
			}
			else if(statisticsValue == 3){
				System.out.println(th.viewHighestRatedTH(limitValue, connection.stmt));
			}
			else{
				break;
			}
		}
	}
	
	private static void seperationScreen(Connector connection) throws IOException{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		SeperationUsers seperation = new SeperationUsers();
		
		String seperationChoice = null;
		int seperationValue = 0;
		
		while (true) {
			System.out.println("\nSeperation Menu:");
			System.out.println("1. View if user is 1 Degree Of Seperation");
			System.out.println("2. View if user is 2 Degree Of Seperation");
			System.out.println("3. Return to previous menu\n");
			System.out.println("Please enter your choice:");
			
			while ((seperationChoice = input.readLine()) == null && seperationChoice.length() == 0);

			try {
				seperationValue = Integer.parseInt(seperationChoice);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			if(!(seperationValue == 1 || seperationValue == 2)){
				break;
			}
			
			if(seperationValue == 1){
				System.out.println("List of all users that are 1 Degree of Seperation from you:");
				System.out.println(seperation.viewSeperation1(sessionUserName, connection.stmt));
			}
			else if(seperationValue == 2){
				System.out.println("List of all users that are 2 Degrees of Seperation from you:");
				System.out.println(seperation.viewSeperation2(sessionUserName, connection.stmt));
			}
			else{
				break;
			}
		}
	}
	
	private static void feedbackScreen(Connector connection) throws IOException{
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		FeedbackUser feedback = new FeedbackUser();
		
		String feedbackChoice = null;
		int feedbackValue = 0;
		
		while (true) {
			System.out.println("\nFeedback Menu:");
			System.out.println("1. Leave new Feedback on a TH");
			System.out.println("2. View All Feedback for a TH");
			System.out.println("3. Rate User Feedback of a TH");
			System.out.println("4. View Ratings of a User Feedback of a TH");
			System.out.println("5. View Top Most Rated Feedbacks of a TH");
			System.out.println("6. Return to previous menu\n");
			System.out.println("Please enter your choice:");
			
			while ((feedbackChoice = input.readLine()) == null && feedbackChoice.length() == 0);

			try {
				feedbackValue = Integer.parseInt(feedbackChoice);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			if(!(feedbackValue == 1 || feedbackValue == 2 || feedbackValue == 3 || feedbackValue == 4 || feedbackValue == 5)){
				break;
			}
			
			if(feedbackValue == 1){
				
				String hidChoice = null;
				String scoreChoice = null;
				String messageChoice = null;
				String dateChoice = null;
				
				System.out.println("You can get the hid from browing THs, column 1.");
				System.out.println("Enter TH hid to leave a feedback for:");
				while ((hidChoice = input.readLine()) == null && hidChoice.length() == 0);
				
				System.out.println("Enter the score you wish to give this TH: (Scale 1 -10)");
				while ((scoreChoice = input.readLine()) == null && scoreChoice.length() == 0);
				
				System.out.println("Enter the feedback comment you wish to give this TH: ");
				while ((messageChoice = input.readLine()) == null && messageChoice.length() == 0);
				
				if(feedback.newFeedback(hidChoice, sessionUserName, scoreChoice, messageChoice, connection.stmt)){
					System.out.println("Feedback was added.");
				}
				else{
					System.out.println("Feedback was not added.");
				}
			}
			else if(feedbackValue == 2){
				
				String hidChoice = null;
				
				System.out.println("You can get the hid from browing THs, column 1.");
				System.out.println("Enter TH hid to leave a feedback for:");
				while ((hidChoice = input.readLine()) == null && hidChoice.length() == 0);
				
				System.out.println(feedback.viewTHFeedback(hidChoice, connection.stmt));

			}
			else if(feedbackValue == 3){
				
				String fbidChoice = null;
				String fbRating = null;
				
				System.out.println("You can get the feedback id from browing all feedback of a given TH, column 1.");
				System.out.println("Enter Feedback id to rate the user feedback of:");
				while ((fbidChoice = input.readLine()) == null && fbidChoice.length() == 0);
				
				System.out.println("Enter desired rating of feedback as a integer, use the following scale: ");
				System.out.println("Scale: 0 = 'useless', 1 = 'useful', 2 = 'very-useful'");
				while ((fbRating = input.readLine()) == null && fbRating.length() == 0);
				
				if(feedback.rateUserFeedback(sessionUserName, fbidChoice, fbRating, connection.stmt)){
					System.out.println("Feedback Rating added.");
				}
				else{
					System.out.println("Feedback Rating not added.");
				}
			}
			else if(feedbackValue == 4){
				
				String fbidChoice = null;
				
				System.out.println("You can get the feedback id from browing all feedback of a given TH, column 1.");
				System.out.println("Enter Feedback id to see the user feedbacks of:");
				while ((fbidChoice = input.readLine()) == null && fbidChoice.length() == 0);
				
				System.out.println(feedback.viewRatingForFeedback(fbidChoice, connection.stmt));
			}
			else if(feedbackValue == 5){
				
				String hidChoice = null;
				String limitChoice = null;
				
				System.out.println("You can get the hid from browing THs, column 1.");
				System.out.println("Enter TH hid to view the top most feedback for:");
				while ((hidChoice = input.readLine()) == null && hidChoice.length() == 0);
				
				System.out.println("Enter the amount of top most comments to view:");
				while ((limitChoice = input.readLine()) == null && limitChoice.length() == 0);
				
				System.out.println(feedback.viewTopUsefulFeedbacksForTH(sessionUserName, limitChoice, hidChoice, connection.stmt));

			}
			else{
				break;
			}
		}
	}
	
	private static void awardsScreen(Connector connection) throws IOException{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		AdminAwards awards = new AdminAwards();
		
		String awardChoice = null;
		int awardValue = 0;
		
		String limitChoice = null;
		int limitValue = 0;
		
		while (true) {
			System.out.println("\nAward Menu:");
			System.out.println("1. View Most Trusted Users");
			System.out.println("2. View Most Useful Users");
			System.out.println("3. Return to previous menu\n");
			System.out.println("Please enter your choice:");
			
			while ((awardChoice = input.readLine()) == null && awardChoice.length() == 0);

			try {
				awardValue = Integer.parseInt(awardChoice);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			if(!(awardValue == 1 || awardValue == 2)){
				break;
			}
			
			System.out.println("Choose how many users to display:");
			while ((limitChoice = input.readLine()) == null && limitChoice.length() == 0);

			try {
				limitValue = Integer.parseInt(limitChoice);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			if(awardValue == 1){
				System.out.println(awards.viewMostTrustedUsers(limitValue, connection.stmt));
			}
			else if(awardValue == 2){
				System.out.println(awards.viewMostUsefulUsers(limitValue, connection.stmt));
			}
			else{
				break;
			}
		}
	}

}
