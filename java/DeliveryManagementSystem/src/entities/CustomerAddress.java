package entities;

/**
 * Entity: CustomerAddress
 *
 * Description:
 * Represents an address associated with a system customer.
 *
 * Responsibilities:
 * - Store the customer's address information
 * - Associate the address with the customer's CPF identifier
 * - Format address data for display
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public class CustomerAddress extends Address {
	private String customerId;
	
	/**
	 * No-argument constructor
	 */
	public CustomerAddress() {
		super();
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Override
	public String formatAddress() {
		return "Postal Code: " + postalCode +
				" | Street Name: " + name +
				" | Street Number: " + number;
	}

	@Override
	public String getId() {
		return customerId;
	}
	
	

}
