package cs5414.bank.network;

import java.util.ArrayList;
import java.util.HashMap;

import cs5414.bank.message.BankReplyMessage;
import cs5414.bank.message.BankRequestMessage;
import cs5414.bank.message.BankRequestMessage.RequestType;
import cs5414.bank.message.BaseMessage;
import cs5414.bank.message.FailureDetectorMessage;
import cs5414.bank.message.RecoveryStateMessage;
import cs5414.bank.misc.BranchBalances;
import cs5414.bank.misc.VectorClock;

public class BranchServer extends BaseServer {
	
	private MessageSenderClient senderClient;
	private FailureDetector oracle;
	private HashMap<String, BranchBalances> branchBalances;
	
	public BranchServer(String name, NetworkInfo net, String replica_branch) {
		super(name, net);
		senderClient = new MessageSenderClient(name, net);
		oracle = new FailureDetector(replica_branch);
		branchBalances = new HashMap<String, BranchBalances>();
		initBranchStates();
	}
	
	private void initBranchStates() {
		clearBranchStates();
		ArrayList<String> branchNames = oracle.getConfigurator().getConfig().get_node_groups(servName);
		for (String branchName: branchNames) {
			branchBalances.put(branchName, new BranchBalances());
		}
	}
	
	private void clearBranchStates() {
		branchBalances.clear();
	}
	
	private void setBranchState(String branchName, BranchBalances newBalances) {
		branchBalances.put(branchName, newBalances);
	}
	
	private boolean isMemberForBranch(String prefix) {
		return oracle.getConfigurator().getConfig().get_group_nodes(prefix).contains(prefix);
	}
	
	private boolean isHeadForBranch(String prefix) {
		return oracle.getConfigurator().i_am_head(servName, prefix);
	}
	
	private boolean isTailForBranch(String prefix) {
		return oracle.getConfigurator().i_am_tail(servName, prefix);
	}
	
	private String getSuccessorForBranch(String prefix) {
		return oracle.getConfigurator().my_successor(servName, prefix);
	}
	
	private boolean messageIsExternal(BankRequestMessage message) {
		return !(message.source.contains("server"));
	}
	
	private void passMessageToSuccessor(BankRequestMessage message) {
		String branchName = message.destination.substring(0, 2);
		String successorName = getSuccessorForBranch(branchName);
		BankRequestMessage passMessage = new BankRequestMessage();
		passMessage.source = servName;
		passMessage.destination = successorName;
		passMessage.account = message.account;
		passMessage.accountInto = message.accountInto;
		passMessage.amount = message.amount;
		passMessage.msgNumForReplies = message.msgNumForReplies;
		if (message.originator == null) {
			passMessage.originator = message.source;
		} else {
			passMessage.originator = message.originator;
		}
		passMessage.serial = message.serial;
		senderClient.sendMessage(passMessage);
	}
	
	private void sendReplyToOriginator(BankRequestMessage message, int resultBalance) {
		BankReplyMessage reply = new BankReplyMessage();
		reply.source = servName;
		reply.destination = message.originator;
		reply.account = message.account;
		reply.balance = resultBalance;
		reply.inReplyToNum = message.msgNumForReplies;
		senderClient.sendMessage(reply);
	}
	
	private int handleBankRequestLocally(BankRequestMessage message) {
		String requestedBranch = message.account.substring(0, 2);
		BranchBalances requestedBranchBalances = branchBalances.get(requestedBranch);
		switch (message.requestType) {
		case QUERY:
			return requestedBranchBalances.query(
					message.account.substring(3),
					message.serial);
		case DEPOSIT:
			return requestedBranchBalances.deposit(
					message.account.substring(3),
					message.amount,
					message.serial);
		case TRANSFER:
		case WITHDRAW:
			return requestedBranchBalances.withdraw(
					message.account.substring(3),
					message.amount,
					message.serial);
		default:
			return 0;
		}
	}
	
	protected void processMessage(BaseMessage message) {
		if (message instanceof BankRequestMessage) {
			
			BankRequestMessage brMessage = (BankRequestMessage) message;
			String requestedBranch = brMessage.account.substring(0, 2);
			
			if (!isMemberForBranch(requestedBranch)) {
				return;
			}
			
			if (!messageIsExternal(brMessage)) {
				int resultBalance = handleBankRequestLocally(brMessage);
				if (isTailForBranch(requestedBranch)) {
					sendReplyToOriginator(brMessage, resultBalance);
				}
			} else if (brMessage.requestType == RequestType.QUERY
					&& isTailForBranch(requestedBranch)) {
				int resultBalance = handleBankRequestLocally(brMessage);
				sendReplyToOriginator(brMessage, resultBalance);
			} else if (isHeadForBranch(requestedBranch)) {
				handleBankRequestLocally(brMessage);
				passMessageToSuccessor(brMessage);
			}
			
		} else if (message instanceof FailureDetectorMessage) {
			
			FailureDetectorMessage fdMessage =
					(FailureDetectorMessage) message;
			oracle.handleTransition(fdMessage);
			
		} else if (message instanceof RecoveryStateMessage) {
			
			RecoveryStateMessage rsMessage = (RecoveryStateMessage) message;
			setBranchState(rsMessage.branchName, rsMessage.branchBalances);
			
			// trigger local handlers if applicable
			
		}
	}
	
	public static void main(String[] args) {
		String myName = args[0];
		String namesFile = args[1];
		String topoFile = args[2];
		String replica_branch = args[3];
		NetworkInfo net = new NetworkInfo(myName, namesFile, topoFile);
		BranchServer branch = new BranchServer(myName, net, replica_branch);
		branch.start();
	}
	
}
