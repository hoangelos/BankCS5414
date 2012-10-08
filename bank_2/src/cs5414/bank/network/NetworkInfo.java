package cs5414.bank.network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;

public class NetworkInfo {
	
	private String thisHostName;
	private HashMap<String, String> namesToHosts;
	private HashMap<String, Integer> namesToPorts;
	private HashMap<String, HashSet<String>> connectedHosts;
	
	public NetworkInfo(String hostName, String namesFile, String topoFile) {
		try {
			thisHostName = hostName;
			namesToHosts = new HashMap<String, String>();
			namesToPorts = new HashMap<String, Integer>();
			connectedHosts = new HashMap<String, HashSet<String>>();
			BufferedReader namesReader = new BufferedReader(new FileReader(namesFile));
			while (namesReader.ready()) {
				String line = namesReader.readLine();
				String[] split = line.split("\\t");
				namesToHosts.put(split[0], split[1]);
				namesToPorts.put(split[0], Integer.parseInt(split[2]));
			}
			namesReader.close();
			BufferedReader topoReader = new BufferedReader(new FileReader(topoFile));
			while (topoReader.ready()) {
				String line = topoReader.readLine();
				String[] split = line.split("\\t");
				if (!connectedHosts.containsKey(split[0])) {
					connectedHosts.put(split[0], new HashSet<String>());
				}
				connectedHosts.get(split[0]).add(split[1]);
			}
			topoReader.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
	public String getThisHost() {
		return thisHostName;
	}
	
	public String getHost(String hostName) {
		return namesToHosts.get(hostName);
	}
	
	public int getPort(String hostName) {
		return namesToPorts.get(hostName);
	}
	
	public boolean connectedDirectly(String hostFrom, String hostTo) {
		HashSet<String> destSet = connectedHosts.get(hostFrom);
		if (destSet == null) {
			return false;
		}
		return destSet.contains(hostTo);
	}
	
}
