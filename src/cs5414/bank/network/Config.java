package cs5414.bank.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Config {
	protected HashMap<String, ArrayList<String>> defaultGroups;
	protected HashMap<String, ArrayList<String>> defaultNodes;
	protected HashMap<String, ArrayList<String>> currentGroups;
	protected HashMap<String, ArrayList<String>> currentNodes;
	
	public Config() {
		defaultGroups = new HashMap<String, ArrayList<String>>();
		defaultNodes = new HashMap<String, ArrayList<String>>();
		currentGroups = new HashMap<String, ArrayList<String>>();
		currentNodes = new HashMap<String, ArrayList<String>>();
	}
	
	public ArrayList<String> get_node_groups(String node) {
		//Grab the list of groups that the node is a part of.
		ArrayList<String> groups = currentNodes.get(node);
		
		return groups;
	}
	
	public ArrayList<String> get_group_nodes(String group) {
		ArrayList<String> nodes = currentGroups.get(group);
		return nodes;
	}
	
	public void rm_node(String node) {
		//Get list of groups that the node belongs to
		ArrayList<String> groups = currentNodes.get(node);
		
		//Loop through each group removing the node
		Iterator<String> iterator = groups.iterator();
		while(iterator.hasNext()) {
			int index = currentGroups.get(iterator.next()).indexOf(iterator.next());
			currentGroups.get(iterator.next()).remove(index);
		}
		currentNodes.remove(node);
	}
	
	public void add_node(String node) {
		if( !currentNodes.containsKey(node) ) {
			if( defaultNodes.containsKey(node)) {
				//Populate the groups list with the 
				ArrayList<String> groups = defaultNodes.get(node);
				currentNodes.get(node).addAll(groups);
				
				Iterator<String> iterator = groups.iterator();
				while(iterator.hasNext()) {
					currentGroups.get(iterator.next()).add(node);
				}
				
			}
		}
		
	}

}
