package ui.customer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

import entities.Customer;
import entities.Order;
import services.OrderService;
import view.OrderItemView;

/**
 * Class: CustomerOrderMenu
 *
 * Description:
 * Class responsible for displaying all orders that the customer has already made
 *
 * Responsibilities:
 * - provide interactive menus for the user
 *
 * @author Rodrigo
 * @since 08-05-2026
 */

public class CustomerOrderMenu {
	
	private OrderService servicoPedido;
	private Scanner sc;
	
	public CustomerOrderMenu(Connection conn, Scanner sc) {
		this.servicoPedido = new OrderService(conn);
		this.sc = sc;		
	}
	
	/**
	 * Displays all orders that the customer has already made in the system
	 * @param c Customer object
	 */
	public void mostrarPedidosCliente(Customer c) {
		int option;
		
		ArrayList<Order> listaPedidos = servicoPedido.retornarPedidosCliente(c.getCpf());
		
		if (listaPedidos.isEmpty()) {
			System.out.println("Você ainda não possui pedidos no sistema!");
			return;
		}
		
		while (true) {
		
			System.out.println("\nSEUS PEDIDOS");
			System.out.println("============================================================================");
					
			for (int i = 0; i < listaPedidos.size(); i++) {
					System.out.println(i+1 + "-  " + listaPedidos.get(i));
			}
			
			System.out.println("============================================================================\n");
			
			System.out.println("================================================");
			System.out.println("1- Conferir detalhes de um pedido");
			System.out.println("2- Voltar ao menu anterior");
			System.out.println("================================================\n");
			
			System.out.print("Informe a ação desejada: ");
			
			while (true) {
				try {
					option = sc.nextInt();
					sc.nextLine();
					
					// Check if the user's option is outside the allowed range
					if (option >= 1 && option <= 2) {
						break;
					} else {
						System.out.println("Digite uma opção válida: ");
					}
					
				} catch (Exception e) {
					sc.nextLine();
					System.out.println("Digite apenas números: ");
					option = -1;
				}
			}
			
			switch (option) {
				case 1:
					this.detalharPedido(listaPedidos);
					break;
				case 2:
					System.out.println("Voltando ao menu anterior");
					return;
			}
		
		}
		
	}
	
	/**
	 * Displays all items in a specific order
	 * @param listaPedidos list containing all orders
	 */
	private void detalharPedido(ArrayList<Order> listaPedidos) {
		int index;
		double valorTotal = 0;
		System.out.print("Digite o índice do pedido que você deseja visualizar detalhes: ");
		
		// Field for validating user input
		while (true) {
		    try {
		    	index = sc.nextInt();
			    sc.nextLine();
			    
			    index--; // User sees from (1) to (N). Computer sees from (0) to (N-1)
			    
		        servicoPedido.validarIndex(listaPedidos, index);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Digite um índice válido: ");
		    }
		}
		
		// Stores the order code to avoid code repetition
		int codigoPedido = listaPedidos.get(index).getNumeroPedido();
		
		// Stores all items from the order the user chose
		ArrayList<OrderItemView> listaItensPedido = servicoPedido.retornarItensPedido(codigoPedido);
		
		System.out.printf("\nEXIBINDO ITENS DO PEDIDO %d:\n", codigoPedido);
		System.out.println("============================================================================");
		
		for (OrderItemView ip: listaItensPedido) {
			System.out.println("Produto: " + ip.getNome() + " | Quantidade: " + ip.getQuantidade() + " | Preço Unidade: " + ip.getPreco());
			valorTotal += ip.getQuantidade() * ip.getPreco();
		}
	
		System.out.println("============================================================================");
		System.out.println("SUB-TOTAL");
		System.out.printf("R$ %.2f\n", valorTotal);
		System.out.println("============================================================================");
		System.out.println("SUB-TOTAL COM DESCONTO");
		
		valorTotal = servicoPedido.calcularDesconto(valorTotal);
		
		System.out.printf("R$ %.2f\n", valorTotal);
		System.out.println("============================================================================");
		System.out.println("TAXA DE ENTREGA:");
		System.out.println("R$ 8.00 adicionados ao total");
		
		valorTotal = servicoPedido.adicionarTaxaEntrega(valorTotal);
		
		System.out.println("============================================================================");
		System.out.println("VALOR TOTAL");
		System.out.printf("%.2f\n", valorTotal);
		System.out.print("============================================================================\n\n");
		
		
		System.out.println("Aperte enter para continuar");
		sc.nextLine();
	}
}
