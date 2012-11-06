package cs5414.bank.gui.faildetect;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import cs5414.bank.gui.faildetect.Constants;
import cs5414.bank.gui.GUIBase;
import cs5414.bank.gui.servers.ReplyReceiverServer;
import cs5414.bank.message.BaseMessage;
import cs5414.bank.message.FailureDetectorMessage;
import cs5414.bank.network.MessageSenderClient;
import cs5414.bank.network.NetworkInfo;

public class FailGUI extends GUIBase implements ActionListener {
	private JTextField nodeName;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		final String gui_name = args[0];
		final String nameFile = args[1];
		final String topoFile = args[2];
		System.err.println("FailGUI started");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FailGUI window = new FailGUI(gui_name, nameFile, topoFile);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FailGUI(String my_name, String nameFile, String topoFile) {
		super(my_name, nameFile, topoFile);
		FailureDetectorListener serv = new FailureDetectorListener(my_name, net);
		serv.start();
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(UIManager.getColor("Desktop.background"));
		frame.getContentPane().setLayout(null);	
		
		//Setup main GUI settings, title, etc
		JLabel lblCsBank = new JLabel("CS5414 Failure Detector");
		lblCsBank.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblCsBank.setBounds(6, 6, 373, 33);
		frame.getContentPane().add(lblCsBank);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(3,2));
		panel.setBounds(135, 51, 309, 221);
		
		JLabel lblCancelAcct = new JLabel("Node Name");
		lblCancelAcct.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCancelAcct.setBounds(16, 23, 75, 16);
		panel.add(lblCancelAcct);
		
		nodeName = new JTextField();
		nodeName.setBounds(93, 14, 141, 35);
		panel.add(nodeName);
		nodeName.setColumns(10);
		
		JButton btnDoDeposit = new JButton("Failed");
		btnDoDeposit.setActionCommand(Constants.DO_FAILED);
		btnDoDeposit.addActionListener(this);
		btnDoDeposit.setBounds(71, 176, 117, 29);
		panel.add(btnDoDeposit);
		
		JButton btnDepositCancel = new JButton("Recovered");
		btnDepositCancel.setActionCommand(Constants.DO_RECOVERED);
		btnDepositCancel.addActionListener(this);
		btnDepositCancel.setBounds(186, 176, 117, 29);
		panel.add(btnDepositCancel);	
		
		//Add panel to frame
		frame.getContentPane().add(panel);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (Constants.DO_FAILED == e.getActionCommand()) {
			String node = nodeName.getText();
			FailureDetectorMessage message = new FailureDetectorMessage();
			message.source = name;
			message.destination = name;
			message.serial = getUID();
			message.node = node;
			MessageSenderClient testClient = new MessageSenderClient(name, net);
			testClient.sendMessage(message);
			
			nodeName.setText(null);
		} else if (Constants.DO_RECOVERED == e.getActionCommand()) {
			
		}
	}
	
	private class FailureDetectorListener extends ReplyReceiverServer {

		public FailureDetectorListener(String name, NetworkInfo net) {
			super(name, net);
		}
		
		protected void processMessage(BaseMessage message) {
			
		}
		
	}
}
