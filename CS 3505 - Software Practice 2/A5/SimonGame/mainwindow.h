/*
 * Nicholas Moore (u0847478)
 * Robert Weischedel (u0887905)
 * CS 3505 A Simon Game
 * 2/29/16
 */

#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>

#include "gamelogic.h"

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow {
    Q_OBJECT
public:
    explicit MainWindow(QWidget *parent = 0);
    // The destructor for the main window class.
    ~MainWindow();
private slots:
    // An event handler if the red button is clicked.
    void on_RedButton_clicked();
    // An event handler if the blue button is clicked.
    void on_BlueButton_clicked();
    // An event handler if the start/restart button is clicked.
    void on_StartRestartButton_clicked();
    // Changes the red button's color to red.
    void setRedButtonToRed();
    // Changes the blue button's color to blue.
    void setBlueButtonToBlue();
    // Changes the red button's color to gray.
    void setRedButtonToGray();
    // Changes the blue button's color to gray.
    void setBlueButtonToGray();
    // Changes the percentage for the process bar.
    void on_progressBar_valueChanged(int value);
    // Handles if the user closes the window.
    void closeEvent(QCloseEvent*);
    // Handles if the hint button is clicked.
    void on_HintButton_clicked();

private:
    Ui::MainWindow *ui;
    // Creates an object to store the game logic.
    gamelogic* computer;
    // Keeps track of the current turn that the user is on.
    int currentTurn;
    // Keeps track of the total amount of turns in the current round and the current score.
    int totalTurns;
    // Keeps track of the high score.
    int highScore;
    // Keeps track of how many hints you have.
    int hints;
    // This method will perform the turn for the computer.
    void computerTurn();
    // Will delay the program for the given amount of time.
    void delay(int);
    // If the user loses the game this will end everything for it.
    void loseGame(int);
    // Sets the high score for the user.
    void setHighScore(int);
    // Gets the high score for the user.
    int getHighScore();
    // Sets the text box containing the user's current score.
    void updateScoreText();
    // Sets the text box containing the high score.
    void updateHighScoreText();
};

#endif // MAINWINDOW_H
