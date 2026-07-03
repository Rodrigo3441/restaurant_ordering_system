package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class: DatabaseConnection
 *
 * Description:
 * Manages the database connection
 *
 * Responsibilities:
 * - Connect to the database
 * - Handle exceptions on connection failure
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public class DatabaseConnection {

	private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/free_food_en";
	private static final String DATABASE_USER = "postgres";
	private static final String DATABASE_PASSWORD = "root";
	
	/**
	 * Gets a database connection
	 *
	 * @return Connection object
	 */
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException("An error occurred while connecting to the database", e);
		}
	}
}
