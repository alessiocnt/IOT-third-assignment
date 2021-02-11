package damservice.msg;

import damservice.Mode;
import damservice.State;
import msg.SerialCommChannel;

public interface DsMsgSender {
	public void sendState(State state);
	
	public void sendMode(Mode mode);
	
	public void sendDamGap(int gap);
	
	public SerialCommChannel getCh();
}
