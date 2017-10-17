#ifndef GAMEEDITOR_H
#define GAMEEDITOR_H

#include "PhysicsArea.h"
#include <cmath>
#include "SFML/Graphics.hpp"
#include <QVector>


class MaterialProfile {



};


class GameEditor : public PhysicsArea
{
public:
    GameEditor(QWidget *parent);
    void mousePressEvent(QMouseEvent *event) override;
    void mouseMoveEvent(QMouseEvent *event) override;
    void mouseReleaseEvent(QMouseEvent *event) override;
	void wheelEvent(QWheelEvent *event) override;
    void blowUpBuilding(bool house) override;

	void setZoom(float level);

    void onInit2() override;
    void onUpdate2() override;
    int material = -2;
    int drawType = -2;
    int currentButton = -5;
    b2World getWorld();

signals:
	void mouseLocationChanged(float xMeters, float yMeters);


private:
    QVector<b2Vec2> polygonPoints;
    float zoomLevel = 2;
    bool deleted = false;
    float xStart;
    float yStart;

    void drawInteriorWall(b2Vec2 start, b2Vec2 end);
    b2Body* addRectOfType(std::string type, b2Vec2 start, b2Vec2 end);

    const float studWidth = 2;

    // Probably delete this?
   // sf::

    int determinant(int x1, int y1, int x2, int y2, int x3, int y3);
};

#endif // GAMEEDITOR_H
