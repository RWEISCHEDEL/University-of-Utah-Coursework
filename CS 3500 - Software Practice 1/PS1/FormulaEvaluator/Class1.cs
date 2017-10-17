using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Text.RegularExpressions;

namespace FormulaEvaluator
{
    /// <summary>
    /// This class is a Library that is used to evaluate formula expressions.
    /// </summary>
    public static class Evaluator
    {
        
        /// <summary>
        /// The Delegate that serves as the variable evaluator for the given string expression. It will help find the values associated with the variable in the expression.
        /// If it exists, it returns an int value, it not it throws an excpetion. 
        /// </summary>
        /// <param name="v">The string variable name from the expression that Evaluator wishes to know the integer value of.</param>
        /// <returns>The int value of the variable.</returns>
        public delegate int Lookup(String v);

        /// <summary>
        /// This method performs evaluations on formula expressions entered by the user. The user types in a string expression and give it a Delegate function to serve
        /// as the variable evaluator, then the Library solves the equation and returns the answer if possible. If it is not able to be solved or illegal arguments have been entered, an 
        /// exception is thrown.
        /// Robert Weischedel u0887905 CS3500 9/10/15
        /// </summary>
        /// <param name="exp">A string expression that is given by the user that they wish to know the integer answer to.</param>
        /// <param name="variableEvaluator">This is a function that is brought into the Library to serve as the decoder for the variables that could be found in the given expression.</param>
        /// <returns>A integer value for the solved expression</returns>
        public static int Evaluate(String exp, Lookup variableEvaluator)
        {
            // Check and see if the string that is brought in 
            if (string.IsNullOrWhiteSpace(exp))
            {
                throw new ArgumentException();
            }

            // These stacks represent the bins where the valeus and operators go from the given expression.
            Stack<double> valueStack = new Stack<double>();

            Stack<char> operatorStack = new Stack<char>();

            // Use a Regex command to split the given  expression into an array of strings.
            String[] substrings = Regex.Split(exp, "(\\()|(\\))|(-)|(\\+)|(\\*)|(/)");

            // This for loop searches through the string array
            for (int i = 0; i < substrings.Length; i++)
            {
                // This variable to to hold the values found in the string array after they are parsed
                double valFromEvalString;

                // Trim any excess whitespace
                String stringToEval = substrings[i].Trim();

                // Check and see if the string is empty or null, if it is just skip this iteration through the for loop
                if (String.IsNullOrWhiteSpace(stringToEval))
                {
                    continue;
                }

                // If the string is a integer
                if (double.TryParse(stringToEval, out valFromEvalString))
                {
                    // Check and see if a * or / is on top of the operator stack.
                    if (operatorStack.hasOnTop('*', '/'))
                    {
                        // Ensure that the valueStack has atleast one value in it.
                        if (valueStack.Count() == 0)
                        {
                            throw new ArgumentException();
                        }

                        // Pop the top value from the valueStack
                        double poppedVal = valueStack.Pop();

                        // Pop the top symbol from the operatorStack
                        char poppedSymbol = operatorStack.Pop();

                        // If the val that was just parsed is zero and the symbol is /, throw an excpetion to avoid divide by zero.
                        if (valFromEvalString == 0 && poppedSymbol.Equals('/'))
                        {
                            throw new ArgumentException();
                        }

                        // Call the performMath function to get the new resulting value and push it on the valueStack.

                        valueStack.Push(performMath(poppedVal, valFromEvalString, poppedSymbol));
                    }
                    else
                    {

                        // Just push the value on the valueStack

                        valueStack.Push(valFromEvalString);
                    }
                }
                // If the string is a + or -
                else if (stringToEval[0].Equals('+') || stringToEval[0].Equals('-'))
                {

                    // Check and see if there is a + or - on top of the operatorStack
                    if (operatorStack.hasOnTop('+', '-'))
                    {
                        // Make sure there are atleast two values in the valueStack
                        if (valueStack.Count() < 2)
                        {
                            throw new ArgumentException();
                        }
                        // Pop two values and an operator symbol
                        double poppedVal1 = valueStack.Pop();
                        double poppedVal2 = valueStack.Pop();

                        char poppedExpression = operatorStack.Pop();

                        // Call the performMath function to get the new resulting value and push it on the valueStack.

                        valueStack.Push(performMath(poppedVal2, poppedVal1, poppedExpression));
                    }

                    // Or if a + or - is not on top of the operator stack, push the new + or - on the operatorStack
                    operatorStack.Push(stringToEval[0]);

                }
                // If the string contains a * or /
                else if (stringToEval[0].Equals('*') || stringToEval[0].Equals('/'))
                {
                    // Push the symbol on the operatorStack
                    operatorStack.Push(stringToEval[0]);
                }
                // If the string contains a (
                else if (stringToEval[0].Equals('('))
                {
                    // Push the symbol on the operatorStack
                    operatorStack.Push(stringToEval[0]);
                }
                // If the string contains a )
                else if (stringToEval[0].Equals(')'))
                {
                    // Declare the variables that will be used later on for the variuos operations.
                    double poppedVal1;
                    double poppedVal2;
                    char poppedExpression;

                    // Check and see if a + or - is on top of the operatorStack
                    if (operatorStack.hasOnTop('+', '-'))
                    {
                        // Ensure there are atleast two values in the valueStack. If not throw and exception.
                        if (valueStack.Count() < 2)
                        {
                            throw new ArgumentException();
                        }

                        // Pop the two values and a symbol
                        poppedVal1 = valueStack.Pop();
                        poppedVal2 = valueStack.Pop();

                        poppedExpression = operatorStack.Pop();

                        // Call the performMath function to get the new resulting value and push it on the valueStack.
                        valueStack.Push(performMath(poppedVal2, poppedVal1, poppedExpression));
                    }

                    // Ensure that a ) is on top of the operator stack, if not throw an exception.
                    if (operatorStack.Count == 0 || !operatorStack.Peek().Equals('('))
                    {
                        throw new ArgumentException();
                    }

                    // Remove the ) from the top of the operatorStack
                    operatorStack.Pop();

                    // Check and see if the operatorStack now has a * or / on top
                    if (operatorStack.hasOnTop('*', '/'))
                    {
                        // Ensure there are atleast two values in the valueStack. If not throw and exception.
                        if (valueStack.Count() < 2)
                        {
                            throw new ArgumentException();
                        }

                        // Pop the two values and a symbol
                        poppedVal1 = valueStack.Pop();
                        poppedVal2 = valueStack.Pop();

                        poppedExpression = operatorStack.Pop();

                        // Ensure that if the second popped value is a 0 and the symbol is /, that an exception is thrown to avoid dividing by zero.
                        if (poppedVal2 == 0 && poppedExpression.Equals('/'))
                        {
                            throw new ArgumentException();
                        }

                        // Call the performMath function to get the new resulting value and push it on the valueStack.
                        valueStack.Push(performMath(poppedVal2, poppedVal1, poppedExpression));
                    }

                }
                else
                {
                    // Use a Regex to set up the parameters for what a valid variable expression can be.
                    Regex varReqs = new Regex(@"^[a-zA-Z]+[0-9]+$");

                    // Check and see if the string meets all the parameters of the Regex, if not throw an exception
                    if (!varReqs.IsMatch(stringToEval.Trim()))
                    {
                        throw new ArgumentException();
                    }

                    // Call the variableEvaluator delegate to find the function that defines what the values are for the variables.
                    int decodedVal = variableEvaluator(stringToEval);

                    // Check and see if a * or / is on top of the operator stack.
                    if (operatorStack.hasOnTop('*', '/'))
                    {
                        // Ensure that the valueStack has atleast one value in it.
                        if (valueStack.Count() == 0)
                        {
                            throw new ArgumentException();
                        }

                        // Pop the top value from the valueStack
                        double poppedVal = valueStack.Pop();

                        // Pop the top symbol from the operatorStack
                        char poppedSymbol = operatorStack.Pop();

                        // If the val that was just parsed is zero and the symbol is /, throw an excpetion to avoid divide by zero.
                        if (decodedVal == 0 && poppedSymbol.Equals('/'))
                        {
                            throw new ArgumentException();
                        }

                        // Call the performMath function to get the new resulting value and push it on the valueStack.
                        valueStack.Push(performMath(poppedVal, decodedVal, poppedSymbol));
                    }
                    else
                    {
                        // Just push the value on the valueStack
                        valueStack.Push(decodedVal);
                    }
                }
            }

            // We are now free from the for loop.

            // This vairable contains the final answer that will be returned to the caller.
            double finalAnswer;

            // Check and see if the operatorStack is empty
            if (operatorStack.Count() == 0)
            {
                // Check and see if the valueStack only contains 1 value, if not throw and exception.
                if (valueStack.Count() != 1)
                {
                    throw new ArgumentException();
                }

                // Set the finalAnswer to the only value in the valueStack.
                finalAnswer = valueStack.Pop();
            }
            //If the operatorStack is not empty
            else
            {
                // Ensure that the operatorStack only has one value and the value stack only has two values. If not throw an exception.
                if (operatorStack.Count() != 1 || valueStack.Count() != 2)
                {
                    throw new ArgumentException();
                }

                // Check and see if the operator stack has a + or - left in it, if not throw an exception.
                if (operatorStack.hasOnTop('+', '-'))
                {
                    // Ensure there are atleast two values in the valueStack. If not throw and exception.
                    if (valueStack.Count() < 2)
                    {
                        throw new ArgumentException();
                    }

                    // Pop the two values and a symbol
                    double poppedVal1 = valueStack.Pop();
                    double poppedVal2 = valueStack.Pop();

                    char poppedExpression = operatorStack.Pop();

                    // Call perfromMath function to get the new value and store it in finalAnswer
                    finalAnswer = performMath(poppedVal2, poppedVal1, poppedExpression);
                }
                // If the top of the stack was not a + or -, throw an exception.
                else
                {
                    throw new ArgumentException();
                }
            }

            // Return final answer to caller.
            return (int)finalAnswer;
        }

