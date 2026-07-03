package services;

import java.sql.Connection;
import java.util.ArrayList;

import database.RestaurantDAO;
import entities.Restaurant;

/**
 * Class: RestaurantService
 *
 * Description:
 * Service class responsible for enforcing business rules for restaurants.
 *
 * Responsibilities:
 * - provide validation methods for restaurant data
 * - communicate with the data access layer
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class RestaurantService {
	private RestaurantDAO dao;
	private Connection conn;
	
	
	public RestaurantService(Connection conn) {
		this.dao = new RestaurantDAO();
		this.conn = conn;
	}
	
	/**
	 * Validates that a CNPJ is correctly formatted and not already in use.
	 * @param cnpj restaurant CNPJ
	 */
	public void validarCnpj(String cnpj) {
		if (!cnpjValido(cnpj)) {
	        throw new IllegalArgumentException("Digite um CNPJ válido.");
	    }

	    if (!cnpjDisponivel(cnpj)) {
	        throw new IllegalArgumentException("O CNPJ já está em uso.");
	    }
	}
	
	/**
	 * Validates the integrity of the restaurant name.
	 * @param nome restaurant name
	 */
	public void validarNome(String nome) {
		if(!nomeValido(nome)) {
			throw new IllegalArgumentException("Utilize um nome válido");
		}
	}
	
	/**
	 * Validates the integrity of the restaurant phone number.
	 * @param telefone restaurant phone
	 */
	public void validarTelefone(String telefone) {
		if(!telefoneValido(telefone)) {
			throw new IllegalArgumentException("Utilize um telefone válido");
		}
	}
		
	/**
	 * Validates the integrity of the restaurant password.
	 * @param senha restaurant password
	 */
	public void validarSenha(String senha) {
		if(!senhaValida(senha)) {
			throw new IllegalArgumentException("Utilize uma senha válida");
		}
	}
	
	/**
	 * Updates the restaurant's name.
	 * @param r restaurant object
	 * @param nome new restaurant name
	 * @return boolean indicating success
	 */
	public boolean atualizarNome(Restaurant r, String nome) {
		if (!nomeValido(nome)) {
			throw new IllegalArgumentException("Utilize um nome válido: ");
		}
		
		r.setName(nome);
		return dao.updateRestaurant(conn, r);
	}
	
	/**
	 * Updates the restaurant's phone number.
	 * @param r restaurant object
	 * @param telefone new phone number
	 * @return boolean indicating success
	 */
	public boolean atualizarTelefone(Restaurant r, String telefone) {
		if (!telefoneValido(telefone)) {
			throw new IllegalArgumentException("Utilize um nome válido: ");
		}
		
		r.setPhone(telefone);
		return dao.updateRestaurant(conn, r);
	}
	
	/**
	 * Updates the restaurant's password.
	 * @param r restaurant object
	 * @param senha new password
	 * @return boolean indicating success
	 */
	public boolean atualizarSenha(Restaurant r, String senha) {
		if (!senhaValida(senha)) {
			throw new IllegalArgumentException("Utilize uma senha válida: ");
		}
		
		r.setPasscode(senha);
		return dao.updateRestaurant(conn, r);
	}
	
	/**
	 * Registers a new restaurant in the system.
	 * @param r restaurant object
	 * @return boolean indicating success
	 */
	public boolean cadastrarRestaurante(Restaurant r) {
		return dao.addRestaurant(conn, r);
	}
	
	/**
	 * Returns a restaurant by the given CNPJ.
	 * @param cnpj restaurant CNPJ
	 * @return Restaurant object
	 */
	public Restaurant retornarRestaurante(String cnpj) {
		return dao.returnRestaurant(conn, cnpj);
	}
	
	/**
	 * Returns a list of all restaurants in the system.
	 * @return ArrayList of Restaurant
	 */
	public ArrayList<Restaurant> listarRestaurantes(){
		return dao.returnRestaurantList(conn);
	}

	private boolean cnpjValido(String cnpj) {	
		return cnpj.length() == 14 && cnpj.matches("^[0-9]+$");
	}

	private boolean cnpjDisponivel(String cnpj) {
		return dao.returnRestaurant(conn, cnpj) == null;
	}
	
	private boolean nomeValido(String nome) {
		return nome.length() >= 3 && nome.length() <= 40;
	}

	private boolean telefoneValido(String telefone) {
		return telefone.length() <= 11 && telefone.matches("^[0-9]+$");
	}
		
	private boolean senhaValida(String senha) {
		return senha.length() < 255;
	}	
	
	
}
