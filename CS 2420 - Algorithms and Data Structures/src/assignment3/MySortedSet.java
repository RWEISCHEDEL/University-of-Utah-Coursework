package assignment3;

import java.util.Collection; 
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The class and its associated methods construct and interact with lists of all different types of objects and 
 * Primitives. The list doesn't allow any duplicate objects in the list as well. The class contains two constructors
 * on how the user would like their various generic types sorted. The user can either just use the comparable class and
 * the natural ordering of the objects or pass in a comparator object to specify a specific ordering. The contained methods
 * allow for the altering of the list.
 * @author Robert Weischedel && Makenzie Elliott && Dr. Meyer
 *
 * @param <E>
 */
public class MySortedSet<E> implements SortedSet<E>{

	// List of private member variables:
	// Array that acts as the list
	private E[] theList;
	// How many spots are filled in the array
	private int occupiedElements;
	// How large the array is
	private int arrayLimit;
	// The Comparator object that the user can bring in
	private Comparator<? super E> theComparator;
	
	/**
	 * If this constructor is used to create the sorted set, 
	 * it is assumed that the elements are ordered using their natural ordering 
	 * (i.e., E implements Comparable<? super E>).
	 */
	public MySortedSet(){
		
		// Initialize the values and array, set the Comparator to null since it is not used
		occupiedElements = 0;
		arrayLimit = 20;
		theList = (E[])new Object[arrayLimit];
		theComparator = null;
		
	}
	
	/**
	 * If this constructor is used to create the sorted set, 
	 * it is assumed that the elements are ordered using the provided comparator.
	 * @param comparator
	 */
	public MySortedSet(Comparator<? super E> comparator){
	
		// Initialize the values and array, set the Comparator to theComparator private member variable
		theComparator = comparator;
		occupiedElements = 0;
		arrayLimit = 20;
		theList = (E[])new Object[arrayLimit];
		theComparator = comparator;
		
	}

	/**
	 * @return The comparator used to order the elements in this set, or null if
	 *         this set uses the natural ordering of its elements (i.e., uses
	 *         Comparable).
	 */
	@Override
	public Comparator<? super E> comparator() {
		
		// Return the comparator object
		return theComparator;

	}

	
	/**
	 * @return the first (lowest, smallest) element currently in this set
	 * @throws NoSuchElementException
	 *             if the set is empty
	 */
	@Override
	public E first() throws NoSuchElementException {

		// If the list is empty throw an exception
		if(this.isEmpty()){
			throw new NoSuchElementException();
		}
		
		// Return the first item in the array
		return theList[0];
	}

	/**
	 * @return the last (highest, largest) element currently in this set
	 * @throws NoSuchElementException
	 *             if the set is empty
	 */
	@Override
	public E last() throws NoSuchElementException {

		// If the list is empty throw an exception
		if(this.isEmpty()){
			throw new NoSuchElementException();
		}
		
		// Return the last item in the array
		return theList[occupiedElements - 1];
		
	}

	/**
	 * Adds the specified element to this set if it is not already present and
	 * not set to null.
	 * 
	 * @param o
	 *            -- element to be added to this set
	 * @return true if this set did not already contain the specified element
	 */
	@Override
	public boolean add(E o) {

		// If the object is equal to null, return false
		if(o == null){
			return false;
		}
		
		// If the array is empty, place the object in the first position of the array
		if(isEmpty()){
			theList[0] = o;
			occupiedElements++;
			return true;
		}

		// If the array is full, expand the array
		if(occupiedElements == arrayLimit){
			E[] temp = (E[])new Object[arrayLimit*2];
			for(int i = 0; i < arrayLimit; i++){
				temp[i] = theList[i];
			}
			theList = temp;
			arrayLimit = theList.length;
			temp = null;
		}
		
		// If the object is not contained in the array add it to the array
		if(!contains(o)){
			
			// Find the position of the new object
			int position = binarySearch((E)o);

			// Create two new arrays to split up the array when trying to add the new object
			E[] firstHalf;
			E[] secondHalf;
			
			// Split of the old array, so that the new element can be added
			if(compare(o, theList[position]) > 0){
				firstHalf = (E[])(new Object[position + 1]);
				secondHalf = (E[])(new Object[occupiedElements - (position)]);
				
				for(int i = 0; i < firstHalf.length; i++){
					firstHalf[i] = theList[i];
				}
				
				secondHalf[0] = o;
	
				for(int i = 1; i < secondHalf.length; i++){
					secondHalf[i] = theList[i + position];
				}
			}
			else{
				firstHalf = (E[])(new Object[position + 1]);
				secondHalf = (E[])(new Object[occupiedElements - (position)]);
				
				for(int i = 0; i < firstHalf.length - 1; i++){
					firstHalf[i] = theList[i];
				}
				
				firstHalf[firstHalf.length-1] = o;
				
				for(int i = 0; i < secondHalf.length; i++){
					secondHalf[i] = theList[i + position];
				}
			}
			
			// join the two lists with the new insertion to make one final list with no duplicates
			for(int i = 0; i < firstHalf.length; i++){
				theList[i] = firstHalf[i];
			}
			for(int i = 0; i < secondHalf.length; i++){
				theList[firstHalf.length + i] = secondHalf[i];
			}
			occupiedElements++;
			return true;
		}
		
		return false;
	}

