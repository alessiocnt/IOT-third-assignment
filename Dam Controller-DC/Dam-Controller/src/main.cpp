/**
*	Ceredi Simone - simone.ceredi@studio.unibo.it
*	Conti Alessio - alessio.conti3@studio.unibo.it
*/



#include <Arduino.h>
#include "SoftwareSerial.h"
#include "header.h"
#include "main.h"
#include "Led.h"
#include "ServoMotor.h"
#include "BlinkTask.h"
#include "ServoMovementTask.h"
#include "MsgControllerTask.h"
#include "NormalStateTask.h"
#include "AlarmStateTask.h"
#include "Scheduler.h"

ServoMovementTask *servoMovementTask;
BlinkTask *blinkTask;
Led *led1;
ServoMotor *servo;
NormalStateTask *normalStateTask;
AlarmStateTask *alarmStateTask;
ManualModeTask *manualModeTask;
MsgControllerTask *msgControllerTask;
SoftwareSerial btChannel(HC_TX_PIN, HC_RX_PIN);

Scheduler scheduler;

void createSensors()
{
    led1 = new Led(LED_PIN);
    servo = new ServoMotor(SERVO_PIN);
}

void createTasks()
{
    servoMovementTask = new ServoMovementTask(servo);
    blinkTask = new BlinkTask();
    normalStateTask = new NormalStateTask();
    alarmStateTask = new AlarmStateTask();
    manualModeTask = new ManualModeTask();
    msgControllerTask = new MsgControllerTask();
}

void setupTasks()
{
    servoMovementTask->init(SCHEDULE_TIME);
    scheduler.addTask(servoMovementTask);
    servoMovementTask->setActive(true);
    blinkTask->init(SCHEDULE_TIME * 25, led1, BLINK_FOREVER);
    blinkTask->setActive(false);
    scheduler.addTask(blinkTask);
    normalStateTask->init(SCHEDULE_TIME * 5);
    scheduler.addTask(normalStateTask);
    alarmStateTask->init(SCHEDULE_TIME * 5);
    scheduler.addTask(alarmStateTask);
    manualModeTask->init(SCHEDULE_TIME * 5);
    scheduler.addTask(manualModeTask);
    msgControllerTask->init(SCHEDULE_TIME);
    scheduler.addTask(msgControllerTask);
    msgControllerTask->setActive(true);
}

void setup()
{
    Serial.begin(9600);
    createSensors();
    createTasks();
    setupTasks();
    scheduler.init(SCHEDULE_TIME);
}

void loop()
{
    scheduler.schedule();
}