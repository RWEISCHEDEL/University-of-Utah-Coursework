#include "adminpage.h"
#include "ui_adminpage.h"

AdminPage::AdminPage(QWidget *parent) : QWidget(parent), ui(new Ui::AdminPage) {
    ui->setupUi(this);

    //set widget background color
    QPalette Pal(palette());
    Pal.setColor(QPalette::Background, Qt::white);
    this->setAutoFillBackground(true);
    this->setPalette(Pal);
}

AdminPage::~AdminPage() {
    delete ui;
}

void AdminPage::paintEvent(QPaintEvent *) {
    QStyleOption opt;
    opt.init(this);
    QPainter p(this);
    style()->drawPrimitive(QStyle::PE_Widget, &opt, &p, this);
}

void AdminPage::on_pushButtonBack_clicked() {
    emit pushButtonBack_clicked();
}

// get list of users from server
void AdminPage::on_pushButtonUsers_clicked() {

    // set ui label and populating content
    ui->labelWebViewDescription->setText("List of Users");
    ui->webView->load(QUrl("http://www.pichunts.com:8080/Explosion/explosion/admin/users"));

}

// for each user, get average score of all games played
void AdminPage::on_pushButtonAvgScore_clicked() {

    // set ui label and populating content
    ui->labelWebViewDescription->setText("Average Score for Each Player");
    ui->webView->load(QUrl("http://www.pichunts.com:8080/Explosion/explosion/users/tavg"));

}

// for each user, get average score for all games played, by level
void AdminPage::on_pushButtonAvgScoreByLevel_clicked() {

    // set ui label and populating content
    ui->labelWebViewDescription->setText("Average Score by Level for Each Player");
    ui->webView->load(QUrl("http://www.pichunts.com:8080/Explosion/explosion/users/lavg"));
}

// for each user, get the total number games played
void AdminPage::on_pushButtonNumGames_clicked() {

    // set ui label and populating content
    ui->labelWebViewDescription->setText("Total Number of Games Played for Each Player");
    ui->webView->load(QUrl("http://www.pichunts.com:8080/Explosion/explosion/users/tgames"));
}

//
void AdminPage::on_pushButtonNumGamesPerLevel_clicked() {

    // set ui label and populating content
    ui->labelWebViewDescription->setText("Total Number of Games Played by Level for Each Player");
    ui->webView->load(QUrl("http://www.pichunts.com:8080/Explosion/explosion/users/lvltot"));
}
