package cs5414.bank.network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Topology {
	
	protected Map<String, Set<String>> connections;
	
	public Topology() {
		connections = new TreeMap<String, Set<String>>();
	}
	
	public Topology(String filename) {
		this();
		try {
			BufferedReader file = new BufferedReader(new FileReader(filename));
			while (file.ready()) {
				String line = file.readLine();
				String[] split = line.split("\\t");
				addConnection(split[0], split[1]);
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
	protected void addConnection(String from, String to) {
		if (!connections.containsKey(from)) {
			connections.put(from, new TreeSet<String>());
		}
		connections.get(from).add(to);
	}
	
	public boolean canSendDirectly(String from, String to) {
		return connections.containsKey(from)
				&& connections.get(from).contains(to);
	}
	
}
