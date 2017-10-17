/*
 * Nicholas Moore (u0847478)
 * Robert Weischedel (u0887905)
 * CS 3505 A Simon Game
 * 2/29/16
 */

#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "gamelogic.h"
#include <QAbstractButton>
#include <QTimer>
#include <QTime>
#include <QProgressBar>
#include <QMessageBox>
#include <QString>
#include <QFile>
#include <QIODevice>
#include <QTextStream>
#include <QCloseEvent>

MainWindow::MainWindow(QWidget *parent) : QMainWindow(parent), ui(new Ui::MainWindow) {
    ui->setupUi(this);

    currentTurn = 0;
    totalTurns = 1;
    // This makes it so that the user cannot click the buttons.
    ui->BlueButton->setEnabled(false);
    ui->RedButton->setEnabled(false);
    ui->HintButton->setEnabled(false);
    ui->StartRestartButton->setText("START");
    // Intializes all of the scores.
    highScore = getHighScore();
    updateScoreText();
    updateHighScoreText();
}

MainWindow::~MainWindow() {
    delete ui;
}

void MainWindow::on_RedButton_clicked() {
    currentTurn++;

    setRedButtonToRed();

    // Makes sure the user clicked the right color and checks if they have
    // finished the sequence.
    if(computer->getColor(currentTurn) != 0) {
        loseGame(totalTurns - 1);
    }
    else if(currentTurn == totalTurns) {
        // Updates the high score if needed.
        if (currentTurn > highScore)
        {
            highScore++;
            updateHighScoreText();
        }
        on_progressBar_valueChanged(100);
        computer->addTurn();
        totalTurns++;
        updateScoreText();
        computerTurn();
    }
    else {
        on_progressBar_valueChanged(currentTurn * 100 / totalTurns);
    }
}

void MainWindow::on_BlueButton_clicked() {
    currentTurn++;

    setBlueButtonToBlue();

    // Makes sure the user clicked the right color and checks if they have
    // finished the sequence.
    if(computer->getColor(currentTurn) != 1) {
        loseGame(totalTurns - 1);
    }
    else if (currentTurn == totalTurns) {
        // Updates the high score if needed.
        if (currentTurn > highScore)
        {
            highScore++;
            updateHighScoreText();
        }
        on_progressBar_valueChanged(100);
        computer->addTurn();
        totalTurns++;
        updateScoreText();
        computerTurn();
    }
    else {
        on_progressBar_valueChanged(currentTurn * 100 / totalTurns);
    }
}

void MainWindow::on_StartRestartButton_clicked() {
    on_progressBar_valueChanged(0);

    // Makes it so that the user cannot click the buttons.
    ui->BlueButton->setEnabled(false);
    ui->RedButton->setEnabled(false);

    // Checks if we need to update the high score in the file.
    if(getHighScore() < highScore) {
        setHighScore(highScore);
    }

    // Makes sure the text is correct and delays the game when restarting.
    if(ui->StartRestartButton->text() == "START"){
        ui->StartRestartButton->setText("RESTART");
    }
    else {
        delay(1000);
    }

    // Creates a new gamelogic so that we don't get repetition.
    computer = new gamelogic();
    currentTurn = 0;
    totalTurns = 1;
    hints = 1;
    updateScoreText();
    QString hintText = QString("HINTS: %1").arg(hints);
    ui->HintButton->setText(hintText);

    // Lets the computer have its turn.
    computerTurn();
}

void MainWindow::on_HintButton_clicked() {
    if (computer->getColor(currentTurn + 1) == 0) {
        setRedButtonToRed();
    }
    else {
        setBlueButtonToBlue();
    }

    hints--;

    QString hintText = QString("HINTS: %1").arg(hints);
    ui->HintButton->setText(hintText);

    if (hints < 1) {
        ui->HintButton->setEnabled(false);
    }
}

/*
 * The methods below will all change a button to a different color,
 * the names are very self-explanatory.
 */
void MainWindow::setRedButtonToGray() {
    ui->RedButton->setStyleSheet("background-color:lightgray");
}

void MainWindow::setBlueButtonToGray() {
    ui->BlueButton->setStyleSheet("background-color:lightgray");
}

