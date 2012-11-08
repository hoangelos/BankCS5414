package cs5414.bank.misc;

import java.io.Serializable;
import java.util.HashMap;

public class VectorClock implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, Long> subClocks;
	
	public VectorClock() {
		subClocks = new HashMap<String, Long>();
	}
	
	public long getClockForName(String name) {
		if (subClocks.containsKey(name)) {
			return subClocks.get(name);
		} else {
			return 0;
		}
	}
	
	public long incrementClockForName(String name) {
		long currentSubClockVal = 0;
		if (subClocks.containsKey(name)) {
			currentSubClockVal = subClocks.get(name);
		}
		++currentSubClockVal;
		subClocks.put(name, currentSubClockVal);
		return currentSubClockVal;
	}
	
	public long setClockForNameToAtLeast(String name, long newVal) {
		long currentSubClockVal = 0;
		if (subClocks.containsKey(name)) {
			currentSubClockVal = subClocks.get(name);
		}
		if (currentSubClockVal < newVal) {
			currentSubClockVal = newVal;
		}
		subClocks.put(name, currentSubClockVal);
		return currentSubClockVal;
	}
	
}
