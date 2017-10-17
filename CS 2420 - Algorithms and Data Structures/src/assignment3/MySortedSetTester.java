package assignment3;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * The purpose of the JUnit test is to provide several test cases for each method and constructor in the MySortedSet
 * Class.
 * @author Robert Weischedel && Makenzie Elliott
 *
 */
public class MySortedSetTester {

	// Create several MySortedSet objects and comparators to test
	private MySortedSet theSet, theSetWithComparator, theSetWithComparatorStrings, theSetWithComparatorDoubles;
	private orderNumbers comparator = new orderNumbers();
	private orderStrings compareStrings = new orderStrings();
	private orderDoubles compareDoubles = new orderDoubles();

	/**
	 * Initialize the sets before the JUnit runs.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		theSet = new MySortedSet();
		
		theSetWithComparator = new MySortedSet(comparator);
		
		theSetWithComparatorStrings = new MySortedSet(compareStrings);

		theSetWithComparatorDoubles = new MySortedSet(compareDoubles);

	}

	/**
	 * This test is not used.
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * This test sees if a set is empty
	 */
	@Test
	public void testMySortedSetEmpty() {
		assertTrue(theSet.isEmpty());
	}

	/**
	 * This test uses a sorted set with integers, to see if the set is sorted correctly.
	 */
	@Test
	public void testMySortedSetWithIntegerElements() {
		theSet.add(10);
		theSet.add(40);
		theSet.add(20);
		theSet.add(10);
		theSet.add(80);
		theSet.add(70);
		theSet.add(10);

		assertEquals(10, theSet.first());
		assertEquals(80, theSet.last());
	}
	
	/**
	 * This test uses a sorted set with no duplicate integers, to see if the set is sorted correctly.
	 */
	@Test
	public void testMySortedSetWithNoDuplicates(){
		
		theSet.add(10);
		theSet.add(10);
		theSet.add(10);
		theSet.add(10);
		theSet.add(20);
		theSet.add(20);
		theSet.add(20);
		
		assertEquals(2, theSet.size());
		
	}
	
	/**
	 * This test uses a sorted set with string, to see if the set is sorted correctly.
	 */
	@Test 
	public void testMySortedSetWithStringElements(){
		
		theSet.add("hello");
		theSet.add("world");
		theSet.add("how");
		theSet.add("are");
		theSet.add("you");
		
		assertEquals("are", theSet.first());
		assertEquals("you", theSet.last());
	}
	
	/**
	 * This test uses a sorted set with doubles, to see if the set is sorted correctly.
	 */
	@Test
	public void testMySortedSetWithDoubles(){
		theSet.add(1.4);
		theSet.add(-3.4);
		theSet.add(-8.9);
		theSet.add(6.0);
		theSet.add(2.0);
		
		assertEquals(-8.9, theSet.first());
		assertEquals(6.0, theSet.last());
	}

	/**
	 * This test uses a sorted set with a comparator object is empty.
	 */
	@Test
	public void testMySortedSetComparatorEmpty() {

		assertTrue(theSetWithComparator.isEmpty());

	}

	/**
	 * This is a Class that implements comparator so we can test the comparator
	 * as a parameter with integers
	 */
	protected class orderNumbers implements Comparator<Integer> {

		// make a different way to compare 
		@Override
		public int compare(Integer o1, Integer o2) {
			return o2 - o1;
		}

	}

	

	/**
	 * This is a Class that implements comparator so we can test the comparator
	 * as a parameter with Strings
	 */
	protected class orderStrings implements Comparator<String> {

		// make a different way to compare 
		public int compare(String o1, String o2) {
					
			return o1.compareTo(o2) * -1;
		}
	}
	
	/**
	 * This is a Class that implements comparator so we can test the comparator
	 * as a parameter with Doubles
	 */
	protected class orderDoubles implements Comparator<Double> {

		// make a different way to compare 
		public int compare(Double o1, Double o2) {
			return (int)(o2 - o1);
		}

	}
	
	/**
	 * This test uses a sorted set with integers, to see if the set is sorted correctly with rules from a comparator.
	 */
	@Test
	public void testMySortedSetComparatorWithIntegers() {

		theSetWithComparator.add(10);
		theSetWithComparator.add(20);
		theSetWithComparator.add(30);
		theSetWithComparator.add(40);
		theSetWithComparator.add(50);
		theSetWithComparator.add(60);
		theSetWithComparator.add(80);

		assertEquals(80, theSetWithComparator.first());
		assertEquals(10, theSetWithComparator.last());

	}
	
