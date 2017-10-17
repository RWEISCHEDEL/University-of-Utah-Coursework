package assignment3;

import java.util.Comparator;

/**
 * This is what we used to do our timing tests on the contains and add method
 * Each test was done individually by taking an average of 1000 tests per element.
 * @author Robert Weischedel & Makenzie Elliott
 *
 */
public class Test {

	public static void main(String[] args) {
		
		long startTime, midpointTime, stopTime;
	    MySortedSet theSet = new MySortedSet();
	    
	    for(int i = 0; i < 1200000; i++){
	    	theSet.add(i);
	    }
	    

	    // First, spin computing stuff until one second has gone by.
	    // This allows this thread to stabilize.

	    startTime = System.nanoTime();
	    while (System.nanoTime() - startTime < 1000000000) { // empty block
	    }

	    // Now, run the test.

	    long timesToLoop = 1000;

	    startTime = System.nanoTime();

	    for (long i = 0; i < timesToLoop; i++)
	      theSet.contains(0);

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

	    System.out.println("It takes exactly " + averageTime
	        + " to run the contains method");

		
		// This is for the adds method
		MySortedSet theSet2 = new MySortedSet();
		for(int i = 0; i < 1100000; i++){
			theSet2.add(i);
		}

	    // First, spin computing stuff until one second has gone by.
	    // This allows this thread to stabilize.

	    /*startTime = System.nanoTime();
	    while (System.nanoTime() - startTime < 1000000000) { // empty block
	    }*/

	    // Now, run the test.

	    //long timesToLoop = 5000;

	    startTime = System.nanoTime();

	   // for (long i = 0; i < timesToLoop; i++){
	    	theSet.add(1100000);
	   // }
	    
	    //midpointTime = System.nanoTime();

	    // Run an empty loop to capture the cost of running the loop.

/*	    for (long i = 0; i < timesToLoop; i++) { // empty block
	    }*/

	    stopTime = System.nanoTime();

	    // Compute the time, subtract the cost of running the loop
	    // from the cost of running the loop and computing square roots.
	    // Average it over the number of runs.

	    /*double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime))
	        / timesToLoop;*/
	    
	    averageTime = stopTime - startTime;

	    System.out.println("It takes exactly " + averageTime
	        + " nanoseconds to compute the square roots of the "
	        + " numbers 1..10.");
	  }

}
