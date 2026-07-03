package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import entities.Address;
import entities.CustomerAddress;
import entities.RestaurantAddress;

/**
 * Class: EnderecoDAO
 *
 * Description:
 * Manages client and restaurant address data.
 *
 * Responsibilities:
 * - Connect to the database
 * - Manipulate address data
 *
 * @author Rodrigo
 * @since 24-04-2026
 */

public class AddressDAO {
		
	/**
	 * Inserts a restaurant address into the database.
	 * @param conn database connection
	 * @param restaurantAddress restaurant address object
	 * @return boolean
	 */
	public boolean addRestaurantAddress(Connection conn, Address restaurantAddress) {
		String sqlQuery = "INSERT INTO restaurant_address "
				+ "(res_add_restaurant_id_pk_fk, "
				+ "res_add_postal_code_pk, "
				+ "name, "
				+ "number) VALUES (?, ?, ?, ?)";
		
		// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared query
			stmt.setString(1, restaurantAddress.getId());
			stmt.setString(2, restaurantAddress.getPostalCode());
			stmt.setString(3, restaurantAddress.getName());
			stmt.setInt(4, restaurantAddress.getNumber());
			
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
			
		} catch (SQLException e) {
			System.err.println("Error in restaurant_address adding operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Retrieves restaurant address details for use in other operations.
	 * @param conn database connection  
	 * @param id restaurant CNPJ
	 * @return restaurant address object
	 */
	public RestaurantAddress returnRestaurantAddress(Connection conn, String id) {
		String sqlQuery = "SELECT * FROM restaurant_address WHERE res_add_restaurant_id_pk_fk = ?";
		
		// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared query
			stmt.setString(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			// if a result is found for the CNPJ, create a restaurant address object
			// with the result attributes
			if (result.next()) {
				RestaurantAddress er = new RestaurantAddress();
				
				er.setRestaurantId(result.getString("res_add_restaurant_id_pk_fk"));
				er.setPostalCode(result.getString("res_add_postal_code_pk"));
				er.setName(result.getString("name"));
				er.setNumber(result.getInt("number"));
				
				return er;

			}
									
		} catch (SQLException e) {
			System.err.println("Error in restaurant_address querying operation.");
		    e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Updates a restaurant address in the database.
	 * @param conn database connection
	 * @param restaurantAddress address object
	 */
	public boolean updateRestaurantAddress(Connection conn, Address restaurantAddress) {
		String sqlQuery = "UPDATE restaurant_address SET " +
	            "res_add_postal_code_pk = ?, " +
	            "name = ?, " +
	            "number = ? " +
	            "WHERE res_add_restaurant_id_pk_fk = ?";

		// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared query
			stmt.setString(1, restaurantAddress.getPostalCode());
	        stmt.setString(2, restaurantAddress.getName());
	        stmt.setInt(3, restaurantAddress.getNumber());
	        stmt.setString(4, restaurantAddress.getId());
						
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
			
		} catch (SQLException e) {
			System.err.println("Error in restaurant_address updating operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Inserts a client address into the database.
	 * @param conn database connection
	 * @param customerAddress client address object
	 * @return boolean
	 */
	public boolean addCustomerAddress(Connection conn, Address customerAddress) {
		String sqlQuery = "INSERT INTO customer_address "
				+ "(cus_add_customer_id_pk_fk, "
				+ "cus_add_postal_code_pk, "
				+ "name, "
				+ "number) VALUES (?, ?, ?, ?)";
		
		// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared query
			stmt.setString(1, customerAddress.getId());
			stmt.setString(2, customerAddress.getPostalCode());
			stmt.setString(3, customerAddress.getName());
			stmt.setInt(4, customerAddress.getNumber());
			
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
			
		} catch (SQLException e) {
			System.err.println("Error in customer_address adding operation");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Retrieves client address details for later use.
	 * @param conn database connection
	 * @param cpf client CPF
	 * @return client address object
	 */
	public CustomerAddress returnCustomerAddress(Connection conn, String cpf) {
		String sqlQuery = "SELECT * FROM customer_address WHERE cus_add_customer_id_pk_fk = ?";
		
		// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared query
			stmt.setString(1, cpf);
			
			ResultSet result = stmt.executeQuery();
			
			// if a result is found for the CPF, create a client address object
			// with the result attributes
			if (result.next()) {
				CustomerAddress customerAddress = new CustomerAddress();
				
				customerAddress.setCustomerId(result.getString("pk_fk_cli_cpf"));
				customerAddress.setPostalCode(result.getString("pk_enc_cep"));
				customerAddress.setName(result.getString("enc_nome"));
				customerAddress.setNumber(result.getInt("enc_numero"));
				
				return customerAddress;

			}
									
		} catch (SQLException e) {
			System.err.println("Error in customer_address querying operation");
		    e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Updates a client address in the database.
	 * @param conn database connection
	 * @param customerAddress address object
	 * @return boolean
	 */
	public boolean updateCustomerAddress(Connection conn, Address customerAddress) {
		String sqlQuery = "UPDATE customer_address SET " +
	            "cus_add_postal_code_pk = ?, " +
	            "name = ?, " +
	            "number = ? " +
	            "WHERE cus_add_customer_id_pk_fk = ?";

		// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared query
			stmt.setString(1, customerAddress.getPostalCode());
	        stmt.setString(2, customerAddress.getName());
	        stmt.setInt(3, customerAddress.getNumber());
	        stmt.setString(4, customerAddress.getId());
						
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
			
		} catch (SQLException e) {
			System.err.println("Error in customer_address updating operation");
		    e.printStackTrace();
		}
		
		return false;
	}
	
}
