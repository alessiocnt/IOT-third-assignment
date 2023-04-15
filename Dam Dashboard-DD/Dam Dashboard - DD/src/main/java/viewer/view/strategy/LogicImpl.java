package viewer.view.strategy;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import org.knowm.xchart.XYChart;
import viewer.datacollector.DataCollector;

public class LogicImpl implements Logic{
	private final JLabel lblState;
	private final JLabel lblMode;
	private final JLabel lblGap;
	private final DataCollector dataCollector;
	private final XYChart chart;
	private final List<Double> fakeData = new ArrayList<>();
	private String state;

	public LogicImpl(final JLabel lblState, final JLabel lblMode, final JLabel lblGap, final DataCollector dataCollector, final XYChart chart) {
		this.lblState = lblState;
		this.lblMode = lblMode;
		this.lblGap = lblGap;
		this.dataCollector = dataCollector;
		this.chart = chart;
		this.fakeData.add((double) 0);
		this.state = dataCollector.getState();
	}
	
	public void execute() {
		this.dataCollector.CollectState();
		this.state = dataCollector.getState();
		if(this.state.equals("normal")) {
			doNormalState();
		} else if(this.state.equals("prealarm")) {
			doPreAlarmState();
		} else {
			doAlarmState();
		}
	}
	
	private void doNormalState() {
		this.lblState.setText("State: " + this.state.toUpperCase());
		this.lblMode.setVisible(false);
		this.lblGap.setVisible(false);
		chart.updateXYSeries("Water Level", fakeData, fakeData, null);
		System.out.println("Settato normale");
	}
	
	private void doPreAlarmState() {
		this.lblState.setText("State: " + this.state.toUpperCase());
		this.lblMode.setVisible(false);
		this.lblGap.setVisible(false);
		this.dataCollector.CollectWaterLevel();
		chart.updateXYSeries("Water Level", this.dataCollector.getTime(), this.dataCollector.getWaterLevel(), null);
		System.out.println("Settato prealarm");
	}
	
	private void doAlarmState() {
		this.lblState.setText("State: " + this.state.toUpperCase());
		this.dataCollector.CollectMode();
		this.lblMode.setText("Mode: " + this.dataCollector.getMode().toUpperCase());
		this.dataCollector.CollectGap();
		this.lblGap.setText("Gap: " + this.dataCollector.getGap());
		this.dataCollector.CollectWaterLevel();
		chart.updateXYSeries("Water Level", this.dataCollector.getTime(), this.dataCollector.getWaterLevel(), null);
		this.lblMode.setVisible(true);
		this.lblGap.setVisible(true);
		System.out.println("Settato alarm");
	}
}
