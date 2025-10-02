package app.views;


import java.awt.CardLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import app.views.subFrame.AdminProfile;
import app.views.subFrame.EmployeeList;


public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panelCenter;
	private JMenuBar menuBar;
	private JMenu adminMenu;
	private JMenu mnNewMenu_1;
	private JMenuItem adminProfileBtn;
	private JSeparator separator;
	private JMenuItem logoutBtn;
	private JMenuItem employeeBtn;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				var frame = new MainFrame();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Manage Employee");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 920, 540);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		adminMenu = new JMenu("Admin");
		menuBar.add(adminMenu);

		adminProfileBtn = new JMenuItem("Profile");
		adminProfileBtn.addActionListener(this::adminProfileBtnActionPerformed);
		adminMenu.add(adminProfileBtn);

		separator = new JSeparator();
		adminMenu.add(separator);

		logoutBtn = new JMenuItem("Log out");
		adminMenu.add(logoutBtn);

		mnNewMenu_1 = new JMenu("Employee");
		menuBar.add(mnNewMenu_1);

		employeeBtn = new JMenuItem("Manage Employee");
		employeeBtn.addActionListener(this::employeeBtnActionPerformed);
		mnNewMenu_1.add(employeeBtn);

		panelCenter = new JPanel();
		panelCenter.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelCenter);
		panelCenter.setLayout(new CardLayout(0, 0));

		panel = new JPanel();
		panelCenter.add(panel, "name_82586991688700");
		panel.setLayout(null);

		var adminProfilePanel = new AdminProfile();
		adminProfilePanel.setTitle("Admin");
		panelCenter.add(adminProfilePanel,"adminProfilePanel");

		//		var employeePanel = new EmployeeList();
		//		employeePanel.setTitle("Employee");

		var employeePanel_1 = new EmployeeList();
		employeePanel_1.setTitle("Employee_1");
		panelCenter.add(employeePanel_1,"employee_1");


	}


	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	protected void adminProfileBtnActionPerformed(ActionEvent e) {
		var layout = (CardLayout)panelCenter.getLayout();
		layout.show(panelCenter,"adminProfilePanel");

	}
	protected void employeeBtnActionPerformed(ActionEvent e) {
		var layout = (CardLayout)panelCenter.getLayout();
		layout.show(panelCenter, "employee_1");
	}
}
