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
    if(firstRun)
    {
        firstRun = false;
        led->switchOff();
    }
    float currentDistance = sonar->getDistance();
    Serial.println(currentDistance);
    // Nel caso in cui ci sia una variazione eccessiva del valore in un lasso di tempo molto limitato ipotizzo una lettura errata del dispositivo
    /* if(abs(currentDistance - prevDistance) > 1 )
    {
        return;
    } */
    /*     if(currentDistance >= D2 && currentDistance <= D1) 
        {
            this->setActive(false);
            Serial.println("Vado in pre");
            //preAllarmModeTask->setActive(true);
        } 
        else if (currentDistance <= D2)
        {
            this->setActive(false);
            Serial.println("Vado in allarme");
            //allarmModeTask->setActive(true);
        }
        else
        {
            this->prevDistance = currentDistance;
        } */
}

void NormalModeTask::setup()
{
    this->firstRun = true;
    this->prevDistance = sonar->getDistance();
}