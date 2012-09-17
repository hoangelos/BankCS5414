package cs5414.bank.branch.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import cs5414.bank.branch.gui.Constants;
import cs5414.bank.branch.gui.cards.*;

public class BranchGUI implements ActionListener {
	private JFrame frame;
	static public JPanel panel;
	static public ResultsCard resultsCard;
	static private MenuCard menuCard;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BranchGUI window = new BranchGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BranchGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(UIManager.getColor("Desktop.background"));
		frame.getContentPane().setLayout(null);
		
		//Setup main GUI settings, title, etc
		JLabel lblCsBank = new JLabel("CS5414 Bank");
		lblCsBank.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblCsBank.setBounds(136, 6, 173, 33);
		frame.getContentPane().add(lblCsBank);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Setup Panel Cards are enclosed in
		panel = new JPanel();
		panel.setLayout(new CardLayout(0, 0));
		panel.setBounds(135, 51, 309, 221);
		
		//Setup Home Menu screen
		menuCard = new MenuCard(this);
		panel.add(menuCard, Constants.MENU_PANEL);
		
		//Setup Withdrawn screen
		WithdrawlCard withdrawCard = new WithdrawlCard(this);
		panel.add(withdrawCard, Constants.WITHDRAW_PANEL);
		
		//Setup Deposit screen
		DepositCard depositCard = new DepositCard(this);
		panel.add(depositCard, Constants.DEPOSIT_PANEL);
		
		//Setup Query screen
		QueryCard queryCard = new QueryCard(this);
		panel.add(queryCard, Constants.QUERY_PANEL);
		
		//Setup Transfer screen
		TransferCard transferCard = new TransferCard(this);
		panel.add(transferCard, Constants.TRANSFER_PANEL);
		
		//Setup Results scren
		resultsCard = new ResultsCard(this);
		panel.add(resultsCard, Constants.RESULTS_PANEL);
		
		//Add panel to frame
		frame.getContentPane().add(panel);
	}
	public void actionPerformed(ActionEvent e) {
		
		if ( (Constants.MENU_PANEL == e.getActionCommand()) ||
			 (Constants.WITHDRAW_PANEL == e.getActionCommand()) ||
			 (Constants.DEPOSIT_PANEL == e.getActionCommand()) ||
			 (Constants.QUERY_PANEL == e.getActionCommand()) ||
			 (Constants.TRANSFER_PANEL == e.getActionCommand())) {
			menuCard.actionPerformed(e);
		} 
	}
}
