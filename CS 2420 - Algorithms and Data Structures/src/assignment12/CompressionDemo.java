package assignment12;

import java.io.File;

public class CompressionDemo {
  
  public static void compressFile(String infile, String outfile) {
    HuffmanTree t = new HuffmanTree();

    t.compressFile(new File(infile), new File(outfile));
    
    t.huffmanToDot("/home/jmurphy/Desktop/huffman_tree.dot");
  }
  
  public static void decompressFile(String infile, String outfile) {
    HuffmanTree t = new HuffmanTree();

    t.decompressFile(new File(infile), new File(outfile));
  }
  
  public static void main(String[] args) {
    compressFile("/home/jmurphy/workspace/cs2420/src/assignment12/hugeFile", "/home/jmurphy/Desktop/compressed.txt");
    
    decompressFile("/home/jmurphy/Desktop/compressed.txt", "/home/jmurphy/Desktop/decompressed.txt");
  }
}
