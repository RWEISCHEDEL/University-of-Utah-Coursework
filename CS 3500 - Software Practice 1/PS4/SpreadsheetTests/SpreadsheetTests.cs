using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using SS;
using SpreadsheetUtilities;
using System.Collections.Generic;

/// <summary>
/// Robert Weischedel CS 3500 10/1/15
/// </summary>
namespace SpreadsheetTests
{
    /// <summary>
    /// This class acts as a testing suite for the Spreadsheet class. It overs a wide range of methods to test the Spreadsheet class.
    /// </summary>
    [TestClass]
    public class SpreadsheetTests
    {
        // The following set of tests are for testing the TestGetCellContents method.

        /// <summary>
        /// This tests ensures that GetCellContents throws InvalidNameException if the cell name you wish to get the contents of is null.
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestGetCellContentsExceptions1()
        {
            Spreadsheet sheet = new Spreadsheet();
            String contents = "cs3500";
            sheet.SetCellContents("A1", contents);
            sheet.GetCellContents(null);
        }

        /// <summary>
        /// This tests ensures that GetCellContents throws InvalidNameException if the cell name you wish to get the contents of is not a valid variable name.
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestGetCellContentsExceptions2()
        {
            Spreadsheet sheet = new Spreadsheet();
            String contents = "cs3500";
            sheet.SetCellContents("A1", contents);
            sheet.GetCellContents("1A");
        }

        /// <summary>
        /// This test ensures that when you try and retrieve a cell that has String contents that it will return that String.
        /// </summary>
        [TestMethod]
        public void TestGetCellContents1()
        {
            Spreadsheet sheet = new Spreadsheet();
            String contents = "cs3500";
            sheet.SetCellContents("A1", contents);
            Assert.AreEqual(sheet.GetCellContents("A1"), "cs3500");
        }

        /// <summary>
        /// This test ensures that when you try and retrieve a cell that has double value contents that it will return that double value.
        /// </summary>
        [TestMethod]
        public void TestGetCellContents2()
        {
            Spreadsheet sheet = new Spreadsheet();
            Double contents = 2.5;
            sheet.SetCellContents("A1", contents);
            Assert.AreEqual(sheet.GetCellContents("A1"), 2.5);
        }

        /// <summary>
        /// This test ensures that when you try and retrieve a cell that has formula contents that it will return that formula.
        /// </summary>
        [TestMethod]
        public void TestGetCellContents3()
        {
            Spreadsheet sheet = new Spreadsheet();
            Formula f1 = new Formula("1+1");
            sheet.SetCellContents("A1", f1);
            Assert.AreEqual(sheet.GetCellContents("A1"), f1);
        }

        /// <summary>
        /// This test ensures that when you try and retrieve a cell that has no contents or is empty, a empty string is returned.
        /// </summary>
        [TestMethod]
        public void TestGetCellContents4()
        {
            Spreadsheet sheet = new Spreadsheet();
            Formula f1 = new Formula("1+1");
            sheet.SetCellContents("A1", f1);
            Assert.AreEqual(sheet.GetCellContents("A2"), "");
        }

        // The following set of tests are for testing the GetNamesOfAllNonemptyCells method.

        /// <summary>
        /// This test ensure that when one cell is created that it can be retrieved when a list of full cells is returned.
        /// </summary>
        [TestMethod]
        public void GetNamesOfAllNonemptyCells1()
        {
            Spreadsheet sheet = new Spreadsheet();
            Formula f1 = new Formula("1+1");
            sheet.SetCellContents("A1", f1);

            HashSet<String> testKeys1 = new HashSet<String>(sheet.GetNamesOfAllNonemptyCells());
            HashSet<String> testKeys2 = new HashSet<String>();
            testKeys2.Add("A1");

            Assert.IsTrue(testKeys1.Count == 1 && testKeys2.Count == 1 && testKeys1.Contains("A1") && testKeys2.Contains("A1"));
        }

