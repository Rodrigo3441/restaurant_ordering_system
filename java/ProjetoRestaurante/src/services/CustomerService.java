package services;

import java.sql.Connection;
import database.CustomerDAO;
import entities.Customer;

/**
 * Class: CustomerService
 *
 * Description:
 * Service class responsible for managing customer business rules.
 *
 * Responsibilities:
 * - provide validation methods for customer information
 * - communicate with the data access layer
 *
 * @author Rodrigo
 * @since 24-04-2026
 */

public class CustomerService {
	private CustomerDAO customerDAO;
	private Connection conn;
	
	/**
	 * Constructor that receives the database connection object.
	 *
	 * @param conn database connection
	 */
	public CustomerService(Connection conn) {
		this.customerDAO = new CustomerDAO();
		this.conn = conn;
	}
	
	/**
	 * Validate customer id: checks format and availability.
	 * @param id customer's CPF (Brazilian tax ID)
	 */
	public void checkCustomerId(String id) {
		if (!isCustomerIdValid(id)) {
	        throw new IllegalArgumentException("Enter a valid id");
	    }

	    if (!isCustomerIdAvailable(id)) {
	        throw new IllegalArgumentException("This id is already used");
	    }
	}
	
	/**
	 * Validate first name integrity.
	 * @param firstName first name
	 */
	public void checkFirstName(String firstName) {
		if(!isFirstNameValid(firstName)) {
			throw new IllegalArgumentException("Enter a valid first name");
		}
	}
	
	/**
	 * Validate middle name integrity.
	 * @param middleName middle name
	 */
	public void checkMiddleName(String middleName) {
		if(!isMiddleNameValid(middleName)) {
			throw new IllegalArgumentException("Enter a valid middle name");
		}
	}
	
	/**
	 * Validate last name integrity.
	 * @param lastName last name
	 */
	public void checkLastName(String lastName) {
		if(!isLastNameValid(lastName)) {
			throw new IllegalArgumentException("Enter a valid last name");
		}
	}
	
	/**
	 * Validate phone number integrity.
	 * @param phone phone number
	 */
	public void checkPhone(String phone) {
		if(!isPhoneValid(phone)) {
			throw new IllegalArgumentException("Enter a valid phone number");
		}
	}
	
	/**
	 * Validate email integrity.
	 * @param email email address
	 */
	public void checkEmail(String email) {
		if(!isEmailValid(email)) {
			throw new IllegalArgumentException("Enter a valid email");
		}
	}
	
	/**
	 * Validate password integrity.
	 * @param passcode password
	 */
	public void checkPasscode(String passcode) {
		if(!isPasscodeValid(passcode)) {
			throw new IllegalArgumentException("Enter a valid password");
		}
	}
	
	/**
	 * Update customer's first name.
	 * @param customer customer object
	 * @param newFirstName first name
	 * @return true if update succeeded
	 */
	public boolean updateFirstName(Customer customer, String newFirstName) {
		if (!isFirstNameValid(newFirstName)) {
			throw new IllegalArgumentException("Enter a valid first name");
		}
		
		customer.setFirstName(newFirstName);
		return customerDAO.updateCustomer(conn, customer);
	}
	
	/**
	 * Update customer's middle name.
	 * @param customer customer object
	 * @param newMiddleName middle name
	 * @return true if update succeeded
	 */
	public boolean updateMiddleName(Customer customer, String newMiddleName) {
		if (!isMiddleNameValid(newMiddleName)) {
			throw new IllegalArgumentException("Enter a valid middle name");
		}
		
		customer.setMiddleName(newMiddleName);
		return customerDAO.updateCustomer(conn, customer);
	}
	
	/**
	 * Update customer's last name.
	 * @param customer customer object
	 * @param newLastName last name
	 * @return true if update succeeded
	 */
	public boolean updateLastName(Customer customer, String newLastName) {
		if (!isLastNameValid(newLastName)) {
			throw new IllegalArgumentException("Enter a valid last name");
		}
		
		customer.setLastName(newLastName);
		return customerDAO.updateCustomer(conn, customer);
	}
	
	/**
	 * Update customer's phone number.
	 * @param customer customer object
	 * @param newPhone phone number
	 * @return true if update succeeded
	 */
	public boolean updatePhone(Customer customer, String newPhone) {
		if (!isPhoneValid(newPhone)) {
			throw new IllegalArgumentException("Utilize um telefone válido");
		}
		
		customer.setPhone(newPhone);
		return customerDAO.updateCustomer(conn, customer);
	}
	
	/**
	 * Update customer's email.
	 * @param customer customer object
	 * @param newEmail email address
	 * @return true if update succeeded
	 */
	public boolean updateEmail(Customer customer, String newEmail) {
		if (!isEmailValid(newEmail)) {
			throw new IllegalArgumentException("Enter a valid email");
		}
		
		customer.setEmail(newEmail);
		return customerDAO.updateCustomer(conn, customer);
	}
	
	/**
	 * Update customer's password.
	 * @param customer customer object
	 * @param newPasscode password
	 * @return true if update succeeded
	 */
	public boolean updatePasscode(Customer customer, String newPasscode) {
		if (!isPasscodeValid(newPasscode)) {
			throw new IllegalArgumentException("Enter a valid password");
		}
		
		customer.setPasscode(newPasscode);
		return customerDAO.updateCustomer(conn, customer);
	}
	
	/**
	 * Register a new customer in the system.
	 * @param customer customer object
	 * @return true if insertion succeeded
	 */
	public boolean addCustomer(Customer customer) {
		return customerDAO.addCustomer(conn, customer);
	}
	
	/**
	 * Return a customer by CPF.
	 * @param id customer's CPF
	 * @return Customer or null if not found
	 */
	public Customer returnCustomer(String id) {
		return customerDAO.returnCustomer(conn, id);
	}
	
	private boolean isCustomerIdValid(String id) {	
		return id.length() == 11 && id.matches("^[0-9]+$");
	}
	
	private boolean isCustomerIdAvailable(String id) {
		return customerDAO.returnCustomer(conn, id) == null;
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
	
	private boolean isEmailValid(String email) {
		return email.length() <= 255 && email.contains("@");
	}
	
	private boolean isPasscodeValid(String passcode) {
		return passcode.length() < 255;
	}	
	
	
}