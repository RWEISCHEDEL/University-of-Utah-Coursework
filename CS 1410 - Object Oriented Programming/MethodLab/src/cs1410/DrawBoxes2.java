package cs1410;

public class DrawBoxes2
{
	/**
	 * This method draws three boxes on the console.
	 */
	public static void main (String[] args)
	{
		drawBox();
		System.out.println();
		drawBox();
		System.out.println();
		drawBox();
		System.out.println();
		drawBox();
		System.out.println();
		drawBox();
	}

	/**
	 * Prints a crude ASCII art box to the console.
	 */
	public static void drawBox ()
	{
		System.out.println("+------+");
		System.out.println("|      |");
		System.out.println("|      |");
		System.out.println("+------+");
	}
}
