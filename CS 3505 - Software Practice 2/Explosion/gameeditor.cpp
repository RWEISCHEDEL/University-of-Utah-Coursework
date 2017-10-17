#include "gameeditor.h"

using namespace sf;

class QueryCallback : public b2QueryCallback {
public:
QueryCallback(const b2Vec2& point) {
		m_point = point;
		m_object = 0;
	}
	bool ReportFixture(b2Fixture* fixture)
	{
		if (fixture->IsSensor()) {
			return true; //ignore sensors
		}
		bool inside = fixture->TestPoint(m_point);
		if (inside) {
			 // We are done, terminate the query.
			 m_object = fixture->GetBody();
				 return false;
		}
		return true;
	}
	b2Vec2  m_point;
	b2Body* m_object;
};


class DistributedArray {
    float minX;
    float maxX;
    float maxSpacing;
};

GameEditor::GameEditor(QWidget *parent) : PhysicsArea(parent)
{

}

void GameEditor::onInit2() {
    // Create a component
    std::stringstream woodTexturePath;
    woodTexturePath << path << "wood.png";
    PhysicsAreaComponent *woodComponent = new PhysicsAreaComponent("wood", woodTexturePath.str(),b2_dynamicBody,.5f,1);

    std::stringstream groundTexturePath;
    groundTexturePath << path << "ground.png";
    PhysicsAreaComponent *groundComponent = new PhysicsAreaComponent("ground", groundTexturePath.str(),b2_staticBody,1,.7);

    std::stringstream concreteTexturePath;
    concreteTexturePath << path << "concrete.png";
    PhysicsAreaComponent *concreteComponent = new PhysicsAreaComponent("concrete", concreteTexturePath.str(),b2_dynamicBody,1,.7);

    std::stringstream buildingTexturePath;
    buildingTexturePath << path << "building.png";
    PhysicsAreaComponent *buildingComponent = new PhysicsAreaComponent("building", buildingTexturePath.str(),b2_staticBody,1.f,.1);

    std::stringstream houseTexturePath;
    houseTexturePath << path << "house.png";
    PhysicsAreaComponent *houseComponent = new PhysicsAreaComponent("house", houseTexturePath.str(),b2_staticBody,1.f,.1);

    // Register that component with the physics area
    registerComponentWithName("wood", woodComponent);
    registerComponentWithName("ground", groundComponent);
    registerComponentWithName("concrete", concreteComponent);
    registerComponentWithName("house", houseComponent);
    registerComponentWithName("building", buildingComponent);

    sf::Vector2f worldPos = this->mapPixelToCoords(sf::Vector2i(this->getSize().x,this->getSize().y));
    addComponentWithName("ground", 0,worldPos.y/2 - 50,getSize().x,100,0);

    int xView = getView().getSize().x;
    int yView = getView().getSize().y;

    int houseWidth = static_cast<int>(getView().getSize().x/4.f);
    int houseHeight = houseWidth * 547.f/781.f;
    int buildingWidth = static_cast<int>(getView().getSize().x/5.f);
    int buildingHeight = buildingWidth * 959.f/668.f;

    // REMOVE THESE, THEY ARE TEMPORARY BUILDINGS
    addComponentWithName("house",-xView/2 + houseWidth/2,yView/2 - 100 - houseHeight/2,houseWidth,houseHeight,0);
    addComponentWithName("building",xView/2 - buildingWidth/2,yView/2 - 100 - buildingHeight/2,buildingWidth,buildingHeight,0);
}

void GameEditor::onUpdate2()
{
    if (polygonPoints.size() > 1) {
        for (int i = 0; i < polygonPoints.size() - 1; i++) {
            sf::Vertex line[] = {sf::Vertex(sf::Vector2f(polygonPoints.at(i).x,polygonPoints.at(i).y), sf::Color::Black),
                                sf::Vertex(sf::Vector2f(polygonPoints.at(i+1).x,polygonPoints.at(i+1).y), sf::Color::Black)};

            draw(line,2,sf::Lines);
        }
    }
}

b2World GameEditor::getWorld()
{
    return world;
}

