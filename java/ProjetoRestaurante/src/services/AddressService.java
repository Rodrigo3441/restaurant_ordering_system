package services;

import java.sql.Connection;
import entities.Endereco;
import entities.EnderecoCliente;
import entities.EnderecoRestaurante;
import database.EnderecoDAO;

/**
 * Classe: ServicoEndereco
 *
 * Descrição:
 * Classe responsável por validar serviços de Endereço do cliente e do restaurante
 *
 * Responsabilidades:
 * - oferecer métodos e validações da regra de negócio
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class EnderecoService {
	private EnderecoDAO dao;
	private Connection conn;
	
	public EnderecoService(Connection conn) {
		this.dao = new EnderecoDAO();
		this.conn = conn;
	}
	
	/**
	 * Retorna o endereço do restaurante com base no CNPJ informado
	 * @param cnpj do restaurante
	 * @return objeto restaurante
	 */
	public EnderecoRestaurante retornarEnderecoRestaurante(String cnpj) {
		return dao.retornarEnderecoRestaurante(conn, cnpj);
	}
	
	/**
	 * Retorna o endereço do cliente com base no CPF informado
	 * @param cpf do cliente
	 * @return objeto EnderecoCliente
	 */
	public EnderecoCliente retornarEnderecoCliente(String cpf) {
		return dao.retornarEnderecoCliente(conn, cpf);
	}
	
	/**
	 * Realiza o cadastro de um endereço do restaurante no sistema
	 * @param er objeto EndereçoRestaurante
	 * @return boolean
	 */
	public boolean inserirEnderecoRestaurante(Endereco er) {
		return dao.inserirEnderecoRestaurante(conn, er);
	}
	
	/**
	 * Realiza o cadastro de um endereço do cliente no sistema
	 * @param ec objeto EndereçoCliente
	 * @return boolean
	 */
	public boolean inserirEnderecoCliente(Endereco ec) {
		return dao.inserirEnderecoCliente(conn, ec);
	}
	
	/**
	 * Valida se um CEP inserido é válido
	 * @param cep do endereço
	 */
	public void validarCep(String cep) {
		if (!cepValido(cep)) {
			throw new IllegalArgumentException("Digite um CEP válido");
		}
	}
	
	/**
	 * Valida se o nome da rua do endereço é válido
	 * @param nome da rua do endereço
	 */
	public void validarNome(String nome) {
		if (!nomeValido(nome)) {
			throw new IllegalArgumentException("Digite um nome válido");
		}
	}
	
	/**
	 * Valida se o número do endereço é válido
	 * @param numero do endereço
	 */
	public void validarNumero(int numero) {
		if (!numeroValido(numero)) {
			throw new IllegalArgumentException("Digite um número válido");
		}
	}
	
	/**
	 * Realiza a atualização do CEP do endereço do cliente no sistema
	 * @param ec objeto EnderecoCliente
	 * @param cep do endereço
	 * @return boolean
	 */
	public boolean atualizarCepEnderecoCliente(Endereco ec, String cep) {
		if (!cepValido(cep)) {
			throw new IllegalArgumentException("Digite um CEP válido");
		}
		
		ec.setCep(cep);
		return dao.atualizarEnderecoCliente(conn, ec);
	}
	
	/**
	 * Realiza a atualização do nome da rua do endereço do cliente no sistema
	 * @param ec objeto EnderecoCliente
	 * @param nome da rua do cliente
	 * @return boolean
	 */
	public boolean atualizarNomeEnderecoCliente(Endereco ec, String nome) {
		if (!nomeValido(nome)) {
			throw new IllegalArgumentException("Digite um nome válido");
		}
		
		ec.setNome(nome);
		return dao.atualizarEnderecoCliente(conn, ec);
	}
	
	/**
	 * Realiza a atualização do número do endereço do cliente no sistema
	 * @param ec objeto EnderecoCliente
	 * @param numero do endereço cliente
	 * @return boolean
	 */
	public boolean atualizarNumeroEnderecoCliente(Endereco ec, int numero) {
		if (!numeroValido(numero)) {
			throw new IllegalArgumentException("Digite um número válido");
		}
		
		ec.setNumero(numero);
		return dao.atualizarEnderecoCliente(conn, ec);
	}
	
	/**
	 * Realiza a atualização do CEP do endereço do restaurante no sistema
	 * @param er objeto EnderecoRestaurante
	 * @param cep do endereço
	 * @return boolean
	 */
	public boolean atualizarCepEnderecoRestaurante(Endereco er, String cep) {
		if (!cepValido(cep)) {
			throw new IllegalArgumentException("Digite um CEP válido");
		}
		
		er.setCep(cep);
		return dao.atualizarEnderecoRestaurante(conn, er);
	}
	
	/**
	 * Realiza a atualização do nome da rua do endereço do restaurante no sistema
	 * @param er objeto EnderecoRestaurante
	 * @param nome da rua do restaurante
	 * @return boolean
	 */
	public boolean atualizarNomeEnderecoRestaurante(Endereco er, String nome) {
		if (!nomeValido(nome)) {
			throw new IllegalArgumentException("Digite um nome válido");
		}
		
		er.setNome(nome);
		return dao.atualizarEnderecoRestaurante(conn, er);
	}
	
	/**
	 * Realiza a atualização do número do endereço do restaurante no sistema
	 * @param er objeto EnderecoRestaurante
	 * @param numero do endereço restaurante
	 * @return boolean
	 */
	public boolean atualizarNumeroEnderecoRestaurante(Endereco er, int numero) {
		if (!numeroValido(numero)) {
			throw new IllegalArgumentException("Digite um número válido");
		}
		
		er.setNumero(numero);
		return dao.atualizarEnderecoRestaurante(conn, er);
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