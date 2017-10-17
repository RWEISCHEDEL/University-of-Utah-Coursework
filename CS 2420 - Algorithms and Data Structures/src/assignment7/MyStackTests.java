package assignment7;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * This class is a series of JUnit tests designed to test the various methods that are implemented by the MyStack class. Some methods like push, size and isEmpty are
 * used extensively in the testing of the other methods, so we did not feel it necessary to test them as much in their own JUnit test methods.
 * 
 * @author Robert Weischedel and Tanner Martin
 *
 */
public class MyStackTests
{
	/**
	 * This method tests the clear method from MyStack. It first pushes four items, confirms the stack size, the clears the list. It uses the isEmpty method and 
	 * size method again to test if the list is empty.
	 */
	@Test
	public void testClear1()
	{
		// Create a MyStack of ints and fill it.
		MyStack<Integer> clear1 = new MyStack<Integer>();
		clear1.push(1);
		clear1.push(2);
		clear1.push(3);
		clear1.push(4);
		
		// Check the size of MyStack
		assertTrue(clear1.size() == 4);

		// Clear the MyStack
		clear1.clear();

		// Check and see if the MyStack is empty.
		assertTrue(clear1.size() == 0);
		assertTrue(clear1.isEmpty());
	}
	
	/**
	 * This method tests the clear method from MyStack. It first pushes four items, checks the stack size, then clears the list. Then it pushes two more items, checks
	 * the size of the stack and clears it again. It also uses the isEmpty method again to test if the list is empty.
	 */
	@Test
	public void testClear2(){
		
		// Create a MyStack of Strings and fill it.
		MyStack<String> clear2 = new MyStack<String>();
		clear2.push("a");
		clear2.push("b");
		clear2.push("c");
		clear2.push("d");
		
		// Check the size of MyStack
		assertTrue(clear2.size() == 4);

		// Clear the MyStack
		clear2.clear();

		// Check and see if the MyStack is empty.
		assertTrue(clear2.size() == 0);
		assertTrue(clear2.isEmpty());
		
		// Push more items on the empty stack.
		clear2.push("a");
		clear2.push("b");
		
		// Check the size of MyStack
		assertTrue(clear2.size() == 2);
		
		// Clear the MyStack
		clear2.clear();

		// Check and see if the MyStack is empty.
		assertTrue(clear2.size() == 0);
		assertTrue(clear2.isEmpty());	
	}

	/**
	 * This method tests the peek method from MyStack. It does this by first creating a MyStack and then pushing four values into it. It then first checks the size of
	 * the MyStack, peeks at the top value, and then pops the top value and does this process till you reach only one element in the stack.
	 */
	@Test
	public void testPeek1()
	{
		// Create a MyStack of ints and fill it.
		MyStack<Integer> peek1 = new MyStack<Integer>();
		peek1.push(1);
		peek1.push(2);
		peek1.push(3);
		peek1.push(4);
		
		// In repeated order: First check size of MyStack, Second peek at element at top of MyStack, Third pop the top value.
		assertTrue(peek1.size() == 4);

		assertTrue(peek1.peek().equals(4));
		
		assertTrue(peek1.pop() == 4);
		
		assertTrue(peek1.size() == 3);
		
		assertTrue(peek1.peek().equals(3));
		
		assertTrue(peek1.pop() == 3);
		
		assertTrue(peek1.size() == 2);
		
		assertTrue(peek1.peek().equals(2));
		
		assertTrue(peek1.pop() == 2);
		
		assertTrue(peek1.size() == 1);
		
		assertTrue(peek1.peek().equals(1));
		
	}
	
