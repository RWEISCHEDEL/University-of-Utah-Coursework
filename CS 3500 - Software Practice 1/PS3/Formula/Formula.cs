// Skeleton written by Joe Zachary for CS 3500, September 2013
// Read the entire skeleton carefully and completely before you
// do anything else!

// Version 1.1 (9/22/13 11:45 a.m.)

// Change log:
//  (Version 1.1) Repaired mistake in GetTokens
//  (Version 1.1) Changed specification of second constructor to
//                clarify description of how validation works

// Code filled in by : Robert Weischedel u0887905 CS 3500

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

namespace SpreadsheetUtilities
{
    /// <summary>
    /// Represents formulas written in standard infix notation using standard precedence
    /// rules.  The allowed symbols are non-negative numbers written using double-precision 
    /// floating-point syntax; variables that consist of a letter or underscore followed by 
    /// zero or more letters, underscores, or digits; parentheses; and the four operator 
    /// symbols +, -, *, and /.  
    /// 
    /// Spaces are significant only insofar that they delimit tokens.  For example, "xy" is
    /// a single variable, "x y" consists of two variables "x" and y; "x23" is a single variable; 
    /// and "x 23" consists of a variable "x" and a number "23".
    /// 
    /// Associated with every formula are two delegates:  a normalizer and a validator.  The
    /// normalizer is used to convert variables into a canonical form, and the validator is used
    /// to add extra restrictions on the validity of a variable (beyond the standard requirement 
    /// that it consist of a letter or underscore followed by zero or more letters, underscores,
    /// or digits.)  Their use is described in detail in the constructor and method comments.
    /// </summary>
    public class Formula
    {

        private List<String> expression;

        private HashSet<String> normalizedVars;

        /// <summary>
        /// Creates a Formula from a string that consists of an infix expression written as
        /// described in the class comment.  If the expression is syntactically invalid,
        /// throws a FormulaFormatException with an explanatory Message.
        /// 
        /// The associated normalizer is the identity function, and the associated validator
        /// maps every string to true.  
        /// </summary>
        public Formula(String formula) : this(formula, s => s, s => true)
        {
        }

