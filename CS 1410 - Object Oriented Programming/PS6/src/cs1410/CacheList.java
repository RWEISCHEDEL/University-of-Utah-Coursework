package cs1410;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * A CacheList is a collection of Cache objects together with these six
 * constraints:
 * 
 * @author Robert Weischedel and Joe Zachary
 * 
 * <ol>
 * <li>A title constraint</li>
 * <li>An owner constraint</li>
 * <li>A minimum difficulty constraint</li>
 * <li>A maximum difficulty constraint</li>
 * <li>A minimum terrain constraint</li>
 * <li>A maximum terrain constraint</li>
 * </ol>
 */
public class CacheList
{
    // The caches being managed by this CacheList. They are arranged in
    // ascending order according to cache title.
	// All private variables.
    private ArrayList<Cache> allCaches;
    private String titleConstraint;
    private String ownerConstraint;
    private double minDifficulty;
    private double maxDifficulty;
    private double minTerrain;
    private double maxTerrain;

    /**
     * Creates a CacheList from the specified Scanner. Each line of the Scanner
     * should contain the description of a cache in a format suitable for
     * consumption by the Cache constructor. The resulting CacheList should
     * contain one Cache object corresponding to each line of the Scanner.
     * 
     * Sets the initial value of the title and owner constraints to the empty
     * string, sets the minimum difficulty and terrain constraints to 1.0, and
     * sets the maximum difficulty and terrain constraints to 5.0.
     * 
     * Throws an IOException if the Scanner throws an IOException, or
     * an IllegalArgumentException if any of the individual lines are not
     * appropriate for the Cache constructor.
     * 
     * When an IllegalArgumentException e is thrown, e.getMessage() is the
     * number of the line that was being read when the error that triggered the
     * exception was encountered. Lines are numbered beginning with 1.
     */
    public CacheList (Scanner caches) throws IOException
    {
        allCaches = new ArrayList<Cache>();
        int lineCounter = 1;
        while(caches.hasNextLine()){
        	try{
        	Cache newCache = new Cache(caches.nextLine());
        	allCaches.add(newCache);
        	}
        	catch(IllegalArgumentException e){
        		throw new IllegalArgumentException("" + lineCounter);
        	}
        	lineCounter++;
  
        }
        
        Collections.sort(allCaches, new CacheComparator());
        
        // Set all constraints.
        titleConstraint = "";
        ownerConstraint = "";
        minDifficulty = 1.0;
        maxDifficulty = 5.0;
        minTerrain = 1.0;
        maxTerrain = 5.0;
    }

    /**
     * Sets the title constraint to the specified value.
     */
    public void setTitleConstraint (String title)
    {
        // TODO: Implement
    	titleConstraint = title;
    }

    /**
     * Sets the owner constraint to the specified value.
     */
    public void setOwnerConstraint (String owner)
    {
        // TODO: Implement
    	ownerConstraint = owner;
    }

    /**
     * Sets the minimum and maximum difficulty constraints to the specified
     * values.
     */
    public void setDifficultyConstraints (double min, double max)
    {
    	// TODO: Implement
    	minDifficulty = min;
    	maxDifficulty = max;
    }

    /**
     * Sets the minimum and maximum terrain constraints to the specified values.
     */
    public void setTerrainConstraints (double min, double max)
    {
    	// TODO: Implement
    	minTerrain = min;
    	maxTerrain = max;
    }

    /**
     * Returns a list that contains each cache c from the CacheList so long as
     * c's title contains the title constraint as a substring, c's owner equals
     * the owner constraint (unless the owner constraint is empty), c's
     * difficulty rating is between the minimum and maximum difficulties
     * (inclusive), and c's terrain rating is between the minimum and maximum
     * terrains (inclusive).
     * 
     * The returned list is arranged in ascending order by cache title.
     */
    public ArrayList<Cache> select ()
    {
        // TODO: Complete this implementation
        ArrayList<Cache> caches = new ArrayList<Cache>();
        for(int c = 0; c < allCaches.size(); c++){
        			if(allCaches.get(c).getDifficulty() >= minDifficulty && allCaches.get(c).getDifficulty() <= maxDifficulty){
        				if(allCaches.get(c).getTerrain() >= minTerrain && allCaches.get(c).getTerrain() <= maxTerrain){
        					if(allCaches.get(c).getOwner().equals(ownerConstraint) || ownerConstraint.equals("")){
        						if(allCaches.get(c).getTitle().indexOf(titleConstraint) >= 0 || titleConstraint.equals("")){
        							caches.add(allCaches.get(c));
        						}
        					}
        				}
        			}
        		}
        
        return caches;
    }

    /**
     * Returns a list containing all the owners of all of the Cache objects in
     * this CacheList. There are no duplicates. The list is arranged in
     * ascending order.
     */
    public ArrayList<String> getOwners ()
    {
        // TODO: Complete this implementation
        ArrayList<String> owners = new ArrayList<String>();
		for (int i = 0; i < allCaches.size(); i++){
			String checkOwner = allCaches.get(i).getOwner();
			if(!owners.contains(checkOwner)){
				owners.add(checkOwner);
			}
		}
		
        Collections.sort(owners, new StringComparator());
        return owners;
    }

    /**
     * Useful for sorting lists of strings.
     */
    private class StringComparator implements Comparator<String>
    {
        public int compare (String s1, String s2)
        {
            return s1.compareToIgnoreCase(s2);
        }
    }

    /**
     * Useful for sorting lists of caches.
     */
    private class CacheComparator implements Comparator<Cache>
    {
        public int compare (Cache c1, Cache c2)
        {
            return c1.getTitle().compareToIgnoreCase(c2.getTitle());
        }
    }
}
