package entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Entity: Order
 *
 * Description:
 * Represents an order placed by a customer in the system.
 *
 * Responsibilities:
 * - Store general order information
 * - Associate customer, restaurant and delivery person with the order
 * - Track the order status
 * - Record the date and time when the order was placed
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public class Order {
	private Integer orderNumber;
	private String orderStatus;
	private String deliveryPersonId;
	private String restaurantId;
	private String customerId;
	private LocalDateTime orderDate;
	
	/**
	 * No-argument constructor
	 */
	public Order() {
		
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * Returns the delivery person id for the order.
	 * If no delivery person is assigned, returns a default message.
	 *
	 * @return delivery person id or a message indicating no delivery person assigned
	 */
	public String getDeliveryPersonId() {
		if (deliveryPersonId == null) {
			return "No delivery person assigned.";
		}
		
		return deliveryPersonId;
	}
	
	public void setDeliveryPersonId(String deliveryPersonId) {
		this.deliveryPersonId = deliveryPersonId;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getOrderDate() {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		
	    return orderDate.format(dateFormat);
	}	
	
	public void setOrderDate(LocalDateTime orderDate) {
	    this.orderDate = orderDate;
	}
	
	@Override
	public String toString() {
		return " Order Number: " + orderNumber +
			   " |Order Status: " + orderStatus +
		       " | Delivery Person: " + this.getDeliveryPersonId() +
		       " | Customer: " + customerId +
		       " | Order Date: " + this.getOrderDate();
	}
}
