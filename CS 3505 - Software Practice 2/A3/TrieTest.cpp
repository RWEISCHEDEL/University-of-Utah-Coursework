/***********************************
 * Robert Weischedel
 * CS 3505
 * A3 - Binary Trie
 * TrieTest.cpp
 **********************************/

//Include all the desired libraries.
using namespace std;

#include <iostream>
#include <vector>
#include <string>
#include <fstream>
#include "Trie.h"
#include "Node.h"

/*This method generates a Binary Trie object and peforms a series of tests on the object to ensure it is working correctly. Note: This method takes in two input parameters.*/
int main(int argc, const char * argv[]){

    // If the number of arguments isn't three, send a error message and return.
    if(argc != 3){
        cout << "You didn't enter the correct number of files!" << endl;
        return 0;
    }

    // Generate strings to hold the dictionary and queries files.
    string dictionaryFile = argv[1];
    string queriesFile = argv[2];

    // Generate a string to read the values from the files.
    string inputLine = "";

    // Create the Binary Trie object.
    Trie* binTrie = new Trie();

    // Create a new ifstream to read the input dictionary file.
    ifstream wordDict;

    // Open the dictionary File to begin reading it.
    wordDict.open(dictionaryFile);

    // While the dictionary is open, read each line.
    if(wordDict.is_open()){
        while(getline(wordDict, inputLine)){

            // Add the word to the Binary Trie.
            binTrie->addWord(inputLine);
        }
    }
    // If the dictionary file closed for some reason, return a error message and end the program.
    else{
        cout << "The input dictionary encountered an issue." << endl;
        return 0;
    }

    // Reset the inputLine.
    inputLine = "";

    // Create a new ifstream to read the queries file.
    ifstream wordQueries;

    // Open the queries file to begin reading it.
    wordQueries.open(queriesFile);

    // While the querie file is open, read each line from it.
    if(wordQueries.is_open()){
        while(getline(wordQueries, inputLine)){

            // If the word is in the Binary Trie, print a found message.
            if(binTrie->isWord(inputLine)){
                cout << "The word " + inputLine + " was found." << endl;
            }
            // If the word wasn't in the Binary Trie.
            else{

                // Attempt to find all the suggested words with that prefix.
                vector<string> wordsFromDict = binTrie->allWordsWithPrefix(inputLine);
                // If there were suggested words, print them out.
                if(wordsFromDict.size() != 0){
                    cout << "The word " << inputLine << " was not found, here are a few suggestions instead :" << endl;

                    // Loop thought and return all the suggested words.
                    for(string & word : wordsFromDict){
                        cout << word << endl;
                    }
                }
                // If there were no suggestions, print a message.
                else{
                    cout << "The word " << inputLine << " was not found, with no suggestions as well." << endl;
                }
            }
        }
    }
    // If the queries file closes for some reason, print a error message and return.
    else{
        cout << "Issue reading the queries file." << endl;
        return 0;
        }

    // Delete the Binary Trie.
    delete binTrie;

    return 0;

}
