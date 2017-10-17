/***********************************
 * Robert Weischedel and Nick Moore
 * CS 3505
 * 2/15/16
 * A4 - Testing
 **********************************/

#include "gtest/gtest.h"
#include "Trie/Trie.h"
#include <vector>

int refCount = 0;

TEST(TrieTestCases, TestAdd1Word) {
    Trie* t1 = new Trie();
    string testWrd("hiya");
    t1->addWord(testWrd);

    ASSERT_EQ(5, refCount);

    bool word = t1->isWord(testWrd);
    ASSERT_TRUE(word);
    delete t1;

    ASSERT_EQ(0, refCount);
}

TEST(TrieTestCases, TestAdd2Words){
    Trie* t2 = new Trie();

    string testWrd1("computer");

    string testWrd2("science");
    t2->addWord(testWrd1);

    ASSERT_EQ(9, refCount);

    t2->addWord(testWrd2);

    ASSERT_EQ(16, refCount);

    bool word1 = t2->isWord(testWrd1);
    bool word2 = t2->isWord(testWrd2);
    ASSERT_TRUE(word1);
    ASSERT_TRUE(word2);
    delete t2;

    ASSERT_EQ(0, refCount);
}

TEST(TrieTestCases, TestAdd2Similiar){
    Trie* t3 = new Trie();
    string testWrd1("compute");
    string testWrd2("computer");
    t3->addWord(testWrd1);

    ASSERT_EQ(8, refCount);

    t3->addWord(testWrd2);

    ASSERT_EQ(9, refCount);

    bool word1 = t3->isWord(testWrd1);
    bool word2 = t3->isWord(testWrd2);
    ASSERT_TRUE(word1);
    ASSERT_TRUE(word2);
    delete t3;

    ASSERT_EQ(0, refCount);
}

TEST(TrieTestCases, TestAdd3Similiar){
    Trie* t4 = new Trie();
    string testWrd1("compute");
    string testWrd2("computer");
    string testWrd3("computing");
    t4->addWord(testWrd1);

    ASSERT_EQ(8, refCount);
    t4->addWord(testWrd2);

    ASSERT_EQ(9, refCount);
    t4->addWord(testWrd3);

    ASSERT_EQ(12, refCount);
    bool word1 = t4->isWord(testWrd1);
    bool word2 = t4->isWord(testWrd2);
    bool word3 = t4->isWord(testWrd3);
    ASSERT_TRUE(word1);
    ASSERT_TRUE(word2);
    ASSERT_TRUE(word3);
    delete t4;

    ASSERT_EQ(0, refCount);
}

TEST(TrieTestCases, TestTrieCopyConstructor1){
    Trie* t5 = new Trie();
    string testWrd1("computing");
    t5->addWord(testWrd1);

    ASSERT_EQ(10, refCount);

    Trie* u5 = new Trie(*t5);

    ASSERT_TRUE(u5->isWord(testWrd1));

    delete t5;
    delete u5;

    ASSERT_EQ(0, refCount);
}

TEST(TrieTestCases, TestTrieCopyConstructor2){
    Trie* t6 = new Trie();
    string testWrd1("computing");
    string testWrd2("utah");
    t6->addWord(testWrd1);

    ASSERT_EQ(10, refCount);
    t6->addWord(testWrd2);

    ASSERT_EQ(14, refCount);
    Trie* u6 = new Trie(*t6);
    ASSERT_TRUE(u6->isWord(testWrd1));
    ASSERT_TRUE(u6->isWord(testWrd2));

	u6->addWord("pickles");

	ASSERT_FALSE(t6->isWord("pickles"));
	ASSERT_TRUE(u6->isWord("pickles"));

    delete t6;
    delete u6;

    ASSERT_EQ(0, refCount);
}

TEST(TrieTestCases, TestTriePrefixFinder1){
    Trie* t7 = new Trie();

    string testWrd1("compute");
    string testWrd2("computing");
    string testWrd3("computer");

    t7->addWord(testWrd1);
    t7->addWord(testWrd2);
    t7->addWord(testWrd3);

    ASSERT_EQ(12, refCount);

    vector<string> testWords;

    testWords.push_back("compute");
    testWords.push_back("computer");
    testWords.push_back("computing");

    vector<string> returnedWords = t7->allWordsWithPrefix("comp");

    ASSERT_TRUE(testWords == returnedWords);

    delete t7;

    ASSERT_EQ(0, refCount);

}

TEST(TrieTestCases, TestTriePrefixFinder2){
    Trie* t8 = new Trie();

    string testWrd1("compute");
    string testWrd2("computing");
    string testWrd3("computer");

    t8->addWord(testWrd1);
    t8->addWord(testWrd2);
    t8->addWord(testWrd3);

    ASSERT_EQ(12, refCount);

    vector<string> testWords;

    testWords.push_back("compute");
    testWords.push_back("computing");
    testWords.push_back("computer");

    vector<string> returnedWords = t8->allWordsWithPrefix("hard");

    ASSERT_FALSE(testWords == returnedWords);

    ASSERT_EQ(0, returnedWords.size());

    delete t8;

    ASSERT_EQ(0, refCount);

}

TEST(TrieTestCases, TestTrieEqualOperator1){
    Trie* t9 = new Trie();

    string testWrd1("compute");
    string testWrd2("computing");
    string testWrd3("computer");

    t9->addWord(testWrd1);
    t9->addWord(testWrd2);
    t9->addWord(testWrd3);

    ASSERT_EQ(12, refCount);

    Trie* u9 = new Trie();

    *u9 = *t9;

    ASSERT_TRUE(u9->isWord(testWrd1));
    ASSERT_TRUE(u9->isWord(testWrd2));
    ASSERT_TRUE(u9->isWord(testWrd3));

	

    delete t9;
    delete u9;

    ASSERT_EQ(0, refCount);

}

TEST(TrieTestCases, TestTrieEqualOperator2){
    Trie* t10 = new Trie();

    string testWrd1("word");

    t10->addWord(testWrd1);

    ASSERT_EQ(5, refCount);

    Trie* u10 = new Trie();

    *u10 = *t10;

    string testWrd2("hiya");

    ASSERT_TRUE(u10->isWord(testWrd1));
    ASSERT_FALSE(u10->isWord(testWrd2));

    delete t10;
    delete u10;

    ASSERT_EQ(0, refCount);

}
