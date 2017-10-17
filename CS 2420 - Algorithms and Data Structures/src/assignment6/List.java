package assignment6;

import java.util.NoSuchElementException;

/**
 * Template for a list.
 * 
 * @author Dr. Meyer
 * 
 * @param <E>
 *            -- the type of elements contained in the list
 */
public interface List<E> {

	/**
	 * Inserts the specified element at the beginning of the list.
	 * O(1) for a doubly-linked list.
	 */
	void addFirst(E element);

	/**
	 * Inserts the specified element at the end of the list.
	 * O(1) for a doubly-linked list.
	 */
	void addLast(E o);

	/**
	 * Inserts the specified element at the specified position in the list.
	 * Throws IndexOutOfBoundsException if index is out of range (index < 0 || index > size())
	 * O(N) for a doubly-linked list.
	 */
	void add(int index, E element) throws IndexOutOfBoundsException;

	/**
	 * Returns the first element in the list.
	 * Throws NoSuchElementException if the list is empty.
	 * O(1) for a doubly-linked list.
	 */
	E getFirst() throws NoSuchElementException;
	
	/**
	 * Returns the last element in the list.
	 * Throws NoSuchElementException if the list is empty.
	 * O(1) for a doubly-linked list.
	 */
	E getLast() throws NoSuchElementException;
	
	/**
	 * Returns the element at the specified position in the list.
	 * Throws IndexOutOfBoundsException if index is out of range (index < 0 || index >= size())
	 * O(N) for a doubly-linked list.
	 */
	E get(int index) throws IndexOutOfBoundsException;
	
	/**
	 * Removes and returns the first element from the list.
	 * Throws NoSuchElementException if the list is empty.
	 * O(1) for a doubly-linked list.
	 */
	E removeFirst() throws NoSuchElementException;
	
	/**
	 * Removes and returns the last element from the list.
	 * Throws NoSuchElementException if the list is empty.
	 * O(1) for a doubly-linked list.
	 */
	E removeLast() throws NoSuchElementException;
	
	/**
	 * Removes and returns the element at the specified position in the list.
	 * Throws IndexOutOfBoundsException if index is out of range (index < 0 || index >= size())
	 * O(N) for a doubly-linked list.
	 */
	E remove(int index) throws IndexOutOfBoundsException;
	
	/**
	 * Returns the index of the first occurrence of the specified element in the list, 
	 * or -1 if this list does not contain the element.
	 * O(N) for a doubly-linked list.
	 */
	int indexOf(E element);
	
	/**
	 * Returns the index of the last occurrence of the specified element in this list, 
	 * or -1 if this list does not contain the element.
	 * O(N) for a doubly-linked list.
	 */
	int lastIndexOf(E element);
	
	/**
	 * Returns the number of elements in this list.
	 * O(1) for a doubly-linked list.
	 */
	int size();
	
	/**
	 * Returns true if this collection contains no elements.
	 * O(1) for a doubly-linked list.
	 */
	boolean isEmpty();
	
	/**
	 * Removes all of the elements from this list.
	 * O(1) for a doubly-linked list.
	 */
	void clear();
	
	/**
	 * Returns an array containing all of the elements in this list in proper sequence 
	 * (from first to last element).
	 * O(N) for a doubly-linked list.
	 */
	Object[] toArray();
}