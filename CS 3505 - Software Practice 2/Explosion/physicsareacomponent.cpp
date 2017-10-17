#include "physicsareacomponent.h"

PhysicsAreaComponent::PhysicsAreaComponent(std::string name, std::string texturePath, b2BodyType bodyType, float density, float friction)
{
	sf::Image imageFromPath;
	imageFromPath.loadFromFile(texturePath);
	texture.loadFromImage(imageFromPath);
	this->name = name;
	this->bodyType = bodyType;
	this->density = density;
	this->friction = friction;
    this->hits = 0;
}


PhysicsAreaComponent::PhysicsAreaComponent(std::string name, sf::Sprite *sprite, b2BodyType bodyType, float density, float friction)
{
    this->sprite = sprite;
    this->shouldDrawSprite = true;
    this->shouldDrawOutline = false;
    this->name = name;
    this->bodyType = bodyType;
    this->density = density;
    this->friction = friction;
    this->hits = 0;
}

PhysicsAreaComponent::~PhysicsAreaComponent() {
	//delete name;
}


