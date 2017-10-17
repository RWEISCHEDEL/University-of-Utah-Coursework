// Written by Jackson Murphy for CS 3500. Last updated December 7th, 2015
using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using MySql.Data.MySqlClient;
using AgCubio;
using System.Collections.Generic;

namespace AgCubioUnitTests
{
    [TestClass]
    public class ProgramUnitTests
    {
        /// <summary>
        /// Checks whether we are properly inserting player stats into the
        /// Player_Games table
        /// </summary>
        [TestMethod]
        public void TestDatabaseInsertionPlayerGameTable()
        {
            String DbConnectionString = "server=atr.eng.utah.edu;database=cs3500_jmurphy;uid=cs3500_jmurphy;password=PSWRD";

            //String date = DateTime.Now.ToString();

            using (MySqlConnection conn = new MySqlConnection(DbConnectionString))
            {
                try
                {
                    // Open a DB connection
                    conn.Open();

                    // Create an SQL command to enter player's info into DB
                    MySqlCommand command = conn.CreateCommand();

                    command.CommandText = "INSERT INTO Player_Games (Name, Lifespan, Maximum_Mass, Number_Of_Cubes_Eaten, Number_Of_Food_Eaten, Number_Of_Player_Cubes_Eaten) VALUES (" +
                        "'Jackson'" + ", " + 10000 + ", " + 1000 + ", " + 1 + ", " + 1 + ", " +
                        0 + ")";

                    // Insert
                    command.ExecuteNonQuery();

                    Console.ReadLine();

                }
                catch (Exception e)
                {
                    Console.WriteLine("Error with Database: " + e.ToString());
                    Console.ReadLine();
                }
            }
        }

        /// <summary>
        /// Checks whether we are properly querying DB for the number of rows in the
        /// Player_Games table
        /// </summary>
        [TestMethod]
        public void TestDatabaseRetrieveNumberOfRecords()
        {
            String DbConnectionString = "server=atr.eng.utah.edu;database=cs3500_jmurphy;uid=cs3500_jmurphy;password=PSWRD";

            using (MySqlConnection conn = new MySqlConnection(DbConnectionString))
            {
                try
                {
                    // Open a DB connection
                    conn.Open();

                    // Create an SQL command to enter player's info into DB
                    MySqlCommand command = conn.CreateCommand();

                    command.CommandText = "SELECT COUNT(*) FROM Player_Games";

                    int rowsInTable;

                    //using (MySqlDataReader reader = command.ExecuteReader())
                    //{
                    rowsInTable = Convert.ToInt32(command.ExecuteScalar());
                    //}

                    int sessionID = rowsInTable;



                }
                catch (Exception e)
                {
                    Console.WriteLine("Error with Database: " + e.ToString());
                    Console.ReadLine();
                }

            }
        }


        /// <summary>
        /// Checks whether we are properly inserting player stats into the Names_Of_Players_eaten table
        /// </summary>
        [TestMethod]
        public void TestDatabaseInsertionEatenPlayersTable()
        {
            String DbConnectionString = "server=atr.eng.utah.edu;database=cs3500_jmurphy;uid=cs3500_jmurphy;password=PSWRD";

            using (MySqlConnection conn = new MySqlConnection(DbConnectionString))
            {
                try
                {
                    // Open a DB connection
                    conn.Open();

                    // Create an SQL command to enter player's info into DB
                    MySqlCommand command = conn.CreateCommand();

                    command.CommandText = "INSERT INTO Names_Of_Players_Eaten (Session_ID, Players_Eaten) VALUES (" +
                        4 + ", " + "\"Jim, Joe, Frank, Amit,\" " + ")";

                    // Insert
                    command.ExecuteNonQuery();

                    Console.ReadLine();

                }
                catch (Exception e)
                {
                    Console.WriteLine("Error with Database: " + e.ToString());
                    Console.ReadLine();
                }
            }
        }

        /// <summary>
        /// This test ensures that the ranking loop works for just one player.
        /// </summary>
        [TestMethod]
        public void TestRankingLoopInUpdate1Player()
        {
            World world = new World("C:\\Users\\weisched\\Source\\Repos\\JacksonRepo\\AgCubio\\Resources\\XMLWorldParameters.xml");

            Cube player1 = new Cube(100.0, 100.0, -2134567, 1, 1, false, "Player1", 1000);

            world.AddOrUpdateCube(player1);

            PlayerSessionStats player1Stats = new PlayerSessionStats();

            world.TeamStatistics.Add(player1.team_id, player1Stats);

            world.TeamStatistics[player1.team_id].CurrentMass = player1.Mass;

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

            Assert.AreEqual(1, player1Stats.HighestRankAchieved);
        }

