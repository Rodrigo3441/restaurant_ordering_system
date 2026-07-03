package services;

import java.sql.Connection;
import java.util.ArrayList;
import database.DeliveryPersonDAO;
import entities.DeliveryPerson;

/**
 * Class: DeliveryPersonService
 *
 * Description:
 * Service layer responsible for managing business rules related to delivery persons.
 *
 * Responsibilities:
 * - provide validation methods for delivery person data
 * - communicate with the data access layer
 *
 * @author Rodrigo
 * @since 30-04-2026
 */

public class DeliveryPersonService {
	// database connection used for all operations
	private DeliveryPersonDAO dao;
	private Connection conn;
	
	public DeliveryPersonService(Connection conn) {
		this.dao = new DeliveryPersonDAO();
		this.conn = conn;
	}
	
	/**
	 * Validates a CPF: checks format and availability.
	 * @param cpf delivery person's CPF
	 */
	public void validarCpf(String cpf) {
		if (!cpfValido(cpf)) {
	        throw new IllegalArgumentException("Digite um CPF válido.");
	    }

	    if (!cpfDisponivel(cpf)) {
	        throw new IllegalArgumentException("O CPF já está em uso.");
	    }
	}
	
	/**
	 * Validates the delivery person's first name.
	 * @param primeiroNome first name
	 */
	public void validarPrimeiroNome(String primeiroNome) {
		if(!primeiroNomeValido(primeiroNome)) {
			throw new IllegalArgumentException("Utilize um nome válido");
		}
	}
	
	/**
	 * Validates the delivery person's middle name.
	 * @param nomeMeio middle name
	 */
	public void validarNomeMeio(String nomeMeio) {
		if(!nomeMeioValido(nomeMeio)) {
			throw new IllegalArgumentException("Utilize um nome válido");
		}
	}
	
	/**
	 * Validates the delivery person's last name.
	 * @param ultimoNome last name
	 */
	public void validarUltimoNome(String ultimoNome) {
		if(!ultimoNomeValido(ultimoNome)) {
			throw new IllegalArgumentException("Utilize um nome válido");
		}
	}
	
	/**
	 * Validates the delivery person's phone number.
	 * @param telefone phone number
	 */
	public void validarTelefone(String telefone) {
		if(!telefoneValido(telefone)) {
			throw new IllegalArgumentException("Utilize um telefone válido");
		}
	}
	
	/**
	 * Validates the delivery person's vehicle plate.
	 * @param placaVeiculo vehicle plate
	 */
	public void validarPlacaVeiculo(String placaVeiculo) {
		if(!placaVeiculoValida(placaVeiculo)) {
			throw new IllegalArgumentException("Digite uma placa de veículo válida");
		}
	}
	
	/**
	 * Updates the delivery person's first name.
	 * @param e delivery person object
	 * @param primeiroNome new first name
	 * @return boolean indicating success
	 */
	public boolean atualizarPrimeiroNome(DeliveryPerson e, String primeiroNome) {
		if (!primeiroNomeValido(primeiroNome)) {
			throw new IllegalArgumentException("Utilize um nome válido: ");
		}
		
		e.setFirstName(primeiroNome);
		return dao.updateDeliveryPerson(conn, e);
	}
	
	/**
	 * Updates the delivery person's middle name.
	 * @param e delivery person object
	 * @param nomeMeio new middle name
	 * @return boolean indicating success
	 */
	public boolean atualizarNomeMeio(DeliveryPerson e, String nomeMeio) {
		if (!nomeMeioValido(nomeMeio)) {
			throw new IllegalArgumentException("Utilize um nome válido: ");
		}
		
		e.setMiddleName(nomeMeio);
		return dao.updateDeliveryPerson(conn, e);
	}
	
