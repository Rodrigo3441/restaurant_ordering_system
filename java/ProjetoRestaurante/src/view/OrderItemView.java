package view;
/**
 * Class: OrderItemView
 *
 * Description:
 * Represents an item in the customer's shopping cart, containing basic
 * information about the selected product such as name, price, code, and
 * desired quantity.
 *
 * Responsibilities:
 * - Hold the data for a product added to the cart
 * - Manage the quantity chosen by the customer
 * - Serve as a model for displaying order items in the UI
 *
 * Notes:
 * - Each instance represents a single product in the cart
 * - Quantity may be modified through user interaction
 *
 * @author rodrigo
 * @since 04-05-2026
 */
public class OrderItemView {
	String nomeProduto;
	Double preco;
	Integer codigoProduto;
	Integer quantidade;
	

	public OrderItemView() {
		
	}

	public String getNome() {
		return nomeProduto;
	}

	public void setNome(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigo(Integer codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	
	@Override
	public String toString() {
		return " Nome: " + nomeProduto +
		       " | Preço: R$ " + preco +
		       " | Quantidade: " + quantidade;
	}
	
	
	
}
