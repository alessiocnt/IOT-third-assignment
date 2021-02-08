package mqtt.client;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.MqttClientOptions;
import io.vertx.mqtt.messages.MqttConnAckMessage;
import io.vertx.mqtt.messages.MqttPublishMessage;

public class DSMqttClientImpl implements DSMqttClient {

	private Vertx vertx;
	private MqttClient client;
	private MqttClientOptions options;
	
	public DSMqttClientImpl() {
		this.vertx = Vertx.vertx();
		this.options = new MqttClientOptions().setAutoKeepAlive(true).setKeepAliveInterval(2);
		this.client = MqttClient.create(this.vertx, options);
		Future<MqttConnAckMessage> f = this.client.connect(1883, "broker.hivemq.com");
		while(!f.isComplete()) { }
		this.client.closeHandler(r -> this.reconnect());
	}
	
	private void reconnect() {
		if(!this.client.isConnected()) {
			this.client = MqttClient.create(this.vertx, options);
			Future<MqttConnAckMessage> f = this.client.connect(1883, "broker.hivemq.com");
			while(!f.isComplete()) { }
		}
	}
	
	@Override
	public void subscribe(String topic, Handler<MqttPublishMessage> handler) {
		
		client.publishHandler(handler).subscribe(topic, 0);

	}

	@Override
	public void publish(String topic, String value) {
		if(!this.client.isConnected()) {
			this.client = MqttClient.create(this.vertx, options);
			Future<MqttConnAckMessage> f = this.client.connect(1883, "broker.hivemq.com");
			while(!f.isComplete()) { }
		}
		client.publish(topic, Buffer.buffer(value), MqttQoS.EXACTLY_ONCE, false, false);

	}

	@Override
	public MqttClient getClient() {
		return this.client;
	}

}
