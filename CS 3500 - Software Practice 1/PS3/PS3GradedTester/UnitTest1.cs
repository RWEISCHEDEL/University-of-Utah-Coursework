using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using SpreadsheetUtilities;
using System.Collections.Generic;

namespace GradingTests
{
    [TestClass]
    public class GradingTests
    {
        /**
         * Did not use Test01 through Test16 for grading.
        [TestMethod()]
        public void Test01()
        {
            Formula f = new Formula("5");
            Assert.AreEqual(5.0, (double)f.Evaluate(s => 0), 1e-9);
        }
        [TestMethod()]
        public void Test02()
        {
            Formula f = new Formula("X5");
            Assert.AreEqual(13.0, (double)f.Evaluate(s => 13), 1e-9);
        }
        [TestMethod()]
        public void Test03()
        {
            Formula f = new Formula("5.2 + 3.7");
            Assert.AreEqual(8.9, (double)f.Evaluate(s => 0), 1e-9);
        }
        [TestMethod()]
        public void Test04()
        {
            Formula f = new Formula("18.1-10.1");
            Assert.AreEqual(8, (double)f.Evaluate(s => 0), 1e-9);
        }
        [TestMethod()]
        public void Test05()
        {
            Formula f = new Formula("2*4.1");
            Assert.AreEqual(8.2, (double)f.Evaluate(s => 0), 1e-9);
        }
        [TestMethod()]
        public void Test06()
        {
            Formula f = new Formula("16/2");
            Assert.AreEqual(8, (double)f.Evaluate(s => 0), 1e-9);
        }
        [TestMethod()]
        public void Test07()
        {
            Formula f = new Formula("2.1+X1");
            Assert.AreEqual(6.2, (double)f.Evaluate(s => 4.1), 1e-9);
        }
        [TestMethod()]
        public void Test08()
        {
            Formula f = new Formula("2*6.0+3.1");
            Assert.AreEqual(15.1, (double)f.Evaluate(s => 0), 1e-9);
        }
        [TestMethod()]
        public void Test09()
        {
            Formula f = new Formula("2+6.0*3.1");
            Assert.AreEqual(20.6, (double)f.Evaluate(s => 0), 1e-9);
        }
        [TestMethod()]
        public void Test10()
        {
            Formula f = new Formula("(2+6.0)*3.1");
            Assert.AreEqual(24.8, (double)f.Evaluate(s => 0), 1e-9);
        }
        [TestMethod()]
        public void Test11()
        {
            Formula f = new Formula("2*(3+5)");
            Assert.AreEqual(16, (double)f.Evaluate(s => 0), 1e-9);
        }
        [TestMethod()]
        public void Test12()
        {
            Formula f = new Formula("2+(3+5)");
            Assert.AreEqual(10, (double)f.Evaluate(s => 0), 1e-9);
        }
        [TestMethod()]
        public void Test13()
        {
            Formula f = new Formula("2+(3+5*9)");
            Assert.AreEqual(50, (double)f.Evaluate(s => 0), 1e-9);
        }
        [TestMethod()]
        public void Test14()
        {
            Formula f = new Formula("2+3*(3+5)");
            Assert.AreEqual(26, (double)f.Evaluate(s => 0), 1e-9);
        }
        [TestMethod()]
        public void Test15()
        {
            Formula f = new Formula("2+3*5+(3+4*8)*5+2");
            Assert.AreEqual(194, (double)f.Evaluate(s => 0), 1e-9);
        }
         
        */

        // Simple tests that return FormulaErrors
        [TestMethod()]
        public void Test16()
        {
            Formula f = new Formula("2+X1");
            Assert.IsInstanceOfType(f.Evaluate(s => { throw new ArgumentException("Unknown variable"); }), typeof(FormulaError));
        }

        [TestMethod()]
        public void Test17()
        {
            Formula f = new Formula("5/0");
            Assert.IsInstanceOfType(f.Evaluate(s => 0), typeof(FormulaError));
        }

        [TestMethod()]
        public void Test18()
        {
            Formula f = new Formula("(5 + X1) / (X1 - 3)");
            Assert.IsInstanceOfType(f.Evaluate(s => 3), typeof(FormulaError));
        }


