/*******************
 * Robert Weischedel
 * CS 3505
 * Assignmnet 2
 * HaruPDF.cpp
 * This serves as the actual class file for the HaruPDF wrapper class.
 * It inherits the methods from the HaruPDF.h file.
 *******************/

// Include all the needed libraries.
using namespace std;

#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <string>
#include <math.h>
#include "HaruPDF.h"
#include "hpdf.h"

// Declare the desired variables to implement the good coding practice of ensuring that each file can complie on its own.
HPDF_Doc pdf;
HPDF_Page page;
char fname[256];
HPDF_Font font;

/*This serves as the constructor for the class, it wraps all the complex method from the Haru Library so we can simply and abstract them away.*/
HaruPDF::HaruPDF(){

    // Generate a new pdf and page.
    pdf = HPDF_New(NULL, NULL);

    page = HPDF_AddPage(pdf);

    // Set page size and details.
    HPDF_Page_SetSize(page, HPDF_PAGE_SIZE_A5, HPDF_PAGE_PORTRAIT);

    HPDF_Page_SetTextLeading(page, 20);

    HPDF_Page_SetGrayStroke(page, 0);

    // Begin accepting text to the pdf.
    HPDF_Page_BeginText(page);

}

/*This method allows the user to change the font type and size.*/
void HaruPDF::SetTextAndFont(char* fontName, int fontSize){

    // Generate the font to the desired input.
    font = HPDF_GetFont(pdf, fontName, NULL);

    // Apply the font and the text size to the page.
    HPDF_Page_SetFontAndSize(page, font, fontSize);
}

/*This method wraps the complex task of adding letters to the pdf in a simple to use method. It takes in the letter and its desired location and angle.*/
void HaruPDF::SetPDFLetters(char letter, double angle, double x, double y){

    // Create a buffer so that the input can be 0 terminated as req by lib Haru.
    char buffer[2];

    // Generate the letter at the desired angle.
    HPDF_Page_SetTextMatrix(page, cos(angle), sin(angle), -sin(angle), cos(angle), x, y);

    // Build the buffer with the letter and the 0 so that it can be placed.
    buffer[0] = letter;

    buffer[1] = 0;

    // Place the letter on the pdf.
    HPDF_Page_ShowText(page,buffer);

}

/*This method ends the accepting of text process and saves the file to the desired inputed filename*/
void HaruPDF::GenerateFile(char* filename){

    // Stop accepting text into the pdf.
    HPDF_Page_EndText(page);

    // Save the pdf to the desired file name and free up the space it took.
    HPDF_SaveToFile(pdf,filename);

    HPDF_Free(pdf);
}
