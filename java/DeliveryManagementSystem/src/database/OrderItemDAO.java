package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entities.OrderItem;
import view.OrderItemView;

/**
 * Class: OrderItemDAO
 *
 * Description:
 * Responsible for managing the association between the restaurant's product
 * catalog and each order (many-to-many relationship).
 *
 * Responsibilities:
 * - Connect to the database
 * - Perform data operations for order items
 *
 * @author Rodrigo
 * @since 07-05-2026
 */

public class OrderItemDAO {
	
	/**
	 * Inserts a customer order item into the database.
	 * @param conn database connection
	 * @param orderItem order item object
	 * @return true if insert succeeded, false otherwise
	 */
	public boolean addOrderItem(Connection conn, OrderItem orderItem) {
		String sqlQuery = "INSERT INTO order_item ("
						+ "order_item_order_id_pk_fk, "
						+ "order_item_product_id_pk_fk, "
						+ "quantity) VALUES (?, ?, ?)";
		
			// prepare the statement before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
				// bind attributes to the prepared statement
			stmt.setInt(1, orderItem.getOrderNumber());
			stmt.setInt(2, orderItem.getProductNumber());
			stmt.setInt(3, orderItem.getQuantity());
			
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
			
		} catch (SQLException e) {
			System.err.println("Error in order_item adding operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Returns all items for a given order for display in the customer menu.
	 * @param conn database connection
	 * @param orderId order identifier
	 * @return ArrayList of OrderItemView
	 */
	public ArrayList<OrderItemView> returnAllOrderItem(Connection conn, int orderId) {		
		String sqlQuery = "SELECT "
						+ "p.name, "
						+ "oi.quantity, "
						+ "rp.price "
						+ "FROM order_item AS oi "
						+ "INNER JOIN product AS p "
						+ "ON oi.order_item_product_id_pk_fk = p.product_id_pk "
						+ "INNER JOIN restaurant_product AS rp "
						+ "ON p.product_id_pk = rp.res_pro_product_id_pk_fk "
						+ "WHERE oi.order_item_order_id_pk_fk = ?;";
		
		// List to store all order item view instances
		ArrayList<OrderItemView> orderItemList = new ArrayList<OrderItemView>();
				
		// prepare the statement before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared statement
			stmt.setInt(1, orderId);
			
			ResultSet result = stmt.executeQuery();
			
			while (result.next()) {
				OrderItemView ip = new OrderItemView();
				ip.setName(result.getString("name"));
				ip.setPrice(result.getDouble("price"));
				ip.setQuantity(result.getInt("quantity"));
				
				orderItemList.add(ip);
			}
			
									
		} catch (SQLException e) {
			System.err.println("Error in order_item querying operation.");
		    e.printStackTrace();
		}
		
		return orderItemList;
	}
}
