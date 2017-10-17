package cs1410;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;


public class Grapher
{
	/**
	 * If column1 and column2 have different lengths, throws an IllegalArgumentException.
	 * If column2 contains any non-positive numbers, throws an IllegalAgumentException.
	 * If categories and colors have different lengths, or are of length zero, throws an IllegalArgumentException.
	 * If categories contains any duplicates, throws an IllegalArgumentException.
	 * If operation is anything other than 0, 1, or 2, throws an IllegalArgumentException.
	 * 
	 * Put side-by-side, the lists column1 and column2 represent a table, where each row contains a string and
	 * a number.  For example, here is a table that might give the number of people named Zebediah in different parts
	 * of four states.
	 * 
	 * <pre>
	 *  Utah          5
	 *  Nevada        6
	 *  California   12
	 *  Oregon        8
	 *  Utah          9
	 *  California   10   
	 *  Nevada        4
	 *  Nevada        4
	 *  Oregon       17
	 *  California    6
	 * </pre>
	 * 
	 * Let c be a string that appears in the parameter categories. We can associate c with a list of numbers by collecting 
	 * every number that is paired with c in the table.  For example, if categories = [Utah, Nevada, California], we get the
	 * following pairings:
	 * 
	 * Utah       [5, 9]
	 * Nevada     [6, 4, 4]
	 * California [12, 10, 6]
	 * 
	 * We can convert each list to a number by either summing it, averaging it, or finding its maximum.  For example, if we 
	 * choose to sum the lists, we get the following pairings:
	 * 
	 * Utah       14
	 * Nevada     14
	 * California 28
	 * 
	 * The job of this method is to use column1, column2, and categories to obtain a pairing between strings and numbers as
	 * described above.  (If any of the names in categories is paired with no numbers, the method should throw an IllegalArgumentException.) 
	 * The method should sum each list if operation is 0, average each list if operation is 1, or find the maximum of each list if operation is 2.
	 * 
	 * Once the method has paired each name in categories with a number, it should display the data with either a pie chart (if usePieChart is
	 * true) or a bar graph (otherwise).  The area of each slice (of a pie chart) and the length of each bar (in a bar graph) should be proportional 
	 * to the number being graphed.  In the example above, in a pie chart Utah and Nevada should each have a quarter of the pie and California should
	 * have half.  In a bar graph, California's line should be twice as long as Utah's and Nevada's.
	 * 
	 * In both the pie chart and the bar graph, the color used for categories[i] should be colors[i].
	 * 
	 * The method should display the pie chart or bar graph by drawing on the g parameter.  The example code below draws both a pie chart
	 * and a bar graph for the situation described above.  
	 */
	
	/**
	 * @author Robert Weischedel
	 * This method takes in several ArrayLists in order to sort through the specified data, perform the correct data on it, and then graph the data.
	 * @param g - Graphics Object
	 * @param column1 - A ArrayList containing the names of the items
	 * @param column2 - A ArrayList containing the values of the items
	 * @param categories - A ArrayList containing the names of the items you wish to search for in column1
	 * @param colors - A ArrayList containing the colors the user wishes to use for the graphs
	 * @param operation - Only valid input of 0,1,2. Determines which math operation to perform on ArrayList Data
	 * @param usePieChart - A boolean allowing the user to choose if they want a Pie or Bar Graph
	 */
	public static void drawGraph (Graphics g, ArrayList<String> column1,
			ArrayList<Integer> column2, ArrayList<String> categories,
			ArrayList<Color> colors, int operation, boolean usePieChart)
	{
		// Call validator to ensure the values and data in the ArrayLists is valid
		if(validator(column1, column2, categories, colors, operation)){
		
		ArrayList<Integer> valuesOfCol2 = new ArrayList<Integer>();
		
		// Determine which operation to do and call that particular function
		switch(operation){
		case 0:
		    valuesOfCol2 = operation0(column1, column2, categories);
			break;
		case 1:
			valuesOfCol2 = operation1(column1, column2, categories);
			break;
		case 2:
			valuesOfCol2 = operation2(column1, column2, categories);
			break;
		default:
			throw new IllegalArgumentException();
		}
		
		// Determine if a Pie Chart or Bar Graph will be displayed
		if(usePieChart){
			drawPieChart(g, categories, colors, valuesOfCol2, operation);
		}
		else{
			drawBarGraph(g, categories, colors, valuesOfCol2, operation);
		}
		
		}
		
	}
	
	/**
	 * This method brings in the ArrayLists from drawGraph and ensures that the data is valid
	 * @param column1
	 * @param column2
	 * @param categories
	 * @param colors
	 * @param operation
	 * @return
	 */
	public static boolean validator(ArrayList<String> column1, ArrayList<Integer> column2, ArrayList<String> categories, ArrayList<Color> colors, int operation){
		if (column1.size() != column2.size()){
			throw new IllegalArgumentException();
		}
		
		for (int i = 0; i < column2.size(); i++){
			if (column2.get(i) < 0){
				throw new IllegalArgumentException();
			}
		}
		
		if (categories.size() != colors.size() || categories.isEmpty() || colors.isEmpty()){
			throw new IllegalArgumentException();
		}
		
		for (int i = 0; i < categories.size(); i++){
			String checkValue = categories.get(i);
				for (int c = i + 1; c < categories.size(); c++){
						if(categories.get(c).equals(checkValue)){
							throw new IllegalArgumentException();
						}
				}
		}
		
		if(operation != 0 && operation != 1 && operation != 2){
			throw new IllegalArgumentException();
		}
		
		for(int i = 0; i < categories.size(); i++){
			int total = 0;
		    for(int c = 0; c < column1.size(); c++){
		    	if(categories.get(i).equals(column1.get(c))){
		        total++;
		        }
		    }
		    if(total == 0){
		    	throw new IllegalArgumentException();
		   }
		    
	    }

		
		return true;
	}

