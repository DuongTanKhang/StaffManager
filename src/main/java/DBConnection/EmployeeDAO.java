package DBConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Employee;

public class EmployeeDAO {

	public List<Employee> getAllEmployees() {
		List<Employee> list = new ArrayList<>();

		var sql = "SELECT _id, _name, _role, _phone, _email, _dob, _gender, _work_type FROM tbl_employee";

		try (var conn = SqlServerConnection.getConnection();
				var stmt = conn.prepareStatement(sql);
				var rs = stmt.executeQuery()) {

			while (rs.next()) {
				var emp = new Employee(
						rs.getInt("_id"),
						rs.getString("_name"),
						rs.getString("_role"),
						rs.getString("_phone"),
						rs.getString("_email"),
						rs.getDate("_dob"),
						rs.getBoolean("_gender"),
						rs.getBoolean("_work_type")
						);
				list.add(emp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void addEmployee(Employee emp) throws SQLException {
		var sql = "INSERT INTO tbl_employee (_name, _role, _phone, _email, _dob, _gender, _work_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (var conn = SqlServerConnection.getConnection();
				var ps = conn.prepareStatement(sql)) {

			ps.setString(1, emp.get_name());
			ps.setString(2, emp.get_role());
			ps.setString(3, emp.get_phone());
			ps.setString(4, emp.get_email());
			ps.setDate(5, emp.get_dob());
			ps.setBoolean(6, emp.is_gender());
			ps.setBoolean(7, emp.is_work_type());
			ps.executeUpdate();
		}
	}

	public void updateEmployee(Employee emp) throws SQLException {
		var sql = "UPDATE tbl_employee SET _name=?, _role=?, _phone=?, _email=?, _dob=?, _gender=?, _work_type=? WHERE _id=?";
		try (var conn = SqlServerConnection.getConnection();
				var ps = conn.prepareStatement(sql)) {

			ps.setString(1, emp.get_name());
			ps.setString(2, emp.get_role());
			ps.setString(3, emp.get_phone());
			ps.setString(4, emp.get_email());
			ps.setDate(5, emp.get_dob());
			ps.setBoolean(6, emp.is_gender());
			ps.setBoolean(7, emp.is_work_type());
			ps.setInt(8, emp.get_id());
			ps.executeUpdate();
		}
	}
}
