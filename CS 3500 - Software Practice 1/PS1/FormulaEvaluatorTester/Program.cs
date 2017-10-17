using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using FormulaEvaluator;

namespace FormulaEvaluatorTester
{
    class EvaluatorTester
    {
        /// <summary>
        /// This main method serves as the tester for the FormulaEvaluator Library that was created. It performs a variety of tests, from basic to advanced expression evaluation to testing 
        /// for invalid operators and variable names entered.
        /// Robert Weischedel u0887905 CS 3500 9/10/15
        /// </summary>
        /// <param name="args"></param>
        static void Main(string[] args)
        {
            try
            {
                int test23 = Evaluator.Evaluate("2+5*7)", LookupMethod);
                Console.WriteLine("Test 23 Failed!");
            }
            catch
            {
                Console.WriteLine("Test 23 Passed!");
                Console.Read();
            }

            //// Testing small expressions
            //int test1 = Evaluator.Evaluate("((1+1)+2)", LookupMethod);
            //Console.WriteLine(test1);

            //int test2 = Evaluator.Evaluate("5-5", LookupMethod);
            //Console.WriteLine(test2);

            //int test3 = Evaluator.Evaluate("9*9", LookupMethod);
            //Console.WriteLine(test3);

            //int test4 = Evaluator.Evaluate("10/5", LookupMethod);
            //Console.WriteLine(test4);

            //int test5 = Evaluator.Evaluate("(5+5)-2", LookupMethod);
            //Console.WriteLine(test5);

            //int test6 = Evaluator.Evaluate("(10+5)-10", LookupMethod);
            //Console.WriteLine(test6);

            //int test7 = Evaluator.Evaluate("(5*6)-2", LookupMethod);
            //Console.WriteLine(test7);

            //int test8 = Evaluator.Evaluate("25 + (10 -2)", LookupMethod);
            //Console.WriteLine(test8);

            //int test9 = Evaluator.Evaluate("(10*10)/2", LookupMethod);
            //Console.WriteLine(test9);

            //int test10 = Evaluator.Evaluate("(15/5)*3", LookupMethod);
            //Console.WriteLine(test10);

            //int test11 = Evaluator.Evaluate("15/5*3", LookupMethod);
            //Console.WriteLine(test11);

            //int test12 = Evaluator.Evaluate("15/3*5", LookupMethod);
            //Console.WriteLine(test12);

            //int test13 = Evaluator.Evaluate("2+5*3", LookupMethod);
            //Console.WriteLine(test13);

            //int test14 = Evaluator.Evaluate("(15*15)-(10*10)", LookupMethod);
            //Console.WriteLine(test14);

            //// Testing larger expression with spaces.

            //int test15 = Evaluator.Evaluate("(5 + 5) - (2 + 1)", LookupMethod);
            //Console.WriteLine(test15);

            //int test16 = Evaluator.Evaluate("(2 + 3) * 5 + 2", LookupMethod);
            //Console.WriteLine(test16);

            //int test17 = Evaluator.Evaluate("( 5 / 5 ) - 2", LookupMethod);
            //Console.WriteLine(test17);

            //int test18 = Evaluator.Evaluate(" (   10 /    5   /  5 )   -    1", LookupMethod);
            //Console.WriteLine(test18);

            //// Testing even larger expressions.

            //int test19 = Evaluator.Evaluate("(20/5/2+3)-2", LookupMethod);
            //Console.WriteLine(test19);

            //int test20 = Evaluator.Evaluate("5*4*3*2*1/12", LookupMethod);
            //Console.WriteLine(test20);

            //int test21 = Evaluator.Evaluate("5+1*4+2*3+3*2+4*1+5/12", LookupMethod);
            //Console.WriteLine(test21);

            //int test22 = Evaluator.Evaluate("5+1-5*4+2-4*3+3-3*2+4-2*1+5-1/12", LookupMethod);
            //Console.WriteLine(test22);

            //// Testing for null and whitespace entered.

            //try
            //{
            //    int test23 = Evaluator.Evaluate("", LookupMethod);
            //    Console.WriteLine("Test 23 Empty Input String Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 23 Empty Input String Passed!");
            //}

            //try
            //{
            //    int test24 = Evaluator.Evaluate("                                ", LookupMethod);
            //    Console.WriteLine("Test 24 Large Whitespace Entered Only Test Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 24 Large Whitespace Entered Only Test Passed!");
            //}

            //try
            //{
            //    int test25 = Evaluator.Evaluate(null, LookupMethod);
            //    Console.WriteLine("Test 25 Null Entered Test Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 25 Null Entered Test Passed!");
            //}




            //// Begin Variable Testing

            //int test26 = Evaluator.Evaluate("5 + x1", LookupMethod);
            //Console.WriteLine(test26);

            //int test27 = Evaluator.Evaluate("(5 + x1) * 2", LookupMethod);
            //Console.WriteLine(test27);

            //int test28 = Evaluator.Evaluate("(x1 *5) + 3 * x1", LookupMethod);
            //Console.WriteLine(test28);

            //int test29 = Evaluator.Evaluate("x1 / x1", LookupMethod);
            //Console.WriteLine(test29);

            //int test30 = Evaluator.Evaluate("x1 + x2 + x3 + x4 + x5", LookupMethod);
            //Console.WriteLine(test30);

            //int test31 = Evaluator.Evaluate("(x1 + x2 + x3 + x4 + x5) / Y123456789", LookupMethod);
            //Console.WriteLine(test31);

            //int test32 = Evaluator.Evaluate("X123456 * x1 + x2 + x3 + x4 + x5 / ASHJKL1859", LookupMethod);
            //Console.WriteLine(test32);

            //int test33 = Evaluator.Evaluate("x1 * X123456 / x2 - ASHJKL1859 + x3 * aSEgjkl1 / x4 - PulPO2 + x5 * E2 / Y123456789", LookupMethod);
            //Console.WriteLine(test33);

            //int test34 = Evaluator.Evaluate("x1 + x2 + x3 * x4 * x5", LookupMethod);
            //Console.WriteLine(test34);

            //int test35 = Evaluator.Evaluate("(x1 * X123456 / x2 - ASHJKL1859 + x3 * aSEgjkl1 / x4 - PulPO2 + x5 * E2 / Y123456789) * 2 - 3", LookupMethod);
            //Console.WriteLine(test35);

            //int test36 = Evaluator.Evaluate("(x1*X123456/x2-ASHJKL1859+x3*aSEgjkl1/x4-PulPO2+x5*E2/Y123456789)*2-3", LookupMethod);
            //Console.WriteLine(test36);

            //// Begin Testing of Thrown Excpetions - Divide By Zero

            //try
            //{
            //    int test37 = Evaluator.Evaluate("0 / 0", LookupMethod);
            //    Console.WriteLine("Test 37 Divide by Zero Test Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 37 Divide by Zero Test Passed!");
            //}

            //try
            //{
            //    int test38 = Evaluator.Evaluate("x1 / 0", LookupMethod);
            //    Console.WriteLine("Test 38 Divide by Zero Test Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 38 Divide by Zero Test Passed!");
            //}

            //try
            //{
            //    int test39 = Evaluator.Evaluate("(5+1-5*4+2-4*3+3-3*2+4-2*1+5-1/12) / 0", LookupMethod);
            //    Console.WriteLine("Test 39 Divide by Zero Test Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 39 Divide by Zero Test Passed!");
            //}

            //try
            //{
            //    int test40 = Evaluator.Evaluate("(5+1-5*4+2-4*3+3/0-3*2+4-2*1+5-1/12)", LookupMethod);
            //    Console.WriteLine("Test 40 Divide by Zero Test Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 40 Divide by Zero Test Passed!");
            //}

            //// Begin Testing of Thrown Excpetions - Invalid Operators Entered Into Expression

            //try
            //{
            //    int test41 = Evaluator.Evaluate("/", LookupMethod);
            //    Console.WriteLine("Test 41 Invalid Expression Entered Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 41 Invalid Expression Entered Passed!");
            //}

            //try
            //{
            //    int test42 = Evaluator.Evaluate("5 + * 2", LookupMethod);
            //    Console.WriteLine("Test 42 Invalid Expression Entered Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 42 Invalid Expression Entered Passed!");
            //}

            //try
            //{
            //    int test43 = Evaluator.Evaluate("5 + 2 / 5 - (", LookupMethod);
            //    Console.WriteLine("Test 43 Invalid Expression Entered Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 43 Invalid Expression Entered Passed!");
            //}

            //try
            //{
            //    int test44 = Evaluator.Evaluate("(5 + 2", LookupMethod);
            //    Console.WriteLine("Test 44 Invalid Expression Entered Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 44 Invalid Expression Entered Passed!");
            //}

            //try
            //{
            //    int test44 = Evaluator.Evaluate("5 * 2)", LookupMethod);
            //    Console.WriteLine("Test 44 Invalid Expression Entered Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 44 Invalid Expression Entered Passed!");
            //}

            //try
            //{
            //    int test45 = Evaluator.Evaluate("/ 5 + 2", LookupMethod);
            //    Console.WriteLine("Test 45 Invalid Expression Entered Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 45 Invalid Expression Entered Passed!");
            //}

            //try
            //{
            //    int test46 = Evaluator.Evaluate("5 + 2 + x1 + x5 / * x2", LookupMethod);
            //    Console.WriteLine("Test 46 Invalid Expression Entered Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 46 Invalid Expression Entered Passed!");
            //}

            //try
            //{
            //    int test47 = Evaluator.Evaluate("x1 * x2 / x5 + - x1", LookupMethod);
            //    Console.WriteLine("Test 47 Invalid Expression Entered Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 47 Invalid Expression Entered Passed!");
            //}

            //try
            //{
            //    int test48 = Evaluator.Evaluate("5 + 2 . ", LookupMethod);
            //    Console.WriteLine("Test 48 Invalid Expression Entered Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 48 Invalid Expression Entered Passed!");
            //}

            //try
            //{
            //    int test49 = Evaluator.Evaluate("? + 5 + 5", LookupMethod);
            //    Console.WriteLine("Test 49 Invalid Expression Entered Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 49 Invalid Expression Entered Passed!");
            //}

            //try
            //{
            //    int test50 = Evaluator.Evaluate("5 + &", LookupMethod);
            //    Console.WriteLine("Test 50 Invalid Expression Entered Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 50 Invalid Expression Entered Passed!");
            //}

            //try
            //{
            //    int test51 = Evaluator.Evaluate("x1 * x2 + x3 - x4 + %", LookupMethod);
            //    Console.WriteLine("Test 51 Invalid Expression Entered Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 51 Invalid Expression Entered Passed!");
            //}

            //// Begin Testing of Thrown Excpetions - Invalid Variable Names

            //try
            //{
            //    int test52 = Evaluator.Evaluate("5 + x6", LookupMethod);
            //    Console.WriteLine("Test 52 Invalid Expression Entered Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 52 Invalid Expression Entered Passed!");
            //}

            //try
            //{
            //    int test53 = Evaluator.Evaluate("5 * x1 + x2 * ymca", LookupMethod);
            //    Console.WriteLine("Test 53 Invalid Expression Entered Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 53 Invalid Expression Entered Passed!");
            //}

            //try
            //{
            //    int test54 = Evaluator.Evaluate("booger - 5 * x1 + x2 * x4 + 7", LookupMethod);
            //    Console.WriteLine("Test 54 Invalid Expression Entered Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 54 Invalid Expression Entered Passed!");
            //}

            //try
            //{
            //    int test55 = Evaluator.Evaluate("5 * x1 + cs3500 * 7", LookupMethod);
            //    Console.WriteLine("Test 55 Invalid Expression Entered Failed!");
            //}
            //catch
            //{
            //    Console.WriteLine("Test 55 Invalid Expression Entered Passed!");
            //}

            //Console.Read();
        }


        /// <summary>
        /// This method serves as the variableEvaluator in the FormulaEvaluator Library. It looks up the value associated with the variable and returns that value or throws an
        /// exception if the varibable is not in the Dictionary.
        /// </summary>
        /// <param name="variable">A String containing the name of the variable you wish to look up.</param>
        /// <returns>A int value of the variable that it is related too.</returns>
        public static int LookupMethod(String variable)
        {
            // Create a Dictionary and add some variables and thier values to it.
            Dictionary<String, int> varDict = new Dictionary<String, int>();

            varDict.Add("x1", 5);

            varDict.Add("x2", 10);

            varDict.Add("x3", 1);

            varDict.Add("x4", 2);

            varDict.Add("x5", 3);

            varDict.Add("X123456", 11);

            varDict.Add("ASHJKL1859", 12);

            varDict.Add("aSEgjkl1", 8);

            varDict.Add("PulPO2", 4);

            varDict.Add("E2", 6);

            varDict.Add("Y123456789", 7);

            // Check and see if the variable is in the Dictionary, if not throw an exception.
            if (!varDict.ContainsKey(variable))
            {
                throw new ArgumentException();
            }

            // Return the value associated with the variable.
            return varDict[variable];
        }
    }


}
