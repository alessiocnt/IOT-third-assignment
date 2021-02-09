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
			System.out.print("Distanza: ");
			System.out.println(r.payload().toString());
		});
		//client.publish("outTopic", "Asbregafioi");
		
		final MqttClient c = client.getClient();
		
        
        Runnable r = new Runnable() {
			public void run() {
				while (true) {
					client.reconnect();
					try {
						c.ping();
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
