package cs5414.bank.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.ServerSocket;

import cs5414.bank.message.DebugMessage;
import cs5414.bank.message.Message;

public class Server {
	
	protected class ConnectionThread extends Thread {
		
		private Socket sock;
		
		public ConnectionThread(Socket sock) {
			this.sock = sock;
		}
		
		public void run() {
			System.err.println("Connection thread started.");
			try {
				ObjectInputStream msgInput = new ObjectInputStream(sock.getInputStream());
				ObjectOutputStream msgOutput = new ObjectOutputStream(sock.getOutputStream());
				Message msgRecv = (Message) msgInput.readObject();
				System.err.println("Received message: " + msgRecv);
				Message msgToSend = processMessage(msgRecv);
				System.err.println("Replying: " + msgToSend);
				msgOutput.writeObject(msgToSend);
				sock.close();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
			
		}
	}
	
	protected class ServerThread extends Thread {
		public void run() {
			try {
				ServerSocket servSock = new ServerSocket(port);
				Socket sock;
				System.err.println("Server thread started.");
				while (true) {
					sock = servSock.accept();
					System.err.println("Accepted connection.");
					ConnectionThread connThread = new ConnectionThread(sock);
					connThread.run();
				}
			} catch (IOException e) {
				e.printStackTrace(System.err);
			}
		}
	}
	
	protected String name;
	protected int port;
	
	public Server(String name, int port) {
		this.name = name;
		this.port = port;
	}
	
	protected Message processMessage(Message input) {
		System.err.println("Processing: " + input);
		return new DebugMessage(null, null, null, "Pong!");
	}
	
	public void start() {
		(new ServerThread()).run();
	}
	
	public static void main(String[] args) {
		Server testServ = new Server("test_serv", 10100);
		testServ.start();
	}
	
}
