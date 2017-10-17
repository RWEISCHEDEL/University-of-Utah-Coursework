#include "educationpage.h"
#include "ui_educationpage.h"

EducationPage::EducationPage(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::EducationPage)
{
    ui->setupUi(this);

}

EducationPage::~EducationPage()
{
    delete ui;
}

void EducationPage::paintEvent(QPaintEvent *)
{
    QStyleOption opt;
    opt.init(this);
    QPainter p(this);
    style()->drawPrimitive(QStyle::PE_Widget, &opt, &p, this);
}

void EducationPage::on_educationBackButton_clicked()
{
    emit educationBackButton_clicked();
}
