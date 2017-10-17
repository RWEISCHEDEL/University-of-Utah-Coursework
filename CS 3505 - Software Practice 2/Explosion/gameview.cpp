#include "gameview.h"

#include <string>
#include <QDebug>
#include "animation.h"
#include "networkconnector.h"
#include <QApplication>
#include <QSettings>

using namespace sf;

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

/*
 * Private class for finding if a point is located within a body.
 */
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


GameView::GameView(QWidget *parent) : PhysicsArea(parent) {}

/*
 * Once initiated, this method allows setup inside the game itself
 */
void GameView::onInit2()
{
    bombType = -2;

    // Set up the contact listener so we know when things are hitting buildings
    theListener = new ContactListener();
    world.SetContactListener(theListener);

    // Set up the bomb texture
    std::stringstream bombTexturePath;
    bombTexturePath << path << "bomb.png";
    sf::Image bombImage;
    bombImage.loadFromFile(bombTexturePath.str());
    bomb = new sf::Texture;
    bomb->loadFromImage(bombImage);

    // Set up the tnt texture
    std::stringstream dynamiteTexturePath;
    dynamiteTexturePath << path << "tnt.png";
    sf::Image dynamiteImage;
    dynamiteImage.loadFromFile(dynamiteTexturePath.str());
    tnt = new sf::Texture;
    tnt->loadFromImage(dynamiteImage);

    // Set up the thermite texture
    std::stringstream cutterTexturePath;
    cutterTexturePath << path << "thermiteCutter.png";
    sf::Image cutterImage;
    cutterImage.loadFromFile(cutterTexturePath.str());
    cutter = new sf::Texture;
    cutter->loadFromImage(cutterImage);

    // Setup the building texture
    std::stringstream buildingTexturePath;
    buildingTexturePath << path << "building.png";
    PhysicsAreaComponent *buildingComponent = new PhysicsAreaComponent("building", buildingTexturePath.str(),b2_staticBody,1.f,.1);

    // Setup the house texture
    std::stringstream houseTexturePath;
    houseTexturePath << path << "house.png";
    PhysicsAreaComponent *houseComponent = new PhysicsAreaComponent("house", houseTexturePath.str(),b2_staticBody,1.f,.1);

    // Setup the wood texture
    std::stringstream woodTexturePath;
    woodTexturePath << path << "wood.png";
    PhysicsAreaComponent *woodComponent = new PhysicsAreaComponent("wood", woodTexturePath.str(),b2_dynamicBody,.5f,.1);

    // Setup the ground texture
    std::stringstream groundTexturePath;
    groundTexturePath << path << "ground.png";
    PhysicsAreaComponent *groundComponent = new PhysicsAreaComponent("ground", groundTexturePath.str(),b2_staticBody,1.f,.1);

    // Setup the concrete texture
    std::stringstream concreteTexturePath;
    concreteTexturePath << path << "concrete.png";
    PhysicsAreaComponent *concreteComponent = new PhysicsAreaComponent("concrete", concreteTexturePath.str(),b2_dynamicBody,1,.7);

    // Register that component with the physics area
    registerComponentWithName("wood", woodComponent);
    registerComponentWithName("ground", groundComponent);
    registerComponentWithName("concrete", concreteComponent);
    registerComponentWithName("house", houseComponent);
    registerComponentWithName("building", buildingComponent);

    // Create the ground
    sf::Vector2f worldPos = this->mapPixelToCoords(sf::Vector2i(this->getSize().x,this->getSize().y));
    addComponentWithName("ground", 0,worldPos.y/2 - 50,getSize().x,100,0);

    // Set the score
    score = 10000;

    // Setup the font for the score
    std::stringstream fontStream;
    fontStream << path << "SickFont.ttf";
    font.loadFromFile(fontStream.str());
    text.setFont(font);
    text.setCharacterSize(50);
    text.setColor(sf::Color::Black);

    // Setup the house and building on either side
    int xView = getView().getSize().x;
    int yView = getView().getSize().y;

    int houseWidth = static_cast<int>(getView().getSize().x/4.f);
    int houseHeight = houseWidth * 547.f/781.f;
    int buildingWidth = static_cast<int>(getView().getSize().x/5.f);
    int buildingHeight = buildingWidth * 959.f/668.f;
    houseX = -xView/2 + houseWidth/2;
    houseY = yView/2 - 100 - houseHeight/2;
    buildingX = xView/2 - buildingWidth/2;
    buildingY = yView/2 - 100 - buildingHeight/2;

    houseBod = addComponentWithName("house",houseX,houseY,houseWidth,houseHeight,0);
    buildingBod = addComponentWithName("building",buildingX,buildingY,buildingWidth,buildingHeight,0);

    rayOrigin = b2Vec2((buildingX - buildingWidth/2 - 1)/SCALE, (buildingY - 3 * buildingHeight / 4)/SCALE);

    // Setup the explosion texture
    std::stringstream explosionTexturePath;
    explosionTexturePath << path << "explosion.png";
    explosion = new sf::Texture();
    explosion->loadFromFile(explosionTexturePath.str());

    // Setup explosion 2 texture
    std::stringstream explosion2TexturePath;
    explosion2TexturePath << path << "explosion2.png";
    explosion2 = new sf::Texture();
    explosion2->loadFromFile(explosion2TexturePath.str());

    // Setup the texture for the house/building explosion
    std::stringstream bigExplosionTexturePath;
    bigExplosionTexturePath << path << "bigExplosion1.png";
    bigExplosion = new sf::Texture();
    bigExplosion->loadFromFile(bigExplosionTexturePath.str());

    std::stringstream cuttingTexturePath;
    cuttingTexturePath << path << "cutterSheet.png";
    cuttingSh = new sf::Texture();
    cuttingSh->loadFromFile(cuttingTexturePath.str());

    houseExploding = false;
    buildingExploding = false;
    removeHouse = false;
    removeBuilding = false;
    lostGame = false;

    gameOver.setFont(font);
    gameOver.setCharacterSize(200);
    gameOver.setColor(sf::Color::Black);
    gameOver.setString("GAME OVER");
    gameOver.setPosition(-gameOver.getLocalBounds().width/2,-gameOver.getLocalBounds().height);

    cutCharges = 3;
    smallCharges = 3;
    bigCharges = 2;
    started = false;
    wonGame = false;

    gameWin.setFont(font);
    gameWin.setCharacterSize(200);
    gameWin.setColor(sf::Color::Black);
    gameWin.setString("YOU WON!");
    gameWin.setPosition(-gameOver.getLocalBounds().width/2,-gameOver.getLocalBounds().height);

    countdown = 480;
}

