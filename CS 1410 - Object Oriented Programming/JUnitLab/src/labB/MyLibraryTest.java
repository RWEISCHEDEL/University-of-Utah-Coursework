package labB;

import static org.junit.Assert.*;

import java.util.Scanner;

import labA.MyMath;

import org.junit.Test;

public class MyLibraryTest {

	@Test
	public void testCountTokens() {
		assertEquals(2, MyLibrary.countTokens(new Scanner ("Hello world")));
		assertEquals(6, MyLibrary.countTokens(new Scanner ("Hello world\n Hello Java\t hello computer")));
		assertEquals(1, MyLibrary.countTokens(new Scanner ("HelloWorld")));
		assertEquals(0, MyLibrary.countTokens(new Scanner ("")));
		assertEquals(0, MyLibrary.countTokens(new Scanner ("        ")));
	}

	@Test
	public void testChangeCase() {
		assertEquals("ROBERT", MyLibrary.changeCase("Robert"));
		assertEquals("bronco", MyLibrary.changeCase("bronco"));
		assertEquals("COMPUTER", MyLibrary.changeCase("COmpUTer"));
		assertEquals("hello", MyLibrary.changeCase("hELLO"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testChangeCase2 ()
	{
		// This test will pass if the method call below
		// throws an IllegalArgumentException
		MyLibrary.changeCase("");
		MyLibrary.changeCase("        ");
	}

	@Test
	public void testFactorial() {
		assertEquals(1, MyLibrary.factorial(1));
		assertEquals(1, MyLibrary.factorial(0));
		assertEquals(120, MyLibrary.factorial(5));
		assertEquals(40320, MyLibrary.factorial(8));
		
	}
	
	@Test(expected = RuntimeException.class)
	public void testFactorial2 ()
	{
		// This test will pass if the method call below
		// throws an RuntimeException
		MyLibrary.factorial(-1);
		MyLibrary.factorial(-5);
	}

}
