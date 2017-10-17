package assignment8;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.junit.Test;

/**
 * This class is a series of JUnit test cases for the BinarySearchTree class that we implemented for Assignment 8. In this class we test all the 
 * public methods that we implemented in the BinarySearchTree class. Some methods like size and contains we didn't test as much because of how much 
 * we used them to test the other methods in the class.
 * 
 * @author Tanner Martin and Robert Weischedel
 *
 */
public class BinarySearchTreeTest
{

	/**
	 * This method tests the add method, it uses a for loop to add several integer values to a BST
	 */
	@Test
	public void testAdd()
	{
		BinarySearchTree<Integer> addList = new BinarySearchTree<Integer>();

		for (int i = 0; i <= 11; i++)
		{
			addList.add(i);
		}

		for (int i = 0; i <= 11; i++)
		{
			assertTrue(addList.contains(i));
		}

		assertFalse(addList.contains(39));
	}

	/**
	 * This method tests the addAll method by first creating an ArrayList filled with integers and then adding the ArrayList values to a BST
	 */
	@Test
	public void testAddAll()
	{
		ArrayList<Integer> numsToAdd = new ArrayList<Integer>();
		numsToAdd.add(1);
		numsToAdd.add(2);
		numsToAdd.add(3);
		numsToAdd.add(4);
		numsToAdd.add(5);
		
		
		BinarySearchTree<Integer> addAllList = new BinarySearchTree<Integer>();
		addAllList.addAll(numsToAdd);
		
		assertTrue(addAllList.contains(1));
		assertTrue(addAllList.contains(2));
		assertTrue(addAllList.contains(3));
		assertTrue(addAllList.contains(4));
		assertTrue(addAllList.contains(5));
	}

	/**
	 * This method tests the clear method on a BST we create with one value then we clear it and check the size.
	 */
	@Test
	public void testClear()
	{

		BinarySearchTree<Integer> clearList = new BinarySearchTree<Integer>();
		clearList.add(1);

		assertEquals(1, clearList.size());

		clearList.clear();
		assertEquals(0, clearList.size());
	}

	/**
	 * This method tests the contains method on a BST we fill with a few values and we check if each have been added and are contained within.
	 */
	@Test
	public void testContains()
	{
		BinarySearchTree<Integer> containList = new BinarySearchTree<Integer>();
		containList.add(1);
		containList.add(2);
		containList.add(13);
		containList.add(12);

		assertTrue(containList.contains(1));
		assertTrue(containList.contains(2));
		assertTrue(containList.contains(13));
		assertTrue(containList.contains(12));

	}

	@Test
	public void testContainsAll()
	{
		ArrayList<Integer> containsList = new ArrayList<Integer>();
		containsList.add(2);
		containsList.add(1);
		containsList.add(3);
		
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		returnList.add(2);
		returnList.add(1);
		returnList.add(3);
		
		BinarySearchTree<Integer> containAllList = new BinarySearchTree<Integer>();
		containAllList.addAll(containsList);
		
		assertTrue(containAllList.containsAll(returnList));
		assertTrue(containAllList.contains(2));
		assertTrue(containAllList.contains(1));
		assertTrue(containAllList.contains(3));
	}

	@Test
	public void testFirst()
	{
		BinarySearchTree<Integer> tree2 = new BinarySearchTree<Integer>();
		tree2.add(20);
		tree2.add(27);
		tree2.add(9);
		tree2.add(5);
		tree2.add(2);
		tree2.add(6);
		tree2.add(16);
		tree2.add(11);
		tree2.add(10);
		tree2.add(19);
		tree2.add(17);
		tree2.add(30);
		tree2.add(25);
		
		assertTrue(new Integer(2).equals(tree2.first()));
	}

	@Test
	public void testIsEmpty()
	{
		BinarySearchTree<Integer> emptyList = new BinarySearchTree<Integer>();
		assertTrue(emptyList.isEmpty());

		emptyList.add(2);
		assertFalse(emptyList.isEmpty());
	}

