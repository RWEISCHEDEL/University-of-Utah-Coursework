#ifndef ARSENALPAGE_H
#define ARSENALPAGE_H

#include <QWidget>
#include <QIcon>
#include <QPushButton>

namespace Ui {
class ArsenalPage;
}

class ArsenalPage : public QWidget {
    Q_OBJECT

public:
    explicit ArsenalPage(QWidget *parent = 0);
    ~ArsenalPage();

QVector<QString> chargeTNT, chargeC4, chargeSemtex, chargeThermiteCutter;
QHash<QString, QString> tntMap, c4Map, semtexMap, thermiteCutterMap;

void layoutTNTButtons(int numTNT);
void layoutC4Buttons(int numC4);
void layoutSemtexButtons(int numSemtex);
void layoutThermiteCutterButtons(int numThermiteCutter);

public slots:
    void addPurchasedTNT();
    void addPurchasedC4();
    void addPurchasedSemtex();
    void addPurchasedThermiteCutter();

    void useCharge(QString chargeType);
    void useTntCharge();
    void useC4();
    void useSemtex();
    void useThermiteCutter();

private:
    Ui::ArsenalPage *ui;
    QVector<QPushButton*> listOfActiveTNT, listOfActiveC4, listOfActiveSemtex, listOfActiveThermiteCutter;
    QIcon iconTNT, iconC4, iconSemtex, iconThermiteCutter;
    int budget;
};

#endif // ARSENALPAGE_H
