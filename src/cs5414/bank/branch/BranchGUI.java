package cs5414.bank.branch;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BranchGUI implements ActionListener {
	private JFrame frame;
	private JPanel panel;
    final static String MENU_PANEL = "Menu Panel";
    final static String WITHDRAW_PANEL = "Withdraw Panel";
    final static String DEPOSIT_PANEL = "Deposit Panel";
    final static String QUERY_PANEL = "Query Panel";
    final static String TRANSFER_PANEL = "Transfer Panel";
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    
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
		JPanel menuCard = new JPanel(new GridLayout(4,1));
	    JPanel withdrawCard = new JPanel(new GridLayout(4,2));
	    JPanel depositCard = new JPanel(new GridLayout(4,2));
	    JPanel queryCard = new JPanel(new GridLayout(4,2));
	    JPanel transferCard = new JPanel(new GridLayout(4,2));
		frame = new JFrame();
		frame.getContentPane().setBackground(UIManager.getColor("Desktop.background"));
		frame.getContentPane().setLayout(null);
		
		
		JLabel lblCsBank = new JLabel("CS5414 Bank");
		lblCsBank.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblCsBank.setBounds(136, 6, 173, 33);
		frame.getContentPane().add(lblCsBank);
		
		//Setup Menu Card
		JButton btnShowWithdrawl = new JButton("Withdrawl");
		btnShowWithdrawl.setBackground(new Color(47, 79, 79));
		btnShowWithdrawl.setBounds(6, 51, 117, 29);
		menuCard.add(btnShowWithdrawl);
		btnShowWithdrawl.setActionCommand(WITHDRAW_PANEL);
		btnShowWithdrawl.addActionListener(this);
		
		JButton btnShowDeposit = new JButton("Deposit");
		btnShowDeposit.addActionListener(this);
		btnShowDeposit.setActionCommand(DEPOSIT_PANEL);
		btnShowDeposit.setBackground(new Color(47, 79, 79));
		btnShowDeposit.setBounds(6, 107, 117, 29);
		menuCard.add(btnShowDeposit);
		
		JButton btnShowBalance = new JButton("Balance");
		btnShowBalance.setBackground(new Color(47, 79, 79));
		btnShowBalance.addActionListener(this);
		btnShowBalance.setActionCommand(QUERY_PANEL);
		btnShowBalance.setBounds(6, 165, 117, 29);
		menuCard.add(btnShowBalance);
		
		JButton btnShowTransfer = new JButton("Transfer");
		btnShowTransfer.setBackground(new Color(47, 79, 79));
		btnShowTransfer.addActionListener(this);
		btnShowTransfer.setActionCommand(TRANSFER_PANEL);
		btnShowTransfer.setBounds(6, 221, 117, 29);
		menuCard.add(btnShowTransfer);
		
		//Setup Panel Cards are enclosed in
		panel = new JPanel();
		panel.setLayout(new CardLayout(0, 0));
		panel.add(menuCard, MENU_PANEL);
		panel.add(withdrawCard, WITHDRAW_PANEL);
		panel.add(depositCard, DEPOSIT_PANEL);
		panel.add(queryCard, QUERY_PANEL);
		panel.add(transferCard, TRANSFER_PANEL);
		
		//WithDrawl Card Details
		JLabel lblTestLabel = new JLabel("Account #");
		lblTestLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTestLabel.setBounds(16, 23, 75, 16);
		withdrawCard.add(lblTestLabel);
		
		textField = new JTextField();
		textField.setBounds(93, 14, 141, 35);
		withdrawCard.add(textField);
		textField.setColumns(10);
		
		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAmount.setBounds(16, 81, 75, 16);
		withdrawCard.add(lblAmount);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(93, 72, 141, 35);
		withdrawCard.add(textField_1);
		
		JLabel lblSerial = new JLabel("Serial #");
		lblSerial.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSerial.setBounds(16, 138, 75, 16);
		withdrawCard.add(lblSerial);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(93, 129, 141, 35);
		withdrawCard.add(textField_2);
		
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setBounds(71, 176, 117, 29);
		withdrawCard.add(btnWithdraw);
		
		JButton btnWithdrawlCancel = new JButton("Cancel");
		btnWithdrawlCancel.setActionCommand(MENU_PANEL);
		btnWithdrawlCancel.addActionListener(this);
		btnWithdrawlCancel.setBounds(186, 176, 117, 29);
		withdrawCard.add(btnWithdrawlCancel);
		
		//WithDrawl Card Details
		JLabel lblCancelAcct = new JLabel("Account #");
		lblCancelAcct.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCancelAcct.setBounds(16, 23, 75, 16);
		depositCard.add(lblCancelAcct);
		
		textField = new JTextField();
		textField.setBounds(93, 14, 141, 35);
		depositCard.add(textField);
		textField.setColumns(10);
		
		JLabel lblCancelAmount = new JLabel("Amount");
		lblCancelAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCancelAmount.setBounds(16, 81, 75, 16);
		depositCard.add(lblCancelAmount);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(93, 72, 141, 35);
		depositCard.add(textField_1);
		
		JLabel lblCancelSerial = new JLabel("Serial #");
		lblCancelSerial.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCancelSerial.setBounds(16, 138, 75, 16);
		depositCard.add(lblCancelSerial);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(93, 129, 141, 35);
		depositCard.add(textField_2);
		
		JButton btnDoDeposit = new JButton("Deposit");
		btnDoDeposit.setBounds(71, 176, 117, 29);
		depositCard.add(btnDoDeposit);
		
		JButton btnDepositCancel = new JButton("Cancel");
		btnDepositCancel.setActionCommand(MENU_PANEL);
		btnDepositCancel.addActionListener(this);
		btnDepositCancel.setBounds(186, 176, 117, 29);
		depositCard.add(btnDepositCancel);
		
		//WithDrawl Card Details
		JLabel lblQueryAcct = new JLabel("Account #");
		lblQueryAcct.setHorizontalAlignment(SwingConstants.RIGHT);
		lblQueryAcct.setBounds(16, 23, 75, 16);
		queryCard.add(lblQueryAcct);
		
		textField = new JTextField();
		textField.setBounds(93, 14, 141, 35);
		queryCard.add(textField);
		textField.setColumns(10);
		
		JLabel lblQueryAmount = new JLabel("Amount");
		lblQueryAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblQueryAmount.setBounds(16, 81, 75, 16);
		queryCard.add(lblQueryAmount);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(93, 72, 141, 35);
		queryCard.add(textField_1);
		
		JLabel lblQuerySerial = new JLabel("Serial #");
		lblQuerySerial.setHorizontalAlignment(SwingConstants.RIGHT);
		lblQuerySerial.setBounds(16, 138, 75, 16);
		queryCard.add(lblQuerySerial);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(93, 129, 141, 35);
		queryCard.add(textField_2);
		
		JButton btnDoQuery = new JButton("Get Balance");
		btnDoQuery.setBounds(71, 176, 117, 29);
		queryCard.add(btnDoQuery);
		
		JButton btnQueryCancel = new JButton("Cancel");
		btnQueryCancel.setActionCommand(MENU_PANEL);
		btnQueryCancel.addActionListener(this);
		btnQueryCancel.setBounds(186, 176, 117, 29);
		queryCard.add(btnQueryCancel);
		
		//WithDrawl Card Details
		JLabel lblXferAcct = new JLabel("Account #");
		lblXferAcct.setHorizontalAlignment(SwingConstants.RIGHT);
		lblXferAcct.setBounds(16, 23, 75, 16);
		transferCard.add(lblXferAcct);
		
		textField = new JTextField();
		textField.setBounds(93, 14, 141, 35);
		transferCard.add(textField);
		textField.setColumns(10);
		
		JLabel lblXferAmount = new JLabel("Amount");
		lblXferAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblXferAmount.setBounds(16, 81, 75, 16);
		transferCard.add(lblXferAmount);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(93, 72, 141, 35);
		transferCard.add(textField_1);
		
		JLabel lblXferSerial = new JLabel("Serial #");
		lblXferSerial.setHorizontalAlignment(SwingConstants.RIGHT);
		lblXferSerial.setBounds(16, 138, 75, 16);
		transferCard.add(lblXferSerial);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(93, 129, 141, 35);
		transferCard.add(textField_2);
		
		JButton btnDoXfer = new JButton("Withdraw");
		btnDoXfer.setBounds(71, 176, 117, 29);
		transferCard.add(btnDoXfer);
		
		JButton btnXferCancel = new JButton("Cancel");
		btnXferCancel.setActionCommand(MENU_PANEL);
		btnXferCancel.addActionListener(this);
		btnXferCancel.setBounds(186, 176, 117, 29);
		transferCard.add(btnXferCancel);
		
		panel.setBounds(135, 51, 309, 221);
		frame.getContentPane().add(panel);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		CardLayout cl = (CardLayout) (panel.getLayout());
		cl.show(panel, e.getActionCommand());
	}
}