        /// <summary>
        /// Creates a Formula from a string that consists of an infix expression written as
        /// described in the class comment.  If the expression is syntactically incorrect,
        /// throws a FormulaFormatException with an explanatory Message.
        /// 
        /// The associated normalizer and validator are the second and third parameters,
        /// respectively.  
        /// 
        /// If the formula contains a variable v such that normalize(v) is not a legal variable, 
        /// throws a FormulaFormatException with an explanatory message. 
        /// 
        /// If the formula contains a variable v such that isValid(normalize(v)) is false,
        /// throws a FormulaFormatException with an explanatory message.
        /// 
        /// Suppose that N is a method that converts all the letters in a string to upper case, and
        /// that V is a method that returns true only if a string consists of one letter followed
        /// by one digit.  Then:
        /// 
        /// new Formula("x2+y3", N, V) should succeed
        /// new Formula("x+y3", N, V) should throw an exception, since V(N("x")) is false
        /// new Formula("2x+y3", N, V) should throw an exception, since "2x+y3" is syntactically incorrect.
        /// </summary>
        public Formula(String formula, Func<string, string> normalize, Func<string, bool> isValid)
        {
            // Ensure that the input string is not null.
            if (formula == null)
            {
                throw new FormulaFormatException("Null is not a valid formula expression.");
            }

            // Create a HashSet to hold the normalized variables in.
            normalizedVars = new HashSet<String>();

            // Create a List to hold all the expression tokens in.
            expression = new List<String>(GetTokens(formula));

            // Ensure that there is atleast one token in the expression, if not throw an exception.
            if (expression.Count() < 1)
            {
                throw new FormulaFormatException("There needs to be at least one token in the expression.");
            }

            // Keep tract of the number of open and closed parenthesis and the index of the expression tokens List.
            int openParan = 0, closedParan = 0, i = 0;

            // Create a variable to hold the parsed value
            double number = 0;

            // Set the token value to the first item in the expression.
            String token = expression[i];

            // Ensure that the first item in the expression is a variable, a ( or a number. If not throw an exception.
            if (!(IsVariable(token) || token.Equals("(") || Double.TryParse(token, out number)))
            {
                throw new FormulaFormatException("The first token must be a variable, a opening parenthesis, or a number.");
            }

            // Set the token value to the last item in the expression.
            token = expression[expression.Count - 1];

            // Ensure that the last item in the expression is a variable, a ) or a number. If not throw an exception.
            if (!(IsVariable(token) || token.Equals(")") || Double.TryParse(token, out number)))
            {
                throw new FormulaFormatException("The last token must be a variable, a closing parenthesis, or a number.");
            }

            // Began to loop through the expression to ensure that it is syntatically correct.
            for (i = 0; i < expression.Count(); i++)
            {
                // Set the token to the current index of the expression.
                token = expression[i];

                // If token is equal to ( increment the openParan variable.
                if (token.Equals("("))
                {
                    // Ensure that we are not at the end of the List
                    if (i < expression.Count() - 1)
                    {
                        // If the value preceeding the open parenthesis is not a varaible, value or a (, throw an exception.
                        if (!(IsVariable(expression[i + 1]) || expression[i + 1].Equals("(") || Double.TryParse(expression[i + 1], out number)))
                        {
                            throw new FormulaFormatException("The token preceeding a opening parenthesis must be either a variable, a opening parenthesis, or a number.");
                        }
                    }
                    // Increment the number of open parenthesis.
                    openParan++;
                }
                // If the token is a ) increment the closedParan variable.
                else if (token.Equals(")"))
                {
                    // Increment the number of closed parenthesis.
                    closedParan++;

                    // If the number of closed parenthesis exceeds the number of open parenthesis, throw an exception.
                    if (closedParan > openParan)
                    {
                        throw new FormulaFormatException("The number of closed parenthesis exceeded the number of opening parenthesis.");
                    }
                    // Ensure that we are not at the end of the List
                    if (i < expression.Count() - 1)
                    {
                        // If the value preceeding the closed parenthesis is not a operator or a ), throw an exception.
                        if (!(expression[i + 1].Equals("+") || expression[i + 1].Equals("-") || expression[i + 1].Equals("*") || expression[i + 1].Equals("/") || expression[i + 1].Equals(")")))
                        {
                            throw new FormulaFormatException("The token preceeding a closing parenthesis must be either a operator or a closing parenthesis.");
                        }
                    }
                }
                else if (token.Equals("+") || token.Equals("-") || token.Equals("*") || token.Equals("/"))
                {
                    // Ensure that we are not at the end of the List
                    if (i < expression.Count() - 1)
                    {
                        // If the value preceeding the open parenthesis is not a varaible, value or a (, throw an exception.
                        if (!(IsVariable(expression[i + 1]) || expression[i + 1].Equals("(") || Double.TryParse(expression[i + 1], out number)))
                        {
                            throw new FormulaFormatException("The token preceeding a operator must be either a variable, a opening parenthesis, or a number.");
                        }
                    }
                }
                else if (Double.TryParse(token, out number))
                {
                    // Ensure that we are not at the end of the List
                    if (i < expression.Count() - 1)
                    {
                        // If the value preceeding the value is not a operator or a ), throw an exception.
                        if (!(expression[i + 1].Equals("+") || expression[i + 1].Equals("-") || expression[i + 1].Equals("*") || expression[i + 1].Equals("/") || expression[i + 1].Equals(")")))
                        {
                            throw new FormulaFormatException("The token preceeding a number must be either a operator or a closing parenthesis.");
                        }
                    }
                    // Add the number back into the expression token list.
                    expression[i] = number + "";
                }
                else if (IsVariable(token))
                {
                    // Ensure that the normalized token is a variable.
                    if (!IsVariable(normalize(token)))
                    {
                        throw new FormulaFormatException("The normalized token does not meet the requirements for what a variable is.");
                    }
                    // Ensure that we are not at the end of the List
                    if (i < expression.Count() - 1)
                    {
                        // If the value preceeding the variable is not a operator or a ), throw an exception.
                        if (!(expression[i + 1].Equals("+") || expression[i + 1].Equals("-") || expression[i + 1].Equals("*") || expression[i + 1].Equals("/") || expression[i + 1].Equals(")")))
                        {
                            throw new FormulaFormatException("The token preceeding a variable must be either a operator or a closing parenthesis.");
                        }
                    }
                    // Ensure that the token is valid after it has been normalized, if not throw an exception.
                    if (!isValid(normalize(token)))
                    {
                        throw new FormulaFormatException("The variable is not in a valid format defined by the given specifciations.");
                    }

                    else
                    {
                        // Add the normalized variable to the expressions token list.
                        expression[i] = normalize(token);

                        // Add the normalized value to a HashSet of normalized variables.
                        normalizedVars.Add(expression[i]);
                    }
                }

            }
            // Ensure that the number of open parenthesis are equal to the closed parenthesis.
            if (openParan != closedParan)
            {
                throw new FormulaFormatException("The number of parenthesis aren't balanced.");
            }
        }

