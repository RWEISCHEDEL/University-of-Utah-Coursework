// Written by Robert Weischedel and Jackson Murphy for CS 3500. Last updated December 3, 2015.

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Drawing;
using System.Timers;
using System.Xml;

namespace AgCubio
{
    /// <summary>
    /// This class describes a game world that contains players, food, and viruses. It contains many parameters
    /// that determine how the world looks and behaves (size, player speed, etc.) as well as provides
    /// game mechanics like player movement, player splitting, virus explosions, and so forth.
    /// 
    /// This class, along with the Cube and PlayerSessionStats classes, are the "Model" for the AgCubio game.
    /// </summary>
    public class World

    {
        /// <summary>
        /// This member variable stores the value for the WIDTH of the game world. 
        /// </summary>
        public readonly double WIDTH;

        /// <summary>
        /// This member variable stores the value for the HEIGHT of the game world. 
        /// </summary>
        public readonly double HEIGHT;

        /// <summary>
        /// This member variable sets the initial mass for player cubes.
        /// </summary>
        public readonly double INITIAL_PLAYER_MASS;

        /// <summary>
        /// How many times per second the state of the world is updated.
        /// </summary>
        public readonly int UPDATES_PER_SECOND;

        /// <summary>
        /// This member varaible sets the max amount of food to be displayed in the game world at a time.
        /// </summary>
        public readonly int MAX_FOOD;

        /// <summary>
        /// This member variable sets the mass of all the food cubes in the world. 
        /// </summary>
        public readonly int FOOD_MASS;

        /// <summary>
        /// This member variable keeps tract of the current amount of food in the world. 
        /// </summary>
        public int foodCountInWorld { get; private set; }


        /// <summary>
        /// This member variable keeps tract of the current amount of viruses in the world. 
        /// </summary>
        public int virusCountInWorld { get; private set; }

        /// <summary>
        /// This member variable keeps track of the maximum number of viruses that can exist in the world.
        /// </summary>
        public readonly int MAX_VIRUSES;

        /// <summary>
        /// This member variable sets the color for the virus cubes.
        /// </summary>
        private readonly Color VIRUS_COLOR;

        /// <summary>
        /// This member variable keeps track of all the different cube colors that each food or player cube could possibly be.
        /// </summary>
        private readonly Color[] CUBE_COLORS;

        /// <summary>
        /// This member variable keeps track of the current team_id of the player cube.
        /// </summary>
        private int team_id;

        /// <summary>
        /// is member variable keeps track of the current uid of the player cube.
        /// </summary>
        private int uid;

        /// <summary>
        /// This member variable stores all the Cubes in the game world, food and player alike.
        /// </summary>
        public HashSet<Cube> Cubes { get; private set; }

        /// <summary>
        /// Contains all the cubes whose status has changed since they were last sent to the clients. 
        /// For example, if a player moves and eats a food cube, both the player and eaten food will be placed in this set.
        /// The player will have a new position and mass, and the food will get a mass of 0.
        /// This set is cleared after its contents have been sent to the clients.
        /// </summary>
        public HashSet<Cube> CubesChangedSinceLastUpdate { get; set; }

        /// <summary>
        /// This memeber variable keeps track of all the player cube objects in the world.
        /// </summary>
        public Dictionary<int, List<Cube>> Teams;

        /// <summary>
        /// Keeps track of various game statistics for each team, including the number of cubes eaten, the names of players eaten,
        /// the maximum mass the team has ever achieved, the amount of time the team was alive, etc.
        /// </summary>
        public Dictionary<int, PlayerSessionStats> TeamStatistics;
        

        /// <summary>
        /// Helps determine the speed of a player cube. Make 1 for example client, 20 for our client
        /// </summary>
        private readonly double PLAYER_MOVE_FACTOR;

        /// <summary>
        /// The maximum speed of a player cube, in pixels per update. Make 5 for example client, 20 for our client
        /// </summary>
        public readonly double TOP_SPEED;

        /// <summary>
        /// The minimum speed of a player cube, in pixels per update
        /// </summary>
        public readonly double MIN_SPEED;

        /// <summary>
        /// How many cubes a player can have on their team
        /// </summary>
        private readonly int MAX_TEAM_COUNT;

        /// <summary>
        /// A cube must have at least this much mass to do a split.
        /// </summary>
        public readonly double MIN_SPLIT_MASS;

        /// <summary>
        /// The maximum limit on how far a newly split cube can be thrown ahead before returning to normal
        /// speed
        /// </summary>
        private readonly double MAX_SPLIT_DISTANCE;

        /// <summary>
        /// The amount of time, in seconds, it takes for a split cube to reach its final destination
        /// </summary>
        private readonly double SPLIT_JUMP_DURATION;

        /// <summary>
        /// The time, in seconds, that must pass after a split until the player cubes can merge together again.
        /// </summary>
        public readonly double TIME_UNTIL_SPLIT_CUBES_CAN_MERGE;

        /// <summary>
        /// For keeping track of how long each team must wait until their cubes can merge after a split.
        /// The int refers to a team ID.
        /// </summary>
        public Dictionary<int, Timer> teamTimers;

        /// <summary>
        /// This member variable keep track of the amount of a cube another cube must cover to absorb it.
        /// </summary>
        private readonly double ABSORB_DISTANCE;

        /// <summary>
        /// This member variable keeps track of the mass of the virus cubes.
        /// </summary>
        private readonly double VIRUS_MASS;

        /// <summary>
        /// How far a cube is propelled after a virus explosion
        /// </summary>
        private readonly double VIRUS_JUMP_DISTANCE;

