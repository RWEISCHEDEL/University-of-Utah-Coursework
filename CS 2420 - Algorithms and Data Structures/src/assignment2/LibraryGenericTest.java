package assignment2;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Testing class for LibraryGeneric.
 *
 * @author Robert Weischedel && Makenzie Elliott && Dr. Meyer
 */
public class LibraryGenericTest {

	public static void main(String[] args) {

		// test a library that uses names (String) to id patrons
		LibraryGeneric<String> lib1 = new LibraryGeneric<String>();
		lib1.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		lib1.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		lib1.add(9780446580342L, "David Baldacci", "Simple Genius");

		String patron1 = "Jane Doe";

		// test to see if the specified book is not checked out
		if (!lib1.checkout(9780330351690L, patron1, 1, 1, 2008))
			System.err.println("TEST FAILED: first checkout");

		// test to see if the specifeid book is not checked out
		if (!lib1.checkout(9780374292799L, patron1, 1, 1, 2008))
			System.err.println("TEST FAILED: second checkout");

		// a list with all the books that "Jane Doe" has checked out
		ArrayList<LibraryBookGeneric<String>> booksCheckedOut1 = lib1
				.lookup(patron1);

		// checks a list of books that a holder has checked out
		if (booksCheckedOut1 == null
				|| booksCheckedOut1.size() != 2
				|| !booksCheckedOut1.contains(new Book(9780330351690L,
						"Jon Krakauer", "Into the Wild"))
				|| !booksCheckedOut1.contains(new Book(9780374292799L,
						"Thomas L. Friedman", "The World is Flat"))
				|| !booksCheckedOut1.get(0).getHolder().equals(patron1)
				|| !booksCheckedOut1.get(0).getDueDate()
						.equals(new GregorianCalendar(2008, 1, 1))
				|| !booksCheckedOut1.get(1).getHolder().equals(patron1)
				|| !booksCheckedOut1.get(1).getDueDate()
						.equals(new GregorianCalendar(2008, 1, 1)))
			System.err.println("TEST FAILED: lookup(holder)");

		// returns true if the books are already checked out, then it applies
		// the NOT! statement,
		// it then checks in the books
		if (!lib1.checkin(patron1))
			System.err.println("TEST FAILED: checkin(holder)");

		// test a library that uses phone numbers (PhoneNumber) to id patrons
		LibraryGeneric<PhoneNumber> lib2 = new LibraryGeneric<PhoneNumber>();
		lib2.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		lib2.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		lib2.add(9780446580342L, "David Baldacci", "Simple Genius");

		PhoneNumber patron2 = new PhoneNumber("801.555.1234");

		if (!lib2.checkout(9780330351690L, patron2, 1, 1, 2008))
			System.err.println("TEST FAILED: first checkout");
		if (!lib2.checkout(9780374292799L, patron2, 1, 1, 2008))
			System.err.println("TEST FAILED: second checkout");
		ArrayList<LibraryBookGeneric<PhoneNumber>> booksCheckedOut2 = lib2
				.lookup(patron2);
		if (booksCheckedOut2 == null
				|| booksCheckedOut2.size() != 2
				|| !booksCheckedOut2.contains(new Book(9780330351690L,
						"Jon Krakauer", "Into the Wild"))
				|| !booksCheckedOut2.contains(new Book(9780374292799L,
						"Thomas L. Friedman", "The World is Flat"))
				|| !booksCheckedOut2.get(0).getHolder().equals(patron2)
				|| !booksCheckedOut2.get(0).getDueDate()
						.equals(new GregorianCalendar(2008, 1, 1))
				|| !booksCheckedOut2.get(1).getHolder().equals(patron2)
				|| !booksCheckedOut2.get(1).getDueDate()
						.equals(new GregorianCalendar(2008, 1, 1)))
			System.err.println("TEST FAILED: lookup(holder)");
		if (!lib2.checkin(patron2))
			System.err.println("TEST FAILED: checkin(holder)");

		// testing ended
		System.out.println("Testing done.");
		System.out.println("Addition testing for the methods and getInventoryList, getOrderedByAuthor and getOverdueList are in the \nClass of JUnit test cases called LibraryGenericListTests Class");
	}
}