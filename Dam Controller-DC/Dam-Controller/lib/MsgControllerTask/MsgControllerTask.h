#ifndef __MSG_CONTROLLER_TASK__
#define __MSG_CONTROLLER_TASK__

#include <Arduino.h>
#include "Task.h"
#include "header.h"
#include "SoftwareSerial.h"

#define DELTA 10
#define INIT_DELAY 15

class MsgControllerTask : public Task
{
private:
    SoftwareSerial* btChannel;
    enum {
        NORMAL,
        ALARM
    } state;
public:
    MsgControllerTask(SoftwareSerial* btChannel);
    void init(int period);
    void tick();
};

#endif