	/**
	 * This method tests the peek method in MyStack. It does this by first creating a MyStack and pushing four elements on it. Then it checks the size of the stack 
	 * and it peeks at the top element. Then we push two more elements on the stack and then peek at the top element. After that we pop the top element and then peek
	 * at the new top element till we reach the original sized list of four items.
	 */
	@Test
	public void testPeek2()
	{
		// Create a MyStack of Strings and fill it.
		MyStack<String> peek2 = new MyStack<String>();
		peek2.push("a");
		peek2.push("b");
		peek2.push("c");
		peek2.push("d");
		
		// Check the size of MyStack
		assertTrue(peek2.size() == 4);

		// Peek at top value of stack
		assertTrue(peek2.peek().equals("d"));
		
		// Push two new elements on the stack.
		peek2.push("e");
		peek2.push("f");
		
		// Peek and then pop the top element till you reach the original sized set of 4 elements.
		assertTrue(peek2.peek().equals("f"));
		
		assertEquals("f", peek2.pop());
		
		assertTrue(peek2.peek().equals("e"));
		
		assertEquals("e", peek2.pop());
		
		assertTrue(peek2.peek().equals("d"));
		
		assertTrue(peek2.size() == 4);
	}
	
	/**
	 * This method tests if the NoSuchElementException is thrown when the user tries to peek into an empty list.
	 */
	@Test(expected = NoSuchElementException.class)
	public void testPeekException1()
	{
		MyStack<String> peekException1 = new MyStack<String>();
		
		peekException1.peek();
	}
	
	/**
	 * This method tests if the NoSuchElementException is thrown on when the user tries to peek at a list that once had an item then the item
	 * was popped making it now empty.
	 */
	@Test(expected = NoSuchElementException.class)
	public void testPeekException2()
	{
		MyStack<String> peekException2 = new MyStack<String>();
		
		peekException2.push("a");
		
		peekException2.pop();
		
		peekException2.peek();
		
		
	}

	/**
	 * This method tests the pop method from the MyStack class. It does this by pushing four elements onto it, then it pops two elements from
	 * the stack. Each time checking the size and peeking at the top element.
	 */
	@Test
	public void testPop1()
	{
		// Create a MyStack and push four ints into it.
		MyStack<Integer> pop1 = new MyStack<Integer>();
		pop1.push(1);
		pop1.push(2);
		pop1.push(3);
		pop1.push(4);
		
		// Check the size of the stack.
		assertEquals(4, pop1.size());

		// Pop the top element, peek at the new top element and check the size.
		pop1.pop();

		assertTrue(pop1.peek().equals(3));
		
		assertEquals(3, pop1.size());

		pop1.pop();

		assertTrue(pop1.peek().equals(2));
		
		assertEquals(2, pop1.size());
	}

	/**
	 * This method tests the pop method in the MyStack class. It does this by first creating a MyStack and pushing six elements onto it. And 
	 * then we pop each element in the list, each time checking the size of the list and also peeking at the new top element. It does this till
	 * it reaches only one element in the stack.
	 */
	@Test
	public void testPop2()
	{
		// Create a MyStack and push six Strings onto it.
		MyStack<String> pop2 = new MyStack<String>();
		pop2.push("a");
		pop2.push("b");
		pop2.push("c");
		pop2.push("d");
		pop2.push("e");
		pop2.push("f");
		
		// Check the size
		assertEquals(6, pop2.size());
		
		// Pop, peek and check the size of the stack till you reach stack size of 1.
		assertEquals("f", pop2.pop());

		assertTrue(pop2.peek().equals("e"));
		
		assertEquals(5, pop2.size());
		
		assertEquals("e", pop2.pop());

		assertTrue(pop2.peek().equals("d"));
		
		assertEquals(4, pop2.size());
		
		assertEquals("d", pop2.pop());

		assertTrue(pop2.peek().equals("c"));
		
		assertEquals(3, pop2.size());
		
		assertEquals("c", pop2.pop());

		assertTrue(pop2.peek().equals("b"));
		
		assertEquals(2, pop2.size());
		
		assertEquals("b", pop2.pop());

		assertTrue(pop2.peek().equals("a"));
		
		assertEquals(1, pop2.size());
		
		
	}
	
	/**
	 * This method tests to see if the NoSuchElementList will be thrown if a user tries to pop and empty list. This time we just use and empty
	 * stack.
	 */
	@Test(expected = NoSuchElementException.class)
	public void testPopException1()
	{
		MyStack<String> popException1 = new MyStack<String>();
		
		popException1.pop();
	}
	
