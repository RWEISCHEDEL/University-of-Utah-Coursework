#include "losepage.h"
#include "ui_losepage.h"

LosePage::LosePage(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::LosePage)
{
    ui->setupUi(this);

    QPixmap pix(":/Images/fail.jpeg");
    ui->patrickFail->setPixmap(pix);

    //set widget background color
    QPalette Pal(palette());
    Pal.setColor(QPalette::Background, Qt::white);
    this->setAutoFillBackground(true);
    this->setPalette(Pal);
}

LosePage::~LosePage()
{
    delete ui;
}

void LosePage::on_restartLevelButton_clicked()
{
    emit restartLevelButton_clicked();
}

void LosePage::on_gameMenuButton_clicked()
{
    emit gameMenuButton_clicked();
}

void LosePage::on_getHelpButton_clicked()
{
    emit getHelpButton_clicked();
}

void LosePage::on_getEducatedButton_clicked()
{
    emit getEducatedButton_clicked();
}
