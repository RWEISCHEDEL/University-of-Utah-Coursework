package assignment6;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * This class is a series of JUnit tests created to test the MyLinkedList class
 * and its associated methods. Some methods such as get, getFirst, getLast, and
 * size were used extensively in our other method tests. So for those methods we
 * didn't test them as extensively with in their own methods because they were
 * properly tested and exercised in all the other methods. All the other methods
 * were tested several times (at least three) on a single MyLinkedList object
 * linked list.
 * 
 * @author Robert Weischedel and Tanner Martin
 *
 */
public class MyLinkedListTest {
	/**
	 * This test is the first of the tests if the add method on a linked list.
	 * We first test what happens when you add a new mode in an existing index,
	 * then we test what happens when you add a new node at index 0, and finally
	 * test what happens when a new node to the end of the list. In addition, we
	 * make sure that the list is pushed down correctly as well.
	 */
	@Test
	public void testAdd1() {
		// Create a new MyLinkedList
		MyLinkedList<String> list = new MyLinkedList<String>();

		// Fill the MyLinkedList only using the add method, and add a new
		// element in the middle of the list
		list.add(0, "a");
		list.add(1, "b");
		list.add(2, "c");
		list.add(3, null);
		list.add(4, "e");
		list.add(5, "f");
		list.add(2, "z");

		// Check the current size of the list.
		assertEquals(7, list.size());

		// Check and see if each value was placed in the correct index.
		assertEquals("a", list.get(0));
		assertEquals("b", list.get(1));
		assertEquals("z", list.get(2));
		assertEquals("c", list.get(3));
		assertEquals(null, list.get(4));
		assertEquals("e", list.get(5));
		assertEquals("f", list.get(6));

		// Check and see if the head and tail have been properly set.
		assertEquals("a", list.getFirst());
		assertEquals("f", list.getLast());

		// Add a new element to the first of the list.
		list.add(0, "first");

		// Check and see if list size has changed.
		assertEquals(8, list.size());

		// Check and see if the newly added element is the head.
		assertEquals("first", list.getFirst());

		// Add a new element to the last of the list.
		list.add(8, "last");

		// Check and see if the newly added element is the tail.
		assertEquals("last", list.getLast());

	}

	/**
	 * This test is another test of the add method except this time using
	 * integers. In this test we first add a few integers using the add method,
	 * then perform checks on the data. Then we add a new node at index 1 to see
	 * if the linked list shifts like its supposed to and then perform checks on
	 * the data. In addition, we make sure that the list is pushed down
	 * correctly as well.
	 */
	@Test
	public void testAdd2() {
		// Fill up the MyLinkedList with integer values.
		MyLinkedList<Integer> list2 = new MyLinkedList<Integer>();
		for (int i = 0; i <= 5; i++) {
			list2.add(i, i);
		}

		// Check that the head and tail values are correct.
		assertTrue(list2.getFirst().equals(0));
		assertTrue(list2.getLast().equals(5));

		// this tests our size before add
		assertEquals(6, list2.size());

		// Add a new element that would cause the linked list to shift.
		list2.add(1, 33);

		// this tests our size after the add
		assertEquals(7, list2.size());

		// Check to see if the list has shifted correctly after adding the new
		// element.
		assertTrue(list2.get(0).equals(0));
		assertTrue(list2.get(1).equals(33));
		assertTrue(list2.get(2).equals(1));
		assertTrue(list2.get(3).equals(2));
		assertTrue(list2.get(4).equals(3));
		assertTrue(list2.get(5).equals(4));
		assertTrue(list2.get(6).equals(5));

		// Ensure that the head and tail values haven't changed.
		assertTrue(list2.getFirst().equals(0));
		assertTrue(list2.getLast().equals(5));
	}

