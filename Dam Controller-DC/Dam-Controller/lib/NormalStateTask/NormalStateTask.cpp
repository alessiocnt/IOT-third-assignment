#include "NormalStateTask.h"
#include "header.h"
#include "main.h"

NormalStateTask::NormalStateTask() { }

void NormalStateTask::init(int period) {
    Task::init(period);
}

void NormalStateTask::tick() {
    // Turns off the led
    blinkTask->setActive(false);
    led1->switchOff();
}
