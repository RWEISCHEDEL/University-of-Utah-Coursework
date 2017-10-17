package lab1;

public class TimingExperiment01 {

  public static void main(String[] args) {
    long lastTime = System.currentTimeMillis();
    int advanceCount = 0;
    while (advanceCount < 100) {
      long currentTime = System.currentTimeMillis();
      if (currentTime == lastTime)
        continue;
      System.out.println("Time advanced " + (currentTime - lastTime)
          + " milliseconds.");
      lastTime = currentTime;
      advanceCount++;
    }
  }
}