        /// <summary>
        /// Evaluates this Formula, using the lookup delegate to determine the values of
        /// variables.  When a variable symbol v needs to be determined, it should be looked up
        /// via lookup(normalize(v)). (Here, normalize is the normalizer that was passed to 
        /// the constructor.)
        /// 
        /// For example, if L("x") is 2, L("X") is 4, and N is a method that converts all the letters 
        /// in a string to upper case:
        /// 
        /// new Formula("x+7", N, s => true).Evaluate(L) is 11
        /// new Formula("x+7").Evaluate(L) is 9
        /// 
        /// Given a variable symbol as its parameter, lookup returns the variable's value 
        /// (if it has one) or throws an ArgumentException (otherwise).
        /// 
        /// If no undefined variables or divisions by zero are encountered when evaluating 
        /// this Formula, the value is returned.  Otherwise, a FormulaError is returned.  
        /// The Reason property of the FormulaError should have a meaningful explanation.
        ///
        /// This method should never throw an exception.
        /// </summary>
        public object Evaluate(Func<string, double> lookup)
        {
            // These stacks represent the bins where the valeus and operators go from the given expression.
            Stack<double> valueStack = new Stack<double>();

            Stack<char> operatorStack = new Stack<char>();

            // Use a Regex command to split the given  expression into an array of strings.
            String[] substrings = expression.ToArray();

