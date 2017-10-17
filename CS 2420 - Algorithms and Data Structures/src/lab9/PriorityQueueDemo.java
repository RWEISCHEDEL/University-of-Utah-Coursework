package lab9;

import java.util.Arrays;
import java.util.PriorityQueue;

public class PriorityQueueDemo {

	public static void main(String[] args) {
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		PriorityQueue<Integer> pqR = new PriorityQueue<Integer>(10, new Lab9Comparator());
		
		
		 
		
		
		pq.add(36);
		pq.add(17);
		pq.add(3);
		pq.add(100);
		pq.add(19);
		pq.add(2);
		pq.add(70);
		
		pqR.add(36);
		pqR.add(17);
		pqR.add(3);
		pqR.add(100);
		pqR.add(19);
		pqR.add(2);
		pqR.add(70);

		System.out.println("Array: " + Arrays.toString(pq.toArray()));

		System.out.println("First item removed: " + pq.remove());
		System.out.println("Second item removed: " + pq.remove());
		System.out.println("Third item removed: " + pq.remove());
		System.out.println("Fourth item removed: " + pq.remove());
		System.out.println("Fifth item removed: " + pq.remove());
		System.out.println("Sixth item removed: " + pq.remove());
		System.out.println("Seventh item removed: " + pq.remove());
		
		System.out.println();
		
		System.out.println("First item removed: " + pqR.remove());
		System.out.println("Second item removed: " + pqR.remove());
		System.out.println("Third item removed: " + pqR.remove());
		System.out.println("Fourth item removed: " + pqR.remove());
		System.out.println("Fifth item removed: " + pqR.remove());
		System.out.println("Sixth item removed: " + pqR.remove());
		System.out.println("Seventh item removed: " + pqR.remove());
	}
}