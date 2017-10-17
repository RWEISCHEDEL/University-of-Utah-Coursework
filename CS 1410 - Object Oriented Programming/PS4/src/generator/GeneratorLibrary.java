package generator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Library of methods used for random text generation
 * 
 * @author Joe Zachary and Robert Weischedel
 */
public class GeneratorLibrary {
	/**
	 * Returns the contents of a plain text file as a string. Throws an
	 * IOException if the file can't be read or if any other problem is
	 * encountered.
	 */
	public static String fileToString(File file) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		return new String(encoded, StandardCharsets.UTF_8);
	}

	/**
	 * Randomly generates and returns a string using the algorithm sketched in
	 * P4. The parameters are the source text, the generation level, and the
	 * length of the string to be generated.
	 * 
	 * If level >= the length of the text, or if level is negative, throws an
	 * IllegalArgumentException.
	 */
	public static String generateText(String text, int level, int length) {
		// Make sure parameters are valid
		if (text.length() <= level || level < 0) {
			throw new IllegalArgumentException();
		}

		// This random number generator is used throughout as a source
		// of randomness
		Random rand = new Random();

		// Pick the initial random seed
		String seed = pickRandomSeed(text, level, rand);

		// Compose the string
		String result = "";
		while (result.length() < length) {
			// Find out how many times the seed occurs inside the text
			int count = countTargetOccurrences(text, seed);

			// If there are no occurrences, pick a new seed
			if (count == 0) {
				seed = pickRandomSeed(text, level, rand);
			}

			// Otherwise, advance the text generation by one character
			else {
				int n = rand.nextInt(count);
				char c = getCharAfterNthOccurrence(text, n + 1, seed);
				seed = (seed + c).substring(1);
				result += c;
			}
		}

		// When the result is long enough, return it
		return result;
	}

	/**
	 * Returns a substring of text that contains length characters, beginning at
	 * an index chosen randomly using rand. If there are not length characters
	 * in text, throws an IllegalArgumentException.
	 */
	public static String pickRandomSeed(String text, int length, Random rand) {
		int index = rand.nextInt(text.length() - (length - 1));
		return text.substring(index, index + length);
	}

	/**
	 * Let n be the length of text.
	 * 
	 * If the length of target is >= n, throws an IllegalArgumentException
	 * 
	 * Otherwise, if the length of target is 0, returns n
	 * 
	 * Otherwise, returns the number of times that target appears in the first
	 * n-1 characters of text
	 */
	public static int countTargetOccurrences(String text, String target) {
		int lengthOfText = text.length();
		int lengthOfTarget = target.length();
		int numOfOccurances = 0;
		int targetLocation;

		if (lengthOfTarget >= lengthOfText) {
			throw new IllegalArgumentException();
		}

		if (lengthOfTarget == 0) {
			return lengthOfText;
		}

		int countText = 0;

		while (countText < lengthOfText - 2) {

			targetLocation = text.indexOf(target, countText);

			if (targetLocation >= 0) {
				numOfOccurances++;
				countText = targetLocation + 1;
			}

			else {
				break;
			}

		}
		return numOfOccurances;
	}

	/**
	 * If n is not positive, throws an IllegalArgumentException.
	 * 
	 * Otherwise, returns the character that follows the nth occurrence of the
	 * target inside the text.
	 * 
	 * If the target does not occur n times within the text, or if the nth
	 * occurrence is not followed by a character, throws NoSuchElementException.
	 */
	public static char getCharAfterNthOccurrence(String text, int n,
			String target) {
		int lengthOfText = text.length();
		int lengthOfTarget = target.length();
		int numOfOccurances = 0;
		int targetLocation;
		char charAfterNthTime = ' ';

		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		int countText = 0;
		while (countText < lengthOfText) {

			targetLocation = text.indexOf(target, countText);

			if (targetLocation >= 0) {
				numOfOccurances++;
				if (numOfOccurances == n) {
					if (text.indexOf(target, targetLocation) + lengthOfTarget == lengthOfText) {
						throw new NoSuchElementException();
					}
					charAfterNthTime = text.charAt(targetLocation
							+ lengthOfTarget);
					break;
				}

				countText = targetLocation + 1;
			}

			else {
				break;
			}

		}

		if (numOfOccurances != n) {
			throw new NoSuchElementException();
		}

		return charAfterNthTime;

	}
}
