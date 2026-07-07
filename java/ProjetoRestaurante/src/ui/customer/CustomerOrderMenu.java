package ui.customer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

import entities.Customer;
import entities.Order;
import services.OrderService;
import view.OrderItemView;

/**
 * Class: CustomerOrderMenu
 *
 * Description:
 * Class responsible for displaying all orders that the customer has already made
 *
 * Responsibilities:
 * - provide interactive menus for the user
 *
 * @author Rodrigo
 * @since 08-05-2026
 */

public class CustomerOrderMenu {
	
	private OrderService orderService;
	private Scanner sc;
	
	public CustomerOrderMenu(Connection conn, Scanner sc) {
		this.orderService = new OrderService(conn);
		this.sc = sc;		
	}
	
	/**
	 * Displays all orders that the customer has already made in the system
	 * @param customer Customer object
	 */
	public void displayCustomerOrders(Customer customer) {
		int option;
		
		ArrayList<Order> orderList = orderService.returnOrdersByCustomer(customer.getId());
		
		if (orderList.isEmpty()) {
			System.out.println("You haven't ordered anything on the system!");
			return;
		}
		
		while (true) {
		
			System.out.println("\nYOUR ORDERS");
			System.out.println("============================================================================");
					
			for (int i = 0; i < orderList.size(); i++) {
					System.out.println(i+1 + "-  " + orderList.get(i));
			}
			
			System.out.println("============================================================================\n");
			
			System.out.println("================================================");
			System.out.println("1- Check order details");
			System.out.println("2- return");
			System.out.println("================================================\n");
			
			System.out.print("Select what do you want to do: ");
			
			while (true) {
				try {
					option = sc.nextInt();
					sc.nextLine();
					
					// Check if the user's option is outside the allowed range
					if (option >= 1 && option <= 2) {
						break;
					} else {
						System.out.println("Enter a valid option: ");
					}
					
				} catch (Exception e) {
					sc.nextLine();
					System.out.println("Enter only numbers: ");
					option = -1;
				}
			}
			
			switch (option) {
				case 1:
					this.displayOrderDetails(orderList);
					break;
				case 2:
					System.out.println("Returning to the previous menu");
					return;
			}
		
		}
		
	}
	
	/**
	 * Displays all items in a specific order
	 * @param orderList list containing all orders
	 */
	private void displayOrderDetails(ArrayList<Order> orderList) {
		int index;
		double totalValue = 0;
		System.out.print("Enter the index of the order you want to check details: ");
		
		// Field for validating user input
		while (true) {
		    try {
		    	index = sc.nextInt();
			    sc.nextLine();
			    
			    index--; // User sees from (1) to (N). Computer sees from (0) to (N-1)
			    
		        orderService.checkIndex(orderList, index);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Enter a valid index: ");
		    }
		}
		
		// Stores the order code to avoid code repetition
		int orderNumber = orderList.get(index).getOrderNumber();
		
		// Stores all items from the order the user chose
		ArrayList<OrderItemView> orderItemList = orderService.returnOrderItems(orderNumber);
		
		System.out.printf("\nSHOWING ITEMS OF THE ORDER %d:\n", orderNumber);
		System.out.println("============================================================================");
		
		for (OrderItemView ip: orderItemList) {
			System.out.println("Product: " + ip.getName() + " | Quantity: " + ip.getQuantity() + " | Unit price: " + ip.getPrice());
			totalValue += ip.getQuantity() * ip.getPrice();
		}
	
		System.out.println("============================================================================");
		System.out.println("SUBTOTAL");
		System.out.printf("R$ %.2f\n", totalValue);
		System.out.println("============================================================================");
		System.out.println("SUBTOTAL AFTER DISCOUNT");
		
		totalValue = orderService.calculateDiscount(totalValue);
		
		System.out.printf("R$ %.2f\n", totalValue);
		System.out.println("============================================================================");
		System.out.println("DELIVERY FEE:");
		System.out.println("R$ 8.00 added to the total value");
		
		totalValue = orderService.addDeliveryFee(totalValue);
		
		System.out.println("============================================================================");
		System.out.println("TOTAL VALUE");
		System.out.printf("%.2f\n", totalValue);
		System.out.print("============================================================================\n\n");
		
		
		System.out.println("Press any key to continue");
		sc.nextLine();
	}
}
