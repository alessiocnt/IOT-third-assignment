#include "ManualModeTask.h"

ManualModeTask::ManualModeTask() { }

void ManualModeTask::init(int period) {
    Task::init(period);
}

void ManualModeTask::tick() {
    // Stops the led from blinking and turns it on
    blinkTask->setActive(false);
    led1->switchOn();
}