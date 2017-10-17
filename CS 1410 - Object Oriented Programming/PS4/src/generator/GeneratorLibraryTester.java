package generator;

import static org.junit.Assert.*;
import static generator.GeneratorLibrary.*;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Test methods for the GeneratorLibrary
 * @author Joe Zachary
 */
public class GeneratorLibraryTester
{
	@Test
	public void testCountStrings ()
	{
		try
		{
			countTargetOccurrences("", "");
			fail("No exception thrown");
		}
		catch (IllegalArgumentException e)
		{
		}

		try
		{
			countTargetOccurrences("x", "x");
			fail("No exception thrown");
		}
		catch (IllegalArgumentException e)
		{
		}

		try
		{
			countTargetOccurrences("x", "xy");
			fail("No exception thrown");
		}
		catch (IllegalArgumentException e)
		{
		}

		assertEquals(4, countTargetOccurrences("abcd", ""));
		assertEquals(0, countTargetOccurrences("xxx", "y"));
		assertEquals(1, countTargetOccurrences("xyz", "y"));
		assertEquals(1, countTargetOccurrences("aba", "a"));
		assertEquals(2, countTargetOccurrences("abab", "a"));
		assertEquals(1, countTargetOccurrences("abcdef", "abcde"));
		assertEquals(2, countTargetOccurrences("aaaa", "aa"));
		assertEquals(3, countTargetOccurrences("aaaab", "aa"));
		assertEquals(3, countTargetOccurrences("This is a picture of my sister", "is"));
	}

	@Test
	public void testGetCharAfterNthOccurrenceExceptions ()
	{
		try
		{
			getCharAfterNthOccurrence("xy", -1, "x");
			fail("No exception thrown");
		}
		catch (IllegalArgumentException e)
		{
		}
		
		try
		{
			getCharAfterNthOccurrence("xy", 0, "x");
			fail("No exception thrown");
		}
		catch (IllegalArgumentException e)
		{
		}
		
		
		try
		{
			getCharAfterNthOccurrence("abcd", 1, "xy");
			fail("No exception thrown");
		}
		catch (NoSuchElementException e)
		{
		}
		
		try
		{
			getCharAfterNthOccurrence("ab", 1, "ab");
			fail("No exception thrown");
		}
		catch (NoSuchElementException e)
		{
		}
		
		try
		{
			getCharAfterNthOccurrence("abc", 2, "ab");
			fail("No exception thrown");
		}
		catch (NoSuchElementException e)
		{
		}
		
		try
		{
			getCharAfterNthOccurrence("abcabcabc", 4, "ab");
			fail("No exception thrown");
		}
		catch (NoSuchElementException e)
		{
		}
		
		try
		{
			getCharAfterNthOccurrence("abcabcabc", 3, "abc");
			fail("No exception thrown");
		}
		catch (NoSuchElementException e)
		{
		}
	}
	
	@Test
	public void testGetCharAfterNthOccurrencePatterns ()
	{
		assertEquals('a', getCharAfterNthOccurrence("abcdef", 1, ""));
		assertEquals('b', getCharAfterNthOccurrence("abcdef", 2, ""));
		assertEquals('c', getCharAfterNthOccurrence("abcdef", 3, ""));
		assertEquals('d', getCharAfterNthOccurrence("abcdef", 4, ""));
		assertEquals('e', getCharAfterNthOccurrence("abcdef", 5, ""));
		assertEquals('f', getCharAfterNthOccurrence("abcdef", 6, ""));
		try {
			getCharAfterNthOccurrence("abcdef", 7, "");
			fail("No exception thrown");
		}
		catch (NoSuchElementException e)
		{
		}
		
		assertEquals('a', getCharAfterNthOccurrence("aaaaab", 1, "a"));
		assertEquals('a', getCharAfterNthOccurrence("aaaaab", 2, "a"));
		assertEquals('a', getCharAfterNthOccurrence("aaaaab", 3, "a"));
		assertEquals('a', getCharAfterNthOccurrence("aaaaab", 4, "a"));
		assertEquals('b', getCharAfterNthOccurrence("aaaaab", 5, "a"));
		try {
			getCharAfterNthOccurrence("aaaaab", 6, "a");
			fail("No exception thrown");
		}
		catch (NoSuchElementException e)
		{
		}
		
		assertEquals('a', getCharAfterNthOccurrence("aaaaab", 1, "aa"));
		assertEquals('a', getCharAfterNthOccurrence("aaaaab", 2, "aa"));
		assertEquals('a', getCharAfterNthOccurrence("aaaaab", 3, "aa"));
		assertEquals('b', getCharAfterNthOccurrence("aaaaab", 4, "aa"));
		try {
			getCharAfterNthOccurrence("aaaaab", 5, "aa");
			fail("No exception thrown");
		}
		catch (NoSuchElementException e)
		{
		}
		
		assertEquals('a', getCharAfterNthOccurrence("aaaaab", 1, "aaa"));
		assertEquals('a', getCharAfterNthOccurrence("aaaaab", 2, "aaa"));
		assertEquals('b', getCharAfterNthOccurrence("aaaaab", 3, "aaa"));
		try {
			getCharAfterNthOccurrence("aaaaab", 4, "aaa");
			fail("No exception thrown");
		}
		catch (NoSuchElementException e)
		{
		}
		
		assertEquals('a', getCharAfterNthOccurrence("aaaaab", 1, "aaaa"));
		assertEquals('b', getCharAfterNthOccurrence("aaaaab", 2, "aaaa"));
		try {
			getCharAfterNthOccurrence("aaaaab", 3, "aaaa");
			fail("No exception thrown");
		}
		catch (NoSuchElementException e)
		{
		}
		
		assertEquals('b', getCharAfterNthOccurrence("aaaaab", 1, "aaaaa"));	
		try {
			getCharAfterNthOccurrence("aaaaab", 2, "aaaaa");
			fail("No exception thrown");
		}
		catch (NoSuchElementException e)
		{
		}
	}
	
	@Test
	public void testGetCharAfterNthOccurrence ()
	{
		assertEquals('d', getCharAfterNthOccurrence("abcdefabcabczabcc", 1, "abc"));
		assertEquals('a', getCharAfterNthOccurrence("abcdefabcabczabcc", 2, "abc"));
		assertEquals('z', getCharAfterNthOccurrence("abcdefabcabczabcc", 3, "abc"));		
		assertEquals('d', getCharAfterNthOccurrence("abcDABCd", 1, "ABC"));
	}
}
