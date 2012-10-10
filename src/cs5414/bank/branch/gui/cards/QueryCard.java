package cs5414.bank.branch.gui.cards;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cs5414.bank.branch.gui.BranchGUI;
import cs5414.bank.branch.gui.Constants;
import cs5414.bank.message.BankReplyMessage;
import cs5414.bank.message.BankRequestMessage;
import cs5414.bank.network.MessageSenderClient;

public class QueryCard extends JPanel implements ActionListener {
	private JTextField acctField;
	private BranchGUI home;
	
	public QueryCard(BranchGUI maingui) {
		//Query Card Details
		super(new GridLayout(5,2));
		home = maingui;
		createPanel();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (Constants.DO_QUERY == e.getActionCommand()) {
			String acct = acctField.getText();
			long serial = home.getUID();
			BankRequestMessage message = new BankRequestMessage();
			message.source = BranchGUI.name;
			message.destination = BranchGUI.branch_name;
			message.serial = serial;
			message.account = acct;
			message.requestType = BankRequestMessage.RequestType.QUERY;
			
			MessageSenderClient testClient = new MessageSenderClient(BranchGUI.name, BranchGUI.net);
			String host = BranchGUI.net.getHost(BranchGUI.branch_name);
			int port = BranchGUI.net.getPort(BranchGUI.branch_name);
			testClient.sendMessage(message);
			removeAll();
			createPanel();
		}
	}	
	public void createPanel() {
		JLabel lblQueryAcct = new JLabel("Account #");
		lblQueryAcct.setHorizontalAlignment(SwingConstants.RIGHT);
		lblQueryAcct.setBounds(16, 23, 75, 16);
		this.add(lblQueryAcct);
		
		acctField = new JTextField();
		acctField.setBounds(93, 14, 141, 35);
		acctField.setColumns(10);
		this.add(acctField);
		
		JButton btnDoQuery = new JButton("Get Balance");
		btnDoQuery.setActionCommand(Constants.DO_QUERY);
		btnDoQuery.addActionListener(this);
		btnDoQuery.setBounds(71, 176, 117, 29);
		this.add(btnDoQuery);
		
		JButton btnQueryCancel = new JButton("Cancel");
		btnQueryCancel.setActionCommand(Constants.MENU_PANEL);
		btnQueryCancel.addActionListener(home);
		btnQueryCancel.setBounds(186, 176, 117, 29);
		this.add(btnQueryCancel);
	}
}
