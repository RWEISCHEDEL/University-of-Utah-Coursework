#ifndef NETWORKCONNECTOR_H
#define NETWORKCONNECTOR_H

#include <QObject>
#include <QNetworkAccessManager>
#include <QNetworkRequest>
#include <QNetworkReply>
#include <QUrl>
#include <QDebug>
#include <QUrlQuery>

/*
 * //TODO: DELETE THE networkconnector.h CLASS - not used
 */
class NetworkConnector : public QObject {
    Q_OBJECT

public:
    explicit NetworkConnector(QObject *parent = 0);

    //void logIn(QString user, QString pass);
    void logIn();
    void recordScore(QString user, int score);

signals:

public slots:
    void replyFinished(QNetworkReply *effit);

private:
   QNetworkAccessManager *manager;

};

#endif // NETWORKCONNECTOR_H
