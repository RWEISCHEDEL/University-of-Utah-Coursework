#ifndef GAMEVIEW_H
#define GAMEVIEW_H

#include <QWidget>
#include <QMouseEvent>
#include "PhysicsArea.h"
#include <cmath>
#include "SFML/Graphics.hpp"
#include <QList>
#include "animatedsprite.h"
#include "physicsareacomponent.h"
#include <QNetworkReply>
#include <QJsonDocument>
#include <QJsonObject>

class GameView : public PhysicsArea
{
    Q_OBJECT

public:
    GameView(QWidget *parent);

    void mousePressEvent(QMouseEvent *event) override;
    void mouseMoveEvent(QMouseEvent *event) override;
    void mouseReleaseEvent(QMouseEvent *event) override;
    void onInit2() override;
    void blowUpBuilding(bool house) override;
    bool draggingNewRect = false;
    float xStart, yStart;
    int material;
    sf::Texture *explosion;
    sf::Texture *explosion2;
    sf::Texture *bigExplosion;
    sf::Texture *cuttingSh;
    void onUpdate2() override;
    void buttonCallback(int id);
    void reset();
public slots:
    void scoreReplyFinished(QNetworkReply* effit);

private:
    class ContactListener : public b2ContactListener
      {
        void BeginContact(b2Contact* contact) {
            //check if either body is a building or house.
            b2Body *body = contact->GetFixtureA()->GetBody();
            b2Body *body1 = contact->GetFixtureB()->GetBody();

            std::string *compIDptr = (static_cast<std::string*>(body->GetUserData()));
            std::string compID = *compIDptr;

            std::string *compIDptr1 = (static_cast<std::string*>(body1->GetUserData()));
            std::string compID1 = *compIDptr1;

            if (compID.compare("building") == 0 || compID1.compare("building") == 0) {
                buildingHits++;
                // LOWER THE SCORE
            }
            else if (compID.compare("house") == 0 || compID1.compare("house") == 0) {
                houseHits++;
                // LOWER THE SCORE
            }
        }

        void EndContact(b2Contact* contact) {

        }
    public:
        int houseHits = 0;
        int buildingHits = 0;
    };
    class Bomb {
    public:
        sf::Vector2f spot;
        int type;
    };

    // For keeping track of game analytics
    // MAKE SURE TO RESET ON GAME LOADS
    int houseFrames;
    int buildingFrames;
    bool houseExploding;
    bool buildingExploding;
    bool removeHouse;
    bool removeBuilding;
    int houseHit;
    int buildingHit;
    int lostGame;
    bool wonGame;
    b2Vec2 rayOrigin;
    bool started;

    b2Body *houseBod;
    b2Body *buildingBod;
    int cutCharges;
    int smallCharges;
    int bigCharges;
    int houseX;
    int houseY;
    int buildingX;
    int buildingY;
    sf::Font font;
    sf::Text text;
    sf::Text gameOver;
    sf::Text gameWin;
    int countdown;
    int score;
    ContactListener *theListener;
    int bombType;
    sf::Clock frameClock;
    QList<Bomb> explosionPoints;
    QList<AnimatedSprite*> spritesList;
    sf::Texture *bomb;
    sf::Texture *tnt;
    sf::Texture *cutter;
    void setOffBombs();
    void gameWon(bool won);
    void addSFMLSprite(float posX, float posY, float sizeX, float sizeY, sf::Texture *texture);
    void addFrames(Animation *anim, int width, int height, int totalWidth, int totalHeight);
    b2Vec2 minXB2Vec2(b2Vec2 one, b2Vec2 two);
    b2Vec2 minYB2Vec2(b2Vec2 one, b2Vec2 two);
    int determinant(int x1, int y1, int x2, int y2, int x3, int y3);
};
#endif // GAMEVIEW_H
