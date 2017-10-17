#ifndef ADMINPAGE_H
#define ADMINPAGE_H

#include <QWidget>
#include <QPainter>
#include <QNetworkAccessManager>
#include <QNetworkReply>
#include <QNetworkRequest>
#include <QJsonDocument>
#include <QJsonObject>

namespace Ui {
class AdminPage;
}

class AdminPage : public QWidget
{
    Q_OBJECT

public:
    explicit AdminPage(QWidget *parent = 0);
    ~AdminPage();
    void paintEvent(QPaintEvent *);

signals:
    void pushButtonBack_clicked();
    void pushButtonUsers_clicked();
    void pushButtonAvgScore_clicked();
    void pushButtonAvgScoreByLevel_clicked();
    void pushButtonAvgNumGames_clicked();
    void pushButtonNumGamesPerLevel_clicked();

private slots:
    void on_pushButtonBack_clicked();
    void on_pushButtonUsers_clicked();
    void on_pushButtonAvgScore_clicked();
    void on_pushButtonAvgScoreByLevel_clicked();
    void on_pushButtonNumGames_clicked();
    void on_pushButtonNumGamesPerLevel_clicked();

private:
    Ui::AdminPage *ui;
};

#endif // ADMINPAGE_H
