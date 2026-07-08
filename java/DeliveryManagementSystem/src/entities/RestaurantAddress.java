package entities;

/**
 * Entity: RestaurantAddress
 *
 * Description:
 * Represents the address associated with a restaurant in the system.
 *
 * Responsibilities:
 * - Store the restaurant's address information
 * - Associate the address with the restaurant's CNPJ (tax ID)
 * - Format the address data for display
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public class RestaurantAddress extends Address {
	private String restaurantId;
	
	/**
	 * No-argument constructor
	 */
	public RestaurantAddress() {
		super();
	}


	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	@Override
	public String formatAddress() {
		return "Postal Code: " + postalCode +
				" | Street Name: " + name +
				" | Street Number: " + number;
	}

	@Override
	public String getId() {
		return restaurantId;
	}
	
	
}
