#ifndef __MSG_CONTROLLER_TASK__
#define __MSG_CONTROLLER_TASK__

#include <Arduino.h>
#include "Task.h"
#include "header.h"
#include "main.h"
#include "SoftwareSerial.h"

#define DELTA 10
#define INIT_DELAY 15

class MsgControllerTask : public Task
{
private:
    enum {
        NORMAL,
        ALARM,
        MANUAL
    } state;
    void msgInterpreter(String msg);
public:
    MsgControllerTask();
    void init(int period);
    void tick();
};

#endif