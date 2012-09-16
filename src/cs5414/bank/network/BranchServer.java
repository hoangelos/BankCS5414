package cs5414.bank.network;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import cs5414.bank.message.*;

public class BranchServer extends Server {
	
	private Set<String> usedMessageIds;
	private Map<String, Integer> accountAmounts;
	
	protected synchronized Message processMessage(Message input) {
		System.err.println("Received message " + input);
		if (input instanceof QueryMessage) {
			QueryMessage inputCast = (QueryMessage) input;
			String combined_id = inputCast.getAccount()
					+ "#" + inputCast.getMessageId();
			usedMessageIds.add(combined_id);
			Integer bal = accountAmounts.get(inputCast.getAccount());
			return new ResponseMessage(inputCast.getDestination(), 
					inputCast.getSource(), null,
					inputCast.getMessageId(), bal);
		} else if (input instanceof DepositMessage) {
			DepositMessage inputCast = (DepositMessage) input;
			String combined_id = inputCast.getAccount()
					+ "#" + inputCast.getMessageId();
			if (usedMessageIds.contains(combined_id)) {
				return new ResponseMessage(inputCast.getDestination(),
						inputCast.getSource(), null,
						inputCast.getMessageId(), null);
			} else {
				usedMessageIds.add(combined_id);
				String acc = inputCast.getAccount();
				if (!accountAmounts.containsKey(acc)) {
					accountAmounts.put(acc, 0);
				}
				accountAmounts.put(acc,
						accountAmounts.get(acc) + inputCast.getAmount());
				return new ResponseMessage(inputCast.getDestination(),
						inputCast.getSource(), null,
						inputCast.getMessageId(),
						accountAmounts.get(acc));
			}
		} else if (input instanceof WithdrawMessage) {
			DepositMessage inputCast = (DepositMessage) input;
			String combined_id = inputCast.getAccount()
					+ "#" + inputCast.getMessageId();
			if (usedMessageIds.contains(combined_id)) {
				return new ResponseMessage(inputCast.getDestination(),
						inputCast.getSource(), null,
						inputCast.getMessageId(), null);
			} else {
				usedMessageIds.add(combined_id);
				String acc = inputCast.getAccount();
				if (!accountAmounts.containsKey(acc)) {
					accountAmounts.put(acc, 0);
				}
				accountAmounts.put(acc,
						accountAmounts.get(acc) - inputCast.getAmount());
				return new ResponseMessage(inputCast.getDestination(),
						inputCast.getSource(), null,
						inputCast.getMessageId(),
						accountAmounts.get(acc));
			}
		} else if (input instanceof TransferMessage) {
			throw new RuntimeException();
		} else if (input instanceof DebugMessage) {
			return new DebugMessage(null, null, null, "Pong!");
		}
		return null;
	}
	
	public BranchServer(String name, int port) {
		super(name, port);
		usedMessageIds = new TreeSet<String>();
		accountAmounts = new TreeMap<String, Integer>();
	}

}
