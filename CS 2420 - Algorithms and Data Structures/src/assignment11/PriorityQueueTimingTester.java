package assignment11;

import java.util.Random;

public class PriorityQueueTimingTester {
	
	public static void main(String[] args) {
	    long startTime, midpointTime, stopTime;

	    // First, spin computing stuff until one second has gone by.
	    // This allows this thread to stabilize.
	    
	    PriorityQueue<Integer> ourQueue = new PriorityQueue<Integer>();
	    
	    //Fill the queue
	    Random rand = new Random();
	    
	    for(int i = 0; i <= 1000; i++){
	    	int num = rand.nextInt();
	    	ourQueue.add(num);
	    }

	    startTime = System.nanoTime();
	    while (System.nanoTime() - startTime < 1000000000) { // empty block
	    }

	    // Now, run the test.
	    
	    for(int c = 1000; c <= 10000; c+= 1000){

	    long timesToLoop = 10000;

	    startTime = System.nanoTime();

	    for (long i = 0; i < timesToLoop; i++){
	    	
	    	long addStart = System.nanoTime();
	    	ourQueue.add(rand.nextInt());
	    	long addEnd = System.nanoTime();
	    	
	    	long startFindMin = System.nanoTime();
	    	ourQueue.findMin();
	    	long endFindMin = System.nanoTime();
	    	
	    	long startDeleteMin = System.nanoTime();
	    	ourQueue.deleteMin();
	    	long endDeleteMin = System.nanoTime();
	    	
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

	    System.out.println("It takes exactly " + averageTime
	        + " nanoseconds to compute the square roots of the "
	        + " numbers 1..10.");
	    
	    }
	  }

}
