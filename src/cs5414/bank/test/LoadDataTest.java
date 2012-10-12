package cs5414.bank.test;

import cs5414.bank.message.BankRequestMessage;
import cs5414.bank.message.BankRequestMessage.RequestType;
import cs5414.bank.message.RequestSnapshotMessage;
import cs5414.bank.network.BaseServer;
import cs5414.bank.network.MessageSenderClient;
import cs5414.bank.network.NetworkInfo;

public class LoadDataTest extends BaseServer {
	
	MessageSenderClient senderClient;
	
	public LoadDataTest(NetworkInfo net) {
		super("ee_special", net);
		senderClient = new MessageSenderClient("ee_special", net);
	}
	
	public void start() {
		super.start();
		BankRequestMessage deposit = new BankRequestMessage();
		deposit.requestType = RequestType.DEPOSIT;
		deposit.source = servName;
		deposit.serial = 1;
		deposit.amount = 100;
		deposit.account = "aa.0001";
		deposit.destination = "aa_server";
		senderClient.sendMessage(deposit);
		deposit.serial = 2;
		deposit.amount = 75;
		deposit.account = "aa.0002";
		deposit.destination = "aa_server";
		senderClient.sendMessage(deposit);
		deposit.serial = 3;
		deposit.amount = 50;
		deposit.account = "aa.0003";
		deposit.destination = "aa_server";
		senderClient.sendMessage(deposit);
		deposit.serial = 4;
		deposit.amount = 125;
		deposit.account = "bb.0001";
		deposit.destination = "bb_server";
		senderClient.sendMessage(deposit);
		deposit.serial = 5;
		deposit.amount = 130;
		deposit.account = "bb.0002";
		deposit.destination = "bb_server";
		senderClient.sendMessage(deposit);
		deposit.serial = 6;
		deposit.amount = 135;
		deposit.account = "bb.0003";
		deposit.destination = "bb_server";
		senderClient.sendMessage(deposit);
		deposit.serial = 7;
		deposit.amount = 150;
		deposit.account = "cc.0001";
		deposit.destination = "cc_server";
		senderClient.sendMessage(deposit);
		deposit.serial = 8;
		deposit.amount = 155;
		deposit.account = "cc.0002";
		deposit.destination = "cc_server";
		senderClient.sendMessage(deposit);
		deposit.serial = 9;
		deposit.amount = 175;
		deposit.account = "dd.0001";
		deposit.destination = "dd_server";
		senderClient.sendMessage(deposit);
		deposit.serial = 10;
		deposit.amount = 185;
		deposit.account = "dd.0002";
		deposit.destination = "dd_server";
		senderClient.sendMessage(deposit);
		deposit.serial = 11;
		deposit.amount = 195;
		deposit.account = "dd.0003";
		deposit.destination = "dd_server";
		senderClient.sendMessage(deposit);
		/*
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		RequestSnapshotMessage request = new RequestSnapshotMessage();
		request.source = "ee_special";
		request.destination = "aa_server";
		senderClient.sendMessage(request);
		*/
		System.err.println("Done sending initial batch!");
		System.exit(0);
	}
	
	public static void main(String[] args) {
		NetworkInfo net = new NetworkInfo("ee_special",
				"res/three_one_names", "res/three_one_topo");
		LoadDataTest test = new LoadDataTest(net);
		test.start();
	}
	
}
