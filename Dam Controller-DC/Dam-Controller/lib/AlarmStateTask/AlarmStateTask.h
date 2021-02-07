#ifndef __ALARM_STATE_TASK__
#define __ALARM_STATE_TASK__

#include <Arduino.h>
#include "Task.h"
#include "header.h"

#define DELTA 10
#define INIT_DELAY 15

class AlarmStateTask : public Task
{
private:
    int openness;

public:
    AlarmStateTask();
    void init(int period);
    void setOpenness(int openness);
    void tick();
};

#endif