package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import database.DeliveryPersonDAO;
import database.OrderItemDAO;
import database.OrderDAO;
import entities.Customer;
import entities.DeliveryPerson;
import entities.OrderItem;
import entities.Order;
import entities.Restaurant;
import view.OrderItemView;
import view.RestaurantProductView;
import database.RestaurantProductDAO;

/**
 * Class: OrderService
 *
 * Description:
 * Service class responsible for managing business rules related to orders.
 *
 * Responsibilities:
 * - provide validation methods for order-related information
 * - interact with the data access layer
 *
 * @author Rodrigo
 * @since 04-05-2026
 */

public class OrderService {
	private Connection conn;
	private OrderDAO orderDAO;
	private RestaurantProductDAO restaurantProductDAO;
	private DeliveryPersonDAO deliveryPersonDAO;
	private OrderItemDAO orderItemDAO;


	public OrderService(Connection conn) {
		this.orderDAO = new OrderDAO();
		this.restaurantProductDAO = new RestaurantProductDAO();
		this.orderItemDAO = new OrderItemDAO();
		this.deliveryPersonDAO = new DeliveryPersonDAO();
		this.conn = conn;
	}
	
	/**
	 * Generic method to validate whether the provided index is valid for an ArrayList of any type
	 * @param list the list to validate
	 * @param index the index to check
	 */
	public <T> void checkIndex(ArrayList<T> list, int index) {
		if(!isIndexValid(list, index)) {
			throw new IllegalArgumentException("Enter a valid index");
		}
	}
	
	/**
	 * Validates whether the quantity provided by the user is valid for the given product
	 * @param productView product view with stock information
	 * @param quantity quantity to validate
	 */
	public void checkQuantity(RestaurantProductView productView, int quantity) {
		if (!isQuantityValid(productView, quantity)){
			throw new IllegalArgumentException("Enter a valid quantity");
		}
	}
	
	/**
	 * Instantiates an OrderItemView and returns it for the user's shopping cart
	 * @param productView product view used to create the order item
	 * @param quantity quantity for the order item
	 * @return OrderItemView
	 */
	public OrderItemView createOrderItem(RestaurantProductView productView, int quantity) {
		OrderItemView orderItemView = new OrderItemView();
		orderItemView.setName(productView.getProductName());
		orderItemView.setPrice(productView.getProductPrice());
		orderItemView.setProductNumber(productView.getProductNumber());
		orderItemView.setQuantity(quantity);
		return orderItemView;
	}
	
	/**
	 * Searches for a product in the shopping cart by its code and returns its index in the list.
	 * @param cart user's shopping cart
	 * @param productNumber product code to search for
	 * @return index of the product in the list, or -1 if not found
	 */
	public int returnCartItemPosition(
		ArrayList<OrderItemView> cart, 
		int productNumber
	) {
		for (int i = 0; i < cart.size(); i++) {
			if (cart.get(i).getProductNumber() == productNumber) {
				return i; //return the position of the product in the cart
			}
		}
		
		return -1; //not found
	}
	
	/**
	 * Searches for a product in the provided list by its code.
	 * @param productList list of available products
	 * @param productNumber product code to search for
	 * @return the matching product, or null if not found
	 */
	public RestaurantProductView returnProductByNumber(
		ArrayList<RestaurantProductView> productList,
		int productNumber
	) {
		for (int i = 0; i < productList.size(); i++) {
			if (productList.get(i).getProductNumber() == productNumber) {
				return productList.get(i); //return the matching product
			}
		}
		return null;
	}
	
