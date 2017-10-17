/*******************
 * Robert Weischedel
 * CS 3505
 * Assignmnet 2
 * HaruPDF.h
 * This serves as a header file for the HaruPDF wrapper class.
 *******************/

// Include unique include guards so the .h file won't be confused with others.
#ifndef A2_HARUPDF_H
#define A2_HARUPDF_H

// Include all the needed libraries.
using namespace std;

#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <string>
#include <math.h>
#include "hpdf.h"

/*This class serves as a wrapper for the Haru Library*/
class HaruPDF
{
    // Declare all the desired variables.
private:
    HPDF_Doc pdf;
    HPDF_Page page;
    char fname[256];
    HPDF_Font font;

    // Declare all the stubbed out method headers.
public:
    HaruPDF();

    void SetTextAndFont(char* fontName, int);

    void SetPDFLetters(char letter, double angle, double x, double y);

    void GenerateFile(char* fileName);

};

#endif
