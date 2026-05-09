package ui.cliente;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import entities.Cliente;
import entities.Restaurante;
import services.PedidoService;
import view.ItemPedidoView;

/**
 * Classe: MenuConfirmacaoPedido
 *
 * Descrição:
 * Classe responsável por fornecer um relatório do pedido do cliente antes do mesmo confirmar a compra
 *
 * Responsabilidades:
 * - fornecer interface
 * - comunicar com a camada serviço
 *
 * @author Rodrigo
 * @since 05-05-2026
 */


public class MenuConfirmacaoPedido {
	
	private PedidoService servicopedido;
	private Scanner sc;
	
	public MenuConfirmacaoPedido(Connection conn, Scanner sc) {
		this.servicopedido = new PedidoService(conn);
		this.sc = sc;
	}
	
	
	/**
	 * exibe todos os detalhes do pedido do usuário, solicita confirmação e retorna se o pedido foi confirmado ou não
	 * @param r objeto restaurante
	 * @param c objeto cliente
	 * @param carrinhoCompras do cliente
	 * @return true se o pedido foi criado
	 */
	public boolean mostrarDetalhesPedido(Restaurante r, Cliente c, ArrayList<ItemPedidoView> carrinhoCompras) {
		double valorTotal = 0;
		
		System.out.println("\n============================================================================");
		System.out.println("RESUMO DETALHADO DO PEDIDO");
		System.out.println("============================================================================");
		System.out.printf("Nome do cliente: %s %s\n", c.getPrimeiroNome(), c.getUltimoNome());
		
		System.out.println("============================================================================");
		System.out.println("Lista de produtos:");
		
		for (int i = 0; i < carrinhoCompras.size(); i++) {
			double precoQuantidade = carrinhoCompras.get(i).getPreco()*carrinhoCompras.get(i).getQuantidade();
			valorTotal += precoQuantidade;
			System.out.println(	carrinhoCompras.get(i));
		}
		
		System.out.println("============================================================================");
		System.out.println("SUB-TOTAL");
		System.out.printf("R$ %.2f\n", valorTotal);
		System.out.println("============================================================================");
		System.out.println("SUB-TOTAL COM DESCONTO");
		
		valorTotal = servicopedido.calcularDesconto(valorTotal);
		
		System.out.printf("R$ %.2f\n", valorTotal);
		System.out.println("============================================================================");
		System.out.println("TAXA DE ENTREGA:");
		System.out.println("R$ 8.00 adicionados ao total");
		
		valorTotal = servicopedido.adicionarTaxaEntrega(valorTotal);
		
		System.out.println("============================================================================");
		System.out.println("VALOR TOTAL");
		System.out.printf("%.2f\n", valorTotal);
		System.out.print("============================================================================\n\n");
		
		System.out.println("Deseja confirmar o pedido?");
		
		int escolhaUsuario = this.exibirMenuAcao();
		
		switch (escolhaUsuario) {
			case 1:
				System.out.println("Pedido concluído com sucesso!");
				servicopedido.cadastrarPedido(r, c, carrinhoCompras);
				return true;
			case 2:
				System.out.println("Voltando ao menu de pedidos");
		}
		
		return false;	
	}
	
	/**
	 * Exibe o menu das ações disponíveis para o usuário confirmar o pedido, cancelar ou adicionar mais algum produto
	 * @return número da ação desejada
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
				
				//verificar se a opção do usuário está fora do intervalo permitido
				if (option >= 1 && option <= 2) {
					//retorna a ação desejada do usuário
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
