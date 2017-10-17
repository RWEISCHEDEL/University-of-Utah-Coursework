#-------------------------------------------------
#
# Project created by QtCreator 2016-04-12T14:31:25
#
#-------------------------------------------------

QT      += core gui
QT      += webkitwidgets\
        network


CONFIG += c++11

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = Explosion
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp \
    QSFMLCanvas.cpp \
    PhysicsArea.cpp \
    loginpage.cpp \
    gamepage.cpp \
    gamemenupage.cpp \
    shoppage.cpp \
    helppage.cpp \
    winpage.cpp \
    losepage.cpp \
    educationpage.cpp \
    registerpage.cpp \
    levelspage.cpp \
    arsenalpage.cpp \
    gameeditor.cpp \
    physicsareacomponent.cpp \
    gameeditorwindow.cpp \
    gameview.cpp \
    animation.cpp \
    animatedsprite.cpp \
    explosive.cpp \
    explosivesmanager.cpp \
    networkconnector.cpp \
    adminpage.cpp

HEADERS  += mainwindow.h \
    QSFMLCanvas.h \
    PhysicsArea.h \
    statics.h \
    loginpage.h \
    gamepage.h \
    gamemenupage.h \
    shoppage.h \
    helppage.h \
    winpage.h \
    losepage.h \
    educationpage.h \
    registerpage.h \
    levelspage.h \
    arsenalpage.h \
    gameeditor.h \
    physicsareacomponent.h \
    gameeditorwindow.h \
    gameview.h \
    animation.h \
    animatedsprite.h \
    statics.h \
    explosive.h \
    explosivesmanager.h \
    gameviewinput.h \
    networkconnector.h \
    adminpage.h

FORMS    += mainwindow.ui \
    loginpage.ui \
    gamemenupage.ui \
    shoppage.ui \
    helppage.ui \
    winpage.ui \
    losepage.ui \
    educationpage.ui \
    registerpage.ui \
    levelspage.ui \
    arsenalpage.ui \
    gameeditorwindow.ui \
    adminpage.ui \
    gamepage.ui

macx: LIBS += -L'/usr/local/lib/' -lsfml-window.2.3.2 -lsfml-audio.2.3.2 -lsfml-graphics.2.3.2 -lsfml-network.2.3.2 -lsfml-system.2.3.2

INCLUDEPATH += '/usr/local/include'
DEPENDPATH += '/usr/local/include'

macx: LIBS += -L'$$PWD/Box2D/lib/' -lBox2D.2.3.0

INCLUDEPATH += '$$PWD/Box2D/include'
DEPENDPATH += '$$PWD/Box2D/include'

RESOURCES += \
    images.qrc \
    levels.qrc

#macx: LIBS += -L$$PWD/jsoncpp/lib/ -ljsoncpp

#INCLUDEPATH += $$PWD/jsoncpp/include
#DEPENDPATH += $$PWD/jsoncpp/include

#macx: PRE_TARGETDEPS += $$PWD/jsoncpp/lib/libjsoncpp.a

SOURCES += Box2DJson/b2dJson.cpp \
           Box2DJson/b2dJsonImage.cpp \
           Box2DJson/b2dJsonImage_OpenGL.cpp \
           Box2DJson/jsoncpp.cpp



HEADERS += Box2DJson/b2dJson.h \
           Box2DJson/b2dJsonImage_OpenGL.h \
           Box2DJson/b2dJsonImage.h \
           Box2DJson/bitmap.h
           Box2DJson/json/json.h
           Box2DJson/json/json-forwards.h
