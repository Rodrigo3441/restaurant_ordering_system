package entities;

/**
 * Classe Abstrata: Endereco
 *
 * Descrição:
 * Representa uma entidade base para endereços do sistema.
 *
 * Responsabilidades:
 * - armazenar dados comuns dos endereços
 * - definir comportamentos compartilhados
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public abstract class Endereco {
	protected String cep;
	protected String nome;
	protected Integer numero;
	
	/**
	 * Construtor sem argumentos
	 */
	protected Endereco() {
		
	}

	/**
	 * retorna o CEP do endereço.
	 * Caso não exista CEP cadastrado, retorna uma mensagem padrão
	 * 
	 * @return CEP do endereço ou mensagem informando ausência de CEP
	 */
	public String getCep() {
		if (cep != null) {
			return cep;
		} 
		return "Sem cep cadastrado";
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	/**
	 * Retorna o nome da rua.
	 * Caso não exista nome cadastrado, retorna uma mensagem padrão.
	 *
	 * @return nome da rua ou mensagem informando ausência de cadastro
	 */
	public String getNome() {
		if (nome != null) {
			return nome;
		} 
		return "Sem nome de rua cadastrado";
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Retorna o número do endereço.
	 * Caso não exista número cadastrado, retorna 0.
	 *
	 * @return número do endereço ou 0
	 */
	public Integer getNumero() {
		if (numero != null) {
			return numero;
		}
		return 0;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	/**
	 * Retorna uma string formatada do endereço
	 * @return String de endereço formatada
	 */
	public abstract String formatarEndereco();
	
	/**
	 * Retorna a identificação de quem o endereço pertence
	 * @return String com identificação cpf/cnpj
	 */
	public abstract String getIdentificacao();
	
	
}
