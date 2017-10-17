package assignment10;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * This class serves as the JUnit test cases for the ChainingHashTable class. Some methods like size, isEmpty, and getLength is not tested
 * as extensively in their own classes because they are used extensively in testing the other methods in the class. 
 * @author Jackson Murphy and Robert Weischedel
 *
 */
public class ChainingHashTableTester {

	@Test
	public void testAdd() {

		ChainingHashTable ct = new ChainingHashTable(5, new GoodHashFunctor());

		assertTrue(ct.add("hi"));

		assertEquals(1, ct.size());
		assertEquals(5, ct.getLength());

		assertTrue(ct.add("hello"));
		assertTrue(ct.add("ballon"));
		assertTrue(ct.add("orbit"));
		assertTrue(ct.add("nuclear"));

		assertEquals(5, ct.size());
		assertEquals(10, ct.getLength());

		assertFalse(ct.add("hi"));

		assertEquals(5, ct.size());

	}

	@Test
	public void testAddAll() {

		ChainingHashTable ct = new ChainingHashTable(5, new GoodHashFunctor());

		ArrayList<String> test = new ArrayList<String>(Arrays.asList("String",
				"Word", "Cs 2420", "cs 1410"));

		assertTrue(ct.addAll(test));
		
		assertEquals(4, ct.size());

		assertFalse(ct.addAll(test));

		ArrayList<String> test2 = new ArrayList<String>(Arrays.asList("String",
				"Word", "Cs 3500", "cs 1410", "String"));

		assertTrue(ct.addAll(test2));
		
		assertEquals(5, ct.size());

	}


	@Test
	public void testContains() {
		ChainingHashTable ct = new ChainingHashTable(5, new GoodHashFunctor());
		
		ct.add("hi");
		
		assertEquals(1, ct.size());
		
		assertTrue(ct.contains("hi"));
		
		ct.add("wordz");
		ct.add("caddyshack");
		ct.add("halo");
		ct.add("legend");
		ct.add("moo");
		
		assertEquals(6, ct.size());
		
		assertTrue(ct.contains("wordz"));
		
		assertTrue(ct.contains("caddyshack"));
		
		assertFalse(ct.contains("moos"));
		
		assertFalse(ct.contains("halo3"));
		
		ct.add(null);
		
		assertTrue(ct.contains(null));
	}

	@Test
	public void testContainsAll() {
		ChainingHashTable ct = new ChainingHashTable(5, new GoodHashFunctor());
		
		ArrayList<String> test = new ArrayList<String>(Arrays.asList("String", "Word", "Cs 2420", "cs 1410"));

		ct.addAll(test);
		
		assertEquals(4, ct.size());
		
		assertTrue(ct.containsAll(test));
		
		ArrayList<String> test2 = new ArrayList<String>(Arrays.asList("String", "Word", "Cs 3500", "cs 1410", "String"));
		
		assertFalse(ct.containsAll(test2));
		
		ct.addAll(test2);
		
		assertTrue(ct.containsAll(test2));
		
	}

	@Test
	public void testIsEmpty() {
		ChainingHashTable ct = new ChainingHashTable(5, new GoodHashFunctor());
		
		assertTrue(ct.isEmpty());
		
		ct.add("hi");
		
		assertFalse(ct.isEmpty());
		
		ct.clear();
		
		assertTrue(ct.isEmpty());
	}

}
