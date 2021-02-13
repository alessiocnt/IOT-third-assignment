#include "AlarmStateTask.h"
#include "header.h"
#include "main.h"

AlarmStateTask::AlarmStateTask() { }

void AlarmStateTask::init(int period) {
    Task::init(period);
}

void AlarmStateTask::tick() {
    // Activates the led blinking
    blinkTask->setActive(true);
}
