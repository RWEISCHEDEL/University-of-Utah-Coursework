package assignment5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * This class serves a utility class containing the code for two different divide and conquer sorting algorithms, mergesort and 
 * quicksort. This class contains all the associated methods and member variables there are for each of the two sorting algorithms.
 * All methods in this class are declared static, so there can only be once instance of each of the sorting methods. Each of the 
 * sorting algorithms uses the insertion sort method when the array size reaches below a certain threshold level. In addition, both 
 * algorithms use driver methods, to call the recursive methods. Both sorting algorithms can take in generic objects.
 * @author Robert Weischedel && Makenzie Elliott
 *
 */
public class SortUtil {

	// Two private member variables were created to hold the threshold value of merge and quick sort.
	private static int thresholdVal = 10, thresholdValQuickSort = 10;
	
	/**
	 * This method acts as setter method for the Merge Sort threshold value, to aid in testing of the best threshold value.
	 * @param n  - The integer value to set the threshold value too
	 * @return - The updated integer threshold value
	 */
	public static int setThreshold(int n) {
		// To ensure no negative values entered
		if(n < 0){
			return thresholdVal;
		}
		// Reset the threshold value to n
		thresholdVal = n;
		return thresholdVal;
	}

	/**
	 * This method acts as a getter method for the Merge Sort thresholdValue to aid in testing of the best threshold value.
	 * @return - The integer threshold value
	 */
	public static int getThreshold() {
		return thresholdVal;
	}

	/**
	 * This method acts as the driver method for the merge sort algorithm. It also checks to see if the input array is set to null
	 * or has a size of 0.
	 * 
	 * @param arr - The array that needs to be sorted, it is an ArrayList of type generic
	 * @param comp - The Comparator object that defines the ordering of the merge sort.
	 */
	public static <T> void mergesort(ArrayList<T> arr, Comparator<? super T> comp) {
		
		// Check the input of the ArrayList
		if (arr == null || arr.size() == 0) {
			return;
		}

		// Create a temp generic array to act as a holder for the values being switched
		T[] temp = (T[]) new Object[arr.size()];
		
		// Call the recusive portion of the Merge Sort 
		recursiveMerge(arr, temp, 0, arr.size() - 1, comp);
	}

	/**
	 * This method performs a mergesort on the generic ArrayList given as input. The mergesort implementation switches over to 
	 * insertion sort when the size of the ArrayList to be sorted meets a certain threshold.
	 * 
	 * @param arr
	 * @param temp
	 * @param left
	 * @param right
	 * @param comp
	 */
	private static <T> void recursiveMerge(ArrayList<T> arr, T[] temp, int left, int right, Comparator<? super T> comp) {

		// Check the current size of the ArrayList being brought in, if its equal to or below the thresholdVal, then do insertion sort
		int size = right - left + 1;
		if (size <= thresholdValQuickSort) {
			insertionSort(arr, comp);
			return;
		}

		// If the ArrayList isn't too small keep performing recursive Merge Sort
		if (left < right) {
			int center = (left + right) / 2;
			recursiveMerge(arr, temp, left, center, comp);
			recursiveMerge(arr, temp, center + 1, right, comp);
			merge(arr, temp, comp, left, center + 1, right);
		}
	}

	/**
	 * This method is called by the recursiveMerge method to merge two sorted lists together. It looks at the first item in each of the
	 * Arrays and chooses which one is smaller and adds it to the correct place in the ArrayList.
	 * 
	 * @param arr - An ArrayList of which we want to merge the values of.
	 * @param temp - An generic Array of which we use as a holder for the values to merge.
	 * @param comp - The Comparator object that defines the ordering of the sort.
	 * @param left - The left most boundary of the ArrayList
	 * @param mid - The midpoint value of the ArrayList
	 * @param right - The right most boundary of the ArrayList
	 */
	private static <T> void merge(ArrayList<T> arr, T[] temp, Comparator<? super T> comp, int left, int mid, int right) {

		// Create variables to store the left boundary of the array to check
		int leftEnd = mid - 1;
		// Create a variable to store the temporary position of the merge
		int tempPos = left;
		// Create a variable to store the number of elements in the ArrayList
		int numElements = right - left + 1;

		// Search through the values merging them when accordingly
		while (left <= leftEnd && mid <= right) {
			if (comp.compare(arr.get(left), arr.get(mid)) <= 0) {
				temp[tempPos++] = arr.get(left++);
			} else {
				temp[tempPos++] = arr.get(mid++);
			}
		}

		// Check for boundary cases at the end.
		while (left <= leftEnd) {
			temp[tempPos++] = arr.get(left++);
		}

		while (mid <= right) {
			temp[tempPos++] = arr.get(mid++);
		}

		// Fill the remainder of the array 
		for (int i = 0; i < numElements; i++, right--) {
			arr.set(right, temp[right]);
		}
	}