	/**
	 * This test tests if the IndexOutOfBoundsException is thrown when the user
	 * tries to add a node at a negative index.
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void testAddException1() {
		// Create and fill a linked list
		MyLinkedList<String> exceptionList = new MyLinkedList<String>();

		exceptionList.add(0, "a");
		exceptionList.add(1, "b");
		exceptionList.add(2, "c");

		// Try and add a node at a negative index.
		exceptionList.add(-1, "swag");
	}

	/**
	 * This test test if the IndexOutOfBoundsException is thrown when the user
	 * tries to add a node outside the current size of the linked list.
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void testAddException2() {
		// Create and fill a linked list
		MyLinkedList<String> exceptionList2 = new MyLinkedList<String>();

		exceptionList2.add(0, "a");
		exceptionList2.add(1, "b");
		exceptionList2.add(2, "c");

		// Try and add a node at a index outside the size of the linked list.
		exceptionList2.add(4, "yolo");
	}

	/**
	 * This method tests our addFirst method. We first test this by using
	 * addFirst to add the first element in the new linked list and then use add
	 * to add additional nodes. Then we use the addFirst method two more times
	 * to see if the linked list head is updated each time as well as the size.
	 * We also ensure the tail remains the same. In addition, we make sure that
	 * the list is pushed down correctly as well.
	 */
	@Test
	public void testAddFirst() {
		MyLinkedList<String> list3 = new MyLinkedList<String>();

		// Use addFirst to add the first element then add additional elements.
		list3.addFirst("a");
		list3.add(1, "b");
		list3.add(2, "c");
		list3.add(3, "d");

		// Check the current size of the list.
		assertEquals(4, list3.size());

		// Check the head and tail values of the list.
		assertEquals("a", list3.getFirst());
		assertEquals("d", list3.getLast());

		// Add a new first element.
		list3.addFirst("wassup");

		// Check the current size of the list.
		assertEquals(5, list3.size());

		// Check to see if the newly added element is the new head element. And
		// that tail hasn't changed.
		assertEquals("wassup", list3.getFirst());
		assertEquals("d", list3.getLast());

		// Check to see if all other values have been pushed when a new head
		// element is added.
		assertEquals("wassup", list3.get(0));
		assertEquals("a", list3.get(1));
		assertEquals("b", list3.get(2));
		assertEquals("c", list3.get(3));
		assertEquals("d", list3.get(4));

		// Add a new first element to the linked list.
		list3.addFirst("swag");

		// Check that the newly added element is the new head and that the tail
		// hasn't changed.
		assertEquals("swag", list3.getFirst());
		assertEquals("d", list3.getLast());
		assertEquals(6, list3.size());
	}

	/**
	 * This method tests our addLast method. We do this by first adding a bunch
	 * of nodes using add, then we call addLast three times, and after each time
	 * we call addLast we perform checks to ensure that the tail has changed as
	 * well as the size of the list. We also see if the list behaves correctly,
	 * only changing the last value and all other values stay at their current
	 * indexes.
	 */
	@Test
	public void testAddLast() {
		// Create a new linked list
		MyLinkedList<String> list4 = new MyLinkedList<String>();

		// Add some elements
		list4.add(0, "a");
		list4.add(1, "b");
		list4.add(2, "c");
		list4.add(3, "d");

		// this tests our size before addLast
		assertEquals(4, list4.size());

		// And a new tail node
		list4.addLast("wassup");

		// This tests our size after the addLast
		assertEquals(5, list4.size());

		// This also tests our getLast method to see if the tail has changed.
		assertEquals("wassup", list4.getLast());

		// Checking that every other element gets pushed forward after addFirst
		assertEquals("a", list4.get(0));
		assertEquals("b", list4.get(1));
		assertEquals("c", list4.get(2));
		assertEquals("d", list4.get(3));
		assertEquals("wassup", list4.get(4));

		// Add another node to the last of the linked list and check the data.
		list4.addLast("yo");
		assertEquals("yo", list4.getLast());
		assertEquals("a", list4.getFirst());
		assertEquals(6, list4.size());

		// Add another node to the last of the linked list and check the data.
		list4.addLast("swag");
		assertEquals("swag", list4.getLast());
		assertEquals("a", list4.getFirst());
		assertEquals(7, list4.size());

	}

