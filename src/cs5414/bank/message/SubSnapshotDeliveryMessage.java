package cs5414.bank.message;

import cs5414.bank.misc.LocalSubSnapshot;

public class SubSnapshotDeliveryMessage extends BaseMessage {

	private static final long serialVersionUID = 1L;
	
	public LocalSubSnapshot snapshot;

}
