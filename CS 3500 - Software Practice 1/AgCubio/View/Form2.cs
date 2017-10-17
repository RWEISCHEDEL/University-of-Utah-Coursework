// Written by Robert Weischedel and Jackson Murphy for CS 3500. Last updated November 17, 2015
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using AgCubio;
using Newtonsoft.Json;
using System.Net.Sockets;




namespace AgCubio
{
    /// <summary>
    /// A window that displays the AgCubio game. This form is created and opened by this
    /// View's Form1. Upon opening, this form attempts to connect to the game server.
    /// If the server connection is successful, the game begins and is played on this 
    /// form. If the player dies, this form opens this view's Form3, which is the Game Over form.
    /// If this form is closed in the middle of the game, Form1 reappears.
    /// 
    /// This form incorporates a view port that can be set to either have a constant scale factor,
    /// or to scale as a function of the overall size of the player's cubes. 
    /// </summary>
    public partial class Form2 : Form
    {
        // This form's member variables

        private System.Drawing.SolidBrush myBrush;

        private String serverName;

        private String playerName;

        private String partiallyReceivedCube = "";

        private Socket socket;

        private Form1 parentForm;

        private int team_id;

        private int mainPlayerCubeId;
        private int mainPlayerCubeWidth;

        private int scaledMainPlayerXPos;
        private int scaledMainPlayerYPos;

        private int unscaledMainPlayerXPos;
        private int unscaledMainPlayerYPos;

        private int spatialWidthOfPlayerCubes = 1; // initialize to 1

        private int smallestXOfPlayerCube = int.MaxValue;
        private int largestXOfPlayerCube = 0;
        private int smallestYOfPlayerCube = int.MaxValue;
        private int largestYOfPlayerCube = 0;

        private const int SCALE_FACTOR = 1;
        private const bool useConstantScaling = true;


        private World world;
        private const int WORLD_HEIGHT = 1000;
        private const int WORLD_WIDTH = 1000;

        private System.Diagnostics.Stopwatch stopwatch;
        private long singleFrameElapsedTime;
        private int framesPerSecond;
        private int lastRecordedPositiveFPS = 1; // initialize to 1

        private int totalPlayerMass;

        private int allTimeHighMass;
        
        private bool GameOver;

        private System.Diagnostics.Stopwatch totalPlayTimer;
        private long totalPlayTime;

        /// <summary>
        /// Constructs a new Form2 (Default constructor)
        /// </summary>
        public Form2()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Constructs a new Form2. This constructor is called in Form1.
        /// </summary>
        public Form2(String playerName, String serverName, Form1 form1)
        {
            // Whether or not the player has died
            GameOver = false;

            // The combined mass of all the player's cubes
            totalPlayerMass = 0;

            // For enhanced graphics
            this.DoubleBuffered = true;

            this.playerName = playerName;

            this.serverName = serverName;

            this.parentForm = form1;

            this.world = new World("C:\\Users\\weisched\\Source\\Repos\\JacksonRepo\\AgCubio\\Resources\\XMLWorldParameters.xml");

            InitializeComponent();
        }

        /// <summary>
        /// Called once the form loads.
        /// </summary>
        private void Form2_Load(object sender, EventArgs e)
        {
            // At the end of the game, we show the player how long their game lasted.
            // We use a timer to do this, and we start it here.
            totalPlayTimer = new System.Diagnostics.Stopwatch();
            totalPlayTimer.Reset();
            totalPlayTimer.Start();

            // Try to connect to the game server on a background thread.
            backgroundWorker1.RunWorkerAsync();
        }