	/**
	 * This method tests the remove method to ensure that the list acts
	 * accordingly when you remove elements from different indexes. We first
	 * test by removing an node in the middle of the list, then we test two
	 * times by removing the element at the end of the list. After each removing
	 * we perform checks to ensure the size has changed and the head and tail
	 * have updated correctly.
	 */
	@Test
	public void testRemove() {
		// Create a new linked list/
		MyLinkedList<String> list5 = new MyLinkedList<String>();

		// Fill it with elements.
		list5.add(0, "a");
		list5.add(1, "b");
		list5.add(2, "c");
		list5.add(3, "d");

		// Check current size of linked list.
		assertEquals(4, list5.size());

		// Remove the node at index 1.
		assertEquals("b", list5.remove(1));

		// Ensure the linked list moves up elements properly, the size is
		// correct and head/tail are correct.
		assertEquals("a", list5.get(0));
		assertEquals("c", list5.get(1));
		assertEquals("d", list5.get(2));

		assertEquals("a", list5.getFirst());
		assertEquals("d", list5.getLast());

		assertEquals(3, list5.size());

		// Remove another item and perform checks.
		assertEquals("d", list5.remove(2));

		assertEquals("a", list5.getFirst());
		assertEquals("c", list5.getLast());

		assertEquals(2, list5.size());

		// Remove another item and perform checks.
		assertEquals("c", list5.remove(1));

		assertEquals("a", list5.getFirst());
		assertEquals("a", list5.getLast());

		assertEquals(1, list5.size());
	}

	/**
	 * This test tests if the IndexOutOfBoundsException throws correctly when
	 * the user tries to remove an node that is outside the size of the linked
	 * list.
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void removeException1() {
		// Create a linked list and fill it.
		MyLinkedList<String> exceptionList9 = new MyLinkedList<String>();

		exceptionList9.add(0, "a");
		exceptionList9.add(1, "b");
		exceptionList9.add(2, "c");
		exceptionList9.add(3, "d");

		// Try to remove a node at a index that doesn't exist.
		exceptionList9.remove(4);

	}

	/**
	 * This test tests if the IndexOutOfBoundsException throws correctly when
	 * the user tries to remove an node that is at a negative index.
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void removeException2() {
		// Create a linked list and fill it.
		MyLinkedList<String> exceptionList10 = new MyLinkedList<String>();

		exceptionList10.add(0, "a");
		exceptionList10.add(1, "b");
		exceptionList10.add(2, "c");
		exceptionList10.add(3, "d");

		// Try to remove an element at a negative index.
		exceptionList10.remove(-1);

	}

	/**
	 * This method tests your removeLast method. It does this three times in the
	 * method each time checking to see if the size of the linked list has
	 * decreased and that the tail node has also been updated.
	 */
	@Test
	public void testRemoveLast() {
		// Create a linked list and fill it.
		MyLinkedList<String> list6 = new MyLinkedList<String>();

		list6.add(0, "a");
		list6.add(1, "b");
		list6.add(2, "c");
		list6.add(3, "d");

		// Check the current tail node
		assertEquals("d", list6.getLast());

		// this tests our size before removeLast
		assertEquals(4, list6.size());

		// Remove the last element and see if the list will act accordingly.
		// Also check the size and if the tail has been
		// updated.
		assertEquals("d", list6.removeLast());

		assertEquals("a", list6.get(0));
		assertEquals("b", list6.get(1));
		assertEquals("c", list6.get(2));

		assertEquals("c", list6.getLast());

		assertEquals(3, list6.size());

		// Remove the last element again and perform the same checks
		assertEquals("c", list6.removeLast());
		assertEquals("b", list6.getLast());
		assertEquals("b", list6.get(1));
		assertEquals(2, list6.size());

		// Remove the last element again and perform the same checks
		assertEquals("b", list6.removeLast());
		assertEquals("a", list6.getLast());
		assertEquals("a", list6.getFirst());
		assertEquals("a", list6.get(0));
		assertEquals(1, list6.size());
	}

	/**
	 * This method tests to see if the NoSuchElementException will be thrown if
	 * the removeLast method is called on a empty list.
	 */
	@Test(expected = NoSuchElementException.class)
	public void removeLastException() {
		MyLinkedList<String> exceptionList7 = new MyLinkedList<String>();

		exceptionList7.removeLast();

	}

