package asteroids;

import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * This class serves as the constructor for a single piece of ship debris of random size,
 * and extends the Participant Class that every single piece of ship debris can use its associated methods.  
 * @author Robert Weischedel && Makenzie Elliott
 *
 */
public class ShipDebris extends Participant
{
    // The outline of the ShipDebris 
    private Shape outline;

    // Constructs a ShipDebris 
    public ShipDebris ()
    {
        // draw a single piece of ShipDebris 
        double size = Math.random() * 20;
        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(-1, 0);
        poly.lineTo(size, size);
        poly.closePath();
        outline = poly;
    }

    /**
     * Returns the outline of the ship debris.
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
    }

}
