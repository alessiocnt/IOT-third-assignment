#ifndef __HEADER__
#define __HEADER__

class ServoMovementTask;
class BlinkTask;
class Led;

#define HC_TX_PIN 2
#define HC_RX_PIN 3
#define LED_PIN 4
#define SERVO_PIN 5


extern ServoMovementTask* servoMovementTask;
extern BlinkTask* blinkTask;
extern Led* led1;

#endif