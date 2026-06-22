package services;

import java.sql.Connection;
import java.util.ArrayList;

import database.RestauranteDAO;
import entities.Restaurante;

/**
 * Classe: ServicoRestaurante
 *
 * Descrição:
 * Classe responsável por gerenciar as regras de negócio do restaurante
 *
 * Responsabilidades:
 * - oferecer métodos de validação das informações
 * - se comunicar com a camada de dados
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class RestauranteService {
	private RestauranteDAO dao;
	private Connection conn;
	
	
	public RestauranteService(Connection conn) {
		this.dao = new RestauranteDAO();
		this.conn = conn;
	}
	
	/**
	 * Responsável por verificar disponibilidade de CNPJ e se é válido
	 * @param cnpj do restaurante
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
	 * Responsável por verificar integridade do nome do restaurante
	 * @param primeiroNome
	 */
	public void validarNome(String nome) {
		if(!nomeValido(nome)) {
			throw new IllegalArgumentException("Utilize um nome válido");
		}
	}
	
	/**
	 * Responsável por verificar integridade do telefone
	 * @param telefone do restaurante
	 */
	public void validarTelefone(String telefone) {
		if(!telefoneValido(telefone)) {
			throw new IllegalArgumentException("Utilize um telefone válido");
		}
	}
		
	/**
	 * Responsável por verificar integridade de senha
	 * @param senha do restaurante
	 */
	public void validarSenha(String senha) {
		if(!senhaValida(senha)) {
			throw new IllegalArgumentException("Utilize uma senha válida");
		}
	}
	
	/**
	 * Atualiza nome do restaurante
	 * @param r objeto restaurante
	 * @param nome do restaurante
	 * @return boolean
	 */
	public boolean atualizarNome(Restaurante r, String nome) {
		if (!nomeValido(nome)) {
			throw new IllegalArgumentException("Utilize um nome válido: ");
		}
		
		r.setNome(nome);
		return dao.atualizarRestaurante(conn, r);
	}
	
	/**
	 * atualiza o telefone do restaurante
	 * @param r objeto restaurante
	 * @param telefone do restaurante
	 * @return boolean
	 */
	public boolean atualizarTelefone(Restaurante r, String telefone) {
		if (!telefoneValido(telefone)) {
			throw new IllegalArgumentException("Utilize um nome válido: ");
		}
		
		r.setTelefone(telefone);
		return dao.atualizarRestaurante(conn, r);
	}
	
	/**
	 * Atualiza a senha do restaurante
	 * @param r objeto restaurante
	 * @param senha do restaurante
	 * @return boolean
	 */
	public boolean atualizarSenha(Restaurante r, String senha) {
		if (!senhaValida(senha)) {
			throw new IllegalArgumentException("Utilize uma senha válida: ");
		}
		
		r.setSenha(senha);
		return dao.atualizarRestaurante(conn, r);
	}
	
	/**
	 * Realiza o cadastro de um restaurante no sistema
	 * @param r objeto restaurante
	 * @return boolean
	 */
	public boolean cadastrarRestaurante(Restaurante r) {
		return dao.inserirRestaurante(conn, r);
	}
	
	/**
	 * Retorna um restaurante com base no CNPJ informado
	 * @param cnpj do restaurante
	 * @return objeto Restaurante
	 */
	public Restaurante retornarRestaurante(String cnpj) {
		return dao.retornarRestaurante(conn, cnpj);
	}
	
	/**
	 * Retorna uma lista com todos os restaurantes do sistema
	 * @return arraylist do tipo Restaurante
	 */
	public ArrayList<Restaurante> listarRestaurantes(){
		return dao.listarRestaurantes(conn);
	}

	private boolean cnpjValido(String cnpj) {	
		return cnpj.length() == 14 && cnpj.matches("^[0-9]+$");
	}

	private boolean cnpjDisponivel(String cnpj) {
		return dao.retornarRestaurante(conn, cnpj) == null;
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
