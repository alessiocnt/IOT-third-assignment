#include "MsgControllerTask.h"
#include "SoftwareSerial.h"
#include "MsgService.h"

MsgControllerTask::MsgControllerTask(SoftwareSerial* btChannel) {
    this->btChannel = btChannel;
    this->state = NORMAL;
}

void MsgControllerTask::init(int period) {
    Task::init(period);
}

void MsgControllerTask::tick() {
    if(MsgService.isMsgAvailable()) {
        MsgService.sendMsg(MsgService.receiveMsg()->getContent());
    }
    MsgService.sendMsg(this->btChannel->write("a"));
}