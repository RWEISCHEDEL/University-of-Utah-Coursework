#ifndef HELPPAGE_H
#define HELPPAGE_H

#include <QWidget>

namespace Ui {
class HelpPage;
}

class HelpPage : public QWidget
{
    Q_OBJECT

public:
    explicit HelpPage(QWidget *parent = 0);
    ~HelpPage();

signals:
    void helpBackButton_clicked();

private slots:
    void on_helpBackButton_clicked();

private:
    Ui::HelpPage *ui;
};

#endif // HELPPAGE_H
