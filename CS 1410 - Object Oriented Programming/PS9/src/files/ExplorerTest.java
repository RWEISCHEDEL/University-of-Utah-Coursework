package files;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

/**
 * This ExplorerTest is the JUnit Test cases for the Explorer class. We are unable to test the GUI constructor, but all
 * the helper methods that were used in the Explorer class were tested here. I used a folder that I created called PS9
 * saved on the COE X drive to test all theses methods. And I am please to report that they all passed. 
 * @author Robert Weischedel
 *
 */
public class ExplorerTest {

	/**
	 * This JUnit test, tests the countItems method, it sees how many files and folders are in the
	 * user selected folder.
	 */
	@Test
	public void testCountItems() {
		File test = new File("X:\\Documents\\PS9 Test");
		assertEquals(13,Explorer.countItems(test));
	}

	/**
	 * This JUnit test, tests the numLargeFiles method, it sees how many files there are in the user selected
	 * folder that are over 100000 bytes
	 */
	@Test
	public void testNumLargeFiles() {
		File test = new File("X:\\Documents\\PS9 Test");
		assertEquals(6,Explorer.numLargeFiles(test));
	}

	/**
	 * This JUnit test, tests the largestItem method, it sees what is the largest file or folder in the 
	 * user selected folder
	 */
	@Test
	public void testLargestItem() {
		File test = new File("X:\\Documents\\PS9 Test");
		assertEquals("Dawkins, Richard, Mount Improbable, Spider Silk (1).pdf", Explorer.largestItem(test).getName());
	}

	/**
	 * This JUnit test, tests the modifiedLast method, it sees what was the last modified file or fodler
	 * in the user selected folder
	 */
	@Test
	public void testModifiedLast() {
		File test = new File("X:\\Documents\\PS9 Test");
		assertEquals("Poop - Copy.docx",Explorer.modifiedLast(test).getName());
	}

}
