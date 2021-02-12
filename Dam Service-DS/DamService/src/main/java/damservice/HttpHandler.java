package damservice;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;

import com.sun.net.httpserver.HttpServer;

import damservice.data.DsData;
import http.querymapper.QueryMapper;

public class HttpHandler {
	private HttpServer server;
	private DsData data;
	
	public HttpHandler(DsData data) {
		this.data = data;
		try {
			this.server = HttpServer.create(new InetSocketAddress(8000), 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createModeContext();
		createStateContext();
		createLevelContext();
		createTimeContext();
		createGapContext();
		createSetContext();
		this.server.setExecutor(null); // creates a default executor
	}
	
	

	public void start() {
		this.server.start();
	}
	
	public void stop() {
		this.server.stop(0);
	}
	
	private void createModeContext() {
		this.server.createContext("/mode", (t) -> {
			String response = this.data.getMode().getValue();
			t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
		});
	}
	
	private void createStateContext() {
		this.server.createContext("/state", (t) -> {
			String response = this.data.getState().getValue();
			t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
		});
	}
	
	private void createLevelContext() {
		this.server.createContext("/level", (t) -> {
			String response = String.valueOf(this.data.getWaterLevel().get(this.data.getWaterLevel().size() - 1));
			t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
		});
	}
	
	private void createTimeContext() {
		this.server.createContext("/time", (t) -> {
			String response = String.valueOf(this.data.getMeasurementsTimes().get(this.data.getWaterLevel().size() - 1));
			System.out.println("Sending time = " + response);
			t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
		});
	}
	
	private void createGapContext() {
		this.server.createContext("/gap", (t) -> {
			String response = String.valueOf(this.data.getGapLevel());
			t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
		});
	}
	
	private void createSetContext() {
		this.server.createContext("/setvalues", (t) -> {
			String response = "ok";
			Map<String, String> m = QueryMapper.resolveQuery(t.getRequestURI().getQuery());
			m.forEach((k, v) -> {
				if(k.equals("mode")) {
					this.data.setMode(v.equalsIgnoreCase("auto") ? Mode.AUTO : Mode.MANUAL);
					System.out.println("Changed mode to " + this.data.getMode().getValue());
				} if (k.equals("gap")) {
					if(this.data.getMode() == Mode.MANUAL) {
						this.data.setGapLevel(Integer.valueOf(v));
						System.out.println("Changed gap to " + this.data.getGapLevel());
					} else {
						System.out.println("Automatic mode, DAM gap is automatically set");
					}
				}
			});
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });
	}

}
