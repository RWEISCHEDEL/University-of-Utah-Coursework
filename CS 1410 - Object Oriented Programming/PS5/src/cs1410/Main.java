package cs1410;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import graphics.GraphWindow;

public class Main
{
	/**
	 * @author Robert Weischedel, CS 1410
	 * The main method is used to check the pie and bar graphs for accuracy, because JUnit can't check Graphics Objects.
	 * Each testSenario tests a different, kind of situation for the program to handle, like for each operation, each graph and for different amounts of search data.
	 * If you do not wish to see all the graphs at once, please comment the sections you don't wish to see.
	 */
	public static void main (String[] args)
	{
		//These tests test for finding three different items in column 1, or there are three things in categories.
		// Data Used : States Example Data
    	testSenario1();
	    testSenario2();
		testSenario3();
        testSenario4();
		testSenario5();
		testSenario6();
		
		//These tests test for finding two different items in column 1, or there are two things in categories.
		//Data Used : Colleges Data
		testSenario7();
		testSenario8();
		testSenario9();
		testSenario10();
		testSenario11();
		testSenario12();
		
		//These test for finding four different items in column 1, or there are four things in categories.
		//Data Used : Colleges Data
		testSenario13();
		testSenario14();
		testSenario15();
		testSenario16();
		testSenario17();
		testSenario18();
		
		// These test for finding one item in column 1, or there is one thing in categories.
		// Data Used : States Example Data
		testSenario19();
		testSenario20();
		testSenario21();
		testSenario22();
		testSenario23();
		testSenario24();
		
		
		
	}
	
