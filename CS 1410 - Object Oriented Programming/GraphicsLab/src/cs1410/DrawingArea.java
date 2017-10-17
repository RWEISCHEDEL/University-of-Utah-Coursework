package cs1410;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Represents the area where custom graphics can be drawn.
 * 
 * @author Joe Zachary for CS 1410
 */
public class DrawingArea extends JPanel
{
	public void paintComponent (Graphics g)
	{
		int x = 5; // x measures distance from the left
		int y = 50; // y measures distance from the top
		int count = 0;
		while (count < 3){
		drawHouse(g, x, y);
		count++;
		x+=60;
		y+=60;
		}

		
	}
	
	public void drawHouse(Graphics g, int x, int y){
		//Font font1 = new Font("Serif", Font.BOLD,20);
		g.setColor(Color.RED); // Draw using red
		g.drawRect(x, y, 50, 30); // Draw a rectangle
		g.setColor(Color.BLUE);
		g.drawLine(x, y, x + 25, y - 15); // Draw first line of a triangle
		g.drawLine(x + 25, y - 15, x + 50, y); // Draw second line of a triangle
		g.setColor(Color.GREEN);
		g.drawRect(x + 5, y + 8 ,10, 10);
		g.setColor(Color.CYAN);
		Font turd = new Font("Serif", Font.ITALIC, 30);
		g.setFont(turd);
		g.setFont(new Font("Serif", Font.BOLD,20));
		g.drawString("My First Graphic", x + 100, y);
	}
}