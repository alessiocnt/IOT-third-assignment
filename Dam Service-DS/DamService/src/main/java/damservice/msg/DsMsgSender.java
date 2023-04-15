package damservice.msg;

import damservice.Mode;
import damservice.State;
import msg.SerialCommChannel;

public interface DsMsgSender {
	
	/**
	 * Sends the state to the arduino
	 * @param state the state to send
	 */
	public void sendState(State state);
	
	/**
	 * Sends the mode to the arduino
	 * @param mode the mode to send
	 */
	public void sendMode(Mode mode);
	
	/**
	 * Sends the gap to the arduino
	 * @param gap the gap to send
	 */
	public void sendDamGap(int gap);
	
}
