#include "NormalModeTask.h"

NormalModeTask::NormalModeTask(Sonar *sonar, Led *led)
{
    this->sonar = sonar;
    this->led = led;
    setup();
}

void NormalModeTask::init(int period)
{
    Task::init(period);
}

void NormalModeTask::tick()
{
    float currentDistance = sonar->getDistance();
    if(currentDistance >= D2 && currentDistance <= D1) 
    {
        Serial.println("Vado in PreAlarm");
        mqtt->publish("SimAleS", "prealarm");
        /* Convert float to char[] */
        char buff[6];
        dtostrf(currentDistance, 4, 2, buff);
        mqtt->publish("SimAleD", buff);
        this->setActive(false);
        blinkTask->init(BLINKING_PERIOD, led, BLINK_FOREVER);
        blinkTask->setActive(true);
        preAllarmModeTask->setup();
        preAllarmModeTask->setActive(true);
    } 
    else if (currentDistance <= D2)
    {
        this->setActive(false);
        Serial.println("Vado in Alarm");
        mqtt->publish("SimAleS", "alarm");
        /* Convert float to char[] */
        char buff[6];
        dtostrf(currentDistance, 4, 2, buff);
        mqtt->publish("SimAleD", buff);
        led->switchOn();
        allarmModeTask->setup();
        allarmModeTask->setActive(true);
    }
}

void NormalModeTask::setup()
{
    this->prevDistance = sonar->getDistance();
}