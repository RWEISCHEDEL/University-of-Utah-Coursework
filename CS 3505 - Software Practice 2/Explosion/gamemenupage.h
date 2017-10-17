#ifndef GAMEMENUPAGE_H
#define GAMEMENUPAGE_H

#include <QWidget>

namespace Ui {
class GameMenuPage;
}

class GameMenuPage : public QWidget
{
    Q_OBJECT

public:
    explicit GameMenuPage(QWidget *parent = 0);
    ~GameMenuPage();
protected:
    void paintEvent(QPaintEvent *);

signals:
    void levelsButton_clicked();
    void levelEditorButton_clicked();
    void shopButton_clicked();
    void helpButton_clicked();
    void educationButton_clicked();
    void adminButton_clicked();
    void logoutButton_clicked();
    void loginButton_clicked();

private slots:
    void on_levelsButton_clicked();
    void on_levelEditorButton_clicked();
    void on_shopButton_clicked();
    void on_helpButton_clicked();
    void on_educationButton_clicked();
    void on_pushButtonAdmin_clicked();
    void on_pushButtonLogOut_clicked();
    void widgetMadeVisibleCheckUser(int pageInt);

    void on_pushButtonLogIn_clicked();

private:
    Ui::GameMenuPage *ui;
};

#endif // GAMEMENUPAGE_H
