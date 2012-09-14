package cs5414.bank.message;

public class DebugMessage extends Message {

	protected String text;
	
	private static final long serialVersionUID = 1L;
	
	public DebugMessage(String src, String dest, String m_id, String txt) {
		super(src, dest, m_id);
		text = txt;
	}
	
	public String getText() {
		return text;
	}
	
	public String toString() {
		return "[DebugMessage " + source
				+ " " + destination
				+ " " + id
				+ " " + text + "]";
	}
	
}
