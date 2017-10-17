#include "gameeditorwindow.h"
#include "ui_gameeditorwindow.h"

GameEditorWindow::GameEditorWindow(QWidget *parent) :
    QWidget(parent),
	ui(new Ui::GameEditorWindow)
{
    ui->setupUi(this);

}

GameEditorWindow::~GameEditorWindow() {
    delete ui;
}

b2World GameEditorWindow::getWorld()
{
    return ui->editor->getWorld();
}


QString GameEditorWindow::getWorldJSON() {
    return QString::fromStdString(ui->editor->toJSON());
}

void GameEditorWindow::on_Material_buttonClicked(int id) {
    ui->editor->material = id;
}

void GameEditorWindow::on_DrawType_buttonClicked(int id) {
    ui->editor->drawType = id;
}

void GameEditorWindow::on_leButtons_buttonClicked(int id)
{
    ui->editor->currentButton = id;
}

void GameEditorWindow::on_heightSpinbox_valueChanged(double arg1) {

}

void GameEditorWindow::on_widthSpinbox_valueChanged(double arg1) {

}


void GameEditorWindow::on_gameMenuButton_clicked()
{
    emit gameMenu_button_clicked();
}
