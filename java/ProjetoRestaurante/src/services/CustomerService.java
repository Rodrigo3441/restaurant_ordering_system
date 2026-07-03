package services;

import java.sql.Connection;
import database.CustomerDAO;
import entities.Customer;

/**
 * Class: CustomerService
 *
 * Description:
 * Service class responsible for managing customer business rules.
 *
 * Responsibilities:
 * - provide validation methods for customer information
 * - communicate with the data access layer
 *
 * @author Rodrigo
 * @since 24-04-2026
 */

public class CustomerService {
	private CustomerDAO dao;
	private Connection conn;
	
	/**
	 * Constructor that receives the database connection object.
	 *
	 * @param conn database connection
	 */
	public CustomerService(Connection conn) {
		this.dao = new CustomerDAO();
		this.conn = conn;
	}
	
	/**
	 * Validate CPF: checks format and availability.
	 * @param cpf customer's CPF (Brazilian tax ID)
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
	 * Validate first name integrity.
	 * @param primeiroNome first name
	 */
	public void validarPrimeiroNome(String primeiroNome) {
		if(!primeiroNomeValido(primeiroNome)) {
			throw new IllegalArgumentException("Utilize um nome válido");
		}
	}
	
	/**
	 * Validate middle name integrity.
	 * @param nomeMeio middle name
	 */
	public void validarNomeMeio(String nomeMeio) {
		if(!nomeMeioValido(nomeMeio)) {
			throw new IllegalArgumentException("Utilize um nome válido");
		}
	}
	
	/**
	 * Validate last name integrity.
	 * @param ultimoNome last name
	 */
	public void validarUltimoNome(String ultimoNome) {
		if(!ultimoNomeValido(ultimoNome)) {
			throw new IllegalArgumentException("Utilize um nome válido");
		}
	}
	
	/**
	 * Validate phone number integrity.
	 * @param telefone phone number
	 */
	public void validarTelefone(String telefone) {
		if(!telefoneValido(telefone)) {
			throw new IllegalArgumentException("Utilize um telefone válido");
		}
	}
	
	/**
	 * Validate email integrity.
	 * @param email email address
	 */
	public void validarEmail(String email) {
		if(!emailValido(email)) {
			throw new IllegalArgumentException("Utilize um email válido");
		}
	}
	
	/**
	 * Validate password integrity.
	 * @param senha password
	 */
	public void validarSenha(String senha) {
		if(!senhaValida(senha)) {
			throw new IllegalArgumentException("Utilize uma senha válida");
		}
	}
	
	/**
	 * Update customer's first name.
	 * @param c customer object
	 * @param primeiroNome first name
	 * @return true if update succeeded
	 */
	public boolean atualizarPrimeiroNome(Customer c, String primeiroNome) {
		if (!primeiroNomeValido(primeiroNome)) {
			throw new IllegalArgumentException("Utilize um nome válido: ");
		}
		
		c.setFirstName(primeiroNome);
		return dao.updateCustomer(conn, c);
	}
	
	/**
	 * Update customer's middle name.
	 * @param c customer object
	 * @param nomeMeio middle name
	 * @return true if update succeeded
	 */
	public boolean atualizarNomeMeio(Customer c, String nomeMeio) {
		if (!nomeMeioValido(nomeMeio)) {
			throw new IllegalArgumentException("Utilize um nome válido: ");
		}
		
		c.setMiddleName(nomeMeio);
		return dao.updateCustomer(conn, c);
	}
	
	/**
	 * Update customer's last name.
	 * @param c customer object
	 * @param ultimoNome last name
	 * @return true if update succeeded
	 */
	public boolean atualizarUltimoNome(Customer c, String ultimoNome) {
		if (!ultimoNomeValido(ultimoNome)) {
			throw new IllegalArgumentException("Utilize um nome válido: ");
		}
		
		c.setLastName(ultimoNome);
		return dao.updateCustomer(conn, c);
	}
	
	/**
	 * Update customer's phone number.
	 * @param c customer object
	 * @param telefone phone number
	 * @return true if update succeeded
	 */
	public boolean atualizarTelefone(Customer c, String telefone) {
		if (!telefoneValido(telefone)) {
			throw new IllegalArgumentException("Utilize um telefone válido: ");
		}
		
		c.setPhone(telefone);
		return dao.updateCustomer(conn, c);
	}
	
	/**
	 * Update customer's email.
	 * @param c customer object
	 * @param email email address
	 * @return true if update succeeded
	 */
	public boolean atualizarEmail(Customer c, String email) {
		if (!emailValido(email)) {
			throw new IllegalArgumentException("Utilize um telefone válido: ");
		}
		
		c.setEmail(email);
		return dao.updateCustomer(conn, c);
	}
	
	/**
	 * Update customer's password.
	 * @param c customer object
	 * @param senha password
	 * @return true if update succeeded
	 */
	public boolean atualizarSenha(Customer c, String senha) {
		if (!senhaValida(senha)) {
			throw new IllegalArgumentException("Utilize uma senha válida: ");
		}
		
		c.setPasscode(senha);
		return dao.updateCustomer(conn, c);
	}
	
	/**
	 * Register a new customer in the system.
	 * @param c customer object
	 * @return true if insertion succeeded
	 */
	public boolean cadastrarCliente(Customer c) {
		return dao.addCustomer(conn, c);
	}
	
	/**
	 * Return a customer by CPF.
	 * @param cpf customer's CPF
	 * @return Customer or null if not found
	 */
	public Customer retornarCliente(String cpf) {
		return dao.returnCustomer(conn, cpf);
	}
	
	private boolean cpfValido(String cpf) {	
		return cpf.length() == 11 && cpf.matches("^[0-9]+$");
	}
	
	private boolean cpfDisponivel(String cpf) {
		return dao.returnCustomer(conn, cpf) == null;
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
	
	private boolean emailValido(String email) {
		return email.length() <= 255 && email.contains("@");
	}
	
	private boolean senhaValida(String senha) {
		return senha.length() < 255;
	}	
	
	
}