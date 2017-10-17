package labB;

import java.util.Scanner;

public class MyLibrary
{
	/**
	 * Returns the number of tokens contained in s
	 */
	public static int countTokens (Scanner s)
	{
		int count = 0;
		while (s.hasNext())
		{
			count++;
			s.next();
		}
		return count;
	}

	/**
	 * If s is empty, throws an IllegalArgumentException. Otherwise, if the
	 * first character of s is an upper case letter, returns the result of
	 * converting s to upper case. Otherwise, returns the result of converting s
	 * to lower case.
	 */
	public static String changeCase (String s)
	{
		if (s.length() == 0)
		{
			throw new IllegalArgumentException();
		}
		else if (Character.isUpperCase(s.charAt(0)))
		{
			return s.toUpperCase();
		}
		else
		{
			return s.toLowerCase();
		}
	}

	/**
	 * If n is negative, throws a RuntimeException. Otherwise, returns n!
	 */
	public static long factorial (int n)
	{
		if (n < 0)
		{
			throw new RuntimeException();
		}
		else
		{
			long fact = 1;
			while (n > 0)
			{
				fact *= n;
				n--;
			}
			return fact;
		}
	}
}
