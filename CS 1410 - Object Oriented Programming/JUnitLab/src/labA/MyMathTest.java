package labA;

import static org.junit.Assert.*;

import org.junit.Test;

public class MyMathTest
{
	@Test
	public void testAverage1 ()
	{
		// This test will pass if both assertions below are true
		assertEquals(3, MyMath.average(3), 1e-6);
		assertEquals(4, MyMath.average(1, 4, 7), 1e-6);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAverage2 ()
	{
		// This test will pass if the method call below
		// throws an IllegalArgumentException
		MyMath.average();
	}
}
