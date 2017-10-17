package ttt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Represents a tic-tac-toe game board
 * @author Joe Zachary
 */
public class TTTGui extends JFrame {
	
	/**
	 * Launches a game of tic-tac-toe
	 */
	public static void main (String[] args) {
		TTTGui ttt = new TTTGui();
		ttt.setVisible(true);
	}
	
	
	private JLabel status;    // Where status messages are displayed
	private JLabel score;     // Where the score is displayed
	private BoardPanel boardPanel; // The main part of the GUI
	
	
	/**
	 * Creates a top-level tic-tac-toe window
	 */
	public TTTGui () {
		
		// When this window is closed, the program exits
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Tic Tac Toe");
		
		// Represents the state of the game
		TTTBoard board = new TTTBoard();
		
		// Controls the interaction between the GUI and the board
		TTTController controller = new TTTController(this, board);	
		
		// Top-level panel within the window
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		// Lay out the main panel
		boardPanel = new BoardPanel(controller);
		mainPanel.add(boardPanel, "Center");
		JPanel labels = new JPanel();
		labels.setLayout(new GridLayout(1,2));
		labels.add(status = new JLabel());
		labels.add(score = new JLabel());
		mainPanel.add(labels, "North");
		JButton reset = new JButton("Start New Game");
		reset.addActionListener(controller);
		mainPanel.add(reset, "South");
		
		// Compose the top-level window and get going.
		setContentPane(mainPanel);
		setSize(300,300);
		controller.reset();
		
	}
	
	
	/**
	 * Sets the status label
	 */
	public void setStatus (String s) {
		status.setText(s);
	}
	
	
	/**
	 * Sets the score label
	 */
	public void setScore (String s) {
		score.setText(s);
	}
	
	
	/**
	 * Clears the board
	 */
	public void clearBoard () {
		boardPanel.clear();
	}

}


/**
 * Represents the playing area of a TTTGui.
 */
class BoardPanel extends JPanel {

	private Font font;        // Font used to display X and O
	
	
	/**
	 * Creates a BoardPanel given the controller and the board state.
	 */
	public BoardPanel (ActionListener listener) {
		setLayout(new GridLayout(3,3));
		font = new Font(getFont().getFamily(), 0, 72);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				JButton b = new JButton(" ");
				b.setFont(font);
				b.setName("" + i + j);
				add(b);
				b.addActionListener(listener);
			}
		}
	}
	
	
	public void clear () {
		for (int i = 0; i < 9; i++) {
			JButton b = (JButton) getComponent(i);
			b.setText("");
		}
	}

}




