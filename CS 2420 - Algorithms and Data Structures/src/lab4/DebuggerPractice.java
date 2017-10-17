package lab4;

public class DebuggerPractice {


	public static void main(String[] args) {

		int[] arr1 = new int[] {1, 2, 3, 4, 5};
		int[] arr2 = new int[] {5, 10, 15, 20, 25}; 
		
		
		System.out.println("Welcome to lab 4");
		
		
		
		// Part 1
		
		/*if(Part1.binarySearch(arr1, 2) == false)
			System.out.println("Binary search is broken");
		if(Part1.binarySearch(arr1, 0) == true)
			System.out.println("Binary search is broken");
		if(Part1.binarySearch(arr2, 20) == false)
			System.out.println("Binary search is broken");
		if(Part1.binarySearch(arr2, 18) == true)
			System.out.println("Binary search is broken");*/
		 


		

		// Part 2
		//Uncomment this line when instructed to in the lab web page
//		Part2.infiniteLoops();



		// Part 3
		//Uncomment this block of code when instructed to in the lab web page
		
		if(Part3.checkPassword("1 4 9 16 25")) {
			System.out.println("Password accepted");
		} else {
			System.out.println("Incorrect password");
		}
		
	}

}