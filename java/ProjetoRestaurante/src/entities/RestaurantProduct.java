package entities;

/**
 * Entity: RestaurantProduct
 *
 * Description:
 * Represents a product offered by a restaurant in the system.
 *
 * Responsibilities:
 * - Link products to restaurants
 * - Store product pricing information
 * - Track available stock quantity
 * - Identify products sold by the restaurant
 *
 * Author: Rodrigo
 * Since: 2026-04-20
 */

public class RestaurantProduct {
	private String restaurantId;
	private Integer productNumber;
	private Integer stockAmount;
	private Double price;
	
	
	// No-argument constructor
	public RestaurantProduct() {
		
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Integer getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}

	public Integer getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(Integer stockAmount) {
		this.stockAmount = stockAmount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}	
	
}
