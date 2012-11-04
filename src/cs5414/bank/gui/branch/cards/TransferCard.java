package cs5414.bank.gui.branch.cards;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cs5414.bank.gui.branch.BranchGUI;
import cs5414.bank.gui.branch.Constants;
import cs5414.bank.message.BankRequestMessage;
import cs5414.bank.network.MessageSenderClient;

public class TransferCard extends JPanel implements ActionListener {
	private JTextField acctFromField;
	private JTextField acctToField;
	private JTextField amtField;
	private BranchGUI home;
	
	public TransferCard(BranchGUI maingui) {
		//Transfer Card Details
		super(new GridLayout(5,2));
		home = maingui;
		createPanel();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (Constants.DO_TRANSFER == e.getActionCommand()) {
			String fromAcct = acctFromField.getText();
			String toAcct = acctToField.getText();
			int amt = Integer.parseInt(amtField.getText());
			long serial = home.getUID();
			BankRequestMessage message = new BankRequestMessage();
			message.source = BranchGUI.name;
			message.destination = BranchGUI.branch_name;
			message.account = fromAcct;
			message.accountInto = toAcct;
			message.amount = amt;
			message.serial = serial;
			message.requestType = BankRequestMessage.RequestType.TRANSFER;
			MessageSenderClient testClient = new MessageSenderClient(BranchGUI.name, BranchGUI.net);

			testClient.sendMessage(message);
			removeAll();
			createPanel();
		}
	}

	public void createPanel() {
		JLabel lblXferFromAcct = new JLabel("From Account #");
		lblXferFromAcct.setHorizontalAlignment(SwingConstants.RIGHT);
		lblXferFromAcct.setBounds(16, 23, 75, 16);
		this.add(lblXferFromAcct);
		
		acctFromField = new JTextField();
		acctFromField.setBounds(93, 14, 141, 35);
		this.add(acctFromField);
		acctFromField.setColumns(10);
		
		JLabel lblXferToAcct = new JLabel("To Account #");
		lblXferToAcct.setHorizontalAlignment(SwingConstants.RIGHT);
		lblXferToAcct.setBounds(16, 23, 75, 16);
		this.add(lblXferToAcct);
		
		acctToField = new JTextField();
		acctToField.setBounds(93, 14, 141, 35);
		this.add(acctToField);
		acctToField.setColumns(10);
		
		JLabel lblXferAmount = new JLabel("Amount");
		lblXferAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblXferAmount.setBounds(16, 81, 75, 16);
		this.add(lblXferAmount);
		
		amtField = new JTextField();
		amtField.setColumns(10);
		amtField.setBounds(93, 72, 141, 35);
		this.add(amtField);
		
		JButton btnDoXfer = new JButton("Transfer");
		btnDoXfer.setActionCommand(Constants.DO_TRANSFER);
		btnDoXfer.addActionListener(this);
		btnDoXfer.setBounds(71, 176, 117, 29);
		this.add(btnDoXfer);
		
		JButton btnXferCancel = new JButton("Cancel");
		btnXferCancel.setActionCommand(Constants.MENU_PANEL);
		btnXferCancel.addActionListener(home);
		btnXferCancel.setBounds(186, 176, 117, 29);
		this.add(btnXferCancel);	
	}
}
