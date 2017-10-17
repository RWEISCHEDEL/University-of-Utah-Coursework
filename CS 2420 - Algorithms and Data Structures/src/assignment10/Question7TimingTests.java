package assignment10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Question7TimingTests {
	
	 public static void main(String[] args) {
		    long startTime, midpointTime, stopTime;

		    
		    // The different functors to test
		    BadHashFunctor badFunctor = new BadHashFunctor();
		    MediocreHashFunctor medFunctor = new MediocreHashFunctor();
		    GoodHashFunctor goodFunctor = new GoodHashFunctor();
		    
		    File input; 
		    Scanner dictonary = null;
		    try {
		    	input = new File("large_word_list.txt");
				dictonary = new Scanner(input);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		    QuadProbeHashTable quadTable = new QuadProbeHashTable(1000, goodFunctor);
		    ChainingHashTable chainTable = new ChainingHashTable(1000, goodFunctor);
		    
		    // First, spin computing stuff until one second has gone by.
		    // This allows this thread to stabilize.
		    startTime = System.nanoTime();
		    while (System.nanoTime() - startTime < 1000000000) { // empty block
		    }

		    for(int addedItems = 1000; addedItems <= 10000; addedItems +=1000){
		    // Now, run the test.

		   // long addedItems = 1000;

		    startTime = System.nanoTime();

		    for (long i = 0; i < addedItems; i++){
		    	chainTable.add(dictonary.next());
		    }

		    midpointTime = System.nanoTime();

		    // Run an empty loop to capture the cost of running the loop.

		    for (long i = 0; i < addedItems; i++) { // empty block
		    }

		    stopTime = System.nanoTime();

		    // Compute the time, subtract the cost of running the loop
		    // from the cost of running the loop and computing square roots.
		    // Average it over the number of runs.

		    double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime))
		        / addedItems;

		    System.out.println("It takes exactly " + averageTime
		        + " to add " + addedItems + " with num of collisions " + chainTable.getCollisions());
		    
		    quadTable.clear();
		    chainTable.clear();
		    }
		  }

}
