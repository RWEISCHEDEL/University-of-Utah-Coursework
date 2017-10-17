package cs1410;

/**
 * Lab exercise for CS 1410
 * 
 * @author Joe Zachary
 */
public class Triangles
{
	/**
	 * Draws ten triangles
	 */
	public static void main (String[] args)
	{
		int size = 1;
		while (size <= 10)
		{
			drawTriangle(size);
			System.out.println();
			size = size + 1;
		}
	}

	/**
	 * Draws a triangle of the specified size
	 */
	public static void drawTriangle (int size)
	{
		int i = 1;
		while (i <= size)
		{
			drawLine(size - i, 2 * i - 1, '*');
			i = i + 1;
		}
	}

	/**
	 * Draws a line that begins with the specified amount of white space
	 * padding, followed by size copies of the symbol, followed by a newline.
	 */
	public static void drawLine (int padding, int size, char symbol)
	{
		int i = 0;
		while (i < padding)
		{
			System.out.print(' ');
			i = i + 1;
		}

		int j = 0;
		while (j < size)
		{
			System.out.print(symbol);
			j = j + 1;
		}

		System.out.println();
	}
}
