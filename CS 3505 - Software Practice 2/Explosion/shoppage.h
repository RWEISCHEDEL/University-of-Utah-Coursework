#ifndef SHOPPAGE_H
#define SHOPPAGE_H

#include <QWidget>
#include <QHash>
#include <QIcon>
#include <QNetworkReply>

namespace Ui {
class ShopPage;
}

class ShopPage : public QWidget {
    Q_OBJECT

public:
    explicit ShopPage(QWidget *parent = 0);
    ~ShopPage();
    void buildStore();

signals:
    void shopBackButton_clicked();
    void purchasedC4();
    void purchasedTNT();
    void purchasedSemtex();
    void purchasedThermiteCutter();

public slots:
    void purchaseCharge();
    void loadBudget(int budgetFromLevel);
    void replyFinished(QNetworkReply* effit);

private slots:
    void on_shopBackButton_clicked();
    void on_pushButtonSendToServer_clicked();
    void on_pushButtonLogin_clicked();

private:
    Ui::ShopPage *ui;
    int budget;
    QHash<int, QString> shopItems;
    QIcon iconTntCost, iconC4Cost, iconSemtexCost, iconThermiteCutterCost;

};

#endif // SHOPPAGE_H
