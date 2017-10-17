package L15;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;


// A Binary Search Tree that holds only strings
public class BST {
	
	BSTNode root;
	
	public BST()
	{
		root = null;
	}
	
	
	private class BSTNode
	{
		String data;
		BSTNode left;
		BSTNode right;
		
		public BSTNode(String d)
		{
			data = d;
			left = null;
			right = null;
		}
		@Override
		public String toString()
		{
			return data;
		}
	}
	
	
	// Driver method for insertion (start recursion at root)
	public void insert(String s)
	{
		if(root == null)
			root = new BSTNode(s);
		else
			insertRecursive(s, root);
	}
	
	// Recursive method for insertion
	private void insertRecursive(String s, BSTNode n)
	{
		// Does the new item go to the left or right?
		if(s.compareTo(n.data) < 0) 
		{
			// If we found an empty spot (null), put the item there
			if(n.left == null) 
				n.left = new BSTNode(s);
			else
				insertRecursive(s, n.left);
		}
		else
		{
			// If we found an empty spot (null), put the item there
			if(n.right == null)
				n.right = new BSTNode(s);
			else
				insertRecursive(s, n.right);
		}
		
	}
	
	
	// Driver method for contains
	public boolean contains(String s)
	{
		System.out.println("searching for " + s);
		return searchRecursive(s, root);
	}
	
	// Recursive method for contains
	private boolean searchRecursive(String s, BSTNode n)
	{

		// Reached the bottom of the tree
		if(n == null)
			return false;
		
		// Found the item at this node
		if(s.equals(n.data))
			return true;
		
		// Otherwise, search in left or right subtree
		if(s.compareTo(n.data) < 0)
		{
			System.out.println("\t at node " + n + ", going left");
			return searchRecursive(s, n.left);
		}
		else
		{
			System.out.println("\t at node " + n + ", going right");
			return searchRecursive(s, n.right);
			
		}
			
	}
	
	
	// Performs a depth first traversal of the tree
	// Invokes one of three recursive DTF traversal orders
	public ArrayList<String> DFT()
	{
		ArrayList<String> retval = new ArrayList<String>();
		//preorderDFT(retval, root);
		//inorderDFT(retval, root);
		postorderDFT(retval, root);
		return retval;
	}
	
	private void preorderDFT(ArrayList<String> strings, BSTNode n)
	{
		if(n == null)
			return;
		strings.add(n.data);
		preorderDFT(strings, n.left);
		preorderDFT(strings, n.right);
	}
	
	private void inorderDFT(ArrayList<String> strings, BSTNode n)
	{
		if(n == null)
			return;
		inorderDFT(strings, n.left);
		strings.add(n.data);
		inorderDFT(strings, n.right);
	}
	
	private void postorderDFT(ArrayList<String> strings, BSTNode n)
	{
		if(n == null)
			return;
		postorderDFT(strings, n.left);
		postorderDFT(strings, n.right);
		strings.add(n.data);
	}
	
	
	// Driver for writing this tree to a dot file
	public void writeDot(String filename)
	{
		try {
			// PrintWriter(FileWriter) will write output to a file
			PrintWriter output = new PrintWriter(new FileWriter(filename));
			
			// Set up the dot graph and properties
			output.println("digraph BST {");
			output.println("node [shape=record]");
			
			if(root != null)
				writeDotRecursive(root, output);
			// Close the graph
			output.println("}");
			output.close();
		}
		catch(Exception e){e.printStackTrace();}
	}

	
	// Recursive method for writing the tree to  a dot file
	private void writeDotRecursive(BSTNode n, PrintWriter output) throws Exception
	{
		output.println(n.data + "[label=\"<L> |<D> " + n.data + "|<R> \"]");
		if(n.left != null)
		{
			// write the left subtree
			writeDotRecursive(n.left, output);
			
			// then make a link between n and the left subtree
			output.println(n.data + ":L -> " + n.left.data + ":D" );
		}
		if(n.right != null)
		{
			// write the left subtree
			writeDotRecursive(n.right, output);
			
			// then make a link between n and the right subtree
			output.println(n.data + ":R -> " + n.right.data + ":D" );
		}
		
	}

}

