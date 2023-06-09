#ifndef __NORMALMODETASK__
#define __NORMALMODETASK__

#include <Arduino.h>
#include "header.h"
#include "main.h"
#include "Mqtt.h"
#include "Task.h"
#include "Led.h"
#include "Sonar.h"

#include "BlinkTask.h"
#include "PreAllarmModeTask.h"
#include "AllarmModeTask.h"

// Class that models a NormalModeTask - Puts the system in normal mode or maintains the sys in this state
class NormalModeTask : public Task
{
private:
    Sonar *sonar;
    Led *led;
    float prevDistance;

public:
    NormalModeTask(Sonar *sonar, Led *led);
    void init(int period);
    void tick();
    void setup();
};

#endif