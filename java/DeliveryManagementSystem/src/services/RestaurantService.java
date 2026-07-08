package services;

import java.sql.Connection;
import java.util.ArrayList;

import database.RestaurantDAO;
import entities.Restaurant;

/**
 * Class: RestaurantService
 *
 * Description:
 * Service class responsible for enforcing business rules for restaurants.
 *
 * Responsibilities:
 * - provide validation methods for restaurant data
 * - communicate with the data access layer
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class RestaurantService {
	private RestaurantDAO restaurantDAO;
	private Connection conn;
	
	
	public RestaurantService(Connection conn) {
		this.restaurantDAO = new RestaurantDAO();
		this.conn = conn;
	}
	
	/**
	 * Validates that a CNPJ is correctly formatted and not already in use.
	 * @param id restaurant CNPJ
	 */
	public void checkRestaurantId(String id) {
		if (!isRestaurantIdValid(id)) {
	        throw new IllegalArgumentException("Enter a valid restaurant ID");
	    }

	    if (!isRestaurantIdAvailable(id)) {
	        throw new IllegalArgumentException("This restaurant ID is already used");
	    }
	}
	
	/**
	 * Validates the integrity of the restaurant name.
	 * @param name restaurant name
	 */
	public void checkName(String name) {
		if(!isNameValid(name)) {
			throw new IllegalArgumentException("Enter a valid name");
		}
	}
	
	/**
	 * Validates the integrity of the restaurant phone number.
	 * @param phone restaurant phone
	 */
	public void checkPhone(String phone) {
		if(!isPhoneValid(phone)) {
			throw new IllegalArgumentException("Enter a valid phone number");
		}
	}
		
	/**
	 * Validates the integrity of the restaurant password.
	 * @param passcode restaurant password
	 */
	public void checkPasscode(String passcode) {
		if(!isPasscodeValid(passcode)) {
			throw new IllegalArgumentException("Enter a valid password");
		}
	}
	
	/**
	 * Updates the restaurant's name.
	 * @param restaurant restaurant object
	 * @param newName new restaurant name
	 * @return boolean indicating success
	 */
	public boolean updateName(Restaurant restaurant, String newName) {
		if (!isNameValid(newName)) {
			throw new IllegalArgumentException("Enter a valid name");
		}
		
		restaurant.setName(newName);
		return restaurantDAO.updateRestaurant(conn, restaurant);
	}
	
	/**
	 * Updates the restaurant's phone number.
	 * @param restaurant restaurant object
	 * @param newPhone new phone number
	 * @return boolean indicating success
	 */
	public boolean updatePhone(Restaurant restaurant, String newPhone) {
		if (!isPhoneValid(newPhone)) {
			throw new IllegalArgumentException("Enter a valid phone number");
		}
		
		restaurant.setPhone(newPhone);
		return restaurantDAO.updateRestaurant(conn, restaurant);
	}
	
	/**
	 * Updates the restaurant's password.
	 * @param restaurant restaurant object
	 * @param newPasscode new password
	 * @return boolean indicating success
	 */
	public boolean updatePasscode(Restaurant restaurant, String newPasscode) {
		if (!isPasscodeValid(newPasscode)) {
			throw new IllegalArgumentException("Enter a valid password");
		}
		
		restaurant.setPasscode(newPasscode);
		return restaurantDAO.updateRestaurant(conn, restaurant);
	}
	
	/**
	 * Registers a new restaurant in the system.
	 * @param restaurant restaurant object
	 * @return boolean indicating success
	 */
	public boolean addRestaurant(Restaurant restaurant) {
		return restaurantDAO.addRestaurant(conn, restaurant);
	}
	
	/**
	 * Returns a restaurant by the given CNPJ.
	 * @param id restaurant CNPJ
	 * @return Restaurant object
	 */
	public Restaurant returnRestaurant(String id) {
		return restaurantDAO.returnRestaurant(conn, id);
	}
	
	/**
	 * Returns a list of all restaurants in the system.
	 * @return ArrayList of Restaurant
	 */
	public ArrayList<Restaurant> returnRestaurantList(){
		return restaurantDAO.returnRestaurantList(conn);
	}

	private boolean isRestaurantIdValid(String id) {	
		return id.length() == 14 && id.matches("^[0-9]+$");
	}

	private boolean isRestaurantIdAvailable(String id) {
		return restaurantDAO.returnRestaurant(conn, id) == null;
	}
	
	private boolean isNameValid(String name) {
		return name.length() >= 3 && name.length() <= 40;
	}

	private boolean isPhoneValid(String phone) {
		return phone.length() <= 11 && phone.matches("^[0-9]+$");
	}
		
	private boolean isPasscodeValid(String passcode) {
		return passcode.length() < 255;
	}	
	
	
}
