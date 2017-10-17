package rational;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * This set of JUnit tests is for the Rat class constructor and its associated methods. 
 * @author Joe Zachary and Robert Weischedel
 *
 */
public class RatTest
{
	/**
	 * This test sees if you construct a empty Rat that it will be made into a zero.
	 */
    @Test
    public void testConstructor1 ()
    {
        Rat r = new Rat();
        assertEquals("0", r.toString());
    }

    /**
     * This test see if when you construct a Rat it will be made into the correct value.
     */
    @Test
    public void testConstructor2 ()
    {
        Rat r = new Rat(5);
        assertEquals("5", r.toString());
    }
    
    /**
     * This test see if when you construct a Rat it will be made into the correct value. This also test to see if you have
     * a 0 in the denominator or a invalid value in the fields.
     */
    @Test
    public void testConstructor3 ()
    {
        Rat r1 = new Rat("1", "2");
        assertEquals("1/2", r1.toString());

        Rat r2 = new Rat("5", "15");
        assertEquals("1/3", r2.toString());

        Rat r3 = new Rat("-4", "-2");
        assertEquals("2", r3.toString());

        Rat r4 = new Rat("6", "-8");
        assertEquals("-3/4", r4.toString());

        Rat r5 = new Rat("3", "-15");
        assertEquals("-1/5", r5.toString());
        
        try {
            new Rat("4", "0");
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {           
        }
        
        try {
            new Rat("4", "x");
            fail("No exception thrown");
        }
        catch (NumberFormatException e)
        {           
        }
    }

    /**
     * This test see if when you construct a Rat it will be made into the correct value. This also test to see if you have
     * a 0 in the denominator.
     */
    @Test
    public void testConstructor4 ()
    {
        Rat r1 = new Rat(1, 2);
        assertEquals("1/2", r1.toString());

        Rat r2 = new Rat(5, 15);
        assertEquals("1/3", r2.toString());

        Rat r3 = new Rat(-4, -2);
        assertEquals("2", r3.toString());

        Rat r4 = new Rat(6, -8);
        assertEquals("-3/4", r4.toString());

        Rat r5 = new Rat(3, -15);
        assertEquals("-1/5", r5.toString());
        
        try {
            new Rat(4, 0);
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {           
        }
    }

    /**
     * This test see if when you construct a Rat you can add it to another Rat.
     */
    @Test
    public void testAdd ()
    {
        Rat r1 = new Rat(2,5);
        Rat r2 = new Rat(3,4);
        assertEquals("23/20", r1.add(r2).toString());
        
        r1 = new Rat(1, 7);
        r2 = new Rat(-1, 5);
        assertEquals("-2/35", r1.add(r2).toString());
    }

    /**
     * This test see if when you construct a Rat you can subtract it from another Rat.
     */
    @Test
    public void testSub ()
    {
        Rat r1 = new Rat(2,5);
        Rat r2 = new Rat(3,4);
        assertEquals("-7/20", r1.sub(r2).toString());
        
        r1 = new Rat(1, 7);
        r2 = new Rat(-1, 5);
        assertEquals("12/35", r1.sub(r2).toString());
    }

    /**
     * This test see if when you construct a Rat you can multiply it to another Rat.
     */
    @Test
    public void testMul ()
    {
        Rat r1 = new Rat(2,5);
        Rat r2 = new Rat(3,4);
        assertEquals("3/10", r1.mul(r2).toString());
        
        r1 = new Rat(1, 7);
        r2 = new Rat(-1, 5);
        assertEquals("-1/35", r1.mul(r2).toString());
    }

    /**
     * This test see if when you construct a Rat you can divide it from another Rat.
     */
    @Test
    public void testDiv ()
    {
        Rat r1 = new Rat(2,5);
        Rat r2 = new Rat(3,4);
        assertEquals("8/15", r1.div(r2).toString());
        
        r1 = new Rat(1, 7);
        r2 = new Rat(-1, 5);
        assertEquals("-5/7", r1.div(r2).toString());
        
        try {
            r1 = new Rat(3,4);
            r2 = new Rat(0);
            r1.div(r2);
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {
        }
    }

    /**
     * This test see if when you construct a Rat you can compare it to another Rat.
     */
    @Test
    public void testCompareTo ()
    {
        Rat r1 = new Rat(3,4);
        Rat r2 = new Rat(6,8);
        Rat r3 = new Rat(1,2);
        assertEquals(0, r1.compareTo(r2));
        assertTrue(r1.compareTo(r3) > 0);
        assertTrue(r3.compareTo(r1) < 0);
    }

    /**
     * This test see if when you construct a Rat you can convert it into a double value.
     */
    @Test
    public void testToDouble ()
    {
        Rat r1 = new Rat(3,4);
        Rat r2 = new Rat(1,3);
        assertEquals(0.75, r1.toDouble(), 1e-12);
        assertEquals(0.3333333333333, r2.toDouble(), 1e-12);
    }

    /**
     * This test see if when you construct a Rat you can check and see if it equals another Rat.
     */
    @Test
    public void testEqualsRat ()
    {
        Rat r1 = new Rat(1,5);
        Rat r2 = new Rat(2,10);
        Rat r3 = new Rat(1,2);
        assertTrue(r1.equals(r2));
        assertFalse(r1.equals(r3));
    }

    /**
     * This test see if when you construct a Rat and find the gcd from its num and den.
     */
    @Test
    public void testGcd ()
    {
        assertEquals(2, Rat.gcd(6, 20));
        assertEquals(1, Rat.gcd(11, 29));
        assertEquals(10, Rat.gcd(30, 20));
    }

}