	/**
	 * This is a version of insertion sort that takes in a generic ArrayList and Comparator and sorts the ArrayList according to
	 * the Comparator.
	 * 
	 * @param arr
	 * @param comp
	 */
	public static <T> void insertionSort(ArrayList<T> arr, Comparator<? super T> comp) {

		// Create variables to hold the index value and to keep tract of the value to look at in the array.
		int j;
		T index;

		for (int i = 1; i < arr.size(); i++) {
			index = arr.get(i);
			j = i;
			while (j > 0 && comp.compare(arr.get(j - 1), index) > 0) {
				arr.set(j, arr.get(j - 1));
				j--;
			}
			arr.set(j, index);
		}

	}

	/**
	 * This method acts as the driver method for the quick sort algorithm. It also checks to see if the input array is set to null
	 * or the ArrayList has a size of 0.
	 * 
	 * @param arr - An ArrayList of which we want to quick sort the values of.
	 * @param comp - The Comparator object that defines the ordering of the sort.
	 */
	public static <T> void quicksort(ArrayList<T> arr, Comparator<? super T> comp) {
		// Check the input of the ArrayList
		if (arr == null || arr.size() == 0) {
			return;
		}

		// Begin the quicksort recursive portion
		recursiveQuick(arr, comp, 0, arr.size() - 1);
	}

	/**
	 * This method performs a quicksort on the generic ArrayList given as input. The quicksort implementation is also able to switch 
	 * among three different pivot selection strategies. The quicksort also switches to insertion sort on some small threshold.
	 * 
	 * @param arr - An ArrayList of which we want to quick sort the values of.
	 * @param comp - The Comparator object that defines the ordering of the sort.
	 * @param left - The left most boundary index of the ArrayList
	 * @param right - The right most boundary index of the ArrayList
	 */
	private static <T> void recursiveQuick(ArrayList<T> arr, Comparator<? super T> comp, int left, int right) {

		// Check the current size of the ArrayList being brought in, if its equal to or below the thresholdVal, then do insertion sort
		int size = right - left + 1;
		if (size <= thresholdValQuickSort) {
			insertionSort(arr, comp);
			return;
		}

		// Find the pivot based on which pivot method you wish. You can use pivot1, pivot2, or pivot3 method
		int pivot = pivot2(arr, comp);
		
		T pivotVal = arr.get(pivot);

		// Swap the pivot to the end of the ArrayList and change its current location.
		swap(arr, pivot, size - 1);
		
		pivot = size - 1;

		// This next series of code, performs the partition step of the the Quicksort algorithm.
		
		// Keeps track of the size of the current location/size of the ArrayList
		int low;
		int high;
		
		for (low = left, high = right - 1; ;) {

			// Search from the left side up to the pivot, also check that we don't go out of current ArrayList size boundary.
			while (comp.compare(arr.get(++low), pivotVal) < 0 && low < right - 1) {
				;
			}
			// Search from the right side down to the pivot, also check that we don't go out of current ArrayList size boundary.
			while (comp.compare(pivotVal, arr.get(--high)) < 0 && high > left) {
				;
			}
			if (low >= high) {
				break;
			}
			
			// Swaps the the values to their correct location according to the pivot
			swap(arr, low, high);

		}

		// Swap the pivot value back to its correct location
		swap(arr, low, right - 1);
		
		// Quicksort left hald of the list
		recursiveQuick(arr, comp, left, low - 1);

		// Quicksort right half of the list
		recursiveQuick(arr, comp, low + 1, right);

	}
	

	/**
	 * This method swaps two values in an ArrayList, given the index1 and index2 index values.
	 * 
	 * @param arr - An ArrayList of which we want to swap the values of.
	 * @param index1 - The location of the first value to swap
	 * @param index2 - The location of the second value to swap
	 */
	public static <T> void swap(ArrayList<T> arr, int index1, int index2) {
		T holder = arr.get(index1);

		arr.set(index1, arr.get(index2));

		arr.set(index2, holder);
	}

