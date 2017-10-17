package assignment7;

import java.util.ArrayList;

/**
 * This class is what we used for all of your timing tests in assignment 7 on the MyStack class. We would just comment out the ones that we didn't need to test at 
 * the time. The only thing we would change for each test is the actual method call of which method we needed to test, which we would erase the old method call and 
 * write the new one. 
 * 
 * @author Robert Weischedel and Tanner Martin
 *
 */
public class MyStackTiming {
	public static void main(String[] args) {
		long startTime, midpointTime, stopTime;

		// First, spin computing stuff until one second has gone by.
		// This allows this thread to stabilize.
		for (int numberOfElements = 10000; numberOfElements <= 100000; numberOfElements += 10000) {

			// Create a new MyStack
			MyStack<Integer> sourceList = new MyStack();

			// Add the values to the stack when necessary.
			
			for(int i = 1; i <= numberOfElements; i++)
			{
				sourceList.push(numberOfElements);
			}

			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000) { // empty block
			}

			// Now, run the test.

			long timesToLoop = 9999;

			startTime = System.nanoTime();

			// The heart of the timing tests, all of our various method calls
			// were done in here. We just commmented out the methods that we didnt need.
			for (long i = 0; i < timesToLoop; i++) {

				sourceList.push(numberOfElements);
				//sourceList.pop();
				//sourceList.peek();
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
			// System.out.println(averageTime);
			// System.out.println(numberOfElements);
		}
	}
}