	/**
	 * Adds all of the elements in the specified collection to this set if they
	 * are not already present and not set to null.
	 * 
	 * @param c
	 *            -- collection containing elements to be added to this set
	 * @return true if this set changed as a result of the call
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {

		// Create a variable to see if even one element in the collection was added
		boolean somethingAdded = false;
		
		// Create an iterator to iterate through the collection
		Iterator<? extends E> iterateCollection = c.iterator();
		
		// Search through the collection and see if the elements to be added are already there or not
		while(iterateCollection.hasNext()){
			E checkedObj = iterateCollection.next();
			if(!contains(checkedObj)){
				// Add the element and set boolean to true
				add(checkedObj);
				somethingAdded = true;
			}
		}
		// Return if something was added to the list or not
		return somethingAdded;
	}

	/**
	 * Removes all of the elements from this set. The set will be empty after
	 * this call returns.
	 */
	@Override
	public void clear() {
		
		// Create a new array and reset the arrayLimit size to 20.
		arrayLimit = 20;
		E[] newList = (E[])new Object[arrayLimit];
		// Have theList point to the newList instead
		theList = newList;
		newList = null;
		occupiedElements = 0;
		
	}

	/**
	 * @param o
	 *            -- element whose presence in this set is to be tested
	 * @return true if this set contains the specified element
	 */
	@Override
	public boolean contains(Object o) {

		// If the object is null return false
		if(o == null){
			return false;
		}	
		
		// Find the position of the desired object
		int position = binarySearch((E)o);
		
		// If that position in the list is empty, then return false
		if(theList[position] == null){
			return false;
		}
		
		// Check and see if the object at that position is equal to the desired object
		if(theList[position].equals(o)){
			return true;
		}
		
		return false;
	}

	/**
	 * @param c
	 *            -- collection to be checked for containment in this set
	 * @return true if this set contains all of the elements of the specified
	 *         collection
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		
		// Create an iterator to iterate through the collection
		Iterator<?> iterateCollection = c.iterator();
		
		// Search through the collection and see if the elements are already there or not
		while(iterateCollection.hasNext()){
			Object checkedObj = iterateCollection.next();
			
			// If one element in the collection is not in the array, return false
			if(!contains(checkedObj)){
				return false;
			}	
		}
		return true;
	}

	/**
	 * @return true if this set contains no elements
	 */
	@Override
	public boolean isEmpty() {

		// If there are no elements in the array, return true
		if (occupiedElements == 0){
			return true;
		}
		return false;
	}

	/**
	 * @return an iterator over the elements in this set, where the elements are
	 *         returned in sorted (ascending) order
	 */
	@Override
	public Iterator<E> iterator() {
		
		// Create and return a new iterator object
		TheIterator iterator = new TheIterator();

		return iterator;

	}

	/**
	 * Removes the specified element from this set if it is present.
	 * 
	 * @param o
	 *            -- object to be removed from this set, if present
	 * @return true if this set contained the specified element
	 */
	@Override
	public boolean remove(Object o) {
		
		// If the object is equal to null or the array is empty, return false
		if (o == null || isEmpty()) {
			return false;
		}

		// If the array doesn't contain that object, return false
		if (!contains((E) o)) {
			return false;
		}
		
		// Find the position the of the object in the array and set that location to null
		int position = binarySearch((E) o);
		theList[position] = null;
		occupiedElements--;
		
		// Move all the elements in the array over
		for (int i = position; i < occupiedElements; i++) {
			theList[i] = theList[i + 1];
		}
		return true;
	}

