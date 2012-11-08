package cs5414.bank.message;

import cs5414.bank.misc.BranchBalances;

public class RecoveryStateMessage extends BaseMessage {
	
	private static final long serialVersionUID = 1L;
	
	public String branchName;
	public BranchBalances branchBalances;

}
