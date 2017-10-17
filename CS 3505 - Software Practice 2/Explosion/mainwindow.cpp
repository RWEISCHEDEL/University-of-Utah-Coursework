#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "Box2D/Box2D.h"
#include "SFML/Main.hpp"
#include "SFML/Graphics.hpp"
#include "PhysicsArea.h"
#include <QFrame>
#include <QPoint>
#include <QDesktopWidget>
#include <QFileDialog>
#include <QSettings>

///
/// \brief The MainWindow class
/// Game window
///
MainWindow::MainWindow(QWidget *parent) : QMainWindow(parent), ui(new Ui::MainWindow) {
    ui->setupUi(this);
    QMainWindow::showFullScreen();

    ui->menuPage->setEnabled(false);
    ui->gameStackedWidget->setCurrentWidget(ui->loginPage);
    //signals and slots from widgets to get to navigate through app
    connect(ui->loginPage, SIGNAL(loginButton_clicked()), this, SLOT(goTo_GameMenuPage()));
    connect(ui->gameMenuPage, SIGNAL(levelsButton_clicked()), this, SLOT(goTo_LevelsPage()));
    connect(ui->gameMenuPage, SIGNAL(helpButton_clicked()), this, SLOT(goTo_HelpPage()));
    connect(ui->gameMenuPage, SIGNAL(educationButton_clicked()), this, SLOT(goTo_EducationPage()));
    connect(ui->gameMenuPage, SIGNAL(shopButton_clicked()), this, SLOT(goTo_ShopPage()));
    connect(ui->gameMenuPage, SIGNAL(adminButton_clicked()), this, SLOT(goTo_AdminPage()));
    connect(ui->gameMenuPage, SIGNAL(logoutButton_clicked()), this, SLOT(goTo_LoginPage()));
    connect(ui->gameMenuPage, SIGNAL(loginButton_clicked()), this, SLOT(goTo_LoginPage()));
    connect(ui->registerPage, SIGNAL(registerButton_clicked()), this, SLOT(goTo_GameMenuPage()));
    connect(ui->registerPage, SIGNAL(registerBackButton_clicked()), this, SLOT(goTo_LoginPage()));
    connect(ui->loginPage, SIGNAL(loginRegisterButton_clicked()), this, SLOT(goTo_RegisterPage()));
    connect(ui->levelsPage, SIGNAL(levelsBackButton_clicked()), this, SLOT(goTo_GameMenuPage()));
    connect(ui->shopPage, SIGNAL(shopBackButton_clicked()), this, SLOT(goTo_GameMenuPage()));
    connect(ui->educationPage, SIGNAL(educationBackButton_clicked()), this, SLOT(goTo_GameMenuPage()));
    connect(ui->helpPage, SIGNAL(helpBackButton_clicked()), this, SLOT(goTo_GameMenuPage()));
    connect(ui->gameMenuPage, SIGNAL(levelEditorButton_clicked()), this, SLOT(goTo_LevelEditorPage()));
    connect(ui->adminPage, SIGNAL(pushButtonBack_clicked()), this, SLOT(goTo_GameMenuPage()));
    connect(ui->gameEditorWindow, SIGNAL(gameMenu_button_clicked()), this, SLOT(goTo_GameMenuPage()));
    connect(ui->gamePage, SIGNAL(gameMenu_button_clicked()), this, SLOT(goTo_GameMenuPage()));
    // send signals from gameStackedWidget whenever the visible page is changed to trigger checking or clearing of user input from UIs
    connect(ui->gameStackedWidget, SIGNAL(currentChanged(int)), ui->gameMenuPage, SLOT(widgetMadeVisibleCheckUser(int)));
    connect(ui->gameStackedWidget, SIGNAL(currentChanged(int)), ui->loginPage, SLOT(widgetMadeVisibleClearForm(int)));
    connect(ui->gameStackedWidget, SIGNAL(currentChanged(int)), ui->registerPage, SLOT(widgetMadeVisibleClearForm(int)));

    //win page navigation
    connect(ui->winPage, SIGNAL(chooseLevelButton_clicked()), SLOT(goTo_LevelsPage()));
    connect(ui->winPage, SIGNAL(gameMenuButton_clicked()), SLOT(goTo_GameMenuPage()));
    connect(ui->winPage, SIGNAL(getEducatedButton_clicked()), SLOT(goTo_EducationPage()));
    //*********needs to reload the same level on the game page
    connect(ui->winPage, SIGNAL(restartLevelButton_clicked()), SLOT(goTo_GamePage()));

    //TODO: 4. add a new connection from the mainwindow to levels page
    //TODO: 1. add a new level_ file into the levels.qrc resource folder after you build it.
    connect(ui->levelsPage, SIGNAL(load_Level1()), this, SLOT(on_actionLoad_Game_Level1_clicked()));
    connect(ui->levelsPage, SIGNAL(load_level2()), this, SLOT(on_actionLoad_Game_Level2_clicked()));
    connect(ui->levelsPage, SIGNAL(load_level3()), this, SLOT(on_actionLoad_Game_Level3_clicked()));
    connect(ui->levelsPage, SIGNAL(load_level4()), this, SLOT(on_actionLoad_Game_Level4_clicked()));
    connect(ui->levelsPage, SIGNAL(load_level5()), this, SLOT(on_actionLoad_Game_Level5_clicked()));
    connect(ui->levelsPage, SIGNAL(show_gamePage()), this, SLOT(goTo_GamePage()));
    connect(ui->levelsPage, SIGNAL(reset_game()), ui->gamePage, SLOT(resetLevel()));

    //lose page navigation
    connect(ui->losePage, SIGNAL(getHelpButton_clicked()), SLOT(goTo_HelpPage()));
    connect(ui->losePage, SIGNAL(gameMenuButton_clicked()), SLOT(goTo_GameMenuPage()));
    connect(ui->losePage, SIGNAL(getEducatedButton_clicked()), SLOT(goTo_EducationPage()));
    //*********needs to reload the same level on the game page
    connect(ui->losePage, SIGNAL(restartLevelButton_clicked()), SLOT(goTo_GamePage()));

    //*********Attempts to connect arsenal and shop
    connect(ui->shopPage, SIGNAL(purchasedTNT()), ui->arsenalPage, SLOT(addPurchasedTNT()));
    connect(ui->shopPage, SIGNAL(purchasedC4()), ui->arsenalPage, SLOT(addPurchasedC4()));
    connect(ui->shopPage, SIGNAL(purchasedSemtex()), ui->arsenalPage, SLOT(addPurchasedSemtex()));
    connect(ui->shopPage, SIGNAL(purchasedThermiteCutter()), ui->arsenalPage, SLOT(addPurchasedThermiteCutter()));

    connect(ui->levelsPage, SIGNAL(send_Budget(int)), ui->shopPage, SLOT(loadBudget(int)));
}

