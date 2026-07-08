package ui.restaurant;

import java.sql.Connection;
import java.util.Scanner;
import entities.DeliveryPerson;
import services.DeliveryPersonService;

/**
 * Class: DeliveryPersonProfileMenu
 *
 * Description:
 * Class responsible for providing the system interface for the restaurant
 * to manage the registered delivery person's information.
 *
 * Responsibilities:
 * - provide interactive menus to the user
 *
 * @author Rodrigo
 * @since 30-04-2026
 */

public class DeliveryPersonProfileMenu {
	
	private DeliveryPersonService deliveryPersonService;
	private Scanner sc;

	public DeliveryPersonProfileMenu(Connection conn, Scanner sc) {
		this.deliveryPersonService = new DeliveryPersonService(conn);
		this.sc = sc;
	}
	
	/**
	 * Responsible for displaying profile customization actions
	 * @param deliverer delivery person object
	 */
	public void displayDeliverersProfile(DeliveryPerson deliverer) {
		int option = 9;
		
		// validate the user's menu option input
		while (true) {
			
			System.out.println("\nDELIVERY PERSON PROFILE MENU");
			System.out.println("================================================");
			System.out.println("Updating:");
			System.out.println(deliverer);
			System.out.println("1- Update first name");
			System.out.println("2- Update middle name");
			System.out.println("3- Update last name");
			System.out.println("4- Update phone number");
			System.out.println("5- Update vehicle plate");
			System.out.println("6- Return");
			System.out.println("================================================\n");
			
			System.out.print("Select what you want to do: ");
			
			try {
				
				option = sc.nextInt();
				sc.nextLine();
				
				// validate if the option is within the valid range
				if (!(option >= 0 && option <= 6)) {
					System.out.println("Enter a valid option: ");
				}
				
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("Enter only numbers: ");
				option = -1;
			}
			
			// execute the action corresponding to the user's option			
			switch (option) {
				case 1:
					this.updateFirstName(deliverer);
					break;
				case 2:
					this.updateMiddleName(deliverer);
					break;
				case 3:
					this.updateLastName(deliverer);
					break;
				case 4:
					this.updatePhone(deliverer);
					break;
				case 5:
					this.updateVehiclePlate(deliverer);
					break;
				case 6:
					return;
				
			}

		}
		
	}
	
	/**
	 * Responsible for updating the delivery person's first name
	 * @param deliverer delivery person object
	 */
	private void updateFirstName(DeliveryPerson deliverer) {

		// field for first name validation
		while (true) {
			System.out.print("Enter the first name of the deliverer (3-20 letters): ");
			
			String firstName = sc.next().trim();
			
			try {
				if(deliveryPersonService.updateFirstName(deliverer, firstName)) {
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
	 * Responsible for updating the delivery person's middle name
	 * @param entregador delivery person object
	 */
	private void updateMiddleName(DeliveryPerson entregador) {

		// field for middle name validation
		while (true) {
			System.out.print("Enter the middle name of the deliverer (3-40 letters): ");
			
			String middleName = sc.next().trim();
			
			try {
				if(deliveryPersonService.updateMiddleName(entregador, middleName)) {
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
	 * Responsible for updating the delivery person's last name
	 * @param entregador delivery person object
	 */
	private void updateLastName(DeliveryPerson entregador) {

		// field for last name validation
		while (true) {
			System.out.print("Enter the last name of the deliverer (3-20 letters): ");
			
			String lastName = sc.next().trim();
			
			try {
				if(deliveryPersonService.updateLastName(entregador, lastName)) {
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
	 * Responsible for updating the delivery person's phone
	 * @param entregador delivery person object
	 */
	private void updatePhone(DeliveryPerson entregador) {

		// field for phone validation
		while (true) {
			System.out.print("Enter the phone number of the deliverer (maximum of 11 numbers): ");
			
			String phone = sc.next().trim();
			
			try {
				if(deliveryPersonService.updatePhone(entregador, phone)) {
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
	 * Responsible for updating the delivery person's vehicle plate
	 * @param entregador delivery person object
	 */
	private void updateVehiclePlate(DeliveryPerson entregador) {

		// field for vehicle plate validation
		while (true) {
			System.out.print("Enter the vehicle plate of the deliverer: ");
			
			String vehiclePlate = sc.next().trim().toUpperCase();
			
			try {
				if(deliveryPersonService.updateVehiclePlate(entregador, vehiclePlate)) {
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
