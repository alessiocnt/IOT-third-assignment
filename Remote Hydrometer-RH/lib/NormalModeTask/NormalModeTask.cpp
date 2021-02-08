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
    Serial.println("NormalMode");
    float currentDistance = sonar->getDistance();
    Serial.println(currentDistance);
    // Nel caso in cui ci sia una variazione eccessiva del valore in un lasso di tempo molto limitato ipotizzo una lettura errata del dispositivo
    /* if(abs(currentDistance - prevDistance) > 1 )
    {
        return;
    } */
        if(currentDistance >= D2 && currentDistance <= D1) 
        {
            Serial.println("Vado in pre");
            this->setActive(false);
            blinkTask->init(BLINKING_PERIOD, led, BLINK_FOREVER);
            blinkTask->setActive(true);
            preAllarmModeTask->setup();
            preAllarmModeTask->setActive(true);
        } 
        else if (currentDistance <= D2)
        {
            this->setActive(false);
            Serial.println("Vado in allarme");
            led->switchOn();
            allarmModeTask->setup();
            allarmModeTask->setActive(true);
        }
        /* else
        {
            this->prevDistance = currentDistance;
        } */
}

void NormalModeTask::setup()
{
    this->prevDistance = sonar->getDistance();
}