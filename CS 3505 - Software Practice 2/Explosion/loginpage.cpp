#include "loginpage.h"
#include "ui_loginpage.h"
#include <QDebug>
#include <QPainter>
#include <QSettings>

LoginPage::LoginPage(QWidget *parent) : QWidget(parent), ui(new Ui::LoginPage) {

    ui->setupUi(this);

    //set widget background color
    QPalette Pal(palette());
    Pal.setColor(QPalette::Background, Qt::white);
    this->setAutoFillBackground(true);
    this->setPalette(Pal);

    ui->passwordField->setEchoMode(QLineEdit::Password);
}

LoginPage::~LoginPage() {
    delete ui;
}

void LoginPage::paintEvent(QPaintEvent *) {
    QStyleOption opt;
    opt.init(this);
    QPainter p(this);
    style()->drawPrimitive(QStyle::PE_Widget, &opt, &p, this);
}

//Sets focus on username field when page shows
void LoginPage::showEvent(QShowEvent *e) {
    ui->usernameField->setFocus();
    QWidget::showEvent(e);
}

/**
 * When LOGIN button is clicked, get data from user, and send it to server.
 * @brief LoginPage::on_loginButton_clicked
 */
void LoginPage::on_loginButton_clicked() {
    QString user = ui->usernameField->text();
    QString password = ui->passwordField->text();

    // make connection to server
    QNetworkAccessManager *manager = new QNetworkAccessManager(this);
    QNetworkRequest request;
    connect(manager, SIGNAL(finished(QNetworkReply*)), this, SLOT(loginReplyFinished(QNetworkReply*)));
    QUrl url("http://www.pichunts.com:8080/Explosion/explosion/auth");
    QUrlQuery query;
    query.addQueryItem("username", user);
    query.addQueryItem("password", password);
    url.setQuery(query.query());

    // set global property for user
    qApp->setProperty("userGlobalStr", user);

    // header info
    request.setUrl(QUrl(url));
    request.setRawHeader("Accept", "application/json");
    manager->get(request);
}

/**
 * Slot to return and analyze the login response data from the server.
 * @brief LoginPage::loginReplyFinished
 * @param effit
 */
void LoginPage::loginReplyFinished(QNetworkReply* effit) {

    QByteArray loginArr;

    if (effit->error()) {
        qDebug() << "Error!";
        qDebug() << effit->errorString();
    }
    // if no error, then get contents from server reply
    else {
        loginArr = effit->readAll();
        // All of the following comments can be uncommented in order to debug network connection errors.
        // qDebug() << effit->header(QNetworkRequest::ContentTypeHeader).toString();
        // qDebug() << effit->attribute(QNetworkRequest::HttpStatusCodeAttribute).toInt();
        // qDebug() << effit->attribute(QNetworkRequest::HttpReasonPhraseAttribute).toString();
        // qDebug() << "Success" << QString(loginArr);
        // ui->errorLabel->setText(QString(loginArr));

        // parse JSON
        QJsonDocument jsonRaw(QJsonDocument::fromJson(loginArr));
        QJsonObject json = jsonRaw.object();

        bool myBool = json["isValid"].toBool();
        bool isAdmin = json["isAdmin"].toBool();
        QString str = json["reason"].toString();

        // set QNetworkReply to be deleted
        effit->deleteLater();

        if (isAdmin) {
            qApp->setProperty("userIsAdministrator", "true");
        }
        else {
            qApp->setProperty("userIsAdministrator", "false");
        }

        // if login is valid, then emit signal, save user settings
        if (myBool) {
            emit loginButton_clicked();
        }
        else {
            ui->errorLabel->setText(str);
            qApp->setProperty("userGlobalStr", "");
            return;
        }
    }
}

/**
 * Whenever the stackedwidget being shown changes, clear the loginpage
 * @brief LoginPage::widgetMadeVisibleClearForm
 */
void LoginPage::widgetMadeVisibleClearForm(int) {
    ui->errorLabel->clear();
    ui->passwordField->clear();
    ui->usernameField->clear();
}

void LoginPage::on_registerButton_clicked() {
    emit loginRegisterButton_clicked();
}
