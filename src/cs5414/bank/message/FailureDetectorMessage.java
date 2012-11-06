package cs5414.bank.message;

public class FailureDetectorMessage extends BaseMessage {
	
	public enum RequestType {
		
		FAILED		(0),
		RECOVERED 	(1);

		public int type;
		
		RequestType(int type) {
			this.type = type;
		}
		
	}
	
	private static final long serialVersionUID = 1L;
	
	public RequestType requestType;
	public long serial;
	public String node;
	
	public String toString() {
		return super.toString() + " -> FailureDetectorMessage [requestType "
				+ requestType.name() + " serial " + serial + " node "
				+ node + "]";
	}
}
