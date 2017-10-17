#include "gamepage.h"
#include "ui_gamepage.h"
#include "gameview.h"
#include <QPixmap>
#include <QIcon>
#include <QSize>

GamePage::GamePage(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::GamePage)
{
    ui->setupUi(this);

    QPixmap bomb(":/Images/bomb.png");
    QIcon bombIcon(bomb);
    ui->bomb->setIcon(bombIcon);
    ui->bomb->setIconSize(QSize(100,100));

    QPixmap pow(":/Images/pow");
    QIcon powIcon(pow);
    ui->setOffBombs->setIcon(powIcon);
    ui->setOffBombs->setIconSize(pow.rect().size());

    QPixmap tnt(":/Images/tnt.png");
    QIcon tntIcon(tnt);
    ui->cutter->setIcon(tntIcon);
    ui->cutter->setIconSize(tnt.rect().size());

    QPixmap cutter(":/Images/thermiteCutter.png");
    QIcon cutterIcon(cutter);
    ui->realCutter->setIcon(cutterIcon);
    ui->realCutter->setIconSize(cutter.rect().size());
}

GamePage::~GamePage()
{
    delete ui;
}

void GamePage::resetLevel()
{
    ui->gameview->reset();
}

void GamePage::loadLevelJSON(QString levelJSONString) {
    ui->gameview->loadJSON(levelJSONString.toStdString());
    ui->gameview->reset();
}

void GamePage::on_bombbuttons_buttonClicked(int id) {
    ui->gameview->buttonCallback(id);
}

void GamePage::on_gameMenuButton_clicked()
{
    emit gameMenu_button_clicked();
}