	/**
	 * This method tests the removeFirst method. It does this by first creating
	 * a linked list with some elements already in it and then calling the
	 * removeFirst method three times. After each call there are checks done to
	 * ensure that the size of the list and head has been updated, and as well
	 * that the elements correctly bump up.
	 */
	@Test
	public void testRemoveFirst() {
		// Create the linked list and fill it.
		MyLinkedList<String> list7 = new MyLinkedList<String>();

		list7.add(0, "a");
		list7.add(1, "b");
		list7.add(2, "c");
		list7.add(3, "d");

		// Check the current head value
		assertEquals("a", list7.getFirst());

		assertEquals(4, list7.size());

		// Remove the first element, check the size and ensure the head has
		// updated. As well ensure that the nodes have
		// correctly bumped up.
		assertEquals("a", list7.removeFirst());

		assertEquals("b", list7.get(0));
		assertEquals("c", list7.get(1));
		assertEquals("d", list7.get(2));

		assertEquals("b", list7.getFirst());

		assertEquals(3, list7.size());

		// Remove the first element again and perform some checks.
		assertEquals("b", list7.removeFirst());
		assertEquals("c", list7.getFirst());
		assertEquals("c", list7.get(0));
		assertEquals(2, list7.size());

		// Remove the first element again and perform some checks.
		assertEquals("c", list7.removeFirst());
		assertEquals("d", list7.getFirst());
		assertEquals("d", list7.get(0));
		assertEquals(1, list7.size());

	}

	/**
	 * This method tests to see if the NoSuchElementException will be thrown if
	 * the removeFirst method is called on a empty list.
	 */
	@Test(expected = NoSuchElementException.class)
	public void removeFirstException() {
		MyLinkedList<String> exceptionList8 = new MyLinkedList<String>();

		exceptionList8.removeFirst();

	}

	/**
	 * This method tests the getFirst method, since we use this method
	 * extensively throughout the other tests we felt that it wasn't necessary
	 * to test it very much more.
	 */
	@Test
	public void testGetFirst() {
		// Create the linked list and fill it.
		MyLinkedList<String> list8 = new MyLinkedList<String>();

		list8.add(0, "a");
		list8.add(1, "b");
		list8.add(2, "c");
		list8.add(3, "d");

		// Check and see if the first node is the head.
		assertEquals("a", list8.getFirst());
	}

	/**
	 * This method tests to see if the NoSuchElementException will be thrown if
	 * the getFirst method is called on a empty list.
	 */
	@Test(expected = NoSuchElementException.class)
	public void getFirstException() {
		MyLinkedList<String> exceptionList3 = new MyLinkedList<String>();

		exceptionList3.getFirst();

	}

	/**
	 * This method tests the getLast method, since we use this method
	 * extensively throughout the other tests we felt that it wasn't necessary
	 * to test it very much more.
	 */
	@Test
	public void testGetLast() {
		// Create the linked list and fill it.
		MyLinkedList<String> list9 = new MyLinkedList<String>();

		list9.add(0, "a");
		list9.add(1, "b");
		list9.add(2, "c");
		list9.add(3, "d");

		// Check and see if the last node is the tail.
		assertEquals("d", list9.getLast());
	}

	/**
	 * This method tests to see if the NoSuchElementException will be thrown if
	 * the getLast method is called on a empty list.
	 */
	@Test(expected = NoSuchElementException.class)
	public void getLastException() {
		MyLinkedList<String> exceptionList4 = new MyLinkedList<String>();

		exceptionList4.getLast();

	}

	/**
	 * This method tests the get method, since we use this method extensively
	 * throughout the other tests we felt that it wasn't necessary to test it
	 * very much more.
	 */
	@Test
	public void testGet() {
		// Create the linked list and fill it.
		MyLinkedList<String> list9 = new MyLinkedList<String>();

		list9.add(0, "a");
		list9.add(1, "b");
		list9.add(2, "c");
		list9.add(3, "d");

		// Check and see if the correct value is at the given index.
		assertEquals("c", list9.get(2));
	}

