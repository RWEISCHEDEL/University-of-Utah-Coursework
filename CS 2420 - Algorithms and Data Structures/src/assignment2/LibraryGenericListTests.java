package assignment2;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


/**
 * This is a series of JUnit Test Cases to test the LibarayGeneric Class and its associated classes. Specifically these 
 * tests test the sorting functions of phase 3 of the assignment. Other more detailed and specific tests can be found 
 * in LibraryTest and LibraryGenericTest classes.
 * @author Robert Weischedel && Makenzie Elliott
 *
 */
public class LibraryGenericListTests {

	@Test
	public void testLookupByISBN() {

		// Test lookup(ISBN)
		//

		LibraryGeneric<String> lib3 = new LibraryGeneric<String>();
		lib3.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		lib3.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		lib3.add(9780446580342L, "David Baldacci", "Simple Genius");

		lib3.checkout(9780374292799L, "Makenzie Elliott", 2, 30, 2015);

		// test to see if the correct holder shows up when lookup(isbn) is used
		assertEquals("Makenzie Elliott", lib3.lookup(9780374292799L));

	}

	@Test
	public void testLookupByISBN2() {

		// test Lookup ISBN
		//
		// checks to see if the default holder is null
		LibraryGeneric<String> lib3 = new LibraryGeneric<String>();
		lib3.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		lib3.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		lib3.add(9780446580342L, "David Baldacci", "Simple Genius");

		// test to the if the lookup(ISBN) method returns null because the book
		// hasn't been checked out
		assertEquals(null, lib3.lookup(9780446580342L));

	}

	@Test
	public void testLookupByISBN3() {
		// test to see if you lookup by an ISBN that is not in the library, it
		// should return null

		LibraryGeneric<String> lib3 = new LibraryGeneric<String>();
		lib3.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		lib3.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		lib3.add(9780446580342L, "David Baldacci", "Simple Genius");

		// the ISBN being looked up is not in the library
		// test to the if the lookup(ISBN) method returns null because the book
		// hasn't been checked out
		assertEquals(null, lib3.lookup(9780446580355L));

	}

	@Test
	public void testLookupByHolderEmptyList() {

		// test lookup(holder)

		// Returns the list of library books generics checked out to the
		// specified holder.
		LibraryGeneric<String> lib3 = new LibraryGeneric<String>();
		lib3.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		lib3.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		lib3.add(9780446580342L, "David Baldacci", "Simple Genius");

		// create an empty list with nothing checked out
		ArrayList<LibraryBookGeneric<String>> testHolderList = lib3
				.lookup("Robert Weischedel");

		// test to see if the list created by lookup(holder) returns an empty
		// list
		assertEquals(0, testHolderList.size());

	}

