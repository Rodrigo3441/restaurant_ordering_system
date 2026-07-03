package ui.restaurant;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import entities.Product;
import entities.RestaurantProduct;
import entities.Restaurant;
import services.ProductService;
import services.OrderService;
import services.RestaurantProductService;
import view.RestaurantProductView;

/**
 * Class: RestaurantProductMenu
 *
 * Description:
 * Class responsible for providing an interface for the restaurant to manage its products
 *
 * Responsibilities:
 * - provide a data communication interface
 * - communicate with the service layer
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class RestaurantProductMenu {
	
	private Scanner sc;
	private ProductService servicoproduto;
	private RestaurantProductService servicoprodutorestaurante;
	private OrderService servicoPedido;
	
	
	public RestaurantProductMenu(Connection conn, Scanner sc) {
		this.servicoprodutorestaurante = new RestaurantProductService(conn);
		this.servicoproduto = new ProductService(conn);
		this.servicoPedido = new OrderService(conn);
		this.sc = sc;
	}
	
	/**
	 * Display the menu for the restaurant to manage its product catalog
	 * @param r restaurant object
	 */
	public void mostrarMenuProdutos(Restaurant r) {
		int option = 9;
		
		// validate the user's menu option input
		while (true) {
			
			System.out.println("\nMENU PRODUTOS DO RESTAURANTE");
			System.out.println("================================================");
			System.out.println("1- Cadastrar um novo produto");
			System.out.println("2- Gerenciar produtos cadastrados");
			System.out.println("3- Voltar ao menu anterior");
			System.out.println("================================================\n");
			
			System.out.print("Informe a ação desejada: ");
			
			try {
				
				option = sc.nextInt();
				sc.nextLine();
				
			} catch (Exception e) {
				sc.nextLine();
				System.out.print("Digite apenas números (1-3): ");
			}
			
			// process the menu options
			switch (option) {
				case 1:
					this.cadastrarProduto(r.getId());
					break;
				case 2:
					this.gerenciarProdutosCadastrados(r.getId());
					break;
				case 3:
					System.out.println("Voltando ao menu anterior");
					return;
				default:
					System.out.print("Opção inválida, tente novamente: ");
				
			}

		}
		
	}
	
	/**
	 * Display the interface for the restaurant to add a product to its catalog
	 * Determines whether the product is already globally registered and should only be associated,
	 * or if it should be inserted from scratch
	 * @param cnpj restaurant identifier in session
	 */
	private void cadastrarProduto(String cnpj) {
		String nomeProduto;
		
		// field for validating the product name
		while (true) {
			System.out.print("Insira o nome do produto que você deseja cadastrar (3-40 letras): ");
			nomeProduto = sc.nextLine().trim().toLowerCase();

		    try {
		    	servicoproduto.validarNome(nomeProduto);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		Product p = servicoproduto.buscarProdutoPorNome(nomeProduto);
		
		// check whether a product with the same name was returned
		if (p != null) {
			if (servicoprodutorestaurante.produtoJaEstaCadastrado(cnpj, p.getNumber())) {
				System.out.println("Esse produto já está associado ao restaurante!");
				return;
			}
			
			System.out.print("Esse produto já está cadastrado no catálogo global. Deseja adicionar ele no catálogo do seu restaurante? (1-sim/2-não): ");
			
			int option = 9;
			
			// validate the decision option entered by the user
			do {
				try {
					option = sc.nextInt();
					sc.nextLine();

				} catch (Exception e) {
					sc.nextLine();
					System.out.println("Digite apenas números: ");
					option = -1;
				}
				
				// process decision options
				switch (option) {
					case 1:
						this.associarProdutoRestaurante(p, cnpj);
						return;
					case 2:
						System.out.println("A operação foi cancelada pelo usuário.");
						return;
					default:
					    System.out.println("Digite uma opção válida (1 ou 2)");
						
				}

			} while (true);
			
		}
		
		this.cadastrarProdutoNovo(cnpj, nomeProduto);
	}
		
	/**
	 * If the product name entered by the user is not found in the database,
	 * this method is used to register it globally and then associate it with the restaurant
	 * @param cnpj
	 * @param nomeProduto
	 */
	private void cadastrarProdutoNovo(String cnpj, String nomeProduto) {
		int codigo;
		String descricaoProduto;
		
		// field for validating the product description
		while (true) {
			System.out.print("Insira a descrição do produto: ");
			descricaoProduto = sc.nextLine().trim().toLowerCase();

		    try {
		    	servicoproduto.validarDescricao(descricaoProduto);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for validating the product code
		while (true) {
			System.out.print("Insira o código do produto no catálogo global (Um valor numérico): ");
			
		    try {
		    	codigo = sc.nextInt();
				sc.nextLine();
				
		    	servicoproduto.validarCodigo(codigo);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		System.out.println("\nCONFIRMANDO INFORMAÇÕES: ");
		System.out.println("================================================");
		System.out.printf("Código global do produto: %s\n", codigo);
		System.out.printf("Nome do produto: %s\n", nomeProduto);
		System.out.printf("Descrição do produto: %s\n", descricaoProduto);
		System.out.println("================================================\n");
		
		System.out.print("Deseja confirmar as informações? (s para sim/n para cancelar): ");
		
		// validate the user's choice
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("s")) {
				// instantiate a new product and assign attributes
				Product p = new Product();
				
				p.setNumber(codigo);
				p.setName(nomeProduto);
				p.setDescription(descricaoProduto);
				
				// call the registration method and verify success
				if(servicoproduto.inserirProduto(p)) {
					System.out.println("Produto cadastrado no catálogo global com sucesso!");
					
					// after registering globally, associate with the restaurant
					this.associarProdutoRestaurante(p, cnpj);
					
				} else {
					System.out.println("Ocorreu um erro desconhecido ao tentar cadastrar.");
				}
				
				break;
				
			} else if (opt.equals("n")) {
				System.out.println("Nada foi alterado");
				return;
				
			} else {
				System.out.print("Opção inválida, tente novamente: ");
			}
			
		}
	}
	
	/**
	 * Associate a product from the global catalog with a specific restaurant
	 * @param p product object
	 * @param cnpj restaurant identifier
	 */
	private void associarProdutoRestaurante(Product p, String cnpj) {
		int quantidadeEstoque;
		double preco;
		
		// field for validating the product stock quantity
		while (true) {
			System.out.printf("Insira a quantidade atual em estoque do produto %s: ", p.getName());
			
		    try {
		    	quantidadeEstoque = sc.nextInt();
				sc.nextLine();
				
		    	servicoprodutorestaurante.validarQuantidadeEstoque(quantidadeEstoque);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.println("Digite um valor válido para a quantidade em estoque");
		    }
		}
		
		// field for validating the product price
		while (true) {
			System.out.printf("Insira o preço do produto %s: ", p.getName());
			
		    try {
		    	preco = sc.nextDouble();
				sc.nextLine();
				
		    	servicoprodutorestaurante.validarPrecoProduto(preco);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.println("Digite um preço válido para o produto");
		    }
		}
		
		System.out.println("\nCONFIRMANDO INFORMAÇÕES: ");
		System.out.println("================================================");
		System.out.printf("Código global do produto: %d\n", p.getNumber());
		System.out.printf("Nome do produto: %s\n", p.getName());
		System.out.printf("Quantidade em estoque: %d\n", quantidadeEstoque);
		System.out.printf("Preço do produto: R$ %.2f\n", preco);
		System.out.println("================================================\n");
		
		System.out.print("Deseja confirmar as informações? (s para sim/n para cancelar): ");
		
		// validate the user's choice
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("s")) {
				// instantiate a new restaurant product and assign attributes
				RestaurantProduct pr = new RestaurantProduct();
				
				pr.setRestaurantId(cnpj);
				pr.setProductNumber(p.getNumber());
				pr.setStockAmount(quantidadeEstoque);
				pr.setPrice(preco);
				
				// call the association method and verify success
				if(servicoprodutorestaurante.associarProdutoRestaurante(pr)) {
					System.out.println("Produto associado ao catálogo do restaurante com sucesso!");
					return;
					
				} else {
					System.out.println("Ocorreu um erro desconhecido ao tentar associar.");
				}
				
				break;
				
			} else if (opt.equals("n")) {
				System.out.println("Nada foi alterado");
				return;
				
			} else {
				System.out.print("Opção inválida, tente novamente: ");
			}
			
		}
		
	}
	
	/**
	 * Display all products associated with the restaurant in session
	 * @param cnpj restaurant identifier
	 */
	public void gerenciarProdutosCadastrados(String cnpj) {
		int option = 0;
		ArrayList<RestaurantProductView> listaProdutos = servicoprodutorestaurante.retornarTodoProdutoRestaurante(cnpj);

		// stop execution when there are no registered products
		if (listaProdutos.isEmpty()) {
			System.out.println("Não há produtos cadastrados para o restaurante!");
			return;
		}
		
		System.out.println("EXIBINDO TODOS OS PRODUTOS DO RESTAURANTE:");
		
		// print each product for that restaurant with an index
		System.out.println("\n============================================================================");
		for (int i = 0; i < listaProdutos.size(); i++) {
			System.out.println(i+1 + "- " + listaProdutos.get(i));
		}
		System.out.println("============================================================================\n");
		
		System.out.println("Deseja realizar alguma ação?");
		System.out.println("================================================");
		System.out.println("1- Atualizar a quantidade em estoque");
		System.out.println("2- Remover um produto do restaurante");
		System.out.println("3- Voltar ao menu anterior");
		System.out.println("================================================");
		
		System.out.print("Informe a ação desejada: ");
		
		while (true) {
			
			try {
				
				option = sc.nextInt();
				sc.nextLine();
				
			} catch (Exception e) {
				sc.nextLine();
			}
			
			// process the menu options for the user
			switch (option) {					
				case 1:
					this.atualizarQuantidadeEstoque(cnpj, listaProdutos);
					return;
					
				case 2:
					this.removerProduto(cnpj);
					return;
				case 3:
					System.out.println("Voltando ao menu principal");
					return;
					
				default: 
					System.out.print("Opção inválida, tente novamente (1-4): ");
			}

		}
		
	}
	
	/**
	 * Provide the interaction for the restaurant to update the stock quantity of a specific product
	 * @param cnpj restaurant identifier
	 * @param listaProdutos all products of the restaurant
	 */
	private void atualizarQuantidadeEstoque(String cnpj, ArrayList<RestaurantProductView> listaProdutos) {
		int index;
		int quantidadeEstoque;
		
		System.out.println("Digite o índice do produto que você deseja atualizar: ");
		
		// field for validating user input
		while (true) {
		    try {
		    	index = sc.nextInt();
			    sc.nextLine();
			    
			    index--; // user sees from 1 to N, computer uses 0 to N-1
			    
		        servicoPedido.validarIndex(listaProdutos, index);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Digite um índice válido: ");
		    }
		}
		
		RestaurantProductView produtoAlvo = listaProdutos.get(index);

		
		System.out.printf("Digite a nova quantidade em estoque do produto %s: ", produtoAlvo.getProductName());
		
		// field for validating the product's stock quantity
		while (true) {
		
		    try {
		    	quantidadeEstoque = sc.nextInt();
				sc.nextLine();
				
		    	servicoprodutorestaurante.validarQuantidadeEstoque(quantidadeEstoque);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.println("Digite um valor válido para a quantidade em estoque");
		    }
		}
		
		System.out.printf("Deseja confirmar a atualização da quantidade em estoque do produto %s? (s-sim/n-não): ", produtoAlvo.getProductName());
		// the restaurant confirms whether to update the stock quantity
		while (true) {
			String escolha = sc.next().trim().toLowerCase();
		
			switch (escolha) {
				case "s":	
					if (servicoprodutorestaurante.atualizarProdutoRestaurante(cnpj, produtoAlvo, quantidadeEstoque)) {
						System.out.println("Quantidade em estoque do produto atualizada com sucesso!");
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
	 * Allow the restaurant to provide a product code and remove it from its catalog
	 * @param cnpj restaurant identifier in session
	 */
	private void removerProduto(String cnpj) {
		int codigo;
		System.out.print("Insira o código do produto que você deseja remover do restaurante: ");
		
		// field for validating the product code
		while (true) {
	
		    try {	
		    	codigo = sc.nextInt();
				sc.nextLine();
			    	
		        servicoprodutorestaurante.validarCodigoProduto(codigo);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Digite um código válido: ");
		    }
		}
		
		Product alvo = servicoproduto.buscarProdutoPorId(codigo);
		
		if (alvo != null) {
			System.out.printf("Deseja apagar o produto %s do seu restaurante? (s-sim/n-não): ",alvo.getName());
			
			// validate the user's choice
			while (true) {
				
				String opt = sc.next();
				
				if (opt.equals("s")) {
				
					// try to remove the product from the restaurant's catalog and verify success
					try {
						servicoprodutorestaurante.apagarProdutoRestaurante(cnpj, codigo);
						System.out.println("Produto deletado do restaurante com sucesso!");
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					
					break;
					
				} else if (opt.equals("n")) {
					System.out.println("Nada foi alterado");
					return;
					
				} else {
					System.out.print("Opção inválida, tente novamente: ");
				}
				
			}
			
		} else {
			System.out.println("Não há nenhum produto cadastrado com esse código.");
			return;
		}
		
		
	}
}
