#ifndef __ALARM_STATE_TASK__
#define __ALARM_STATE_TASK__

#include <Arduino.h>
#include "Task.h"
#include "header.h"
#include "main.h"

/**
 * Models the system alarm state 
 */
class AlarmStateTask : public Task
{
public:
    AlarmStateTask();
    void init(int period);
    void tick();
};

#endif