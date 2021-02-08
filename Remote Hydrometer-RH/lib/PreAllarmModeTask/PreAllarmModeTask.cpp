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
    this->setup();
}

void PreAllarmModeTask::tick()
{
    //Serial.println("PreAllMode");
    timer++;
    float currentDistance = sonar->getDistance();
    //Serial.println(currentDistance);
    if (this->myPeriod * this->timer >= SEND_PRE_ALL_TIME)
    {
        /* Send msg */
        Serial.println("Invio pre");
        /* Convert float to char[] */
        char b[sizeof(float)];
        memcpy(b, &currentDistance, sizeof(currentDistance));
        mqtt->publish("SimAleD", b);
        this->setup();
    }

    if(currentDistance >= D1) 
    {
        Serial.println("Vado in norm");
        mqtt->publish("SimAleS", "Normal");
        this->setActive(false);
        blinkTask->setActive(false);
        led->switchOff();
        normalModeTask->setup();
        normalModeTask->setActive(true);
    } 
    else if (currentDistance <= D2)
    {
        Serial.println("Vado in Allarme");
        mqtt->publish("SimAleS", "Allarm");
        this->setActive(false);
        blinkTask->setActive(false);
        led->switchOn();
        allarmModeTask->setup();
        allarmModeTask->setActive(true);
    }
}

void PreAllarmModeTask::setup()
{
    this->timer = 0;
}