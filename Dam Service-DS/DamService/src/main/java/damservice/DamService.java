package damservice;

import io.vertx.mqtt.MqttClient;
import mqtt.client.DSMqttClient;
import mqtt.client.DSMqttClientImpl;

public class DamService {

	public static void main(String[] args) {
		DSMqttClient client = new DSMqttClientImpl();
		
		client.subscribe("SimAleS", r -> {
			System.out.println("Stato:" + r.payload().toString());
		});
		client.subscribe("SimAleD", r -> {
			System.out.println("Distanza:" + r.payload().toString());
		});
		client.publish("outTopic", "Asbregafioi");
		
		MqttClient c = client.getClient();
		while(true) {
			c.ping();
		}

	}

}