        /// <summary>
        /// A list of team IDs for the teams that have recently been exploded by a virus.
        /// A program using a world object can check to see if there is anything in this list.
        /// If so, it knows to start merge timers for the teams in this list.
        /// </summary>
        public List<int> virusMergeTimersToStart = new List<int>();

        /// <summary>
        /// When a player's cube falls below this mass, it no longer loses mass to attrition
        /// </summary>
        public readonly double ATTRITION_LOWER_MASS_LIMIT;

        /// <summary>
        /// WHen a player's cube has more than this much mass, it starts losing mass very rapidly
        /// </summary>
        public readonly double RAPID_ATTRITION_THRESHOLD;

        /// <summary>
        /// Player cubes lose this amount of mass on every world update.
        /// </summary>
        public readonly double NORMAL_ATTRITION_PER_UPDATE;


        /// <summary>
        /// Player cubes experience higher mass attrition per update when they're over a certain mass
        /// </summary>
        public readonly double HIGH_ATTRITION_PER_UPDATE;

        /// <summary>
        /// This member varaible keeps track of the amount of food that is too be added at each update tick.
        /// </summary>
        public readonly int NEW_FOOD_AMT_PER_UPDATE;

        /// <summary>
        /// This member varable keeps track of the intiatl food amount that should be added to the game world on first startup.
        /// </summary>
        public readonly int INITIAL_FOOD_AMT;


        /// <summary>
        /// This serves as the constructor for the World. It takes in a filepath that contains a xml so that the world can be built accoring to parameters found in the file, if the 
        /// file fails to load that world will be generated according to the defaults we have set. 
        /// </summary>
        /// <param name="gameParametersFilePath">A string containing the filepath of the desired game parameters xml file.</param>
        public World(String gameParametersFilePath)
        {
            // Create the HashSet to store all the cubes in the world.
            Cubes = new HashSet<Cube>();

            CubesChangedSinceLastUpdate = new HashSet<Cube>();

            Teams = new Dictionary<int, List<Cube>>();

            TeamStatistics = new Dictionary<int, PlayerSessionStats>();

            // Try and read the Xml file
            try
            {
                XmlDocument gameParameters = new XmlDocument();
                using (XmlReader reader = XmlReader.Create(gameParametersFilePath))
                {
                    gameParameters.Load(reader);
                }

                XmlNode parametersNodes = gameParameters.SelectSingleNode("parameters");

                // Read and generate the world based on the parameters in the Xml
                this.WIDTH = double.Parse(parametersNodes.SelectSingleNode("width").InnerText);
                this.HEIGHT = double.Parse(parametersNodes.SelectSingleNode("height").InnerText);
                this.UPDATES_PER_SECOND = int.Parse(parametersNodes.SelectSingleNode("updatespersecond").InnerText);
                this.TOP_SPEED = int.Parse(parametersNodes.SelectSingleNode("top_speed").InnerText);
                this.MIN_SPEED = int.Parse(parametersNodes.SelectSingleNode("min_speed").InnerText);
                this.PLAYER_MOVE_FACTOR = int.Parse(parametersNodes.SelectSingleNode("playermovefactor").InnerText);
                this.MAX_TEAM_COUNT = int.Parse(parametersNodes.SelectSingleNode("maxteamcount").InnerText);
                this.MIN_SPLIT_MASS = int.Parse(parametersNodes.SelectSingleNode("minsplitmass").InnerText);
                this.MAX_SPLIT_DISTANCE = double.Parse(parametersNodes.SelectSingleNode("maxsplitdistance").InnerText);
                this.SPLIT_JUMP_DURATION = double.Parse(parametersNodes.SelectSingleNode("splitjumpduration").InnerText);
                this.TIME_UNTIL_SPLIT_CUBES_CAN_MERGE = double.Parse(parametersNodes.SelectSingleNode("timesplitcubesmerge").InnerText);
                this.ABSORB_DISTANCE = double.Parse(parametersNodes.SelectSingleNode("absorbdelta").InnerText);
                this.VIRUS_MASS = double.Parse(parametersNodes.SelectSingleNode("virusmass").InnerText);
                this.VIRUS_JUMP_DISTANCE = double.Parse(parametersNodes.SelectSingleNode("virusjumpdistance").InnerText);
                this.ATTRITION_LOWER_MASS_LIMIT = double.Parse(parametersNodes.SelectSingleNode("attritionlowermass").InnerText);
                this.RAPID_ATTRITION_THRESHOLD = double.Parse(parametersNodes.SelectSingleNode("attritionrapidthreshold").InnerText);
                this.NORMAL_ATTRITION_PER_UPDATE = double.Parse(parametersNodes.SelectSingleNode("normalattritionperupdate").InnerText);
                this.HIGH_ATTRITION_PER_UPDATE = double.Parse(parametersNodes.SelectSingleNode("highattritionperupdate").InnerText);
                this.NEW_FOOD_AMT_PER_UPDATE = int.Parse(parametersNodes.SelectSingleNode("newfoodperupdate").InnerText);
                this.INITIAL_FOOD_AMT = int.Parse(parametersNodes.SelectSingleNode("initialfoodamt").InnerText);
                this.INITIAL_PLAYER_MASS = double.Parse(parametersNodes.SelectSingleNode("initialplayermass").InnerText);
                this.MAX_FOOD = int.Parse(parametersNodes.SelectSingleNode("maxfood").InnerText);
                this.FOOD_MASS = int.Parse(parametersNodes.SelectSingleNode("foodmass").InnerText);
                this.MAX_VIRUSES = int.Parse(parametersNodes.SelectSingleNode("maxvirus").InnerText);

            }
            catch
            {
                Console.WriteLine("XML file failed to load, setting world accoring to defaults.");

                // Set the world with all system defaults.
                this.WIDTH = 1100;

                this.HEIGHT = 1100;

                this.UPDATES_PER_SECOND = 15;

                this.TOP_SPEED = 5;

                this.MIN_SPEED = 1;

                this.PLAYER_MOVE_FACTOR = 1;

                this.MAX_TEAM_COUNT = 20;

                this.MIN_SPLIT_MASS = 500;

                this.MAX_SPLIT_DISTANCE = 300.0;

                this.SPLIT_JUMP_DURATION = 2.0;

                this.TIME_UNTIL_SPLIT_CUBES_CAN_MERGE = 6.0;

                this.ABSORB_DISTANCE = 0.1;

                this.VIRUS_MASS = 2000;

                this.VIRUS_JUMP_DISTANCE = 300;

                this.ATTRITION_LOWER_MASS_LIMIT = 500;

                this.RAPID_ATTRITION_THRESHOLD = 3000;

                this.NORMAL_ATTRITION_PER_UPDATE = 1.0;

                this.HIGH_ATTRITION_PER_UPDATE = 10.0;

                this.NEW_FOOD_AMT_PER_UPDATE = 3;

                this.INITIAL_FOOD_AMT = 2000;

                this.INITIAL_PLAYER_MASS = 2000;

                this.MAX_FOOD = 2500;

                this.FOOD_MASS = 1;           

                this.MAX_VIRUSES = 2;

            }

            this.team_id = 0;

            this.uid = 0;

            this.VIRUS_COLOR = Color.Lime;

            this.virusCountInWorld = 0;

            this.foodCountInWorld = 0;

            this.CUBE_COLORS = new Color[]{Color.Maroon, Color.IndianRed, Color.LightCyan, Color.LightCoral, Color.MediumPurple, Color.MediumTurquoise,
            Color.OrangeRed, Color.Salmon, Color.RoyalBlue, Color.Yellow, Color.SeaShell, Color.Orange, Color.Peru, Color.SeaGreen, Color.Silver};

            // Initialize all the timers for the players for how long they must wait till they are combined again.
            this.teamTimers = new Dictionary<int, Timer>();



        }