void GameView::blowUpBuilding(bool house)
{
    Animation *explosive = new Animation();
    if (house) {
        explosive->setSpriteSheet(*bigExplosion);
        addFrames(explosive,512,512,4096,2048);
        AnimatedSprite* newSprite = new AnimatedSprite(sf::seconds(.05),false,false,explosive);
        newSprite->setPosition(sf::Vector2f(houseX-256,houseY-256));
        spritesList.append(newSprite);
        houseExploding = true;
        houseFrames = 0;
    } else {
        explosive->setSpriteSheet(*bigExplosion);
        addFrames(explosive,512,512,4096,2048);
        AnimatedSprite* newSprite = new AnimatedSprite(sf::seconds(.05),false,false,explosive);
        newSprite->setPosition(sf::Vector2f(buildingX-256,buildingY-256));
        spritesList.append(newSprite);
        buildingExploding = true;
        buildingFrames = 0;
    }
}

void GameView::onUpdate2()
{
    if (!wonGame && started && !lostGame) {
        RayCastCallback *callback = new RayCastCallback();
        world.RayCast(callback,rayOrigin,b2Vec2(rayOrigin.x - 100, rayOrigin.y));
        if (!callback->hit) {
            countdown--;
            if (countdown < 0) {
                wonGame = true;
                 gameWon(wonGame);
                updateIt = false;
            }
        }
    }

    QList<AnimatedSprite*> remove;
    sf::Time frameTime = frameClock.restart();
    for (AnimatedSprite *k : spritesList) {
        k->update(frameTime);
        k->play();
        draw(*k);
        if (k->m_currentFrame > k->getAnimation()->getSize() - 2) remove.append(k);
    }
    for (AnimatedSprite *i : remove) {
        spritesList.removeOne(i);
        delete i;
    }
    remove.clear();

    score -= theListener->houseHits * 500;
    houseHit += theListener->houseHits;
    theListener->houseHits = 0;

    score -= theListener->buildingHits * 500;
    buildingHit += theListener->buildingHits;
    theListener->buildingHits = 0;

    if (houseHit > 11) {
        blowUpBuilding(true);
    }
    if (buildingHit > 11) {
        blowUpBuilding(false);
    }
    if (houseHit > 11 || buildingHit > 11 || score < 0) {
        lostGame = true;
        houseHit = 0;
        buildingHit = 0;
        updateIt = false;
    }

    if (lostGame) {
        draw(gameOver);
    }

    if (wonGame) {
        draw(gameWin);
    }

    text.setString("SCORE: " + std::to_string(score));
    int yText = -getView().getSize().y / 2 + 20;
    text.setPosition(-text.getLocalBounds().width/2,yText);
    draw(text);

    if (houseExploding) {
        if (houseFrames == 40) removeHouse = true;
        else houseFrames++;
    }
    if (buildingExploding) {
        if (buildingFrames == 40) removeBuilding = true;
        else buildingFrames++;
    }
    if (removeHouse && houseBod != nullptr) {
        world.DestroyBody(houseBod);
        houseBod = nullptr;
        removeHouse = false;
    }
    if (removeBuilding && buildingBod != nullptr) {
        world.DestroyBody(buildingBod);
        buildingBod = nullptr;
        removeBuilding = false;
    }

    sf::Vertex line[] = {sf::Vertex(sf::Vector2f((rayOrigin.x+100)*SCALE,-rayOrigin.y*SCALE), sf::Color::Black),
                        sf::Vertex(sf::Vector2f((rayOrigin.x-100)*SCALE,-rayOrigin.y*SCALE), sf::Color::Black)};

    draw(line,2,sf::Lines);
}

