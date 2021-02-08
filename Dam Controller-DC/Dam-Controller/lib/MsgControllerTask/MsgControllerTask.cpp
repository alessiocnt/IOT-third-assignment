#include "MsgControllerTask.h"
#include "SoftwareSerial.h"

MsgControllerTask::MsgControllerTask()
{
    btChannel.begin(9600);
    this->state = NORMAL;
}

void MsgControllerTask::init(int period)
{
    Task::init(period);
}

void MsgControllerTask::tick()
{
    switch (this->state)
    {
    case NORMAL:
        alarmStateTask->setActive(false);
        manualModeTask->setActive(false);
        normalStateTask->setActive(true);
        break;
    case MANUAL:
        alarmStateTask->setActive(false);
        normalStateTask->setActive(false);
        manualModeTask->setActive(true);
        break;
    default:
        normalStateTask->setActive(false);
        manualModeTask->setActive(false);
        alarmStateTask->setActive(true);
        break;
    }
    /* reading data from BT to Serial */
    if (btChannel.available()) {
        msgInterpreter(btChannel.readString());
        
    } else if (Serial.available()) {
        msgInterpreter(Serial.readString());
    }
}

void MsgControllerTask::msgInterpreter(String msg) {
    String pre;
    String suff;
    msg.remove(msg.length() - 1);
    pre = msg.substring(0, msg.indexOf(':'));
    suff = msg.substring(msg.indexOf(':') + 1);
    Serial.println(msg);
    Serial.println(pre);
    Serial.println(suff);
    if(pre.equalsIgnoreCase("state")) {
        if(suff.equalsIgnoreCase("normal")) {
            Serial.println("NormalState");
            this->state = NORMAL;
        } else {
            Serial.println("AlarmState");
            this->state = ALARM;
        }
    } else if(pre.equalsIgnoreCase("mode")) {
        if(suff.equalsIgnoreCase("manual")) {
            Serial.println("Manual mode");
            this->state = MANUAL;
        }
    } else if(pre.equalsIgnoreCase("level")) {
        int open = suff.toInt();
        servoMovementTask->setPosition(map(open, 0, 100, 0, 180));
    }
}