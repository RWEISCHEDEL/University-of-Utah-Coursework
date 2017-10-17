#ifndef PHYSICSAREA_H
#define PHYSICSAREA_H

#include "QSFMLCanvas.h"
#include <QMouseEvent>
#include <QWidget>
#include <SFML/Graphics.hpp>
#include "Box2D/Box2D.h"
#include <QDebug>
#include "statics.h"
#include <string>
#include <QString>
#include <sstream>
#include <map>
#include "physicsareacomponent.h"
#include <QList>

class PhysicsArea : public QSFMLCanvas
{
public:
    PhysicsArea(QWidget *parent, unsigned frameTime = 0);
    void OnInit() override;
    void OnUpdate() override;
    void wheelEvent(QWheelEvent *event) override;
    void keyPressEvent(QKeyEvent *event) override;

	//void resizeEvent(QResizeEvent *event) override;

	std::string toJSON();
	bool loadJSON(const std::string worldJSONData);

protected:
    b2World world;
    float SCALE;
    sf::Texture* sky;
    sf::Sprite* skySprite;
    bool updateIt;

    QList<sf::Sprite*> sprites;
	std::map<std::string, PhysicsAreaComponent*> components;
    virtual void onInit2() = 0;
    virtual void onUpdate2() = 0;
    virtual void blowUpBuilding(bool house) = 0;
    void registerComponentWithName(std::string name, PhysicsAreaComponent *component);
    b2Body* addComponentWithName(std::string name, float xPos, float yPos, float width, float height, float rotation);
    b2Body* addComponentWithName(std::string name, b2Vec2 origin, b2Vec2 points[], int pointCount, float rotation);
    float magnitudeForBlastRadius(float r);

    double explosiveRayCastInterval();

    float numberOfRaysToCast = 720;
    float radiusToMagScalar = 0.0001; // This is wrong

    void addExplosion(float worldX, float worldY, float blastRadius);
    void addExplosion(float worldX, float worldY, float blastRadius, float startAngle, float endAngle);
    sf::ConvexShape *shape = nullptr;

	// Set on init, used to restore zoom settings after each draw. Do not change after init()
	sf::View camera;

	float zoomDelta = 0.01; // how quickly in/out you can zoom
	float zoomLevel = 1;    // the current zoom level
	const float maxZoomLevel = 2;
    const float minZoomLevel = 0.01;

	void zoomIn();
	void zoomOut();
	void setZoomLevel(float level);

	// pan control
    float cameraPositionX;
    float cameraPositionY;



};

#endif // PHYSICSAREA_H
