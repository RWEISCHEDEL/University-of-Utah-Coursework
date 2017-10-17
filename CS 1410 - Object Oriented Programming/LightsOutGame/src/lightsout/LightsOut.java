package lightsout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Lights Out Game for CS 1410
 * @author Robert Weischedel
 * Date : 11/5/14
 * This program creates a simple lights out game using the LightsOut class and its encompassed methods. 
 */
public class LightsOut extends JFrame implements ActionListener{

	// Private Member Variables:
	
	// Represents the Manual Setup JButton
	private JButton manual;
	// Represents the New Game JButton
	private JButton reset;
	// Represents the standard blue/gray color the JButtons come with standard. Used to reset the buttons to lights on
	private Color buttonColor;
	// Represents when the Manual Setup JButton has been pressed. True if in Manual Setup Mode.
	private boolean enterMMode;
	// Sets up a Array of JButtons that are used all over the program.
	private JButton[] buttons;

	/**
	 * The main method creates a new LightsOut object, effectively creating a new game board.
	 * @param args
	 */
	public static void main (String[] args) {
		new LightsOut();
		
	}
	
	/**
	 * The LightsOut class, the constructor for the Lights Out board.
	 */
	public LightsOut (){
		// Set up the board
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Lights Out!");
		
		// Initialize an Array of JButtons
		buttons = new JButton[25];
		
		// Set up the main panel with a border layout, all other panels will be placed on this main panel.
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		// Set up the gameBoard panel with a 5X5 grid layout to represent 25 squares, and then fill it with JButtons.
		JPanel gameBoard = new JPanel();
		gameBoard.setLayout(new GridLayout(5,5,5,5));
		
		for (int i = 0; i < 25; i++) {
				JButton b = new JButton("");
				//Add a listener to each button, to tell if that JButton has been clicked. 
				b.addActionListener(this);
				// Name each JButton
				b.setName("" + i);
				buttons[i] = b;
				// Add each Button to the game board.
				gameBoard.add(b);
		}
		
		// Add the game board grid to the main panel.
		mainPanel.add(gameBoard, "Center");

		// Create the New Game and Manual Mode JButtons.
		reset = new JButton("New Game");
		//Add a listener to New Game button, to tell if that JButton has been clicked. 
		reset.addActionListener(this);
		// Name New Game JButton
		reset.setName("" + 25);
		manual = new JButton("Enter Manual Setup");
		// Name Manual Setup JButton
		manual.setName("" + 26);
		//Add a listener to Manual Setup JButton, to tell if that JButton has been clicked.
		manual.addActionListener(this);
		// Not in Manual Mode
		enterMMode = false;
		
		// Orient the New Game and Manual Mode JButtons on a options panel and then add that panel to main panel.
		JPanel options = new JPanel();
		options.setLayout(new BorderLayout());
		options.add(reset, "South");
		options.add(manual, "North");
		mainPanel.add(options, "South");
		
		// Display the board on screen and reset the game board.
		setContentPane(mainPanel);
		setSize(300,300);
		setVisible(true);
		reset();
	}

