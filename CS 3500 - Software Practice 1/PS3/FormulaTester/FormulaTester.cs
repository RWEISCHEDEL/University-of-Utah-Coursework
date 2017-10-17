using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using SpreadsheetUtilities;
using System.Collections.Generic;

/// <summary>
/// This serves as a tester for the Formula class I built for PS3.
/// Testing suite written by : Robert Weischedel u0887905 CS3500
/// </summary>
namespace FormulaTester
{
    /// <summary>
    /// This class serves as a testing suite for the Formula class and its associated methods.
    /// </summary>
    [TestClass]
    public class FormulaTester
    {
        // The following methods test both the constructor and the evaluate method to ensure both are working properly. The expressions get
        // more complex the further you go down.

        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaValuesOnly1()
        {
            Formula f1 = new Formula("1+1");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(2.0, output);

        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation with parenthesis.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaValuesOnly2()
        {
            Formula f1 = new Formula("(5+5)/2");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(5.0, output);

        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation with parenthesis.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaValuesOnly3()
        {
            Formula f1 = new Formula("(10+5)-10");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(5.0, output);

        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation with parenthesis.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaValuesOnly4()
        {
            Formula f1 = new Formula("25 + (10 - 2)");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(33.0, output);

        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation with correct order of operations.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaValuesOnly5()
        {
            Formula f1 = new Formula("15/5*3");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(9.0, output);

        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation with correct order of operations and parenthesis.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaValuesOnly6()
        {
            Formula f1 = new Formula("(2 + 3) * 5 + 2");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(27.0, output);

        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a more complex math operation with correct order of operations and parenthesis.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaValuesOnly7()
        {
            Formula f1 = new Formula("(20/5/2+3)-2");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(3.0, output);

        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a more complex math operation with correct order of operations and parenthesis.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaValuesOnly8()
        {
            Formula f1 = new Formula("(5*4*3*2*1) - 5");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(115.0, output);

        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a complex math operation with correct order of operations.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaValuesOnly9()
        {
            Formula f1 = new Formula("5+1*4+2*3+3*2+4*1+5/12");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(25.4167, Math.Round((double)output, 4));

        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a very complex math operation with correct order of operations.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaValuesOnly10()
        {
            Formula f1 = new Formula("5+1-5*4+2-4*3+3-3*2+4-2*1+5-1/12");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(-20.0833, Math.Round((double)output, 4));

        }

        // The following tests still test both the constructor and the evaluate method, but this time using variables and values. It also tests
        // the various validity options for variables. 

        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation involving numbers and variables.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithValVar1()
        {
            Formula f1 = new Formula("1+x");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(6.0, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation involving numbers and variables.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithValVar2()
        {
            Formula f1 = new Formula("1+_x");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(6.0, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation involving numbers and variables.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithValVar3()
        {
            Formula f1 = new Formula("1+_x1");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(6.0, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation involving numbers and variables.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithValVar4()
        {
            Formula f1 = new Formula("1+x_1_");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(6.0, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation involving numbers and variables.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithValVar5()
        {
            Formula f1 = new Formula("1 + _x2 + 3");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(9.0, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a more complex math operation involving numbers and variables.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithValVar6()
        {
            Formula f1 = new Formula("1 * _x2 / x_2");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(1.0, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a more complex math operation involving numbers, complex variables, correct use of
        /// order of operations and parenthesis.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithValVar7()
        {
            Formula f1 = new Formula("(1 + _x2) * (3 + yz123adsfagsqha)");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(48.0, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a very complex math operation involving numbers, complex variables and correct use of
        /// order of operations.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithValVar8()
        {
            Formula f1 = new Formula("5+x-5*Y+Z-a1*a_2+_U1-a1__*ewww+_opo123-z23*o9+5-ZSD/a_long_var");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(-71.0, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a very complex math operation involving numbers, complex variables and correct use of
        /// order of operations and parenthesis.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithValVar9()
        {
            Formula f1 = new Formula("(5+x-5)*Y+Z-a1*(a_2+_U1-a1__)*ewww+_opo123-z23*o9+5-(ZSD/a_long_var)");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(-111.0, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a very complex math operation involving numbers, complex variables and correct use of
        /// order of operations.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithValVar10()
        {
            Formula f1 = new Formula("1+a1+2-a2-3*a3*4/a4/5+a5+6-a6-7*a7*8/a8/9+a9+10-a10");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(10.3778, Math.Round((double)output, 4));
        }

        // The following tests still test if the formula constructor and evaluate method do work properly, but these tests only use varaibles. This is also another
        // good test of using valid variable names.

        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation using only variables.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithVar1()
        {
            Formula f1 = new Formula("x+X");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(10.0, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation using only variables.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithVar2()
        {
            Formula f1 = new Formula("x1+X2");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(10.0, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation using only variables.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithVar3()
        {
            Formula f1 = new Formula("x+X+__+______+a1_");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(25.0, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation using only variables.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithVar4()
        {
            Formula f1 = new Formula("u_1231452 * A_123 + a_123");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(30.0, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation using only variables.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithVar5()
        {
            Formula f1 = new Formula("x+X+aA+AA_A");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(20.0, output);
        }
        /// <summary>
        /// 
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithVar6()
        {
            Formula f1 = new Formula("_x1 + y12");
            object output = f1.Evaluate(Lookup);
            Assert.AreEqual(5.0, output);
        }

        // The following tests test and see if given a Normalizer and IsValid methods, instead of lambda notation the formula class still performs correctly.

        /// <summary>
        /// This test ensures that If using a normalizer that makes all lower case values and a IsValid method that makes all variables have to be atleast length 3.
        /// That the class still performs as it should.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithNormalizerIsValid1()
        {
            Formula f1 = new Formula("_x1 + X12 + aA_ + A_A", Normalizer, IsValid);
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(20.0, output);
        }

        // The following tests ensure that decimal math works with and without using variables.

        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation using both decimals and natural numbers.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithDec1()
        {
            Formula f1 = new Formula("1+1.5");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(2.500, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation using both variables containing decimals and natural numbers.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithDec2()
        {
            Formula f1 = new Formula("1+x1");
            object output = f1.Evaluate(x => 0.5);
            Assert.AreEqual(1.500, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation using both decimals and natural numbers and variables.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithDec3()
        {
            Formula f1 = new Formula("0.5 + x1 * 1.75");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(9.250, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a more complex math operation using both decimals and natural numbers and decimals
        /// in variables and parenthesis.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithDec4()
        {
            Formula f1 = new Formula("0.75 + (0.25 * _z1)");
            object output = f1.Evaluate(x => 0.1);
            Assert.AreEqual(0.775, output);
        }

        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation using both decimals and decimals in variables.
        /// </summary>
        [TestMethod]
        public void Public_TestBasicFormulaWithDec5()
        {
            Formula f1 = new Formula("1.5 / mach5");
            object output = f1.Evaluate(x => 0.5);
            Assert.AreEqual(3.0, output);
        }

        // This set of tests ensures that both the formula constructor and evaluate method can handle exponent math.


        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation using values in scientific notation. 
        /// </summary>
        [TestMethod]
        public void Public_TestScientificNot1()
        {
            Formula f1 = new Formula("5e10 + 1e10");
            object output = f1.Evaluate(x => 5);
            Assert.AreEqual(6e10, output);
        }

        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation using values in scientific notation and a varaible that 
        /// has a value in scientific notation.
        /// </summary>
        [TestMethod]
        public void Public_TestScientificNot2()
        {
            Formula f1 = new Formula("5e10 + 1e10 + x1");
            object output = f1.Evaluate(x => 5e10);
            Assert.AreEqual(11e10, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation using values in scientific notation and a varaible that
        /// has a value in scientific notation.
        /// </summary>
        [TestMethod]
        public void Public_TestScientificNot3()
        {
            Formula f1 = new Formula("5e10 / x1");
            object output = f1.Evaluate(x => 5e10);
            Assert.AreEqual(1.0, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation using values in scientific notation and a varaible that
        /// has a value in scientific notation.
        /// </summary>
        [TestMethod]
        public void Public_TestScientificNot4()
        {
            Formula f1 = new Formula("5e10 * 1e10");
            object output = f1.Evaluate(x => 5e10);
            Assert.AreEqual(5e20, output);
        }
        /// <summary>
        /// This test ensures that the constructor and evaluate methods can solve and build a simple math operation using values in scientific notation and a varaible that
        /// has a value in scientific notation.
        /// </summary>
        [TestMethod]
        public void Public_TestScientificNot5()
        {
            Formula f1 = new Formula("5e10 * x2 + 1e10");
            object output = f1.Evaluate(x => 10e10);
            Assert.AreEqual(5.00000000001e21, output);
        }

        // The following tests ensures that the GetVariables method is working properly under a variety of different situations.


        /// <summary>
        /// This test ensures that the GetVariable method can return a empty IEnumerable object when the formula contains no variables.
        /// </summary>
        [TestMethod]
        public void Public_TestGetVariables1()
        {
            Formula f1 = new Formula("1 + 1");
            List<String> example1 = new List<String>(f1.GetVariables());
            Assert.AreEqual(0, example1.Count);
        }
        /// <summary>
        /// This test ensures that the GetVariable method can return two variables that are the same, but since there is no normalizer they are treated differently.
        /// </summary>
        [TestMethod]
        public void Public_TestGetVariables2()
        {
            Formula f1 = new Formula("x + X");
            List<String> example1 = new List<String>(f1.GetVariables());
            Assert.AreEqual(2, example1.Count);
            Assert.IsTrue(example1.Contains("X") && example1.Contains("x"));
        }
        /// <summary>
        /// This test ensures that the GetVariable method can return only one variable when the normalizer is used.
        /// </summary>
        [TestMethod]
        public void Public_TestGetVariables3()
        {
            Formula f1 = new Formula("x + X", x => x.ToUpper(), x => true);
            List<String> example1 = new List<String>(f1.GetVariables());
            Assert.AreEqual(1, example1.Count);
            Assert.IsTrue(example1.Contains("X"));
            Assert.IsFalse(example1.Contains("x"));
        }
        /// <summary>
        /// This test ensures that the GetVariable method can return only one variable when the normalizer is used.
        /// </summary>
        [TestMethod]
        public void Public_TestGetVariables4()
        {
            Formula f1 = new Formula("x + X", x => x.ToLower(), x => true);
            List<String> example1 = new List<String>(f1.GetVariables());
            Assert.AreEqual(1, example1.Count);
            Assert.IsTrue(example1.Contains("x"));
            Assert.IsFalse(example1.Contains("X"));
        }
        /// <summary>
        /// This test ensures that the GetVariable method can return many different variables, each that are unique because there is no normalizer.
        /// </summary>
        [TestMethod]
        public void Public_TestGetVariables5()
        {
            Formula f1 = new Formula("x + x + X + y + Y + z + Z");
            List<String> example1 = new List<String>(f1.GetVariables());
            Assert.AreEqual(6, example1.Count);
            Assert.IsTrue(example1.Contains("x") && example1.Contains("X") && example1.Contains("y") && example1.Contains("Y") && example1.Contains("z") && example1.Contains("Z"));
        }
        /// <summary>
        /// This test ensures that the GetVariable method can return a few different variables, because many are normalized to the same value.
        /// </summary>
        [TestMethod]
        public void Public_TestGetVariables6()
        {
            Formula f1 = new Formula("x + X + y + Y + z + Z", x => x.ToUpper(), x => true);
            List<String> example1 = new List<String>(f1.GetVariables());
            Assert.AreEqual(3, example1.Count);
            Assert.IsTrue(example1.Contains("X") && example1.Contains("Y") && example1.Contains("Z"));
            Assert.IsFalse(example1.Contains("x") || example1.Contains("y") || example1.Contains("z"));
        }
        /// <summary>
        /// This test ensures that the GetVariable method can return a few different variables, because many are normalized to the same value.
        /// </summary>
        [TestMethod]
        public void Public_TestGetVariables7()
        {
            Formula f1 = new Formula("_x1 + _X1 + _y + _Y_ + _z123 + _Z123", x => x.ToUpper(), x => true);
            List<String> example1 = new List<String>(f1.GetVariables());
            Assert.AreEqual(4, example1.Count);
            Assert.IsTrue(example1.Contains("_X1") && example1.Contains("_Y") && example1.Contains("_Y_") && example1.Contains("_Z123"));
            Assert.IsFalse(example1.Contains("_x1") || example1.Contains("_z123"));
        }
        /// <summary>
        /// This test ensures that the GetVariable method can return a variable only once, not matter how many times it appears in the formula.
        /// </summary>
        [TestMethod]
        public void Public_TestGetVariables8()
        {
            Formula f1 = new Formula("x + x - x * x / x", x => x.ToUpper(), x => true);
            List<String> example1 = new List<String>(f1.GetVariables());
            Assert.AreEqual(1, example1.Count);
            Assert.IsTrue(example1.Contains("X"));
            Assert.IsFalse(example1.Contains("x"));
        }

        // The following tests test the ToString method.


        /// <summary>
        /// This test ensures that the ToString method can return a simple expression back and with no whitespace.
        /// </summary>
        [TestMethod]
        public void Public_TestToString1()
        {
            Formula f1 = new Formula("x + x - x * x / x");
            Assert.AreEqual("x+x-x*x/x", f1.ToString());
        }
        /// <summary>
        /// This test ensures that the ToString method can return a simple expression back including variables and with no whitespace.
        /// </summary>
        [TestMethod]
        public void Public_TestToString2()
        {
            Formula f1 = new Formula("1 + 2 + 3 + _x1");
            Assert.AreEqual("1+2+3+_x1", f1.ToString());
        }
        /// <summary>
        /// This test ensures that the ToString method can return a more complex expression back including variables and with no whitespace.
        /// </summary>
        [TestMethod]
        public void Public_TestToString3()
        {
            Formula f1 = new Formula("_x1 + z_1 *A_123_ / __a");
            Assert.AreEqual("_x1+z_1*A_123_/__a", f1.ToString());
        }

        // The following tests test the Equals method.


        /// <summary>
        /// This test ensures that the Equals method will return true if two formulas are the same despite whitespace.
        /// </summary>
        [TestMethod]
        public void Public_TestEquals1()
        {
            Formula f1 = new Formula("1+1");
            Formula f2 = new Formula("1 + 1");
            Assert.IsTrue(f1.Equals(f2));
        }
        /// <summary>
        /// This test ensures that the Equals method will return false if two formulas are the same execpt thier variables havent been normalized.
        /// </summary>
        [TestMethod]
        public void Public_TestEquals2()
        {
            Formula f1 = new Formula("1+x1");
            Formula f2 = new Formula("1 + X1");
            Assert.IsFalse(f1.Equals(f2));
        }
        /// <summary>
        /// This test ensures that the Equals method will return false if two formulas are the same execpt thier variables havent been normalized.
        /// </summary>
        [TestMethod]
        public void Public_TestEquals3()
        {
            Formula f1 = new Formula("_x1*c_2+5.0");
            Formula f2 = new Formula("_X1*C_2+5");
            Assert.IsFalse(f1.Equals(f2));
        }
        /// <summary>
        /// This test ensures that the Equals method will return true even if the number of trailing zeros is different.
        /// </summary>
        [TestMethod]
        public void Public_TestEquals4()
        {
            Formula f1 = new Formula("1 + X1 + 5.0");
            Formula f2 = new Formula("1 + X1 + 5.000000");
            Assert.IsTrue(f1.Equals(f2));
        }
        /// <summary>
        /// This test ensures that the Equals method will return true if two formulas are the same after thier variables havent been normalized.
        /// </summary>
        [TestMethod]
        public void Public_TestEquals5()
        {
            Formula f1 = new Formula("1 + x1 + 5.0", x => x.ToUpper(), x => true);
            Formula f2 = new Formula("1 + X1 + 5.000000", x => x.ToUpper(), x => true);
            Assert.IsTrue(f1.Equals(f2));
        }

        /// <summary>
        /// This test ensures that the Equals method will return true if two formulas are the same after thier variables havent been normalized.
        /// </summary>
        [TestMethod]
        public void Public_TestEquals6()
        {
            Formula f1 = new Formula("_A1 + x1 + 5.0 * K_2 / v4_ + r", x => x.ToUpper(), x => true);
            Formula f2 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + R", x => x.ToUpper(), x => true);
            Assert.IsTrue(f1.Equals(f2));
        }
        /// <summary>
        /// This test ensures that the Equals method will return false if two formulas are the same except one of thier values is different.
        /// </summary>
        [TestMethod]
        public void Public_TestEquals7()
        {
            Formula f1 = new Formula("_A1 + x1 + 6.0 * K_2 / v4_ + r", x => x.ToUpper(), x => true);
            Formula f2 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + R", x => x.ToUpper(), x => true);
            Assert.IsFalse(f1.Equals(f2));
        }
        /// <summary>
        /// This test ensures that the Equals method will return true if the two formulas being compared are exactly the same.
        /// </summary>
        [TestMethod]
        public void Public_TestEquals8()
        {
            Formula f1 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + R", x => x.ToUpper(), x => true);
            Formula f2 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + R", x => x.ToUpper(), x => true);
            Assert.IsTrue(f1.Equals(f2));
        }
        /// <summary>
        /// This test ensures that the Equals method will return false if the two objects being compared are not the same.
        /// </summary>
        [TestMethod]
        public void Public_TestEquals9()
        {
            String f1 = "stuff";
            Formula f2 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + R", x => x.ToUpper(), x => true);
            Assert.IsFalse(f1.Equals(f2));
        }
        /// <summary>
        /// This test ensures that the Equals method will return false if one of the objects is null.
        /// </summary>
        [TestMethod]
        public void Public_TestEquals10()
        {
            Formula f1 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + R", x => x.ToUpper(), x => true);
            Formula f2 = null;
            Assert.IsFalse(f1.Equals(f2));
        }
        /// <summary>
        /// This test ensures that the Equals method will return false on two formulas that are not of the same length.
        /// </summary>
        [TestMethod]
        public void Public_TestEquals11()
        {
            Formula f1 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + R", x => x.ToUpper(), x => true);
            Formula f2 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_", x => x.ToUpper(), x => true);
            Assert.IsFalse(f1.Equals(f2));
        }

        // The following tests test and ensure that the == operator override was done correctly.


        /// <summary>
        /// This test ensures that the == override returns true if both objects being compared are null.
        /// </summary>
        [TestMethod]
        public void Public_TestEqualEquals1()
        {
            Formula f1 = null;
            Formula f2 = null;
            Assert.IsTrue(f1 == f2);
        }
        /// <summary>
        /// This test ensures that the == override returns true if one of the objects being compared are null.
        /// </summary>
        [TestMethod]
        public void Public_TestEqualEquals2()
        {
            Formula f1 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + R", x => x.ToUpper(), x => true);
            Formula f2 = null;
            Assert.IsFalse(f1 == f2);
        }
        /// <summary>
        /// This test ensures that the == override returns true if one of the objects being compared are null.
        /// </summary>
        [TestMethod]
        public void Public_TestEqualEquals3()
        {
            Formula f1 = null;
            Formula f2 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + R", x => x.ToUpper(), x => true);
            Assert.IsFalse(f1 == f2);
        }
        /// <summary>
        /// This test ensures that the == override returns true if both of the objects are indeed the same.
        /// </summary>
        [TestMethod]
        public void Public_TestEqualEquals4()
        {
            Formula f1 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + R", x => x.ToUpper(), x => true);
            Formula f2 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + R", x => x.ToUpper(), x => true);
            Assert.IsTrue(f1 == f2);
        }
        /// <summary>
        /// This test ensures that the == override returns false if the objects are not the same.
        /// </summary>
        [TestMethod]
        public void Public_TestEqualEquals5()
        {
            Formula f1 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + wrx", x => x.ToUpper(), x => true);
            Formula f2 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + R", x => x.ToUpper(), x => true);
            Assert.IsFalse(f1 == f2);
        }

        // The following tests test if the != operator override was done correctly.


        /// <summary>
        /// This test ensures that the != override returns true if both objects are null.
        /// </summary>
        [TestMethod]
        public void Public_TestNotEquals1()
        {
            Formula f1 = null;
            Formula f2 = null;
            Assert.IsFalse(f1 != f2);
        }
        /// <summary>
        /// This test ensures that the != override returns true if one of the objects are null.
        /// </summary>
        [TestMethod]
        public void Public_TestNotEquals2()
        {
            Formula f1 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + wrx", x => x.ToUpper(), x => true);
            Formula f2 = null;
            Assert.IsTrue(f1 != f2);
        }
        /// <summary>
        /// This test ensures that the != override returns true if one of the objects are null.
        /// </summary>
        [TestMethod]
        public void Public_TestNotEquals3()
        {
            Formula f1 = null;
            Formula f2 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + wrx", x => x.ToUpper(), x => true);
            Assert.IsTrue(f1 != f2);
        }
        /// <summary>
        /// This test ensures that the != override returns false if both of the objects are the same.
        /// </summary>
        [TestMethod]
        public void Public_TestNotEquals4()
        {
            Formula f1 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + R", x => x.ToUpper(), x => true);
            Formula f2 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + R", x => x.ToUpper(), x => true);
            Assert.IsFalse(f1 != f2);
        }
        /// <summary>
        /// This test ensures that the != override returns true if both of the objects are not the same.
        /// </summary>
        [TestMethod]
        public void Public_TestNotEquals5()
        {
            Formula f1 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + wrx", x => x.ToUpper(), x => true);
            Formula f2 = new Formula("_a1 + X1 + 5.000000 * k_2 / v4_ + R", x => x.ToUpper(), x => true);
            Assert.IsTrue(f1 != f2);
        }

        // The following tests ensure that the GetHashCode Method returns consistent values. 


        /// <summary>
        /// This test ensures that GetHashCode returns the same value for the exact same formulas.
        /// </summary>
        [TestMethod]
        public void Public_TestGetHashCode1()
        {
            Formula f1 = new Formula("x1 + z1");
            Formula f2 = new Formula("x1 + z1");
            Assert.AreEqual(f1.GetHashCode(), f2.GetHashCode());

        }
        /// <summary>
        /// This test ensures that GetHashCode doesn't returns the same value for two different formulas.
        /// </summary>
        [TestMethod]
        public void Public_TestGetHashCode2()
        {
            Formula f1 = new Formula("x1 + Z1");
            Formula f2 = new Formula("x1 + z1");
            Assert.AreNotEqual(f1.GetHashCode(), f2.GetHashCode());
        }
        /// <summary>
        /// This test ensures that GetHashCode returns the same value for the exact same formulas, even in the case of using the normalizer.
        /// </summary>
        [TestMethod]
        public void Public_TestGetHashCode3()
        {
            Formula f1 = new Formula("x1 + Z1", x => x.ToUpper(), x => true);
            Formula f2 = new Formula("x1 + z1", x => x.ToUpper(), x => true);
            Assert.AreEqual(f1.GetHashCode(), f2.GetHashCode());

        }
        /// <summary>
        /// This test ensures that GetHashCode returns a different value for the two differnt formulas, even in the case of using the normalizer.
        /// </summary>
        [TestMethod]
        public void Public_TestGetHashCode4()
        {
            Formula f1 = new Formula("u1 + Z1", x => x.ToUpper(), x => true);
            Formula f2 = new Formula("x1 + z1", x => x.ToUpper(), x => true);
            Assert.AreNotEqual(f1.GetHashCode(), f2.GetHashCode());

        }
        /// <summary>
        /// This test ensures that GetHashCode returns the same value for the same exact strings.
        /// </summary>
        [TestMethod]
        public void Public_TestGetHashCode5()
        {
            Formula f1 = new Formula("x1 + z1");
            String hash = "x1+z1";
            Assert.AreEqual(f1.GetHashCode(), hash.GetHashCode());

        }

        // The following tests test and the various thrown Execptions in the Formula class.

        // The following tests test the thrown Exceptions in the formula constructor.


        /// <summary>
        /// This test ensures when a nothing but whitespace is entered an excepetion is thrown. There must be atleast one token.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions1()
        {
            Formula f1 = new Formula("  ");
        }
        /// <summary>
        /// This test ensures when nothing is entered an excepetion is thrown. There must be atleast one token.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions2()
        {
            Formula f1 = new Formula("");
        }
        /// <summary>
        /// This test ensures when null is entered an excepetion is thrown. There must be atleast one token.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions3()
        {
            Formula f1 = new Formula(null);
        }
        /// <summary>
        /// This test ensures that the first token entered must be a value, variable or open parenthesis.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions4()
        {
            Formula f1 = new Formula("+ 5 - 7");
        }
        /// <summary>
        /// This test ensures that the last token entered must be a value, variable or closed parenthesis.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions5()
        {
            Formula f1 = new Formula("5 - 7 /");
        }
        /// <summary>
        /// This test ensures that preceeding and open parenthesis must be a value, variable or open parenthesis.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions6()
        {
            Formula f1 = new Formula("(+ 5 - 7");
        }
        /// <summary>
        /// This test ensures that preceeding and open parenthesis must be a value, variable or open parenthesis.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions7()
        {
            Formula f1 = new Formula("()+ 5 - 7");
        }
        /// <summary>
        /// This test ensure that the number of open and closing parenthesis are equal.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions8()
        {
            Formula f1 = new Formula("(1 + 5) - 7)");
        }
        /// <summary>
        /// This test ensures that preceeding and closed parenthesis must be a operator or a closed parenthesis.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions9()
        {
            Formula f1 = new Formula("(1 + 5)8 - 7");
        }
        /// <summary>
        /// This test ensures that preceeding a operator must be a value, variable or open parenthesis.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions10()
        {
            Formula f1 = new Formula("( 1+ 5 -) 7");
        }
        /// <summary>
        /// This test ensures that preceeding a operator must be a value, variable or open parenthesis.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions11()
        {
            Formula f1 = new Formula("1 + - 5 - 7");
        }
        /// <summary>
        /// This test ensures that preceeding a value must be a operator or closed parenthesis.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions12()
        {
            Formula f1 = new Formula("1 + 5 5 - 7");
        }
        /// <summary>
        /// This test ensures that preceeding a variable must be a operator or closed parenthesis.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions13()
        {
            Formula f1 = new Formula("x1 5 + 5 - 7");
        }
        /// <summary>
        /// This test ensures that preceeding a open parenthesis must be a value, variable or open parenthesis.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions14()
        {
            Formula f1 = new Formula("x1 ( + 5 - 7");
        }
        /// <summary>
        /// This test ensure no invalid variables are entered into the formula expression.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions15()
        {
            Formula f1 = new Formula("1x 5 + 5 - 7");
        }
        /// <summary>
        /// This test ensure that the number of open and closed parenthesis must be the same.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions16()
        {
            Formula f1 = new Formula("((5 + 5 - 7)");
        }
        /// <summary>
        /// This test ensure that NaN can't be entered as a value.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions17()
        {
            Formula f1 = new Formula("Double.NaN + 1");
        }
        
        /// <summary>
        /// This test ensures that if including a variable that doesn't meet the IsValid requirements a exception is thrown.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions18()
        {
            Formula f1 = new Formula("1 + _x12 * a + A", Normalizer, IsValid);
        }
        /// <summary>
        /// This test ensure that if a Bad Normalizer is given that and exception will be thrown.
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions19()
        {
            Formula f1 = new Formula("1 + _x12 * a + A", BadNormalizer, IsValid);
        }
        /// <summary>
        /// 
        /// </summary>
        [TestMethod()]
        [ExpectedException(typeof(FormulaFormatException))]
        public void Public_TestFormulaConstructorExceptions20()
        {
            Formula f1 = new Formula("(1 + #)");
        }


        // The following tests ensure that the Evaluator thrown exceptions work.

        /// <summary>
        /// This test ensures that a FormulaError is returned if a division by zero occurs.
        /// </summary>
        [TestMethod()]
        public void Public_TestFormulaEvaluatorExceptions1()
        {
            Formula f1 = new Formula("0/0");
            object error =  f1.Evaluate(x => 5);
            Assert.IsTrue(error is FormulaError);
        }
        /// <summary>
        /// This test ensures that a FormulaError is returned if a division by zero occurs.
        /// </summary>
        [TestMethod()]
        public void Public_TestFormulaEvaluatorExceptions2()
        {
            Formula f1 = new Formula("(0+0)/0");
            object error = f1.Evaluate(x => 5);
            Assert.IsTrue(error is FormulaError);
        }
        /// <summary>
        /// This test ensure that a FormulaError is returned if valid variable is passed in, but it doesn't have a value.
        /// </summary>
        [TestMethod()]
        public void Public_TestFormulaEvaluatorExceptions3()
        {
            Formula f1 = new Formula("1 + _x1 + y12 + z28");
            object error = f1.Evaluate(Lookup);
            Assert.IsTrue(error is FormulaError);
        }
        /// <summary>
        /// This test ensures that a Formula Error is returned if a divide by zero occurs.
        /// </summary>
        [TestMethod()]
        public void Public_TestFormulaEvaluatorExceptions4()
        {
            Formula f1 = new Formula("(0*0)/0");
            object error = f1.Evaluate(x => 5);
            Assert.IsTrue(error is FormulaError);
        }
        /// <summary>
        /// This test ensures that a Formula Error is returned if a divide by zero occurs.
        /// </summary>
        [TestMethod()]
        public void Public_TestFormulaEvaluatorExceptions5()
        {
            Formula f1 = new Formula("(0*0/0)");
            object error = f1.Evaluate(x => 5);
            Assert.IsTrue(error is FormulaError);
        }
        /// <summary>
        /// This test ensures that a Formula Error is returned if a divide by zero occurs.
        /// </summary>
        [TestMethod()]
        public void Public_TestFormulaEvaluatorExceptions6()
        {
            Formula f1 = new Formula("0 / (2-2)");
            object error = f1.Evaluate(x => 5);
            Assert.IsTrue(error is FormulaError);
        }
        /// <summary>
        /// This test ensures that a Formula Error is returned if a divide by zero occurs.
        /// </summary>
        [TestMethod()]
        public void Public_TestFormulaEvaluatorExceptions7()
        {
            Formula f1 = new Formula("(2-2) / (2-2)");
            object error = f1.Evaluate(x => 5);
            Assert.IsTrue(error is FormulaError);
        }
        /// <summary>
        /// This test ensures that a Formula Error is returned if a divide by zero occurs if using a variable.
        /// </summary>
        [TestMethod()]
        public void Public_TestFormulaEvaluatorExceptions8()
        {
            Formula f1 = new Formula("(2-2) / x");
            object error = f1.Evaluate(x => 0);
            Assert.IsTrue(error is FormulaError);
        }


        // The following tests are the ones for my private methods used in the Formula class.

        /// <summary>
        /// Test and ensure that IsVaraible returns true for correct variables.
        /// </summary>
        [TestMethod()]
        public void Private_TestIsVariable1()
        {
            Formula f = new Formula("1+1");
            PrivateObject formula_accessor = new PrivateObject(f);
            Assert.AreEqual(true, formula_accessor.Invoke("IsVariable", "x12"));

        }
        /// <summary>
        /// Test and ensure that IsVaraible returns true for correct variables.
        /// </summary>
        [TestMethod()]
        public void Private_TestIsVariable2()
        {
            Formula f = new Formula("1+1");
            PrivateObject formula_accessor = new PrivateObject(f);
            Assert.AreEqual(true, formula_accessor.Invoke("IsVariable", "X12"));

        }
        /// <summary>
        /// Test and ensure that IsVaraible returns true for correct variables.
        /// </summary>
        [TestMethod()]
        public void Private_TestIsVariable3()
        {
            Formula f = new Formula("1+1");
            PrivateObject formula_accessor = new PrivateObject(f);
            Assert.AreEqual(true, formula_accessor.Invoke("IsVariable", "_X12"));

        }
        /// <summary>
        /// Test and ensure that IsVaraible returns true for correct variables.
        /// </summary>
        [TestMethod()]
        public void Private_TestIsVariable4()
        {
            Formula f = new Formula("1+1");
            PrivateObject formula_accessor = new PrivateObject(f);
            Assert.AreEqual(true, formula_accessor.Invoke("IsVariable", "X12_"));

        }
        /// <summary>
        /// Test and ensure that IsVaraible returns true for correct variables.
        /// </summary>
        [TestMethod()]
        public void Private_TestIsVariable5()
        {
            Formula f = new Formula("1+1");
            PrivateObject formula_accessor = new PrivateObject(f);
            Assert.AreEqual(true, formula_accessor.Invoke("IsVariable", "X12_x"));

        }
        /// <summary>
        /// Test and ensure that IsVaraible returns true for correct variables.
        /// </summary>
        [TestMethod()]
        public void Private_TestIsVariable6()
        {
            Formula f = new Formula("1+1");
            PrivateObject formula_accessor = new PrivateObject(f);
            Assert.AreEqual(true, formula_accessor.Invoke("IsVariable", "X12_X"));

        }
        /// <summary>
        /// Test and ensure that IsVaraible returns true for correct variables.
        /// </summary>
        [TestMethod()]
        public void Private_TestIsVariable7()
        {
            Formula f = new Formula("1+1");
            PrivateObject formula_accessor = new PrivateObject(f);
            Assert.AreEqual(true, formula_accessor.Invoke("IsVariable", "x"));

        }
        /// <summary>
        /// Test and ensure that IsVaraible returns false for incorrect variables.
        /// </summary>
        [TestMethod()]
        public void Private_TestIsVariable8()
        {
            Formula f = new Formula("1+1");
            PrivateObject formula_accessor = new PrivateObject(f);
            Assert.AreEqual(false, formula_accessor.Invoke("IsVariable", "#12"));

        }
        /// <summary>
        /// Test and ensure that IsVaraible returns false for incorrect variables.
        /// </summary>
        [TestMethod()]
        public void Private_TestIsVariable9()
        {
            Formula f = new Formula("1+1");
            PrivateObject formula_accessor = new PrivateObject(f);
            Assert.AreEqual(false, formula_accessor.Invoke("IsVariable", "12"));

        }


        // The following tests are for the hasOnTop Method.


        /// <summary>
        /// This test ensures that if a certain char value is on top of the stack it returns true, if not return false.
        /// </summary>
        [TestMethod()]
        public void Private_TesthasOnTop()
        {
            Formula f = new Formula("1+1");

            PrivateObject formula_accessor = new PrivateObject(f);

            // Create a stack and push values on it checking if they are on top.

            Stack<char> stack = new Stack<char>();

            stack.Push('*');

            Assert.AreEqual(true, formula_accessor.Invoke("hasOnTop", stack, '*', '/'));

            stack.Push('+');

            Assert.AreEqual(true, formula_accessor.Invoke("hasOnTop", stack, '+', '-'));

            stack.Push('/');

            Assert.AreEqual(true, formula_accessor.Invoke("hasOnTop", stack, '*', '/'));

            Assert.AreEqual(false, formula_accessor.Invoke("hasOnTop", stack, '+', '-'));
        }


        // The following tests are for the performMath Method.

        /// <summary>
        /// 
        /// </summary>
        [TestMethod()]
        public void Private_TestperformMath()
        {

            // Create a formula object so I can test the method.
            Formula f = new Formula("1+1");

            PrivateObject formula_accessor = new PrivateObject(f);

            Assert.AreEqual(2.0, formula_accessor.Invoke("performMath", 1, 1, '+'));

            Assert.AreEqual(0.0, formula_accessor.Invoke("performMath", 1, 1, '-'));

            Assert.AreEqual(1.0, formula_accessor.Invoke("performMath", 1, 1, '*'));

            Assert.AreEqual(1.0, formula_accessor.Invoke("performMath", 1, 1, '/'));

        }







        // The following are methods used to aid in Testing.

        /// <summary>
        /// A simple normalizer method in order to test bringing in a normalizer method into the Formula constructor. This normalizer simply converts the string to lower case.
        /// </summary>
        /// <param name="s">The string value you wish to normalize.</param>
        /// <returns>A string with only lower chars in it.</returns>
        private static String Normalizer(String s)
        {
            return s.ToLower();
        }
        /// <summary>
        /// A example of a bad normalizer built to test and if the user gives a normalizer that would break the variable rules.
        /// </summary>
        /// <param name="s">The string value you wish to normalize.</param>
        /// <returns>Return a $ or a bad variable.</returns>
        private static String BadNormalizer(String s)
        {
            return "$";
        }
        /// <summary>
        /// This method serves only to test bringing in and using a IsValid method into the formula constructor. This IsValid checker ensures ther are no varibles of size less than
        /// or equal to 3.
        /// </summary>
        /// <param name="s">The string you wish to ensure is valid.</param>
        /// <returns>A boolean variable that determines if the inputed string is less than or equal to three.</returns>
        private static Boolean IsValid(String s)
        {
            if(s.Length <= 3)
            {
                return true;
            }
            return false;
        }
        /// <summary>
        /// This method serves only to test the Evaluate method in the Formula constructor. 
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        private static double Lookup(String s)
        {
            Dictionary<String, double> lookup = new Dictionary<String, double>();

            lookup.Add("_x1", 2.0);
            lookup.Add("y12", 3.0);

            if (lookup.ContainsKey(s))
            {
                return lookup[s];
            }

            throw new ArgumentException();
        }



    }


}