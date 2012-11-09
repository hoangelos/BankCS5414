package cs5414.bank.network;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import cs5414.bank.network.Config;

public class Configurator {
	
	private Config config;
	
	public Configurator(String namesFile) {
		HashMap<String, ArrayList<String>> defaultGroups = new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> defaultNodes = new HashMap<String, ArrayList<String>>();
		try {
			BufferedReader namesReader = new BufferedReader(new FileReader(namesFile));
			while (namesReader.ready()) {
				String line = namesReader.readLine();
				String[] split = line.split("\\t");
				String group = split[0];
				String[] nodes = split[1].split(",");
				ArrayList<String> nodeList = new ArrayList<String>(Arrays.asList(nodes));
				
				if(defaultNodes.containsKey(group)) {
					defaultNodes.get(group).addAll(nodeList);
				} else {
					defaultNodes.put(group, nodeList);
				}
				
				Iterator<String> iterator = nodeList.iterator();
				while(iterator.hasNext()) {
					String node = iterator.next();
					
					if(defaultGroups.containsKey(node)) {
						defaultGroups.get(node).add(group);
					} else {
						defaultGroups.put(node, new ArrayList<String>());
						defaultGroups.get(node).add(group);
					}
				}
			}
			namesReader.close();
		} catch(Exception e) {
			e.printStackTrace(System.err);
		}
		config = new Config(defaultGroups, defaultNodes);
	}
	
	public Config getConfig() {
		return config;
	}
	
	public boolean i_am_head(String node, String group) {
		int index = config.get_group_nodes(group).indexOf(node);
		return(0 == index);
	}
	
	public boolean i_am_tail(String node, String group) {
		int index = config.get_group_nodes(group).indexOf(node);
		return(config.get_group_nodes(group).size()-1 == index);
	}
	
	public String my_successor(String node, String group) {
		int index = config.get_group_nodes(group).indexOf(node);
		return config.get_group_nodes(group).get(index+1);
	}
	
	public void add(String node) {
		config.add_node(node);
	}
	
	public void remove(String node) {
		config.rm_node(node);
	}
	

}
