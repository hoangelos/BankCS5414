package cs5414.bank.test;

import cs5414.bank.message.BaseMessage;
import cs5414.bank.message.StringMessage;
import cs5414.bank.network.BaseServer;
import cs5414.bank.network.MessageSenderClient;
import cs5414.bank.network.NetworkInfo;

public class PingSender {
	
	NetworkInfo network;
	String myName;
	String targetName;
	
	public PingSender(NetworkInfo net, String from, String to) {
		network = net;
		myName = from;
		targetName = to;
	}
	
	public void start() {
		ReplyReceiverServer replier = new ReplyReceiverServer();
		replier.start();
		MessageSenderClient sender = new MessageSenderClient(myName, network);
		for (int i = 0; i < 10; ++i) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace(System.err);
			}
			System.err.println("Sending ping #" + i);
			StringMessage message = new StringMessage();
			message.autoNumber();
			message.source = myName;
			message.destination = targetName;
			message.messageBody = "Ping! " + i;
			sender.sendMessage(message);
		}
	}
	
	public static void main(String[] args) {
		NetworkInfo net = new NetworkInfo("ping_sender", "res/ping_names", "res/ping_topo");
		PingSender ps = new PingSender(net, "ping_sender", "ping_replier");
		ps.start();
	}
	
	private class ReplyReceiverServer extends BaseServer {
		public ReplyReceiverServer() {
			super(myName, PingSender.this.network);
		}
		
		protected void processMessage(BaseMessage message) {
			System.err.println("Ping sender's reply receiver processing message:");
			System.err.println(message);
		}
	}
	
}
