package cs5414.bank.gui.branch.cards;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cs5414.bank.gui.branch.BranchGUI;
import cs5414.bank.gui.branch.Constants;
import cs5414.bank.message.BankRequestMessage;
import cs5414.bank.network.MessageSenderClient;

public class DepositCard extends JPanel implements ActionListener {
	private JTextField acctField;
	private JTextField amtField;
	private BranchGUI home;
	
	public DepositCard(BranchGUI maingui) {
		//Deposit Card Details
		super(new GridLayout(5,2));
		home = maingui;
		createPanel();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (Constants.DO_DEPOSIT == e.getActionCommand()) {
			String acct = acctField.getText();
			int amt = Integer.parseInt(amtField.getText());
			long serial = home.getUID();
			BankRequestMessage message = new BankRequestMessage();
			message.source = BranchGUI.name;
			message.destination = BranchGUI.branch_name;
			message.serial = serial;
			message.account = acct;
			message.amount = amt;
			message.requestType = BankRequestMessage.RequestType.DEPOSIT;
			MessageSenderClient testClient = new MessageSenderClient(BranchGUI.name, BranchGUI.net);

			testClient.sendMessage(message);
			amtField.setText(null);
			acctField.setText(null);
			removeAll();
			createPanel();

		}
	}
	
	public void createPanel() {
		JLabel lblCancelAcct = new JLabel("Account #");
		lblCancelAcct.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCancelAcct.setBounds(16, 23, 75, 16);
		this.add(lblCancelAcct);
		
		acctField = new JTextField();
		acctField.setBounds(93, 14, 141, 35);
		this.add(acctField);
		acctField.setColumns(10);
		
		JLabel lblCancelAmount = new JLabel("Amount");
		lblCancelAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCancelAmount.setBounds(16, 81, 75, 16);
		this.add(lblCancelAmount);
		
		amtField = new JTextField();
		amtField.setColumns(10);
		amtField.setBounds(93, 72, 141, 35);
		this.add(amtField);
		
		JButton btnDoDeposit = new JButton("Deposit");
		btnDoDeposit.setActionCommand(Constants.DO_DEPOSIT);
		btnDoDeposit.addActionListener(this);
		btnDoDeposit.setBounds(71, 176, 117, 29);
		this.add(btnDoDeposit);
		
		JButton btnDepositCancel = new JButton("Cancel");
		btnDepositCancel.setActionCommand(Constants.MENU_PANEL);
		btnDepositCancel.addActionListener(home);
		btnDepositCancel.setBounds(186, 176, 117, 29);
		this.add(btnDepositCancel);	
	}

}
