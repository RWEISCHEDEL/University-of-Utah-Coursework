#ifndef PHYSICSAREACOMPONENT_H
#define PHYSICSAREACOMPONENT_H
#include <SFML/Graphics.hpp>
#include "Box2D/Box2D.h"
#include <QString>


///
/// \brief The PhysicsAreaComponent class
/// An identifier object for various types of objects that can be added to the physics area
/// includes information such as the texture for the type and physics info (density... etc)
///
class PhysicsAreaComponent
{

public:
	sf::Texture texture;
	b2BodyType bodyType;
    sf::Sprite *sprite;
	std::string name;
	float density;
	float friction;
	float mass;
    int hits;
    bool shouldDrawOutline = true;
    bool shouldDrawSprite = false;
    PhysicsAreaComponent(std::string name, std::string texturePath, b2BodyType bodyType, float density, float friction);
    PhysicsAreaComponent(std::string name, sf::Sprite *sprite, b2BodyType bodyType, float density, float friction);

	~PhysicsAreaComponent();
};




#endif // PHYSICSAREACOMPONENT_H
