#include "AllarmModeTask.h"

AllarmModeTask::AllarmModeTask(Sonar *sonar, Led *led)
{
    this->sonar = sonar;
    this->led = led;
    setup();
}

void AllarmModeTask::init(int period)
{
    Task::init(period);
}

void AllarmModeTask::tick()
{
   // Serial.println("AllMode");
    timer++;
    float currentDistance = sonar->getDistance();
   // Serial.println(currentDistance);
    if (this->myPeriod * this->timer >= SEND_ALL_TIME)
    {
        /* Send msg */
        Serial.println("Invio all");
        this->setup();
    }
    
    if(currentDistance >= D1) 
    {
        Serial.println("Vado in Norm");
        this->setActive(false);
        led->switchOff();
        normalModeTask->setup();
        normalModeTask->setActive(true);
    } 
    else if (currentDistance >= D2 && currentDistance <= D1)
    {
        Serial.println("Vado in PreAll");
        this->setActive(false);
        blinkTask->init(BLINKING_PERIOD, led, BLINK_FOREVER);
        blinkTask->setActive(true);
        preAllarmModeTask->setup();
        preAllarmModeTask->setActive(true);
    }
}

void AllarmModeTask::setup()
{
    this->timer = 0;
}