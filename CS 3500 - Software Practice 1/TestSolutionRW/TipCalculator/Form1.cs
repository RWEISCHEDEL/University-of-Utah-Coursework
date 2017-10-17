using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TipCalculator
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Comp_Tip_Click(object sender, EventArgs e)
        {
            double value = Convert.ToDouble(Total_Bill_TBox.Text);

            value = value * 0.2;

            String answer = value + "";

            Total_Bill_Tip_TBox.Text = answer;
        }
    }
}
