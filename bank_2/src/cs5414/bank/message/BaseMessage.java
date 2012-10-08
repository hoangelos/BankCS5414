package cs5414.bank.message;

import java.io.Serializable;

public class BaseMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static int autoNumCounter = 0; //so really, they start at 1
	
	public String source;
	public String destination;
	public int msgNumForReplies;
	public int inReplyToNum;
	
	public BaseMessage() {
		msgNumForReplies = -1;
		inReplyToNum = -1;
	}
	
	public void autoNumber() {
		++autoNumCounter;
		msgNumForReplies = autoNumCounter;
	}
	
	public String toString() {
		return "Message "
				+ "[source " + source
				+ " destination " + destination
				+ " number " + msgNumForReplies
				+ " reply-to " + inReplyToNum + "]";
	}
	
}
