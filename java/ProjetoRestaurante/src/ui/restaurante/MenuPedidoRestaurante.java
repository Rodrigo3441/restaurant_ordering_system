package ui.restaurante;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import entities.Entregador;
import entities.Pedido;
import entities.Restaurante;
import services.EntregadorService;
import services.PedidoService;

/**
 * Classe: MenuPedidoRestaurante
 *
 * Descrição:
 * Classe responsável por fornecer uma interface para que o restaurante possa gerenciar os seus pedidos
 *
 * Responsabilidades:
 * - oferecer menus interativos para o usuário
 *
 * @author Rodrigo
 * @since 07-05-2026
 */

public class MenuPedidoRestaurante {
	private PedidoService servicoPedido;
	private EntregadorService servicoEntregador;
	private Scanner sc;
	
	public MenuPedidoRestaurante(Connection conn, Scanner sc) {
		this.servicoPedido = new PedidoService(conn);
		this.servicoEntregador = new EntregadorService(conn);
		this.sc = sc;
	}
		
	/**
	 * Exibe todos os pedidos do restaurante para que o restaurante possa gerenciar eles
	 * @param r objeto restaurante
	 */
	public void mostrarMenuPedidos(Restaurante r) {
		
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
					
					//verificar se a opção do usuário está fora do intervalo permitido
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
					this.gerenciarPedidosEmPreparo(r.getCnpj());
					break;
				case 2:
					this.gerenciarPedidosEnviados(r.getCnpj());
					break;
				case 3:
					this.visualizarPedidosConcluidos(r.getCnpj());
					break;
				case 4: 
					System.out.println("Voltando ao menu anterior");
					return;
			}
			
		}
		
	}
	
	/**
	 * fornece uma interface visual para que o restaurante possa gerenciar produtos
	 * que ainda estão sendo preparados	
	 * @param cnpj do restaurante
	 */
	private void gerenciarPedidosEmPreparo(String cnpj) {

		//lista com todos os pedidos para permitir o acesso à primeira posição (pedido mais antigo)
		ArrayList<Pedido> listaPedidos = servicoPedido.retornarPedidosRestaurante(cnpj, "Em preparo");
		
		//impede operações se a lista estiver vazia
		if (listaPedidos.isEmpty()) {
			System.out.println("Não há nenhum pedido sendo preparado no momento!");
			return;
		}
		
		System.out.println("EXIBINDO PEDIDOS EM PREPARO PELO RESTAURANTE: ");
		System.out.println("\n============================================================================");
		
		//inverte a lista para exibir os pedidos mais antigos em cima
		Collections.reverse(listaPedidos);
		
		for (Pedido p: listaPedidos) {
			System.out.println(p);
		}
		
		System.out.println("============================================================================\n");
		
		
		System.out.printf("Próximo pedido a ter seu status alterado: %d\n", listaPedidos.get(0).getNumeroPedido());
		System.out.print("Deseja atribuir um entregador ao pedido e atualizar o seu status? (s-sim/n-não): ");
		
		//usuário informa se deseja ou não remover o produto do carrinho
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
	 * faz a atribuição de um entregador a um pedido e atualiza o status de 0 (livre) para 1 (ocupado)
	 * @param p objeto pedido
	 */
	private void atribuirEntregadorPedido(Pedido p) {
		int index;
		
		//lista para armazenar todos os entregadores do sistema
		ArrayList<Entregador> listaEntregadores = servicoEntregador.listarEntregadores();
		
		//impede qualquer operação se a lista de entregadores estiver vazia
		if (listaEntregadores.isEmpty()) {
			System.out.println("Não há nenhum entregador disponível no momento!");
			return;
		}
		
		//remove todos os entregadores ocupados da lista para melhor visibilidade
		for (int i = 0; i < listaEntregadores.size(); i++) {
			if (listaEntregadores.get(i).getDisponibilidade() == 1) {
				listaEntregadores.remove(i);
			}
		}
		
		System.out.println("\nENTREGADORES DISPONÍVEIS PARA ENTREGA");
		System.out.println("============================================================================");
				
		for (int i = 0; i < listaEntregadores.size(); i++) {
				System.out.println(i+1 + "-  " + listaEntregadores.get(i));
		}
		
		System.out.println("============================================================================\n");
		System.out.printf("Digite o índice do entregador que você deseja atribuir ao pedido %d: ", p.getNumeroPedido());
		
	
		//campo para validação entrada pelo usuário
		while (true) {
		    try {
		    	index = sc.nextInt();
			    sc.nextLine();
			    
			    index--; //usuário enxerga de (1)à(N). Computador enxerga de (0)à(N-1)
			    
		        servicoPedido.validarIndex(listaEntregadores, index);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Digite um índice válido: ");
		    }
		}
		
		//da lista de entregadores disponíveis armazena o entregador escolhido pelo restaurante
		Entregador entregador = listaEntregadores.get(index);
		
		
		System.out.printf("Deseja confirmar a atribuição do entregador %s ao pedido %d? (s-sim/n-não): ", entregador.getCpf(), p.getNumeroPedido());
		//restaurante confirma se deseja atribuir o entregador ao pedido
		while (true) {
			String escolha = sc.next().trim().toLowerCase();
		
			switch (escolha) {
				case "s":	
					if (servicoPedido.atualizarEntregaPedido(p, entregador, (short) 1, "Saiu entrega")) {
						System.out.printf("Entregador atribuído ao pedido %d com sucesso!\n", p.getNumeroPedido());
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
	 * fornece uma interface visual para que o restaurante possa marcar um pedido como concluído
	 * @param cnpj do restaurante
	 */
	private void gerenciarPedidosEnviados(String cnpj) {
		
		ArrayList<Pedido> listaPedidos = servicoPedido.retornarPedidosRestaurante(cnpj, "Saiu entrega");
		
		if (listaPedidos.isEmpty()) {
			System.out.println("Não há nenhum pedido concluído no momento!");
			return;
		}
		
		System.out.println("PRODUTOS QUE SAÍRAM PARA ENTREGA: ");
		System.out.println("\n============================================================================");
		
		//inverte a lista para exibir os pedidos mais antigos em cima
		Collections.reverse(listaPedidos);
		
		for (int i = 0; i < listaPedidos.size(); i++) {
			System.out.println(i+1 + "-  " + listaPedidos.get(i));
		}
		
		System.out.println("============================================================================\n");
		
		
		System.out.print("Deseja marcar um pedido como concluído? (s-sim/n-não): ");
		
		//usuário informa se deseja ou não remover o produto do carrinho
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
	 * recebe o índice de qual produto o restaurante deseja marcar como entregue
	 * @param listaPedidos
	 */
	private void marcarPedidoConcluido(ArrayList<Pedido> listaPedidos) {
		int index;
		System.out.print("Digite o índice do pedido que você deseja marcar como concluído: ");
		
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
		
		Pedido pedido = listaPedidos.get(index);
		Entregador entregador = servicoEntregador.retornarEntregador(pedido.getCpfEntregador());
		
		System.out.printf("Deseja confirmar a conclusão do pedido %d? (s-sim/n-não): ", pedido.getNumeroPedido());
		//restaurante confirma se deseja atribuir o entregador ao pedido
		while (true) {
			String escolha = sc.next().trim().toLowerCase();
		
			switch (escolha) {
				case "s":	
					if (servicoPedido.atualizarEntregaPedido(pedido, entregador, (short) 0, "Entregue")) {
						System.out.printf("Pedido %d entregue com sucesso!\n", pedido.getNumeroPedido());
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
	 * mostra todos os pedidos que já foram marcados como entregue pelo restaurante
	 * @param cnpj do restaurante
	 */
	private void visualizarPedidosConcluidos(String cnpj) {
		
		ArrayList<Pedido> listaPedidos = servicoPedido.retornarPedidosRestaurante(cnpj, "Entregue");
		
		//impede operações se a lista estiver vazia
		if (listaPedidos.isEmpty()) {
			System.out.println("Não há pedidos realizados no restaurante!");
			return;
		}
		
		System.out.println("PEDIDOS QUE JÁ FORAM CONCLUÍDOS: ");
		System.out.println("\n============================================================================");
		
		//inverte a lista para exibir os pedidos mais antigos em cima
		Collections.reverse(listaPedidos);
		
		for (int i = 0; i < listaPedidos.size(); i++) {
			System.out.println(i+1 + "-  " + listaPedidos.get(i));
		}
		
		System.out.println("============================================================================\n");
		System.out.println("Aperte enter para continuar");
		sc.nextLine();
	
	}
	
}
