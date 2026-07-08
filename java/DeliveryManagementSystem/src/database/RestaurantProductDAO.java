package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entities.RestaurantProduct;
import view.OrderItemView;
import view.RestaurantProductView;

/**
 * Class: RestaurantProductDAO
 *
 * Description:
 * DAO responsible for managing associations between global catalog products
 * and individual restaurants (many-to-many relationship).
 *
 * Responsibilities:
 * - Connect to the database
 * - Perform data manipulations related to restaurant-product associations
 *
 * @author Rodrigo
 * @since 28-04-2026
 */

public class RestaurantProductDAO {
	
	/**
	 * Inserts a restaurant-product association into the associative table.
	 * @param conn database connection
	 * @param restaurantProduct RestaurantProduct object to insert
	 * @return true if insertion succeeded, false otherwise
	 */
	public boolean addRestaurantProduct(Connection conn, RestaurantProduct restaurantProduct) {
		String sqlQuery = "INSERT INTO restaurant_product (" +
				"res_pro_restaurant_id_pk_fk, " +
				"res_pro_product_id_pk_fk, " +
				"stock_amount, " +
				"price) VALUES (?, ?, ?, ?)";
		
		// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared query
			stmt.setString(1, restaurantProduct.getRestaurantId());
			stmt.setInt(2, restaurantProduct.getProductNumber());
			stmt.setInt(3, restaurantProduct.getStockAmount());
			stmt.setDouble(4, restaurantProduct.getPrice());
			
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
			
		} catch (SQLException e) {
			System.err.println("Error in restaurant_product adding operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Retrieves all products for a given restaurant from the database.
	 * @param conn database connection
	 * @param id the restaurant's CNPJ (identifier)
	 * @return list of RestaurantProductView representing the restaurant's products
	 */
	public ArrayList<RestaurantProductView> returnAllProductsPerRestaurant(Connection conn, String id) {		
		String sqlQuery = "SELECT "
				+ "p.name, "
				+ "p.product_id_pk,"
				+ "rp.price, "
				+ "rp.stock_amount, "
				+ "p.description "
				+ "FROM restaurant_product AS rp "
				+ "INNER JOIN product AS p "
				+ "ON p.product_id_pk = rp.res_pro_product_id_pk_fk "
				+ "WHERE rp.res_pro_restaurant_id_pk_fk = ?";
		
		// list to store all restaurant product instances
		ArrayList<RestaurantProductView> productList = new ArrayList<RestaurantProductView>();
				
		// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared query
			stmt.setString(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			// if there are results for products by CNPJ, add each product
			// with the result attributes to the product list
			while (result.next()) {
				RestaurantProductView pr = new RestaurantProductView();
				pr.setProductNumber(result.getInt("product_id_pk"));
				pr.setProductName(result.getString("name"));
				pr.setProductPrice(result.getDouble("price"));
				pr.setStockAmount(result.getInt("stock_amount"));
				pr.setDescription(result.getString("description"));
				
				productList.add(pr);
			}
			
									
		} catch (SQLException e) {
			System.err.println("Error in restaurant_product querying operation.");
		    e.printStackTrace();
		}
		
		return productList;
	}
	
	/**
	 * Checks whether a product is already associated with a restaurant.
	 * @param conn database connection
	 * @param id restaurant identifier
	 * @param number product code to check
	 * @return true if the association exists, false otherwise
	 */
	public boolean isProductAlreadyAdded(Connection conn, String id, int number) {
		String sqlQuery = "SELECT * FROM restaurant_product "
				+ "WHERE res_pro_restaurant_id_pk_fk = ? "
				+ "AND res_pro_product_id_pk_fk = ?";
		
		// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared query
			stmt.setString(1, id);
			stmt.setInt(2, number);
			
			ResultSet result = stmt.executeQuery();
			
			return result.next();
									
		} catch (SQLException e) {
			System.err.println("Error in restaurant_product querying operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Deletes a product association from a restaurant, removing it from the restaurant's catalog.
	 * @param conn database connection
	 * @param id restaurant identifier
	 * @param number product code to delete
	 * @return true if deletion succeeded, false otherwise
	 */
	public boolean deleteProductRestaurant(Connection conn, String id, int number) {
		String sqlQuery = "DELETE FROM restaurant_product "
				+ "WHERE res_pro_restaurant_id_pk_fk = ? "
				+ "AND res_pro_product_id_pk_fk = ?";
		
		// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared query
			stmt.setString(1, id);
			stmt.setInt(2, number);
			
			// execute the query and validate success
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;

		} catch (SQLException e) {
			System.err.println("Error in restaurant_product deleting operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean updateRestaurantProduct(Connection conn, RestaurantProduct restaurantProduct) {
		String sqlQuery = "UPDATE restaurant_product " +
				"SET stock_amount = ?, " +
				"price = ? "
				+ "WHERE res_pro_restaurant_id_pk_fk = ? "
				+ "AND res_pro_product_id_pk_fk = ?";

		// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
		
			// bind attributes to the prepared query
			stmt.setInt(1, restaurantProduct.getStockAmount());
			stmt.setDouble(2, restaurantProduct.getPrice());
			stmt.setString(3, restaurantProduct.getRestaurantId());
			stmt.setInt(4, restaurantProduct.getProductNumber());
						
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
		
		} catch (SQLException e) {
			System.err.println("Error in restaurant_product updating operation.");
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Decreases the stock of a restaurant product when an order is placed.
	 * @param conn database connection
	 * @param id restaurant identifier
	 * @param item order item containing product code and quantity
	 * @return true if stock was successfully decreased, false otherwise
	 */
	public boolean decreaseStockAmount(Connection conn, String id, OrderItemView item) {
		String sqlQuery = "UPDATE restaurant_product " +
						  "SET stock_amount = stock_amount - ? " +
						  "WHERE res_pro_restaurant_id_pk_fk = ? "
						  + "AND res_pro_product_id_pk_fk = ? "
						  + "AND stock_amount >= ?";

		// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
		
			// bind attributes to the prepared query
			
			stmt.setInt(1, item.getQuantity());
			stmt.setString(2, id);
			stmt.setInt(3, item.getProductNumber());
			stmt.setInt(4, item.getQuantity());
						
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
			
		} catch (SQLException e) {
			System.err.println("Error in restaurant_product decreasingStock operation.");
			e.printStackTrace();
			}
		
		return false;
	}
	
}
