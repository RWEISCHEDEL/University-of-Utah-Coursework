/*
 * Nicholas Moore (u0847478)
 * CS 3505
 * A3: Rule-Of-Three with a Trie
 * Node.cpp
 * 2/1/16
 */

#include <iostream>
#include <string>
#include <vector>

#include "Node.h"

using namespace std;

Node *letters[26];
bool isWordFlag;

// An empty constructor for the node.
Node::Node()
{
	refCount++;

    // This will intialize an array full of null pointers.
    for(int i = 0; i < 26; i++)
    {
        letters[i] = nullptr;
    }
    // When the node is first created, we can say that it is not a word... Yet.
    isWordFlag = false;
}

// This will add a letter to the node, helping create a sequence of letters.
Node* Node::addLetter(char letter)
{
    letters[letter - 97] = new Node;
    return letters[letter - 97];
}

// This will return the node at the character entered.
Node* Node::getLetter(char letter)
{
    return letters[letter - 97];
}

// Checks to see if at this point the sequence of characters creates a word.
bool Node::isWord()
{
    return isWordFlag;
}

// We can tell the node if it is the end of a word.
void Node::finishWord()
{
    isWordFlag = true;
}

// This will let us copy the node and the ones below it.
void Node::copyNode(Node* node)
{
    // Will iterate through the array and copy its data.
    for(int i = 0; i < 26; i++)
    {
        // Recursively calls itself to copy Nodes.
        if(letters[i] != nullptr)
        {
            node->letters[i] = new Node;
            node->letters[i]->isWordFlag = letters[i]->isWordFlag;
            letters[i]->copyNode(node->letters[i]);
        }
    }
}

// This will get the words containing the prefix and the vector you want to add them to.
vector<string> Node::getWordsWithPrefix(string prefix, string word, vector<string>* wordsWithPrefix)
{
    if(prefix == "")
    {
        string currentWord;

        for(int i = 0; i < 26; i++)
        {
            // Checks if it contains a letter.
            if(letters[i] != nullptr)
            {
                currentWord = word + char(i + 97);
                // If this is a word we add it to the vector.
                if(letters[i]->isWord())
                {
                    wordsWithPrefix->push_back(currentWord);
                }

                // Recursively calls the function to find more words.
                letters[i]->getWordsWithPrefix(prefix, currentWord, wordsWithPrefix);
            }
        }
    }
    else
    {
        /*
        if (prefix.length() == 1 && this->isWord())
        {
            wordsWithPrefix->push_back(word);
        }
        */
        if(letters[prefix[0] - 97] != nullptr)
        {
            letters[prefix[0] - 97]->getWordsWithPrefix(prefix.substr(1), word, wordsWithPrefix);
        }
    }

    return *wordsWithPrefix;
}

// Will delete every node that is a child of the current node.
void Node::deleteNode()
{

	refCount--;

    // Deletes every child.
    for(int i = 0; i < 26; i++)
    {
        if(letters[i] != nullptr)
        {
            letters[i]->deleteNode();
            delete letters[i];
            // Deletes the current node.
            letters[i] = nullptr;
        }
    }
}
