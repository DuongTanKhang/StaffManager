package DBConnection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlServerConnection {
	public static void main(String[] args) {
		var url = "jdbc:sqlserver://localhost:1433;databaseName=Staff_Timekeeping_Managment_2;encrypt=true;trustServerCertificate=true;";
		var user = "sa";
		var password = "123456";

		try (var conn = DriverManager.getConnection(url, user, password)) {
			if (conn != null) {
				System.out.println("Kết nối SQL Server thành công!");
			}
		} catch (SQLException e) {
			System.out.println("Lỗi kết nối: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
