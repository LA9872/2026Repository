package Leonardo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class MyAdder {
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyAdder window = new MyAdder();
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
	public MyAdder() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter ONLY positive integers");
		lblNewLabel.setBounds(101, 33, 170, 25);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("X");
		lblNewLabel_1.setBounds(43, 84, 48, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Y");
		lblNewLabel_2.setBounds(43, 123, 48, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					bignumber67 x = new bignumber67(textField.getText());
					bignumber67 y = new bignumber67(textField_1.getText());
					bignumber67 z = x.add(y);
					textArea.setText(z.getValue());
				} catch (IllegalArgumentException ex) {
					textArea.setText("input error");
				}
			}
		});
		btnNewButton.setBounds(140, 146, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(90, 81, 199, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(90, 120, 199, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textArea = new JTextArea();
		textArea.setBounds(72, 180, 250, 25);
		frame.getContentPane().add(textArea);
	}
}