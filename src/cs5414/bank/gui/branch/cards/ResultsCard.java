package cs5414.bank.gui.branch.cards;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import cs5414.bank.gui.branch.BranchGUI;
import cs5414.bank.gui.branch.Constants;

public class ResultsCard extends JPanel {
	private BranchGUI homeScreen;
    private JTextPane jtp;
	
	public ResultsCard(BranchGUI maingui) {
		super(new GridLayout(3,1));
		homeScreen = maingui;
	}
	
	public void display(String account, int balance) {
		jtp = new JTextPane();
		StyledDocument doc = jtp.getStyledDocument();

		SimpleAttributeSet keyWord = new SimpleAttributeSet();
		StyleConstants.setBold(keyWord, true);
		
		try {
			doc.insertString(0, "Account", keyWord);
			doc.insertString(doc.getLength(), "\t"+account+"\n", null);
			doc.insertString(doc.getLength(), "Balance", keyWord);
			doc.insertString(doc.getLength(), "\t"+String.valueOf(balance), null);
		} catch(BadLocationException e) {
	    	System.out.println(e);
		}
		
		jtp.setEditable(false);
		this.add(jtp);
		
		//Add button to take us back home
		JButton btnWithdrawlCancel = new JButton("Main Menu");
		btnWithdrawlCancel.setActionCommand(Constants.MENU_PANEL);
		btnWithdrawlCancel.addActionListener(homeScreen);
		btnWithdrawlCancel.setBounds(186, 176, 117, 29);
		this.add(btnWithdrawlCancel, BorderLayout.SOUTH);
	}
}
