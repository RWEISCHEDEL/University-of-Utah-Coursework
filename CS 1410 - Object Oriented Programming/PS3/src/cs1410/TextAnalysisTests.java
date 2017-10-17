package cs1410;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

public class TextAnalysisTests 
{
	@Test
	public void testCountToken ()
	{
		assertEquals(0, TextAnalysis.countToken(new Scanner(""), "x"));
		assertEquals(3, TextAnalysis.countToken(new Scanner(
				"one two one two three two four"), "two"));
	}

	@Test
	public void testCountWhitespace ()
	{
		assertEquals(0, TextAnalysis.countWhitespace(""));
		assertEquals(0, TextAnalysis.countWhitespace("xyz"));
		assertEquals(10,
				TextAnalysis.countWhitespace("one two  three   four    "));
		assertEquals(8,
				TextAnalysis.countWhitespace("one\ttwo\n\nthree   four\n\t"));
	}

	@Test
	public void testAverageTokenLength ()
	{
		assertEquals(0, TextAnalysis.averageTokenLength(new Scanner("")), 1e-6);
		assertEquals(3.66666666666667,
				TextAnalysis.averageTokenLength(new Scanner("one two three")),
				1e-6);
	}

	@Test
	public void testIsPalindrome ()
	{
		assertTrue(TextAnalysis.isPalindrome(""));
		assertTrue(TextAnalysis.isPalindrome("z"));
		assertFalse(TextAnalysis.isPalindrome("xy"));
		assertTrue(TextAnalysis.isPalindrome("abcddcba"));
		assertFalse(TextAnalysis.isPalindrome("abcddbba"));
		assertTrue(TextAnalysis.isPalindrome("abcdedcba"));
		assertFalse(TextAnalysis.isPalindrome("abcdedbba"));
	}

	@Test
	public void testContainsToken ()
	{
		assertFalse(TextAnalysis.containsToken("", "xyz"));
		assertTrue(TextAnalysis.containsToken("xyz", "xyz"));
		assertTrue(TextAnalysis.containsToken("xyx xyy xyz xya", "xyz"));
		assertFalse(TextAnalysis.containsToken("xyx xyy xyZ xya", "xyz"));
	}

	@Test
	public void testFindLineWithToken ()
	{
		assertEquals(null,
				TextAnalysis.findLineWithToken(new Scanner(""), "hello"));
		assertEquals("hello world", TextAnalysis.findLineWithToken(new Scanner(
				"hello world"), "world"));
		assertEquals(null, TextAnalysis.findLineWithToken(new Scanner(
				"hello world"), "abc"));
		assertEquals("hello world", TextAnalysis.findLineWithToken(new Scanner(
				"hello world"), "world"));
		assertEquals("this is a test", TextAnalysis.findLineWithToken(new Scanner(
				"hello world\nthis is a test\nthis is another test"), "a"));
	}

	@Test
	public void testFindLongestPalindrome ()
	{
		assertEquals("", TextAnalysis.findLongestPalindrome(new Scanner("")));
		assertEquals("did", TextAnalysis.findLongestPalindrome(new Scanner("I did something wrong")));
		assertEquals("", TextAnalysis.findLongestPalindrome(new Scanner("This is an apple")));
		assertEquals("peep", TextAnalysis.findLongestPalindrome(new Scanner("a bb xyz\nI heard a peep sis")));
	}
	
	@Test
	public void testFindLongestLine ()
	{
		assertEquals(null, TextAnalysis.findLongestLine(new Scanner("")));
		assertEquals("hello", TextAnalysis.findLongestLine(new Scanner("hello")));
		assertEquals("This is a test", TextAnalysis.findLongestLine(new Scanner("Hello\nThis is a test\nGoodbye")));
	}

	@Test
	public void testFindMostWhitespace ()
	{
		assertEquals(null, TextAnalysis.findMostWhitespace(new Scanner("")));
		assertEquals("a b c", TextAnalysis.findMostWhitespace(new Scanner("a b c")));
		assertEquals("a bb\t\t", TextAnalysis.findMostWhitespace(new Scanner("a bb\na bb\t\t\nxyz")));
	}

	@Test
	public void testFindNextLargestToken ()
	{
		assertEquals(null, TextAnalysis.findNextTokenInSortedOrder(new Scanner(""), "a"));
		assertEquals(null, TextAnalysis.findNextTokenInSortedOrder(new Scanner("a"), "b"));
		assertEquals("b", TextAnalysis.findNextTokenInSortedOrder(new Scanner("b"), "a"));
		assertEquals("says", TextAnalysis.findNextTokenInSortedOrder(new Scanner("This is a test\nof the system\nsays the woman"), "rat"));
	}

}
