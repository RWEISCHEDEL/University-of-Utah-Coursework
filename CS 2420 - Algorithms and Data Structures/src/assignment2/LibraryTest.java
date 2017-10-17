package assignment2;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Testing class for Library. This uses the standard Library and LibraryBook classes for these sets of tests. Some of
 * these tests were done by Dr. Meyer and some were written by us. 
 * @author Robert Weischedel && Makenzie Elliott
 */
public class LibraryTest {

  public static void main(String[] args) {
    // test an empty library
    Library lib = new Library();

    if (lib.lookup(978037429279L) != null)
      System.err.println("TEST FAILED -- empty library: lookup(isbn)");
    ArrayList<LibraryBook> booksCheckedOut = lib.lookup("Jane Doe");
    if (booksCheckedOut == null || booksCheckedOut.size() != 0)
      System.err.println("TEST FAILED -- empty library: lookup(holder)");
    if (lib.checkout(978037429279L, "Jane Doe", 1, 1, 2008))
      System.err.println("TEST FAILED -- empty library: checkout");
    if (lib.checkin(978037429279L))
      System.err.println("TEST FAILED -- empty library: checkin(isbn)");
    if (lib.checkin("Jane Doe"))
      System.err.println("TEST FAILED -- empty library: checkin(holder)");
    
    System.out.println("Book Lookup Test :" + lib.lookup(978037429279L));

    // test a small library
    lib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
    lib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
    lib.add(9780446580342L, "David Baldacci", "Simple Genius");

    if (lib.lookup(9780330351690L) != null)
      System.err.println("TEST FAILED -- small library: lookup(isbn)");
    if (!lib.checkout(9780330351690L, "Jane Doe", 1, 1, 2008))
      System.err.println("TEST FAILED -- small library: checkout");
    booksCheckedOut = lib.lookup("Jane Doe");
    
    System.out.println("The Tests Begin Now!");
    System.out.println(booksCheckedOut);
    System.out.println(booksCheckedOut.size());
    System.out.println(booksCheckedOut.get(0));
    System.out.println(booksCheckedOut.get(0).getHolder());
    System.out.println(booksCheckedOut.get(0).getDueDate());
    
    if (booksCheckedOut == null
        || booksCheckedOut.size() != 1
        || !booksCheckedOut.get(0).equals(new Book(9780330351690L, "Jon Krakauer", "Into the Wild"))
        || !booksCheckedOut.get(0).getHolder().equals("Jane Doe")
        || !booksCheckedOut.get(0).getDueDate().equals(new GregorianCalendar(2008, 1, 1)))
      System.err.println("TEST FAILED -- small library: lookup(holder)");
    if (!lib.checkin(9780330351690L))
      System.err.println("TEST FAILED -- small library: checkin(isbn)");
    if (lib.checkin("Jane Doe"))
      System.err.println("TEST FAILED -- small library: checkin(holder)");

    // test a medium library
    lib.addAll("Mushroom_Publishing.txt");

    // FILL IN
    System.out.println("Dr. Meyer's Testing done." + "\n");
    
    System.out.println("Our Testing Begins" + "\n");
    
    // The following tests are for looking to see if a book had been checked in, then checking it out, then checking
    // back in.
    Library lib2 = new Library();
    
    // Add books to library
    lib2.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
    lib2.add(9780330351690L, "Jon Krakauer", "Into the Wild");
    lib2.add(9780446580342L, "David Baldacci", "Simple Genius");
    
    // Testing the lookup method for book not checked out
    System.out.println("\n" + "Testing of the lookup by isbn method : ");
    if(lib2.lookup(9780374292799L) == null){
    	System.out.println("The book has not been checked out by a holder.");
    }
    
    // Testing the lookup method for not real book
    if(lib2.lookup(9780374292791L) == null){
    	System.out.println("The book does not exist in lib2.");
    }
    
    // Checkout a book
    lib2.checkout(9780374292799L, "Meckenzi", 6, 6, 2016);
    
    // Testing the lookup method for a checked out book
    if(lib2.lookup(9780374292799L) == "Meckenzi"){
    	System.out.println("The book has been checked out by Meckenzi.");
    }
    
    // Check book back in
    lib2.checkin(9780374292799L);
    
    // Test lookup method to ensure book is still not checked out. 
    if(lib2.lookup(9780374292799L) == null){
    	System.out.println("The book has not been checked out by a holder.");
    }
    
  }

  /**
   * Returns a library of "dummy" books (random ISBN and placeholders for author
   * and title).
   * 
   * Useful for collecting running times for operations on libraries of varying
   * size.
   * 
   * @param size --
   *          size of the library to be generated
   */
  public static ArrayList<LibraryBook> generateLibrary(int size) {
    ArrayList<LibraryBook> result = new ArrayList<LibraryBook>();

    for (int i = 0; i < size; i++) {
      // generate random ISBN
      Random randomNumGen = new Random();
      String isbn = "";
      for (int j = 0; j < 13; j++)
        isbn += randomNumGen.nextInt(10);

      result.add(new LibraryBook(Long.parseLong(isbn), "An author", "A title"));
    }

    return result;
  }

  /**
   * Returns a randomly-generated ISBN (a long with 13 digits).
   * 
   * Useful for collecting running times for operations on libraries of varying
   * size.
   */
  public static long generateIsbn() {
    Random randomNumGen = new Random();

    String isbn = "";
    for (int j = 0; j < 13; j++)
      isbn += randomNumGen.nextInt(10);

    return Long.parseLong(isbn);
  }
}