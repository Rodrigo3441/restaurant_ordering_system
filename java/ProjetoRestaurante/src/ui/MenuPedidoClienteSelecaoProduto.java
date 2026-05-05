package ui;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import entities.Cliente;
import entities.Restaurante;
import services.PedidoService;
import services.ProdutoRestauranteService;
import view.ItemPedidoView;
import view.ProdutoRestauranteView;

/**
 * Classe: MenuPedidoClienteSelecaoProduto
 *
 * Descrição:
 * Classe responsável por oferecer a interface para o cliente escolher
 * os produtos que ele deseja comprar
 *
 * Responsabilidades:
 * - oferecer menus interativos para o restaurante
 *
 * @author Rodrigo
 * @since 04-05-2026
 */

public class MenuPedidoClienteSelecaoProduto {
	private Scanner sc = new Scanner(System.in);
	private PedidoService servicopedido;
	private ProdutoRestauranteService servicoprodutorestaurante;

	
	/**
	 * 
	 * @param conn
	 */
	public MenuPedidoClienteSelecaoProduto(Connection conn) {
		this.servicopedido = new PedidoService(conn);
		this.servicoprodutorestaurante = new ProdutoRestauranteService(conn);
	}
	
	
	/**
	 * Exibe todos os produtos do restaurante escolhido pelo usuário e permite que ele adicione esses
	 * produtos ao carrinho de compras dele
	 * @param r objeto Restaurante
	 * @param c objeto Cliente
	 */
	public void mostrarProdutos(Restaurante r, Cliente c) {

		//armazena todos os produtos do restaurante escolhido pelo usuário
		ArrayList<ProdutoRestauranteView> listaProdutos = servicoprodutorestaurante.retornarTodoProdutoRestaurante(r.getCnpj());
		
		ArrayList<ItemPedidoView> carrinhoCompras = new ArrayList<ItemPedidoView>();
		
		//interrompe se o restaurante não tiver pelo menos um produto
		if (listaProdutos.isEmpty()) {
			System.out.println("ERRO: não há produtos cadastrados para esse restaurante!");
			return;
		}
		
		//loop que permite o usuário adicionar vários produtos ao carrinho
		while (true) {
			int contador = 1; //enumerador dos produtos
			int index;		  //indice do produto escolhido pelo usuário
			
			//Imprime o atual carrinho de compras do usuário
			System.out.println("\n\nSEU CARRINHO DE COMPRAS");
			System.out.println("============================================================================");
			if (carrinhoCompras.isEmpty()) {
				System.out.println("Seu carrinho de compras está vazio!");
			} else {
				for (ItemPedidoView ip: carrinhoCompras) {
					System.out.println(ip);
				}
			}
			System.out.print("============================================================================\n\n");
			
			System.out.printf("PRODUTOS DO RESTAURANTE %s:\n", r.getNome());
			System.out.println("============================================================================");
			for (ProdutoRestauranteView prd: listaProdutos) {
				System.out.println(contador + "- " + prd.formatarParaCliente());
				contador++;
			}
			System.out.print("============================================================================\n\n");
			
			System.out.println("Digite o índice do produto que você deseja adicionar no carrinho");
			System.out.print("Ou digite -1 para encerrar e ir para os detalhes da compra: ");
			
			//campo para validação entrada pelo usuário
			while (true) {
			    try {
			    	index = sc.nextInt();
				    sc.nextLine();
				    
				    if (index == -1 && !carrinhoCompras.isEmpty()) {
				    	System.out.println("Mostrando detalhes da compra");
				    }
				    
				    index--; //usuário enxerga de (1)à(N). Computador enxerga de (0)à(N-1)
				    
			        servicopedido.validarIndexProduto(listaProdutos, index);
			        break;
			    } catch (IllegalArgumentException e) {
			        System.out.println(e.getMessage());
			    } catch (Exception e) {
			    	sc.nextLine();
			    	System.out.print("Digite um índice válido: ");
			    }
			}
			
			//armazena o produto escolhido só no momento de adicionar ao carrinho
			ProdutoRestauranteView produtoTemp = listaProdutos.get(index);
			
			System.out.printf("Produto escolhido: %s\n", produtoTemp.getNomeProduto());
			System.out.print("Deseja adicionar esse produto ao seu carrinho? (s-sim/n-não): ");
			
			//usuário informa se confirma ou não a ação de inserir no carrinho
			outer:
			while (true) {
				String escolha = sc.next().trim().toLowerCase();
			
				switch (escolha) {
				case "s":
					// Antes de adicionar, verifica se o produto já está no carrinho
					// e guarda o índice dele (ou -1 caso não exista)
					int indexProdutoExistente = servicopedido.retornarPosicaoItemCarrinho(carrinhoCompras, produtoTemp.getCodigoProduto());
					
					if (indexProdutoExistente != -1) {
						ProdutoRestauranteView produto = servicopedido.retornarProdutoPeloCodigo(listaProdutos, produtoTemp.getCodigoProduto());
						this.atualizarItemPedido(produto, carrinhoCompras.get(indexProdutoExistente));
					} else {
						carrinhoCompras.add(this.criarItemPedido(produtoTemp, carrinhoCompras));
					}
					
					break outer;
				case "n":
					break outer;
				default:
					System.out.print("Digite uma opção válida: ");
				}
			}
			
		}
		
	}

