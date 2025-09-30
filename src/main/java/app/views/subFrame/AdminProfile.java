package app.views.subFrame;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JInternalFrame;

public class AdminProfile extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnNewButton;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				var frame = new AdminProfile();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminProfile() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		btnNewButton = new JButton("Test");
		btnNewButton.setBounds(141, 113, 89, 23);
		getContentPane().add(btnNewButton);

		btnNewButton_1 = new JButton("test 2");
		btnNewButton_1.setBounds(96, 41, 89, 23);
		getContentPane().add(btnNewButton_1);

	}
}