/* Mouse Events */
void GameEditor::mouseReleaseEvent(QMouseEvent *event)
{
    delete shape;
    shape = nullptr;
    qDebug() << "current button: " << currentButton;
    // get the current mouse position in the window
    sf::Vector2i pixelPos = sf::Mouse::getPosition(*this);

    // convert it to world coordinates
    sf::Vector2f worldPos = this->mapPixelToCoords(pixelPos);

    if (currentButton == -5 && !deleted) {
        std::string type = (material == -2) ? "wood" : "concrete";
        float width = std::abs(xStart - worldPos.x);
        float height = std::abs(yStart - worldPos.y);
        addComponentWithName(type,xStart + width/2,yStart + height/2,width,height,0);
    }
    else if (currentButton == -3 && !deleted) {
        drawInteriorWall(b2Vec2(xStart, yStart), b2Vec2(worldPos.x,worldPos.y));
    }
    else if (currentButton == -6 && !deleted) {
        if (polygonPoints.size() > 1) {
            if (((std::abs(worldPos.x - polygonPoints.at(0).x < 8)) && (std::abs(worldPos.y - polygonPoints.at(0).y < 8))) || polygonPoints.size() > 7) {
                qDebug() << "Number of points:" << polygonPoints.size();
                // Create a local coordinate system for the new object from the
                // global points from SFML
                std::vector<b2Vec2> localPoly = polygonPoints.toStdVector();
                // Create highs and lows that will be overwritten on the first round.
                // Find the greatest and least x & y coordinates
                int xLow = 50000;
                int xHigh = -50000;
                int yLow = 50000;
                int yHigh = -50000;
                for (int i = 0; i < polygonPoints.size(); i++) {
                    localPoly[i] = polygonPoints.at(i);
                    if (polygonPoints.at(i).x < xLow) xLow = polygonPoints.at(i).x;
                    if (polygonPoints.at(i).x > xHigh) xHigh = polygonPoints.at(i).x;
                    if (polygonPoints.at(i).y < yLow) yLow = polygonPoints.at(i).y;
                    if (polygonPoints.at(i).y > yHigh) yHigh = polygonPoints.at(i).y;
                }

                // Now find the global origin of the local object
                int xMid = (xHigh + xLow)/2;
                int yMid = (yHigh + yLow)/2;

                // Use the global difference to the origin to calculate local coordinates
                for (int i = 0; i < localPoly.size(); i++) {
                    localPoly[i].x = localPoly[i].x - xMid;
                    localPoly[i].y = localPoly[i].y - yMid;
                }

                // Using Emanuele Feronato's code for placing vertices in clockwise order
                int n = localPoly.size();
                int d;
                int i1 = 1;
                int i2 = n - 1;
                std::vector<b2Vec2> clockwisePoly(n);
                b2Vec2 C;
                b2Vec2 D;
                for (int k = 0; k < localPoly.size(); k++) {
                    for (int j = k+1; j < localPoly.size(); j++) {
                        if (localPoly[j].x < localPoly[k].x) {
                            b2Vec2 temp = localPoly[k];
                            localPoly[k] = localPoly[j];
                            localPoly[j] = temp;
                        }
                    }
                }

                clockwisePoly[0] = localPoly[0];
                C = localPoly[0];
                D = localPoly[n-1];

                for (int i = 1; i < n - 1; i++) {
                    d = determinant(C.x,C.y,D.x,D.y,localPoly[i].x,localPoly[i].y);
                    if (d<0) {
                         clockwisePoly[i1++]=localPoly[i];
                    } else {
                         clockwisePoly[i2--]=localPoly[i];
                    }
                }
                clockwisePoly[i1] = localPoly[n-1];

                // Can't figure out how to calculate the centroid myself so I transfer the points
                // into Box2d (scaled down using the global scale factor) and then pull out and scale
                // up the centroid.
                std::vector<b2Vec2> points(clockwisePoly.size());
                for (int i = 0; i < clockwisePoly.size(); i++) {
                    points[i] = b2Vec2(clockwisePoly[i].x/SCALE,-clockwisePoly[i].y/SCALE);
                }
                std::vector<b2Vec2> holdPoints(clockwisePoly);
                b2Vec2 *arrayPoints = &points[0];
                b2PolygonShape shape;
                shape.Set(arrayPoints,points.size());
                b2Vec2 center = b2Vec2(shape.m_centroid.x*SCALE,shape.m_centroid.y*(-SCALE));
                center.x = center.x + xMid;
                center.y = -center.y + yMid;

                for (int i = 0; i < clockwisePoly.size(); i++) {
                    qDebug() << "Point" << i << "is:" << clockwisePoly[i].x << clockwisePoly[i].y;
                }

                b2Vec2 *holdingPoints = &holdPoints[0];
                addComponentWithName("wood",center,holdingPoints,holdPoints.size(),0);
                polygonPoints.clear();
            }
            else polygonPoints.append(b2Vec2(worldPos.x,worldPos.y));
        }
        else polygonPoints.append(b2Vec2(worldPos.x,worldPos.y));
        qDebug() << "Point at: " << worldPos.x << worldPos.y << "Pointies Count:" << polygonPoints.size();
    }
    deleted = false;
}

