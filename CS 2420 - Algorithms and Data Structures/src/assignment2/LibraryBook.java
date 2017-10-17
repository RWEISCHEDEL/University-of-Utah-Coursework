package assignment2;

import java.util.GregorianCalendar;

/**
 * This class creates a LibraryBook object by first extending the Book class and using the Book class constructor to
 * first create a Book object with a isbn, author, and title. Then it creates a LibraryBook object by adding more
 * information such as book holder and due date. 
 * @author Robert Weischedel && Makenzie Elliott
 *
 */
public class LibraryBook extends Book{
	
	// Private member variables to hold information about the bookHolder(borrower) and the due date of the book.
	private String bookHolder;
	private GregorianCalendar dueDate;
	
	// Constructs a LibraryBook object by first creating a Book object and then adding a bookHolder and dueDate to it.
	public LibraryBook(long isbn, String author, String title){
		super(isbn, author, title);
		bookHolder = null;
		dueDate = null;
	}
	
	// Returns the String name of the bookHolder
	public String getHolder(){
		return bookHolder;
	}
	
	// Returns the GregorianCalendar due date object
	public GregorianCalendar getDueDate(){
		return dueDate;
	}
	
	// Checks the book back into the library
	public void checkin(){
		bookHolder = null;
		dueDate = null;
	}
	
	// Checks the book out of the library
	public void checkout(String holder, int month, int day, int year){
		bookHolder = holder;
		dueDate = new GregorianCalendar(year, month, day);
		
	}
	
	
	

}
