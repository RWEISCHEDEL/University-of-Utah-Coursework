package assignment6;

import java.util.NoSuchElementException;

/**
 * This class allows users to create MyLinkedList Objects that act as doubly
 * linked lists. Contained within this class is in additional private class
 * called Node which creates the new link or node objects that make up linked
 * lists. Also contained within this class is several methods including but not
 * limited to add, remove, and get to allow the user of the class to effectively
 * and efficiently interact with the MyLinkedList doubly linked list object.
 * This class is also designed to allow various generic input types in the
 * link/node objects.
 * 
 * @author Robert Weischedel and Tanner Martin
 *
 * @param <E>
 */
public class MyLinkedList<E> implements List<E> {
	/**
	 * This secondary class creates the link or node objects that make up a
	 * doubly linked list. It is a private class so that the nodes can't be
	 * accessed directly by another program creating MyLinkedList objects. But
	 * we did declare the member varaibles public so that they can be accessed
	 * by the super class MyLinkedList
	 * 
	 * @author Robert Weischedel and Tanner Martin
	 *
	 */
	private class Node {
		// Create member variables to hold the information for the data, and
		// references to the previous and next nodes
		public E data;
		public Node previous;
		public Node next;

		/**
		 * Create a new node object to add to MyLinkedList
		 * 
		 * @param data
		 *            - The information stored in the node, it is generic so it
		 *            can store many different types or objects
		 * @param previous
		 *            - The node that is before this new node being created.
		 * @param next
		 *            - The node that is after this new node being created.
		 */
		public Node(E data, Node previous, Node next) {
			// Setting the data to the new node
			this.data = data;
			this.previous = previous;
			this.next = next;
		}
	}

	// Private member variables used to hold the references to the head/tail of
	// the MyLinkedList and also the size of the MyLinkedList
	private Node head;
	private Node tail;
	private int size;

	/**
	 * Serves as the constructor for the MyLinkedList. It calls the clear method
	 * to set the head and tail to null and reset the size.
	 */
	public MyLinkedList() {
		clear();
	}

	/**
	 * This method adds a new node object to index 0 in the MyLinkedList object
	 * and sets it the head. If there is no head it just creates and stores the
	 * new node as the head and tail, if there it it makes the new node the head
	 * and moves the old head up the list to index 1.
	 */
	@Override
	public void addFirst(E element) {

		// If the list is empty create a new node object and set it as both the
		// head and tail object.
		if (size == 0) {
			// Create the new head node
			head = new Node(element, null, head);

			// Set tail to the head
			tail = head;

			// Increment the size
			size++;
		}
		// If the list has more than one object create a new node object and set
		// it to head.
		else {
			// Create the new node
			Node newNode = new Node(element, null, head);

			// Set the old head to point its previous to the new head object
			head.previous = newNode;

			// Set the head to the new node
			head = newNode;

			// Increment the size
			size++;
		}

	}

	/**
	 * This method adds a new node to the last position of the list or index ==
	 * size. It sets this new node to tail.
	 */
	@Override
	public void addLast(E o) {
		// If the size is equal to 0, call addFirst.
		if (size == 0) {
			addFirst(o);
		}
		// Create a new node and set it to the new tail.
		else {
			// Create new node
			Node newNode = new Node(o, tail, null);

			// Reset tail to new node
			tail.next = newNode;
			tail = newNode;

			// Increment size of linked list.
			size++;
		}
	}

	/**
	 * This method adds a new node with the desired element at the desired
	 * index. The old node that that index will be pushed up to index + 1. If
	 * the index is 0 or equal to size this method will call the the appropriate
	 * addFirst and addLast methods as well. This method accomplished adding by
	 * severing the links between the current and the previous node at that
	 * index and then linking them up with the newly created node
	 * 
	 * @throws - IndexOutOfBoundsException when a invalid index is brought in.
	 * @param index
	 *            - The desired location you wish to add a new node.
	 * @param element
	 *            - The desired generic object/ value you wish to add into the
	 *            newly added node.
	 */
	@Override
	public void add(int index, E element) throws IndexOutOfBoundsException {
		// Check for correct index size
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}

		// If index is 0, call the addFirst method.
		if (index == 0) {
			addFirst(element);
		}
		// If index is equal to size call the addLast method.
		else if (index == size) {
			addLast(element);
		}

