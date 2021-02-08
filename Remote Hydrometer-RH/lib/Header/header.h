#ifndef __HEADER__
#define __HEADER__

#define D1 1
#define D2 0.4
#define STD_PERIOD 500
#define BLINKING_PERIOD 250
#define SCHEDULER_FREQ 250

#define SEND_ALL_TIME 5000
#define SEND_PRE_ALL_TIME 10000

#define LED_PIN 5
#define SONAR_TRIG_PIN 13
#define SONAR_ECHO_PIN 12
// Necessaria forward declaration in quanto le inclusioni di alcuni task devono essere ad anello
class BlinkTask;
class NormalModeTask;
class PreAllarmModeTask;
class AllarmModeTask;

#endif