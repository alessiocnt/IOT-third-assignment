#include "ServoMovementTask.h"

ServoMovementTask::ServoMovementTask(ServoMotor *servoMotor)
{
    this->position = 180;
    this->nextPosition = 0;
    this->servoMotor = servoMotor;
}

void ServoMovementTask::init(int period)
{
    Serial.println("Initializing Servo");
    servoMotor->on();
    while (position > 0)
    {
        position -= 1;
        setServoPosition(position);
        delay(INIT_DELAY);
    }
    Serial.println("Servo initialized");
    Task::init(period);
}

void ServoMovementTask::setPosition(int position)
{
    nextPosition = position;
}

void ServoMovementTask::tick()
{
    if (position == nextPosition)
    {
        return;
    }
    if (position > nextPosition)
    {
        position -= DELTA;
    }
    else
    {
        position += DELTA;
    }
    setServoPosition(position);
}

void ServoMovementTask::setServoPosition(int position)
{
    servoMotor->setPosition(position);
}