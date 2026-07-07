package ui.restaurant;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import entities.DeliveryPerson;
import entities.Order;
import entities.Restaurant;
import services.DeliveryPersonService;
import services.OrderService;

/**
 * Class: RestaurantOrderMenu
 *
 * Description:
 * Class responsible for providing an interface for the restaurant to manage its orders
 *
 * Responsibilities:
 * - present interactive menus to the user
 *
 * @author Rodrigo
 * @since 07-05-2026
 */

public class RestaurantOrderMenu {
	private OrderService orderService;
	private DeliveryPersonService deliveryPersonService;
	private Scanner sc;
	
	public RestaurantOrderMenu(Connection conn, Scanner sc) {
		this.orderService = new OrderService(conn);
		this.deliveryPersonService = new DeliveryPersonService(conn);
		this.sc = sc;
	}
		
	/**
	 * Displays all restaurant orders so the restaurant can manage them
	 * @param restaurant restaurant object
	 */
	public void displayOrderMenu(Restaurant restaurant) {
		
		int option;
		
		while (true) {
		
			System.out.println("\nORDER MANAGEMENT MENU");
			System.out.println("================================================");
			System.out.println("1- Manage orders that are being prepared");
			System.out.println("2- Manage orders that were shipped");
			System.out.println("3- Check orders completed");
			System.out.println("4- Return");
			System.out.println("================================================\n");
			
			System.out.print("Choose what you want to do: ");
				
			while (true) {
				try {
					option = sc.nextInt();
					sc.nextLine();
					
					// verify if user option is within allowed range
					if (option >= 1 && option <= 4) {
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
					this.managePreparingOrders(restaurant.getId());
					break;
				case 2:
					this.manageShippedOrders(restaurant.getId());
					break;
				case 3:
					this.checkCompletedOrders(restaurant.getId());
					break;
				case 4: 
					System.out.println("Returning to the previous menu");
					return;
			}
			
		}
		
	}
	
	/**
	 * Provides a visual interface for the restaurant to manage orders that are still being prepared
	 * @param id restaurant cnpj
	 */
	private void managePreparingOrders(String id) {

		// list with all orders to allow access to the first position (oldest order)
		ArrayList<Order> orderList = orderService.returnOrdersByRestaurant(id, "Preparing");
		
		// prevent operations if the list is empty
		if (orderList.isEmpty()) {
			System.out.println("There is no orders being prepared at the moment!");
			return;
		}
		
		System.out.println("SHOWING ALL ORDERS BEING PREPARED BY THE RESTAURANT: ");
		System.out.println("\n============================================================================");
		
		// reverse the list to show the oldest orders first
		Collections.reverse(orderList);
		
		for (Order p: orderList) {
			System.out.println(p);
		}
		
		System.out.println("============================================================================\n");
		
		
		System.out.printf("Next order to have your status updated: %d\n", orderList.get(0).getOrderNumber());
		System.out.print("Do you want to assign a deliverer to the order and update the status? (y-yes/n-no): ");
		
		// ask the user if they want to assign a delivery person and update the order status
		while (true) {
			String choice = sc.next().trim().toLowerCase();
		
			switch (choice) {
				case "y":	
					this.assignDelivererOrder(orderList.get(0));			
					return;
				case "n":
					System.out.println("Nothing has changed");
					return;
				default:
					System.out.print("Enter a valid option: ");
			}
		}
		
	}
	
	/**
	 * Assigns a delivery person to an order and updates availability status from 0 (free) to 1 (busy)
	 * @param order order object
	 */
	private void assignDelivererOrder(Order order) {
		int index;
		
		// list to store all delivery persons in the system
		ArrayList<DeliveryPerson> delivererList = deliveryPersonService.returnDeliveryPersonList();
		
		// prevent operation if the delivery person list is empty
		if (delivererList.isEmpty()) {
			System.out.println("There is no deliverers available at the moment!");
			return;
		}
		
		// remove all occupied delivery persons from the list for better visibility
		for (int i = 0; i < delivererList.size(); i++) {
			if (delivererList.get(i).getAvailable() == 1) {
				delivererList.remove(i);
			}
		}
		
		System.out.println("\nDELIVERERS CURRENTLY AVAILABLE");
		System.out.println("============================================================================");
				
		for (int i = 0; i < delivererList.size(); i++) {
				System.out.println(i+1 + "-  " + delivererList.get(i));
		}
		
		System.out.println("============================================================================\n");
		System.out.printf("Enter the index of the deliverer you want to assign to the order %d: ", order.getOrderNumber());
		
	
		// field to validate user input
		while (true) {
		    try {
		    	index = sc.nextInt();
		    	sc.nextLine();
		    	
		    	index--; // user sees from (1) to (N). Computer sees from (0) to (N-1)
		    	
		        orderService.checkIndex(delivererList, index);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Enter a valid index: ");
		    }
		}
		
		// from the available delivery persons list, store the chosen delivery person
		DeliveryPerson entregador = delivererList.get(index);
		
		
		System.out.printf("Do you want to confirm the assignment of the deliverer %s to the order %d? (y-yes/n-no): ", entregador.getId(), order.getOrderNumber());
		// restaurant confirms whether to assign the delivery person to the order
		while (true) {
			String choice = sc.next().trim().toLowerCase();
		
			switch (choice) {
				case "y":	
					if (orderService.updateOrderStatus(order, entregador, (short) 1, "Shipped")) {
						System.out.printf("Deliverer assigned to the order %d successfully!\n", order.getOrderNumber());
					} else {
						System.out.println("An error has occurred");
					}
					return;
				case "n":
					System.out.println("Nothing has changed");
					return;
				default:
					System.out.print("Enter a valid option: ");
			}
		}
				
	}
	
	/**
	 * Provides a visual interface for the restaurant to mark an order as completed
	 * @param id restaurant cnpj
	 */
	private void manageShippedOrders(String id) {
		
		ArrayList<Order> orderList = orderService.returnOrdersByRestaurant(id, "Shipped");
		
		if (orderList.isEmpty()) {
			System.out.println("There is no shipped orders at the moment!");
			return;
		}
		
		System.out.println("ORDERS THAT ARE BEING SHIPPED: ");
		System.out.println("\n============================================================================");
		
		// reverse the list to show the oldest orders first
		Collections.reverse(orderList);
		
		for (int i = 0; i < orderList.size(); i++) {
			System.out.println(i+1 + "-  " + orderList.get(i));
		}
		
		System.out.println("============================================================================\n");
		
		
		System.out.print("Do you want to mark an order as delivered? (y-yes/n-no): ");
		
		// ask the user if they want to mark an order as completed
		while (true) {
			String choice = sc.next().trim().toLowerCase();
		
			switch (choice) {
				case "y":	
					this.markAsDelivered(orderList);			
					return;
				case "n":
					System.out.println("Nothing has changed");
					return;
				default:
					System.out.print("Enter a valid option: ");
			}
		}
		
	}
	
	/**
	 * Receives the index of the order the restaurant wants to mark as completed
	 * @param orderList list of orders
	 */
	private void markAsDelivered(ArrayList<Order> orderList) {
		int index;
		System.out.print("Enter the index of the order you want to mark as delivered: ");
		
		// field to validate user input
		while (true) {
		    try {
		    	index = sc.nextInt();
		    	sc.nextLine();
		    	
		    	index--; // user sees from (1) to (N). Computer sees from (0) to (N-1)
		    	
		        orderService.checkIndex(orderList, index);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Enter a valid index: ");
		    }
		}
		
		Order order = orderList.get(index);
		DeliveryPerson deliverer = deliveryPersonService.returnDeliveryPerson(order.getDeliveryPersonId());
		
		System.out.printf("Do you want to mark the order %d as delivered? (y-yes/n-no): ", order.getOrderNumber());
		// restaurant confirms whether to mark the order as delivered
		while (true) {
			String choice = sc.next().trim().toLowerCase();
		
			switch (choice) {
				case "y":	
					if (orderService.updateOrderStatus(order, deliverer, (short) 0, "Delivered")) {
						System.out.printf("Order %d delivered successfully!\n", order.getOrderNumber());
					} else {
						System.out.println("An error has occurred");
					}
					return;
				case "n":
					System.out.println("Nothing has changed");
					return;
				default:
					System.out.print("Enter a valid option: ");
			}
		}
		
	}
	
	/**
	 * Shows all orders that have already been marked as delivered by the restaurant
	 * @param id restaurant cnpj
	 */
	private void checkCompletedOrders(String id) {
		
		ArrayList<Order> orderList = orderService.returnOrdersByRestaurant(id, "Delivered");
		
		// prevent operations if the list is empty
		if (orderList.isEmpty()) {
			System.out.println("There are no orders delivered in the restaurat!");
			return;
		}
		
		System.out.println("DELIVERED ORDERS: ");
		System.out.println("\n============================================================================");
		
		// reverse the list to show the oldest orders first
		Collections.reverse(orderList);
		
		for (int i = 0; i < orderList.size(); i++) {
			System.out.println(i+1 + "-  " + orderList.get(i));
		}
		
		System.out.println("============================================================================\n");
		System.out.println("Press any key to continue");
		sc.nextLine();
	
	}
	
}
