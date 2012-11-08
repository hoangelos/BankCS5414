package cs5414.bank.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import cs5414.bank.network.Config;

public class Configurator {
	
	private Config config;
	
	public Configurator() {
		config = new Config();
	}
	
	public Config getConfig() {
		return config;
	}
	
	public boolean leads_group(String node, String group) {
		int index = config.get_group_nodes(group).indexOf(node);
		return(0 == index);
	}
	
	public void add(String node) {
		config.add_node(node);
	}
	
	public void remove(String node) {
		config.rm_node(node);
	}
	

}
