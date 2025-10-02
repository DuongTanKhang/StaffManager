package app.views.subFrame;

import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import DBConnection.EmployeeDAO;
import app.views.EmployeeFormDialog;
import model.Employee;

public class EmployeeList extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;
	private EmployeeDAO employeeDAO;

	public EmployeeList() {
		setLayout(new BorderLayout());

		employeeDAO = new EmployeeDAO();

		// ==== Table ====
		model = new DefaultTableModel(
				new Object[]{"ID", "Name", "Role", "Phone", "Email", "DOB", "Gender", "Work Type"}, 0
				) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(model);
		table.setRowHeight(22);
		add(new JScrollPane(table), BorderLayout.CENTER);

		// ==== Toolbar ====
		var toolbar = new JToolBar();
		var addBtn = new JButton("Add");
		var editBtn = new JButton("Edit");
		var deleteBtn = new JButton("Delete");

		toolbar.add(addBtn);
		toolbar.add(editBtn);
		toolbar.add(deleteBtn);
		add(toolbar, BorderLayout.NORTH);

		reloadEmployees();

		// ==== Action buttons ====
		addBtn.addActionListener(e -> {
			try { onAdd(); } catch (SQLException ex) { showError(ex); }
		});
		editBtn.addActionListener(e -> {
			try { onEdit(); } catch (SQLException ex) { showError(ex); }
		});
		deleteBtn.addActionListener(e -> {
			try { onDelete(); } catch (SQLException ex) { showError(ex); }
		});
	}

	private void reloadEmployees() {
		model.setRowCount(0);
		var employees = employeeDAO.getAllEmployees();
		for (Employee emp : employees) {
			model.addRow(new Object[]{
					emp.get_id(),
					emp.get_name(),
					emp.get_role(),
					emp.get_phone(),
					emp.get_email(),
					emp.get_dob(),
					emp.is_gender() ? "Male" : "Female",
							emp.is_work_type() ? "Full-time" : "Part-time"
			});
		}
	}

	private void onAdd() throws SQLException {
		var parentFrame = (java.awt.Frame) SwingUtilities.getWindowAncestor(this);
		var dialog = new EmployeeFormDialog(parentFrame, null);
		dialog.setVisible(true);
		if (dialog.isSucceeded()) {
			employeeDAO.addEmployee(dialog.getEmployee());
			reloadEmployees();
		}
	}

	private void onEdit() throws SQLException {
		var row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(this, "Select an employee to edit!");
			return;
		}
		var id = (int) model.getValueAt(row, 0);
		var emp = employeeDAO.getById(id);
		if (emp == null) {
			JOptionPane.showMessageDialog(this, "Employee not found!");
			return;
		}

		var parentFrame = (java.awt.Frame) SwingUtilities.getWindowAncestor(this);
		var dialog = new EmployeeFormDialog(parentFrame, emp);
		dialog.setVisible(true);
		if (dialog.isSucceeded()) {
			employeeDAO.updateEmployee(dialog.getEmployee());
			reloadEmployees();
		}
	}

	private void onDelete() throws SQLException {
		var row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(this, "Select an employee to delete!");
			return;
		}
		var id = (int) model.getValueAt(row, 0);
		if (JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			employeeDAO.deleteEmployee(id);
			reloadEmployees();
		}
	}

	private void showError(Exception ex) {
		ex.printStackTrace();
		JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
				"Database Error", JOptionPane.ERROR_MESSAGE);
	}
}
