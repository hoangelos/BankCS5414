package cs5414.bank.misc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import cs5414.bank.message.BankRequestMessage;
import cs5414.bank.message.BaseMessage;
import cs5414.bank.message.TakeSnapshotMessage;
import cs5414.bank.network.NetworkInfo;

public class LocalSubSnapshot implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String uid;
	public String atServer;
	public HashMap<String, Integer> initialBalances;
	public HashSet<String> waitingToReceiveFrom;
	public ArrayList<BaseMessage> seenMessagesInTransit;
	
	public LocalSubSnapshot(String uid, String servName,
			HashMap<String, Integer> balances, NetworkInfo network) {
		this.uid = uid;
		atServer = servName;
		initialBalances = new HashMap<String, Integer>();
		String prefix = servName.substring(0, 2);
		for (String name: balances.keySet()) {
			int bal = balances.get(name);
			if (bal != 0) {
				initialBalances.put(prefix + "." + name, bal);
			}
		}
		seenMessagesInTransit = new ArrayList<BaseMessage>();
		waitingToReceiveFrom = new HashSet<String>();
		for (String inLink: network.getInboundLinks(servName)) {
			if (inLink.contains("_server")) {
				waitingToReceiveFrom.add(inLink);
			}
		}
	}
	
	public boolean processMessage(BaseMessage message) {
		if (message instanceof BankRequestMessage) {
			seenMessagesInTransit.add(message);
			return false;
		} else if (message instanceof TakeSnapshotMessage) {
			waitingToReceiveFrom.remove(message.source);
			if (waitingToReceiveFrom.isEmpty()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	public String toString() {
		String result = "LocalSubSnapshot [uid " + uid
				+ " atServer " + atServer + "\n";
		result += "initialBalances:\n";
		for (String acc: initialBalances.keySet()) {
			result += acc + ": " + initialBalances.get(acc) + "\n";
		}
		result += "seenMessagesInTransit:\n";
		for (BaseMessage msg: seenMessagesInTransit) {
			result += msg + "\n";
		}
		result += "]";
		return result;
	}

}
