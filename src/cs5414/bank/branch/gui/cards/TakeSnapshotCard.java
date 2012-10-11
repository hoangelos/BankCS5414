package cs5414.bank.branch.gui.cards;

import java.awt.CardLayout;
import java.awt.Color;
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
import cs5414.bank.message.BankRequestMessage;
import cs5414.bank.message.RequestSnapshotMessage;
import cs5414.bank.network.MessageSenderClient;

public class TakeSnapshotCard extends JPanel implements ActionListener {
	private BranchGUI home;
	
	public TakeSnapshotCard(BranchGUI maingui) {
		//Setup Menu Card
		super(new GridLayout(5,1));
		home = maingui;
		createPanel();
	
	}

	public void actionPerformed(ActionEvent e) {
		if (Constants.DO_SNAPSHOT == e.getActionCommand()) {
			long serial = home.getUID();
			RequestSnapshotMessage message = new RequestSnapshotMessage();
			message.source = BranchGUI.name;
			message.destination = BranchGUI.branch_name;
			MessageSenderClient testClient = new MessageSenderClient(BranchGUI.name, BranchGUI.net);

			testClient.sendMessage(message);
			removeAll();
			createPanel();
		}
	}
	
	public void createPanel() {
		JButton btnDoSnapshot = new JButton("Take Snapshot");
		btnDoSnapshot.setActionCommand(Constants.DO_SNAPSHOT);
		btnDoSnapshot.addActionListener(this);
		btnDoSnapshot.setBounds(71, 176, 117, 29);
		this.add(btnDoSnapshot);
		
		JButton btnDepositCancel = new JButton("Cancel");
		btnDepositCancel.setActionCommand(Constants.MENU_PANEL);
		btnDepositCancel.addActionListener(home);
		btnDepositCancel.setBounds(186, 176, 117, 29);
		this.add(btnDepositCancel);
	}
	
}
