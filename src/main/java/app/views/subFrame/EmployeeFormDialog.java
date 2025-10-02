package app.views.subFrame;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.sql.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.Employee;

public class EmployeeFormDialog extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtName, txtRole, txtPhone, txtEmail, txtDob;
	private JRadioButton rbMale, rbFemale;
	private JComboBox<String> cbWorkType;
	private boolean succeeded;
	private Employee employee;

	public EmployeeFormDialog(Frame parentFrame, Employee emp) {
		super(parentFrame, true);
		setTitle(emp == null ? "Add Employee" : "Edit Employee");
		setSize(400, 320);
		setLocationRelativeTo(parentFrame);
		setLayout(new GridLayout(8, 2, 5, 5));

		// ==== Name ====
		add(new JLabel("Name:"));
		txtName = new JTextField();
		add(txtName);

		// ==== Role ====
		add(new JLabel("Role:"));
		txtRole = new JTextField();
		add(txtRole);

		// ==== Phone ====
		add(new JLabel("Phone:"));
		txtPhone = new JTextField();
		add(txtPhone);

		// ==== Email ====
		add(new JLabel("Email:"));
		txtEmail = new JTextField();
		add(txtEmail);

		// ==== DOB ====
		add(new JLabel("Date of Birth (yyyy-MM-dd):"));
		txtDob = new JTextField();
		add(txtDob);

		// ==== Gender ====
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

		// ==== Work Type ====
		add(new JLabel("Work Type:"));
		cbWorkType = new JComboBox<>(new String[]{"Full-time", "Part-time"});
		add(cbWorkType);

		// ==== Buttons ====
		var btnOk = new JButton("OK");
		var btnCancel = new JButton("Cancel");
		add(btnOk);
		add(btnCancel);

		// Prefill nếu edit
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

		// === Action buttons ===
		btnOk.addActionListener(e -> {
			try {
				var name = txtName.getText().trim();
				if (name.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Name is required!");
					return;
				}

				var role = txtRole.getText().trim();
				var phone = txtPhone.getText().trim();
				var email = txtEmail.getText().trim();
				var dob = txtDob.getText().isBlank() ? null : Date.valueOf(txtDob.getText().trim());
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

	private static Frame getWindowAncestor(JInternalFrame parent) {
		var win = SwingUtilities.getWindowAncestor(parent);
		return (win instanceof Frame f) ? f : null;
	}

	public boolean isSucceeded() {
		return succeeded;
	}

	public Employee getEmployee() {
		return employee;
	}
}
