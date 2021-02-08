#include <Arduino.h>
#include "Mqtt.h"
#include <ESP8266WiFi.h>        // Include the Wi-Fi library
#include <PubSubClient.h>

unsigned long lastMsg = 0;
char msg[MSG_BUFFER_SIZE];
WiFiClient espClient;
PubSubClient client(espClient);

Mqtt::Mqtt() {}

void callback(char* topic, byte* payload, unsigned int length) {
	Serial.print("Message arrived [");
	Serial.print(topic);
	Serial.print("] ");
	for (unsigned int i = 0; i < length; i++) {
	Serial.print((char)payload[i]);
	}
	Serial.println();
}

void Mqtt::connect(const char* mqtt_server) {
	this->mqtt_server = mqtt_server;
	client.setServer(mqtt_server, 1883);
  	client.setCallback(callback);
}

void Mqtt::subscribe(const char* topic) {
	this->loop();
	client.subscribe(topic);
}

void Mqtt::publish(char* topic, char* msg) {
	this->loop();
	client.publish(topic, msg);
}

void Mqtt::loop() {
	if (!client.connected()) {
    	reconnect();
  	}
  	client.loop();
}

void Mqtt::reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
	Serial.print("Attempting MQTT connection...");
	// Create a random client ID
	String clientId = "ESP8266Client-";
	clientId += String(random(0xffff), HEX);
	// Attempt to connect
	if (client.connect(clientId.c_str())) {
	  Serial.println("connected");
	} else {
	  Serial.print("failed, rc=");
	  Serial.print(client.state());
	  Serial.println(" try again in 2 seconds");
	  // Wait 2 seconds before retrying
	  delay(2000);
	}
  }
}

