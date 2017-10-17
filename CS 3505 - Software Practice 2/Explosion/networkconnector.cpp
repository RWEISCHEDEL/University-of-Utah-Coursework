#include "networkconnector.h"
#include <QJsonDocument>
#include <QJsonObject>

/*
 * //TODO: DELETE THE networkconnector.cpp CLASS - not used
 */
NetworkConnector::NetworkConnector(QObject *parent) : QObject(parent) {

    manager = new QNetworkAccessManager(this);
    connect(manager, SIGNAL(finished(QNetworkReply*)),
                        this, SLOT(replyFinished(QNetworkReply*)));

//    QObject::connect(manager, &QNetworkAccessManager::finished, this, &NetworkConnector::replyFinished);
}

//void NetworkConnector::logIn(QString user, QString pass) {
void NetworkConnector::logIn() {

    QString user = QString("test");
    QString pass = QString("testpass");
    QNetworkRequest request;

    // build http query that adds user and pass to URL
    QUrl url("http://www.pichunts.com:8080/Explosion/explosion/auth");
    QUrlQuery query;
    query.addQueryItem("username", user);
    query.addQueryItem("password", pass);
    url.setQuery(query.query());
    qDebug() << url;

    // set up and do requests
    request.setUrl(url);
    request.setRawHeader("Accept", "text/plain");

    qDebug() << "made it to manager get request.";
    manager->get(request);
    qDebug() << "after manager get request";


    //-------------------------
//    request.setUrl(QUrl("http://www.pichunts.com:8080/Explosion/explosion/hello"));
//    request.setRawHeader("Accept", "text/html");
//    qDebug() << request.header(QNetworkRequest::ContentTypeHeader);
//    manager->get(request);

//    QNetworkReply* loginReply = manager->get(request); // do we need to handle the ReadyRead() signal that this emits?

//    // get data from server output errors first
//    qDebug() << loginReply->errorString();
//    QByteArray loginArr = loginReply->readAll();
//    qDebug() << QString(loginArr);

//    // get JSON parse into a QPair
//    QJsonDocument jsonRaw(QJsonDocument::fromJson(loginArr));
//    QJsonObject json = jsonRaw.object();
//    qDebug() << json["isValid"].toString();

}

void NetworkConnector::replyFinished(QNetworkReply* effit) {

    if(effit->error()) {
        qDebug() << "Error";
        qDebug() << effit->errorString();
    }
    else
    {
        qDebug() << effit->header(QNetworkRequest::ContentTypeHeader).toString();
        qDebug() << effit->attribute(QNetworkRequest::HttpStatusCodeAttribute).toInt();
        qDebug() << effit->attribute(QNetworkRequest::HttpReasonPhraseAttribute).toString();
        QByteArray arr = effit->readAll();
        qDebug() << "Success" << QString(arr);
    }

    effit->deleteLater();
}

void NetworkConnector::recordScore(QString user, int score) {

    QNetworkAccessManager *manager = new QNetworkAccessManager(this);
    QNetworkRequest request;
    request.setUrl(QUrl("http://www.pichunts.com:8080/Explosion/explosion/setscore"));
    request.setRawHeader("Accept", "text/html");
    QNetworkReply* recordScoreReply = manager->get(request);

}
