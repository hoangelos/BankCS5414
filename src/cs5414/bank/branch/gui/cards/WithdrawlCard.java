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

import cs5414.bank.branch.gui.Constants;
import cs5414.bank.branch.gui.BranchGUI;
import cs5414.bank.message.Message;
import cs5414.bank.message.ResultMessage;
import cs5414.bank.message.WithdrawMessage;
import cs5414.bank.network.Client;

public class WithdrawlCard extends JPanel implements ActionListener  {
	private JTextField acctField;
	private JTextField amtField;
	private JTextField serialField;
	private BranchGUI home;
	
	public WithdrawlCard(BranchGUI maingui) {
		super(new GridLayout(5,2));
		home = maingui;
		//WithDrawl Card Details

		createPanel(this, null, -1, null, -1);
		
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setActionCommand(Constants.DO_WITHDRAWL);
		btnWithdraw.addActionListener(this);
		btnWithdraw.setBounds(71, 176, 117, 29);
		this.add(btnWithdraw);
		
		JButton btnWithdrawlCancel = new JButton("Cancel");
		btnWithdrawlCancel.setActionCommand(Constants.MENU_PANEL);
		btnWithdrawlCancel.addActionListener(maingui);
		btnWithdrawlCancel.setBounds(186, 176, 117, 29);
		this.add(btnWithdrawlCancel);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (Constants.DO_WITHDRAWL == e.getActionCommand()) {
			String acct = acctField.getText();
			int amt = Integer.parseInt(amtField.getText());
			String serial = serialField.getText();
			WithdrawMessage message = new WithdrawMessage(null, null, serial, acct, amt);
			Client testClient = new Client("branchgui_client");
			try {
				ResultMessage msg = (ResultMessage) (testClient.sendMessage("localhost", 10100, message));
				System.err.println("Withdrawl Results Coming in");
				amtField.setText(null);
				serialField.setText(null);
				acctField.setText(null);
				JPanel withdrawPanel = new JPanel(new GridLayout(4,2));
				int balance = msg.getResult();
				createPanel(withdrawPanel, acct, amt, serial, balance);
				BranchGUI.resultsCard.display(withdrawPanel, balance);
				CardLayout cl = (CardLayout) (BranchGUI.panel.getLayout());
				cl.show(BranchGUI.panel, Constants.RESULTS_PANEL);
			} catch (IOException err) {
				System.err.println("Error in sending Withdrawl Message. IO Exception");
			} catch (ClassNotFoundException err) {
				System.err.println("Error in sending Withdrawl Message. Class Not Found");
			}
		} else if(Constants.MENU_PANEL == e.getActionCommand()) {
			createPanel(this, null, -1, null, -1);
			removeAll();
			home.actionPerformed(e);
		}
	}
	
	private void createPanel(JPanel panelToAdd, String acct, int amt, String serial, int balance) {
		JLabel lblTestLabel = new JLabel("Account #");
		lblTestLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTestLabel.setBounds(16, 23, 75, 16);
		panelToAdd.add(lblTestLabel);
		
		acctField = new JTextField();
		acctField.setColumns(10);
		acctField.setBounds(93, 14, 141, 35);
		panelToAdd.add(acctField);
		if (acct != null) {
			acctField.setText(acct);
		}
		
		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAmount.setBounds(16, 81, 75, 16);
		panelToAdd.add(lblAmount);
		
		amtField = new JTextField();
		amtField.setColumns(10);
		amtField.setBounds(93, 72, 141, 35);
		panelToAdd.add(amtField);
		if (amt >= 0) {
			amtField.setText(Integer.toString(amt));
		}
		
		JLabel lblSerial = new JLabel("Serial #");
		lblSerial.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSerial.setBounds(16, 138, 75, 16);
		panelToAdd.add(lblSerial);
		
		serialField = new JTextField();
		serialField.setColumns(10);
		serialField.setBounds(93, 129, 141, 35);
		panelToAdd.add(serialField);
		if(serial != null) {
			serialField.setText(serial);
		}
		
		if(balance >= 0) {
			acctField.setEnabled(false);
			amtField.setEnabled(false);
			serialField.setEnabled(false);
		}
	}
}
