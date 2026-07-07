package ui.restaurant;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import entities.DeliveryPerson;
import services.DeliveryPersonService;

/**
 * Class: RestaurantDeliveryPersonMenu
 *
 * Description:
 * Class responsible for providing the delivery person management interface
 * for restaurants in the system
 *
 * Responsibilities:
 * - provide interactive menus for the restaurant
 *
 * @author Rodrigo
 * @since 30-04-2026
 */

public class RestaurantDeliveryPersonMenu {
	
	private Scanner sc;
	private DeliveryPersonService deliveryPersonService;
	private Connection conn;
	
	public RestaurantDeliveryPersonMenu(Connection conn, Scanner sc) {
		this.deliveryPersonService = new DeliveryPersonService(conn);
		this.conn = conn;
		this.sc = sc;
	}
	
	/**
	 * Method mostrarMenuEntregador
	 * 
	 * Responsible for offering the initial menu options for restaurant delivery person management
	 * 
	 */
	public void displayDelivererMenu() {
			
			int option = 9;
			
			// validate user's menu choice input
			do {
				
				System.out.println("\nDELIVERY PERSON MANAGEMENT MENU");
				System.out.println("================================================");
				System.out.println("1- Add a new deliverer");
				System.out.println("2- Show all deliverers");
				System.out.println("3- Update deliverer information");
				System.out.println("4- Delete a deliverer from the system");
				System.out.println("5- Return");
				System.out.println("================================================\n");
				
				System.out.print("Choose what you want to do: ");
				
				try {
					
					option = sc.nextInt();
					sc.nextLine();
					
				} catch (Exception e) {
					sc.nextLine();
					System.out.println("Enter only numbers: ");
					option = -1;
				}
				
				// access the menu options
				switch (option) {
					case 1:
						this.addDeliverer();
						break;
						
					case 2:
						this.showDeliverers();
						System.out.println("Press any key to continue");
						sc.nextLine();
						break;
						
					case 3:
						this.updateDeliverer();
						break;
						
					case 4:
						this.removeDeliverer();
						break;
						
					case 5:
						System.out.println("Returning to the previous menu");
						return;
						
					default: 
						System.out.println("Invalid option, try again: ");
				}
	
				
			} while (option != 5);
			
		}
	
	
	/**
	 * Responsible for providing the registration interface for a new delivery person
	 * 
	 */
	private void addDeliverer() {
		
		String delivererId;
		String firstName;
		String middleName;
		String lastName;
		String phone;
		String vehiclePlate;
		
		System.out.println("NEW DELIVERER REGISTRATION");
		
		// field for CPF validation
		while (true) {
		    System.out.print("Enter the deliverer ID (11 characters): ");
		    delivererId = sc.nextLine().trim();

		    try {
		        deliveryPersonService.checkDelivererId(delivererId);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for first name validation
		while (true) {
			System.out.print("Enter the deliverer first name: ");
		    firstName = sc.nextLine().trim();

		    try {
		    	deliveryPersonService.checkFirstName(firstName);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for last name validation
		while (true) {
			System.out.print("Enter the deliverer last name: ");
		    lastName = sc.nextLine().trim();

		    try {
		    	deliveryPersonService.checkLastName(lastName);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for middle name validation
		while (true) {
			System.out.print("Enter the deliverer middle name: ");
		    middleName = sc.nextLine().trim();

		    try {
		    	deliveryPersonService.checkMiddleName(middleName);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
				
		// field for phone validation
		while (true) {
			System.out.print("Enter the deliverer phone number: ");
		    phone = sc.nextLine().trim();

		    try {
		    	deliveryPersonService.checkPhone(phone);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for vehicle plate validation
		while (true) {
			System.out.print("Enter the deliverer vehicle plate: ");
		    vehiclePlate = sc.nextLine().trim().toUpperCase();

		    try {
		    	deliveryPersonService.checkVehiclePlate(vehiclePlate);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		System.out.println("\nCONFIRM INFORMATIONS: ");
		System.out.println("================================================");
		System.out.printf("Deliverer ID: %s\n", delivererId);
		System.out.printf("First name: %s\n", firstName);
		System.out.printf("Middle name: %s\n", middleName);
		System.out.printf("Last name: %s\n", lastName);
		System.out.printf("Phone number: %s\n", phone);
		System.out.printf("Vehicle plate: %s\n", vehiclePlate);
		System.out.println("================================================\n");
		
		System.out.print("Are these information correct? (y-yes /c-cancel): ");
		
		// validate user's choice
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("y")) {
				// instantiate a new DeliveryPerson and set attributes			
				DeliveryPerson deliverer = new DeliveryPerson();
				deliverer.setId(delivererId);
				deliverer.setFirstName(firstName);
				deliverer.setMiddleName(middleName);
				deliverer.setLastName(lastName);
				deliverer.setPhone(phone);
				deliverer.setVehicle(vehiclePlate);
				deliverer.setAvailable((short) 0);
				
				// call the registration method and check if it succeeded
				if(deliveryPersonService.addDeliveryPerson(deliverer)) {
					System.out.println("Deliverer's account has been created!");
					
				} else {
					System.out.println("An error has occurred while trying to create the deliverer's account.");
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
	 * List all delivery persons registered in the system
	 */
	private void showDeliverers() {
		ArrayList<DeliveryPerson> deliverersList = deliveryPersonService.returnDeliveryPersonList();

		// stop execution when there are no delivery persons registered
		if (deliverersList.isEmpty()) {
			System.out.println("There is no deliverers on the system!");
			return;
		}
		
		System.out.println("\nSHOWING ALL DELIVERERS FROM THE SYSTEM:");
		System.out.println("================================================");
		// print each delivery person for the restaurant
		for (DeliveryPerson e: deliverersList) {
			System.out.println(e);
		}
		System.out.println("================================================\n");
		
	}
	

	/**
	 * Displays the list of all delivery persons in the system and asks the restaurant to provide the CPF
	 * that will be deleted from the system
	 */
	private void removeDeliverer() {
		
		this.showDeliverers();
		
		System.out.print("Enter the ID of the deliverer you want to remove from the system: ");
		
		// CPF of the delivery person to be deleted
		String id = sc.next().trim();
		
		// store the target delivery person for operations
		DeliveryPerson deliverer = deliveryPersonService.returnDeliveryPerson(id);
		
		if (deliverer != null) {
			System.out.println(deliverer);
			System.out.printf("Do you want to remove the deliverer %s from the system? (y-yes/n-no): ",deliverer.getId());
			
			// validate user's choice
			while (true) {
				
				String opt = sc.next();
				
				if (opt.equals("y")) {
				
					// call the deletion method and check if it succeeded
					try {
						deliveryPersonService.deleteDeliveryPerson(id);
						System.out.println("Deliverer removed from the system!");
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					
					break;
					
				} else if (opt.equals("n")) {
					System.out.println("Nothing has changed");
					return;
					
				} else {
					System.out.print("Invalid option, try again: ");
				}
				
			}
			
		} else {
			System.out.println("There is no deliverers with the specified ID.");
			return;
		}
		
	}
	
	/**
	 * Displays the list of all delivery persons in the system and asks the restaurant to provide the CPF
	 * of the delivery person whose information should be updated
	 */
	private void updateDeliverer() {
		
		this.showDeliverers();
		
		System.out.print("Enter the ID of the deliverer you want to update informations: ");
		
		// CPF of the delivery person to be updated
		String cpf = sc.next().trim();
		
		// store the target delivery person for operations
		DeliveryPerson deliverer = deliveryPersonService.returnDeliveryPerson(cpf);
		
		if (deliverer != null) {
			DeliveryPersonProfileMenu deliveryPersonProfileMenu = new DeliveryPersonProfileMenu(conn, sc);
			deliveryPersonProfileMenu.displayDeliverersProfile(deliverer);
			
		} else {
			System.out.println("There is no deliverers with the specified ID.");
		}
	}
	

}
