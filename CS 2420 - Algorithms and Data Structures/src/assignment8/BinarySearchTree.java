package assignment8;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * This class serves as an implementation of a Binary Search Tree for our Assignment 8. This class contains several methods that allows the user to
 * interact with BST and it also contains an additional class that acts as the nodes for the BST. This class uses Comaparable to compare the elements
 * in the nodes. In addition this class is designed to be a generic class, so that the BST Node can contain main different types of primitives and 
 * objects.
 * @author Tanner Martin and Robert Weischedel
 *
 * @param <Type> - A generic type or object the BST will be consisted of.
 */
public class BinarySearchTree<Type extends Comparable<? super Type>> implements SortedSet<Type> {

	// Private member variables to keep tract of the size and root node
	private int size;
	private BSTNode root;

	/**
	 * This method serves as the constructor of a new BinarySearchTree object.
	 */
	public BinarySearchTree() {
		root = null;
		size = 0;
	}

	/**
	 * This inner class serves as the implementation for the creation of new nodes for the BinarySearchTree. It contains the constructor to create new
	 * nodes and also the private member variables to store the data.
	 * @author Tanner Martin and Robert Weischedel
	 *
	 */
	class BSTNode {
		
		// Private member variables to hold the data, left, right and parent references.
		Type data;
		BSTNode left;
		BSTNode right;
		BSTNode parent;

		/**
		 * This serves as the constructor of a new BST Node object. It sets all references to null and sets the data to the item.
		 * @param item - The element you wish to add to the BST
		 */
		public BSTNode(Type item) {
			data = item;
			left = null;
			right = null;
			parent = null;
		}
	}

	/**
	 * This method adds the specified element to the BST. It returns true or false based on if the item was added to the BST. 
	 * 
	 * @param item - The element you wish to add to the set.
	 * @return A true or false depending on if the item was added to the BST.
	 * @throws NullPointerException - If the inputed item is null
	 */
	@Override
	public boolean add(Type item) throws NullPointerException {
		
		// Check if the element is null
		if (item == null) {
			throw new NullPointerException();
		}

		// If root is equal to null or if this is the first item you are adding to the BST.
		if (root == null) {
			root = new BSTNode(item);
			size++;
			return true;
		}

		// Find the parent where the node should be added
		BSTNode temp = getNode(item);
		size++;

		// If the item is already there, return false.
		if (item.compareTo(temp.data) == 0) {
			return false;
		}
		// If the item is not there find if it goes left or right of the parent node
		else if (item.compareTo(temp.data) < 0) {
			temp.left = new BSTNode(item);
			temp.left.parent = temp;
			return true;
		} 
		else {
			temp.right = new BSTNode(item);
			temp.right.parent = temp;
			return true;
		}
	}

	/**
	 * This method adds all the values from a collection set into the BST. It does this by calling the add method for each of the elements found in
	 * the collection set that was given as input.
	 * 
	 * @param - A collection set containing generics.
	 * @return - A true or false depending on if at least one of the values in the collection set was added to the BST.
	 * @throws NullPointerException - If the input item is null.
	 */
	@Override
	public boolean addAll(Collection<? extends Type> items) throws NullPointerException {

		// Create a boolean flag to determine if at least one item in the collection was added
		boolean newItemAdded = false;

		// Iterate through the inputed collection set
		for (Type t : items) {
			
			if (t == null) {
				throw new NullPointerException();
			}

			// Check and see of we can add the item, if we did change the flag true.
			if (add(t)) {
				newItemAdded = true;
			}

		}
		
		return newItemAdded;
	}

	/**
	 * This method clears the whole BST and even deletes the node.
	 */
	@Override
	public void clear() {
		root = null;
		size = 0;
	}

	/**
	 * This method is used in all the other methods in this class. It is the main searching algorithm we use to find the parent or the actual node in
	 * all the other classes. This method has been declared private because it doesn't need to be accessed outside of the class and is used by almost
	 * all other methods.
	 * 
	 * @param item - The item found in the node we wish to find or locate the parent of.
	 * @return - The actual or parent node of the item you wish to find. 
	 */
	private BSTNode getNode(Type item) {

		// Start at the root variable
		BSTNode temp = root;

		// Compare the root value to the item value to determine where to start.
		int compareValue = item.compareTo(temp.data);

		// As long as the compareValue is not equal continue looping.
		while (compareValue != 0) {
			// If it is less than zero move left down the BST
			if (compareValue < 0){
				if (temp.left != null){
					temp = temp.left;
				}
				// If the value is null break.
				else{
					break;
				}
			}
			// Else move right down the BST
			else{
			if (temp.right != null){
				temp = temp.right;
			}
			// If the value is null break.
			else{
				break;
			}
			}

			// Compare the new temp data value to continue or exit out of the loop.
			compareValue = item.compareTo(temp.data);
		}
		return temp;

	}

