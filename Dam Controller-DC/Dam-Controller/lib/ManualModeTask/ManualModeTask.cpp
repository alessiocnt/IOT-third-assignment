#include "ManualModeTask.h"

ManualModeTask::ManualModeTask() { }

void ManualModeTask::init(int period) {
    Task::init(period);
}

void ManualModeTask::tick() {
    blinkTask->setActive(false);
    led1->switchOn();
}