/* Mouse Events */
void GameView::mouseReleaseEvent(QMouseEvent *event)
{
    // Do Nothing
}

void GameView::mouseMoveEvent(QMouseEvent *event)
{
    // Do nothing
}


void GameView::mousePressEvent(QMouseEvent *event) {
    if (!lostGame) {
        sf::Vector2i pixelPos = sf::Mouse::getPosition(*this);
        // convert it to world coordinates
        sf::Vector2f worldPos = this->mapPixelToCoords(pixelPos);

        if (bombType == -2) {
            if (smallCharges > 0) {
                addSFMLSprite(worldPos.x,worldPos.y,82,100,bomb);
                Bomb theBomb;
                theBomb.spot = sf::Vector2f(worldPos.x,worldPos.y);
                theBomb.type = bombType;
                explosionPoints.append(theBomb);
                smallCharges--;
            }
        }
        else if (bombType == -3) {
            if (bigCharges > 0) {
                addSFMLSprite(worldPos.x,worldPos.y,75,100,tnt);
                Bomb theBomb;
                theBomb.spot = sf::Vector2f(worldPos.x,worldPos.y);
                theBomb.type = bombType;
                explosionPoints.append(theBomb);
                bigCharges--;
            }
        }
        else if (bombType == -4) {
            if (cutCharges > 0) {
                addSFMLSprite(worldPos.x,worldPos.y,100,43,cutter);
                Bomb theBomb;
                theBomb.spot = sf::Vector2f(worldPos.x,worldPos.y);
                theBomb.type = bombType;
                explosionPoints.append(theBomb);
                cutCharges--;
            }
        }
    }
}

void GameView::buttonCallback(int id) {
    if (id == -2 || id == -3 || id == -4) bombType = id;
    else if (id == -5) setOffBombs();
}

void GameView::reset()
{
    countdown = 480;
    updateIt = true;
    started = true;
    wonGame = false;
    houseFrames = 0;
    buildingFrames = 0;
    houseExploding = false;
    buildingExploding = false;
    removeHouse = false;
    removeBuilding = false;
    cutCharges = 3;
    smallCharges = 3;
    bigCharges = 2;
    houseHit = 0;
    buildingHit = 0;
    lostGame = false;
    score = 10000;

    for (b2Body* body = world.GetBodyList(); body != 0; body = body->GetNext()) {
        std::string *compIDptr = (static_cast<std::string*>(body->GetUserData()));
        std::string compID = *compIDptr;

        if (compID.compare("building") == 0) buildingBod = body;
        else if (compID.compare("house") == 0) houseBod = body;
    }
}

void GameView::addFrames(Animation *anim, int width, int height, int totalWidth, int totalHeight) {
    for (int i = 0; i < totalHeight/height; i++) {
        for (int k = 0; k < totalWidth/width; k++) {
            anim->addFrame(sf::IntRect(width * k, height * i, width, height));
        }
    }
}

