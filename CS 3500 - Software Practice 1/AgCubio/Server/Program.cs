// Written by Robert Weischedel and Jackson Murphy for CS 3500. 
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using AgCubio;
using System.Net.Sockets;
using Newtonsoft.Json;
using System.Timers;
using MySql.Data.MySqlClient;
using System.Net;

namespace Server
{
    /// <summary>
    /// The entry point for the AgCubio game. This class serves as the Server. It handles incoming client connections
    /// and game requests, and continually broadcasts the state of the game world to all connected clients.
    /// </summary>
    public class Program
    {
        /// <summary>
        /// This member variable holds/refers to the whole game world.
        /// </summary>
        private static World world;

        /// <summary>
        /// This HashTable holds all the sockets and the uids of the clients that are currently connected.
        /// </summary>
        private static Dictionary<Socket, int> ClientSockets;

        /// <summary>
        /// Used to temporarily store all sockets we wish to remove from our ClientSockets table
        /// </summary>
        private static List<Socket> socketsToRemove;

        /// <summary>
        /// This member varable serves only as a locker for when only one client socket needs to be accessed at a time.
        /// </summary>
        private readonly static object clientSocketsLocker = new object();

        /// <summary>
        /// This member varable serves only as a locker to ensure that mulitple threads aren't trying to revieve data at the same time.
        /// </summary>
        private readonly static object receiveRequestLocker = new object();

        /// <summary>
        /// Used to ensure timers aren't being modified in world.teamTimers at the same time as we use a foreach
        /// in the timer event method to see which timer has gone off
        /// </summary>
        private readonly static object mergeTimerLocker = new object();

        /// <summary>
        /// This is the the update timer for the world, every time the timer goes off the world updates.
        /// </summary>
        private static Timer updateTimer;

        /// <summary>
        /// This boolean variable keeps track if there are players that have joined on the server or not.
        /// </summary>
        private static bool noPlayersJoined;

        /// <summary>
        /// Used to send all recently-updates cubes to the clients
        /// </summary>
        private static StringBuilder allWorldCubes;

        /// <summary>
        /// Must send this first to a web browser before sending an html page
        /// </summary>
        private const string HttpResponseHeader = "HTTP/1.1 200 OK\r\nConnection: close\r\nContent-Type: text/html; charset=UTF-8\r\n\r\n";

        /// <summary>
        /// The database connection string
        /// </summary>
        private const string DbConnectionString = "server=atr.eng.utah.edu;database=cs3500_jmurphy;uid=cs3500_jmurphy;password=Derek_Zoolander";

        /// <summary>
        /// Used to prevent processing a dead player multiple times.
        /// </summary>
        private static HashSet<int> DeadPlayersUIDs;


        /// <summary>
        /// The main method that starts the server. It calls the start method in this class, to begin building the world.
        /// </summary>
        /// <param name="args"></param>
        public static void Main(string[] args)
        {
            // Write a line to the player showing that the server is opening.
            Console.WriteLine("Our Server");

            // Call the start method to begin starting the server.
            Start();

            Console.Read();
        }

        /// <summary>
        /// This method begins preperations for the building of the game world by creating and filling the world and also creating the update timer
        /// for the server as well. 
        /// </summary>
        static void Start()
        {
            // No players have added at this moment so set the member variable to false.
            noPlayersJoined = true;

            // Create the HashTable for the client sockets and thier respective player cube ids.
            ClientSockets = new Dictionary<Socket, int>();

            socketsToRemove = new List<Socket>();

            allWorldCubes = new StringBuilder();

            DeadPlayersUIDs = new HashSet<int>();

            // Create the game world.
            world = new World("C:\\Users\\weisched\\Source\\Repos\\JacksonRepo\\AgCubio\\Resources\\XMLWorldParameters.xml");

            // Lock the world object, and create all the food that will be added to the world.
            lock (world)
            {

                // Populate the world with an initial amount of food
                world.CreateFood(world.INITIAL_FOOD_AMT, true);

                world.CreateVirus(world.MAX_VIRUSES, true);

            }

            // Create timer for calling Update()
            updateTimer = new Timer(1 / (world.UPDATES_PER_SECOND / (double)1000));
            updateTimer.Elapsed += new ElapsedEventHandler(updateTimerEvent);
            updateTimer.Start();

            // Call Sever_Awaiting_Client method and await the connection of the first client. 
            Network.Server_Awaiting_Client(HandleNewClient, 11000);

            // Also have server listen for incoming web requests on Port 11100
            Network.Server_Awaiting_Client(GetWebRequest, 11100);

        }

