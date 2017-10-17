package triangles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The PaintTriangle class serves as the constructor of the Sierpinski Triangle that is draw on the Sierpinksi class
 * GUI. It handles the color changes from the Radio Button Groups, gets the level of Sierpinski triangles from the 
 * Sierpinski class and draws the triangles from the paintComponent.
 * @author Robert Weischedel
 */
public class PaintTriangle extends JPanel{
	
	// These member variable ButtonGroups serve to hold the information of the radio buttons brought in from the Sierpinski class
	private ButtonGroup backButtons;
    private ButtonGroup foreButtons;
    
    // This member variable serves to hold the user desired level of Sierpinksi Triangle.
    private int level;
    
    // These member variables serve to hold the three coordinate points of each Sierpinksi Triangle.
    private int x1, x2, x3, y1, y2, y3;
    
    /**
     * Creates a PaintTriangle object and sets the ButtonGroup member variables to those brought in from 
     * Sierpinski class. It also paints the background color of the GUI to white.
     * @param backButtons - Holds the information from the Sierpinksi class background color radio ButtonGroup
     * @param foreButtons - Holds the information from the Sierpinksi class foreground color radio ButtonGroup
     */
    public PaintTriangle (ButtonGroup backButtons, ButtonGroup foreButtons)
    {
    	// Initialize the member variables
        this.backButtons = backButtons;
        this.foreButtons = foreButtons;
        
        // Set background color to white
        setBackground(Color.white);
    }
    
    @Override
    public void paintComponent (Graphics g)
    {
        // We invoke the original, overridden version to paint the background.
        super.paintComponent(g);

        // Turn on anti-aliasting
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        // Read the radio buttons to determine the color that is selected and set the back and foreground to the correct color
        g.setColor(getForegroundColor());
        setBackground(getBackgroundColor());
        
        // Initizalize the member variables using the getWidth() and getHeight() methods so that the Triangle can resized.
        x1 = getWidth() / 2;
        x2 = getWidth() - 25;
        x3 = 25;
        y1 = 25;
        y2 = getHeight() - 25;
        y3 = getHeight() - 25;
        
        // Call the drawTraingle method. Takes in the graphics object.
        drawTriangle(g);
        
    }

    /**
     * The drawTriangle method takes in the graphics object and draws the first Sierpinski triangle 
     * or base triangle if level is equal to zero. If not it calls the recursive triangle method to create all the 
     * other smaller triangles. It passes the midpoint coordinates of the triangle into the method.
     * @param g - Graphics object
     */
    private void drawTriangle(Graphics g) {
    	if(level == 0){
    		// Create the level 0 base triangle
    		g.fillPolygon(new int[]{x1,x2,x3}, new int[]{y1,y2,y3}, 3);
    	}
    	
    	else{
    		// If level is not zero, create the level zero base triangle, then call the recursive method
    		g.fillPolygon(new int[]{x1,x2,x3}, new int[]{y1,y2,y3}, 3);
    		// Counter variable used to end the recursive method.
    		int counter = 1;
    		// Pass the midpoints of the coordinates into the recursiveTriangle method.
    		recursiveTriangle(g, (x1 + x2) / 2 , (x2 + x3) / 2, (x3 + x1) / 2, (y1 + y2) / 2, (y2 + y3) /2, (y3 + y1) / 2, counter);

    	}
		
	}

    /**
     * The recursiveTriangle method is a recursive method that draws all the smaller triangles on the larger
     * background traingle. It takes in a three coordinates and each times finds the midpoint of a smaller traingle.
     * It also takes in a counter to help the method to end. The coordinates use the same names as the member variables
     * but they are their own varaibles.
     * @param g - Graphics object
     * @param x1 - int X coordinate
     * @param x2 - int X coordinate
     * @param x3 - int X coordinate
     * @param y1 - int Y coordinate
     * @param y2 - int Y coordinate
     * @param y3 - int Y coordinate
     * @param counter - keeps the count of how many times the traingles have been drawn.
     */
	private void recursiveTriangle(Graphics g, int x1, int x2, int x3, int y1, int y2, int y3, int counter) {
		
		// Set color of trangles being drawn as the background color.
		g.setColor(getBackgroundColor());
		
		// Draw a Triangle
		g.fillPolygon(new int[]{x1,x2,x3}, new int[]{y1,y2,y3}, 3);
		
		// If the counter is less than the level, keep calling the method. Each
		if (counter < level){
			recursiveTriangle(g, 
					(x1 + x2) / 2 + (x2 - x3) / 2,
					(x1 + x2) / 2 + (x1 - x3) /2,
					(x1 + x2) / 2,
					(y1 + y2) / 2 + (y2 - y3) / 2,
					(y1 + y2) / 2 + (y1 - y3) / 2,
					(y1 + y2) / 2, counter + 1);
			
			recursiveTriangle(g, 
					(x3 + x2) / 2 + (x2 - x1) / 2,
					(x2 + x3) / 2 + (x3 - x1) /2,
					(x2 + x3) / 2,
					(y2 + y3) / 2 + (y2 - y1) / 2,
					(y2 + y3) / 2 + (y3 - y1) / 2,
					(y2 + y3) / 2, counter + 1);
			
			recursiveTriangle(g, 
					(x1 + x3) / 2 + (x3 - x2) / 2,
					(x1 + x3) / 2 + (x1 - x2) /2,
					(x1 + x3) / 2,
					(y1 + y3) / 2 + (y3 - y2) / 2,
					(y1 + y3) / 2 + (y1 - y2) / 2,
					(y1 + y3) / 2, counter + 1);
		}
		
	}

	/**
     * Returns the selected background color based on the user selected radio button choice option.
     * @return Color
     */
    public Color getBackgroundColor ()
    {
    	// Each color is decided by which Radio Button is selected.
        switch (backButtons.getSelection().getActionCommand())
        {
        case "white":
            return Color.white;
        case "blue":
            return Color.blue;
        case "green":
            return Color.green;
        default:
            return Color.black;
        }
    }
    
    /**
     * Returns the selected foreground color based on the user selected radio button choice option.
     * @return Color
     */
    public Color getForegroundColor ()
    {
    	// Each color is decided by which Radio Button is selected.
        switch (foreButtons.getSelection().getActionCommand())
        {
        case "black":
            return Color.black;
        case "yellow":
            return Color.yellow;
        case "red":
            return Color.red;
        default:
            return Color.black;
        }
    }
    
    // Gets the desired Sierpinski Triangle level from the Sierpinski class.
    public void setLevel(int level){
    	// Set the member variable to the level value from Sierpinski class.
    	this.level = level;
    }


}
