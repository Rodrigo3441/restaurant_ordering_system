  package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.DeliveryPerson;

/**
 * Class: DeliveryPersonDAO
 *
 * Description:
 * Class responsible for managing DeliveryPerson data
 *
 * Responsibilities:
 * - Connect to the database
 * - Perform data manipulations
 *
 * @author Rodrigo
 * @since 21-04-2026
 */

public class DeliveryPersonDAO {
	
	/**
	 * Responsible for inserting a new delivery person into the database
	 * @param conn connection object
	 * @param deliveryPerson delivery person object
	 * @return boolean
	 */
	public boolean addDeliveryPerson(Connection conn, DeliveryPerson deliveryPerson) {
		String sqlQuery = "INSERT INTO delivery_person (" +
						"del_per_id_pk," +
						"first_name, " +
						"middle_name, " +
						"last_name, " +
						"phone, " +
						"vehicle, " +
						"available) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?)";
		
			// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind the attributes to the prepared query
			stmt.setString(1, deliveryPerson.getId());
			stmt.setString(2, deliveryPerson.getFirstName());
			stmt.setString(3, deliveryPerson.getMiddleName());
			stmt.setString(4, deliveryPerson.getLastName());
			stmt.setString(5, deliveryPerson.getPhone());
			stmt.setString(6, deliveryPerson.getVehicle());
			stmt.setShort(7, deliveryPerson.getAvailable());
			
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
			
		} catch (SQLException e) {
			System.err.println("Error in delivery_person adding operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsible for retrieving the delivery person's information from the database
	 * so it can be used for assigning deliveries
	 * @param conn connection object
	 * @param id cpf of the searched delivery person
	 * @return delivery person object
	 */
	public DeliveryPerson returnDeliveryPerson(Connection conn, String id) {
		String sqlQuery = "SELECT * FROM delivery_person WHERE del_per_id_pk = ?";
		
			// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind the attributes to the prepared query
			stmt.setString(1, id);
			
			ResultSet result = stmt.executeQuery();
			
				// if there is a result for the cpf search, instantiate a delivery person
				// object with the result attributes
			if (result.next()) {
				DeliveryPerson deliveryPerson = new DeliveryPerson();
							
				deliveryPerson.setId(result.getString("del_per_id_pk"));
				deliveryPerson.setFirstName(result.getString("first_name"));
				deliveryPerson.setMiddleName(result.getString("middle_name"));
				deliveryPerson.setLastName(result.getString("last_name"));
				deliveryPerson.setPhone(result.getString("phone"));
				deliveryPerson.setVehicle(result.getString("vehicle"));
				deliveryPerson.setAvailable(result.getShort("available"));
				
				return deliveryPerson;

			}
									
		} catch (SQLException e) {
			System.err.println("Error in delivery_person querying operation.");
		    e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Responsible for updating a delivery person's information in the database
	 * @param conn connection object
	 * @param deliveryPerson delivery person object
	 * @return boolean
	 */
	public boolean updateDeliveryPerson(Connection conn, DeliveryPerson deliveryPerson) {
		String sqlQuery = "UPDATE delivery_person SET " +
			            "first_name = ?, " +
			            "middle_name = ?, " +
			            "last_name = ?, " +
			            "phone = ?, " +
			            "vehicle = ?, " +
			            "available = ? " +
			            "WHERE del_per_id_pk = ?";

			// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind the attributes to the prepared query
			stmt.setString(1, deliveryPerson.getFirstName());
	        stmt.setString(2, deliveryPerson.getMiddleName());
	        stmt.setString(3, deliveryPerson.getLastName());
	        stmt.setString(4, deliveryPerson.getPhone());
	        stmt.setString(5, deliveryPerson.getVehicle());
	        stmt.setShort(6, deliveryPerson.getAvailable());
	        stmt.setString(7, deliveryPerson.getId());
						
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
			
		} catch (SQLException e) {
			System.err.println("Error in delivery_person updating operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsible for deleting a delivery person from the database
	 * @param conn connection object
	 * @param id cpf of the delivery person
	 * @return boolean
	 */
	public boolean deleteDeliveryPerson(Connection conn, String id) {
		String sqlQuery = "DELETE FROM delivery_person WHERE del_per_id_pk = ?";
		
			// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			// bind the attributes to the prepared query
			stmt.setString(1, id);
			
			// query execution
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;

		} catch (SQLException e) {
			System.err.println("Error in delivery_person deleting operation.");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsible for retrieving information for all delivery persons in the system
	 * @param conn connection object
	 * @return ArrayList of delivery persons
	 */
	public ArrayList<DeliveryPerson> returnDeliveryPersonList(Connection conn){
		
		// List to store all DeliveryPerson instances
		ArrayList<DeliveryPerson> deliveryPersonList = new ArrayList<DeliveryPerson>();
		
		String sqlQuery = "SELECT * FROM delivery_person";
		
			// prepare the query before execution
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			ResultSet result = stmt.executeQuery();
			
				// storing all found delivery persons in the dynamic list
			while (result.next()) {
				DeliveryPerson deliveryPerson = new DeliveryPerson();
		
				deliveryPerson.setId(result.getString("del_per_id_pk"));
				deliveryPerson.setFirstName(result.getString("first_name"));
				deliveryPerson.setMiddleName(result.getString("middle_name"));
				deliveryPerson.setLastName(result.getString("last_name"));
				deliveryPerson.setPhone(result.getString("phone"));
				deliveryPerson.setVehicle(result.getString("vehicle"));
				deliveryPerson.setAvailable(result.getShort("available"));
				
				deliveryPersonList.add(deliveryPerson);
			}
			
			
		} catch (SQLException e) {
			System.err.println("Error in delivery_person listing operation.");
		    e.printStackTrace();
		}
		
		return deliveryPersonList;
	}
	
}
