package entities;

public class ItemPedido {
	private Integer numeroPedido;
	private Integer codigoProduto;
	private Integer quantidade;
	
	
	public ItemPedido() {
		
	}

	/**
	 * 
	 * @return
	 */
	public Integer getNumeroPedido() {
		return numeroPedido;
	}

	/**
	 * 
	 * @param numeroPedido
	 */
	public void setNumeroPedido(Integer numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getCodigoProduto() {
		return codigoProduto;
	}

	/**
	 * 
	 * @param codigoProduto
	 */
	public void setCodigoProduto(Integer codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getQuantidade() {
		return quantidade;
	}

	/**
	 * 
	 * @param quantidade
	 */
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	
}
