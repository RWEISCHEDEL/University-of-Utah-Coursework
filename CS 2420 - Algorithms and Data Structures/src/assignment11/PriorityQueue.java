package assignment11;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Represents a priority queue of generically-typed items. The queue is
 * implemented as a min heap. The min heap is implemented implicitly as an
 * array.
 * 
 * This class was given to us mostly completed by Dr. Meyer. All that we had to
 * implement was the add, findMin, and deleteMin methods in addition to their
 * associated helped methods.
 * 
 * @author Robert Weischedel and Jackson Murphy
 */
public class PriorityQueue<AnyType> {

	// List of Private Member variables

	// Holds the current number of elements stored in the array.
	private int currentSize;

	// This is the array where we store all the values.
	private AnyType[] array;

	// This variable holds the Comparator that the user can choose to bring in
	// if a different ordering is desired.
	private Comparator<? super AnyType> cmp;

	/**
	 * Constructs an empty priority queue. Orders elements according to their
	 * natural ordering (i.e., AnyType is expected to be Comparable) AnyType is
	 * not forced to be Comparable.
	 */
	public PriorityQueue() {
		currentSize = 0;
		cmp = null;
		array = (AnyType[]) new Object[10]; // safe to ignore warning
	}

	/**
	 * Construct an empty priority queue with a specified comparator. Orders
	 * elements according to the input Comparator (i.e., AnyType need not be
	 * Comparable).
	 */
	public PriorityQueue(Comparator<? super AnyType> c) {
		currentSize = 0;
		cmp = c;
		array = (AnyType[]) new Object[10]; // safe to ignore warning
	}

	/**
	 * @return the number of items in this priority queue.
	 */
	public int size() {
		return currentSize;
	}

	/**
	 * Makes this priority queue empty.
	 */
	public void clear() {
		currentSize = 0;
	}

	/**
	 * This method returns the minimum item in the priority queue.
	 * 
	 * @return the minimum item in this priority queue.
	 * @throws NoSuchElementException
	 *             if this priority queue is empty.
	 * 
	 *             (Runs in constant time.)
	 */
	public AnyType findMin() throws NoSuchElementException {
		
		if(currentSize == 0){
			throw new NoSuchElementException();
		}
		
		return array[0];
	}

	/**
	 * Removes and returns the minimum item in this priority queue.
	 * 
	 * @throws NoSuchElementException
	 *             if this priority queue is empty.
	 * 
	 *             (Runs in logarithmic time.)
	 */
	public AnyType deleteMin() throws NoSuchElementException {

		// if the heap is empty, throw a NoSuchElementException
		if (currentSize == 0) {
			throw new NoSuchElementException();
		}

		// store the minimum item so that it may be returned at the end
		AnyType min = findMin();

		// replace the item at minIndex with the last item in the tree
		array[0] = array[currentSize - 1];

		// update size
		currentSize--;

		// percolate the item at minIndex down the tree until heap order is
		// restored
		// It is STRONGLY recommended that you write a percolateDown helper
		// method!
		percolateDown();

		// return the minimum item that was stored

		return min;
	}

	/**
	 * This method adds a new item to the priority queue. In addition it will
	 * automatically percolate the new item up if it is located in the wrong
	 * position. Also this method will update and enlarge the size of the array
	 * if it grows to large.
	 * 
	 * (Runs in logarithmic time.) Can sometimes terminate early.
	 * 
	 * @param x
	 *            -- the item to be inserted into the queue
	 */
	public void add(AnyType x) {

		// if the array is full, double its capacity
		if (currentSize == array.length) {
			array = Arrays.copyOf(array, array.length * 2);
		}

		// add the new item to the next available node in the tree, so that
		// complete tree structure is maintained
		array[currentSize] = x;

		// percolate the new item up the levels of the tree until heap order is
		// restored
		percolateUp();

		// update size
		currentSize++;

	}

