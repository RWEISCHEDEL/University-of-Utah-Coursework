package asteroids;

import javax.swing.*;
import java.awt.event.*;

/**
 * For timing transitions between states (splash screen, next screen, final
 * screen) in the controller. When the timer is created, it is given the current
 * transition count. This is incremented inside the controller with each
 * transition. If the timer goes off and the transition count is different, the
 * callback is not performed as it is out of date.
 * 
 * @author Joe Zachary
 */
public class TransitionTimer implements ActionListener
{
    // Transition count when this object was created
    private int transitionCount;

    // Controller that contains callback method
    private Controller controller;

    // Underlying timer
    private Timer timer;

    /**
     * Creates a TransitionTimer.
     */
    public TransitionTimer (int msecs, int transitionCount,
            Controller controller)
    {
        this.transitionCount = transitionCount;
        this.controller = controller;
        timer = new Timer(msecs, this);
        timer.addActionListener(this);
        timer.start();
    }

    /**
     * Makes a callback as long as the transition count hasn't changed.
     */
    @Override
    public void actionPerformed (ActionEvent e)
    {
        timer.stop();
        if (controller.getTransitionCount() == transitionCount)
        {
            controller.performTransition();
        }
    }
}
