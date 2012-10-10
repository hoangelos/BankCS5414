package cs5414.bank.test;

import cs5414.bank.message.BankRequestMessage;
import cs5414.bank.message.BankRequestMessage.RequestType;
import cs5414.bank.message.RequestSnapshotMessage;
import cs5414.bank.network.BaseServer;
import cs5414.bank.network.MessageSenderClient;
import cs5414.bank.network.NetworkInfo;

public class TestThreeOneSnapshot extends BaseServer {
	
	MessageSenderClient senderClient;
	
	public TestThreeOneSnapshot(NetworkInfo net) {
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
		deposit.account = "aa.acct";
		deposit.destination = "aa_server";
		senderClient.sendMessage(deposit);
		deposit.amount = 125;
		deposit.account = "bb.acct";
		deposit.destination = "bb_server";
		senderClient.sendMessage(deposit);
		deposit.amount = 150;
		deposit.account = "cc.acct";
		deposit.destination = "cc_server";
		senderClient.sendMessage(deposit);
		deposit.amount = 175;
		deposit.account = "dd.acct";
		deposit.destination = "dd_server";
		senderClient.sendMessage(deposit);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		RequestSnapshotMessage request = new RequestSnapshotMessage();
		request.source = "ee_special";
		request.destination = "aa_server";
		senderClient.sendMessage(request);
		System.err.println("Done sending initial batch!");
	}
	
	public static void main(String[] args) {
		NetworkInfo net = new NetworkInfo("ee_special",
				"res/three_one_names", "res/three_one_topo");
		TestThreeOneSnapshot test = new TestThreeOneSnapshot(net);
		test.start();
	}
	
}
