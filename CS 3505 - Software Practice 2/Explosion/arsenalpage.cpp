#include "arsenalpage.h"
#include "ui_arsenalpage.h"
#include <QDebug>
#include <QPalette>
#include <QColor>
#include <QPushButton>
#include <qmath.h>

ArsenalPage::ArsenalPage(QWidget *parent) : QWidget(parent), ui(new Ui::ArsenalPage) {
    ui->setupUi(this);

    // set color
    QPalette Pal(palette());
    QColor backColor = QColor("#f0f8ff");
    Pal.setColor(QPalette::Background, backColor);
    this->setAutoFillBackground(true);
    this->setPalette(Pal);
    // set size
    // this->setMaximumSize(200,100);
    // this->setMinimumSize(100,50);
    this->show();

    layoutTNTButtons(7);
    layoutC4Buttons(5);
    layoutSemtexButtons(2);
    layoutThermiteCutterButtons(3);
}

ArsenalPage::~ArsenalPage() {
    delete ui;
}

void ArsenalPage::useCharge(QString chargeType) {
    QVector<QPushButton*>* buttonVec;
    void (ArsenalPage::*layoutFunction)(int); // variable that points to a layout function that takes int and is void (function pointer)

    if (chargeType == "TNT") {
        buttonVec = &listOfActiveTNT;
        layoutFunction = &ArsenalPage::layoutTNTButtons;
    }
    else if (chargeType == "C4") {
        buttonVec = &listOfActiveC4;
        layoutFunction = &ArsenalPage::layoutC4Buttons;
    }

    int numCharge = buttonVec->size()-1;
    for (QPushButton* but : *buttonVec) {
        delete but;
    }

    buttonVec->clear();
    // make the layout generic
    (this->*layoutFunction)(numCharge);
}

void ArsenalPage::layoutTNTButtons(int numTNT) {
    int count = 0;

    if (numTNT == 0) {
        ui->labelArsenal->setText("No more TNT");
    }

    for (int i = 0; i < numTNT; i++) {
        chargeTNT.append("tnt");
    }

    for (auto c : chargeTNT) {

        // add a new button and give it color
        QPushButton *b = new QPushButton();
        b->setFixedWidth(20);
        b->setFixedHeight(20);
        b->setStyleSheet("QPushButton{background-color: #f0f8ff; border: none}");

        // add button to grid layout formulaically
        ui->horizontalLayoutTNT->setAlignment(Qt::AlignLeft);
        ui->horizontalLayoutTNT->addWidget(b);

        // add signal (TBD) to button and give button a name/*
        connect(b, SIGNAL (clicked()), this, SLOT (useTntCharge()));
        QString buttonName = QString ("TNT%1").arg(count);
        b->setObjectName(buttonName);
        iconTNT.addFile(QString::fromUtf8(":/Images/dynamite.png"), QSize(), QIcon::Normal, QIcon::On);
        b->setIcon(iconTNT);
        b->setIconSize(QSize(20,20));

        tntMap.insert(buttonName, c);
        listOfActiveTNT.push_back(b);
        count++;
    }
}

void ArsenalPage::useTntCharge() {

    // print the button that was pushed
    QObject *senderObj = sender();
    QString senderObjName = senderObj->objectName();
    ui->labelArsenal->setText("Pushed TNT button named "+senderObjName);

    // delete one TNT charge from list to get new list size
    int numTNT = listOfActiveTNT.size()-1;

    // delete tnt buttons for mem management
    for (QPushButton* but : listOfActiveTNT) {
        delete but;
    }

    // clear lists then rebuild arsenal to new size
    listOfActiveTNT.clear();
    tntMap.clear();
    chargeTNT.clear();
    layoutTNTButtons(numTNT);
}

void ArsenalPage::addPurchasedTNT() {

    // add one TNT and relayout the buttons
    int numTNT = chargeTNT.size()+1;

    // delete buttons for mem management
    for (QPushButton* but : listOfActiveTNT) {
        delete but;
    }

    // clear lists then rebuild arsenal to new size
    listOfActiveTNT.clear();
    tntMap.clear();
    chargeTNT.clear();
    layoutTNTButtons(numTNT);
}

void ArsenalPage::layoutC4Buttons(int numC4) {
    int count = 0;

    for (int i = 0; i < numC4; i++) {
        chargeC4.append("c4");
    }

    if (numC4 == 0) {
        ui->labelArsenal->setText("No more C4");
    }

    for (auto c : chargeC4) {
        // add a new button and give it color
        QPushButton *b = new QPushButton();
        b->setFixedWidth(20);
        b->setFixedHeight(20);
        b->setStyleSheet("QPushButton{background-color: #f0f8ff; border: none}");

        // add button to grid layout formulaically
        ui->horizontalLayoutC4->setAlignment(Qt::AlignLeft);
        ui->horizontalLayoutC4->addWidget(b);

        // add signal (TBD) to button and give button a name/*
        connect(b, SIGNAL (clicked()), this, SLOT (useC4()));
        QString buttonName = QString ("C4%1").arg(count);
        b->setObjectName(buttonName);
        iconC4.addFile(QString::fromUtf8(":/Images/c4.png"), QSize(), QIcon::Normal, QIcon::On);
        b->setIcon(iconC4);
        b->setIconSize(QSize(20,20));

        c4Map.insert(buttonName, c);
        listOfActiveC4.push_back(b);
        count++;
    }
}

void ArsenalPage::useC4() {

    // print the button that was pushed
    QObject *senderObj = sender();
    QString senderObjName = senderObj->objectName();
    ui->labelArsenal->setText("Pushed C4 button named "+senderObjName);

    // decrement bomb list size, delete buttons for mem management
    int numC4 = listOfActiveC4.size()-1;
    for (QPushButton* but : listOfActiveC4) {
         delete but;
    }

    // clear lists then rebuild arsenal to new size
    listOfActiveC4.clear();
    c4Map.clear();
    chargeC4.clear();
    layoutC4Buttons(numC4);
}

