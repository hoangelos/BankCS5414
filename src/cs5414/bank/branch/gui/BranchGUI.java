package cs5414.bank.branch.gui;

import java.awt.*;
import java.awt.event.*;
import java.lang.management.ManagementFactory;

import javax.swing.*;

import cs5414.bank.branch.gui.Constants;
import cs5414.bank.branch.gui.cards.*;
import cs5414.bank.message.BankReplyMessage;
import cs5414.bank.message.BaseMessage;
import cs5414.bank.message.SubSnapshotDeliveryMessage;
import cs5414.bank.network.BaseServer;
import cs5414.bank.network.NetworkInfo;

public class BranchGUI implements ActionListener {
	private JFrame frame;
	static public JPanel panel;
	static public ResultsCard resultsCard;
	static public DisplaySnapshotCard displaySnapshotCard;
	static private MenuCard menuCard;
	static public String name;
	static public String branch_name;
	static public NetworkInfo net;
	static public String[] jvmID;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		name = args[0];
		branch_name = args[1];
		String nameFile = args[2];
		String topoFile = args[3];
		net = new NetworkInfo(name, nameFile, topoFile);
		System.err.println("BranchGUI " + name + " started");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BranchGUI window = new BranchGUI(name, branch_name);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BranchGUI(String my_name, String my_branch_name) {
		name = my_name;
		branch_name = my_branch_name;
		jvmID = ManagementFactory.getRuntimeMXBean().getName().split("@");
		ReplyReceiverServer serv = new ReplyReceiverServer(my_name, net);
		serv.start();
		initialize();
	}

	public long getUID() {
		java.util.Date today = new java.util.Date();
		java.sql.Timestamp ts1 = new java.sql.Timestamp(today.getTime());
		String tsTime1 = String.valueOf(ts1.getTime());
		
		String UID = jvmID[0] + tsTime1;
		return Long.valueOf(UID);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(UIManager.getColor("Desktop.background"));
		frame.getContentPane().setLayout(null);
		
		//Setup main GUI settings, title, etc
		JLabel lblCsBank = new JLabel("CS5414 Bank - "+name+" Terminal");
		lblCsBank.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblCsBank.setBounds(6, 6, 373, 33);
		frame.getContentPane().add(lblCsBank);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Setup Panel Cards are enclosed in
		panel = new JPanel();
		panel.setLayout(new CardLayout(0, 0));
		panel.setBounds(135, 51, 309, 221);
		
		//Setup Home Menu screen
		menuCard = new MenuCard(this);
		panel.add(menuCard, Constants.MENU_PANEL);
		
		//Setup Withdrawn screen
		WithdrawlCard withdrawCard = new WithdrawlCard(this);
		panel.add(withdrawCard, Constants.WITHDRAW_PANEL);
		
		//Setup Deposit screen
		DepositCard depositCard = new DepositCard(this);
		panel.add(depositCard, Constants.DEPOSIT_PANEL);
		
		//Setup Query screen
		QueryCard queryCard = new QueryCard(this);
		panel.add(queryCard, Constants.QUERY_PANEL);
		
		//Setup Transfer screen
		TransferCard transferCard = new TransferCard(this);
		panel.add(transferCard, Constants.TRANSFER_PANEL);
		
		TakeSnapshotCard takeSnapshotCard = new TakeSnapshotCard(this);
		panel.add(takeSnapshotCard, Constants.TAKE_SNAPSHOT_PANEL);
		
		//Setup Results scren
		resultsCard = new ResultsCard(this);
		panel.add(resultsCard, Constants.RESULTS_PANEL);
		
		displaySnapshotCard = new DisplaySnapshotCard(this);
		panel.add(displaySnapshotCard, Constants.SHOW_SNAPSHOT_PANEL);
		
		//Add panel to frame
		frame.getContentPane().add(panel);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if ( (Constants.MENU_PANEL == e.getActionCommand()) ||
			 (Constants.WITHDRAW_PANEL == e.getActionCommand()) ||
			 (Constants.DEPOSIT_PANEL == e.getActionCommand()) ||
			 (Constants.QUERY_PANEL == e.getActionCommand()) ||
			 (Constants.TRANSFER_PANEL == e.getActionCommand())) {
			menuCard.actionPerformed(e);
		} 
	}
	
	private class ReplyReceiverServer extends BaseServer {
		
		private int counter;
		
		public ReplyReceiverServer(String name, NetworkInfo net) {
			super(name, net);
			counter = 0;
		}
		
		protected void processMessage(BaseMessage message) {
			System.err.println("Message received #" + counter + ": " + message);
			++counter;
			
			if(message instanceof BankReplyMessage) {
				BankReplyMessage msg = (BankReplyMessage) message;
				
				System.err.println("Results Coming in");
				int balance = msg.balance;
				String account = msg.account;
				BranchGUI.resultsCard.display(account, balance);
				
				CardLayout cl = (CardLayout) (BranchGUI.panel.getLayout());
				cl.show(BranchGUI.panel, Constants.RESULTS_PANEL);				
			} else if (message instanceof SubSnapshotDeliveryMessage) {
				SubSnapshotDeliveryMessage msg = (SubSnapshotDeliveryMessage) message;
				System.err.println("Snapshot Coming in " + msg.snapshot.toString());
				BranchGUI.displaySnapshotCard.displayPanel(msg.snapshot);

				CardLayout cl = (CardLayout) (BranchGUI.panel.getLayout());
				cl.show(BranchGUI.panel, Constants.SHOW_SNAPSHOT_PANEL);
			}

		}
		
	}
	
}
