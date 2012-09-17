package cs5414.bank.branch.gui.cards;

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

import cs5414.bank.branch.gui.BranchGUI;
import cs5414.bank.branch.gui.Constants;
import cs5414.bank.message.DepositMessage;
import cs5414.bank.message.ResultMessage;
import cs5414.bank.network.Client;

public class DepositCard extends JPanel implements ActionListener {
	private JTextField acctField;
	private JTextField amtField;
	private JTextField serialField;
	private BranchGUI home;
	
	public DepositCard(BranchGUI maingui) {
		//Deposit Card Details
		super(new GridLayout(5,2));
		home = maingui;
		createPanel(this, null, -1, null, -1);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (Constants.DO_DEPOSIT == e.getActionCommand()) {
			String acct = acctField.getText();
			int amt = Integer.parseInt(amtField.getText());
			String serial = serialField.getText();
			DepositMessage message = new DepositMessage(null, null, null, serial, acct, amt);
			Client testClient = new Client("branchgui_client");
			try {
				ResultMessage msg = (ResultMessage) (testClient.sendMessage("localhost", 10500, message));
				System.err.println("Deposit Results Coming in");
				amtField.setText(null);
				serialField.setText(null);
				acctField.setText(null);
				JPanel depositPanel = new JPanel(new GridLayout(4,2));
				int balance = msg.getResult();
				DepositMessage origMsg = (DepositMessage) (msg.getMsg());
				createPanel(depositPanel, origMsg.getAccount(), origMsg.getAmount(), origMsg.getSerial(), balance);
				BranchGUI.resultsCard.display(depositPanel, balance);
				CardLayout cl = (CardLayout) (BranchGUI.panel.getLayout());
				cl.show(BranchGUI.panel, Constants.RESULTS_PANEL);
				removeAll();
				createPanel(this, null, -1, null, -1);
			} catch (IOException err) {
				System.err.println("Error in sending Deposit Message. IO Exception");
			} catch (ClassNotFoundException err) {
				System.err.println("Error in sending Deposit Message. Class Not Found");
			}
		}
	}
	
	public void createPanel(JPanel panelToAdd, String acct, int amt, String serial, int balance) {
		JLabel lblCancelAcct = new JLabel("Account #");
		lblCancelAcct.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCancelAcct.setBounds(16, 23, 75, 16);
		panelToAdd.add(lblCancelAcct);
		
		acctField = new JTextField();
		acctField.setBounds(93, 14, 141, 35);
		panelToAdd.add(acctField);
		acctField.setColumns(10);
		if (acct != null) {
			acctField.setText(acct);
		}
		
		JLabel lblCancelAmount = new JLabel("Amount");
		lblCancelAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCancelAmount.setBounds(16, 81, 75, 16);
		panelToAdd.add(lblCancelAmount);
		
		amtField = new JTextField();
		amtField.setColumns(10);
		amtField.setBounds(93, 72, 141, 35);
		panelToAdd.add(amtField);
		if (amt >= 0) {
			amtField.setText(Integer.toString(amt));
		}
		
		JLabel lblCancelSerial = new JLabel("Serial #");
		lblCancelSerial.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCancelSerial.setBounds(16, 138, 75, 16);
		panelToAdd.add(lblCancelSerial);
		
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
		} else {
			JButton btnDoDeposit = new JButton("Deposit");
			btnDoDeposit.setActionCommand(Constants.DO_DEPOSIT);
			btnDoDeposit.addActionListener(this);
			btnDoDeposit.setBounds(71, 176, 117, 29);
			panelToAdd.add(btnDoDeposit);
			
			JButton btnDepositCancel = new JButton("Cancel");
			btnDepositCancel.setActionCommand(Constants.MENU_PANEL);
			btnDepositCancel.addActionListener(home);
			btnDepositCancel.setBounds(186, 176, 117, 29);
			panelToAdd.add(btnDepositCancel);	
		}
	}

}
