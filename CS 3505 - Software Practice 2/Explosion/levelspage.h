#ifndef LEVELSPAGE_H
#define LEVELSPAGE_H

#include <QWidget>
#include <QMessageBox>
#include <QFileDialog>
#include <QTextStream>

namespace Ui {
class LevelsPage;
}

class LevelsPage : public QWidget
{
    Q_OBJECT

public:
    explicit LevelsPage(QWidget *parent = 0);
    ~LevelsPage();
protected:
    void paintEvent(QPaintEvent *);

signals:
    void levelsBackButton_clicked();
    void send_Budget(int);
    void load_Level1();
    void load_level2();
    void load_level3();
    void load_level4();
    void load_level5();
    void show_gamePage();
    void reset_game();

private slots:
    void on_levelsBackButton_clicked();
    void on_level1Button_clicked();
    void on_level2Button_clicked();
    void on_level3Button_clicked();
    void on_level4Button_clicked();
    void on_level5Button_clicked();

private:
    Ui::LevelsPage *ui;
};

#endif // LEVELSPAGE_H
