package entities;

public class Pedido {
	private Integer numeroPedido;
	private String status;
	private String cpfEntregador;
	private String cnpjRestaurante;
	private String cpfCliente;
	
	/**
	 * 
	 * @param numeroPedido
	 * @param status
	 * @param cpfEntregador
	 * @param cnpjRestaurante
	 * @param cpfCliente
	 */
	public Pedido(String status, String cpfEntregador, String cnpjRestaurante,
			String cpfCliente) {
		this.status = status;
		this.cpfEntregador = cpfEntregador;
		this.cnpjRestaurante = cnpjRestaurante;
		this.cpfCliente = cpfCliente;
	}
	
	public Pedido() {
		
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
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * @return
	 */
	public String getCpfEntregador() {
		return cpfEntregador;
	}
	
	/**
	 * 
	 * @param cpfEntregador
	 */
	public void setCpfEntregador(String cpfEntregador) {
		this.cpfEntregador = cpfEntregador;
	}

	/**
	 * 
	 * @return
	 */
	public String getCnpjRestaurante() {
		return cnpjRestaurante;
	}

	/**
	 * 
	 * @param cnpjRestaurante
	 */
	public void setCnpjRestaurante(String cnpjRestaurante) {
		this.cnpjRestaurante = cnpjRestaurante;
	}

	/**
	 * 
	 * @return
	 */
	public String getCpfCliente() {
		return cpfCliente;
	}

	/**
	 * 
	 * @param cpfCliente
	 */
	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}
	
	
	
}
