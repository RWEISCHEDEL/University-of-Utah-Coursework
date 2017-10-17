#include "mainwindow.h"
#include <QApplication>
#include <QDebug>
#include "gameeditorwindow.h"
#include <QLoggingCategory>

using namespace std;

int main(int argc, char *argv[])
{
    int status;

    try {
        QApplication a(argc, argv);

        MainWindow w;
        w.show();

        // set global settings for user, admin, and suppress ssl warnings
        a.setProperty("userGlobalStr", "");
        a.setProperty("userIsAdministrator", "");
        QLoggingCategory::setFilterRules("qt.network.ssl.warning=false");

        status = a.exec();
    }

    catch (std::exception &e) {
        qDebug() << e.what();
    }

    return status;
}
