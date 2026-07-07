package ui.customer;

import java.sql.Connection;
import java.util.Scanner;

import entities.Customer;
import entities.Address;
import entities.CustomerAddress;
import services.AddressService;

/**
 * Class: CustomerAddressMenu
 *
 * Description:
 * Responsible for presenting address management options to the customer
 *
 * Responsibilities:
 * - provide the user interface
 * - communicate with the service layer
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class CustomerAddressMenu {
	
	private Scanner sc;
	private Address customerAddress;
	private AddressService addressService;
	
	
	public CustomerAddressMenu(Connection conn, Scanner sc) {
		this.addressService = new AddressService(conn);
		this.sc = sc;
	}

	/**
	 * Displays the address interface so the customer can manage it.
	 * There are two states:
	 * - if the customer has an address, show the edit menu
	 * - otherwise, show the add-address menu
	 * @param customer customer object
	 */
	public void displayAddressMenu(Customer customer) {
		
		int option = 9;
		
		// store the customer's address
		customerAddress = addressService.returnCustomerAddress(customer.getId());
		
		if (customerAddress == null) {
			System.out.println("You don't have a registered address!");
			System.out.println("What do you want to do?");
			System.out.println("1- Add new address");
			System.out.println("2- Return");
			
				// choice handling for menu when no address is registered
			while (true) {
				try {
					
					option = sc.nextInt();
					sc.nextLine();
					
					// check if the user's choice is outside the allowed range
					if (!(option > 0 && option <= 2)) {
						System.out.println("Enter a valid option: ");
					}
				
					} catch (Exception e) {
						sc.nextLine();
						System.out.println("Enter only numbers: ");
						option = -1;
					}
			
					switch (option) {
					case 1:
						this.addAddress(customer);
						return;
					
					case 2:
						System.out.println("Returning to the previous menu");
						return;
					}
			
			}
			
		} else {
			
			while (true) {
				System.out.println("\nCURRENT ADDRESS:");
				System.out.println("================================================");
				System.out.println(customerAddress.formatAddress());
				System.out.println("================================================");
				System.out.println("1- Update Postal Code");
				System.out.println("2- Update Street Name");
				System.out.println("3- Update Street Number");
				System.out.println("4- Return");
				System.out.println("================================================\n");
				
				System.out.print("Select what do you want to do: ");
				
				// choice handling for menu when an address is already registered
				try {
					
					option = sc.nextInt();
					sc.nextLine();
					
					// check if the user's choice is outside the allowed range
					if (!(option > 0 && option <= 4)) {
						System.out.println("Enter a valid option: ");
					}
					
				} catch (Exception e) {
					sc.nextLine();
					System.out.println("Enter only numbers: ");
					option = -1;
				}

				// access the menu options
				switch (option) {
					case 1:
						this.updatePostalCode(customerAddress);
						break;
					case 2:
						this.updateStreetName(customerAddress);
						break;
					case 3:
						this.updateStreetNumber(customerAddress);
						break;
					case 4:
						System.out.println("Returning to the previous menu");
						return;
						
					default: 
						System.out.println("Invalid option, try again: ");
				}
				
			}		
			

		} 
		
	}	
	
	/**
	 * Implements the registration of an address for a customer
	 * @param customer customer object
	 */
	private void addAddress(Customer customer) {
		String postalCode;
		String name;
		int number;
	
		// field for CEP validation
		while (true) {
		    System.out.print("Enter your postal code (8 numbers): ");
		    postalCode = sc.nextLine().trim();

		    try {
		        addressService.checkPostalCode(postalCode);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for street name validation
		while (true) {
		    System.out.print("Enter the name of your street: ");
		    name = sc.nextLine().trim();

		    try {
		        addressService.checkName(name);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// validation of the customer's street number
		while (true) {
		    System.out.print("Enter the number of your street: ");
	
		    try {
		    	
		    	number = sc.nextInt();
			    sc.nextLine();
			    
		        addressService.checkNumber(number);
		        break;
		    } catch (Exception e) {
		        System.out.println(e.getMessage());
		        
		    }
		}
		
		System.out.println("\nCONFIRM INFORMATIONS: ");
		System.out.println("================================================");
		System.out.printf("Postal code: %s\n", postalCode);
		System.out.printf("Street name: %s\n", name);
		System.out.printf("Street number: %d\n", number);
		System.out.println("================================================\n");
		System.out.print("Are these informations correct? (y-yes/c-cancel): ");
		
		// validation of the user's choice
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("y")) {
				// instantiate a new CustomerAddress and bind attributes
				CustomerAddress address = new CustomerAddress();
				address.setPostalCode(postalCode);
				address.setCustomerId(customer.getId());
				address.setName(name);
				address.setNumber(number);
				
				
				//chamada do método para cadastro e verificação se houve êxito na ação
				if(addressService.addCustomerAddress(address)) {
					System.out.println("Your added has been added!");
					return;
					
				} else {
					System.out.println("An error has occurred while trying to add your address.");
				}
				
				break;
				
			} else if (opt.equals("n")) {
				System.out.println("Nothing has changed");
				return;
				
			} else {
				System.out.print("Invalid option, try again: ");
			}
			
		}
		
	}

	/**
	 * Implements CEP update
	 * @param address address object
	 */
	private void updatePostalCode(Address address) {
		
		// field for CEP validation
		while (true) {
			System.out.print("Enter your new postal code (8 numbers): ");
			
			String postalCode = sc.next().trim();
			
			try {
				if(addressService.updatePostalCodeCustomerAddress(address, postalCode)) {
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
	 * Implements update of the address name
	 * @param address address object
	 */
	private void updateStreetName(Address address) {
		
		// field for street name validation
		while (true) {
			System.out.print("Enter your new street name: ");
			
			String name = sc.nextLine().trim();
			
			try {
				if(addressService.updateNameCustomerAddress(address, name)) {
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
	 * Implements update of the customer's address number
	 * @param address address object
	 */
	private void updateStreetNumber(Address address) {
		
		// field for street number validation
		while (true) {
			System.out.print("Enter your new street number: ");
	
			try {
				
				int number = sc.nextInt();
				sc.nextLine();
				
				if(addressService.updateNumberCustomerAddress(address, number)) {
					System.out.println("information updated successfully!");
					break;
				} else {
					System.out.println("Error while trying to update informations");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				break;
			}
		}
		
	}
	
}