	/**
	 * This method checks to see if the BST contains a specific element. It uses the getNode method in order to see if the node is in the method
	 * or not. 
	 * 
	 * @param item - The item you wish to know if it is in the BST or not.
	 * @return - True or false if the item is contained or not
	 * @throws NullPointerException - If the inputed item is null.
	 */
	@Override
	public boolean contains(Type item) throws NullPointerException {
		
		// If item is equal to null, throw the exception.
		if (item == null) {
			throw new NullPointerException();
		}

		// Check if root is null or not.
		if(root == null){
			return false;
		}
		
		// Find the node
		BSTNode temp = getNode(item);

		// Check if that is the actual node or parent.
		if (item.compareTo(temp.data) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * This method checks to see if any the elements in a given collection set are actually contained in the BST. It does this by calling the contains
	 * method on each element in the collection.
	 * 
	 * @param - A collection set containing generics.
	 * @return - True or false depending if any of the elements in the collection are contained in the BST
	 */
	@Override
	public boolean containsAll(Collection<? extends Type> items) throws NullPointerException {

		// Iterate through all elements in the collection
		for (Type t : items) {
			
			if (t == null) {
				throw new NullPointerException();
			}
			
			// Check if the element is in the BST
			if (contains(t)) {
				return true;
			}

		}
		return false;
	}

	/**
	 * This method finds the first or lowest value or left most value in the set. This is a driver method for the recursive method firstRecursive. 
	 * 
	 * @return - The data value of the first node.
	 * @throws - NoSuchElemetnExcpetion - If the BST is empty.
	 */
	@Override
	public Type first() throws NoSuchElementException {

		if (isEmpty()) {
			throw new NoSuchElementException();
		}

		return firstRecursive(root).data;
	}

	/**
	 * This is the recursive portion of the first method. It continually searches left by going down the left of the BST.
	 * @param temp - The node you wish to start from.
	 * 
	 * @return - The first node. 
	 */
	private BSTNode firstRecursive(BSTNode temp) {

		if (temp.left == null) {
			return temp;
		}

		return firstRecursive(temp.left);
	}

	/**
	 * This method checks to see if the BST is empty or not.
	 * 
	 * @return - True or false if the BST is empty or not.
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * This method finds the last or largest value or right most value in the set. This is a driver method for the recursive method lastRecursive.
	 * 
	 * @return - The data value of the last node
	 * @throws - NoSuchElementException - If the BST is empty.
	 */
	@Override
	public Type last() throws NoSuchElementException {
		
		if (isEmpty()) {
			throw new NoSuchElementException();
		}

		return lastRecursive(root);
	}

	/**
	 * This is the recursive portion of the last method. It continually searches right by going down the right of the BST.
	 * 
	 * @param temp - The node who wish to start from
	 * @return - The last node.
	 */
	private Type lastRecursive(BSTNode temp) {

		if (temp.right == null) {
			return temp.data;
		}

		return lastRecursive(temp.right);

	}

	/**
	 * This method removes the inputed item from the BST. There are three different cases for deleting nodes in a BST and this method handles them all.
	 * It handles the case of when the node that you wish to delete is a leaf node, when it is a parent node with one and two children. 
	 * 
	 * @param - The generic item we wish to remove.
	 * @return - True or False depending if the item was removed from BST or not.
	 * @throws - NullPointerException If the item brought in is null.
	 */
	@Override
	public boolean remove(Type item) throws NullPointerException{
		
		// If there is only one node left, call the clear command and return true.
		if(size == 1){
			clear();
			return true;
		}
		
		// If there are no items in the list to delete return false, since we didn't remove anything.
		if(size == 0){
			return false;
		}
		
		// Check and see if the item is null
		if (item == null) {
			throw new NullPointerException();
		}
			
		// Find the node with the specified item.
		BSTNode temp = getNode(item);
		
		// Check and see if the node returned from getNode is the correct one.
		if(item.compareTo(temp.data) != 0){
			return false;
		}
		
		// Get the parent of the node you wish to remove.
		BSTNode tempParent = temp.parent;
		
		// This handles if the node is a leaf node.
		if(temp.left == null && temp.right == null){
			// If the node is on the left of the parent
			if(item.compareTo(tempParent.left.data) == 0){
				// Set the reference to null, decrement the size of list
				tempParent.left = null;
				temp = null;
				size--;
				return true;
			}
			// Or the right
			else{
				// Set the reference to null, decrement the size of list
				tempParent.right = null;
				temp = null;
				size--;
				return true;
			}
		}
		
		// This handles if the node has one child
		
		// If the child is on the left.
		if(temp.left != null && temp.right == null){
			
			// If the node we are looking at is the root node.
			if(temp.data.compareTo(root.data) == 0){
				temp.data = temp.left.data;
				temp.left = null;
				size--;
				return true;
			}
			
			// If the node is on the left of the parent and child is on the left
			if(tempParent.left.data.compareTo(temp.data) == 0){
				temp.left.parent = tempParent;
				tempParent.left = temp.left;
				temp = null;
				size--;
				return true;
			}
			// If the node is on the right of the parent and child is on the left
			else{
				temp.left.parent = tempParent;
				tempParent.right = temp.left;
				temp = null;
				size--;
				return true;
			}
		}
		// If the child is on the right.
		else if(temp.right != null && temp.left == null){
			
			// If the node we are looking at is the root node.
			if(temp.data.compareTo(root.data) == 0){
				temp.data = temp.right.data;
				temp.right = null;
				size--;
				return true;
			}
			// If the node is on the left of the parent and child is on the right
			if(tempParent.left.data.compareTo(temp.data) == 0){
				temp.right.parent = tempParent;
				tempParent.left = temp.right;
				temp = null;
				size--;
				return true;
			}
			// If the node is on the right of the parent and leaf is on the right
			else{
				temp.right.parent = tempParent;
				tempParent.right = temp.right;
				temp = null;
				size--;
				return true;
			}
		}
		
		// This handles if the node has two children
		if(temp.left != null && temp.right != null || temp.data.compareTo(root.data) == 0)
		{	
			// This calls the firstRecusive method to find the left most value on the right side of the node we wish to delete
			BSTNode successor = firstRecursive(temp.right);
			
			// Set the node we wish to delete data to the successor nodes data.
			temp.data = successor.data;
			
			// Delete the node and reset its parent reference.
			if(successor.parent.left.data.compareTo(successor.data) == 0)
			{
				successor.parent.left = null;
			}
			else
			{
				successor.parent.right = null;
			}
			successor = null;
			size--;
			return true;
		}
		
		// Return false if nothing is deleted.
		return false;
	}

	/**
	 * This method removes all the items that are found in a given collection set. It calls the remove method as we iterate through the each item 
	 * in the set. 
	 * 
	 * @param - A collection set containing generics.
	 * @return - True or False depending on if any of the items in the collection are removed from the BST
	 * @throws - NullPointerException If the item brought in is null.
	 */
	@Override
	public boolean removeAll(Collection<? extends Type> items) throws NullPointerException{
		
		// Create a boolean flag to determine if at least one item in the collection was added
		boolean itemRemoved = false;

		// Iterate through each item in the set
		for (Type t : items) {
			if (t == null) {
				throw new NullPointerException();
			}

			// Try and remove the item, if one is removed, then return true
			if (remove(t)) {
				itemRemoved = true;
			}

		}
		return itemRemoved;
	}

	/**
	 * This method returns the current size of the BST.
	 * 
	 * @param - Integer that is the size of the BST
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * This method acts as a driver method for the inOrderTraversal. It returns an ArrayList of all the data values that can currently be found in the BST.
	 * 
	 * @return - ArrayList of BST data values
	 */
	@Override
	public ArrayList<Type> toArrayList() {
		
		ArrayList<Type> returnArray = new ArrayList<Type>();
		
		// Call inOrderTraversal
		return inOrderTraversal(this.root, returnArray);
	}

	/**
	 * This method is the recursive portion of the toArrayList method. It uses in order traversing to retreve the data values from all nodes that are currently in the BST.
	 * 
	 * @param temp - The node we wish to start at, which is the root node.
	 * @param returnArray - The empty ArrayList we wish to fill.
	 * @return - The filled returnArray ArrayList
	 */
	private ArrayList<Type> inOrderTraversal(BSTNode temp, ArrayList<Type> returnArray) {
		
		// Check if node is equal to null.
		if(temp == null){
			return null;
		}
		
		// Perform inOrderTraversal of the BST
		inOrderTraversal(temp.left, returnArray);
		returnArray.add(temp.data);
		inOrderTraversal(temp.right, returnArray);
		
		return returnArray;
	}

	/**
	 *  Driver for writing this tree to a dot file. This method was given to us by Dr. Meyer.
	 * @param filename - The name of the file that we wish to make a visualization of. 
	 */
	public void writeDot(String filename) {
		try {
			// PrintWriter(FileWriter) will write output to a file
			PrintWriter output = new PrintWriter(new FileWriter(filename));

			// Set up the dot graph and properties
			output.println("digraph BST {");
			output.println("node [shape=record]");

			if (root != null)
				writeDotRecursive(root, output);
			// Close the graph
			output.println("}");
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  Recursive method for writing the tree to a dot file. This method was given to us by Dr. Meyer.
	 * @param n - The root node
	 * @param output - The desired file you wish to have the visualization of.
	 * @throws Exception - If the file can not be found or is corrupted.
	 */
	private void writeDotRecursive(BSTNode n, PrintWriter output)
			throws Exception {
		output.println(n.data + "[label=\"<L> |<D> " + n.data + "|<R> \"]");
		if (n.left != null) {
			// write the left subtree
			writeDotRecursive(n.left, output);

			// then make a link between n and the left subtree
			output.println(n.data + ":L -> " + n.left.data + ":D");
		}
		if (n.right != null) {
			// write the left subtree
			writeDotRecursive(n.right, output);

			// then make a link between n and the right subtree
			output.println(n.data + ":R -> " + n.right.data + ":D");
		}

	}

}