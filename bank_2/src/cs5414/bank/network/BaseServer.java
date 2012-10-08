package cs5414.bank.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import cs5414.bank.message.BaseMessage;

public class BaseServer {
	
	protected Object lock;
	protected NetworkInfo network;
	protected String servName;
	protected int servPort;
	protected LinkedList<BaseMessage> receivedMessages;
	
	public BaseServer(String name, NetworkInfo net) {
		servName = name;
		servPort = net.getPort(name);
		lock = new Object();
		network = net;
		receivedMessages = new LinkedList<BaseMessage>();
	}
	
	public void start() {
		AcceptThread accept = new AcceptThread();
		accept.start();
		ProcessThread process = new ProcessThread();
		process.start();
	}
	
	protected void enqueueMessage(BaseMessage message) {
		synchronized (lock) {
			receivedMessages.push(message);
			lock.notify();
		}
	}
	
	protected void processMessage(BaseMessage message) {
		System.err.println("Base server processing message:");
		System.err.println(message);
	}
	
	protected class AcceptThread extends Thread {
		public void run() {
			ServerSocket servSock;
			try {
				servSock = new ServerSocket(servPort);
				while (true) {
					Socket sock = servSock.accept();
					System.err.println("Server accepted new connection");
					ReceiveThread receive = new ReceiveThread(sock);
					receive.start();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
	}
	
	protected class ReceiveThread extends Thread {
		
		protected Socket sock;
		
		public ReceiveThread(Socket sock) {
			this.sock = sock;
		}
		
		public void run() {
			try {
				ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
				Object obj = ois.readObject();
				System.err.println("Received message:");
				System.err.println(obj);
				if (obj instanceof BaseMessage) {
					BaseMessage messageCast = (BaseMessage) obj;
					enqueueMessage(messageCast);
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			} finally {
				try {
					sock.close();
				} catch (IOException e) {
					//nothing we can do
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	protected class ProcessThread extends Thread {
		public void run() {
			BaseMessage message;
			while (true) {
				message = null;
				synchronized (lock) {
					if (!receivedMessages.isEmpty()) {
						message = receivedMessages.pop();
					}
				}
				if (message != null) {
					System.err.println("Processing message:");
					System.err.println(message);
					processMessage(message);
				}
				synchronized (lock) {
					if (receivedMessages.isEmpty()) {
						try {
							lock.wait();
						} catch (InterruptedException ex) {
							//ignore; loop will retry
						}
					}
				}
			}
		}
	}
	
}
