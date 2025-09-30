package model;

import java.sql.Date;

public class Employee {

	private int _id;
	private String _name;
	private String _phone;
	private String _email;
	private String _role;
	private java.sql.Date _dob;
	private boolean _gender;
	private boolean _work_type;

	public Employee(int _id, String _name, String _role, String _phone, String _email,
			Date _dob, boolean _gender, boolean _work_type) {
		this._id = _id;
		this._name = _name;
		this._role = _role;
		this._phone = _phone;
		this._email = _email;
		this._dob = _dob;
		this._gender = _gender;
		this._work_type = _work_type;
	}
	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_phone() {
		return _phone;
	}

	public void set_phone(String _phone) {
		this._phone = _phone;
	}

	public String get_email() {
		return _email;
	}

	public void set_email(String _email) {
		this._email = _email;
	}

	public java.sql.Date get_dob() {
		return _dob;
	}

	public void set_dob(java.sql.Date _dob) {
		this._dob = _dob;
	}

	public boolean is_gender() {
		return _gender;
	}

	public void set_gender(boolean _gender) {
		this._gender = _gender;
	}

	public boolean is_work_type() {
		return _work_type;
	}

	public void set_work_type(boolean _work_type) {
		this._work_type = _work_type;
	}

	public String get_role() {
		return _role;
	}

	public void set_role(String _role) {
		this._role = _role;
	}

	@Override
	public String toString() {
		var genderText = _gender ? "Male" : "Female";
		var workTypeText = _work_type ? "Full-time" : "Part-time";

		return "Name: " + _name +
				", Role: " + _role +
				", Phone: " + _phone +
				", Email: " + _email +
				", Date of Birth: " + (_dob != null ? _dob.toString() : "Not provided") +
				", Gender: " + genderText +
				", Work Type: " + workTypeText;
	}


}
