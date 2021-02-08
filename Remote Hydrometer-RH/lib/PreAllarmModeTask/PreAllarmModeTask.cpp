#include "PreAllarmModeTask.h"

PreAllarmModeTask::PreAllarmModeTask(Sonar *sonar, Led *led)
{
    this->sonar = sonar;
    this->led = led;
    setup();
}

void PreAllarmModeTask::init(int period)
{
    Task::init(period);
}

void PreAllarmModeTask::tick()
{
    Serial.println("PreAllMode");
    /* if(firstRun)
    {
        firstRun = false;
        blinkTask->init(BLINKING_PERIOD, led, BLINK_FOREVER);
        blinkTask->setActive(true);
    } */
    float currentDistance = sonar->getDistance();
    Serial.println(currentDistance);
    if(currentDistance >= D1) 
    {
        Serial.println("Vado in norm");
        this->setActive(false);
        blinkTask->setActive(false);
        led->switchOff();
        normalModeTask->setup();
        normalModeTask->setActive(true);
    } 
    else if (currentDistance <= D2)
    {
        Serial.println("Vado in Allarme");
        this->setActive(false);
        blinkTask->setActive(false);
        led->switchOn();
        allarmModeTask->setup();
        allarmModeTask->setActive(true);
    }
}

void PreAllarmModeTask::setup()
{
    this->firstRun = true;
}