        /// <summary>
        /// This test ensure that when multiple cells are created that they can be retrieved when a list of full cells is returned.
        /// </summary>
        [TestMethod]
        public void GetNamesOfAllNonemptyCells2()
        {
            Spreadsheet sheet = new Spreadsheet();
            Formula f1 = new Formula("1+1");
            sheet.SetCellContents("A1", f1);
            sheet.SetCellContents("B1", "cs3500");
            sheet.SetCellContents("C1", 1.5);

            HashSet<String> testKeys1 = new HashSet<String>(sheet.GetNamesOfAllNonemptyCells());
            HashSet<String> testKeys2 = new HashSet<String>();
            testKeys2.Add("A1");
            testKeys2.Add("B1");
            testKeys2.Add("C1");

            Assert.IsTrue(testKeys1.Count == 3 && testKeys2.Count == 3 && testKeys1.Contains("A1") && testKeys1.Contains("B1") && testKeys1.Contains("C1") && testKeys2.Contains("A1") && testKeys2.Contains("B1") && testKeys2.Contains("C1"));
        }

        /// <summary>
        /// This test ensures that when no cells are created that the list of filled cells will return empty.
        /// </summary>
        [TestMethod]
        public void GetNamesOfAllNonemptyCells3()
        {
            Spreadsheet sheet = new Spreadsheet();

            HashSet<String> testKeys1 = new HashSet<String>(sheet.GetNamesOfAllNonemptyCells());

            Assert.IsTrue(testKeys1.Count == 0);
        }

        // The following set of tests are for the SetCellContents(String, Formula) method.

        /// <summary>
        /// This test ensures that when null is entered as a possible name for the cell a exception is thrown.
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestSetCellContentsSFExceptions1()
        {
            Spreadsheet sheet = new Spreadsheet();
            Formula f1 = new Formula("1+1");
            sheet.SetCellContents(null, f1);

        }

        /// <summary>
        /// This test ensures that if a invalid variable name is entered for the cell name a exception is thrown.
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestSetCellContentsSFExceptions2()
        {
            Spreadsheet sheet = new Spreadsheet();
            Formula f1 = new Formula("1+1");
            sheet.SetCellContents("1A", f1);

        }