	/**
	 * Removes from this set all of its elements that are contained in the
	 * specified collection.
	 * 
	 * @param c
	 *            -- collection containing elements to be removed from this set
	 * @return true if this set changed as a result of the call
	 */
	@Override
	public boolean removeAll(Collection<?> c) {

		// Create a variable to see if even one element in the collection was removed
		boolean somethingRemoved = false;
		
		// Create an iterator to iterate through the collection
		Iterator<?> iterateCollection = c.iterator();
		
		// Search through the collection and see if the elements are already there or not
		while(iterateCollection.hasNext()){
			Object checkedObj = iterateCollection.next();
			
			// If the object is found, remove it and change the boolean to true
			if(contains(checkedObj)){
				remove(checkedObj);
				somethingRemoved = true;
			}
		}
		return somethingRemoved;
	}

	/**
	 * @return the number of elements in this set
	 */
	@Override
	public int size() {	
		return occupiedElements;
	}

	/**
	 * @return an array containing all of the elements in this set, in sorted
	 *         (ascending) order.
	 */
	@Override
	public Object[] toArray() {
		
		// Create a new Object array to return.
		Object[] arrayToReturn = new Object[arrayLimit];
		
		// Fill the new Object array
		for(int i = 0; i < occupiedElements ; i++){
			arrayToReturn[i] = theList[i];
		}
		return arrayToReturn;
	}
	
	/**
	 * returns the position that the new object should be places based on how the 
	 * items should be compared.
	 * @param o
	 * @return
	 */
	public int binarySearch(E o){
		
		// Set initial values for the low, mid and high
		int mid = -1;
		int low = 0;
		int high = occupiedElements-1;
		
		// If the array is empty return 0
		if(occupiedElements == 0){
			return 0;
		}
		else{
			
			// Use the binary search algorithm to find the position of the object
			while(low < high){
				mid = (low + high)/2;
				
				if (compare(o, theList[mid]) > 0){
					low = mid + 1;
				}
				else{
					high = mid;
				}
			}			
			return low;
		}	
		
	}

	/**
	 * This method converts the array to a string in order to aid in testing and visibility.
	 */
	@Override
	public String toString(){
		
		// Create a string to store the array values in it
		String toPrint = "";
		
		// Save all array values to the string
		for(int i = 0; i < occupiedElements; i++){
			if(toPrint.equals("")){
				toPrint = theList[i] + " ";
			}
			else{
				toPrint += theList[i] + " ";
			}
		}
		return toPrint;
	}
	
	/**
	 * The purpose of this method is to determine how to compare two objects, either with comparable or comparator
	 * @param x - the first object you wish to compare
	 * @param y - the second object you wish to compare
	 * @return the int value of the comparison test
	 */
	public int compare(E x, E y){
		// If no comparator was given when object was created, use the comparable interface
		if(theComparator == null){
			Comparable<? super E> a =  (Comparable<? super E>) x;
			Comparable<? super E> b =  (Comparable<? super E>) y;
			return a.compareTo((E) b);
		}
		// Use the user defined comparator interface
		else{
			return theComparator.compare(x, y);
		}
	}
	
	/**
	 * This class sets defines how the iterator is supposed to perform in this given class. It overrides all the
	 * methods from the iterator interface.
	 * @author Robert Weischedel && Makenzie Elliott
	 */
	protected class TheIterator implements Iterator<E>
	{
		// The location that the iterator is at
		int position;

		/**
		 * Creates a new TheIterator object
		 */
		public TheIterator()
		{
			position = 0;
		}

		/**
		 * Checks and sees if there is another value in the thing being iterated through
		 */
		public boolean hasNext()
		{
			if (position <= occupiedElements){
				return true;
			}
			return false;
		}

		/**
		 * Retrieves the next object in the the thing being iterated through
		 */
		public E next()
		{
			E current = theList[position];
			position++;
			return current;
		}

		/**
		 * This one remains unimplemented because we didn't need it for this assignment
		 */
		public void remove()
		{
		}

	}

}
