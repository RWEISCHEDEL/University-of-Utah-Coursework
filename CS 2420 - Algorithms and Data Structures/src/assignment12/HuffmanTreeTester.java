package assignment12;

import static org.junit.Assert.*;
import org.junit.Test;
import assignment12.HuffmanTree.Node;;

public class HuffmanTreeTester {

	/**
	 * This method is to test the compareTo method in the inner private Node class. In order to test the comapreTo
	 * method, I had to make the inner Node class static and public. But after the testing was over we switched the 
	 * Node class back to private and made in non-static. 
	 */
	@Test
	public void testCompareTo() {
		
		// Make two Nodes both with 'A' in them with two different weights.
		Node testA1 = new Node(65, 1);
		
		Node testA2 = new Node(65, 2);
		
		assertEquals(-1, testA1.compareTo(testA2));
		
		assertEquals(1, testA2.compareTo(testA1));
		
		// Make two Nodes both with 'B' in them with two different weights.
		Node testB10 = new Node(66, 10);
		
		Node testB20 = new Node(66, 20);
		
		assertEquals(-1, testB10.compareTo(testB20));
		
		assertEquals(1, testB20.compareTo(testB10));
		
		// Make two nodes with the same weight but with different characters in them.
		Node testTieA = new Node(65, 1);
		
		Node testTieB = new Node(66, 1);
		
		assertEquals(-1, testTieA.compareTo(testTieB));
		
		assertEquals(1, testTieB.compareTo(testTieA));
		
		// Create a Node to test and see what happens when two exact same nodes are compared.
		Node testEqualsC1 = new Node(67, 1);
		
		assertEquals(1, testEqualsC1.compareTo(testEqualsC1));
		
		
	}


}
