package lab1;

public class TimingExperiment07 {

  public static void main(String[] args) {
    long startTime = System.nanoTime();

    for (double d = 1; d <= 10; d++)
      Math.sqrt(d);

    long stopTime = System.nanoTime();

    System.out.println("It takes exactly " + (stopTime - startTime)
        + " nanoseconds to compute the square roots of the "
        + " numbers 1..10.");
  }
}