	/**
	 * This test uses a sorted set with strings, to see if the set is sorted correctly with rules from a comparator.
	 */
	@Test
	public void testMySortedSetComparatorWithStrings() {

		theSetWithComparatorStrings.add("The");
		theSetWithComparatorStrings.add("Brown");
		theSetWithComparatorStrings.add("Cow");
		theSetWithComparatorStrings.add("Wants");
		theSetWithComparatorStrings.add("A");
		theSetWithComparatorStrings.add("Gal");
		theSetWithComparatorStrings.add("Hello");

		assertEquals("Wants", theSetWithComparatorStrings.first());
		assertEquals("A", theSetWithComparatorStrings.last());

	}
	
	/**
	 * This test uses a sorted set with doubles, to see if the set is sorted correctly with rules from a comparator.
	 */
	@Test
	public void testMySortedSetComparatorWithDoubles() {

		theSetWithComparatorDoubles.add(1.0);
		theSetWithComparatorDoubles.add(2.0);
		theSetWithComparatorDoubles.add(3.5);
		theSetWithComparatorDoubles.add(4.6);
		theSetWithComparatorDoubles.add(5.0);
		theSetWithComparatorDoubles.add(6.9);
		theSetWithComparatorDoubles.add(2.4);

		assertEquals(6.9, theSetWithComparatorDoubles.first());
		assertEquals(1.0, theSetWithComparatorDoubles.last());

	}

	/**
	 * This test sees if the correct comparator is returned from the specified set.
	 */
	@Test
	public void testComparatorIntegers() {
		assertEquals(comparator, theSetWithComparator.comparator());
	}
	
	/**
	 * This test sees if the correct comparator is returned from the specified set.
	 */
	@Test
	public void testComparatorStrings(){
		assertEquals(compareStrings, theSetWithComparatorStrings.comparator());
	}

	/**
	 * This test sees if the correct comparator is returned from the specified set.
	 */
	@Test
	public void testComparatorDoubles(){
		assertEquals(compareDoubles, theSetWithComparatorDoubles.comparator());
	}
	
	/**
	 * This JUnit test, tests the first method with integers.
	 */
	@Test
	public void testFirstWithIntegers() {
		
		theSet.add(10);
		theSet.add(40);
		theSet.add(20);
		theSet.add(10);
		theSet.add(80);
		theSet.add(70);
		theSet.add(10);

		assertEquals(10, theSet.first());		
	}
	
	/**
	 * This JUnit test, tests the first method with strings.
	 */
	@Test
	public void testFirstWithStrings() {
		
		theSet.add("hello");
		theSet.add("world");
		theSet.add("how");
		theSet.add("are");
		theSet.add("you");
		
		assertEquals("are", theSet.first());
		
	}
	
	/**
	 * This JUnit test, tests the first method with doubles.
	 */
	@Test
	public void testFirstWithDoubles() {
		
		theSet.add(1.4);
		theSet.add(-3.4);
		theSet.add(-8.9);
		theSet.add(6.0);
		theSet.add(2.0);
		
		assertEquals(-8.9, theSet.first());
		
	}
	
	/**
	 * This JUnit test, tests the last method with integers.
	 */
	@Test
	public void testLastWithIntegers() {
		
		theSet.add(10);
		theSet.add(40);
		theSet.add(20);
		theSet.add(10);
		theSet.add(80);
		theSet.add(70);
		theSet.add(10);

		assertEquals(80, theSet.last());		
	}
	
	/**
	 * This JUnit test, tests the last method with strings.
	 */
	@Test
	public void testLastWithStrings() {
		
		theSet.add("hello");
		theSet.add("world");
		theSet.add("how");
		theSet.add("are");
		theSet.add("you");
		
		assertEquals("you", theSet.last());
		
	}
	
	/**
	 * This JUnit test, tests the last method with doubles.
	 */
	@Test
	public void testLastWithDoubles() {
		
		theSet.add(1.4);
		theSet.add(-3.4);
		theSet.add(-8.9);
		theSet.add(6.0);
		theSet.add(2.0);
		
		assertEquals(6.0, theSet.last());
		
	}