        /// <summary>
        /// Called every time the screen needs to be repainted. This method is called very often
        /// and is invoked within itself.
        /// </summary>
        private void Form2_Paint(object sender, PaintEventArgs e)
        {
            // If the player has died
            if (GameOver)
            {
                totalPlayTimer.Stop();

                totalPlayTime = (totalPlayTimer.ElapsedMilliseconds / 1000);

                this.Close();
            }

            // To calculate frames per second, we measure how long it takes to paint 1 frame.
            stopwatch = new System.Diagnostics.Stopwatch();
            stopwatch.Reset();
            stopwatch.Start();

            // Important to lock the world object because cubes are being added to the world
            // in the processedReceivedData() method below.
            lock (world)
            {
                // Each time we paint the screen, we calculate the player's total mass
                // We are initializing the variable here.
                totalPlayerMass = 0;

                foreach (Cube cube in world.Cubes)
                {
                    // If the cube belongs to the player, increment the total player mass and
                    // update the X,Y extremes of the player's cubes
                    if (cube.food == false && (cube.team_id == team_id || cube.uid == team_id))
                    {
                        totalPlayerMass += (int)cube.Mass;

                        UpdateSpatialWidthOfPlayerCubes(cube, ref smallestXOfPlayerCube, ref smallestYOfPlayerCube, ref largestXOfPlayerCube, ref largestYOfPlayerCube);
                    }


                    // The next few blocks of code convert the positions of all the cubes to 
                    // create a "view port" by which the player sees only a subsection of the
                    // world.
                    int cubeXPos, cubeYPos, width;

                    if (useConstantScaling == true)
                    {
                        ScaleCubeCoordinatesWithConstantScaling(cube, out cubeXPos, out cubeYPos);

                        // We must scale the width of each cube in addition to its position
                        width = cube.Width * SCALE_FACTOR;

                    }

                    else // if using dynamic scaling
                    {

                        int dynamicScaleFactor;
                        ScaleCubeCoordinatesWithDynamicScaling(cube, out cubeXPos, out cubeYPos, out dynamicScaleFactor);

                        // We must scale the width of each cube in addition to its position
                        width = cube.Width * dynamicScaleFactor;

                    }

                    // The field of play looks good when the food size is always 3.
                    if (cube.food == true && cube.team_id != -1)
                    {
                        width = 3;
                    }

                    // The cube will be painted in this color
                    Color color = Color.FromArgb(cube.argb_color);
                    myBrush = new SolidBrush(color);

                    // Paint the cube onto the screen
                    e.Graphics.FillRectangle(myBrush, new Rectangle(cubeXPos - width / 2, cubeYPos - width / 2, width, width));

                    // Write the player name on the cube
                    if (cube.food == false)
                    {
                        RectangleF rect = new RectangleF(cubeXPos - width / 2, cubeYPos - width / 2, width, width);

                        float nameWidth = width / 5 + 1; // add 1 b/c it can never equal 0

                        // Write the player name on the cube. Yellow seems to be the best color.
                        e.Graphics.DrawString(cube.Name, new Font(new FontFamily("Arial"), nameWidth, FontStyle.Regular, GraphicsUnit.Pixel), new SolidBrush(Color.Yellow), rect);
                    }
                }
            }

            // At the end of the game, we show the player their peak mass. Here, we update
            // the peak mass if the mass calculated in this frame surpasses the previous peak.
            if (totalPlayerMass > allTimeHighMass)
                allTimeHighMass = totalPlayerMass;

            // Measure how long it took to paint this frame.
            stopwatch.Stop();

            singleFrameElapsedTime = stopwatch.ElapsedMilliseconds;

            // Convert the time for one frame to FPS
            framesPerSecond = (int)(((double)1.0 / singleFrameElapsedTime) * 1000);

            // Sometimes the FPS is negative (likely due to timer imprecision). If that's the
            // case, we want to instead show a recent positive FPS
            if (framesPerSecond > 0)
                lastRecordedPositiveFPS = framesPerSecond;
            else
                framesPerSecond = lastRecordedPositiveFPS;

            // Update labels that continuously show the player various statistics during the game.
            PlayerInfoFPSDataLabel.Text = framesPerSecond.ToString();

            PlayerInfoMassDataLabel.Text = totalPlayerMass.ToString();

            // Refreshing the labels seems to be necessary to avoid losing them on the screen
            PlayerInfoFPSLabel.Refresh();
            PlayerInfoFPSDataLabel.Refresh();
            PlayerInfoMassDataLabel.Refresh();
            PlayerInfoMassLabel.Refresh();

            // This calls Paint again
            Invalidate();
        }