        /// <summary>
        /// Fires with a frequency determined by World.UPDATES_PER_SECOND. 
        /// Calls Update() to broadcast the current state of the world to all connected clients.
        /// </summary>
        private static void updateTimerEvent(object source, ElapsedEventArgs e)
        {
            if (!noPlayersJoined)
            {
                Update();
            }

        }

        /// <summary>
        /// Uses AgCubio's Network class to begin receiving the web request. Changes the 
        /// callback fcn of the state object so that HandleWebRequest will be called to process
        /// the request info.
        /// </summary>
        static void GetWebRequest(PreservedState state)
        {
            state.callBack = HandleWebRequest;

            Network.i_want_more_data(state);
        }

        /// <summary>
        /// Responds to web requests asking for information about historical games, player stats, etc.
        /// Valid url requests include: "/scores", "games?player=Jack", and "eaten?id=22" where Jack could be
        /// any name, and 22 could be any number. If the request doesn't follow this format, or if the name or
        /// id doesn't exist in the database, we respond with an error page
        /// </summary>
        private static void HandleWebRequest(PreservedState state)
        {
            if (state.strBuilder == null || state.strBuilder.ToString() == "")
            {
                state.socket.Close();
                return;
            }

            String request = state.strBuilder.ToString();

            // Pull out just the query from all the info we get from the request
            // E.g.  "GET /games?player=Joe HTTP/1.1..."  -->  "games?player=Joe"
            String temp = request.Substring(request.IndexOf('/') + 1);

            String urlQuery = temp.Substring(0, temp.IndexOf('/') - 5);

            // Depending on the query (scores, games?player=..., or eaten?id=...), we call the corresponding helper method 
            // that gets info from our DB and responds to the browser
            if (urlQuery == "scores")
                SendScores(state);

            // if the query is of the form "games?player=..." 
            else if (urlQuery[0] == 'g')
                SendGamesByPlayer(state, urlQuery);

            else if (urlQuery[0] == 'e')
                SendEatenCubes(state, urlQuery);

            else
                SendErrorMessage(state);

        }

        /// <summary>
        /// Sends a web browser an html page saying their request could not be understood, and gives valid options for a 
        /// proper request.
        /// <param name="state">The state obj containing the callback to end the socket.</param>
        /// </summary>
        private static void SendErrorMessage(PreservedState state)
        {
            // Generate the help message and some useful information for correct places to go.
            string msgToBrowser = "<h1>Whoops! There was an error...</h1><br><h2>The address you entered doesn't exist, try entering a address such as the following:</h2><br><h3>To see statistics for all games : /scores</h3>" +
                "<h3>To see all games by a particular player (e.g. Joe) : /games?player=Joe</h3><h3>To see results for a particular session (e.g. Session 4) : /eaten?id=4</h3>";

            // Send the information to the webpage and close the socket.
            Network.Send(state.socket, HttpResponseHeader);

            Network.Send(state.socket, msgToBrowser, (Object) => { Console.WriteLine("Sent HTML"); state.socket.Close(); return; });
        }

        /// <summary>
        /// Send a web brower and html page stating all the current game statistics of all games ever played.
        /// <param name="state">The state obj containing the callback to end the socket.</param>
        /// </summary>
        private static void SendScores(PreservedState state)
        {
            // Begin generating the information for the html page.
            String html = "<h1>Scores</h1><table border = 1><tr><td><b>Player</b></td><td><b>Max Mass</b></td><td><b>Rank</b></td><td><b>Cubes Eaten</b></td><td><b>Time Alive (MM:SS)</b></td></tr>";

            // Access our database
            using (MySqlConnection conn = new MySqlConnection(DbConnectionString))
            {
                try
                {
                    conn.Open();

                    MySqlCommand command = conn.CreateCommand();

                    // Generate teh command of what information we want from the database.
                    command.CommandText = "select Name, Maximum_Mass, Rank, Number_Of_Cubes_Eaten, Lifespan from Player_Games";

                    // Retrive the information from the database and attach it to the string going to the web browser.
                    using (MySqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            html += "<tr><td><a href = \"http://localhost:11100/games?player=" + reader["Name"] + "\">" + reader["Name"] + "</a>" + "</td><td>" + reader["Maximum_Mass"] + "</td><td>" + reader["Rank"] + "</td><td>" + reader["Number_Of_Cubes_Eaten"] + "</td><td>" + ToDateFormat(reader["Lifespan"].ToString()) + "</td></tr>";
                        }

                        html += "</table>";
                    }
                }
                // Send the error html page if there is an issue.
                catch (Exception e)
                {
                    SendErrorMessage(state);
                }
            }

