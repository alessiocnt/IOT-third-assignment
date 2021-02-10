package damservice.data;

import java.util.ArrayList;
import java.util.List;

import damservice.Mode;
import damservice.State;
import damservice.msg.DsMsgSender;

public class DsDataImpl implements DsData {
	
	private List<Float> waterLevel = new ArrayList<>();
	private State state = State.ALARM;
	private Mode mode = Mode.AUTO;
	private int gapLevel = 0;
	//private DsMsgSender msgSender;
	
	public DsDataImpl(/*DsMsgSender msgSender*/) {
		//this.msgSender = msgSender;
		waterLevel.add((float) 1.0);
	}
	
	@Override
	public void pushWaterLevel(float level) {
		this.waterLevel.add(level);
//		this.msgSender.sendWaterLevel(level);
	}

	@Override
	public void setState(State state) {
		this.state = state;
//		this.msgSender.sendState(state);
		if(this.mode == Mode.MANUAL && this.state != State.ALARM) {
			this.setMode(Mode.AUTO);
		}
	}

	@Override
	public void setMode(Mode mode) {
		this.mode = mode;
//		this.msgSender.sendMode(mode);
	}

	@Override
	public void setGapLevel(int gap) {
		this.gapLevel = gap;
//		this.msgSender.sendDamGap(gap);
	}

	@Override
	public List<Float> getWaterLevel() {
		return this.waterLevel;
	}

	@Override
	public State getState() {
		return this.state;
	}

	@Override
	public Mode getMode() {
		return this.mode;
	}

	@Override
	public int getGapLevel() {
		return this.gapLevel;
	}

}
