#include "helppage.h"
#include "ui_helppage.h"

HelpPage::HelpPage(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::HelpPage)
{
    ui->setupUi(this);
}

HelpPage::~HelpPage()
{
    delete ui;
}

void HelpPage::on_helpBackButton_clicked()
{
    emit helpBackButton_clicked();
}
