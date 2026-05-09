package ui.cliente;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

import entities.Cliente;
import entities.Pedido;
import services.PedidoService;
import view.ItemPedidoView;

/**
 * Classe: MenuPedidoCliente
 *
 * Descrição:
 * Classe responsável por listar todos os pedidos que o cliente já realizou
 *
 * Responsabilidades:
 * - oferecer menus interativos para o usuário
 *
 * @author Rodrigo
 * @since 08-05-2026
 */

public class MenuPedidoCliente {
	
	private PedidoService servicoPedido;
	private Scanner sc;
	
	public MenuPedidoCliente(Connection conn, Scanner sc) {
		this.servicoPedido = new PedidoService(conn);
		this.sc = sc;		
	}
	
	/**
	 * Exibe todos os pedidos que o cliente já realizou no sistema
	 * @param c objeto Cliente
	 */
	public void mostrarPedidosCliente(Cliente c) {
		int option;
		
		ArrayList<Pedido> listaPedidos = servicoPedido.retornarPedidosCliente(c.getCpf());
		
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
					
					//verificar se a opção do usuário está fora do intervalo permitido
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
	 * Exibe todos os itens que estão em determinado pedido
	 * @param listaPedidos lista com todos os pedidos
	 */
	private void detalharPedido(ArrayList<Pedido> listaPedidos) {
		int index;
		double valorTotal = 0;
		System.out.print("Digite o índice do pedido que você deseja visualizar detalhes: ");
		
		//campo para validação entrada pelo usuário
		while (true) {
		    try {
		    	index = sc.nextInt();
			    sc.nextLine();
			    
			    index--; //usuário enxerga de (1)à(N). Computador enxerga de (0)à(N-1)
			    
		        servicoPedido.validarIndex(listaPedidos, index);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Digite um índice válido: ");
		    }
		}
		
		//armazena o código do pedido para evitar repetição de código
		int codigoPedido = listaPedidos.get(index).getNumeroPedido();
		
		//armazena todos os itens do pedido que o usuário escolheu
		ArrayList<ItemPedidoView> listaItensPedido = servicoPedido.retornarItensPedido(codigoPedido);
		
		System.out.printf("\nEXIBINDO ITENS DO PEDIDO %d:\n", codigoPedido);
		System.out.println("============================================================================");
		
		for (ItemPedidoView ip: listaItensPedido) {
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
