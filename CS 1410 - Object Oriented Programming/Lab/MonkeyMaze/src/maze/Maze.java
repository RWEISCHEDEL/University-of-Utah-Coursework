package maze;

import java.awt.*;
import java.util.*;
import javax.swing.*;


/**
 * Provides a searchable maze as a JPanel that can be incorporated
 * into a GUI.  There is always a unique path between any two rooms.
 * @author Joe Zachary for CS 3500, November 2010
 */

public class Maze extends JPanel {
	
	private Room[][] rooms;   // The rooms in the maze
	private int rows;         // Number of rows in the maze
	private int columns;      // Number of columns in the maze
	private Random rand;      // Random number generator
	
	
	/**
	 * Creates a randomly generated maze.
	 * @param rows
	 * @param columns
	 */
	public Maze (int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		rand = new Random();
		setLayout(new GridLayout(rows,columns));
		reset();
	}
	
	
	/** 
	 * Resets and recarves the maze
	 */
	public void reset () {
		removeAll();
		rooms = new Room[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Room r = new Room();
				rooms[i][j] = r;
			}
		}
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				Room room = rooms[r][c];
				room.addWall(get(r-1,c), "N");
				room.addWall(get(r,c+1), "E");
				room.addWall(get(r+1,c), "S");
				room.addWall(get(r,c-1), "W");
				add(room);
			}
		}
		carve(rooms[rows-1][columns-1]);

	}	
	
	
	/**
	 * Removes any stains from the rooms
	 */
	public void unstain () {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				rooms[r][c].clear();
			}
		}
	}
	

	/**
	 * Returns the number of rows in the maze
	 * @return
	 */
	public int getRows () {
		return rows;
	}
	
	
	/**
	 * Returns the number of columns in the maze
	 * @return
	 */
	public int getCols () {
		return columns;
	}
	
	
	/**
	 * Returns the room at the specified row and column, or
	 * null if there is no such room.
	 * @param r The row
	 * @param c The column
	 * @return
	 */
	public Room get (int r, int c) {
		try {
			return rooms[r][c];
		}
		catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	
	/**
	 * Carves a maze.  All the rooms are required to have
	 * four walls when this method is called.
	 * @param current Starting point for carving
	 */
	private void carve (Room current) {
			HashSet<Room> visited = new HashSet<Room>();
			ArrayList<Room> path = new ArrayList<Room>();
			visited.add(current);
			path.add(current);
			extendPath(visited, path);
	}
	

	/**
	 * Helper method for carve
	 * @param visited Rooms that have been visited so far
	 * @param path Currently path of rooms visited
	 */
	private void extendPath (HashSet<Room> visited, ArrayList<Room> path) {
		while (visited.size() != rows*columns) {
			Room current = path.get(path.size()-1);
			ArrayList<Room> adjacent = current.getWalledRooms();
			boolean found = false;
			while (adjacent.size() > 0) {
				Room room = adjacent.remove(rand.nextInt(adjacent.size()));
				if (visited.contains(room)) continue;
				visited.add(room);
				path.add(room);
				current.makeDoor(room);
				room.makeDoor(current);
				found = true;
				break;
			}
			if (!found) {
				path.remove(path.size()-1);
			}
		}
	}


}
