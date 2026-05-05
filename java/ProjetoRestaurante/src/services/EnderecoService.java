package services;

import java.sql.Connection;
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
	
	public EnderecoService(Connection conn) {
		this.dao = new EnderecoDAO(conn);
	}
	
	/**
	 * Retorna o endereço do restaurante com base no CNPJ informado
	 * @param cnpj do restaurante
	 * @return um objeto restaurante
	 */
	public EnderecoRestaurante retornarEnderecoRestaurante(String cnpj) {
		return dao.retornarEnderecoRestaurante(cnpj);
	}
	
	/**
	 * 
	 * @param cpf
	 * @return
	 */
	public EnderecoCliente retornarEnderecoCliente(String cpf) {
		return dao.retornarEnderecoCliente(cpf);
	}
	
	/**
	 * 
	 * @param er
	 * @return
	 */
	public boolean inserirEnderecoRestaurante(EnderecoRestaurante er) {
		return dao.inserirEnderecoRestaurante(er);
	}
	
	public boolean inserirEnderecoCliente(EnderecoCliente ec) {
		return dao.inserirEnderecoCliente(ec);
	}
	
	/**
	 * 
	 * @param cep
	 */
	public void validarCep(String cep) {
		if (!cepValido(cep)) {
			throw new IllegalArgumentException("Digite um CEP válido");
		}
	}
	
	/**
	 * 
	 * @param nome
	 */
	public void validarNome(String nome) {
		if (!nomeValido(nome)) {
			throw new IllegalArgumentException("Digite um nome válido");
		}
	}
	
	/**
	 * 
	 * @param numero
	 */
	public void validarNumero(int numero) {
		if (!numeroValido(numero)) {
			throw new IllegalArgumentException("Digite um número válido");
		}
	}
	
	/**
	 * 
	 * @param ec
	 * @param cep
	 * @return
	 */
	public boolean atualizarCepEnderecoCliente(EnderecoCliente ec, String cep) {
		if (!cepValido(cep)) {
			throw new IllegalArgumentException("Digite um CEP válido");
		}
		
		ec.setCep(cep);
		return dao.atualizarEnderecoCliente(ec);
	}
	
	/**
	 * 
	 * @param ec
	 * @param nome
	 * @return
	 */
	public boolean atualizarNomeEnderecoCliente(EnderecoCliente ec, String nome) {
		if (!nomeValido(nome)) {
			throw new IllegalArgumentException("Digite um nome válido");
		}
		
		ec.setNome(nome);
		return dao.atualizarEnderecoCliente(ec);
	}
	
	/**
	 * 
	 * @param ec
	 * @param numero
	 * @return
	 */
	public boolean atualizarNumeroEnderecoCliente(EnderecoCliente ec, int numero) {
		if (!numeroValido(numero)) {
			throw new IllegalArgumentException("Digite um número válido");
		}
		
		ec.setNumero(numero);
		return dao.atualizarEnderecoCliente(ec);
	}
	
	/**
	 * 
	 * @param er
	 * @param cep
	 * @return
	 */
	public boolean atualizarCepEnderecoRestaurante(EnderecoRestaurante er, String cep) {
		if (!cepValido(cep)) {
			throw new IllegalArgumentException("Digite um CEP válido");
		}
		
		er.setCep(cep);
		return dao.atualizarEnderecoRestaurante(er);
	}
	
	/**
	 * 
	 * @param er
	 * @param nome
	 * @return
	 */
	public boolean atualizarNomeEnderecoRestaurante(EnderecoRestaurante er, String nome) {
		if (!nomeValido(nome)) {
			throw new IllegalArgumentException("Digite um nome válido");
		}
		
		er.setNome(nome);
		return dao.atualizarEnderecoRestaurante(er);
	}
	
	/**
	 * 
	 * @param er
	 * @param numero
	 * @return
	 */
	public boolean atualizarNumeroEnderecoRestaurante(EnderecoRestaurante er, int numero) {
		if (!numeroValido(numero)) {
			throw new IllegalArgumentException("Digite um número válido");
		}
		
		er.setNumero(numero);
		return dao.atualizarEnderecoRestaurante(er);
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
