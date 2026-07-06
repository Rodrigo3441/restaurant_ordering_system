package ui.customer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import entities.Customer;
import entities.Restaurant;
import services.OrderService;
import services.RestaurantProductService;
import view.OrderItemView;
import view.RestaurantProductView;

/**
 * Class: ProductSelectionMenu
 *
 * Description:
 * Class responsible for providing the interface for the customer to choose
 * the products they want to buy
 *
 * Responsibilities:
 * - provide interactive menus for the restaurant
 *
 * @author Rodrigo
 * @since 04-05-2026
 */

public class ProductSelectionMenu {
	
	private OrderService servicopedido;
	private RestaurantProductService servicoprodutorestaurante;
	private Connection conn;
	private Scanner sc;

	public ProductSelectionMenu(Connection conn, Scanner sc) {
		this.servicopedido = new OrderService(conn);
		this.servicoprodutorestaurante = new RestaurantProductService(conn);
		this.conn = conn;
		this.sc = sc;
	}
	
	
	/**
	 * Displays all products from the restaurant chosen by the user and allows them
	 * to add these products to their shopping cart
	 * @param r Restaurant object
	 * @param c Customer object
	 */
	public void mostrarProdutos(Restaurant r, Customer c) {

		// Stores all products offered by the restaurant selected by the customer
		ArrayList<RestaurantProductView> listaProdutos = servicoprodutorestaurante.returnAllProductsPerRestaurant(r.getId());
		
		// Stores all products that the user has added to the shopping cart
		ArrayList<OrderItemView> carrinhoCompras = new ArrayList<OrderItemView>();
		
		// Stop if the restaurant has no products registered
		if (listaProdutos.isEmpty()) {
			System.out.println("ERRO: não há produtos cadastrados para esse restaurante!");
			return;
		}
		
		// Loop that allows the user to add multiple products to the cart
		while (true) {
			
			// Prints the current user's shopping cart
			System.out.println("\n\nSEU CARRINHO DE COMPRAS");
			System.out.println("============================================================================");
			
			if (carrinhoCompras.isEmpty()) {
				System.out.println("Seu carrinho de compras está vazio!");
			} else {
				// Prints all products with an index enumerating each item
				for (int i = 0; i < carrinhoCompras.size(); i++) {
					System.out.println(i+1 + "- " + carrinhoCompras.get(i));
				}
			}
			System.out.print("============================================================================\n\n");
			
			System.out.printf("PRODUTOS DO RESTAURANTE %s:\n", r.getName());
			System.out.println("============================================================================");
			
			for (int i = 0; i < listaProdutos.size(); i++) {
				System.out.println(i+1 + "- " + listaProdutos.get(i));
			}
			System.out.print("============================================================================\n\n");
			
			
			int escolhaUsuario = this.exibirMenuAcao();
			
			switch (escolhaUsuario) {
				case 1:
					this.escolherItemPedido(listaProdutos, carrinhoCompras);
					break;
				case 2:
					this.removerItemPedido(carrinhoCompras);
					break;
				case 3:
					// Access the details menu if the cart is not empty
					if (carrinhoCompras.isEmpty()) {
						System.out.println("ERRO: o carrinho de compras está vazio!");
						break;
					}
					
					OrderConfirmationMenu menuconfirmacao = new OrderConfirmationMenu(conn, sc);
					
					// Calls the method and stores whether the order was placed or not
					boolean pedidoRealizado = menuconfirmacao.mostrarDetalhesPedido(r, c, carrinhoCompras);
					
					// If it was placed, this method will return here
					if (pedidoRealizado) {
						return;
					}					
					
					break;
				case 4:
					System.out.print("Compra cancelada, aperte enter para continuar: ");
					sc.nextLine();
					return;
			}
			
		}
		
	}
	
