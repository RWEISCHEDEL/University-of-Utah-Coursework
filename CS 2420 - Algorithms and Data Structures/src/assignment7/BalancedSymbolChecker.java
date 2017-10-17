package assignment7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class containing the checkFile method for checking if the (, [, and { symbols in an input file are correctly matched. We implemented the 
 * checkFile and main methods, while the other methods were provided for us by Dr. Meyer. We made no alterations to those given methods and 
 * thier commenting. This class as a pusedo-complier for our assignment 7. It is also used to test our MyStack class method. All files brought 
 * in were given to us by Dr. Meyer.
 * 
 * @author Robert Weischedel and Tanner Martin
 */
public class BalancedSymbolChecker {

	/**
	 * We use this main method to test the checkFile method. We call the checkFile method from here in main and feed in the string filename.
	 * 
	 * @param args
	 * @throws FileNotFoundException - From checkFile if the file is nonexistent or lost.
	 */
	/*public static void main(String[] args) throws FileNotFoundException {

		checkFile("Class13.java");
		checkFile("Class14.java");
		checkFile("Class11.java");
		checkFile("Class12.java");
	}*/

	/**
	 * Returns a message indicating whether the input file has unmatched symbols. (Use the methods below for constructing messages.) Throws
	 * FileNotFoundException if the file does not exist. 
	 * 
	 * @throws FileNotFoundException
	 */
	public static String checkFile(String filename) throws FileNotFoundException {
		
		// Create a new stack and file from the filename that was brought in.
		MyStack<Character> stack = new MyStack<Character>();
		File file = new File(filename);

		// Create a Scanner to store the File into.
		Scanner checkFile;

		// Create boolean flags to tell if a block comment has begun, or a string " has begun or a char ' has begun. They are true when there 
		// are none of the values.
		boolean commentCheck = true;
		boolean stringCheck = true;
		boolean charCheck = true;

		// Try and put the file into the scanner, if not throw the exception.
		try {
			checkFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}

		// Variable to keep track of line number for error printout message.
		int lineNum = 0;
		
		// Begin looping through each line of the file.
		while (checkFile.hasNextLine()) {
			
			// Save the line as a string, get the size of the line, and increment the line number variable.
			String line = checkFile.nextLine();
			int lineSize = line.length();
			lineNum++;

			// Loop through each character in the current line and perform checks on it
			for (int columnNum = 0; columnNum < lineSize; columnNum++) {

				// Pull and check the char at each index.
				char checkChar = line.charAt(columnNum);
				
				// If it isn't the end of the line, and it isn't a comment block, see if the checkChar is a '.
				if((columnNum < lineSize-1) && (checkChar =='\'') && commentCheck)
				{
					// Toggle/untoggle charCheck for when ' is found.
					if(charCheck){
						charCheck = false;
					}
					else{
						charCheck = true;
					}
				}
				
				// If it isn't the end of the line, and it isn't a comment block or comment line, see if the checkChar is a ".
				if((columnNum < lineSize-1) && (checkChar == '\"') && (line.charAt(columnNum -1) != '\\') && commentCheck)
				{
					// Toggle/untoggle stringCheck when " is found.
					if(stringCheck){
						stringCheck = false;
					}
					else{
						stringCheck = true;
					}
					
				}
				
				// If it isn't the end of the line, and if c is / check and see if it is a comment by checking checkChar index + 1 is /
				if ((columnNum < lineSize-1) && (checkChar == '/' && line.charAt(columnNum + 1) == '/')){
					break;
				}

				// If it isn't the end of the line, and if c is / check and see if it is a block comment by checking checkChar index + 1 is 
				// * and toggle commentCheck
				if ((columnNum < lineSize-1) && (checkChar == '/' && line.charAt(columnNum + 1) == '*')) {
					commentCheck = false;
				}
				
				// If it isn't the end of the line, and if c is * check and see if it is a comment by checking checkChar index + 1 is / and
				// untoggle commentCheck
				if ((columnNum < lineSize-1) && (checkChar == '*' && line.charAt(columnNum + 1) == '/')) {
					commentCheck = true;
				}

				// Check and see if checkChar is a opening symbol. If it is and all flags are untoggled push it onto the stack, else if its
				// a closing symbol and all flags are untoggled pop the stack and compare checkChar with the popped element.
				if ((checkChar == '(' || checkChar == '[' || checkChar == '{') && commentCheck && stringCheck && charCheck) {
					//Push the open symbol onto the stack
					stack.push(checkChar);
					
				} else if ((checkChar == ')' || checkChar == ']' || checkChar == '}') && commentCheck && stringCheck && charCheck) {

					// If the stack is empty throw a unmtachedSymbol message.
					if (stack.size() == 0) {
						return unmatchedSymbol(lineNum, columnNum + 1, checkChar, ' ');
					}
					
					// Pop the top element in the stack
					char check = stack.pop();

					// Compare the popped element with checkChar element to see if dont we have a matching open/closed pair. And throw the
					// correct unmatedSymbol message.
					if (check == '(' && checkChar != ')') {
						return unmatchedSymbol(lineNum, columnNum + 1, checkChar, expectedLastSymbol(check));
					} else if (check == '[' && checkChar != ']') {
						return unmatchedSymbol(lineNum, columnNum + 1, checkChar, expectedLastSymbol(check));
					} else if (check == '{' && checkChar != '}') {
						return unmatchedSymbol(lineNum, columnNum + 1, checkChar, expectedLastSymbol(check));
					}
				}
			}
		}
		
		// Check and see if a comment block was left unclosed.
		if(commentCheck == false){
			return unfinishedComment();
		}

		// If the stack is empty throw allSymbolsMatch message, if not throw an unmatchedSymbol at end of file message
		if (stack.isEmpty()) {
			return allSymbolsMatch();
		} else {
			char last = stack.pop();
			return unmatchedSymbolAtEOF(expectedLastSymbol(last));
		}
	}

	/**
	 * This method is a helper method we used to aid in printing the expected symbol portion of the error messages. It takes in the lastSymbol
	 * of the stack and then returns its complement closing symbol.
	 * 
	 * @param lastSymbol - A char value of the top symbol found in the stack.
	 * @return - The expected char symbol to close the lastSymbol brought in.
	 */
	private static char expectedLastSymbol(char lastSymbol) {
		if (lastSymbol == '(') {
			return ')';
		} else if (lastSymbol == '[') {
			return ']';
		} else {
			return '}';
		}
	}

	/**
	 * Returns an error message for unmatched symbol at the input line and
	 * column numbers. Indicates the symbol match that was expected and the
	 * symbol that was read.
	 */
	private static String unmatchedSymbol(int lineNumber, int colNumber,
			char symbolRead, char symbolExpected) {
		return "ERROR: Unmatched symbol at line " + lineNumber + " and column "
				+ colNumber + ". Expected " + symbolExpected + ", but read "
				+ symbolRead + " instead.";
	}

	/**
	 * Returns an error message for unmatched symbol at the end of file.
	 * Indicates the symbol match that was expected.
	 */
	private static String unmatchedSymbolAtEOF(char symbolExpected) {
		return "ERROR: Unmatched symbol at the end of file. Expected "
				+ symbolExpected + ".";
	}

	/** Returns an error message for a file that ends with an open /* comment. */
	private static String unfinishedComment() {
		return "ERROR: File ended before closing comment.";
	}

	/** Returns a message for a file in which all symbols match. */
	private static String allSymbolsMatch() {
		return "No errors found. All symbols match.";
	}
}