        /// <summary>
        /// This method allows you to add new cubes, update a cube or completely remove a cube from the world.
        /// </summary>
        /// <param name="cube">The cube obj you wihsh to add or update in the world.</param>
        public void AddOrUpdateCube(Cube cube)
        {
            // If the cube is in the HashSet
            if (Cubes.Contains(cube))
            {
                // If the mass of the cube is 0, remove it.
                if (cube.Mass == 0)
                {
                    Cubes.Remove(cube);
                }
                // Else, remove the old version of it and add the updated version.
                else
                {
                    Cubes.Remove(cube);

                    Cubes.Add(cube);
                }
            }
            // If the cube doesn't exist in the HashSet add it. 
            else
            {
                Cubes.Add(cube);
            }

        }

        /// <summary>
        ///  This method returns a Point location in the game world to be a players spawn location. It ensures that the player will not spawn on another player 
        /// or virus cube.
        /// </summary>
        /// <returns>A point that acts as a player cubes physical spawn location.</returns>
        public System.Windows.Point GetPlayerSpawnLoc()
        {
            // Create Rectangle objects to help in determining if the Player Cube will intersect with any other player cube.
            Rectangle cubeSquare;

            Rectangle playerSquare;

            // This bool lets the method know to exit the overall while loop when a valid spawn location has been found.
            bool notFoundValidLocation = true;

            // This bool lets the inner foreach loop to exit when a bad spawn location has been generated.
            bool badSpawnLocation = false;

            // Create a random number generator.
            Random r = new Random();

            // Generate the minimum X range to spawn based on player mass and world width.
            int minXRange = (int)Math.Sqrt(INITIAL_PLAYER_MASS); // Changed from Food Mass

            int maxXRange = (int)(WIDTH - Math.Sqrt(INITIAL_PLAYER_MASS));

            int playerX = r.Next(minXRange, maxXRange);

            // Generate the minimum Y range to spawn based on player mass and world width.
            int minYRange = (int)Math.Sqrt(INITIAL_PLAYER_MASS);

            int maxYRange = (int)(HEIGHT - Math.Sqrt(INITIAL_PLAYER_MASS));

            int playerY = r.Next(minYRange, maxYRange);



            while (notFoundValidLocation)
            {
                // Create a square with the player information.
                int playerCubeX = (int)(playerX - (Math.Sqrt(INITIAL_PLAYER_MASS) / 2.0));

                int playerCubeY = (int)(playerY - (Math.Sqrt(INITIAL_PLAYER_MASS) / 2.0));

                int playerCubeWidth = (int)Math.Sqrt(INITIAL_PLAYER_MASS);

                playerSquare = new Rectangle(playerCubeX, playerCubeY, playerCubeWidth, playerCubeWidth);

                // Iterate through all the cubes in the world and see if the new players location will spawn into another player.
                foreach (Cube cube in Cubes)
                {
                    // Only look at cubes that are players.
                    if (!cube.food || cube.team_id == -1)
                    {
                        // Create a square with the other players information.
                        int cubeX = (int)(cube.loc_x - (cube.Width / 2.0));

                        int cubeY = (int)(cube.loc_y - (cube.Width / 2.0));

                        cubeSquare = new Rectangle(cubeX, cubeY, cube.Width, cube.Width);

                        // If they intersect, break out of the loop and let the outer loop know to generate a new spawn location.
                        if (playerSquare.IntersectsWith(cubeSquare))
                        {
                            badSpawnLocation = true;

                            break;
                        }
                    }

                }

                // If the spawn location is bad, generate a new one.
                if (badSpawnLocation)
                {
                    playerX = r.Next(minXRange, maxXRange);

                    playerY = r.Next(minYRange, maxYRange);

                    badSpawnLocation = false;
                }
                // If it is good then end the loop.
                else
                {
                    notFoundValidLocation = false;
                }
            }


            // Return the newly create spawn location point.
            return new System.Windows.Point((double)playerX, (double)playerY);

        }

