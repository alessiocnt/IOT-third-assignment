package damservice;

import damservice.data.DsData;
import damservice.data.DsDataImpl;
import damservice.msg.DsMsgSender;
import damservice.msg.DsMsgSenderImpl;
import io.vertx.mqtt.MqttClient;
import mqtt.client.DSMqttClient;
import mqtt.client.DSMqttClientImpl;


public class DamService {

	public static void main(String[] args) {
		DsMsgSender msgSender = new DsMsgSenderImpl(args[0] /*COM3  ttyACM0*/); 
		DsData data = new DsDataImpl(msgSender);
		mqttHandler(data);
		
        HttpHandler httpServer = new HttpHandler(data);
        httpServer.start();
	}
	
	private static void mqttHandler(DsData data) {
		DSMqttClient clientS = new DSMqttClientImpl();
		DSMqttClient clientD = new DSMqttClientImpl();
		// Subscribes to the mqtt topics
		clientS.subscribe("SimAleS", r -> {
			switch (r.payload().toString()) {
			case "normal":
				data.setState(State.NORMAL);
				break;
			case "prealarm":
				data.setState(State.PREALARM);
				break;
			default:
				data.setState(State.ALARM);
				break;
			}
		});
		clientD.subscribe("SimAleD", r -> {
			data.pushWaterLevel(Float.valueOf(r.payload().toString()));
		});
		
		final MqttClient cS = clientS.getClient();
		final MqttClient cD = clientD.getClient();
		// Keeps the mqtt connection alive
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