	/**
	 * Calculates the discount for a customer's total order value.
	 * @param totalValue customer's total order value
	 * @return the total value after applying the discount
	 */
	public double calculateDiscount(double totalValue) {
		if (totalValue > 300) {
			return totalValue * 0.85;
			
		} else if (totalValue > 200){
			return totalValue * 0.90;
			
		} else if (totalValue > 100) {
			return totalValue * 0.95;
		} else {
			return totalValue;
		}
	}
	
	
	/**
	 * Registers an order in the system along with all its items.
	 * Also decreases the restaurant's stock for purchased products.
	 * @param restaurant restaurant object
	 * @param customer customer object
	 * @param cart customer's shopping cart
	 */
	public void createOrder(
		Restaurant restaurant, 
		Customer customer, 
		ArrayList<OrderItemView> cart
	) {
		// local OrderDAO instance that will use the connection with autoCommit disabled
		OrderDAO orderDAO = new OrderDAO();
		
		try {
			// disable automatic commits on the database connection
			conn.setAutoCommit(false);
			
			// insert the order and get the generated primary key to insert items
			int idPedido = orderDAO.addOrder(conn, restaurant, customer);
			
			for (OrderItemView item: cart) {
				
				// decrease the purchased quantity from stock and store the result to verify
				boolean wasStockAmountUpdated = restaurantProductDAO.decreaseStockAmount(conn, restaurant.getId(), item);
				
				if (!wasStockAmountUpdated) {
					throw new RuntimeException("Insufficient stock");
				}
				
				// instantiate an OrderItem to be inserted into the join table and set its fields
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderNumber(idPedido);
				orderItem.setProductCode(item.getProductNumber());
				orderItem.setQuantity(item.getQuantity());
				
				orderItemDAO.addOrderItem(conn, orderItem);
				
				// commit after each item insertion
				conn.commit();
			}
			
		} catch (Exception e) {
			
			try {
				// on error, roll back all changes made in the database
	            conn.rollback();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
			
			throw new RuntimeException(e);
			
		} finally {
			
			try {
				// re-enable auto commit on the connection for other operations
	            conn.setAutoCommit(true);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			
		}
	}
	
	/**
	 * Returns all orders associated with the given restaurant, filtered by preparation status
	 * @param id restaurant identifier
	 * @param status order status to filter by
	 * @return list of orders
	 */
	public ArrayList<Order> returnOrdersByRestaurant(String id, String status){
		return orderDAO.returnAllOrdersPerRestaurant(conn, id, status);
	}
	
	/**
	 * Returns all orders that a given customer has placed in the system
	 * @param id customer's identifier
	 * @return list of orders
	 */
	public ArrayList<Order> returnOrdersByCustomer(String id){
		return orderDAO.returnAllOrdersPerCustomer(conn, id);
	}
	
	/**
	 * Returns all items associated with a given order
	 * @param orderNumber order id
	 * @return list of OrderItemView for the order
	 */
	public ArrayList<OrderItemView> returnOrderItems(int orderNumber){
		return orderItemDAO.returnAllOrderItem(conn, orderNumber);
	}
	
	/**
	 * Updates delivery information for an order and the delivery person's availability
	 * using a transaction to ensure database consistency.
	 * @param order order to update
	 * @param deliverer delivery person responsible
	 * @param available new availability for the delivery person
	 * @param orderStatus new status for the order
	 * @return true if update succeeds, false otherwise
	 */
	public boolean updateOrderStatus(
			Order order, 
			DeliveryPerson deliverer, 
			short available, 
			String orderStatus ) {

		    try {

			// disable database auto-commit
	        conn.setAutoCommit(false);

		        // update order attributes
	        order.setDeliveryPersonId(deliverer.getId());
	        order.setOrderStatus(orderStatus);

		        // execute update and verify it succeeded
		        if (!orderDAO.updateOrder(conn, order)) {
		        	// rollback and return false if update failed
		            conn.rollback(); 
		            return false;
		        }

		        // update delivery person attributes
	        deliverer.setAvailable(available);

		      // execute update and verify it succeeded
		        if (!deliveryPersonDAO.updateDeliveryPerson(conn, deliverer)) {
		        	// rollback and return false if update failed
		            conn.rollback();
		            return false;
		        }

		        // commit if everything executed correctly and return success
	        conn.commit();
	        return true;

	    } catch (Exception ex) {

		        try {
		            conn.rollback();
		        } catch (SQLException e1) {
		            e1.printStackTrace();
		        }
		
		        ex.printStackTrace();
		        return false;

	    } finally {

		        try {
		            conn.setAutoCommit(true);
		        } catch (SQLException e2) {
		            e2.printStackTrace();
		        }
	    }
	}
	
	/**
	 * return the total value of the order + delivery fee
	 * @param totalValue da compra
	 * @return double
	 */
	public double addDeliveryFee(double totalValue) {
		return totalValue + 8.00;
	}

	private <T> boolean isIndexValid(ArrayList<T> list, int index) {
		return index >= 0 && index < list.size();
	}
	
	private boolean isQuantityValid(RestaurantProductView productView, int quantity) {
		return quantity > 0 && quantity <= productView.getStockAmount(); 
	}
	
}
