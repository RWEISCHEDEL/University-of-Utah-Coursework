package assignment10;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * This class serves as the JUnit test cases for the QuadProbeHashTable class. Some methods like size, getLength, and isEmpty are used
 * to aid in testing the other methods and in so doing are thoroughly tested in the process and aren't tested as extensively in their own
 * testing methods.
 * 
 * @author Jackson Murphy and Robert Weischedel
 *
 */
public class QuadProbeHashTableTester {

	@Test
	public void testAdd() {
		QuadProbeHashTable ht = new QuadProbeHashTable(10, new GoodHashFunctor());

		assertEquals(11, ht.getLength());

		assertTrue(ht.add("hello"));
		assertTrue(ht.add("ardvark"));
		assertTrue(ht.add("booger"));
		assertTrue(ht.add("N64"));
		assertTrue(ht.add("hasbigload"));
		assertTrue(ht.add("hassmallload"));
		
		assertEquals(6, ht.size());

		assertEquals(13, ht.getLength());
		
		// Attempt to add items that have already been added and null.
		assertFalse(ht.add("hello"));
		assertFalse(ht.add("N64"));
		
		assertEquals(6, ht.size());
		
		assertEquals(13, ht.getLength());
		
		assertFalse(ht.add(null));
		
		assertEquals(6, ht.size());
		
		assertEquals(13, ht.getLength());
		
		// Add one additional item.
		assertTrue(ht.add("zebra"));
		
		assertEquals(7, ht.size());
		
		assertEquals(17, ht.getLength());

	}

	@Test
	public void testAddAll() {
		
		QuadProbeHashTable ht = new QuadProbeHashTable(10, new GoodHashFunctor());
		
		ArrayList<String> test = new ArrayList<String>(Arrays.asList("howdy", "holler", "wordup", "dawg", "hater", "swag"));
		
		assertTrue(ht.addAll(test));
		
		assertEquals(6, ht.size());
		
		assertFalse(ht.addAll(test));
		
		assertEquals(6, ht.size());
		
		ArrayList<String> test2 = new ArrayList<String>(Arrays.asList("chevy", "ford", "cadillac", "ferrari", "porshe", "hater", "swag"));
		
		assertTrue(ht.addAll(test2));
		
		assertEquals(11, ht.size());
	}

	@Test
	public void testClear() {
		
		QuadProbeHashTable ht = new QuadProbeHashTable(10, new GoodHashFunctor());
		
		ArrayList<String> test = new ArrayList<String>(Arrays.asList("howdy", "holler", "wordup", "dawg", "hater", "swag"));
		
		ht.addAll(test);
		
		assertEquals(6, ht.size());
		
		assertFalse(ht.isEmpty());
		
		ht.clear();
		
		assertEquals(0, ht.size());
		
		assertTrue(ht.isEmpty());
	}
	
	@Test
	public void testGetLength() {
		
		QuadProbeHashTable ht = new QuadProbeHashTable(10, new GoodHashFunctor());
		
		assertEquals(11, ht.getLength());
		
		ht.add("Robert");
		ht.add("Jackson");
		ht.add("Bronco");
		ht.add("CS 2420");
		ht.add("tuesday");
		ht.add("CS 2100");
		
		assertEquals(13, ht.getLength());
		
	}

	@Test
	public void testContains() {

		QuadProbeHashTable ht = new QuadProbeHashTable(10,
				new GoodHashFunctor());

		assertTrue(ht.add("hello"));
		assertTrue(ht.add("ardvark"));
		assertTrue(ht.add("booger"));
		assertTrue(ht.add("N64"));
		assertTrue(ht.add("hasbigload"));
		assertTrue(ht.add("hassmallload"));

		assertTrue(ht.contains("hasbigload"));
		assertTrue(ht.contains("N64"));
		assertTrue(ht.contains("hello"));
		assertTrue(ht.contains("hassmallload"));
		
		assertFalse(ht.contains("hasmediumload"));
	}

	@Test
	public void testContainsAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsEmpty() {
		fail("Not yet implemented");
	}

}
