package services;

import java.sql.Connection;
import java.util.ArrayList;
import database.EntregadorDAO;
import entities.Entregador;

/**
 * Classe: EntregadorService
 *
 * Descrição:
 * Classe responsável por gerenciar as regras de negócio do entregador
 *
 * Responsabilidades:
 * - oferecer métodos de validação das informações
 * - se comunicar com a camada de dados
 *
 * @author Rodrigo
 * @since 30-04-2026
 */

public class EntregadorService {
	//conexão com o banco de dados que será usada em todas as operações
	private EntregadorDAO dao;
	
	public EntregadorService(Connection conn) {
		this.dao = new EntregadorDAO(conn);
	}
	
	/**
	 * Responsável por verificar disponibilidade de CPF e se é válido
	 * @param cpf do entregador
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
	 * @param primeiroNome do Entregador
	 */
	public void validarPrimeiroNome(String primeiroNome) {
		if(!primeiroNomeValido(primeiroNome)) {
			throw new IllegalArgumentException("Utilize um nome válido");
		}
	}
	
	/**
	 * Responsável por verificar integridade do nome do meio
	 * @param nomeMeio do Entregador
	 */
	public void validarNomeMeio(String nomeMeio) {
		if(!nomeMeioValido(nomeMeio)) {
			throw new IllegalArgumentException("Utilize um nome válido");
		}
	}
	
	/**
	 * Responsável por verificar integridade do ultimo nome
	 * @param ultimoNome do entregador
	 */
	public void validarUltimoNome(String ultimoNome) {
		if(!ultimoNomeValido(ultimoNome)) {
			throw new IllegalArgumentException("Utilize um nome válido");
		}
	}
	
	/**
	 * Responsável por verificar integridade do telefone
	 * @param telefone do entregador
	 */
	public void validarTelefone(String telefone) {
		if(!telefoneValido(telefone)) {
			throw new IllegalArgumentException("Utilize um telefone válido");
		}
	}
	
	/**
	 * Responsável por verificar integridade do veiculo
	 * @param veiculo vinculado ao entregador
	 */
	public void validarPlacaVeiculo(String placaVeiculo) {
		if(!placaVeiculoValida(placaVeiculo)) {
			throw new IllegalArgumentException("Digite uma placa de veículo válida");
		}
	}
	
	/**
	 * Atualiza primeiro nome do entregador
	 * @param e objeto entregador
	 * @param primeiroNome do entregador
	 * @return êxito ou não
	 */
	public boolean atualizarPrimeiroNome(Entregador e, String primeiroNome) {
		if (!primeiroNomeValido(primeiroNome)) {
			throw new IllegalArgumentException("Utilize um nome válido: ");
		}
		
		e.setPrimeiroNome(primeiroNome);
		return dao.atualizarEntregador(e);
	}
	
	/**
	 * Atualiza nome do meio do entregador
	 * @param e objeto entregador
	 * @param nomeMeio do entregador
	 * @return êxito ou não 
	 */
	public boolean atualizarNomeMeio(Entregador e, String nomeMeio) {
		if (!nomeMeioValido(nomeMeio)) {
			throw new IllegalArgumentException("Utilize um nome válido: ");
		}
		
		e.setNomeMeio(nomeMeio);
		return dao.atualizarEntregador(e);
	}
	
	/**
	 * Atualiza ultimo nome do entregador
	 * @param e objeto entregador
	 * @param ultimoNome do entregador
	 * @return êxito ou não
	 */
	public boolean atualizarUltimoNome(Entregador e, String ultimoNome) {
		if (!ultimoNomeValido(ultimoNome)) {
			throw new IllegalArgumentException("Utilize um nome válido: ");
		}
		
		e.setUltimoNome(ultimoNome);
		return dao.atualizarEntregador(e);
	}
	
	/**
	 * atualiza o telefone do entregador
	 * @param e objeto entregador
	 * @param telefone do entregador
	 * @return boolean
	 */
	public boolean atualizarTelefone(Entregador e, String telefone) {
		if (!telefoneValido(telefone)) {
			throw new IllegalArgumentException("Utilize um telefone válido: ");
		}
		
		e.setTelefone(telefone);
		return dao.atualizarEntregador(e);
	}
	
	/**
	 * Atualiza a placa do veículo do entregador
	 * @param e Entregador
	 * @param placaVeiculo
	 * @return boolean
	 */
	public boolean atualizarPlacaVeiculo(Entregador e, String placaVeiculo) {
		if (!placaVeiculoValida(placaVeiculo)) {
			throw new IllegalArgumentException("Utilize uma placa válida: ");
		}
		
		e.setVeiculo(placaVeiculo);
		return dao.atualizarEntregador(e);
	}
		
	/**
	 * Cadastra um entregador no sistema 
	 * @param r
	 * @return boolean
	 */
	public boolean cadastrarEntregador(Entregador e) {
		return dao.inserirEntregador(e);
	}
	
	/**
	 * Retorna um único entregador com base no cpf informado
	 * @param cpf do entregador
	 * @return Entregador
	 */
	public Entregador retornarEntregador(String cpf) {
		return dao.retornarEntregador(cpf);
	}
	
	/**
	 * Retorna todos os entregadores que está cadastrados no sistema
	 * @return ArrayList
	 */
	public ArrayList<Entregador> listarEntregadores() {
		return dao.listarEntregadores();
	}
	
	/**
	 * Faz a remoção de um entregador do sistema e retorna se houve êxito na operação
	 * @param cpf do entregador
	 * @return boolean
	 */
	public boolean removerEntregador(String cpf) {
		return dao.deletarEntregador(cpf);
	}
	
	private boolean cpfValido(String cpf) {	
		return cpf.length() == 11 && cpf.matches("^[0-9]+$");
	}

	private boolean cpfDisponivel(String cpf) {
		return dao.retornarEntregador(cpf) == null;
	}
	
	private boolean primeiroNomeValido(String primeiroNome) {
		return primeiroNome.length() >= 3 && primeiroNome.length() <= 20 && primeiroNome.matches("^[A-Za-zÀ-ÿ]+$");
	}
	
	private boolean nomeMeioValido(String nomeMeio) {
		return nomeMeio.length() >= 3 && nomeMeio.length() <= 20 && nomeMeio.matches("^[A-Za-zÀ-ÿ ]+$");
	}
	
	private boolean ultimoNomeValido(String ultimoNome) {
		return ultimoNome.length() >= 3 && ultimoNome.length() <= 20 && ultimoNome.matches("^[A-Za-zÀ-ÿ]+$");
	}
	
	private boolean telefoneValido(String telefone) {
		return telefone.length() <= 11 && telefone.matches("^[0-9]+$");
	}
	
	/**
	 * Verifica se a placa do carro do entregador está no formato certo de 7 caracteres
	 * e no padrão mercosul LLLNLNN
	 * @param placaVeiculo
	 * @return boolean
	 */
	private boolean placaVeiculoValida(String placaVeiculo) {
		return placaVeiculo.length() == 7 && placaVeiculo.matches("^[A-Z]{3}[0-9][A-Z][0-9]{2}$");
	}
	
	
	
}
