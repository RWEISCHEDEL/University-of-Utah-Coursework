using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SpreadsheetUtilities;
using System.Text.RegularExpressions;

/// <summary>
/// Robert Weischedel CS 3500 10/1/15
/// </summary>
namespace SS
{
    /// <summary>
    /// This class implements from AbstractSpreadsheet. Its purpose is to create the 
    /// </summary>
    public class Spreadsheet : AbstractSpreadsheet
    {
        // Contains the DependencyGraph object in order to keep track of all the dependees and dependents of the various cells.
        private DependencyGraph dependGraph;

        // Contains a HashTable filled with cells that have been filled with values and contents.
        private Dictionary<String, Cell> filledCells;

        /// <summary>
        /// This serves as the constructor for the class and creates a new Spreadsheet object.
        /// </summary>
        public Spreadsheet()
        {
            // Create a new DependencyGraph object.
            dependGraph = new DependencyGraph();

            // Create a new HashTable Dictionary to place the filled cells in.
            filledCells = new Dictionary<String, Cell>();
        }

        /// <summary>
        /// This method takes in a string name of a cell, first it verfies the string name is a valid name for a cell and is not equal to null.
        /// Then the method tries to get the content value out of the cell. There are only four types of content values that can be returned.
        /// They are 1. A String, 2. A Double, 3. A Formula 4. A empty String
        /// </summary>
        /// <param name="name">A String contianing the name of the cell you wish to retrieve its contents of.</param>
        /// <returns>This method returns either a String, a double or a Formula object. Or a empty String.</returns>
        public override object GetCellContents(string name)
        {
            // Check if the input String name is equal to null or is not a valid variable name, if it is not, throw a InvalidNameException.
            if (ReferenceEquals(name, null) || !IsVariable(name))
            {
                throw new InvalidNameException();
            }

            // Create a new cell object.
            Cell cell;

            // Try and retrieve the cell object with the given name. If it exists, return its contents.
            if (filledCells.TryGetValue(name, out cell))
            {
                return cell.cellContents;
            }

            // If the cell with that particular name doesn't exist, return a empty string signifying that the cell is empty. 
            return "";
        }

        /// <summary>
        /// This method returns all the names of the cells that are currently filled with some value.
        /// </summary>
        /// <returns>A List of all the filled cells.</returns>
        public override IEnumerable<string> GetNamesOfAllNonemptyCells()
        {
            // Return all the keys or names from the filledCells Dictionary.
            return filledCells.Keys;
        }

        /// <summary>
        /// This method sets the contents of cell if a formula is entered into the cell. It first checks the inputs and ensures that the String
        /// name is not null and is a valid variable name. Then it ensures that the formula object is not set to null. After that, it updates
        /// the dependecies of the cell and also adds the cell to the filled cells Dictionary or overwrites the value if that cell is already filled.
        /// It also checks if the new entered formula would result in a circular dependancy. If it does, then the cell object with the matching 
        /// String name is not changed. It then returns a ISet of the cells including the cell itself that depend on that cell directly or 
        /// indirectly.
        /// </summary>
        /// <param name="name">The name of the cell we wish to add or update.</param>
        /// <param name="formula">The formula that we wish to add to the given cell.</param>
        /// <returns>A HashSet of cells that depend on the cell directly or indirectly.</returns>
        public override ISet<string> SetCellContents(string name, Formula formula)
        {
            // If the given input String name is null or not a valid variable name, throw a InvalidNameException.
            if (ReferenceEquals(name, null) || !IsVariable(name))
            {
                throw new InvalidNameException();
            }

            // If the given input formula object is set to null, throw a ArgumentNullException.
            if (ReferenceEquals(formula, null))
            {
                throw new ArgumentNullException();
            }

            // Replace the dependent values associated with name with those in the newly inputed formula.
            dependGraph.ReplaceDependees(name, formula.GetVariables());

            // Try and see if the formula that is about to be put into the cell will result in a Circular Dependancy.
            try
            {
                dependGraph.ReplaceDependees(name, formula.GetVariables());

                // Retrieve all the dependees assocaited with the input String name and see if it results in a circular dependancy.
                // If it doesn't store the dependee values.
                HashSet<String> newdependeesOfCell = new HashSet<String>(GetCellsToRecalculate(name));

                // Create a new cell and put the inputed formula object into it.
                Cell newCell = new Cell(formula);

                // If a cell doesn't exist with that given name add it Dictionary.
                if (!filledCells.ContainsKey(name))
                {
                    // Add the newly created cell with the given name to the Dictionary.
                    filledCells.Add(name, newCell);
                }
                else
                {
                    // If a cell with that name already exists overwrite its value.
                    filledCells[name] = newCell; 
                }

                // Return the List of dependee values.
                return newdependeesOfCell;
            }
            // If the formula that was about to be put in the cell results in a Circular Dependancy, revert the cell to its orginal data.
            catch(CircularException)
            {
                // Throw the appropriate exception.
                throw new CircularException();
            }

        }

