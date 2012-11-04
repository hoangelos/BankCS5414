package cs5414.bank.gui.branch.cards;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import cs5414.bank.gui.branch.BranchGUI;
import cs5414.bank.gui.branch.Constants;
import cs5414.bank.message.BaseMessage;
import cs5414.bank.message.RequestSnapshotMessage;
import cs5414.bank.misc.LocalSubSnapshot;
import cs5414.bank.network.MessageSenderClient;

public class DisplaySnapshotCard  extends JPanel implements ActionListener  {
	private BranchGUI home;
    private JTextPane jtp;
	
	public DisplaySnapshotCard(BranchGUI maingui) {
		//Setup Menu Card
		super();
		home = maingui;
	
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
		}
	}
	
	public void displayPanel(LocalSubSnapshot local_snapshot) {

		removeAll();
		jtp = new JTextPane();
		StyledDocument doc = jtp.getStyledDocument();
		
		SimpleAttributeSet keyWord = new SimpleAttributeSet();
		StyleConstants.setBold(keyWord, true);
	    
	    try {
		    doc.insertString(0, "Balances:\n\n", keyWord);
		    doc.insertString(doc.getLength(), "Account\tBalance\n", null);
			for (String acc: local_snapshot.initialBalances.keySet()) {
				doc.insertString(doc.getLength(), acc + "\t" + local_snapshot.initialBalances.get(acc) + "\n", null);
			}
			doc.insertString(doc.getLength(), "\nInTransit:\n\n", keyWord);
			for (BaseMessage msg: local_snapshot.seenMessagesInTransit) {
				doc.insertString(doc.getLength(), msg + "\n", null);
			}
	    } catch(BadLocationException e) {
	    	System.out.println(e);
	    }

		this.add(jtp);
		
		JButton btnDepositCancel = new JButton("Cancel");
		btnDepositCancel.setActionCommand(Constants.MENU_PANEL);
		btnDepositCancel.addActionListener(home);
		btnDepositCancel.setBounds(186, 176, 117, 29);
		this.add(btnDepositCancel);	
	}
	
}