void MainWindow::setRedButtonToRed() {
    ui->RedButton->setStyleSheet("background-color:red");
    QTimer::singleShot(150, this, SLOT(setRedButtonToGray()));
}

void MainWindow::setBlueButtonToBlue() {
    ui->BlueButton->setStyleSheet("background-color:blue");
    QTimer::singleShot(150, this, SLOT(setBlueButtonToGray()));
}

void MainWindow::on_progressBar_valueChanged(int value) {
    ui->progressBar->setValue(value);
}

void MainWindow::computerTurn() {
    // Makes it so that the user cannot click the buttons.
    ui->BlueButton->setEnabled(false);
    ui->RedButton->setEnabled(false);
    ui->HintButton->setEnabled(false);

    // Gives a short delay before the computer's turn starts.
    delay(1000);

    currentTurn = 0;

    // Will add hints based on how many levels you have got to.
    if ((totalTurns - 1) % 5 == 0) {
        hints = hints + ((totalTurns - 1) / 5);
        QString hintText = QString("HINTS: %1").arg(hints);
        ui->HintButton->setText(hintText);
    }

    on_progressBar_valueChanged(0);

    // Runs through the turns for the computer and shows the user those turns.
    for (int i = 1; i <= totalTurns; i++) {
        // This will show the color to the user.
        if (computer->getColor(i) == 0) {
            setRedButtonToRed();
        }
        else {
            setBlueButtonToBlue();
        }

        // Decreases the delays the further you get into the game.
        if (totalTurns * 50 <= 900) {
            delay(1000 - 50 * totalTurns);
        }
        else
        {
            delay(100);
        }
    }

    // After the computer is done the user can click the buttons.
    ui->BlueButton->setEnabled(true);
    ui->RedButton->setEnabled(true);
    if (hints > 0) {
        ui->HintButton->setEnabled(true);
    }
}

/*
 * Idea found at http://stackoverflow.com/questions/3752742/how-do-i-create-a-pause-wait-function-using-qt.
 */
void MainWindow::delay(int time) {
    // Adds the amount of time given to the current time.
    QTime endTime= QTime::currentTime().addMSecs(time);
    // Kills the processes until we are at the time computed above.
    while (QTime::currentTime() < endTime) {
        QCoreApplication::processEvents(QEventLoop::AllEvents, 100);
    }
}

void MainWindow::loseGame(int round) {
    // Makes it so that the user cannot click the buttons.
    ui->BlueButton->setEnabled(false);
    ui->RedButton->setEnabled(false);
    QMessageBox* messageBox = new QMessageBox();
    QString text;

    // Checks if we need to update the high score.
    if(getHighScore() < highScore) {
        setHighScore(highScore);
        text = QString("Wrong Pattern! \nYou lost after %1 round(s) played \nand set a new high score!").arg(round);
    }
    else {
        text = QString("Wrong Pattern! \nYou lost after %1 round(s) played.").arg(round);
    }
    // Writes everything to the message box and presents it to the user.
    messageBox->setText(text);
    messageBox->show();
}

void MainWindow::setHighScore(int score) {
    // Writes the score to the file.
    QFile file("SimonHighScore.txt");
    if (file.open(QIODevice::ReadWrite))
    {
        QTextStream stream(&file);
        stream << score << endl;
    }
}

int MainWindow::getHighScore() {
    // This will read in the high score stored in the file listed below.
    QFile file("SimonHighScore.txt");
    if(!file.open(QIODevice::ReadOnly)) {
        setHighScore(0);
    }

    // Reads the first and only line of the file.
    QTextStream input(&file);
    return input.readLine().toInt();
}

void MainWindow::updateScoreText() {
    // This will update the current score after every round.
    QString text = QString("Score: %1").arg(totalTurns - 1);
    ui->Score->setText(text);
}

void MainWindow::updateHighScoreText() {
    // This will update the high score.
    QString text = QString("High Score: %1").arg(highScore);
    ui->HighScore->setText(text);
}

void MainWindow::closeEvent(QCloseEvent *event){
    // Checks if we need to update the high score before we close the program.
    if(getHighScore() < highScore) {
        setHighScore(highScore);
    }
    event->accept();
}
