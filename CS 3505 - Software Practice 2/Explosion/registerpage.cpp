#include "registerpage.h"
#include "ui_registerpage.h"
#include "loginpage.h"

RegisterPage::RegisterPage(QWidget *parent) : QWidget(parent), ui(new Ui::RegisterPage) {
    ui->setupUi(this);
    ui->statusLabel->setText("");

    //set widget background color
    QPalette Pal(palette());
    Pal.setColor(QPalette::Background, Qt::white);
    this->setAutoFillBackground(true);
    this->setPalette(Pal);
}

RegisterPage::~RegisterPage() {
    delete ui;
}

void RegisterPage::paintEvent(QPaintEvent *) {
    QStyleOption opt;
    opt.init(this);
    QPainter p(this);
    style()->drawPrimitive(QStyle::PE_Widget, &opt, &p, this);
}

void RegisterPage::showEvent(QShowEvent *e) {
    ui->usernameTextBox->setFocus();
    QWidget::showEvent(e);
}

void RegisterPage::clearAllFields() {
    ui->usernameTextBox->clear();
    ui->passwordTextBox->clear();
    ui->passwordTextBox2->clear();
}

void RegisterPage::on_registerButton_clicked() {
    QString user = ui->usernameTextBox->text();
    QString password = ui->passwordTextBox->text();
    QString passwordCheck = ui->passwordTextBox2->text();

    // We don't want the user to enter an empty user or password.
    if (user.length() == 0) {
        ui->statusLabel->setText("Please enter a non-empty username.");
        return;
    }
    else if (password.length() == 0) {
        ui->statusLabel->setText("Please enter a non-empty password.");
        return;
    }
    else if (password != passwordCheck) {
        ui->statusLabel->setText("Both passwords entered do not match.");
        return;
    }
    // connect to the server and dbase to check username isn't used already
    else {
        ui->statusLabel->setText("Connecting ...");

        // make connection to server
        QNetworkAccessManager *manager = new QNetworkAccessManager(this);
        QNetworkRequest request;
        connect(manager, SIGNAL(finished(QNetworkReply*)), this, SLOT(registerReplyFinished(QNetworkReply*)));
        QUrl url("http://www.pichunts.com:8080/Explosion/explosion/auth/reg");
        QUrlQuery query;
        query.addQueryItem("username", user);
        query.addQueryItem("password", password);
        url.setQuery(query.query());

        // set global property for user
        qApp->setProperty("userGlobalStr", user);

        // header information
        request.setUrl(QUrl(url));
        request.setRawHeader("Accept", "application/json");
        // qDebug() << request.header(QNetworkRequest::ContentTypeHeader);
        manager->get(request);
    }
}

// slot to capture request data from server
void RegisterPage::registerReplyFinished(QNetworkReply* effit) {

    QByteArray regArr;

    if (effit->error()) {
        qDebug() << "Error!";
        qDebug() << effit->errorString();
    }
    // get reply content from server
    else {
        // the following commented out lines will help debug network connection issues if uncommented
        // qDebug() << effit->header(QNetworkRequest::ContentTypeHeader).toString();
        // qDebug() << effit->attribute(QNetworkRequest::HttpStatusCodeAttribute).toInt();
        // qDebug() << effit->attribute(QNetworkRequest::HttpReasonPhraseAttribute).toString();
        regArr = effit->readAll();
        // qDebug() << "Success" << QString(regArr);
        ui->statusLabel->setText(QString(regArr));

        // parse JSON detail
        QJsonDocument jsonRaw(QJsonDocument::fromJson(regArr));
        QJsonObject json = jsonRaw.object();
        // qDebug() << json["isTaken"];
        // qDebug() << json["reason"];

        bool isTaken = json["isTaken"].toBool();
        QString reason = json["reason"].toString();

        // set QNetworkReply to be deleted later
        effit->deleteLater();

        // analyze reply from server and if username is taken, try again, else continue
        if (isTaken) {
            qApp->setProperty("userGlobalStr", "");
            ui->statusLabel->setText("Username is already taken. Try again.");
            clearAllFields();
            ui->usernameTextBox->setFocus();
        }
        else {
            ui->statusLabel->setText("You were successfully added.");
            emit registerButton_clicked();
        }
    }
}

void RegisterPage::widgetMadeVisibleClearForm(int) {
    ui->passwordTextBox->clear();
    ui->passwordTextBox2->clear();
    ui->usernameTextBox->clear();
    ui->statusLabel->clear();
}

void RegisterPage::on_showPassword_clicked() {
        if (ui->passwordTextBox->echoMode() == QLineEdit::Password) {
            ui->passwordTextBox->setEchoMode(QLineEdit::Normal);
            ui->passwordTextBox2->setEchoMode(QLineEdit::Normal);
            ui->showPassword->setText("Hide Password");
        }
        else {
            ui->passwordTextBox->setEchoMode(QLineEdit::Password);
            ui->passwordTextBox2->setEchoMode(QLineEdit::Password);
            ui->showPassword->setText("Show Password");
        }
    }

void RegisterPage::on_backButton_clicked()
{
    emit registerBackButton_clicked();
}