        // Tests of syntax errors detected by the constructor
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Test19()
        {
            Formula f = new Formula("+");
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Test20()
        {
            Formula f = new Formula("2+5+");
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Test21()
        {
            Formula f = new Formula("2+5*7)");
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Test22()
        {
            Formula f = new Formula("((3+5*7)");
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Test23()
        {
            Formula f = new Formula("5x");
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Test24()
        {
            Formula f = new Formula("5+5x");
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Test25()
        {
            Formula f = new Formula("5+7+(5)8");
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Test26()
        {
            Formula f = new Formula("5 5");
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Test27()
        {
            Formula f = new Formula("5 + + 3");
        }

        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Test28()
        {
            Formula f = new Formula("");
        }

        // Some more complicated formula evaluations
        [TestMethod()]
        public void Test29()
        {
            Formula f = new Formula("y1*3-8/2+4*(8-9*2)/14*x7");
            Assert.AreEqual(5.14285714285714, (double)f.Evaluate(s => (s == "x7") ? 1 : 4), 1e-9);
        }

        [TestMethod()]
        public void Test30()
        {
            Formula f = new Formula("x1+(x2+(x3+(x4+(x5+x6))))");
            Assert.AreEqual(6, (double)f.Evaluate(s => 1), 1e-9);
        }

        [TestMethod()]
        public void Test31()
        {
            Formula f = new Formula("((((x1+x2)+x3)+x4)+x5)+x6");
            Assert.AreEqual(12, (double)f.Evaluate(s => 2), 1e-9);
        }

        [TestMethod()]
        public void Test32()
        {
            Formula f = new Formula("a4-a4*a4/a4");
            Assert.AreEqual(0, (double)f.Evaluate(s => 3), 1e-9);
        }

        // Test of the Equals method
        [TestMethod()]
        public void Test33()
        {
            Formula f1 = new Formula("X1+X2");
            Formula f2 = new Formula("X1+X2");
            Assert.IsTrue(f1.Equals(f2));
        }

        [TestMethod()]
        public void Test34()
        {
            Formula f1 = new Formula("X1+X2");
            Formula f2 = new Formula(" X1  +  X2   ");
            Assert.IsTrue(f1.Equals(f2));
        }

        [TestMethod()]
        public void Test35()
        {
            Formula f1 = new Formula("2+X1*3.00");
            Formula f2 = new Formula("2.00+X1*3.0");
            Assert.IsTrue(f1.Equals(f2));
        }

        [TestMethod()]
        public void Test36()
        {
            Formula f1 = new Formula("1e-2 + X5 + 17.00 * 19 ");
            Formula f2 = new Formula("   0.0100  +     X5+ 17 * 19.00000 ");
            Assert.IsTrue(f1.Equals(f2));
        }

        [TestMethod()]
        public void Test37()
        {
            Formula f = new Formula("2");
            Assert.IsFalse(f.Equals(null));
            Assert.IsFalse(f.Equals(""));
        }


        // Tests of == operator
        [TestMethod()]
        public void Test38()
        {
            Formula f1 = new Formula("2");
            Formula f2 = new Formula("2");
            Assert.IsTrue(f1 == f2);
        }

        [TestMethod()]
        public void Test39()
        {
            Formula f1 = new Formula("2");
            Formula f2 = new Formula("5");
            Assert.IsFalse(f1 == f2);
        }

        [TestMethod()]
        public void Test40()
        {
            Formula f1 = new Formula("2");
            Formula f2 = new Formula("2");
            Assert.IsFalse(null == f1);
            Assert.IsFalse(f1 == null);
            Assert.IsTrue(f1 == f2);
        }


        // Tests of != operator
        [TestMethod()]
        public void Test41()
        {
            Formula f1 = new Formula("2");
            Formula f2 = new Formula("2");
            Assert.IsFalse(f1 != f2);
        }

        [TestMethod()]
        public void Test42()
        {
            Formula f1 = new Formula("2");
            Formula f2 = new Formula("5");
            Assert.IsTrue(f1 != f2);
        }


        // Test of ToString method
        [TestMethod()]
        public void Test43()
        {
            Formula f = new Formula("2*5");
            Assert.IsTrue(f.Equals(new Formula(f.ToString())));
        }


        // Tests of GetHashCode method
        [TestMethod()]
        public void Test44()
        {
            Formula f1 = new Formula("2*5");
            Formula f2 = new Formula("2*5");
            Assert.IsTrue(f1.GetHashCode() == f2.GetHashCode());
        }

        [TestMethod()]
        public void Test45()
        {
            Formula f1 = new Formula("2*5");
            Formula f2 = new Formula("3/8*2+(7)");
            Assert.IsTrue(f1.GetHashCode() != f2.GetHashCode());
        }


        // Tests of GetVariables method
        [TestMethod()]
        public void Test46()
        {
            Formula f = new Formula("2*5");
            Assert.IsFalse(f.GetVariables().GetEnumerator().MoveNext());
        }

        [TestMethod()]
        public void Test47()
        {
            Formula f = new Formula("2*X2");
            List<string> actual = new List<string>(f.GetVariables());
            HashSet<string> expected = new HashSet<string>() { "X2" };
            Assert.AreEqual(actual.Count, 1);
            Assert.IsTrue(expected.SetEquals(actual));
        }

        [TestMethod()]
        public void Test48()
        {
            Formula f = new Formula("2*X2+Y3");
            List<string> actual = new List<string>(f.GetVariables());
            HashSet<string> expected = new HashSet<string>() { "Y3", "X2" };
            Assert.AreEqual(actual.Count, 2);
            Assert.IsTrue(expected.SetEquals(actual));
        }

        [TestMethod()]
        public void Test49()
        {
            Formula f = new Formula("2*X2+X2");
            List<string> actual = new List<string>(f.GetVariables());
            HashSet<string> expected = new HashSet<string>() { "X2" };
            Assert.AreEqual(actual.Count, 1);
            Assert.IsTrue(expected.SetEquals(actual));
        }

        [TestMethod()]
        public void Test50()
        {
            Formula f = new Formula("X1+Y2*X3*Y2+Z7+X1/Z8");
            List<string> actual = new List<string>(f.GetVariables());
            HashSet<string> expected = new HashSet<string>() { "X1", "Y2", "X3", "Z7", "Z8" };
            Assert.AreEqual(actual.Count, 5);
            Assert.IsTrue(expected.SetEquals(actual));
        }

        // Tests to make sure there can be more than one formula at a time
        [TestMethod()]
        public void Test51a()
        {
            Formula f1 = new Formula("2");
            Formula f2 = new Formula("3");
            Assert.IsTrue(f1.ToString().IndexOf("2") >= 0);
            Assert.IsTrue(f2.ToString().IndexOf("3") >= 0);
        }

        [TestMethod()]
        public void Test51b()
        {
            Formula f1 = new Formula("2");
            Formula f2 = new Formula("3");
            Assert.IsTrue(f1.ToString().IndexOf("2") >= 0);
            Assert.IsTrue(f2.ToString().IndexOf("3") >= 0);
        }

        [TestMethod()]
        public void Test51c()
        {
            Formula f1 = new Formula("2");
            Formula f2 = new Formula("3");
            Assert.IsTrue(f1.ToString().IndexOf("2") >= 0);
            Assert.IsTrue(f2.ToString().IndexOf("3") >= 0);
        }

        [TestMethod()]
        public void Test51d()
        {
            Formula f1 = new Formula("2");
            Formula f2 = new Formula("3");
            Assert.IsTrue(f1.ToString().IndexOf("2") >= 0);
            Assert.IsTrue(f2.ToString().IndexOf("3") >= 0);
        }

        [TestMethod()]
        public void Test51e()
        {
            Formula f1 = new Formula("2");
            Formula f2 = new Formula("3");
            Assert.IsTrue(f1.ToString().IndexOf("2") >= 0);
            Assert.IsTrue(f2.ToString().IndexOf("3") >= 0);
        }

        // Stress test for constructor
        [TestMethod()]
        public void Test52a()
        {
            Formula f = new Formula("(((((2+3*X1)/(7e-5+X2-X4))*X5+.0005e+92)-8.2)*3.14159) * ((x2+3.1)-.00000000008)");
        }

        // Stress test for constructor
        [TestMethod()]
        public void Test52b()
        {
            Formula f = new Formula("(((((2+3*X1)/(7e-5+X2-X4))*X5+.0005e+92)-8.2)*3.14159) * ((x2+3.1)-.00000000008)");
        }

        // Stress test for constructor
        [TestMethod()]
        public void Test52c()
        {
            Formula f = new Formula("(((((2+3*X1)/(7e-5+X2-X4))*X5+.0005e+92)-8.2)*3.14159) * ((x2+3.1)-.00000000008)");
        }

        // Stress test for constructor
        [TestMethod()]
        public void Test52d()
        {
            Formula f = new Formula("(((((2+3*X1)/(7e-5+X2-X4))*X5+.0005e+92)-8.2)*3.14159) * ((x2+3.1)-.00000000008)");
        }

        // Stress test for constructor
        [TestMethod()]
        public void Test52e()
        {
            Formula f = new Formula("(((((2+3*X1)/(7e-5+X2-X4))*X5+.0005e+92)-8.2)*3.14159) * ((x2+3.1)-.00000000008)");
        }

    }
}