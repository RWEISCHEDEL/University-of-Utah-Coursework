#ifndef EDUCATIONPAGE_H
#define EDUCATIONPAGE_H

#include <QWidget>
#include <QImage>
#include <QPainter>

namespace Ui {
class EducationPage;
}

class EducationPage : public QWidget
{
    Q_OBJECT

public:
    explicit EducationPage(QWidget *parent = 0);
    ~EducationPage();
protected:
    void paintEvent(QPaintEvent *);

signals:
    void educationBackButton_clicked();

private slots:
    void on_educationBackButton_clicked();

private:
    Ui::EducationPage *ui;
};

#endif // EDUCATIONPAGE_H