        /// <summary>
        /// This test ensures that the ranking loop works for three different players.
        /// </summary>
        [TestMethod]
        public void TestRankingLoopInUpdate3players()
        {

            // Create world.
            World world = new World("C:\\Users\\weisched\\Source\\Repos\\JacksonRepo\\AgCubio\\Resources\\XMLWorldParameters.xml");

            // Create Player1
            Cube player1 = new Cube(100.0, 100.0, -2134567, 1, 1, false, "Player1", 1000);

            world.AddOrUpdateCube(player1);

            PlayerSessionStats player1Stats = new PlayerSessionStats();

            world.TeamStatistics.Add(player1.team_id, player1Stats);

            world.TeamStatistics[player1.team_id].CurrentMass = player1.Mass;

            // Create Player2
            Cube player2 = new Cube(100.0, 100.0, -2134567, 2, 2, false, "Player2", 2000);

            world.AddOrUpdateCube(player2);

            PlayerSessionStats player2Stats = new PlayerSessionStats();

            world.TeamStatistics.Add(player2.team_id, player2Stats);

            world.TeamStatistics[player2.team_id].CurrentMass = player2.Mass;

            // Create Player3
            Cube player3 = new Cube(100.0, 100.0, -2134567, 3, 3, false, "Player3", 3000);

            world.AddOrUpdateCube(player3);

            PlayerSessionStats player3Stats = new PlayerSessionStats();

            world.TeamStatistics.Add(player3.team_id, player3Stats);

            world.TeamStatistics[player3.team_id].CurrentMass = player3.Mass;

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

            Assert.AreEqual(3, player1Stats.HighestRankAchieved);

            Assert.AreEqual(2, player2Stats.HighestRankAchieved);

            Assert.AreEqual(1, player3Stats.HighestRankAchieved);
        }




        /// <summary>
        /// This test ensures that the ranking loop works for six different players. Then it changes the masses of a few player cubes to see if the ranks update if the rank was only 
        /// greater than that player cubes preivously greater rank.
        /// </summary>
        [TestMethod]
        public void TestRankingLoopInUpdate6players()
        {

            // Create world.
            World world = new World("C:\\Users\\weisched\\Source\\Repos\\JacksonRepo\\AgCubio\\Resources\\XMLWorldParameters.xml");

            // Create Player1
            Cube player1 = new Cube(100.0, 100.0, -2134567, 1, 1, false, "Player1", 1000);

            world.AddOrUpdateCube(player1);

            PlayerSessionStats player1Stats = new PlayerSessionStats();

            world.TeamStatistics.Add(player1.team_id, player1Stats);

            world.TeamStatistics[player1.team_id].CurrentMass = player1.Mass;

            // Create Player2
            Cube player2 = new Cube(100.0, 100.0, -2134567, 2, 2, false, "Player2", 2000);

            world.AddOrUpdateCube(player2);

            PlayerSessionStats player2Stats = new PlayerSessionStats();

            world.TeamStatistics.Add(player2.team_id, player2Stats);

            world.TeamStatistics[player2.team_id].CurrentMass = player2.Mass;

            // Create Player3
            Cube player3 = new Cube(100.0, 100.0, -2134567, 3, 3, false, "Player3", 3000);

            world.AddOrUpdateCube(player3);

            PlayerSessionStats player3Stats = new PlayerSessionStats();

            world.TeamStatistics.Add(player3.team_id, player3Stats);

            world.TeamStatistics[player3.team_id].CurrentMass = player3.Mass;


            // Create Player4
            Cube player4 = new Cube(100.0, 100.0, -2134567, 4, 4, false, "Player1", 4000);

            world.AddOrUpdateCube(player4);

            PlayerSessionStats player4Stats = new PlayerSessionStats();

            world.TeamStatistics.Add(player4.team_id, player4Stats);

            world.TeamStatistics[player4.team_id].CurrentMass = player4.Mass;

            // Create Player5
            Cube player5 = new Cube(100.0, 100.0, -2134567, 5, 5, false, "Player2", 5000);

            world.AddOrUpdateCube(player5);

            PlayerSessionStats player5Stats = new PlayerSessionStats();

            world.TeamStatistics.Add(player5.team_id, player5Stats);

            world.TeamStatistics[player5.team_id].CurrentMass = player5.Mass;

            // Create Player6
            Cube player6 = new Cube(100.0, 100.0, -2134567, 6, 6, false, "Player3", 6000);

            world.AddOrUpdateCube(player6);

            PlayerSessionStats player6Stats = new PlayerSessionStats();

            world.TeamStatistics.Add(player6.team_id, player6Stats);

            world.TeamStatistics[player6.team_id].CurrentMass = player6.Mass;

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

            Assert.AreEqual(int.MaxValue, player1Stats.HighestRankAchieved);

            Assert.AreEqual(5, player2Stats.HighestRankAchieved);

            Assert.AreEqual(4, player3Stats.HighestRankAchieved);

            Assert.AreEqual(3, player4Stats.HighestRankAchieved);

            Assert.AreEqual(2, player5Stats.HighestRankAchieved);

            Assert.AreEqual(1, player6Stats.HighestRankAchieved);


            world.TeamStatistics[player1.team_id].CurrentMass = 10000;

            world.TeamStatistics[player2.team_id].CurrentMass = 5000;

            world.TeamStatistics[player6.team_id].CurrentMass = 1000;


            // Create a list of all the current teams that exist masses.
            teamMasses.Clear();

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


            Assert.AreEqual(1, player1Stats.HighestRankAchieved);

            Assert.AreEqual(2, player2Stats.HighestRankAchieved);

            Assert.AreEqual(4, player3Stats.HighestRankAchieved);

            Assert.AreEqual(3, player4Stats.HighestRankAchieved);

            Assert.AreEqual(2, player5Stats.HighestRankAchieved);

            Assert.AreEqual(1, player6Stats.HighestRankAchieved);
        }



    }
}