	/**
	 * solicita quantas unidades de um produto o usuário deseja comprar
	 * @param produtoTemp o produto que o usuário escolheu
	 * @param carrinhoCompras todos os produtos que o cliente escolheu
	 * @return ItemPedidoView
	 */
	private ItemPedidoView criarItemPedido(ProdutoRestauranteView produtoTemp, ArrayList<ItemPedidoView> carrinhoCompras) {
		int quantidade; 
		
		System.out.printf("Digite quantas unidades de %s você deseja adicionar ao carrinho: ", produtoTemp.getNomeProduto());
		
		//campo para validação da quantidade escolhida pelo usuário
		while (true) {
		    try {
		    	quantidade  = sc.nextInt();
			    sc.nextLine();

		        servicopedido.validarQuantidade(produtoTemp, quantidade);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Digite um índice válido: ");
		    }
		}
		
		return servicopedido.criarItemPedido(produtoTemp, quantidade);	
	}
	
	/**
	 * atualiza a quantidade de um produto que o usuário já inseriu no carrinho
	 * @param produto do restaurante
	 * @param item escolhido pelo cliente
	 */
	private void atualizarItemPedido(
		ProdutoRestauranteView produto, 
		ItemPedidoView item
	) {
		int quantidade;

		System.out.printf("O produto %s já está no carrinho. Deseja atualizar a quantidade? (s-sim/n-não)", item.getNome());
		
		//usuário informa se deseja ou não atualizar a quantidade do produto no carrinho
		outer:
		while (true) {
			String escolha = sc.next().trim().toLowerCase();
		
			switch (escolha) {
			case "s":	
				
				System.out.printf("Digite quantas unidades de %s você deseja comprar no total: ", item.getNome());
				
				//campo para validação da quantidade escolhida pelo usuário
				while (true) {
				    try {
				    	quantidade  = sc.nextInt();
					    sc.nextLine();

				        servicopedido.validarQuantidade(produto, quantidade);
				        item.setQuantidade(quantidade);
				        break;
				    } catch (IllegalArgumentException e) {
				        System.out.println(e.getMessage());
				    } 
				    catch (Exception e) {
				    	sc.nextLine();
				    	System.out.print("Digite uma quantidade válida: ");
				    }
				}
				
				break outer;
			case "n":
				break outer;
			default:
				System.out.print("Digite uma opção válida: ");
			}
		}

	}
}
