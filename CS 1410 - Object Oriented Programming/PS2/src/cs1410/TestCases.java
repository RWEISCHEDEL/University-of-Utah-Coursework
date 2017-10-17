package cs1410;

import static org.junit.Assert.*;
import static cs1410.MethodLibrary.*;

import org.junit.Test;

public class TestCases
{

	@Test
	public void testCapitalize ()
	{
		assertEquals("X", capitalize("x"));
		assertEquals("Hello", capitalize("hello"));
		assertEquals("HElLo", capitalize("hElLo"));
		assertEquals("123ab", capitalize("123ab"));
	}

	@Test
	public void testIsVowel ()
	{
		assert (isVowel('a'));
		assert (isVowel('e'));
		assert (isVowel('i'));
		assert (isVowel('o'));
		assert (isVowel('u'));
		assert (isVowel('A'));
		assert (isVowel('E'));
		assert (isVowel('I'));
		assert (isVowel('O'));
		assert (isVowel('U'));
		assert (!isVowel('x'));
		assert (!isVowel('h'));
	}

	@Test
	public void testToPigLatin ()
	{
		assertEquals("ickslay", toPigLatin("slick"));
		assertEquals("ICKSLay", toPigLatin("SLICK"));
		assertEquals("ongstray", toPigLatin("strong"));
		assertEquals("ONGSTRay", toPigLatin("STRONG"));
		assertEquals("xyzzy", toPigLatin("xyzzy"));
		assertEquals("orangeway", toPigLatin("orange"));
	}

	@Test
	public void testConvertToPigLatin ()
	{
		assertEquals("isthay isway away esttay ",
				convertToPigLatin("this is a test"));
		assertEquals("isthay isway away esttay ",
				convertToPigLatin("    this     is     a         test"));
		assertEquals("", convertToPigLatin(""));
		assertEquals("", convertToPigLatin("           "));
		assertEquals(
				"eThay ainray inway ainSpay allsfay ainlymay inway ethay ainplay ",
				convertToPigLatin("The rain in Spain falls mainly in the plain"));
	}

	@Test
	public void testMakeLine ()
	{
		assertEquals("&******&", makeLine('&', '*', 8));
		assertEquals("##", makeLine('#', '?', 2));
		assertEquals("===", makeLine('=', '=', 3));
		assertEquals("|++|", makeLine('|', '+', 4));
	}

	@Test
	public void testMakeRectangle ()
	{
		assertEquals("++\n++\n", makeRectangle(2, 2));
		assertEquals("+-+\n| |\n+-+\n", makeRectangle(3, 3));
		assertEquals("+--+\n|  |\n|  |\n|  |\n+--+\n", makeRectangle(5, 4));
	}

	@Test
	public void testNextHailstone ()
	{
		assertEquals(1, nextHailstone(1));
		assertEquals(16, nextHailstone(5));
		assertEquals(100, nextHailstone(33));
		assertEquals(1, nextHailstone(2));
		assertEquals(8, nextHailstone(16));
		assertEquals(1000000, nextHailstone(2000000));
	}

	@Test
	public void testHailstones ()
	{
		assertEquals("1 ", hailstones(1));
		assertEquals("16 8 4 2 1 ", hailstones(16));
		assertEquals("7 22 11 34 17 52 26 13 40 20 10 5 16 8 4 2 1 ", hailstones(7));
	}

	@Test
	public void testCountWords ()
	{
		assertEquals(0, countWords("", 1, 100));
		assertEquals(0, countWords("a b c", 2, 4));
		assertEquals(3, countWords("a b c", 1, 1));
		assertEquals(2, countWords("a b cd", 1, 1));
		assertEquals(2, countWords("look at the ferry", 4, 5));
		assertEquals(6, countWords("The rain in Spain falls mainly in the plain", 3, 5));
	}

	@Test
	public void testStripWhiteSpace ()
	{
		assertEquals("", stripWhiteSpace(""));
		assertEquals("", stripWhiteSpace("   \n\n\n \t \t \n\n\n"));
		assertEquals("x", stripWhiteSpace("x"));
		assertEquals("x", stripWhiteSpace(" x "));
		assertEquals("thisisatest", stripWhiteSpace("this is a test"));
		assertEquals("thisisatest", stripWhiteSpace("\nthis\tis\ta\ntest\t"));
	}

}
