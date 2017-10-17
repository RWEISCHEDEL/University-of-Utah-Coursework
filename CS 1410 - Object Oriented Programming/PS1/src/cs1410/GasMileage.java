package cs1410;

import javax.swing.JOptionPane;

/* Name: Robert Weischedel
 * Class: CS 1410 - 001
 * Date: 8/29/14
 * Assignment: PS1
 */

public class GasMileage {
	public static void main (String[] args)
	{
		// Get input from user
		String carType = JOptionPane.showInputDialog("Please enter your type of car.");
		String numMilesDriven = JOptionPane.showInputDialog("Please enter the number of miles driven.");
		String currentGasPrice = JOptionPane.showInputDialog("Please enter the current price in dollars for a gallon of gasoline.");
		String gallonsUsed = JOptionPane.showInputDialog("Please enter the number of gallons used.");
		
		// Translate input from Strings to correct types
		int milesDriven = Integer.parseInt(numMilesDriven);
		double gasPrice = Double.parseDouble(currentGasPrice);
		double gallons = Double.parseDouble(gallonsUsed);
		
		// Perform calculations
		double gasCost = gasPrice * gallons;
		double mpg = milesDriven / gallons;
		double gasCostPerMile = gasCost / milesDriven;
		
		// Round all calculations to two decimal places
		String displayedGasCost = String.format("%.2f", gasCost);
		String displayedMpg = String.format("%.2f", mpg);
		String displayedCostPerMile = String.format("%.2f", gasCostPerMile);
		
		// Display answers
		JOptionPane.showMessageDialog(null,carType + '\n' + "Gas cost: $" + displayedGasCost + '\n' + "Miles per gallon: "+ displayedMpg + '\n' + "Gas cost per mile: $" + displayedCostPerMile);
		
		
	}

}
