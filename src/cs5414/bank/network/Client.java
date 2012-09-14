package cs5414.bank.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import cs5414.bank.message.DebugMessage;
import cs5414.bank.message.Message;

public class Client {
	
	protected String name;
	
	public Client(String name) {
		this.name = name;
	}
	
	public Message sendMessage(String servHost, int servPort, Message outMessage)
			throws IOException, ClassNotFoundException {
		Socket connSocket;
		connSocket = new Socket(servHost, servPort);
		System.err.println("Connected.");
		ObjectOutputStream objOutput = new ObjectOutputStream(connSocket.getOutputStream());
		ObjectInputStream objInput = new ObjectInputStream(connSocket.getInputStream());
		System.err.println("Sending: " + outMessage);
		objOutput.writeObject(outMessage);
		Message replyMessage = (Message) objInput.readObject();
		System.err.println("Received reply: " + replyMessage);
		return replyMessage;
	}
	
	public static void main(String[] args) throws Exception {
		Client testClient = new Client("test_client");
		Message testMessage = new DebugMessage(null, null, null, "Ping!");
		testClient.sendMessage("localhost", 10100, testMessage);
	}
	
}
