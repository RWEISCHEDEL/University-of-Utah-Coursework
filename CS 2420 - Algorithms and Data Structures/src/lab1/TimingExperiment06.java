package lab1;

public class TimingExperiment06 {

  public static void main(String[] args) {
    long secondsToRun = 10;
    long startTime = System.nanoTime();
    long lastTime = startTime;
    int advanceCount = 0;
    int loopCount = 0;
    long currentTime;
    long totalTime = 1000000000 * secondsToRun;
    long elapsedTime = 0;
    while (elapsedTime < totalTime) {
      loopCount++;
      if ((currentTime = System.nanoTime()) != lastTime) {
        lastTime = currentTime;
        elapsedTime = lastTime - startTime;
        advanceCount++;
      }
    }
    double advancesPerSecond = advanceCount / (double) secondsToRun;
    System.out.println("Time advanced " + advancesPerSecond
        + " times per second.");
    System.out.println("The loop tested the time " + loopCount + " times.");
  }
}