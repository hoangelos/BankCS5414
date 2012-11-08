package cs5414.bank.network;

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
	private boolean serverRunning;
	private HashMap<String, BranchBalances> branchBalances;
	
	public BranchServer(String name, NetworkInfo net, String replica_branch) {
		super(name, net);
		senderClient = new MessageSenderClient(name, net);
		oracle = new FailureDetector(replica_branch);
		serverRunning = true;
		branchBalances = new HashMap<String, BranchBalances>();
	}
	
	private void initBranchStates() {
		
	}
	
	private void clearBranchStates() {
		branchBalances.clear();
	}
	
	private void setBranchState(String branchName, BranchBalances newBalances) {
		branchBalances.put(branchName, newBalances);
	}
	
	private boolean isMemberForBranch(String prefix) {
		return true;
	}
	
	private boolean isHeadForBranch(String prefix) {
		return true;
	}
	
	private boolean isTailForBranch(String prefix) {
		return true;
	}
	
	private boolean messageIsExternal(BankRequestMessage message) {
		return true;
	}
	
	protected void processMessage(BaseMessage message) {
		if (message instanceof BankRequestMessage) {
			
			if (!serverRunning) {
				return;
			}
			
			BankRequestMessage brMessage = (BankRequestMessage) message;
			if (messageIsExternal(brMessage)) {
				
				
				
			} else {
				
				
				
			}
			
		} else if (message instanceof FailureDetectorMessage) {
			
			FailureDetectorMessage fdMessage =
					(FailureDetectorMessage) message;
			oracle.handleTransition(fdMessage);
			
		} else if (message instanceof RecoveryStateMessage) {
			
			
			
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
