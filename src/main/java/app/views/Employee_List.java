package app.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import DBConnection.EmployeeDAO;
import model.Employee;

public class Employee_List extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel tableModel;
	private EmployeeDAO _employeeDAO;
	private JTable table;
	private JButton btnAdd;
	private JButton btnEdit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				var frame = new Employee_List();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Employee_List() {
		setTitle("Employee List");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 400);

		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// Table
		String[] columnNames = {"Name", "Role", "Phone", "Email", "Date of Birth", "Gender", "Work Type"};
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);

		var scrollPanel = new JScrollPane(table);
		contentPane.add(scrollPanel, BorderLayout.CENTER);

		// Buttons panel
		var panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnAdd = new JButton("Add Employee");
		btnEdit = new JButton("Edit Employee");
		panelButtons.add(btnAdd);
		panelButtons.add(btnEdit);

		contentPane.add(panelButtons, BorderLayout.SOUTH);

		// DAO
		_employeeDAO = new EmployeeDAO();
		loadEmployees();
	}

	private void loadEmployees() {
		var employees = _employeeDAO.getAllEmployees();

		for (Employee emp : employees) {
			var genderText = emp.is_gender() ? "Male" : "Female";
			var workTypeText = emp.is_work_type() ? "Full-time" : "Part-time";

			tableModel.addRow(new Object[]{
					emp.get_name(),
					emp.get_role(),
					emp.get_phone(),
					emp.get_email(),
					emp.get_dob() != null ? emp.get_dob().toString() : "Not provided",
							genderText,
							workTypeText
			});
		}
	}
}
