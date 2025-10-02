package app.views.subFrame;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import DBConnection.EmployeeDAO;
import model.Employee;


public class EmployeeList extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JPanel panel_1;
	private DefaultTableModel tableModel;
	private JPanel panel_2;
	private JScrollPane scrollPane;
	private JTable table;
	private JPanel panel_4;
	private JPanel paginationPanel;
	private EmployeeDAO _employeeDAO;
	private JButton firstPageBtn;
	private JButton prevBtn;
	private JButton nextBtn;
	private JComboBox rowNumbCbb;
	private JButton lastPageBtn;
	private JTextField showPage;
	private JPanel actionPanel;
	private JButton reLoadEmployee;
	private JButton addBtn;
	private JButton editBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(() -> {
			try {
				var frame = new EmployeeList();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EmployeeList() {
		setTitle("Employee List");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1319, 622);
		getContentPane().setLayout(new GridLayout(0, 2, 0, 0));

		panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Employees Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_1);
		var gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0};
		panel_1.setLayout(gbl_panel_1);

		scrollPane = new JScrollPane();
		var gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		gbc_scrollPane.gridwidth = 1;
		gbc_scrollPane.gridheight = 3; // span 3 rows
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.weightx = 1.0;
		gbc_scrollPane.weighty = 1.0;
		panel_1.add(scrollPane, gbc_scrollPane);

		//create table show Employee
		String[] columnNames = {"ID", "Name", "Role", "Phone", "Email", "Date of Birth", "Gender", "Work Type"};
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);

		panel_4 = new JPanel();
		var gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 4;
		panel_1.add(panel_4, gbc_panel_4);
		panel_4.setLayout(new GridLayout(2, 2, 0, 0));

		paginationPanel = new JPanel();
		panel_4.add(paginationPanel);
		var gbl_paginationPanel = new GridBagLayout();
		gbl_paginationPanel.columnWidths = new int[] {120, 120, 120, 120, 120};
		gbl_paginationPanel.rowHeights = new int[]{30, 30, 0};
		gbl_paginationPanel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0};
		gbl_paginationPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		paginationPanel.setLayout(gbl_paginationPanel);

		firstPageBtn = new JButton("First Page");
		var gbc_firstPageBtn = new GridBagConstraints();
		gbc_firstPageBtn.fill = GridBagConstraints.BOTH;
		gbc_firstPageBtn.insets = new Insets(0, 0, 5, 5);
		gbc_firstPageBtn.gridx = 0;
		gbc_firstPageBtn.gridy = 0;
		paginationPanel.add(firstPageBtn, gbc_firstPageBtn);

		prevBtn = new JButton("Previous");
		var gbc_prevBtn = new GridBagConstraints();
		gbc_prevBtn.fill = GridBagConstraints.BOTH;
		gbc_prevBtn.insets = new Insets(0, 0, 5, 5);
		gbc_prevBtn.gridx = 1;
		gbc_prevBtn.gridy = 0;
		paginationPanel.add(prevBtn, gbc_prevBtn);

		rowNumbCbb = new JComboBox();
		rowNumbCbb.setModel(new DefaultComboBoxModel(new String[] {"5", "10", "15", "20"}));
		rowNumbCbb.setEditable(true);
		var gbc_rowNumbCbb = new GridBagConstraints();
		gbc_rowNumbCbb.fill = GridBagConstraints.BOTH;
		gbc_rowNumbCbb.insets = new Insets(0, 0, 5, 5);
		gbc_rowNumbCbb.gridx = 2;
		gbc_rowNumbCbb.gridy = 0;
		paginationPanel.add(rowNumbCbb, gbc_rowNumbCbb);

		nextBtn = new JButton("Next");
		var gbc_nextBtn = new GridBagConstraints();
		gbc_nextBtn.fill = GridBagConstraints.BOTH;
		gbc_nextBtn.insets = new Insets(0, 0, 5, 5);
		gbc_nextBtn.gridx = 3;
		gbc_nextBtn.gridy = 0;
		paginationPanel.add(nextBtn, gbc_nextBtn);

		lastPageBtn = new JButton("Last Page");
		var gbc_lastPageBtn = new GridBagConstraints();
		gbc_lastPageBtn.fill = GridBagConstraints.BOTH;
		gbc_lastPageBtn.insets = new Insets(0, 0, 5, 0);
		gbc_lastPageBtn.gridx = 4;
		gbc_lastPageBtn.gridy = 0;
		paginationPanel.add(lastPageBtn, gbc_lastPageBtn);

		showPage = new JTextField();
		showPage.setHorizontalAlignment(SwingConstants.CENTER);
		showPage.setText("1");
		var gbc_showPage = new GridBagConstraints();
		gbc_showPage.insets = new Insets(0, 0, 0, 5);
		gbc_showPage.fill = GridBagConstraints.BOTH;
		gbc_showPage.gridx = 2;
		gbc_showPage.gridy = 1;
		paginationPanel.add(showPage, gbc_showPage);
		showPage.setColumns(10);

		actionPanel = new JPanel();
		panel_4.add(actionPanel);
		var gbl_actionPanel = new GridBagLayout();
		gbl_actionPanel.columnWidths = new int[] {120, 120, 120, 120, 120};
		gbl_actionPanel.rowHeights = new int[] {30, 30, 0};
		gbl_actionPanel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0};
		gbl_actionPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		actionPanel.setLayout(gbl_actionPanel);

		reLoadEmployee = new JButton("Refresh");
		var gbc_reLoadEmployee = new GridBagConstraints();
		gbc_reLoadEmployee.fill = GridBagConstraints.BOTH;
		gbc_reLoadEmployee.insets = new Insets(0, 0, 5, 5);
		gbc_reLoadEmployee.gridx = 0;
		gbc_reLoadEmployee.gridy = 0;
		actionPanel.add(reLoadEmployee, gbc_reLoadEmployee);

		addBtn = new JButton("Add Employee");
		var gbc_addBtn = new GridBagConstraints();
		gbc_addBtn.fill = GridBagConstraints.BOTH;
		gbc_addBtn.insets = new Insets(0, 0, 5, 5);
		gbc_addBtn.gridx = 1;
		gbc_addBtn.gridy = 0;
		actionPanel.add(addBtn, gbc_addBtn);

		editBtn = new JButton("Edit Employee");
		var gbc_editBtn = new GridBagConstraints();
		gbc_editBtn.fill = GridBagConstraints.BOTH;
		gbc_editBtn.insets = new Insets(0, 0, 5, 5);
		gbc_editBtn.gridx = 2;
		gbc_editBtn.gridy = 0;
		actionPanel.add(editBtn, gbc_editBtn);

		panel_2 = new JPanel();
		panel.add(panel_2);


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
