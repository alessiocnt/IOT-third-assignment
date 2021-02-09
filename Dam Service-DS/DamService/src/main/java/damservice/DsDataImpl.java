package damservice;

import java.util.ArrayList;
import java.util.List;

public class DsDataImpl implements DsData {
	
	List<Float> waterLevel = new ArrayList<>();
	State state;
	Mode mode;
	int gapLevel;

	@Override
	public void pushWaterLevel(float level) {
		this.waterLevel.add(level);
	}

	@Override
	public void setState(State state) {
		this.state = state;
	}

	@Override
	public void setMode(Mode mode) {
		this.mode = mode;
	}

	@Override
	public void setGapLevel(int gap) {
		this.gapLevel = gap;
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