        /// <summary>
        /// Called when the player presses a key. If the key was the spacebar, we send a 
        /// "Split cube" request to the game server.
        /// </summary>
        private void Form2_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == (char)Keys.Space)
            {
                // Call our helper method
                sendMoveOrSplit("Split");
            }
        }

        /// <summary>
        /// Called when the form is closing.
        /// </summary>

        private void Form2_FormClosing(object sender, FormClosingEventArgs e)
        {
            // Try to gracefully disconnect from the game server
            if (socket != null)
            {
                if(socket.Connected == true)
                {
                    socket.Shutdown(SocketShutdown.Both);

                    socket.Close();
                }
                
            }


            // If the player died, we call the Form1 helper method that will display the 
            // end-of-game stats.
            if (GameOver)
            {
                parentForm.openForm3(allTimeHighMass, totalPlayTime);

            }

            // If the form is closed before the player dies, we just take the player back to the
            // welcome screen
            else
            {
                parentForm.Show();
            }
        }


        // Helper Methods


        /// <summary>
        /// Converts a cube's X and Y coordinates according to this form's Scale factor. This is 
        /// essential for the subwindow feature of the game. The steps of this method are:
        /// 1. Multiply the x and y position of each cube by the scale factor
        /// 2. Subtract from each cube's scaled x and y coordinates the x and y scaled coordinates
        ///     of the main player cube (this puts the main player cube in the top left corner of
        ///     the form.
        /// 3. Add to each cube's x and y coordinates: (form width / 2) and (form height / 2), 
        ///     respectively. (This puts the main player cube at the center of the form)
        /// </summary>
        private void ScaleCubeCoordinatesWithConstantScaling(Cube cube, out int scaledXPos, out int scaledYPos)
        {

            // We always show the player's main cube in the center of the screen. To do this, we
            // must save the main cube's scaled coordinates 
            if (cube.uid == mainPlayerCubeId)
            {
                unscaledMainPlayerXPos = (int)cube.loc_x;

                unscaledMainPlayerYPos = (int)cube.loc_y;

                // Multiply the x and y coordinates of the main player cube by the scale factor
                scaledMainPlayerXPos = scaledXPos = (int)(cube.loc_x) * SCALE_FACTOR;

                scaledMainPlayerYPos = scaledYPos = (int)(cube.loc_y) * SCALE_FACTOR;
            }

            else
            {
                // Multiply the x and y coordinates of the cube by the scale factor
                scaledXPos = (int)(cube.loc_x) * SCALE_FACTOR;
                scaledYPos = (int)(cube.loc_y) * SCALE_FACTOR;
            }


            // subtract the main player's x and y position from this cube's x and y position
            scaledXPos -= scaledMainPlayerXPos;

            scaledYPos -= scaledMainPlayerYPos;

            // finally, add back half of this form's width and height.
            scaledXPos += this.Width / 2;

            scaledYPos += this.Height / 2;


        }

        /// <summary>
        /// Converts a cube's X and Y coordinates according to this form's Scale factor. This is
        ///  essential for the subwindow feature of the game. Here, the scale factor is not a 
        /// constant, but rather a function of the area occupied by all the player's cubes.
        /// The steps are:
        /// 1. Multiply the x and y position of each cube by the scale factor
        /// 2. Subtract from each cube's scaled x and y coordinates the x and y scaled coordinates
        ///     of the main player cube (this puts the main player cube in the top left corner of
        ///     the form.
        /// 3. Add to each cube's x and y coordinates: (form width / 2) and (form height / 2), 
        ///     respectively. (This puts the main player cube at the center of the form)
        /// 
        /// This method will be called en lieu of the ScaleCubeCoordinatesWithConstantScaling()
        /// if this form's variable "useConstantScaling" == false
        /// </summary>
        private void ScaleCubeCoordinatesWithDynamicScaling(Cube cube, out int scaledXPos, out int scaledYPos, out int dynamicScaleFactor)
        {
            // Find out how much space the team of cubes takes up
            int spatialXWidthOfPlayerCubes = largestXOfPlayerCube - smallestXOfPlayerCube;
            int spatialYWdithOfPlayerCubes = largestYOfPlayerCube - smallestYOfPlayerCube;

            // use the greater of the two distances to calculate the scale factor
            int playerCubesWidth = Math.Max(spatialXWidthOfPlayerCubes, spatialYWdithOfPlayerCubes);

            if (playerCubesWidth == 0) // case where player has only 1 cube
                dynamicScaleFactor = 200 / mainPlayerCubeWidth;

            else
                dynamicScaleFactor = 1000 / (playerCubesWidth) + 1; // add 1 so it's never 0. 

            // We always show the player's main cube in the center of the screen. To do this, we
            // must save the main cube's scaled coordinates 
            if (cube.uid == mainPlayerCubeId)
            {
                unscaledMainPlayerXPos = (int)cube.loc_x;

                unscaledMainPlayerYPos = (int)cube.loc_y;

                scaledMainPlayerXPos = scaledXPos = (int)(cube.loc_x) * dynamicScaleFactor;

                scaledMainPlayerYPos = scaledYPos = (int)(cube.loc_y) * dynamicScaleFactor;
            }

            else
            {
                // Multiply the x and y coordinates of the cube by the scale factor
                scaledXPos = (int)(cube.loc_x) * dynamicScaleFactor;
                scaledYPos = (int)(cube.loc_y) * dynamicScaleFactor;
            }


            // subtract the main player's x and y position from this cube's x and y position
            scaledXPos -= scaledMainPlayerXPos;

            scaledYPos -= scaledMainPlayerYPos;


            // finally, add back half of this form's width and height.
            scaledXPos += this.Width / 2;

            scaledYPos += this.Height / 2;


        }




        /// <summary>
        /// This is a helper method for determining the scale factor for the view port. The cube parameter is one of
        ///  the player's cubes. When the player has multiple cubes, we scale based on the total width of the 
        /// player's cubes, rather than just the width of the primary player cube. 
        /// The last 4 parameters are the smallest X coord value, smallest Y coord value, largest X coord value, and
        /// largest Y coord value of the player cubes. If there is only one player cube, smallX and largeX are 
        /// equivalent (they're just the X coordinate of the player cube), and likewise for smallY and largeY.
        /// </summary>
        private void UpdateSpatialWidthOfPlayerCubes(Cube cube, ref int smallX, ref int smallY, ref int largeX, ref int largeY)
        {
            int cubeX = (int)cube.loc_x;
            int cubeY = (int)cube.loc_y;

            if (cubeX < smallX)
                smallX = cubeX;

            if (cubeX > largeX)
                largeX = cubeX;

            if (cubeY < smallY)
                smallY = cubeY;

            if (cubeY > largeY)
                largeY = cubeY;
        }

        /// <summary>
        /// The first callback function to be executed upon connecting with the game server.
        /// It's also called if we fail to connect with the server, in which case we close down
        /// this form and give the player a error message.
        /// 
        /// If the connection is successful, we send the server our player name and then start
        /// receiving data from the server.
        /// </summary>
        /// <param name="state"></param>
        private void sendPlayerName(PreservedState state)
        {
            if (state.socket.Connected == false)
            {
                MessageBox.Show("Unable to connect to requested server.");
                MethodInvoker closeWindow = new MethodInvoker(() => this.Close());

                this.Invoke(closeWindow);
            }
            else
            {
                Network.Send(state.socket, playerName + '\n');

                // replace this callback with another callback in the preserved state object
                state.callBack = processReceivedData;

                // Ask for more data from the server
                Network.i_want_more_data(state);
            }

            
        }

        /// <summary>
        /// The second and final callback function to be called upon receiving data from the game server.
        /// Here we convert a string containing information for cubes into Cube objects. We process these
        /// cubes into our world and then ask the server for more cube strings.
        /// </summary>
        /// <param name="state"></param>
        private void processReceivedData(PreservedState state)
        {

            // Pull out the string containing cube info from the state parameter
            String stringFromServer = state.strBuilder.ToString();

            if (stringFromServer.Length > 0)
            {
                // Cubes are divided by newline characters in the string
                String[] cubes = stringFromServer.Split('\n');

                String lastCube = cubes[cubes.Length - 1];

                // if the last string received from the server ended with a partial cube, we need to 
                // prepend this to the first cube in the current batch of cubes
                if (partiallyReceivedCube != "")
                {
                    cubes[0] = partiallyReceivedCube + cubes[0];

                    partiallyReceivedCube = "";
                }

                // if the info of the last cube in the message was incomplete.
                if (lastCube != "")
                {
                    // save away the partial cube info
                    partiallyReceivedCube = lastCube;

                }

                // Remove the last element of the array. If the last cube was incomplete,
                // it's safe to delete the last element because we already saved. If the last cube was
                // complete, it's still safe to delete the last element because it will contain an empty string
                cubes = cubes.Take(cubes.Length - 1).ToArray();

                // Must lock because the world object is being used in the Paint method above
                lock (world)
                {
                    // Loop through string looking for all \n
                    // Pull each \n terminated String and convert to Cube
                    foreach (String item in cubes)
                    {
                        Cube cube = JsonConvert.DeserializeObject<Cube>(item);

                        // The first cube we receive is the player's cube
                        if (world.Cubes.Count == 0)
                        {
                            // The team_id is used to identify all cubes belonging to the player
                             team_id = cube.uid;

                            // For the view port scaling, we want to keep track of the main player cube
                            mainPlayerCubeId = cube.uid;

                        }

                        if (cube.uid == mainPlayerCubeId)
                        {
                            mainPlayerCubeWidth = cube.Width;

                            // If the main player's cube has 0 mass, we're dead. The server smartly 
                            // sets the main player cube to another player cube if the main player 
                            // cube dies after a split but other player cube(s) remain.
                            if (cube.Mass == 0)
                            {
                                GameOver = true;
     
                            }

                        }

                        // If this cube hasn't been added to the world yet, add it. 
                        world.AddOrUpdateCube(cube);

                    }

                }

                // Clear out the state's stringBuilder for the next incoming message
                state.strBuilder.Clear();

                // Request a "move" command from the server
                sendMoveOrSplit("Move");

                // Ask the server for more cube data
                Network.i_want_more_data(state);
            }

        }


        /// <summary>
        /// Sends a message to the game server requesting either that our player cube be split, or
        /// to tell the server where we want to move
        /// </summary>
        /// <param name="command"></param>
        private void sendMoveOrSplit(String command)
        {
            // The coordinates of the mouse w.r.t. the top left corner of the monitor (not the form)
            Point mousePos = MousePosition;

            // The coordinates of the top left corner of this form
            Point formPos = this.Location;

            // subtract the form corner from the mousePos to get a rough translation to the coordinates of
            // the mouse in the world. Subtracting 200 gives us better accuracy.
            double mouseXPos = mousePos.X - formPos.X - 200;

            double mouseYPos = mousePos.Y - formPos.Y - 200;

            if (command == "Move")
            {
                Network.Send(socket, "(move, " + mouseXPos + ", " + mouseYPos + ")\n");
            }
            else // command == "Split"
            {
                Network.Send(socket, "(split, " + mouseXPos + ", " + mouseYPos + ")\n");
            }


        }






        /// <summary>
        /// Does work on a background thread.
        /// </summary>
        private void backgroundWorker1_DoWork(object sender, DoWorkEventArgs e)
        {
            try
            {
                // Try to do the intial connect to the server
                socket = Network.Connect_to_Server(sendPlayerName, serverName, 11000);
            }
            catch (Exception error)
            {
                MessageBox.Show("Unable to connect to requested server.");
                MethodInvoker closeWindow = new MethodInvoker(() => this.Close());

                this.Invoke(closeWindow);
            }

        }



       private void Form2_MouseMove(object sender, MouseEventArgs e)
        {
            // The (x,y) coordinates of the mouse cursor with respect to the Form's top left corner
            //Point mouseLocation = e.Location;

            //mouseXPos = mouseLocation.X;

            //mouseYPos = mouseLocation.Y;
        }


    }
}
