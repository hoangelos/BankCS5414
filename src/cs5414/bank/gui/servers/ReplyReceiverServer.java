package cs5414.bank.gui.servers;


import cs5414.bank.message.BaseMessage;
import cs5414.bank.network.BaseServer;
import cs5414.bank.network.NetworkInfo;

public class ReplyReceiverServer extends BaseServer {
	
	private int counter;
	
	public ReplyReceiverServer(String name, NetworkInfo net) {
		super(name, net);
		counter = 0;
	}
	
	protected void processMessage(BaseMessage message) {
		System.err.println("Message received #" + counter + ": " + message);
		++counter;
		processCallback(message);
	}
	
	protected void processCallback(BaseMessage message) {
	}
}