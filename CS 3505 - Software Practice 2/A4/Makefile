# Robert Weischedel & Nick Moore
# CS 3505
# Assignment 4

# Flags
ifndef $(CXX)
	CXX=g++ -std=c++11
endif

FLAGS=-std=c++11

IFLAGS=-L./gtest -I./gtest/include -lgtest -lgtest_main -lpthread


# Create the .a
GoogleTest: Trie.o Node.o
	$(CXX) -o GoogleTest Trie.o Node.o TrieTestCases.cpp $(IFLAGS)

# Create the Trie.o file
Trie.o: Trie/Trie.h Trie/Trie.cpp
	$(CXX) $(FLAGS) -c -I. Trie/Trie.cpp

# Create the Node.o file
Node.o: Trie/Node.cpp Trie/Node.h
	$(CXX) $(FLAGS) -c -I. Trie/Node.cpp

# Clean up executable and .o files
clean:
	rm *.o
	rm GoogleTest

# Automated Test
test:
	./GoogleTest
