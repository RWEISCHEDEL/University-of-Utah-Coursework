package assignment8;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

/**
 * This class tests the spellCheck method that we implemented out of the SpellChecker class. In addition to passing the SpellCheckerDemo tests, we 
 * added a few additional tests with out own text files to ensure that our spell checker implementation is working correctly. The .txt are included in
 * the zip file. With this test and the SpellChecker Demo method we felt like that the spell checker was adequately tested. In addition when asked about
 * how we could more extensively test the spell checker, Jason stated that just a few tests should be fine because in a different JUnit we test our BST
 * extensivley.
 * @author Tanner Martin and Robert Weischedel
 *
 */
public class SpellCheckerTest
{
	/**
	 * For this method we test the spellCheck method in addition to the add method, were we add a new word and see if it is contained in the Dictionary.
	 */
	@Test
	public void testSpellCheckWithAdd()
	{
		SpellChecker mySC = new SpellChecker(new File("NewDictionary1.txt"));
		mySC.addToDictionary("cologne");
		
		ArrayList<String> returnList = (ArrayList<String>) mySC.spellCheck(new File("NewDictionary2.txt"));
		
		assertTrue(returnList.contains("theif"));
		assertTrue(returnList.contains("colonge"));
	}
	
	/**
	 * For this method we test the spell check method in addition to the remove method, were we removed a word in the diction then checked to see if 
	 * it was no longer in the dictionary.
	 */
	@Test
	public void testSpellCheckWithRemove()
	{
		SpellChecker mySC = new SpellChecker(new File("NewDictionary3.txt"));
		mySC.removeFromDictionary("hey");
		
		ArrayList<String> returnList = (ArrayList<String>) mySC.spellCheck(new File("NewDictionary4.txt"));
		
		assertTrue(returnList.contains("theif"));
		assertFalse(returnList.contains("hey"));
	}

	
}
