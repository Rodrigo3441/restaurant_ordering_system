package services;

import java.sql.Connection;
import entities.Address;
import entities.CustomerAddress;
import entities.RestaurantAddress;
import database.AddressDAO;

/**
 * Class: AddressService
 *
 * Description:
 * Service class responsible for validating address operations for customers and restaurants
 *
 * Responsibilities:
 * - provide business logic methods and validation
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class AddressService {
	private AddressDAO adressDAO;
	private Connection conn;
	
	public AddressService(Connection conn) {
		this.adressDAO = new AddressDAO();
		this.conn = conn;
	}
	
	/**
	 * Returns the restaurant address based on the provided CNPJ
	 * @param id restaurant CNPJ
	 * @return restaurant address object
	 */
	public RestaurantAddress returnRestaurantAddress(String id) {
		return adressDAO.returnRestaurantAddress(conn, id);
	}
	
	/**
	 * Returns the customer address based on the provided CPF
	 * @param id customer CPF
	 * @return customer address object
	 */
	public CustomerAddress returnCustomerAddress(String id) {
		return adressDAO.returnCustomerAddress(conn, id);
	}
	
	/**
	 * Inserts a restaurant address into the system
	 * @param restaurantAddress restaurant address object
	 * @return boolean
	 */
	public boolean addRestaurantAddress(Address restaurantAddress) {
		return adressDAO.addRestaurantAddress(conn, restaurantAddress);
	}
	
	/**
	 * Inserts a customer address into the system
	 * @param customerAddress customer address object
	 * @return boolean
	 */
	public boolean addCustomerAddress(Address customerAddress) {
		return adressDAO.addCustomerAddress(conn, customerAddress);
	}
	
	/**
	 * Validates whether the provided ZIP code is valid
	 * @param postalCode address ZIP code
	 */
	public void checkPostalCode(String postalCode) {
		if (!isPostalCodeValid(postalCode)) {
			throw new IllegalArgumentException("Enter a valid postal code");
		}
	}
	
	/**
	 * Validates whether the provided street name is valid
	 * @param name street name
	 */
	public void checkName(String name) {
		if (!isNameValid(name)) {
			throw new IllegalArgumentException("Enter a valid name");
		}
	}
	
	/**
	 * Validates whether the provided address number is valid
	 * @param number address number
	 */
	public void checkNumber(int number) {
		if (!isNumberValid(number)) {
			throw new IllegalArgumentException("Enter a valid number");
		}
	}
	
	/**
	 * Updates the customer address ZIP code in the system
	 * @param customerAddress customer address object
	 * @param newPostalCode address ZIP code
	 * @return boolean
	 */
	public boolean updatePostalCodeCustomerAddress(Address customerAddress, String newPostalCode) {
		if (!isPostalCodeValid(newPostalCode)) {
			throw new IllegalArgumentException("Enter a valid postal code");
		}
		
		customerAddress.setPostalCode(newPostalCode);
		return adressDAO.updateCustomerAddress(conn, customerAddress);
	}
	
	/**
	 * Updates the customer street name in the system
	 * @param customerAddress customer address object
	 * @param newName customer street name
	 * @return boolean
	 */
	public boolean updateNameCustomerAddress(Address customerAddress, String newName) {
		if (!isNameValid(newName)) {
			throw new IllegalArgumentException("Enter a valid name");
		}
		
		customerAddress.setName(newName);
		return adressDAO.updateCustomerAddress(conn, customerAddress);
	}
	
	/**
	 * Updates the customer address number in the system
	 * @param customerAddress customer address object
	 * @param number customer address number
	 * @return boolean
	 */
	public boolean updateNumberCustomerAddress(Address customerAddress, int number) {
		if (!isNumberValid(number)) {
			throw new IllegalArgumentException("Enter a valid number");
		}
		
		customerAddress.setNumber(number);
		return adressDAO.updateCustomerAddress(conn, customerAddress);
	}
	
	/**
	 * Updates the restaurant address ZIP code in the system
	 * @param restaurantAddress restaurant address object
	 * @param newPostalCode address ZIP code
	 * @return boolean
	 */
	public boolean updatePostalCodeRestaurantAddress(Address restaurantAddress, String newPostalCode) {
		if (!isPostalCodeValid(newPostalCode)) {
			throw new IllegalArgumentException("Enter a valid postal code");
		}
		
		restaurantAddress.setPostalCode(newPostalCode);
		return adressDAO.updateRestaurantAddress(conn, restaurantAddress);
	}
	
	/**
	 * Updates the restaurant street name in the system
	 * @param customerAddress restaurant address object
	 * @param newName restaurant street name
	 * @return boolean
	 */
	public boolean updateNameRestaurantAddress(Address customerAddress, String newName) {
		if (!isNameValid(newName)) {
			throw new IllegalArgumentException("Enter a valid name");
		}
		
		customerAddress.setName(newName);
		return adressDAO.updateRestaurantAddress(conn, customerAddress);
	}
	
	/**
	 * Updates the restaurant address number in the system
	 * @param restaurantAddress restaurant address object
	 * @param newNumber restaurant address number
	 * @return boolean
	 */
	public boolean updateNumberRestaurantAddress(Address restaurantAddress, int newNumber) {
		if (!isNumberValid(newNumber)) {
			throw new IllegalArgumentException("Enter a valid number");
		}
		
		restaurantAddress.setNumber(newNumber);
		return adressDAO.updateRestaurantAddress(conn, restaurantAddress);
	}
	
	private boolean isPostalCodeValid(String postalCode) {
		return postalCode.length() <= 8 && postalCode.matches("^[0-9]+$");
	}
	
	private boolean isNameValid(String name) {
		return name.length() <= 100 && name.matches("^[a-zA-ZÀ-ÿ ]+$");
	}
	
	private boolean isNumberValid(int number) {
		return number >= 0;
	}
}