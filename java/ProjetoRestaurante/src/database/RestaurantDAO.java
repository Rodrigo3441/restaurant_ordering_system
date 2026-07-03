package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.Restaurant;

/**
 * Class: RestaurantDAO
 *
 * Description:
 * DAO responsible for managing restaurant data
 *
 * Responsibilities:
 * - Connect to the database
 * - Perform data operations
 *
 * @author Rodrigo
 * @since 21-04-2026
 */

public class RestaurantDAO {
	
	/**
	 * Inserts a new restaurant into the database.
	 * @param conn database connection
	 * @param restaurant restaurant object to insert
	 * @return true if insert succeeded, false otherwise
	 */
	public boolean addRestaurant(Connection conn, Restaurant restaurant) {
		String sqlQuery = "INSERT INTO restaurant ("
				+ "restaurant_id_pk, "
				+ "name, "
				+ "phone, "
				+ "res_senha) VALUES (?, ?, ?, ?)";
		
		// preparing the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// binding attributes to the prepared statement
			stmt.setString(1, restaurant.getId());
			stmt.setString(2, restaurant.getName());
			stmt.setString(3, restaurant.getPhone());
			stmt.setString(4, restaurant.getPasscode());
			
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
			
		} catch (SQLException e) {
			System.err.println("Error in restaurant adding operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Retrieves restaurant information from the database for use in operations.
	 * @param conn database connection
	 * @param id CNPJ of the restaurant to retrieve
	 * @return a Restaurant object if found, otherwise null
	 */
	public Restaurant returnRestaurant(Connection conn, String id) {
		String sqlQuery = "SELECT * FROM restaurant WHERE restaurant_id_pk = ?";
		
			// preparing the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// binding attributes to the prepared statement
			stmt.setString(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			// if there is a result for the CNPJ, instantiate a Restaurant object
			// with the attributes from the result
			if (result.next()) {
				Restaurant restaurant = new Restaurant();
				
				restaurant.setId(result.getString("restaurant_id_pk"));
				restaurant.setName(result.getString("name"));
				restaurant.setPhone(result.getString("phone"));
				restaurant.setPasscode(result.getString("passcode"));
				
				// restaurant category may be null
				int category = result.getInt("cat_id_fk");
				if (!result.wasNull()) {
					restaurant.setCategoryId(category);
				}
				
				return restaurant;

			}
									
		} catch (SQLException e) {
			System.err.println("Error in restaurant querying operation.");
		    e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Updates a restaurant's information in the database.
	 * @param conn database connection
	 * @param restaurant restaurant object with updated data
	 * @return true if update succeeded, false otherwise
	 */
	public boolean updateRestaurant(Connection conn, Restaurant restaurant) {
		String sqlQuery = "UPDATE restaurant " +
							"SET name = ?, " +
							"phone = ?, " +
							"cat_id_fk = ?, " +
							"passcode = ? " +
							"WHERE restaurant_id_pk = ?";

		// preparing the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// binding attributes to the prepared statement
			stmt.setString(1, restaurant.getName());
			stmt.setString(2, restaurant.getPhone());
			stmt.setObject(3, restaurant.getCategoryId());
			stmt.setString(4, restaurant.getPasscode());
			stmt.setString(5, restaurant.getId());
						
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
			
		} catch (SQLException e) {
			System.err.println("Error in restaurant updating operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Deletes a restaurant from the database.
	 * @param conn database connection
	 * @param id CNPJ of the restaurant to delete
	 * @return true if delete succeeded, false otherwise
	 */
	public boolean deleteRestaurant(Connection conn, String id) {
		String sqlQuery = "DELETE FROM restaurant WHERE restaurant_id_pk = ?";
		
		// preparing the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// binding attributes to the prepared statement
			stmt.setString(1, id);
			
			// execute the query and validate success
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;

		} catch (SQLException e) {
			System.err.println("Error in restaurant deleting operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Retrieves information for all restaurants in the system.
	 * @param conn database connection
	 * @return ArrayList of Restaurant objects
	 */
	public ArrayList<Restaurant> returnRestaurantList(Connection conn){
		
		//list to store all restaurant instances
		ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();
		
		String sqlQuery = "SELECT * FROM restaurant";
		
		// preparing the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			ResultSet result = stmt.executeQuery();
			
			// storing all found restaurants into the dynamic restaurant list
			while (result.next()) {
				Restaurant restaurant = new Restaurant();
				
				restaurant.setId(result.getString("restaurant_id_pk"));
				restaurant.setName(result.getString("name"));
				restaurant.setPhone(result.getString("phone"));
				restaurant.setCategoryId(result.getInt("cat_id_fk"));
				restaurant.setPasscode(result.getString("passcode"));
				
				restaurantList.add(restaurant);
			}
			
			
		} catch (SQLException e) {
			System.err.println("Error in restaurant querying operation.");
		    e.printStackTrace();
		}
		
		return restaurantList;
	}
}
