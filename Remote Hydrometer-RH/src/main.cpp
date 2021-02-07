/**
*	Ceredi Simone - simone.ceredi@studio.unibo.it
*	Conti Alessio - alessio.conti3@studio.unibo.it
*/

#include <Arduino.h>
#include "main.h"
#include "Led.h"
#include "header.h"
#include "Scheduler.h"
#include "Sonar.h"
#include "NormalModeTask.h"

BlinkTask *blinkTask;

Led *led;
Sonar *sonar;
NormalModeTask* normalModeTask;


Scheduler scheduler;

void createSensors()
{
    led = new Led(LED_PIN);
    sonar = new Sonar(SONAR_TRIG_PIN, SONAR_ECHO_PIN);
}

void createTasks()
{
    normalModeTask = new NormalModeTask(sonar, led);
    // preAllarmModeTask = new preAllarmModeTask(sonar, led);
    // allarmModeTask = new allarmModeTask(sonar, led);
    blinkTask = new BlinkTask();
}

void setupTasks()
{
    normalModeTask->init(NORMAL_PERIOD);
    scheduler.addTask(normalModeTask);

    // preAllarmModeTask->init(PRE_ALLARM_PERIOD);
    // scheduler.addTask(preAllarmModeTask);

    // allarmModeTask->init(ALLARM_PERIOD);
    // scheduler.addTask(allarmModeTask);

    scheduler.addTask(blinkTask);
    blinkTask->setActive(false);
}

void setup()
{
    createSensors();
    createTasks();
    setupTasks();
    scheduler.init(SCHEDULER_FREQ);
    normalModeTask->setActive(true);
}

void loop()
{
    scheduler.schedule();
}
