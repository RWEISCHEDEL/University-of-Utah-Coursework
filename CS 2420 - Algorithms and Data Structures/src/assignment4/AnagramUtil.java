package assignment4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * This class contains several methods to bring in and compare various STring arrays and files to check if the strings they contain are
 * anagrams of each other. The methods contained are as follows : sort, insertion sort, areAnagrams, and two getLargestAnagramGroup 
 * methods each with their own different inputs. 
 * @author Robert Weischedel && Makenzie Elliott
 *Assignment 4
 *CS 2420
 */
public class AnagramUtil {


	/**
	 * This method returns the sorted version of the input string. The sorting
	 * must be accomplished using an insertion sort.
	 * 
	 * @param s - A String to be sorted
	 * @return - a sorted string by character
	 */
	public static String sort(String word) {

		// If word is null, return null
		if (word == null) {
			return null;
		}

		// Variable to store returned value
		String sortedString = "";
		
		// Convert the brought in word to lower case
		String lowerCase = word.toLowerCase();

		// Create an array to store each character of the word in. And then fill it with the lower case version
		Character[] charArray = new Character[word.length()];

		for (int i = 0; i < lowerCase.length(); i++) {
			charArray[i] = lowerCase.charAt(i);
		}

		// Create a Comparator to use in the insertion sort method
		class sortChars implements Comparator<Character> {

			@Override
			public int compare(Character o1, Character o2) {
				return o1.compareTo(o2);
			}

		}
		
		// Create an instance of the comparator
		sortChars comp = new sortChars();
		
		// Call insertion sort
		insertionSort(charArray, comp);
		
		// Place the sorted string into a new string
		for (int i = 0; i < word.length(); i++) {
			sortedString += charArray[i];
		}

		return sortedString;
	}

	/**
	 * This generic method sorts the input array using an insertion sort and the
	 * input Comparator object.
	 * 
	 * @param t - A generic array of any type
	 * @param comp - A Comparator of the type of the array
	 */
	public static <T> void insertionSort(T[] t, Comparator<? super T> comp) {

		// This is a base standard insertion sort, ex
		int j;
		T index;
		for (int i = 1; i < t.length; i++) {
			index = t[i];
			j = i;
			while (j > 0 && comp.compare(t[j - 1], index) > 0) {
				t[j] = t[j - 1];
				j--;
			}
			t[j] = index;
		}

	}

	/**
	 * This method returns true if the two input strings are anagrams of each
	 * other, otherwise returns false.
	 * 
	 * @param s - String 
	 * @param t - String
	 * @return boolean if the strings are anagrams of each other
	 */
	public static boolean areAnagrams(String s, String t) {
		
		// If either string is null, return null.
		if (s == null || t == null) {
			return false;
		}

		// Sort both strings by characters, by calling the sort method
		String sortedS = sort(s);
		String sortedT = sort(t);

		// Check and see if the sorted strings are equal to each other
		if (sortedS.equals(sortedT)) {
			return true;
		}
		return false;

	}

