package cs5414.bank.test;

import java.util.ArrayList;

import cs5414.bank.message.BankRequestMessage;
import cs5414.bank.message.FailureDetectorMessage;
import cs5414.bank.network.BaseServer;
import cs5414.bank.network.MessageSenderClient;
import cs5414.bank.network.NetworkInfo;

public class TestReplicationWithFail extends BaseServer {
	
	MessageSenderClient senderClient;
	
	public TestReplicationWithFail(NetworkInfo net) {
		super("test_sender", net);
		senderClient = new MessageSenderClient("test_sender", net);
	}
	
	public void start() {
		try {
			super.start();
			ArrayList<String> replicas = new ArrayList<String>();
			replicas.add("aa_server");
			replicas.add("bb_server");
			replicas.add("cc_server");
			BankRequestMessage deposit = new BankRequestMessage();
			deposit.requestType = BankRequestMessage.RequestType.DEPOSIT;
			deposit.source = "test_sender";
			deposit.account = "aa.test";
			deposit.amount = 100;
			deposit.msgNumForReplies = 100;
			deposit.serial = 100;
			for (String replica: replicas) {
				deposit.destination = replica;
				senderClient.sendMessage(deposit);
			}
			Thread.sleep(1000);
			FailureDetectorMessage fail = new FailureDetectorMessage();
			fail.source = "test_sender";
			fail.node = "aa_server";
			fail.requestType = FailureDetectorMessage.RequestType.FAILED;
			for (String replica: replicas) {
				deposit.destination = replica;
				senderClient.sendMessage(deposit);
			}
			System.err.println("Please manually restart aa_server.");
			System.in.read();
			Thread.sleep(1000);
			FailureDetectorMessage recover = new FailureDetectorMessage();
			recover.source = "test_sender";
			recover.node = "aa_server";
			recover.requestType = FailureDetectorMessage.RequestType.RECOVERED;
			for (String replica: replicas) {
				deposit.destination = replica;
				senderClient.sendMessage(deposit);
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
	public static void main(String[] args) {
		NetworkInfo net = new NetworkInfo("test_sender", "res/chain_repl_names", "res/chain_repl_topo");
		TestReplicationNoFail test = new TestReplicationNoFail(net);
		test.start();
	}
	
}