	/**
	 * Displays the available action menu for the order screen
	 * @return desired action number
	 */
	private int exibirMenuAcao() {
		int option;
	
		System.out.println("================================================");
		System.out.println("1- adicionar produto ao carrinho");
		System.out.println("2- Remover produto do carrinho");
		System.out.println("3- Encerrar Compra");
		System.out.println("4- Cancelar compra");
		System.out.println("================================================\n");
		
		System.out.print("Informe a ação desejada: ");
			
		while (true) {
			try {
				option = sc.nextInt();
				sc.nextLine();
				
				// Check if the user's option is outside the allowed range
				if (option >= 1 && option <= 4) {
					// Return the action selected by the user
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
	
	/**
	 * Allows the user to choose a product by index to add to the shopping cart
	 * @param listaProdutos list of all products from the selected restaurant
	 * @param carrinhoCompras all products that the user has already chosen
	 */
	private void escolherItemPedido(ArrayList<RestaurantProductView> listaProdutos, ArrayList<OrderItemView> carrinhoCompras) {
		int index;		  //indice do produto escolhido pelo usuário
		
		System.out.println("Digite o índice do produto que você deseja adicionar no carrinho");
		
		// field for validating user input
		while (true) {
		    try {
		    	index = sc.nextInt();
			    sc.nextLine();
			    
				index--; // user sees from (1) to (N). Computer sees from (0) to (N-1)
			    
		        servicopedido.checkIndex(listaProdutos, index);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Digite um índice válido: ");
		    }
		}
		
		// store the chosen product only at the moment of adding it to the cart
		RestaurantProductView produtoTemp = listaProdutos.get(index);
		
		// check to prevent selecting a product with no stock
		if (produtoTemp.getStockAmount() == 0) {
			System.out.println("Esse produto está sem estoque!");
			return;
		}
		
		System.out.printf("Produto escolhido: %s\n", produtoTemp.getProductName());
		System.out.print("Deseja adicionar esse produto ao seu carrinho? (s-sim/n-não): ");
		
		// user indicates whether they confirm the action of adding it to the cart
		outer:
		while (true) {
			String escolha = sc.next().trim().toLowerCase();
		
			switch (escolha) {
				case "s":
					// Before adding, check if the product is already in the cart
					// and store its index (or -1 if it does not exist)
					int indexProdutoExistente = servicopedido.returnCartItemPosition(carrinhoCompras, produtoTemp.getProductNumber());
					
					if (indexProdutoExistente != -1) {
						RestaurantProductView produto = servicopedido.returnProductByNumber(listaProdutos, produtoTemp.getProductNumber());
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

	/**
	 * Prompts how many units of a product the user wants to buy
	 * @param produtoTemp the product chosen by the user
	 * @param carrinhoCompras all products the customer chose
	 * @return OrderItemView
	 */
	private OrderItemView criarItemPedido(
		RestaurantProductView produtoTemp, 
		ArrayList<OrderItemView> carrinhoCompras
	) {
		int quantidade; 
		
		System.out.printf("Digite quantas unidades de %s você deseja adicionar ao carrinho: ", produtoTemp.getProductName());
		
		// field for validating the quantity chosen by the user
		while (true) {
		    try {
		    	quantidade  = sc.nextInt();
			    sc.nextLine();

		        servicopedido.checkQuantity(produtoTemp, quantidade);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Digite um índice válido: ");
		    }
		}
		
		return servicopedido.createOrderItem(produtoTemp, quantidade);	
	}
	
	/**
	 * Updates the quantity of a product that the user already added to the cart
	 * @param produto restaurant product
	 * @param item chosen by the customer
	 */
	private void atualizarItemPedido(
		RestaurantProductView produto, 
		OrderItemView item
	) {
		int quantidade;

		System.out.printf("O produto %s já está no carrinho. Deseja atualizar a quantidade? (s-sim/n-não)", item.getName());
		
		// user indicates whether they want to update the product quantity in the cart
		outer:
		while (true) {
			String escolha = sc.next().trim().toLowerCase();
		
			switch (escolha) {
			case "s":	
				
				System.out.printf("Digite quantas unidades de %s você deseja comprar no total: ", item.getName());
				
				// field for validating the quantity chosen by the user
				while (true) {
				    try {
				    	quantidade  = sc.nextInt();
					    sc.nextLine();

				        servicopedido.checkQuantity(produto, quantidade);
				        item.setQuantity(quantidade);
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
	
	/**
	 * Prompts the index of the product the user wants to remove from the shopping cart
	 * @param carrinhoCompras user's shopping cart
	 */
	private void removerItemPedido(ArrayList<OrderItemView> carrinhoCompras) {
		// index of the product that will be removed from the cart
		int index; 
		
		if (carrinhoCompras.isEmpty()) {
			System.out.println("ERRO: não há produtos adicionados no carrinho!");
			return;
		}
		
		System.out.print("Digite o índice do produto que você deseja remover do carrinho: ");
		
		// field for validating user input
		while (true) {
		    try {
		    	index = sc.nextInt();
			    sc.nextLine();
		    	
			    index--; // user sees from (1) to (N). Computer sees from (0) to (N-1)
				
		        servicopedido.checkIndex(carrinhoCompras, index);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Digite um índice válido: ");
		    }
		}
		
		System.out.printf("Deseja remover o produto %s do seu carrinho? (s-sim/n-não): ", carrinhoCompras.get(index).getName());
		
		// user indicates whether they want to remove the product from the cart
		while (true) {
			String escolha = sc.next().trim().toLowerCase();
		
			switch (escolha) {
				case "s":	
					carrinhoCompras.remove(index);			
					return;
				case "n":
					System.out.println("Nada foi alterado");
					return;
				default:
					System.out.print("Digite uma opção válida: ");
			}
		}
	}
}
