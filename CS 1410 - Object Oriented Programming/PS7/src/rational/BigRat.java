package rational;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * This constructor and its associated methods build rational numbers from large integer values.
 * @author Robert Weischedel
 *
 */
public class BigRat {
	
    // Representation invariant:
    // den > 0
    // gcd(|num|, den) == 1
    // If num == 0, then den == 1
    private BigInteger num; // Numerator of the Rat
    private BigInteger den; // Denominator of the Rat

    /**
     * Creates the rational number 0
     */
    public BigRat ()
    {
        num = BigInteger.valueOf(0);
        den = BigInteger.valueOf(1); 
    }

    /**
     * Creates the rational number n
     */
    public BigRat (BigInteger n)
    {
        num = n;
        den = BigInteger.valueOf(1);
    }

    /**
     * Creates a rational number by parsing the two parameters into a numerator
     * and a denominator.  Throws a NumberFormatException if the parameters
     * don't parse, and an IllegalArgumentException if d parses to zero.
     */
    public BigRat (String n, String d)
    {
        this(new BigInteger(n), new BigInteger(d));
    }

    /**
     * If d is zero, throws an IllegalArgumentException. Otherwise creates the
     * rational number n/d
     */
    public BigRat (BigInteger n, BigInteger d)
    {
        // Check parameter restriction
        if (d == BigInteger.valueOf(0))
        {
            throw new IllegalArgumentException();
        }

        // Adjust sign of denominator
        num = n;
        den = d;
        if (den.compareTo(BigInteger.valueOf(0)) == -1)
        {
            num = num.multiply(BigInteger.valueOf(-1));
            den = den.multiply(BigInteger.valueOf(-1));
        }

        // Zero has a standard representation
        if (num == BigInteger.valueOf(0))
        {
            den = BigInteger.valueOf(1);
        }

        // Factor out common terms
        BigInteger g = gcd(num.abs(), den);
        num = num.divide(g);
        den = den.divide(g);
    }

    /**
     * Returns the sum of this and r
     */
    public BigRat add (BigRat r)
    {
    	if(this.den != r.den){
    		BigInteger den1;
    		BigInteger num1;
    		BigInteger den2;
    		BigInteger num2;
    			den1 = this.den.multiply(r.den);
    			num1 = this.num.multiply(r.den);
    			den2 = r.den.multiply(this.den);
    			num2 = r.num.multiply(this.den);
    			return new BigRat(num1.add(num2), den1);
    	}
    	else{
    		return new BigRat(this.num.add(r.num), this.den);
    	}
    	
    	//return new BigRat(this.num.multiply(r.den).add(r.num.multiply(this.den)), this.den.multiply(r.den));
    }

    /**
     * Returns the difference of this and r
     */
    public BigRat sub (BigRat r)
    {
    	if(this.den != r.den){
    		BigInteger den1;
    		BigInteger num1;
    		BigInteger den2;
    		BigInteger num2;
    			den1 = this.den.multiply(r.den);
    			num1 = this.num.multiply(r.den);
    			den2 = r.den.multiply(this.den);
    			num2 = r.num.multiply(this.den);
    			return new BigRat(num1.subtract(num2), den1);
    	}
    	else{
    		return new BigRat(this.num.subtract(r.num), this.den);
    	}
    }

    /**
     * Returns the product of this and r
     */
    public BigRat mul (BigRat r)
    {
        return new BigRat(this.num.multiply(r.num), this.den.multiply(r.den));
    }

    /**
     * If r is zero, throws an IllegalArgumentException. Otherwise, returns the
     * quotient of this and r.
     */
    public BigRat div (BigRat r)
    {
    	BigInteger den;
    	BigInteger num;
    	
    	den = this.den.multiply(r.num);
    	num = this.num.multiply(r.den);
    	
        return new BigRat(num, den);
    }

    /**
     * Returns a negative number if this < r, zero if this == r, a positive
     * number if this > r
     */
    public int compareTo (BigRat r)
    {
        BigInteger left = this.num.multiply(r.den);
        BigInteger right = r.num.multiply(this.den);
        return left.compareTo(right);
    }

    /**
     * Returns a string version of this in simplest and lowest terms. Examples:
     * 3/4 => "3/4" 6/8 => "3/4" 2/1 => "2" 0/8 => "0" 3/-4 => "-3/4"
     */
    @Override
    public String toString ()
    {
        if (den.equals(BigInteger.valueOf(1)))
        {
            return "" + num;
        }
        else
        {
            return num + "/" + den;
        }
    }

    /**
     * Converts this to the closest double
     */
    public double toDouble ()
    {
        return num.doubleValue() / den.doubleValue();
    }

    /**
     * Reports whether this == r
     */
    @Override
    public boolean equals (Object o)
    {
        if (o instanceof BigRat)
        {
            BigRat r = (BigRat) o;
            return compareTo(r) == 0;
        }
        else
        {
            return false;
        }

    }

    /**
     * Returns the greatest common divisor of a and b, where a >= 0 and b > 0.
     */
    public static BigInteger gcd (BigInteger a, BigInteger b)
    {
        while (b.compareTo(BigInteger.valueOf(0)) == 1)
        {
            BigInteger remainder = a.mod(b);
            a = b;
            b = remainder;
        }
        return a;
    }

}
