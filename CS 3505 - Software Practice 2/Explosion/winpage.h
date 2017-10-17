#ifndef WINPAGE_H
#define WINPAGE_H

#include <QWidget>

namespace Ui {
class WinPage;
}

class WinPage : public QWidget
{
    Q_OBJECT

public:
    explicit WinPage(QWidget *parent = 0);
    ~WinPage();

signals:
    void chooseLevelButton_clicked();
    void getEducatedButton_clicked();
    void gameMenuButton_clicked();
    void restartLevelButton_clicked();

private slots:
    void on_chooseLevelButton_clicked();

    void on_getEducatedButton_clicked();

    void on_gameMenuButton_clicked();

    void on_restartLevelButton_clicked();

private:
    Ui::WinPage *ui;
};

#endif // WINPAGE_H
