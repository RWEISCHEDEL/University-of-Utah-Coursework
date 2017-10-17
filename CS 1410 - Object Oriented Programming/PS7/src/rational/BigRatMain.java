package rational;

import java.math.BigInteger;

/**
 * This method tests the BigRat class and a few of its methods. I used this to test and play around with the constructor
 * to ensure it was working correctly. (Not necessary) 
 * @author Robert Weischedel
 *
 */
public class BigRatMain {
	
	public static void main (String[] args){
	
	BigInteger nummer = new BigInteger("1");
	BigInteger dummer = new BigInteger("200000000000000000000");
	BigInteger dummer1 = new BigInteger("200000000000000000000");
	BigInteger nummer1 = new BigInteger("15");
	BigInteger num1 = new BigInteger("15");
	BigInteger dum1 = new BigInteger("15");
	
	BigRat r1 = new BigRat(nummer, nummer1);
	BigRat r2 = new BigRat(nummer1, dummer1);
	
	System.out.println(r1.add(r1));
	System.out.println(r1.toDouble());
	}

}
