package seiot.modulo_lab_4_1;

import java.sql.Time;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttServer;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.MqttClientOptions;

public class MQTTServer {

	public static void main(String[] args) {

		Vertx vertx = Vertx.vertx();
//		MqttServer mqttServer = MqttServer.create(vertx);
//		mqttServer.endpointHandler(endpoint -> {
//	
//		  // shows main connect info
//		  System.out.println("MQTT client [" + endpoint.clientIdentifier() + "] request to connect, clean session = " + endpoint.isCleanSession());
//	
//		  if (endpoint.auth() != null) {
//		    System.out.println("[username = " + endpoint.auth().getUsername() + ", password = " + endpoint.auth().getPassword() + "]");
//		  }
//		  if (endpoint.will() != null) {
//		    System.out.println("[will topic = " + endpoint.will().getWillTopic() + " msg = " + new String(endpoint.will().getWillMessageBytes()) +
//		      " QoS = " + endpoint.will().getWillQos() + " isRetain = " + endpoint.will().isWillRetain() + "]");
//		  }
//	
//		  System.out.println("[keep alive timeout = " + endpoint.keepAliveTimeSeconds() + "]");
//	
//		  // accept connection from the remote client
//		  endpoint.accept(false);
//	
//		})
//		  .listen(ar -> {
//	
//		    if (ar.succeeded()) {
//	
//		      System.out.println("MQTT server is listening on port " + ar.result().actualPort());
//		    } else {
//	
//		      System.out.println("Error on starting the server");
//		      ar.cause().printStackTrace();
//		    }
//		  });
		
		MqttClientOptions options = new MqttClientOptions();
		options.setAutoKeepAlive(true);
		System.out.println(options.isAutoKeepAlive());
		MqttClient client = MqttClient.create(vertx, options);
		client.connect(1883, "localhost", s-> {
			System.out.println("Ci stanno tracciando stacca stacca");
			client.publishHandler(r -> {
				  System.out.println("There are new message in topic: " + r.topicName());
				  System.out.println("Content(as string) of the message: " + r.payload().toString());
				  System.out.println("QoS: " + r.qosLevel());
				  // In order to keep the connection alive
				  client.ping();
				})
				  .subscribe("outTopic", 0);
			client.publish("inTopic",
					  Buffer.buffer("Hello from the Vert.x MQTT server"),
					  MqttQoS.EXACTLY_ONCE,
					  false,
					  false);
			client.publish("inTopic",
					  Buffer.buffer("Hello from the Vert.x MQTT server"),
					  MqttQoS.EXACTLY_ONCE,
					  false,
					  false);
			client.publish("inTopic",
					  Buffer.buffer("Hello from the Vert.x MQTT server"),
					  MqttQoS.EXACTLY_ONCE,
					  false,
					  false);
			client.publish("inTopic",
					  Buffer.buffer("Hello from the Vert.x MQTT server"),
					  MqttQoS.EXACTLY_ONCE,
					  false,
					  false);
			client.publish("inTopic",
					  Buffer.buffer("Hello from the Vert.x MQTT server"),
					  MqttQoS.EXACTLY_ONCE,
					  false,
					  false);
			client.exceptionHandler(h -> {
				System.out.println("Errore dio can");
				client.disconnect();
			});
		});
	}
}
