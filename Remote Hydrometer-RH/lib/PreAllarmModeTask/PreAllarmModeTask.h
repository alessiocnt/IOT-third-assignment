#ifndef __PREALLMODETASK__
#define __PREALLMODETASK__

#include <Arduino.h>
#include "header.h"
#include "main.h"
#include "Task.h"
#include "Led.h"
#include "Sonar.h"

#include "BlinkTask.h"
#include "NormalModeTask.h"
#include "AllarmModeTask.h"

// Class that models a PreAllarmModeTask - Puts the system in pre allarm mode or maintains the sys in this state
class PreAllarmModeTask : public Task
{
private:
    Sonar *sonar;
    Led *led;
    bool firstRun;

public:
    PreAllarmModeTask(Sonar *sonar, Led *led);
    void init(int period);
    void tick();
    void setup();
};

#endif