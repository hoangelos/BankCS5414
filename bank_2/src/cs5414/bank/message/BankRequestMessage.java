package cs5414.bank.message;

public class BankRequestMessage extends BaseMessage {
	
	public enum RequestType {
		
		QUERY		(0),
		DEPOSIT 	(1),
		WITHDRAW	(2),
		TRANSFER	(3);

		public int type;
		
		RequestType(int type) {
			this.type = type;
		}
		
	}
	
	private static final long serialVersionUID = 1L;
	
	public RequestType requestType;
	public int serial;
	public String account;
	public String accountInto;
	public int amount;
	
	public String toString() {
		return super.toString() + " -> BankRequestMessage [requestType "
				+ requestType.name() + " serial " + serial + " account "
				+ account + " accountInto " + accountInto + " amount "
				+ amount + "]";
	}
	
}
