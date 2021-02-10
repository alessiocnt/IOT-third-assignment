package damservice.msg;

import damservice.Mode;
import damservice.State;

public interface DsMsgSender {
	public void sendState(State state);
	
	public void sendMode(Mode mode);
	
	public void sendDamGap(int gap);
}
