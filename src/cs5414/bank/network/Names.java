package cs5414.bank.network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.TreeMap;

public class Names {
	
	protected Map<String, String> names_hosts;
	protected Map<String, Integer> names_ports;
	
	public Names() {
		names_hosts = new TreeMap<String, String>();
		names_ports = new TreeMap<String, Integer>();
	}
	
	public Names(String filename) {
		this();
		try {
			BufferedReader file = new BufferedReader(new FileReader(filename));
			while (file.ready()) {
				String line = file.readLine();
				String[] split = line.split("\\t");
				addName(split[0], split[1], Integer.parseInt(split[2]));
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	
	protected void addName(String name, String host, int port) {
		names_hosts.put(name, host);
		names_ports.put(name, port);
	}
	
	public String resolve_host(String name) {
		return names_hosts.get(name);
	}
	
	public Integer resolve_port(String name) {
		return names_ports.get(name);
	}
	
}
