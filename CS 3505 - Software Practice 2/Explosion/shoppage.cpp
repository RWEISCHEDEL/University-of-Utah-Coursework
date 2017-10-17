#include "shoppage.h"
#include "ui_shoppage.h"
#include "levelspage.h"
#include <QString>
#include <QPushButton>
#include <QDebug>
#include <QSignalMapper>
#include <QLoggingCategory>
#include "networkconnector.h"
#include <QUrlQuery>

ShopPage::ShopPage(QWidget *parent) : QWidget(parent), ui(new Ui::ShopPage) {
    ui->setupUi(this);
    this->show();

    QSignalMapper* signalMapper = new QSignalMapper (this);

    budget = 10000;
    ui->labelScoreNum->setText(QString::number(budget));

    //connect(levelspage, SIGNAL(level1_Button_clicked(int)), this, SLOT(loadBudget(int)));
    //signalMapper->setMapping(this, 5000);

    ui->labelAction->setText("");
    shopItems.insert(1, "tnt");
    shopItems.insert(2, "c4");
    shopItems.insert(3, "semtex");
    shopItems.insert(4, "thermiteCutter");

    buildStore();
}

ShopPage::~ShopPage() {
    delete ui;
}

void ShopPage::loadBudget(int budgetFromLevel) {
    budget = budgetFromLevel;
    ui->labelScoreNum->setText(QString::number(budgetFromLevel));
}

void ShopPage::buildStore() {
    int count = 0;

    for (auto c : shopItems) {

        // add a new button and give it color
        QPushButton *b = new QPushButton();
        b->setFixedWidth(200);
        b->setFixedHeight(200);
        b->setStyleSheet("QPushButton{background-color: #f0f8ff;}");

        // add button to grid layout formulaically
        ui->horizontalLayoutShop->setAlignment(Qt::AlignLeft);
        ui->horizontalLayoutShop->addWidget(b);

        // add signal (TBD) to button and give button a name/*
        connect(b, SIGNAL (clicked()), this, SLOT (purchaseCharge()));
        QString buttonName = QString ("b%1").arg(count);
        b->setObjectName(buttonName);
        if (count == 0) {
            iconTntCost.addFile(QString::fromUtf8(":/Images/dynamiteCost.png"), QSize(), QIcon::Normal, QIcon::On);
            b->setIcon(iconTntCost);
            b->setIconSize(QSize(180,180));
        }
        else if (count == 1) {
            iconC4Cost.addFile(QString::fromUtf8(":/Images/c4Cost.png"), QSize(), QIcon::Normal, QIcon::On);
            b->setIcon(iconC4Cost);
            b->setIconSize(QSize(180,180));
        }
        else if (count == 2) {
            iconSemtexCost.addFile(QString::fromUtf8(":/Images/semtexCost.png"), QSize(), QIcon::Normal, QIcon::On);
            b->setIcon(iconSemtexCost);
            b->setIconSize(QSize(180, 180));
        }
        else if (count == 3) {
            iconThermiteCutterCost.addFile(QString::fromUtf8(":/Images/thermiteCutterCost.png"), QSize(), QIcon::Normal, QIcon::On);
            b->setIcon(iconThermiteCutterCost);
            b->setIconSize(QSize(180, 180));
        }
        count++;
    }
}

void ShopPage::purchaseCharge() {

    // get the name of the button that was pushed
    QObject *senderObj = sender();
    QString senderObjName = senderObj->objectName();
    if (senderObjName == "b0") senderObjName = "TNT";
    if (senderObjName == "b1") senderObjName = "C4";
    if (senderObjName == "b2") senderObjName = "Semtex";
    if (senderObjName == "b3") senderObjName = "Thermite Cutter";

    // decrement score depending on what item is purchased (available cash)
    if (senderObjName == "TNT" && budget > 500) {
        budget = budget - 500;
        ui->labelAction->setText("You purchased a "+senderObjName);
        ui->labelScoreNum->setText(QString::number(budget));
        emit purchasedTNT();
    }
    else if (senderObjName == "C4" && budget > 1000) {
        budget = budget - 1000;
        ui->labelAction->setText("You purchased a "+senderObjName);
        ui->labelScoreNum->setText(QString::number(budget));
        emit purchasedC4();
    }
    else if (senderObjName == ("Semtex") && budget > 2500) {
        budget = budget - 2500;
        ui->labelAction->setText("You purchased a "+senderObjName);
        ui->labelScoreNum->setText(QString::number(budget));
        emit purchasedSemtex();
    }
    else if (senderObjName == "Thermite Cutter" && budget > 5000) {
         budget = budget - 5000;
         ui->labelAction->setText("You purchased a "+senderObjName);
         ui->labelScoreNum->setText(QString::number(budget));
         emit purchasedThermiteCutter();
}
    else {
        ui->labelAction->setText("Not enough money to purchase a "+senderObjName);
    }

}

void ShopPage::on_shopBackButton_clicked() {
    emit shopBackButton_clicked();
}

// method that sends data to Nick's server and gets databack
void ShopPage::on_pushButtonSendToServer_clicked() {

    // turns off ssl warnings
    //QLoggingCategory::setFilterRules("qt.network.ssl.warning=false");

    QNetworkAccessManager *manager = new QNetworkAccessManager(this);
    QNetworkRequest request;

    connect(manager, SIGNAL(finished(QNetworkReply*)), this, SLOT(replyFinished(QNetworkReply*)));

    request.setUrl(QUrl("http://www.pichunts.com:8080/Explosion/explosion/hello"));
    request.setRawHeader("Accept", "text/html");

    qDebug() << request.header(QNetworkRequest::ContentTypeHeader);
    manager->get(request);

}

// slot
void ShopPage::replyFinished(QNetworkReply* effit) {

    if (effit->error()) {
        qDebug() << "Error!";
        qDebug() << effit->errorString();
    }
    else { // output contents
        qDebug() << effit->header(QNetworkRequest::ContentTypeHeader).toString();
        qDebug() << effit->attribute(QNetworkRequest::HttpStatusCodeAttribute).toInt();
        qDebug() << effit->attribute(QNetworkRequest::HttpReasonPhraseAttribute).toString();
        QByteArray arr = effit->readAll();
        qDebug() << "Success" << QString(arr);
        ui->lineFromServer->setText(QString(arr));
    }
    effit->deleteLater();
}

void ShopPage::on_pushButtonLogin_clicked() {

//    NetworkConnector* net = new NetworkConnector();
//    net.logIn("test", "testpass");


    //---------------------The following block will work if uncommented------------------------
    QString user = "test";
    QString pass = "testpass";

    QNetworkAccessManager *manager = new QNetworkAccessManager(this);
    QNetworkRequest request;

    connect(manager, SIGNAL(finished(QNetworkReply*)), this, SLOT(replyFinished(QNetworkReply*)));

    // build http query that adds user and pass to URL
    QUrl url("http://www.pichunts.com:8080/Explosion/explosion/auth");
    QUrlQuery query;
    query.addQueryItem("username", user);
    query.addQueryItem("password", pass);
    url.setQuery(query.query());
    qDebug() << url;

    request.setUrl(QUrl(url));
    request.setRawHeader("Accept", "text/plain");

    qDebug() << request.header(QNetworkRequest::ContentTypeHeader);
    manager->get(request);
}
