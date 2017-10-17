package cs1410;

/**
 * Represents a variety of information about a geocache. A geocache has a title,
 * an owner, a difficulty rating, a terrain rating, a GC code, a latitude, and a
 * longitude.
 * @author Robert Weischedel
 */
public class Cache
{
	// All private variables. 
	private String title;
	private String owner;
	private Double difficulty;
	private Double terrain;
	private String GCcode;
	private String latitude;
	private String longitude;

    /**
     * Creates a Cache from a string that consists of these seven cache
     * attributes: the GC code, the title, the owner, the difficulty rating, the
     * terrain rating, the latitude, and the longitude, in that order, separated
     * by single TAB ('\t') characters.
     * 
     * If any of the following problems are present, throws an
     * IllegalArgumentException:
     * <ul>
     * <li>Fewer than seven attributes</li>
     * <li>More than seven attributes</li>
     * <li>A GC code that is anything other than "GC" followed by one or more
     * upper-case letters and/or digits</li>
     * <li>A difficulty or terrain rating that parses to anything other than the
     * doubles 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, or 5.</li>
     * <li>A title, owner, latitude, or longitude that consists only of white
     * space</li>
     */
	
	/**
	 * This Constructor takes in a string of attributes and converts it into a Cache Object. It also splits the attributes up into several different pieces of information. 
	 * @param attributes
	 */
    public Cache (String attributes)
    {
    	String[] information = attributes.split("\t");
    	
    	if (information.length != 7){
    		throw new IllegalArgumentException();
    	}
    	
    	GCcode = information[0];
    	if(GCcode.indexOf("GC") != 0){
    		throw new IllegalArgumentException();
    	}
    	
    	String diffRating = information[3];
    	difficulty = Double.parseDouble(diffRating);
    	
    	if(difficulty != 1 && difficulty != 1.5 && difficulty != 2 && difficulty != 2.5 && difficulty != 3 && difficulty != 3.5 && difficulty != 4 && difficulty != 4.5 && difficulty != 5){
    		throw new IllegalArgumentException();
    	}
    	
    	String terrRating = information[4];
    	terrain = Double.parseDouble(terrRating);
    	
    	if(terrain != 1 && terrain != 1.5 && terrain != 2 && terrain != 2.5 && terrain != 3 && terrain != 3.5 && terrain != 4 && terrain != 4.5 && terrain != 5){
    		throw new IllegalArgumentException();
    	}
    	
    	owner = information[2];
    	if(owner.trim().isEmpty()){
    		throw new IllegalArgumentException();
    	}
    	
    	title = information[1];
    	if(title.trim().isEmpty()){
    		throw new IllegalArgumentException();
    	}
    	
    	latitude = information[5];
    	if(latitude.trim().isEmpty()){
    		throw new IllegalArgumentException();
    	}
    	
    	longitude = information[6];
    	if(longitude.trim().isEmpty()){
    		throw new IllegalArgumentException();
    	}

    	
    }

    /**
     * Converts this cache to a string
     */
    public String toString ()
    {
        return getTitle() + " by " + getOwner();
    }

    /**
     * Returns the owner of this cache
     */
    public String getOwner ()
    {
        return owner;
    }

    /**
     * Returns the title of this cache
     */
    public String getTitle ()
    {
        return title;
    }

    /**
     * Returns the difficulty rating of this cache
     */
    public double getDifficulty ()
    {
        return difficulty;
    }

    /**
     * Returns the terrain rating of this cache
     */
    public double getTerrain ()
    {
        return terrain;
    }

    /**
     * Returns the GC code of this cache
     */
    public String getGcCode ()
    {
        return GCcode;
    }

    /**
     * Returns the latitude of this cache
     */
    public String getLatitude ()
    {
        return latitude;
    }

    /**
     * Returns the longitude of this cache
     */
    public String getLongitude ()
    {
        return longitude;
    }
}
