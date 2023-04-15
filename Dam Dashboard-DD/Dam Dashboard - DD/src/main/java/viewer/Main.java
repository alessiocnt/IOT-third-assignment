package viewer;

import viewer.datacollector.DataCollector;
import viewer.datacollector.DataCollectorImpl;
import viewer.view.View;
import viewer.view.ViewImpl;

public class Main {
	public static void main(String[] args) throws Exception {
		String host = "192.168.1.48";
		int port = 8000;
		
		DataCollector dataCollector = new DataCollectorImpl(host, port);
		View view = new ViewImpl(dataCollector);
		while(true) {
			view.render();
			Thread.sleep(1000);
		}
	}
}
