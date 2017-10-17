#include "gamemenupage.h"
#include "ui_gamemenupage.h"
#include <QPainter>
#include <QDebug>

GameMenuPage::GameMenuPage(QWidget *parent) : QWidget(parent), ui(new Ui::GameMenuPage) {
    ui->setupUi(this);

    //set widget background color
    QPalette Pal(palette());
    Pal.setColor(QPalette::Background, Qt::white);
    this->setAutoFillBackground(true);
    this->setPalette(Pal);
}

GameMenuPage::~GameMenuPage() {
    delete ui;
}

void GameMenuPage::paintEvent(QPaintEvent *) {
    QStyleOption opt;
    opt.init(this);
    QPainter p(this);
    style()->drawPrimitive(QStyle::PE_Widget, &opt, &p, this);
}

void GameMenuPage::widgetMadeVisibleCheckUser(int pageInt) {
//    qDebug() << pageInt;
    if (pageInt == 0) {
        // qDebug() << "property of isAdmin in game menu" << qApp->property("userIsAdministrator");
        if (qApp->property("userIsAdministrator").toBool()) {
            ui->pushButtonAdmin->setVisible(true);
        }
        else {
            ui->pushButtonAdmin->setVisible(false);
        }
    }
    if (qApp->property("userGlobalStr") != "") {
        ui->pushButtonLogOut->setVisible(true);
        ui->pushButtonLogIn->setVisible(false);
        ui->levelsButton->setVisible(true);             //TODO: Tanner, if you want these to always be visible, just comment this code out.
        ui->levelEditorButton->setVisible(true);        //TODO: Tanner, if you want these to always be visible, just comment this code out.
    }
    else {
        ui->levelsButton->setVisible(false);            //TODO: Tanner, if you want these to always be visible, just comment this code out.
        ui->levelEditorButton->setVisible(false);       //TODO: Tanner, if you want these to always be visible, just comment this code out.
        ui->pushButtonLogOut->setVisible(false);
        ui->pushButtonLogIn->setVisible(true);
    }
    // turn off this button until shop page is deleted
    ui->shopButton->setVisible(false);
}

void GameMenuPage::on_levelsButton_clicked() {
    emit levelsButton_clicked();
}

void GameMenuPage::on_levelEditorButton_clicked() {
    emit levelEditorButton_clicked();
}

void GameMenuPage::on_shopButton_clicked() {
    emit shopButton_clicked();
}

void GameMenuPage::on_helpButton_clicked() {
    emit helpButton_clicked();
}

void GameMenuPage::on_educationButton_clicked() {
    emit educationButton_clicked();
}

void GameMenuPage::on_pushButtonAdmin_clicked() {
    emit adminButton_clicked();
}

void GameMenuPage::on_pushButtonLogOut_clicked() {
    qApp->setProperty("userGlobalStr", "");
    qApp->setProperty("userIsAdministrator", "");
    emit logoutButton_clicked();
}

void GameMenuPage::on_pushButtonLogIn_clicked() {
    emit loginButton_clicked();
}
