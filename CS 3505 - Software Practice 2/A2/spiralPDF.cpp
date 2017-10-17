/*******************
 * Robert Weischedel
 * CS 3505
 * Assignmnet 2
 * spiralPDF.cpp
 * This class contains a main method that generates a spiral of text in a pdf.
 * It uses the HaruPDF and Spiral class and header files to do this.
 *******************/

// Include all the desired libraries.
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <math.h>
#include <iostream>
#include "HaruPDF.h"
#include "Spiral.h"

/*This method calls the HaruPDF and Spiral classes in order to generate a spiral of text in a pdf file.*/
int main(int argc, char** argv){

    // Names for the desired file name and font type.
    std::string fileName = "spiralPDF.pdf";

    std::string fontName = "Helvetica";

    // Value for desired font size.
    int fontSize = 20;

    // Create a HaruPDF obj to build all the baiscs of the desired pdf.
    HaruPDF pdfGenerator;

    // Create a Spiral obj to generate the values to print the spiral text.
    Spiral spiralGenerator (210, 300, 180, 75);

    // Arrays to store the file name and font type, also convert them both from strings to char arrays.
    char fontNameArr[1024];

    char fileNameArr[1024];

    strcpy(fontNameArr, fontName.c_str());

    strcpy(fileNameArr, fileName.c_str());

    // Set up the Text and Font for the pdf.
    pdfGenerator.SetTextAndFont(fontNameArr, fontSize);

    // Check and ensure that input was entered if not, end the program.
    if(argc == 1){

        cout << "Warning no input text entered, please re-run the program." << endl;

        return 0;
    }

    // Get the desired text from the command line and store it.
    std::string inputText = argv[1];

    const char* inputCharacters = inputText.c_str();

    // Loop through each character in the inputed text and place it in the pdf.
    for(unsigned int i = 0; i < strlen(inputCharacters); i++){

        // Generate and place each letter in the pdf.
        pdfGenerator.SetPDFLetters(inputCharacters[i], spiralGenerator.get_spiral_angle(), spiralGenerator.get_text_x(), spiralGenerator.get_text_y());

        // Increment the values for the location for the next letter.
        spiralGenerator++;

    }

    // Save and build the actual pdf.
     pdfGenerator.GenerateFile(fileNameArr);

    return 0;

}
