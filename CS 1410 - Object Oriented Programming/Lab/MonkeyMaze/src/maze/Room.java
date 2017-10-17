package maze;

import java.awt.*;
import java.util.*;

import javax.swing.*;


/**
 * Represents a room in a maze
 * @author Joe Zachary, November 2011
 */

public class Room extends JPanel {
	
	private HashMap<String,Room> walls;   // Maps orientations to rooms on the other side of walls
	private HashMap<String,Room> doors;   // Maps orientations to rooms on the other side of doors
	private ArrayList<Color> stains;      // Colors that should be used to stain the floor
	
	
	/**
	 * Creates an empty room with no walls, doors, or stains
	 */
	public Room () {
		walls = new HashMap<String,Room>();
		doors = new HashMap<String,Room>();
		stains = new ArrayList<Color>();
	}
	
	
	/**
	 * Links the room to a neighbor and puts a solid wall in between.
	 * @param room Neighboring room
	 * @param orientation "N", "E", "S", or "W"
	 */
	public void addWall (Room room, String orientation) {
		if (room != null) {
			walls.put(orientation, room);
		}	
	}
	
	
	/**
	 * Returns a list of all the rooms that are separated from this
	 * room with solid walls.
	 * @return
	 */
	public ArrayList<Room> getWalledRooms () {
		return new ArrayList<Room>(walls.values());
	}
	
	
	
	/**
	 * Puts a door through the wall between a neighboring room
	 * @param room
	 */
	public void makeDoor (Room room) {
		for (String o: walls.keySet()) {
			if (walls.get(o) == room) {
				walls.remove(o);
				doors.put(o, room);
				return;
			}
		}
	}
	
	
	/**
	 * Reports whether there's a door in the specified direction
	 * @param face "N", "E", "S", or "W"
	 * @return
	 */
	public boolean hasDoor (String face) {
		return doors.get(face) != null;
	}
	
	
	/**
	 * Returns the room on the other side of the door in the
	 * specified direction
	 * @param face "N", "E", "S", or "W"
	 * @return
	 */
	public Room getRoom (String face) {
		return (Room) doors.get(face);
	}
	
	
	/**
	 * Clears out all the stains.
	 */
	public void clear () {
		synchronized (this) {
			stains = new ArrayList<Color>();
		}
	}
	
	
	/** Adds a stain, and throws in a delay for the
	 *  purposes of animation.
	 *  @param stain
	 */
	public void addStain (Color stain) {
		synchronized (this) {
			stains.add(stain);
		}
		repaint();
		delay();
	}
	
	
	/**
	 * Removes a stain, and throws in a delay for the
	 * purposes of animation.
	 * @param stain
	 */
	public void removeStain (Color stain) {
		synchronized (this) {
			stains.remove(stain);
		}
		repaint();
		delay();
	}
	
	
	/**
	 * Does a 10-millisecond delay.
	 */
	private static void delay () {
		try {
			Thread.sleep(10);
		}
		catch (Exception e) {	
		}
	}
	
	
	/**
	 * Reports whether the room has a particular stain.
	 * @param color
	 * @return
	 */
	public boolean isStained (Color color) {
		return stains.contains(color);
	}
	
	
	/**
	 * Paints the floor of the room.
	 */
	public void paintComponent (Graphics g) {
		Dimension size = getSize();
		synchronized (this) {
			g.setColor(setColors());
		}
		g.fillRect(0, 0, size.width, size.height);
	}
	
	
	/**
	 * Paints the walls of the room
	 */
	public void paintBorder (Graphics g) {
		Dimension size = getSize();
		g.setColor(Color.black);
		if (!doors.containsKey("N")) g.drawLine(0, 0, size.width-1, 0);
		if (!doors.containsKey("W")) g.drawLine(0, 0, 0, size.height-1);
		if (!doors.containsKey("E")) g.drawLine(size.width-1, 0, size.width-1, size.height-1);
		if (!doors.containsKey("S")) g.drawLine(0, size.height-1, size.width-1, size.height-1);
	}
	

	/**
	 * Combines the various stains into an overall stain
	 * @return
	 */
	private Color setColors () {

		// If there are no stains, used white
		if (stains.size() == 0) {
			return Color.white;
		}
		
		// If there is one stain, use it
		if (stains.size() == 1) {
			return stains.get(0);
		}
		
		// Otherwise, combine the stains
		float red = 0;
		float blue = 0;
		float green = 0;
		double gamma = 2.0;
		for (Color stain: stains) {
			red += Math.pow(stain.getRed()/255.0, gamma);
			blue += Math.pow(stain.getBlue()/255.0, gamma);
			green += Math.pow(stain.getGreen()/255.0, gamma);
		}
		return new Color((float) Math.pow(red/stains.size(), 1/gamma),
						 (float) Math.pow(green/stains.size(), 1/gamma),
			             (float) Math.pow(blue/stains.size(), 1/gamma));
	}


}
