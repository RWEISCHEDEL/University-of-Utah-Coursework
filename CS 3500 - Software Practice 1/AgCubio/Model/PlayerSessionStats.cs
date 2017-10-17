// Written by Jackson Murphy and Robert Weischedel for CS 3500.
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AgCubio
{
    /// <summary>
    /// Keeps track of various statistics for a player during the course of a single AgCubio game.
    /// </summary>
    public class PlayerSessionStats
    {

        /// <summary>
        /// When the player began their game
        /// </summary>
        public DateTime TimeOfBirth { get; set; }

        /// <summary>
        /// When the player died (all of his cubes were eaten)
        /// </summary>
        public DateTime TimeOfDeath { get; set; }

        /// <summary>
        /// If the player was ever one of the Top-5 highest masses, record
        /// their rank here. Invariant: -1 if the player never makes it into
        /// the Top-5
        /// </summary>
        public int HighestRankAchieved { get; set; }

        /// <summary>
        /// The maximum aggregate mass that the player achieved with his cube(s)
        /// </summary>
        public double MaximumMass { get; set; }

        /// <summary>
        /// The current aggregate mass that a player has
        /// </summary>
        public double CurrentMass { get; set; }

        /// <summary>
        /// The number of food cubes the player ate during the course of the game
        /// </summary>
        public int NumberOfFoodEaten { get; set; }

        /// <summary>
        /// The number of other player cubes the player ate during the course of the game
        /// </summary>
        public int NumberOfPlayersEaten { get; set; }

        /// <summary>
        /// The names of the other players that this player ate.
        /// </summary>
        public HashSet<String> NamesOfPlayersEaten { get; set; }

        /// <summary>
        /// Creates a PlayerSessionStats object. Does not initialize MaximumMass nor TimeOfDeath (these are left as null). Initializes the
        /// other properties. 
        /// </summary>
        public PlayerSessionStats()
        {
            TimeOfBirth = DateTime.Now;
            NumberOfFoodEaten = 0;
            NumberOfPlayersEaten = 0;
            NamesOfPlayersEaten = new HashSet<string>();
            HighestRankAchieved = int.MaxValue;
            
        }

    }
}
