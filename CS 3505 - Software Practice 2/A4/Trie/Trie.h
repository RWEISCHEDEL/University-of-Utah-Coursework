/*
 * Nicholas Moore (u0847478)
 * CS 3505
 * A3: Rule-Of-Three with a Trie
 * Trie.h
 * 2/1/16
 */

#ifndef TRIE_H
#define TRIE_H

#include <iostream>
#include <vector>

#include "Node.h"

extern int refCount;

using namespace std;

class Trie
{
public:
    Node* currentNode;

    // The empty constructor for the Trie.
    Trie();
    // The constructor that takes in the root node to copy.
    Trie(const Trie&);
    // This is the deconstructor for the object.
    ~Trie();
    // This will override the = operator.
    Trie operator=(const Trie&);

    // This will add a word to the Trie.
    void addWord(string);
    // This will check if the argument entered is contained within the Trie.
    bool isWord(string);
    // This will get the words containing the prefix given.
    vector<string> allWordsWithPrefix(string);
};

#endif