        /// <summary>
        /// This method sets the contents of a cell in the case of a String is entered into a cell. First this method ensures that the input String 
        /// name of the cell is not null and is a valid variable name. It also ensures that the input String text is not set to null. After that 
        /// the method creates a new cell with the given name and fills it with the text value. If the cell with the given name already exisits then
        /// it updates the value. It then returns a ISet of the cells inclduing the cell itself that depend on that cell directly or indirectly.
        /// </summary>
        /// <param name="name">The name of the cell we wish to add or update.</param>
        /// <param name="text">The String value we wish to put into a cell.</param>
        /// <returns>A ISet of cells that depend on the cell directly or indirectly.</returns>
        public override ISet<string> SetCellContents(string name, string text)
        {
            // If the given input String name is null or not a valid variable name, throw a InvalidNameException.
            if (ReferenceEquals(name, null) || !IsVariable(name))
            {
                throw new InvalidNameException();
            }

            // If the given input String text is null, throw a ArgumentNullException.
            if (ReferenceEquals(text, null))
            {
                throw new ArgumentNullException();
            }

            // Create a new cell and put the String text into it.
            Cell newCell = new Cell(text);

            // If a cell doesn't exist with that given name add it Dictionary.
            if (!filledCells.ContainsKey(name))
            {
                // Add the newly created cell with the given name to the Dictionary.
                filledCells.Add(name, newCell);
            }
            else
            {
                // If a cell with that name already exists overwrite its value.
                filledCells[name] = newCell;
            }

            // If the contents of the cell now equals empty string, treat it as a empty cell and remove it.
            if (filledCells[name].cellContents.Equals(""))
            {
                // Remove the empty cell from the filled cells Dictionary.
                filledCells.Remove(name);
            }

            // Replace all dependees of the cell with a empty HashSet, since the cell only contains a string it has no dependees.
            dependGraph.ReplaceDependees(name, new HashSet<String>());

            // Return a HashSet of cells that depend on this cell.
            return new HashSet<String>(GetCellsToRecalculate(name));
        }

        /// <summary>
        /// This method sets the contents of a cell in the case of a double is entered into a cell. First this method ensures that the input String
        /// name of the cell is not null and is a valid variable name. After that the method creates a new cell with the given name and fills it 
        /// with the double value. If the cell with the given name already exists then it updates the value. It then returns a ISet of the cells 
        /// inclduing the cell itself that depend on that cell directly or indirectly.
        /// </summary>
        /// <param name="name">The name of the cell we wish to add or update</param>
        /// <param name="number">The double value we wish to put into a cell.</param>
        /// <returns>A ISet of cells that depend on the cell directly or indirectly</returns>
        public override ISet<string> SetCellContents(string name, double number)
        {
            // If the given input String name is null or not a valid variable name, throw a InvalidNameException.
            if (ReferenceEquals(name, null) || !IsVariable(name))
            {
                throw new InvalidNameException();
            }

            // Create a new cell and put the double number into it.
            Cell newCell = new Cell(number);

            // If a cell doesn't exist with that given name add it Dictionary.
            if (!filledCells.ContainsKey(name))
            {
                // Add the newly created cell with the given name to the Dictionary.
                filledCells.Add(name, newCell);
            }
            else
            {
                // If a cell with that name already exists overwrite its value.
                filledCells[name] = newCell;
            }

            // Replace all dependees of the cell with a empty HashSet, since the cell only contains a string it has no dependees.
            dependGraph.ReplaceDependees(name, new HashSet<String>());

            // Return a HashSet of cells that depend on this cell.
            return new HashSet<String>(GetCellsToRecalculate(name));
        }

        /// <summary>
        /// This method returns a List of all the DirectDependents that are associated with the given cell name. It does this by first ensuring that
        /// the string name is not null and is a valid variable name. Then it uses the DependencyGraph to retireve a list of all the dependent values
        /// associated with the inputed String names cell.
        /// </summary>
        /// <param name="name"></param>
        /// <returns></returns>
        protected override IEnumerable<string> GetDirectDependents(string name)
        {
            // If the input String name is equal to null, throw a throw new ArgumentNullException.
            if (ReferenceEquals(name, null))
            {
                throw new ArgumentNullException();
            }

            // If the input String name is not a valid variable name, throw a throw new InvalidNameException.
            if (!IsVariable(name))
            {
                throw new InvalidNameException();
            }

            // Call the DependencyGraph object and retrieve and return all the dependent values assocaited with the input Sting name.
            return dependGraph.GetDependents(name);
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
        /// This is a private class that is used to create a all the cells in the spreadsheet.
        /// </summary>
        private class Cell
        {
            /// <summary>
            /// This method acts as the getter and setter methods for the cell contents for all the different types of cells.
            /// </summary>
            public Object cellContents { get; private set; }

            /// <summary>
            /// This method acts as the getter and setter methods for the cell value for all the different types of cells.
            /// </summary>
            public Object cellValue { get; private set; }

            /// <summary>
            /// This constructor serves to create a cell object that contains a string.
            /// </summary>
            /// <param name="contents">A String containing the value to be stored in the cell.</param>
            public Cell(String contents)
            {
                // In the case of a String both the cellContents and cellValue is the same.
                cellContents = cellValue = contents;
            }

            /// <summary>
            /// This constructor serves to create a cell object that contains a double.
            /// </summary>
            /// <param name="contents">A double containing the value to be stored in the cell.</param>
            public Cell(Double contents)
            {
                // In the case of a double both the cellContents and cellValue is the same.
                cellContents = cellValue = contents;
            }

            /// <summary>
            /// This constructor serves to create a cell object that contains a Formula and its value.
            /// </summary>
            /// <param name="contents">A Formula which it and its potential value to be stored in the cell.</param>
            public Cell(Formula contents)
            {
                // Store the formula object as the cells contents.
                cellContents = contents;

                // Store the double value or the Formula Error that might result into the cells value.
                cellValue = contents.Evaluate(x => 1);
            }
        }
    }

}
