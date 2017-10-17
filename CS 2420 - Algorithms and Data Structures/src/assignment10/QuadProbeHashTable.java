/**
 * 
 */
package assignment10;

import java.util.Arrays;
import java.util.Collection;

/**
 * This class is part of assignment 10 on creating and using HashTables. In this
 * implementation, we used a quad probe technique of handling collisions and
 * also of rehashing the HashTable. It should also be noted that this HashTable
 * array is made up of HashEntries which is an object defined in this class
 * which contains the data to store and also if that item is active or not, to
 * make it easier to handle deletion by using lazy deletion. It should be noted
 * that this implementation of a HashTable can only accept Strings as inputs
 * into the table. NOTE: The size of the array refers to the number of items
 * stored in the array, not the length of the array!
 * 
 * @author Jackson Murphy and Robert Weischedel
 * 
 */
public class QuadProbeHashTable implements Set<String> {

	// Private Member variables to be used to hold the array of HashEntries, the
	// Functor and the size of the array.
	private HashEntry[] hashTable;
	private HashFunctor functor;
	private int size;

	// This private member variable was added to aid in testing the analysis
	// document.
	private long numOfCollisions;
	
	private long numOfReHash;

	/**
	 * This method serves as the constructor for this class. It creates a new
	 * array of HashEntries and also brings in the size and the functor that
	 * will be used in this instance of the QuadProdeHashTable.
	 * 
	 * @param capacity
	 *            - The initial desired size of the array.
	 * @param functor
	 *            - The HashFunctor that is desired to be used in rehashing.
	 */
	public QuadProbeHashTable(int capacity, HashFunctor functor) {
		hashTable = new HashEntry[nextPrime(capacity)];
		this.functor = functor;
		size = 0;
		numOfCollisions = 0;
		numOfReHash = 0;
	}

	/**
	 * This method takes in a string that the user wishes to add to the
	 * HashTable. It calls the findPos method to find the correct location to
	 * add the string and then ensures that the value in not a duplicate. If it
	 * is not and the spot is not full, then add the desired item.
	 * 
	 * @param item
	 *            - the item whose presence is ensured in this set
	 * @return true if this set changed as a result of this method call (that
	 *         is, if the input item was actually inserted); otherwise, returns
	 *         false
	 */
	@Override
	public boolean add(String item) {

		// Check if inputed item is null.
		if (item == null) {
			return false;
		}

		// Call findPos to find the position where item should be added
		int index = findPos(item);

		// Ensure no duplicates
		if (hashTable[index] != null && hashTable[index].key.equals(item)) {
			return false;
		}

		// Add the new item
		hashTable[index] = new HashEntry(item);

		size++;

		// Check and see if the Hash Table needs to be reHashed
		if (isLoaded()) {
			reHash();
		}

		return true;
	}

	/**
	 * This method is similar to the add method in the class, except that it
	 * takes in a collection of strings from the user to be inputed into the
	 * HashTable. It does this by looping through the collection of strings and
	 * calling the add method on each string.
	 * 
	 * @param items
	 *            - the collection of items whose presence is ensured in this
	 *            set
	 * @return true if this set changed as a result of this method call (that
	 *         is, if any item in the input collection was actually inserted);
	 *         otherwise, returns false
	 */
	@Override
	public boolean addAll(Collection<? extends String> items) {

		// Check and see if items collection is null.
		if (items == null) {
			return false;
		}

		// Create a boolean flag to handle if at least one item was added to the
		// HashTable.
		boolean hasAdded = false;

		// Loop through each item in the collection and call the add method to
		// attempt and add it to the HashTable. If one was added, change boolean
		// flag to true.
		for (String s : items) {
			if (add(s)) {
				hasAdded = true;
			}
		}
		return hasAdded;
	}

	/**
	 * This method clears the hashTable and the size, when the user desires to
	 * clear all items that are currently found in the HashTable.
	 */
	@Override
	public void clear() {

		// Overwrite hashTable with a new empty one of the same length, also
		// reset size.
		hashTable = new HashEntry[hashTable.length];
		size = 0;
		numOfCollisions = 0;
		numOfReHash = 0;
	}

	/**
	 * This method determines if the item inputed by the user can be found in
	 * HashTable or not. It does this by calling findPos to find the index of
	 * where the string should be located.
	 * 
	 * @param item
	 *            - the item sought in this set
	 * @return true if there is an item in this set that is equal to the input
	 *         item; otherwise, returns false
	 */
	@Override
	public boolean contains(String item) {

		// Check if inputed item is null.
		if (item == null) {
			return false;
		}

		// Call findPos to find the position where item should be located if it
		// is in there.
		int index = findPos(item);

		// Check if the item is at that location
		if (hashTable[index] != null && hashTable[index].key.equals(item)) {
			return true;
		}

		return false;
	}