	/**
	 * This JUnit test, tests the add method with integers.
	 */
	@Test
	public void testAddInteger() {
		
		theSet.add(1);
		assertFalse(theSet.isEmpty());
	}
	
	/**
	 * This JUnit test, tests the add method with strings.
	 */
	@Test
	public void testAddString() {
		
		theSet.add("hello");
		assertFalse(theSet.isEmpty());
	}
	
	/**
	 * This JUnit test, tests the add method with doubles.
	 */
	@Test
	public void testAddDouble() {
		
		theSet.add(1.5);
		assertFalse(theSet.isEmpty());
	}

	/**
	 * This JUnit test, tests the addAll method with integers.
	 */
	@Test
	public void testAddAllWithIntegers() {
		ArrayList<Integer> theList = new ArrayList<Integer>();
		
		theList.add(2);
		theList.add(5);
		theList.add(3);
		
		theSet.addAll(theList);
		
		assertEquals(5, theSet.last());		
		
	}
	
	/**
	 * This JUnit test, tests the addAll method with strings.
	 */
	@Test
	public void testAddAllWithStrings() {
		ArrayList<String> theList = new ArrayList<String>();
		
		theList.add("hello");
		theList.add("this assinment");
		theList.add("was hard");
		
		theSet.addAll(theList);
		
		assertEquals("was hard", theSet.last());		
		
	}
	
	/**
	 * This JUnit test, tests the addAll method with doubles.
	 */
	@Test
	public void testAddAllWithDoubles() {
		ArrayList<Double> theList = new ArrayList<Double>();
		
		theList.add(1.5);
		theList.add(10.0);
		theList.add(5.6);
		
		theSet.addAll(theList);
		
		assertEquals(10.0, theSet.last());		
		
	}

	/**
	 * This JUnit test, tests the clear method
	 */
	@Test
	public void testClear() {
		theSet.add(10);
		theSet.add(40);
		theSet.add(20);
		theSet.add(10);
		theSet.add(80);
		theSet.add(70);
		theSet.add(10);

		theSet.clear();
		
		assertTrue(theSet.isEmpty());

	}
	
	/**
	 * This JUnit test, tests the clear method with a comparator
	 */
	@Test
	public void testClearWithComparator() {
		
		theSetWithComparator.add(10);
		theSetWithComparator.add(20);
		theSetWithComparator.add(30);
		theSetWithComparator.add(40);
		theSetWithComparator.add(50);
		theSetWithComparator.add(60);
		theSetWithComparator.add(80);

		theSet.clear();
		
		assertTrue(theSet.isEmpty());

	}

	/**
	 * This JUnit test, tests the contains method with integers.
	 */
	@Test
	public void testContainsInteger() {
		theSet.add(10);
		theSet.add(40);
		theSet.add(20);
		theSet.add(10);
		theSet.add(80);
		theSet.add(70);
		theSet.add(10);

		assertTrue(theSet.contains(80));
	}
	
	/**
	 * This JUnit test, tests the contains method with strings.
	 */
	@Test
	public void testContainsStrings(){
		
		theSet.add("hello");
		theSet.add("world");
		theSet.add("how");
		theSet.add("are");
		theSet.add("you");
		
		assertTrue(theSet.contains("how"));
	}

	/**
	 * This JUnit test, tests the containsAll method with integers.
	 */
	@Test
	public void testContainsAllIntegers() {
		ArrayList<Integer> theList = new ArrayList<Integer>();
		
		theList.add(2);
		theList.add(5);
		theList.add(3);
		
		theSet.addAll(theList);
		
		assertTrue(theSet.containsAll(theList));	
		
	}
	
	/**
	 * This JUnit test, tests the containsAll method with strings.
	 */
	@Test
	public void testContainsAllStrings() {
		ArrayList<String> theList = new ArrayList<String>();
		
		theList.add("hello");
		theList.add("this assinment");
		theList.add("was hard");
		
		theSet.addAll(theList);
		
		assertTrue(theSet.containsAll(theList));		
		
	}
	