		// Find the current node at the index and add the new node to that index
		else {

			// Find the current node at that index
			Node current = getNode(index);

			// Set its data, previous and next values
			Node newNode = new Node(element, current.previous, current);

			// Change the previous current to point to new current
			current.previous.next = newNode;
			current.previous = newNode;

			// Increment the size of the linked list
			size++;
		}

	}

	/**
	 * This method is used to find and return the node at the desired index. It
	 * doesn't do any error checking on the index value as this method is never
	 * declared directly, but is called in several other methods in the class.
	 * It finds the node by checking the value of the index. If the index value
	 * is less than half the size of the list, this method begins searching at
	 * the head. If the index is greater it begins searching at the tail.
	 * 
	 * @param index
	 *            - The index value of which node you wish to find and return.
	 * @return - The Node object at the desired index value.
	 */
	private Node getNode(int index) {
		// Create a node p to act as a place holder.
		Node p;

		// If the index is less than half of the size of the list start
		// searching from the head of the linked list.
		if (index <= size / 2) {
			// Set the place holder to head
			p = head;

			// Begin looping from the head up till the desired index.
			for (int i = 0; i < index; i++) {
				p = p.next;
			}
		}
		// If the index is greater than half the size of the list start
		// searching from the tail of the linked list.
		else {
			// Set the place holder to tail
			p = tail;

			// Begin loop from the tail down till the desired index.
			for (int i = size - 1; i > index; i--) {
				p = p.previous;
			}
		}

		// Retrun the node at the desired location.
		return p;

	}

	/**
	 * This method returns the value of the head node or the first node in the
	 * linked list.
	 * 
	 * @throws - NoSuchElementException if the size of the list is empty.
	 * @return - The generic object/value contained within the head node.
	 */
	@Override
	public E getFirst() throws NoSuchElementException {
		// If the list is empty throw a NoSuchElementException exception.
		if (size == 0) {
			throw new NoSuchElementException();
		}

		// Return the data found in the head node.
		return (E) head.data;
	}

	/**
	 * This method returns the value of the tail node or last node in the linked
	 * list.
	 * 
	 * @throws - NoSuchElementException if the size of the list is empty.
	 * @return - The generic object/value contained within the tail node.
	 */
	@Override
	public E getLast() throws NoSuchElementException {
		// If the list is empty throw a NoSuchElementException exception.
		if (size == 0) {
			throw new NoSuchElementException();
		}

		// Return the data found in the tail node.
		return (E) tail.data;
	}

	/**
	 * This method returns the value of the node at the given index. It uses the
	 * getNode method to find the node at that index value.
	 * 
	 * @throws - IndexOutOfBoundsException when a invalid index is brought in.
	 * @return - The generic object/value contained within the desired node.
	 */
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		// Check for valid index input, if not throw a
		// IndexOutOfBoundsException.
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		// Call the getNode method and return the data of that node.
		return (E) getNode(index).data;
	}

	/**
	 * This method removes the first element in the linked list or head and
	 * returns the data in that node. It also sets the node next to the head as
	 * the new tail.
	 * 
	 * @throws - NoSuchElementException if the list is empty
	 * @return - The generic object/value contained in the head node.
	 */
	@Override
	public E removeFirst() throws NoSuchElementException {
		// If linked list is empty throw a NoSuchElementException.
		if (size == 0) {
			throw new NoSuchElementException();
		}

		// Retrieve the data from the head node.
		E returnData = (E) head.data;

		// Reset the head to the heads next and set the new heads previous to
		// null.
		head = head.next;
		head.previous = null;

		// Decrement the size of the list
		size--;

		// Return the data from the old head.
		return returnData;
	}

	/**
	 * This method removes the last element in the linked list or tail and
	 * returns the data in that node. It also sets the node previous to the tail
	 * as the new tail.
	 * 
	 * @throws - NoSuchElementException if the list is empty
	 * @return - The generic object/value contained in the tail node.
	 */
	@Override
	public E removeLast() throws NoSuchElementException {
		// If linked list is empty throw a NoSuchElementException.
		if (size == 0) {
			throw new NoSuchElementException();
		}

		// Retrieve the data from the tail node.
		E returnData = (E) tail.data;

		// Reset the tail to the tails previous and set the new tails next to
		// null.
		tail = tail.previous;
		//tail.next = null;

		// Decrement the size of the list.
		size--;

		// Return the data from the old tail.
		return returnData;
	}

	/**
	 * This method removes the node at the desired index and returns the value
	 * that was at that node. If the index is 0 or equals the size then the
	 * appropriate removeFirst and removeLast methods are called. This method
	 * accomplishes this by first getting the method at that index saving the
	 * data stored within it and then reseting the nodes before and after it to
	 * each other.
	 *
	 * @throws - IndexOutOfBoundsException an invalid index is brought in.
	 * @return - The generic object/value contained in the removed node.
	 */
	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		// Check for valid index input, if not throw a
		// IndexOutOfBoundsException.
		if (index < 0 || index >= size || size == 0) {
			throw new IndexOutOfBoundsException();
		}

		// If the index is 0 call removeFirst instead.
		if (index == 0) {
			return removeFirst();

		}
		// If the index is equal to size call removeLast method.
		else if (index == size - 1) {
			return removeLast();
		} else {
			// Get the node you wish to remove at the index.
			Node removeNode = getNode(index);

			// Retrieve the data from that node you wish to remove
			E returnData = removeNode.data;

			// Set the previous and next of nodes before and after the node you
			// wish to remove to each other.
			removeNode.next.previous = removeNode.previous;
			removeNode.previous.next = removeNode.next;

			// Decrement the size
			size--;

			// Return the data in the removed node.
			return returnData;
		}
	}

	/**
	 * This method returns the first index of an desired element in the linked
	 * list. This is done by starting at the head and searching through the
	 * linked list till the desired element is found. Or -1 is returned if the
	 * element isn't found.
	 * 
	 * @return - A integer value stating the first index of the desired element.
	 */
	@Override
	public int indexOf(E element) {
		// Loop through the linked list starting at the beginning till you reach
		// the desired element.
		for (int i = 0; i < size; i++) {
			// Use getNode to pull the node at each index and compare the data
			// of that node with the desired element.
			if (getNode(i).data.equals(element)) {
				// Return the index of the element
				return i;
			}
		}

		// Return -1 if element isn't found.
		return -1;
	}

	/**
	 * This method returns the last index of an desired element in the linked
	 * list. This is done by starting at the tail and searching through the
	 * linked list till the desired element is found. Or -1 is returned if the
	 * element isn't found.
	 * 
	 * @return - A integer value stating the last index of the desired element.
	 */
	@Override
	public int lastIndexOf(E element) {
		// Loop through the linked list starting at the end till you reach the
		// desired element.
		for (int i = size - 1; i >= 0; i--) {
			// Use getNode to pull the node at each index and compare the data
			// of that node with the desired element.
			if (getNode(i).data.equals(element)) {
				// Return the index of the element.
				return i;
			}
		}

		// Return -1 if element isn't found.
		return -1;
	}

	/**
	 * This method returns the current size of the linked list.
	 * 
	 * @param - An integer value stating the current size of the linked list
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * This method checks and sees if the linked list is empty or not. It does
	 * this by checking the size of the linked list.
	 * 
	 * @return - A boolean stating if the linked list is empty or not.
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * This method clears the entire MyLinedList object by setting the head and
	 * tail values to null, effectively cutting off all references to the other
	 * node objects, so that they can be cleaned up by Java garbage collection.
	 */
	@Override
	public void clear() {
		// Set the head and tail to both null.
		/*
		 * head = new Node(null, null, null); tail = new Node(null, head, null);
		 * head.next = tail;
		 */

		head = null;
		tail = null;

		// Set the size of the array to zero.
		size = 0;
	}

	/**
	 * This method returns and object array that is filled with all the elements
	 * in the linked list. It goes through each node object, pulls the data from
	 * it and then stores it into an array. Goes from head to tail.
	 * 
	 * @return - Object array containing all data from the linked list
	 */
	@Override
	public Object[] toArray() {
		// Set a node to so we don't alter head.
		Node p = head;

		// Create the object array to return.
		Object[] returnArray = new Object[size];

		// Fill the array with the data from the nodes
		for (int i = 0; i < size; i++) {
			returnArray[i] = p.data;
			p = p.next;
		}

		// Return the array
		return returnArray;
	}

}
