package cs5414.bank.gui.branch.cards;

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

import cs5414.bank.gui.branch.Constants;
import cs5414.bank.gui.branch.BranchGUI;
import cs5414.bank.message.BankRequestMessage;
import cs5414.bank.network.MessageSenderClient;

public class WithdrawlCard extends JPanel implements ActionListener  {
	private JTextField acctField;
	private JTextField amtField;
	private BranchGUI home;
	
	public WithdrawlCard(BranchGUI maingui) {
		super(new GridLayout(5,2));
		home = maingui;
		//WithDrawl Card Details

		createPanel();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (Constants.DO_WITHDRAWL == e.getActionCommand()) {
			String acct = acctField.getText();
			int amt = Integer.parseInt(amtField.getText());
			long serial = home.getUID();
			BankRequestMessage message = new BankRequestMessage();
			message.source = BranchGUI.name;
			message.destination = BranchGUI.branch_name;
			message.account = acct;
			message.amount = amt;
			message.serial = serial;
			message.requestType = BankRequestMessage.RequestType.WITHDRAW;
			MessageSenderClient testClient = new MessageSenderClient(BranchGUI.name, BranchGUI.net);

			testClient.sendMessage(message);
			
			amtField.setText(null);
			acctField.setText(null);
			removeAll();
			createPanel();

		} else if(Constants.MENU_PANEL == e.getActionCommand()) {
			createPanel();
			home.actionPerformed(e);
		}
	}
	
	private void createPanel() {
		JLabel lblTestLabel = new JLabel("Account #");
		lblTestLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTestLabel.setBounds(16, 23, 75, 16);
		this.add(lblTestLabel);
		
		acctField = new JTextField();
		acctField.setColumns(10);
		acctField.setBounds(93, 14, 141, 35);
		this.add(acctField);
		
		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAmount.setBounds(16, 81, 75, 16);
		this.add(lblAmount);
		
		amtField = new JTextField();
		amtField.setColumns(10);
		amtField.setBounds(93, 72, 141, 35);
		this.add(amtField);

		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setActionCommand(Constants.DO_WITHDRAWL);
		btnWithdraw.addActionListener(this);
		btnWithdraw.setBounds(71, 176, 117, 29);
		this.add(btnWithdraw);
		
		JButton btnWithdrawlCancel = new JButton("Cancel");
		btnWithdrawlCancel.setActionCommand(Constants.MENU_PANEL);
		btnWithdrawlCancel.addActionListener(home);
		btnWithdrawlCancel.setBounds(186, 176, 117, 29);
		this.add(btnWithdrawlCancel);
	}
}