	@Test
	public void testLookupByHolderFullList() {

		// Tests the holder's list of checked out books.
		LibraryGeneric<String> library = new LibraryGeneric<String>();

		library.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		library.add(9780374292791L, "Thomas L. Friedman", "The Head is Flat");
		library.add(9781843192954L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");
		library.add(9781843192701L, "Moyra Caldecott", "The Lily and the Bull");
		library.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		library.add(9780446580342L, "David Baldacci", "Simple Genius");
		library.add(9781843193319L, "Alan Burt Akers", "Transit to Scorpio");
		library.add(9781843192953L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");

		library.checkout(9780374292799L, "Robert Weishdell", 2, 30, 2008);
		library.checkout(9781843192954L, "Makenzie Elliott", 1, 30, 2005);
		library.checkout(9781843192953L, "Robert Weishdell", 1, 28, 1990);
		library.checkout(9780330351690L, "Makenzie Elliott", 2, 1, 2015);
		library.checkout(9780374292791L, "Makenzie Elliott", 2, 2, 2015);

		ArrayList<LibraryBookGeneric<String>> makenziesList = library
				.lookup("Makenzie Elliott");

		assertEquals(3, makenziesList.size());
	}

	@Test
	public void testCheckOut() {

		// Tests the holder's list of checked in books.
		LibraryGeneric<String> library = new LibraryGeneric<String>();

		library.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		library.add(9780374292791L, "Thomas L. Friedman", "The Head is Flat");
		library.add(9781843192954L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");
		library.add(9781843192701L, "Moyra Caldecott", "The Lily and the Bull");
		library.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		library.add(9780446580342L, "David Baldacci", "Simple Genius");
		library.add(9781843193319L, "Alan Burt Akers", "Transit to Scorpio");
		library.add(9781843192953L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");

		library.checkout(9780374292799L, "Robert Weishdell", 2, 30, 2008);

		assertEquals("Robert Weishdell", library.lookup(9780374292799L));

	}

	@Test
	public void testCheckOut2() {

		// Tests the holder's list of checked in books.
		LibraryGeneric<String> library = new LibraryGeneric<String>();

		library.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		library.add(9780374292791L, "Thomas L. Friedman", "The Head is Flat");
		library.add(9781843192954L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");
		library.add(9781843192701L, "Moyra Caldecott", "The Lily and the Bull");
		library.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		library.add(9780446580342L, "David Baldacci", "Simple Genius");
		library.add(9781843193319L, "Alan Burt Akers", "Transit to Scorpio");
		library.add(9781843192953L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");

		// test to see if a book can be checked out that doesn't exist
		assertFalse(library.checkout(9790374292799L, "Robert Weischedel", 2,
				30, 2015));

	}

	@Test
	public void testCheckOut3() {

		// Tests the holder's list of checked in books.
		LibraryGeneric<String> library = new LibraryGeneric<String>();

		library.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		library.add(9780374292791L, "Thomas L. Friedman", "The Head is Flat");
		library.add(9781843192954L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");
		library.add(9781843192701L, "Moyra Caldecott", "The Lily and the Bull");
		library.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		library.add(9780446580342L, "David Baldacci", "Simple Genius");
		library.add(9781843193319L, "Alan Burt Akers", "Transit to Scorpio");
		library.add(9781843192953L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");

		library.checkout(9781843193319L, "Makenzie Elliott", 2, 28, 2015);

		// test to see if the book is already checked out
		assertFalse(library.checkout(9781843193319L, "Robert Weischedel", 2,
				30, 2015));

	}

	@Test
	public void testCheckinByISBN() {

		// Tests the holder's list of checked out books.
		LibraryGeneric<String> library = new LibraryGeneric<String>();

		library.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		library.add(9780374292791L, "Thomas L. Friedman", "The Head is Flat");
		library.add(9781843192954L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");
		library.add(9781843192701L, "Moyra Caldecott", "The Lily and the Bull");
		library.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		library.add(9780446580342L, "David Baldacci", "Simple Genius");
		library.add(9781843193319L, "Alan Burt Akers", "Transit to Scorpio");
		library.add(9781843192953L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");

		library.checkout(9780374292799L, "Robert Weishdell", 2, 30, 2008);

		// test check in by isbn returns true if it can be checked in without a
		// dubplicate ISBN or a non existing one
		assertTrue(library.checkin(9780374292799L));

	}

	@Test
	public void testCheckinByISBN2() {

		LibraryGeneric<String> library = new LibraryGeneric<String>();

		library.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		library.add(9780374292791L, "Thomas L. Friedman", "The Head is Flat");
		library.add(9781843192954L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");
		library.add(9781843192701L, "Moyra Caldecott", "The Lily and the Bull");
		library.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		library.add(9780446580342L, "David Baldacci", "Simple Genius");
		library.add(9781843193319L, "Alan Burt Akers", "Transit to Scorpio");
		library.add(9781843192953L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");

		// test to see if the method returns false when an isbn is being checked
		// in that doesn't exist
		assertFalse(library.checkin(1780446580342L));

	}

	@Test
	public void testCheckinByISBN3() {

		LibraryGeneric<String> library = new LibraryGeneric<String>();

		library.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		library.add(9780374292791L, "Thomas L. Friedman", "The Head is Flat");
		library.add(9781843192954L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");
		library.add(9781843192701L, "Moyra Caldecott", "The Lily and the Bull");
		library.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		library.add(9780446580342L, "David Baldacci", "Simple Genius");
		library.add(9781843193319L, "Alan Burt Akers", "Transit to Scorpio");
		library.add(9781843192953L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");

		library.checkout(9781843192953L, "Me", 1, 2, 1990);
		library.checkin(9781843192953L);

		// test to see if the method returns false when an isbn is being checked
		// in that has already been checked in
		assertFalse(library.checkin(9781843192953L));

	}

	@Test
	public void testCheckinByHolder() {

		// Tests the holder's list of checked in books.
		LibraryGeneric<String> library = new LibraryGeneric<String>();

		library.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		library.add(9780374292791L, "Thomas L. Friedman", "The Head is Flat");
		library.add(9781843192954L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");
		library.add(9781843192701L, "Moyra Caldecott", "The Lily and the Bull");
		library.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		library.add(9780446580342L, "David Baldacci", "Simple Genius");
		library.add(9781843193319L, "Alan Burt Akers", "Transit to Scorpio");
		library.add(9781843192953L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");

		library.checkout(9780374292799L, "Robert Weishdell", 2, 30, 2008);
		library.checkout(9781843192954L, "Makenzie Elliott", 1, 30, 2005);
		library.checkout(9781843192953L, "Robert Weishdell", 1, 28, 1990);
		library.checkout(9780330351690L, "Makenzie Elliott", 2, 1, 2015);
		library.checkout(9780374292791L, "Makenzie Elliott", 2, 2, 2015);

		ArrayList<LibraryBookGeneric<String>> robertsList = library
				.lookup("Robert Weishdell");

		assertEquals(2, robertsList.size());

		library.checkin("Robert Weishdell");

		robertsList = library.lookup("Robert Weishdell");

		assertEquals(0, robertsList.size());
		assertEquals(null, library.lookup(9780374292799L));

	}

	@Test
	public void testCheckinByHolder2() {

		// Tests the holder's list of checked in books.
		LibraryGeneric<String> library = new LibraryGeneric<String>();

		library.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		library.add(9780374292791L, "Thomas L. Friedman", "The Head is Flat");
		library.add(9781843192954L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");
		library.add(9781843192701L, "Moyra Caldecott", "The Lily and the Bull");
		library.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		library.add(9780446580342L, "David Baldacci", "Simple Genius");
		library.add(9781843193319L, "Alan Burt Akers", "Transit to Scorpio");
		library.add(9781843192953L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");

		// If no books with the specified holder are in the library, returns
		// false;
		assertFalse(library.checkin("Makenzie Elliott"));

	}

	@Test
	public void testISBNOrder() {

		// Test a library that uses names (String) to id patrons

		LibraryGeneric<String> library = new LibraryGeneric<String>();
		library.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		library.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		library.add(9780446580342L, "David Baldacci", "Simple Genius");
		library.add(9781843193319L, "Alan Burt Akers", "Transit to Scorpio");
		library.add(9781843192954L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");
		library.add(9781843192701L, "Moyra Caldecott", "The Lily and the Bull");
		library.add(9781843192039L, "William Fitzmaurice",
				"Operation: Sergeant York");
		library.add(9781843192022L, "Roger Taylor", "Whistler");

		ArrayList<LibraryBookGeneric<String>> testISBNorder = library
				.getInventoryList();

		assertEquals(9780330351690L, testISBNorder.get(0).getIsbn());
		assertEquals(9781843193319L, testISBNorder
				.get(testISBNorder.size() - 1).getIsbn());
	}

	@Test
	public void testAuthorOrder() {

		// Test a Library that contains many different authors, but with all
		// different names.
		LibraryGeneric<String> library = new LibraryGeneric<String>();
		library.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		library.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		library.add(9780446580342L, "David Baldacci", "Simple Genius");
		library.add(9781843193319L, "Alan Burt Akers", "Transit to Scorpio");
		library.add(9781843192954L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");
		library.add(9781843192701L, "Moyra Caldecott", "The Lily and the Bull");
		library.add(9781843192039L, "William Fitzmaurice",
				"Operation: Sergeant York");
		library.add(9781843192022L, "Roger Taylor", "Whistler");

		ArrayList<LibraryBookGeneric<String>> testAuthorOrder = library
				.getOrderedByAuthor();

		assertEquals("Alan Burt Akers", testAuthorOrder.get(0).getAuthor());
		assertEquals("William Fitzmaurice",
				testAuthorOrder.get(testAuthorOrder.size() - 1).getAuthor());
	}

	@Test
	public void testAuthorOrderEmptyList() {

		LibraryGeneric<String> library = new LibraryGeneric<String>();
		ArrayList<LibraryBookGeneric<String>> testAuthorOrder = library
				.getOrderedByAuthor();
		
		// returns an empty list if there are no books in the library
		assertEquals(0, testAuthorOrder.size());

	}

	@Test
	public void testAuthorOrderTieBreaker() {

		// Test a Library that contains many different authors, but with some
		// similar names to ensure that the
		// book title will then be taken into consideration.
		LibraryGeneric<String> library = new LibraryGeneric<String>();

		library.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		library.add(9780374292791L, "Thomas L. Friedman", "The Head is Flat");
		library.add(9781843192954L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");
		library.add(9781843192701L, "Moyra Caldecott", "The Lily and the Bull");

		ArrayList<LibraryBookGeneric<String>> testAuthorOrder = library
				.getOrderedByAuthor();

		assertEquals(9780374292791L, testAuthorOrder.get(2).getIsbn());
		assertEquals(9780374292799L,
				testAuthorOrder.get(testAuthorOrder.size() - 1).getIsbn());

	}

	@Test
	public void testOverdueList() {

		// // Test a Library that contains many different due dates to ensure
		// the correct list would be printed.
		LibraryGeneric<String> library = new LibraryGeneric<String>();

		library.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		library.add(9780374292791L, "Thomas L. Friedman", "The Head is Flat");
		library.add(9781843192954L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");
		library.add(9781843192701L, "Moyra Caldecott", "The Lily and the Bull");
		library.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		library.add(9780446580342L, "David Baldacci", "Simple Genius");
		library.add(9781843193319L, "Alan Burt Akers", "Transit to Scorpio");
		library.add(9781843192953L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");

		library.checkout(9780374292799L, "Robert Weishdell", 2, 30, 2008);
		library.checkout(9781843192954L, "Makenzie Elliott", 1, 30, 2005);
		library.checkout(9781843192953L, "Robert Weishdell", 1, 28, 1990);
		library.checkout(9780330351690L, "Makenzie Elliott", 2, 1, 2015);
		library.checkout(9780330351690L, "Makenzie Elliott", 2, 2, 2015);

		ArrayList<LibraryBookGeneric<String>> testDueDates = library
				.getOverdueList(2, 1, 2015);

		assertEquals(9781843192953L, testDueDates.get(0).getIsbn());
		assertEquals(9780330351690L, testDueDates.get(testDueDates.size() - 1)
				.getIsbn());

	}
	
	@Test
	public void testOverdueListEmpty() {

		// // Test a Library that contains many different due dates to ensure
		// the correct list would be printed.
		LibraryGeneric<String> library = new LibraryGeneric<String>();

		library.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		library.add(9780374292791L, "Thyomas L. Friedman", "The Head is Flat");
		library.add(9781843192954L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");
		library.add(9781843192701L, "Moyra Caldecott", "The Lily and the Bull");
		library.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		library.add(9780446580342L, "David Baldacci", "Simple Genius");
		library.add(9781843193319L, "Alan Burt Akers", "Transit to Scorpio");
		library.add(9781843192953L, "Dennis Radha-Rose",
				"The Anxiety Relief Program");

		
		ArrayList<LibraryBookGeneric<String>> testDueDates = library
				.getOverdueList(2, 1, 2015);

		// returns an empty list if nothing has been checked out 
		assertEquals(0, testDueDates.size());

	}
}