        /// <summary>
        /// This method generates and returns a random color for each food and player object.
        /// </summary>
        /// <returns>A random color from a list of colors.</returns>
        public int SetCubeColor()
        {
            // Create a random number generator.
            Random r = new Random();

            // Generate the random value.
            int randomColorValue = r.Next(0, CUBE_COLORS.Length);

            // Return the color from the array that is located at that location.
            return CUBE_COLORS[randomColorValue].ToArgb();
        }

        /// <summary>
        /// This method returns and updates a team_id value for a player cube.
        /// </summary>
        /// <returns>A integer value signifying the next availabe team_id value.</returns>
        public int GetNewTeamID()
        {
            // Return the integer team_id value, then increment it.
            return team_id++;
        }

        /// <summary>
        /// This method returns and updates a uid value for a player cube.
        /// </summary>
        /// <returns>A integer value signifying the next availabe uid value.</returns>
        public int GetNextUID()
        {
            return uid++;
        }


        /// <summary>
        /// This method generates and adds a specified amount of food to the world, based on the desired inputed food amount value. 
        /// When we initally populate the world with food, we call this method with the "isInitialFoodCreation" boolean parameter
        /// set to true. Any food created thereafter sets that parameter to false. This distinction is necessary because the 
        /// server sends the initial bunch of food in a different method.
        /// </summary>
        /// <param name="amtOfFood">The amount of food cubes that are to be added to the world.</param>
        /// <param name="isInitialFoodCreation">A boolean determining if this calling of this method is the intital creation of the world or not.</param>
        public void CreateFood(int amtOfFood, bool isInitialFoodCreation)
        {
            // Create a random number generator. 
            Random r = new Random();

            // Generate the minimum X range to spawn based on food mass and world width.
            int minXRange = (int)Math.Sqrt(FOOD_MASS);

            int maxXRange = (int)(WIDTH - Math.Sqrt(FOOD_MASS));

            // Generate the minimum Y range to spawn based on food mass and world width.
            int minYRange = (int)Math.Sqrt(FOOD_MASS);

            int maxYRange = (int)(HEIGHT - Math.Sqrt(FOOD_MASS));

            // Loop through and generate the desired amount of food cubes and add them to the world.
            for (int i = 0; i < amtOfFood; i++)
            {
                // Generate the X and Y postition for a food cube.
                int foodX = r.Next(minXRange, maxXRange);

                int foodY = r.Next(minYRange, maxYRange);

                // Generate the random color a food cube. **** Why not use helper?
                int maxColorRange = CUBE_COLORS.Length;

                int argb_color = CUBE_COLORS[r.Next(0, maxColorRange)].ToArgb();

                // Generate a random team-id and uid for a food cube.
                int food_uid = GetNextUID();

                int food_team_id = 0;

                // Update the amount of food in this world.
                this.foodCountInWorld++;

                Cube food = new Cube(foodX, foodY, argb_color, food_uid, food_team_id, true, "", FOOD_MASS);

                // Create the new food cube and add it to the world.
                Cubes.Add(food);

                // Any food created after the initial bunch of food should also be placed in the set for delivery to the clients.
                if (!isInitialFoodCreation)
                    CubesChangedSinceLastUpdate.Add(food);
            }
        }


