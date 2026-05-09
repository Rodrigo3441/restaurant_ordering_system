package entities;

/**
 * Entidade: ItemPedido
 *
 * Descrição:
 * Representa um item associado a um pedido do sistema.
 *
 * Responsabilidades:
 * - Armazenar informações do produto vinculado ao pedido
 * - Registrar a quantidade solicitada do produto
 * - Relacionar produtos aos pedidos realizados
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public class ItemPedido {
	private Integer numeroPedido;
	private Integer codigoProduto;
	private Integer quantidade;
	
	
	/**
	 * Construtor sem argumentos
	 */
	public ItemPedido() {
		
	}

	public Integer getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(Integer numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public Integer getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(Integer codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	
}
