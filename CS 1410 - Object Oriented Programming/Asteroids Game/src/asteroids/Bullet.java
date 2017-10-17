package asteroids;

import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
/**
 * This class serves as the constructor for a single bullet piece, and extends the Participant Class so
 * that every single bullet piece can use its associated methods. 
 * @author Robert Weischedel && Makenzie Elliott
 *
 */
public class Bullet extends Participant
{
 // The outline of the bullet
    private Shape outline;

    // Constructs a single bullet
    public Bullet ()
    {
        
        //draw a single bullet piece 
        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(-1, 0);
        poly.lineTo(0, 0);
        poly.lineTo(0, -1);
        poly.lineTo(-1, -1);
        poly.closePath();
        outline = poly;
    }


    /**
     * Returns the outline of the bullet.
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
