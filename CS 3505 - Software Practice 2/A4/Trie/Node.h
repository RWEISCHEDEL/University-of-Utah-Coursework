/*
 * Nicholas Moore (u0847478)
 * CS 3505
 * A3: Rule-Of-Three with a Trie
 * Node.h
 * 2/1/16
 */

#ifndef NODE_H
#define NODE_H

#include <iostream>
#include <string>
#include <vector>

extern int refCount;

using namespace std;

class Node
{
    // This is an array of the alphabet in lower case.
    Node *letters[26];
    // Tells us if that particular sequence of letters is a word.
    bool isWordFlag;

public:
    // An empty constructor for the node.
    Node();
    // Adds a char to the array to eventually create a word.
    Node* addLetter(char);
    // Gets the node of the letter.
    Node* getLetter(char);
    // Checks if this particular sequence of letters is a word.
    bool isWord();
    // Sets this equal to a word by setting the boolean flag to true.
    void finishWord();
    // This will copy the node.
    void copyNode(Node*);
    // This get the words and return a vector of them.
    vector<string> getWordsWithPrefix(string, string, vector<string>*);
    // This will delete the node.
    void deleteNode();
};

#endif
