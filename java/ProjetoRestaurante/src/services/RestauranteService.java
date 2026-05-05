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
	//conexão com o banco de dados que será usada em todas as operações
	private RestauranteDAO dao;
	
	/**
	 * Construtor que recebe o objeto para conexão com a camada de dados
	 * @param dao objeto AcessoDadosRestaurante
	 */
	public RestauranteService(Connection conn) {
		this.dao = new RestauranteDAO(conn);
	}
	
	/**
	 * Método validarCnpj
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
	 * Método validarNome
	 * Responsável por verificar integridade do nome do restaurante
	 * @param primeiroNome
	 */
	public void validarNome(String nome) {
		if(!nomeValido(nome)) {
			throw new IllegalArgumentException("Utilize um nome válido");
		}
	}
	
	/**
	 * Método validarTelefone
	 * Responsável por verificar integridade do telefone
	 * @param telefone do restaurante
	 */
	public void validarTelefone(String telefone) {
		if(!telefoneValido(telefone)) {
			throw new IllegalArgumentException("Utilize um telefone válido");
		}
	}
		
	/**
	 * Método validarSenha
	 * Responsável por verificar integridade de senha
	 * @param senha do restaurante
	 */
	public void validarSenha(String senha) {
		if(!senhaValida(senha)) {
			throw new IllegalArgumentException("Utilize uma senha válida");
		}
	}
	
	/**
	 * Método atualizarNome
	 * Atualiza nome do restaurante
	 * @param r objeto restaurante
	 * @param nome do restaurante
	 * @return êxito ou não
	 */
	public boolean atualizarNome(Restaurante r, String nome) {
		if (!nomeValido(nome)) {
			throw new IllegalArgumentException("Utilize um nome válido: ");
		}
		
		r.setNome(nome);
		return dao.atualizarRestaurante(r);
	}
	
	/**
	 * Método atualizarTelefone
	 * atualiza o telefone do restaurante
	 * @param r objeto restaurante
	 * @param telefone do restaurante
	 * @return êxito ou não
	 */
	public boolean atualizarTelefone(Restaurante r, String telefone) {
		if (!telefoneValido(telefone)) {
			throw new IllegalArgumentException("Utilize um nome válido: ");
		}
		
		r.setTelefone(telefone);
		return dao.atualizarRestaurante(r);
	}
	
	/**
	 * Método atualizarSenha
	 * Atualiza a senha do restaurante
	 * @param r objeto restaurante
	 * @param senha do restaurante
	 * @return êxito ou não
	 */
	public boolean atualizarSenha(Restaurante r, String senha) {
		if (!senhaValida(senha)) {
			throw new IllegalArgumentException("Utilize uma senha válida: ");
		}
		
		r.setSenha(senha);
		return dao.atualizarRestaurante(r);
	}
	
	/**
	 * Método cadastrarRestaurante
	 * @param r objeto restaurante
	 * @return êxito ou não
	 */
	public boolean cadastrarRestaurante(Restaurante r) {
		return dao.inserirRestaurante(r);
	}
	
	/**
	 * Método retornarRestaurante
	 * @param cnpj do restaurante
	 * @return objeto Restaurante
	 */
	public Restaurante retornarRestaurante(String cnpj) {
		return dao.retornarRestaurante(cnpj);
	}
	
	/**
	 * Método listarRestaurantes
	 * @return arraylist
	 */
	public ArrayList<Restaurante> listarRestaurantes(){
		return dao.listarRestaurantes();
	}

	private boolean cnpjValido(String cnpj) {	
		return cnpj.length() == 14 && cnpj.matches("^[0-9]+$");
	}

	private boolean cnpjDisponivel(String cnpj) {
		return dao.retornarRestaurante(cnpj) == null;
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
