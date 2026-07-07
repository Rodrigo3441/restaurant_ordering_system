package ui.customer;

import java.sql.Connection;
import java.util.Scanner;

import entities.Customer;
import services.CustomerService;

/**
 * Class: CustomerProfileMenu
 *
 * Description:
 * Provides the user interface for customer profile management.
 *
 * Responsibilities:
 * - present interactive menus for the user to edit their profile
 *
 * @author Rodrigo
 * @since 24-04-2026
 */

public class CustomerProfileMenu {
	
	private Scanner sc;
	private CustomerService customerService;
	private Connection conn;
	
	public CustomerProfileMenu(CustomerService customerService, Connection conn, Scanner sc) {
		this.customerService = customerService;
		this.conn = conn;
		this.sc = sc;
	}
	

	/**
	 * Displays the profile editing actions menu
	 * @param customer Customer object
	 */
	public void displayProfileMenu(Customer customer) {
		int option = 9;
		
		// validate the user's menu option input
		while (true) {
			
			System.out.println("\nCUSTOMER PROFILE MENU");
			System.out.println("================================================");
			System.out.println("1- Update first name");
			System.out.println("2- Update middle name");
			System.out.println("3- Update last name");
			System.out.println("4- Update phone number");
			System.out.println("5- Update email address");
			System.out.println("6- Update passcode");
			System.out.println("7- Update address");
			System.out.println("8- exit profile menu");
			System.out.println("================================================\n");
			System.out.print("Select what you want to do: ");
			
			try {
				
				option = sc.nextInt();
				sc.nextLine();
				
				// check if the user's option is outside the allowed range
				if (!(option >= 0 && option <= 8)) {
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
					this.updateFirstName(customer);
					break;
				case 2:
					this.updateMiddleName(customer);
					break;
				case 3:
					this.updateLastName(customer);
					break;
				case 4:
					this.updatePhone(customer);
					break;
				case 5:
					this.updateEmail(customer);
					break;
				case 6:
					this.updatePasscode(customer);		
					break;
				case 7:
					CustomerAddressMenu customerAddressMenu = new CustomerAddressMenu(conn, sc);
					customerAddressMenu.displayAddressMenu(customer);
					break;
				case 8:
					System.out.println("Returning to the previous menu");
					return;
				
			}

		}
		
	}
	
	/**
	 * Updates the customer's first name
	 * @param customer customer object
	 */
	private void updateFirstName(Customer customer) {


		// input loop for validating first name
		while (true) {
			System.out.print("Enter your first name (3-20 letters): ");
			
			String newFirstName = sc.next().trim();
			
			try {
				if(customerService.updateFirstName(customer, newFirstName)) {
					System.out.println("Information updated successfully!");
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
	 * Updates the customer's middle name
	 * @param customer customer object
	 */
	private void updateMiddleName(Customer customer) {


		// input loop for validating middle name
		while (true) {
			System.out.print("Enter your middle name (3-40 letters): ");
			
			String newMiddleName = sc.next().trim();
			
			try {
				if(customerService.updateMiddleName(customer, newMiddleName)) {
					System.out.println("Information updated successfully!");
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
	 * Updates the customer's last name
	 * @param customer customer object
	 */
	private void updateLastName(Customer customer) {


		// input loop for validating last name
		while (true) {
			System.out.print("Enter your last name (3-20 letters): ");
			
			String newLastName = sc.next().trim();
			
			try {
				if(customerService.updateLastName(customer, newLastName)) {
					System.out.println("Information updated successfully!");
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
	 * Updates the customer's phone number
	 * @param customer customer object
	 */
	private void updatePhone(Customer customer) {


		// input loop for validating phone number
		while (true) {
			System.out.print("Enter your phone number (maximum of 11 numbers): ");
			
			String phone = sc.next().trim();
			
			try {
				if(customerService.updatePhone(customer, phone)) {
					System.out.println("Information updated successfully!");
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
	 * Updates the customer's email
	 * @param customer customer object
	 */
	private void updateEmail(Customer customer) {


		// input loop for validating email
		while (true) {
			System.out.print("Enter your email address: ");
			
			String email = sc.next().trim();
			
			try {
				if(customerService.updateEmail(customer, email)) {
					System.out.println("Information updated successfully!");
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
	 * Updates the customer's password
	 * @param customer customer object
	 */
	private void updatePasscode(Customer customer) {


		// input loop for validating password
		while (true) {
			System.out.print("Enter your password: ");
			
			String passcode = sc.next().trim();
			
			try {
				if(customerService.updatePasscode(customer, passcode)) {
					System.out.println("Information updated successfully!");
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
