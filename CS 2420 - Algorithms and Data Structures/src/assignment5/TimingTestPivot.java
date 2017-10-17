package assignment5;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class was used as the basis for the Timing Tests for the Quick Sort Tests. We would test each quick sort pivot method over a
 * variety of different array sizes. We would manual change the method for choosing the pivot in SortUtil.
 * @author Robert Weischedel && Makenzie Elliott
 *
 */
public class TimingTestPivot {

	public static void main(String[] args) {
		long startTime, midpointTime, stopTime, count = 0, addListStart = 0, addListEnd = 0;

		// First, spin computing stuff until one second has gone by.
		// This allows this thread to stabilize.

		// Create a temp and test ArrayLists to constantly reset and use the values of. 
		ArrayList<Integer> test;
		ArrayList<Integer> temp;
		
		// Create a comparator to pass into the the merge sort
		class intComparator implements Comparator<Integer> {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}

		}

		// Create a comparator object
		intComparator comp = new intComparator();

		// Loop through changing the array size accordingly. 
		int arraySize = 100;
		while (arraySize <= 1000000) {
			test = SortUtil.generateAverageCase(arraySize);
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

				count += (addListEnd - addListStart);

				SortUtil.quicksort(temp, comp);
				// System.out.println("Done a sort");
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

			averageTime = averageTime - (count / timesToLoop);
			System.out.println("It takes exactly " + averageTime
					+ " nanoseconds");
			
			arraySize += 100;

		}
	}

}
