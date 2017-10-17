package assignment4;

import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class is a set of JUnit tests to throughly test our AnagramUtil class and all of its associated methods, inputs,
 * and outputs. The names for each methods were carefully selected to adequately describe what each JUNit method is testing for.
 * @author Robert Weischedel && Makenzie Elliott
 *
 */
public class AnagramUtilTester {

	// Create some private member variables for the comparators and String arrays. 
	String[] theSimpleFile;
	String[] theModerateFile;
	Comparator sortChars, sortStrings;

	@Before
	public void setUp() throws Exception {

		theSimpleFile = AnagramTester.readFile("simple_word_list");
		theModerateFile = AnagramTester.readFile("moderate_word_list");

		// Create Comparator class.
		class sortChars implements Comparator<Character> {

			@Override
			public int compare(Character char1, Character char2) {
				return char1.compareTo(char2);
			}
		}
		
		class sortStrings implements Comparator<String>{

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
			
		}
		
		sortChars = new sortChars();
		sortStrings = new sortStrings();

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSortString() {

		assertEquals("ehllo", AnagramUtil.sort("hello"));

	}

	@Test 
	public void testSortStringLong() {

		assertEquals("aaaccdeefgiiiiiiillloopprrsssttuux",
				AnagramUtil.sort("supercalifragilisticexpialidotious"));
	}

	
	@Test
	public void testSortStringWithUppercase() {

		assertEquals("aceepprsu", AnagramUtil.sort("Uppercase"));
	}
	
	@Test
	public void testSortNull(){
		
		String s = null;
		
		assertNull(AnagramUtil.sort(s));
	}

	@Test
	public void testInsertionSortOnStringsWithSimpleList() {
		
		AnagramUtil.insertionSort(theSimpleFile, sortStrings);
		
		assertTrue(theSimpleFile[0].compareTo(theSimpleFile[1]) <0);
		
	}
	
	@Test
	public void testInsertionSortOnStringsWithModerateList() {
		
		AnagramUtil.insertionSort(theModerateFile, sortStrings);
		
		assertTrue(theModerateFile[0].compareTo(theModerateFile[1]) <0);
		
	}
	
	
	@Test
	public void testInsertionSortOnStringsWithRandomFile() {
		
		
		String[] randomWords = new String[30];
		
		for(int i = 0; i < randomWords.length; i++){
			randomWords[i] = AnagramTester.randomString(i + 1);
		}
		
		AnagramUtil.insertionSort(randomWords, sortStrings);
		
		assertTrue(randomWords[0].compareTo(randomWords[1]) <0);
		
	}

	@Test
	public void testInsertionSortOnChars() {
		
		Character[] characterArray = new Character[5];
		
		String s = "hello";
		
		for(int i = 0; i < characterArray.length; i++){
			characterArray[i] = s.charAt(i); 
		}
		
		AnagramUtil.insertionSort(characterArray, sortChars);
		
		assertTrue(characterArray[0] < characterArray[1]);
		
	}

	@Test
	 public void testAreAnagramsTrue() {

	
		String o1 = "Caters";
		String o2 = "Reacts";
		
		assertTrue(AnagramUtil.areAnagrams(o1, o2));
	}
	
	@Test
	public void testAreAnagramsFalse() {

	
		String o1 = "Racecare";
		String o2 = "Carless";
		
		assertFalse(AnagramUtil.areAnagrams(o1, o2));
	}
	
	@Test
	public void testAreAnagramsOneIsNull() {

	
		String o1 = null;
		String o2 = "Reacts";
		
		assertFalse(AnagramUtil.areAnagrams(o1, o2));
	}
	
	
	@Test
	public void testAreAnagramsBothAreNull() {

	
		String o1 = null;
		String o2 = null;
		
		assertFalse(AnagramUtil.areAnagrams(o1, o2));
	}
	
	
	
	@Test
	public void testGetLargestAnagramGroupEmptyArray() {
		
		String[] emptyArray = new String[0];
		
		assertEquals(0, AnagramUtil.getLargestAnagramGroup(emptyArray).length);
	
	}
	
	@Test
	public void testGestLArgestAnagramsWithSimpleList(){
		
		assertEquals(7, AnagramUtil.getLargestAnagramGroup(theSimpleFile).length);
	}

	
	@Test
	public void testGestLArgestAnagramsWithModerateList(){
		
		assertEquals(2, AnagramUtil.getLargestAnagramGroup(theModerateFile).length);
	}
	
	
	@Test
	public void testGetLargestAnagramGroupEmptyFile() {
		
		assertEquals(0, AnagramUtil.getLargestAnagramGroup("EmptyFile").length);
	
	}
	
	@Test
	public void testGetLargestAnagramGroupSimpleList(){
		
		assertEquals(7, AnagramUtil.getLargestAnagramGroup("simple_word_list").length);
	}
	
	@Test
	public void testGetLargestAnagramGroupModerateList(){
		
		assertEquals(2, AnagramUtil.getLargestAnagramGroup("moderate_word_list").length);
	}

}
