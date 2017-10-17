package assignment8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

/**
 * This class is what we used for the problem number 3 timing tests of the analysis document for assignment 8 on the BinarySearchTree class. All data 
 * that we used to answer question 4 of the analysis document came from this method. We tested both the Java and our version of BST filled with
 *  randomized elements in this method.
 * 
 * @author Robert Weischedel and Tanner Martin
 *
 */
public class BinarySearchTreeTimingTest2 {
	public static void main(String[] args) {
		long startTime, midpointTime, stopTime;

		// First, spin computing stuff until one second has gone by.
		// This allows this thread to stabilize.
		for (int numberOfElements = 1000; numberOfElements <= 5000; numberOfElements += 100) {

			// Create a Java BST
			TreeSet<Integer> javaTree = new TreeSet<Integer>();
			
			// Create our BST implementation
			BinarySearchTree<Integer> bstTree = new BinarySearchTree<Integer>();
			
			// Create an ArrayList to hold the values in order to permeate them to later insert into the BST's.
			ArrayList<Integer> sourceList = new ArrayList<Integer>();
			
			// Fille the ArrayList
			for(int i = 0; i <= numberOfElements; i++)
			{
				sourceList.add(i);
			}
			
			// Shuffle the ArrayList into a permeated order and add to Java's and our BST's.
			Collections.shuffle(sourceList);
			javaTree.addAll(sourceList);
			bstTree.addAll(sourceList);
			
			
			// Begin the timing test for Java's BST
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000) { // empty block
			}

			// Now, run the test.

			long timesToLoop = 9999;

			startTime = System.nanoTime();

			// The heart of the timing tests, all of our various method calls
			// were done in here. We just commmented out the methods that we didnt need.
			for (long i = 0; i < timesToLoop; i++) {

				// Call contains on Java's BST
				javaTree.contains(numberOfElements);
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

			System.out.println(numberOfElements + "\t" + averageTime);

			 
			 /************************/
			
			// Begin the timing tests for our BST.
			 startTime = System.nanoTime();
				while (System.nanoTime() - startTime < 1000000000) { // empty block
				}

				// Now, run the test.

				timesToLoop = 9999;

				startTime = System.nanoTime();

				// The heart of the timing tests, all of our various method calls
				// were done in here. We just commmented out the methods that we didnt need.
				for (long i = 0; i < timesToLoop; i++) {

					// Call contains on our BST 
					bstTree.contains(numberOfElements);
				}

				midpointTime = System.nanoTime();

				// Run an empty loop to capture the cost of running the loop.

				for (long i = 0; i < timesToLoop; i++) { // empty block
				}

				stopTime = System.nanoTime();

				// Compute the time, subtract the cost of running the loop
				// from the cost of running the loop and computing square roots.
				// Average it over the number of runs.

				averageTime = ((midpointTime - startTime) - (stopTime - midpointTime))
						/ timesToLoop;

				System.out.println(numberOfElements + "\t" + averageTime);
		}
	}
}
