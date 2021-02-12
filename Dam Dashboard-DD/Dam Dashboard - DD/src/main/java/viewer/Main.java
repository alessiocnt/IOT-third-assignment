package viewer;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.client.WebClient;
import viewer.datacollector.DataCollector;
import viewer.datacollector.DataCollectorImpl;
import viewer.view.View;
import viewer.view.ViewImpl;

public class Main {
	public static void main(String[] args) throws Exception {
		String host = "localhost";
		int port = 8080;
		
		DataCollector dataCollector = new DataCollectorImpl(host, port);
		View view = new ViewImpl(dataCollector);
		
		

		
		/*
		while(true) {
			
			
			
			System.out.println(msg);
			dataCollector.collectData(msg);
		}*/
	}
}
