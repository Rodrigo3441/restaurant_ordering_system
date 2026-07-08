package view;
/**
 * Class: OrderItemView
 *
 * Description:
 * Represents an item in the customer's shopping cart, containing basic
 * information about the selected product such as name, price, code, and
 * desired quantity.
 *
 * Responsibilities:
 * - Hold the data for a product added to the cart
 * - Manage the quantity chosen by the customer
 * - Serve as a model for displaying order items in the UI
 *
 * Notes:
 * - Each instance represents a single product in the cart
 * - Quantity may be modified through user interaction
 *
 * @author rodrigo
 * @since 04-05-2026
 */
public class OrderItemView {
	String productName;
	Double price;
	Integer productNumber;
	Integer quantity;
	

	public OrderItemView() {
		
	}

	public String getName() {
		return productName;
	}

	public void setName(String productName) {
		this.productName = productName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	
	@Override
	public String toString() {
		return " Name: " + productName +
		       " | Price: R$ " + price +
		       " | Quantity: " + quantity;
	}
	
	
	
}
