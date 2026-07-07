package ui.customer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import entities.Customer;
import entities.Restaurant;
import services.OrderService;
import view.OrderItemView;

/**
 * Class: OrderConfirmationMenu
 *
 * Description:
 * Class responsible for providing a summary of the customer's order before they confirm the purchase
 *
 * Responsibilities:
 * - provide interface
 * - communicate with the service layer
 *
 * @author Rodrigo
 * @since 05-05-2026
 */


public class OrderConfirmationMenu {
	
	private OrderService orderService;
	private Scanner sc;
	
	public OrderConfirmationMenu(Connection conn, Scanner sc) {
		this.orderService = new OrderService(conn);
		this.sc = sc;
	}
	
	
	/**
	 * Displays all order details for the user, asks for confirmation, and returns whether the order was confirmed or not
	 * @param restaurant restaurant object
	 * @param customer customer object
	 * @param cart customer's shopping cart
	 * @return true if the order was created
	 */
	public boolean displayOrderDetails(
			Restaurant restaurant, 
			Customer customer, 
			ArrayList<OrderItemView> cart) {
		
		double totalValue = 0;
		
		System.out.println("\n============================================================================");
		System.out.println("ORDER DETAILS");
		System.out.println("============================================================================");
		System.out.printf("Customer name: %s %s\n", customer.getFirstName(), customer.getLastName());
		
		System.out.println("============================================================================");
		System.out.println("Product list:");
		
		for (int i = 0; i < cart.size(); i++) {
			double quantityPrice = cart.get(i).getPrice()*cart.get(i).getQuantity();
			totalValue += quantityPrice;
			System.out.println(	cart.get(i));
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
		
		System.out.println("Do you want to confirm your order?");
		
		int userChoice = this.displayChoiceMenu();
		
		switch (userChoice) {
			case 1:
				System.out.println("Order placed successfully!");
				orderService.createOrder(restaurant, customer, cart);
				return true;
			case 2:
				System.out.println("returning to the order menu");
		}
		
		return false;	
	}
	
	/**
	 * Displays the action menu for the user to confirm the order, cancel, or add more products
	 * @return selected action number
	 */
	private int displayChoiceMenu() {
		int option;
	
		System.out.println("================================================");
		System.out.println("1- Confirm order");
		System.out.println("2- return");
		System.out.println("================================================\n");
		
		System.out.print("Select what you want to do: ");
			
		while (true) {
			try {
				option = sc.nextInt();
				sc.nextLine();
				
				// check if the user's option is outside the allowed range
				if (option >= 1 && option <= 2) {
					// return the user's chosen action
					return option; 
				} else {
					System.out.println("Enter a valid option: ");
				}
				
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("Enter only numbers: ");
				option = -1;
			}
		}
		
	}
	
}
