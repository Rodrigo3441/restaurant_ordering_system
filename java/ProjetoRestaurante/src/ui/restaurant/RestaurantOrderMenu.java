package ui.restaurant;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import entities.DeliveryPerson;
import entities.Order;
import entities.Restaurant;
import services.DeliveryPersonService;
import services.OrderService;

/**
 * Class: RestaurantOrderMenu
 *
 * Description:
 * Class responsible for providing an interface for the restaurant to manage its orders
 *
 * Responsibilities:
 * - present interactive menus to the user
 *
 * @author Rodrigo
 * @since 07-05-2026
 */

public class RestaurantOrderMenu {
	private OrderService servicoPedido;
	private DeliveryPersonService servicoEntregador;
	private Scanner sc;
	
	public RestaurantOrderMenu(Connection conn, Scanner sc) {
		this.servicoPedido = new OrderService(conn);
		this.servicoEntregador = new DeliveryPersonService(conn);
		this.sc = sc;
	}
		
	/**
	 * Displays all restaurant orders so the restaurant can manage them
	 * @param r restaurant object
	 */
	public void mostrarMenuPedidos(Restaurant r) {
		
		int option;
		
		while (true) {
		
			System.out.println("\nMENU GERENCIADOR DE PEDIDOS");
			System.out.println("================================================");
			System.out.println("1- Gerenciar pedidos em preparo");
			System.out.println("2- Gerenciar pedidos que saíram para entrega");
			System.out.println("3- Visualizar pedidos concluídos");
			System.out.println("4- Voltar ao menu anterior");
			System.out.println("================================================\n");
			
			System.out.print("Informe a ação desejada: ");
				
			while (true) {
				try {
					option = sc.nextInt();
					sc.nextLine();
					
					// verify if user option is within allowed range
					if (option >= 1 && option <= 4) {
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
					this.gerenciarPedidosEmPreparo(r.getId());
					break;
				case 2:
					this.gerenciarPedidosEnviados(r.getId());
					break;
				case 3:
					this.visualizarPedidosConcluidos(r.getId());
					break;
				case 4: 
					System.out.println("Voltando ao menu anterior");
					return;
			}
			
		}
		
	}
	
	/**
	 * Provides a visual interface for the restaurant to manage orders that are still being prepared
	 * @param cnpj restaurant cnpj
	 */
	private void gerenciarPedidosEmPreparo(String cnpj) {

		// list with all orders to allow access to the first position (oldest order)
		ArrayList<Order> listaPedidos = servicoPedido.returnOrdersByRestaurant(cnpj, "Em preparo");
		
		// prevent operations if the list is empty
		if (listaPedidos.isEmpty()) {
			System.out.println("Não há nenhum pedido sendo preparado no momento!");
			return;
		}
		
		System.out.println("EXIBINDO PEDIDOS EM PREPARO PELO RESTAURANTE: ");
		System.out.println("\n============================================================================");
		
		// reverse the list to show the oldest orders first
		Collections.reverse(listaPedidos);
		
		for (Order p: listaPedidos) {
			System.out.println(p);
		}
		
		System.out.println("============================================================================\n");
		
		
		System.out.printf("Próximo pedido a ter seu status alterado: %d\n", listaPedidos.get(0).getOrderNumber());
		System.out.print("Deseja atribuir um entregador ao pedido e atualizar o seu status? (s-sim/n-não): ");
		
		// ask the user if they want to assign a delivery person and update the order status
		while (true) {
			String escolha = sc.next().trim().toLowerCase();
		
			switch (escolha) {
				case "s":	
					this.atribuirEntregadorPedido(listaPedidos.get(0));			
					return;
				case "n":
					System.out.println("Nada foi alterado");
					return;
				default:
					System.out.print("Digite uma opção válida: ");
			}
		}
		
	}
	
	/**
	 * Assigns a delivery person to an order and updates availability status from 0 (free) to 1 (busy)
	 * @param p order object
	 */
	private void atribuirEntregadorPedido(Order p) {
		int index;
		
		// list to store all delivery persons in the system
		ArrayList<DeliveryPerson> listaEntregadores = servicoEntregador.returnDeliveryPersonList();
		
		// prevent operation if the delivery person list is empty
		if (listaEntregadores.isEmpty()) {
			System.out.println("Não há nenhum entregador disponível no momento!");
			return;
		}
		
		// remove all occupied delivery persons from the list for better visibility
		for (int i = 0; i < listaEntregadores.size(); i++) {
			if (listaEntregadores.get(i).getAvailable() == 1) {
				listaEntregadores.remove(i);
			}
		}
		
		System.out.println("\nENTREGADORES DISPONÍVEIS PARA ENTREGA");
		System.out.println("============================================================================");
				
		for (int i = 0; i < listaEntregadores.size(); i++) {
				System.out.println(i+1 + "-  " + listaEntregadores.get(i));
		}
		
		System.out.println("============================================================================\n");
		System.out.printf("Digite o índice do entregador que você deseja atribuir ao pedido %d: ", p.getOrderNumber());
		
	
		// field to validate user input
		while (true) {
		    try {
		    	index = sc.nextInt();
		    	sc.nextLine();
		    	
		    	index--; // user sees from (1) to (N). Computer sees from (0) to (N-1)
		    	
		        servicoPedido.checkIndex(listaEntregadores, index);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Digite um índice válido: ");
		    }
		}
		
		// from the available delivery persons list, store the chosen delivery person
		DeliveryPerson entregador = listaEntregadores.get(index);
		
		
		System.out.printf("Deseja confirmar a atribuição do entregador %s ao pedido %d? (s-sim/n-não): ", entregador.getId(), p.getOrderNumber());
		// restaurant confirms whether to assign the delivery person to the order
		while (true) {
			String escolha = sc.next().trim().toLowerCase();
		
			switch (escolha) {
				case "s":	
					if (servicoPedido.updateOrderStatus(p, entregador, (short) 1, "Saiu entrega")) {
						System.out.printf("Entregador atribuído ao pedido %d com sucesso!\n", p.getOrderNumber());
					} else {
						System.out.println("Ocorreu um erro");
					}
					return;
				case "n":
					System.out.println("Nada foi alterado");
					return;
				default:
					System.out.print("Digite uma opção válida: ");
			}
		}
				
	}
	
	/**
	 * Provides a visual interface for the restaurant to mark an order as completed
	 * @param cnpj restaurant cnpj
	 */
	private void gerenciarPedidosEnviados(String cnpj) {
		
		ArrayList<Order> listaPedidos = servicoPedido.returnOrdersByRestaurant(cnpj, "Saiu entrega");
		
		if (listaPedidos.isEmpty()) {
			System.out.println("Não há nenhum pedido concluído no momento!");
			return;
		}
		
		System.out.println("PRODUTOS QUE SAÍRAM PARA ENTREGA: ");
		System.out.println("\n============================================================================");
		
		// reverse the list to show the oldest orders first
		Collections.reverse(listaPedidos);
		
		for (int i = 0; i < listaPedidos.size(); i++) {
			System.out.println(i+1 + "-  " + listaPedidos.get(i));
		}
		
		System.out.println("============================================================================\n");
		
		
		System.out.print("Deseja marcar um pedido como concluído? (s-sim/n-não): ");
		
		// ask the user if they want to mark an order as completed
		while (true) {
			String escolha = sc.next().trim().toLowerCase();
		
			switch (escolha) {
				case "s":	
					this.marcarPedidoConcluido(listaPedidos);			
					return;
				case "n":
					System.out.println("Nada foi alterado");
					return;
				default:
					System.out.print("Digite uma opção válida: ");
			}
		}
		
	}
	
	/**
	 * Receives the index of the order the restaurant wants to mark as completed
	 * @param listaPedidos list of orders
	 */
	private void marcarPedidoConcluido(ArrayList<Order> listaPedidos) {
		int index;
		System.out.print("Digite o índice do pedido que você deseja marcar como concluído: ");
		
		// field to validate user input
		while (true) {
		    try {
		    	index = sc.nextInt();
		    	sc.nextLine();
		    	
		    	index--; // user sees from (1) to (N). Computer sees from (0) to (N-1)
		    	
		        servicoPedido.checkIndex(listaPedidos, index);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Digite um índice válido: ");
		    }
		}
		
		Order pedido = listaPedidos.get(index);
		DeliveryPerson entregador = servicoEntregador.returnDeliveryPerson(pedido.getDeliveryPersonId());
		
		System.out.printf("Deseja confirmar a conclusão do pedido %d? (s-sim/n-não): ", pedido.getOrderNumber());
		// restaurant confirms whether to mark the order as delivered
		while (true) {
			String escolha = sc.next().trim().toLowerCase();
		
			switch (escolha) {
				case "s":	
					if (servicoPedido.updateOrderStatus(pedido, entregador, (short) 0, "Entregue")) {
						System.out.printf("Pedido %d entregue com sucesso!\n", pedido.getOrderNumber());
					} else {
						System.out.println("Ocorreu um erro");
					}
					return;
				case "n":
					System.out.println("Nada foi alterado");
					return;
				default:
					System.out.print("Digite uma opção válida: ");
			}
		}
		
	}
	
	/**
	 * Shows all orders that have already been marked as delivered by the restaurant
	 * @param cnpj restaurant cnpj
	 */
	private void visualizarPedidosConcluidos(String cnpj) {
		
		ArrayList<Order> listaPedidos = servicoPedido.returnOrdersByRestaurant(cnpj, "Entregue");
		
		// prevent operations if the list is empty
		if (listaPedidos.isEmpty()) {
			System.out.println("Não há pedidos realizados no restaurante!");
			return;
		}
		
		System.out.println("PEDIDOS QUE JÁ FORAM CONCLUÍDOS: ");
		System.out.println("\n============================================================================");
		
		// reverse the list to show the oldest orders first
		Collections.reverse(listaPedidos);
		
		for (int i = 0; i < listaPedidos.size(); i++) {
			System.out.println(i+1 + "-  " + listaPedidos.get(i));
		}
		
		System.out.println("============================================================================\n");
		System.out.println("Aperte enter para continuar");
		sc.nextLine();
	
	}
	
}
