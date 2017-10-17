namespace AgCubio
{
    partial class Form3
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
            this.GameOverBannerLabel = new System.Windows.Forms.Label();
            this.GameOverMassLabel = new System.Windows.Forms.Label();
            this.GameOverPlayTimeLabel = new System.Windows.Forms.Label();
            this.GameOverMassDataLabel = new System.Windows.Forms.Label();
            this.GameOverTimePlayedDataLabel = new System.Windows.Forms.Label();
            this.PlayAgainButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // GameOverBannerLabel
            // 
            this.GameOverBannerLabel.Anchor = System.Windows.Forms.AnchorStyles.Top;
            this.GameOverBannerLabel.AutoSize = true;
            this.GameOverBannerLabel.Font = new System.Drawing.Font("hooge 05_53", 70F);
            this.GameOverBannerLabel.ForeColor = System.Drawing.Color.White;
            this.GameOverBannerLabel.Location = new System.Drawing.Point(14, 32);
            this.GameOverBannerLabel.Name = "GameOverBannerLabel";
            this.GameOverBannerLabel.Size = new System.Drawing.Size(602, 117);
            this.GameOverBannerLabel.TabIndex = 0;
            this.GameOverBannerLabel.Text = "Game Over";
            this.GameOverBannerLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // GameOverMassLabel
            // 
            this.GameOverMassLabel.AutoSize = true;
            this.GameOverMassLabel.Font = new System.Drawing.Font("hooge 05_53", 20F);
            this.GameOverMassLabel.ForeColor = System.Drawing.Color.White;
            this.GameOverMassLabel.Location = new System.Drawing.Point(28, 210);
            this.GameOverMassLabel.Name = "GameOverMassLabel";
            this.GameOverMassLabel.Size = new System.Drawing.Size(197, 34);
            this.GameOverMassLabel.TabIndex = 1;
            this.GameOverMassLabel.Text = "High Mass : ";
            // 
            // GameOverPlayTimeLabel
            // 
            this.GameOverPlayTimeLabel.AutoSize = true;
            this.GameOverPlayTimeLabel.Font = new System.Drawing.Font("hooge 05_53", 20F);
            this.GameOverPlayTimeLabel.ForeColor = System.Drawing.Color.White;
            this.GameOverPlayTimeLabel.Location = new System.Drawing.Point(28, 290);
            this.GameOverPlayTimeLabel.Name = "GameOverPlayTimeLabel";
            this.GameOverPlayTimeLabel.Size = new System.Drawing.Size(224, 34);
            this.GameOverPlayTimeLabel.TabIndex = 2;
            this.GameOverPlayTimeLabel.Text = "Time Played : ";
            // 
            // GameOverMassDataLabel
            // 
            this.GameOverMassDataLabel.AutoSize = true;
            this.GameOverMassDataLabel.Font = new System.Drawing.Font("hooge 05_53", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.GameOverMassDataLabel.ForeColor = System.Drawing.Color.White;
            this.GameOverMassDataLabel.Location = new System.Drawing.Point(278, 210);
            this.GameOverMassDataLabel.Name = "GameOverMassDataLabel";
            this.GameOverMassDataLabel.Size = new System.Drawing.Size(170, 34);
            this.GameOverMassDataLabel.TabIndex = 3;
            this.GameOverMassDataLabel.Text = "Mass Data";
            // 
            // GameOverTimePlayedDataLabel
            // 
            this.GameOverTimePlayedDataLabel.AutoSize = true;
            this.GameOverTimePlayedDataLabel.Font = new System.Drawing.Font("hooge 05_53", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.GameOverTimePlayedDataLabel.ForeColor = System.Drawing.Color.White;
            this.GameOverTimePlayedDataLabel.Location = new System.Drawing.Point(278, 290);
            this.GameOverTimePlayedDataLabel.Name = "GameOverTimePlayedDataLabel";
            this.GameOverTimePlayedDataLabel.Size = new System.Drawing.Size(268, 34);
            this.GameOverTimePlayedDataLabel.TabIndex = 4;
            this.GameOverTimePlayedDataLabel.Text = "Time Played Data";
            // 
            // PlayAgainButton
            // 
            this.PlayAgainButton.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(128)))), ((int)(((byte)(0)))));
            this.PlayAgainButton.Font = new System.Drawing.Font("hooge 05_53", 30F);
            this.PlayAgainButton.Location = new System.Drawing.Point(34, 375);
            this.PlayAgainButton.Name = "PlayAgainButton";
            this.PlayAgainButton.Size = new System.Drawing.Size(512, 66);
            this.PlayAgainButton.TabIndex = 5;
            this.PlayAgainButton.Text = "Play Again?";
            this.PlayAgainButton.UseVisualStyleBackColor = false;
            this.PlayAgainButton.Click += new System.EventHandler(this.PlayAgainButton_Click);
            // 
            // Form3
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Black;
            this.ClientSize = new System.Drawing.Size(628, 576);
            this.Controls.Add(this.PlayAgainButton);
            this.Controls.Add(this.GameOverTimePlayedDataLabel);
            this.Controls.Add(this.GameOverMassDataLabel);
            this.Controls.Add(this.GameOverPlayTimeLabel);
            this.Controls.Add(this.GameOverMassLabel);
            this.Controls.Add(this.GameOverBannerLabel);
            this.ForeColor = System.Drawing.Color.Black;
            this.Name = "Form3";
            this.Text = "Form3";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Form3_FormClosing);
            this.Load += new System.EventHandler(this.Form3_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label GameOverBannerLabel;
        private System.Windows.Forms.Label GameOverMassLabel;
        private System.Windows.Forms.Label GameOverPlayTimeLabel;
        private System.Windows.Forms.Label GameOverMassDataLabel;
        private System.Windows.Forms.Label GameOverTimePlayedDataLabel;
        private System.Windows.Forms.Button PlayAgainButton;
    }
}