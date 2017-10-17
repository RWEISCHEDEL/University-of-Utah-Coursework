package assignment12;

import java.io.File;

/**
 * This class served as the testing experiment code for the Assignment 12 Analysis document. I simply created a new HuffmanTree object, and used the compressFile method
 * within its class to compress my "ASCIIVALS" text file and save the compressed file to my "output" text file.
 * @author Robert Weischedel
 *
 */
public class HuffmanTreeExperiment {

	public static void main(String[] args) {
		
		// Create a new Huffman Tree object
		HuffmanTree t = new HuffmanTree();

		// Compress the file filled with the 93 unique character string into a smaller file called "output".
	    t.compressFile(new File("ASCIIVALS"), new File("output"));

	}

}
