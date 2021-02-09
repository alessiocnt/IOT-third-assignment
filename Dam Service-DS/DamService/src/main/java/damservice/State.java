package damservice;

public enum State {
	ALARM("alarm"),
	PREALARM("prealarm"),
	NORMAL("normal");
	
	private final String val;
	
	State(String val) {
		this.val = val;
	}

	public String getValue() {
		return this.val;
	}
}
