package app.views;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import model.Employee;

public class EmployeeFormDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField txtName, txtRole, txtPhone, txtEmail, txtDob;
	private JRadioButton rbMale, rbFemale;
	private JComboBox<String> cbWorkType;
	private boolean succeeded;
	private Employee employee;

	public EmployeeFormDialog(Frame parent, Employee emp) {
		super(parent, true);
		setTitle(emp == null ? "Add Employee" : "Edit Employee");
		setSize(420, 320);
		setLocationRelativeTo(parent);
		setLayout(new GridBagLayout());

		var gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		var row = 0;

		// Name
		gbc.gridx = 0; gbc.gridy = row;
		add(new JLabel("Name:"), gbc);
		gbc.gridx = 1;
		txtName = new JTextField(20);
		add(txtName, gbc);

		// Role
		gbc.gridx = 0; gbc.gridy = ++row;
		add(new JLabel("Role:"), gbc);
		gbc.gridx = 1;
		txtRole = new JTextField();
		add(txtRole, gbc);

		// Phone
		gbc.gridx = 0; gbc.gridy = ++row;
		add(new JLabel("Phone:"), gbc);
		gbc.gridx = 1;
		txtPhone = new JTextField();
		add(txtPhone, gbc);

		// Email
		gbc.gridx = 0; gbc.gridy = ++row;
		add(new JLabel("Email:"), gbc);
		gbc.gridx = 1;
		txtEmail = new JTextField();
		add(txtEmail, gbc);

		// Dob
		gbc.gridx = 0; gbc.gridy = ++row;
		add(new JLabel("Date of Birth (yyyy-MM-dd):"), gbc);
		gbc.gridx = 1;
		txtDob = new JTextField();
		add(txtDob, gbc);

		// Gender
		gbc.gridx = 0; gbc.gridy = ++row;
		add(new JLabel("Gender:"), gbc);
		gbc.gridx = 1;
		var genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		rbMale = new JRadioButton("Male");
		rbFemale = new JRadioButton("Female");
		var group = new ButtonGroup();
		group.add(rbMale);
		group.add(rbFemale);
		genderPanel.add(rbMale);
		genderPanel.add(rbFemale);
		add(genderPanel, gbc);

		// Work type
		gbc.gridx = 0; gbc.gridy = ++row;
		add(new JLabel("Work Type:"), gbc);
		gbc.gridx = 1;
		cbWorkType = new JComboBox<>(new String[]{"Full-time", "Part-time"});
		add(cbWorkType, gbc);

		// Buttons
		gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		var btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		var btnOk = new JButton("OK");
		var btnCancel = new JButton("Cancel");
		btnPanel.add(btnOk);
		btnPanel.add(btnCancel);
		add(btnPanel, gbc);

		// Nếu sửa thì bind data
		if (emp != null) {
			bindData(emp);
		}

		// OK
		btnOk.addActionListener(e -> {
			if (collectData()) {
				succeeded = true;
				dispose();
			}
		});

		// Cancel
		btnCancel.addActionListener(e -> {
			succeeded = false;
			dispose();
		});
	}

	/** Hiển thị data của employee lên form */
	private void bindData(Employee emp) {
		employee = emp;
		txtName.setText(emp.get_name());
		txtRole.setText(emp.get_role());
		txtPhone.setText(emp.get_phone());
		txtEmail.setText(emp.get_email());
		if (emp.get_dob() != null) {
			txtDob.setText(emp.get_dob().toString());
		}
		if (emp.is_gender()) {
			rbMale.setSelected(true);
		} else {
			rbFemale.setSelected(true);
		}
		cbWorkType.setSelectedIndex(emp.is_work_type() ? 0 : 1);
	}

	/** Lấy data từ form -> Employee object */
	private boolean collectData() {
		try {
			var name = txtName.getText().trim();
			var role = txtRole.getText().trim();
			var phone = txtPhone.getText().trim();
			var email = txtEmail.getText().trim();
			var dobText = txtDob.getText().trim();

			if (name.isEmpty() || phone.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Name and Phone cannot be empty!");
				return false;
			}

			var dob = dobText.isBlank() ? null : Date.valueOf(dobText);
			var gender = rbMale.isSelected();
			var workType = cbWorkType.getSelectedIndex() == 0;

			if (employee == null) {
				employee = new Employee(0, name, role, phone, email, dob, gender, workType);
			} else {
				employee.set_name(name);
				employee.set_role(role);
				employee.set_phone(phone);
				employee.set_email(email);
				employee.set_dob(dob);
				employee.set_gender(gender);
				employee.set_work_type(workType);
			}
			return true;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Invalid data: " + ex.getMessage());
			return false;
		}
	}

	public boolean isSucceeded() {
		return succeeded;
	}

	public Employee getEmployee() {
		return employee;
	}
}