        /// <summary>
        /// Moves all of a team's player cubes towards the player's mouse position, specified by the dest_x, dest_y 
        /// parameters. If the mouse is beyond the boundaries of the world, the cubes will be stopped
        /// at the boundary.
        /// </summary>
        /// <param name="dest_x">The desired x destination the player cube is to begin moving toward.</param>
        /// <param name="dest_y">The desired y destination the player cube is to begin moving toward.</param>
        /// <param name="team">A list of all cubes that belong to the same team(player cube and split cubes) to move toward the desired destination.</param>
        public void MovePlayer(double dest_x, double dest_y, List<Cube> team)
        {
            foreach (Cube player in team)
            {

                // Calculate new x and y positions for the player cube

                // First, get the x and y distance from the cube to the mouse
                double x_dist_to_dest = dest_x - player.loc_x;
                double y_dist_to_dest = dest_y - player.loc_y;

                // get the distance between the player and the mouse
                double distToMouse = Math.Sqrt(Math.Pow(x_dist_to_dest, 2) + Math.Pow(y_dist_to_dest, 2));

                // The amount of distance in pixels to move in the x and y directions from the cubes current location
                //double moveXDistance;
                //double moveYDistance;


                // If player cube recently split off, it has a greater than usual speed. Its next
                // locations were already determined in the Split method.
                if (player.HasMomentum)
                {
                    // We always take the Point at the front of the cube's FuturePositions list, and then delete it
                    System.Windows.Point newLocation = player.FuturePositionsAfterSplit[0];

                    player.FuturePositionsAfterSplit.RemoveAt(0);

                    player.loc_x = newLocation.X;
                    player.loc_y = newLocation.Y;

                    player.MomentumRemaining--;

                    // Once the split cube has moved at an increased speed for its set duration, we mark it so that next time around it will be treated as a normal cube
                    if (player.MomentumRemaining == 0)
                        player.HasMomentum = false;
                }


                else // player.HasMomentum == false. 
                {

                    if (dest_x == player.loc_x && dest_y == player.loc_y)
                    {
                        return;
                    }

                    // divide the move-distance-factor by the total distance to the destination point in order to know how much to scale the x and y.
                    double fractionOfDistanceToMove = (PLAYER_MOVE_FACTOR * 1000 / player.Mass) / (distToMouse + 1);


                    double moveXDistance = (x_dist_to_dest * fractionOfDistanceToMove);
                    double moveYDistance = (y_dist_to_dest * fractionOfDistanceToMove);

                    // Make sure our new cube location wouldn't cause the cube to
                    // travel too fast nor too slow
                    CheckForTopAndMinSpeeds(ref moveXDistance, ref moveYDistance);


                    player.loc_x += (moveXDistance);
                    player.loc_y += (moveYDistance);


                    // Check if this move caused the cube to eat food or another player; be eaten by another player;
                    // contract a virus; or bump into a fellow team cube
                    // If method returns true, we ran into a teammate and therefore we need to find a new position.
                    // Our strategy for finding a better position is to try moving in only either the x- or y- 
                    // directions towards the mouse. If both of these also cause us to absorb a teammate, we don't move.
                    if (AbsorbCubeCheck(player) == true)
                    {
                        // try moving in just the y-direction 
                        player.loc_x -= (moveXDistance);

                        if (AbsorbCubeCheck(player) == true)
                        {
                            // try moving in just the x-direction
                            player.loc_x += (moveXDistance);
                            player.loc_y -= (moveYDistance);

                            if (AbsorbCubeCheck(player) == true)
                            {
                                // don't move the player at all
                                player.loc_x -= (moveXDistance);
                                //Console.WriteLine("Don't move player");
                            }
                        }
                    }
                }


                // Prevent cube from moving beyond the world boundaries
                if ((player.loc_x - player.Width / 2) < 0)
                    player.loc_x = player.Width / 2;

                if ((player.loc_y - player.Width / 2) < 0)
                    player.loc_y = player.Width / 2;

                if ((player.loc_x + player.Width / 2) > this.WIDTH)
                    player.loc_x = this.WIDTH - player.Width / 2;

                if ((player.loc_y + player.Width / 2) > this.HEIGHT)
                    player.loc_y = this.HEIGHT - player.Width / 2;



                AddOrUpdateCube(player);
                CubesChangedSinceLastUpdate.Add(player);
            }
        }


        /// <summary>
        /// /// Splits a team's cubes. A cube is split into two smaller cubes if it has sufficient Mass, and if the team has
        /// not reached its maximum limit of team cubes. When a cube splits, the original cube shrinks and remains in place,
        /// and the new cube is propelled forward towards destX, destY. 
        /// After a split, a timer is started that specifies when the cubes can recombine into a larger cube.
        /// </summary>
        /// <param name="destX"></param>
        /// <param name="destY"></param>
        /// <param name="team_id"></param>
        public void Split(double destX, double destY, int team_id)
        {
            List<Cube> team = Teams[team_id];
            List<Cube> cubesToBeAddedToTeam = new List<Cube>();

            int teamCount = team.Count;


            foreach (Cube player in team)
            {
                // Only split a cube if the team count is under the Max, and the cube is massive enough
                if (teamCount > MAX_TEAM_COUNT)
                    return;

                if (player.Mass < MIN_SPLIT_MASS)
                    continue;

                // We're good to split the cube

                // Halve the original cube's mass
                player.Mass /= 2;

                // Determine where the new cube should be created
                System.Windows.Point newCubePos = FindSuitableSplitSpawnLoc(player, team);
                double newCubeX = newCubePos.X;
                double newCubeY = newCubePos.Y;

                Cube newCube = new Cube(newCubeX, newCubeY, player.argb_color, uid++, player.team_id, false, player.Name, player.Mass);

                // Theses cubes won't be able to merge until some time goes by
                newCube.CanMerge = false;
                player.CanMerge = false;

                // Sets properties in newCube to give it a short boost
                GiveCubeAJumpTrajectory(newCube, destX, destY, MAX_SPLIT_DISTANCE);

                // add new cube to a list that will be added to the team after all original cubes have been looped over
                cubesToBeAddedToTeam.Add(newCube);


                // Update the world, and add the original cube and new cube to the set that will be sent to the clients
                AddOrUpdateCube(player);
                AddOrUpdateCube(newCube);
                CubesChangedSinceLastUpdate.Add(player);
                CubesChangedSinceLastUpdate.Add(newCube);

                // Increment team count.
                teamCount++;

            }

            // Add all newly generated player cubes to the team
            team.AddRange(cubesToBeAddedToTeam);

        }

        /// <summary>
        /// Sets a cubes properties such that it travels at an increased speed towards a destination point for
        /// a period of time.
        /// </summary>
        public void GiveCubeAJumpTrajectory(Cube cube, double destX, double destY, double maxJumpDistance)
        {
            // If the distance from the cube to the destination is greater than the
            // maximum allowable jump distance, shorten the jump.
            double x_dist_to_dest = destX - cube.loc_x;
            double y_dist_to_dest = destY - cube.loc_y;

            double distanceToMouse = Math.Sqrt(Math.Pow(x_dist_to_dest, 2) + Math.Pow(y_dist_to_dest, 2));

            if (distanceToMouse > maxJumpDistance)
            {
                // reduce the new x,y coordinates of the jump destination
                x_dist_to_dest *= (maxJumpDistance / distanceToMouse);
                y_dist_to_dest *= (maxJumpDistance / distanceToMouse);
            }


            // The split cube doesn't immediately jump to its destination. Instead, it moves there smoothly
            // over a short period of time.
            // First, decide how many moves it should take the cube to get to its destination
            int moves = (int)(UPDATES_PER_SECOND * SPLIT_JUMP_DURATION);


            // Create a list for the cube that contains its future positions for the duration of the split jump
            List<System.Windows.Point> futureCubePositions = new List<System.Windows.Point>();

            for (int i = 0; i < moves; i++)
            {
                double posX = cube.loc_x + (x_dist_to_dest / moves) * (i + 1);
                double posY = cube.loc_y + (y_dist_to_dest / moves) * (i + 1);

                futureCubePositions.Add(new System.Windows.Point(posX, posY));
            }

            // Set this array of positions to the new cube's FuturePositions array
            cube.FuturePositionsAfterSplit = futureCubePositions;

            cube.MomentumRemaining = moves;
            cube.HasMomentum = true;
        }