	/**
	 * This JUnit test, tests the containsAll method with doubles.
	 */
	@Test
	public void testContainsAllDoubles() {
		ArrayList<Double> theList = new ArrayList<Double>();
		
		theList.add(1.5);
		theList.add(10.0);
		theList.add(5.6);
		
		theSet.addAll(theList);
		
		assertTrue(theSet.containsAll(theList));	
		
	}

	/**
	 * This JUnit test, tests the isEmpty method
	 */
	@Test
	public void testIsEmptyInteger() {
		theSet.add("hello");
		theSet.add("world");
		theSet.add("how");
		theSet.add("are");
		theSet.add("you");
		
		theSet.clear();
		
		assertTrue(theSet.isEmpty());
	}
	
	/**
	 * This JUnit test, tests the isEmpty method with strings.
	 */
	@Test
	public void testIsEmptyWithStrings() {
		theSet.add("hello");
		theSet.add("world");
		theSet.add("how");
		theSet.add("are");
		theSet.add("you");
		
		theSet.clear();
		
		assertTrue(theSet.isEmpty());
	}

	/**
	 * This JUnit test, tests the Iterator method to see if the correct iterator is returned
	 */
	@Test
	public void testIterator() {

		ArrayList<String> theList = new ArrayList<String>();

		theList.add("hello");
		theList.add("this assinment");
		theList.add("was hard");
		
		theSet.addAll(theList);
		
		assertTrue(theSet.containsAll(theList));	
		
	}

	/**
	 * This JUnit test, tests the remove method using integers
	 */
	@Test
	public void testRemoveIntegers() {
		theSet.add(10);
		theSet.add(40);
		theSet.add(20);
		theSet.add(10);
		theSet.add(80);
		theSet.add(70);
		theSet.add(10);
		
		theSet.remove(80);

		assertFalse(theSet.contains(80));
	}
	
	/**
	 * This JUnit test, tests the remove method using strings
	 */
	@Test 
	public void testRemoveString(){
		
		theSet.add("hello");
		theSet.add("world");
		theSet.add("how");
		theSet.add("are");
		theSet.add("you");
		
		theSet.remove("are");
		
		assertFalse(theSet.contains("are"));

	}
	
	/**
	 * This JUnit test, tests the remove method using doubles
	 */
	@Test
	public void testRemoveDouble(){
		theSet.add(1.4);
		theSet.add(-3.4);
		theSet.add(-8.9);
		theSet.add(6.0);
		theSet.add(2.0);
		
		theSet.remove(6.0);
		
		assertFalse(theSet.contains(6.0));

	}

	/**
	 * This JUnit test, tests the removeAll method using integers
	 */
	@Test
	public void testRemoveAllIntegers() {
		
		ArrayList<Integer> theList = new ArrayList<Integer>();
		
		theList.add(2);
		theList.add(5);
		theList.add(3);
		
		theSet.addAll(theList);
		theSet.removeAll(theList);
		
		assertTrue(theSet.isEmpty());	
		
	}
	
	/**
	 * This JUnit test, tests the removeAll method using strings
	 */
	@Test
	public void testRemoveAllStrings() {
		ArrayList<String> theList = new ArrayList<String>();
		
		theList.add("hello");
		theList.add("assinment");
		theList.add("was");
		
		theSet.addAll(theList);
		theSet.removeAll(theList);
		
		assertTrue(theSet.isEmpty());	
		
	}
	
	/**
	 * This JUnit test, tests the removeAll method using doubles
	 */
	@Test
	public void testRemoveAllDoubles() {
		ArrayList<Double> theList = new ArrayList<Double>();
		
		theList.add(1.5);
		theList.add(10.0);
		theList.add(5.6);
		
		theSet.addAll(theList);
		theSet.removeAll(theList);
		
		assertTrue(theSet.isEmpty());	
		
	}

	/**
	 * This JUnit test, tests the size method
	 */
	@Test
	public void testSize() {

		theSet.add(1.4);
		theSet.add(-3.4);
		theSet.add(-8.9);
		theSet.add(6.0);
		theSet.add(2.0);
		
		assertEquals(5, theSet.size());
	}
	
