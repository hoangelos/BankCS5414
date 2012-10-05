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
import cs5414.bank.message.TransferMessage;
import cs5414.bank.network.Client;

public class TransferCard extends JPanel implements ActionListener {
	private JTextField acctFromField;
	private JTextField acctToField;
	private JTextField amtField;
	private JTextField serialField;
	
	public TransferCard(BranchGUI maingui) {
		//Transfer Card Details
		super(new GridLayout(5,2));
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
		
		JLabel lblXferSerial = new JLabel("Serial #");
		lblXferSerial.setHorizontalAlignment(SwingConstants.RIGHT);
		lblXferSerial.setBounds(16, 138, 75, 16);
		this.add(lblXferSerial);
		
		serialField = new JTextField();
		serialField.setColumns(10);
		serialField.setBounds(93, 129, 141, 35);
		this.add(serialField);
		
		JButton btnDoXfer = new JButton("Transfer");
		btnDoXfer.setActionCommand(Constants.DO_TRANSFER);
		btnDoXfer.addActionListener(this);
		btnDoXfer.setBounds(71, 176, 117, 29);
		this.add(btnDoXfer);
		
		JButton btnXferCancel = new JButton("Cancel");
		btnXferCancel.setActionCommand(Constants.MENU_PANEL);
		btnXferCancel.addActionListener(maingui);
		btnXferCancel.setBounds(186, 176, 117, 29);
		this.add(btnXferCancel);	
	}
	
	public void actionPerformed(ActionEvent e) {
		if (Constants.DO_TRANSFER == e.getActionCommand()) {
			String fromAcct = acctFromField.getText();
			String toAcct = acctToField.getText();
			int amt = Integer.parseInt(amtField.getText());
			String serial = serialField.getText();
			TransferMessage message = new TransferMessage(null, null, null, serial, fromAcct, toAcct, amt);
			Client testClient = new Client("branchgui_client");
			try {
				String host = BranchGUI.names.resolve_host(BranchGUI.branch_name);
				int port = BranchGUI.names.resolve_port(BranchGUI.branch_name);
				Message msg = testClient.sendMessage(host, port, message);
				System.err.println("Send works");
			} catch (IOException err) {
				System.err.println("Error in sending Query Message. IO Exception");
			} catch (ClassNotFoundException err) {
				System.err.println("Error in sending Query Message. Class Not Found");
			}
		}
	}

}
