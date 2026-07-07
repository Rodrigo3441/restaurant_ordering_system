package ui.restaurant;

import java.sql.Connection;
import java.util.Scanner;
import entities.Restaurant;
import services.RestaurantService;

/**
 * Class: RestaurantMenu
 *
 * Description:
 * Class responsible for providing the system interface for restaurants
 *
 * Responsibilities:
 * - provide interactive menus for the customer
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class RestaurantMenu {
	
	private RestaurantService restaurantService;
	private Connection conn;
	private Scanner sc;
	
	/**
	 * Receives a connection to allow communication with the database
	 * 
	 * @param conn
	 */
	public RestaurantMenu(Connection conn, Scanner sc) {
		this.restaurantService = new RestaurantService(conn);
		this.conn = conn;
		this.sc = sc;
	}
	
	/**
	 * Responsible for presenting the initial options for the restaurant to log in or register an account
	 */
	public void displayMainMenu() {
			
			int option = 9;
			
			// validate the user's option input
			while (true) {
				
				System.out.println("\nRESTAURANT MENU");
				System.out.println("================================================");
				System.out.println("1- Sign in");
				System.out.println("2- Create an account");
				System.out.println("3- Return");
				System.out.println("================================================\n");
				
				System.out.print("Select what you want to do: ");
				
				try {
					
					option = sc.nextInt();
					sc.nextLine();
					
					// check whether the user's option is outside the allowed range
					if (!(option >= 0 && option <= 3)) {
						System.out.println("Enter a valid option: ");
					}
					
				} catch (Exception e) {
					sc.nextLine();
					System.out.println("Enter only numbers: ");
					option = -1;
				}
				
				// access menu options			
				switch (option) {
					case 1:
						this.signIn();
						break;
						
					case 2:
						this.createAnAccount();
						break;
						
					case 3:
						System.out.println("returning to the main menu");
						return;
						
					default: 
						System.out.println("Invalid option, try again: ");
				}
				
			}
			
		}
	
	
	/**
	 * Responsible for providing the registration interface for the restaurant
	 */
	private void createAnAccount() {
		
		String restaurantId;
		String name;
		String phone;
		String passcode;
		
		System.out.println("\nRESTAURANT ACCOUNT CREATION");
		
		// field for CNPJ validation
		while (true) {
		    System.out.print("Enter your restaurant ID (14 numbers): ");
		    restaurantId = sc.nextLine().trim();

		    try {
		        restaurantService.checkRestaurantId(restaurantId);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for restaurant name validation
		while (true) {
		    System.out.print("Enter the restaurant name: ");
		    name = sc.nextLine().trim();

		    try {
		        restaurantService.checkName(name);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
				
		// field for phone validation
		while (true) {
		    System.out.print("Enter the restaurant phone number: ");
		    phone = sc.nextLine().trim();

		    try {
		        restaurantService.checkPhone(phone);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
	
		// field for password validation
		while (true) {
		    System.out.print("Enter the restaurant account password: ");
		    passcode = sc.nextLine().trim();

		    try {
		        restaurantService.checkPasscode(passcode);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		System.out.println("\nCONFIRM INFORMATIONS: ");
		System.out.println("================================================");
		System.out.printf("Restaurant ID: %s\n", restaurantId);
		System.out.printf("restaurant name: %s\n", name);
		System.out.printf("Phone number: %s\n", phone);
		System.out.printf("Password: %s\n", passcode);
		System.out.println("================================================\n");
		
		System.out.print("Are these informations correct? (y-yes/n-no): ");
		
		// validate the user's choice
		while (true) {
			
			String option = sc.next();
			
			if (option.equals("y")) {
				// instantiate a new restaurant and set its attributes
				Restaurant restaurant = new Restaurant();
				restaurant.setId(restaurantId);
				restaurant.setName(name);
				restaurant.setPhone(phone);
				restaurant.setPasscode(passcode);
				
				// call the registration method and verify whether it succeeded
				if(restaurantService.addRestaurant(restaurant)) {
					System.out.println("Your account has been created!");
					
				} else {
					System.out.println("An error has occurred while trying to create the restaurant account.");
				}
				
				break;
				
			} else if (option.equals("n")) {
				System.out.println("Nothing has changed");
				return;
				
			} else {
				System.out.print("Invalid option, try again: ");
			}
			
		}
	}
	
	/**
	 * Responsible for receiving the restaurant credentials and passing them to the service layer
	 */
	private void signIn() {
		System.out.print("Enter your restaurant ID to log in to your account: ");
		
		String cnpj = sc.next().trim();
		
		// store all restaurant information
		Restaurant r = restaurantService.returnRestaurant(cnpj);
		
		// check whether a restaurant was returned
		if(r != null) {

			System.out.print("Enter the restaurant password: ");
			
			String senha = sc.next().trim();
			
			// verify whether the restaurant password matches the entered password
			if (r.getPasscode().equals(senha)) {
				System.out.println("Welcome, " + r.getName() + "!");
				this.menuRestauranteLogado(r);
			} else {
				System.out.println("User or password invalid.");
			}
			
		} else {
			System.out.println("This restaurant ID is not on the system. (maybe you entered the wrong ID)");
		}
		
	}
	
	/**
	 * Responsible for offering the action menu for the logged-in restaurant
	 * @param restaurant restaurant object
	 */
	private void menuRestauranteLogado(Restaurant restaurant) {
		int option = -1;
		
		while (true) {
			System.out.println("\nRESTAURANT MANAGMENT MENU");
			System.out.println("================================================");
			System.out.println("What do you want to do?");
			System.out.println("1- Update restaurant profile");
			System.out.println("2- Manage deliverers");
			System.out.println("3- Manage products");
			System.out.println("4- Manage orders");
			System.out.println("5- Sign out");
			System.out.println("================================================\n");
			
			System.out.print("Select what you want to do: ");
			
			try {
				
				option = sc.nextInt();
				
				// check whether the user's option is outside the allowed range
				if (!(option >= 0 && option <= 5)) {
					System.out.println("Enter a valid option: ");
				}
				
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("Enter only numbers: ");
				option = -1;
			}
			
			switch (option) {
				case 1:
					RestaurantProfileMenu restaurantProfileMenu = new RestaurantProfileMenu(restaurantService, conn, sc);
					restaurantProfileMenu.displayProfileMenu(restaurant);
					break;
				case 2:
					RestaurantDeliveryPersonMenu restaurantDeliveryPersonMenu = new RestaurantDeliveryPersonMenu(conn, sc);
					restaurantDeliveryPersonMenu.displayDelivererMenu();
					break;
				case 3:
					RestaurantProductMenu restaurantProductMenu = new RestaurantProductMenu(conn, sc);
					restaurantProductMenu.displayProductMenu(restaurant);
					break;
				case 4:
					RestaurantOrderMenu restaurantOrderMenu = new RestaurantOrderMenu(conn, sc);
					restaurantOrderMenu.displayOrderMenu(restaurant);
					break;
				case 5:
					System.out.println("See you next time.");
					return;
			}
			
		}
		
	}

}
