package cs5414.bank.message;

public class ResponseMessage extends Message {
	
	protected String inReplyTo;
	protected Object response;
	
	private static final long serialVersionUID = 1L;
	
	public ResponseMessage(String src, String dest, String m_id,
			String rep, Object resp) {
		super(src, dest, m_id);
		inReplyTo = rep;
		response = resp;
	}

	public String getInReplyTo() {
		return inReplyTo;
	}

	public Object getResponse() {
		return response;
	}
	
}
