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

		String[] columnNames = {"ID", "Name", "Role", "Phone", "Email", "Date of Birth", "Gender", "Work Type"};
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);

		table.removeColumn(table.getColumnModel().getColumn(0));

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

		// Add button
		btnAdd.addActionListener(e -> {
			var dialog = new EmployeeFormDialog(this, null);
			dialog.setVisible(true);
			if (dialog.isSucceeded()) {
				try {
					_employeeDAO.addEmployee(dialog.getEmployee());
					reloadEmployees();
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "Error adding employee: " + ex.getMessage());
				}
			}
		});

		// Edit button
		btnEdit.addActionListener(e -> {
			var row = table.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(this, "Please select an employee to edit!");
				return;
			}

			var modelRow = table.convertRowIndexToModel(row);
			var id = (int) tableModel.getValueAt(modelRow, 0);
			var name = (String) tableModel.getValueAt(modelRow, 1);
			var role = (String) tableModel.getValueAt(modelRow, 2);
			var phone = (String) tableModel.getValueAt(modelRow, 3);
			var email = (String) tableModel.getValueAt(modelRow, 4);
			var dobStr = (String) tableModel.getValueAt(modelRow, 5);
			var genderStr = (String) tableModel.getValueAt(modelRow, 6);
			var workTypeStr = (String) tableModel.getValueAt(modelRow, 7);

			var emp = new Employee(
					id,
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
					reloadEmployees();
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "Error updating employee: " + ex.getMessage());
				}
			}
		});
	}

	private void loadEmployees() {
		var employees = _employeeDAO.getAllEmployees();

		for (Employee emp : employees) {
			var genderText = emp.is_gender() ? "Male" : "Female";
			var workTypeText = emp.is_work_type() ? "Full-time" : "Part-time";

			tableModel.addRow(new Object[]{
					emp.get_id(),
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

	private void reloadEmployees() {
		tableModel.setRowCount(0); // clear table
		loadEmployees();
	}
}
