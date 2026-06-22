package view;
/**
 * Class: RestaurantProductView
 *
 * Description:
 * Represents the view of a restaurant product,
 * containing combined information such as name, description, price, and stock quantity.
 *
 * Responsibilities:
 * - Store data resulting from JOIN query between PRODUCT and PRODUCT_RESTAURANT
 * - Serve as a model for displaying products to the restaurant
 *
 * @author rodrigo
 * @since 28-04-2026
 */
public class RestaurantProductView {
	private Integer codigoProduto;
	private String nomeProduto;
	private Double precoProduto;
	private Integer quantidadeEstoque;
	private String descricao;
	
	/**
	 * No-argument constructor
	 */
	public RestaurantProductView() {
		
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
		return "Nome: " + nomeProduto +
		       " | Preço: R$ " + precoProduto +
		       " | Estoque: " + quantidadeEstoque +
		       " | Descrição: " + descricao +
			   " | Código: " + codigoProduto;
	}
	
	public String formatarParaCliente() {
		return "Nome: " + nomeProduto +
			   " | Descrição: " + descricao +
		       " | Preço: R$ " + precoProduto +
		       " | Estoque: " + quantidadeEstoque;
		       
	}
	
}
