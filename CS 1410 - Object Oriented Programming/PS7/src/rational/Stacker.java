package rational;

import java.math.BigInteger;

/**
 * This method solves the block solving problem that was mentioned in class. It uses the BigRat class to keep adding the correct
 * fraction values till it reaches or exceeds the extention value.
 * If extension = 5, excepect about 3 mins to recieve answer. Values 0-4 take significantly less time to complete. 
 * @author Robert Weischedel
 *
 */
public class Stacker {
	public static void main (String[] args){
		int extension = 1;
		System.out.println("With extension size = " + extension);
		System.out.println("The answer is = " + stackSize(extension));
	
	}
	
	/**
	 * Takes in the extension and calculates the correct stack size based on it. 
	 * @param int extension
	 * @return n - long
	 */
	public static long stackSize(int extension){
		long n = 0;
		int factor = 2;
		
		// Create a BigRat to start the loop
		BigRat sumVal = new BigRat(new BigInteger("0"), new BigInteger("1"));
		BigRat testVal = new BigRat(new BigInteger("" + extension), new BigInteger("1"));
		
		while(sumVal.compareTo(testVal) <= 0){
			n++;
			BigRat addVal = new BigRat(new BigInteger("1"), new BigInteger("" + n * factor));
			sumVal = sumVal.add(addVal);
		}
		
		return n;
		
	}

}