// For finding the determinant of a 3 by 3 matrix
int GameEditor::determinant(int x1, int y1, int x2, int y2, int x3, int y3) {
            return x1*y2+x2*y3+x3*y1-y1*x2-y2*x3-y3*x1;
}

void GameEditor::mouseMoveEvent(QMouseEvent *event)
{
    // get the current mouse position in the window
    sf::Vector2i pixelPos = sf::Mouse::getPosition(*this);

    // convert it to world coordinates
    sf::Vector2f worldPos = this->mapPixelToCoords(pixelPos);

    if ((currentButton == -3 || currentButton == -5) && !deleted) {
        delete shape;
        shape = new sf::ConvexShape();
        shape->setPointCount(4);
        shape->setPoint(0,Vector2f(xStart,yStart));
        shape->setPoint(1,Vector2f(worldPos.x,yStart));
        shape->setPoint(2,Vector2f(worldPos.x,worldPos.y));
        shape->setPoint(3,Vector2f(xStart,worldPos.y));
        shape->setOutlineThickness(2);
        shape->setOutlineColor(sf::Color::Black);
        float width = std::abs(xStart - worldPos.x);
        float height = std::abs(yStart - worldPos.y);
        shape->setPosition(xStart + width/2,yStart + height/2);
        shape->setOrigin(xStart + width/2,yStart + height/2);
    }
}



void GameEditor::mousePressEvent(QMouseEvent *event)
{
    // get the current mouse position in the window
    sf::Vector2i pixelPos = sf::Mouse::getPosition(*this);
    // convert it to world coordinates
    sf::Vector2f worldPos = this->mapPixelToCoords(pixelPos);

    QueryCallback callback(b2Vec2(worldPos.x/SCALE,-worldPos.y/SCALE));
    b2AABB aabb;
    aabb.lowerBound = b2Vec2(worldPos.x/SCALE-.001,-worldPos.y/SCALE-.001);
    aabb.upperBound = b2Vec2(worldPos.x/SCALE+.001,-worldPos.y/SCALE+.001);
    world.QueryAABB( &callback, aabb );
    b2Body *hotBod = callback.m_object;
    if (hotBod) {
        if (hotBod->GetType() != b2_staticBody) world.DestroyBody(hotBod);
        deleted = true;
    }
    else {
        xStart = worldPos.x;
        yStart = worldPos.y;
    }
}

void GameEditor::wheelEvent(QWheelEvent *event) {

}

void GameEditor::blowUpBuilding(bool house)
{

}


void GameEditor::drawInteriorWall(b2Vec2 start, b2Vec2 end) {

    float minX = fmin(start.x, end.x);
    float maxX = fmax(start.x, end.x);
    float minY = fmin(start.y, end.y);
    float maxY = fmax(start.y, end.y);

    b2Vec2 centerTopBottom((minX + maxX)/2, (minY + maxY)/2);


    // top stud
    b2Body *topStud = addRectOfType("wood",  b2Vec2(minX, minY), b2Vec2(maxX, minY + studWidth));




    // left stud
    // bottom stud
    // right stud
    // filler studs






}


b2Body* GameEditor::addRectOfType(std::string type, b2Vec2 start, b2Vec2 end) {

    float minX = fmin(start.x, end.x);
    float maxX = fmax(start.x, end.x);
    float minY = fmin(start.y, end.y);
    float maxY = fmax(start.y, end.y);

    float width = std::abs(minX - maxX);
    float height = std::abs(minY - maxY);
    return addComponentWithName(type,xStart + width/2,yStart + height/2,width,height,0);
}




void GameEditor::setZoom(float level) {
	float delta = level - zoomLevel;
	zoomDelta += delta;
	zoomLevel = level;
}

// Different Crap to do


