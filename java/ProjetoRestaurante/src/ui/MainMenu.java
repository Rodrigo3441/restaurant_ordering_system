package ui;

import java.sql.Connection;
import java.util.Scanner;

import ui.customer.CustomerMenu;
import ui.restaurant.RestaurantMenu;

/**
 * Class: MainMenu
 *
 * Description:
 * Responsible for displaying the main user interaction menu of the system.
 *
 * Responsibilities:
 * - Show access options for Customers and Restaurants
 * - Perform basic navigation between menus
 *
 * @author Rodrigo
 * @since 23-04-2026
 */

public class MainMenu {
	
	private Scanner sc;
	private Connection conn;
	
	/**
	 * Constructor
	 *
	 * @param conn Database connection used by menus
	 * @param sc   Scanner for reading user input
	 */
	public MainMenu(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}
	
	/**
	 * Display method
	 *
	 * Responsible for showing the main menu on the screen for system users,
	 * whether Customers or Restaurants.
	 */
	public void display() {
		
		int option = 9;
		
		// validate user's menu option input
		while (true) {
			
			System.out.println("\nMAIN MENU");
			System.out.println("================================================");
			System.out.println("Restaurant Ordering System");
			System.out.println("Welcome to our system!\n");
			
			System.out.println("1- Access customer menu");
			System.out.println("2- Access restaurant menu");
			System.out.println("3- Exit system");
			System.out.println("================================================\n");
			System.out.print("Select what you want to do: ");
			
			try {
				
				option = sc.nextInt();
				
				// check if the user's option is outside the allowed range
				if (!(option >= 0 && option <= 3)) {
					System.out.println("Enter a valid option: ");
				}
				
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("Enter only numbers: ");
				option = -1;
			}
			
			// handle menu options
			switch (option) {
				case 1:
					CustomerMenu customerMenu = new CustomerMenu(conn, sc);
					customerMenu.displayMainMenu();
					break;
	
				case 2:
					RestaurantMenu restaurantMenu = new RestaurantMenu(conn, sc);
					restaurantMenu.mostrarMenuPrincipal();
					break;
					
				case 3:
					System.out.println("Thanks for using our system.");
					return;
			}

		}
				
	}
	
}
