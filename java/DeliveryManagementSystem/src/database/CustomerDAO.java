package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Customer;

/**
 * Class: ClienteDAO
 *
 * Description:
 * Manages Cliente data access and persistence.
 *
 * Responsibilities:
 * - Connect to the database
 * - Perform CRUD operations for Cliente
 *
 * @author Rodrigo
 * @since 21-04-2026
 */

public class CustomerDAO {
	
	/**
	 * Inserts a new cliente into the database.
	 * @param conn database connection
	 * @param customer cliente object
	 */
	public boolean addCustomer(Connection conn, Customer customer) {
		String sqlQuery = "INSERT INTO customer ("
				+ "customer_id_pk, "
				+ "first_name, "
				+ "middle_name, "
				+ "last_name, "
				+ "phone, "
				+ "email, "
				+ "passcode) VALUES (?, ?, ?, ?, ?, ?, ?)";
		
			// prepare statement
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind parameters
			stmt.setString(1, customer.getId());
			stmt.setString(2, customer.getFirstName());
			stmt.setString(3, customer.getMiddleName());
			stmt.setString(4, customer.getLastName());
			stmt.setString(5, customer.getPhone());
			stmt.setString(6, customer.getEmail());
			stmt.setString(7, customer.getPasscode());
			
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
			
		} catch (SQLException e) {
			System.err.println("Error in customer adding operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Retrieves a cliente by CPF from the database.
	 * @param conn database connection
	 * @param id cliente CPF
	 * @return cliente object or null if not found
	 */
	public Customer returnCustomer(Connection conn, String id) {
		String sqlQuery = "SELECT * FROM customer WHERE customer_id_pk = ?";
		
			// prepare statement
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind parameters
			stmt.setString(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			// if a result is found, populate a Cliente object
			if (result.next()) {
				Customer customer = new Customer();
							
				customer.setId(result.getString("customer_id_pk"));
				customer.setFirstName(result.getString("first_name"));
				customer.setMiddleName(result.getString("middle_name"));
				customer.setLastName(result.getString("last_name"));
				customer.setPhone(result.getString("phone"));
				customer.setEmail(result.getString("email"));
				customer.setPasscode(result.getString("passcode"));
				
				return customer;

			}
									
		} catch (SQLException e) {
			System.err.println("Error in customer querying operation.");
		    e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Updates an existing cliente's information.
	 * @param conn database connection
	 * @param customer cliente object
	 */
	public boolean updateCustomer(Connection conn, Customer customer) {
		String sqlQuery = "UPDATE customer SET " +
	            "first_name = ?, " +
	            "middle_name = ?, " +
	            "last_name = ?, " +
	            "phone = ?, " +
	            "email = ?, " +
	            "passcode = ? " +
	            "WHERE customer_id_pk = ?";

			// prepare statement
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind parameters
			stmt.setString(1, customer.getFirstName());
	        stmt.setString(2, customer.getMiddleName());
	        stmt.setString(3, customer.getLastName());
	        stmt.setString(4, customer.getPhone());
	        stmt.setString(5, customer.getEmail());
	        stmt.setString(6, customer.getPasscode());
	        stmt.setString(7, customer.getId());
						
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
			
		} catch (SQLException e) {
			System.err.println("Error in customer updating operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Deletes a cliente by CPF.
	 * @param conn database connection
	 * @param id cliente CPF
	 * @return true if deletion succeeded
	 */
	public boolean deleteCustomer(Connection conn, String id) {
		String sqlQuery = "DELETE FROM customer WHERE customer_id_pk = ?";
		
			// prepare statement
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind parameters
			stmt.setString(1, id);
			
			// execute update and check success
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;

		} catch (SQLException e) {
			System.err.println("Error in customer deleting operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
}
