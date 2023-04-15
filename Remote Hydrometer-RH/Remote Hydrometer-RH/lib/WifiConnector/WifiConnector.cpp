#include <Arduino.h>
#include "WifiConnector.h"
#include <ESP8266WiFi.h> 

void WifiConnector::setupWifi(const char* ssid, const char* password)
{
  WiFi.begin(ssid, password);
  Serial.print("Connecting to ");
  Serial.print(ssid); Serial.println(" ...");

  int i = 0;
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(++i); Serial.print(' ');
  }
  Serial.print("IP address:\t");
  Serial.println(WiFi.localIP());
}