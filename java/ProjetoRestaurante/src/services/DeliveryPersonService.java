package services;

import java.sql.Connection;
import java.util.ArrayList;
import database.DeliveryPersonDAO;
import entities.DeliveryPerson;

/**
 * Class: DeliveryPersonService
 *
 * Description:
 * Service layer responsible for managing business rules related to delivery persons.
 *
 * Responsibilities:
 * - provide validation methods for delivery person data
 * - communicate with the data access layer
 *
 * @author Rodrigo
 * @since 30-04-2026
 */

public class DeliveryPersonService {
	// database connection used for all operations
	private DeliveryPersonDAO deliveryPersonDAO;
	private Connection conn;
	
	public DeliveryPersonService(Connection conn) {
		this.deliveryPersonDAO = new DeliveryPersonDAO();
		this.conn = conn;
	}
	
	/**
	 * Validates a CPF: checks format and availability.
	 * @param id delivery person's CPF
	 */
	public void checkDelivererId(String id) {
		if (!isDelivererIdValid(id)) {
	        throw new IllegalArgumentException("Enter a valid id");
	    }

	    if (!isDelivererIdAvailable(id)) {
	        throw new IllegalArgumentException("This id is already used");
	    }
	}
	
	/**
	 * Validates the delivery person's first name.
	 * @param firstName first name
	 */
	public void checkFirstName(String firstName) {
		if(!isFirstNameValid(firstName)) {
			throw new IllegalArgumentException("Enter a valid first name");
		}
	}
	
	/**
	 * Validates the delivery person's middle name.
	 * @param middleName middle name
	 */
	public void checkMiddleName(String middleName) {
		if(!isMiddleNameValid(middleName)) {
			throw new IllegalArgumentException("Enter a valid middle name");
		}
	}
	
	/**
	 * Validates the delivery person's last name.
	 * @param lastName last name
	 */
	public void checkLastName(String lastName) {
		if(!isLastNameValid(lastName)) {
			throw new IllegalArgumentException("Enter a valid last name");
		}
	}
	
	/**
	 * Validates the delivery person's phone number.
	 * @param phone phone number
	 */
	public void checkPhone(String phone) {
		if(!isPhoneValid(phone)) {
			throw new IllegalArgumentException("Enter a valid phone number");
		}
	}
	
	/**
	 * Validates the delivery person's vehicle plate.
	 * @param vehiclePlate vehicle plate
	 */
	public void checkVehiclePlate(String vehiclePlate) {
		if(!isVehiclePlateValid(vehiclePlate)) {
			throw new IllegalArgumentException("Enter a valid vehicle plate");
		}
	}
	
	/**
	 * Updates the delivery person's first name.
	 * @param deliverer delivery person object
	 * @param newFirstName new first name
	 * @return boolean indicating success
	 */
	public boolean updateFirstName(DeliveryPerson deliverer, String newFirstName) {
		if (!isFirstNameValid(newFirstName)) {
			throw new IllegalArgumentException("Enter a valid first name");
		}
		
		deliverer.setFirstName(newFirstName);
		return deliveryPersonDAO.updateDeliveryPerson(conn, deliverer);
	}
	
	/**
	 * Updates the delivery person's middle name.
	 * @param deliverer delivery person object
	 * @param newMiddleName new middle name
	 * @return boolean indicating success
	 */
	public boolean updateMiddleName(DeliveryPerson deliverer, String newMiddleName) {
		if (!isMiddleNameValid(newMiddleName)) {
			throw new IllegalArgumentException("Enter a valid middle name");
		}
		
		deliverer.setMiddleName(newMiddleName);
		return deliveryPersonDAO.updateDeliveryPerson(conn, deliverer);
	}
	
