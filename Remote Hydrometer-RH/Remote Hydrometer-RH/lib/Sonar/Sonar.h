#ifndef __SONAR__
#define __SONAR__

// Class that models a Sonar
class Sonar
{
public:
    Sonar(int trigPin, int echoPin);
    float getDistance();

private:
    int trigPin;
    int echoPin;
    float soundSpeed;
    float lastDistance;
};

#endif
