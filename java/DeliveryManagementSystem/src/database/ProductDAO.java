package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Product;

/**
 * Class: ProductDAO
 *
 * Description:
 * Data access object responsible for managing product records.
 *
 * Responsibilities:
 * - Connect to the database
 * - Perform CRUD operations on product data
 *
 * @author Rodrigo
 * @since 21-04-2026
 */

public class ProductDAO {
	
	/**
	 * Inserts a new product into the database.
	 * @param conn database connection
	 * @param product product object to insert
	 * @return true if insert succeeded, false otherwise
	 */
	public boolean addProduct(Connection conn, Product product) {
		String sqlQuery = "INSERT INTO product (" +
				"product_id_pk, " +
				"name, " +
				"description, " +
				"cat_id_fk) " +
				"VALUES (?, ?, ?, ?)";
		
		// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared query
			stmt.setInt(1, product.getNumber());
			stmt.setString(2, product.getName());
			stmt.setString(3, product.getDescription());
			stmt.setObject(4, product.getCategoryId());
			
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
			
		} catch (SQLException e) {
			System.err.println("Error in product adding operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Retrieves a product from the database by its id.
	 * @param conn database connection
	 * @param number product id to search for
	 * @return Product object if found, otherwise null
	 */
	public Product returnProductById(Connection conn, int number) {
		String sqlQuery = "SELECT * FROM product WHERE product_id_pk = ?";
		
		// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared query
			stmt.setInt(1, number);
			
			ResultSet result = stmt.executeQuery();
			
			// if there is a result from the search by id, instantiate a Product
			// with the attributes from the result
			if (result.next()) {
				Product product = new Product();
							
				product.setNumber(result.getInt("product_id_pk"));
				product.setName(result.getString("name"));
				product.setDescription(result.getString("description"));
				
				//product category can be null
				int category = result.getInt("cat_id_fk");
				if (!result.wasNull()) {
					product.setCategoryId(category);
				}
				return product;

			}
									
		} catch (SQLException e) {
			System.err.println("Error in product querying operation.");
		    e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Retrieves a product from the database by matching its name.
	 * Uses SQL LIKE to perform a partial match.
	 * @param conn database connection
	 * @param name name (or partial name) of the product to search for
	 * @return Product object if a match is found, otherwise null
	 */
	public Product returnProductByName(Connection conn, String name) {
		String sqlQuery = "SELECT * FROM product WHERE name LIKE ?";
		
		// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared query
			stmt.setString(1, "%" + name + "%");
			
			ResultSet result = stmt.executeQuery();
			
			// if there is a result from the name search, instantiate a Product
			// with the attributes from the result
			if (result.next()) {
				Product product = new Product();
							
				product.setNumber(result.getInt("product_id_pk"));
				product.setName(result.getString("name"));
				product.setDescription(result.getString("description"));
				
				//product category can be null
				int categoria = result.getInt("cat_id_fk");
				if (!result.wasNull()) {
					product.setCategoryId(categoria);
				}
				return product;

			}
									
		} catch (SQLException e) {
			System.err.println("Error in product querying operation.");
		    e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Updates an existing product's information in the database.
	 * @param conn database connection
	 * @param product product object with updated values
	 * @return true if update succeeded, false otherwise
	 */
	public boolean updateProduct(Connection conn, Product product) {
		String sqlQuery = "UPDATE product SET "
				+ "name = ?, "
				+ "description = ?, "
				+ "cat_id_fk = ? "
				+ "WHERE product_id_pk = ?";

		// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared query
			stmt.setString(1, product.getName());
	        stmt.setString(2, product.getDescription());
	        stmt.setObject(3, product.getCategoryId());
	        stmt.setObject(4, product.getNumber());
	
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
			
		} catch (SQLException e) {
			System.err.println("Error in product updating operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Deletes a product from the database by id.
	 * @param conn database connection
	 * @param number id of the product to delete
	 * @return true if delete succeeded, false otherwise
	 */
	public boolean deleteProduct(Connection conn, int number) {
		String sqlQuery = "DELETE FROM product WHERE product_id_pk = ?";
		
		// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind attributes to the prepared query
			stmt.setInt(1, number);
			
			// execute the query and validate success
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;

		} catch (SQLException e) {
			System.err.println("Error in product deleting operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
}