	/**
	 * This method is one of the pivot finding scenarios that we were asked to come up for the assignment to see which is the most
	 * effective. This method takes 3 random values inside of an ArrayList and returns the index to which  the middle value is 
	 * occurring. This test is similar to what we did in pivot2, except it is randomized.
	 * 
	 * @param arr - An ArrayList of which we want to find the pivot value of.
	 * @param comp - The Comparator object that defines the ordering of the sort.
	 * @return - The integer index of the chosen pivot value
	 */
	public static <T> int pivot1(ArrayList<T> arr, Comparator<? super T> comp) {

		// Create a random generator to find the random array index locations
		Random randomGenerator = new Random();

		// Find and store the random index locations
		int index1 = randomGenerator.nextInt(arr.size());

		int index2 = randomGenerator.nextInt(arr.size());

		int index3 = randomGenerator.nextInt(arr.size());

		// Compare the values of each index with each other and return the one that is in the middle.
		if (comp.compare(arr.get(index1), arr.get(index2)) < 0
				&& comp.compare(arr.get(index2), arr.get(index3)) < 0) {
			return index2;
		} else if (comp.compare(arr.get(index2), arr.get(index1)) < 0
				&& comp.compare(arr.get(index1), arr.get(index3)) < 0) {
			return index1;
		} else {
			return index3;

		}
	}

	/**
	 * This method is one of the pivot finding scenarios that we were asked to come up for the assignment to see which is the most 
	 * effective. In this pivot finding method, we choose the first, middle and last element in the ArrayList and return the value
	 * that is in the middle. This test is similar to what we did in pivot1, except it is not randomized.
	 * 
	 * @param arr - An ArrayList of which we want to find the pivot value of.
	 * @param comp - The Comparator object that defines the ordering of the sort.
	 * @return - The integer index of the chosen pivot value
	 */
	public static <T> int pivot2(ArrayList<T> arr, Comparator<? super T> comp) {

		// Find the values of the first, middle and ending index.
		int index1 = 0;

		int index2 = arr.size() / 2;

		int index3 = arr.size() - 1;

		// Compare the values of each index with each other and return the one that is in the middle.
		if (comp.compare(arr.get(index1), arr.get(index2)) < 0
				&& comp.compare(arr.get(index2), arr.get(index3)) < 0) {
			return index2;
		} else if (comp.compare(arr.get(index2), arr.get(index1)) < 0
				&& comp.compare(arr.get(index1), arr.get(index3)) < 0) {
			return index1;
		} else {
			return index3;
		}

	}

	/**
	 * This method is one of the pivot finding scenarios that we were asked to come up for the assignment to see which is the most
	 * effective. In this pivot finding method, we just choose the middle item in the ArrayList.
	 * 
	 * @param arr - An ArrayList of which we want to find the pivot value of. 
	 * @return - The integer index of the chosen pivot value
	 */
	public static <T> int pivot3(ArrayList<T> arr) {
		return arr.size() / 2;
	}

	/**
	 * This method generates and returns an ArrayList of integers 1 to size in ascending order. This method is designed to aid in the 
	 * testing of the sorting algorithms. 
	 * 
	 * @param size - The integer value of how many value were you
	 * @return - An ArrayList that is filled with the input size of integers in ascending order.
	 */
	public static ArrayList<Integer> generateBestCase(int size) {

		// Create an ArrayList to return
		ArrayList<Integer> arrayOfInts = new ArrayList<Integer>();
		// Fill the ArrayList in ascending order
		for (int i = 1; i <= size; i++) {
			arrayOfInts.add(i);
		}
		return arrayOfInts;

	}

	/**
	 * This method generates and returns an ArrayList of integers 1 to size in permuted order (i,e., randomly ordered). It accomplishes
	 *  this by using a random generator to mix up an already sorted list. This method is designed to aid in the testing of the 
	 *  sorting algorithms.
	 * 
	 * @param size - The integer value of how many value were you
	 * @return - An ArrayList that is filled with the input size of integers in random order.
	 */
	public static ArrayList<Integer> generateAverageCase(int size) {

		// Create and ArrayList to return
		ArrayList<Integer> integerList = new ArrayList<Integer>();
		
		// Call the best case method to fill the ArrayList with the correct number of elements
		integerList = generateBestCase(size);

		// Create a new random generate to mix up the values from the ArrayList
		Random randomGenerator = new Random();

		// Randomize the ArrayList values
		for (int i = 0; i < integerList.size(); i++) {
			int index1 = randomGenerator.nextInt(integerList.size());

			int index2 = randomGenerator.nextInt(integerList.size());

			swap(integerList, index1, index2);
		}

		return integerList;
	}

	/**
	 * This method generates and returns an ArrayList of integers 1 to size in descending order. This method is designed to aid 
	 * in the testing of the sorting algorithms.
	 * 
	 * @param size - The integer value of how many value were you 
	 * @return - An ArrayList that is filled with the input size of integers in reverse sorted order.
	 */
	public static ArrayList<Integer> generateWorstCase(int size) {

		// Create a new ArrayList to return
		ArrayList<Integer> arrayOfInts = new ArrayList<Integer>();
		
		// Fill it up in reverse order
		for (int i = 0; i < size; i++) {
			arrayOfInts.add(size - i);
		}
		return arrayOfInts;

	}

}