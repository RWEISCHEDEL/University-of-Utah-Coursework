package lab8;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class HashAndTreeTimer {

  final static int N = 80000;

  final static int TIMES_TO_LOOP = 100;

  public static void main(String[] args) {
    HashAndTreeTimer timer = new HashAndTreeTimer();

    // time HashSet
    double hashSetTime = timer.getSearchTime(new HashSet<String>(N));

    // time TreeSet
    double treeSetTime = timer.getSearchTime(new TreeSet<String>());

    System.out.println("N = " + N);
    System.out.println("Time required for HashSet: " + hashSetTime / 1000000000
        + " seconds");

    System.out.println("Time required for TreeSet: " + treeSetTime / 1000000000
        + " seconds");
    System.out.println();

    // time HashMap
    double hashMapTime = timer.getSearchTime(new HashMap<String, Integer>(N));

    // time TreeMap
    double treeMapTime = timer.getSearchTime(new TreeMap<String, Integer>());

    System.out.println("Time required for HashMap: " + hashMapTime / 1000000000
        + " seconds");

    System.out.println("Time required for TreeMap: " + treeMapTime / 1000000000
        + " seconds");
    
  }

  public HashAndTreeTimer() {
    dataCollection = generateStrings();
    dataArray = dataCollection.toArray(new String[N]);
  }

  private double getSearchTime(Set<String> s) {
    // s is empty, insert N random items into s
    fillUp(s);

    // s is full, time N random successful searches
    Random rng = new Random(SEARCH_SEED);
    long startTime, midpointTime, stopTime;

    // spin computing stuff until one second has gone by
    startTime = System.nanoTime();
    while (System.nanoTime() - startTime < 1000000000) { // empty block
    }

    startTime = System.nanoTime();

    for (long i = 0; i < TIMES_TO_LOOP; i++)
      for (int j = 0; j < N; j++)
        s.contains(dataArray[rng.nextInt(N)]);

    midpointTime = System.nanoTime();

    // run empty loops to capture their cost
    for (long i = 0; i < TIMES_TO_LOOP; i++)
      for (int j = 0; j < N; j++) { // empty block
      }

    stopTime = System.nanoTime();

    // compute the time: subtract the cost of running the experiment
    // from the cost of running the empty loops and average it over the number
    // of runs
    return ((midpointTime - startTime) - (stopTime - midpointTime))
        / TIMES_TO_LOOP;
  }

  private double getSearchTime(Map<String, Integer> m) {
    // m is empty, insert N random items into m
    fillUp(m);

    // m is full, time N random successful searches
    Random rng = new Random(SEARCH_SEED);
    long startTime, midpointTime, stopTime;

    // spin computing stuff until one second has gone by
    startTime = System.nanoTime();
    while (System.nanoTime() - startTime < 1000000000) { // empty block
    }

    startTime = System.nanoTime();

    for (long i = 0; i < TIMES_TO_LOOP; i++)
      for (int j = 0; j < N; j++)
        m.containsKey(dataArray[rng.nextInt(N)]);

    midpointTime = System.nanoTime();

    // run empty loops to capture their cost
    for (long i = 0; i < TIMES_TO_LOOP; i++)
      for (int j = 0; j < N; j++) { // empty block
      }

    stopTime = System.nanoTime();

    // compute the time: subtract the cost of running the experiment
    // from the cost of running the empty loops and average it over the number
    // of runs
    return ((midpointTime - startTime) - (stopTime - midpointTime))
        / TIMES_TO_LOOP;
  }

  private void fillUp(Set<String> s) {
    s.addAll(dataCollection);
  }

  private void fillUp(Map<String, Integer> m) {
    Random rng = new Random(FILL_SEED);

    for (String s : dataCollection)
      m.put(s, rng.nextInt());
  }

  private Set<String> generateStrings() {
    final int SEED = 300;
    final int DEFAULT_STRING_LENGTH = 10;
    final char[] letters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
        'y', 'z' };

    Random r = new Random(SEED);
    Set<String> result = new HashSet<String>(N);
    String[] s = new String[N];

    for (int i = 0; i < N; i++) {
      s[i] = "";
      for (int j = 0; j < DEFAULT_STRING_LENGTH; j++)
        s[i] += letters[r.nextInt(letters.length)];
      if (!result.add(s[i]))
        i--;
    }

    return result;
  }

  private Set<String> dataCollection;

  private String[] dataArray;

  private final static int SEARCH_SEED = 100;

  private final static int FILL_SEED = 200;
}