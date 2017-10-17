#ifndef REGISTERPAGE_H
#define REGISTERPAGE_H

#include <QWidget>
#include <QLineEdit>
#include <QPainter>
#include <QNetworkAccessManager>
#include <QNetworkRequest>
#include <QNetworkReply>
#include <QUrl>
#include <QDebug>
#include <QUrlQuery>
#include <QJsonDocument>
#include <QJsonObject>

namespace Ui {
class RegisterPage;
}

class RegisterPage : public QWidget
{
    Q_OBJECT

public:
    explicit RegisterPage(QWidget *parent = 0);
    ~RegisterPage();

protected:
    void paintEvent(QPaintEvent *);
    void showEvent(QShowEvent *);
    void clearAllFields();

signals:
    void registerButton_clicked();
    void registerBackButton_clicked();

private slots:
    void on_registerButton_clicked();
    void on_showPassword_clicked();
    void registerReplyFinished(QNetworkReply* effit);
    void widgetMadeVisibleClearForm(int);

    void on_backButton_clicked();

private:
    Ui::RegisterPage *ui;
};

#endif // REGISTERPAGE_H
