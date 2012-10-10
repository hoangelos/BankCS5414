package cs5414.bank.message;

public class StringMessage extends BaseMessage {
	
	private static final long serialVersionUID = 1L;
	
	public String messageBody;
	
	public String toString() {
		return super.toString()
				+ " -> StringMessage [body " + messageBody + "]";
	}
	
}
