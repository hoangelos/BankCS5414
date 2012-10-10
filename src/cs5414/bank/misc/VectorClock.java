package cs5414.bank.misc;

import java.util.HashMap;

public class VectorClock {
	
	private HashMap<String, Integer> subClocks;
	
	public VectorClock() {
		subClocks = new HashMap<String, Integer>();
	}
	
	public int getClockForName(String name) {
		if (subClocks.containsKey(name)) {
			return subClocks.get(name);
		} else {
			return 0;
		}
	}
	
	public int incrementClockForName(String name) {
		int currentSubClockVal = 0;
		if (subClocks.containsKey(name)) {
			currentSubClockVal = subClocks.get(name);
		}
		++currentSubClockVal;
		subClocks.put(name, currentSubClockVal);
		return currentSubClockVal;
	}
	
	public int setClockForNameToAtLeast(String name, int newVal) {
		int currentSubClockVal = 0;
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
