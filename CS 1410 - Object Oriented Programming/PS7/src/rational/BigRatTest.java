package rational;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

/**
 * This set of JUnit tests is for the BigRat class constructor and its associated methods. 
 * @author Robert Weischedel
 *
 */
public class BigRatTest {

	/**
	 * This test sees if you construct a empty BigRat that it will be made into a zero.
	 */
	   @Test
	    public void testConstructor1 ()
	    {
	        BigRat r = new BigRat();
	        assertEquals("0", r.toString());
	    }

	   /**
	    * This test see if when you construct a BigRat it will be made into the correct value.
	    */
	    @Test
	    public void testConstructor2 ()
	    {
	        BigRat r1 = new BigRat(BigInteger.valueOf(5));
	        assertEquals("5", r1.toString());
	        
	        BigRat r2 = new BigRat(new BigInteger("2000000000000"));
	        assertEquals("2000000000000", r2.toString());
	    }
	    
	    /**
	     * This test see if when you construct a BigRat it will be made into the correct value. This also test to see if you have
	     * a 0 in the denominator or a invalid value in the fields.
	     */
	    @Test
	    public void testConstructor3 ()
	    {
	        BigRat r1 = new BigRat(BigInteger.valueOf(1), BigInteger.valueOf(2));
	        assertEquals("1/2", r1.toString());

	        BigRat r2 = new BigRat(BigInteger.valueOf(5), BigInteger.valueOf(15));
	        assertEquals("1/3", r2.toString());

	        BigRat r3 = new BigRat(BigInteger.valueOf(-4), BigInteger.valueOf(-2));
	        assertEquals("2", r3.toString());

	        BigRat r4 = new BigRat(BigInteger.valueOf(6), BigInteger.valueOf(-8));
	        assertEquals("-3/4", r4.toString());

	        BigRat r5 = new BigRat(BigInteger.valueOf(3), BigInteger.valueOf(-15));
	        assertEquals("-1/5", r5.toString());
	        
	        BigRat r6 = new BigRat(new BigInteger("2000000000000"), new BigInteger("2000000000000"));
	        assertEquals("1", r6.toString());
	        
	        BigRat r7 = new BigRat(new BigInteger("1"), new BigInteger("2000000000000"));
	        assertEquals("1/2000000000000", r7.toString());
	        
	        try {
	            new BigRat(BigInteger.valueOf(4), BigInteger.valueOf(0));
	            fail("No exception thrown");
	        }
	        catch (IllegalArgumentException e)
	        {           
	        }
	        
	        try {
	            new BigRat("4", "x");
	            fail("No exception thrown");
	        }
	        catch (NumberFormatException e)
	        {           
	        }
	    }

	    /**
	     * This test see if when you construct a BigRat it will be made into the correct value. This also test to see if you have
	     * a 0 in the denominator.
	     */
	    @Test
	    public void testConstructor4 ()
	    {
	        BigRat r1 = new BigRat(BigInteger.valueOf(1), BigInteger.valueOf(2));
	        assertEquals("1/2", r1.toString());

	        BigRat r2 = new BigRat(BigInteger.valueOf(5), BigInteger.valueOf(15));
	        assertEquals("1/3", r2.toString());

	        BigRat r3 = new BigRat(BigInteger.valueOf(-4), BigInteger.valueOf(-2));
	        assertEquals("2", r3.toString());

	        BigRat r4 = new BigRat(BigInteger.valueOf(6), BigInteger.valueOf(-8));
	        assertEquals("-3/4", r4.toString());

	        BigRat r5 = new BigRat(BigInteger.valueOf(3), BigInteger.valueOf(-15));
	        assertEquals("-1/5", r5.toString());
	        
	        BigRat r6 = new BigRat(new BigInteger("2000000000000"), new BigInteger("2000000000000"));
	        assertEquals("1", r6.toString());
	        
	        BigRat r7 = new BigRat(new BigInteger("1"), new BigInteger("2000000000000"));
	        assertEquals("1/2000000000000", r7.toString());
	        
	        try {
	            new BigRat(BigInteger.valueOf(4), BigInteger.valueOf(0));
	            fail("No exception thrown");
	        }
	        catch (IllegalArgumentException e)
	        {           
	        }
	    }

