package damservice;

public enum Mode {
	AUTO("auto"),
	MANUAL("manual");
	
	private final String val;
	
	Mode(String val) {
		this.val = val;
	}
	
	public String getValue() {
		return this.val;
	}
}
