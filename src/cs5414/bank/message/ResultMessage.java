package cs5414.bank.message;

public class ResultMessage extends Message {

	protected int result = 0;
	protected Message msg;
	
	private static final long serialVersionUID = 1L;
	
	public ResultMessage(String src, String dest, String m_id, Message msgSent, int balance) {
		super(src, dest, m_id);
		result = balance;
		msg = msgSent;
	}
	
	public int getResult() {
		return result;
	}
	
	public Message getMsg() {
		return msg;
	}
	
	public String toString() {
		return "[ResultMessage " + source
				+ " " + destination
				+ " " + id
				+ " " + msg
				+ " " + Integer.toString(result) + "]";
	}
	
}