MainWindow::~MainWindow() {
    delete ui;
}

// Set full screen
void MainWindow::setFullScreen() {
    // Make our window without panels
    this->setWindowFlags(Qt::FramelessWindowHint | Qt::Tool | Qt::WindowStaysOnTopHint);
    // Resize refer to desktop
    this->resize(QApplication::desktop()->size());

    this->setFocusPolicy(Qt::StrongFocus);
    this->setAttribute(Qt::WA_QuitOnClose, true);

    qApp->processEvents();
    show();
    this->setFocus();
}

void MainWindow::goTo_AdminPage() {
    ui->gameStackedWidget->setCurrentWidget(ui->adminPage);
}

void MainWindow::goTo_GamePage() {
    ui->gameStackedWidget->setCurrentWidget(ui->gamePage);
}

void MainWindow::goTo_GameMenuPage() {
    ui->gameStackedWidget->setCurrentWidget(ui->gameMenuPage);
}

void MainWindow::goTo_HelpPage() {
    ui->gameStackedWidget->setCurrentWidget(ui->helpPage);
}

void MainWindow::goTo_LevelEditorPage() {
    ui->gameStackedWidget->setCurrentWidget(ui->gameEditorWindow);
}

void MainWindow::goTo_EducationPage() {
    ui->gameStackedWidget->setCurrentWidget(ui->educationPage);
}

void MainWindow::goTo_LevelsPage() {
    ui->gameStackedWidget->setCurrentWidget(ui->levelsPage);
}

void MainWindow::goTo_LoginPage() {
    ui->gameStackedWidget->setCurrentWidget(ui->loginPage);
}

void MainWindow::goTo_LosePage() {
    ui->gameStackedWidget->setCurrentWidget(ui->losePage);
}

void MainWindow::goTo_ShopPage() {
    ui->gameStackedWidget->setCurrentWidget(ui->shopPage);
}

void MainWindow::goTo_WinPage() {
    ui->gameStackedWidget->setCurrentWidget(ui->winPage);
}

void MainWindow::goTo_RegisterPage() {
    ui->gameStackedWidget->setCurrentWidget(ui->registerPage);
}

void MainWindow::on_actionLose_triggered() {
    ui->gameStackedWidget->setCurrentWidget(ui->losePage);
}

void MainWindow::on_actionGameMenu_triggered() {
    ui->gameStackedWidget->setCurrentWidget(ui->gameMenuPage);
}

void MainWindow::on_actionRegister_triggered() {
    ui->gameStackedWidget->setCurrentWidget(ui->registerPage);
}

void MainWindow::on_actionHelp_triggered() {
    ui->gameStackedWidget->setCurrentWidget(ui->helpPage);
}

