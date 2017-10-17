package asteroids;

import java.awt.Shape;
import java.awt.geom.*;

/**
 * Represents ship objects
 * 
 * @author Joe Zachary
 */
public class Ship extends Participant
{
    // The outline of the ship
    private Shape outline;

    // Constructs a ship
    public Ship ()
    {
        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(20, 0);
        poly.lineTo(-20, 12);
        poly.lineTo(-12, 0);
        poly.lineTo(-20, -12);
        poly.closePath();
        outline = poly;
    }

    /**
     * Returns the x-coordinate of the point on the screen where the ship's nose
     * is located.
     */
    public double getXNose ()
    {
        Point2D.Double point = new Point2D.Double(20, 0);
        transformPoint(point);
        return point.getX();
    }

    /**
     * Returns the x-coordinate of the point on the screen where the ship's nose
     * is located.
     */
    public double getYNose ()
    {
        Point2D.Double point = new Point2D.Double(20, 0);
        transformPoint(point);
        return point.getY();
    }

    /**
     * Returns the outline of the ship.
     */
    @Override
    protected Shape getOutline ()
    {
        return outline;
    }

    /**
     * Customizes the base move method by imposing friction
     */
    @Override
    public void move ()
    {
        super.move();
        friction();
    }
}