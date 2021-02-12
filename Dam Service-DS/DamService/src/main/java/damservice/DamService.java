package damservice;

import damservice.data.DsData;
import damservice.data.DsDataImpl;
import damservice.msg.DsMsgSender;
import damservice.msg.DsMsgSenderImpl;
import io.vertx.mqtt.MqttClient;
import mqtt.client.DSMqttClient;
import mqtt.client.DSMqttClientImpl;
import msg.SerialCommChannel;


public class DamService {

	public static void main(String[] args) {
		DsMsgSender msgSender = new DsMsgSenderImpl("COM3" /*COM3  ttyACM0*/); 
		DsData data = new DsDataImpl(msgSender);
		mqttHandler(data);
		
        HttpHandler httpServer = new HttpHandler(data);
        httpServer.start();
//        SerialCommChannel ch = msgSender.getCh();
//        while(true) {
//        	if(ch.isMsgAvailable()) {
//        		try {
//					System.out.println(ch.receiveMsg());
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//        	}
//        	try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
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
			//System.out.println("Stato:" + data.getState().getValue());
		});
		clientD.subscribe("SimAleD", r -> {
			data.pushWaterLevel(Float.valueOf(r.payload().toString()));
			//System.out.println("Distanza:" + r.payload().toString());
		});
		
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
