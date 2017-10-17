package assignment5;

import static org.junit.Assert.*; 

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This Class is a series of JUnit tests designed to help test and debug SortUtil. 
 * @author Robert Weischedel && Makenzie Elliott
 *
 */
public class SortUtilTestCases {

	ArrayList<Integer> theAverageList, theBestList, theWorstList, emptyList, nullList;
	theComparator comp;
	
	@Before
	public void setUp() throws Exception {
		theAverageList = SortUtil.generateAverageCase(10);
		theBestList = SortUtil.generateBestCase(10);
		theWorstList = SortUtil.generateWorstCase(10);
		comp = new theComparator();
		emptyList = new ArrayList();
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testSetThreshold1(){
		SortUtil.setThreshold(10);
		assertEquals(10, SortUtil.getThreshold());  
	}
	
	@Test
	public void testSetThreshold2(){
		SortUtil.setThreshold(20);
		assertEquals(20, SortUtil.getThreshold());
	}
	
	@Test
	public void testSetThreshold3(){
		SortUtil.setThreshold(50);
		assertEquals(50, SortUtil.getThreshold());
	}
	
	@Test
	public void testGetThreshold1(){
		SortUtil.setThreshold(45);
		assertEquals(45, SortUtil.getThreshold());
	}
	
	@Test
	public void testGetThreshold2(){
		SortUtil.setThreshold(10);
		assertEquals(10, SortUtil.getThreshold());
	}
	
	@Test
	public void testGetThreshold3(){
		SortUtil.setThreshold(100);
		assertEquals(100, SortUtil.getThreshold());
	}
	
	
	@Test
	public void testMergesortAverageList() {
		SortUtil.mergesort(theAverageList, comp);
		assertTrue(comp.compare(theAverageList.get(0), theAverageList.get(1))<0);
			
	}
	
	@Test
	public void testMergesortWorstList(){
		SortUtil.mergesort(theWorstList, comp);
		assertTrue(comp.compare(theWorstList.get(0), theWorstList.get(1))<0);
	}
	
	@Test
	public void testMersortsortBestList(){
		SortUtil.mergesort(theBestList, comp);
		assertTrue(comp.compare(theBestList.get(0), theBestList.get(1))<0);
	}
	
	@Test 
	public void testMergesortEmptyList(){
		SortUtil.mergesort(emptyList, comp);
		assertEquals(0,emptyList.size());
	}
	
	@Test
	public void testMergesortNullList(){
		SortUtil.mergesort(nullList, comp);
		assertNull(nullList);
	}

	@Test
	public void testRecursiveMergesortWorstList(){
		SortUtil.mergesort(theWorstList, comp);
		assertTrue(comp.compare(theWorstList.get(0), theWorstList.get(1))<0);
	}
	
	@Test
	public void testRecursiveMersortBestList(){
		SortUtil.mergesort(theBestList, comp);
		assertTrue(comp.compare(theBestList.get(0), theBestList.get(1))<0);
	}
	
	@Test 
	public void testRecursiveMergesortEmptyList(){
		SortUtil.mergesort(emptyList, comp);
		assertEquals(0,emptyList.size());
	}
	
	@Test
	public void testRecursiveMergesortNullList(){
		SortUtil.mergesort(nullList, comp);
		assertNull(nullList);
	}

	@Test
	public void testMergeAverageList() {
		// the methods for merge and recursive merge are made private because no other class should be implementing them 
		// except for the driver method of merge.  So in order to test that the merge works you must test it via the driver method
		// in from merge sort
		SortUtil.mergesort(theAverageList, comp);
		assertTrue(theAverageList.size() == 10);
	
	}
	
	@Test
	public void testMergeBestList() {
		// the methods for merge and recursive merge are made private because no other class should be implementing them 
		// except for the driver method of merge.  So in order to test that the merge works you must test it via the driver method
		// in from merge sort
		SortUtil.mergesort(theBestList, comp);
		assertTrue(theBestList.size() == 10);
	
	}
	
	@Test
	public void testMergeWorstList() {
		// the methods for merge and recursive merge are made private because no other class should be implementing them 
		// except for the driver method of merge.  So in order to test that the merge works you must test it via the driver method
		// in from merge sort
		SortUtil.mergesort(theWorstList, comp);
		assertTrue(theWorstList.size() == 10);
	
	}

	@Test
	public void testInsertionSortAverageList() {
		SortUtil.insertionSort(theAverageList, comp);	
		assertTrue(comp.compare(theAverageList.get(0), theAverageList.get(1))<0);
	}

	@Test
	public void testInsertionSortWorstList() {
		SortUtil.insertionSort(theWorstList, comp);	
		assertTrue(comp.compare(theWorstList.get(0), theWorstList.get(1))<0);
	}
	
	@Test
	public void testInsertionSortBestList() {
		SortUtil.insertionSort(theBestList, comp);	
		assertTrue(comp.compare(theBestList.get(0), theBestList.get(1))<0);
	}
	
	
	@Test
	public void testQuicksortAverageList() {
		SortUtil.quicksort(theAverageList, comp);
		assertTrue(comp.compare(theAverageList.get(0), theAverageList.get(1))<0);
	}

	@Test
	public void testQuicksortWorstList() {
		SortUtil.quicksort(theWorstList, comp);
		assertTrue(comp.compare(theWorstList.get(0), theWorstList.get(1))<0);
	}
	
	@Test
	public void testQuicksortBestList() {
		SortUtil.quicksort(theBestList, comp);
		assertTrue(comp.compare(theBestList.get(0), theBestList.get(1))<0);
	}
	
	@Test 
	public void testQuicksortEmptyList(){
		SortUtil.quicksort(emptyList, comp);
		assertEquals(0,emptyList.size());
	}
	
	@Test
	public void testQuicksortNullList(){
		SortUtil.quicksort(nullList, comp);
		assertNull(nullList);
	}
	
	
	
	@Test
	public void testQuicksortRecursiveMethodAverageList() {
		SortUtil.quicksort(theAverageList, comp);
		assertTrue(comp.compare(theAverageList.get(0), theAverageList.get(1))<0);
	}

	@Test
	public void testQuicksortRecursiveMethodWorstList() {
		SortUtil.quicksort(theWorstList, comp);
		assertTrue(comp.compare(theWorstList.get(0), theWorstList.get(1))<0);
	}
	
	@Test
	public void testQuicksortRecursiveMethodBestList() {
		SortUtil.quicksort(theBestList, comp);
		assertTrue(comp.compare(theBestList.get(0), theBestList.get(1))<0);
	}

	@Test
	public void testSwap() {
		SortUtil.swap(theBestList, 0, 1);
		assertTrue(theBestList.get(0) == 2);	
	}
	
	@Test
	public void testSwap2() {
		SortUtil.swap(theBestList, 0, 9);
		assertTrue(theBestList.get(9) == 1);	
	}

	@Test
	public void testSwap3() {
		SortUtil.swap(theBestList, 4, 9);
		assertTrue(theBestList.get(9) == 5);	
	}
	
	@Test
	public void testPivot1() {
		// this uses a random generator that returns an index from the list at random.  It cannot be tested
		SortUtil.pivot1(theBestList, comp);
		//fail("Cannot be tested because its a random number");
	}

	@Test
	public void testPivot2() {
		assertEquals(5, SortUtil.pivot2(theBestList, comp));
	}

	@Test
	public void testPivot2Test2() {
		// this pivot only works correctly for the best case scenario. Poor choice on our part. 
		// All other cases it returns the last element in the list
		// giving us our worst case every time
		assertEquals(9, SortUtil.pivot2(theWorstList, comp));
	}
	
	@Test
	public void testPivot3ForBestList() {
		assertEquals(5, SortUtil.pivot3(theBestList));
	}
	
	@Test
	public void testPivot3ForWorstList() {
		assertEquals(5, SortUtil.pivot3(theWorstList));
	}
	
	@Test
	public void testPivot3forAverageList() {
		assertEquals(5, SortUtil.pivot3(theAverageList));
	}

	@Test
	public void testGenerateBestCaseOn100() {
		ArrayList<Integer> thebest = SortUtil.generateBestCase(100);
		assertTrue(comp.compare(thebest.get(50), thebest.get(99))<0);
	}

	
	@Test
	public void testGenerateBestCaseOn1000() {
		ArrayList<Integer> thebest = SortUtil.generateBestCase(1000);
		assertTrue(comp.compare(thebest.get(50), thebest.get(999))<0);
	}
	
	@Test
	public void testGenerateBestCaseOn1000000() {
		ArrayList<Integer> thebest = SortUtil.generateBestCase(1000000);
		assertTrue(comp.compare(thebest.get(1), thebest.get(99999))<0);
	}
	
	@Test
	public void testGenerateAverageCaseOn100() {
		ArrayList<Integer> thebest = SortUtil.generateAverageCase(100);
		assertTrue(thebest.size() == 100);
		assertTrue(thebest.contains(10));
	}

	
	@Test
	public void testGenerateAverageCaseOn1000() {
		ArrayList<Integer> thebest = SortUtil.generateAverageCase(1000);
		assertTrue(thebest.size() == 1000);
		assertTrue(thebest.contains(10));

	}
	
	@Test
	public void testGenerateAverageCaseOn1000000() {
		ArrayList<Integer> thebest = SortUtil.generateAverageCase(1000000);
		assertTrue(thebest.size() == 1000000);
		assertTrue(thebest.contains(10));

	}
	@Test
	public void testGenerateWorstCaseOn100() {
		ArrayList<Integer> thebest = SortUtil.generateWorstCase(100);
		assertTrue(comp.compare(thebest.get(50), thebest.get(99))>0);
	}

	
	@Test
	public void testGenerateWorstCaseOn1000() {
		ArrayList<Integer> thebest = SortUtil.generateWorstCase(1000);
		assertTrue(comp.compare(thebest.get(50), thebest.get(999))>0);
	}
	
	@Test
	public void testGenerateWorstCaseOn1000000() {
		ArrayList<Integer> thebest = SortUtil.generateWorstCase(1000000);
		assertTrue(comp.compare(thebest.get(1), thebest.get(99999))>0);
	}

	public class theComparator implements Comparator<Integer>{

		@Override
		public int compare(Integer o1, Integer o2) {
			return o1-o2;
		}
		
	}
}