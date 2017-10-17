package asteroids;

import javax.swing.*;
import java.awt.event.*;

/**
 * Provides objects that wait for a certain amount of time to pass before making
 * a callback to a CountdownTimerListener.
 * 
 * @author Joe Zachary
 */
public class CountdownTimer implements ActionListener
{
    // The participant that will be passed back in the callback
    private Participant p;

    // Internal timer
    private Timer timer;

    // Object that is to be notified when time expires
    private CountdownTimerListener listener;

    /**
     * Constructs an object that waits for the given number of milliseconds to
     * pass before invoking the timeExpired method on the listener and passing
     * back the provided participant as a parameter.
     */
    public CountdownTimer (CountdownTimerListener listener, Participant p,
            int msecs)
    {
        this.listener = listener;
        this.p = p;
        timer = new Timer(msecs, this);
        timer.start();
    }

    /**
     * When the interval has passed, stops the timer and makes the callback.
     */
    @Override
    public void actionPerformed (ActionEvent e)
    {
        timer.stop();
        listener.timeExpired(p);
    }
}
