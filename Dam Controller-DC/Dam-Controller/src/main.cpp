#include <Arduino.h>
#include "SoftwareSerial.h"
#include "header.h"
#include "Led.h"
#include "ServoMotor.h"
#include "MsgService.h"
#include "BlinkTask.h"
#include "ServoMovementTask.h"
#include "MsgControllerTask.h"
#include "NormalStateTask.h"
#include "AlarmStateTask.h"
#include "Scheduler.h"

/*
 *  BT module connection:  
 *  - pin 2 <=> TXD
 *  - pin 3 <=> RXD
 */ 

ServoMovementTask *servoMovementTask;
BlinkTask *blinkTask;
Led *led1;
ServoMotor *servo;
NormalStateTask *normalStateTask;
AlarmStateTask *alarmStateTask;
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
    // normalStateTask = new NormalStateTask();
    // alarmStateTask = new AlarmStateTask();
    msgControllerTask = new MsgControllerTask();
}

void setupTasks()
{
    int MCD = 50;
    servoMovementTask->init(MCD);
    scheduler.addTask(servoMovementTask);
    blinkTask->init(MCD, led1, BLINK_FOREVER);
    scheduler.addTask(blinkTask);
    // normalStateTask->init(MCD);
    // scheduler.addTask(normalStateTask);
    // alarmStateTask->init(MCD);
    // scheduler.addTask(alarmStateTask);
    msgControllerTask->init(MCD);
    scheduler.addTask(msgControllerTask);
    msgControllerTask->setActive(true);
}

void setup()
{
    MsgService.init();
    scheduler.init(50);
    createSensors();
    createTasks();
    setupTasks();
}

void loop()
{
    // put your main code here, to run repeatedly:
    scheduler.schedule();
}