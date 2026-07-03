package view;
/**
 * Class: RestaurantProductView
 *
 * Description:
 * Represents the view of a restaurant product,
 * containing combined information such as name, description, price, and stock quantity.
 *
 * Responsibilities:
 * - Store data resulting from JOIN query between PRODUCT and PRODUCT_RESTAURANT
 * - Serve as a model for displaying products to the restaurant
 *
 * @author rodrigo
 * @since 28-04-2026
 */
public class RestaurantProductView {
	private Integer productNumber;
	private String productName;
	private Double productPrice;
	private Integer stockAmount;
	private String description;
	
	/**
	 * No-argument constructor
	 */
	public RestaurantProductView() {
		
	}

	public int getProductNumber() {
		return productNumber;
	}
	
	public void setProductNumber(int productNumber) {
		this.productNumber = productNumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(Integer stockAmount) {
		this.stockAmount = stockAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Name: " + productName +
		       " | Price: R$ " + productPrice +
		       " | Stock Amount: " + stockAmount +
		       " | Description: " + description +
			   " | Number: " + productNumber;
	}
	
	public String formatarParaCliente() {
		return "Name: " + productName +
			   " | Description: " + description +
		       " | Price: R$ " + productPrice +
		       " | Stock Amount: " + stockAmount;
		       
	}
	
}
