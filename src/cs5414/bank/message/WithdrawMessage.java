package cs5414.bank.message;

public class WithdrawMessage extends Message {
	
	protected String account;
	protected int amount;
	
	private static final long serialVersionUID = 1L;
	
	public WithdrawMessage(String src, String dest, String m_id,
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
		return "[WithdrawMessage " + source
				+ " " + destination
				+ " " + id + " "
				+ account + " " + amount
				+ "]"; 
	}
	
}