            // Send the information to the webpage and close the socket.
            Network.Send(state.socket, HttpResponseHeader);

            Network.Send(state.socket, html, (Object) => { Console.WriteLine("Sent HTML"); state.socket.Close(); return; });
        }

        /// <summary>
        /// /// Send a web brower and html page stating all the game statistics of a certain player for all games they have played.
        /// <param name="state">The state obj containing the callback to end the socket.</param>
        /// </summary>
        private static void SendGamesByPlayer(PreservedState state, String req)
        {
            try
            {
                // Retrive the palyer name from the request of whose information we need to pull. If it doesn't exist send an error message.
                if (req.Substring(0, 13) != "games?player=")
                {
                    SendErrorMessage(state);
                    return;
                }

                // Retrieve the player name and begin generating the string to be sent to the web browser.
                string player = req.Substring(req.IndexOf('=') + 1);

                String html = "<h1>Games for " + player + "</h1>";

                html += "<table border = 1><tr><td><b>Max Mass</b></td><td><b>Rank</b></td><td><b>Cubes Eaten</b></td><td><b>Players Eaten</b></td><td><b>Time Alive</b></td><td><b>Time Of Death</b></td></tr>";

                // Access our database
                using (MySqlConnection conn = new MySqlConnection(DbConnectionString))
                {
                    try
                    {
                        conn.Open();

                        MySqlCommand command = conn.CreateCommand();

                        // Generate the command for the desired information we seek from the database.
                        command.CommandText = "select Maximum_Mass, Rank, Number_Of_Cubes_Eaten, Number_Of_Player_Cubes_Eaten, Lifespan, Time_Of_Death from Player_Games where Name = \"" + player + "\"";

                        // Retrieve that inforamtion from the database
                        using (MySqlDataReader reader = command.ExecuteReader())
                        {
                            while (reader.Read())
                            {
                                html += "<tr><td>" + reader["Maximum_Mass"] + "</td><td>" + reader["Rank"] + "</td><td>" + reader["Number_Of_Cubes_Eaten"] + "</td><td>" + reader["Number_Of_Player_Cubes_Eaten"] + "</td><td>" +
                                ToDateFormat(reader["Lifespan"].ToString()) + "</td><td>" + reader["Time_Of_Death"] + "</td></tr>";
                            }

                            html += "</table><br><a href = \"http://localhost:11100/scores\">" + "Go To All Scores" + "</a>";
                        }
                    }
                    // If there is an error send the error html page to the browser
                    catch (Exception e)
                    {
                        SendErrorMessage(state);
                        return;
                    }
                }

                // Send the information to the webpage and close the socket.
                Network.Send(state.socket, HttpResponseHeader);

                Network.Send(state.socket, html, (Object) => { Console.WriteLine("Sent HTML"); state.socket.Close(); return; });
            }
            // If there is an error send the error html page to the browser
            catch (Exception e)
            {
                SendErrorMessage(state);
            }
        }

