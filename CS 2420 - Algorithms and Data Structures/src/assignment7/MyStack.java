package assignment7;

import assignment6.MyLinkedList;
import java.util.NoSuchElementException;

/** Represents a generic stack (first in, last out). This implementation of Stack uses a LinkedList data structure to contain all the data and act as the stack. The LinkedList we used
 * in particular was the MyLinkedList implementation that we created for our last assignment. MyLinkedList code can be found in package 6. This class contains all methods for the 
 * MyStack class as well including, clear, isEmpty, peek, pop, push and size. This class is mainly used in our BalancedSymbolChecker class to act as a stack as a 
 * pusedo-complier. This class is generic as well, so it can handle a wide variety of primitives and objects. Implementation details and comments of a method that is from 
 * MyLinkedList can be found in the MyLinkedList.java class page.
 * 
 * @author Robert Weischedel and Tanner Martin
 * 
 * @param <E> - The generic type of element stored into the stack.
 *    
 */
public class MyStack<E>
{

	// Create a private member variable to store the MyLinkedList object we are using for stack.
	private MyLinkedList<E> stack;

	/**
	 * This serves as the constructor of the stack, by creating a new MyLinkedList Object.
	 */
	public MyStack()
	{
		stack = new MyLinkedList<E>();
	}

	/**
	 * This method clears all items from the stack or stack size equal to zero. It does this by using MyLinkedList's clear method.
	 */
	public void clear()
	{
		stack.clear();
	}

	/**
	 * This method returns a true or false if the stack is empty or not. It does this by using MyLinkedList's isEmpty method.
	 * 
	 * @return - a boolean determining if the stack is empty or not.
	 */
	public boolean isEmpty()
	{
		return stack.isEmpty();
	}

	/**
	 * This method returns the value at the top of the stack. It accomplishes this by using the getLast method from MyLinkedList. It also throws an 
	 * NoSuchElementException if the stack is empty.
	 * 
	 * @return - A generic type value that is found on the top of the stack.
	 * @throws NoSuchElementException - Thrown if stack is empty.
	 */
	public E peek() throws NoSuchElementException
	{
		// check if stack is empty.
		if(stack.isEmpty())
		{
			throw new NoSuchElementException();
		}
		// Return the top item of stack.
		return stack.getLast();
	}

	/**
	 * This method returns and removes the top element at the top of the stack. It accomplishes this by using the the removeLast method from MyLinkedlist. It also
	 * throws a NoSuchElementException if the stack is empty.
	 * 
	 * @return - A generic type value that is found on the top of the stack.
	 * @throws NoSuchElementException - Thrown if stack is empty.
	 */
	public E pop() throws NoSuchElementException
	{
		// check if stack is empty.
		if(stack.isEmpty())
		{
			throw new NoSuchElementException();
		}
		// Return and remove the top item of stack.
		return stack.removeLast();
	}

	/**
	 * This method pushes the inputed item onto the top of the stack. It accomplishes this by using the addLast method from MyLinkedList.
	 * 
	 * @param item - The generic item that you wish to add to the top of the stack.
	 */
	public void push(E item)
	{
		stack.addLast(item);
	}

	/**
	 * This method returns the current size of the stack or how many items are in the stack. It accomplishes this by using the size method from MyLinkedList.
	 * @return
	 */
	public int size()
	{
		return stack.size();
	}
}