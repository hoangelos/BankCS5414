package cs5414.bank.test;

import cs5414.bank.message.BankRequestMessage;
import cs5414.bank.message.BankRequestMessage.RequestType;
import cs5414.bank.message.BaseMessage;
import cs5414.bank.network.BaseServer;
import cs5414.bank.network.MessageSenderClient;
import cs5414.bank.network.NetworkInfo;

public class TestSingleBranchServer extends Thread {
	
	public void run() {
		System.err.println("Test started.");
		NetworkInfo net = new NetworkInfo("aa_gui",
				"res/single_branch_names", "res/single_branch_topo");
		ReplyReceiverServer serv = new ReplyReceiverServer("aa_gui", net);
		serv.start();
		System.err.println("Listener started.");
		MessageSenderClient sender = new MessageSenderClient("aa_gui", net);
		BankRequestMessage deposit = new BankRequestMessage();
		deposit.autoNumber();
		deposit.serial = deposit.msgNumForReplies;
		deposit.source = "aa_gui";
		deposit.destination = "aa_server";
		deposit.requestType = RequestType.DEPOSIT;
		deposit.account = "aa.acc";
		deposit.amount = 100;
		BankRequestMessage withdraw = new BankRequestMessage();
		withdraw.autoNumber();
		withdraw.serial = withdraw.msgNumForReplies;
		withdraw.source = "aa_gui";
		withdraw.destination = "aa_server";
		withdraw.requestType = RequestType.WITHDRAW;
		withdraw.account = "aa.acc";
		withdraw.amount = 25;
		BankRequestMessage transfer = new BankRequestMessage();
		transfer.autoNumber();
		transfer.serial = transfer.msgNumForReplies;
		transfer.source = "aa_gui";
		transfer.destination = "aa_server";
		transfer.requestType = RequestType.TRANSFER;
		transfer.account = "aa.acc";
		transfer.accountInto = "aa.acc2";
		transfer.amount = 25;
		BankRequestMessage query = new BankRequestMessage();
		query.autoNumber();
		query.serial = query.msgNumForReplies;
		query.source = "aa_gui";
		query.destination = "aa_server";
		query.requestType = RequestType.QUERY;
		query.account = "aa.acc2";
		try {
			sender.sendMessage(deposit);
			Thread.sleep(1000);
			sender.sendMessage(withdraw);
			sender.sendMessage(withdraw);
			sender.sendMessage(withdraw);
			Thread.sleep(1000);
			sender.sendMessage(transfer);
			Thread.sleep(1000);
			sender.sendMessage(query);
			Thread.sleep(1000);
			System.err.println("Messages sent.");
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
	public static void main(String[] args) {
		TestSingleBranchServer test = new TestSingleBranchServer();
		test.start();
	}
	
	private class ReplyReceiverServer extends BaseServer {
		
		private int counter;
		
		public ReplyReceiverServer(String name, NetworkInfo net) {
			super(name, net);
			counter = 0;
		}
		
		protected void processMessage(BaseMessage message) {
			System.err.println("Message received #" + counter + ": " + message);
			++counter;
		}
		
	}
	
}
