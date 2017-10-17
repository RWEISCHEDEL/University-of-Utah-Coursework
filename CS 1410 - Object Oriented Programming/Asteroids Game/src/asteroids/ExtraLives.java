package asteroids;

import java.awt.Shape;
import java.awt.geom.Path2D;

/**
 * This class acts as a constructor for a heart(extra life) object. It extends the participant class so
 * ExtraLives can use the associated methods 
 * @author Robert Weischedel && Makenzie Elliott
 *
 */
public class ExtraLives extends Participant
{

    // The outline of the heart
    private Shape outline;

    // Constructs a heart
    public ExtraLives ()
    {
        Path2D.Double poly = new Path2D.Double();
        poly.moveTo(0, 0);
        poly.lineTo(7, -7);
        poly.lineTo(9, -5);
        poly.lineTo(9, 0);
        poly.lineTo(0, 9);
        poly.lineTo(-9, 0);
        poly.lineTo(-9, -5);
        poly.lineTo(-7, -7);
        poly.closePath();
        outline = poly;
    }

    /**
     * Returns the outline of the heart.
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
