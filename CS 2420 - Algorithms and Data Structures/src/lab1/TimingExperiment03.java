package lab1;

public class TimingExperiment03 {

  public static void main(String[] args) {
    long lastTime = System.nanoTime();
    int advanceCount = 0;
    while (advanceCount < 100) {
      long currentTime = System.nanoTime();
      if (currentTime == lastTime)
        continue;
      System.out.println("Time advanced " + (currentTime - lastTime)
          + " nanoseconds.");
      lastTime = currentTime;
      advanceCount++;
    }
  }
}