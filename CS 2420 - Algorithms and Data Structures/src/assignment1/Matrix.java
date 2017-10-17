package assignment1;

/**
 * This class creates new integer Matrix objects, buy bringing in a two dimensional array of ints. There are several
 * methods that could be enacted on the Matrix objects that are defined in the class as well. Equals, times, plus, 
 * and to string. 
 * @author Dr. Meyer and Robert Weischedel
 * 1/22/15
 * CS 2420
 */
public class Matrix {
	// Variables to hold the number of Rows and Columns found in a Matrix, and a new two dimensional array that will
	// serve as the Matrix (data).
	int numRows;
	int numColumns;
	int data[][];
	
	// Constructor with data for new matrix (automatically determines dimensions)
	public Matrix(int d[][])
	{
		numRows = d.length; // d.length is the number of 1D arrays in the 2D array
		if(numRows == 0)
			numColumns = 0;
		else
			numColumns = d[0].length; // d[0] is the first 1D array
		data = new int[numRows][numColumns]; // create a new matrix to hold the data
		// copy the data over
		for(int i=0; i < numRows; i++) 
			for(int j=0; j < numColumns; j++)
				data[i][j] = d[i][j];
	}
	
	@Override // instruct the compiler that we do indeed intend for this method to override the superclass' (Object) version
	public boolean equals(Object o)
	{
		if(!(o instanceof Matrix)) // make sure the Object we're comparing to is a Matrix
			return false;
		Matrix m = (Matrix)o; // if the above was not true, we know it's safe to treat 'o' as a Matrix
		
		// First check and see if both Matrix objects have the same number of rows and cols. If not return false.
		if((this.numColumns != m.numColumns) || (this.numRows != m.numRows)){
			return false;
		}
		
		// Search through each value located in the Matrix and see if it matches the value
		// in the corresponding location. Return false if not.  
		for(int i =0; i < m.numRows; i++){
			for(int j = 0; j < m.numColumns; j++){
				if(m.data[i][j] != this.data[i][j]){
					return false;
				}
			}
		}
		
		return true; 
	}
	
	@Override // instruct the compiler that we do indeed intend for this method to override the superclass' (Object) version
	public String toString()
	{
		// Create a string to store the soon to be created Matrix String
		String matrixToString = "";
		
		// Pull out each Matrix value, and store it in matrixToString
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numColumns; j++){
				matrixToString = matrixToString + data[i][j] + " ";
			}
			matrixToString = matrixToString + "\n";
		}
		// Return the String Object
		return matrixToString;
	}
	
	/**
	 * This method allows two Matrices to be multiplied together if they are compatible. It returns a new Matrix object.
	 * @param m - A matrix object
	 * @return
	 */
	public Matrix times(Matrix m)
	{
		// Check and see if the Matrices are compatible to be multiplied, if not return null. 
		if(this.numColumns != m.numRows){
			return null;
		}
		
		// Create a new two dimensional array to store the values of the matrix multiplication.
		int placeHolderData[][] = new int[this.numRows][m.numColumns];
		
		int sum = 0;
		
		// Use three for loops to sort through the Matrix data values.
		int i, j, k;
		for(i = 0; i < this.numRows; i++){
			for(j = 0; j < m.numColumns; j++){
				for(k = 0; k < m.numRows; k++){
					sum += this.data[i][k] * m.data[k][j];
				}
				placeHolderData[i][j] = sum;
				sum = 0;
			}
		}
		
		// Create a new Matrix object from the new two dimensional array
		Matrix multMatrix = new Matrix(placeHolderData);

		return multMatrix;
	}
	
	/**
	 * This method allows two matrices to the added together if they are compatible. It returns a new Matrix object if
	 * the addition was successful
	 * @param m - A matrix object
	 * @return
	 */
	public Matrix plus(Matrix m)
	{

		// Check to ensure the matrices are compatible to be added together, if not return null
		if((this.numColumns != m.numColumns) || (this.numRows != m.numRows)){
			return null;
		}
		
		// Create a new two dimensional array to store the values of the matrix addition.
		int placeHolderData[][] = new int[m.numRows][m.numColumns];
		
		// Add up the values in the two Matrix objects
		for(int i = 0; i < m.numRows; i++){
			for(int j = 0; j < m.numColumns; j++){
				placeHolderData[i][j] = this.data[i][j] + m.data[i][j];
			}
		}
		
		// Create a new Matrix object from the new two dimensional array
		Matrix addedMatrix = new Matrix(placeHolderData);
		
		
		return addedMatrix;
	}
}