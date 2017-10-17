package lab2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestFindSmallestDiff {
	
	private int[] arr1, arr2, arr3, arr4, arr5;

	@Before
	public void setUp() throws Exception {
		  arr1 = new int[0];
		  arr2 = new int[] { 3, 3, 3 };
		  arr3 = new int[] { 52, 4, -8, 0, -17 };
		  arr4 = new int[] {-3, 9, 100, 45, 99, 105};
		  arr5 = new int[] {-1, -2, -5, -10, -8};
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindSmallestDiff1() {
	  assertEquals(-1, DiffUtil.findSmallestDiff(arr1));
	}

	@Test
	public void testFindSmallestDiff2() {
	  assertEquals(0, DiffUtil.findSmallestDiff(arr2));
	}

	@Test
	public void testFindSmallestDiff3() {
	  assertEquals(4, DiffUtil.findSmallestDiff(arr3));
	}
	
	@Test
	public void testFindSmallestDiff4() {
	  assertEquals(1, DiffUtil.findSmallestDiff(arr4));
	}
	  
	@Test
	public void testFindSmallestDiff5() {
	  assertEquals(1, DiffUtil.findSmallestDiff(arr5));
	}

}
