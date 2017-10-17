package asteroids;

/**
 * Must be implemented by classes that wish to receive callbacks from a
 * CountdownTimer.
 * 
 * @author Joe Zachary
 */

public interface CountdownTimerListener
{
    /**
     * Called when a countdown timer reaches zero
     */
    public void timeExpired (Participant p);
}