	/**
	 * Updates the delivery person's last name.
	 * @param e delivery person object
	 * @param ultimoNome new last name
	 * @return boolean indicating success
	 */
	public boolean atualizarUltimoNome(DeliveryPerson e, String ultimoNome) {
		if (!ultimoNomeValido(ultimoNome)) {
			throw new IllegalArgumentException("Utilize um nome válido: ");
		}
		
		e.setLastName(ultimoNome);
		return dao.updateDeliveryPerson(conn, e);
	}
	
	/**
	 * Updates the delivery person's phone number.
	 * @param e delivery person object
	 * @param telefone new phone number
	 * @return boolean indicating success
	 */
	public boolean atualizarTelefone(DeliveryPerson e, String telefone) {
		if (!telefoneValido(telefone)) {
			throw new IllegalArgumentException("Utilize um telefone válido: ");
		}
		
		e.setPhone(telefone);
		return dao.updateDeliveryPerson(conn, e);
	}
	
	/**
	 * Updates the delivery person's vehicle plate.
	 * @param e delivery person object
	 * @param placaVeiculo new vehicle plate
	 * @return boolean indicating success
	 */
	public boolean atualizarPlacaVeiculo(DeliveryPerson e, String placaVeiculo) {
		if (!placaVeiculoValida(placaVeiculo)) {
			throw new IllegalArgumentException("Utilize uma placa válida: ");
		}
		
		e.setVehicle(placaVeiculo);
		return dao.updateDeliveryPerson(conn, e);
	}
		
	/**
	 * Registers a delivery person in the system.
	 * @param e delivery person to register
	 * @return boolean indicating success
	 */
	public boolean cadastrarEntregador(DeliveryPerson e) {
		return dao.addDeliveryPerson(conn, e);
	}
	
	/**
	 * Returns a delivery person by CPF.
	 * @param cpf delivery person's CPF
	 * @return DeliveryPerson or null if not found
	 */
	public DeliveryPerson retornarEntregador(String cpf) {
		return dao.returnDeliveryPerson(conn, cpf);
	}
	
	/**
	 * Returns all registered delivery persons.
	 * @return list of DeliveryPerson
	 */
	public ArrayList<DeliveryPerson> listarEntregadores() {
		return dao.returnDeliveryPersonList(conn);
	}
	
	/**
	 * Removes a delivery person by CPF.
	 * @param cpf delivery person's CPF
	 * @return boolean indicating success
	 */
	public boolean removerEntregador(String cpf) {
		return dao.deleteDeliveryPerson(conn, cpf);
	}
	
	private boolean cpfValido(String cpf) {	
		return cpf.length() == 11 && cpf.matches("^[0-9]+$");
	}

	private boolean cpfDisponivel(String cpf) {
		return dao.returnDeliveryPerson(conn, cpf) == null;
	}
	
	private boolean primeiroNomeValido(String primeiroNome) {
		return primeiroNome.length() >= 3 && primeiroNome.length() <= 20 && primeiroNome.matches("^[A-Za-zÀ-ÿ]+$");
	}
	
	private boolean nomeMeioValido(String nomeMeio) {
		return nomeMeio.length() >= 3 && nomeMeio.length() <= 40 && nomeMeio.matches("^[A-Za-zÀ-ÿ ]+$");
	}
	
	private boolean ultimoNomeValido(String ultimoNome) {
		return ultimoNome.length() >= 3 && ultimoNome.length() <= 20 && ultimoNome.matches("^[A-Za-zÀ-ÿ]+$");
	}
	
	private boolean telefoneValido(String telefone) {
		return telefone.length() <= 11 && telefone.matches("^[0-9]+$");
	}
	
	/**
	 * Checks if a vehicle plate matches the Mercosur standard.
	 * @param placaVeiculo vehicle plate to validate
	 * @return boolean
	 */
	private boolean placaVeiculoValida(String placaVeiculo) {
		return placaVeiculo.length() == 7 && placaVeiculo.matches("^[A-Z]{3}[0-9][A-Z][0-9]{2}$");
	}
	
	
	
}
