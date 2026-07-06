package services;

import database.ProductDAO;
import java.sql.Connection;
import entities.Product;

/**
 * Class: ProductService
 *
 * Description:
 * Service responsible for managing business rules related to products.
 *
 * Responsibilities:
 * - provide validation methods for product data
 * - communicate with the data access layer
 *
 * @author Rodrigo
 * @since 27-04-2026
 */
public class ProductService {
	private ProductDAO productDAO;
	private Connection conn;
	
	public ProductService(Connection conn) {
		this.productDAO = new ProductDAO();
		this.conn = conn;
	}

	/**
	 * Validates that the product name is valid
	 * @param name product name
	 */
	public void checkName(String name) {
		if (!isNameValid(name)) {
			throw new IllegalArgumentException("Enter a valid name");
		}
	}
	
	/**
	 * Validates that the product description is valid
	 * @param description product description
	 */
	public void checkDescription(String description) {
		if (!isDescriptionValid(description)) {
			throw new IllegalArgumentException("Enter a valid description");
		}
	}
	
	/**
	 * Checks if the product code is valid and not already used by another product
	 * @param productNumber product code
	 */
	public void checkNumber(int productNumber) {
		if (!isProductNumberValid(productNumber)) {
			throw new IllegalArgumentException("Enter a valid product number");
		}
		
		if (!isProductNumberAvailable(productNumber)) {
			throw new IllegalArgumentException("This product number is already used");
		}
	}
	
	/**
	 * Searches and returns a product by the given name
	 * @param name product name to search for
	 * @return Product object or null if not found
	 */
	public Product returnProductByName(String name) {
		name = name.toLowerCase().trim();
		return productDAO.returnProductByName(conn, name);
	}
	
	/**
	 * Searches and returns a product by the given id/code
	 * @param productNumber product id
	 * @return Product object or null if not found
	 */
	public Product returnProductById(int productNumber) {
		return productDAO.returnProductById(conn, productNumber);
	}
	
	/**
	 * Inserts a product into the system catalog
	 * @param product Product object to insert
	 * @return true if insertion succeeded
	 */
	public boolean addProduct(Product product) {
		return productDAO.addProduct(conn, product);
	}
	
	private boolean isNameValid(String name) {
		return name.length() >= 3 && name.length() < 40;
	}
	
	private boolean isDescriptionValid(String description) {
		return description.length() < 255;
	}
	
	private boolean isProductNumberValid(int productNumber) {
		return productNumber > 0 && productNumber < 2_000_000_000;
	}
	
	/**
	 * Checks whether the given product code is available for use
	 * @param productNumber product code to check
	 * @return true if the code is available
	 */
	private boolean isProductNumberAvailable(int productNumber) {
		return productDAO.returnProductById(conn, productNumber) == null;
	}
	
}
