package view;
/**
 * Classe: ProdutoRestauranteView
 *
 * Descrição:
 * Representa a visualização de um produto associado a um restaurante,
 * contendo informações combinadas como nome, descrição, preço e quantidade em estoque.
 *
 * Responsabilidades:
 * - Armazenar dados resultantes de consultas com JOIN entre PRODUTO e PRODUTO_RESTAURANTE
 * - Servir como modelo para exibição de produtos ao restaurante
 *
 * @author rodrigo
 * @since 28-04-2026
 */
public class ProdutoRestauranteView {
	private Integer codigoProduto;
	private String nomeProduto;
	private Double precoProduto;
	private Integer quantidadeEstoque;
	private String descricao;
	
	/**
	 * Construtor sem argumentos
	 */
	public ProdutoRestauranteView() {
		
	}

	public int getCodigoProduto() {
		return codigoProduto;
	}
	
	public void setCodigoProduto(int codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public Double getPrecoProduto() {
		return precoProduto;
	}

	public void setPrecoProduto(Double precoProduto) {
		this.precoProduto = precoProduto;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return " Código: " + codigoProduto +
			   " |Nome: " + nomeProduto +
		       " | Preço: R$ " + precoProduto +
		       " | Estoque: " + quantidadeEstoque +
		       " | Descrição: " + descricao;
	}
	
	public String formatarParaCliente() {
		return " Nome: " + nomeProduto +
			   " | Descrição: " + descricao +
		       " | Preço: R$ " + precoProduto +
		       " | Estoque: " + quantidadeEstoque;
		       
	}
	
}
