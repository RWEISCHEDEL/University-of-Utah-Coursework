/*
 * Nicholas Moore (u0847478)
 * Robert Weischedel (u0887905)
 * CS 3505 A Simon Game
 * 2/29/16
 */

#ifndef GAMELOGIC_H
#define GAMELOGIC_H

#include "vector"

/*
 * This will keep track of the state of the game.
 *
 * 0 stands for red in the vector, 1 stands for blue.
 */
class gamelogic {
private:
    // The current turn the user is on.
    int turn;
    // The vector that contains the sequence of colors.
    std::vector<int> colors;
public:
    // An empty constructor for the to create the .
    gamelogic();
    // A deconstructor for the gamelogic class.
    ~gamelogic();
    // Gets the color (referenced as an int) based on the turn.
    int getColor(int);
    // Adds another turn or "round" to the game.
    void addTurn();
};

#endif //GAMELOGIC_H
