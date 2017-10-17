package cs1410;

import java.util.Scanner;

/**
 * A collection of methods for the second assignment of CS 1410.
 * 
 * @author Robert Weischedel
 */
public class MethodLibrary {
	/**
	 * You can use this main method for experimenting with your methods if you
	 * like, but it is not part of the assignment.
	 */
	public static void main(String[] args) {
	}

	/**
	 * Returns the string that results from capitalizing the first character of
	 * word, which is required to have at least one character. For example,
	 * capitalize("hello") is "Hello" and capitalize("Jack") is "Jack".
	 */
	public static String capitalize(String word) {

		char firstChar = word.charAt(0);
		String cutWord = word.substring(1);
		String capChar = firstChar + "";
		capChar = capChar.toUpperCase();
		String finalWord = capChar + cutWord;
		return finalWord;
	}

	/**
	 * Reports whether or not c is a vowel ('a', 'e', 'i', 'o', 'u' or an
	 * upper-case version).
	 */
	public static boolean isVowel(char c) {
		// Convert char to string
		String letterToString = "" + c;
		// Convert string to uppercase
		letterToString = letterToString.toUpperCase();
		char vowelCheck = letterToString.charAt(0);
		if ((vowelCheck == 'A') || (vowelCheck == 'E') || (vowelCheck == 'I')
				|| (vowelCheck == 'O') || (vowelCheck == 'U')) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the index within s of the first vowel ('a', 'e', 'i', 'o', 'u' or
	 * an upper-case version) that occurs in s. If there is no vowel in s,
	 * returns -1.
	 * 
	 * For example, the method should return 0 for "Apple", 1 for "hello", 2 for
	 * "slope", 3 for "strength", and -1 for "xyzzy".
	 */
	public static int firstVowelIndex(String s) {
		// NOTE: This method is completely implemented. There is no need
		// for you to change anything.
		int i = 0;
		while (i < s.length()) {
			if (isVowel(s.charAt(i))) {
				return i;
			}
			i = i + 1;
		}
		return -1;
	}

	/**
	 * Returns the result of converting s to "Pig Latin". Convert a string s to
	 * Pig Latin by using the following rules:
	 * 
	 * (1) If s contains no vowels, do nothing to it.
	 * 
	 * (2) Otherwise, if s starts with a vowel, append "way" to the end.
	 * 
	 * (3) Othewise, move everything up to (but not including) the first vowel
	 * to the end and add "ay".
	 * 
	 * For example, "hello" converts to "ellohay", "small" converts to
	 * "allsmay", "eat" converts to "eatway", and "nth" converts to "nthway".
	 * 
	 * IMPLEMENTATION NOTE: For full credit (and an easier implementation), use
	 * the firstVowelIndex method provided above in your method's
	 * implementation. You will also find the substring method available on
	 * String objects very useful.
	 */
	public static String toPigLatin(String s) {
		// String variable declarations, pigLatin for final word, pigLaitnFH for
		// first half and pigLatinSH for second half
		String pigLatin = "";
		String pigLatinFH = "";
		String pigLatinSH = "";
		int vowelLocation = firstVowelIndex(s);
		if (vowelLocation < 0) {
			return s;
		} else if (vowelLocation == 0) {
			pigLatin = s + "way";
			return pigLatin;
		} else {
			pigLatinFH = s.substring(0, vowelLocation);
			pigLatinSH = s.substring(vowelLocation);

			pigLatin = pigLatinSH + pigLatinFH + "ay";

			return pigLatin;
		}

	}

	/**
	 * Returns the result of converting each word from words into Pig Latin and
	 * then appending the results, with each converted word followed by a single
	 * space character. A word is a sequence of characters separated from other
	 * words by white space. If there are no words, returns the empty string.
	 * 
	 * For example, "" converts to "" and "This is a test" converts to
	 * "isThay isway away estay ".
	 */
	public static String convertToPigLatin(String words) {
		String englishWord = "";
		String pigLatinWord = "";
		String pigLatinSentence = "";
		Scanner input = new Scanner(words);
		while (input.hasNext()) {
			englishWord = input.next();
			pigLatinWord = toPigLatin(englishWord);
			pigLatinSentence = pigLatinSentence + pigLatinWord + " ";
		}
		input.close();
		return pigLatinSentence;
	}

	/**
	 * Returns a String of length width that begins and ends with the character
	 * edge and contains width-2 copies of the character inner in between. For
	 * example, if edge is '+', inner is '-', and width is 8, the method should
	 * return "+------+".
	 * 
	 * The method does not print anything. The parameter width must be greater
	 * than or equal to 2.
	 */
	public static String makeLine(char edge, char inner, int width) {
		String line = edge + "";
		String middle = inner + "";
		int countWidth = 1;
		while (countWidth < width - 1) {
			line = line + middle;
			countWidth = countWidth + 1;
		}
		line = line + edge + "";
		return line;
	}

	/**
	 * Returns a string which, when printed out, will be a rectangle shaped like
	 * this:
	 * 
	 * <pre>
	 * +----------+
	 * |          |
	 * |          |
	 * |          |
	 * |          |
	 * |          |
	 * +----------+
	 * </pre>
	 * 
	 * The returned string should consist of height lines, each ending with a
	 * newline. In addition to the newline, the first and last lines should
	 * begin and end with '+' and should contain width-2 '-' symbols. In
	 * addition to the newline, the other lines should begin and end with '|'
	 * and should contain width-2 spaces.
	 * 
	 * The method does not print anything. Both parameters must be greater than
	 * or equal to 2.
	 * 
	 * IMPLEMENTATION NOTE: For full credit (and for easier implementation),
	 * make use of your makeLine method in your implementation of makeRectangle.
	 * 
	 */
	public static String makeRectangle(int height, int width) {
		int heightCounter = 0;
		String completedSquare = "";
		// String to hold the middle portion of the square | |
		String completedMiddle = "";

		String topAndBottom = makeLine('+', '-', width) + "\n";
		String middle = makeLine('|', ' ', width) + "\n";

		while (heightCounter < height - 2) {
			completedMiddle = completedMiddle + middle;
			heightCounter++;
		}
		completedSquare = topAndBottom + completedMiddle + topAndBottom;
		return completedSquare;
	}

	/**
	 * Returns the integer that follows n in a Hailstone sequence. If n is 1,
	 * returns 1. Otherwise, returns either n/2 (if n is even) or 3n+1 (if n is
	 * odd). The parameter n must be positive.
	 */
	public static int nextHailstone(int n) {
		if (n == 1) {
			return 1;
		} else if (n % 2 == 0) {
			n = n / 2;
			return n;
		} else {
			n = (3 * n) + 1;
			return n;
		}

	}

	/**
	 * Returns a string consisting of a Hailstone sequence beginning with the
	 * positive integer n and ending with 1. The string should consist of a
	 * sequence of numerals, with each numeral followed by a single space. When
	 * a numeral m (other than 1) appears in the sequence, it should be followed
	 * by nextHailstone(m).
	 * 
	 * For example, nextHailstone(1) should return "1 " and nextHailstone(5)
	 * should return "5 16 8 4 2 1 ".
	 */
	public static String hailstones(int n) {
		String answer = "";
		answer = n + " ";
		while (n > 1) {
			n = nextHailstone(n);
			answer = answer + n + " ";
		}
		return answer;
	}

	/**
	 * Returns the number of words from the words parameter that have at least
	 * min but no more than max characters. Words in the string are delimited by
	 * white space. For example,
	 * countWords("Now is the time for all good people", 4, 6) should return 2.
	 */
	public static int countWords(String words, int min, int max) {
		String wordHolder = "";
		int counter = 0;
		Scanner s = new Scanner(words);
		while (s.hasNext()) {
			wordHolder = s.next();
			if ((wordHolder.length() >= min) && (wordHolder.length() <= max)) {
				counter = counter + 1;
			}
		}
		s.close();

		return counter;
	}

	/**
	 * Returns the string that results from stripping all of the white space
	 * from text.
	 */
	public static String stripWhiteSpace(String text) {
		String noWhiteSpace = "";
		Scanner s = new Scanner(text);
		while (s.hasNext()) {
			noWhiteSpace = noWhiteSpace + s.next();
		}
		s.close();
		return noWhiteSpace;

	}
}
