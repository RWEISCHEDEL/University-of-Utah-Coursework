#ifndef GAMEPAGE_H
#define GAMEPAGE_H

#include <QWidget>
#include "Box2D/Box2D.h"

namespace Ui {
class GamePage;
}

class GamePage : public QWidget
{
    Q_OBJECT
signals:
    void gameMenu_button_clicked();
public:
    explicit GamePage(QWidget *parent = 0);
    ~GamePage();
//    void resetLevel();
    void loadLevel(b2World newLevel);

    void loadLevelJSON(QString levelJSONString);

public slots:
    void on_bombbuttons_buttonClicked(int id);
    void resetLevel();

private slots:
    void on_gameMenuButton_clicked();

private:
    Ui::GamePage *ui;
};

#endif // GAMEPAGE_H
