package asteroids;

import java.awt.event.*;  
import java.util.Random;

import javax.swing.*;

import static asteroids.Constants.*;

/**
 * Controls a game of asteroids
 * 
 * @author Robert Weischedel && Makenzie Elliott
 */
public class Controller implements CollisionListener, ActionListener,
        KeyListener, CountdownTimerListener
{
    // Shared random number generator
    private Random random;

    // initializes the ship object 
    private Ship ship;
    
    // initialized the extra life heart object 
    private ExtraLives extraLife;
    
    // initializes the bullets
    private Bullet[] bullets = new Bullet[8];
    
    // Initializes the bullet count to 0 because no bullets have been fired yet
    private int bulletCount = 0;

    // When this timer goes off, it is time to refresh the animation
    private Timer refreshTimer;

    // Count of how many transitions have been made. This is used to keep two
    // conflicting transitions from being made at almost the same time.
    private int transitionCount;

    // Number of lives left
    private int lives;
    
    // displays and changes the score
    private int score;

    // The Game and Screen objects being controlled
    private Game game;
    private Screen screen;
    
    // Initializes the score you need to reach in order to reset the screen 
    private int gameClearingScore;
    
    // Initializes the asteroid speed so that it can be manipulated later
    private int asteroidSpeed;
    
    // booleans created to check whether the directional buttons are up or down
    private boolean rightDown, leftDown, upDown;
    

    /**
     * Constructs a controller to coordinate the game and screen
     */
    public Controller (Game game, Screen screen)
    {
        // Record the game and screen objects
        this.game = game;
        this.screen = screen;

        // Initialize the random number generator
        random = new Random();

        // Set up the refresh timer.
        refreshTimer = new Timer(FRAME_INTERVAL, this);
        transitionCount = 0;
        
        // Initializes the speed of the large asteroids 
        asteroidSpeed = 3;

        // Bring up the splash screen and start the refresh timer
        splashScreen();
        refreshTimer.start();
    }

    /**
     * Configures the game screen to display the splash screen
     */
    private void splashScreen ()
    {
        // Clear the screen and display the legend
        screen.clear();
        screen.setLegend("Asteroids");

        // Place four asteroids near the corners of the screen.
        placeAsteroids();

        // Make sure there's no ship
        ship = null;

    }

    /**
     * Get the number of transitions that have occurred.
     */
    public int getTransitionCount ()
    {
        return transitionCount;
    }

    /**
     * The game is over. Displays a message to that effect and enables the start
     * button to permit playing another game.
     */
    private void finalScreen ()
    {
        screen.setLegend(GAME_OVER);
        screen.removeCollisionListener(this);
        screen.removeKeyListener(this);
    }

    /**
     * Places four asteroids near the corners of the screen. Gives them random
     * velocities and rotations.
     */
    private void placeAsteroids ()
    {
        Participant a = new Asteroid(0, 2, EDGE_OFFSET, EDGE_OFFSET);
        a.setVelocity(asteroidSpeed, random.nextDouble() * 2 * Math.PI);
        a.setRotation(2 * Math.PI * random.nextDouble());
        screen.addParticipant(a);

        a = new Asteroid(asteroidSpeed, 2, SIZE - EDGE_OFFSET, EDGE_OFFSET);
        a.setVelocity(3, random.nextDouble() * 2 * Math.PI);
        a.setRotation(2 * Math.PI * random.nextDouble());
        screen.addParticipant(a);

        a = new Asteroid(asteroidSpeed, 2, EDGE_OFFSET, SIZE - EDGE_OFFSET);
        a.setVelocity(3, random.nextDouble() * 2 * Math.PI);
        a.setRotation(2 * Math.PI * random.nextDouble());
        screen.addParticipant(a);

        a = new Asteroid(asteroidSpeed, 2, SIZE - EDGE_OFFSET, SIZE - EDGE_OFFSET);
        a.setVelocity(3, random.nextDouble() * 2 * Math.PI);
        a.setRotation(2 * Math.PI * random.nextDouble());
        screen.addParticipant(a);
    }

    /**
     * Set things up and begin a new game.
     */
    private void initialScreen ()
    {
        // Clear the screen
        screen.clear();
        
        // Place four asteroids
        placeAsteroids();

        // Place the ship
        placeShip();
        
        // place the extra life
        placeHeart();
                
        // fills the array with bullets 
        for(int i = 0; i < bullets.length; i++){
            bullets[i] = new Bullet();
        }
        
        
        // Reset statistics
        lives = 3;
        score = 0;
        
        // resets the amount needed to clear the game screen and start a new level
        gameClearingScore = 2080;

        // Start listening to events. In case we're already listening, take
        // care to avoid listening twice.
        screen.removeCollisionListener(this);
        screen.removeKeyListener(this);
        screen.addCollisionListener(this);
        screen.addKeyListener(this);

        // Give focus to the game screen
        screen.requestFocusInWindow();
    }

    /**
     * Place a ship in the center of the screen.
     */
    private void placeShip ()
    {
        if (ship == null)
        {
            ship = new Ship();
        }
        ship.setPosition(SIZE / 2, SIZE / 2);
        ship.setRotation(-Math.PI / 2);
        // to stop the ship movement when new game button is pressed 
        ship.setVelocity(0, ship.getRotation());
        screen.addParticipant(ship);
    }
    
    /**
     * Creates a new heart object that grants the player an extra life if they 
     * collide with the participant 
     */
    private void placeHeart()
    {
        
        // if the ship exists it creates a new heart
        if(ship != null)
        {
            extraLife = new ExtraLives();
        }
        // sets the heart to show up in a random position on the screen 
        extraLife.setPosition(Math.random()*SIZE, Math.random()*SIZE);
        screen.addParticipant(extraLife);
    }

    /**
     * Deal with collisions between participants.
     */
    @Override
    public void collidedWith (Participant p1, Participant p2)
    {
        // These if statements handle asteroid and ship collisions  
        if (p1 instanceof Asteroid && p2 instanceof Ship)
        {
            asteroidCollision((Asteroid) p1);
            shipCollision((Ship) p2);
        }
        else if (p1 instanceof Ship && p2 instanceof Asteroid)
        {
            asteroidCollision((Asteroid) p2);
            shipCollision((Ship) p1);
        }
        
        //these if statements handle bullet and asteroid collisions 
        else if (p1 instanceof Bullet && p2 instanceof Asteroid)
        {
            asteroidCollision((Asteroid) p2);
            screen.removeParticipant(p1);
            if (bulletCount > 0)
                bulletCount--;
        }
        
        // These 4 if statements handle heart, ship and asteroid collisions.  
        // If ship collides with heart add one life, 
        // if asteroid collides with heart it disappears  
        else if (p1 instanceof Asteroid && p2 instanceof Bullet)
        {
            asteroidCollision((Asteroid) p1);
            screen.removeParticipant(p2);
            if(bulletCount > 0)
                bulletCount--;
        }
        else if (p1 instanceof ExtraLives && p2 instanceof Ship)
        {
            screen.removeParticipant(p1);
            lives++;
            game.setLives(lives);
            game.setLabel();
        }
        else if (p1 instanceof Ship && p2 instanceof ExtraLives)
        {
            screen.removeParticipant(p2);
            lives++;
            game.setLives(lives);
            game.setLabel();
        }
        else if (p1 instanceof ExtraLives && p2 instanceof Asteroid)
        {
            screen.removeParticipant(p1);
            
        }
        else if (p1 instanceof Asteroid && p2 instanceof ExtraLives)
        {
            screen.removeParticipant(p2);
       
        }
    }

    /**
     * The ship has collided with an asteroid, also generates Ship Debris Objects 
     */
    private void shipCollision (Ship s)
    {
        // Remove the ship from the screen and null it out
        screen.removeParticipant(s);
        
        
        // make three ship debris part object when the ship has hit an asteroid
        // creates object and gives it a timer, speed, and rotation.
        // It then adds the object to the screen
        for(int i = 0; i < 3; i++){
            ShipDebris parts = new ShipDebris();
            new CountdownTimer(this, parts, 1500);
            parts.setPosition(ship.getX(), ship.getY());
            parts.setVelocity(Math.random() * 2, random.nextDouble() * 2 * Math.PI);
            screen.addParticipant(parts);
        }
        
        // set ship t null object 
        ship = null;
        
        

        // Display a legend and make it disappear in one second
        screen.setLegend("Ouch!");
        new CountdownTimer(this, null, 1000);

        // Decrement lives
        lives--;
        
        // updates the game information labels
        game.setLives(lives);
        game.setLabel();

        // Start the timer that will cause the next round to begin.
        new TransitionTimer(END_DELAY, transitionCount, this);
    }

    /**
     * Ship or bullet has hit an asteroid object.  It will break up the large and medium sized
     * asteroids, update the game score, and increase the speed of the smaller asteroids. It also
     * creates debris from a destroyed asteroid. 
     */
    private void asteroidCollision (Asteroid a)
    {
        // The asteroid disappears
        screen.removeParticipant(a);
        
        // create 10 asteroidDebris objects every time an asteroid is destroyed, also set up a timer 
        // for them to vanish and gives them velocity and rotation.  It then adds the object to the screen
        for (int i = 0; i < 10; i++){
            AsteroidDebris debris = new AsteroidDebris();
            new CountdownTimer(this, debris, 1000);
            debris.setPosition(a.getX(), a.getY());
            debris.setVelocity(Math.random() * 2, random.nextDouble() * 2 * Math.PI);
            screen.addParticipant(debris);
        }
        
        // get the size and shape of current asteroid 
        int size = a.getSize();
        int speed = asteroidSpeed;
        
        // changes the score according to which asteroid was hit
        if(size == 2){
            score += 20;
            speed += 1;
        }
        else if(size == 1){
            score += 50;
            speed += 1;
        }
        else if(size == 0){
            score += 100;
        }
       
        // resets the game label and updates the score
        game.setScore(score);
        game.setLabel();
        
        
        // Create two smaller asteroids. Put them at the same position
        // as the one that was just destroyed and give them a random
        // direction.
        size = size - 1;
        if (size >= 0)
        {
            Asteroid a1 = new Asteroid(random.nextInt(4), size, a.getX(),
                    a.getY());
            Asteroid a2 = new Asteroid(random.nextInt(4), size, a.getX(),
                    a.getY());
            a1.setVelocity(speed, random.nextDouble() * 2 * Math.PI);
            a2.setVelocity(speed, random.nextDouble() * 2 * Math.PI);
            a1.setRotation(2 * Math.PI * random.nextDouble());
            a2.setRotation(2 * Math.PI * random.nextDouble());
            screen.addParticipant(a1);
            screen.addParticipant(a2);
        }
        
        // If all asteroids have been destroyed and the ship is still alive, reset the screen and begin
        // a new level 
        if(score == gameClearingScore){
         
            screen.removeParticipant(ship);
            ship = null;
            gameClearingScore += 2080;
            if (asteroidSpeed < SPEED_LIMIT){
                asteroidSpeed++;
            }
            
            //place four new large asteroids, and a new ship and heart object 
            placeAsteroids();
            placeShip();
            placeHeart();
           
        }
    }

    /**
     * This method will be invoked because of button presses and timer events.
     */
    @Override
    public void actionPerformed (ActionEvent e)
    {
        // The start button has been pressed. Stop whatever we're doing
        // and bring up the initial screen
        if (e.getSource() instanceof JButton)
        {
            transitionCount++;
            initialScreen();
            game.setLives(lives);
            game.setScore(score);
            game.setLabel();
        }

        // Time to refresh the screen
        else if (e.getSource() == refreshTimer)
        {
            
            // if the up key is pressed, move the ship 
            if (upDown == true){
                if (ship != null)
                {
                    ship.setVelocity(SPEED_LIMIT, ship.getRotation());
                    ship.accelerate(SPEED_LIMIT);

                }
            } 
            
            // if the right key is pressed, rotate the ship right
            if (rightDown == true){
                if (ship != null)
                {
                    //ship.setVelocity(SPEED_LIMIT, ship.getRotation());
                    ship.rotate(Math.PI / 16);
                }
            }
            
            // if the left key is pressed, rotate the ship left 
            if (leftDown == true){
                if (ship != null)
                {
                    //ship.setVelocity(SPEED_LIMIT, ship.getRotation());
                    ship.rotate(-Math.PI / 16);
                } 
            }
            
            
            // if they up key is released, give the ship friction to slow it down
            if (upDown == false){
                if (ship != null){
                    ship.friction();
                } 
            }
            
          
           
            // Refresh screen
            screen.refresh();
        
            
                  
        }
         
    }

    /**
     * Based on the state of the controller, transition to the next state.
     */
    public void performTransition ()
    {
        // Record that a transition was made. That way, any other pending
        // transitions will be ignored.
        transitionCount++;

        // If there are no lives left, the game is over. Show
        // the final screen.
        if (lives == 0)
        {
            finalScreen();
        }

        // The ship must have been destroyed. Place a new one and
        // continue on the current level
        else
        {
            placeShip();
        }
    }

    /**
     * Handles all key presses done by the user,  Handles all ship controls and firing of bullets 
     */
    @Override
    public void keyPressed (KeyEvent e)
    {
        
        // switch the boolean to true when the respective key is pressed or held down
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            leftDown = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            rightDown = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            upDown = true;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_T){
            
            if(ship!= null){
                ship.setPosition(Math.random() * SIZE, Math.random() * SIZE);
          }
        }
        
        // Handles firing of the bullets by first pulling an bullet object out of the bullets array,
        // then it gives that bullet a position, velocity and the bullet to the screen 
        
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            if (ship != null)
            { 
                if (bulletCount < bullets.length)
                {
                    //Bullet bullet = new Bullet();
                    new CountdownTimer(this, bullets[bulletCount], BULLET_DURATION);
                    bullets[bulletCount].setPosition(ship.getXNose(),
                            ship.getYNose());
                    bullets[bulletCount].setVelocity(BULLET_SPEED,
                            ship.getRotation());
                    screen.addParticipant(bullets[bulletCount]);
                    bulletCount++;
                }
                
            }
        }
    }

    /**
     * Handles when the key's are released by setting the booleans back to false
     */
    @Override
    public void keyReleased (KeyEvent e)
    {
        // if the key is released set the respective boolean variable to false 
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            leftDown = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            rightDown = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            upDown = false;
        }
    }

    @Override
    public void keyTyped (KeyEvent e)
    {
    }

    /**
     * Callback for countdown timer. Used to create transient effects.
     */
    @Override
    public void timeExpired (Participant p)
    {
        if (p == null){
            screen.setLegend("");
        }
        
        // if the participant is a bullet remove it from the screen, if bullet is last in array, 
        // reset bulletCount. 
        else if (p == bullets[0] || p == bullets[1] || p == bullets[2] ||
                p == bullets[3] ||p == bullets[4] || p == bullets[5] || 
                p == bullets[6] || p == bullets[7]){
            if (p == bullets[7]){
                bulletCount = 0;
            }
            screen.removeParticipant(p);
        }
        
        // if participant is AsteroidDebris remove it from the screen 
        else if (p instanceof AsteroidDebris){
            screen.removeParticipant(p);
        }
        
        // If Participant is ShipDebris remove it from the screen 
        else if (p instanceof ShipDebris){
            screen.removeParticipant(p);
        }
    }
}
