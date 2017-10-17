namespace AgCubio
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.AgCubioLabel = new System.Windows.Forms.Label();
            this.PlayerNameLabel = new System.Windows.Forms.Label();
            this.ServerNameLabel = new System.Windows.Forms.Label();
            this.PlayerNameTextBox = new System.Windows.Forms.TextBox();
            this.ServerNameTextBox = new System.Windows.Forms.TextBox();
            this.button1 = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // AgCubioLabel
            // 
            this.AgCubioLabel.Anchor = System.Windows.Forms.AnchorStyles.Top;
            this.AgCubioLabel.AutoSize = true;
            this.AgCubioLabel.Font = new System.Drawing.Font("hooge 05_53", 50F);
            this.AgCubioLabel.ForeColor = System.Drawing.Color.White;
            this.AgCubioLabel.Location = new System.Drawing.Point(193, 9);
            this.AgCubioLabel.Name = "AgCubioLabel";
            this.AgCubioLabel.Size = new System.Drawing.Size(320, 84);
            this.AgCubioLabel.TabIndex = 0;
            this.AgCubioLabel.Text = "AgCubio";
            // 
            // PlayerNameLabel
            // 
            this.PlayerNameLabel.AutoSize = true;
            this.PlayerNameLabel.Font = new System.Drawing.Font("hooge 05_53", 21.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.PlayerNameLabel.ForeColor = System.Drawing.Color.White;
            this.PlayerNameLabel.Location = new System.Drawing.Point(103, 136);
            this.PlayerNameLabel.Name = "PlayerNameLabel";
            this.PlayerNameLabel.Size = new System.Drawing.Size(211, 36);
            this.PlayerNameLabel.TabIndex = 1;
            this.PlayerNameLabel.Text = "Player Name";
            // 
            // ServerNameLabel
            // 
            this.ServerNameLabel.AutoSize = true;
            this.ServerNameLabel.Font = new System.Drawing.Font("hooge 05_53", 21.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ServerNameLabel.ForeColor = System.Drawing.Color.White;
            this.ServerNameLabel.Location = new System.Drawing.Point(103, 216);
            this.ServerNameLabel.Name = "ServerNameLabel";
            this.ServerNameLabel.Size = new System.Drawing.Size(121, 36);
            this.ServerNameLabel.TabIndex = 2;
            this.ServerNameLabel.Text = "Server";
            // 
            // PlayerNameTextBox
            // 
            this.PlayerNameTextBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 22F);
            this.PlayerNameTextBox.Location = new System.Drawing.Point(335, 131);
            this.PlayerNameTextBox.Name = "PlayerNameTextBox";
            this.PlayerNameTextBox.Size = new System.Drawing.Size(248, 41);
            this.PlayerNameTextBox.TabIndex = 3;
            this.PlayerNameTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.PlayerNameTextBox_KeyPress);
            // 
            // ServerNameTextBox
            // 
            this.ServerNameTextBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 22F);
            this.ServerNameTextBox.Location = new System.Drawing.Point(335, 215);
            this.ServerNameTextBox.Name = "ServerNameTextBox";
            this.ServerNameTextBox.Size = new System.Drawing.Size(248, 41);
            this.ServerNameTextBox.TabIndex = 4;
            this.ServerNameTextBox.Text = "localhost";
            this.ServerNameTextBox.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.ServerNameTextBox_KeyPress);
            // 
            // button1
            // 
            this.button1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(128)))), ((int)(((byte)(0)))));
            this.button1.Font = new System.Drawing.Font("hooge 05_53", 30F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.button1.ForeColor = System.Drawing.Color.Black;
            this.button1.Location = new System.Drawing.Point(109, 315);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(474, 67);
            this.button1.TabIndex = 5;
            this.button1.Text = "Play!";
            this.button1.UseVisualStyleBackColor = false;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Black;
            this.ClientSize = new System.Drawing.Size(755, 421);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.ServerNameTextBox);
            this.Controls.Add(this.PlayerNameTextBox);
            this.Controls.Add(this.ServerNameLabel);
            this.Controls.Add(this.PlayerNameLabel);
            this.Controls.Add(this.AgCubioLabel);
            this.ForeColor = System.Drawing.Color.Black;
            this.Name = "Form1";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label AgCubioLabel;
        private System.Windows.Forms.Label PlayerNameLabel;
        private System.Windows.Forms.Label ServerNameLabel;
        private System.Windows.Forms.TextBox PlayerNameTextBox;
        private System.Windows.Forms.TextBox ServerNameTextBox;
        private System.Windows.Forms.Button button1;
    }
}

