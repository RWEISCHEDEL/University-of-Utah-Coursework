package assignment9;

/**
 * This class is used in Assignment 9. It is a representation of a Node that is used for our PathFinder class to represent the different symbols and their locations that are 
 * found in the PacMan maze grids. Contained in this class are all the associated methods to access and change the data that can be stored in the Node. Our rendition of a Node
 * is slightly different than your typical implementation of graph Node. Instead of saving the Node's neighbors in the Node, we saved the row and column value of each Node in
 * itself. Since this assignment is in a grid format, and the only directions we can move are up, down, left right we found it easier to just store the row and column number
 * of each Node.
 * 
 * @author Robert Weischedel and Tanner Martin
 *
 */
public class Node
{
	// These private member variables store all the information that the Node contains.
	
	// This private member variable contains the symbol at that location in the maze.
	private char symbol;
	
	// This private member variable sees if the node has been visited or not.
	private boolean visited;
	
	// This private member variable holds a reference to the node that was previously looked at before coming to this node.
	private Node cameFrom;
	
	// These private member variables contain the row and column values of each node in the grid.
	private int row;
	private int column;
	
	/**
	 * This acts as the constructor for a new Node object. It brings in three parameters in order to create a new node.
	 * 
	 * @param symbol - The Char symbol at the location of the newly created Node.
	 * @param row - The integer row value of the newly created Node.
	 * @param column - The integer column value of the newly created Node.
	 */
	public Node(char symbol, int row, int column)
	{
		// Store the parameter values to the private member variables.
		this.symbol = symbol;
		this.visited = false;
		this.row = row;
		this.column = column;
	}

	/**
	 * This method sets the symbol of the Node to the symbol that is brought in as the parameter.
	 * 
	 * @param newSymbol - The new char symbol that you wish to store in the node.
	 */
	public void setSymbol(char newSymbol)
	{
		this.symbol = newSymbol;
	}

	/**
	 * This method acts as a getter method for the symbol value of the Node.
	 * 
	 * @return - The current symbol of the node.
	 */
	public char getSymbol()
	{
		return symbol;
	}

	/**
	 * This method sets the visited private member variable to true. Since in our implementation we never need to reset the node to false.
	 */
	public void setVisitedTrue()
	{
		this.visited = true;
	}
	
	/**
	 * This method acts as a getter method for the row value of the Node.
	 * 
	 * @return - The row value of the Node.
	 */
	public int getRow()
	{
		return row;
	}
	
	/**
	 * This method acts as a getter method for the column value of the Node.
	 * 
	 * @return - The row value of the Node.
	 */
	public int getColumn()
	{
		return column;
	}
	
	/**
	 * This method acts as a getter method for the visited value of the Node.
	 * 
	 * @return - The visited value of the Node.
	 */
	public boolean getVisited(){
		return visited;
		
	}
	
	/**
	 * This method acts as a getter method for the cameFrom object of the Node.
	 * 
	 * @return - The node that preceded this node.
	 */
	public Node getCameFrom(){
		return cameFrom;
	}
	
	/**
	 * This method acts as a setter method for the cameFrom private member variable. It takes in a Node and sets it to cameFrom in this Node.
	 * 
	 * @param cameFrom - The node that preceded this node.
	 */
	public void setCameFrom(Node cameFrom){
		this.cameFrom = cameFrom;
	}
}
