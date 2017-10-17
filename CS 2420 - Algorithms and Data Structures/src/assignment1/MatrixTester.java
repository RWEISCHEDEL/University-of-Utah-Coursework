/*
 * Here is a starting point for your Matrix tester. You will have to fill in the rest of "main" with
 * more code to sufficiently test your Matrix class. We will be using our own MatrixTester for grading. 
*/
package assignment1;

/**
 * This class only contains a main method that provides various test cases for the Matrix Class. The main method contains
 * several different tests for each of the various methods that can be invoked on a Matrix object. 
 * @author Dr. Meyer and Robert Weischedel
 * 1/22/15
 * CS 2420
 */
public class MatrixTester {
	
	/**
	 * Contains the creation of several Matrix objects and all the tests if the various methods that can be invoked 
	 * on a Matrix object. .equals, .toString, .plus, .times
	 * @param args
	 */
	public static void main(String[] args)
	{	
		// Create a Matrix Objects
		Matrix M1 = new Matrix(new int[][]
		                                 {{1, 2, 3},
										  {2, 5, 6}});
		
		Matrix M2 = new Matrix(new int[][]
		                                 {{4, 5},
										  {3, 2},
										  {1, 1}});
		
		// this is the known correct result of multiplying M1 by M2
		Matrix referenceMultiply = new Matrix(new int[][]
		                                                {{13, 12},
														 {29, 26}});
		
		Matrix M3 = new Matrix(new int [][]
										  {{1, 0},
				                           {0, 1}});
		
		Matrix M4 = new Matrix(new int [][]
				                          {{0, -1},
                                           {-1, 0}});
		
		
		/* 
		 * Note that none of the tests below will be correct until you have implemented all methods.
		 * This is just one example of a test, you must write more tests and cover all cases.
		 */
		
		// get the matrix computed by your times method
		Matrix computedMultiply = M1.times(M2);
		
		// exercises your toString method
		System.out.println("Computed result for M1 * M2:\n" + computedMultiply); 
		
		// exercises your .equals method, and makes sure that your computed result is the same as the known correct result
		if(!referenceMultiply.equals(computedMultiply)) 
			System.out.println("Should be:\n" + referenceMultiply);
		

		// The various tests begin here :
		System.out.println("The following tests are designed to test the four different methods that can be envoked on a Matrix Object : " + "\n");
		
		// Testing of the .equals method
		System.out.println(".equals Method tests : ");
		System.out.println("Testing if M1 is equal to M1 : " + M1.equals(M1));
		System.out.println("Testing if M1 is equal to M2 : " + M1.equals(M2));
		System.out.println("Testing if M1 is equal to M3 : " + M1.equals(M3));
		System.out.println("Testing if M1 is equal to M4 : " + M1.equals(M4) + "\n");
		
		System.out.println("Testing if M2 is equal to M1 : " + M2.equals(M1));
		System.out.println("Testing if M2 is equal to M2 : " + M2.equals(M2));
		System.out.println("Testing if M2 is equal to M3 : " + M2.equals(M3));
		System.out.println("Testing if M2 is equal to M4 : " + M2.equals(M4) + "\n");
		
		System.out.println("Testing if referenceMultiply is equal to referenceMultiply : " + referenceMultiply.equals(referenceMultiply));
		System.out.println("Testing if referenceMultiply is equal to M1 : " + referenceMultiply.equals(M1));
		System.out.println("Testing if referenceMultiply is equal to M2 : " + referenceMultiply.equals(M2));
		System.out.println("Testing if M1 is equal to referenceMultiply : " + M1.equals(referenceMultiply));
		System.out.println("Testing if M2 is equal to referenceMultiply : " + M2.equals(referenceMultiply));
		System.out.println("Testing if referenceMultiply is equal to computedMultiply : " + referenceMultiply.equals(computedMultiply));
		System.out.println("Testing if computedMultiply is equal to referenceMultiply : " + computedMultiply.equals(referenceMultiply) + "\n");
		
		System.out.println("Testing if M3 is equal to M1 : " + M3.equals(M1));
		System.out.println("Testing if M3 is equal to M2 : " + M3.equals(M2));
		System.out.println("Testing if M3 is equal to M3 : " + M3.equals(M3));
		System.out.println("Testing if M3 is equal to M4 : " + M3.equals(M4) + "\n");
		
		System.out.println("Testing if M4 is equal to M1 : " + M4.equals(M1));
		System.out.println("Testing if M4 is equal to M2 : " + M4.equals(M2));
		System.out.println("Testing if M4 is equal to M3 : " + M4.equals(M3));
		System.out.println("Testing if M4 is equal to M4 : " + M4.equals(M4) + "\n");
		
		// Testing of the .toString method
		System.out.println(".toString Method tests");
		System.out.println("M1 toString : ");
		System.out.println(M1.toString());
		
		System.out.println("M2 toString : ");
		System.out.println(M2.toString());
		
		System.out.println("referenceMultiply toString : ");
		System.out.println(referenceMultiply.toString());
		
		System.out.println("computedMultiply toString : ");
		System.out.println(computedMultiply.toString());
		
		System.out.println("M3 toString : ");
		System.out.println(M3.toString());
		
		System.out.println("M4 toString : ");
		System.out.println(M4.toString());
		
		
		// Testing of the .plus method
		System.out.println("\n" + ".plus Method tests");
		System.out.println("M1 + M1 : ");
		System.out.println(M1.plus(M1));
		
		System.out.println("M1 + M2 : ");
		System.out.println(M1.plus(M2));
		
		System.out.println("M1 + M3 : ");
		System.out.println(M1.plus(M3));
		
		System.out.println("M1 + M4 : ");
		System.out.println(M1.plus(M4));
		
		System.out.println("M2 + M1 : ");
		System.out.println(M2.plus(M1));
		
		System.out.println("M2 + M2 : ");
		System.out.println(M2.plus(M2));
		
		System.out.println("M2 + M3 : ");
		System.out.println(M2.plus(M3));
		
		System.out.println("M2 + M4 : ");
		System.out.println(M2.plus(M4));
		
		System.out.println("M1 + referenceMultiply : ");
		System.out.println(M1.plus(referenceMultiply));
		
		System.out.println("referenceMultiply + M1 : ");
		System.out.println(referenceMultiply.plus(M1));
		
		System.out.println("M2 + referenceMultiply : ");
		System.out.println(M2.plus(referenceMultiply));
		
		System.out.println("referenceMultiply + M2 : ");
		System.out.println(referenceMultiply.plus(M2));
		
		System.out.println("referenceMultiply + referenceMultiply : ");
		System.out.println(referenceMultiply.plus(referenceMultiply));
		
		System.out.println("M1 + computedMultiply : ");
		System.out.println(M1.plus(computedMultiply));
		
		System.out.println("computedMultiply + M1 : ");
		System.out.println(computedMultiply.plus(M1));
		
		System.out.println("M2 + computedMultiply : ");
		System.out.println(M2.plus(computedMultiply));
		
		System.out.println("computedMultiply + M2 : ");
		System.out.println(computedMultiply.plus(M2));
		
		System.out.println("computedMultiply + computedMultiply : ");
		System.out.println(computedMultiply.plus(computedMultiply));
		
		System.out.println("M3 + M1 : ");
		System.out.println(M3.plus(M1));
		
		System.out.println("M3 + M2 : ");
		System.out.println(M3.plus(M2));
		
		System.out.println("M3 + M3 : ");
		System.out.println(M3.plus(M3));
		
		System.out.println("M3 + M4 : ");
		System.out.println(M3.plus(M4));
		
		System.out.println("M4 + M1 : ");
		System.out.println(M4.plus(M1));
		
		System.out.println("M4 + M2 : ");
		System.out.println(M4.plus(M2));
		
		System.out.println("M4 + M3 : ");
		System.out.println(M4.plus(M3));
		
		System.out.println("M4 + M4 : ");
		System.out.println(M4.plus(M4));
		
		
		// Testing of the .times method 
		System.out.println("\n" + ".times Method tests");
		System.out.println("M1 * M1 : ");
		System.out.println(M1.times(M1));
		
		System.out.println("M1 * M2 : ");
		System.out.println(M1.times(M2));
		
		System.out.println("M1 * M3 : ");
		System.out.println(M1.times(M3));
		
		System.out.println("M1 * M4 : ");
		System.out.println(M1.times(M4));
		
		System.out.println("M2 * M1 : ");
		System.out.println(M2.times(M1));
		
		System.out.println("M2 * M2 : ");
		System.out.println(M2.times(M2));
		
		System.out.println("M2 * M3 : ");
		System.out.println(M2.times(M3));
		
		System.out.println("M2 * M4 : ");
		System.out.println(M2.times(M4));
		
		System.out.println("M1 * referenceMultiply : ");
		System.out.println(M1.times(referenceMultiply));
		
		System.out.println("referenceMultiply * M1 : ");
		System.out.println(referenceMultiply.times(M1));
		
		System.out.println("M2 * referenceMultiply : ");
		System.out.println(M2.times(referenceMultiply));
		
		System.out.println("referenceMultiply * M2 : ");
		System.out.println(referenceMultiply.times(M2));
		
		System.out.println("referenceMultiply * referenceMultiply : ");
		System.out.println(referenceMultiply.times(referenceMultiply));
		
		System.out.println("M1 * computedMultiply : ");
		System.out.println(M1.times(computedMultiply));
		
		System.out.println("computedMultiply * M1 : ");
		System.out.println(computedMultiply.times(M1));
		
		System.out.println("M2 * computedMultiply : ");
		System.out.println(M2.times(computedMultiply));
		
		System.out.println("computedMultiply * M2 : ");
		System.out.println(computedMultiply.times(M2));
		
		System.out.println("computedMultiply * computedMultiply : ");
		System.out.println(computedMultiply.times(computedMultiply));
		
		System.out.println("M3 * M1 : ");
		System.out.println(M3.times(M1));
		
		System.out.println("M3 * M2 : ");
		System.out.println(M3.times(M2));
		
		System.out.println("M3 * M3 : ");
		System.out.println(M3.times(M3));
		
		System.out.println("M3 * M4 : ");
		System.out.println(M3.times(M4));
		
		System.out.println("M4 * M1 : ");
		System.out.println(M4.times(M1));
		
		System.out.println("M4 * M2 : ");
		System.out.println(M4.times(M2));
		
		System.out.println("M4 * M3 : ");
		System.out.println(M4.times(M3));
		
		System.out.println("M4 * M4 : ");
		System.out.println(M4.times(M4));
		
		
		
		
	}
}