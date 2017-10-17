namespace TipCalculator
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
            this.Total_Bill_Label = new System.Windows.Forms.Label();
            this.Total_Bill_TBox = new System.Windows.Forms.TextBox();
            this.Total_Bill_Tip_TBox = new System.Windows.Forms.TextBox();
            this.Comp_Tip = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // Total_Bill_Label
            // 
            this.Total_Bill_Label.AutoSize = true;
            this.Total_Bill_Label.Location = new System.Drawing.Point(34, 27);
            this.Total_Bill_Label.Name = "Total_Bill_Label";
            this.Total_Bill_Label.Size = new System.Drawing.Size(75, 13);
            this.Total_Bill_Label.TabIndex = 0;
            this.Total_Bill_Label.Text = "Enter Total Bill";
            // 
            // Total_Bill_TBox
            // 
            this.Total_Bill_TBox.Location = new System.Drawing.Point(133, 20);
            this.Total_Bill_TBox.Name = "Total_Bill_TBox";
            this.Total_Bill_TBox.Size = new System.Drawing.Size(135, 20);
            this.Total_Bill_TBox.TabIndex = 1;
            // 
            // Total_Bill_Tip_TBox
            // 
            this.Total_Bill_Tip_TBox.Location = new System.Drawing.Point(134, 58);
            this.Total_Bill_Tip_TBox.Name = "Total_Bill_Tip_TBox";
            this.Total_Bill_Tip_TBox.Size = new System.Drawing.Size(133, 20);
            this.Total_Bill_Tip_TBox.TabIndex = 2;
            // 
            // Comp_Tip
            // 
            this.Comp_Tip.Location = new System.Drawing.Point(37, 58);
            this.Comp_Tip.Name = "Comp_Tip";
            this.Comp_Tip.Size = new System.Drawing.Size(83, 20);
            this.Comp_Tip.TabIndex = 3;
            this.Comp_Tip.Text = "Compute Tip";
            this.Comp_Tip.UseVisualStyleBackColor = true;
            this.Comp_Tip.Click += new System.EventHandler(this.Comp_Tip_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(385, 339);
            this.Controls.Add(this.Comp_Tip);
            this.Controls.Add(this.Total_Bill_Tip_TBox);
            this.Controls.Add(this.Total_Bill_TBox);
            this.Controls.Add(this.Total_Bill_Label);
            this.Name = "Form1";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label Total_Bill_Label;
        private System.Windows.Forms.TextBox Total_Bill_TBox;
        private System.Windows.Forms.TextBox Total_Bill_Tip_TBox;
        private System.Windows.Forms.Button Comp_Tip;
    }
}

