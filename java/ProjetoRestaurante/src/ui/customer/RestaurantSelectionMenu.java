package ui.customer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import entities.Customer;
import entities.Restaurant;
import services.OrderService;
import services.RestaurantService;

/**
 * Class: RestaurantSelectionMenu
 *
 * Description:
 * Class responsible for providing an interface for customers to choose
 * a restaurant to place orders.
 *
 * Responsibilities:
 * - Provide interactive menus for restaurant selection
 *
 * @author Rodrigo
 * @since 04-05-2026
 */

public class RestaurantSelectionMenu {
	
	private RestaurantService restaurantService;
	private OrderService orderService;
	private Connection conn;
	private Customer customer;
	private Scanner sc;

	public RestaurantSelectionMenu(Connection conn, Customer cliente, Scanner sc) {
		this.restaurantService = new RestaurantService(conn);
		this.orderService = new OrderService(conn);
		this.customer = cliente;
		this.conn = conn;
		this.sc = sc;
	}
	
	
	/**
	 * Displays the interface for the user to choose which restaurant they want to order from.
	 */
	public void mostrarRestaurantes() {
		int counter = 1; //restaurants enumerator
		int index;		  //user choice
		
		ArrayList<Restaurant> restaurantList = restaurantService.returnRestaurantList();
		
		// prevents the user from advancing if there are no restaurants
		if (restaurantList.isEmpty()) {
			System.out.println("ERROR: There is no registered restaurants on the system!");
			return;
		}
		
		System.out.println("\nRESTAURANT LIST TO MAKE AN ORDER");
		System.out.println("================================================");
		
		for (Restaurant r: restaurantList) {
			System.out.println(counter + "- " + r.getName());
			counter++;
		}
		System.out.println("================================================\n");
		
		System.out.print("Enter the restaurant index you want to buy from: ");
		
		// field for validating the index of the chosen restaurant
		while (true) {
		    try {
		    	index = sc.nextInt();
			    sc.nextLine();
			    
			    index--; // user sees from (1) to (N). Computer sees from (0) to (N-1)
			    
		        orderService.checkIndex(restaurantList, index);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Enter a valid index: ");
		    }
		}
		
		// gets the restaurant chosen by the user from the list based on the index
		Restaurant restaurant = restaurantList.get(index);

		ProductSelectionMenu menuselecaoproduto = new ProductSelectionMenu(conn, sc);
		menuselecaoproduto.mostrarProdutos(restaurant, customer);
		
	}	
	
}
