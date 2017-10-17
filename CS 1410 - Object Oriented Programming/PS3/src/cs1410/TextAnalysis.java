package cs1410;

import java.util.Scanner;
/**
 * PS3 Homework Assignment - TextAnalysis Method
 * @author Robert Weischedel
 * 9/18/14
 */
public class TextAnalysis {

	public static void main(String[] args) {
	}

	/**
	 * countToken: Takes a Scanner s and a String t as parameters, and returns
	 * an int. It returns the number of time that t occurs as a token in s.
	 * 
	 * @param s
	 *            - Scanner from user
	 * @param t
	 *            - String from user containing token they want to find.
	 * @return numTokens - number of times token occurs in scanner
	 */
	public static int countToken(Scanner s, String t) {
		int numTokens = 0;
		String wordCheck = "";
		while (s.hasNext()) {
			wordCheck = s.next();
			if (wordCheck.equals(t)) {
				numTokens++;
			}
		}
		return numTokens;
	}

	/**
	 * countWhitespace: Takes a String s as a parameter and returns an int. It
	 * returns the number of whitespace characters contained in s.
	 * 
	 * @param s
	 *            - String from user or findMostWhitespace method
	 * @return whiteSpaceCount - number of white spaces found in string
	 */
	public static int countWhitespace(String s) {
		int lengthOfString = s.length();
		int counter = 0;
		int whiteSpaceCount = 0;
		while (counter < lengthOfString) {
			if (Character.isWhitespace(s.charAt(counter))) {
				whiteSpaceCount++;
			}
			counter++;
		}
		return whiteSpaceCount;
	}

	/**
	 * averageTokenLength: Takes a Scanner s as a parameter and returns a
	 * double. If s contains no tokens, it returns 0. Otherwise, it returns the
	 * average length of all the tokens in s.
	 * 
	 * @param s
	 *            - Scanner from user
	 * @return - a double containing the average length of all tokens from the
	 *         Scanner
	 */
	public static double averageTokenLength(Scanner s) {
		double numTokens = 0;
		double allTokensLength = 0;
		String wordCheck = "";
		while (s.hasNext()) {
			wordCheck = s.next();
			allTokensLength = allTokensLength + wordCheck.length();
			numTokens++;
		}
		if (numTokens == 0) {
			return 0;
		} else {
			return allTokensLength / numTokens;
		}
	}