	    /**
	     * This test see if when you construct a BigRat you can add it to another BigRat.
	     */
	    @Test
	    public void testAdd ()
	    {
	        BigRat r1 = new BigRat(BigInteger.valueOf(2), BigInteger.valueOf(5));
	        BigRat r2 = new BigRat(BigInteger.valueOf(3), BigInteger.valueOf(4));
	        assertEquals("23/20", r1.add(r2).toString());
	        
	        r1 = new BigRat(BigInteger.valueOf(1), BigInteger.valueOf(7));
	        r2 = new BigRat(BigInteger.valueOf(-1), BigInteger.valueOf(5));
	        assertEquals("-2/35", r1.add(r2).toString());
	        
	        r1 = new BigRat(new BigInteger("1"), new BigInteger("2000000000000"));
	        r2 = new BigRat(new BigInteger("1"), new BigInteger("2000000000000"));
	        assertEquals("1/1000000000000", r1.add(r2).toString());
	        
	        r1 = new BigRat(new BigInteger("2"), new BigInteger("2000000000000"));
	        r2 = new BigRat(new BigInteger("2"), new BigInteger("2000000000000"));
	        assertEquals("1/500000000000", r1.add(r2).toString());
	    }

	    /**
	     * This test see if when you construct a BigRat you can subtract it from another BigRat.
	     */
	    @Test
	    public void testSub ()
	    {
	        BigRat r1 = new BigRat(BigInteger.valueOf(2), BigInteger.valueOf(5));
	        BigRat r2 = new BigRat(BigInteger.valueOf(3), BigInteger.valueOf(4));
	        assertEquals("-7/20", r1.sub(r2).toString());
	        
	        r1 = new BigRat(BigInteger.valueOf(1), BigInteger.valueOf(7));
	        r2 = new BigRat(BigInteger.valueOf(-1), BigInteger.valueOf(5));
	        assertEquals("12/35", r1.sub(r2).toString());
	        
	        r1 = new BigRat(new BigInteger("1"), new BigInteger("2000000000000"));
	        r2 = new BigRat(new BigInteger("2"), new BigInteger("2000000000000"));
	        assertEquals("-1/2000000000000", r1.sub(r2).toString());
	        
	        r1 = new BigRat(new BigInteger("6"), new BigInteger("2000000000000"));
	        r2 = new BigRat(new BigInteger("2"), new BigInteger("2000000000000"));
	        assertEquals("1/500000000000", r1.sub(r2).toString());
	    }

	    /**
	     * This test see if when you construct a BigRat you can multiply it to another BigRat.
	     */
	    @Test
	    public void testMul ()
	    {
	        BigRat r1 = new BigRat(BigInteger.valueOf(2), BigInteger.valueOf(5));
	        BigRat r2 = new BigRat(BigInteger.valueOf(3), BigInteger.valueOf(4));
	        assertEquals("3/10", r1.mul(r2).toString());
	        
	        r1 = new BigRat(BigInteger.valueOf(1), BigInteger.valueOf(7));
	        r2 = new BigRat(BigInteger.valueOf(-1), BigInteger.valueOf(5));
	        assertEquals("-1/35", r1.mul(r2).toString());
	        
	        r1 = new BigRat(new BigInteger("1"), new BigInteger("2000000000000"));
	        r2 = new BigRat(new BigInteger("1"), new BigInteger("2000000000000"));
	        assertEquals("1/4000000000000000000000000", r1.mul(r2).toString());
	        
	        r1 = new BigRat(new BigInteger("10"), new BigInteger("2000000000000"));
	        r2 = new BigRat(new BigInteger("7"), new BigInteger("1500000000000"));
	        assertEquals("7/300000000000000000000000", r1.mul(r2).toString());
	        
	        
	    }

	    /**
	     * This test see if when you construct a BigRat you can divide it from another BigRat.
	     */
	    @Test
	    public void testDiv ()
	    {
	        BigRat r1 = new BigRat(BigInteger.valueOf(2), BigInteger.valueOf(5));
	        BigRat r2 = new BigRat(BigInteger.valueOf(3), BigInteger.valueOf(4));
	        assertEquals("8/15", r1.div(r2).toString());
	        
	        r1 = new BigRat(BigInteger.valueOf(1), BigInteger.valueOf(7));
	        r2 = new BigRat(BigInteger.valueOf(-1), BigInteger.valueOf(5));
	        assertEquals("-5/7", r1.div(r2).toString());
	        
	        r1 = new BigRat(new BigInteger("1"), new BigInteger("2000000000000"));
	        r2 = new BigRat(new BigInteger("1"), new BigInteger("2000000000000"));
	        assertEquals("1", r1.div(r2).toString());
	        
	        r1 = new BigRat(new BigInteger("6"), new BigInteger("2000000000000"));
	        r2 = new BigRat(new BigInteger("2"), new BigInteger("2000000000000"));
	        assertEquals("3", r1.div(r2).toString());
	        
	        r1 = new BigRat(new BigInteger("1"), new BigInteger("2000000000000"));
	        r2 = new BigRat(new BigInteger("2"), new BigInteger("2000000000000"));
	        assertEquals("1/2", r1.div(r2).toString());

	        
	        try {
	            r1 = new BigRat(BigInteger.valueOf(3), BigInteger.valueOf(4));
	            r2 = new BigRat(BigInteger.valueOf(0));
	            r1.div(r2);
	            fail("No exception thrown");
	        }
	        catch (IllegalArgumentException e)
	        {
	        }
	    }

