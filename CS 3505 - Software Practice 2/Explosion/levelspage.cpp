#include "levelspage.h"
#include "ui_levelspage.h"
#include <QPainter>

LevelsPage::LevelsPage(QWidget *parent) : QWidget(parent), ui(new Ui::LevelsPage) {
    ui->setupUi(this);

    // set color
    QPalette Pal(palette());
    QColor backColor = QColor("#f0f8ff");
    Pal.setColor(QPalette::Background, Qt::white);
    this->setAutoFillBackground(true);
    this->setPalette(Pal);

    // set size
    //this->show();
    QPixmap pix1(":/Images/lev1");
    QIcon icon1(pix1);
    ui->level1Button->setIcon(icon1);
    ui->level1Button->setIconSize(pix1.size());


    QPixmap pix2(":/Images/lev2");
    QIcon icon2(pix2);
    ui->level2Button->setIcon(icon2);
    ui->level2Button->setIconSize(pix2.size());

    QPixmap pix3(":/Images/lev3");
    QIcon icon3(pix3);
    ui->level3Button->setIcon(icon3);
    ui->level3Button->setIconSize(pix3.size());

    QPixmap pix4(":/Images/lev4");
    QIcon icon4(pix4);
    ui->level4Button->setIcon(icon4);
    ui->level4Button->setIconSize(pix4.size());

    QPixmap pix5(":/Images/lev5");
    QIcon icon5(pix5);
    ui->level5Button->setIcon(icon5);
    ui->level5Button->setIconSize(pix5.size());
}

LevelsPage::~LevelsPage() {
    delete ui;
}

void LevelsPage::paintEvent(QPaintEvent *)
{
    QStyleOption opt;
    opt.init(this);
    QPainter p(this);
    style()->drawPrimitive(QStyle::PE_Widget, &opt, &p, this);
}

void LevelsPage::on_levelsBackButton_clicked() {
    emit levelsBackButton_clicked();
}

void LevelsPage::on_level1Button_clicked() {

    // set global property for user level
    qApp->setProperty("userGlobalLevel", 1);
    emit show_gamePage();
    emit reset_game();
    emit load_Level1();
    emit send_Budget(5000);
}

void LevelsPage::on_level2Button_clicked() {
    // set global property for user level
    qApp->setProperty("userGlobalLevel", 2);
    emit show_gamePage();
    emit reset_game();
    emit load_level2();
    emit send_Budget(10000);
}

void LevelsPage::on_level3Button_clicked()
{
    // set global property for user level
    qApp->setProperty("userGlobalLevel", 3);
    emit load_level3();
    emit send_Budget(15000);
}

void LevelsPage::on_level4Button_clicked()
{
    // set global property for user level
    qApp->setProperty("userGlobalLevel", 4);
    emit load_level4();
    emit send_Budget(20000);
}

void LevelsPage::on_level5Button_clicked()
{
    // set global property for user level
    qApp->setProperty("userGlobalLevel", 5);
    emit load_level5();
    emit send_Budget(25000);
}
