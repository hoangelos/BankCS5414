package cs5414.bank.message;

import java.io.Serializable;

public class Message implements Serializable {
	
	protected String source;
	protected String destination;
	protected String id;
	
	private static final long serialVersionUID = 1L;
	
	public Message(String src, String dest, String m_id) {
		source = src;
		destination = dest;
		id = m_id;
	}
	
	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	public String getMessageId() {
		return id;
	}
	
	public String toString() {
		return "[Message " + source
				+ " " + destination
				+ " " + id + "]"; 
	}
	
}
