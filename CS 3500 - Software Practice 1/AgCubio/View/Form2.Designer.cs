namespace AgCubio
{
    partial class Form2
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
            this.backgroundWorker1 = new System.ComponentModel.BackgroundWorker();
            this.helpProvider1 = new System.Windows.Forms.HelpProvider();
            this.PlayerInfoFPSLabel = new System.Windows.Forms.Label();
            this.PlayerInfoFPSDataLabel = new System.Windows.Forms.Label();
            this.PlayerInfoMassLabel = new System.Windows.Forms.Label();
            this.PlayerInfoMassDataLabel = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // backgroundWorker1
            // 
            this.backgroundWorker1.DoWork += new System.ComponentModel.DoWorkEventHandler(this.backgroundWorker1_DoWork);
            // 
            // PlayerInfoFPSLabel
            // 
            this.PlayerInfoFPSLabel.AutoSize = true;
            this.PlayerInfoFPSLabel.ForeColor = System.Drawing.Color.White;
            this.PlayerInfoFPSLabel.Location = new System.Drawing.Point(1077, 26);
            this.PlayerInfoFPSLabel.Name = "PlayerInfoFPSLabel";
            this.PlayerInfoFPSLabel.Size = new System.Drawing.Size(36, 13);
            this.PlayerInfoFPSLabel.TabIndex = 6;
            this.PlayerInfoFPSLabel.Text = "FPS : ";
            // 
            // PlayerInfoFPSDataLabel
            // 
            this.PlayerInfoFPSDataLabel.AutoSize = true;
            this.PlayerInfoFPSDataLabel.ForeColor = System.Drawing.Color.White;
            this.PlayerInfoFPSDataLabel.Location = new System.Drawing.Point(1119, 26);
            this.PlayerInfoFPSDataLabel.Name = "PlayerInfoFPSDataLabel";
            this.PlayerInfoFPSDataLabel.Size = new System.Drawing.Size(0, 13);
            this.PlayerInfoFPSDataLabel.TabIndex = 7;
            // 
            // PlayerInfoMassLabel
            // 
            this.PlayerInfoMassLabel.AutoSize = true;
            this.PlayerInfoMassLabel.ForeColor = System.Drawing.Color.White;
            this.PlayerInfoMassLabel.Location = new System.Drawing.Point(1071, 48);
            this.PlayerInfoMassLabel.Name = "PlayerInfoMassLabel";
            this.PlayerInfoMassLabel.Size = new System.Drawing.Size(41, 13);
            this.PlayerInfoMassLabel.TabIndex = 8;
            this.PlayerInfoMassLabel.Text = "Mass : ";
            // 
            // PlayerInfoMassDataLabel
            // 
            this.PlayerInfoMassDataLabel.AutoSize = true;
            this.PlayerInfoMassDataLabel.ForeColor = System.Drawing.Color.White;
            this.PlayerInfoMassDataLabel.Location = new System.Drawing.Point(1118, 48);
            this.PlayerInfoMassDataLabel.Name = "PlayerInfoMassDataLabel";
            this.PlayerInfoMassDataLabel.Size = new System.Drawing.Size(0, 13);
            this.PlayerInfoMassDataLabel.TabIndex = 9;
            // 
            // Form2
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Black;
            this.ClientSize = new System.Drawing.Size(1184, 1032);
            this.Controls.Add(this.PlayerInfoMassDataLabel);
            this.Controls.Add(this.PlayerInfoMassLabel);
            this.Controls.Add(this.PlayerInfoFPSDataLabel);
            this.Controls.Add(this.PlayerInfoFPSLabel);
            this.DoubleBuffered = true;
            this.Name = "Form2";
            this.Text = "Form2";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Form2_FormClosing);
            this.Load += new System.EventHandler(this.Form2_Load);
            this.Paint += new System.Windows.Forms.PaintEventHandler(this.Form2_Paint);
            this.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.Form2_KeyPress);
            this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.Form2_MouseMove);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.ComponentModel.BackgroundWorker backgroundWorker1;
        private System.Windows.Forms.HelpProvider helpProvider1;
        private System.Windows.Forms.Label PlayerInfoFPSLabel;
        private System.Windows.Forms.Label PlayerInfoFPSDataLabel;
        private System.Windows.Forms.Label PlayerInfoMassLabel;
        private System.Windows.Forms.Label PlayerInfoMassDataLabel;
    }
}