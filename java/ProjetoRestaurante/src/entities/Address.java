package entities;

/**
 * Abstract Class: Address
 *
 * Description:
 * Represents a base entity for addresses in the system.
 *
 * Responsibilities:
 * - Store common address data
 * - Define shared behaviors
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public abstract class Address {
	protected String postalCode;
	protected String name;
	protected Integer number;
	
	/**
	 * No-argument constructor
	 */
	protected Address() {
		
	}

	/**
	 * Returns the postal code of the address.
	 * If no postal code is registered, returns a default message.
	 * 
	 * @return Postal code of the address or message indicating no postal code
	 */
	public String getPostalCode() {
		if (postalCode != null) {
			return postalCode;
		} 
		return "No postal code available.";
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	/**
	 * Returns the street name.
	 * If no street name is registered, returns a default message.
	 *
	 * @return Street name or message indicating no registration
	 */
	public String getName() {
		if (name != null) {
			return name;
		} 
		return "No street name available.";
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the address number.
	 * If no address number is registered, returns 0.
	 *
	 * @return Address number or 0
	 */
	public Integer getNumber() {
		if (number != null) {
			return number;
		}
		return 0;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	/**
	 * Returns a formatted string representation of the address
	 * @return Formatted address string
	 */
	public abstract String formatAddress();
	
	/**
	 * Returns the identification of the owner of the address
	 * @return String with CPF/CNPJ identification
	 */
	public abstract String getId();
	
	
}
