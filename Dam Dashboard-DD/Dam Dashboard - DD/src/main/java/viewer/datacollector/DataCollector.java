package viewer.datacollector;

import java.util.List;

/**
 * Models a DataCollector.
 */
public interface DataCollector {
	/**
	 * Allows the collection of data received from http request.
	 */
	public void CollectWaterLevel();
	public void CollectState();
	public void CollectMode();
	public void CollectGap();
	
	/**
	 * Getters
	 */
	public List<Double> getWaterLevel();
	public List<Double> getTime();
	public String getState();
	public String getMode();
	public int getGap();
}
