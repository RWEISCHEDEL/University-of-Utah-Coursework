// This specifies in which package (folder) the file lives
package cs1410;

// When your code refers to classes from other packages, you must 
// import those packages.
import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Up to this point in the course, we have created classes only as
 * containers for static methods and constants.  That's about to
 * change, and in a big way.
 */
public class Midterm {
	
	/**
	 * When a Java program is run, execution starts with the main
	 * method from some class.  The header of the method must be as
	 * below, or Java won't recognize the method as a main method.
	 */
	public static void main (String[] args) {	
		double d = Math.sqrt(8);
	}
	
	
	/**
	 * This method illustrates the four primitive types that
	 * we've been using.  Each primitive type has a name and
	 * a way to directly write the possible values as literals. 
	 */
	public static void primitiveTypes () {
		
		// The int type models the mathematical integers.  However,
		// they have a restricted range, from roughly -2 billion to
		// +2 billion.
		int x = 5;
		
		// The double type models the real numbers.  However, they too
		// have a restricted range.  There are limits on the maximum 
		// exponent, the minimum exponent, and the number of digits in
		// the mantissa.
		double d = 1.2e-7;
		
		// The bool type provides the two truth values true and false.
		boolean b = false;
		
		// The char type provides characters from various alphabets, coded
		// using the Unicode standard.
		char c = 'H';
	}
	
	
	/**
	 * This method illustrates the operators that can be used to compute
	 * with primitive values.
	 */
	public static void operators () {
		
		// Int computations
		int n = 2;
		int m = 5;
		int add = n + m;
		int sub = n - m;
		int mul = n * m;
		int div = n / m;
		int mod = n % m;
		
		// Double computations
		double x = 1.3e-3;
		double y = 3.14159;
		double _add = x + y;
		double _sub = x - y;
		double _mul = x * y;
		double _div = x / y;
		
		// Boolean computations
		boolean a = true;
		boolean b = false;
		boolean and = a && b;
		boolean or = a || b;
		boolean not = !b;   
		
		// Comparisons work with ints, doubles, and chars
		char c1 = 'A';
		char c2 = 'B';
		b = x >= y;
		b = n <= m;
		b = c1 > c2;
		b = y < x;
		b = n == m;
		b = n != m;

	}
	
	/**
	 * Objects can be created by calling constructors (although strings
	 * have literals).  Programmers can define new types of objects,
	 * but not new types of primitives.
	 */
	public static void objects () {	
		String s = "Hello world";
		Scanner scan = new Scanner("This is a test");
		File f = new File("c:/example.txt");
		Random r = new Random();
	}
	
	/**
	 * Exceptions can be caught (NumberFormatException) or allowed to 
	 * propagate (FileNotFoundException).  Try blocks can be used
	 * to catch exceptions or to automatically close resources.
	 */
	public static int exceptions () throws FileNotFoundException {
		int sum = 0;
		try (Scanner s = new Scanner(new File("c:/example.txt"))) {
			while (s.hasNext()) {
				try {
				sum += Integer.parseInt(s.next());
				}
				catch (NumberFormatException e) {
				}
			}
		}
		return sum;
	}
	
	
	/**
	 * This method illustrates expressions.  An expressions is a piece of
	 * code that has a value.
	 */
	public static void expressions () {
		
		// Literals
		int n = 2;
		String s = "Hello";
		
		// Constructors
		Random rand = new Random();
		Scanner scan = new Scanner("This is a test"); 
		
		// Variables
		int m = n;
		String r = s;
		
		// Constants
		double d = Math.PI;
		
		// Operator expressions
		m = n * n;
		r = s + s;
		
		// Static method calls (that return values)
		d = Math.sqrt(13.2);
		d = Math.cos(2.91);
		
		// Non-static method calls (that return values)
		n = rand.nextInt();
		boolean b = scan.hasNext();
		s = scan.next();
		
		// Expressions can be compounded as long as type
		// restrictions are respected.
		b = rand.nextInt() * n > Math.sqrt(Math.PI);
			
	}
	
	
	/**
	 * This method illustrates statements.  A statement is a piece of code
	 * that has a side effect.
	 */
	public static boolean statements () {
		
		// Declarations
		int n = 7;
		double d = 1.5e7;
		
		// Assignments
		n = n + 22;
		d = Math.sqrt(d+d);
		
		// Method calls
		System.out.println("Hello world");
		
		// Conditionals
		if (n < 13) {
			System.out.println("Young");
		}
		else if (n > 19) {
			System.out.println("Old");
		}
		else {
			System.out.println("Teen");
		}
		
		// While loops
		Scanner s = new Scanner("This is a test");
		int length = 0;
		while (s.hasNext()) {
			length = length + s.next().length();
		}
		
		// Returns
		return true;
	}
	
	
	/**
	 * This method is called average.  It has two
	 * formal parameters, x and y, both doubles.  It
	 * returns an integer approximation to their
	 * average.
	 */
	public static double average (double x, double y) {
		double avg = (x + y) / 2;
		return avg;
	}
	
	
	/**
	 * Prints out the contents of the Scanner.  This is an
	 * example of a counting loop.
	 */
	public void printScanner (Scanner s) {
		while (s.hasNext()) {
			System.out.println(s.next());
		}
	}
	
	
	/**
	 * Returns whether or not s contains a digit.  This is an
	 * example of a searching loop.
	 */
	public boolean hasDigit (String s) {
		int i = 0;
		while (i < s.length()) {
			if (Character.isDigit(s.charAt(i))) {
				return true;
			}
			i++;
		}
		return false;
	}
	
	
	/**
	 * Returns the sum of all the integers in the Scanner, under
	 * the assumption that it contains nothing but integers.  This is
	 * an example of an accumulation loop.
	 */
	public int sum (Scanner s) {
		int sum = 0;
		while (s.hasNextInt()) {
			sum = sum + s.nextInt();
		}
		return sum;
	}


	/**
	 * Returns the character from s that occurs last in lexicographic
	 * order, under the assumption that it contains at least one
	 * character.  This is an example of an optimization loop.
	 */
	public char lastChar (String s) {
		char last = s.charAt(0);
		int i = 1;
		while (i < s.length()) {
			if (s.charAt(i) > last) {
				last = s.charAt(i);
			}
			i++;
		}
		return last;
	}
		

}