	/**
	 * This method is called from drawGraph, if operation == 0. It takes the specified categories and adds up the appropriate values from column2.
	 * @param column1
	 * @param column2
	 * @param categories
	 * @return - A ArrayList of summed up values of each specified category
	 */
	public static ArrayList<Integer> operation0 (ArrayList<String> column1, ArrayList<Integer> column2, ArrayList<String> categories){
		ArrayList<Integer> valuesOfCol2 = new ArrayList<Integer>();

		for (int i = 0; i < categories.size(); i++){
			int total = 0;
				for (int c = 0; c < column1.size(); c++){
						if(categories.get(i).equals(column1.get(c))){
							total = total + column2.get(c);
						}
				}
				valuesOfCol2.add(total);
		}
		
		return valuesOfCol2;
		
	}
	
	/**
	 * This method is called from drawGraph, if operation == 1. It takes the specified categories and finds the average of the appropriate values of column2.
	 * @param column1
	 * @param column2
	 * @param categories
	 * @return - A ArrayList of averaged vales of each specified category
	 */
	public static ArrayList<Integer> operation1 (ArrayList<String> column1, ArrayList<Integer> column2, ArrayList<String> categories){
		ArrayList<Integer> valuesOfCol2 = new ArrayList<Integer>();
		for (int i = 0; i < categories.size(); i++){
			int numOfOccurances = 0;
			int total = 0;
				for (int c = 0; c < column1.size(); c++){
						if(categories.get(i).equals(column1.get(c))){
							total = total + column2.get(c);
							numOfOccurances++;
						}
				}
				valuesOfCol2.add(total/numOfOccurances);
		}
		
		return valuesOfCol2;
		
	}
	
	/**
	 * This method is called from drawGraph, if operation == 2. It takes the specified categories and finds the max from the appropriate values from column2.
	 * @param column1
	 * @param column2
	 * @param categories
	 * @return - A ArrayList containing the max value of each specified category 
	 */
	public static ArrayList<Integer> operation2 (ArrayList<String> column1, ArrayList<Integer> column2, ArrayList<String> categories){
		ArrayList<Integer> valuesOfCol2 = new ArrayList<Integer>();

		for (int i = 0; i < categories.size(); i++){
			int maxValue = column2.get(0);
				for (int c = 0; c < column1.size(); c++){
						if(categories.get(i).equals(column1.get(c))){
							if(column2.get(c) > maxValue){
								maxValue = column2.get(c);
							}
						}
				}
				valuesOfCol2.add(maxValue);
		}
		
		return valuesOfCol2;
		
	}
	
	/**
	 * This method is called from drawGraph, if usePieChar == true. It takes the graphics object, ArrayList of colors, and categories and the desired values
	 * and makes a Pie Chart out of the data. 
	 * @param g
	 * @param categories
	 * @param colors
	 * @param valuesOfCol2
	 */
	public static void drawPieChart(Graphics g, ArrayList<String> categories, ArrayList<Color> colors, ArrayList<Integer> valuesOfCol2, int operation) {
		int beginDegree = 0;
		double total = 0.0;
		int colorSquaresY = 200;
		int stringYCoord = 210;
		String banner = "";
		
		for(int i = 0; i < valuesOfCol2.size(); i++){
			total += valuesOfCol2.get(i);
		}
		
		for(int i = 0; i < categories.size(); i++){
			g.setColor(colors.get(i));
			int endingDegree = (int)(((valuesOfCol2.get(i)*360)/total) + 1);
			g.fillArc(100, 200, 100, 100, beginDegree, endingDegree);
			g.fillRect(210, colorSquaresY, 10, 10);
			g.setColor(Color.black);
			g.drawString(categories.get(i), 225, stringYCoord);
			beginDegree = beginDegree + endingDegree;
			colorSquaresY += 15;
			stringYCoord += 15;
			banner = banner + categories.get(i) + " ";
		}
		
		g.setFont(new Font("Serif", Font.BOLD,20));
		switch(operation){
		case 0:
		    g.drawString("The Sum of: " + banner, 50,50);
			break;
		case 1:
			g.drawString("The Average of: " + banner, 50,50);
			break;
		case 2:
			g.drawString("The Max of: " + banner, 50,50);
			break;
		}
		
	}
	
	/**
	 * This method is called from drawGraph, if usePieChar == false. It takes the graphics object, ArrayList of colors, and categories and the desired values
	 * and makes a Bar Graph out of the data. 
	 * @param g
	 * @param categories
	 * @param colors
	 * @param valuesOfCol2
	 */
	public static void drawBarGraph(Graphics g, ArrayList<String> categories, ArrayList<Color> colors, ArrayList<Integer> valuesOfCol2, int operation) {
		int barYCoord = 100;
		int stringYCoord = 120;
		String banner = "";
		
		for(int i = 0; i < categories.size(); i++){
			g.setColor(colors.get(i));
			g.fillRect(110, barYCoord, valuesOfCol2.get(i), 25);
			g.setColor(Color.black);
			g.drawString(categories.get(i), 50, stringYCoord);
			barYCoord += 25;
			stringYCoord += 25;
			banner = banner + categories.get(i) + "    ";
		}
		
		g.setFont(new Font("Serif", Font.BOLD,20));
		switch(operation){
		case 0:
		    g.drawString("The Sum of: " + banner, 50,50);
			break;
		case 1:
			g.drawString("The Average of: " + banner, 50,50);
			break;
		case 2:
			g.drawString("The Max of: " + banner, 50,50);
			break;
		}
		
		
	}
}
