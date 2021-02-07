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
 *
 */ 

ServoMovementTask *servoMovementTask;
BlinkTask *blinkTask;
Led *led1;
ServoMotor *servo;
NormalStateTask *normalStateTask;
AlarmStateTask *alarmStateTask;
MsgControllerTask *msgControllerTask;
SoftwareSerial btChannel(2, 3);

//Scheduler scheduler;

void createSensors()
{
    // led1 = new Led(LED_PIN);
    // servo = new ServoMotor(SERVO_PIN);
}

void createTasks()
{
    // servoMovementTask = new ServoMovementTask(servo);
    // blinkTask = new BlinkTask();
    // normalStateTask = new NormalStateTask();
    // alarmStateTask = new AlarmStateTask();
    //msgControllerTask = new //MsgControllerTask(&btChannel);
}

void setupTasks()
{
    int MCD = 50;
    // servoMovementTask->init(MCD);
    //scheduler.addTask(servoMovementTask);
    // blinkTask->init(MCD, led1, BLINK_FOREVER);
    //scheduler.addTask(blinkTask);
    // normalStateTask->init(MCD);
    // scheduler.addTask(normalStateTask);
    // alarmStateTask->init(MCD);
    // scheduler.addTask(alarmStateTask);
    //msgControllerTask->init(MCD);
    //scheduler.addTask(msgControllerTask);
    //msgControllerTask->setActive(true);
}

void setup() {
  Serial.begin(9600);
  while (!Serial) {};
  
  btChannel.begin(9600);

  Serial.println("sending cmd..");
  /* check if it is there */
  btChannel.print("AT");
  delay(1000);
  
  /* set the name */
  btChannel.print("AT+NAMEisi00"); // Set the name to isiXX
  delay(1000);

  /* get the version */
  // btChannel.print("AT+VERSION");
  // delay(1000);

  /* get the name */
  // btChannel.print("AT+NAME"); 
  // delay(1000);

  /* get the name */
  // btChannel.print("AT+ADDR"); 
  // delay(1000);

}

void loop() {
  
  /* reading data from BT to Serial */
  if (btChannel.available())
    Serial.write(btChannel.read());

  /* reading data from the Serial to BT */
  if (Serial.available())
    btChannel.write(Serial.read());
}