        /// <summary>
        /// This test ensures that if null is passed in for the formula that a exception is thrown.
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(ArgumentNullException))]
        public void TestSetCellContentsSFExceptions3()
        {
            Spreadsheet sheet = new Spreadsheet();
            Formula f1 = null;
            sheet.SetCellContents("A1", f1);

        }

        /// <summary>
        /// This test ensures that if a circular dependancy happens on a small scale, that an exception is thrown.
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(CircularException))]
        public void TestSetCellContentsSFExceptions4()
        {
            Spreadsheet sheet = new Spreadsheet();
            Formula f1 = new Formula("A1+1");
            sheet.SetCellContents("A1", f1);

        }

        /// <summary>
        /// This test ensures that if a circular dependancy happens on a larger scale, that an exception is thrown.
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(CircularException))]
        public void TestSetCellContentsSFExceptions5()
        {
            Spreadsheet sheet = new Spreadsheet();
            Formula f1 = new Formula("B1+1");
            Formula f2 = new Formula("C1+D1");
            Formula f3 = new Formula("E1+E2");
            Formula f4 = new Formula("5+A1");
            sheet.SetCellContents("A1", f1);
            sheet.SetCellContents("B1", f2);
            sheet.SetCellContents("C1", f3);
            sheet.SetCellContents("D1", f4);

        }

        /// <summary>
        /// This test ensures that if a cell already exsists that when you try to set that same cells contents to something else, it is updated to the new formula.
        /// </summary>
        [TestMethod]
        public void TestSetCellContentsSF1()
        {
            Spreadsheet sheet = new Spreadsheet();
            Formula f1 = new Formula("1+1");
            Formula f2 = new Formula("1+2");

            sheet.SetCellContents("A1", f1);

            Assert.AreEqual(sheet.GetCellContents("A1"), f1);

            sheet.SetCellContents("A1", f2);

            Assert.AreEqual(sheet.GetCellContents("A1"), f2);

        }

        /// <summary>
        /// This test ensures that the appropriate dependencies are returned when using a few simple formulas. And that even if the cells are the same except for case the program 
        /// treats them differently.
        /// </summary>
        [TestMethod]
        public void TestSetCellContentsSF2()
        {
            Spreadsheet sheet = new Spreadsheet();
            Formula f1 = new Formula("A4+A2");
            Formula f4 = new Formula("A1+1");

            sheet.SetCellContents("A1", f1);
            sheet.SetCellContents("A2", 2.0);

            Assert.IsTrue(sheet.SetCellContents("A4", 5.0).SetEquals(new HashSet<string>() { "A4", "A1" }));

            Assert.IsTrue(sheet.SetCellContents("a4", f4).SetEquals(new HashSet<string>() { "a4" }));
        }

        /// <summary>
        /// This test ensures that the appropriate dependencies are returned when using a more simple formulas.
        /// </summary>
        [TestMethod]
        public void TestSetCellContentsSF3()
        {
            Spreadsheet sheet = new Spreadsheet();
            Formula f1 = new Formula("B1+A2");
            Formula f2 = new Formula("B1+A3");
            Formula f3 = new Formula("B1+B2");

            sheet.SetCellContents("A1", f1);
            sheet.SetCellContents("B1", 6);
            sheet.SetCellContents("A2", f2);
            sheet.SetCellContents("A3", f3);
            Assert.IsTrue(sheet.SetCellContents("B2", 5.0).SetEquals(new HashSet<string>() { "B2", "A3", "A2", "A1" }));
        }

        // The following set of tests are for the SetCellContents(String, String) method. 

        /// <summary>
        /// This test ensures that when null is entered as a possible name for the cell a exception is thrown.
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestSetCellContentsSSExceptions1()
        {
            Spreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents(null, "cs3500");

        }

        /// <summary>
        /// This test ensures that if a invalid variable name is entered for the cell name a exception is thrown.
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestSetCellContentsSSExceptions2()
        {
            Spreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents("1A", "cs3500");

        }

        /// <summary>
        /// This test ensures that if null is passed in for the formula that a exception is thrown.
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(ArgumentNullException))]
        public void TestSetCellContentsSSExceptions3()
        {
            Spreadsheet sheet = new Spreadsheet();
            String input = null;
            sheet.SetCellContents("A1", input);

        }

        /// <summary>
        /// This test ensures that if a cell already exsists that when you try to set that same cells contents to something else, it is updated to the new String.
        /// </summary>
        [TestMethod]
        public void TestSetCellContentsSS1()
        {
            Spreadsheet sheet = new Spreadsheet();

            sheet.SetCellContents("A1", "cs3500");

            Assert.AreEqual(sheet.GetCellContents("A1"), "cs3500");

            sheet.SetCellContents("A1", "compsci");

            Assert.AreEqual(sheet.GetCellContents("A1"), "compsci");

        }

        /// <summary>
        /// This test ensure that if a empty string is passed into a cell, then the cell is considered empty and it is removed from the Dictionary.
        /// </summary>
        [TestMethod]
        public void TestSetCellContentsSS2()
        {
            Spreadsheet sheet = new Spreadsheet();

            Assert.AreEqual(sheet.GetCellContents("A1"), "");

            HashSet<String> test = new HashSet<String>(sheet.GetNamesOfAllNonemptyCells());

            Assert.IsTrue(test.Count == 0);
        }

        /// <summary>
        /// This test ensures that if a cell is filled with a string it is only dependent on itself. And that even if the cells are the same except for case the program treats them
        /// differently.
        /// </summary>
        [TestMethod]
        public void TestSetCellContentsSS3()
        {
            Spreadsheet sheet = new Spreadsheet();

            Assert.IsTrue(sheet.SetCellContents("A1", "cs3500").SetEquals(new HashSet<string>() { "A1" }));

            Assert.IsTrue(sheet.SetCellContents("a1", "cs3500").SetEquals(new HashSet<string>() { "a1" }));
        }

        /// <summary>
        /// This test ensures that if a cell is filled with a string it is only dependent on itself.
        /// </summary>
        [TestMethod]
        public void TestSetCellContentsSS4()
        {
            Spreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents("A1", "B1+A2");
            sheet.SetCellContents("B1", "6");
            sheet.SetCellContents("A2", "B1+A3");
            sheet.SetCellContents("A3", "B1+A2");

            Assert.IsTrue(sheet.SetCellContents("B2", "8").SetEquals(new HashSet<string>() { "B2" }));
        }

        /// <summary>
        /// This test ensure that if a empty string is passed into a cell, then the cell is considered empty and it is removed from the Dictionary.
        /// </summary>
        [TestMethod]
        public void TestSetCellContentsSS5()
        {
            Spreadsheet sheet = new Spreadsheet();

            Assert.IsTrue(sheet.SetCellContents("A1", "").SetEquals(new HashSet<string>() { "A1" }));
        }

        // The following set of tests are for the SetCellContents(String, Double) method.

        /// <summary>
        /// This test ensures that when null is entered as a possible name for the cell a exception is thrown.
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestSetCellContentsSDExceptions1()
        {
            Spreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents(null, 3.7);

        }

        /// <summary>
        /// This test ensures that if a invalid variable name is entered for the cell name a exception is thrown.
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void TestSetCellContentsSDExceptions2()
        {
            Spreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents("1A", 3.7);

        }

        /// <summary>
        /// This test ensures that if a cell already exsists that when you try to set that same cells contents to something else, it is updated to the new String.
        /// </summary>
        [TestMethod]
        public void TestSetCellContentsSD1()
        {
            Spreadsheet sheet = new Spreadsheet();

            sheet.SetCellContents("A1", 3.7);

            Assert.AreEqual(sheet.GetCellContents("A1"), 3.7);

            sheet.SetCellContents("A1", 4.0);

            Assert.AreEqual(sheet.GetCellContents("A1"), 4.0);

        }

        /// <summary>
        /// This test ensures that if a cell is filled with a string it is only dependent on itself. And that even if the cells are the same except for case the program treats them
        /// differently.
        /// </summary>
        [TestMethod]
        public void TestSetCellContentsSD2()
        {
            Spreadsheet sheet = new Spreadsheet();

            Assert.IsTrue(sheet.SetCellContents("a1", 3.7).SetEquals(new HashSet<string>() { "a1" }));

            Assert.IsTrue(sheet.SetCellContents("A1", 3.7).SetEquals(new HashSet<string>() { "A1" }));
        }

        /// <summary>
        /// This test ensures that if a cell is filled with a string it is only dependent on itself.
        /// </summary>
        [TestMethod]
        public void TestSetCellContentsSD3()
        {
            Spreadsheet sheet = new Spreadsheet();
            sheet.SetCellContents("A1", 1.0);
            sheet.SetCellContents("B1", 1.1);
            sheet.SetCellContents("A2", 1.2);
            sheet.SetCellContents("A3", 7.5);

            Assert.IsTrue(sheet.SetCellContents("B2", 5.6).SetEquals(new HashSet<string>() { "B2" }));
        }

        // The following set of tests are for the GetDirectDependents method.

        /// <summary>
        /// This test ensures that if null is passed as an input for a cell name a exception is thrown.
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(ArgumentNullException))]
        public void GetDirectDependentsExceptions1()
        {
            Spreadsheet sheet = new Spreadsheet();

            PrivateObject sheet_accessor = new PrivateObject(sheet);

            String name = null;

            sheet_accessor.Invoke("GetDirectDependents", name);
        }

        /// <summary>
        /// This test ensures that if a invalid variable name is passed as an input for a cell name a exception is thrown.
        /// </summary>
        [TestMethod]
        [ExpectedException(typeof(InvalidNameException))]
        public void GetDirectDependentsExceptions2()
        {
            Spreadsheet sheet = new Spreadsheet();

            PrivateObject sheet_accessor = new PrivateObject(sheet);

            sheet_accessor.Invoke("GetDirectDependents", "1A");
        }




    }
}
