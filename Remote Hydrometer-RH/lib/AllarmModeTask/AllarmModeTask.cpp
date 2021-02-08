#include "AllarmModeTask.h"
#include<string>

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
        /* Convert float to char[] */
        char buff[6];
        dtostrf(currentDistance, 4, 2, buff);
        Serial.println(buff);
        String(currentDistance);
        mqtt->publish("SimAleD", buff);
        this->setup();
    }
    
    if(currentDistance >= D1) 
    {
        Serial.println("Vado in Norm");
        mqtt->publish("SimAleS", "Normal");
        this->setActive(false);
        led->switchOff();
        normalModeTask->setup();
        normalModeTask->setActive(true);
    } 
    else if (currentDistance >= D2 && currentDistance <= D1)
    {
        Serial.println("Vado in PreAll");
        mqtt->publish("SimAleS", "PreAllarm");
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