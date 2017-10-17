package rational;

/**
 * Provides rational number (fraction) objects.
 * @author Joe Zachary and Robert Weischedel
 */
public class Rat
{
    // Representation invariant:
    // den > 0
    // gcd(|num|, den) == 1
    // If num == 0, then den == 1
    private int num; // Numerator of the Rat
    private int den; // Denominator of the Rat

    /**
     * Creates the rational number 0
     */
    public Rat ()
    {
        num = 0;
        den = 1;  
    }

    /**
     * Creates the rational number n
     */
    public Rat (int n)
    {
        num = n;
        den = 1;
    }

    /**
     * Creates a rational number by parsing the two parameters into a numerator
     * and a denominator.  Throws a NumberFormatException if the parameters
     * don't parse, and an IllegalArgumentException if d parses to zero.
     */
    public Rat (String n, String d)
    {
        this(Integer.parseInt(n), Integer.parseInt(d));
    }

    /**
     * If d is zero, throws an IllegalArgumentException. Otherwise creates the
     * rational number n/d
     */
    public Rat (int n, int d)
    {
        // Check parameter restriction
        if (d == 0)
        {
            throw new IllegalArgumentException();
        }

        // Adjust sign of denominator
        num = n;
        den = d;
        if (den < 0)
        {
            num = -num;
            den = -den;
        }

        // Zero has a standard representation
        if (num == 0)
        {
            den = 1;
        }

        // Factor out common terms
        int g = gcd(Math.abs(num), den);
        num = num / g;
        den = den / g;
    }

    /**
     * Returns the sum of this and r
     */
    public Rat add (Rat r)
    {
    	if(this.den != r.den){
    		int den1;
    		int num1;
    		int num2;
    			den1 = this.den * r.den;
    			num1 = this.num *  r.den;
    			num2 = r.num * this.den;
    			return new Rat(num1 + num2, den1);
    	}
    	else{
    		return new Rat(this.num + r.num, this.den);
    	}
    }

    /**
     * Returns the difference of this and r
     */
    public Rat sub (Rat r)
    {
    	if(this.den != r.den){
    		int den1;
    		int num1;
    		int num2;
    			den1 = this.den * r.den;
    			num1 = this.num *  r.den;
    			num2 = r.num * this.den;
    			return new Rat(num1 - num2, den1);
    	}
    	else{
    		return new Rat(this.num - r.num, this.den);
    	}
    }

    /**
     * Returns the product of this and r
     */
    public Rat mul (Rat r)
    {
        return new Rat(this.num * r.num, this.den * r.den);
    }

    /**
     * If r is zero, throws an IllegalArgumentException. Otherwise, returns the
     * quotient of this and r.
     */
    public Rat div (Rat r)
    {
    	int den;
    	int num;
    	
    	den = this.den * r.num;
    	num = this.num * r.den;
    	
        return new Rat(num, den);
    }

    /**
     * Returns a negative number if this < r, zero if this == r, a positive
     * number if this > r
     */
    public int compareTo (Rat r)
    {
        int left = this.num * r.den;
        int right = r.num * this.den;
        return Integer.compare(left, right);
    }

    /**
     * Returns a string version of this in simplest and lowest terms. Examples:
     * 3/4 => "3/4" 6/8 => "3/4" 2/1 => "2" 0/8 => "0" 3/-4 => "-3/4"
     */
    @Override
    public String toString ()
    {
        if (den == 1)
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
        return (double) num / den;
    }

    /**
     * Reports whether this == r
     */
    @Override
    public boolean equals (Object o)
    {
        if (o instanceof Rat)
        {
            Rat r = (Rat) o;
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
    public static int gcd (int a, int b)
    {
        while (b > 0)
        {
            int remainder = a % b;
            a = b;
            b = remainder;
        }
        return a;
    }
}
