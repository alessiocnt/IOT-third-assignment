#include "MsgControllerTask.h"
#include "SoftwareSerial.h"

MsgControllerTask::MsgControllerTask()
{
    btChannel.begin(9600);
    this->state = NORMAL;
    this->mode = AUTO;
}

void MsgControllerTask::init(int period)
{
    Task::init(period);
}

void MsgControllerTask::tick()
{
    // Activates the right task for the current state/mode
    if(this->mode == AUTO) {
        switch (this->state)
        {
        case NORMAL:
            alarmStateTask->setActive(false);
            manualModeTask->setActive(false);
            normalStateTask->setActive(true);
            break;
        default:
            normalStateTask->setActive(false);
            manualModeTask->setActive(false);
            alarmStateTask->setActive(true);
            break;
        }
    } else {
        alarmStateTask->setActive(false);
        normalStateTask->setActive(false);
        manualModeTask->setActive(true);
    }
    
    // Reads data form the serial (sent from the ds) or from bt (sent from dm)

    if (Serial.available()) {
        msgInterpreter(Serial.readStringUntil('\r\n'));
    }

    if (btChannel.available() && (this->state == ALARM || this->mode == MANUAL)) {
        msgInterpreter(btChannel.readStringUntil('\r\n'));
    } 
}

void MsgControllerTask::msgInterpreter(String msg) {
    // Splits the recived string in order to interpret it
    String pre;
    String suff;
    pre = msg.substring(0, msg.indexOf(':'));
    suff = msg.substring(msg.indexOf(':') + 1);
    if(pre.equalsIgnoreCase("state")) {
        if(suff == "normal" || suff == "prealarm") {
            this->state = NORMAL;
        } else {
            this->state = ALARM;
        }
    } else if(pre.equalsIgnoreCase("mode")) {
        if(suff == "manual") {
            this->mode = MANUAL;
        } else {
            this->mode = AUTO;
        }
    } else if(pre.equalsIgnoreCase("gap")) {
        // Sets the dam openness
        int val = map(suff.toInt(), 0, 100, 0 ,180);
        Serial.println("Moving to " + String(val));
        servoMovementTask->setPosition(val);
    }
}