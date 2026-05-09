package entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Entidade: Pedido
 *
 * Descrição:
 * Representa um pedido realizado por um cliente no sistema.
 *
 * Responsabilidades:
 * - Armazenar informações gerais do pedido
 * - Associar cliente, restaurante e entregador ao pedido
 * - Controlar o status do pedido
 * - Registrar a data e hora da realização do pedido
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public class Pedido {
	private Integer numeroPedido;
	private String status;
	private String cpfEntregador;
	private String cnpjRestaurante;
	private String cpfCliente;
	private LocalDateTime dataPedido;
	
	/**
	 * Construtor sem argumentos
	 */
	public Pedido() {
		
	}

	public Integer getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(Integer numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * retorna o CPF do entregador do pedido.
	 * Caso não exista um entregador atribuido, retorna uma mensagem padrão
	 * 
	 * @return CPF do entregador ou mensagem informando ausência de entregador
	 */
	public String getCpfEntregador() {
		if (cpfEntregador == null) {
			return "Sem entregador atribuido";
		}
		
		return cpfEntregador;
	}
	
	public void setCpfEntregador(String cpfEntregador) {
		this.cpfEntregador = cpfEntregador;
	}

	public String getCnpjRestaurante() {
		return cnpjRestaurante;
	}

	public void setCnpjRestaurante(String cnpjRestaurante) {
		this.cnpjRestaurante = cnpjRestaurante;
	}

	public String getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}
	
	public String getDataPedido() {
		DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		
	    return dataPedido.format(formatoData);
	}	
	
	public void setDataPedido(LocalDateTime dataPedido) {
	    this.dataPedido = dataPedido;
	}
	
	@Override
	public String toString() {
		return " Número do pedido: " + numeroPedido +
			   " |Status: " + status +
		       " | Entregador atribuido: " + this.getCpfEntregador() +
		       " | Cliente: " + cpfCliente +
		       " | Data do pedido: " + this.getDataPedido();
	}
}
