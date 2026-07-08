package entities;

/**
 * Entity: DeliveryPerson
 *
 * Description:
 * Represents a delivery person registered in the system.
 *
 * Responsibilities:
 * - Store personal information of the delivery person
 * - Maintain vehicle data used for deliveries
 * - Control the availability of the delivery person
 * - Display formatted information of the delivery person
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public class DeliveryPerson extends User {
	private String vehicle;
	private Short available;
		
	/**
	 * No-argument constructor
	 */
	public DeliveryPerson() {
		super();
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String veiculo) {
		this.vehicle = veiculo;
	}

	public short getAvailable() {
		return available;
	}	

	/**
	 * Returns an availability message for the delivery person based on the availability index
	 * @return a string with the two possible availability statuses
	 */
	public String getAvailableString() {
		return (this.available == 0) ? "Available" : "Not Available";
	}

	public void setAvailable(Short available) {
		this.available = available;
	}
	
	@Override
	public String toString() {
		return String.format("Full Name: %s %s %s | "
							+ "ID: %s | "
							+ "Phone: %s | "
							+ "Vehicle: %s | "
							+ "Available: %s", 
							firstName, 
							middleName, 
							lastName, 
							id,
							phone, 
							vehicle, 
							this.getAvailableString());
		
	}
	
	
	
}