	/**
	 * This method returns the largest group of anagrams in the input array of
	 * words, in no particular order. It returns an empty array if there are no
	 * anagrams in the input array.
	 * 
	 * @param arr - Array of Strings
	 * @return - And array Of Strings that are anagrams of each other
	 */
	public static String[] getLargestAnagramGroup(String[] arr) {

		if (arr.length == 0) {
			return arr;
		}

		// Create a new array
		String[] copy = new String[arr.length];

		for (int i = 0; i < copy.length; i++) {
			copy[i] = arr[i];
		}

		// convert everything to lower case
		// sort each string individually
		for (int i = 0; i < copy.length; i++) {
			copy[i] = (String) sort(copy[i].toLowerCase());
		}

		// creates a comparator for strings
		class compareStrings implements Comparator<String> {

			@Override
			public int compare(String o1, String o2) {

				return o1.compareTo(o2);
			}
		}

		// initialize the comparator
		compareStrings compString = new compareStrings();

		// sorting the list into order from a to z
		insertionSort(copy, compString);

		// temporary array to hold how many instances of each anagram
		ArrayList<Integer> temp = new ArrayList<Integer>();

		// something to compare to
		String check = copy[0];

		// keeps track of how many of each anagram
		int numOfValues = 0;

		// goes through and counts how many of each anagram and then adds that
		// number
		// to the array list. The list is sorted so when the next element is not
		// the same
		// then it clears the number of values and then starts the count again.
		for (int i = 0; i < copy.length; i++) {
			if (copy[i].equals(check)) {
				numOfValues++;
			} else {
				temp.add(numOfValues);
				check = copy[i];
				numOfValues = 1;
			}
		}

		// adds the last value to the list
		temp.add(numOfValues);

		// something to compare to
		int largestSoFar = temp.get(0);

		// finds the largest number of anagrams in the array of numbers
		for (int i = 1; i < temp.size(); i++) {
			if (temp.get(i) > largestSoFar) {
				largestSoFar = temp.get(i);
			}
		}

		// go back through the list and return the anagrams

		// the sum will find the right anagram in the original list
		int sum = 0;

		//
		for (int i = 0; i <= temp.indexOf(largestSoFar); i++) {
			sum += temp.get(i);
		}

		// the value is the anagram that occurs the most.
		// the sum is the position where the largest group of anagrams should be
		// in the copy list
		// the copy list is sorted alphabetically by character and by strings.
		// if you add the amount of times each anagram occurs and end at the
		// largest occurence
		// then it equals then the sum is equal to the index of where the
		// anagram is located in
		// the copy list.
		String value = copy[sum - 1];

		// create a new list of anagrams to return that has a size equal to the
		// number of occurences
		// of the largest group of anagrams
		String[] largestAn = new String[largestSoFar];

		// count keeps track of the index for the new list of anagrams
		int count = 0;
		// fill the new array with the anagrams from the original list so that
		// they are returned in their
		// original state.
		for (int i = 0; i < arr.length; i++) {
			// if the value that was found to have the most occurences is an
			// anagram of anything
			// in the original list, then add it to the new list
			if (areAnagrams(arr[i], value)) {
				largestAn[count] = arr[i];
				count++;
			}
		}

		// Return the array
		return largestAn;

	}

	/**
	 * Behaves the same as the previous method, but reads the list of words from
	 * the input filename. It is assumed that the file contains one word per
	 * line. If the file does not exist or is empty, the method returns an empty
	 * array because there are no anagrams.
	 * 
	 * @param filename - a file of words to check and see which are anagrams of each other
	 * @return - And array Of Strings that are anagrams of each other
	 */
	public static String[] getLargestAnagramGroup(String filename) {
		
		// Create a new file with the given filename path name
		File file = new File(filename);
		
		// Create an Arraylist to pull the information from the Scanner
		ArrayList<String> temp = new ArrayList<String>();
		
		// Create a Scanner to pull info from the File
		Scanner anagramFile;
		
		// Save the information from the file into the Scanner
		try {
			anagramFile = new Scanner(file);
			
			while(anagramFile.hasNext()){
				temp.add(anagramFile.next());
			}
			// Once finished with the Scanner close it
			anagramFile.close();
		} catch (FileNotFoundException e) {
			String[] empty = new String[0];
			return empty;
		}
		
		// Create an array to store the information from the ArrayList
		String[] anagramGroup = new String[temp.size()];
		
		// Place all the information from the ArrayList into the Array
		for(int i = 0; i < temp.size(); i++){
			anagramGroup[i] = temp.get(i);
		}
		
		// Call the getLargestAnagramGroup method to do the work
		String[] largestAnagram = getLargestAnagramGroup(anagramGroup);
		
		// Return the array
		return largestAnagram;

	}

}
