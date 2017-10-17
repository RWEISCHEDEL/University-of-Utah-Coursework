/*********************************
 * Robert Weischedel
 * Lab 1 Example - Hello World
 *
 ********************************/
#include <stdio.h>
#include "say.h"

int main(int argc, char *argv[]){
    // printf("Hello, World!\n");
    // printf("Hello, %s\n", argv[1]);

    speak("Hello");
    int i;
    for(i = 1; i < argc; i++){
        speak(argv[i]);
    }
    //speak(argv[1]);
    speak("\n");


    return 0;
}
