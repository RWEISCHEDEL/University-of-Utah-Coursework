package assignment11;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class PriorityQueueTester {

	@Test
	public void testSize() {
		PriorityQueue<String> q = new PriorityQueue<String>();
		
		q.add("Hi");
		q.add("hasbigload");
		q.add("hat8r");
		q.add("swag");
		q.add("wurd");
		q.add("bloodz");
		
		assertEquals(6, q.size());
		
		q.add("holla");
		
		assertEquals(7, q.size());
		
		q.deleteMin();
		
		assertEquals(6, q.size());
		
		q.clear();
		
		assertEquals(0, q.size());
	}

	@Test
	public void testClear() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindMin() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteMin() {
PriorityQueue<String> q = new PriorityQueue<String>();
		
		q.add("hasbigload");
		q.add("hat8r");
		q.add("swag");
		q.add("wurd");
		q.add("bloodz");
		q.add("cryptz");
		q.add("smallbigload");
		q.add("hassmallload");
		q.add("drugZ");
		q.add("zebra");
		q.add("apple");
		
		q.deleteMin();
		
		//q.generateDotFile("/home/jmurphy/Desktop/CS2420 Assignment11/del-1.dot");
		
		q.deleteMin();
		
		//q.generateDotFile("/home/jmurphy/Desktop/CS2420 Assignment11/del-2.dot");
	}

	@Test
	public void testAddStrings() {
		
		PriorityQueue<String> q = new PriorityQueue<String>();
		
		q.add("hasbigload");
		q.add("hat8r");
		q.add("swag");
		q.add("wurd");
		q.add("bloodz");
		q.add("cryptz");
		q.add("smallbigload");
		q.add("hassmallload");
		q.add("drugZ");
		q.add("zebra");
		q.add("apple");
		
		assertEquals(11, q.size());
		
		q.clear();
		
		assertEquals(0, q.size());
		
		
		q.add("hasbigload");
		
		assertEquals("hasbigload", q.findMin());
		
		q.add("hat8r");
		q.add("swag");
		q.add("wurd");
		q.add("bloodz");
		q.add("cryptz");
		q.add("smallbigload");
		q.add("hassmallload");
		q.add("drugZ");
		q.add("zebra");
		q.add("apple");
		
		assertEquals(11, q.size());
		
		//q.generateDotFile("/home/jmurphy/Desktop/CS2420 Assignment11/add-1.dot");
		
		Object checkArray[] = {"apple", "bloodz", "cryptz", "hasbigload", "drugZ", "swag", "smallbigload", "wurd", "hassmallload", "zebra", "hat8r"};
		
		assertTrue(Arrays.equals(q.toArray(), checkArray));
		
	}
	
	@Test
	public void testAddInteger() {
		
		PriorityQueue<Integer> q = new PriorityQueue<Integer>();
		
		q.add(10);
		q.add(1);
		q.add(3);
		q.add(156);
		q.add(10985);
		q.add(5);
		q.add(69);
		q.add(78);
		q.add(49);
		q.add(85);
		q.add(58);
		q.add(25);
		
		assertEquals(12, q.size());
		
		Object checkArray[] = {1, 10, 3, 49, 58, 5, 69, 156, 78, 10985, 85, 25};
		
		assertTrue(Arrays.equals(q.toArray(), checkArray));
		
	}
	
	@Test 
	public void testAddCharacter() {
		
		PriorityQueue<Character> q = new PriorityQueue<Character>();
		
		q.add('u');
		q.add('x');
		q.add('y');
		q.add('z');
		q.add('w');
		q.add('f');
		q.add('g');
		q.add('h');
		q.add('q');
		q.add('o');
		q.add('&');
		
		assertEquals(11, q.size());
		
		/*Object array[] = q.toArray();
		
		
		for(Object o : array){
			System.out.println(o);
		}*/
		
		Object checkArray[] = {'&', 'f', 'g', 'q', 'h', 'y', 'u', 'z', 'w', 'x', 'o'};
		
		assertTrue(Arrays.equals(q.toArray(), checkArray));
		
	}

}
