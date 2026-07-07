package ui.restaurant;

import java.sql.Connection;
import java.util.Scanner;

import entities.Restaurant;
import services.RestaurantService;

/**
 * Class: RestaurantProfileMenu
 *
 * Description:
 * Class responsible for providing the system interface for the restaurant
 *
 * Responsibilities:
 * - provide interactive menus for the user
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class RestaurantProfileMenu {
	
	private RestaurantService restaurantService;
	private Connection conn;
	private Scanner sc;
	
	public RestaurantProfileMenu(RestaurantService servicorestaurante, Connection conn, Scanner sc) {
		this.restaurantService = servicorestaurante;
		this.conn = conn;
		this.sc = sc;
	}
	

	/**
	 * Responsible for displaying restaurant profile customization actions
	 * @param r Restaurant object
	 */
	public void displayProfileMenu(Restaurant r) {
		int option = 9;
		
		// validation of the user's option input
		while (true) {
			
			System.out.println("\nRESTAURANT PROFILE");
			System.out.println("================================================");
			System.out.println("1- Update name");
			System.out.println("2- Update phone number");
			System.out.println("3- Update password");
			System.out.println("4- Update address");
			System.out.println("5- Return");
			System.out.println("================================================\n");
			
			System.out.print("Select what you want to do: ");
			
			try {
				
				option = sc.nextInt();
				sc.nextLine();
				
				// check if the user's option is outside the allowed range
				if (!(option >= 0 && option <= 6)) {
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
					this.updateName(r);
					break;
				case 2:
					this.updatePhone(r);
					break;
				case 3:
					this.updatePasscode(r);
					break;
				case 4:
					RestaurantAddressMenu restaurantAddressMenu = new RestaurantAddressMenu(conn, sc);
					restaurantAddressMenu.displayAddressMenu(r);
					break;
				case 5:
					System.out.println("Returning to the previous menu");
					return;
				
			}

		} 
		
	}
	
	/**
	 * Responsible for updating the restaurant name
	 * @param r restaurant object
	 */
	private void updateName(Restaurant r) {
		// field for restaurant name validation
		while (true) {
			System.out.print("Enter the restaurant name (3-40 letters): ");
			
			String name = sc.nextLine().trim();
			
			try {
				if(restaurantService.updateName(r, name)) {
					System.out.println("information updated successfully!");
					break;
				} else {
					System.out.println("Error while trying to update informations");
				}
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	/**
	 * Responsible for updating the restaurant phone
	 * @param r restaurant object
	 */
	private void updatePhone(Restaurant r) {
		// field for phone validation
		while (true) {
			System.out.print("Enter the new phone number (maximum of 11 numbers): ");
			
			String phone = sc.next().trim();
			
			try {
				if(restaurantService.updatePhone(r, phone)) {
					System.out.println("information updated successfully!");
					break;
				} else {
					System.out.println("Error while trying to update informations");
				}
			} catch (IllegalArgumentException  e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Responsible for updating the restaurant password
	 * @param r
	 */
	private void updatePasscode(Restaurant r) {
		// field for password validation
		while (true) {
			System.out.print("Enter the new password of the restaurant: ");
			
			String passcode = sc.next().trim();
			
			try {
				if(restaurantService.updatePasscode(r, passcode)) {
					System.out.println("information updated successfully!");
					break;
				} else {
					System.out.println("Error while trying to update informations");
				}
			} catch (IllegalArgumentException  e) {
				System.out.println(e.getMessage());
			}
		}	
			
	}
	
}
