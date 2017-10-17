package lab5;

import java.util.Arrays;

/**
 * This class checks to see how well a random number generator works. It tests
 * several important properties, including the following.
 * 
 * the number of zeros after 10000 tries 
 * how many tries before all the numbers between 1-1000 are generated 
 * the number of unique numbers generated in 1000 tries 
 * the number of odd-even pairs 
 * the number of odd-odd pairs 
 * the number of even-odd pairs 
 * the number of even-even pairs 
 * the average number generated
 */

public class CheckRandomNumberGenerator {

  // for testing only generate numbers between 0 and max_size
  private final int max_size = 10000;

  // keeps track the oddness/evenness of consecutive numbers
  private int odd_odd_count_ = 0;

  private int odd_even_count_ = 0;

  private int even_even_count_ = 0;

  private int even_odd_count_ = 0;

  // average of all the random numbers
  private long average_of_all_ = 0;

  private int counter_ = 0;

  // histogram of the random numbers being generated
  private int[] histogram_ = new int[this.max_size];

  private int previous_ = -1;

  // number of boxes in the histogram that are still
  // empty after a thousand numbers are generated
  private int number_of_zeros_after_ten_thousand = 0;

  // number of boxes filled
  private int numbers_filled = 0;

  // number of tries taken to fill in box
  private int numbers_generated = 0;

  /**
   * Clears the histogram
   */
  public void clear_histogram() {
    for (int i = 0; i < this.max_size; i++) {
      this.histogram_[i] = 0;
    }
  }

  /**
   * This function runs a series of tests to see how well a random number
   * generator works.
   * 
   * @param generator
   *          the random number generator to test
   * @param seed
   *          the value to set the seed in the random number generator
   */
  public void check_random(RandomNumberGenerator generator, int seed) {
    // set the seed of the random number generator
    generator.set_seed(seed);

    clear_histogram();

    // test odd/even of numbers
    // test average number
    for (int i = 0; i < this.max_size; i++) {
      int value = generator.next_int(this.max_size);

      this.histogram_[value]++;

      this.average_of_all_ += value;
      this.counter_++;

      if (this.previous_ >= 0) {

        if (is_odd(this.previous_)) {
          if (is_odd(value)) {
            this.odd_odd_count_++;
          } else {
            this.odd_even_count_++;
          }
        } else {
          if (is_odd(value)) {
            this.even_odd_count_++;
          } else {
            this.even_even_count_++;
          }
        }
      }

      this.previous_ = value;
    }

    Arrays.sort(this.histogram_);

    // max_size iterations tested. now see how many zeros
    while (this.histogram_[this.number_of_zeros_after_ten_thousand] == 0) {
      this.number_of_zeros_after_ten_thousand++;
    }

    fill_array(generator); // determine how many calls to next_int are required
    // to hit every number

    // time how long it takes to generate 10,000,000 numbers
    long start_time = System.nanoTime();
    for (int i = 0; i < 10000000; i++) {
      generator.next_int(10000);
    }
    long end_time = System.nanoTime();

    // finally print some stats:
    System.out.println("\n  -- Random Number Tester Stats --\n");

    System.out.printf(
        "Time to generate 10000000 random numbers is:  %.1f seconds\n\n",
        (float) ((end_time - start_time) / 1000000000.0));

    System.out.println("Number of Zeros after " + this.max_size + " tries: "
        + this.number_of_zeros_after_ten_thousand);
    System.out.println("Number of odd_even  pairs: " + this.odd_even_count_);
    System.out.println("Number of odd_odd   pairs: " + this.odd_odd_count_);
    System.out.println("Number of even_odd  pairs: " + this.even_odd_count_);
    System.out.println("Number of even_even pairs: " + this.even_even_count_
        + "\n");

    System.out.println("Average number is: " + (float) this.average_of_all_
        / (float) this.counter_);
    System.out.println("Median number of times a number was generated: "
        + this.histogram_[this.histogram_.length / 2]);
    System.out.println("Min    number of times a number was generated: "
        + this.histogram_[0]);
    System.out.println("Max    number of times a number was generated: "
        + this.histogram_[this.histogram_.length - 1] + "\n");

    System.out.println("Repeatability on Same Seed: " + test_seed(generator)
        + "\n");

    if (this.numbers_filled < this.max_size) {
      System.out.println("Could not fill histogram after " + 1000000
          + " tries.");
      System.out.println("There were still "
          + (this.max_size - this.numbers_filled) + " indices empty\n");
    } else {
      System.out.println("It took " + this.numbers_generated
          + " generated numbers to hit every possibility from 0 to "
          + this.max_size);
    }

  } // end check_random

  /**
   * Test the check_random class with a random number generator
   */
  public static void main(String[] args) {
    System.out.println("---------------------------------------");
    System.out.println("Testing Poor Random Number Generator");
    RandomNumberGenerator j_rand = new PoorRandomNumberGenerator();
    CheckRandomNumberGenerator check_rand = new CheckRandomNumberGenerator();
    check_rand.check_random(j_rand, 0);

    System.out.println("---------------------------------------");
    System.out.println("Testing Java's Random Number Generator");
    j_rand = new JavasRandomNumberGenerator();
    check_rand = new CheckRandomNumberGenerator();
    check_rand.check_random(j_rand, 0);

    
     System.out.println("---------------------------------------");
     System.out.println ("Testing Better Random Number Generator"); j_rand =
     new BetterRandomNumberGenerator(); check_rand = new CheckRandomNumberGenerator();
     check_rand.check_random(j_rand, 0);
     
  }

  /**
   * This function tests to see if setting the seed to the same value returns
   * the same sequence
   * 
   * @param generator
   *          the random number generator to use
   * @return true if setting the seed to the same value returns t he same
   *         sequence, false otherwise
   */
  private boolean test_seed(RandomNumberGenerator generator) {
    int[] first_sequence = new int[10000];
    generator.set_seed(0);

    for (int i = 0; i < 10000; i++) {
      first_sequence[i] = generator.next_int(10000);
    }

    int[] second_sequence = new int[10000];
    generator.set_seed(0);
    for (int i = 0; i < 10000; i++) {
      second_sequence[i] = generator.next_int(10000);
    }

    for (int i = 0; i < 10000; i++) {
      if (first_sequence[i] != second_sequence[i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * This function determines how many numbers need to be generated before an
   * array of "max_size" is filled.
   * 
   * @param generator
   *          the random number generator to test.
   */
  private void fill_array(RandomNumberGenerator generator) {
    boolean[] is_empty = new boolean[this.max_size];
    for (int i = 0; i < is_empty.length; i++) {
      is_empty[i] = true;
    }
    int index;
    while (this.numbers_filled < this.max_size
        && this.numbers_generated < 1000000) {
      index = generator.next_int(this.max_size);
      if (is_empty[index]) {
        is_empty[index] = false;
        this.numbers_filled++;
      }
      this.numbers_generated++;
    }
  }

  /**
   * Determines if a number is odd
   * 
   * @param value
   *          the number to determine if it's odd
   * @return returns true if the value is odd, false otherwise
   */
  private boolean is_odd(int value) {
    return (value % 2) == 1;
  }

} // end class