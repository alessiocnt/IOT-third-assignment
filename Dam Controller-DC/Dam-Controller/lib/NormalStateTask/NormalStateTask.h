#ifndef __NORMAL_STATE_TASK__
#define __NORMAL_STATE_TASK__

#include <Arduino.h>
#include "Task.h"
#include "header.h"

#define DELTA 10
#define INIT_DELAY 15

class NormalStateTask : public Task
{
private:
    int openness;

public:
    NormalStateTask();
    void init(int period);
    void setOpenness(int openness);
    void tick();
};

#endif