        /// <summary>
        /// Splits up a player cube into smaller cubes. The smaller cubes shoot off in different directions.
        /// </summary>
        /// <param name="cube"></param>
        public void ExplodePlayerOnVirus(Cube cube)
        {
            double initialX = cube.loc_x;
            double initialY = cube.loc_y;

            // Split player cube into 4 smaller cubes.
            // Divide player cube's mass by 4
            cube.Mass /= 4;

            // Spawn the other 3 cubes near the original cube
            Cube c2 = new Cube(cube.loc_x + cube.Width, cube.loc_y, cube.argb_color, uid++, cube.team_id, false, cube.Name, cube.Mass);
            Cube c3 = new Cube(cube.loc_x, cube.loc_y - cube.Width, cube.argb_color, uid++, cube.team_id, false, cube.Name, cube.Mass);
            Cube c4 = new Cube(cube.loc_x - cube.Width, cube.loc_y, cube.argb_color, uid++, cube.team_id, false, cube.Name, cube.Mass);


            List<Cube> cubes = new List<Cube>() { cube, c2, c3, c4 };

            // Update the team
            Teams[cube.team_id].Remove(cube);
            Teams[cube.team_id].AddRange(cubes);

            // These are the destinations that each cube will jump to
            System.Windows.Point cube1Position = new System.Windows.Point(initialX, VIRUS_JUMP_DISTANCE + initialY);
            System.Windows.Point cube2Position = new System.Windows.Point(initialX + VIRUS_JUMP_DISTANCE, initialY);
            System.Windows.Point cube3Position = new System.Windows.Point(initialX, initialY - VIRUS_JUMP_DISTANCE);
            System.Windows.Point cube4Position = new System.Windows.Point(initialX - VIRUS_JUMP_DISTANCE, initialY);

            List<System.Windows.Point> points = new List<System.Windows.Point>() { cube1Position, cube2Position, cube3Position, cube4Position };

            // Jump each cube to its destination
            for (int i = 0; i < 4; i++)
            {
                GiveCubeAJumpTrajectory(cubes[i], points[i].X, points[i].Y, VIRUS_JUMP_DISTANCE);

                cubes[i].CanMerge = false;

                // Broadcast changes
                CubesChangedSinceLastUpdate.Add(cubes[i]);
                AddOrUpdateCube(cubes[i]);
            }


            // Prevent the exploded player cubes from remerging until some time has passed
            // Add the team_id to this list so that the program knows to start a timer.
            virusMergeTimersToStart.Add(cube.team_id);

        }


        /// <summary>
        /// This method prevents a cube from moving faster than the TOP_SPEED member variable of the world, or from
        /// moving slower than the MIN_SPEED variable.
        /// </summary>
        /// <param name="xDistance"></param>
        /// <param name="yDistance"></param>
        public void CheckForTopAndMinSpeeds(ref double xDistance, ref double yDistance)
        {
            if (xDistance > 0)
            {
                if (xDistance > TOP_SPEED)
                    xDistance = TOP_SPEED;
                else if (xDistance < MIN_SPEED)
                    xDistance = MIN_SPEED;
            }
            else // x Distance < 0
            {
                if (xDistance < -TOP_SPEED)
                    xDistance = -TOP_SPEED;
                else if (xDistance > -MIN_SPEED)
                    xDistance = -MIN_SPEED;
            }

            if (yDistance > 0)
            {
                if (yDistance > TOP_SPEED)
                    yDistance = TOP_SPEED;
                else if (yDistance < MIN_SPEED)
                    yDistance = MIN_SPEED;
            }
            else // yDistance < 0
            {
                if (yDistance < -TOP_SPEED)
                    yDistance = -TOP_SPEED;
                else if (yDistance > -MIN_SPEED)
                    yDistance = -MIN_SPEED;
            }
        }

        /// <summary>
        /// This method returns the spawn location of all the cubes that are created when a player cube is split.
        /// </summary>
        /// <param name="originalPlayer">The player cube that is splitting.</param>
        /// <param name="team">The team containing all the org player cube and all the split cubes.</param>
        /// <returns></returns>
        private System.Windows.Point FindSuitableSplitSpawnLoc(Cube originalPlayer, List<Cube> team)
        {

            double origPlayerX = originalPlayer.loc_x;
            double origPlayerY = originalPlayer.loc_y;

            // for now, just place new cube to the right of the original cube
            return new System.Windows.Point(origPlayerX + originalPlayer.Width, origPlayerY);

        }

