package services;

import java.sql.Connection;
import database.ClienteDAO;
import entities.Cliente;

/**
 * Classe: ServicoCliente
 *
 * Descrição:
 * Classe responsável por gerenciar as regras de negócio do cliente
 *
 * Responsabilidades:
 * - oferecer métodos de validação das informações
 * - se comunicar com a camada de dados
 *
 * @author Rodrigo
 * @since 24-04-2026
 */

public class ClienteService {
	private ClienteDAO dao;
	private Connection conn;
	
	/**
	 * Construtor que recebe o objeto para conexão com a camada de dados
	 * 
	 * @param dao objeto AccesoDadosCliente
	 */
	public ClienteService(Connection conn) {
		this.dao = new ClienteDAO();
		this.conn = conn;
	}
	
	/**
	 * Responsável por verificar disponibilidade de CPF e se o mesmo é válido
	 * @param cpf do cliente
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
	 * Responsável por verificar integridade do primeiro nome
	 * @param primeiroNome
	 */
	public void validarPrimeiroNome(String primeiroNome) {
		if(!primeiroNomeValido(primeiroNome)) {
			throw new IllegalArgumentException("Utilize um nome válido");
		}
	}
	
	/**
	 * Responsável por verificar integridade do nome do meio
	 * @param nomeMeio do cliente
	 */
	public void validarNomeMeio(String nomeMeio) {
		if(!nomeMeioValido(nomeMeio)) {
			throw new IllegalArgumentException("Utilize um nome válido");
		}
	}
	
	/**
	 * Responsável por verificar integridade do ultimo nome
	 * @param ultimoNome do cliente
	 */
	public void validarUltimoNome(String ultimoNome) {
		if(!ultimoNomeValido(ultimoNome)) {
			throw new IllegalArgumentException("Utilize um nome válido");
		}
	}
	
	/**
	 * Responsável por verificar integridade do telefone
	 * @param telefone do cliente
	 */
	public void validarTelefone(String telefone) {
		if(!telefoneValido(telefone)) {
			throw new IllegalArgumentException("Utilize um telefone válido");
		}
	}
	
	/**
	 * Responsável por verificar integridade do email
	 * @param email do cliente
	 */
	public void validarEmail(String email) {
		if(!emailValido(email)) {
			throw new IllegalArgumentException("Utilize um email válido");
		}
	}
	
	/**
	 * Responsável por verificar integridade de senha
	 * @param senha do cliente
	 */
	public void validarSenha(String senha) {
		if(!senhaValida(senha)) {
			throw new IllegalArgumentException("Utilize uma senha válida");
		}
	}
	
	/**
	 * Atualiza primeiro nome do cliente
	 * @param c objeto cliente
	 * @param primeiroNome do cliente
	 * @return êxito ou não
	 */
	public boolean atualizarPrimeiroNome(Cliente c, String primeiroNome) {
		if (!primeiroNomeValido(primeiroNome)) {
			throw new IllegalArgumentException("Utilize um nome válido: ");
		}
		
		c.setPrimeiroNome(primeiroNome);
		return dao.atualizarCliente(conn, c);
	}
	
	/**
	 * Atualiza nome do meio do cliente
	 * @param c objeto cliente
	 * @param nomeMeio do cliente
	 * @return êxito ou não 
	 */
	public boolean atualizarNomeMeio(Cliente c, String nomeMeio) {
		if (!nomeMeioValido(nomeMeio)) {
			throw new IllegalArgumentException("Utilize um nome válido: ");
		}
		
		c.setNomeMeio(nomeMeio);
		return dao.atualizarCliente(conn, c);
	}
	
	/**
	 * Atualiza ultimo nome do cliente
	 * @param c objeto cliente
	 * @param ultimoNome do cliente
	 * @return êxito ou não
	 */
	public boolean atualizarUltimoNome(Cliente c, String ultimoNome) {
		if (!ultimoNomeValido(ultimoNome)) {
			throw new IllegalArgumentException("Utilize um nome válido: ");
		}
		
		c.setUltimoNome(ultimoNome);
		return dao.atualizarCliente(conn, c);
	}
	
	/**
	 * atualiza o telefone do cliente
	 * @param c objeto cliente
	 * @param telefone do cliente
	 * @return êxito ou não
	 */
	public boolean atualizarTelefone(Cliente c, String telefone) {
		if (!telefoneValido(telefone)) {
			throw new IllegalArgumentException("Utilize um telefone válido: ");
		}
		
		c.setTelefone(telefone);
		return dao.atualizarCliente(conn, c);
	}
	
	/**
	 * atualiza o email do cliente
	 * @param c objeto cliente
	 * @param email do cliente
	 * @return êxito ou não
	 */
	public boolean atualizarEmail(Cliente c, String email) {
		if (!emailValido(email)) {
			throw new IllegalArgumentException("Utilize um telefone válido: ");
		}
		
		c.setEmail(email);
		return dao.atualizarCliente(conn, c);
	}
	
	/**
	 * Atualiza a senha do cliente
	 * @param c objeto cliente
	 * @param senha do cliente
	 * @return êxito ou não
	 */
	public boolean atualizarSenha(Cliente c, String senha) {
		if (!senhaValida(senha)) {
			throw new IllegalArgumentException("Utilize uma senha válida: ");
		}
		
		c.setSenha(senha);
		return dao.atualizarCliente(conn, c);
	}
	
	/**
	 * cadastra um cliente no sistema
	 * @param c objeto cliente
	 * @return boolean
	 */
	public boolean cadastrarCliente(Cliente c) {
		return dao.inserirCliente(conn, c);
	}
	
	/**
	 * retorna um cliente baseado em seu cpf
	 * @param cnpj
	 * @return Cliente
	 */
	public Cliente retornarCliente(String cpf) {
		return dao.retornarCliente(conn, cpf);
	}
	
	private boolean cpfValido(String cpf) {	
		return cpf.length() == 11 && cpf.matches("^[0-9]+$");
	}
	
	private boolean cpfDisponivel(String cpf) {
		return dao.retornarCliente(conn, cpf) == null;
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