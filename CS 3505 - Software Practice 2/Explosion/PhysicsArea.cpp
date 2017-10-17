#include "PhysicsArea.h"
#include <cmath>
#include "Box2DJson/b2dJson.h"


using namespace sf;

PhysicsArea::PhysicsArea(QWidget *parent, unsigned frameTime) : world(b2Vec2(0.f, -40.0f)), QSFMLCanvas(parent)
{
    SCALE = 30;
}


class RayCastCallback : public b2RayCastCallback {
public:
    RayCastCallback()
    {
        m_fixture = NULL;
    }

    float32 ReportFixture(b2Fixture* fixture, const b2Vec2& point,
                          const b2Vec2& normal, float32 fraction)
    {
        m_fixture = fixture;
        hit = true;
        m_point = point; m_normal = normal; m_fraction = fraction; return fraction;
    }

    b2Fixture* m_fixture;
    b2Vec2 m_point;
    b2Vec2 m_normal;
    float32 m_fraction;
    bool hit = false;
};

void PhysicsArea::OnInit()
{
    // get current camera position
    cameraPositionX = 0; // for now, put the camera dead center
    cameraPositionY = 0; // around the reference boxes above
    onInit2();
	camera = getView();

    std::stringstream skyTexturePath;
    skyTexturePath << path << "sky.png";
    sf::Image skyImage;
    skyImage.loadFromFile(skyTexturePath.str());
    sky = new sf::Texture;
    sky->loadFromImage(skyImage);
    updateIt = true;

    skySprite = new sf::Sprite;
    skySprite->setTextureRect(sf::IntRect(0,0,sky->getSize().x,sky->getSize().y));
    skySprite->setPosition(sf::Vector2f(-(getView().getSize().x/2),-getView().getSize().y/2));
    skySprite->setTexture(*sky);
    skySprite->setScale(getView().getSize().x/sky->getSize().x, getView().getSize().y/sky->getSize().y);

}

void PhysicsArea::OnUpdate()
{
    if (updateIt) world.Step(1/120.f, 8, 3);
    clear(Color::White);
    draw(*skySprite);

	// Copy original camera settings
    sf::View cameraView(camera);

	// center on point
    cameraView.setCenter(cameraPositionX,cameraPositionY);
	// set zoom
    cameraView.zoom(zoomLevel);
	// load camera settings into view
    setView(cameraView);


	for (b2Body* body = world.GetBodyList(); body != 0; body = body->GetNext())
    {
        for(b2Fixture *bodyFixture = body->GetFixtureList(); bodyFixture != 0 ; bodyFixture = bodyFixture->GetNext()) {
            std::string *compIDptr = (static_cast<std::string*>(body->GetUserData()));
            std::string compID = *compIDptr;

            b2PolygonShape* polyShape = (b2PolygonShape*) bodyFixture->GetShape();

            ConvexShape shapeToFill;
            shapeToFill.setPosition(body->GetPosition().x*SCALE, -body->GetPosition().y*SCALE);

            int vertCount = polyShape->GetVertexCount();
            shapeToFill.setPointCount(vertCount);

            for(int vert = 0 ; vert < vertCount ; vert++) {
                b2Vec2 aVertex = polyShape->GetVertex(vert);
                sf::Vector2f sfVect;
                sfVect.x = aVertex.x*SCALE;
                sfVect.y = -aVertex.y*SCALE;
                shapeToFill.setPoint(vert,sfVect);
            }
            shapeToFill.setRotation(-57.29578f * body->GetAngle());
            shapeToFill.setTexture(&components.at(compID)->texture);
            QString building = "building";
            QString house = "house";
            if (compID.compare("building") != 0 && compID.compare("house") != 0) {
                shapeToFill.setOutlineThickness(2);
                shapeToFill.setOutlineColor(sf::Color::Black);
            }

            draw(shapeToFill);
        }
    }
    if (shape != nullptr) draw(*shape);
    for (sf::Sprite *k : sprites) {
        draw(*k);
    }
    onUpdate2();
}

void PhysicsArea::keyPressEvent(QKeyEvent *event) {
    switch(event->key()){
    case Qt::Key_Up:
        cameraPositionY += 10;
        break;
    case Qt::Key_Left:
        cameraPositionX -= 10;
        break;
    case Qt::Key_Right:
        cameraPositionX += 10;
        break;
    case Qt::Key_Down:
        cameraPositionY -= 10;
        break;
    case Qt::Key_0:

        break;
	case Qt::Key_Equal:
		zoomIn();
		break;
	case Qt::Key_Minus:
		zoomOut();
		break;
    }
}

void PhysicsArea::wheelEvent(QWheelEvent *event) {
    if (event->delta() > 0) {
		zoomIn();
    } else {
		zoomOut();
    }
}

void PhysicsArea::zoomIn() {
	setZoomLevel(zoomLevel - zoomDelta);
}

void PhysicsArea::zoomOut() {
	qDebug() << "zooming out: " << zoomLevel << " - " << zoomDelta;
	setZoomLevel(zoomLevel + zoomDelta);
}

void PhysicsArea::setZoomLevel(float level) {
	if (level > maxZoomLevel) {
		level = maxZoomLevel;
	}
	if (level < minZoomLevel) {
		level = minZoomLevel;
	}
	zoomLevel = level;
	qDebug() << "zoom level set to: " << zoomLevel;
}





void PhysicsArea::registerComponentWithName(std::string name, PhysicsAreaComponent *component) {
    components.insert(std::pair<std::string,PhysicsAreaComponent*>(name,component));
}


