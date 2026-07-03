package services;

import java.sql.Connection;
import entities.Address;
import entities.CustomerAddress;
import entities.RestaurantAddress;
import database.AddressDAO;

/**
 * Class: AddressService
 *
 * Description:
 * Service class responsible for validating address operations for customers and restaurants
 *
 * Responsibilities:
 * - provide business logic methods and validation
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class AddressService {
	private AddressDAO dao;
	private Connection conn;
	
	public AddressService(Connection conn) {
		this.dao = new AddressDAO();
		this.conn = conn;
	}
	
	/**
	 * Returns the restaurant address based on the provided CNPJ
	 * @param cnpj restaurant CNPJ
	 * @return restaurant address object
	 */
	public RestaurantAddress retornarEnderecoRestaurante(String cnpj) {
		return dao.returnRestaurantAddress(conn, cnpj);
	}
	
	/**
	 * Returns the customer address based on the provided CPF
	 * @param cpf customer CPF
	 * @return customer address object
	 */
	public CustomerAddress retornarEnderecoCliente(String cpf) {
		return dao.returnCustomerAddress(conn, cpf);
	}
	
	/**
	 * Inserts a restaurant address into the system
	 * @param er restaurant address object
	 * @return boolean
	 */
	public boolean inserirEnderecoRestaurante(Address er) {
		return dao.addRestaurantAddress(conn, er);
	}
	
	/**
	 * Inserts a customer address into the system
	 * @param ec customer address object
	 * @return boolean
	 */
	public boolean inserirEnderecoCliente(Address ec) {
		return dao.addCustomerAddress(conn, ec);
	}
	
	/**
	 * Validates whether the provided ZIP code is valid
	 * @param cep address ZIP code
	 */
	public void validarCep(String cep) {
		if (!cepValido(cep)) {
			throw new IllegalArgumentException("Digite um CEP válido");
		}
	}
	
	/**
	 * Validates whether the provided street name is valid
	 * @param nome street name
	 */
	public void validarNome(String nome) {
		if (!nomeValido(nome)) {
			throw new IllegalArgumentException("Digite um nome válido");
		}
	}
	
	/**
	 * Validates whether the provided address number is valid
	 * @param numero address number
	 */
	public void validarNumero(int numero) {
		if (!numeroValido(numero)) {
			throw new IllegalArgumentException("Digite um número válido");
		}
	}
	
	/**
	 * Updates the customer address ZIP code in the system
	 * @param ec customer address object
	 * @param cep address ZIP code
	 * @return boolean
	 */
	public boolean atualizarCepEnderecoCliente(Address ec, String cep) {
		if (!cepValido(cep)) {
			throw new IllegalArgumentException("Digite um CEP válido");
		}
		
		ec.setPostalCode(cep);
		return dao.updateCustomerAddress(conn, ec);
	}
	
	/**
	 * Updates the customer street name in the system
	 * @param ec customer address object
	 * @param nome customer street name
	 * @return boolean
	 */
	public boolean atualizarNomeEnderecoCliente(Address ec, String nome) {
		if (!nomeValido(nome)) {
			throw new IllegalArgumentException("Digite um nome válido");
		}
		
		ec.setName(nome);
		return dao.updateCustomerAddress(conn, ec);
	}
	
	/**
	 * Updates the customer address number in the system
	 * @param ec customer address object
	 * @param numero customer address number
	 * @return boolean
	 */
	public boolean atualizarNumeroEnderecoCliente(Address ec, int numero) {
		if (!numeroValido(numero)) {
			throw new IllegalArgumentException("Digite um número válido");
		}
		
		ec.setNumber(numero);
		return dao.updateCustomerAddress(conn, ec);
	}
	
	/**
	 * Updates the restaurant address ZIP code in the system
	 * @param er restaurant address object
	 * @param cep address ZIP code
	 * @return boolean
	 */
	public boolean atualizarCepEnderecoRestaurante(Address er, String cep) {
		if (!cepValido(cep)) {
			throw new IllegalArgumentException("Digite um CEP válido");
		}
		
		er.setPostalCode(cep);
		return dao.updateRestaurantAddress(conn, er);
	}
	
	/**
	 * Updates the restaurant street name in the system
	 * @param er restaurant address object
	 * @param nome restaurant street name
	 * @return boolean
	 */
	public boolean atualizarNomeEnderecoRestaurante(Address er, String nome) {
		if (!nomeValido(nome)) {
			throw new IllegalArgumentException("Digite um nome válido");
		}
		
		er.setName(nome);
		return dao.updateRestaurantAddress(conn, er);
	}
	
	/**
	 * Updates the restaurant address number in the system
	 * @param er restaurant address object
	 * @param numero restaurant address number
	 * @return boolean
	 */
	public boolean atualizarNumeroEnderecoRestaurante(Address er, int numero) {
		if (!numeroValido(numero)) {
			throw new IllegalArgumentException("Digite um número válido");
		}
		
		er.setNumber(numero);
		return dao.updateRestaurantAddress(conn, er);
	}
	
	private boolean cepValido(String cep) {
		return cep.length() <= 8 && cep.matches("^[0-9]+$");
	}
	
	private boolean nomeValido(String nome) {
		return nome.length() <= 100 && nome.matches("^[a-zA-ZÀ-ÿ ]+$");
	}
	
	private boolean numeroValido(int numero) {
		return numero >= 0;
	}
}