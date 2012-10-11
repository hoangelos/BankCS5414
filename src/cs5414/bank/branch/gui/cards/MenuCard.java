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

public class MenuCard extends JPanel implements ActionListener {
	
	public MenuCard(BranchGUI maingui) {
		//Setup Menu Card
		super(new GridLayout(5,1));
		JButton btnShowWithdrawl = new JButton("Withdrawl");
		btnShowWithdrawl.setBackground(new Color(47, 79, 79));
		btnShowWithdrawl.setBounds(6, 51, 117, 29);
		this.add(btnShowWithdrawl);
		btnShowWithdrawl.setActionCommand(Constants.WITHDRAW_PANEL);
		btnShowWithdrawl.addActionListener(this);
		
		JButton btnShowDeposit = new JButton("Deposit");
		btnShowDeposit.addActionListener(this);
		btnShowDeposit.setActionCommand(Constants.DEPOSIT_PANEL);
		btnShowDeposit.setBackground(new Color(47, 79, 79));
		btnShowDeposit.setBounds(6, 107, 117, 29);
		this.add(btnShowDeposit);
		
		JButton btnShowBalance = new JButton("Balance");
		btnShowBalance.setBackground(new Color(47, 79, 79));
		btnShowBalance.addActionListener(this);
		btnShowBalance.setActionCommand(Constants.QUERY_PANEL);
		btnShowBalance.setBounds(6, 165, 117, 29);
		this.add(btnShowBalance);
		
		JButton btnShowTransfer = new JButton("Transfer");
		btnShowTransfer.setBackground(new Color(47, 79, 79));
		btnShowTransfer.addActionListener(this);
		btnShowTransfer.setActionCommand(Constants.TRANSFER_PANEL);
		btnShowTransfer.setBounds(6, 221, 117, 29);
		this.add(btnShowTransfer);
		
		JButton btnDoSnapshot = new JButton("Snapshot");
		btnDoSnapshot.setBackground(new Color(47, 79, 79));
		btnDoSnapshot.addActionListener(this);
		btnDoSnapshot.setActionCommand(Constants.TAKE_SNAPSHOT_PANEL);
		btnDoSnapshot.setBounds(6, 279, 117, 29);
		this.add(btnDoSnapshot);		
	}

	public void actionPerformed(ActionEvent e) {
		if ( (Constants.MENU_PANEL == e.getActionCommand()) ||
			 (Constants.WITHDRAW_PANEL == e.getActionCommand()) ||
			 (Constants.DEPOSIT_PANEL == e.getActionCommand()) ||
			 (Constants.QUERY_PANEL == e.getActionCommand()) ||
			 (Constants.TRANSFER_PANEL == e.getActionCommand()) ||
			 (Constants.TAKE_SNAPSHOT_PANEL == e.getActionCommand())) {
			CardLayout cl = (CardLayout) (BranchGUI.panel.getLayout());
			cl.show(BranchGUI.panel, e.getActionCommand());
			BranchGUI.resultsCard.removeAll();
			BranchGUI.panel.updateUI();
		} 
	}
	
}
