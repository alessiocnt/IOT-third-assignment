#ifndef __NORMALMODETASK__
#define __NORMALMODETASK__

#include <Arduino.h>
#include "header.h"
#include "main.h"
#include "Task.h"
#include "Led.h"
#include "Sonar.h"
#include "MsgService.h"

// Class that models a NormalModeTask - Puts the system in normal mode or maintains the sys in this state
class NormalModeTask : public Task
{
private:
    Sonar *sonar;
    Led *led;
    float prevDistance;
    bool firstRun;

public:
    NormalModeTask(Sonar *sonar, Led *led);
    void init(int period);
    void tick();
    void setup();
};

#endif