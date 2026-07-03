package entities;

/**
 * Entity: OrderItem
 *
 * Description:
 * Represents an item associated with a system order.
 *
 * Responsibilities:
 * - Store information about the product linked to the order
 * - Record the requested quantity of the product
 * - Associate products with completed orders
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public class OrderItem {
	private Integer orderNumber;
	private Integer productNumber;
	private Integer quantity;
	
	
	/**
	 * No-argument constructor
	 */
	public OrderItem() {
		
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getProductNumber() {
		return productNumber;
	}

	public void setProductCode(Integer productNumber) {
		this.productNumber = productNumber;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
}
