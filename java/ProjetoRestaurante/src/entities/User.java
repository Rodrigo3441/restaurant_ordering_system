package entities;

/**
 * Abstract Class: User
 *
 * Description:
 * Represents a base entity for system users.
 *
 * Responsibilities:
 * - Store users' personal information
 * - Define common attributes shared by subclasses
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public abstract class User {
	protected String id;
	protected String firstName;
	protected String middleName;
	protected String lastName;
	protected String phone;
	
	// No-argument constructor
	protected User() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}	
	
	
	
}
