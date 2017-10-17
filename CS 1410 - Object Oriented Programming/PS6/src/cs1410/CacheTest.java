package cs1410;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This is a series of tests for the Cache Constructor and its methods. This JUnit Test is used for PS6.
 * The tests that have "Exp" in the method calls test for the IllegalArgumentExceptions thrown in the Cache constructor.
 * The other tests test the methods that are made in the Cache method. 
 * @author Robert Weischedel
 *
 */
public class CacheTest {
	
	/**
	 * This test is to make sure that the program throws an exception if there are less than 7 items. 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCacheExp1A() {
		Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\tN40 45.850\tW111 48.045");
	}
	
	/**
	 * This test is to make sure that the program throws an exception if there are more than 7 items. 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCacheExp1B() {
		Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045\thello");
	}
	
	/**
	 * This test is to make sure that the program throws an exception if the GC Code doesn't begin with GC.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCacheExp2() {
		Cache c = new Cache("RQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
	}
	
	
	/**
	 * This test is to make sure that the program throws an exception if the difficulty level is higher than 5.0.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCacheExp3A() {
		Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t7\t3\tN40 45.850\tW111 48.045");
	}
	
	/**
	 * This test is to make sure that the program throws an exception if the difficulty is between 1.0 and 5.0, but it has the incorrect decimal value.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCacheExp3B() {
		Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t1.1\t3\tN40 45.850\tW111 48.045");
	}
	
	/**
	 * This test is to make sure that the program throws an exception if the terrain is higher than 5.0.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCacheExp4A() {
		Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t10\tN40 45.850\tW111 48.045");
	}
	
	/**
	 * This test is to make sure that the program throws an exception if the terrain is between 1.0 and 5.0, but it has the incorrect decimal value.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCacheExp4B() {
		Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t4.7\tN40 45.850\tW111 48.045");
	}
	
	/**
	 * This test is to make sure that the program throws an exception if the owner field is just whitespace.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCacheExp5() {
		Cache c = new Cache("GCRQWK\tOld Three Tooth\t    \t3.5\t3\tN40 45.850\tW111 48.045");
	}
	
	/**
	 * This test is to make sure that the program throws an exception if the title field is just whitespace.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCacheExp6() {
		Cache c = new Cache("GCRQWK\t      \tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
	}
	
	/**
	 * This test is to make sure that the program throws an exception if the latitude field is just whitespace.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCacheExp7() {
		Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\t         \tW111 48.045");
	}
	
	/**
	 * This test is to make sure that the program throws an exception if the longitiude field is just whitespace.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCacheExp8() {
		Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\t           ");
	}
	
	
	
	// These series of tests ensure that the Cache method returns work.
	
	/**
	 * This test is to make sure the method getGcCode returns the GCcode.
	 */
	@Test
	public void testCache1() {
		Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
		assertEquals("GCRQWK",c.getGcCode());
	}
	
	/**
	 * This test is to make sure the method getTitle returns the title.
	 */
	@Test
	public void testCache2() {
		Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
		assertEquals("Old Three Tooth",c.getTitle());
	}
	
	/**
	 * This test is to make sure the method getOwner returns the owner.
	 */
	@Test
	public void testCache3() {
		Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
		assertEquals("geocadet",c.getOwner());
	}
	
	/**
	 * This test is to make sure the method toString returns the title and owner correctly.
	 */
	@Test
	public void testCache4() {
		Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
		assertEquals("Old Three Tooth by geocadet",c.toString());
	}
	
	/**
	 * This test is to make sure the method getDifficulty returns the difficulty.
	 */
	@Test
	public void testCache5() {
		Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
		assertEquals(3.5, c.getDifficulty(), 1e-6);
	}
	
	/**
	 * This test is to make sure the method getTerrain returns the terrain.
	 */
	@Test
	public void testCache6() {
		Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
		assertEquals(3, c.getTerrain(), 1e-6);
	}
	
	/**
	 * This test is to make sure the method getLatitude returns the latitude.
	 */
	@Test
	public void testCache7() {
		Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
		assertEquals("N40 45.850", c.getLatitude());
	}
	
	/**
	 * This test is to make sure the method getLongitude returns the longitude.
	 */
	@Test
	public void testCache8() {
		Cache c = new Cache("GCRQWK\tOld Three Tooth\tgeocadet\t3.5\t3\tN40 45.850\tW111 48.045");
		assertEquals("W111 48.045", c.getLongitude());
	}

}
