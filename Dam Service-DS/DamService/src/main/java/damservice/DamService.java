package damservice;

import damservice.data.DsData;
import damservice.data.DsDataImpl;
import io.vertx.mqtt.MqttClient;
import mqtt.client.DSMqttClient;
import mqtt.client.DSMqttClientImpl;


public class DamService {

	public static void main(String[] args) {
		DsData data = new DsDataImpl(/*new DsMsgSenderImpl("ACM0" COM3  ttyACM0 , null, 8000, null, 8000)*/);
		mqttHandler(data);
		
        HttpHandler httpServer = new HttpHandler(data);
        httpServer.start();
        
        while(true) {
        	try {
        		if(data.getWaterLevel().get(data.getWaterLevel().size() - 1) != null) {
            		System.out.println("YEEEET " + data.getWaterLevel().get(data.getWaterLevel().size() - 1));
            	}
        	} catch (Exception e) {
				// TODO: handle exception
			}
        	try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }

	}
	
	private static void mqttHandler(DsData data) {
		DSMqttClient clientS = new DSMqttClientImpl();
		DSMqttClient clientD = new DSMqttClientImpl();
		
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
			System.out.println("Stato:" + data.getState().getValue());
		});
		clientD.subscribe("SimAleD", r -> {
			data.pushWaterLevel(Float.valueOf(r.payload().toString()));
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
