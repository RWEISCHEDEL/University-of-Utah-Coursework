package cs1410;

public class StopSigns2 {
	public static void main (String[] args){
		printSign();
		printStopSign();
		printSign();
		printStopSign();
		printSign();	
	}
	
	// Method to make the regular sign
	public static void printSign (){
		printTop();
		printBottom();
	}
	
	// Method to make the stop sign
	public static void printStopSign (){
		printTop();
		printMiddle();
		printBottom();	
	}
	
	// Prints the top of the sign
	public static void printTop (){
		System.out.println("  ______  ");
		System.out.println(" /      \\ ");
		System.out.println("/        \\");
	}
	
	// Prints the Middle and STOP portion of the sign
	public static void printMiddle (){
		System.out.println("|  STOP  |");
	}
	
	
	//Prints the bottom of the sign and also includes a line for space
	public static void printBottom (){
		System.out.println("\\        /");
		System.out.println(" \\______/ ");
		System.out.println();
	}
}
