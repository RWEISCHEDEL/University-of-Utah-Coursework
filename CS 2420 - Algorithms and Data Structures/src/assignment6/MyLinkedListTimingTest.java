package assignment6;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class is what we used for all of your timing tests in assignment 6 on
 * the MyLinkedList, LinkedList and ArrayList. We would just comment out the
 * ones that we didn't need to test at the time. The only thing we would change
 * for each test is the actual method call of which method we needed to test,
 * which we would erase the old method call and write the new one.
 * 
 * @author Robert Weischedel and Tanner Martin
 *
 */
public class MyLinkedListTimingTest {
	public static void main(String[] args) {
		long startTime, midpointTime, stopTime;

		// First, spin computing stuff until one second has gone by.
		// This allows this thread to stabilize.
		for (int numberOfElements = 1000; numberOfElements <= 10000; numberOfElements += 1000) {
			//LinkedList<Integer> sourceList = new LinkedList();
			// MyLinkedList<Integer> sourceList = new MyLinkedList();
			 ArrayList<Integer> sourceList = new ArrayList();

			// Add the values to the MyLinkedList, LinkedList or ArrayList.
			for (int i = 0; i <= numberOfElements; i++) {
				sourceList.add(i, i);
			}

			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000) { // empty block
			}

			// Now, run the test.

			long timesToLoop = 900;

			startTime = System.nanoTime();

			// The heart of the timing tests, all of our various method calls
			// were done in here.
			for (long i = 0; i < timesToLoop; i++) {
				sourceList.remove(1);
				// sourceList.add(0, numberOfElements);
				// for (int k = 0; k <= numberOfElements; k++)
				// {
				// sourceList.get(sourceList.size() / 2);
				// sourceList.remove(0);
				// }
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

			// System.out.println(averageTime + "\t" + numberOfElements);
			 System.out.println(averageTime);
			//System.out.println(numberOfElements);
		}
	}
}