b2Body* PhysicsArea::addComponentWithName(std::string name,b2Vec2 origin, b2Vec2 points[], int pointCount, float rotation) {

    PhysicsAreaComponent *comp = components.at(name);
    b2BodyDef def;
    def.position = b2Vec2(origin.x/SCALE,-origin.y/SCALE);
	def.type = comp->bodyType;

	b2Body *body = world.CreateBody(&def);

    for (int i = 0; i < pointCount; i++) points[i].Set(points[i].x/SCALE,-points[i].y/SCALE);


	b2PolygonShape shape;
	shape.Set(points, pointCount);

	b2FixtureDef fixture;
	fixture.density = comp->density;
	fixture.friction = comp->friction;
	fixture.shape = &shape;
	body->CreateFixture(&fixture);
	body->SetUserData(&comp->name);
	std::string *compID = (static_cast<std::string*>(body->GetUserData()));
    return body;

}



b2Body* PhysicsArea::addComponentWithName(std::string name, float xPos, float yPos, float width, float height, float rotation) {
    b2Vec2 origin(xPos,yPos);
    b2Vec2 points[] = {b2Vec2(-width/2, -height/2), b2Vec2(-width/2,height/2), b2Vec2(width/2, height/2), b2Vec2(width/2,-height/2)};
    return addComponentWithName(name,origin,points,4,rotation);
}



void PhysicsArea::addExplosion(float worldX, float worldY, float blastRadius) {
    addExplosion(worldX,worldY,blastRadius, 0, 2 * M_PI);
}

void PhysicsArea::addExplosion(float worldX, float worldY, float blastRadius, float startAngle, float endAngle) {
    float x = worldX / SCALE;
    float y = worldY / SCALE;

    float mag = magnitudeForBlastRadius(blastRadius);
    b2Vec2 origin(x, y);

    double currentRayAngle = 0;

    double interval = explosiveRayCastInterval();
    while (currentRayAngle < 2 * M_PI) {
        RayCastCallback cb;

        // get x / y relative to center of explosion
        double yFromExplosion = sin(currentRayAngle) * blastRadius;
        double xFromExplosion = cos(currentRayAngle) * blastRadius;

        // create end of ray in world space
        b2Vec2 rayEnd(xFromExplosion + x, yFromExplosion + y);

        // make the ray cast
        world.RayCast(&cb, origin, rayEnd);

        // check for a hit
        if(cb.hit) {
            // get the hit body:
            b2Body *hitBody = cb.m_fixture->GetBody();

            if (hitBody->GetType() == b2_dynamicBody) {
                // apply the impulse, scaling the mag by resulting vector
                hitBody->ApplyLinearImpulse(b2Vec2(mag * xFromExplosion / cb.m_fraction, mag * yFromExplosion / cb.m_fraction ), hitBody->GetLocalPoint(cb.m_point), true);
            }
        }

        // re-using cb, make sure it's set to false between each cast
        cb.hit = false;

        // next angle.
        currentRayAngle += interval;
    }
}

float PhysicsArea::magnitudeForBlastRadius(float r) {
    return (r * radiusToMagScalar) / numberOfRaysToCast;
}

double PhysicsArea::explosiveRayCastInterval() {
    return (2 * M_PI) / numberOfRaysToCast;
}

std::string PhysicsArea::toJSON() {
	b2dJson json;
    for (b2Body* body = world.GetBodyList(); body != 0; body = body->GetNext()) {
        std::string *compIDptr = (static_cast<std::string*>(body->GetUserData()));
        std::string compID = *compIDptr;
        json.setBodyName(body, compID.c_str());
    }
	return json.writeToString(&world);
}

bool PhysicsArea::loadJSON(const std::string worldJSONData) {

    // pretty nice notes on this whole JSON bit:
    // http://www.iforce2d.net/b2djson/

    //before we can load the level, we should clear out anything
    //in the world
    int bodyCount = world.GetBodyCount();
    b2Body *currentBodies = world.GetBodyList();
    //world.DestroyBody(currentBodies);
    while(world.GetBodyCount() > 0) {
        //QUESTION: Is this going to be an issure with joints?
        world.DestroyBody(&world.GetBodyList()[0]);
    }

    // load up b2dJson to dump the file to the world
    b2dJson json;
    std::string error;
    json.readFromString(worldJSONData, error, &world);//<- dumped to world

    // get every body we just slapped into the world.
    std::vector<b2Body*> allLoadedBodies;

    // track counts of bodies loaded to bodies that have
    // had userdata restored: if they're not 1:1, we're going to crash
    int total = json.getAllBodies(allLoadedBodies);
    int totalWithRestoredUserData = 0;


    // next we'll need the name of every texture that has been
    // registered with our physics area
    for(std::map<std::string, PhysicsAreaComponent*>::iterator it = components.begin() ; it != components.end(); ++it ) {
        PhysicsAreaComponent *comp = components.at(it->first);
        std::vector<b2Body*> bodiesForName;
        json.getBodiesByName(it->first, bodiesForName);
        for(auto &body : bodiesForName) {
           body->SetUserData(&comp->name);
           totalWithRestoredUserData++;
        }
    }



    if(total != totalWithRestoredUserData) {
        qDebug() << "some bodies did not properly load";
    }



    return error.size() == 0;
}

// ^^ NOTES ABOUT ABOVE ^^
// I'm not sure how threading is being handled in this application
// and as such I'm slightly concerned about spining though the
// bodies in the world outside of the animation/physics loop
// a *better* solution for above would be to schedule bodies for
// addition and removal here and then at the top of the
// animation / physics loop, do the adds and removes. That way we
// would KNOW that no physics is going on in another thread.



