#ifndef LOGINPAGE_H
#define LOGINPAGE_H

#include <QWidget>
#include <QNetworkAccessManager>
#include <QNetworkRequest>
#include <QNetworkReply>
#include <QUrl>
#include <QDebug>
#include <QUrlQuery>
#include <QJsonDocument>
#include <QJsonObject>

namespace Ui {
class LoginPage;
}

class LoginPage : public QWidget
{
    Q_OBJECT

public:
    explicit LoginPage(QWidget *parent = 0);
    ~LoginPage();

protected:
    void paintEvent(QPaintEvent *);
    void showEvent(QShowEvent *);

signals:
    void loginButton_clicked();
    void loginRegisterButton_clicked();

private slots:
    void on_loginButton_clicked();
    void on_registerButton_clicked();
    void loginReplyFinished(QNetworkReply* effit);
    void widgetMadeVisibleClearForm(int);

private:
    Ui::LoginPage *ui;
};

#endif // LOGINPAGE_H