	/**
	 * Generates a DOT file for visualizing the binary heap.
	 */
	public void generateDotFile(String filename) {
		try {
			PrintWriter out = new PrintWriter(filename);
			out.println("digraph Heap {\n\tnode [shape=record]\n");

			for (int i = 0; i < currentSize; i++) {
				out.println("\tnode" + i + " [label = \"<f0> |<f1> " + array[i]
						+ "|<f2> \"]");
				if (((i * 2) + 1) < currentSize)
					out.println("\tnode" + i + ":f0 -> node" + ((i * 2) + 1)
							+ ":f1");
				if (((i * 2) + 2) < currentSize)
					out.println("\tnode" + i + ":f2 -> node" + ((i * 2) + 2)
							+ ":f1");
			}

			out.println("}");
			out.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * Internal method for comparing lhs and rhs using Comparator if provided by
	 * the user at construction time, or Comparable, if no Comparator was
	 * provided.
	 */
	private int compare(AnyType lhs, AnyType rhs) {
		if (cmp == null)
			return ((Comparable<? super AnyType>) lhs).compareTo(rhs); // safe
																		// to
																		// ignore
																		// warning
		// We won't test your code on non-Comparable types if we didn't supply a
		// Comparator

		return cmp.compare(lhs, rhs);
	}

	// LEAVE IN for grading purposes
	public Object[] toArray() {
		Object[] ret = new Object[currentSize];
		for (int i = 0; i < currentSize; i++)
			ret[i] = array[i];
		return ret;
	}

	/**
	 * This method is a helper method for the add method above. It takes the
	 * newest added item and ensures that it is located in the correct location
	 * in the queue by percolating it up the queue until it reaches its correct
	 * spot.
	 * 
	 */
	private void percolateUp() {

		// Get the index of the newly added item.
		int addedItemIndex = currentSize;

		// Get the index of newly added items parent.
		int parentIndex = getParentIndex(addedItemIndex);

		// Loop through and move the newly added item up swapping it each time
		// we move up until we find its correct location.
		while (this.compare(array[addedItemIndex], array[parentIndex]) < 0) {

			// Swap the item
			AnyType temp = array[addedItemIndex];
			array[addedItemIndex] = array[parentIndex];
			array[parentIndex] = temp;

			// Update the newly added items index
			addedItemIndex = parentIndex;

			// Find the new parent
			parentIndex = getParentIndex(addedItemIndex);
		}

	}

	/**
	 * This method finds and returns the parent value of the value at the
	 * inputed index.
	 * 
	 * @param addedItemIndex
	 *            - The index of the value you wish to find the parent of.
	 * @return - The parents index.
	 */
	private int getParentIndex(int addedItemIndex) {
		return (addedItemIndex - 1) / 2;
	}

	private void percolateDown() {

		int sortItemIndex = 0;

		boolean donePercolating = false;

		while (!donePercolating) {

			int left = getLeftChild(sortItemIndex);
			int right = getRightChild(sortItemIndex);

			// Two kids
			if (left < currentSize && right < currentSize) {
				if (this.compare(array[left], array[right]) < 0) {
					if (this.compare(array[sortItemIndex], array[left]) > 0) {
						AnyType temp = array[sortItemIndex];
						array[sortItemIndex] = array[left];
						array[left] = temp;

						sortItemIndex = left;
					} else {
						return;
					}

				} else {

					if (this.compare(array[sortItemIndex], array[right]) > 0) {
						AnyType temp = array[sortItemIndex];
						array[sortItemIndex] = array[right];
						array[right] = temp;

						sortItemIndex = right;
					} else {
						return;
					}

				}
			}
			// One child
			else if (left < currentSize) {
				if (this.compare(array[sortItemIndex], array[left]) > 0) {
					AnyType temp = array[sortItemIndex];
					array[sortItemIndex] = array[left];
					array[left] = temp;
				} else {
					return;
				}
			}
			// No children
			else {
				return;
			}

		}

	}

	private int getRightChild(int sortItemIndex) {
		return sortItemIndex * 2 + 2;
	}

	private int getLeftChild(int sortItemIndex) {
		return sortItemIndex * 2 + 1;
	}
}