        /// <summary>
        /// This method is a copy of the AbsorbCubeCheck method, in order to test that its complex algorithm works correctly.
        /// </summary>
        /// <param name="bigCube">The "player" cube.</param>
        /// <param name="smallCube">Any other cube in the world.</param>
        /// <param name="absorptionDelta">The desired absorb distance delta for this test.</param>
        /// <returns></returns>
        public static bool WasEaten(Cube bigCube, Cube smallCube, double absorptionDelta)
        {
            // Create a square with the big cube information.
            int bigCubeX = (int)(bigCube.loc_x - (bigCube.Width / 2.0));

            int bigCubeY = (int)(bigCube.loc_y - (bigCube.Width / 2.0));

            Rectangle bigSquare = new Rectangle(bigCubeX, bigCubeY, bigCube.Width, bigCube.Width);

            // Create a square with the small cube information.
            int smallCubeX = (int)(smallCube.loc_x - (smallCube.Width / 2.0));

            int smallCubeY = (int)(smallCube.loc_y - (smallCube.Width / 2.0));

            Rectangle smallSquare = new Rectangle(smallCubeX, smallCubeY, smallCube.Width, smallCube.Width);

            // If the big square intersects with the smaller one, see if it meets the absorbition delta requirements.
            if (bigSquare.IntersectsWith(smallSquare))
            {
                // Calculate the intersected portion and the smaller cubes area to see if enought absorbtion has happened.
                Rectangle intersection = Rectangle.Intersect(bigSquare, smallSquare);

                int cubeArea = smallSquare.Height * smallSquare.Width;

                int intersectionArea = intersection.Height * intersection.Width;

                // If enought of the big cube has absorbed the small cube, return true. Otherwise return false.
                if (intersectionArea >= cubeArea * absorptionDelta)
                {
                    return true;
                }

                // Big square did intersect with small square, but not enough
                return false;
            }

            // Big square did not intersect with small square
            return false;

        }


        /// <summary>
        /// This method handles the the absorption (eating) of cubes. It takes in a player cube and based on its Mass and location determines if it should eat other cubes or be eaten by another cube.
        /// 
        /// </summary>
        /// <param name="player">The player cube.</param>
        public bool AbsorbCubeCheck(Cube player)
        {
            // Create two Rectangle objects to help in the intersection calculation. The cubeSquare is used for each cube in the world, the intersection is used in calculation.
            Rectangle cubeSquare;

            Rectangle intersection;

            // Create a Set to place all the cubes that need to be updated, each time the cube moves.
            HashSet<Cube> CubesToUpdate = new HashSet<Cube>();

            // Create the player square.
            int playerX = (int)(player.loc_x - (player.Width / 2.0));
            int playerY = (int)(player.loc_y - (player.Width / 2.0));

            Rectangle playerSquare = new Rectangle(playerX, playerY, player.Width, player.Width);

            // Loop through all the cubes in the world to find if any are intersecting the player.
            foreach (Cube cube in Cubes)
            {
                // Create the cube square.
                int cubeX = (int)(cube.loc_x - (cube.Width / 2.0));

                int cubeY = (int)(cube.loc_y - (cube.Width / 2.0));

                cubeSquare = new Rectangle(cubeX, cubeY, cube.Width, cube.Width);

                // If the two squares are intersecting...
                if (playerSquare.IntersectsWith(cubeSquare))
                {
                    // Calculate the intersection value based on the area of the cube and the absorb distance delta.
                    intersection = Rectangle.Intersect(playerSquare, cubeSquare);

                    int cubeArea = cubeSquare.Height * cubeSquare.Width;
                    int playerArea = playerSquare.Height * playerSquare.Width;

                    int intersectionArea = intersection.Height * intersection.Width;

                    // If the player cube has absorbed or has been absorbed another cube, let it be eaten or eat the cube.
                    if (intersectionArea >= cubeArea * ABSORB_DISTANCE || intersectionArea >= playerArea * ABSORB_DISTANCE)
                    {
                        // If the cube is a food or virus. We only take action if the player is larger than the virus.
                        if (cube.food)
                        {
                            if (cube.Mass < player.Mass)
                            {

                                // If the cube is a virus, eat it and split.
                                if (cube.team_id == -1)
                                {
                                    // Transfer the virus cubes mass to the player cube.
                                    player.Mass += cube.Mass;

                                    this.TeamStatistics[player.team_id].CurrentMass += cube.Mass;

                                    // Set the virus cubes mass to 0, aka its dead.
                                    cube.Mass = 0;

                                    // Add the eaten virus cube to the Set of cubes that need to be updated.
                                    CubesToUpdate.Add(cube);

                                    // Decrement the amount of viruses in the world.
                                    virusCountInWorld--;

                                    this.TeamStatistics[player.team_id].NumberOfFoodEaten++;

                                    

                                    // Split up the player
                                    ExplodePlayerOnVirus(player);

                                    break;

                                }

                                // If the cube is food, eat it.
                                else
                                {
                                    // Transfer the food cubes mass to the player cube.
                                    player.Mass += cube.Mass;

                                    this.TeamStatistics[player.team_id].CurrentMass += cube.Mass;

                                    // Set the food cubes mass to 0, aka its dead.
                                    cube.Mass = 0;

                                    // Add the eaten food cube to the Set of cubes that need to be updated.
                                    CubesToUpdate.Add(cube);

                                    // Decrement the amount of food in the world.
                                    foodCountInWorld--;

                                    this.TeamStatistics[player.team_id].NumberOfFoodEaten++;
                                }
                            }
                        }

                        // If the cube is not food.
                        else
                        {
                            // Ensure that the cube being looked at is not the player cube itself.
                            if (cube.uid == player.uid)
                                continue;

                            // If the cube is one of the player's teammates, merge if they are allowed to.
                            if (player.team_id == cube.team_id)
                            {
                                if (player.CanMerge == false || cube.CanMerge == false)
                                    return true;

                                // We're good to merge. Preserve our original team player if we were going to eat it
                                if (cube.uid == player.team_id)
                                {
                                    cube.Mass += player.Mass;
                                    player.Mass = 0;
                                    CubesToUpdate.Add(cube);
                                    break;
                                }

                                // If cube wasn't the original teammate, absorb it
                                player.Mass += cube.Mass;
                                cube.Mass = 0;
                                CubesToUpdate.Add(cube);

                            }

                            // If the cube is a player cube that is smaller than yours, eat it. 
                            else if (cube.Mass < player.Mass)
                            {
                                // Remove the cube from its team. If it's the original team member and 
                                // still has teammates alive, pass on its uid
                                List<Cube> team = Teams[cube.team_id];
                                team.Remove(cube);

                                if (cube.uid == cube.team_id)
                                {

                                    if (team.Count > 0)
                                    {
                                        int tempID = team[0].uid;

                                        team[0].uid = cube.uid;

                                        cube.uid = tempID;

                                        CubesToUpdate.Add(team[0]);

                                    }
                                }

                                // Transfer the losing player cubes mass to the player cube.
                                player.Mass += cube.Mass;

                                this.TeamStatistics[player.team_id].CurrentMass += cube.Mass;

                                // Set the losing player cubes mass to 0, aka its dead.
                                cube.Mass = 0;

                                // Add the eaten player cube to the Set of cubes that need to be updated.
                                CubesToUpdate.Add(cube);

                                // For some reason number of player cubes eaten was extremely high, by doing this we only count eating a cube of a player once. Even
                                // if we eat several of their cubes.
                                if (!this.TeamStatistics[player.team_id].NamesOfPlayersEaten.Contains(cube.Name))
                                {
                                    this.TeamStatistics[player.team_id].NumberOfPlayersEaten++;

                                    this.TeamStatistics[player.team_id].NamesOfPlayersEaten.Add(cube.Name);
                                }
                                

                                

                            }
                            // If the player cube is smaller than another player cube, it gets eaten.
                            // If the eaten cube is the original player but it has teammates still alive,
                            // transfer the uid to a still-living teammate. 
                            else
                            {
                                // Remove player from its team
                                List<Cube> team = Teams[player.team_id];
                                team.Remove(player);

                                if (player.uid == player.team_id)
                                {

                                    if (team.Count > 0)
                                    {
                                        // swap ID of original, dying cube, with one of its teammates
                                        int tempID = team[0].uid;
                                        team[0].uid = player.uid;
                                        player.uid = tempID;

                                        CubesToUpdate.Add(team[0]);
                                    }
                                }
                                // Transfer the players mass to the winning cube.
                                cube.Mass += player.Mass;

                                // Set the player cubes mass to 0, aka its dead.
                                player.Mass = 0;

                                // Add the victor cube to the Set of cubes that need to be updated.
                                CubesToUpdate.Add(cube);

                                // For some reason number of player cubes eaten was extremely high, by doing this we only count eating a cube of a player once. Even
                                // if we eat several of their cubes.
                                if (!this.TeamStatistics[cube.team_id].NamesOfPlayersEaten.Contains(player.Name))
                                {
                                    this.TeamStatistics[cube.team_id].NumberOfPlayersEaten++;

                                    this.TeamStatistics[cube.team_id].NamesOfPlayersEaten.Add(player.Name);

                                }     

                                break;

                            }
                        }
                    }

                }
            }

            // Add the player to the CubesToUpdate Set
            CubesToUpdate.Add(player);

            // Iterate and update all the cubes that have been eaten or changed.
            foreach (Cube cube in CubesToUpdate)
            {
                AddOrUpdateCube(cube);
                CubesChangedSinceLastUpdate.Add(cube);
            }


            // The player cube didn't bump against one of its own teammates
            return false;
        }



