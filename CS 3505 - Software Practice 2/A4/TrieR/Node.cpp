/***********************************
 * Robert Weischedel
 * CS 3505
 * A3 - Binary Trie
 * Node.cpp
 **********************************/

// Include all the desired libraries.
using namespace std;

#include <iostream>
#include <vector>
#include <string>
#include "Node.h"

/*This servers as the main constructor for the node class, it creates a empty ode that contains no pointer elements in its array.*/
Node::Node(){

    refCount++;

    // Fill the array with null pointers.
    for(int i = 0; i < 26; i++){
        alphabetArray[i] = nullptr;
    }

    // Set the word flag to false.
    isWordFlag = false;
}

Node::~Node()
{

	refCount--;

    for(int i; i < 26; i++){

        // If the location is not null, delete the pointer that is there.
        if(alphabetArray[i] != nullptr){

            // Delete the pointer and set it to null.
            delete alphabetArray[i];
            alphabetArray[i] = nullptr;
			std::cout << i << endl;

        }
    }
}

/*This method adds a reference to a letter in the node pointer array. It returns a reference to the newly added node object in (this) array.*/
Node* Node::addChar(char c){
    // Convert the char into a index location.
    int index = c - 97;

    // Create and return the node.
    alphabetArray[index] = new Node;
    return alphabetArray[index];
}

/*This method sees if a reference to a letter exists in the (this) node pointer array. It returns a reference to the desired index whether or not if the char is there or not.*/
Node* Node::hasChar(char c){

    // Convert the char into a index location.
    int index = c - 97;

    // Return the pointer at that index location.
    return alphabetArray[index];
}

/*This method sets the boolean word flag to true when a word can be formed from a path to this node.*/
void Node::setWordFlag(){
    isWordFlag = true;
}

/*This method returns the current state of the boolean word flag.*/
bool Node::getWordFlag(){
    return isWordFlag;
}

/*This method makes a copy of (this) node and its  array onto the node that serves as the input parameter. It recusively calls this method to ensure that all associated node pointers are copied.*/
void Node::copyNode(Node* copy){

    // Iterate through the entire pointer array.
    for(int i = 0; i < 26; i++){

        // If the location is not equal to null, copy node data.
        if(alphabetArray[i] != nullptr){

            // Create a new node for that location.
            copy->alphabetArray[i] = new Node;

            // Copy the boolean word flag.
            copy->alphabetArray[i]->isWordFlag = alphabetArray[i]->isWordFlag;

            // Call this method again to ensure that all node pointers update.
            alphabetArray[i]->copyNode(copy->alphabetArray[i]);
        }
    }
}

/*This method searches through all the node pointers to find all word suggestions that are close to those entered in as input. It searches specifically for any and all words that contain the same prefix as the input values. This method is also recursive to ensure that we traverse all node pointer paths.*/
vector<string> Node::retrievePrefixWords(vector<string>* wordList, string prefix, string word){

    // If the prefix is not a empty string and not null go to the next node.
    if(prefix != ""){

        // Determine the index to search for the next letter.
        int index = prefix[0] - 97;

        // If the pointer at that location is not null, search again.
        if(alphabetArray[index] != nullptr){

            // Call this method again to search the next node.
            alphabetArray[index]->retrievePrefixWords(wordList, prefix.substr(1), word);
        }
    }
    // If the prefix is a empty string.
    else{

        // Iterate through all pointers in the array.
        for(int i = 0; i < 26; i++){

            // If the pointer at the location is not null, check its flag and add the word.
            if(alphabetArray[i] != nullptr){

                // Generate the next letter for the suggested word from prefix.
                char suggestChar = i + 97;
                string newWord = word + suggestChar;

                // Check the bool flag at the location, if true add the word.
                if(alphabetArray[i]->getWordFlag()){

                    // Add the word to the suggested word vector.
                    wordList->push_back(newWord);
                }

                // Call this method again to search the next node.
                alphabetArray[i]->retrievePrefixWords(wordList, prefix, newWord);
            }
        }
    }

    // Return the suggested word list.
    return *wordList;

}
