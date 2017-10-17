package cs1410;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;

/**
 * These are the JUnit test cases and data for the CacheList Class.
 * @author Robert Weischedel
 *
 */
public class CacheListTests {
	// Test data
    private final static String badCaches1 =
            "GC2E5QX\tRed Butte Garden's Secret\tedquest\t1.5\t1.5\tN40 46.133\tW111 49.404\n" +
            "GC28EA\tBelow Red Butte\tKim Best\t3\t6\tN40 45.802\tW111 49.025\n" +
            "GC2ETN9\tDanger Garden\tedquest\t1.5\t1\tN40 45.993\tW111 49.411\n";
    
    private final static String badCaches2 =
            "GC2E5QX\tRed Butte Garden's Secret\tedquest\t1.5\t1.5\tN40 46.133\tW111 49.404\n" +
            "GC28EA\tBelow Red Butte\tKim Best\t3\t1.5\tN40 45.802\tW111 49.025\n" +
            "GC2ETN9\tDanger Garden\tedquest\t10\t1\tN40 45.993\tW111 49.411\n";
    
    private final static String badCaches3 =
            "GC2E5QX\tRed Butte Garden's Secret\tedquest\t1.5\t1.5\tN40 46.133\tW111 49.404\n" +
            "GC28EA\tBelow Red Butte\tKim Best\t3\t1.5\tN40 45.802\tW111 49.025\n" +
            "GC2ETN9\tDanger Garden\tedquest\t1.5\t1\tN40 45.993\t        \n";
    
    private final static String goodCaches =
            "GC2E5QX\tRed Butte Garden's Secret\tedquest\t1.5\t1.5\tN40 46.133\tW111 49.404\n" +
            "GC28EA\tBelow Red Butte\tKim Best\t3\t1.5\tN40 45.802\tW111 49.025\n" +
            "GC2ETN9\tDanger Garden\tedquest\t1.5\t1\tN40 45.993\tW111 49.411\n";

    // The following tests test and see if the CacheList constructor can handle the IllegalArgumentExceptions thrown from the Cache constructor.
    
    /**\
     * This test ensures that CacheList handles the IllegalArgumentException if the terrain is bad.
     * @throws IOException
     */
	@Test
	public void testCacheListExp1() throws IOException {
        try
        {
            CacheList list = new CacheList(new Scanner(badCaches1));
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals("2", e.getMessage());
        }
	}
	
	/**
	 * This test ensures that CacheList handles the IllegalArgumentException if the difficulty is bad.
	 * @throws IOException
	 */
	@Test
	public void testCacheListExp2() throws IOException {
        try
        {
            CacheList list = new CacheList(new Scanner(badCaches2));
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals("3", e.getMessage());
        }
	}
	
	/**
	 * This test ensures that CacheList handles the IllegalArgumentException if the the longitude is a blank field. 
	 * @throws IOException
	 */
	@Test
	public void testCacheListExp3() throws IOException {
        try
        {
            CacheList list = new CacheList(new Scanner(badCaches3));
            fail("No exception thrown");
        }
        catch (IllegalArgumentException e)
        {
            assertEquals("3", e.getMessage());
        }
	}
	
	
	
	// The following tests test and see if the methods and constructors work in the appropriate way with good data. 
	
	/**
	 * This test ensures select can place the correct Caches into the ArrayList based on a Difficulty Constraint.
	 * @throws IOException
	 */
	@Test
	public void testCacheList1() throws IOException {
		CacheList list = new CacheList(new Scanner(goodCaches));
		list.setDifficultyConstraints(3.0, 5.0);
		ArrayList<Cache> caches = list.select();
		assertEquals("Below Red Butte", caches.get(0).getTitle());
		
	}
	
	/**
	 * This test ensures select can place the correct Caches into the ArrayList based on a Terrain Constraint.
	 * @throws IOException
	 */
	@Test
	public void testCacheList2() throws IOException {
		CacheList list = new CacheList(new Scanner(goodCaches));
		list.setTerrainConstraints(1.0, 1.0);
		ArrayList<Cache> caches = list.select();
		assertEquals("Danger Garden", caches.get(0).getTitle());
		
	}
	
	/**
	 * This test ensures select can place the correct Caches into the ArrayList based on a Owner Constraint.
	 * @throws IOException
	 */
	@Test
	public void testCacheList3() throws IOException {
		CacheList list = new CacheList(new Scanner(goodCaches));
		list.setOwnerConstraint("Kim Best");
		ArrayList<Cache> caches = list.select();
		assertEquals("Kim Best", caches.get(0).getOwner());
		assertEquals("Below Red Butte", caches.get(0).getTitle());
		
	}
	
	/**
	 * This test ensures select can place the correct Caches into the ArrayList based on a Title Constraint.
	 * @throws IOException
	 */
	@Test
	public void testCacheList4() throws IOException {
		CacheList list = new CacheList(new Scanner(goodCaches));
		list.setTitleConstraint("Red");
		ArrayList<Cache> caches = list.select();
		assertEquals("Red Butte Garden's Secret", caches.get(1).getTitle());
		assertEquals("Below Red Butte", caches.get(0).getTitle());
		
	}
	
	/**
	 * This test ensures getOwners can place the correct Strings into the ArrayList in alphabetical order.
	 * @throws IOException
	 */
	@Test
	public void testCacheList5() throws IOException {
		CacheList list = new CacheList(new Scanner(goodCaches));
		ArrayList<String> owners = list.getOwners();
		assertEquals("edquest", owners.get(0));
		assertEquals("Kim Best", owners.get(1));
		
	}
	
	/**
	 * This test ensures select can place the correct Caches into the ArrayList with no constraints.
	 * @throws IOException
	 */
	@Test
	public void testCacheList6() throws IOException {
		CacheList list = new CacheList(new Scanner(goodCaches));
		ArrayList<Cache> caches = list.select();
		assertEquals("Red Butte Garden's Secret", caches.get(2).getTitle());
		assertEquals("Below Red Butte", caches.get(0).getTitle());
		assertEquals("Danger Garden", caches.get(1).getTitle());
	}
	
	/**
	 * This test ensures select can place the correct Caches into the ArrayList with all constraints set. 
	 * @throws IOException
	 */
	@Test
	public void testCacheList7() throws IOException {
		CacheList list = new CacheList(new Scanner(goodCaches));
		list.setDifficultyConstraints(1.0, 1.5);
		list.setTerrainConstraints(1.0, 1.5);
		list.setOwnerConstraint("edquest");
		list.setTitleConstraint("Garden");
		ArrayList<Cache> caches = list.select();
		assertEquals("Danger Garden", caches.get(0).getTitle());
		assertEquals("Red Butte Garden's Secret", caches.get(1).getTitle());
	}

}
