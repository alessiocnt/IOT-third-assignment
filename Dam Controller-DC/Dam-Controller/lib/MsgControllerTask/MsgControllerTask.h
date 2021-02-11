#ifndef __MSG_CONTROLLER_TASK__
#define __MSG_CONTROLLER_TASK__

#include <Arduino.h>
#include "Task.h"
#include "header.h"
#include "main.h"
#include "SoftwareSerial.h"

class MsgControllerTask : public Task
{
private:
    enum {
        NORMAL,
        ALARM
    } state;
    enum {
        MANUAL,
        AUTO
    } mode;
    void msgInterpreter(String msg);
public:
    MsgControllerTask();
    void init(int period);
    void tick();
};

#endif