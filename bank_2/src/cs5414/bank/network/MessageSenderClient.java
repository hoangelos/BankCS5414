package cs5414.bank.network;

import java.io.ObjectOutputStream;
import java.net.Socket;

import cs5414.bank.message.BaseMessage;

public class MessageSenderClient {
	
	private String myName;
	private NetworkInfo network;
	
	public MessageSenderClient(String name, NetworkInfo net) {
		myName = name;
		network = net;
	}
	
	public void sendMessage(BaseMessage message) {
		try {
			if (network.connectedDirectly(myName, message.destination)) {
				Socket sock = new Socket(
						network.getHost(message.destination),
						network.getPort(message.destination));
				ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
				oos.writeObject(message);
				sock.close();
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
}
