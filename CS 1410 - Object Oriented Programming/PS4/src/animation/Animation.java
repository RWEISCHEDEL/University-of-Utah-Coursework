package animation;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This method uses the graphics class g to draw all the shapes and objects used
 * in the animation video.
 * 
 * @author Robert Weischedel
 * 
 */
public class Animation {
	/**
	 * This is the method that you need to rewrite to create a custom animation.
	 * This method is called repeatedly as the animation proceeds. It needs to
	 * draw to g how the animation should look after t milliseconds have passed.
	 * 
	 * @param g
	 *            Graphics object on which to draw
	 * @param t
	 *            Number of milliseconds that have passed since animation
	 *            started
	 */
	public static void paintFrame(Graphics g, int t) {

		// The object moves steadily from left to right
		int xF = 5 + t / 10;
		// The object moves steadily from right to left
		int xB = 5 - t / 100;

		int y = 170;

		// All the methods that are continually called to draw the objects
		drawRamp(g);
		drawClouds(g, xB, y, t);
		drawBuilding(g);
		drawTruck(g, xF, y, t);
		drawPoliceCar(g, xF, y, t);

		// Draw this after the truck hits the building
		if (t > 10000) {
			g.setColor(Color.BLACK);
			drawExplosion(g, xF, y, t);
		}
	}

	/**
	 * Draws the smoke effect comming off the building after the truck hits it.
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param t
	 */
	private static void drawExplosion(Graphics g, int x, int y, int t) {
		g.drawOval(1200, 290, 10 + x / 100, 10 + x / 100);
		g.drawOval(1200, 270, 15 + x / 100, 15 + x / 100);
		g.drawOval(1205, 250, 20 + x / 100, 20 + x / 100);
		g.drawOval(1205, 230, 25 + x / 100, 25 + x / 100);
		g.drawOval(1210, 210, 30 + x / 100, 30 + x / 100);
		g.drawOval(1210, 190, 35 + x / 100, 35 + x / 100);
		g.drawOval(1215, 170, 40 + x / 100, 40 + x / 100);
		g.drawOval(1215, 150, 45 + x / 100, 45 + x / 100);
		g.drawOval(1220, 130, 50 + x / 100, 50 + x / 100);
		g.drawOval(1220, 110, 55 + x / 100, 55 + x / 100);
		g.drawOval(1225, 90, 60 + x / 100, 60 + x / 100);
		g.drawOval(1225, 70, 65 + x / 100, 65 + x / 100);

	}

	/**
	 * Draws the ramp the truck uses
	 * 
	 * @param g
	 */
	public static void drawRamp(Graphics g) {
		int locationX = 900;
		int locationY = 500;
		g.drawLine(locationX, locationY, locationX + 25, locationY - 15);
		g.drawRect(locationX + 25, locationY - 15, 25, 15);

	}

	/**
	 * Draws the clouds and sun used in the animation
	 * 
	 * @param g
	 * @param x2
	 * @param y
	 * @param t
	 */
	public static void drawClouds(Graphics g, int x2, int y, int t) {
		g.setColor(Color.YELLOW);
		g.drawOval(x2 + 725, y, 50, 50);
		g.setColor(Color.BLACK);
		g.drawOval(x2 + 800, y, 100, 50);
		g.drawOval(x2 + 700, y + 60, 100, 50);
		g.drawOval(x2 + 600, y, 100, 50);

	}

	/**
	 * Draws the building the truck hits
	 * 
	 * @param g
	 */
	public static void drawBuilding(Graphics g) {

		int fixedXPos = 1200;
		int fixedYPos = 200;
		g.drawRect(fixedXPos, fixedYPos, 200, 300);
		int countY = 0;
		int windowX = 1225;
		int windowY = 225;
		while (countY < 4) {
			g.drawRect(windowX, windowY, 40, 40);
			g.drawRect(windowX + 100, windowY, 40, 40);
			windowY = windowY + 70;
			countY++;
		}

	}

	/**
	 * Draws the truck that is running from the police car
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param t
	 */
	public static void drawTruck(Graphics g, int x, int y, int t) {
		// Once the truck hits the building, stop drawing the truck
		if (t > 10000) {
			return;
		}
		// Change y direction to represent the truck flying off the ramp
		if (t > 7000) {
			y = 425 - (t - 2000) / 20;
		}
		g.drawRect(x + 120, y + 290, 50, 30);
		g.drawRect(x + 170, y + 300, 20, 20);
		g.drawRect(x + 180, y + 300, 10, 10);
		g.drawRect(x + 185, y + 315, 5, 5);
		g.drawOval(x + 120, y + 320, 10, 10);
		g.drawOval(x + 135, y + 320, 10, 10);
		g.drawOval(x + 180, y + 320, 10, 10);
		g.drawString("Walmart", x + 123, y + 310);
	}

	/**
	 * Draws the police car that is chasing the truck
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param t
	 */
	public static void drawPoliceCar(Graphics g, int x, int y, int t) {
		// Alternate the colors to represent police lights
		if (t / 500 % 2 == 0) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.BLUE);
		}

		// Stop after the truck hits the ramp
		if (t > 7500) {
			x = 760;
		}
		g.drawRect(x + 30, y + 300, 40, 20);
		g.drawRect(x + 70, y + 310, 25, 10);
		g.drawRect(x + 10, y + 310, 20, 10);
		g.drawRect(x + 45, y + 295, 15, 5);
		g.drawOval(x + 15, y + 320, 10, 10);
		g.drawOval(x + 78, y + 320, 10, 10);
		g.drawString("Police", x + 33, y + 315);
	}

}
