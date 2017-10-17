package labA;

public class MyMath
{
	/**
	 * If there are no parameters, throws an IllegalArgumentException.
	 * Otherwise, returns the average of its parameters.
	 */
	public static double average (double... numbers)
	{
		if (numbers.length == 0)
		{
			throw new IllegalArgumentException();
		}
		double sum = 0;
		for (int i = 0; i < numbers.length; i++)
		{
			sum += numbers[i];
		}
		return sum / numbers.length;
	}

}
