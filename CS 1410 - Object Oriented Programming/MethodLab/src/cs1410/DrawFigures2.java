package cs1410;

/**
 * A version of DrawFigures1 that uses parameterless, void methods to organize
 * the code.
 */
public class DrawFigures2
{
	/**
	 * Prints out a diamond, an X, and a rocket.
	 */
	public static void main (String[] args)
	{

		printDiamond();
		System.out.println();
		printX();
		System.out.println();
		printRocket();
	}
	
	/**
	 * Prints a V shape.
	 */
	public static void printV ()
	{
		System.out.println(" \\    /");
		System.out.println("  \\  /");
		System.out.println("   \\/");
	}

	/**
	 * Prints a ^ shape.
	 */
	public static void printCaret ()
	{
		System.out.println("   /\\");
		System.out.println("  /  \\");
		System.out.println(" /    \\");
	}

	/**
	 * Prints a diamond
	 */
	public static void printDiamond ()
	{
		// Use methods you have implemented, not println
		printCaret();
		printV();
	}

	/*
	 * Prints an X.
	 */
	public static void printX ()
	{
		// Use methods you have implemented, not println
		printV();
		printCaret();
	}
	
	/*
	 * Prints a rocket label
	 */
	public static void printRocketLabel (String label)
	{
		System.out.println("|" + label + "|");
		
	}
	
	/*
	 * Prints a rocket.
	 */
	public static void printRocket ()
	{
		printCaret();
		DrawBoxes2.drawBox();
		printRocketLabel("United");
		printRocketLabel("States");
		DrawBoxes2.drawBox();
		printCaret();
	}
}
