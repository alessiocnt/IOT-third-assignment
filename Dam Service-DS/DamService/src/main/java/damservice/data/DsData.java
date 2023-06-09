package damservice.data;

import java.util.List;

import damservice.Mode;
import damservice.State;

public interface DsData {
	
	/**
	 * Pushes a new water misuration
	 * @param level the water level
	 */
	public void pushWaterLevel(float level);
	
	/**
	 * Sets the current state
	 * @param state the state
	 */
	public void setState(State state);
	
	/**
	 * Sets the current mode
	 * @param mode the mode
	 */
	public void setMode(Mode mode);
	
	/**
	 * Sets the current dam openness gap
	 * @param gap the gap
	 */
	public void setGapLevel(int gap);
	
	/**
	 * 
	 * @return all the water level
	 */
	public List<Float> getWaterLevel();
	
	/**
	 * 
	 * @return a list of all time when level measurement where made, starting from system initialization 
	 */
	public List<Double> getMeasurementsTimes();
	
	/**
	 *  
	 * @return the current state
	 */
	public State getState();
	
	/**
	 * 
	 * @return the current mode
	 */
	public Mode getMode();
	
	/**
	 * 
	 * @return the current dam gap level
	 */
	public int getGapLevel();
	
}
