package cs5414.bank.message;

public class BankReplyMessage extends BaseMessage {
	
	private static final long serialVersionUID = 1L;
	
	public String account;
	public int balance;
	
	public String toString() {
		return super.toString() + " -> BankReplyMessage [account "
				+ account + " balance " + balance + "]";
	}
	
}
