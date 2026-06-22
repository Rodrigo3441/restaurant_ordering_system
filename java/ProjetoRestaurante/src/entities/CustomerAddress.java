package entities;

/**
 * Entidade: EnderecoCliente
 *
 * Descrição:
 * Representa o endereço associado a um cliente do sistema.
 *
 * Responsabilidades:
 * - Armazenar informações de endereço do cliente
 * - Associar o endereço ao CPF do cliente
 * - Formatar os dados do endereço para exibição
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public class EnderecoCliente extends Endereco {
	private String cpfCliente;
	
	/**
	 * Construtor sem argumentos
	 * 
	 */
	public EnderecoCliente() {
		super();
	}

	public String getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	@Override
	public String formatarEndereco() {
		return "CEP: " + cep +
				" | Nome da rua: " + nome +
				" | Número do cliente: " + numero;
	}

	@Override
	public String getIdentificacao() {
		return cpfCliente;
	}
	
	

}
