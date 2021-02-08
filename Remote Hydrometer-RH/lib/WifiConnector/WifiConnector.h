#include <Arduino.h>
#include <ESP8266WiFi.h>  

class WifiConnector
{
public:
    static void setupWifi(const char* ssid, const char* password);

};