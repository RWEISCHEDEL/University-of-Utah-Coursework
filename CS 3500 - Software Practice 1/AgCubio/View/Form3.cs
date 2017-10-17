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

namespace AgCubio
{
    /// <summary>
    /// This Form represents the player death menu for the AgCubio game.
    /// </summary>
    public partial class Form3 : Form
    {
        /// <summary>
        /// This member variable stores a reference to Form1 or the start menu form. 
        /// </summary>
        private Form1 startMenu;

        /// <summary>
        /// This member variable stores the final total ending mass of the player upon death.
        /// </summary>
        private int endingPlayerMass;

        /// <summary>
        /// This member varaible stores the total time the player was alive in seconds.
        /// </summary>
        private double totalTimePlayed;

        /// <summary>
        /// This acts as the constructor for the player death window form. 
        /// </summary>
        public Form3()
        {
            InitializeComponent();
        }

        /// <summary>
        /// This acts as the constructor for the player death window form. It brings in and stores the final toatl ending mass of the player, the total time the player was alive, and a reference
        /// to the game start menu.
        /// </summary>
        /// <param name="endingPlayerMass">An int of the final total ending mass of the player upon death.</param>
        /// <param name="totalTimePlayed">A double of the total time the player was alive in seconds.</param>
        /// <param name="startMenu"></param>
        public Form3(int endingPlayerMass, double totalTimePlayed, Form1 startMenu)
        {
            // Store all the values in their respective member variables and initialize this form.
            this.endingPlayerMass = endingPlayerMass;

            this.totalTimePlayed = totalTimePlayed;

            this.startMenu = startMenu;

            InitializeComponent();
        }

        /// <summary>
        /// This handles when the play again button is clicked.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void PlayAgainButton_Click(object sender, EventArgs e)
        {
            // Close this form, by first calling Form3_FormClosing.
            this.Close();
        }

        /// <summary>
        /// This handles when the Form is just about to close. 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Form3_FormClosing(object sender, FormClosingEventArgs e)
        {
            // Before this form closes, open the start menu form.
            this.startMenu.Show();
            
        }

        /// <summary>
        /// This handles all the information and processing that needs to be handled right when the form loads. It updates the MassData and TimePlayed Labels.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Form3_Load(object sender, EventArgs e)
        {
            // Retrieve the brought in information.
            GameOverMassDataLabel.Text = endingPlayerMass.ToString();

            GameOverTimePlayedDataLabel.Text = totalTimePlayed.ToString() + " Seconds";

            // Refresh the label
            GameOverMassDataLabel.Refresh();

            GameOverPlayTimeLabel.Refresh();
        }
    }
}
