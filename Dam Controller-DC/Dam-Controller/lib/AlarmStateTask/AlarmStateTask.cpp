#include "AlarmStateTask.h"
#include "header.h"
#include "main.h"

AlarmStateTask::AlarmStateTask() { }

void AlarmStateTask::init(int period) {
    Task::init(period);
}

void AlarmStateTask::tick() {
    blinkTask->setActive(true);
}
