/*
 * Nicholas Moore (u0847478)
 * Robert Weischedel (u0887905)
 * CS 3505 A Simon Game
 * 2/29/16
 */

#include "gamelogic.h"
#include <QtGlobal>
#include <time.h>

gamelogic::gamelogic() {
    turn = 0;
    addTurn();
}

gamelogic::~gamelogic() {
    colors.clear();
}

int gamelogic::getColor(int turn) {
    return colors[turn - 1];
}

void gamelogic::addTurn() {
    turn++;
    qsrand(time(0));
    colors.push_back(qrand() % 2);
}