void GameView::setOffBombs() {
    if (!lostGame) {
        for (Bomb k : explosionPoints) {
            Animation *explosive = new Animation();
            if (k.type == -2) {
                addExplosion(k.spot.x,-k.spot.y,100);
                explosive->setSpriteSheet(*explosion);
                addFrames(explosive,64,64,320,320);
                AnimatedSprite* newSprite = new AnimatedSprite(sf::seconds(.05),false,false,explosive);
                newSprite->setPosition(sf::Vector2f(k.spot.x-32,k.spot.y-32));
                spritesList.append(newSprite);
            }
            else if (k.type == -3) {
                addExplosion(k.spot.x,-k.spot.y,200);
                explosive->setSpriteSheet(*explosion2);
                addFrames(explosive,100,100,900,900);
                AnimatedSprite* newSprite = new AnimatedSprite(sf::seconds(.05),false,false,explosive);
                newSprite->setPosition(sf::Vector2f(k.spot.x-50,k.spot.y-50));
                spritesList.append(newSprite);
            }
            else if (k.type == -4) {
                QueryCallback callback(b2Vec2(k.spot.x/SCALE,-k.spot.y/SCALE));
                b2AABB aabb;
                aabb.lowerBound = b2Vec2(k.spot.x/SCALE-.001,-k.spot.y/SCALE-.001);
                aabb.upperBound = b2Vec2(k.spot.x/SCALE+.001,-k.spot.y/SCALE+.001);
                world.QueryAABB( &callback, aabb );
                b2Body *hotBod = callback.m_object;
                if (hotBod) {
                    if (hotBod->GetType() != b2_staticBody) {
                            world.DestroyBody(hotBod);
                    }
                }
                explosive->setSpriteSheet(*cuttingSh);
                addFrames(explosive,192,192,960,576);
                AnimatedSprite* newSprite = new AnimatedSprite(sf::seconds(.05),false,false,explosive);
                newSprite->setPosition(sf::Vector2f(k.spot.x-96,k.spot.y-96));
                spritesList.append(newSprite);
            }
        }
        explosionPoints.clear();
        sprites.clear();
    }
}

void GameView::gameWon(bool won)
{
    QString level = qApp->property("userGlobalLevel").toString();
    QString score = QString::number(this->score);
    QString gameWon = QString::number(won);

    // make connection to server
    QNetworkAccessManager *manager = new QNetworkAccessManager(this);
    QNetworkRequest request;

    QUrl url("http://www.pichunts.com:8080/Explosion/explosion/score");
    QUrlQuery query;
    connect(manager, SIGNAL(finished(QNetworkReply*)), this, SLOT(scoreReplyFinished(QNetworkReply*)));
    query.addQueryItem("username", qApp->property("userGlobalStr").toString());
    query.addQueryItem("level", level);
    query.addQueryItem("score", score);
    query.addQueryItem("won", gameWon);
    url.setQuery(query.query());
    qDebug() << url;

    // header info
    request.setUrl(QUrl(url));
    request.setRawHeader("Accept", "application/json");

    manager->get(request);
}

void GameView::scoreReplyFinished(QNetworkReply* effit){

    QByteArray loginArr;

    if (effit->error()) {
        qDebug() << "Error!";
        qDebug() << effit->errorString();
    }
    // if no error, then get contents from server reply
    else {
        loginArr = effit->readAll();
        // All of the following comments can be uncommented in order to debug network connection errors.
//        qDebug() << effit->header(QNetworkRequest::ContentTypeHeader).toString();
//        qDebug() << effit->attribute(QNetworkRequest::HttpStatusCodeAttribute).toInt();
//        qDebug() << effit->attribute(QNetworkRequest::HttpReasonPhraseAttribute).toString();
//        qDebug() << "Success" << QString(loginArr);
        // ui->errorLabel->setText(QString(loginArr));

        // parse JSON
        QJsonDocument jsonRaw(QJsonDocument::fromJson(loginArr));
        QJsonObject json = jsonRaw.object();

        bool myBool = json["isRecorded"].toBool();
        QString str = json["reason"].toString();

        // set QNetworkReply to be deleted
        effit->deleteLater();
    }
}

// For finding the determinant of a 3 by 3 matrix
int GameView::determinant(int x1, int y1, int x2, int y2, int x3, int y3) {
            return x1*y2+x2*y3+x3*y1-y1*x2-y2*x3-y3*x1;
}

b2Vec2 GameView::minXB2Vec2(b2Vec2 one, b2Vec2 two) {
    if (one.x > two.x) return two;
    else return one;
}

b2Vec2 GameView::minYB2Vec2(b2Vec2 one, b2Vec2 two) {
    if (one.y > two.y) return two;
    else return one;
}

void GameView::addSFMLSprite(float posX, float posY, float sizeX, float sizeY, sf::Texture *texture) {
    sf::Sprite *newSprite = new sf::Sprite;
    newSprite->setTextureRect(sf::IntRect(0,0,texture->getSize().x,texture->getSize().y));
    newSprite->setOrigin(sizeX/2,sizeY/2);
    newSprite->setPosition(sf::Vector2f(posX,posY));
    newSprite->setTexture(*texture);
    newSprite->setScale(sizeX/newSprite->getLocalBounds().width, sizeY/newSprite->getLocalBounds().height);
    sprites.append(newSprite);
}
