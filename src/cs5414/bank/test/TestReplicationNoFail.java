package cs5414.bank.test;

import java.util.ArrayList;

import cs5414.bank.message.BankRequestMessage;
import cs5414.bank.message.BankRequestMessage.RequestType;
import cs5414.bank.network.BaseServer;
import cs5414.bank.network.MessageSenderClient;
import cs5414.bank.network.NetworkInfo;

public class TestReplicationNoFail extends BaseServer {
	
	MessageSenderClient senderClient;
	
	public TestReplicationNoFail(NetworkInfo net) {
		super("test_sender", net);
		senderClient = new MessageSenderClient("test_sender", net);
	}
	
	public void start() {
		super.start();
		ArrayList<String> replicas = new ArrayList<String>();
		replicas.add("aa_server");
		replicas.add("bb_server");
		replicas.add("cc_server");
		BankRequestMessage deposit = new BankRequestMessage();
		deposit.requestType = RequestType.DEPOSIT;
		deposit.source = "test_sender";
		deposit.account = "aa.test";
		deposit.amount = 100;
		deposit.msgNumForReplies = 100;
		deposit.serial = 100;
		for (String replica: replicas) {
			deposit.destination = replica;
			senderClient.sendMessage(deposit);
		}
	}
	
	public static void main(String[] args) {
		NetworkInfo net = new NetworkInfo("test_sender", "res/chain_repl_names", "res/chain_repl_topo");
		TestReplicationNoFail test = new TestReplicationNoFail(net);
		test.start();
	}
	
}
