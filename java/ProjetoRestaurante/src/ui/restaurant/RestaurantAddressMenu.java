package ui.restaurant;

import java.sql.Connection;
import java.util.Scanner;

import entities.Address;
import entities.RestaurantAddress;
import entities.Restaurant;
import services.AddressService;

/**
 * Class: RestaurantAddressMenu
 *
 * Description:
 * Class responsible for providing options for the restaurant to manage its address
 *
 * Responsibilities:
 * - provide user interface
 * - communicate with the service layer
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class RestaurantAddressMenu {

	private Address restaurantAddress;
	private AddressService addressService;
	private Scanner sc;
	
	
	public RestaurantAddressMenu(Connection conn, Scanner sc) {
		this.addressService = new AddressService(conn);
		this.sc = sc;
	}

	/**
	 * Responsible for displaying the address interface for the restaurant to manage
	 * has two states:
	 * if the restaurant has an address displays one menu, otherwise another
	 * @param restaurant restaurant object
	 */
	public void displayAddressMenu(Restaurant restaurant) {
		
		int option = 9;
		
		//stores the restaurant's address
		restaurantAddress = addressService.returnRestaurantAddress(restaurant.getId());
		
		if (restaurantAddress == null) {
			System.out.println("You don't have a registered address!");
			System.out.println("What do you want to do?");
			
			System.out.println("\n================================================");
			System.out.println("1- Add new address");
			System.out.println("2- Return");
			System.out.println("================================================\n");
			
			System.out.print("Select what you want to do: ");
			
			//choice for menu without registered address 
			do {
				try {
					
					option = sc.nextInt();
					sc.nextLine();
					
					//check if the user's option is outside the allowed range
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
						this.addAddress(restaurant);
						return;
					
					case 2:
						return;
					}
			
			} while (option != 2);
			
		} else {
			
			do {
				System.out.println("\nCURRENT ADDRESS:");
				System.out.println("================================================");
				System.out.println(restaurantAddress.formatAddress());
				System.out.println("================================================");
				System.out.println("1- Update Postal Code");
				System.out.println("2- Update Street Name");
				System.out.println("3- Update Street Number");
				System.out.println("4- Return");
				System.out.println("================================================\n");
				
				System.out.print("Select what do you want to do: ");
				
				// choice for menu with address already registered
				try {
					
					option = sc.nextInt();
					sc.nextLine();
					
					//check if the user's option is outside the allowed range
					if (!(option > 0 && option <= 4)) {
						System.out.println("Enter a valid option: ");
					}
					
				} catch (Exception e) {
					sc.nextLine();
					System.out.println("Enter only numbers: ");
					option = -1;
				}

				//access the menu options			
				switch (option) {
					case 1:
						this.updateRestaurantPostalCode(restaurantAddress);
						break;
					case 2:
						this.updateRestaurantStreetName(restaurantAddress);
						break;
					case 3:
						this.updateRestaurantStreetNumber(restaurantAddress);
						break;
					case 4:
						System.out.println("Returning to the previous menu");
						return;
						
					default: 
						System.out.println("Invalid option, try again: ");
				}
				
			} while (option != 4);		
			

		} 
		
	}	
	
	/**
	 * Implements address registration for a restaurant
	 * @param r restaurant object
	 */
	private void addAddress(Restaurant r) {
		String postalCode;
		String name;
		int number;
	
		//field for CEP validation
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
		
		//field for street name validation
		while (true) {
		    System.out.print("Enter the street name of the restaurant: ");
		    name = sc.nextLine().trim();

		    try {
		        addressService.checkName(name);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// validation of the restaurant's street number
		while (true) {
		    System.out.print("Enter the street number of the restaurant: ");
	
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
		
		//validation of user choice
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("y")) {
				//instantiation of a new restaurant address and linking of attributes
				RestaurantAddress address = new RestaurantAddress();
				address.setPostalCode(postalCode);
				address.setRestaurantId(r.getId());
				address.setName(name);
				address.setNumber(number);
				
				
				//call the method to register and check if the action was successful
				if(addressService.addRestaurantAddress(address)) {
					System.out.println("Restaurant address has been added!");
					return;
					
				} else {
					System.out.println("An error has occurred while trying to add restaurant address.");
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
	 * Edit the restaurant's address
	 * @param er restaurant address object
	 */
	private void updateRestaurantPostalCode(Address er) {
		//field for CEP validation
		while (true) {
			System.out.print("Enter the new restaurant postal code (8 numbers): ");
			
			String postalCode = sc.next().trim();
			
			try {
				if(addressService.updatePostalCodeRestaurantAddress(er, postalCode)) {
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
	 * Implements editing the address name
	 * @param er restaurant address object
	 */
	private void updateRestaurantStreetName(Address er) {
		
		//field for street name validation
		while (true) {
			System.out.print("Enter the new street name of the restaurant: ");
			
			String name = sc.nextLine().trim();
			
			try {
				if(addressService.updateNameRestaurantAddress(er, name)) {
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
	 * Implements editing the address number
	 * @param er restaurant address object
	 */
	private void updateRestaurantStreetNumber(Address er) {
		
		//field for street number validation
		while (true) {
			System.out.print("Enter the new street number of the restaurant: ");
	
			try {
				
				int number = sc.nextInt();
				sc.nextLine();
				
				if(addressService.updateNumberRestaurantAddress(er, number)) {
					System.out.println("Information updated successfully!");
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
