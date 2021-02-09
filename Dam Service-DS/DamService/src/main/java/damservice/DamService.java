package damservice;

import io.vertx.mqtt.MqttClient;
import mqtt.client.DSMqttClient;
import mqtt.client.DSMqttClientImpl;

public class DamService {

	public static void main(String[] args) {
		DSMqttClient clientS = new DSMqttClientImpl();
		DSMqttClient clientD = new DSMqttClientImpl();
		
		clientS.subscribe("SimAleS", r -> {
			System.out.println("Stato:" + r.payload().toString());
		});
		clientD.subscribe("SimAleD", r -> {
			System.out.println("Distanza:" + r.payload().toString());
		});
		//client.publish("outTopic", "Asbregafioi");
		
		final MqttClient cS = clientS.getClient();
		final MqttClient cD = clientD.getClient();
		
        Runnable r = new Runnable() {
			public void run() {
				while (true) {
					clientS.reconnect();
					clientD.reconnect();
					try {
						cS.ping();
						cD.ping();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		Thread thread = new Thread(r);
        thread.start();

	}

}
