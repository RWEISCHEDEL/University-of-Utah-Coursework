#include "explosivesmanager.h"


Explosive::Explosive(b2Body *ord, b2Body *mat) {
    this->ordnancee = ord;
    this->material = mat;
}



Cutter::Cutter(b2Body *a, b2Body *b)  {
    this->a = a;
    this->b = b;
}




ExplosivesManager::ExplosivesManager()
{
    groups.append(new ExplosiveGroup());
    groups.at(0)->name = "Primary Group";
}

// EXPLOSIVE GROUPS

int ExplosivesManager::numberOfExplosiveGroups() {
    return groups.size();
}

QString ExplosivesManager::nameOfExplostiveGroupAtIndex(int i) {
    if(i < groups.size() ) {
        return groups.at(i)->name;
    }
    return "";
}

void ExplosivesManager::selectExplosiveGroupAtIndex(int i) {
    selectedGroupIndex = i;
}

int ExplosivesManager::indexOfSelectedExplosiveGroup() {
    return selectedGroupIndex;
}

void ExplosivesManager::removeExplosiveGroupAtIndex(int i) {
    if(i == 0) {
        return;
    }
    groups.removeAt(i);
}

void ExplosivesManager::setDelayOfSelectedExplosivesGroup(long d) {
    groups.at(selectedGroupIndex)->delayTime = d;
}

long ExplosivesManager::getDelayOfSelectedExplosivesGroup() {
    return groups.at(selectedGroupIndex)->delayTime;
}



ExplosiveGroup* ExplosivesManager::getSelectedSequenceGroup() {
    return groups.at(selectedGroupIndex);
}




