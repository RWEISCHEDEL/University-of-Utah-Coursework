#ifndef LOSEPAGE_H
#define LOSEPAGE_H

#include <QWidget>

namespace Ui {
class LosePage;
}

class LosePage : public QWidget
{
    Q_OBJECT

public:
    explicit LosePage(QWidget *parent = 0);
    ~LosePage();

signals:
    void restartLevelButton_clicked();
    void gameMenuButton_clicked();
    void getHelpButton_clicked();
    void getEducatedButton_clicked();

private slots:
    void on_restartLevelButton_clicked();

    void on_gameMenuButton_clicked();

    void on_getHelpButton_clicked();

    void on_getEducatedButton_clicked();

private:
    Ui::LosePage *ui;
};

#endif // LOSEPAGE_H
