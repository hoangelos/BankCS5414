package cs5414.bank.branch.gui.cards;

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
import cs5414.bank.message.Message;
import cs5414.bank.message.QueryMessage;
import cs5414.bank.network.Client;

public class QueryCard extends JPanel implements ActionListener {
	private JTextField acctField;
	private JTextField serialField;
	
	public QueryCard(BranchGUI maingui) {
		//Query Card Details
		super(new GridLayout(5,2));
		JLabel lblQueryAcct = new JLabel("Account #");
		lblQueryAcct.setHorizontalAlignment(SwingConstants.RIGHT);
		lblQueryAcct.setBounds(16, 23, 75, 16);
		this.add(lblQueryAcct);
		
		acctField = new JTextField();
		acctField.setBounds(93, 14, 141, 35);
		this.add(acctField);
		acctField.setColumns(10);
		
		JLabel lblQuerySerial = new JLabel("Serial #");
		lblQuerySerial.setHorizontalAlignment(SwingConstants.RIGHT);
		lblQuerySerial.setBounds(16, 138, 75, 16);
		this.add(lblQuerySerial);
		
		serialField = new JTextField();
		serialField.setColumns(10);
		serialField.setBounds(93, 129, 141, 35);
		this.add(serialField);
		
		JButton btnDoQuery = new JButton("Get Balance");
		btnDoQuery.setActionCommand(Constants.DO_QUERY);
		btnDoQuery.addActionListener(this);
		btnDoQuery.setBounds(71, 176, 117, 29);
		this.add(btnDoQuery);
		
		JButton btnQueryCancel = new JButton("Cancel");
		btnQueryCancel.setActionCommand(Constants.MENU_PANEL);
		btnQueryCancel.addActionListener(maingui);
		btnQueryCancel.setBounds(186, 176, 117, 29);
		this.add(btnQueryCancel);
			
	}
	
	public void actionPerformed(ActionEvent e) {
		if (Constants.DO_QUERY == e.getActionCommand()) {
			String acct = acctField.getText();
			String serial = serialField.getText();
			QueryMessage message = new QueryMessage(null, null, null, serial, acct);
			Client testClient = new Client("branchgui_client");
			try {
				Message msg = testClient.sendMessage("localhost", 10100, message);
				System.err.println("Send works");
			} catch (IOException err) {
				System.err.println("Error in sending Query Message. IO Exception");
			} catch (ClassNotFoundException err) {
				System.err.println("Error in sending Query Message. Class Not Found");
			}
		}
	}	
	
}
