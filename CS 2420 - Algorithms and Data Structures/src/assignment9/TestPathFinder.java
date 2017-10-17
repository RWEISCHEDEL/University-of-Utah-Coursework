package assignment9;

import java.io.IOException;

/**
 * This method was given to us by Dr. Meyer as a main method to test our PathFinder and Node class.
 * @author Dr. Meyer
 *
 */
public class TestPathFinder {

	
	public static void main(String[] args) throws IOException {
		
		/*
		 * The below code assumes you have a file "tinyMaze.txt" in your project folder.
		 * If PathFinder.solveMaze is implemented, it will create a file "tinyMazeOutput.txt" in your project folder.
		 * You will have to browse to that folder to view the output, it will not
		 * automatically show up in Eclipse.
		 */
		PathFinder.solveMaze("demoMaze.txt", "demoMazeOutput.txt");
		
		
	}

}