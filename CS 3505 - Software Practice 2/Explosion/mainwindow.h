#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QMessageBox>
#include <QFileDialog>

namespace Ui {
class MainWindow;
}

///
/// \brief The MainWindow class
/// Game window
///
class MainWindow : public QMainWindow {
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

private:
    Ui::MainWindow *ui;
    // This was used from: http://stackoverflow.com/questions/1246825/fullscreen-widget
    void setFullScreen();
    int windowHeight;
    int windowWidth;

public slots:
    void goTo_GamePage();
    void goTo_GameMenuPage();
    void goTo_HelpPage();
    void goTo_EducationPage();
    void goTo_LevelsPage();
    void goTo_LoginPage();
    void goTo_LosePage();
    void goTo_ShopPage();
    void goTo_WinPage();
    void goTo_RegisterPage();
    void goTo_LevelEditorPage();
    void goTo_AdminPage();

private slots:
    void on_actionLose_triggered();
    void on_actionGameMenu_triggered();
    void on_actionRegister_triggered();
    void on_actionHelp_triggered();
    void on_actionEducation_triggered();
    void on_actionLevels_triggered();
    void on_actionWin_triggered();
    void on_actionShop_triggered();
    void on_actionLogin_triggered();
    void on_actionGame_triggered();
    void on_actionArsenal_triggered();
    void on_actionGameEditor_triggered();
    void on_actionLoad_Game_triggered();
    void on_actionReset_Level_triggered();
    void on_actionSave_Level_triggered();
    void on_actionAdmin_triggered();

    // levels slots
    void on_actionLoad_Game_Level1_clicked();
    void on_actionLoad_Game_Level2_clicked();
    void on_actionLoad_Game_Level3_clicked();
    void on_actionLoad_Game_Level4_clicked();
    void on_actionLoad_Game_Level5_clicked();

};

#endif // MAINWINDOW_H