void MainWindow::on_actionEducation_triggered() {
    ui->gameStackedWidget->setCurrentWidget(ui->educationPage);
}

void MainWindow::on_actionLevels_triggered() {
    ui->gameStackedWidget->setCurrentWidget(ui->levelsPage);
}

void MainWindow::on_actionWin_triggered() {
    ui->gameStackedWidget->setCurrentWidget(ui->winPage);
}

void MainWindow::on_actionShop_triggered() {
    ui->gameStackedWidget->setCurrentWidget(ui->shopPage);
}

void MainWindow::on_actionLogin_triggered() {
    ui->gameStackedWidget->setCurrentWidget(ui->loginPage);
}

void MainWindow::on_actionGame_triggered() {
    ui->gameStackedWidget->setCurrentWidget(ui->gamePage);
}

void MainWindow::on_actionArsenal_triggered() {
    ui->gameStackedWidget->setCurrentWidget(ui->arsenalPage);
}

void MainWindow::on_actionGameEditor_triggered() {
    ui->gameStackedWidget->setCurrentWidget(ui->gameEditorWindow);
}

void MainWindow::on_actionAdmin_triggered() {
    ui->gameStackedWidget->setCurrentWidget(ui->adminPage);
}

// load saved game
void MainWindow::on_actionLoad_Game_triggered() {
    // set global property for user level
    qApp->setProperty("userGlobalLevel", 0); //O indicates a user made level

    QString fileName = QFileDialog::getOpenFileName(this);
    QFile f(fileName);
    if (f.open(QIODevice::ReadOnly | QIODevice::Text)) {
        QTextStream fileStream(&f);
        QString fileContents = fileStream.readAll();
        f.close();
        ui->gamePage->loadLevelJSON(fileContents);
    }
}

void MainWindow::on_actionReset_Level_triggered() {
    ui->gamePage->resetLevel();
}

// save level
//TODO: File IO ok or fail condition?
void MainWindow::on_actionSave_Level_triggered() {
    //b2World saveWorld = ui->gameEditorWindow->getWorld();
    QString fileName = QFileDialog::getSaveFileName(this);
    QFile f(fileName);
    if(f.open(QIODevice::WriteOnly | QIODevice::Text));
    QTextStream fileOutStream(&f);
    fileOutStream << ui->gameEditorWindow->getWorldJSON();
    f.close();
}

// level 1 button clicked
void MainWindow::on_actionLoad_Game_Level1_clicked() {
    ui->gameStackedWidget->setCurrentWidget(ui->gamePage);

    QFile levelFile(":/Levels/level1");
    if(levelFile.open(QIODevice::ReadOnly | QIODevice::Text)) {
        QTextStream fileStream(&levelFile);
        QString fileContents = fileStream.readAll();
        levelFile.close();
        ui->gamePage->loadLevelJSON(fileContents);
    }
}

//TODO: 2. make a slot, and then add levels with the code pattern seen here and above (for levels 3-5)
void MainWindow::on_actionLoad_Game_Level2_clicked() {
    ui->gameStackedWidget->setCurrentWidget(ui->gamePage);

    QFile levelFile(":/Levels/level2");
    if(levelFile.open(QIODevice::ReadOnly | QIODevice::Text)) {
        QTextStream fileStream(&levelFile);
        QString fileContents = fileStream.readAll();
        levelFile.close();
        ui->gamePage->loadLevelJSON(fileContents);
    }
}

void MainWindow::on_actionLoad_Game_Level3_clicked()
{
    ui->gameStackedWidget->setCurrentWidget(ui->gamePage);

    QFile levelFile(":/Levels/level3");
    if(levelFile.open(QIODevice::ReadOnly | QIODevice::Text)) {
        QTextStream fileStream(&levelFile);
        QString fileContents = fileStream.readAll();
        levelFile.close();
        ui->gamePage->loadLevelJSON(fileContents);
    }
}

void MainWindow::on_actionLoad_Game_Level4_clicked()
{
    ui->gameStackedWidget->setCurrentWidget(ui->gamePage);

    QFile levelFile(":/Levels/level4");
    if(levelFile.open(QIODevice::ReadOnly | QIODevice::Text)) {
        QTextStream fileStream(&levelFile);
        QString fileContents = fileStream.readAll();
        levelFile.close();
        ui->gamePage->loadLevelJSON(fileContents);
    }
}

void MainWindow::on_actionLoad_Game_Level5_clicked()
{
    ui->gameStackedWidget->setCurrentWidget(ui->gamePage);

    QFile levelFile(":/Levels/level5");
    if(levelFile.open(QIODevice::ReadOnly | QIODevice::Text)) {
        QTextStream fileStream(&levelFile);
        QString fileContents = fileStream.readAll();
        levelFile.close();
        ui->gamePage->loadLevelJSON(fileContents);
    }
}



