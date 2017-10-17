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
    /// This Form represents the starting game menu for the AgCubio game.
    /// </summary>
    public partial class Form1 : Form
    {
        /// <summary>
        /// This member variable stores a reference to Form2 or the game window form. 
        /// </summary>
        private Form2 gameActionForm;

        /// <summary>
        /// This member variable stores a reference to Form3 or the player death form.
        /// </summary>
        private Form3 gameOverForm;

        /// <summary>
        /// This acts as the constructor for the opening window form. 
        /// </summary>
        public Form1()
        {
            InitializeComponent();
        }

        /// <summary>
        /// This method handles when the Play button is clicked. It ensures that both the Player name and Server name text boxes have values in them, if not it displays 
        /// a dialog box warning the user.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void button1_Click(object sender, EventArgs e)
        {
            // Ensure there is something in both text boxes, if not display a error message dialog box. 
            if (ServerNameTextBox.Text == "")
            {
                MessageBox.Show("Please enter a player name and the address of the game server.");
            }
            // Create the game window form and show it, hide this current window.
            else
            {
                gameActionForm = new Form2(PlayerNameTextBox.Text, ServerNameTextBox.Text, this);
                
                gameActionForm.Show();

                this.Hide();
            }
        }

        /// <summary>
        /// This method handles and allows the user to hit enter from the Player name text box instead of clicking Play button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void PlayerNameTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == (char)Keys.Enter)
            {
                button1_Click(null, null);
            }
        }

        /// <summary>
        /// This method handles and allows the user to hit enter from the server name text box instead of clicking Play button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ServerNameTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == (char)Keys.Enter)
            {
                button1_Click(null, null);
            }
        }

        /// <summary>
        /// This method handles the calling of Form 3 from Form 2, allowing the player death screen to be called from this Form allowing Form2 to close after player death. 
        /// </summary>
        /// <param name="endingPlayerMass"></param>
        /// <param name="totalPlayerTime"></param>
        public void openForm3(int endingPlayerMass, double totalPlayerTime)
        {
            // Create a new Form3 and feed it the total ending mass of the player, the total time played and a reference to this Form.
            gameOverForm = new Form3(endingPlayerMass, totalPlayerTime, this);

            // Show this Form3 and hide this form.
            gameOverForm.Show();
            this.Hide();
        }
    }
}
