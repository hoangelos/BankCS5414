package cs5414.bank.network;

import java.util.HashMap;

import cs5414.bank.message.BankReplyMessage;
import cs5414.bank.message.BankRequestMessage;
import cs5414.bank.message.BankRequestMessage.RequestType;
import cs5414.bank.message.BaseMessage;
import cs5414.bank.message.FailureDetectorMessage;
import cs5414.bank.misc.VectorClock;

public class BranchServer extends BaseServer {
	
	private MessageSenderClient senderClient;
	private String branchPrefix;
	private HashMap<String, Integer> balances;
	private VectorClock maxUsedSerials;
	private FailureDetector oracle;
	
	public BranchServer(String name, NetworkInfo net) {
		super(name, net);
		senderClient = new MessageSenderClient(name, net);
		branchPrefix = name.substring(0, 2);
		balances = new HashMap<String, Integer>();
		maxUsedSerials = new VectorClock();
		oracle = new FailureDetector();
	}
	
	protected void processMessage(BaseMessage message) {
		String accountPrefix, accountSuffix;
		int prevBalance, newBalance;
		if (message instanceof BankRequestMessage) {
			System.err.println("Processing bank request:");
			System.err.println(message);
			BankRequestMessage requestMessage =
					(BankRequestMessage) message;
			accountPrefix = requestMessage.account.substring(0, 2);
			if (!accountPrefix.equals(branchPrefix)) {
				System.err.println("Mismatched account prefix!");
				return;
			}
			accountSuffix = requestMessage.account.substring(3);
			boolean requestSendReply = false;
			if (maxUsedSerials.getClockForName(accountSuffix)
					< requestMessage.serial) {
				maxUsedSerials.setClockForNameToAtLeast(
						accountSuffix, requestMessage.serial);
				switch (requestMessage.requestType) {
				case QUERY:
					requestSendReply = true;
					break;
				case DEPOSIT:
					prevBalance = 0;
					if (balances.containsKey(accountSuffix)) {
						prevBalance = balances.get(accountSuffix);
					}
					prevBalance += requestMessage.amount;
					balances.put(accountSuffix, prevBalance);
					requestSendReply = true;
					break;
				case WITHDRAW:
					prevBalance = 0;
					if (balances.containsKey(accountSuffix)) {
						prevBalance = balances.get(accountSuffix);
					}
					prevBalance -= requestMessage.amount;
					balances.put(accountSuffix, prevBalance);
					requestSendReply = true;
					break;
				case TRANSFER:
					String intoAccountPrefix = requestMessage.accountInto.substring(0, 2);
					BankRequestMessage pairedDeposit;
					if (intoAccountPrefix.equals(branchPrefix)) {
						prevBalance = 0;
						if (balances.containsKey(accountSuffix)) {
							prevBalance = balances.get(accountSuffix);
						}
						prevBalance -= requestMessage.amount;
						balances.put(accountSuffix, prevBalance);
						pairedDeposit = new BankRequestMessage();
						pairedDeposit.autoNumber();
						pairedDeposit.requestType = RequestType.DEPOSIT;
						pairedDeposit.source = servName;
						pairedDeposit.destination = servName;
						pairedDeposit.serial = requestMessage.serial;
						pairedDeposit.account = requestMessage.accountInto;
						pairedDeposit.amount = requestMessage.amount;
						enqueueMessage(pairedDeposit);
						requestSendReply = true;
					} else if (network.connectedDirectly(servName,
							intoAccountPrefix + "_server")) {
						prevBalance = 0;
						if (balances.containsKey(accountSuffix)) {
							prevBalance = balances.get(accountSuffix);
						}
						prevBalance -= requestMessage.amount;
						balances.put(accountSuffix, prevBalance);
						pairedDeposit = new BankRequestMessage();
						pairedDeposit.autoNumber();
						pairedDeposit.requestType = RequestType.DEPOSIT;
						pairedDeposit.source = servName;
						pairedDeposit.destination = intoAccountPrefix + "_server";
						pairedDeposit.serial = requestMessage.serial;
						pairedDeposit.account = requestMessage.accountInto;
						pairedDeposit.amount = requestMessage.amount;
						senderClient.sendMessage(pairedDeposit);
						requestSendReply = true;
					}
					break;
				default:
					break;
				}	
			}
			if (requestSendReply && !requestMessage.source.equals(servName)) {
				newBalance = 0;
				if (balances.containsKey(accountSuffix)) {
					newBalance = balances.get(accountSuffix);
				}
				BankReplyMessage replyToSend = new BankReplyMessage();
				replyToSend.destination = requestMessage.source;
				replyToSend.source = requestMessage.destination;
				replyToSend.inReplyToNum = requestMessage.msgNumForReplies;
				replyToSend.account = requestMessage.account;
				replyToSend.balance = newBalance;
				senderClient.sendMessage(replyToSend);
				System.err.println("Reply sent!");
			}

		} else if(message instanceof FailureDetectorMessage) {

			FailureDetectorMessage fdMessage =
					(FailureDetectorMessage) message;
			oracle.handleTransition(fdMessage);
		}
	}
	
	public static void main(String[] args) {
		String myName = args[0];
		String namesFile = args[1];
		String topoFile = args[2];
		NetworkInfo net = new NetworkInfo(myName, namesFile, topoFile);
		BranchServer branch = new BranchServer(myName, net);
		branch.start();
	}
	
}
