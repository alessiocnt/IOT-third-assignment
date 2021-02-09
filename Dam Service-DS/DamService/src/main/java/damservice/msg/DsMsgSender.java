package damservice.msg;

import damservice.Mode;
import damservice.State;

public interface DsMsgSender {
	public void sendState(State state);
	
	public void sendMode(Mode mode);
	
	public void sendWaterLevel(float level);
	
	public void sendDamGap(int gap);
}
