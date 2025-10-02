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

	private static final String ADMIN_PROFILE_CARD = "AdminProfile";
	private static final String EMPLOYEE_CARD = "EmployeeList";
	private static final String DEFAULT_CARD = "Home";

	private JPanel panelCenter;

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

	public MainFrame() {
		setTitle("Manage Employee");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 920, 540);

		// ===== Menu Bar =====
		var menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Admin Menu
		var adminMenu = new JMenu("Admin");
		menuBar.add(adminMenu);

		var adminProfileBtn = new JMenuItem("Profile");
		adminProfileBtn.addActionListener(this::adminProfileBtnActionPerformed);
		adminMenu.add(adminProfileBtn);

		adminMenu.add(new JSeparator());

		var logoutBtn = new JMenuItem("Log out");
		logoutBtn.addActionListener(this::logoutBtnActionPerformed);
		adminMenu.add(logoutBtn);

		// Employee Menu
		var employeeMenu = new JMenu("Employee");
		menuBar.add(employeeMenu);

		var employeeBtn = new JMenuItem("Manage Employee");
		employeeBtn.addActionListener(this::employeeBtnActionPerformed);
		employeeMenu.add(employeeBtn);

		//		// Restaurant Menu
		//		var restaurantMenu = new JMenu("Restaurant");
		//		menuBar.add(restaurantMenu);
		//
		//		var restaurantBtn = new JMenuItem("Manage Restaurant");
		//		restaurantBtn.addActionListener(this::restaurantBtnActionPerformed);
		//		restaurantMenu.add(restaurantBtn);

		panelCenter = new JPanel(new CardLayout());
		panelCenter.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelCenter);

		var defaultPanel = new JPanel();
		panelCenter.add(defaultPanel, DEFAULT_CARD);

		var adminProfilePanel = new AdminProfile();
		panelCenter.add(adminProfilePanel, ADMIN_PROFILE_CARD);

		var employeePanel = new EmployeeList();
		panelCenter.add(employeePanel, EMPLOYEE_CARD);

		//		var restaurantPanel = new RestaurantList(); //thêm
		//		panelCenter.add(restaurantPanel, RESTAURANT_CARD);

		showPanel(DEFAULT_CARD);
		showPanel(DEFAULT_CARD);
	}

	private void showPanel(String cardName) {
		var layout = (CardLayout) panelCenter.getLayout();
		layout.show(panelCenter, cardName);
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
		showPanel(ADMIN_PROFILE_CARD);
	}

	protected void employeeBtnActionPerformed(ActionEvent e) {
		showPanel(EMPLOYEE_CARD);
	}

	protected void logoutBtnActionPerformed(ActionEvent e) {
		dispose();

	}
}
