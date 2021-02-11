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
	private DsMsgSender msgSender;
	
	// Norm->Pre 4m
	// Pre->Ala 4.60m
	
	private final float L1 = (float) 4.00;
	private final float L2 = (float) 4.60;
	private final float DeltaL = (float) 0.04;
	
	public DsDataImpl(DsMsgSender msgSender) {
		this.msgSender = msgSender;
		waterLevel.add((float) 3.0);
	}
	
	@Override
	public void pushWaterLevel(float level) {
		this.waterLevel.add((float) (4.80 - level));
		System.out.println("Livello: " + this.waterLevel.get(this.waterLevel.size() - 1));
		if(this.mode == Mode.AUTO) {
			this.adjustGap();
		}
	}
	
	private void adjustGap() {
		float L = this.waterLevel.get(this.waterLevel.size() - 1);
		int P = 0;
		if(L < L2) {
			P = 0;
		} else if(L < L2 + DeltaL) {
			P = 20;
		} else if(L < L2 + (2 * DeltaL)) {
			P = 40;
		} else if(L < L2 + (3 * DeltaL)) {
			P = 60;
		} else if(L < L2 + (4 * DeltaL)) {
			P = 80;
		} else if (L < 4.80) {
			P = 100;
		}
		this.setGapLevel(P);
	}

	@Override
	public void setState(State state) {
		this.state = state;
		this.msgSender.sendState(state);
		if(this.mode == Mode.MANUAL && this.state != State.ALARM) {
			this.setMode(Mode.AUTO);
		}
	}

	@Override
	public void setMode(Mode mode) {
		this.mode = mode;
		this.msgSender.sendMode(mode);
	}

	@Override
	public void setGapLevel(int gap) {
		System.out.println("Setting gap to " + gap);
		this.gapLevel = gap;
		this.msgSender.sendDamGap(gap);
		System.out.println("Setted");
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
