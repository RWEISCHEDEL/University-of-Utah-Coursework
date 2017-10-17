package assignment5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * This class was used as the basis for the Timing Tests for the Merge Sort Tests. We would increment the threshold value for each array
 * size, and set it back to zero when we increased the array size. Effectively testing all threshold sizes for all array sizes. 
 * @author Robert Weischedel && Makenzie Elliott
 *
 */
public class TimingTests {

	public static void main(String[] args) {
		long startTime, midpointTime, stopTime, addListStart = 0, addListEnd = 0, count = 0;

		// First, spin computing stuff until one second has gone by.
		// This allows this thread to stabilize.

		// Generate a new Array List and fill it. 
		ArrayList<Integer> test = new ArrayList<Integer>();
		test = SortUtil.generateAverageCase(100000);
		
		// Create a temp ArrayList to constantly reset and use the values of. 
		ArrayList<Integer> temp;

		// Get the threshold size
		int threshold = SortUtil.getThreshold();

		// Create a comparator to pass into the the merge sort
		class intComparator implements Comparator<Integer> {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}

		}

		// Create the comparator
		intComparator comp = new intComparator();

		// Loop through for 5 different threshold values
		for (int r = 0; r < 5; r++) {
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000) { // empty block
			}

			long timesToLoop = 5000;

			startTime = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) {

				// Remove the time it takes to reset the array
				addListStart = System.nanoTime();
				temp = new ArrayList<Integer>(test);
				addListEnd = System.nanoTime();

				count+= (addListEnd - addListStart);
				
				SortUtil.mergesort(temp, comp);
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

			averageTime = averageTime
					- (count / timesToLoop);
			System.out.println("It takes exactly " + averageTime
					+ " nanoseconds for the threshold of " + threshold);

			threshold = SortUtil.setThreshold(threshold + 5);
			
		}

	}

}

