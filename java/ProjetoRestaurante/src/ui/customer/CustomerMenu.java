package ui.customer;

import java.sql.Connection;
import java.util.Scanner;
import entities.Customer;
import services.CustomerService;

/**
 * Class: CustomerMenu
 *
 * Description:
 * Provides the user interface for customer interactions.
 *
 * Responsibilities:
 * - present interactive menus for customers
 *
 * @author Rodrigo
 * @since 24-04-2026
 */

public class CustomerMenu {
	
	private CustomerService customerService;
	private Connection conn;
	private Scanner sc;
	
	/**
	 * Receives a database connection to allow communication with the DB
	 *
	 * @param conn database connection
	 */
	public CustomerMenu(Connection conn, Scanner sc) {
		this.customerService = new CustomerService(conn);
		this.conn = conn;
		this.sc = sc;
	}
	
	/**
	 * Presents initial options for a customer to log in or register an account
	 */
	public void displayMainMenu() {
			
			int option = 9;
			
			// validate user's menu option input
			while (true) {
				
				System.out.println("\nCUSTOMER MENU");
				System.out.println("================================================");
				System.out.println("1- Sign in");
				System.out.println("2- Create an account");
				System.out.println("3- Return");
				System.out.println("================================================\n");
				System.out.print("Select what you want to do: ");

				
				try {
					
					option = sc.nextInt();
					sc.nextLine();
					
					// check if the user's option is outside the allowed range
					if (!(option >= 0 && option <= 3)) {
						System.out.println("Enter a valid option: ");
					}
					
				} catch (Exception e) {
					sc.nextLine();
					System.out.println("Enter only numbers: ");
					option = -1;
				}
				
				//access the corresponding method based on the user's choice			
				switch (option) {
					case 1:
						this.signIn();
						break;
						
					case 2:
						this.createAnAccount();
						break;
						
					case 3:
						System.out.println("Returning to the main menu");
						return;
						
					default: 
						System.out.println("Invalid option, try again: ");
				}
	
			}
		
		}
	
	
	/**
	 * Provides the registration interface for a new customer
	 */
	private void createAnAccount() {
		
		String customerId;
		String firstName;
		String middleName;
		String lastName;
		String phone;
		String email;
		String passcode;
		
		System.out.println("\nCUSTOMER ACCOUNT CREATION");
		
		// field for CPF validation
		while (true) {
		    System.out.print("Enter your customer ID (11 characters): ");
		    customerId = sc.nextLine().trim();

		    try {
		        customerService.checkCustomerId(customerId);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for first name validation
		while (true) {
		    System.out.print("Enter your first name: ");
		    firstName = sc.nextLine().trim();

		    try {
		        customerService.checkFirstName(firstName);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for last name validation
		while (true) {
		    System.out.print("Enter your last name: ");
		    lastName = sc.nextLine().trim();

		    try {
		        customerService.checkLastName(lastName);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for middle name validation
		while (true) {
		    System.out.print("Enter your middle name: ");
		    middleName = sc.nextLine().trim();

		    try {
		        customerService.checkMiddleName(middleName);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
				
		// field for phone validation
		while (true) {
		    System.out.print("Enter your phone number: ");
		    phone = sc.nextLine().trim();

		    try {
		        customerService.checkPhone(phone);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for email validation
		while (true) {
		    System.out.print("Enter your email address: ");
		    email = sc.nextLine().trim();

		    try {
		        customerService.checkEmail(email);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
	
		// field for password validation
		while (true) {
		    System.out.print("Enter your password for the account: ");
		    passcode = sc.nextLine().trim();

		    try {
		        customerService.checkPasscode(passcode);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		System.out.println("\nCONFIRM INFORMATIONS: ");
		System.out.println("================================================");
		System.out.printf("Customer ID: %s\n", customerId);
		System.out.printf("First name: %s\n", firstName);
		System.out.printf("Middle name: %s\n", middleName);
		System.out.printf("Last name: %s\n", lastName);
		System.out.printf("Phone number: %s\n", phone);
		System.out.printf("Email address: %s\n", email);
		System.out.printf("Account password: %s\n", passcode);
		System.out.println("================================================\n");
		System.out.print("Are these informations correct? (y-yes /c-cancel): ");
		
		// validate user's confirmation choice
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("y")) {
				// instantiate a new customer and set attributes
				Customer customer = new Customer();
				customer.setId(customerId);
				customer.setFirstName(firstName);
				customer.setMiddleName(middleName);
				customer.setLastName(lastName);
				customer.setPhone(phone);
				customer.setEmail(email);
				customer.setPasscode(passcode);
				
					// call service method to register and check success
				if(customerService.addCustomer(customer)) {
					System.out.println("Your account has been created!");
					
				} else {
					System.out.println("An error has occurred while trying to create your account.");
				}
				
				break;
				
			} else if (opt.equals("c")) {
				System.out.println("Nothing has changed");
				return;
				
			} else {
				System.out.print("Invalid option, try again: ");
			}
			
		}
	}
	
	/**
	 * Collects user credentials and forwards them to the service layer
	 */
	private void signIn() {
		System.out.print("Enter your customer ID to log in to your account: ");
		
		String customerId = sc.next().trim();
		
		// retrieve the customer information
		Customer customer = customerService.returnCustomer(customerId);
		
		// check if a customer was returned
		if(customer != null) {

			System.out.print("Enter your account password: ");
			
			String passcode = sc.next().trim();
			
			// verify that the stored password matches the entered password
			if (customer.getPasscode().equals(passcode)) {
				System.out.println("Welcome, " + customer.getFirstName() + "!");
				this.customerLoggedMenu(customer);
			} else {
				System.out.println("User or password invalid.");
			}
			
		} else {
			System.out.println("This customer ID is not on the system. (maybe you entered the wrong ID)");
		}
		
	}
	
	/**
	 * Presents action menu for a logged-in customer
	 * @param c customer object
	 */
	private void customerLoggedMenu(Customer c) {
		int option = -1;
		
		while (true) {
			System.out.println("\nCUSTOMER MENU");
			System.out.println("================================================");
			System.out.printf("What do you want to do today, %s?\n", c.getFirstName());
			System.out.println("1- Update profile");
			System.out.println("2- Check your orders");
			System.out.println("3- Create an order");
			System.out.println("4- Sign out");
			System.out.println("================================================\n");
			
			System.out.print("Select what you want to do: ");
			
			try {
				
				option = sc.nextInt();
				sc.nextLine();
				
					// check if the user's option is outside the allowed range
				if (!(option >= 0 && option <= 4)) {
					System.out.println("Enter a valid option: ");
				}
				
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("Enter only numbers: ");
				option = -1;
			}
			
			switch (option) {
				case 1:
					CustomerProfileMenu customerProfileMenu = new CustomerProfileMenu(customerService, conn, sc);
					customerProfileMenu.displayProfileMenu(c);
					break;
				case 2:
					CustomerOrderMenu customerOrderMenu = new CustomerOrderMenu(conn, sc);
					customerOrderMenu.displayCustomerOrders(c);
					break;
				case 3:
					RestaurantSelectionMenu restaurantSelectionMenu = new RestaurantSelectionMenu(conn, c, sc);
					restaurantSelectionMenu.mostrarRestaurantes();
					break;
				case 4:
					System.out.println("See you next time.");
					return;
			}
			
		}
		
	}

}
