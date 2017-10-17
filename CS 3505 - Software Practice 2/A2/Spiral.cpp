/*******************
 * Robert Weischedel
 * CS 3505
 * Assignmnet 2
 * Spiral.cpp
 * This class inherits from a header file by the same name in order to generate
 * the spiral formula for the pdf class.
 *******************/

// Include the desired libraries.
using namespace std;

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <math.h>
#include <iostream>
#include "Spiral.h"

// Declare the necessary private variables again for good programming practice so that the file can compile on its own.
int nextLetter;
double x;
double y;
double center_x;
double center_y;
double angle;
double spiralAngle;
double textAngle;
double radius;

/*This serves as the constructor for the Spiral class, it takes in a orgin x and y point, and the beginning angle and radius.*/
Spiral::Spiral(double centerX, double centerY, double beginAngle, double beginRadius){

    // Initialize the center x and y point of the spiral.
    center_x = centerX;

    center_y = centerY;

    // Initialize the beginning angle and radius for the spiral.
    angle = beginAngle;

    radius = beginRadius;

    // Initialize the letter counter and update the class to get ready for the first letter.
    nextLetter = 0;

    updateNextLetter();

}

/*Overload the prefix ++ operator, it calls the updateNextLetter method to prepare for the next letter in the desired input text.*/
Spiral& Spiral::operator++(){

    updateNextLetter();

    nextLetter++;

    return *this;
}

/*Overload the postfix ++ operator, it calls the updateNextLetter method to prepare for the next letter in the desired input text.*/
Spiral Spiral::operator++(int input){

    updateNextLetter();

    nextLetter++;

    return *this;
}

/*Overload for the << operator in order to generate the current status of several variables in the class.*/
std::ostream& operator<<(std::ostream& output, Spiral s){

    output << "x: " << x << "y: " << y << "Spiral Angle: " << angle << "Letter Angle: " << spiralAngle << "Current Angle: " << textAngle << endl;

    return output;
}

/*This method returns the current x value for the next inputed letter.*/
double Spiral::get_text_x(){

    return x;

}

/*This method returns the current y value for the next inputed letter.*/
double Spiral::get_text_y(){

    return y;

}

/*This method returns the current letter angle for the next inputed letter.*/
double Spiral::get_text_angle(){

    return textAngle;

}

/*This method returns the current angle of the spiral.*/
double Spiral::get_spiral_angle(){

    return spiralAngle;

}

/*This method updates the entire classes variables each time a letter has been placed. It performs all the calculations for the placement of the next inputed letter. */
void Spiral::updateNextLetter(){

    // Generate the spiral and the text angle for each letter.
    spiralAngle = (angle - 90.0) / 180 * M_PI;

    textAngle = angle / 180.0 * M_PI;

    // Generate the new x and y location for each letter.
    x = center_x + cos(textAngle) * (radius);

    y = center_y + sin(textAngle) * (radius);

    x += nextLetter * cos(textAngle);

    y += nextLetter * sin(textAngle);

    // Decrement the angle for the next letter.
    angle -= 10;
}