        /// <summary>
        /// This method serves as a helper method for the Evaluate function. It looks at the top of the operator stack and sees if two different symbols are or are not on top of the stack.
        /// It also checks to see if the stack has at least one value as well, if not it returns false.
        /// </summary>
        /// <param name="stack">This stack is the operatorStack from the Evaluate method, we bring it in to check and see what is on top of it.</param>
        /// <param name="firstChar">The first char symbol we wish to see if it is on top of the stack.</param>
        /// <param name="secChar">The second char symbol we wish to see if it is on top of the stack.</param>
        /// <returns>True if either char symbol is on top of the stack or false if the stack is empty or doesn't have either symbol on top.</returns>
        public static Boolean hasOnTop(this Stack<char> stack, char firstChar, char secChar)
        {
            // Ensure that that the stack has atleast one value in it.
            if (stack.Count() > 0)
            {
                // If the stack has either char symbol on top return true.
                if (stack.Peek().Equals(firstChar) || stack.Peek().Equals(secChar))
                {
                    return true;
                }
                // If not, return false.
                else
                {
                    return false;
                }
            }
            // If stack is empty, return false.
            else
            {
                return false;
            }
        }

        /// <summary>
        /// This method serves as a helper method for the Evaluator class. It performs all the math for Evaluator. It takes in two double values and a legal char math symbol, and passed on which
        /// symbol performs the correct mathmatical expression to the two input values. If the char is illegal or not know, an exception is thrown.
        /// </summary>
        /// <param name="poppedVal1">A double value brought in from the Evaluator class that needs to have an mathmatical expression performed on it.</param>
        /// <param name="poppedVal2">A double value brought in from the Evaluator class that needs to have an mathmatical expression performed on it.</param>
        /// <param name="symbol">The char symbol of which mathmatical symbol needs to performed on the values.</param>
        /// <returns>A double value of the result of the mathmatical expression that was performed.</returns>
        public static double performMath(double poppedVal1, double poppedVal2, char symbol)
        {
            // Declare a variable to store the answer into.
            double answer;

            // Determine based on which symbol, which mathmatical expression to do. If it doesn't exist, throw an exception.
            switch (symbol)
            {
                case '+':
                    {
                        answer = poppedVal1 + poppedVal2;
                        break;
                    }
                case '-':
                    {
                        answer = poppedVal1 - poppedVal2;
                        break;
                    }
                case '*':
                    {
                        answer = poppedVal1 * poppedVal2;
                        break;
                    }
                case '/':
                    {
                        answer = poppedVal1 / poppedVal2;
                        break;
                    }
                default:
                    {
                        throw new ArgumentException();
                    }
            }
            
            // Return the answer
            return answer;
        }
    }
}