	/**
	 * Updates the delivery person's last name.
	 * @param deliverer delivery person object
	 * @param newLastName new last name
	 * @return boolean indicating success
	 */
	public boolean updateLastName3(DeliveryPerson deliverer, String newLastName) {
		if (!isLastNameValid(newLastName)) {
			throw new IllegalArgumentException("Enter a valid last name");
		}
		
		deliverer.setLastName(newLastName);
		return deliveryPersonDAO.updateDeliveryPerson(conn, deliverer);
	}
	
	/**
	 * Updates the delivery person's phone number.
	 * @param deliverer delivery person object
	 * @param newPhone new phone number
	 * @return boolean indicating success
	 */
	public boolean updatePhone(DeliveryPerson deliverer, String newPhone) {
		if (!isPhoneValid(newPhone)) {
			throw new IllegalArgumentException("Enter a valid phone number");
		}
		
		deliverer.setPhone(newPhone);
		return deliveryPersonDAO.updateDeliveryPerson(conn, deliverer);
	}
	
	/**
	 * Updates the delivery person's vehicle plate.
	 * @param deliverer delivery person object
	 * @param newVehiclePlate new vehicle plate
	 * @return boolean indicating success
	 */
	public boolean updateVehiclePlate(DeliveryPerson deliverer, String newVehiclePlate) {
		if (!isVehiclePlateValid(newVehiclePlate)) {
			throw new IllegalArgumentException("Enter a valid vehicle plate");
		}
		
		deliverer.setVehicle(newVehiclePlate);
		return deliveryPersonDAO.updateDeliveryPerson(conn, deliverer);
	}
		
	/**
	 * Registers a delivery person in the system.
	 * @param deliverer delivery person to register
	 * @return boolean indicating success
	 */
	public boolean addDeliveryPerson(DeliveryPerson deliverer) {
		return deliveryPersonDAO.addDeliveryPerson(conn, deliverer);
	}
	
	/**
	 * Returns a delivery person by CPF.
	 * @param id delivery person's CPF
	 * @return DeliveryPerson or null if not found
	 */
	public DeliveryPerson returnDeliveryPerson(String id) {
		return deliveryPersonDAO.returnDeliveryPerson(conn, id);
	}
	
	/**
	 * Returns all registered delivery persons.
	 * @return list of DeliveryPerson
	 */
	public ArrayList<DeliveryPerson> returnDeliveryPersonList() {
		return deliveryPersonDAO.returnDeliveryPersonList(conn);
	}
	
	/**
	 * Removes a delivery person by CPF.
	 * @param id delivery person's CPF
	 * @return boolean indicating success
	 */
	public boolean deleteDeliveryPerson(String id) {
		return deliveryPersonDAO.deleteDeliveryPerson(conn, id);
	}
	
	private boolean isDelivererIdValid(String id) {	
		return id.length() == 11 && id.matches("^[0-9]+$");
	}

	private boolean isDelivererIdAvailable(String id) {
		return deliveryPersonDAO.returnDeliveryPerson(conn, id) == null;
	}
	
	private boolean isFirstNameValid(String firstName) {
		return firstName.length() >= 3 && firstName.length() <= 20 && firstName.matches("^[A-Za-zÀ-ÿ]+$");
	}
	
	private boolean isMiddleNameValid(String middleName) {
		return middleName.length() >= 3 && middleName.length() <= 40 && middleName.matches("^[A-Za-zÀ-ÿ ]+$");
	}
	
	private boolean isLastNameValid(String lastName) {
		return lastName.length() >= 3 && lastName.length() <= 20 && lastName.matches("^[A-Za-zÀ-ÿ]+$");
	}
	
	private boolean isPhoneValid(String phone) {
		return phone.length() <= 11 && phone.matches("^[0-9]+$");
	}
	
	/**
	 * Checks if a vehicle plate matches the Mercosur standard.
	 * @param vehiclePlate vehicle plate to validate
	 * @return boolean
	 */
	private boolean isVehiclePlateValid(String vehiclePlate) {
		return vehiclePlate.length() == 7 && vehiclePlate.matches("^[A-Z]{3}[0-9][A-Z][0-9]{2}$");
	}
	
	
	
}
