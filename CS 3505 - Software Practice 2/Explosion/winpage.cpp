#include "winpage.h"
#include "ui_winpage.h"

WinPage::WinPage(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::WinPage)
{
    ui->setupUi(this);

    QPixmap pix(":/Images/thumbs_up.jpg");
    ui->thumbsUp->setPixmap(pix);

    //set widget background color
    QPalette Pal(palette());
    Pal.setColor(QPalette::Background, Qt::white);
    this->setAutoFillBackground(true);
    this->setPalette(Pal);
}

WinPage::~WinPage()
{
    delete ui;
}

void WinPage::on_chooseLevelButton_clicked()
{
    emit chooseLevelButton_clicked();
}

void WinPage::on_getEducatedButton_clicked()
{
    emit getEducatedButton_clicked();
}

void WinPage::on_gameMenuButton_clicked()
{
    emit gameMenuButton_clicked();
}

void WinPage::on_restartLevelButton_clicked()
{
    emit restartLevelButton_clicked();
}
