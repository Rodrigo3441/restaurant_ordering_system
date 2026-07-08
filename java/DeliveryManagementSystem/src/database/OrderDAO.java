package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.Customer;
import entities.Order;
import entities.Restaurant;

/**
 * Class: OrderDAO
 *
 * Description:
 * DAO class responsible for managing order data.
 *
 * Responsibilities:
 * - Connect to the database
 * - Perform CRUD operations on order records
 *
 * @author Rodrigo
 * @since 21-04-2026
 */

public class OrderDAO {
	
	/**
	 * Inserts a new order into the database and returns the generated id.
	 * @param conn database connection
	 * @param restaurant Restaurant associated with the order
	 * @param customer Customer who placed the order
	 * @return generated order id, or -1 on failure
	 */
	public int addOrder(Connection conn, Restaurant restaurant, Customer customer) {
		String sqlQuery = "INSERT INTO orders ("
				+ "order_status, "
				+ "restaurant_id_fk, "
				+ "customer_id_fk) "
				+ "VALUES (?, ?, ?)";
		
			// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)){
			
			// bind attributes to the prepared statement
			stmt.setString(1, "Preparing");
			stmt.setString(2, restaurant.getId());
			stmt.setString(3, customer.getId());
			
			stmt.executeUpdate();
			
			// obtain the generated order id
			ResultSet result = stmt.getGeneratedKeys();
			
			if (result.next()) {
				return result.getInt(1);
			}
			
		} catch (SQLException e) {
			System.err.println("Error in orders adding operation.");
		    e.printStackTrace();
		}
		
		return -1;
		
	}
	
	/**
	 * Retrieves order information from the database for lookup purposes.
	 * @param conn database connection
	 * @param number order number to fetch
	 * @return Order object or null if not found
	 */
	public Order returnOrder(Connection conn, int number) {
		String sqlQuery = "SELECT * FROM orders WHERE order_id_pk = ?";
		
			// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared statement
			stmt.setInt(1, number);
			
			ResultSet result = stmt.executeQuery();
			
			// if a result is found, instantiate an Order object with the result attributes
			if (result.next()) {
				Order order = new Order();
				
				order.setOrderNumber(result.getInt("order_id_pk"));
				order.setOrderStatus(result.getString("order_status"));
				order.setDeliveryPersonId(result.getString("delivery_person_id_fk"));
				order.setRestaurantId(result.getString("restaurant_id_fk"));
				order.setCustomerId(result.getString("customer_id_fk"));
				
				return order;

			}
									
		} catch (SQLException e) {
			System.err.println("Error in orders querying operation.");
		    e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Updates an order record in the database.
	 * @param conn database connection
	 * @param order Order object with updated data
	 * @return true if update affected at least one row
	 */
	public boolean updateOrder(Connection conn, Order order) {
		String sqlQuery = "UPDATE orders " +
							"SET order_status = ?, " +
							"delivery_person_id_fk = ? " +
							"WHERE order_id_pk = ?";

			// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared statement
			stmt.setString(1, order.getOrderStatus());
			stmt.setString(2, order.getDeliveryPersonId());
			stmt.setInt(3, order.getOrderNumber());
						
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
			
		} catch (SQLException e) {
			System.err.println("Error in orders updating operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Deletes an order from the database.
	 * @param conn database connection
	 * @param number order number to delete
	 * @return true if deletion affected at least one row
	 */
	public boolean deleteOrder(Connection conn, int number) {
		String sqlQuery = "DELETE FROM orders WHERE order_id_pk = ?";
		
			// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared statement
			stmt.setInt(1, number);
			
			// execute the query and check success
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;

		} catch (SQLException e) {
			System.err.println("Error in orders deleting operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Retrieves all orders for a given restaurant filtered by status.
	 * @param conn database connection
	 * @param id restaurant id
	 * @param orderStatus desired order status
	 * @return ArrayList of orders
	 */
	public ArrayList<Order> returnAllOrdersPerRestaurant(Connection conn, String id, String orderStatus){
		
		// List to store all order instances
		ArrayList<Order> ordersList = new ArrayList<Order>();
		
		String sqlQuery = "SELECT * FROM orders WHERE restaurant_id_fk = ? AND order_status = ? ORDER BY order_date ASC";
		
			// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			stmt.setString(1, id);
			stmt.setString(2, orderStatus);
			
			ResultSet result = stmt.executeQuery();
			
			// store each found order in the dynamic list
			while (result.next()) {
				Order order = new Order();
				
				order.setOrderNumber(result.getInt("order_id_pk"));
				order.setOrderStatus(result.getString("order_status"));
				order.setDeliveryPersonId(result.getString("delivery_person_id_fk"));
				order.setRestaurantId(result.getString("restaurant_id_fk"));
				order.setCustomerId(result.getString("customer_id_fk"));
				order.setOrderDate(result.getTimestamp("order_date").toLocalDateTime());
				
				ordersList.add(order);
			}
			
			
		} catch (SQLException e) {
			System.err.println("Error in orders querying operation.");
		    e.printStackTrace();
		}
		
		return ordersList;
	}
	
	/**
	 * Retrieves all orders placed by a specific customer.
	 * @param conn database connection
	 * @param id customer id
	 * @return ArrayList of orders
	 */
	public ArrayList<Order> returnAllOrdersPerCustomer(Connection conn, String id){
		
		// List to store all order instances
		ArrayList<Order> ordersList = new ArrayList<Order>();
		
		String sqlQuery = "SELECT * FROM orders WHERE customer_id_fk = ? ORDER BY order_date DESC";
		
			// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			stmt.setString(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			// store each found order in the dynamic list
			while (result.next()) {
				Order order = new Order();
				
				order.setOrderNumber(result.getInt("order_id_pk"));
				order.setOrderStatus(result.getString("order_status"));
				order.setDeliveryPersonId(result.getString("delivery_person_id_fk"));
				order.setRestaurantId(result.getString("restaurant_id_fk"));
				order.setCustomerId(result.getString("customer_id_fk"));
				order.setOrderDate(result.getTimestamp("order_date").toLocalDateTime());
				
				ordersList.add(order);
			}
			
			
		} catch (SQLException e) {
			System.err.println("Error in orders querying operation.");
		    e.printStackTrace();
		}
		
		return ordersList;
	}
}
