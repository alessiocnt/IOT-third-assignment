package mqtt.client;


import io.vertx.core.Handler;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.messages.MqttPublishMessage;


public interface DSMqttClient {
	/**
	 * Subscribes to a topic
	 * @param topic identifier of the topic
	 * @param handler Handler for when a message is recived
	 */
	public void subscribe(String topic, Handler<MqttPublishMessage> handler);
	
	/**
	 * Publish something
	 * @param topic identifier of the topic
	 * @param value what to write on the topic
	 */
	public void publish(String topic, String value);
	
	public MqttClient getClient();
}
