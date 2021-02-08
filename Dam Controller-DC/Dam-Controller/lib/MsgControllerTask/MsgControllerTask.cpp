#include "MsgControllerTask.h"
#include "SoftwareSerial.h"

MsgControllerTask::MsgControllerTask()
{
    btChannel.begin(9600);
    this->state = NORMAL;
}

void MsgControllerTask::init(int period)
{
    Task::init(period);
}

void MsgControllerTask::tick()
{
    switch (this->state)
    {
    case NORMAL:
        alarmStateTask->setActive(false);
        normalStateTask->setActive(true);
        break;
    default:
        normalStateTask->setActive(false);
        alarmStateTask->setActive(true);
        break;
    }
    /* reading data from BT to Serial */
    if (btChannel.available())
        Serial.write(btChannel.read());

    /* reading data from the Serial to BT */
    if (Serial.available())
        btChannel.write(Serial.read());
}