Name: Robert Weischedel
Date: 9/28/15

Initial Design Thoughts:
My first intial thoughts was wondering how I was going to implement the SpreadsheetUtilities aka the DependancyGraph PS2 code into this assignment. It 
was quickly seen how and were the formula PS3 code would fit in, but PS2 was a mystery. I will re-read the implemenation and ask a TA about my 
confusion.  I will start working on my project by reading all the method headers and begin by making the constructor. From there I will began by 
implementing all the thrown exceptions in the methods. (I do this to better be able to grasp what the method is supposed to do and to get my code gears
turning. :) ) Then create the private Cell class so I can begin writing the other methods, for the lookup method needed I will simply use a lamda expression
that makes all variables equal to 1. All the other methods interact with a type of cell object. After that implement easy methods like GetDirectDependents and 
GetNamesOfAllNonemptyCells and GetCellContents. 
*UPDATE : LOOK BACK AT THE ABSTRACT CLASS! There are more methods in there. Study and understand how GetDirectDependents works with these methods.

Questions for TA: Look at *UPDATE 10/1/15 at bottom of README

A simplified first look and breakdown of how each method should work.
Design Of Program:

Constructor:
Create a new DependencyGraph object to be used later. (In some way)
Create a new Dictionary to hold the filled cells and their names.

GetCellContents
Ensure valid inputs
Get Contents out of specified cell
Return Contents

GetNamesOfAllNonemptyCells
Return all the key names of the Dictionary.

SetCellContents - Formula
Ensure valid inputs
Save Formula to Cell
*UPDATE : Update/Change the dependees
Return list of dependents

SetCellContents - String
Ensure valid inputs
Save String to Cell
*UPDATE : Update/Change the dependees
Return list of dependents

SetCellContents - Double
Ensure valid inputs
Save Double to Cell
*UPDATE : Update/Change the dependees
Return list of dependents

GetDirectDependents
Return all the Dependents using DependancyGraph

IsVariable
Copy from PS3

Class Cell
Holds the values.
Three content values, four value values.
Use three different constructors, one for each content value.

Versions of PS2 and PS3 Comments:
The version of PS2 I am using for this assignment was the exact same version as the one that I submited. Grading revealed that PS2 had no issues 
because it passed all the tests that I wrote for it, it passed all the tests that were given with the project from the professor, and it passed all
the tests that were used for grading. So I have the utmost confidence that PS2 is functioning perfectly and will perform exceptionally well in the
PS5 code. At the time of the writing of this README and most likely before I have to turn in the PS4 code, PS3 has not been graded so I am not 100%
sure that the PS3 code meets all the demands and requirements expected of it. But regardless of this, I am very confident in my PS3 code. My PS1 code
failed one test when it was graded which I promptly fixed so that it passed all tests. Then copying that code into PS3, ensures that the heart of the 
PS3 code the Evaluate method works perfectly. Along with over 100 test cases I created for PS3, I feel very confident in my PS3 code.

Notes To Grader/TA:
Thank you for taking time to look at my code. I would like to bring to your attention that I had a issue with the Resources folder not being named
properly. With the help of one of the other TA's Matthew we have fixed the issue and that the issue shouldn't effect how the program runs and that it
should run normally. But, in the case that it doesn't and the Resource folder starts freaking out please let me know and we can talk to Matthew to 
verify that de believed we had fixed the issue and go from there.


*UPDATE 9/30/15
I have figured out how the DependencyGraph plays into the overall assignment. You use it to keep track of all the dependents and dependees of each
cell. And you also use that information to prevent a Circular Dependancy when adding formulas in the cells. I was also reminded that the Abstract Class
has some other important methods that help make PS5 work.

*UPDATE 10/1/15
A few notes about testing, I have writen several test cases for my Spreadsheet class and have called the name of the tester SpreadsheetTests. All tests are currently
passing. I have achieved 96.01% code coverage. The only part I haven't touched was the get method for the value portion of the cell in the private cell class. The value
portion is used nowhere in the program and didn't feel like it needed to be tested. Nor could I figure how to access and test a private class nested in another class.

Lessons learned from this assignment:
Start the assignment eariler, read, read, read and re-read the assignment specs and code before starting and then after making the constructor write test case.