        /// <summary>
        /// This method generates and adds a specified amount of viruses to the world, based on the desired inputed virus amount value. 
        /// When we initally populate the world with virus, we call this method with the "isInitialVirusCreation" boolean parameter
        /// set to true. Any viruses created thereafter sets that parameter to false. This distinction is necessary because the 
        /// server sends the initial bunch of viruses in a different method.
        /// </summary>
        /// <param name="amountOfViruses">The desired amount of viruses that need to be added to the game world.</param>
        /// <param name="isInitialVirusCreation">A boolean determining if this calling of this method is the intital creation of the world or not.</param>
        public void CreateVirus(int amountOfViruses, bool isInitialVirusCreation)
        {
            // Create a random number generator. 
            Random r = new Random();

            // Generate the minimum X range to spawn based on virus mass and world width.
            int minXRange = (int)Math.Sqrt(VIRUS_MASS);

            int maxXRange = (int)(WIDTH - Math.Sqrt(VIRUS_MASS));

            // Generate the minimum Y range to spawn based on virus mass and world width.
            int minYRange = (int)Math.Sqrt(VIRUS_MASS);

            int maxYRange = (int)(HEIGHT - Math.Sqrt(VIRUS_MASS));

            // Generate the Green color for the virus.
            int argb_color = VIRUS_COLOR.ToArgb();

            int virus_team_id = -1;

            for (int i = 0; i < amountOfViruses; i++)
            {
                // Generate the X and Y postition for a virus cube.
                int virusX = r.Next(minXRange, maxXRange);

                int virusY = r.Next(minYRange, maxYRange);

                // Generate a random team-id and uid for a virus cube.
                int virus_uid = GetNextUID();

                // Update the amount of virues in this world.
                this.virusCountInWorld++;

                Cube virus = new Cube(virusX, virusY, argb_color, virus_uid, virus_team_id, true, "", VIRUS_MASS);

                // Create the new food cube and add it to the world.
                Cubes.Add(virus);

                // Any food created after the initial bunch of food should also be placed in the set for delivery to the clients.
                if (!isInitialVirusCreation)
                {
                    CubesChangedSinceLastUpdate.Add(virus);
                }
            }
        }
    }
}
