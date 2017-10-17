#ifndef QSFMLCANVAS_H
#define QSFMLCANVAS_H

#ifdef Q_WS_X11
    #include <Qt/qx11info_x11.h>
    #include <X11/Xlib.h>
#endif

#include <QWidget>
#include <SFML/Graphics.hpp>
#include <QResizeEvent>

class QTimer;

class QSFMLCanvas : public QWidget, public sf::RenderWindow
{
public:
    QSFMLCanvas(QWidget *parent, unsigned frameTime = 0);
    virtual ~QSFMLCanvas();
protected:
    QTimer *mTimer;
    bool mInitialized;

    virtual void OnInit() = 0;
    virtual void OnUpdate() = 0;
    QPaintEngine *paintEngine() const override;
    void showEvent(QShowEvent *) override;
    void paintEvent(QPaintEvent *) override;
	//void resizeEvent(QResizeEvent *event) override;
};

#endif // QSFMLCANVAS_H
