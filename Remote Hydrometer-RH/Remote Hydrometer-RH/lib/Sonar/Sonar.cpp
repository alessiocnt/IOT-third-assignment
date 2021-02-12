#include <Arduino.h>
#include "Sonar.h"

Sonar::Sonar(int trigPin, int echoPin)
{
    this->trigPin = trigPin;
    this->echoPin = echoPin;
    pinMode(trigPin, OUTPUT);
    pinMode(echoPin, INPUT);
    this->soundSpeed = 340;
    this->lastDistance = -1;
}

float Sonar::getDistance()
{
    /* invio impulso */
    digitalWrite(trigPin, LOW);
    delayMicroseconds(3);
    digitalWrite(trigPin, HIGH);
    delayMicroseconds(5);
    digitalWrite(trigPin, LOW);

    /* ricevi l’eco */
    long tUS = pulseInLong(echoPin, HIGH);

    double d = tUS * soundSpeed / 1000.0 / 1000.0 / 2;
    if (d - lastDistance > 5)
    {
        // Errore di lettura, scarto il valore ritornando -1
        return 4.8;
    }
    lastDistance = d;
    Serial.println(d);
    return d;
}
