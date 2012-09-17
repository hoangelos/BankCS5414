package cs5414.bank.message;

public class TransferMessage extends Message {
	
	protected String serial;
	protected String account_from;
	protected String account_to;
	protected int amount;
	
	private static final long serialVersionUID = 1L;
	
	public TransferMessage(String src, String dest, String m_id,
			String ser, String act_from, String act_to, int amt) {
		super(src, dest, m_id);
		serial = ser;
		account_from = act_from;
		account_to = act_to;
		amount = amt;
	}

	public String getAccountFrom() {
		return account_from;
	}
	
	public String getAccountTo() {
		return account_to;
	}

	public int getAmount() {
		return amount;
	}
	
	public String getSerial() {
		return serial;
	}
	
	public String toString() {
		return "[TransferMessage " + source
				+ " " + destination
				+ " " + id + " " + serial + " " +
				account_from + " " + account_to
				+ " " + amount + "]"; 
	}
	
}
