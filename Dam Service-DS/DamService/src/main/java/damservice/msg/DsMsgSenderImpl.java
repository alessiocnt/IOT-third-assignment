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
		}

	}
	
	@Override
	public void sendState(State state) {
		int val;
		if (state == State.ALARM) {
			val = 1;
		} else {
			val = 0;
		}
		
		arduinoCh.sendMsg("state:" + val);
	}

	@Override
	public void sendMode(Mode mode) {
		int val;
		if(mode == Mode.MANUAL) {
			val = 1;
		} else {
			val = 0;
		}
		arduinoCh.sendMsg("mode:" + val);
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