	/**
	 * This test : if operation == 0, we have three items in categories, and we want a Pie Chart
	 */
	public static void testSenario1(){
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "Nevada", "California" };
		Color[] colors = { Color.red, Color.blue, Color.green };
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 0, true);
		
	}
	
	/**
	 * This test : if operation == 0, we have three items in categories, and we want a Bar Graph
	 */
	public static void testSenario2(){
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "Nevada", "California" };
		Color[] colors = { Color.red, Color.blue, Color.green };
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 0, false);
		
	}
	
	/**
	 * This test : if operation == 1, we have three items in categories, and we want a Pie Chart
	 */
	public static void testSenario3(){
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "Nevada", "California" };
		Color[] colors = { Color.red, Color.blue, Color.green };
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 1, true);
		
	}
	
	/**
	 * This test : if operation == 1, we have three items in categories, and we want a Bar Graph
	 */
	public static void testSenario4(){
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "Nevada", "California" };
		Color[] colors = { Color.red, Color.blue, Color.green };
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 1, false);
		
	}
	
	/**
	 * This test : if operation == 2, we have three items in categories, and we want a Pie Chart
	 */
	public static void testSenario5(){
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "Nevada", "California" };
		Color[] colors = { Color.red, Color.blue, Color.green };
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 2, true);
		
	}
	
	/**
	 * This test if operation == 2, we have three items in categories, and we want a Bar Graph
	 */
	public static void testSenario6(){
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "Nevada", "California" };
		Color[] colors = { Color.red, Color.blue, Color.green };
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 0, false);
		
	}
	
	/**
	 * This test if : operation == 0, we have two items in categories, and we want a Pie Chart
	 */
	public static void testSenario7(){
		String[] column1 = { "Utah", "Weber", "BYU", "UVU", "Utah", "BYU", "Weber", "Weber", "UVU", "BYU" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "BYU" };
		Color[] colors = { Color.red, Color.blue};
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 0, true);
		
	}
	
	/**
	 * This test if : operation == 0, we have two items in categories, and we want a Bar Graph
	 */
	public static void testSenario8(){
		String[] column1 = { "Utah", "Weber", "BYU", "UVU", "Utah", "BYU", "Weber", "Weber", "UVU", "BYU" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "BYU" };
		Color[] colors = { Color.red, Color.blue};
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 0, false);
		
	}
	
	/**
	 * This test if : operation == 1, we have two items in categories, and we want a Pie Chart
	 */
	public static void testSenario9(){
		String[] column1 = { "Utah", "Weber", "BYU", "UVU", "Utah", "BYU", "Weber", "Weber", "UVU", "BYU" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "BYU" };
		Color[] colors = { Color.red, Color.blue};
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 1, true);
		
	}
	
	/**
	 * This test if : operation == 1, we have two items in categories, and we want a Bar Graph
	 */
	public static void testSenario10(){
		String[] column1 = { "Utah", "Weber", "BYU", "UVU", "Utah", "BYU", "Weber", "Weber", "UVU", "BYU" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "BYU" };
		Color[] colors = { Color.red, Color.blue};
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 1, false);
		
	}
	
	/**
	 * This test if : operation == 2, we have two items in categories, and we want a Pie Chart
	 */
	public static void testSenario11(){
		String[] column1 = { "Utah", "Weber", "BYU", "UVU", "Utah", "BYU", "Weber", "Weber", "UVU", "BYU" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "BYU" };
		Color[] colors = { Color.red, Color.blue};
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 2, true);
		
	}
	
	/**
	 * This test if : operation == 2, we have two items in categories, and we want a Bar Graph
	 */
	public static void testSenario12(){
		String[] column1 = { "Utah", "Weber", "BYU", "UVU", "Utah", "BYU", "Weber", "Weber", "UVU", "BYU" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "BYU" };
		Color[] colors = { Color.red, Color.blue};
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 2, false);
		
	}
	
	/**
	 * This test if : operation == 0, we have four items in categories, and we want a Pie Chart
	 */
	public static void testSenario13(){
		String[] column1 = { "Utah", "Weber", "BYU", "UVU", "Utah", "BYU", "Weber", "Weber", "UVU", "BYU" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "BYU", "UVU", "Weber" };
		Color[] colors = { Color.red, Color.blue, Color.green, Color.pink};
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 0, true);
		
	}
	
	/**
	 * This test if : operation == 0, we have four items in categories, and we want a Bar Graph
	 */
	public static void testSenario14(){
		String[] column1 = { "Utah", "Weber", "BYU", "UVU", "Utah", "BYU", "Weber", "Weber", "UVU", "BYU" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "BYU", "UVU", "Weber" };
		Color[] colors = { Color.red, Color.blue, Color.green, Color.pink};
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 0, false);
		
	}
	
	/**
	 * This test if : operation == 1, we have four items in categories, and we want a Pie Chart
	 */
	public static void testSenario15(){
		String[] column1 = { "Utah", "Weber", "BYU", "UVU", "Utah", "BYU", "Weber", "Weber", "UVU", "BYU" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "BYU", "UVU", "Weber" };
		Color[] colors = { Color.red, Color.blue, Color.green, Color.pink};
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 1, true);
		
	}
	
	/**
	 * This test if : operation == 1, we have four items in categories, and we want a Bar Graph
	 */
	public static void testSenario16(){
		String[] column1 = { "Utah", "Weber", "BYU", "UVU", "Utah", "BYU", "Weber", "Weber", "UVU", "BYU" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "BYU", "UVU", "Weber" };
		Color[] colors = { Color.red, Color.blue, Color.green, Color.pink};
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 1, false);
		
	}
	
	/**
	 * This test if : operation == 2, we have four items in categories, and we want a Pie Chart
	 */
	public static void testSenario17(){
		String[] column1 = { "Utah", "Weber", "BYU", "UVU", "Utah", "BYU", "Weber", "Weber", "UVU", "BYU" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "BYU", "UVU", "Weber" };
		Color[] colors = { Color.red, Color.blue, Color.green, Color.pink};
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 2, true);
		
	}
	
	/**
	 * This test if : operation == 2, we have four items in categories, and we want a Bar Graph
	 */
	public static void testSenario18(){
		String[] column1 = { "Utah", "Weber", "BYU", "UVU", "Utah", "BYU", "Weber", "Weber", "UVU", "BYU" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah", "BYU", "UVU", "Weber" };
		Color[] colors = { Color.red, Color.blue, Color.green, Color.pink};
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 2, false);
		
	}
	
	/**
	 * This test if : operation == 0, we have one item in categories, and we want a Pie Chart
	 */
	public static void testSenario19(){
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah"};
		Color[] colors = { Color.cyan};
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 0, true);
		
	}
	
	/**
	 * This test if : operation == 0, we have one item in categories, and we want a Bar Graph
	 */
	public static void testSenario20(){
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah"};
		Color[] colors = { Color.cyan};
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 0, false);
		
	}
	
	/**
	 * This test if : operation == 1, we have one item in categories, and we want a Pie Chart
	 */
	public static void testSenario21(){
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah"};
		Color[] colors = { Color.cyan};
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 1, true);
		
	}
	
	/**
	 * This test if : operation == 1, we have one item in categories, and we want a Bar Graph
	 */
	public static void testSenario22(){
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah"};
		Color[] colors = { Color.cyan};
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 1, false);
		
	}
	
	/**
	 * This test if : operation == 2, we have one item in categories, and we want a Pie Chart
	 */
	public static void testSenario23(){
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah"};
		Color[] colors = { Color.cyan};
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 2, true);
		
	}
	
	/**
	 * This test if : operation == 2, we have one item in categories, and we want a Bar Graph
	 */
	public static void testSenario24(){
		String[] column1 = { "Utah", "Nevada", "California", "Oregon", "Utah", "California", "Nevada", "Nevada", "Oregon", "California" };
		Integer[] column2 = { 5, 6, 12, 8, 9, 10, 4, 4, 17, 6};
		String[] categories = { "Utah"};
		Color[] colors = { Color.cyan};
		new GraphWindow(new ArrayList<String>(Arrays.asList(column1)),
				new ArrayList<Integer>(Arrays.asList(column2)),
				new ArrayList<String>(Arrays.asList(categories)),
				new ArrayList<Color>(Arrays.asList(colors)), 2, false);
		
	}


}
