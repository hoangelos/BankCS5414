package cs5414.bank.branch.gui.cards;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cs5414.bank.branch.gui.BranchGUI;
import cs5414.bank.branch.gui.Constants;
import cs5414.bank.message.Message;
import cs5414.bank.message.DepositMessage;
import cs5414.bank.network.Client;

public class DepositCard extends JPanel implements ActionListener {
	private JTextField acctField;
	private JTextField amtField;
	private JTextField serialField;
	
	public DepositCard(BranchGUI maingui) {
		//Deposit Card Details
		super(new GridLayout(5,2));
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
		
		JLabel lblCancelSerial = new JLabel("Serial #");
		lblCancelSerial.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCancelSerial.setBounds(16, 138, 75, 16);
		this.add(lblCancelSerial);
		
		serialField = new JTextField();
		serialField.setColumns(10);
		serialField.setBounds(93, 129, 141, 35);
		this.add(serialField);
		
		JButton btnDoDeposit = new JButton("Deposit");
		btnDoDeposit.setActionCommand(Constants.DO_DEPOSIT);
		btnDoDeposit.addActionListener(this);
		btnDoDeposit.setBounds(71, 176, 117, 29);
		this.add(btnDoDeposit);
		
		JButton btnDepositCancel = new JButton("Cancel");
		btnDepositCancel.setActionCommand(Constants.MENU_PANEL);
		btnDepositCancel.addActionListener(maingui);
		btnDepositCancel.setBounds(186, 176, 117, 29);
		this.add(btnDepositCancel);		
	}
	
	public void actionPerformed(ActionEvent e) {
		if (Constants.DO_DEPOSIT == e.getActionCommand()) {
			String acct = acctField.getText();
			int amt = Integer.parseInt(amtField.getText());
			String serial = serialField.getText();
			DepositMessage message = new DepositMessage(null, null, serial, acct, amt);
			Client testClient = new Client("branchgui_client");
			try {
				Message msg = testClient.sendMessage("localhost", 10100, message);
				System.err.println("Send works");
			} catch (IOException err) {
				System.err.println("Error in sending Deposit Message. IO Exception");
			} catch (ClassNotFoundException err) {
				System.err.println("Error in sending Deposit Message. Class Not Found");
			}
		}
	}

}
