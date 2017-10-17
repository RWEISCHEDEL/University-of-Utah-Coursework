/********************************************************************************
** Form generated from reading UI file 'mainwindow.ui'
**
** Created by: Qt User Interface Compiler version 5.5.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MAINWINDOW_H
#define UI_MAINWINDOW_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QProgressBar>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_MainWindow
{
public:
    QWidget *centralWidget;
    QPushButton *RedButton;
    QPushButton *BlueButton;
    QPushButton *StartRestartButton;
    QProgressBar *progressBar;
    QLabel *Title;
    QLabel *HighScore;
    QLabel *Score;
    QPushButton *HintButton;
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *MainWindow)
    {
        if (MainWindow->objectName().isEmpty())
            MainWindow->setObjectName(QStringLiteral("MainWindow"));
        MainWindow->resize(471, 421);
        MainWindow->setStyleSheet(QStringLiteral(""));
        centralWidget = new QWidget(MainWindow);
        centralWidget->setObjectName(QStringLiteral("centralWidget"));
        RedButton = new QPushButton(centralWidget);
        RedButton->setObjectName(QStringLiteral("RedButton"));
        RedButton->setGeometry(QRect(10, 120, 221, 181));
        QFont font;
        font.setFamily(QStringLiteral("WenQuanYi Zen Hei Mono"));
        font.setPointSize(20);
        font.setBold(true);
        font.setItalic(true);
        font.setWeight(75);
        RedButton->setFont(font);
        BlueButton = new QPushButton(centralWidget);
        BlueButton->setObjectName(QStringLiteral("BlueButton"));
        BlueButton->setGeometry(QRect(250, 120, 211, 181));
        BlueButton->setFont(font);
        BlueButton->setStyleSheet(QStringLiteral(""));
        StartRestartButton = new QPushButton(centralWidget);
        StartRestartButton->setObjectName(QStringLiteral("StartRestartButton"));
        StartRestartButton->setGeometry(QRect(120, 310, 251, 26));
        QFont font1;
        font1.setFamily(QStringLiteral("WenQuanYi Zen Hei Mono"));
        font1.setPointSize(12);
        font1.setBold(true);
        font1.setWeight(75);
        StartRestartButton->setFont(font1);
        progressBar = new QProgressBar(centralWidget);
        progressBar->setObjectName(QStringLiteral("progressBar"));
        progressBar->setGeometry(QRect(110, 90, 281, 23));
        progressBar->setValue(0);
        Title = new QLabel(centralWidget);
        Title->setObjectName(QStringLiteral("Title"));
        Title->setGeometry(QRect(130, 0, 231, 31));
        QFont font2;
        font2.setFamily(QStringLiteral("WenQuanYi Zen Hei Mono"));
        font2.setPointSize(26);
        font2.setItalic(true);
        Title->setFont(font2);
        HighScore = new QLabel(centralWidget);
        HighScore->setObjectName(QStringLiteral("HighScore"));
        HighScore->setGeometry(QRect(220, 30, 211, 61));
        QFont font3;
        font3.setFamily(QStringLiteral("WenQuanYi Zen Hei Mono"));
        font3.setPointSize(14);
        font3.setItalic(true);
        HighScore->setFont(font3);
        Score = new QLabel(centralWidget);
        Score->setObjectName(QStringLiteral("Score"));
        Score->setGeometry(QRect(110, 30, 131, 61));
        Score->setFont(font3);
        HintButton = new QPushButton(centralWidget);
        HintButton->setObjectName(QStringLiteral("HintButton"));
        HintButton->setGeometry(QRect(120, 340, 251, 26));
        HintButton->setFont(font1);
        MainWindow->setCentralWidget(centralWidget);
        menuBar = new QMenuBar(MainWindow);
        menuBar->setObjectName(QStringLiteral("menuBar"));
        menuBar->setGeometry(QRect(0, 0, 471, 21));
        MainWindow->setMenuBar(menuBar);
        mainToolBar = new QToolBar(MainWindow);
        mainToolBar->setObjectName(QStringLiteral("mainToolBar"));
        MainWindow->addToolBar(Qt::TopToolBarArea, mainToolBar);
        statusBar = new QStatusBar(MainWindow);
        statusBar->setObjectName(QStringLiteral("statusBar"));
        MainWindow->setStatusBar(statusBar);

        retranslateUi(MainWindow);

        QMetaObject::connectSlotsByName(MainWindow);
    } // setupUi

    void retranslateUi(QMainWindow *MainWindow)
    {
        MainWindow->setWindowTitle(QApplication::translate("MainWindow", "Simon", 0));
        RedButton->setText(QApplication::translate("MainWindow", "RED", 0));
        BlueButton->setText(QApplication::translate("MainWindow", "BLUE", 0));
        StartRestartButton->setText(QApplication::translate("MainWindow", "START", 0));
        Title->setText(QApplication::translate("MainWindow", "Simon Game!", 0));
        HighScore->setText(QApplication::translate("MainWindow", "High Score:", 0));
        Score->setText(QApplication::translate("MainWindow", "Score:", 0));
        HintButton->setText(QApplication::translate("MainWindow", "HINTS", 0));
    } // retranslateUi

};

namespace Ui {
    class MainWindow: public Ui_MainWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MAINWINDOW_H
