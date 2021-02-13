#ifndef __NORMAL_STATE__
#define __NORMAL_STATE__

#include <Arduino.h>
#include "header.h"
#include "Task.h"
#include "main.h"

#define INIT_DELAY 15
/**
 * Models the system normal state 
 */
class NormalStateTask : public Task
{
public:
    NormalStateTask();
    void init(int period);
    void tick();
};

#endif