	/**
	 * This test tests if the IndexOutOfBoundsException throws correctly when
	 * the user tries to get an node that is outside the size of the linked
	 * list.
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void getException1() {
		// Create the linked list and fill it.
		MyLinkedList<String> exceptionList5 = new MyLinkedList<String>();

		exceptionList5.add(0, "a");
		exceptionList5.add(1, "b");
		exceptionList5.add(2, "c");
		exceptionList5.add(3, "d");

		// Try to get a node at a index that doesn't exist.
		exceptionList5.get(4);

	}

	/**
	 * This test tests if the IndexOutOfBoundsException throws correctly when
	 * the user tries to get an node that is at a negative index.
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void getException2() {
		// Create the linked list and fill it.
		MyLinkedList<String> exceptionList6 = new MyLinkedList<String>();

		exceptionList6.add(0, "a");
		exceptionList6.add(1, "b");
		exceptionList6.add(2, "c");
		exceptionList6.add(3, "d");

		// Try to get a node at a negative index.
		exceptionList6.get(-1);

	}

	/**
	 * This method tests the indexOf method to ensure that the first index of
	 * the desired value is returned every time. We perform five tests on the
	 * same linked list that has multiple of the same value in the linked list.
	 */
	@Test
	public void testIndexOf() {
		// Create the linked list and fill it.
		MyLinkedList<String> list10 = new MyLinkedList<String>();

		list10.add(0, "a");
		list10.add(1, "b");
		list10.add(2, "c");
		list10.add(3, "c");
		list10.add(4, "d");
		list10.add(5, "d");

		// Ensure that the first index of the desired value is returned every
		// time.
		assertEquals(0, list10.indexOf("a"));
		assertEquals(1, list10.indexOf("b"));
		assertEquals(2, list10.indexOf("c"));
		assertEquals(4, list10.indexOf("d"));
		assertEquals(-1, list10.indexOf("z"));
	}

	/**
	 * This method tests the lastIndexOf method to ensure that the last index of
	 * the desired value is returned every time. We perform four tests on the
	 * same linked list that has multiple of the same value in the linked list.
	 */
	@Test
	public void testLastIndexOf() {
		// Create the linked list and fill it.
		MyLinkedList<String> list11 = new MyLinkedList<String>();

		list11.add(0, "a");
		list11.add(1, "b");
		list11.add(2, "c");
		list11.add(3, "d");
		list11.add(4, "d");
		list11.add(5, "e");
		list11.add(6, "f");
		list11.add(7, "f");
		list11.add(8, "g");

		// Ensure that the last index of the desired value is returned every
		// time.
		assertEquals(0, list11.lastIndexOf("a"));
		assertEquals(4, list11.lastIndexOf("d"));
		assertEquals(7, list11.lastIndexOf("f"));
		assertEquals(-1, list11.indexOf("z"));
	}

	/**
	 * This method tests the isEmpty method, in this version we test to see if
	 * isEmpty does return true on an empty list.
	 */
	@Test
	public void testIsEmpty1() {
		MyLinkedList<String> empty1 = new MyLinkedList<String>();

		assertTrue(empty1.isEmpty());
	}

	/**
	 * This method tests the isEmpty method, in this version we test to see if
	 * isEmpty does return false on a non-empty list.
	 */
	@Test
	public void testIsEmpty2() {
		MyLinkedList<String> empty2 = new MyLinkedList<String>();

		empty2.addFirst("a");

		assertFalse(empty2.isEmpty());
	}

	/**
	 * This method tests the toArray method. We make an object array and fill
	 * the linked list with the same values, then we check and see if the two
	 * arrays are equal.
	 */
	@Test
	public void testToArray() {
		// Create a array to compare to the returned array.
		Object[] arrayToEqual = { "a", "b", "c", "d" };

		// Create the linked list and fill it with the same values of the
		// previous array.
		MyLinkedList<String> list12 = new MyLinkedList<String>();
		list12.add(0, "a");
		list12.add(1, "b");
		list12.add(2, "c");
		list12.add(3, "d");

		// Check for equality
		assertTrue(Arrays.deepEquals(arrayToEqual, list12.toArray()));
	}

	/**
	 * This method test the clear method. We used a filled linked list and check
	 * its size, then we clear it and check its size.
	 */
	@Test
	public void testClear() {

		// Create the linked list and fill it.
		MyLinkedList<String> clearList = new MyLinkedList<String>();
		clearList.add(0, "a");
		clearList.add(1, "b");
		clearList.add(2, "c");
		clearList.add(3, "d");

		// Check the size
		assertEquals(4, clearList.size());

		// Clear the list
		clearList.clear();

		// Check the size
		assertEquals(0, clearList.size());

	}

	/**
	 * This method tests the size method, since we use this method extensively
	 * throughout the other tests we felt that it wasn't necessary to test it
	 * very much more.
	 */
	@Test
	public void testSize() {
		MyLinkedList<String> sizeList = new MyLinkedList<String>();
		sizeList.add(0, "a");
		sizeList.add(1, "b");
		sizeList.add(2, "c");
		sizeList.add(3, "d");

		assertEquals(4, sizeList.size());
	}
}
