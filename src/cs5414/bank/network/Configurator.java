package cs5414.bank.network;

import java.util.ArrayList;
import java.util.HashMap;

public class Configurator {
	
	private ArrayList<String> config;
	
	public Configurator() {
		config = new ArrayList<String>();
	}
	
	public ArrayList<String> getConfig() {
		return config;
	}
	
	public String getHead() {
		return config.get(0);
	}
	
	public boolean add(String node) {
		config.add(node);
		return config.contains(node);
	}
	
	public boolean remove(String node) {
		config.remove(node);
		return !config.contains(node);
	}
	
	public void clear(String group) {
		config.clear();
	}

}
