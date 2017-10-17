#ifndef EXPLOSIVESMANAGER_H
#define EXPLOSIVESMANAGER_H

#include <QObject>
#include <QList>
#include "Box2D/Box2D.h"


class Explosive {
public:
    Explosive(b2Body *ord, b2Body *mat);
    b2Body *ordnancee;
    b2Body *material;

};


class Cutter {
public:
    Cutter(b2Body *a, b2Body *b);
    b2Body *a;
    b2Body *b;
};


class ExplosiveGroup: public QObject {
public:
    QString name = "Sequence Group";
    long delayTime = 0;
    QList<Explosive*> explosivesInGroup;
    QList<Cutter*> cuttersInGroup;
};


class ExplosivesManager
{
public:
    ExplosivesManager();
    int numberOfExplosiveGroups();
    QString nameOfExplostiveGroupAtIndex(int i);
    void selectExplosiveGroupAtIndex(int i);
    int indexOfSelectedExplosiveGroup();
    void removeExplosiveGroupAtIndex(int i);
    void setDelayOfSelectedExplosivesGroup(long d);
    long getDelayOfSelectedExplosivesGroup();


    virtual void beginSequence() = 0;

private:
    // Explosive Groups for sequencing
    int selectedGroupIndex = 0;

protected:
    ExplosiveGroup* getSelectedSequenceGroup();
    QList<ExplosiveGroup*> groups;

};

#endif // EXPLOSIVESMANAGER_H
