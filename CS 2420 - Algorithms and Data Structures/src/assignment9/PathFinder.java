package assignment9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class is the heart of our Assignment 9 implementation. This class implements several methods that are all a crucial part of solving the 
 * inputed maze text files. For this implementation of a Maze solver using Graphing techniques we used a Breadth First Search algorithm. It should 
 * also be noted that all methods in this are static as well. The solveMaze method acts as a defacto main method in this class as all other classes 
 * are called from it and return to it. For this implementation of a graph we choose to use a 2-D format to represent all of our nodes due to the 
 * inputed Pac-Man mazes only being able to move up, down, left, right.
 * 
 * @author Robert Weischedel and Tanner Martin
 *
 */
public class PathFinder
{
	/**
	 * This method does all the file work and calling of the other methods in this class. It first takes in two strings that are the names for the 
	 * input and output files we wish to write. It finds the correct input file and saves it as a BufferedReader so that it can be used in the fill
	 * method. It then pulls the height and width out of the BufferedReader and then calls the breadthFirstSearch method, and from their writes the
	 * solved array into a new file on the outputFile Name.
	 * 
	 * @param inputFile - The file name of the maze you wish to solve.
	 * @param outputFile - The file name you wish to save the solved maze too.
	 * @throws IOException - Perpetuated from the fill method if the BufferedReader is empty or null.
	 */
	public static void solveMaze(String inputFile, String outputFile) throws IOException
	{
		// Create a new FileReader and BufferedReader to interact and store the desired input file
		FileReader temp;
		BufferedReader input = null;
		
		// Store the input file as a FileReader, then use the temp file to create a BufferedReader
		try
		{
			temp = new FileReader(inputFile);
			input = new BufferedReader(temp);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		// Retrieve the first line of the BufferedReader maze file to get the height and width.
		String[] dimensions = input.readLine().split(" ");

		int height = Integer.parseInt(dimensions[0]);
		int width = Integer.parseInt(dimensions[1]);

		// Create an array of Nodes to put the entire maze into. We accomplish this by calling the fill method.
		Node filledArray[][] = fill(input, height, width);

		// Take the filled array and find the shortest path.
		Node solvedArray[][] = breadthFirstSearch(filledArray);

		// Take the solved array and save it to the chosen outputFile name.
		PrintWriter output;
		try
		{
			// Create a new FileWriter and PrintWrite in order to interact with the solvedArray
			FileWriter file = new FileWriter(outputFile);
			output = new PrintWriter(file);
			
			// Save the height and width to the File
			output.println(height + " " + width);

			// Loop through the solved array and fill the file.
			for (int i = 0; i < solvedArray.length; i++)
			{
				for (int j = 0; j < solvedArray[i].length; j++)
				{
					if(solvedArray[i][j] == null)
					{
						output.print("X");
					}
					else
					{
						output.print(solvedArray[i][j].getSymbol());
					}
				}
				output.println();
			}
			output.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * This method takes in the BufferedReader from solveMaze and the height and width and returns a filled array of Nodes with all the chars from the
	 * BufferedReader in correct order. 
	 * 
	 * @param input - The BufferedReader from solveMaze containing the maze from the desired input file.
	 * @param height - The height of the maze.
	 * @param width - The width of the maze.
	 * @return - A 2-D Node array willed with all the chars from the BufferedReader.
	 * @throws IOException - If the BufferedReader is null or empty.
	 */
	public static Node[][] fill(BufferedReader input, int height, int width) throws IOException
	{
		// Create a Sting to fill with the lines from the buffered reader.
		String line = null;
		
		// Create a 2-D node array of the correct size.
		Node nodeArray[][] = new Node[height][width];

		// Loop through and go through each line and each char of each line and fill the node array with the correct node.
		for (int i = 0; i < height; i++)
		{
			line = input.readLine();
			for (int j = 0; j < width; j++)
			{
				char compare = line.charAt(j);
				if(compare == 'X')
				{
					nodeArray[i][j] = null;
				}
				else if(compare == 'S')
				{
					nodeArray[i][j] = new Node('S', i, j);
				}
				else if(compare == 'G')
				{
					nodeArray[i][j] = new Node('G', i, j);
				}
				else
				{
					nodeArray[i][j] = new Node(' ', i, j);
				}
			}

		}
		
		// Return the filled node array
		return nodeArray;
	}

	/**
	 * This method is a helper method for the breadthFirstSearch Method. It finds the start or S row and column values of the inputed node array and 
	 * returns those two values in and int array. 
	 *  
	 * @param inputedArray - The node array you wish to find the row and col values of its start node.
	 * @return - An int array with the row and column values stored in it.
	 */
	public static int[] getStart(Node[][] inputedArray)
	{
		// The int array we are going to return with the row and column value in it.
		int[] indexArray = new int[2];
		
		// Loop through the node array until you find the S char
		for (int i = 0; i < inputedArray.length; i++)
		{
			for (int j = 0; j < inputedArray[i].length; j++)
			{
				if(inputedArray[i][j] == null)
				{

				}
				else if(inputedArray[i][j].getSymbol() == 'S')
				{
					indexArray[0] = i;
					indexArray[1] = j;
					break;
				}

			}
		}
		
		// Return the int array
		return indexArray;
	}

	/**
	 * This method does all of the work in order to find the shortest path. In order to find the shortest path, this method uses a breath first search
	 * algorithm to search through the inputed 2-D node array.
	 * 
	 * @param inputedArray - The 2-D node array filled with nodes from the solveMaze method.
	 * @return - A 2-D node array that has been solved.
	 */
	public static Node[][] breadthFirstSearch(Node[][] inputedArray)
	{
		// Create a queue in order to keep tract of which neighbors to check next.
		Queue<Node> queue = new LinkedList<Node>();

		// If the inputArray is null throw and IllegalArgumentException
		if(inputedArray == null)
		{
			throw new IllegalArgumentException();
		}

		// Call the getStart method to get the row/col value of the start location.
		int[] locations = getStart(inputedArray);

		// Pull the start node from the inputedArray
		Node start = inputedArray[locations[0]][locations[1]];

		// Set visited to True and add start to queue
		start.setVisitedTrue();
		queue.add(start);

		// Set curr to start in order to begin the while loop.
		Node curr = start;

		// Loop while the queue is not empty.
		while (!queue.isEmpty())
		{
			// Pull the first item from the queue
			curr = queue.poll();

			// Check to see if curr is our goal
			if(curr.getSymbol() == 'G')
			{
				break;
			}

			// Look at the neighbors to the bottom, top, left and right of curr. Add them to the queue if they have not been visited or are not walls.
			// If they meet that criteria, set their cameFrom and visited to true.
			if(inputedArray[curr.getRow() + 1][curr.getColumn()] != null
					&& inputedArray[curr.getRow() + 1][curr.getColumn()].getVisited() == false)
			{
				inputedArray[curr.getRow() + 1][curr.getColumn()].setCameFrom(curr);
				inputedArray[curr.getRow() + 1][curr.getColumn()].setVisitedTrue();
				queue.add(inputedArray[curr.getRow() + 1][curr.getColumn()]);
			}
			if(inputedArray[curr.getRow() - 1][curr.getColumn()] != null
					&& inputedArray[curr.getRow() - 1][curr.getColumn()].getVisited() == false)
			{
				inputedArray[curr.getRow() - 1][curr.getColumn()].setCameFrom(curr);
				inputedArray[curr.getRow() - 1][curr.getColumn()].setVisitedTrue();
				queue.add(inputedArray[curr.getRow() - 1][curr.getColumn()]);
			}
			if(inputedArray[curr.getRow()][curr.getColumn() + 1] != null
					&& inputedArray[curr.getRow()][curr.getColumn() + 1].getVisited() == false)
			{
				inputedArray[curr.getRow()][curr.getColumn() + 1].setCameFrom(curr);
				inputedArray[curr.getRow()][curr.getColumn() + 1].setVisitedTrue();
				queue.add(inputedArray[curr.getRow()][curr.getColumn() + 1]);
			}
			if(inputedArray[curr.getRow()][curr.getColumn() - 1] != null
					&& inputedArray[curr.getRow()][curr.getColumn() - 1].getVisited() == false)
			{
				inputedArray[curr.getRow()][curr.getColumn() - 1].setCameFrom(curr);
				inputedArray[curr.getRow()][curr.getColumn() - 1].setVisitedTrue();
				queue.add(inputedArray[curr.getRow()][curr.getColumn() - 1]);
			}

		}

		// If G was not found return the unaltered input node array.
		if(curr.getSymbol() != 'G')
		{
			return inputedArray;
		}

		// Loop through starting at G till you reach S building a path back using the came from references.
		while (curr.getSymbol() != 'S')
		{
			curr = curr.getCameFrom();

			if(curr.getSymbol() == ' ')
			{
				curr.setSymbol('.');
			}
		}
		
		// Return the altered input array.
		return inputedArray;

	}

}
