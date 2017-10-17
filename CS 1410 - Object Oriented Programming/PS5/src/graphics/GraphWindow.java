// Written by Joe Zachary for CS 1410, September 2011

package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cs1410.Grapher;

/**
 * Provides a top-level animation window
 */
@SuppressWarnings("serial")
public class GraphWindow extends JFrame implements WindowListener
{
	private DrawingArea area; // JPanel that contains graph

	private ArrayList<String> column1;
	private ArrayList<Integer> column2;
	private ArrayList<String> categories;
	private ArrayList<Color> colors;
	private int operation;
	private boolean usePieChart;

	/**
	 * Constructs a custom CS 1410 graph window. The parameters are ultimately
	 * passed through to Grapher.drawGraph. Look there for more documentation.
	 */
	public GraphWindow (ArrayList<String> column1, ArrayList<Integer> column2,
			ArrayList<String> categories, ArrayList<Color> colors,
			int operation, boolean usePieChart)
	{
		this.column1 = column1;
		this.column2 = column2;
		this.categories = categories;
		this.colors = colors;
		this.operation = operation;
		this.usePieChart = usePieChart;

		setTitle("CS 1410 Graph Window");
		setSize(400, 400);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		area = new DrawingArea();
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(area, "Center");
		setContentPane(panel);
	}

	private class DrawingArea extends JPanel
	{
		/**
		 * Coordinates the drawing of a graph
		 */
		@Override
		public void paintComponent (Graphics g)
		{
			try
			{
				g.clearRect(0, 0, getWidth(), getHeight());
				Grapher.drawGraph(g, column1, column2, categories, colors,
						operation, usePieChart);
			}
			catch (RuntimeException e)
			{
				GraphWindow.this.processWindowEvent(
		                new WindowEvent(GraphWindow.this, WindowEvent.WINDOW_CLOSING));
				throw e;
			}
		}
	}

	@Override
	public void windowActivated (WindowEvent arg0)
	{	
	}

	@Override
	public void windowClosed (WindowEvent arg0)
	{
	}

	@Override
	public void windowClosing (WindowEvent arg0)
	{
	}

	@Override
	public void windowDeactivated (WindowEvent arg0)
	{
	}

	@Override
	public void windowDeiconified (WindowEvent arg0)
	{	
	}

	@Override
	public void windowIconified (WindowEvent arg0)
	{	
	}

	@Override
	public void windowOpened (WindowEvent arg0)
	{
	}
}
