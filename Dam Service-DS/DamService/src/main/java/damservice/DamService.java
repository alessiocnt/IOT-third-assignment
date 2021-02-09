package damservice;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;

import com.sun.net.httpserver.HttpServer;

import http.querymapper.QueryMapper;
import io.vertx.mqtt.MqttClient;
import mqtt.client.DSMqttClient;
import mqtt.client.DSMqttClientImpl;


public class DamService {

	public static void main(String[] args) {
		DsData data = new DsDataImpl();
		mqttHandler(data);
		
        httpHandler(data);
        
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
			default:
				data.setState(State.ALARM);
				break;
			}
			System.out.println("Stato:" + data.getState().getValue());
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
	
	public static void httpHandler(DsData data) {
        HttpServer server;
		try {
			server = HttpServer.create(new InetSocketAddress(8000), 0);
			server.createContext("/test", (t) -> {
				String response = "This is the response ";
				Map<String, String> m = QueryMapper.resolveQuery(t.getRequestURI().getQuery());
				m.forEach((k, v) -> {
					if(k.equals("water")) {
						data.pushWaterLevel(Float.parseFloat(v));
						System.out.println("YEE ");
					} if (k.equals("b")) {
						System.out.println("Merdeeeee " + v);
					}
				});
	            t.sendResponseHeaders(200, response.length());
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
	        });
	        server.setExecutor(null); // creates a default executor
	        server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
