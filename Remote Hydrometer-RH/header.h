#ifndef __HEADER__
#define __HEADER__

#define D1 1
#define D2 0.4
#define NORMAL_PERIOD 15000
#define PRE_ALLARM_PERIOD 10000
#define ALLARM_PERIOD 5000
#define SCHEDULER_FREQ 50

#define LED_PIN 2
#define SONAR_TRIG_PIN 13
#define SONAR_ECHO_PIN 12

// Necessaria forward declaration in quanto le inclusioni di alcuni task devono essere ad anello
class BlinkTask;

#endif