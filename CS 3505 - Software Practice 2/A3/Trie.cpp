
/***********************************
 * Robert Weischedel
 * CS 3505
 * A3 - Binary Trie
 * Trie.cpp
 **********************************/

// Include all the desired libraries.
using namespace std;

#include <iostream>
#include <vector>
#include "Trie.h"
#include "Node.h"

/*This serves as the main constructor for the Trie class, it creates a new empty Binary Trie.*/
Trie::Trie(){

    // Create the root node.
    rootNode = new Node;
}

/*This serves as a copy constructor for the Trie class. It generates a Trie class exactly like the one that is inputed.*/
Trie::Trie(const Trie& inputTrie){

    // Create a new root node.
    rootNode = new Node;

    // Copy all the nodes from the input trie to the (this) new Trie.
    inputTrie.rootNode->copyNode(this->rootNode);
}

/*This serves as the deconstructor for the Trie class. It calls a method in the node calls to aid in deleting all the nodes and their pointers in the Trie.*/
Trie::~Trie(){

    // Call destroyNode to destory all the pointers that all nodes have.
    rootNode->destroyNode();

    // Finnally, delete the pointer to the root node.
    delete rootNode;
}

/*This method serves as a override for the = operator. Now it serves as the same function as the copy constructor, except you dont need to generate a new Trie, it overwrites the (this) Trie.*/
Trie& Trie::operator=(Trie inputTrie){

    // Generate a new root node.
    rootNode = new Node;

     // Copy all the input value Trie node values into the (this) Trie.
    inputTrie.rootNode->copyNode(this->rootNode);

    // Return the newly copied Trie.
    return *this;
}

/*This method adds new words to the Binary Trie.*/
void Trie::addWord(std::string word){

    // First it makes a copy of the root node.
    Node* currNode = rootNode;

    // Loop through the input word looking at each char.
    for(unsigned int i = 0; i < word.length(); i++){

        // Store a reference to the current node, in case its the end node.
        Node* lastNode = currNode;

        // If the index is equal to the length of the word.
        if(i == word.length() - 1){

            // Ensure the node is not null, if its not, set the word flag.
            if((currNode = currNode->hasChar(word[i])) != nullptr){
                currNode->setWordFlag();
                break;
            }
            // If the node is null, generate a new node, add char, set flag.
            else{
                currNode = lastNode->addChar(word[i]);
                currNode->setWordFlag();
                break;
            }

        }

        // If the node is null, generate a new node, add char.
        if((currNode = currNode->hasChar(word[i])) == nullptr){
            currNode = lastNode->addChar(word[i]);
        }
    }
}

/*This method checks to see if a word has already been added to the Trie.*/
bool Trie::isWord(string word){

    // Store a copy of the root node.
    Node* currNode = rootNode;

    // Loop through the characters of the word.
    for(unsigned int i = 0; i < word.length(); i++){

        // If the index is equal to the lengh of the word.
        if(i == word.length() -1){

            // If the last node is not null, see if the char matches.
            if((currNode = currNode->hasChar(word[i])) != nullptr){

                // Return the boolean flag of the last value.
                return currNode->getWordFlag();
            }

            // Otherwise return false.
            return false;
        }
        // If it isn't the last value, ensure the node isn't null.
        else{

            // If the node is null, return false.
            if((currNode = currNode->hasChar(word[i])) == nullptr){

                return false;
            }
        }
    }

    // Return false if all else fails.
    return false;
}

/*This method returns all the words that begin with the desired input prefix. To do this it uses a method from the Node class called retrieveWords.*/
vector<string> Trie::allWordsWithPrefix(string prefix){

    // Create a new vector of strings to hold the prefix word suggestions.
    vector<string> allPrefixWords;

    // Retieve all the suggested prefix words.
    rootNode->retrievePrefixWords(&allPrefixWords, prefix, prefix);

    // Return the vector.
    return allPrefixWords;

}