	@Test
	public void testLast()
	{
		BinarySearchTree<Integer> tree3 = new BinarySearchTree<Integer>();
		tree3.add(20);
		tree3.add(27);
		tree3.add(9);
		tree3.add(5);
		tree3.add(2);
		tree3.add(6);
		tree3.add(16);
		tree3.add(11);
		tree3.add(10);
		tree3.add(19);
		tree3.add(17);
		tree3.add(30);
		tree3.add(25);
		
		assertTrue(new Integer(30).equals(tree3.last()));
	}

	@Test
	public void testRemove()
	{
		BinarySearchTree<Integer> tree4 = new BinarySearchTree<Integer>();
		tree4.add(20);
		tree4.add(27);
		tree4.add(9);
		tree4.add(5);
		tree4.add(2);
		tree4.add(6);
		tree4.add(16);
		tree4.add(11);
		tree4.add(10);
		tree4.add(19);
		tree4.add(17);
		tree4.add(30);
		tree4.add(25);
		
		tree4.remove(20);
		tree4.remove(27);
		tree4.remove(9);
		tree4.remove(5);
		
		assertTrue(new Integer(9).equals(tree4.size()));
		assertFalse(new Integer(20).equals(tree4.contains(20)));
		assertFalse(new Integer(27).equals(tree4.contains(27)));
		assertFalse(new Integer(9).equals(tree4.contains(9)));
		assertFalse(new Integer(5).equals(tree4.contains(5)));
	}

	@Test
	public void testRemoveAll()
	{	
		ArrayList<Integer> removeList = new ArrayList<Integer>();
		removeList.add(2);
		removeList.add(3);
		removeList.add(1);
		
		BinarySearchTree<Integer> removeTree = new BinarySearchTree<Integer>();
		removeTree.add(2);
		removeTree.add(3);
		removeTree.add(1);
		
		removeTree.removeAll(removeList);
		
		assertFalse(removeTree.contains(2));
		assertFalse(removeTree.contains(3));
		assertEquals(0, removeTree.size());
		
		
	}

	@Test
	public void testSize()
	{
		BinarySearchTree<Integer> sizeList = new BinarySearchTree<Integer>();

		assertEquals(0, sizeList.size());

		for (int i = 0; i <= 11; i++)
		{
			sizeList.add(i);
		}

		assertEquals(12, sizeList.size());
	}

	@Test
	public void testToArrayList()
	{
		BinarySearchTree<Integer> treeArrayTest = new BinarySearchTree<Integer>();
		treeArrayTest.add(2);
		treeArrayTest.add(3);
		treeArrayTest.add(1);
		
		ArrayList<Integer> returnToArrayList = treeArrayTest.toArrayList();
		
		assertEquals(new Integer(1), returnToArrayList.get(0));
		assertEquals(new Integer(2), returnToArrayList.get(1));
		assertEquals(new Integer(3), returnToArrayList.get(2));
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddException()
	{
		BinarySearchTree<Integer> treeArrayTest = new BinarySearchTree<Integer>();
		treeArrayTest.add(2);
		treeArrayTest.add(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testContainsException()
	{
		BinarySearchTree<Integer> treeArrayTest = new BinarySearchTree<Integer>();
		treeArrayTest.add(2);
		treeArrayTest.add(3);
		
		treeArrayTest.contains(null);	
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFirstException()
	{
		BinarySearchTree<Integer> treeArrayTest = new BinarySearchTree<Integer>();
		
		treeArrayTest.first();	
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testLastException()
	{
		BinarySearchTree<Integer> treeArrayTest = new BinarySearchTree<Integer>();
		
		treeArrayTest.last();	
	}
	
	@Test(expected = NullPointerException.class)
	public void testRemoveException()
	{
		BinarySearchTree<Integer> treeArrayTest = new BinarySearchTree<Integer>();
		treeArrayTest.add(2);
		treeArrayTest.add(3);
		
		treeArrayTest.remove(null);	
	}
}
