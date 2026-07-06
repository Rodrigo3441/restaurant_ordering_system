package ui.customer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import entities.Customer;
import entities.Restaurant;
import services.OrderService;
import view.OrderItemView;

/**
 * Class: OrderConfirmationMenu
 *
 * Description:
 * Class responsible for providing a summary of the customer's order before they confirm the purchase
 *
 * Responsibilities:
 * - provide interface
 * - communicate with the service layer
 *
 * @author Rodrigo
 * @since 05-05-2026
 */


public class OrderConfirmationMenu {
	
	private OrderService servicopedido;
	private Scanner sc;
	
	public OrderConfirmationMenu(Connection conn, Scanner sc) {
		this.servicopedido = new OrderService(conn);
		this.sc = sc;
	}
	
	
	/**
	 * Displays all order details for the user, asks for confirmation, and returns whether the order was confirmed or not
	 * @param r restaurant object
	 * @param c customer object
	 * @param carrinhoCompras customer's shopping cart
	 * @return true if the order was created
	 */
	public boolean mostrarDetalhesPedido(Restaurant r, Customer c, ArrayList<OrderItemView> carrinhoCompras) {
		double valorTotal = 0;
		
		System.out.println("\n============================================================================");
		System.out.println("RESUMO DETALHADO DO PEDIDO");
		System.out.println("============================================================================");
		System.out.printf("Nome do cliente: %s %s\n", c.getFirstName(), c.getLastName());
		
		System.out.println("============================================================================");
		System.out.println("Lista de produtos:");
		
		for (int i = 0; i < carrinhoCompras.size(); i++) {
			double precoQuantidade = carrinhoCompras.get(i).getPrice()*carrinhoCompras.get(i).getQuantity();
			valorTotal += precoQuantidade;
			System.out.println(	carrinhoCompras.get(i));
		}
		
		System.out.println("============================================================================");
		System.out.println("SUB-TOTAL");
		System.out.printf("R$ %.2f\n", valorTotal);
		System.out.println("============================================================================");
		System.out.println("SUB-TOTAL COM DESCONTO");
		
		valorTotal = servicopedido.calculateDiscount(valorTotal);
		
		System.out.printf("R$ %.2f\n", valorTotal);
		System.out.println("============================================================================");
		System.out.println("TAXA DE ENTREGA:");
		System.out.println("R$ 8.00 adicionados ao total");
		
		valorTotal = servicopedido.addDeliveryFee(valorTotal);
		
		System.out.println("============================================================================");
		System.out.println("VALOR TOTAL");
		System.out.printf("%.2f\n", valorTotal);
		System.out.print("============================================================================\n\n");
		
		System.out.println("Deseja confirmar o pedido?");
		
		int escolhaUsuario = this.exibirMenuAcao();
		
		switch (escolhaUsuario) {
			case 1:
				System.out.println("Pedido concluído com sucesso!");
				servicopedido.createOrder(r, c, carrinhoCompras);
				return true;
			case 2:
				System.out.println("Voltando ao menu de pedidos");
		}
		
		return false;	
	}
	
	/**
	 * Displays the action menu for the user to confirm the order, cancel, or add more products
	 * @return selected action number
	 */
	private int exibirMenuAcao() {
		int option;
	
		System.out.println("================================================");
		System.out.println("1- Confirmar pedido");
		System.out.println("2- Voltar ao menu de produtos");
		System.out.println("================================================\n");
		
		System.out.print("Informe a ação desejada: ");
			
		while (true) {
			try {
				option = sc.nextInt();
				sc.nextLine();
				
				// check if the user's option is outside the allowed range
				if (option >= 1 && option <= 2) {
					// return the user's chosen action
					return option; 
				} else {
					System.out.println("Digite uma opção válida: ");
				}
				
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("Digite apenas números: ");
				option = -1;
			}
		}
		
	}
	
}
