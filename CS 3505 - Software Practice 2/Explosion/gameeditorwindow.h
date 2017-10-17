#ifndef GAMEEDITORWINDOW_H
#define GAMEEDITORWINDOW_H

#include <QWidget>
#include <QDebug>
#include "Box2D/Box2D.h"

namespace Ui {
class GameEditorWindow;
}

class GameEditorWindow : public QWidget
{
    Q_OBJECT

public:
    explicit GameEditorWindow(QWidget *parent = 0);
    ~GameEditorWindow();
    b2World getWorld();

    QString getWorldJSON();


signals:
    void gameMenu_button_clicked();
public slots:
    void on_Material_buttonClicked(int id);
    void on_DrawType_buttonClicked(int id);
    void on_leButtons_buttonClicked(int id);
private slots:

	void on_heightSpinbox_valueChanged(double arg1);

	void on_widthSpinbox_valueChanged(double arg1);



    void on_gameMenuButton_clicked();

private:
	Ui::GameEditorWindow *ui;
};

#endif // GAMEEDITORWINDOW_H
