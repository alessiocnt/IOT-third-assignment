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
    int MCD = 50;
    servoMovementTask->init(10);
    scheduler.addTask(servoMovementTask);
    servoMovementTask->setActive(true);
    blinkTask->init(MCD * 5, led1, BLINK_FOREVER);
    blinkTask->setActive(false);
    scheduler.addTask(blinkTask);
    normalStateTask->init(MCD);
    scheduler.addTask(normalStateTask);
    alarmStateTask->init(MCD);
    scheduler.addTask(alarmStateTask);
    manualModeTask->init(MCD);
    scheduler.addTask(manualModeTask);
    msgControllerTask->init(10);
    scheduler.addTask(msgControllerTask);
    msgControllerTask->setActive(true);
}

void setup()
{
    Serial.begin(9600);
    createSensors();
    createTasks();
    setupTasks();
    scheduler.init(10);
}

void loop()
{
    // put your main code here, to run repeatedly:
    scheduler.schedule();
}