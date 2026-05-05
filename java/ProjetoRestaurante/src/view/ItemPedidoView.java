package view;
/**
 * Classe: ItemPedidoView
 *
 * Descrição:
 * Representa um item presente no carrinho de compras do cliente,
 * contendo informações básicas do produto selecionado, como nome,
 * preço, código e quantidade desejada.
 *
 * Responsabilidades:
 * - Armazenar os dados de um produto adicionado ao carrinho
 * - Controlar a quantidade escolhida pelo cliente
 * - Servir como modelo para exibição dos itens do pedido na interface
 *
 * Observações:
 * - Cada instância representa um único produto no carrinho
 * - A quantidade pode ser alterada conforme interação do usuário
 *
 * @author rodrigo
 * @since 04-05-2026
 */
public class ItemPedidoView {
	String nomeProduto;
	Double preco;
	Integer codigoProduto;
	Integer quantidade;
	

	public ItemPedidoView() {
		
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