	/**
	 * This is the action listener for the mouse clicks. It handles what happens when each JButton is clicked, by
	 * calling the approriate methods. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Set buttonColor to standard blue/gray JButton color. Used as Lights On color
		buttonColor = reset.getBackground();
		
		// Get name of button clicked.
		JButton button = (JButton)e.getSource();
		
		// Parse name of button clicked to a integer.
		int buttonName = Integer.parseInt(button.getName());
		
		// If button is New Game button.
		if(buttonName == 25){
			reset();
		}
		
		// If button is Enter Manual Setup.
		else if(buttonName == 26){
			manual.setText("Exit Manual Setup");
			manual.setName("" + 27);
			enterMMode = true;
		}
		
		// If button is Exit Manual Setup
		else if(buttonName == 27){
			manual.setText("Enter Manual Setup");
			manual.setName("" + 26);
			enterMMode = false;
			
		}
		// If in Manual Setup Mode, modify the one button clicked.
		else if(enterMMode){
			toggleColor(buttonName);
		}
		// If not in Manual Setup mode, hand the button name to toggleButton method.
		else{
			toggleButton(buttonName);
		}
		
		// Calls isWon method and displays a winning message if all lights are out.
		if(isWon()){
			JOptionPane.showMessageDialog(null,"You Win\n Click New Game to Play Again");
		}

		
	}

	/**
	 * Brings in a buttonName int value from the actionListener, and then interprets what other buttons to 
	 * toggle based on position. It also calls the toggleColor method. 
	 * @param buttonName - int
	 */
	private void toggleButton(int buttonName) {
		if(buttonName == 0 || buttonName == 4 || buttonName == 20 ||  buttonName == 24){
			if(buttonName == 0){
				toggleColor(buttonName);
				toggleColor(buttonName + 1);
				toggleColor(buttonName + 5);
			}
			else if(buttonName == 4){
				toggleColor(buttonName);
				toggleColor(buttonName - 1);
				toggleColor(buttonName + 5);
			}
			
			else if(buttonName == 20){
				toggleColor(buttonName);
				toggleColor(buttonName + 1);
				toggleColor(buttonName - 5);
			}
			else{
				toggleColor(buttonName);
				toggleColor(buttonName - 1);
				toggleColor(buttonName - 5);
			}

		}
		
		else if(buttonName == 6 || buttonName == 7 || buttonName == 8 || buttonName == 11 || buttonName == 12 || buttonName == 13 || buttonName == 16 || buttonName == 17 || buttonName == 18){
			toggleColor(buttonName);
			toggleColor(buttonName + 1);
			toggleColor(buttonName - 1);
			toggleColor(buttonName + 5);
			toggleColor(buttonName - 5);
		}
		
		else if(buttonName == 1 || buttonName == 2 || buttonName == 3 || buttonName == 21 || buttonName == 22 || buttonName == 23){
			if(buttonName == 1 || buttonName == 2 || buttonName == 3){
				toggleColor(buttonName);
				toggleColor(buttonName + 1);
				toggleColor(buttonName - 1);
				toggleColor(buttonName + 5);
			}
			else{
				toggleColor(buttonName);
				toggleColor(buttonName + 1);
				toggleColor(buttonName - 1);
				toggleColor(buttonName - 5);
			}
		}
		
		else if(buttonName == 5 || buttonName == 10 || buttonName == 15 || buttonName == 9 || buttonName == 14 || buttonName == 19){
			if(buttonName == 5 || buttonName == 10 || buttonName == 15){
				toggleColor(buttonName);
				toggleColor(buttonName + 1);
				toggleColor(buttonName + 5);
				toggleColor(buttonName - 5);
			}
			
			else{
				toggleColor(buttonName);
				toggleColor(buttonName - 1);
				toggleColor(buttonName + 5);
				toggleColor(buttonName - 5);
			}
		}
		
	}

	/**
	 * Called from toggleButton to determine what the color of each button is. And to change it accordingly. 
	 * @param buttonName - int from toggleButton
	 */
	private void toggleColor(int buttonName) {
		if(buttons[buttonName].getBackground() == Color.GRAY){
			buttons[buttonName].setBackground(buttonColor);
		}
		else{
			buttons[buttonName].setBackground(Color.GRAY);
		}
	}

	/**
	 * Called from actionListener if the New Game button is clicked. It sets all buttons to lights out and then
	 * randomly generates 25 moves that could be made by a user to set up a new board. 
	 */
	private void reset() {
		for(int i = 0; i < 25; i++){
			buttons[i].setBackground(Color.GRAY);
		}
		
		for(int i = 0; i < 25; i++){
			int randomButton = getRandomNumber();
			toggleButton(randomButton);
		}
		
	}

	/**
	 * Called from reset, it generates a new random number for reset to use and returns that int value. 
	 * @return - int random value from 0 - 24
	 */
	private int getRandomNumber() {
		Random randomButton = new Random();
		int answer = randomButton.nextInt(24) + 0;
		return answer;
	}

	/**
	 * Called from actionListener to see if all buttons on the board are out. 
	 * @return - true if won, false otherwise
	 */
	private boolean isWon() {
		for(int i = 0; i < 25; i++){
			if(!(buttons[i].getBackground() == Color.GRAY)){
				return false;
			}
		}
		return true;
	}

	
}
