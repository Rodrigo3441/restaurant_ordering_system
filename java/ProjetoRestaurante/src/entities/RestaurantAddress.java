package entities;

/**
 * Entidade: EnderecoRestaurante
 *
 * Descrição:
 * Representa o endereço associado a um restaurante do sistema.
 *
 * Responsabilidades:
 * - Armazenar informações de endereço do restaurante
 * - Associar o endereço ao CNPJ do restaurante
 * - Formatar os dados do endereço para exibição
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public class EnderecoRestaurante extends Endereco {
	private String cnpjRestaurante;
	
	/**
	 * Construtor sem argumentos
	 */
	public EnderecoRestaurante() {
		super();
	}


	public String getCnpjRestaurante() {
		return cnpjRestaurante;
	}

	public void setCnpjRestaurante(String cnpjRestaurante) {
		this.cnpjRestaurante = cnpjRestaurante;
	}

	@Override
	public String formatarEndereco() {
		return "CEP: " + cep +
				" | Nome da rua: " + nome +
				" | Número do restaurante: " + numero;
	}

	@Override
	public String getIdentificacao() {
		return cnpjRestaurante;
	}
	
	
}
