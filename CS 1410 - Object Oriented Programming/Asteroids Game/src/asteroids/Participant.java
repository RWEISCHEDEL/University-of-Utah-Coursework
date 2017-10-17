package asteroids;

import java.awt.*;
import java.awt.geom.*;
import static asteroids.Constants.*;

/**
 * Represents a single moving element in an asteroids game. This is an abstract
 * class, so it can be used only by extending it and implementing the abstract
 * getShape() method in the derived class.
 * 
 * @author Joe Zachary
 */
abstract public class Participant
{
    // Speed in pixels per second in the horizontal (x) and vertical (y)
    // directions
    private double speedX, speedY;

    // Amount by which element is rotated in radians
    private double rotation;

    // Current offset of center from initial position
    private double x, y;

    // Current (transformed) border of element
    private Shape border;

    /**
     * Constructs an empty participant
     */
    protected Participant ()
    {
        speedX = 0;
        speedY = 0;
        rotation = 0;
        x = 0;
        y = 0;
        border = null;
    }

    /**
     * Sets the two components of the participant's velocity. The speed is in
     * pixels per frame refresh and the direction is in radians.
     */
    public void setVelocity (double speed, double direction)
    {
        speedX = Math.cos(direction) * speed;
        speedY = Math.sin(direction) * speed;
    }

    /**
     * Sets the rotation (in radians) of the participant
     */
    public void setRotation (double radians)
    {
        rotation = radians;
    }

    /**
     * Rotates the participant by delta radians.
     */
    public void rotate (double delta)
    {
        rotation += delta;
    }

    /**
     * Gets the current rotation of the participant
     * 
     * @return
     */
    public double getRotation ()
    {
        return rotation;
    }

    /**
     * Accelerates in the direction that the participant is oriented.
     * Participants cannot accelerate beyond the speed limit.
     */
    public void accelerate (double delta)
    {
        double deltaX = delta * Math.cos(rotation);
        double deltaY = delta * Math.sin(rotation);
        speedX += deltaX;
        speedY += deltaY;
        if (Math.sqrt(speedX * speedX + speedY * speedY) > SPEED_LIMIT)
        {
            speedX -= deltaX;
            speedY -= deltaY;
        }
    }

    /**
     * Simulates friction by accelerating the participant opposite to its
     * direction of motion.
     */
    public void friction ()
    {
        if (speedX != 0 || speedY != 0)
        {
            double deltaX = FRICTION * speedX
                    / Math.sqrt(speedX * speedX + speedY * speedY);
            double deltaY = FRICTION * speedY
                    / Math.sqrt(speedX * speedX + speedY * speedY);
            if (Math.abs(deltaX) > Math.abs(speedX)
                    || Math.abs(deltaY) > Math.abs(speedY))
            {
                speedX = 0;
                speedY = 0;
            }
            else
            {
                speedX += deltaX;
                speedY += deltaY;
            }
        }
    }

    /**
     * Sets the position of the center of the participant
     */
    public void setPosition (double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x coordinate of the center of the participant
     */
    public double getX ()
    {
        return x;
    }

    /**
     * Gets the y coordinate of the center of the participant
     */
    public double getY ()
    {
        return y;
    }

    /**
     * This should be overridden by any derived class to return a Shape object
     * that describes the outline of the participant. The center of the Shape
     * should be at coordinate (0,0). The center is the reference used when the
     * Shape is moved or rotated. This method is called frequently, so it should
     * be efficient. The Shape that is returned will not be modified, so it can
     * be cached.
     */
    abstract Shape getOutline ();

    /**
     * Moves this participant to reflect one tick of the clock.
     */
    public void move ()
    {

        // Get the original outline
        Shape original = getOutline();

        // Change the position to reflect participant motion
        x += speedX;
        y += speedY;

        // Translate and rotate the original to reflect the accumulated motion
        AffineTransform trans = AffineTransform.getTranslateInstance(x, y);
        trans.concatenate(AffineTransform.getRotateInstance(rotation));
        border = trans.createTransformedShape(original);

        // If the element has gone sufficiently far out of bounds, move it to
        // the
        // other side of the screen. This change will take effect next time.
        Rectangle2D bounds = border.getBounds2D();
        if (bounds.getMaxX() < 0)
        {
            x += SIZE + (bounds.getMaxX() - bounds.getMinX());
        }
        if (bounds.getMinX() >= SIZE)
        {
            x += -SIZE - (bounds.getMaxX() - bounds.getMinX());
        }
        if (bounds.getMaxY() < 0)
        {
            y += SIZE + (bounds.getMaxY() - bounds.getMinY());
        }
        if (bounds.getMinY() >= SIZE)
        {
            y += -SIZE - (bounds.getMaxY() - bounds.getMinY());
        }
    }

    /**
     * Transforms the point just like the participant is transformed before it
     * is displayed. This can be used to figure out where some point of the
     * participant is going to be located after it is transformed. (This can be
     * useful for computing the tip of a ship, for example.)
     */
    public void transformPoint (Point2D.Double point)
    {
        AffineTransform trans = AffineTransform.getTranslateInstance(x, y);
        trans.concatenate(AffineTransform.getRotateInstance(rotation));
        trans.transform(point, point);
    }

    /**
     * Reports whether this participant overlaps with p.
     */
    public boolean overlaps (Participant p)
    {
        Area a = new Area(border);
        a.intersect(new Area(p.border));
        return !a.isEmpty();
    }

    /**
     * Draws this participant
     */
    public void draw (Graphics2D g)
    {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (border == null)
        {
            border = getOutline();
        }
        g.draw(border);
    }
}
