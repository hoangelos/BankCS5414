package cs5414.bank.message;

public class QueryMessage extends Message {
	protected String account;
	
	private static final long serialVersionUID = 1L;
	
	public QueryMessage(String src, String dest, String m_id, String act) {
		super(src, dest, m_id);
		account = act;
	}

	public String getAccount() {
		return account;
	}
	
	public String toString() {
		return "[QueryMessage " + source
				+ " " + destination
				+ " " + id
				+ " " + account + "]"; 
	}
	
}
