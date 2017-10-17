package files;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The Explorer class creates a File Explorer GUI object. It's purpose is to create a GUI that allows the user to find
 * out specific information about a chosen folder.
 * @author Robert Weischedel
 * 
 */
public class Explorer extends JFrame implements ActionListener {

	// This variable serves as the name for the Close option in the JMenu.
	private JMenuItem closeItem;
	// This variable serves as the name for the Open option in the JMenu.
	private JMenuItem openItem;
	// This variable serves as the Label to print out the information from the largestItem method.
	private JLabel question1;
	// This variable serves as the Label to print out the information from the countItem method
	private JLabel question2;
	// This variable serves as the Label to print out the information from the numLargeFiles method
	private JLabel question3;
	// This variable serves as the Label to print out the information from the modifiedLast method
	private JLabel question4;
	

	// Create a new Explorer object GUI by calling it form the main method.
	public static void main(String[] args) {

		new Explorer();

	}

	/**
	 * This class serves as the constructor of the GUI, it handles the creation of the GUI and adds actionLisener
	 * to the JMenu options that are created. It also creates a JMenu list of options.
	 */
	public Explorer() {
		// Set up the GUI border and exit button.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("File Explorer");

		// Create main JPanel that all other items will be attached to.
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		setContentPane(mainPanel);

		// Create the JMenuBar to handle opening and finding a file and closing the GUI
		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		closeItem = new JMenuItem("Close");
		openItem = new JMenuItem("Open");
		openItem.addActionListener(this);
		closeItem.addActionListener(this);
		fileMenu.add(openItem);
		fileMenu.add(closeItem);
		menubar.add(fileMenu);
		setJMenuBar(menubar);
		
		// Create a JPanel to hold all the JLabels to display the desired information.
		JPanel questionPanel = new JPanel();
		questionPanel.setLayout(new GridLayout(4,1));
		question1 = new JLabel("");
		question2 = new JLabel("");
		question3 = new JLabel("");
		question4 = new JLabel("");
		questionPanel.add(question1);
		questionPanel.add(question2);
		questionPanel.add(question3);
		questionPanel.add(question4);
		

		// Add questionPanel to the mainPanel and make it visible.
		mainPanel.add(questionPanel, "Center");
		setContentPane(mainPanel);
		setSize(1200, 900);
		setVisible(true);
	}

	/**
	 * actionPerformed handles every option that can be selected from the JMenu and calls the appropriate
	 * methods to retrieve the selected folder and to extract the necessary information from it. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// If JMenu option Close is selected
		if (e.getSource() == closeItem) {
			dispose();
		}

		// If JMenu option Open is selected
		if (e.getSource() == openItem) {
			// Allow the user to choose a folder
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = chooser.showOpenDialog(null);

			// If user closes out of file browser, then display "No file chosen"
			if (result != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(null, "No File Chosen");
				return;
			}

			// Bring in and store the user chosen file as a File
			File userChosenFile = chooser.getSelectedFile();

			// Call the numItems method to see how many files and folders are in the selected folder.
			int numFiles = countItems(userChosenFile);
			// Display the information in the GUI main panel
			question2.setText("The number of files and folders in you folder you selected is : " + numFiles);

			// Call the numLargeFiles method to see how many files are over 100,000 bytes.
			long numFilesOver = numLargeFiles(userChosenFile);
			// Display the information in the GUI main panel
			question3.setText("The number of files that are over 100,000 bytes is : " + numFilesOver);

			// Call the largestItem method to see what is the largest file or folder in the selcted folder.
			File longestItem = largestItem(userChosenFile);
			
			// Initialize variables to be used outside the try/catch.
			String longestFileName;
			Long longestFileSize;
			
			// Use a try catch to handle if there is an empty folder or file found. Or if file or folder is borken
			try{
				// Extract information from the longestItemFile and display it on the GUI main panel.
				longestFileName = longestItem.getName();
				longestFileSize = longestItem.length();
				question1.setText("The name and of the largest file or folder is : " + longestFileName + 
						"\n     The size of the file or folder in bytes : " + longestFileSize);
			}
			catch(NullPointerException z){
				JOptionPane.showMessageDialog(null, "Sorry there was an issue with a file or folder.");
			}
			
			// Use the SimpleDataFormat to display the date of the newlyModified File
			SimpleDateFormat dateAndTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			// Call the modifiedLast method to see which file or folder in the selected folder was modified most recently.
			File newlyModified = modifiedLast(userChosenFile);
			// Display the information in the GUI main panel
			question4.setText("The name of the file or folder that has been most recently modified : " + newlyModified.getName() + 
					"\n     Date and Time modified : " + dateAndTime.format(newlyModified.lastModified()));

			// Repaint the main GUI panel to update the information.
			repaint();
		}
	}
	
	/**
	 * largestItem method finds the largest folder or file from the user selected folder.
	 * @param comp - a file or folder from the recursive method itself or the user chosen folder.
	 * @return - a file or folder that is the largest in the user selected folder
	 */
	public static File largestItem (File comp){
		 if(!comp.isDirectory()){
			 return comp;
		 }
		 else{
			 File largest = comp;
			 for (File file : comp.listFiles()){
				 File testFile =  largestItem(file);
				 if(largest.length() < testFile.length()){
					 largest = testFile;
				 }
			 }
			 return largest;
		 }
	 }

	/**
	 * countItems method counts all the files and folders found in the user selected folder
	 * @param comp - a file or folder from the recursive method itself or the user chosen folder.
	 * @return - a int constant the amount of files and folders found in the user selected folder
	 */
	public static int countItems(File comp) {
		if (comp.isFile()) {
			return 1;
		} else {
			int count = 0;
			count++;
			for (File f : comp.listFiles()) {
				count += countItems(f);
			}
			return count;
		}

	}

	/**
	 * numLargeFiles method finds the largest file or folder in the user selected folder
	 * @param comp - a file or folder from the recursive method itself or the user chosen folder.
	 * @return - a long value in bytes of the largest file or folder
	 */
	public static long numLargeFiles(File comp) {
		long count = 0;
		if (comp.isFile()) {
			if (comp.length() > (long) 100000) {
				count++;
			}
		}

		else {
			for (File f : comp.listFiles()) {
				count += numLargeFiles(f);
			}

		}
		return count;

	}

	
	/**
	 * modifiedLast method find the file or folder that was modified most recently in the user selected folder
	 * @param comp - a file or folder from the recursive method itself or the user chosen folder.
	 * @return - a file or folder that has been most recently modified 
	 */
	public static File modifiedLast (File comp){
		File newest = comp;
		 if(comp.isFile()){
			 return comp;
		 }
		 else{
			 for (File file : comp.listFiles()){
				 if(newest.isDirectory()){
					 newest = file;
				 }
				 if(newest.lastModified() < modifiedLast(file).lastModified()){
					 newest = file;
				 }
			 }
			 return newest;
		 }
	 }
}