            // This for loop searches through the string array
            for (int i = 0; i < substrings.Length; i++)
            {
                // This variable to to hold the values found in the string array after they are parsed
                double valFromEvalString;

                // Trim any excess whitespace
                String stringToEval = substrings[i].Trim();

                // If the string is a integer
                if (double.TryParse(stringToEval, out valFromEvalString))
                {
                    // Check and see if a * or / is on top of the operator stack.
                    if (hasOnTop(operatorStack, '*', '/'))
                    {

                        // Pop the top value from the valueStack
                        double poppedVal = valueStack.Pop();

                        // Pop the top symbol from the operatorStack
                        char poppedSymbol = operatorStack.Pop();

                        // If the val that was just parsed is zero and the symbol is /, throw an excpetion to avoid divide by zero.
                        if (valFromEvalString == 0 && poppedSymbol.Equals('/'))
                        {
                            return new FormulaError("Divide by zero error, universe almost ended but I saved you. Your welcome. :)");
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
                    if (hasOnTop(operatorStack, '+', '-'))
                    {
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
                    if (hasOnTop(operatorStack, '+', '-'))
                    {
                        // Pop the two values and a symbol
                        poppedVal1 = valueStack.Pop();
                        poppedVal2 = valueStack.Pop();

                        poppedExpression = operatorStack.Pop();

                        // Call the performMath function to get the new resulting value and push it on the valueStack.
                        valueStack.Push(performMath(poppedVal2, poppedVal1, poppedExpression));
                    }

                    // Remove the ) from the top of the operatorStack
                    operatorStack.Pop();

                    // Check and see if the operatorStack now has a * or / on top
                    if (hasOnTop(operatorStack, '*', '/'))
                    {

                        // Pop the two values and a symbol
                        poppedVal1 = valueStack.Pop();
                        poppedVal2 = valueStack.Pop();

                        poppedExpression = operatorStack.Pop();

                        // Ensure that if the second popped value is a 0 and the symbol is /, that an exception is thrown to avoid dividing by zero.
                        if (poppedVal1 == 0 && poppedExpression.Equals('/'))
                        {
                            return new FormulaError("Divide by zero error, universe almost ended but I saved you. Your welcome. :)");
                        }

                        // Call the performMath function to get the new resulting value and push it on the valueStack.
                        valueStack.Push(performMath(poppedVal2, poppedVal1, poppedExpression));
                    }

                }
                else
                {
                    double decodedVal = 0;

                    try
                    {
                        // Call the variableEvaluator delegate to find the function that defines what the values are for the variables.
                        decodedVal = lookup(stringToEval);
                    }
                    catch (ArgumentException)
                    {
                        return new FormulaError("No value associated with variable.");
                    }

                    

                    // Check and see if a * or / is on top of the operator stack.
                    if (hasOnTop(operatorStack, '*', '/'))
                    {

                        // Pop the top value from the valueStack
                        double poppedVal = valueStack.Pop();

                        // Pop the top symbol from the operatorStack
                        char poppedSymbol = operatorStack.Pop();

                        // If the val that was just parsed is zero and the symbol is /, throw an excpetion to avoid divide by zero.
                        if (decodedVal == 0 && poppedSymbol.Equals('/'))
                        {
                            return new FormulaError("Divide by zero error, universe almost ended but I saved you. Your welcome. :)");
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
            double finalAnswer = 0;

            // Check and see if the operatorStack is empty
            if (operatorStack.Count() == 0)
            {

                // Set the finalAnswer to the only value in the valueStack.
                finalAnswer = valueStack.Pop();
            }
            //If the operatorStack is not empty
            else
            {
                // Check and see if the operator stack has a + or - left in it, if not throw an exception.
                if (hasOnTop(operatorStack, '+', '-'))
                {

                    // Pop the two values and a symbol
                    double poppedVal1 = valueStack.Pop();
                    double poppedVal2 = valueStack.Pop();

                    char poppedExpression = operatorStack.Pop();

                    // Call perfromMath function to get the new value and store it in finalAnswer
                    finalAnswer = performMath(poppedVal2, poppedVal1, poppedExpression);
                }
            }

            // Return final answer to caller.
            return finalAnswer;
        }

        /// <summary>
        /// Enumerates the normalized versions of all of the variables that occur in this 
        /// formula.  No normalization may appear more than once in the enumeration, even 
        /// if it appears more than once in this Formula.
        /// 
        /// For example, if N is a method that converts all the letters in a string to upper case:
        /// 
        /// new Formula("x+y*z", N, s => true).GetVariables() should enumerate "X", "Y", and "Z"
        /// new Formula("x+X*z", N, s => true).GetVariables() should enumerate "X" and "Z".
        /// new Formula("x+X*z").GetVariables() should enumerate "x", "X", and "z".
        /// </summary>
        public IEnumerable<String> GetVariables()
        {

            //for(int i = 0; i < normalizedVars.Count; i++)
            //{
            //    yield return normalizedVars.ElementAt(i);
            //}

            // Search through each variable and return each one 
            foreach (String variable in normalizedVars)
            {
                yield return variable;
            }

            // return new HashSet<String>(normalizedVars);
        }

        /// <summary>
        /// Returns a string containing no spaces which, if passed to the Formula
        /// constructor, will produce a Formula f such that this.Equals(f).  All of the
        /// variables in the string should be normalized.
        /// 
        /// For example, if N is a method that converts all the letters in a string to upper case:
        /// 
        /// new Formula("x + y", N, s => true).ToString() should return "X+Y"
        /// new Formula("x + Y").ToString() should return "x+Y"
        /// </summary>
        public override string ToString()
        {
            // Make a string to hold the formula.
            String formula = "";

            // Loop through and pull each token out of the expression and add it to the string.
            foreach (String token in expression)
            {
                formula += token;
            }
            // Return the string containing the formula.
            return formula;
        }

        /// <summary>
        /// If obj is null or obj is not a Formula, returns false.  Otherwise, reports
        /// whether or not this Formula and obj are equal.
        /// 
        /// Two Formulae are considered equal if they consist of the same tokens in the
        /// same order.  To determine token equality, all tokens are compared as strings 
        /// except for numeric tokens, which are compared as doubles, and variable tokens,
        /// whose normalized forms are compared as strings.
        /// 
        /// For example, if N is a method that converts all the letters in a string to upper case:
        ///  
        /// new Formula("x1+y2", N, s => true).Equals(new Formula("X1  +  Y2")) is true
        /// new Formula("x1+y2").Equals(new Formula("X1+Y2")) is false
        /// new Formula("x1+y2").Equals(new Formula("y2+x1")) is false
        /// new Formula("2.0 + x7").Equals(new Formula("2.000 + x7")) is true
        /// </summary>
        public override bool Equals(object obj)
        {
            // Make two variables to be able to check the values of the doubles found in the expression.
            double this_number = 0, obj_number = 0;

            // If the object brought in is null or is not of the same type return false.
            if (ReferenceEquals(obj, null) || obj.GetType() != this.GetType())
            {
                return false;
            }

            // Cast the object to a Formula object.
            Formula formula = (Formula)obj;

            // Ensure they contain the same amount of tokens.
            if (this.expression.Count != formula.expression.Count)
            {
                return false;
            }

            // Loop through the expression list of tokens
            for (int i = 0; i < this.expression.Count(); i++)
            {
                // Pull each token at each index and put it into a string so they can be compared.
                String this_token = this.expression[i];

                String obj_token = formula.expression[i];

                // Try and parse the values to doubles.
                if (Double.TryParse(this_token, out this_number) && Double.TryParse(obj_token, out obj_number))
                {
                    // If the two values aren't equal return false.
                    if (this_number != obj_number)
                    {
                        return false;
                    }
                }
                else
                {
                    // If the strings aren't equal return false.
                    if (!(this_token.Equals(obj_token)))
                    {
                        return false;
                    }
                }
            }
            // Return true if they are equal.
            return true;
        }

        /// <summary>
        /// Reports whether f1 == f2, using the notion of equality from the Equals method.
        /// Note that if both f1 and f2 are null, this method should return true.  If one is
        /// null and one is not, this method should return false.
        /// </summary>
        public static bool operator ==(Formula f1, Formula f2)
        {
            // If both objects are null, return true.
            if (ReferenceEquals(f1, null) && ReferenceEquals(f2, null))
            {
                return true;
            }

            // If either object is null, return false.
            if (ReferenceEquals(f1, null) || ReferenceEquals(f2, null))
            {
                return false;
            }
            // Call the Equals method to ensure equality.
            return f1.Equals(f2);
        }

        /// <summary>
        /// Reports whether f1 != f2, using the notion of equality from the Equals method.
        /// Note that if both f1 and f2 are null, this method should return false.  If one is
        /// null and one is not, this method should return true.
        /// </summary>
        public static bool operator !=(Formula f1, Formula f2)
        {
            // If both objects are null, return false.
            if (ReferenceEquals(f1, null) && ReferenceEquals(f2, null))
            {
                return false;
            }
            // If either object is null, return true.
            if (ReferenceEquals(f1, null) || ReferenceEquals(f2, null))
            {
                return true;
            }
            // Not the return value of Equals to achieve the desire != answer.
            return !(f1.Equals(f2));
        }

        /// <summary>
        /// Returns a hash code for this Formula.  If f1.Equals(f2), then it must be the
        /// case that f1.GetHashCode() == f2.GetHashCode().  Ideally, the probability that two 
        /// randomly-generated unequal Formulae have the same hash code should be extremely small.
        /// </summary>
        public override int GetHashCode()
        {
            // On the object this method is being invoked on, call its ToString method and return the HashCode of that string.
            return this.ToString().GetHashCode();
        }

        /// <summary>
        /// Given an expression, enumerates the tokens that compose it.  Tokens are left paren;
        /// right paren; one of the four operator symbols; a string consisting of a letter or underscore
        /// followed by zero or more letters, digits, or underscores; a double literal; and anything that doesn't
        /// match one of those patterns.  There are no empty tokens, and no token contains white space.
        /// </summary>
        private static IEnumerable<string> GetTokens(String formula)
        {
            // Patterns for individual tokens
            String lpPattern = @"\(";
            String rpPattern = @"\)";
            String opPattern = @"[\+\-*/]";
            String varPattern = @"[a-zA-Z_](?: [a-zA-Z_]|\d)*";
            String doublePattern = @"(?: \d+\.\d* | \d*\.\d+ | \d+ ) (?: [eE][\+-]?\d+)?";
            String spacePattern = @"\s+";

            // Overall pattern
            String pattern = String.Format("({0}) | ({1}) | ({2}) | ({3}) | ({4}) | ({5})",
                                            lpPattern, rpPattern, opPattern, varPattern, doublePattern, spacePattern);

            // Enumerate matching tokens that don't consist solely of white space.
            foreach (String s in Regex.Split(formula, pattern, RegexOptions.IgnorePatternWhitespace))
            {
                if (!Regex.IsMatch(s, @"^\s*$", RegexOptions.Singleline))
                {
                    yield return s;
                }
            }

        }

        /// <summary>
        /// This method serves as a validator to ensure that the variables that are typed into the formula expression are valid. A variable can start with 
        /// a underscore and anycase letter. It can end with a underscore, a number, and anycase letter.
        /// </summary>
        /// <param name="token">The string of the token you wish to see is a valid variable name.</param>
        /// <returns>A boolean stating whether or not the inputed string is a valid variable name.</returns>
        private Boolean IsVariable(String token)
        {
            // Create a new Regex that meets the new requirments for variables.
            Regex varReqs = new Regex(@"(^([_A-Za-z]+[_A-Za-z0-9]*)$)");

            // See if the inputed string matches those requirements, if yes return true.
            if (varReqs.IsMatch(token))
            {
                return true;
            }
            //If not return false.
            return false;
        }


        /// <summary>
        /// This method serves as a helper method for the Evaluate function. It looks at the top of the operator stack and sees if two different symbols are or are not on top of the stack.
        /// It also checks to see if the stack has at least one value as well, if not it returns false.
        /// </summary>
        /// <param name="stack">This stack is the operatorStack from the Evaluate method, we bring it in to check and see what is on top of it.</param>
        /// <param name="firstChar">The first char symbol we wish to see if it is on top of the stack.</param>
        /// <param name="secChar">The second char symbol we wish to see if it is on top of the stack.</param>
        /// <returns>True if either char symbol is on top of the stack or false if the stack is empty or doesn't have either symbol on top.</returns>
        private Boolean hasOnTop(Stack<char> stack, char firstChar, char secChar)
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
        /// symbol performs the correct mathmatical expression to the two input values.
        /// </summary>
        /// <param name="poppedVal1">A double value brought in from the Evaluator class that needs to have an mathmatical expression performed on it.</param>
        /// <param name="poppedVal2">A double value brought in from the Evaluator class that needs to have an mathmatical expression performed on it.</param>
        /// <param name="symbol">The char symbol of which mathmatical symbol needs to performed on the values.</param>
        /// <returns>A double value of the result of the mathmatical expression that was performed.</returns>
        private double performMath(double poppedVal1, double poppedVal2, char symbol)
        {
            // Declare a variable to store the answer into.
            double answer = 0;

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
            }

            // Return the answer
            return answer;
        }





    }

    /// <summary>
    /// Used to report syntactic errors in the argument to the Formula constructor.
    /// </summary>
    public class FormulaFormatException : Exception
    {
        /// <summary>
        /// Constructs a FormulaFormatException containing the explanatory message.
        /// </summary>
        public FormulaFormatException(String message)
            : base(message)
        {
        }
    }

    /// <summary>
    /// Used as a possible return value of the Formula.Evaluate method.
    /// </summary>
    public struct FormulaError
    {
        /// <summary>
        /// Constructs a FormulaError containing the explanatory reason.
        /// </summary>
        /// <param name="reason"></param>
        public FormulaError(String reason)
            : this()
        {
            Reason = reason;
        }

        /// <summary>
        ///  The reason why this FormulaError was created.
        /// </summary>
        public string Reason { get; private set; }
    }
}