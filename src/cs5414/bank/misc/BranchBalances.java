package cs5414.bank.misc;

import java.io.Serializable;
import java.util.HashMap;

public class BranchBalances implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, Integer> balances;
	private VectorClock maxUsedSerials;
	
	public BranchBalances() {
		balances = new HashMap<String, Integer>();
		maxUsedSerials = new VectorClock();
	}
	
	public int query(String accountSuffix, long serial) {
		maxUsedSerials.setClockForNameToAtLeast(accountSuffix, serial);
		if (balances.containsKey(accountSuffix)) {
			return balances.get(accountSuffix);
		}
		return 0;
	}
	
	public int deposit(String accountSuffix, int amount, long serial) {
		long maxUsedSerial = maxUsedSerials.getClockForName(accountSuffix);
		if (serial > maxUsedSerial) {
			int balance = 0;
			if (balances.containsKey(accountSuffix)) {
				balance = balances.get(accountSuffix);
			}
			balance += amount;
			balances.put(accountSuffix, balance);
		}
		return query(accountSuffix, serial);
	}
	
	public int withdraw(String accountSuffix, int amount, long serial) {
		return deposit(accountSuffix, -1 * amount, serial);
	}
	
}
