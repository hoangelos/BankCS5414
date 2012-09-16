package cs5414.bank.message;

public class DepositMessage extends Message {
	
	protected String account;
	protected int amount;
	
	private static final long serialVersionUID = 1L;
	
	public DepositMessage(String src, String dest, String m_id,
			String act, int amt) {
		super(src, dest, m_id);
		account = act;
		amount = amt;
	}

	public String getAccount() {
		return account;
	}

	public int getAmount() {
		return amount;
	}
	
	public String toString() {
		return "[DepositMessage " + source
				+ " " + destination
				+ " " + id + " "
				+ account + " " + amount
				+ "]"; 
	}
	
}
