package maze;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Provides a GUI controller for the Monkey Maze animation.
 * @author Joe Zachary for CS 3500, November 2010
 *
 */
public class MazeControl extends JFrame implements ActionListener, Runnable {

	
	/**
	 * Launch a maze controller
	 * @param args
	 */
	public static void main (String[] args) {
		new MazeControl().setVisible(true);
	}
	
	
	private JButton start;       // Button that starts animation
	private JButton recarve;     // Button that recarves the maze
	private Maze maze;           // The maze
	
	
	/**
	 * Makes a maze control window
	 */
	public MazeControl () {
		
		// Customize the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Monkey Maze");
		
		// Create the maze
		maze = new Maze(40,40);
		
		// Create a panel with the buttons
		JPanel buttons = new JPanel();
		buttons.add(start = new JButton("Start"));
		buttons.add(recarve = new JButton("Recarve"));
		
		// Give action listeners to the buttons
		start.addActionListener(this);
		recarve.addActionListener(this);
		
		// The top-level panel
		JPanel pane = new JPanel();
		setContentPane(pane);
		pane.setPreferredSize(new Dimension(600, 600));
		pane.setLayout(new BorderLayout());
		pane.add(buttons, "South");
		pane.add(maze, "Center");

		// Lay everything out
		pack();
		
	}
	
	
	/**
	 * Called when one of the buttons is clicked
	 */
	public void actionPerformed (ActionEvent e) {
		
		// Start button
		if (e.getSource() == start) {
			start.setEnabled(false);
			recarve.setEnabled(false);
			maze.unstain();
			maze.revalidate();
			maze.repaint();
			Thread t = new Thread(this);
			t.setDaemon(true);
			t.start();
		}
		
		// Recarve button
		else if (e.getSource() == recarve) {
			maze.reset();
			maze.revalidate();
			maze.repaint();
		}
	}
	
	
	/**
	 * Starts the monkey in the maze
	 */
	public void run () {
		Monkey m = new Monkey(Color.green);
		m.doSearch(maze.get(0,0), maze.get(maze.getRows()-1, maze.getCols()-1));
		start.setEnabled(true);
		recarve.setEnabled(true);
	}	
}

	

