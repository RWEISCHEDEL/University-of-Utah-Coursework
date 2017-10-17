package assignment11;

import java.util.Random;

/**
 * Analyzes the performance of the PriorityQueue class defined in the
 * assignment11 package
 * 
 * @author Jackson Murphy
 *
 */
public class PqAnalysis {

	public static void main(String[] args) {
		/**
		 * The following code compares the performance of our PriorityQueue with
		 * Java's built-in PriorityQueue class
		 */

		// time-tracking vars
		long start = 0, end = 0;
		long javaAddTime = 0, javaFindTime = 0, javaDeleteTime = 0;
		long ourAddTime = 0, ourFindTime = 0, ourDeleteTime = 0;
		int TIMES_TO_LOOP = 50_000;

		// Create the two PQs
		PriorityQueue<Integer> ourQ = new PriorityQueue<Integer>();
		java.util.PriorityQueue<Integer> javaQ = new java.util.PriorityQueue<Integer>();

		// Use a random seed for consistency
		long seed = System.currentTimeMillis();
		Random rand = new Random(seed);
		int num;

		for (int N = 100000; N <= 1000000; N += 50000) {

			// First, build up the heap to a size of N
			for (int i = 0; i < N; i++) {
				num = rand.nextInt();
				ourQ.add(num);
				javaQ.add(num);
			}
			// test: Min of both Qs should be the same
//			System.out.println(ourQ.findMin());
//			System.out.println(javaQ.peek());

			// Now test the performance of all operations
			for (int i = 0; i < TIMES_TO_LOOP; i++) {
				num = rand.nextInt();

				// Time ourQ's add()
				start = System.nanoTime();
				ourQ.add(num);
				end = System.nanoTime();
				ourAddTime += end - start;

				// Time javaQ's add()
				start = System.nanoTime();
				javaQ.add(num);
				end = System.nanoTime();
				javaAddTime += end - start;

				// Time ourQ's findMin()
				start = System.nanoTime();
				ourQ.findMin();
				end = System.nanoTime();
				ourFindTime += end - start;

				// Time javaQ's findMin()
				start = System.nanoTime();
				javaQ.peek();
				end = System.nanoTime();
				javaFindTime += end - start;

				// Time ourQ's deleteMin()
				start = System.nanoTime();
				ourQ.deleteMin();
				end = System.nanoTime();
				ourDeleteTime += end - start;

				// Time javaQ's deleteMin()
				start = System.nanoTime();
				javaQ.remove();
				end = System.nanoTime();
				javaDeleteTime += end - start;

			}

			// Running times -- Nice format
//			System.out.println("N = " + N);
//			System.out.println("ADD: Our Q = " + ourAddTime / TIMES_TO_LOOP
//					+ "  Java Q = " + javaAddTime / TIMES_TO_LOOP);
//			System.out.println("FIND: Our Q = " + ourFindTime / TIMES_TO_LOOP
//					+ "  Java Q = " + javaFindTime / TIMES_TO_LOOP);
//			System.out.println("DELETE: Our Q = " + ourDeleteTime
//					/ TIMES_TO_LOOP + "  Java Q = " + javaDeleteTime
//					/ TIMES_TO_LOOP);
			
			// Running times -- Export format
			System.out.println("N = " + N);
			System.out.println("Add " + ourAddTime / TIMES_TO_LOOP);
			System.out.println(javaAddTime / TIMES_TO_LOOP);
			System.out.println("Find " + ourFindTime / TIMES_TO_LOOP);
			//System.out.println(javaFindTime / TIMES_TO_LOOP);
			System.out.println("Delete" + ourDeleteTime/ TIMES_TO_LOOP);
			//System.out.println(javaDeleteTime/ TIMES_TO_LOOP);
			System.out.println();

			
			
			// Clear both Qs
			ourQ.clear();
			javaQ.clear();
			
			// Reset time-tracking vars
			ourAddTime = ourFindTime = ourDeleteTime = 0;
			javaAddTime = javaFindTime = javaDeleteTime = 0;

		}
	}
}