        /// <summary>
        /// /// /// Send a web brower and html page stating all the game statistics of a certain game session.
        /// <param name="state">The state obj containing the callback to end the socket.</param>
        /// </summary>
        private static void SendEatenCubes(PreservedState state, String req)
        {
            // The game session id requested by the browser
            string id = "";

            // Make sure url has valid format, and pull out the id
            try
            {
                if (req.Substring(0, 9) != "eaten?id=")
                {
                    SendErrorMessage(state);
                    return;
                }

                id = req.Substring(req.IndexOf('=') + 1);
            }
            // If there is an error send the error html page to the browser
            catch (Exception e)
            {
                SendErrorMessage(state);
                return;
            }

            // begin generating the string to be sent to the web browser.
            String html = "<h1>Cubes Eaten During Games Session " + id + "</h1>";

            html += "<table border = 1><tr><td><b>Name</b></td><td><b>Players Eaten</b></td><td><b>Max Mass</b></td><td><b>Rank</b></td><td><b>Cubes Eaten</b></td><td><b>Players Eaten</b></td><td><b>Time Alive (mm:ss)</b></td><td><b>Time Of Death</b></td></tr>";

            String namesOfEatenPlayers = "";

            // Begin requesting information from the database by opening the connection.
            using (MySqlConnection conn = new MySqlConnection(DbConnectionString))
            {
                try
                {
                    conn.Open();

                    MySqlCommand command = conn.CreateCommand();

                    // First, get the names of players eaten, if any exist
                    command.CommandText = "SELECT Players_Eaten FROM Names_Of_Players_Eaten WHERE Names_Of_Players_Eaten.Session_ID = " + id;

                    // Retrive all the eaten players.
                    using (MySqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            namesOfEatenPlayers += reader["Players_Eaten"];
                        }
                    }


                    // Then, get all the rest of the info for the session from the main table
                    command.CommandText = "SELECT Name, Maximum_Mass, Rank, Number_Of_Cubes_Eaten, Number_Of_Player_Cubes_Eaten, Lifespan, Time_Of_Death FROM Player_Games WHERE Player_Games.ID = " + id;

                    using (MySqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            html += "<tr><td>" + reader["Name"] + "</td><td>" + namesOfEatenPlayers + "</td><td>" + reader["Maximum_Mass"] + "</td><td>" + reader["Rank"] + "</td><td>" + reader["Number_Of_Cubes_Eaten"] + "</td><td>" + reader["Number_Of_Player_Cubes_Eaten"] + "</td><td>" +
                            ToDateFormat(reader["Lifespan"].ToString()) + "</td><td>" + reader["Time_Of_Death"] + "</td></tr>";
                        }

                        html += "</table>";
                    }
                }
                // If there is an error send the error html page to the browser
                catch (Exception e)
                {
                    SendErrorMessage(state);
                    return;
                }
            }

            // Send the information to the webpage and close the socket.
            Network.Send(state.socket, HttpResponseHeader);

            Network.Send(state.socket, html, (Object) => { Console.WriteLine("Sent HTML"); state.socket.Close(); return; });

        }




        /// <summary>
        /// This method acts as the callback from Accepting_A_New_Client method. It takes in the state object and updates the callback to be 
        /// RecievePlayerName and then it requests more data from the server to actually recieve the player name.
        /// </summary>
        /// <param name="state">The state obj of the about to about to be added player.</param>
        static void HandleNewClient(PreservedState state)
        {
            // Update the call back and request more data, aka the player name.
            state.callBack = ReceivePlayerName;

            Network.i_want_more_data(state);


        }

        /// <summary>
        /// A Callback that's called when a new client connects.
        /// Gets the client's desired player name, sends the client
        /// their player and the world, and then starts listening
        /// for move and split requests.
        /// </summary>
        /// <param name="state">The state object of the newly added player, it contains its socket, callback and str builder.</param>
        static void ReceivePlayerName(PreservedState state)
        {
            // Pull the player name from the state obj str builder and then clear it.
            String playerName = state.strBuilder.ToString();

            state.strBuilder.Clear();

            // Remove the \n from the end of the player name.
            playerName = playerName.Substring(0, playerName.Length - 1);

            // Declare team id and player cube out here so it can be used in all the locks.
            int team_id;

            Cube playerCube;

            // Lock the world in order to generate information to build the player cube.
            lock (world)
            {
                // Create the player's cube atributes
                Point playerXY = world.GetPlayerSpawnLoc();

                int argb_color = world.SetCubeColor();

                double mass = world.INITIAL_PLAYER_MASS;

                int playerUid = world.GetNextUID();

                team_id = playerUid;

                // Generate the player cube and add it to the world, new players list, and teams list.
                playerCube = new Cube(playerXY.X, playerXY.Y, argb_color, playerUid, team_id, false, playerName, mass);

                world.AddOrUpdateCube(playerCube);

                // Create a team for the player
                world.Teams.Add(team_id, new List<Cube>() { playerCube });

                // Start tracking the player's team's stats
                PlayerSessionStats session = new PlayerSessionStats();
                session.MaximumMass = world.INITIAL_PLAYER_MASS;
                session.CurrentMass = world.INITIAL_PLAYER_MASS;

                world.TeamStatistics.Add(team_id, session);



            }

            // Lock the client sockets set inorder to add the newly created players socket to it.
            lock (clientSocketsLocker)
            {
                ClientSockets.Add(state.socket, team_id);
            }

            // Serialize the player cube to send it to the client.
            String playerCubeStr = JsonConvert.SerializeObject(playerCube) + "\n";

            // Send player their cube
            Network.Send(state.socket, playerCubeStr);

            // Update the callback in order to handle more players potentially adding.
            state.callBack = HandleClientGameRequests;

            // Since there are now players in the game, set this to false so updates can happen.
            noPlayersJoined = false;

            // Send player the current state of the world
            lock (world)
            {
                StringBuilder worldCubes = new StringBuilder();

                foreach (Cube cube in world.Cubes)
                {
                    worldCubes.Append(JsonConvert.SerializeObject(cube) + '\n');
                }

                Network.Send(state.socket, worldCubes.ToString());
            }

            // Request more data from the server.
            Network.i_want_more_data(state);

        }

        /// <summary>
        /// Called whenever we receive a move or split request from a client.
        /// </summary>
        /// <param name="state">The state object of aplayer, it contains its socket, callback and str builder.</param>
        static void HandleClientGameRequests(PreservedState state)
        {
            // Declare these variable to add in the decoding of the string from the network.
            String requests;
            String[] requestArray;
            int team_id;
            String command;

            // Find out what the client's message is
            requests = state.strBuilder.ToString();

            // Pull out and process the first valid request from the network request string.
            String request;

            // Ensure the the request is in a valid format to be processed. 
            if (requests[0] == '(')
            {
                request = requests.Substring(1, requests.IndexOf('\n') - 2);
            }
            // If it is a partial message, look at the next request.
            else
            {
                String temp = requests.Substring(requests.IndexOf('\n') + 1);
                if (temp.Length > 0)
                    request = temp.Substring(1, temp.IndexOf('\n') - 2);
                else
                    return;
            }

            // Clear the remaining duplicate requests.
            state.strBuilder.Clear();

            // Get the team_id of the player from the ClientSockets Set.
            team_id = ClientSockets[state.socket];

            // Split of the request to process all its individual peices.
            requestArray = request.Split(',');

            // Pull out the main command of the request - move or split
            command = requestArray[0];


            // Try and parse the request into the desired double destination values.
            try
            {
                double destX = Convert.ToDouble(requestArray[1].Trim());

                double destY = Convert.ToDouble(requestArray[2].Trim());

                lock (world)
                {
                    if (command == "move")
                    {

                        world.MovePlayer(destX, destY, world.Teams[team_id]);

                    }
                    else // command == "split"
                    {
                        world.Split(destX, destY, team_id);

                        lock (mergeTimerLocker)
                        {
                            StartMergeTimer(team_id);
                        }

                    }
                }
            }

            catch (Exception e)
            {
                Console.WriteLine("Bad Data: " + request);
            }


            // Continue listening for more requests
            Network.i_want_more_data(state);


        }

        /// <summary>
        /// Begins a timer for a team. When the timer goes off, the team is able to merge its
        /// cubes together.
        /// </summary>
        /// <param name="team_id">The player team id of the player cube that has been split.</param>
        private static void StartMergeTimer(int team_id)
        {
            // Create a timer that specifies when the team cubes are able to merge together
            Timer splitMergeTimer = new Timer(world.TIME_UNTIL_SPLIT_CUBES_CAN_MERGE * 1000);

            // If the team already had a merge timer running. Stop it.
            if (world.teamTimers.ContainsKey(team_id))
                world.teamTimers[team_id].Stop();

            world.teamTimers[team_id] = splitMergeTimer;
            splitMergeTimer.Elapsed += new ElapsedEventHandler(splitMergeTimerEvent);
            splitMergeTimer.Start();
        }


        /// <summary>
        /// Check which team's timer went off. Change all of that team's player cubes' .CanMerge property to True 
        /// </summary>
        private static void splitMergeTimerEvent(object source, ElapsedEventArgs e)
        {
            int team = -1;

            // Lock the timer
            lock (mergeTimerLocker)
            {
                // For each cube on a specific team update their timers to stop
                foreach (int teamID in world.teamTimers.Keys)
                {
                    if (source.Equals(world.teamTimers[teamID]))
                    {
                        team = teamID;
                        world.teamTimers[teamID].Stop();
                        world.teamTimers.Remove(teamID);
                        Console.WriteLine(teamID + "'s split merge timer went off");
                        break;
                    }
                }

                // If their team value hasn't changed update the can merger value so they can merge again.
                if (team != -1)
                {
                    foreach (Cube player in world.Teams[team])
                    {
                        player.CanMerge = true;
                    }
                }

                else
                {
                    Console.WriteLine("Couldn't determine which team's timer went off...");
                    Console.ReadLine();
                }

            }
        }



        /// <summary>
        /// This method is the "heartbeat" of the server. It's executed several times per second, as specified by the UPDATES_PER_SECOND member variable.
        /// It creates new food and viruses, atrophies player cubes, and broadcasts to all clients the most recent state of the world.
        /// </summary>
        private static void Update()
        {
            // Lock the world so that we can update it.
            lock (world)
            {
                // Create more food, if needed.
                if (world.foodCountInWorld < world.MAX_FOOD)
                {
                    world.CreateFood(world.NEW_FOOD_AMT_PER_UPDATE, false);
                }

                // Create more viruses, if needed.
                if (world.virusCountInWorld < world.MAX_VIRUSES)
                {
                    int virusesNeeded = world.MAX_VIRUSES - world.virusCountInWorld;

                    world.CreateVirus(virusesNeeded, false);
                }

                // if any cubes have recently been exploded by a virus, start timers for them to re-merge
                if (world.virusMergeTimersToStart.Count > 0)
                {
                    lock (mergeTimerLocker)
                    {
                        foreach (int teamID in world.virusMergeTimersToStart)
                        {
                            StartMergeTimer(teamID);
                        }
                    }

                    world.virusMergeTimersToStart.Clear();

                }



                // Clear out any old data in the StringBuilder
                allWorldCubes.Clear();

                //List<double> TeamMasses = new List<double>();

                //int Rank = 1;

                //foreach (int teamid in world.TeamStatistics.Keys)
                //{

                //    if (world.TeamStatistics[teamid].CurrentMass > world.TeamStatistics[teamid].MaximumMass)
                //    {
                //        world.TeamStatistics[teamid].MaximumMass = world.TeamStatistics[teamid].CurrentMass;
                //    }

                //    TeamMasses.Add(world.TeamStatistics[teamid].CurrentMass);

                //double largestMass = world.TeamStatistics[teamid].MaximumMass;

                //int largestMassId = teamid;

                //foreach (int teamidRank in world.TeamStatistics.Keys)
                //{
                //    if (world.TeamStatistics[teamidRank].MaximumMass > largestMass)
                //    {
                //        largestMass = world.TeamStatistics[teamidRank].MaximumMass;

                //        largestMassId = teamidRank;
                //    }
                //}

                //    TeamMasses.Sort();

                //}
                //foreach (int teamID in world.TeamStatistics.Keys)
                //{
                //    PlayerSessionStats session = world.TeamStatistics[teamID];
                //    if (TeamMasses.Count > 0 && TeamMasses[TeamMasses.Count - 1] == session.CurrentMass)
                //        session.HighestRankAchieved = 1;

                //    else if (TeamMasses.Count > 1 && TeamMasses[TeamMasses.Count - 2] == session.CurrentMass)
                //        session.HighestRankAchieved = 2;

                //    else if (TeamMasses.Count > 2 && TeamMasses[TeamMasses.Count - 3] == session.CurrentMass)
                //        session.HighestRankAchieved = 3;

                //    else if (TeamMasses.Count > 3 && TeamMasses[TeamMasses.Count - 4] == session.CurrentMass)
                //        session.HighestRankAchieved = 4;

                //    else if (TeamMasses.Count > 4 && TeamMasses[TeamMasses.Count - 5] == session.CurrentMass)
                //        session.HighestRankAchieved = 5;

                //}



                // Create a list of all the current teams that exist masses.
                List<double> teamMasses = new List<double>();

                // Loop through the sessions and get all the teams masses.
                foreach (int teamid in world.TeamStatistics.Keys)
                {
                    // Update their mazimum mass value if needed.
                    if (world.TeamStatistics[teamid].CurrentMass > world.TeamStatistics[teamid].MaximumMass)
                    {
                        world.TeamStatistics[teamid].MaximumMass = world.TeamStatistics[teamid].CurrentMass;
                    }

                    // Add each teams current mass to the list.
                    teamMasses.Add(world.TeamStatistics[teamid].CurrentMass);
                }

                // Sort all the teams masses.
                teamMasses.Sort();

                // Loop through and compare all the the current team masses with each other to determine the top 5 players
                // The list is sorted in acsending order so the top 5 largest players are in the bottom of the list.
                foreach (int teamid in world.TeamStatistics.Keys)
                {
                    // If the team's mass is equal to the mass of the last item in the list, its the largest cube and therefore in 1st place.
                    if (world.TeamStatistics[teamid].CurrentMass == teamMasses[teamMasses.Count - 1])
                    {
                        if (world.TeamStatistics[teamid].HighestRankAchieved > 1)
                        {
                            world.TeamStatistics[teamid].HighestRankAchieved = 1;
                        }
                    }
                    // If the team's mass is equal to the mass of the second to last item in the list, its the largest cube and therefore in 2nd place.
                    else if (world.TeamStatistics[teamid].CurrentMass == teamMasses[teamMasses.Count - 2])
                    {
                        if (world.TeamStatistics[teamid].HighestRankAchieved > 2)
                        {
                            world.TeamStatistics[teamid].HighestRankAchieved = 2;
                        }
                    }
                    // If the team's mass is equal to the mass of the third to last item in the list, its the largest cube and therefore in 3rd place.
                    else if (world.TeamStatistics[teamid].CurrentMass == teamMasses[teamMasses.Count - 3])
                    {
                        if (world.TeamStatistics[teamid].HighestRankAchieved > 3)
                        {
                            world.TeamStatistics[teamid].HighestRankAchieved = 3;
                        }
                    }
                    // If the team's mass is equal to the mass of the fourth to last item in the list, its the largest cube and therefore in 4th place.
                    else if (world.TeamStatistics[teamid].CurrentMass == teamMasses[teamMasses.Count - 4])
                    {
                        if (world.TeamStatistics[teamid].HighestRankAchieved > 4)
                        {
                            world.TeamStatistics[teamid].HighestRankAchieved = 4;
                        }
                    }
                    // If the team's mass is equal to the mass of the fifth to last item in the list, its the largest cube and therefore in 5th place.
                    else if (world.TeamStatistics[teamid].CurrentMass == teamMasses[teamMasses.Count - 5])
                    {
                        if (world.TeamStatistics[teamid].HighestRankAchieved > 5)
                        {
                            world.TeamStatistics[teamid].HighestRankAchieved = 5;
                        }
                    }
                }



                // Perform mass attrition on player cubes, update DB if any players have died,
                // and send a String of all the recently modified cubes to all clients.
                foreach (Cube cube in world.CubesChangedSinceLastUpdate)
                {
                    if (cube.food == false)
                    {

                        // If any players have died, update the game Database with their session stats
                        if (cube.uid == cube.team_id && cube.Mass == 0 && !DeadPlayersUIDs.Contains(cube.uid))
                        {
                            DeadPlayersUIDs.Add(cube.uid);
                            UpdateDbWithDeadPlayerStats(cube);
                        }


                        // Update the attrition value for the player cubes if they have grown or shrunk.
                        if (cube.Mass > world.RAPID_ATTRITION_THRESHOLD)
                            cube.Mass -= world.HIGH_ATTRITION_PER_UPDATE;

                        else if (cube.Mass > world.ATTRITION_LOWER_MASS_LIMIT)
                            cube.Mass -= world.NORMAL_ATTRITION_PER_UPDATE;
                    }

                    // Serialize the cube so that it can be sent as a string. 
                    allWorldCubes.Append(JsonConvert.SerializeObject(cube) + '\n');
                }

                // Empty the set for the next update.
                world.CubesChangedSinceLastUpdate.Clear();


                // Send the recently modified cubes to all connected clients.
                lock (clientSocketsLocker)
                {
                    foreach (Socket socket in ClientSockets.Keys)
                    {
                        Network.Send(socket, allWorldCubes.ToString());
                    }

                }
            }
        }


        /// <summary>
        /// Updates the server database with statistics about a player's game session after the player dies.
        /// </summary>
        private static void UpdateDbWithDeadPlayerStats(Cube player)
        {
            // playerStats contains the info we want to store in the DB
            PlayerSessionStats playerStats = world.TeamStatistics[player.team_id];

            // Mark the player's time of death and calculate how long they survived, in seconds
            DateTime timeOfDeath = DateTime.Now;
            DateTime timeOfBirth = playerStats.TimeOfBirth;
            double timeAlive = timeOfDeath.Subtract(timeOfBirth).TotalSeconds;

            String rank;
            if (playerStats.HighestRankAchieved == int.MaxValue)
            {
                rank = "NR";
            }
            else
            {
                rank = playerStats.HighestRankAchieved + "";
            }


            int totalCubesEaten = playerStats.NumberOfFoodEaten + playerStats.NumberOfPlayersEaten;

            using (MySqlConnection conn = new MySqlConnection(DbConnectionString))
            {
                try
                {
                    // Open a DB connection
                    conn.Open();

                    // Create an SQL command that enters player's info into the Player_Games DB table
                    MySqlCommand command = conn.CreateCommand();

                    command.CommandText = "INSERT INTO Player_Games (Name, Lifespan, Maximum_Mass, Number_Of_Cubes_Eaten, Number_Of_Food_Eaten, Number_Of_Player_Cubes_Eaten, Rank) VALUES (\"" +
                        player.Name + "\", " + timeAlive + ", " + playerStats.MaximumMass + ", " + totalCubesEaten + ", " + playerStats.NumberOfFoodEaten + ", " +
                        playerStats.NumberOfPlayersEaten + ", " + rank + ")";

                    // Run the command
                    command.ExecuteNonQuery();

                }
                catch (Exception e)
                {
                    Console.WriteLine("Error with Database: " + e.ToString());
                }
            }
            // If the player obtained a Top-5 position or ate any player cubes, record this info in the appropriate DB tables
            // For these insertions, we use the SessionID as the primary key. However, we don't have the SessionID so we must
            // query the Player_Games table for it. The SessionID is an integer. The first session in the Player_Games table
            // has a SessionID of 1, the second session recorded in the table has a SessionID of 2, etc. So the sessionID
            // for this player's game is the COUNT of all records in the Player_Games table.



            using (MySqlConnection conn = new MySqlConnection(DbConnectionString))
            {
                try
                {
                    // Open a DB connection
                    conn.Open();

                    // Create an SQL command that enters player's info into the Player_Games DB table
                    MySqlCommand command = conn.CreateCommand();
                    // Create command that returns the number of rows (i.e., the most recent SessionID) in the Player_Games table
                    command.CommandText = "SELECT * FROM Player_Games";

                    int sessionID = 0;

                    using (MySqlDataReader reader = command.ExecuteReader())
                    {
                        //sessionID = Convert.ToInt32(command.ExecuteScalar());

                        while (reader.Read())
                        {
                            sessionID++;
                        }
                    }

                    

                    // If the player ate any cubes, concatenate the cube names into a single string and make a record in the
                    // Names_Of_Players_Eaten table in the DB, using the SessionID as the primary key
                    if (playerStats.NamesOfPlayersEaten.Count > 0)
                    {
                        string eatenPlayerNames = "";
                        foreach (string playerName in playerStats.NamesOfPlayersEaten)
                        {
                            eatenPlayerNames += playerName + ", ";
                        }

                        command.CommandText = "INSERT INTO Names_Of_Players_Eaten(Session_ID, Players_Eaten) VALUES (" +
                            sessionID + ", \"" + eatenPlayerNames + "\")";


                        command.ExecuteNonQuery();
                    }


                }
                catch (Exception e)
                {
                    Console.WriteLine("Error with Database: " + e.ToString());
                }
            }
        }


        /// <summary>
        /// Converts a string representing an amount of seconds into a more presentable MM:SS format.
        /// E.g. "75" --> "1:15"
        /// </summary>
        public static string ToDateFormat(string seconds)
        {
            double secs = Convert.ToDouble(seconds);
            int minutes = (int)(secs / 60);
            int remainingSecs = (int)(secs % 60);

            return minutes + ":" + remainingSecs;
        }
    }
}
