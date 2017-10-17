package assignment4;

/**
 * This class contains the main method we used in our timing tests for our analysis document. The structure is almost identical to that
 * of the one used in Lab1. We specifically have the top test set up for the areAanagrams timing tests. And the bottom portion that is
 * commented out, is set up to test the getLargestAnagram method.
 * @author Robert Weischedel && Makenzie Elliott
 *
 */
public class TimingTests {

	public static void main(String[] args) {
		long startTime, midpointTime, stopTime, submidpoint1 = 0, submidpoint2 = 0;
		String word1 = null, word2 = null;
		int countRandomTime = 0;

		//int length = 340;
		// First, spin computing stuff until one second has gone by.
		// This allows this thread to stabilize.

		for (int length = 100; length <= 2000; length += 100) {
			startTime = System.nanoTime();
			
			while (System.nanoTime() - startTime < 1000000000) { // empty block
			}

			// Now, run the test.

			long timesToLoop = 10000;

			startTime = System.nanoTime();

			for (long i = 0; i < timesToLoop; i++) {

				// Create midpoint varaibles to factor out time taken to generate new random strings
				submidpoint1 = System.nanoTime();

				// Create two random strings
				word1 = AnagramTester.randomString(length);
				word2 = AnagramTester.randomString(length);

				submidpoint2 = System.nanoTime();

				countRandomTime += (submidpoint2 - submidpoint1);

				// Test actual method
				AnagramUtil.areAnagrams(word1, word2);
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

			averageTime -= (countRandomTime / timesToLoop);

			System.out.println("It takes exactly " + averageTime
					+ " nanoseconds to compute the areAnagrams method with "
					+ length + " items");
			
/*			int j = 3000;
			String[] testArray = new String[j];
			for (int i = 0; i < j; i++) {
				testArray[i] = AnagramTester.randomString(10);
			}
			long startTime, midpointTime, stopTime;

			// First, spin computing stuff until one second has gone by.
			// This allows this thread to stabilize.


				startTime = System.nanoTime();
				while (System.nanoTime() - startTime < 1000000000) { // empty block
				}

				// Now, run the test.

				long timesToLoop = 10000;

				startTime = System.nanoTime();

				for (long k = 0; k < timesToLoop; k++)
					AnagramUtil.getLargestAnagramGroup(testArray);

				midpointTime = System.nanoTime();

				// Run an empty loop to capture the cost of running the loop.

				for (long l = 0; l < timesToLoop; l++) { // empty block
				}

				stopTime = System.nanoTime();

				// Compute the time, subtract the cost of running the loop
				// from the cost of running the loop and computing square roots.
				// Average it over the number of runs.

				double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime))
						/ timesToLoop;

				System.out.println("It takes exactly " + averageTime
						+ " to test the getLargestAnagramGroup on "
						+ testArray.length + " items");*/

		}
	}

}
