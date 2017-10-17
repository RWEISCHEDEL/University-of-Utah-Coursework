package assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Class representation of a library (a collection of library books).
 * 
 * @author Robert Weischedel && Makenzie Elliott && Dr. Meyer
 */
public class Library {

	// Create an ArrayList to store all LibraryBooks in library
	private ArrayList<LibraryBook> library;

	// Create a new Library by initializing the ArrayList of LibraryBooks
	public Library() {
		library = new ArrayList<LibraryBook>();
	}

	/**
	 * Add the specified book to the library, assume no duplicates.
	 * 
	 * @param isbn
	 *            -- ISBN of the book to be added
	 * @param author
	 *            -- author of the book to be added
	 * @param title
	 *            -- title of the book to be added
	 */
	public void add(long isbn, String author, String title) {
		library.add(new LibraryBook(isbn, author, title));
	}

	/**
	 * Add the list of library books to the library, assume no duplicates.
	 * 
	 * @param list
	 *            -- list of library books to be added
	 */
	public void addAll(ArrayList<LibraryBook> list) {
		library.addAll(list);
	}

	/**
	 * Add books specified by the input file. One book per line with ISBN,
	 * author, and title separated by tabs.
	 * 
	 * If file does not exist or format is violated, do nothing.
	 * 
	 * @param filename
	 */
	public void addAll(String filename) {
		ArrayList<LibraryBook> toBeAdded = new ArrayList<LibraryBook>();

		try {
			Scanner fileIn = new Scanner(new File(filename));
			int lineNum = 1;

			while (fileIn.hasNextLine()) {
				String line = fileIn.nextLine();

				Scanner lineIn = new Scanner(line);
				lineIn.useDelimiter("\\t");

				if (!lineIn.hasNextLong())
					throw new ParseException("ISBN", lineNum);
				long isbn = lineIn.nextLong();

				if (!lineIn.hasNext())
					throw new ParseException("Author", lineNum);
				String author = lineIn.next();

				if (!lineIn.hasNext())
					throw new ParseException("Title", lineNum);
				String title = lineIn.next();

				toBeAdded.add(new LibraryBook(isbn, author, title));

				lineNum++;
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage()
					+ " Nothing added to the library.");
			return;
		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage()
					+ " formatted incorrectly at line " + e.getErrorOffset()
					+ ". Nothing added to the library.");
			return;
		}

		library.addAll(toBeAdded);
	}

	/**
	 * Returns the holder of the library book with the specified ISBN.
	 * 
	 * If no book with the specified ISBN is in the library, returns null.
	 * 
	 * @param isbn
	 *            -- ISBN of the book to be looked up
	 */
	public String lookup(long isbn) {
		// Search through all LibraryBook objects in the library
		for (int i = 0; i < library.size(); i++) {
			if (library.get(i).getIsbn() == (isbn)) {
				// Return the holder of the book with that specified isbn
				return library.get(i).getHolder();
			}
		}
		// If no book is found with that isbn returns null
		return null;
	}

	/**
	 * Returns the list of library books checked out to the specified holder.
	 * 
	 * If the specified holder has no books checked out, returns an empty list.
	 * 
	 * @param holder
	 *            -- holder whose checked out books are returned
	 */
	public ArrayList<LibraryBook> lookup(String holder) {

		// Create an ArrayList to hold all the checked out books that have been borrowed by the particular holder
		ArrayList<LibraryBook> checkedOutBooks = new ArrayList<LibraryBook>();

		// Search through the whole library to find all books held by the holder
		if (library != null) {
			for (int i = 0; i < library.size(); i++) {
				if (library.get(i).getHolder() != null) {
					if (library.get(i).getHolder().equals(holder)) {
						checkedOutBooks.add(library.get(i));
					}
				}
			}
		}
		// Return the list of checkedOutBooks
		return checkedOutBooks;
	}

	/**
	 * Sets the holder and due date of the library book with the specified ISBN.
	 * 
	 * If no book with the specified ISBN is in the library, returns false.
	 * 
	 * If the book with the specified ISBN is already checked out, returns
	 * false.
	 * 
	 * Otherwise, returns true.
	 * 
	 * @param isbn
	 *            -- ISBN of the library book to be checked out
	 * @param holder
	 *            -- new holder of the library book
	 * @param month
	 *            -- month of the new due date of the library book
	 * @param day
	 *            -- day of the new due date of the library book
	 * @param year
	 *            -- year of the new due date of the library book
	 * 
	 */
	public boolean checkout(long isbn, String holder, int month, int day,
			int year) {
		
		// Search through the library and ensure that the book being checked out is in the library
		int index = -1;
		for (int i = 0; i < library.size(); i++) {
			if (library.get(i).getIsbn() == isbn) {
				// If the book is found, save the index of where it is stored and break out of loop.
				index = i;
				break;
			}
		}
		// If the desired book isbn is not found, return false.
		if (index < 0) {
			return false;
		}

		// Check and see if the book has already been checked out. If it has return false.
		if (library.get(index).getHolder() != null) {
			return false;
		}

		// Check out the library book to the specified holder and with a specific date. 
		library.get(index).checkout(holder, month, day, year);

		return true;
	}

	/**
	 * Unsets the holder and due date of the library book.
	 * 
	 * If no book with the specified ISBN is in the library, returns false.
	 * 
	 * If the book with the specified ISBN is already checked in, returns false.
	 * 
	 * Otherwise, returns true.
	 * 
	 * @param isbn
	 *            -- ISBN of the library book to be checked in
	 */
	public boolean checkin(long isbn) {
		
		// Search through the library and ensure that the book being checked out is in the library
		int index = -1;
		for (int i = 0; i < library.size(); i++) {
			if (library.get(i).getIsbn() == isbn) {
				index = i;
				break;
			}
		}
		// If the desired book isbn is not found, return false.
		if (index < 0) {
			return false;
		}
		
		// Check and see if the book has already been checked out. If it has not return false.
		if (library.get(index).getHolder() == null) {
			return false;
		}

		// Check the desired book back in.
		library.get(index).checkin();
		return true;
	}

	/**
	 * 
	 * Unsets the holder and due date for all library books checked out be the
	 * specified holder.
	 * 
	 * If no books with the specified holder are in the library, returns false;
	 * 
	 * Otherwise, returns true.
	 * 
	 * @param holder
	 *            -- holder of the library books to be checked in
	 */
	public boolean checkin(String holder) {
		
		// Create an ArrayList of LibraryBooks that the holder has borrowed.
		ArrayList<LibraryBook> booksBorrowed = new ArrayList<LibraryBook>();

		// Use the lookup method to get a list of borrowed books from that particular holder
		booksBorrowed = lookup(holder);

		// As long as the list has at least one book in it, check all the books from that holder back in.
		if (booksBorrowed.size() != 0) {
			for (int i = 0; i < booksBorrowed.size(); i++) {
				booksBorrowed.get(i).checkin();
			}
			return true;
		}
		// If the holder has no borrowed books return false
		return false;
	}
}