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

    if (Serial.available()) {
        msgInterpreter(Serial.readStringUntil('\r\n'));
    }

    if (btChannel.available() /*&& (this->state == ALARM || this->state == MANUAL)*/) {
        msgInterpreter(btChannel.readStringUntil('\r\n'));
        
    } 
}

void MsgControllerTask::msgInterpreter(String msg) {
    
    String pre;
    int suff;
    //msg.remove(msg.length() - 1);
    pre = msg.substring(0, msg.indexOf(':'));
    suff = msg.substring(msg.indexOf(':') + 1).toInt();
    unsigned char b[msg.length()];
    msg.getBytes(b, msg.length());

    Serial.println("TOTAL = " + msg);
    // Serial.println("MSG = " + String(suff));
    // Serial.println(pre);
    // Serial.println(suff);
    if(pre.equalsIgnoreCase("state")) {
        if(suff == 0) {
            //Serial.println("ARD = NormalState");
            this->state = NORMAL;
        } else {
            //Serial.println("ARD = AlarmState");
            this->state = ALARM;
        }
    } else if(pre.equalsIgnoreCase("mode")) {
        if(suff == 1) {
            //Serial.println("ARD = Manual mode");
            this->state = MANUAL;
        } else {
            //Serial.println("ARD = Auto mode");
            this->state = ALARM;
        }
    } else if(pre.equalsIgnoreCase("gap")) {
        int val = map(suff, 0, 100, 0 ,180);
        Serial.println("Moving to " + String(val));
        servoMovementTask->setPosition(val);
    }
}