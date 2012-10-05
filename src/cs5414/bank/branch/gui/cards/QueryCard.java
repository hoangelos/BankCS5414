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
import cs5414.bank.message.DepositMessage;
import cs5414.bank.message.Message;
import cs5414.bank.message.QueryMessage;
import cs5414.bank.message.ResultMessage;
import cs5414.bank.network.Client;

public class QueryCard extends JPanel implements ActionListener {
	private JTextField acctField;
	private BranchGUI home;
	
	public QueryCard(BranchGUI maingui) {
		//Query Card Details
		super(new GridLayout(5,2));
		home = maingui;
		createPanel(this, null, null, -1);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (Constants.DO_QUERY == e.getActionCommand()) {
			String acct = acctField.getText();
			String serial = home.getUID();
			QueryMessage message = new QueryMessage(null, null, null, serial, acct);
			Client testClient = new Client("branchgui_client");
			try {
				String host = BranchGUI.names.resolve_host(BranchGUI.branch_name);
				int port = BranchGUI.names.resolve_port(BranchGUI.branch_name);
				ResultMessage msg = (ResultMessage) (testClient.sendMessage(host, port, message));
				System.err.println("Query Results Coming in");
				acctField.setText(null);
				JPanel queryPanel = new JPanel(new GridLayout(4,2));
				int balance = msg.getResult();
				QueryMessage origMsg = (QueryMessage) (msg.getMsg());
				createPanel(queryPanel, origMsg.getAccount(), origMsg.getSerial(), balance);
				BranchGUI.resultsCard.display(queryPanel, balance);
				CardLayout cl = (CardLayout) (BranchGUI.panel.getLayout());
				cl.show(BranchGUI.panel, Constants.RESULTS_PANEL);
				removeAll();
				createPanel(this, null, null, -1);
			} catch (IOException err) {
				System.err.println("Error in sending Query Message. IO Exception");
			} catch (ClassNotFoundException err) {
				System.err.println("Error in sending Query Message. Class Not Found");
			}
		}
	}	
	public void createPanel(JPanel panelToAdd, String acct, String serial, int balance) {
		JLabel lblQueryAcct = new JLabel("Account #");
		lblQueryAcct.setHorizontalAlignment(SwingConstants.RIGHT);
		lblQueryAcct.setBounds(16, 23, 75, 16);
		panelToAdd.add(lblQueryAcct);
		
		acctField = new JTextField();
		acctField.setBounds(93, 14, 141, 35);
		acctField.setColumns(10);
		panelToAdd.add(acctField);
		if (acct != null) {
			acctField.setText(acct);
		}
		
		if(balance >= 0) {
			acctField.setEnabled(false);
		} else {
			JButton btnDoQuery = new JButton("Get Balance");
			btnDoQuery.setActionCommand(Constants.DO_QUERY);
			btnDoQuery.addActionListener(this);
			btnDoQuery.setBounds(71, 176, 117, 29);
			panelToAdd.add(btnDoQuery);
			
			JButton btnQueryCancel = new JButton("Cancel");
			btnQueryCancel.setActionCommand(Constants.MENU_PANEL);
			btnQueryCancel.addActionListener(home);
			btnQueryCancel.setBounds(186, 176, 117, 29);
			panelToAdd.add(btnQueryCancel);
		}
	}
}
