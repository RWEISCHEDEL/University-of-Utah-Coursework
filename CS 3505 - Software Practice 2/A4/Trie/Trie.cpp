/*
 * Nicholas Moore (u0847478)
 * CS 3505
 * A3: Rule-Of-Three with a Trie
 * Trie.cpp
 * 2/1/16
 */

#include <iostream>
#include <vector>

#include "Node.h"
#include "Trie.h"

using namespace std;

// This is the empty constructor for the Trie.
Trie::Trie()
{
    currentNode = new Node;
}

// This will take in a Node to copy everything.
Trie::Trie(const Trie& node)
{
    this->currentNode = new Node;
    node.currentNode->copyNode(this->currentNode);
}

// This is the deconstructor for the Node.
Trie::~Trie()
{
    currentNode->deleteNode();
    delete currentNode;
    currentNode = nullptr;
}

// This will override the = operator.
Trie Trie::operator=(const Trie& otherTrie)
{
    currentNode = new Node;
    otherTrie.currentNode->copyNode(this->currentNode);
	refCount--;
    return *this;
}

// This will add a word to the trie.
void Trie::addWord(string word)
{
    Node* current = currentNode;
    for (unsigned int i = 0; i < word.length(); i++)
    {
        Node* last = current;
        char currentLetter = word[i];

        // If we are at the end of the word.
        if (i == word.length() - 1)
        {
            if ((current = current->getLetter(currentLetter)) == nullptr)
            {
                current = last->addLetter(currentLetter);
                current->finishWord();
                break;
            }
            else
            {
                current->finishWord();
                break;
            }
        }

        // Checks whether or not we have the character and if we don't we add the letter to the trie.
        if ((current = current->getLetter(currentLetter)) == nullptr)
        {
            current = last->addLetter(currentLetter);
        }
        else
        {
            continue;
        }
    }
}

// Checks the string given is a word.
bool Trie::isWord(string word)
{
    Node* current = currentNode;
    for (unsigned int i = 0; i < word.length(); i++)
    {
        // Checks if we are at the end of the word.
        if (i == word.length() - 1)
        {
            if ((current = current->getLetter(word[i])) == nullptr)
            {
                return false;
            }
            else
            {
                return current->isWord();
            }
        }
        else
        {
            if ((current = current->getLetter(word[i])) == nullptr)
            {
                return false;
            }
            else
            {
                continue;
            }
        }
    }

    return false;
}

vector<string> Trie::allWordsWithPrefix(string prefix)
{
    vector<string> prefixWords;

    currentNode->getWordsWithPrefix(prefix, prefix, &prefixWords);

    return prefixWords;
}
