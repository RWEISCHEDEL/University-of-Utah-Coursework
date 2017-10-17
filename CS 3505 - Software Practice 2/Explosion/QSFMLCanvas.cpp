#include "QSFMLCanvas.h"
#include <QTimer>

QSFMLCanvas::QSFMLCanvas(QWidget *parent, unsigned frameTime) : QWidget(parent)
{
    mInitialized = false;
    mTimer = new QTimer(this);

    // setup some states to allow direct rendering into the widget
    setAttribute(Qt::WA_PaintOnScreen);
    setAttribute(Qt::WA_OpaquePaintEvent);
    setAttribute(Qt::WA_NoSystemBackground);

    // set strong focus to enable keyboard events to be received
	setFocusPolicy(Qt::StrongFocus);

    // setup the timer
    mTimer->setInterval(frameTime);
}

QSFMLCanvas::~QSFMLCanvas()
{
}

QPaintEngine *QSFMLCanvas::paintEngine() const
{
    return nullptr;
}

void QSFMLCanvas::showEvent(QShowEvent *)
{
    if (!mInitialized) {
        #ifdef Q_WS_X11
            XFlush(QX11Info::display());
        #endif

        // create the SFML window with the widget handle
        sf::ContextSettings settings;
        settings.antialiasingLevel = 32;
        setVerticalSyncEnabled(true);
        RenderWindow::create((void *) winId());
        setFramerateLimit(120);

        // let the derived class do its specific stuff
        OnInit();

        // setup the timer to trigger a refresh at specified framerate
        auto slot1 = static_cast<void (QSFMLCanvas::*)(void)>
                    (&QSFMLCanvas::repaint);
        connect(mTimer, &QTimer::timeout, this, slot1);

        mTimer->start();
        mInitialized = true;
    }
}

void QSFMLCanvas::paintEvent(QPaintEvent *)
{
    // let the derived class do its specific stuff
    OnUpdate();

    // display on screen
    RenderWindow::display();

}

/*
void QSFMLCanvas::resizeEvent(QResizeEvent *event) {
	sf::Vector2u size;
	size.x = event->size().width();
	size.y = event->size().height();
	setSize(size);
	display();
}*/
