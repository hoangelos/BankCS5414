package cs5414.bank.message;

public class QueryMessage extends Message {
	
	protected String serial;
	protected String account;
	
	private static final long serialVersionUID = 1L;
	
	public QueryMessage(String src, String dest, String m_id,
			String ser, String act) {
		super(src, dest, m_id);
		serial = ser;
		account = act;
	}

	public String getAccount() {
		return account;
	}
	
	public String getSerial() {
		return serial;
	}
	
	public String toString() {
		return "[QueryMessage " + source
				+ " " + destination
				+ " " + id + " " + serial
				+ " " + account + "]"; 
	}
	
}
