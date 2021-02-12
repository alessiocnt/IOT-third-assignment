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
	
	public DsMsgSenderImpl(String arduinoPort) {
		boolean ok = false;
		while(!ok) {
			ok = true;
			try {
				arduinoCh = new SerialCommChannel(arduinoPort, 9600);
			} catch (Exception e) {
				ok = false;
				System.out.println("Connettere il DamController");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	@Override
	public void sendState(State state) {	
		arduinoCh.sendMsg("state:" + state.getValue());
	}

	@Override
	public void sendMode(Mode mode) {
		arduinoCh.sendMsg("mode:" + mode.getValue());
	}


	@Override
	public void sendDamGap(int gap) {
		arduinoCh.sendMsg("gap:" + gap);
	}

	@Override
	public SerialCommChannel getCh() {
		return this.arduinoCh;
	}

}
