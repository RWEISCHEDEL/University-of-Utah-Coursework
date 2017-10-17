/****************************
* Robert Weischedel
* CS 3505
* 1/19/15
* A1
****************************/

// Included libaries.
#include <iostream> // Used for basic input/output cin/cout
#include <iomanip> // Used for displaying decimal precision
using namespace std;

// Forward Declarations for the two helper methods used in coverting and showing
// the in to cm conversion.
double convertInchToCm(double);
void niceOutput(double);
void precisionTest();

/*********************
* The main method begins the conversion method by constating accepting input
* from the command line till eof. For each value that is brought in it is
* first converted from in to cm and then displayed on the terminal with 2 
* decimals of precision. 
********************/
int main(){

  // This method is used to answer the part 2 question of the assignment
  // and experiment with the 30 points of precision.
  //precisionTest();
  //return 0;
  
  // The variable to hold the values brought in from the command line.
  double outputVal;

  // While there is input from the commmand line keep converting and displaying
  // the value.
  while(cin >> outputVal){
  
    // Convert the inputted value from in to cm.
    double convertVal = convertInchToCm(outputVal);

    // Display the cm value with 2 decimals of precision and print it to the
    // terminal.
    niceOutput(convertVal);

  }  

}

/*********************
 * This method converts a given value from inches to cm.
 ********************/
double convertInchToCm(double outputVal){

  return outputVal * 2.54;

}

/********************
 * This method sets the decimal precision of a value to two places and then
 * prints that value to the terminal. 
 *******************/
void niceOutput(double convertVal){

  // Use <iomanip> to set precision of the given value. 
  cout << fixed << setprecision(2) << convertVal << endl;

}

/***************************
 * This method was simply used to help answer q2 in the written portion of
 * the assignment.
 *************************/
void precisionTest(){

  double testValue = 0.3;

  cout << fixed << setprecision(15) << testValue << endl;

}
