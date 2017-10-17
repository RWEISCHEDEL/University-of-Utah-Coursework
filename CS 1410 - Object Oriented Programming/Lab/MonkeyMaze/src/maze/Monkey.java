package maze;

import java.awt.*;
import java.util.Random;

import javax.swing.*;


/**
 * A Monkey knows how to move around in a maze and leave a
 * colored trail behind.
 * @author Joe Zachary, November 2010
 */


/**
 * An object that can find its way through a maze, leaving
 * behind a colored trail showing its path
 */
public class Monkey extends JPanel {
		
	// The color of the monkey's trail
	private Color color;
	
	
	/**
	 * Creates a monkey of the specified color
	 * @param color
	 */
	public Monkey (Color color) {
		this.color = color;
	}

	
	/**
	 * The start and goal rooms are unstained.  This method
	 * investigates all possible paths that begin at start
	 * and do not go through any stained rooms.  As it enters
	 * rooms on the path, it stains them.  As it backs out,
	 * it unstains them.  At any given moment in time, then,
	 * the current path that the method is investigating is
	 * stained.
	 * 
	 * If the method reaches the goal, it returns true and
	 * leaves the path stained.  Otherwise, it returns false
	 * and erases all the stains that it made.
	 * 
	 * @param start The room where the search begins
	 * @param goal The room where the search ends
	 * @return
	 */
	public boolean doSearch (Room start, Room goal) {

		// Stain the floor so we'll know we've been here.
		start.addStain(color);

		// If we've reached our goal, stop searching.
		if (start == goal) {
			return true;
		}
		
		// If not, explore all directions except for the
		// one we entered the room through.  If none
		// of those work out, back up and try again.
		if (start.hasDoor("E")) {
			Room r = start.getRoom("E");
			if (!r.isStained(color) && doSearch(r, goal)) return true;
		}
		if (start.hasDoor("W")) {
			Room r = start.getRoom("W");
			if (!r.isStained(color) && doSearch(r, goal)) return true;
		}
		if (start.hasDoor("S")) {
			Room r = start.getRoom("S");
			if (!r.isStained(color) && doSearch(r, goal)) return true;
		}
		if (start.hasDoor("N")) {
			Room r = start.getRoom("N");
			if (!r.isStained(color) && doSearch(r, goal)) return true;
		}
	
		// Nothing worked.  Erase the stain and admit failure.
		start.removeStain(color);
		return false;

	}
	
}
