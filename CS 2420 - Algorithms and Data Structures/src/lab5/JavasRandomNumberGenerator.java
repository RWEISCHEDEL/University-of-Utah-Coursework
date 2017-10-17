package lab5;

/**
 * Builds a RNG simply by wrapping the java.util.Random class.
 */
public class JavasRandomNumberGenerator implements RandomNumberGenerator {

  /*
   * Create a Java.util.Random object to do the actual "work" of this class.
   */
  private java.util.Random random_generator_ = new java.util.Random();

  public int next_int(int max) {
    return this.random_generator_.nextInt(max);
  }

  public void set_seed(long seed) {
    this.random_generator_.setSeed(seed);
  }

  public void set_constants(long _const1, long _const2) {
    // not needed
  }
}