package entities;

/**
 * Entidade: Restaurante
 *
 * Descrição:
 * Representa um restaurante cadastrado no sistema.
 *
 * Responsabilidades:
 * - Armazenar informações do restaurante
 * - Manter dados de contato do restaurante
 * - Associar o restaurante a uma categoria
 * - Gerenciar dados de autenticação e acesso
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public class Restaurante {
	private String cnpj;
	private String nome;
	private String telefone;
	private Integer idCategoria;
	private String senha;
	
	/**
	 * construtor sem argumentos
	 */
	public Restaurante() {
		
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
