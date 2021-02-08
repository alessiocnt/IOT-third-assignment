#ifndef __MYMAIN__
#define __MYMAIN__

#include "ServoMovementTask.h"
#include "BlinkTask.h"
#include "Led.h"
#include "NormalStateTask.h"
#include "AlarmStateTask.h"
#include "ManualModeTask.h"

extern ServoMovementTask* servoMovementTask;
extern BlinkTask* blinkTask;
extern Led* led1;
extern SoftwareSerial btChannel;
extern NormalStateTask* normalStateTask;
extern AlarmStateTask* alarmStateTask;
extern ManualModeTask* manualModeTask;

#endif