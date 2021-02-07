#include <Arduino.h>
#include "Led.h"
#include "ServoMotor.h"

Led* led1;
ServoMotor* servo;

void setup() {
    // put your setup code here, to run once:
    Serial.begin(9600);
    Serial.println("Bella raga");
}

void loop() {
    // put your main code here, to run repeatedly:
    Serial.println("Bella raga");
}