void ArsenalPage::addPurchasedC4() {

    // add one Bomb and relayout the buttons
    int numC4 = chargeC4.size()+1;

    // delete buttons for mem management
    for (QPushButton* but : listOfActiveC4) {
        delete but;
    }

    // clear lists then rebuild arsenal to new size
    listOfActiveC4.clear();
    c4Map.clear();
    chargeC4.clear();
    layoutC4Buttons(numC4);
}

void ArsenalPage::layoutSemtexButtons(int numSemtex) {
    int count = 0;

    for (int i = 0; i < numSemtex; i++) {
        chargeSemtex.append("semtex");
    }

    if (numSemtex == 0) {
        ui->labelArsenal->setText("No more Semtex");
    }

    for (auto c : chargeSemtex) {
        // add a new button and give it color
        QPushButton *b = new QPushButton();
        b->setFixedWidth(20);
        b->setFixedHeight(20);
        b->setStyleSheet("QPushButton{background-color: #f0f8ff; border: none}");

        // add button to grid layout formulaically
        ui->horizontalLayoutSemtex->setAlignment(Qt::AlignLeft);
        ui->horizontalLayoutSemtex->addWidget(b);

        // add signal (TBD) to button and give button a name/*
        connect(b, SIGNAL (clicked()), this, SLOT (useSemtex()));
        QString buttonName = QString ("Semtex%1").arg(count);
        b->setObjectName(buttonName);
        iconSemtex.addFile(QString::fromUtf8(":/Images/semtex.png"), QSize(), QIcon::Normal, QIcon::On);
        b->setIcon(iconSemtex);
        b->setIconSize(QSize(20,20));

        semtexMap.insert(buttonName, c);
        listOfActiveSemtex.push_back(b);
        count++;
    }
}

void ArsenalPage::useSemtex() {

    // print the button that was pushed
    QObject *senderObj = sender();
    QString senderObjName = senderObj->objectName();
    ui->labelArsenal->setText("Pushed Semtex button named "+senderObjName);

    // decrement semtex list size, delete buttons for mem management
    int numSemtex = listOfActiveSemtex.size()-1;
    for (QPushButton* but : listOfActiveSemtex) {
         delete but;
    }

    // clear lists then rebuild arsenal to new size
    listOfActiveSemtex.clear();
    semtexMap.clear();
    chargeSemtex.clear();
    layoutSemtexButtons(numSemtex);
}

void ArsenalPage::addPurchasedSemtex() {

    // add one Bomb and relayout the buttons
    int numSemtex = chargeSemtex.size()+1;

    // delete buttons for mem management
    for (QPushButton* but : listOfActiveSemtex) {
        delete but;
    }

    // clear lists then rebuild arsenal to new size
    listOfActiveSemtex.clear();
    semtexMap.clear();
    chargeSemtex.clear();
    layoutSemtexButtons(numSemtex);
}

void ArsenalPage::layoutThermiteCutterButtons(int numThermiteCutter) {
    int count = 0;

    for (int i = 0; i < numThermiteCutter; i++) {
        chargeThermiteCutter.append("thermite cutter");
    }

    if (numThermiteCutter == 0) {
        ui->labelArsenal->setText("No more Thermite Cutters");
    }

    for (auto c : chargeThermiteCutter) {
        // add a new button and give it color
        QPushButton *b = new QPushButton();
        b->setFixedWidth(20);
        b->setFixedHeight(20);
        b->setStyleSheet("QPushButton{background-color: #f0f8ff; border: none}");

        // add button to grid layout formulaically
        ui->horizontalLayoutThermite->setAlignment(Qt::AlignLeft);
        ui->horizontalLayoutThermite->addWidget(b);

        // add signal (TBD) to button and give button a name/*
        connect(b, SIGNAL (clicked()), this, SLOT (useThermiteCutter()));
        QString buttonName = QString ("Thermite%1").arg(count);
        b->setObjectName(buttonName);
        iconThermiteCutter.addFile(QString::fromUtf8(":/Images/thermiteCutter.png"), QSize(), QIcon::Normal, QIcon::On);
        b->setIcon(iconThermiteCutter);
        b->setIconSize(QSize(20,20));

        thermiteCutterMap.insert(buttonName, c);
        listOfActiveThermiteCutter.push_back(b);
        count++;
    }
}

void ArsenalPage::useThermiteCutter() {

    // print the button that was pushed
    QObject *senderObj = sender();
    QString senderObjName = senderObj->objectName();
    ui->labelArsenal->setText("Pushed Thermite Cutter button named "+senderObjName);

    // decrement thermite cutter list size, delete buttons for mem management
    int numThermiteCutter = listOfActiveThermiteCutter.size()-1;
    for (QPushButton* but : listOfActiveThermiteCutter) {
         delete but;
    }

    // clear lists then rebuild arsenal to new size
    listOfActiveThermiteCutter.clear();
    thermiteCutterMap.clear();
    chargeThermiteCutter.clear();
    layoutThermiteCutterButtons(numThermiteCutter);
}

void ArsenalPage::addPurchasedThermiteCutter() {

    // add one thermite and relayout the buttons
    int numThermiteCutter = chargeThermiteCutter.size()+1;

    // delete buttons for mem management
    for (QPushButton* but : listOfActiveThermiteCutter) {
        delete but;
    }

    // clear lists then rebuild arsenal to new size
    listOfActiveThermiteCutter.clear();
    thermiteCutterMap.clear();
    chargeThermiteCutter.clear();
    layoutThermiteCutterButtons(numThermiteCutter);
}
