#include "MsgControllerTask.h"
#include "SoftwareSerial.h"
#include "MsgService.h"

MsgControllerTask::MsgControllerTask() {
    btChannel.begin(9600);
    this->state = NORMAL;
}

void MsgControllerTask::init(int period) {
    Task::init(period);
}

void MsgControllerTask::tick() {
    if(MsgService.isMsgAvailable()) {
        Serial.println(Serial.read());
        btChannel.write(Serial.read());
        MsgService.sendMsg(MsgService.receiveMsg()->getContent());
    }

    if(btChannel.available()) {
        String res = "";
        MsgService.sendMsg(btChannel.readString());
    }
}