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
    timer++;
    float currentDistance = sonar->getDistance();
    if (this->myPeriod * this->timer >= SEND_ALL_TIME)
    {
        /* Send msg */
        Serial.println("Invio alarm msg");
        /* Convert float to char[] */
        char buff[6];
        dtostrf(currentDistance, 4, 2, buff);
        Serial.println(buff);
        mqtt->publish("SimAleD", buff);
        this->setup();
    }
    
    if(currentDistance >= D1) 
    {
        Serial.println("Vado in Normal");
        mqtt->publish("SimAleS", "normal");
        char buff[6];
        dtostrf(currentDistance, 4, 2, buff);
        mqtt->publish("SimAleD", buff);
        this->setActive(false);
        led->switchOff();
        normalModeTask->setup();
        normalModeTask->setActive(true);
    } 
    else if (currentDistance >= D2 && currentDistance <= D1)
    {
        Serial.println("Vado in PreAlarm");
        mqtt->publish("SimAleS", "prealarm");
        char buff[6];
        dtostrf(currentDistance, 4, 2, buff);
        mqtt->publish("SimAleD", buff);
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