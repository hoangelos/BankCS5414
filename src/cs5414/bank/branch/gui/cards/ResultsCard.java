package cs5414.bank.branch.gui.cards;

import java.awt.BorderLayout;
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
		super(new BorderLayout());
		homeScreen = maingui;
	}
	
	public void display(JPanel sentInfo, int balance) {
		if (sentInfo != null) {
			//Add the info from the different cards on what was sent
			this.add(sentInfo, BorderLayout.NORTH);
			
			//Add label for results
			JLabel resultLbl = new JLabel("Balance");
			resultLbl.setHorizontalAlignment(SwingConstants.RIGHT);
			this.add(resultLbl, BorderLayout.WEST);
			
			//Add the actual results
			JTextField resultTxtField = new JTextField(balance);
			resultTxtField.setText(Integer.toString(balance));
			resultTxtField.setEnabled(false);
			this.add(resultTxtField, BorderLayout.EAST);
			
			//Add button to take us back home
			JButton btnWithdrawlCancel = new JButton("Main Menu");
			btnWithdrawlCancel.setActionCommand(Constants.MENU_PANEL);
			btnWithdrawlCancel.addActionListener(homeScreen);
			btnWithdrawlCancel.setBounds(186, 176, 117, 29);
			this.add(btnWithdrawlCancel, BorderLayout.SOUTH);
		}
	}
}
