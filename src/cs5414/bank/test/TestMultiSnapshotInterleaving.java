package cs5414.bank.test;

import cs5414.bank.message.BankRequestMessage;
import cs5414.bank.message.BankRequestMessage.RequestType;
import cs5414.bank.message.RequestSnapshotMessage;
import cs5414.bank.network.BaseServer;
import cs5414.bank.network.MessageSenderClient;
import cs5414.bank.network.NetworkInfo;

public class TestMultiSnapshotInterleaving extends BaseServer {
	
	MessageSenderClient sender;
	
	public TestMultiSnapshotInterleaving(NetworkInfo net) {
		super("ee_special", net);
		sender = new MessageSenderClient("ee_special", net);
	}
	
	public void start() {
		super.start();
		BankRequestMessage transfer = new BankRequestMessage();
		RequestSnapshotMessage snapshot = new RequestSnapshotMessage();
		transfer.requestType = RequestType.TRANSFER;
		transfer.source = servName;
		transfer.destination = "aa_server";
		transfer.serial = 1;
		transfer.amount = 100;
		transfer.account = "aa.acct";
		transfer.accountInto = "bb.acct";
		sender.sendMessage(transfer);
		snapshot.source = servName;
		snapshot.destination = "aa_server";
		sender.sendMessage(snapshot);
		transfer.destination = "cc_server";
		transfer.serial++;
		transfer.account = "cc.acct";
		transfer.accountInto = "dd.acct";
		sender.sendMessage(transfer);
		snapshot.destination = "dd_server";
		sender.sendMessage(snapshot);
		System.err.println("Done sending!");
	}
	
	public static void main(String[] args) {
		NetworkInfo net = new NetworkInfo("ee_special",
				"res/three_one_names", "res/three_one_topo");
		TestMultiSnapshotInterleaving test =
				new TestMultiSnapshotInterleaving(net);
		test.start();
	}
	
}
