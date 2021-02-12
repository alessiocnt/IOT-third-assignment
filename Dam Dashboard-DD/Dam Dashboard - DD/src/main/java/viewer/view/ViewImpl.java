package viewer.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.*;
import javax.swing.*;
import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

import viewer.datacollector.DataCollector;

public class ViewImpl implements View {
	
	private Frame f;
	private JPanel p;
	private JLabel lblState;
	private JLabel lblMode;
	private JLabel lblGap;
	
	private DataCollector dataCollector;
	private XYChart chart;
	
	public ViewImpl(DataCollector dataCollector) {
		this.dataCollector = dataCollector;
		this.prepareCharts();
		this.prepareGraphics();
	}
	
	private void prepareCharts() {
		List<Double> fakeData = new ArrayList<>();
		fakeData.add((double) 0);
		
		this.chart = new XYChartBuilder().xAxisTitle("Time").yAxisTitle("Water Level").width(1200).height(400).build();
		XYSeries series = this.chart.addSeries("Water Level", null, dataCollector.getWaterLevel());
	    series.setMarker(SeriesMarkers.NONE);
	    chart.getStyler().setYAxisMin((double) -10);
    	chart.getStyler().setYAxisMax((double) 10);
    	chart.getStyler().setLegendVisible(false);
	}
	
	private void prepareGraphics() {
		this.f = new SwingWrapper<XYChart>(chart).displayChart();
		this.p = new JPanel();
		this.p.setLayout(new GridLayout(1, 3));
		
	    this.lblState = new JLabel("State", SwingConstants.CENTER);
		this.lblState.setFont(new Font(this.lblState.getFont().getFontName(), Font.PLAIN, 20));
		this.lblGap = new JLabel("Gap", SwingConstants.CENTER);
		this.lblGap.setFont(new Font(this.lblState.getFont().getFontName(), Font.PLAIN, 20));
		this.lblMode = new JLabel("Mode", SwingConstants.CENTER);
		this.lblMode.setFont(new Font(this.lblState.getFont().getFontName(), Font.PLAIN, 20));
		
		f.setLayout(new GridLayout(2, 1));
		p.add(lblState);
		p.add(lblMode);
		p.add(lblGap);
	    
	    this.p.setBackground(new Color(210, 210, 210));
	    this.f.add(this.p);
	    // set the size of frame 
	    this.f.setSize(1200, 800); 
	    this.f.setTitle("Dam Dashboard - DD");
	    this.f.pack();
	    this.f.setVisible(true); 
	}

	@Override
	public void setData(List<Double> time, List<Double> distance, List<Double> speed, List<Double> acceleration) {
//		charts.get(0).updateXYSeries("Distance", time, distance, null);
//	    charts.get(1).updateXYSeries("Speed", time, speed, null);
//	    charts.get(2).updateXYSeries("Acceleration", time, acceleration, null);
	    f.repaint();
		
	}

	@Override
	public void setState(String state) {
		this.lblState.setText("Current state: " + state);
		  
		  this.lblState.repaint();
		  
	}

	@Override
	public void setCollector(DataCollector dataCollector) {
		this.dataCollector = dataCollector;
	}

	@Override
	public void setFreq(String freq) {
		//this.lblFreq.setText("Sampling freq: " + freq + "Hz");
	}
}