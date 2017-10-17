package assignment8;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is what we used for the problem number 3 timing tests of the analysis document for assignment 8 on the BinarySearchTree class. All data 
 * that we used to answer question 3 of the analysis document came from this method. We tested both the randomized element entry and sorted order tests
 * of question three both in this method.
 * 
 * @author Robert Weischedel and Tanner Martin
 *
 */
public class BinarySearchTreeTimingTests {
	public static void main(String[] args) {
		// Variables to keep tract of the time.
		long startTime, midpointTime, stopTime;

		// We placed the entire code in a for loop to make testing easier. This allows the program to automatically add another 100 values into the
		// BST.
		for (int numberOfElements = 1000; numberOfElements <= 10000; numberOfElements += 100) {

			// This is where we created our BST we would used for the tests, to make testing simple, we used integers for the data.
			BinarySearchTree<Integer> sourceTree = new BinarySearchTree<Integer>();
			
			// Create a ArrayList to fill with elements, we do this so that we can used the Collections.shuffle command for each new ArrayList
			ArrayList<Integer> sourceList = new ArrayList<Integer>();
			
			// Fill the ArrayList with the correct number of elements required for this test.
			for(int i = 0; i <= numberOfElements; i++)
			{
				sourceList.add(i);
			}
			
			// Shuffle the Array, when we needed to test the sorted order set we simply commented this line of code out.
			Collections.shuffle(sourceList);
			
			//Add the values to the BST
			sourceTree.addAll(sourceList);

			// Begin the timing experiment.
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000) { // empty block
			}

			// Now, run the test.

			long timesToLoop = 9999;

			startTime = System.nanoTime();

			// The heart of the timing tests, all of our various method calls
			// were done in here. We just commmented out the methods that we didnt need.
			for (long i = 0; i < timesToLoop; i++) {

				// Search for the largest item in the BST
				sourceTree.contains(numberOfElements);
			}

			midpointTime = System.nanoTime();

			// Run an empty loop to capture the cost of running the loop.

			for (long i = 0; i < timesToLoop; i++) { // empty block
			}

			stopTime = System.nanoTime();

			// Compute the time, subtract the cost of running the loop
			// from the cost of running the loop and computing square roots.
			// Average it over the number of runs.

			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime))
					/ timesToLoop;

			// Print out the information.
			System.out.println(numberOfElements + "\t" + averageTime);
		}
	}
}
