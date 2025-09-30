package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlServerConnection {

	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=Staff_Timekeeping_Managment_2;encrypt=true;trustServerCertificate=true;";
	private static final String USER = "sa";
	private static final String PASSWORD = "123";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static void main(String[] args) {
		try (var conn = getConnection()) {
			if (conn != null) {
				System.out.println("Connected to SQL Server successfully!");
			}
		} catch (SQLException e) {
			System.out.println("Connection error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
