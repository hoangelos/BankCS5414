package cs5414.bank.branch.gui.cards;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cs5414.bank.branch.gui.BranchGUI;
import cs5414.bank.branch.gui.Constants;

public class ResultsCard extends JPanel {
	private BranchGUI homeScreen;
	
	public ResultsCard(BranchGUI maingui) {
		super(new GridLayout(3,1));
		homeScreen = maingui;
	}
	
	public void display(String account, int balance) {
		JPanel top = new JPanel(new BorderLayout());
		
		//Add label for results
		JLabel acctLbl = new JLabel("Account");
		acctLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		top.add(acctLbl, BorderLayout.WEST);
		
		//Add the actual results
		JTextField acctTxtField = new JTextField();
		acctTxtField.setText(account);
		acctTxtField.setEnabled(false);
		top.add(acctTxtField, BorderLayout.EAST);
		this.add(top);
		
		JPanel rowTwo = new JPanel(new BorderLayout());
		
		//Add label for results
		JLabel resultLbl = new JLabel("Balance");
		resultLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		rowTwo.add(resultLbl, BorderLayout.WEST);
		
		//Add the actual results
		JTextField resultTxtField = new JTextField();
		resultTxtField.setText(Integer.toString(balance));
		resultTxtField.setEnabled(false);
		rowTwo.add(resultTxtField, BorderLayout.EAST);
		this.add(rowTwo);
		
		//Add button to take us back home
		JButton btnWithdrawlCancel = new JButton("Main Menu");
		btnWithdrawlCancel.setActionCommand(Constants.MENU_PANEL);
		btnWithdrawlCancel.addActionListener(homeScreen);
		btnWithdrawlCancel.setBounds(186, 176, 117, 29);
		this.add(btnWithdrawlCancel, BorderLayout.SOUTH);
	}
}
