#ifndef __MAIN_H_
#define __MAIN_H_

#include "BlinkTask.h"
#include "NormalModeTask.h"
#include "PreAllarmModeTask.h"
#include "AllarmModeTask.h"
#include "Mqtt.h"

extern BlinkTask *blinkTask;
extern NormalModeTask *normalModeTask;
extern PreAllarmModeTask *preAllarmModeTask;
extern AllarmModeTask *allarmModeTask;
extern Mqtt *mqtt;

#endif