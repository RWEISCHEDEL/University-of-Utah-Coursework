package assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Class representation of a generic library (a collection of library books
 * generic).
 * 
 * @author Robert Weischedel && Makenzie Elliott && Dr. Meyer
 */
public class LibraryGeneric<Type> {

	// Create an ArrayList to store all LibraryBooksGeneric objects in library
	private ArrayList<LibraryBookGeneric<Type>> library;

	// Create a new Library by initializing the ArrayList of LibraryBooksGenerics
	public LibraryGeneric() {
		library = new ArrayList<LibraryBookGeneric<Type>>();
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
		library.add(new LibraryBookGeneric<Type>(isbn, author, title));
	}

	/**
	 * Add the list of library books generic to the library, assume no duplicates.
	 * 
	 * @param list
	 *            -- list of library books generic to be added
	 */
	public void addAll(ArrayList<LibraryBookGeneric<Type>> list) {
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
		ArrayList<LibraryBookGeneric<Type>> toBeAdded = new ArrayList<LibraryBookGeneric<Type>>();

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

				toBeAdded
						.add(new LibraryBookGeneric<Type>(isbn, author, title));

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
	 * Returns the holder of the library book generic with the specified ISBN.
	 * 
	 * If no book with the specified ISBN is in the library, returns null.
	 * 
	 * @param isbn
	 *            -- ISBN of the book to be looked up
	 */
	public Type lookup(long isbn) {
		// Search through all LibraryBookGeneric objects in the library
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
	 * Returns the list of library books generics checked out to the specified holder.
	 * 
	 * If the specified holder has no books checked out, returns an empty list.
	 * 
	 * @param holder
	 *            -- holder whose checked out books are returned
	 */
	public ArrayList<LibraryBookGeneric<Type>> lookup(Type holder) {

		// Create an ArrayList to hold all the checked out books generics that have been borrowed by the particular holder
		ArrayList<LibraryBookGeneric<Type>> checkedOutBooks = new ArrayList<LibraryBookGeneric<Type>>();

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
	public boolean checkout(long isbn, Type holder, int month, int day, int year) {

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
		
		// Check and see if the book has already been checked out. If it has return false.
		if (library.get(index).getHolder() != null) {
			return false;
		}

		// Check out the library book generic to the specified holder and with a specific date. 
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
	public boolean checkin(Type holder) {
		
		// Create an ArrayList of LibraryBooks that the holder has borrowed.
		ArrayList<LibraryBookGeneric<Type>> booksBorrowed = new ArrayList<LibraryBookGeneric<Type>>();

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

	/**
	 * Returns the list of library books, sorted by ISBN (smallest ISBN first).
	 */
	public ArrayList<LibraryBookGeneric<Type>> getInventoryList() {
		
		// Create a new ArrayList of LibraryBookGenerics to copy the sorted ISBN list
		ArrayList<LibraryBookGeneric<Type>> libraryCopy = new ArrayList<LibraryBookGeneric<Type>>();
		libraryCopy.addAll(library);

		// Create a new comparator object to define the rules of ordering of ISBNs
		OrderByIsbn comparator = new OrderByIsbn();

		// Sort the libraryCopy based on the comparator rules
		sort(libraryCopy, comparator);

		return libraryCopy;
	}

	/**
	 * Returns the list of library books, sorted by author
	 */
	public ArrayList<LibraryBookGeneric<Type>> getOrderedByAuthor() {
		
		// Create a new ArrayList of LibraryBookGenerics to copy the sorted author list
		ArrayList<LibraryBookGeneric<Type>> libraryCopy = new ArrayList<LibraryBookGeneric<Type>>();
		libraryCopy.addAll(library);

		// Create a  new comparator object to define the rules of ordering of authors
		OrderByAuthor comparator = new OrderByAuthor();

		// Sort the libraryCopy based on the comparator rules
		sort(libraryCopy, comparator);

		return libraryCopy;

	}

	/**
	 * Returns the list of library books whose due date is older than the input
	 * date. The list is sorted by date (oldest first).
	 *
	 * If no library books are overdue, returns an empty list.
	 */
	public ArrayList<LibraryBookGeneric<Type>> getOverdueList(int month,
			int day, int year) {
		
		// Create a new ArrayList of LibraryBookGenerics to copy the sorted due date list
		ArrayList<LibraryBookGeneric<Type>> libraryCopy = new ArrayList<LibraryBookGeneric<Type>>();

		// Sort through the library and find all due dates that aren't null and newer than the given due date
		for (int i = 0; i < library.size(); i++) {
			if (library.get(i).getDueDate() != null
					&& library.get(i).getDueDate()
							.compareTo(new GregorianCalendar(year, month, day)) <= 0) {
				libraryCopy.add(library.get(i));
			}
		}

		// Create a new comparator object to define the rules of ordering of due dates
		OrderByDueDate comparator = new OrderByDueDate();

		// Sort the libraryCopy based on the comparator rules
		sort(libraryCopy, comparator);

		return libraryCopy;
	}

	/**
	 * Performs a SELECTION SORT on the input ArrayList. 1. Find the smallest
	 * item in the list. 2. Swap the smallest item with the first item in the
	 * list. 3. Now let the list be the remaining unsorted portion (second item
	 * to Nth item) and repeat steps 1, 2, and 3.
	 */
	private static <ListType> void sort(ArrayList<ListType> list,
			Comparator<ListType> c) {
		for (int i = 0; i < list.size() - 1; i++) {
			int j, minIndex;
			for (j = i + 1, minIndex = i; j < list.size(); j++)
				if (c.compare(list.get(j), list.get(minIndex)) < 0)
					minIndex = j;
			ListType temp = list.get(i);
			list.set(i, list.get(minIndex));
			list.set(minIndex, temp);
		}
	}

	/**
	 * Comparator that defines an ordering among library books using the ISBN.
	 */
	protected class OrderByIsbn implements Comparator<LibraryBookGeneric<Type>> {

		/**
		 * Returns a negative value if lhs is smaller than rhs. Returns a
		 * positive value if lhs is larger than rhs. Returns 0 if lhs and rhs
		 * are equal.
		 */
		public int compare(LibraryBookGeneric<Type> lhs,
				LibraryBookGeneric<Type> rhs) {
			return (int) (lhs.getIsbn() - rhs.getIsbn());
		}
	}

	/**
	 * Comparator that defines an ordering among library books using the author,
	 * and book title as a tie-breaker.
	 */
	protected class OrderByAuthor implements
			Comparator<LibraryBookGeneric<Type>> {

		@Override
		public int compare(LibraryBookGeneric<Type> o1,
				LibraryBookGeneric<Type> o2) {
			// If the authors match then, compare the titles
			int order = (int) (o1.getAuthor().compareTo(o2.getAuthor()));
			if (order == 0) {
				return (int) (o1.getTitle().compareTo(o2.getTitle()));
			}
			return order;
		}

	}

	/**
	 * Comparator that defines an ordering among library books using the due
	 * date.
	 */
	protected class OrderByDueDate implements
			Comparator<LibraryBookGeneric<Type>> {

		@Override
		public int compare(LibraryBookGeneric<Type> o1,
				LibraryBookGeneric<Type> o2) {
			// Returns a 1 if o1 dueDate is less than o2, 0 if they are equal and -1 if o2 is less than o1
			return (int) (o1.getDueDate().compareTo(o2.getDueDate()));
		}

	}

}
