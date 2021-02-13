#ifndef __MANUAL_MODE_TASK__
#define __MANUAL_MODE_TASK__

#include <Arduino.h>
#include "Task.h"
#include "header.h"
#include "main.h"

/**
 * Models the system manual mode 
 */
class ManualModeTask : public Task
{
public:
    ManualModeTask();
    void init(int period);
    void tick();
};

#endif