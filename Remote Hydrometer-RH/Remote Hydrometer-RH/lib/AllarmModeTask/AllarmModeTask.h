#ifndef __ALLMODETASK__
#define __ALLMODETASK__

#include <Arduino.h>
#include "header.h"
#include "main.h"
#include "Mqtt.h"
#include "Task.h"
#include "Led.h"
#include "Sonar.h"

#include "BlinkTask.h"
#include "NormalModeTask.h"
#include "PreAllarmModeTask.h"

// Class that models a AllarmModeTask - Puts the system in allarm mode or maintains the sys in this state
class AllarmModeTask : public Task
{
private:
    Sonar *sonar;
    Led *led;
    int timer;

public:
    AllarmModeTask(Sonar *sonar, Led *led);
    void init(int period);
    void tick();
    void setup();
};

#endif