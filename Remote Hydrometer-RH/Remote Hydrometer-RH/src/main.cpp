/**
*	Ceredi Simone - simone.ceredi@studio.unibo.it
*	Conti Alessio - alessio.conti3@studio.unibo.it
*/

#include <Arduino.h>
#include "main.h"
#include "header.h"
#include "Scheduler.h"
#include "Led.h"
#include "Sonar.h"

#include "Mqtt.h"
#include "WifiConnector.h"
/* #include "NormalModeTask.h"
#include "PreAllarmModeTask.h"
#include "BlinkTask.h" */
//#include "AllarmModeTask.h"
const char* ssid     = "TIM-18270469-rep";         // TIM-18270469-rep  android_ale  The SSID (name) of the Wi-Fi network you want to connect to
const char* password = "ceredi63";            // ceredi63           alessio3     The password of the Wi-Fi network
Mqtt *mqtt = new Mqtt();

BlinkTask *blinkTask;
NormalModeTask* normalModeTask;
PreAllarmModeTask* preAllarmModeTask;
AllarmModeTask* allarmModeTask;

Led *led;
Sonar *sonar;


Scheduler scheduler;

void createSensors()
{
    led = new Led(LED_PIN);
    sonar = new Sonar(SONAR_TRIG_PIN, SONAR_ECHO_PIN);
}

void createTasks()
{
    normalModeTask = new NormalModeTask(sonar, led);
    preAllarmModeTask = new PreAllarmModeTask(sonar, led);
    allarmModeTask = new AllarmModeTask(sonar, led);
    blinkTask = new BlinkTask();
}

void setupTasks()
{
    normalModeTask->init(STD_PERIOD);
    scheduler.addTask(normalModeTask);

    preAllarmModeTask->init(STD_PERIOD);
    scheduler.addTask(preAllarmModeTask);

    allarmModeTask->init(STD_PERIOD);
    scheduler.addTask(allarmModeTask);

    scheduler.addTask(blinkTask);
    blinkTask->setActive(false);
}

void setup()
{
    Serial.begin(115200);
    delay(10);
    WifiConnector::setupWifi(ssid, password);
    mqtt->connect("broker.mqtt-dashboard.com");
//    mqtt->subscribe("inTopic");
    createSensors();
    createTasks();
    setupTasks();
    scheduler.init(SCHEDULER_FREQ);
    normalModeTask->setActive(true);
    Serial.println("READY!");
}

void loop()
{
    //Serial.println("Loop");
    mqtt->loop();
    scheduler.schedule();
}
