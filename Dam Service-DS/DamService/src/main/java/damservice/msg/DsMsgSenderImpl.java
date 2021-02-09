package damservice.msg;

import damservice.Mode;
import damservice.State;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.client.WebClient;
import msg.SerialCommChannel;

public class DsMsgSenderImpl implements DsMsgSender {

	private SerialCommChannel arduinoCh;
	private Vertx vertx= Vertx.vertx();
	private WebClient client = WebClient.create(vertx);
	private String DDAddress;
	private int DDPort;
	private String DMAddress;
	private int DMPort;
	
	public DsMsgSenderImpl(String arduinoPort, String DDAddress, int DDPort, String DMAddress, int DMPort) {
		boolean ok = false;
		while(!ok) {
			ok = true;
			try {
				arduinoCh = new SerialCommChannel(arduinoPort, 9600);
			} catch (Exception e) {
				ok = false;
				System.out.println("Connettere il DamController");
			}
		}
		this.DDAddress = DDAddress;
		this.DDPort = DDPort;
		this.DMAddress = DMAddress;
		this.DMPort = DMPort;
	}
	
	@Override
	public void sendState(State state) {
		arduinoCh.sendMsg("state:" + state);
//		this.client
//		  .get(this.DDPort, this.DDAddress, "/state")
//		  .sendBuffer(Buffer.buffer(state.getValue()))
//		  .onFailure(err ->
//		    System.out.println("Error comunicating with the Dam Dashboard " + err.getMessage()));
//		this.client
//		  .get(this.DMPort, this.DMAddress, "/state")
//		  .sendBuffer(Buffer.buffer(state.getValue()))
//		  .onFailure(err ->
//		    System.out.println("Error comunicating with the Dam Dashboard " + err.getMessage()));
	}

	@Override
	public void sendMode(Mode mode) {
		arduinoCh.sendMsg("mode:" + mode);
//		this.client
//		  .get(this.DDPort, this.DDAddress, "/mode")
//		  .sendBuffer(Buffer.buffer(mode.getValue()))
//		  .onFailure(err ->
//		    System.out.println("Error comunicating with the Dam Dashboard " + err.getMessage()));
	}

	@Override
	public void sendWaterLevel(float level) {
//		this.client
//		  .get(this.DDPort, this.DDAddress, "/level")
//		  .sendBuffer(Buffer.buffer(String.valueOf(level)))
//		  .onFailure(err ->
//		    System.out.println("Error comunicating with the Dam Dashboard " + err.getMessage()));
//		this.client
//		  .get(this.DMPort, this.DMAddress, "/level")
//		  .sendBuffer(Buffer.buffer(String.valueOf(level)))
//		  .onFailure(err ->
//		    System.out.println("Error comunicating with the Dam Dashboard " + err.getMessage()));

	}

	@Override
	public void sendDamGap(int gap) {
		arduinoCh.sendMsg("level:" + gap);
//		this.client
//		  .get(this.DDPort, this.DDAddress, "/gap")
//		  .sendBuffer(Buffer.buffer(gap))
//		  .onFailure(err ->
//		    System.out.println("Error comunicating with the Dam Dashboard " + err.getMessage()));
//		this.client
//		  .get(this.DMPort, this.DMAddress, "/gap")
//		  .sendBuffer(Buffer.buffer(gap))
//		  .onFailure(err ->
//		    System.out.println("Error comunicating with the Dam Dashboard " + err.getMessage()));
	}

}