	/**
	 * isPalindrome: Takes a String s as a parameter and returns a boolean. It
	 * returns true if s reads the same forwards and backwards, and returns
	 * false otherwise.
	 * 
	 * @param s
	 *            - String from the user or findLongestPalindrome method
	 * @return - a boolean determining if the value in the string is a
	 *         palindrome or not
	 */
	public static boolean isPalindrome(String s) {
		int wordLength = s.length() - 1;
		String backwards = "";
		while (wordLength >= 0) {
			backwards = backwards + s.charAt(wordLength);
			wordLength--;
		}
		if (s.equals(backwards)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * countainsToken: Takes a String s and a String t as parameters, and
	 * returns a boolean. It returns true if t occurs as a token within s, and
	 * returns false otherwise.
	 * 
	 * @param s
	 *            - String containing line from user or findLineWithToken method
	 * @param t
	 *            - String containing token from user or findLineWithToken
	 *            method
	 * @return - a boolean determining if the token is found in s
	 */
	public static boolean containsToken(String s, String t) {
		String wordCheck = "";
		Scanner z = new Scanner(s);

		while (z.hasNext()) {
			wordCheck = z.next();
			if (wordCheck.equals(t)) {
				z.close();
				return true;
			}

		}
		z.close();
		return false;
	}

	/**
	 * findLineWithToken: Takes a Scanner s and a String t as parameters, and
	 * returns a String. It returns a line from s that contains t as a token (if
	 * such a line exists) and returns null otherwise.
	 * 
	 * @param s
	 *            - Scanner from user
	 * @param t
	 *            - String containing token user wishes to find
	 * @return - Null if line doesn't contain the token or it returns the line
	 *         that contains the token
	 */
	public static String findLineWithToken(Scanner s, String t) {
		String lineToCheck = "";
		while (s.hasNextLine()) {
			lineToCheck = s.nextLine();
			if (containsToken(lineToCheck, t)) {
				return lineToCheck;
			}
		}
		return null;
	}

	/**
	 * findLongestPalindrome: Takes a Scanner s as its parameter and returns a
	 * String. It returns the longest token from s that is a palindrome (if one
	 * exists) or the empty string (otherwise).
	 * 
	 * @param s
	 *            - Scanner from user
	 * @return longestPalindrome - the longest palindrome found in the scanner
	 */
	public static String findLongestPalindrome(Scanner s) {
		String longestPalindrome = "";
		String wordCheck = "";
		while (s.hasNext()) {
			wordCheck = s.next();
			if (isPalindrome(wordCheck)) {
				if (wordCheck.length() > longestPalindrome.length()) {
					longestPalindrome = wordCheck;
				}
			}
		}
		return longestPalindrome;
	}

	/**
	 * findLongestLine: Takes a Scanner s as its parameter and returns a String.
	 * It returns the longest line from s (if one exists) or null (otherwise).
	 * 
	 * @param s
	 *            - Scanner from user
	 * @return longestLine - returns the longest line found in the scanner
	 */
	public static String findLongestLine(Scanner s) {
		String longestLine = "";
		String lineToCheck = "";
		while (s.hasNextLine()) {
			lineToCheck = s.nextLine();
			if (lineToCheck.length() > longestLine.length()) {
				longestLine = lineToCheck;
			}
		}
		if (longestLine == "") {
			return null;
		} else {
			return longestLine;
		}
	}

	/**
	 * findMostWhitespace: Takes a Scanner s as its parameter and returns a
	 * String. It returns the line from s that contains the most whitespace (if
	 * one exists) or null (otherwise).
	 * 
	 * @param s
	 *            - Scanner from user
	 * @return maxWhitespace - returns the line that has the most white
	 *         space in it
	 */
	public static String findMostWhitespace(Scanner s) {
		if (!s.hasNext()){
			return null;
		}
		String checkLine = "";
		String maxWhitespace = s.nextLine();
		
		while (s.hasNextLine()) {
			checkLine = s.nextLine();
			if (countWhitespace(checkLine) > countWhitespace(maxWhitespace)) {
				maxWhitespace = checkLine;
			}

		}
			return maxWhitespace;
	}

	/**
	 * findNextTokenInSortedOrder takes a Scanner s and a String t as its
	 * parameter, and returns a String. It returns the token from s that would
	 * occur immediately after t if all the tokens from s plus t were sorted
	 * into ascending order. If no such token exists, the method should return
	 * null.
	 * 
	 * @param s
	 *            - Scanner from user
	 * @param t
	 *            - String containing token the user wishes to use
	 * @return smallVal - the value that is next in order after the token value
	 *         or null if no such value exists
	 */
	public static String findNextTokenInSortedOrder(Scanner s, String t) {
		String checkToken = "";
		String firstTokenList = "";

		// First search through the scanner and find all the values greater than the token value and store them in a string
		while (s.hasNext()) {
			checkToken = s.next();
			if (t.compareTo(checkToken) < 0) {
				firstTokenList = firstTokenList + " " + checkToken;
			}
		}

		// If the string is empty return null
		if (firstTokenList == "") {
			return null;
		}
		// Put the string in a new scanner and begin searching for the smallest value out of the scanner
		Scanner z = new Scanner(firstTokenList);
		String smallVal = z.next();
		while (z.hasNext()) {
			checkToken = z.next();
			if (smallVal.compareTo(checkToken) > 0) {
				smallVal = checkToken;
			}
		}
		z.close();
		return smallVal;

	}
}
