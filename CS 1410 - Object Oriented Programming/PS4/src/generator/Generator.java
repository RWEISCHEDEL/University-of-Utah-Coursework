package generator;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * A main method and a safeToFile method. The main method also handles any user
 * input errors.
 * 
 * @author Robert Weischedel
 * 
 */
public class Generator {

	/**
	 * This main method : Asks the user for a text file to read Asks the user
	 * for two inputs, level of analysis and length of string returned Prints
	 * the resulting random string Calls the saveToFile method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JFileChooser chooser = new JFileChooser();
		int result = chooser.showOpenDialog(null);

		// If user closes out of file browser, then display "No file chosen"
		if (result != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "No file chosen");
			return;
		}

		// Bring in and store the user chosen file as a File
		File userChosenFile = chooser.getSelectedFile();

		// Ask the user for the level of analysis they wish to be done on the
		// text
		String analysisLevel = JOptionPane
				.showInputDialog("Please enter the level of analysis you wish to have:");

		// Checking to make sure input in valid
		if (analysisLevel == null || analysisLevel.trim().length() <= 0) {
			JOptionPane.showMessageDialog(null, "Not a valid response");
			return;
		}

		int level;

		try {
			level = Integer.parseInt(analysisLevel);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Only integer values allowed ");
			return;
		}

		// Ask the user for the length of the random string they wish to be
		// returned
		String lengthOfOutput = JOptionPane
				.showInputDialog("Please enter the length of output you wish to have:");

		// Checking to make sure input in valid
		if (lengthOfOutput == null || lengthOfOutput.trim().length() <= 0) {
			JOptionPane.showMessageDialog(null, "Not a valid response");
			return;
		}

		int outputLength;

		try {
			outputLength = Integer.parseInt(lengthOfOutput);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Only integer values allowed ");
			return;
		}

		// Convert file into a string
		String convertedFile = "";

		try {
			convertedFile = GeneratorLibrary.fileToString(userChosenFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Generate the random output string
		String randomOutput = GeneratorLibrary.generateText(convertedFile,
				level, outputLength);

		// Ask if they would wish to save the random string, if yes call
		// saveToFile method, if no close program
		try {
			int response = JOptionPane.showConfirmDialog(null,
					"The random output string is :\n" + randomOutput + "\n"
							+ "Do you wish to save this line?");
			if (response == JOptionPane.YES_OPTION) {
				saveToFile(randomOutput);
			}
			return;

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Save file doesn't exist");
		}
	}

	/**
	 * Brings in a string and saves it to a file of the users choosing.
	 * 
	 * @param randomOutput
	 * @throws FileNotFoundException
	 */
	public static void saveToFile(String randomOutput)
			throws FileNotFoundException {
		// Bring up a file chooser and get the user's choice
		JFileChooser chooser = new JFileChooser();
		int result = chooser.showSaveDialog(null);

		// If the user didn't choose a file, display a message and exit
		if (result != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "No file chosen");
			return;
		}

		// If the file exists, let the user know
		File file = chooser.getSelectedFile();
		if (file.exists()) {
			int answer = JOptionPane.showConfirmDialog(null,
					"File already exists, do you want to overwrite it?");
			if (answer != JOptionPane.YES_OPTION) {
				return;
			}
		}

		// Write the random string to the file
		try (PrintStream output = new PrintStream(file)) {
			output.print(randomOutput);
		}

	}

}
