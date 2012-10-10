package cs5414.bank.test;

import cs5414.bank.message.BaseMessage;
import cs5414.bank.message.StringMessage;
import cs5414.bank.network.BaseServer;
import cs5414.bank.network.MessageSenderClient;
import cs5414.bank.network.NetworkInfo;

public class PingEchoServer extends BaseServer {
	
	private MessageSenderClient sender;
	
	public PingEchoServer(String name, NetworkInfo net) {
		super(name, net);
		sender = new MessageSenderClient(name, net);
	}
	
	protected void processMessage(BaseMessage message) {
		System.err.println("Echo server processing message:");
		System.err.println(message);
		if (message instanceof StringMessage) {
			StringMessage messageCast = (StringMessage) message;
			StringMessage reply = new StringMessage();
			reply.autoNumber();
			reply.inReplyToNum = messageCast.msgNumForReplies;
			reply.source = servName;
			reply.destination = message.source;
			reply.messageBody = "Pong! in reply to: " + messageCast.messageBody;
			sender.sendMessage(reply);
		}
	}
	
	public static void main(String[] args) {
		NetworkInfo net = new NetworkInfo("ping_replier", "res/ping_names", "res/ping_topo");
		PingEchoServer pes = new PingEchoServer("ping_replier", net);
		pes.start();
	}
	
}
