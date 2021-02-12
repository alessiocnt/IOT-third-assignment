package viewer.datacollector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;

public class DataCollectorImpl implements DataCollector {

	private static final int Nril = 20;
	private final String host;
	private final int port;
	private final Vertx vertx;
	private final WebClient client;
	
	private List<Double> waterLevel = new ArrayList<>();
	private List<Double> time = new ArrayList<>();
	//private double startTime = new Date().getTime() / 1000;
	private String state;
	private int gap;
	private String mode;
	
	public DataCollectorImpl(final String host, final int port) {
		this.host = host;
		this.port = port;
		this.vertx = Vertx.vertx();
		this.client = WebClient.create(vertx);
		
		this.waterLevel.add((double) 3.0);
		this.time.add((double) (0));
		this.state = "normal";
		this.gap = 0;
		this.mode = "auto";
	}
	
	public List<Double> getWaterLevel(){
		return this.waterLevel;
	}
	
	public List<Double> getTime(){
		return this.time;
	}

	public String getState(){
		return this.state;
	}
	
	public String getMode(){
		return this.mode;
	}
	
	public int getGap(){
		return this.gap;
	}
	
	public void CollectWaterLevel() {
		double t;
		client
		  .get(port, host, "/time")
		  .send()
		  .onSuccess(res -> { 
			  //System.out.println("Getting - Received response with status code: " + res.bodyAsString());
			  this.time.add(Double.parseDouble(res.bodyAsString()));
		  })
		  .onFailure(err ->
		    System.out.println("Something went wrong " + err.toString()));
		if(this.time.size() > 1 && this.time.get(this.time.size() - 1) == this.time.get(this.time.size() -2)) {
			this.time.remove(this.time.size() - 1);
			return;
		}
		client
		  .get(port, host, "/level")
		  .send()
		  .onSuccess(res -> { 
			  //System.out.println("Getting - Received response with status code: " + res.statusCode());
			  this.waterLevel.add(Double.parseDouble(res.bodyAsString()));
			  
		  })
		  .onFailure(err ->
		    System.out.println("Something went wrong " + err.getMessage()));
		
		if(this.waterLevel.size() > Nril) {
			this.waterLevel.remove(0);
			this.time.remove(0);
		}
	}

	public void CollectState() {
		client
		  .get(port, host, "/state")
		  .send()
		  .onSuccess(res -> { 
			  //System.out.println("Getting - Received response with status code: " + res.statusCode());
			  this.state = res.bodyAsString();
		  })
		  .onFailure(err ->
		    System.out.println("Something went wrong " + err.getMessage()));
	}

	public void CollectMode() {
		client
		  .get(port, host, "/mode")
		  .send()
		  .onSuccess(res -> { 
			  //System.out.println("Getting - Received response with status code: " + res.statusCode());
			  this.mode = res.bodyAsString();
		  })
		  .onFailure(err ->
		    System.out.println("Something went wrong " + err.getMessage()));
	}

	public void CollectGap() {
		client
		  .get(port, host, "/gap")
		  .send()
		  .onSuccess(res -> { 
			  //System.out.println("Getting - Received response with status code: " + res.statusCode());
			  this.gap = Integer.parseInt(res.bodyAsString());
		  })
		  .onFailure(err ->
		    System.out.println("Something went wrong " + err.getMessage()));
	}
	
}
