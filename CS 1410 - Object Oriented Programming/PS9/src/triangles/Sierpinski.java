package triangles;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * This Class serves and the constructor of the Sierpinksi GUI interface. It sets up how the GUI looks, and also
 * how the JTextField operates. The back and foreground color changing radio buttons are built here, but handled in the
 * PaintTriangle class. But all the actionListers and MouseListeners are implemtented in this class.
 * @author Robert Weischedel
 *
 */
public class Sierpinski extends JFrame implements MouseListener, ActionListener{
	
	// Declare private JTextField named getLevel so that we can interact with it in the ActionListener
	private JTextField getLevel;
	
	// Create a PaintTriangle object called paintBoard so that we can call it in the GUI and ActionListen to pass the level
	private PaintTriangle paintBoard;

	/**
	 * Calls and creates a new Sierpinski GUI object.
	 * @param args
	 */
	public static void main(String[] args) {
		new Sierpinski();
	}
	
	/**
	 * The class for constructing the Sierpinksi Triangle GUI Interface. The buttons and action listners are
	 * all handled in here. 
	 */
	public Sierpinski (){
		// Set up the GUI title and Close Window Button
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Sierpinski Triangle Generator");
		
		// Set up the main panel with a border layout, all other panels will be placed on this main panel.
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		// Create the three radio buttons to change the Background Color
        JPanel colorPanelBackground = new JPanel();
        JRadioButton white, green, blue;
        colorPanelBackground.add(white = new JRadioButton("White"));
        colorPanelBackground.add(green = new JRadioButton("Green"));
        colorPanelBackground.add(blue = new JRadioButton("Blue"));
        
        // Create a label for the background radio buttons
        JLabel colorPanelBGLabel = new JLabel("Background Color");
        
        // Add all the background radio buttons to the colorGroupB ButtonGroup
        ButtonGroup colorGroupB = new ButtonGroup();
        colorGroupB.add(white);  
        colorGroupB.add(green);
        colorGroupB.add(blue);
        
        // Set action commands and mouse listeners so that PaintTriangle can interact with the background colors.
        white.setActionCommand("white");
        white.addMouseListener(this);
        green.setActionCommand("green");
        green.addMouseListener(this);
        blue.setActionCommand("blue");
        blue.addMouseListener(this);
        white.setSelected(true);
        
        // Create the three radio buttons to change the Foreground Color
        JPanel colorPanelForeground = new JPanel();
        JRadioButton black, yellow, red;
        colorPanelForeground.add(black = new JRadioButton("Black"));
        colorPanelForeground.add(yellow = new JRadioButton("Yellow"));
        colorPanelForeground.add(red = new JRadioButton("Red"));
        
        // Create a label for the foreground radio buttons 
        JLabel colorPanelFGLabel = new JLabel("Triangle Color");
        
        // Add all the background radio buttons to the colorGroupF ButtonGroup
        ButtonGroup colorGroupF = new ButtonGroup();
        colorGroupF.add(black);
        colorGroupF.add(yellow);
        colorGroupF.add(red);
        
        // Set action commands and mouse listeners so that PaintTriangle can interact with the foreground colors.
        black.setActionCommand("black");
        black.addMouseListener(this);
        yellow.setActionCommand("yellow");
        yellow.addMouseListener(this);
        red.setActionCommand("red");
        red.addMouseListener(this);
        black.setSelected(true);

        // Create the JTextField where the users can input the level of Sierpinksi Triangle they want.
        getLevel = new JTextField("0", 5);
        
        //Add a Label for the JTextField getLevel
        JLabel levelLabel = new JLabel("Change Level - Press Enter To Accept Changes");
        
        // Add actionListener to getLevel
        getLevel.addActionListener(this);
        
        // Create a JPanel that will hold all the JLabels created for each of the RadioButton groups and JTextField
        JPanel titleBoard = new JPanel();
        titleBoard.setLayout(new GridLayout(1,3));
        titleBoard.add(colorPanelBGLabel);
        titleBoard.add(colorPanelFGLabel);
        titleBoard.add(levelLabel);
        
        // Create a JPanel that holds all the Radio Buttons and JTextField
        JPanel controlBoard = new JPanel();
        controlBoard.setLayout(new GridLayout(1,3));
        controlBoard.add(colorPanelBackground);
        controlBoard.add(colorPanelForeground);
        controlBoard.add(getLevel);
        
        // Arrange the JPanels titleBoard and controlBoard onto a JPanel called colorBoard
        JPanel colorBoard = new JPanel();
        colorBoard.setLayout(new BorderLayout());
        colorBoard.add(titleBoard, "North");
        colorBoard.add(controlBoard, "South");
        
        // Add colorBoard to the mainPanel
        mainPanel.add(colorBoard, "North");
        
        // Create a new PaintTriangle Object and display it in the center of the mainPanel
        mainPanel.add(paintBoard = new PaintTriangle(colorGroupB, colorGroupF), "Center");
        

		// Display the board on screen
		setContentPane(mainPanel);
		setSize(900,800);
		setVisible(true);
	}

	/**
	 * This method handles the clicking of the JTextField and converting the user input into an integer.
	 * It also handles invalid data.If the data is correct it invokes a setter method to the PaintTriangle class
	 * to set the user defined desire Sierpinksi Triangle Level.
	 */
	public void actionPerformed(ActionEvent e) {
		// Make sure the value typed by the user is between 0 - 10 and an integer, if not display the correct error message.
		try{
		int level = Integer.parseInt(getLevel.getText().trim());
		
		if (level < 0){
			JOptionPane.showMessageDialog(null,"Sorry you can't enter a intgeter less than 0");
		}
		
		else if (level > 10){
			JOptionPane.showMessageDialog(null,"Sorry you can't enter a integer greater than 10");
		}
		
		else{
			paintBoard.setLevel(level);
			repaint();
		}
		
		}
		// If the value isn't an integer.
		catch(NumberFormatException error){
			JOptionPane.showMessageDialog(null,"Sorry you didn't enter a correct integer - Range Excepted 1 - 10");
		}
		
	}
	
	// Only mouseClicked is used in this program.
	
	/**
	 * When a radio button is clicked repaint the whole GUI.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		repaint();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