	    /**
	     * This test see if when you construct a BigRat you can compare it to another BigRat.
	     */
	    @Test
	    public void testCompareTo ()
	    {
	        BigRat r1 = new BigRat(BigInteger.valueOf(3), BigInteger.valueOf(4));
	        BigRat r2 = new BigRat(BigInteger.valueOf(6), BigInteger.valueOf(8));
	        BigRat r3 = new BigRat(BigInteger.valueOf(1), BigInteger.valueOf(2));
	        
	        BigRat r4 = new BigRat(new BigInteger("1"), new BigInteger("1000000000000"));
	        BigRat r5 = new BigRat(new BigInteger("2"), new BigInteger("2000000000000"));
	        BigRat r6 = new BigRat(new BigInteger("4"), new BigInteger("2000000000000"));
	        
	        assertEquals(0, r1.compareTo(r2));
	        assertTrue(r1.compareTo(r3) > 0);
	        assertTrue(r3.compareTo(r1) < 0);
	        
	        assertEquals(0, r4.compareTo(r5));
	        assertTrue(r6.compareTo(r4) > 0);
	        assertTrue(r5.compareTo(r6) < 0);
	        
	    }

	    /**
	     * This test see if when you construct a BigRat you can convert it into a double value.
	     */
	    @Test
	    public void testToDouble ()
	    {
	        BigRat r1 = new BigRat(BigInteger.valueOf(3), BigInteger.valueOf(4));
	        BigRat r2 = new BigRat(BigInteger.valueOf(1), BigInteger.valueOf(3));
	        BigRat r3 = new BigRat(new BigInteger("5"), new BigInteger("1000000000000"));
	        BigRat r4 = new BigRat(new BigInteger("2"), new BigInteger("2000000000000"));
	        
	        assertEquals(0.75, r1.toDouble(), 1e-12);
	        assertEquals(0.3333333333333, r2.toDouble(), 1e-12);
	        assertEquals(0.000000000005, r3.toDouble(), 1e-12);
	        assertEquals(0.000000000001, r4.toDouble(), 1e-12);
	    }

	    /**
	     * This test see if when you construct a BigRat you can check and see if it equals another BigRat.
	     */
	    @Test
	    public void testEqualsBigRat ()
	    {
	        BigRat r1 = new BigRat(BigInteger.valueOf(1), BigInteger.valueOf(5));
	        BigRat r2 = new BigRat(BigInteger.valueOf(2), BigInteger.valueOf(10));
	        BigRat r3 = new BigRat(BigInteger.valueOf(1), BigInteger.valueOf(2));
	        BigRat r4 = new BigRat(new BigInteger("1"), new BigInteger("1000000000000"));
	        BigRat r5 = new BigRat(new BigInteger("2"), new BigInteger("2000000000000"));
	       
	        assertTrue(r1.equals(r2));
	        assertFalse(r1.equals(r3));
	        assertTrue(r4.equals(r5));
	        assertFalse(r4.equals(r3));
	    }

	    /**
	     * This test see if when you construct a BigRat and find the gcd from its num and den.
	     */
	    @Test
	    public void testGcd ()
	    {
	        assertEquals(BigInteger.valueOf(2), BigRat.gcd(BigInteger.valueOf(6), BigInteger.valueOf(20)));
	        assertEquals(BigInteger.valueOf(1), BigRat.gcd(BigInteger.valueOf(11), BigInteger.valueOf(29)));
	        assertEquals(BigInteger.valueOf(10), BigRat.gcd(BigInteger.valueOf(30), BigInteger.valueOf(20)));
	        
	        assertEquals(BigInteger.valueOf(2), BigRat.gcd(new BigInteger("2"), new BigInteger("2000000000000")));
	    }


}
