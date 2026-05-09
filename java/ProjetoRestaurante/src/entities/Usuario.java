package entities;

/**
 * Classe Abstrata: Usuario
 *
 * Descrição:
 * Representa uma entidade base para usuários do sistema.
 *
 * Responsabilidades:
 * - Armazenar informações pessoais dos usuários
 * - Definir atributos comuns compartilhados entre subclasses
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public abstract class Usuario {
	protected String cpf;
	protected String primeiroNome;
	protected String nomeMeio;
	protected String ultimoNome;
	protected String telefone;
	
	/**
	 * Construtor sem argumentos
	 */
	protected Usuario() {
		
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}

	public String getNomeMeio() {
		return nomeMeio;
	}

	public void setNomeMeio(String nomeMeio) {
		this.nomeMeio = nomeMeio;
	}
	
	public String getUltimoNome() {
		return ultimoNome;
	}

	public void setUltimoNome(String ultimoNome) {
		this.ultimoNome = ultimoNome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}	
	
	
	
}