	/**
	 * This method is similar to the contains method except that it takes in a
	 * collection of strings to check and see if all strings contained in the
	 * collection can be found in the HashTable. It does this by looping through
	 * each item in the collection and calling the contains method on it.
	 * 
	 * @param items
	 *            - the collection of items sought in this set
	 * @return true if for each item in the specified collection, there is an
	 *         item in this set that is equal to it; otherwise, returns false
	 */
	@Override
	public boolean containsAll(Collection<? extends String> items) {

		// Check and see if items collection is null.
		if (items == null) {
			return false;
		}

		// Loop through all items in the collection and call contains on each
		// item.
		for (String s : items) {
			if (!contains(s)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method returns true or false based on if the list is empty or not.
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * This method returns the integer value of the number of filled elements in
	 * the HashTable or its size.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * This inner class serves as the items that make up the hashTable array.
	 * They are simple objects that only contain two pieces of data, the key or
	 * value you wish to place into the HashTable and a boolean value stating if
	 * the value is being used or not in the HashTable.
	 * 
	 * @author Jackson Murphy and Robert Weischedel
	 * 
	 */
	private class HashEntry {

		// The member variables that store the String value and whether the item
		// is active in the list.
		public String key;
		public boolean isActive;

		/**
		 * This method serves as the constructor for the HashEntry class. It
		 * automatically assumes that the object you are inserting into the
		 * table is going to be used and therefore active, so it automatically
		 * sets isActive to true.
		 * 
		 * @param input
		 *            - The desired string you wish to enter into the HashTable.
		 */
		public HashEntry(String input) {
			key = input;
			isActive = true;
		}
	}

	/**
	 * This method finds the next prime number greater than or equal to the
	 * inputed value. It calls the isPrime method in order to check if the new
	 * number is prime or not.
	 * 
	 * @param capacity
	 *            - The integer value to find the next prime number of.
	 * @return - A prime number
	 */
	private int nextPrime(int capacity) {

		// If the capacity is even, make it odd.
		if (capacity % 2 == 0) {
			capacity++;
		}

		// Loop and call isPrime until we find the biggest prime number that
		// come after capacity.
		for (; !isPrime(capacity); capacity += 2) {
		}

		return capacity;
	}

	/**
	 * This method is only used in the nextPrime method, it take in the int
	 * value from nextPrime and checks whether it is prime or not.
	 * 
	 * @param n
	 *            - The int value we wish to see if it is prime or not.
	 * @return - A boolean determining if int is prime.
	 */
	private boolean isPrime(int n) {

		// Ensure that n is prime by seeing if it divides evenly into any values
		// starting at three till the size of n.
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method returns the position in the array where the String should be
	 * found, using quadratic probing algorithm.
	 * 
	 * @param s
	 *            the String whose position we want to find
	 * @return the index position
	 */
	private int findPos(String s) {

		// Find the hashValue of the inputed string s.
		int hashValue = functor.hash(s);

		// Calculate the true hashValue index
		hashValue = hashValue % hashTable.length;

		// In case the hash function returns such a large int that it overflows
		// to become a negative int, make the int positive.
		if (hashValue < 0) {
			hashValue += hashTable.length;
		}

		// Call the hashValue index in order to make it easier to keep tract of.
		int index = hashValue + 0;

		// Loop indefinitely till one of the two conditions are met : 1. If the
		// current index is empty or 2. If the index contains the item.
		for (int i = 1;; i++) {

			// If our probing takes us beyond the array's length, wrap around
			if (index >= hashTable.length) {
				index = index % hashTable.length;
			}

			// If the spot is empty, the String should go here
			if (hashTable[index] == null) {
				return index;
			}

			// If the spot is not empty, return the position only if the String
			// is already there
			if (hashTable[index].key.equals(s)) {
				return index;
			}

			// Increment the quad probe step size
			index = (int) (hashValue + Math.pow(i, 2));

			// Increment the number of collisions that have occurred.
			numOfCollisions++;
		}

	}

	/**
	 * This method is called when the load factor becomes >= 0.5, when it is
	 * this method increases the table's capacity and then rehashes all of the
	 * old table's items to the new one.
	 */
	private void reHash() {
		
		numOfReHash++;
		
		HashEntry[] oldArray = Arrays.copyOf(hashTable, hashTable.length);

		// Grow the hashTable, making sure the new size is a prime number
		hashTable = new HashEntry[nextPrime(oldArray.length + 1)];

		// Reset size to zero because the loop below calls add() which
		// increments size.
		size = 0;

		// Rehash active items from the old array into the new array
		for (int i = 0; i < oldArray.length; i++) {
			if (oldArray[i] != null && oldArray[i].isActive) {
				add(oldArray[i].key);
			}
		}

	}

	/**
	 * This method returns true if the load factor is >= 0.5, false otherwise.
	 */
	private boolean isLoaded() {

		if ((double) size / (double) hashTable.length >= 0.5) {
			return true;
		} else
			return false;
	}

	/**
	 * This method returns the current overall length of the Hash Table. Only
	 * used in JUnit testing.
	 * 
	 * @return - The current length of the hashTable.
	 */
	public int getLength() {
		return hashTable.length;
	}

	/**
	 * This method returns the number of collisions that occurred when trying to
	 * add a particular string value. This method was added in order to aid in
	 * testing and to help answer numbers 7 and 8 on the analysis document.
	 * 
	 * @return - The number of collisions that occurred when trying to add an
	 *         element.
	 */
	public long getCollisions() {
		return numOfCollisions;
	}
	
	public long getReHash() {
		return numOfReHash;
		
	}

}
