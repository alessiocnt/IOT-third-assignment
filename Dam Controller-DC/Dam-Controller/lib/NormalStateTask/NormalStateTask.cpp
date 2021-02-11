#include "NormalStateTask.h"
#include "header.h"
#include "main.h"

NormalStateTask::NormalStateTask() { }

void NormalStateTask::init(int period) {
    Task::init(period);
}

void NormalStateTask::tick() {
    //Serial.println("Normal");
    blinkTask->setActive(false);
    led1->switchOff();
}
