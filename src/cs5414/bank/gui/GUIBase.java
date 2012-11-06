package cs5414.bank.gui;

import java.lang.management.ManagementFactory;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cs5414.bank.network.NetworkInfo;

public class GUIBase {

	protected JFrame frame;
	static public JPanel panel;
	static public String[] jvmID;
	static public String name;
	static public NetworkInfo net;
	
	public GUIBase(String my_name, String name_file, String topo_file) {
		name = my_name;
		net = new NetworkInfo(name, name_file, topo_file);
		jvmID = ManagementFactory.getRuntimeMXBean().getName().split("@");
	}

	public long getUID() {
		java.util.Date today = new java.util.Date();
		java.sql.Timestamp ts1 = new java.sql.Timestamp(today.getTime());
		String tsTime1 = String.valueOf(ts1.getTime());
		
		String UID = tsTime1 + jvmID[0];
		return Long.valueOf(UID);
	}
}
