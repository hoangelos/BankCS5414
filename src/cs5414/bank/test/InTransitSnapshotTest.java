package cs5414.bank.test;

import cs5414.bank.message.BankRequestMessage;
import cs5414.bank.message.BankRequestMessage.RequestType;
import cs5414.bank.message.RequestSnapshotMessage;
import cs5414.bank.network.BaseServer;
import cs5414.bank.network.MessageSenderClient;
import cs5414.bank.network.NetworkInfo;

public class InTransitSnapshotTest extends BaseServer {
	
	MessageSenderClient senderClient;
	
	public InTransitSnapshotTest(NetworkInfo net) {
		super("ee_special", net);
		senderClient = new MessageSenderClient("ee_special", net);
	}
	
	public void start() {
		super.start();

		BankRequestMessage transfer1 = new BankRequestMessage();
		transfer1.requestType = RequestType.TRANSFER;
		transfer1.source = servName;
		//assume human operator waits > 100 ms between runs
		transfer1.serial = System.currentTimeMillis() + 1;
		transfer1.amount = 5;
		transfer1.account = "dd.0001";
		transfer1.accountInto = "cc.0002";
		transfer1.destination = "dd_server";
		senderClient.sendMessage(transfer1);

		BankRequestMessage transfer2 = new BankRequestMessage();
		transfer2.requestType = RequestType.TRANSFER;
		transfer2.source = servName;
		transfer2.serial = System.currentTimeMillis() + 2;
		transfer2.amount = 5;
		transfer2.account = "cc.0001";
		transfer2.accountInto = "aa.0001";
		transfer2.destination = "cc_server";
		senderClient.sendMessage(transfer2);
		
		//Take snapshot
		RequestSnapshotMessage request = new RequestSnapshotMessage();
		request.source = "ee_special";
		request.destination = "dd_server";
		senderClient.sendMessage(request);

		BankRequestMessage transfer3 = new BankRequestMessage();
		transfer3.requestType = RequestType.TRANSFER;
		transfer3.source = servName;
		transfer3.serial = System.currentTimeMillis() + 3;
		transfer3.amount = 5;
		transfer3.account = "aa.0001";
		transfer3.accountInto = "bb.0002";
		transfer3.destination = "dd_server";
		senderClient.sendMessage(transfer3);
		
		System.err.println("Done with Snapshot Interweaving test!");
		System.exit(0);
	}
	
	public static void main(String[] args) {
		NetworkInfo net = new NetworkInfo("ee_special",
				"res/three_one_names", "res/three_one_topo");
		InTransitSnapshotTest test = new InTransitSnapshotTest(net);
		test.start();
	}
	
}
