/*******************
 * Robert Weischedel
 * CS 3505
 * Assignmnet 2
 * Spiral.h
 * This serves as the header file for the Spiral class.
 *******************/

// Include gaurds to ensure this header file isn't confused with others.
#ifndef A2_SPIRAL_H
#define A2_SPIRAL_H

// Include the desired libraries.
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <math.h>
#include <iostream>

// Create the header for the Spiral class.
class Spiral
{

// Declare the desired private method and vairables.
private:
int nextLetter;
double x;
double y;
double center_x;
double center_y;
double angle;
double spiralAngle;
double textAngle;
double radius;

// This method will act as a counter to update the class for each consecutive letter.
void updateNextLetter();

// Declare the public methods and constructor for the class.
public:
Spiral(double centerX, double centerY, double rad1, double rad2);
double get_text_x();
double get_text_y();
double get_spiral_angle();
double get_text_angle();
Spiral& operator++();

// Declare the overrides for the ++ operator.
Spiral operator++(int);
friend std:: ostream& operator<<(std::ostream& output, Spiral s);



};

#endif
