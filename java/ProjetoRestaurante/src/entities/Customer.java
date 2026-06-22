package entities;

/**
 * Entidade: Cliente
 *
 * Descrição:
 * Representa um cliente cadastrado no sistema.
 *
 * Responsabilidades:
 * - Armazenar informações pessoais do cliente
 * - Manter dados de autenticação e acesso
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public class Cliente extends Usuario {
	private String email;
	private String senha;
	
	/**
	 * Construtor sem argumentos
	 */
	public Cliente() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	
}
