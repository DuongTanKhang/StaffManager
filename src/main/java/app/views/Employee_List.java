package app.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
		btnAdd.addActionListener(e -> {
			var dialog = new EmployeeFormDialog(this, null);
			dialog.setVisible(true);
			if (dialog.isSucceeded()) {
				try {
					_employeeDAO.addEmployee(dialog.getEmployee());
					tableModel.setRowCount(0); // clear
					loadEmployees();
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "Error adding employee: " + ex.getMessage());
				}
			}
		});

		btnEdit.addActionListener(e -> {
			var row = table.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(this, "Please select an employee to edit!");
				return;
			}

			var name = (String) tableModel.getValueAt(row, 0);
			var role = (String) tableModel.getValueAt(row, 1);
			var phone = (String) tableModel.getValueAt(row, 2);
			var email = (String) tableModel.getValueAt(row, 3);
			var dobStr = (String) tableModel.getValueAt(row, 4);
			var genderStr = (String) tableModel.getValueAt(row, 5);
			var workTypeStr = (String) tableModel.getValueAt(row, 6);

			var emp = new Employee(
					0, // TODO: bạn cần lấy id từ DB hoặc giữ hidden column trong table
					name,
					role,
					phone,
					email,
					dobStr.equals("Not provided") ? null : java.sql.Date.valueOf(dobStr),
							genderStr.equals("Male"),
							workTypeStr.equals("Full-time")
					);

			var dialog = new EmployeeFormDialog(this, emp);
			dialog.setVisible(true);
			if (dialog.isSucceeded()) {
				try {
					_employeeDAO.updateEmployee(dialog.getEmployee());
					tableModel.setRowCount(0);
					loadEmployees();
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "Error updating employee: " + ex.getMessage());
				}
			}
		});
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
