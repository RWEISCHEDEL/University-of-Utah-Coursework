package ttt;
import java.awt.event.*;
import javax.swing.*;

/**
 * Coordinates between a TTTGui (the user interface component)
 * and a TTTBoard (which represents the state of the game).
 * @author Joe Zachary
 */
public class TTTController implements ActionListener {

	private TTTGui gui;      // The user interface component
	private TTTBoard board;  // The state of the game


	/**
	 * Creates a controller whose job is to coordinate the behaviors
	 * of gui and board.
	 */
	public TTTController (TTTGui gui, TTTBoard board) {
		this.gui = gui;
		this.board = board;
	}
	
	
	/**
	 * Resets the controller to begin a new game.
	 */
	public void reset () {
		board.reset();
		gui.clearBoard();
		displayStatus();
		displayScore();
	}
	

	/**
	 * Called when one of the buttons in the user interface is clicked.
	 */
	public void actionPerformed (ActionEvent e) {

		// Get the button that was clicked
		JButton button = (JButton)e.getSource();

		// It was the start new game button
		if (button.getText().equals("Start New Game")) {
			reset();
		}

		// It was one of the nine board buttons
		else {

			// Get the row and column that were clicked
			int row = button.getName().charAt(0) - '0';
			int col = button.getName().charAt(1) - '0';

			// Make the move.  Illegal moves will result in
			// an exception and are ignored.
			String symbol;
			try {
			symbol = board.move(row*3+col+1);
			}
			catch (IllegalArgumentException ex) {
				return;
			}
			
			// Display the symbol that was just played
			button.setText(symbol);
			
			// Update the GUI depending on what the last move accomplished
			if (board.isDrawn()) {
				displayStatus("Draw!");
				displayScore();
			}
			else if (board.isWon()) {
				displayStatus(symbol + " wins!");
				displayScore();
			}
			else {
				displayStatus();
			}

		}

	}

	
	/**
	 * Updates the gui to display the proper score.
	 */
	private void displayScore () {
		int x = board.getXWins();
		int o = board.getOWins();
		int d = board.getDrawCount();
		gui.setScore("X: " + x + "  O: " + o + "  Draws: " + d);
	}
	
	
	/**
	 * Updates the gui to display whose turn it is.
	 */
	private void displayStatus () {
		displayStatus(board.getToMove() + " to move");
	}

	
	/**
	 * Updates the gui to display the specified message.
	 */
	private void displayStatus (String msg) {
		gui.setStatus(msg);
	}

}


