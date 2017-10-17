package asteroids;

import javax.swing.*; 
import java.awt.*;
import static asteroids.Constants.*;

/**
 * Implements the asteroid game.
 * 
 * @author Roberb Weischedel && Makenzie Elliott
 *
 */
public class Game extends JFrame
{
    private JLabel scoreAndLives;
    private int theScore;
    private int theLives;
    /**
     * Launches the game
     */
    public static void main (String[] args)
    {
        Game a = new Game();
        a.setVisible(true);
    }

    /**
     * Lays out the game and creates the controller
     */
    public Game ()
    {
        // Title at the top
        setTitle(TITLE);

        // Default behavior on closing
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main playing area and the controller
        Screen screen = new Screen();
        Controller controller = new Controller(this, screen);

        // This panel contains the screen to prevent the screen from being
        // resized
        JPanel screenPanel = new JPanel();
        screenPanel.setLayout(new GridBagLayout());
        screenPanel.add(screen);

        // The control panel is created to hold the controls of the game 
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 2));
        

        // the control panel contains the Start button, score and lives. 
        JButton startGame = new JButton(START_LABEL);
        controlPanel.add(startGame);
        scoreAndLives = new JLabel("");
        controlPanel.add(scoreAndLives);

        // Organize everything on the main Panel 
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(screenPanel, "Center");
        mainPanel.add(controlPanel, "North");
        setContentPane(mainPanel);
        pack();

        // Connect the controller to the start button
        startGame.addActionListener(controller);
    }
    
    /**
     * Allows you to update the GUI so that it reflects the current score and lives. 
     */
    public void setLabel(){
        scoreAndLives.setText("Lives: " + theLives + "  Score: "+ theScore);
    }
  
    /**
     * Allows you to update the lives from outside the GUI
     * @param n
     */
    public void setLives(int n){
        theLives = n;
    }
    
    /**
     * Allows you to update the score from outside the GUI
     * @param m
     */
    public void setScore(int m){
        theScore = m;
    }
}
