package entities;

/**
 * Entity: Restaurant
 *
 * Description:
 * Represents a restaurant registered in the system.
 *
 * Responsibilities:
 * - Store restaurant information
 * - Maintain the restaurant's contact details
 * - Associate the restaurant with a category
 * - Manage authentication and access data
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public class Restaurant {
	private String id;
	private String name;
	private String phone;
	private Integer categoryId;
	private String passcode;
	
	// No-argument constructor
	public Restaurant() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}
	
	
}
