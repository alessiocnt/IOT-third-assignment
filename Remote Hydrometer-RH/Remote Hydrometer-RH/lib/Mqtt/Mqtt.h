#ifndef __MSGSERVICE__
#define __MSGSERVICE__

#include <Arduino.h>
#include <ESP8266WiFi.h>        // Include the Wi-Fi library
#include <PubSubClient.h>

#define MSG_BUFFER_SIZE	(50)

class Mqtt
{
private:
    const char* mqtt_server;
    int value = 0;
    void reconnect();

public:
    Mqtt();
    void connect(const char* mqtt_server);
    void subscribe(const char* topic);
    void publish(char* topic, char* msg);
    void loop();
};

#endif
