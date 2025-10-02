package app.views;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
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

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtName, txtRole, txtPhone, txtEmail;
	private JTextField txtDob;
	private JRadioButton rbMale, rbFemale;
	private JComboBox<String> cbWorkType;
	private boolean succeeded;
	private Employee employee;

	public EmployeeFormDialog(Frame parent, Employee emp) {
		super(parent, true);
		setTitle(emp == null ? "Add Employee" : "Edit Employee");
		setSize(400, 300);
		setLocationRelativeTo(parent);
		setLayout(new GridLayout(8, 2, 5, 5));

		add(new JLabel("Name:"));
		txtName = new JTextField();
		add(txtName);

		add(new JLabel("Role:"));
		txtRole = new JTextField();
		add(txtRole);

		add(new JLabel("Phone:"));
		txtPhone = new JTextField();
		add(txtPhone);

		add(new JLabel("Email:"));
		txtEmail = new JTextField();
		add(txtEmail);

		add(new JLabel("Date of Birth (yyyy-MM-dd):"));
		txtDob = new JTextField();
		add(txtDob);

		add(new JLabel("Gender:"));
		var genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rbMale = new JRadioButton("Male");
		rbFemale = new JRadioButton("Female");
		var group = new ButtonGroup();
		group.add(rbMale);
		group.add(rbFemale);
		genderPanel.add(rbMale);
		genderPanel.add(rbFemale);
		add(genderPanel);

		add(new JLabel("Work Type:"));
		cbWorkType = new JComboBox<>(new String[]{"Full-time", "Part-time"});
		add(cbWorkType);

		var btnOk = new JButton("OK");
		var btnCancel = new JButton("Cancel");
		add(btnOk);
		add(btnCancel);

		if (emp != null) {
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

		btnOk.addActionListener(e -> {
			try {
				var name = txtName.getText();
				var role = txtRole.getText();
				var phone = txtPhone.getText();
				var email = txtEmail.getText();
				var dob = txtDob.getText().isBlank() ? null : Date.valueOf(txtDob.getText());
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
				succeeded = true;
				dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Invalid data: " + ex.getMessage());
			}
		});

		btnCancel.addActionListener(e -> {
			succeeded = false;
			dispose();
		});
	}

	public boolean isSucceeded() {
		return succeeded;
	}

	public Employee getEmployee() {
		return employee;
	}
}