	/**
	 * This method tests to see if the NoSuchElementException will be thrown if a user tries to pop an empty stack. This time we add an element
	 * pop it and then try and pop the top element again.
	 */
	@Test(expected = NoSuchElementException.class)
	public void testPopException2()
	{
		// Create a MyStack and push one String into it.
		MyStack<String> popException2 = new MyStack<String>();
		popException2.push("a");
		
		// Try and pop the same element twice.
		popException2.pop();
		
		popException2.pop();
		
		
	}
	
	/**
	 * This method tests the push method from the MyStack class. It does this by first creating a new class and pushing two elements into it,
	 * then we check the size and peek at the top element. Then we push two new elements on it and perform the same checks.We didn't test this 
	 * method as much because we use it in all other methods in this JUnit test class.
	 */
	@Test
	public void testPush1()
	{
		// Create a MyStack and push two ints into it.
		MyStack<Integer> push1 = new MyStack<Integer>();
		push1.push(1);
		push1.push(2);

		// Check the size of the stack and peek at the top element.
		assertEquals(2, push1.size());
		assertTrue(push1.peek().equals(2));
		
		// Push two more elements on the stack and perform the same checks.
		push1.push(3);
		push1.push(4);
		
		assertEquals(4, push1.size());
		
		assertTrue(push1.peek().equals(4));
	}
	
	/**
	 * This method tests the push method from the MyStack class. It does this by first creating a new stack and pushing two elements onto it,
	 * then we check the size of it and peek at the top element to ensure it is the last element we addded. Then we push four more elements and 
	 * we perform all the same checks. We didn't test this method as much because we use it in all other methods in this JUnit test class.
	 */
	@Test
	public void testPush2(){
		
		// Create a MyStack and push two strings into it.
		MyStack<String> stack6 = new MyStack<String>();
		stack6.push("a");
		stack6.push("b");
		
		// Check the size and peek at the top element.
		assertEquals(2, stack6.size());

		assertTrue(stack6.peek().equals("b"));
		
		// Two more times we push new two strings, check the size and peek at the top element.
		stack6.push("c");
		stack6.push("d");
		
		assertEquals(4, stack6.size());
		
		assertTrue(stack6.peek().equals("d"));
		
		stack6.push("w");
		stack6.push("z");
		
		assertEquals(6, stack6.size());
		
		assertTrue(stack6.peek().equals("z"));
		
		
	}

	/**
	 * This method tests the size method from the MyStack class. It does this by checking the size of a stack with two elements, then we add
	 * another element and check the size, and then we pop that last element and check the size to see if it is back to two elements. This 
	 * method is used in several other methods to aid in testing and we felt that we didn't need anymore testing on this method.
	 */
	@Test
	public void testSize()
	{
		// Create a MyStack of ints and push two ints into the stack.
		MyStack<Integer> size = new MyStack<Integer>();
		size.push(1);
		size.push(2);
		
		// Check the size
		assertEquals(2, size.size());
		
		// Push a new int on the stack.
		size.push(3);
		
		// Check the size
		assertEquals(3, size.size());
		
		// Pop the last int
		size.pop();
		
		// Check the size
		assertEquals(2, size.size());
	}
	
	/**
	 * This method tests the isEmpty method in the MyStack class. It does this by using isEmpty on a empty stack to see if returns
	 * true. This method is used in several other methods to aid in testing and we felt that we didn't need anymore testing on this method.
	 */
	@Test
	public void testIsEmpty1()
	{
		// Create a empty stack and add no ints to it.
		MyStack<Integer> empty1 = new MyStack<Integer>();

		// Ensure that it is empty
		assertTrue(empty1.size() == 0);
		assertTrue(empty1.isEmpty());
	}
	
	/**
	 * This method tests the isEmpty method in the MyStack class. It does this by using isEmpty on a not empty stack to see if returns
	 * false. This method is used in several other methods to aid in testing and we felt that we didn't need anymore testing on this method.
	 */
	@Test
	public void testIsEmpty2()
	{
		// Create a stack and add one int to it.
		MyStack<Integer> empty2 = new MyStack<Integer>();
		empty2.push(1);
		
		// Ensure that the stack is not empty.
		assertTrue(empty2.size() == 1);
		assertFalse(empty2.isEmpty());
	}

}
