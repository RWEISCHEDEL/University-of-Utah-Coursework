package L15;

import java.util.ArrayList;

public class BSTDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BST bst = new BST();

		bst.insert("dog");
		bst.insert("pelican");
		bst.insert("zebra");
		bst.insert("cat");
		bst.insert("alpaca");
		bst.insert("cow");
		bst.insert("chipmunck");
		bst.insert("dragon");
		
		// Try inserting in alphabetical order instead
		// What happens to the shape (and thus performance) of the tree?
		/*
		bst.insert("alpaca");
		bst.insert("cat");
		bst.insert("chipmunck");
		bst.insert("cow");
		bst.insert("dog");
		bst.insert("dragon");
		bst.insert("pelican");
		bst.insert("zebra");
		*/
		
		
		// Will generate a file "BST.dot" in your project (not package) directory
		// On a CADE linux machine run the command:
		// dot -Tgif BST.dot -o BST.gif
		// Then open up BST.gif to examine the tree
		bst.writeDot("BST.dot");


		System.out.println(bst.contains("cow"));
		System.out.println(bst.contains("elephant"));

		
		// Prints all items in the tree
		// Try commenting out a different traversal order in bst.DFT() 
		ArrayList<String> strings = bst.DFT();
		for(String s : strings)
			System.out.println(s);

	}

}

