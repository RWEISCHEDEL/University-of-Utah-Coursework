package cs1410;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * PS3 Homework Assignment - FileAnalyzer Method
 * @author Robert Weischedel
 * 9/18/14
 */

public class FileAnalyzer {

	/**
	 * This method with act just like the PS3Demo.jar program that was given to us. 
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Bring in a file
		JFileChooser chooser = new JFileChooser();
		int result = chooser.showOpenDialog(null);
		
		// If user closes out of file browser, then display "No file chosen"
		if (result != JFileChooser.APPROVE_OPTION)
		{
			JOptionPane.showMessageDialog(null, "No file chosen");
			return;
		}
		
		// Bring in and store the user chosen file as a File
		File userChosenFile = chooser.getSelectedFile();
		
		// Initialize Scanner
		Scanner readFile;
		
		// Get token string from 
		String token = JOptionPane.showInputDialog("Enter a token:");
		
		// Check user inputed token, to see if valid input
		if (token == null || token.trim().length() < 0)
		{
			JOptionPane.showMessageDialog(null, "Bad token entered");
			return;
		}
		
		// Reload the scanner each time, using the try and catch method before it used in each of the seven method calls.
		
		try {
			
		readFile = new Scanner (userChosenFile);
		// Call the countToken method
		int numTimesToken = TextAnalysis.countToken(readFile,token);
		JOptionPane.showMessageDialog(null, "Your token occurs " + numTimesToken + " times");
		}
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found");
			return;	
		}

		
		try {
			
		readFile = new Scanner (userChosenFile);
		// Call the averageTokenLength method
		double avgTokenLgth = TextAnalysis.averageTokenLength(readFile);
		String averageLength = String.format("%.2f", avgTokenLgth);
		JOptionPane.showMessageDialog(null, "The average token length is " + averageLength);
		}
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found");
			return;
			
		}
		
		try {
			
		readFile = new Scanner (userChosenFile);
		// Call the findLineWithToken method
		String lineWithToken = TextAnalysis.findLineWithToken(readFile, token);
		if (lineWithToken == null){
			JOptionPane.showMessageDialog(null, "No line with your token exists");
		}
		else{
		JOptionPane.showMessageDialog(null, "First line with your token:\n" + lineWithToken);
		}
		
		}
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found");
			return;
			
		}
		
		try {
			
		readFile = new Scanner (userChosenFile);
		// Call the findLongestPalindrome method
		String palindrome = TextAnalysis.findLongestPalindrome(readFile);
		if (palindrome == ""){
			JOptionPane.showMessageDialog(null, "No palindrome exists");
		}
		else {
			JOptionPane.showMessageDialog(null, "Longest palindrome: " + palindrome);
		}
		
		}
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found");
			return;	
		}
		
		try {
			
		readFile = new Scanner (userChosenFile);
		// Call the findLongestLine method
		String longestLine = TextAnalysis.findLongestLine(readFile);
		if (longestLine == null){
			JOptionPane.showMessageDialog(null, "No lines exist");
		}
		else{
		JOptionPane.showMessageDialog(null, "Longest line:\n" + longestLine);
		}
		}
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found");
			return;
		}
		
		try {
			
		readFile = new Scanner (userChosenFile);
		// Call the findMostWhitespace method
		String longestWhitespaceLine = TextAnalysis.findMostWhitespace(readFile);
		if (longestWhitespaceLine == null){
			JOptionPane.showMessageDialog(null, "No lines exist");
		}
		else{
		JOptionPane.showMessageDialog(null, "Line with most Whitespaces\n" + longestWhitespaceLine);
		}
		}
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found");
			return;
			
		}
		
		try {
			
		readFile = new Scanner (userChosenFile);
		// Call the findNextTokenInSortedOrder method
		String nextToken = TextAnalysis.findNextTokenInSortedOrder(readFile,token);
		if (nextToken == null){
			JOptionPane.showMessageDialog(null, "No token would follow yours");
		}
		else{
		JOptionPane.showMessageDialog(null, "This token would follow yours: " + nextToken);
		}
		}
		catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found");
			return;
			
		}
		
		
	}
	
	
}
