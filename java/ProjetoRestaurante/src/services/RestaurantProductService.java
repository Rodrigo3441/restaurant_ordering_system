package services;

import java.sql.Connection;
import java.util.ArrayList;
import database.RestaurantProductDAO;
import entities.RestaurantProduct;
import view.RestaurantProductView;

/**
 * Class: RestaurantProductService
 *
 * Description:
 * Service class responsible for managing business rules for restaurant products in a N:N relationship.
 *
 * Responsibilities:
 * - provide validation methods for product information
 * - communicate with the data access layer
 *
 * @author Rodrigo
 * @since 04-05-2026
 */

public class RestaurantProductService {

	private RestaurantProductDAO restaurantProductDAO;
	private Connection conn;
	
	public RestaurantProductService(Connection conn) {
		this.restaurantProductDAO = new RestaurantProductDAO();
		this.conn = conn;
	}
	
	/**
	 * Validates whether a product stock quantity is valid.
	 * @param stockAmount stock quantity
	 */
	public void checkStockAmount(int stockAmount) {
		if(!isStockAmountValid(stockAmount)) {
			throw new IllegalArgumentException("Enter a valid stock amount");
		}
	}
	
	/**
	 * Validates whether a product price is valid.
	 * @param price product price
	 */
	public void checkProductPrice(double price) {
		if(!isProductPriceValid(price)) {
			throw new IllegalArgumentException("Enter a valid product price");
		}
	}
	
	/**
	 * Validates whether a product code is valid.
	 * @param productNumber product code
	 */
	public void checkProductNumber(int productNumber) {
		if(!isProductNumberValid(productNumber)) {
			throw new IllegalArgumentException("Enter a valid product number");
		}
	}
	
	/**
	 * Checks whether a product is already registered in the restaurant catalog.
	 * @param id restaurant CNPJ
	 * @param productNumber product code
	 * @return true if the product is already registered
	 */
	public boolean isProductAlreadyAdded(String id, int productNumber) {
		return restaurantProductDAO.isProductAlreadyAdded(conn, id, productNumber);
	}
	
	/**
	 * Removes a product from the restaurant catalog.
	 * @param id restaurant CNPJ
	 * @param productNumber product code
	 * @throws Exception if an error occurs while deleting the product
	 */
	public void deleteProductRestaurant(String id, int productNumber) throws Exception {
		if(!restaurantProductDAO.deleteProductRestaurant(conn, id, productNumber)) {
			throw new Exception("An error has occurred while trying to delete the product");		
		} 
		
	}
	
	/**
	 * Associates a global catalog product with the restaurant catalog.
	 * @param restaurantProduct restaurant product object
	 * @return true if the association was successful
	 */
	public boolean addRestaurantProduct(RestaurantProduct restaurantProduct) {
		return restaurantProductDAO.addRestaurantProduct(conn, restaurantProduct);
	}
	
	/**
	 * Updates the stock quantity for a product registered in a restaurant.
	 * @param id restaurant CNPJ
	 * @param restaurantProductView object with product data
	 * @param stockAmount new stock quantity
	 * @return true if the update was successful
	 */
	public boolean updateProductRestaurant(String id, RestaurantProductView restaurantProductView, int stockAmount) {
		
		RestaurantProduct restaurantProduct = new RestaurantProduct();
		
		restaurantProduct.setRestaurantId(id);
		restaurantProduct.setProductNumber(restaurantProductView.getProductNumber());
		restaurantProduct.setPrice(restaurantProductView.getProductPrice());
		restaurantProduct.setStockAmount(stockAmount);
		
		return restaurantProductDAO.updateProductRestaurant(conn, restaurantProduct);
	}
	
	/**
	 * Returns all products registered for the restaurant identified by the provided CNPJ.
	 * @param id restaurant CNPJ in session
	 * @return list of restaurant product views
	 */
	public ArrayList<RestaurantProductView> returnAllProductsPerRestaurant(String id){
		return restaurantProductDAO.returnAllProductsPerRestaurant(conn, id);
	}
	
	private boolean isStockAmountValid(int stockAmount) {
		return stockAmount >= 0;
	}
	
	private boolean isProductPriceValid(double price) {
		return price > 0;
	}
	
	private boolean isProductNumberValid(int productNumber) {
		return productNumber > 0 && productNumber < 2_000_000_000;
	}
	
	
}