	/**
	 * This JUnit test, tests the size method when the array is expanded beyond its capacity
	 */
	@Test
	public void testSizeOverArrayLimit() {

		// test to see if the array expands when the limit is reached and 
		// to see if it keeps track of the number of elements correctly
		
		theSet.add(10);
		theSet.add(20);
		theSet.add(30);
		theSet.add(40);
		theSet.add(50);
		theSet.add(60);
		theSet.add(80);
		theSet.add(100);
		theSet.add(102);
		theSet.add(204);
		theSet.add(106);
		theSet.add(208);
		theSet.add(105);
		theSet.add(203);
		theSet.add(122);
		theSet.add(222);
		theSet.add(123);
		theSet.add(256);
		theSet.add(178);
		theSet.add(290);
		theSet.add(300);
		theSet.add(1);
		theSet.add(1011);
		theSet.add(2011);
		theSet.add(3011);
		theSet.add(4011);
		theSet.add(5011);
		theSet.add(6011);
		theSet.add(8011);
		theSet.add(1001);
		theSet.add(1021);
		theSet.add(2041);
		theSet.add(1061);
		theSet.add(2081);
		theSet.add(1051);
		theSet.add(2031);
		theSet.add(1221);
		theSet.add(2221);
		theSet.add(1231);
		theSet.add(2561);
		theSet.add(1781);
		theSet.add(2901);
		theSet.add(3001);
		theSet.add(11);
		
		assertEquals(44, theSet.size());
		
	}

	/**
	 * This JUnit test, tests the toArray method
	 */
	@Test
	public void testToArray() {

		theSet.add(10);
		theSet.add(40);
		theSet.add(20);
		theSet.add(10);
		theSet.add(80);
		theSet.add(70);
		theSet.add(10);
		
		Object[] integerArray = new Object[theSet.size()];
		
		assertEquals(5, integerArray.length);
		
	}

	/**
	 * This JUnit test, tests the binarySearch method with integers
	 */
	@Test
	public void testBinarySearch() {
		
		theSet.add(10);
		theSet.add(40);
		theSet.add(20);
		theSet.add(80);
		theSet.add(70);
		
		assertEquals(1, theSet.binarySearch(20));
		
	}
	
	/**
	 * This JUnit test, tests the binarySearch method with string
	 */
	@Test
	public void testBinarySearchWithStrings(){
		theSet.add("hello");
		theSet.add("world");
		theSet.add("this");
		theSet.add("analysis");
		theSet.add("document");
		theSet.add("is");
		theSet.add("hard");
		
		assertEquals(4, theSet.binarySearch("is"));
	}

	/**
	 * This JUnit test, tests the binarySearch method with item not in set
	 */
	@Test
	public void testBinarySearchWithItemNotInSet(){
		
		theSet.add(10);
		theSet.add(40);
		theSet.add(20);
		theSet.add(80);
		theSet.add(70);
		
		// this returns the location of where the item should be in the list
		assertEquals(0, theSet.binarySearch(5));
		
	}
	
	/**
	 * This JUnit test, tests the toString method with integers
	 */
	@Test
	public void testToString() {
		
		theSet.add(10);
		theSet.add(40);
		theSet.add(20);
		theSet.add(80);
		theSet.add(70);
		
		assertEquals("10 20 40 70 80 ", theSet.toString());
	
	}
	
	/**
	 * This JUnit test, tests the toString method with strings
	 */
	@Test
	public void testToStringWithStrings(){
		
		theSet.add("hello");
		theSet.add("world");
		
		assertEquals("hello world ", theSet.toString());
	}

	/**
	 * This JUnit test, tests the compare method with integers
	 */
	@Test
	public void testCompare() {
		
		theSet.add(10);
		theSet.add(2);
		
		assertEquals(-1, theSet.compare(theSet.first(), theSet.last()));
	}
	
	/**
	 * This JUnit test, tests the compare method with strings
	 */
	@Test
	public void testCompareWithStrings() {
		
		theSet.add("hello");
		theSet.add("world");
		
		assertEquals(-15, theSet.compare(theSet.first(), theSet.last()));
	}
	
	/**
	 * This JUnit test, tests the compare method with doubles
	 */
	@Test
	public void testCompareWithDoubles(){
		
		theSetWithComparator.add(2);
		theSetWithComparator.add(1);
		
		assertEquals(-1, theSetWithComparator.compare(theSetWithComparator.first(), theSetWithComparator.last()));
	}

}