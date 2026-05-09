package entities;

/**
 * Entidade: ProdutoRestaurante
 *
 * Descrição:
 * Representa um produto disponibilizado por um restaurante no sistema.
 *
 * Responsabilidades:
 * - Associar produtos a restaurantes
 * - Armazenar informações de preço dos produtos
 * - Controlar a quantidade disponível em estoque
 * - Identificar produtos comercializados pelo restaurante
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public class ProdutoRestaurante {
	private String cnpjRestaurante;
	private Integer codigoProduto;
	private Integer quantidadeEstoque;
	private Double preco;
	
	
	/**
	 * Construtor sem argumentos
	 */
	public ProdutoRestaurante() {
		
	}

	public String getCnpjRestaurante() {
		return cnpjRestaurante;
	}

	public void setCnpjRestaurante(String cnpjRestaurante) {
		this.cnpjRestaurante = cnpjRestaurante;
	}

	public Integer getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(Integer codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}	
	
}
